<%@page  import="java.util.*"%>
<%@ page language="java" import="cn.gzccc.data.User" import="cn.gzccc.util.DBHelper" pageEncoding="UTF-8"%>

<%
//import="org.apache.catalina.connector.Connector"
//获取页面传入的用户名和密码
String userId = request.getParameter("userId");//用户名
String userPd = request.getParameter("userPd");//密码
String randomCode = request.getParameter("randomCode");//用户输入验证码
String verifyCode = (String)session.getAttribute("verifyCode");//图片上的字符串


//数据有效性检验
if(userId==null||userId.length()==0
	||userPd==null||userPd.length()==0){
	session.setAttribute("ERROR","用户名或密码为空");
	response.sendRedirect("Error.jsp");
	return;
}

//判断随机验证是否一致
if(randomCode==null || !randomCode.equalsIgnoreCase(verifyCode)){
	session.setAttribute("ERROR","验证码错误！");
	response.sendRedirect("Error.jsp");
	return;

}
userId = new String (userId.getBytes("ISO-8859-1"),"UTF-8");

try{
	String sql = "SELECT * FROM user WHERE user_id ="+userId+" ";
	DBHelper dbh = DBHelper.getInstance();
	List<Map<String,String>> list = dbh.query(sql);//执行查询返回结果集
	if(!userId.equals(list.get(0).get("user_id"))){
	return;
	}
//查询数据库，判断密码是否正确
if(!userPd.equals("8888")){
	session.setAttribute("ERROR","密码错误");
	response.sendRedirect("Error.jsp");
	return;
}
//如果用户名和密码正确,则实例化一个用户对象，并设置到会话中，再定向到欢迎页
User user = new User(userId);
user.setUserName(list.get(0).get("user_name"));
//会话(session) :再同一款浏览器窗口中，对同一个站点，持续地访问不同的页面
session.setAttribute("USER", user);//设置为环境变量
response.sendRedirect("Welcome.jsp");
}catch(Exception e){
	session.setAttribute("ERROR","查询用户出错【"+e.getMessage()+"】");
		response.sendRedirect("Error.jsp");
		return;
}


 %>
