<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="Baffle.jsp" %>
<%@include file="Head.jsp" %>
 
 <%
 //pageContext是页面对象，作用是内置对象中作用范围最小的，只能存在当前页面
 pageContext.setAttribute("PAGE_DESC", "红尘World");


  %>
 
<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">${PAGE_DESC }</div>
<span>${USER.userName},欢迎进入本系统！</span>
<br/>
<a href="<%=path%>/views/Loginout.jsp">【退出】</a>


<%@include file="Foot.jsp" %>
