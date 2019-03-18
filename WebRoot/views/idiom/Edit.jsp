<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../Head.jsp" %>
 
 
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC}</div>
<%@include file="Menu.jsp" %>
<form name="theform" action="${PATH}/${TXCODE}.act" method="post" enctype="multipart/form-data">
	<br/><span>名称：</span>
    <input type="text" name="word" value="${map.name}" class="text0" size="16" maxlength="20" style="text-align:center;" />
    <br/><span>拼音：</span>
    <input type="text" name="pinyin" value="${map.pinyin}" class="text0" size="16" maxlength="64" style="text-align:center;" />
    <br/><span>释义：</span>
    <textarea name="paraphrase" rows="4" cols="32">${map.paraphrase}</textarea>
	<br/><span>出处：</span>
	<textarea name="provenance" rows="4" cols="32">${map.provenance}</textarea>
	<br/><span>示例：</span>
	<textarea name="example" rows="4" cols="32">${map.example}</textarea>
	<br/><span>图片：</span>
	<input type="file" name="upload">
    <br/><input type="button" name="bt1" value="${TXCODE=='Append'?'添加':'保存'}" onclick="this.form.submit();" />
	<input type="hidden" name="option" value="SAVE"/>
	<input type="hidden" name="itemId" value="${map.item_id}"/>
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
		<td style="width:160px">图片</td>
	</tr>
	<span>共找到${fn:length(list)}条成语</span>
	<c:forEach items="${list}" var="item" varStatus="vs">
	<tr style="background-color: #${(vs.count%2==0)?'F0E68C':'E0FFFF'}; ">
		<td>${vs.count}</td>
		<td>${item.name}</td>
		<td>${item.pinyin}</td>
		<td style="text-align: left">${item.paraphrase}</td>
		<td style="text-align: left">${item.provenance}</td>
		<td style="text-align: left">${item.example}</td>
		<td style="text-align: left"></td>
	</tr>
	</c:forEach>
</table>
</c:if>

<%@include file="../Foot.jsp" %>
