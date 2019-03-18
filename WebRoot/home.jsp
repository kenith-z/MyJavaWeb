<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>${APP_NAME}</title>
</head>
  
<body>
<table align="center" border="1" style="width:640px">
	<tr>
		<td style="width:60px;">序号</td>
		<td style="width:120px;">项目</td>
		<td>描述</td>
	</tr>
	<tr>
		<td>1</td>
		<td><a href="<%=path%>/views/Computer.jsp">简单计算器</a></td>
		<td>表单及其控件、页面请求、参数传递、业务处理、结果相应</td>
	</tr>
	<tr>
		<td>2</td>
		<td><a href="<%=path%>/views/Login.jsp">用户登录</a></td>
		<td>内置对象。session(会话),页面挡板，注销，随机验证码(response)</td>
	</tr>
	<tr>
		<td>3</td>
		<td><a href="<%=path%>/views/Luckdraw.jsp">随机抽奖</a></td>
		<td>随机抽取数字</td>
	</tr>
	
	<tr>
		<td>4</td>
		<td><a href="<%=path%>/Query.act">中国成语词典</a></td>
		<td>数据库访问(增删改查)，多条件复合查询、分页查询，简单标签库(JSTL)，MVC开发框架,成语接龙、字形成语、生成Eecel、下载文件</td>
	</tr>
</table>
</body>

</html>
