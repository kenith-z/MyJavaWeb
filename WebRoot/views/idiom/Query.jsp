<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../Head.jsp" %>

<script type="text/javascript">
//编辑成语
function editItem(itemId) {
		//location表示浏览器 的地址栏
		var uri = "${PATH}/Edit.act?option=EDIT&itemId="+itemId;
		window.open(uri);//弹出窗口（新标签页)
	}
//删除成语
function removeItem(itemId,name) {
	var tf = confirm("确定要删除成语【"+name+"】吗？");
	if(tf){
		theform.action="${PATH}/Remove.act";
		theform.itemId.value= itemId;
		theform.submit();
	}
}
//生成并下载Excel
function doExcel(){
	var target=theform.target;
	var option=theform.option.value;
	theform.target = "footframe";
	theform.option.value = "DOWNLOAD";
	disableAllButton(theform);//使表单中的按钮无效
	var loading = document.getElementById("loading");
	loading.style.display = "block";//显示loading图片;
	theform.submit();//提交表单
	//定时器（参数一是回调函数，参数而是等待毫秒数）,在执行完成一定时间后，还原表单的相关属性
	setTimeout(function(){
		theform.target = target;
		theform.option.value = option;
		enableAllButton(theform);//恢复表单中的所有按钮
		loading.style.display = "none";
	},3000);
}
//浏览成语明细内容
function showItem(itemId){
location = "${PATH}/Edit.act?option=SHOW&itemId="+itemId;
}
</script>
 
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC}</div>
<%@include file="Menu.jsp" %>
<form name="theform" action="${PATH}/Query.act" method="post" target="">
	<span>成语名称：</span>
    <input type="text" name="word" value="${word}" class="text0" size="4" maxlength="8" style="text-align:center;" />
    <span>&nbsp;&nbsp;释义：</span>
    <input type="text" name="paraphrase" value="${paraphrase}" class="text0" size="8" maxlength="16" style="text-align:center;" />
	<span>&nbsp;&nbsp;出处：</span>
    <input type="text" name="provenance" value="${provenance}" class="text0" size="4" maxlength="16" style="text-align:center;" />
	<span>&nbsp;&nbsp;拼音：</span>
	<select name="shengmu">
		<option value=""></option>
		<c:forEach items="${sms}" var="item">
		<option value="${item}" ${(item==shengmu)?'selected':''}>${item}</option>	
		</c:forEach>
	</select>
	<select name="iu">
		<option value=""></option>
		<option value="i" ${(iu=='i')?'selected':''}>i</option>
		<option value="u" ${(iu=='u')?'selected':''}>u</option>
	</select>
	
	<select name="yunmu">
		<option value=""></option>
		<c:forEach items="${yms}" var="item">
		<option value="${item}" ${(item==yunmu)?'selected':''}>${item}</option>	
		</c:forEach>
	</select>
	<span>&nbsp;&nbsp;数字开头：</span>
	<select name="numhead">
		<option value="">
		<c:forTokens items="一,二,三,四,五,六,七,八,九,十,百,千,万,亿" delims="," var="item">
		<option value="${item}" ${(item==numhead)?'selected':''}>${item}</option>	
		</c:forTokens>
		</option>
		
	</select>&nbsp;&nbsp;
	<input type="button" name="bt1" value="查询" onclick="goPage(this.form,1)" />
	<input type="button" name="bt1" value="下载Excel" onclick="doExcel()" />
	<input type="hidden" name="itemId" value="" />
	<input type="hidden" name="option" value="LIST">
	<input type="hidden" name="page" value="${page}">
</form>
<c:if test="${not empty list}">
<table id="DataGrid" border="1" style="width:980px">
	<tr style="background-color: #CCCCCC; ">
		<td style="width:40px">序号</td>
		<td style="width:160px">成语</td>
		<td style="width:200px">拼音</td>
		<td style="width:240px">示意</td>
		<td style="width:160px">出处</td>
		<td style="width:160px">示例</td>
		<td style="width:80px">操作</td>
	</tr>
	<span>共找到${rowsCount}条成语</span>
	<c:forEach items="${list}" var="item" varStatus="vs">
	<tr style="background-color: #${(vs.count%2==0)?'F0E68C':'E0FFFF'}; ">
		<td>${vs.count}</td>
		<td><a href="javascript:showItem('${item.item_id}')">${item.name}</a></td>
		<td>${item.pinyin}</td>
		<td style="text-align: left">${item.paraphrase}</td>
		<td style="text-align: left">${item.provenance}</td>
		<td style="text-align: left">${item.example}</td>
		<td>
		<a href="javascript:editItem('${item.item_id}')">修改</a> | 
		<a href="javascript:removeItem('${item.item_id}','${item.name}')">删除</a>
		</td>
	</tr>
	</c:forEach>
</table>
<%@include file="../PageNavigator.jsp" %>
</c:if>

<%@include file="../Foot.jsp" %>
