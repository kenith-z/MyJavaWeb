
<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../Head.jsp" %>
 

 
<body>
<div style="font-size:24px;font-family:宋体;">MVC测试-入口</div>
<%@include file="Menu.jsp" %>

<form name="theform" action="${PATH}/Hello.act" method="post">
	<span>名称：</span>
    <input type="text" name="word" value="软件狗" class="text0" size="4" maxlength="16"/>
	<span>城市：</span>
    <input type="text" name="city" value="广州" class="text0" size="4" maxlength="16"/>
	<input type="button" name="bt1" value="执行" onclick="this.form.submit();" />
</form>
</body>

<%@include file="../Foot.jsp" %>
