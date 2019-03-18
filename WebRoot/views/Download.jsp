<%
String error = (String)request.getAttribute("ERROR");
if(error!=null && error.trim().length()>0){
	response.setContentType("text/html");
	response.setCharacterEncoding("UTF-8");
	out.print("<html><script>alert('"+error+"');</script></html>");
	return;
}
String srcFileName = (String)request.getAttribute("filepath");
String tgtFileName = (String)request.getAttribute("filename");
java.io.File file = null;
if(srcFileName==null || !(file=new java.io.File(srcFileName)).exists()){
	response.setContentType("text/html");
	response.setCharacterEncoding("UTF-8");
	out.print("<html><script>alert('download file not exists!');</script></html>");
	return;
}
boolean isDelete = Boolean.parseBoolean((String)request.getAttribute("isDelete"));
if(tgtFileName==null || tgtFileName.trim().length()==0){
	tgtFileName = srcFileName.substring(srcFileName.lastIndexOf("/")+1);
}
ServletOutputStream sos = null;
java.io.FileInputStream fis = null;
try{
	tgtFileName = new String(tgtFileName.getBytes("GBK"),"ISO-8859-1");
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename="+tgtFileName);
	file = new java.io.File(srcFileName);
	sos = response.getOutputStream();
	fis = new java.io.FileInputStream(srcFileName);
	int bytesRead = 0;
	byte[] buffer = new byte[2048];
	while((bytesRead=fis.read(buffer, 0, 2048)) != -1){
		sos.write(buffer, 0, bytesRead);
	}
}catch(Exception e){
	response.setContentType("text/html");
	response.setCharacterEncoding("UTF-8");
	out.print("<html><script>alert('download failed! caused by ["+e.getMessage()+"]');</script></html>");
}finally{
	try{if(sos!=null) sos.close();}catch(Exception e){}
	try{if(fis!=null) fis.close();}catch(Exception e){}
	if(file!=null && file.exists() && isDelete) file.delete();
	response.setStatus(HttpServletResponse.SC_OK);
	response.flushBuffer();
}
%>