<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="Head.jsp" %>
 
 <%
 //pageContext是页面对象，作用是内置对象中作用范围最小的，只能存在当前页面
 pageContext.setAttribute("PAGE_DESC", "简单计算器");


  %>
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC }</div>
<form name="theform" action="${PATH}/views/Result.jsp" method="post">
    <input type="text" name="num1" value="2" class="text0" size="4" maxlength="4" style="text-align:center;" />
	<select name="opt">
		<option value="0">+</option>
		<option value="1">-</option>
		<option value="2">x</option>
		<option value="3">/</option>
		</select>
	<input type="text" name="num2" value="5" class="text0" size="4" maxlength="4" style="text-align:center;" />	
	<input type="button" name="bt1" value="计算" onclick="this.form.submit();" />
	
	
</form>

<%@include file="Foot.jsp" %>
