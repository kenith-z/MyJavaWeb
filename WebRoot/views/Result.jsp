<%@ page language="java"  import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="Head.jsp" %>

<%
//通过内置对象requext（请求）获取页面出入参数
 String num1 = request.getParameter("num1");
 String opt = request.getParameter("opt");
 String num2 = request.getParameter("num2");
 //"键-值"对映射对象
 Map<String,String> opts = new HashMap<String,String>();
 opts.put("0","+");
 opts.put("1","-");
 opts.put("2","x");
 opts.put("3","/");
 //数据类型转换，并计算结果
 String value = "未知";
 try{
 	float fNum1 = Float.parseFloat(num1);//类型转换为单精度浮点数
 	float fNum2 = Float.parseFloat(num2);//类型转换为单精度浮点数
 	float fValue = 0.0f; //计算结果
 	char c = opt.charAt(0);
 	switch(c){
 	case '0':
 		fValue = fNum1 + fNum2;
 		break;
 	case '1':
 		fValue = fNum1 - fNum2;
 		break;
 	case '2':
 		fValue = fNum1 * fNum2;
 		break;
 	case '3':
 		fValue = fNum1 / fNum2;
 		break;
 	default:
 	break;
 	}
 	value = String.valueOf(fValue);//转换为字符串
 	
 }catch(NullPointerException e){
 	session.setAttribute("ERROR", "异常【参数不能为空】");
 	//利用内置对象response（响应），重定向到其他页面
 	response.sendRedirect("Error.jsp");
 	return;
 }catch(NumberFormatException e){
 	session.setAttribute("ERROR", "异常【数值格式非法】");
 	response.sendRedirect("Error.jsp");
 	return;
 }catch(Exception e){
 	session.setAttribute("ERROR", "异常【"+e.getMessage()+"】");
 	response.sendRedirect("Error.jsp");
 	return;
 }
 
 
 
%>

<body>
<div style="font-size:24px;color:#4f2f4f,font-family:宋体;">
<%=num1%> <%=opts.get(opt)%> <%=num2%> = <%=value%>

</div>


<%@include file="Foot.jsp" %>
