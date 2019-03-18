<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 注销会话，将绘画中的所有环境变量清空 --%>
<% session.invalidate(); %>

<%-- 重定向到登录页面，用jsp标签语法 --%>
<jsp:forward page="Login.jsp" />