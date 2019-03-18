<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../Head.jsp" %>
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC}</div>
<%@include file="Menu.jsp" %>
<form name="theform" action="${PATH}/Shape.act" method="post">
	<span>请输入成语字样 (AABB)：</span>
    <input type="text" name="word" value="${word}" class="text0" size="16" maxlength="16" style="text-align:center;" />
    <span>字出现的位置：</span>
    <c:forEach begin="1" end="${posCount}" var="i">
    <input type="text" name="wd${i}" value="${posChars[i-1]}" class="text0" size="1" maxlength="1" style="text-align:center;" />
	</c:forEach>
	<input type="button" name="bt1" value="查询" onclick="goPage(this.form,1)" />
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
	</tr>
	<span>共找到${rowsCount}条成语</span>
	<c:forEach items="${list}" var="item" varStatus="vs">
	<tr style="background-color: #${(vs.count%2==0)?'F0E68C':'E0FFFF'}; ">
		<td>${vs.count}</td>
		<td>${item.name}</td>
		<td>${item.pinyin}</td>
		<td style="text-align: left">${item.paraphrase}</td>
		<td style="text-align: left">${item.provenance}</td>
		<td style="text-align: left">${item.example}</td>
	</tr>
	</c:forEach>
</table>
<%@include file="../PageNavigator.jsp" %>
</c:if>

<%@include file="../Foot.jsp" %>
