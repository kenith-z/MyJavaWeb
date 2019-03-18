<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="Head.jsp" %>
 
 <%
 //pageContext是页面对象，作用是内置对象中作用范围最小的，只能存在当前页面
 pageContext.setAttribute("PAGE_DESC", "用户登录");
  %>
  

 
<body>
<body>
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3>${PAGE_DESC }</h3>
						<form  name="theform" method="post" action="${PATH}/Login.act">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="userId" class="text" value="1604220743" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="userPd" class="text" value="8888" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="input_outer">
							<span class="us_code"><img id="randomImg" src="${PATH}/views/VerifyImg.jsp" style="cursor:pointer; " onclick="freshImg()"/></span>
							<input name="randomCode" class="text"  id="code" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="text" placeholder="请输入验证码">
							</div>
							<div class="mb2">
							<input class="act-but submit" type="button"    name="bt1" value="                          登录                          " onclick="this.form.submit();" style="color: #FFFFFF"/>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		<script src="${PATH}/js/TweenLite.min.js"></script>
		<script src="${PATH}/js/EasePack.min.js"></script>
		<script src="${PATH}/js/rAF.js"></script>
		<script src="${PATH}/js/demo-1.js"></script>
		
	
  <script type="text/javascript">
function freshImg(){
	var obj = document.getElementById("randomImg");
	obj.style.display = "none";
	obj.src = "${PATH}/views/VerifyImg.jsp?"+Math.random();
	setTimeout(function(){
		obj.style.display = "";
		theform.verifyCode.focus();
	},200);
}
</script>
<%@include file="Foot.jsp" %>
