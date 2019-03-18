<%@ page language="java" import="cn.gzccc.data.User" pageEncoding="UTF-8"%>

<%
//页面挡板页面，再用户未登录的情况下不允许其访问受保护的页面
User user = (User)session.getAttribute("USER");
if(user ==null){
	session.setAttribute("ERROR","请先登陆本系统!");
	response.sendRedirect("views/Error.jsp");
	return;
}

 %>
