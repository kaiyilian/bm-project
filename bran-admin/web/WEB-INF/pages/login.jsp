<%--
  User: Jack
  Date: 2016/5/28
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<!DOCTYPE html>
<html>
<head>
    <title>招才进宝企业管理平台</title>
    <link rel="shortcut icon" href="<%=contextPath%>/image/favicon.ico"/>
    <link rel="bookmark" href="<%=contextPath%>/image/favicon.ico" type="image/x-icon" />

    <script src="<%=contextPath%>/js/jquery/jquery-2.1.1.min.js"></script>
    <%--bootstrap样式--%>
    <link href="<%=contextPath%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/bootstrap/bootstrap.min.js"></script>

    <%--弹框--%>
    <link href="<%=contextPath%>/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/toastr/toastr.min.js"></script>
    <!-- Sweet alert 弹框 -->
    <link href="<%=contextPath%>/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/sweetalert/sweetalert.min.js"></script>
    <%--公共样式--%>
    <script src="<%=contextPath%>/js/bran/bran.js"></script>
    <%--本页面--%>
    <link href="<%=contextPath%>/css/bran/login.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/bran/login.js"></script>
</head>
<body>


<div class="middle-box text-center loginscreen  animated fadeInDown login_bg">

    <header>
        <div class="login_bg1"></div>
        <div class="hotline_container">
            <div class="line">
                <img src="<%=contextPath%>/image/phone.png">
                <span>全国服务热线</span>
            </div>
            <div class="hotline_phone">4006-710-710</div>
        </div>
    </header>

    <div class="content">

        <form class="m-t" role="form" method="post" action="">
            <h4 style="">招才进宝企业后台管理登录</h4>

            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">
                        <img src="<%=contextPath%>/image/icon_userName.png">
                    </div>
                    <input id="userName" type="text" class="form-control" onblur="CaptchaChange();"
                           placeholder="用户名" required="" name="userName" autocomplete="off">
                </div>
            </div>

            <div class="form-group">

                <div class="input-group">
                    <div class="input-group-addon">
                        <img src="<%=contextPath%>/image/icon_userPwd.png">
                    </div>
                    <input id="password" type="password" class="form-control" maxlength="32"
                           placeholder="密码" required="" name="password"
                           autocomplete="off" onfocus="this.type='password'" onkeypress="CheckIsEnter(event)">
                </div>
            </div>

            <div class="form-group captcha_container" style="">

                <div class="input-group">
                    <div class="input-group-addon">
                        <img src="<%=contextPath%>/image/icon_userPwd.png">
                    </div>
                    <input id="" type="text" class="form-control" maxlength="20"
                           placeholder="验证码">
                </div>

                <div class="imgCode" onclick="CaptchaChange()"></div>
            </div>

            <div class="form-group" style="float:left;width:100%;">
                <div class="col-xs-6 " style="">
                    <div class="checkbox pwd_remember">
                        <label>
                            <input type="checkbox">
                            <span>记住用户名</span>
                        </label>
                    </div>
                </div>
                <div class="col-xs-6 pwd_modify" style="line-height: 40px;display: none;"
                     onclick="pwdModifyModalShow()">
                    修改密码
                </div>
            </div>

            <button id="submitBtn" type="button" class="btn btn-primary block full-width m-b"
                    onclick="submitSignin()" style="width:100%;margin-bottom:10px;">
                登录
            </button>
        </form>

    </div>

    <footer>
        <div>苏州不木网络科技有限公司版权所有 苏ICP备16004093号-1 版本V2.6</div>
        <div>
            <span>本系统仅支持</span>
            <a title="点击下载Chrome浏览器"
               href="http://www.blueku.com/ChromeStandalone_50.0.2661.102_Setup.exe">Chrome浏览器</a>
        </div>
    </footer>

</div>


</body>
</html>
