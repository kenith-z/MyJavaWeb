<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../Head.jsp" %>
 
 
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC}</div>
<%@include file="Menu.jsp" %>
<form name="theform" action="${PATH}/Dragon.act" method="post">
	<span>龙首成语：</span>
    <input type="text" name="word" value="${word}" class="text0" size="8" maxlength="8" style="text-align:center;" />
	<input type="button" name="bt1" value="开始接龙" onclick="this.form.submit();" />
</form>
<c:if test="${not empty dragon}">
<table id="DataGrid" border="1" style="width:980px">
	<tr style="background-color: #CCCCCC; ">
		<td style="width:40px">序号</td>
		<td style="width:160px">成语</td>
		<td style="width:200px">拼音</td>
		<td style="width:240px">示意</td>
		<td style="width:160px">出处</td>
		<td style="width:160px">示例</td>
	</tr>
	<span>共找到${fn:length(dragon)}条成语</span>
	<c:forEach items="${dragon}" var="item" varStatus="vs">
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
</c:if>


<%@include file="../Foot.jsp" %>
