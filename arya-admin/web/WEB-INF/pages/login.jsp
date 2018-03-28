<%--
  User: CuiMengxin
  Date: 2015/11/2
  Time: 14:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ include file="layout/head_layout.jsp" %>--%>

<% String contextPath = request.getContextPath().toString(); %>
<html>
<link href="<%=contextPath%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=contextPath%>/css/style.min.css?v=3.0.0" rel="stylesheet">
<link href="<%=contextPath%>/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- 全局js -->
<script src="<%=contextPath%>/js/jquery-2.1.1.min.js"></script>
<script src="<%=contextPath%>/js/bootstrap.min.js"></script>
<!-- Sweet alert -->
<link href="<%=contextPath%>/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/sweetalert/sweetalert.min.js"></script>
<%--全局的配置--%>
<script src="<%=contextPath%>/js/config.js"></script>
<!-- Arya -->
<script src="<%=contextPath%>/js/arya.js"></script>
<script src="<%=contextPath%>/js/plugins/toastr/toastr.min.js"></script>
<script src="<%=contextPath%>/js/urlGroup.js"></script>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
	<div>
		<div>
			<%--<h1 class="logo-name">bumu</h1>--%>
		</div>
		<h3>招才进宝管理平台</h3>

		<form class="m-t" role="form" action="/signin" method="post">
			<div class="form-group">
				<input id="userName" type="text" class="form-control" placeholder="用户名" required="" name="userName"
					   autocomplete="off">
			</div>
			<div class="form-group">
				<input id="password" type="text" class="form-control" placeholder="密码" required="" name="password"
					   autocomplete="off" onfocus="this.type='password'">
			</div>

			<div class="form-group captcha_container" style="display: none;position: relative;">

				<div class="input-group" style="width:100%;">
					<input id="" type="text" class="form-control" maxlength="20" placeholder="验证码">
				</div>

				<div class="imgCode" onclick="changeCaptcha()" style="position: absolute;right:0;"></div>
			</div>

			<button id="submitBtn" type="button" class="btn btn-primary block full-width m-b"
					onclick="submitSignin()">
				登录
			</button>

			<%--<p class="text-muted text-left">--%>
			<%--<input id="remeber_pwd" type="checkbox" class="i-checks">记住账号密码</label>--%>
			<%--</p>--%>
		</form>
	</div>
	<div>
		<a href="<%=contextPath%>/forget_pwd.html">忘记密码？点我</a>
	</div>
</div>
<%--cookie.js--%>
<script src="<%=contextPath%>/js/jquery.cookie.js"></script>
<%--login.js--%>
<script src="<%=contextPath%>/js/login/login.js"></script>

<style>
	.imgCode {
		position: absolute;
		right: 0;
		top: 0;
		z-index: 2;
		height: 34px;
	}

	.imgCode img {
		width: 100%;
		height: 100%;
	}
</style>

</body>

</html>