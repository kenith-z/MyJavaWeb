<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="Head.jsp" %>
 
 <%
 //pageContext是页面对象，作用是内置对象中作用范围最小的，只能存在当前页面
 pageContext.setAttribute("PAGE_DESC", "随机抽奖");


  %>
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC }</div>
<form name="theform" action="${PATH}/views/DoLuck.jsp" method="post">
<br />起始数:<input type="text" name="Start" value="1" class="text0" size="10" maxlength="10" style="text-align:center;" />
<br />终止数:<input type="text" name="Termination" value="1000" class="text0" size="10" maxlength="10" style="text-align:center;" />
<br />中奖数:<input type="text" name="Number" value="20" class="text0" size="10" maxlength="10" style="text-align:center;" />
<input type="button" name="bt1" value="抽奖" onclick="this.form.submit();" />
</form>

<%@include file="Foot.jsp" %>
