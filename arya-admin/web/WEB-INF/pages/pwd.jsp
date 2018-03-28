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
<%--全局的配置--%>
<script src="<%=contextPath%>/js/config.js"></script>
<!-- Arya -->
<script src="<%=contextPath%>/js/plugins/toastr/toastr.min.js"></script>
<script src="<%=contextPath%>/js/arya.js"></script>
<script src="<%=contextPath%>/js/urlGroup.js"></script>
<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
	<div>

		<h3>重新设置密码</h3>

		<div>${error}</div>

		<form class="m-t" role="form" action="/change_pwd" method="post">
			<input id="captcha" name="captcha" type="hidden" value="${captcha}">
			<div class="form-group">
				<input id="new_pwd" type="text" class="form-control" placeholder="密码" required="" name="new_pwd"
					   autocomplete="off" onfocus="this.type='password'">
			</div>
			<div class="form-group">
				<input id="new_pwd_again" type="text" class="form-control" placeholder="重复密码" required=""
					   name="new_pwd_again"
					   autocomplete="off" onfocus="this.type='password'">
			</div>
			<button id="submitBtn" type="button" class="btn btn-primary block full-width m-b"
					onclick="submitChangePwd()">
				确定
			</button>

		</form>
	</div>
</div>
<%--cookie.js--%>
<script src="<%=contextPath%>/js/jquery.cookie.js"></script>
<%--login.js--%>
<script src="<%=contextPath%>/js/login/pwd.js"></script>

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