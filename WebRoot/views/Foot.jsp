<%@ page contentType="text/html; charset=UTF-8"%>
<%
out.println("<div id='loading' style='display:none;position:absolute;top:40%;left:40%;width:160px;Z-index:9999;text-align:center;color:#696969;font-size:12px;'><img src='"+request.getContextPath()+"/image/loading2.gif'/>&nbsp;<br/><span>正在处理中,请稍候......</span></div>");
out.println("<div id='liveboard' title='提示' style='display:none;position:absolute;top:30%;left:30%;width:360px;'>");
out.println("<table align='center' style='border-collapse:collapse' width='360' border='0'>");
out.println("<tr height='60'>");
out.println("<td align='center' id='returninf'>");
out.println("<img id='running' alt='running...' src='"+request.getContextPath()+"/image/loading.gif' border='0'>&nbsp;处理中...");
out.println("</td>");
out.println("</tr>");
out.println("</table>");
out.println("</div>");
out.println("<iframe id='footframe' name='footframe' style='display:none'></iframe>");
out.println("</body>");
out.println("</html>");
%>
