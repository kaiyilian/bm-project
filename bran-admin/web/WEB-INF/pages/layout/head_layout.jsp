<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/11
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/layout/head_layout.css" rel="stylesheet">

<div class="row border-bottom" id="head_layout">

    <nav class="navbar navbar-static-top" role="navigation"
         style="margin-bottom: 0;display:none;">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                <i class="fa fa-bars"></i>
            </a>
        </div>

    </nav>

    <div class="logo" style="" onclick="indexTabOnclick();">
        <img src="image/logo.png"/>
    </div>
    <div class="txt">招才进宝企业管理后台</div>

    <div class="txt1" style="position: absolute;top:0;width:100%;left:0;text-align: center;line-height:75px;">
        吐槽请联系 赵先生：13915581423
    </div>

    <div class="user_info">
        <%--<img src="image/icon_user_head.png"/>--%>
        <span class="icon_message"
              onclick="getInsidePageDiv(urlGroup.notification.index,'notification_center', '消息中心')"></span>
        <span class="icon_user_head"></span>
        <span class="user_name"></span>
        <span class="txt">，您好！</span>

        <span class="pwd_modify_container" onclick="pwdModifyModalShow()">
            <img src="image/icon_pwd_modify.png"/>
            <span>修改密码</span>
        </span>

        <span class="login_out_container" onclick="LoginOut()">
            <img src="image/icon_login_out.png"/>
            <span>退出</span>
        </span>
    </div>
</div>

<div class="modal fade pwd_modify_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body" style="width:100%;">

                <div class="row form-group">
                    <span class="txt col-xs-2">旧密码：</span>
                    <span class="txtInfo col-xs-10 old_pwd">
                        <input type="password" class="form-control" placeholder="请输入旧密码" maxlength="32">
                    </span>
                </div>

                <div class="row form-group">
                    <span class="txt col-xs-2">新密码：</span>
                    <span class="txtInfo col-xs-10 new_pwd">
                        <input type="password" class="form-control" placeholder="请输入新密码" maxlength="32">
                    </span>
                </div>

                <div class="row form-group">
                    <span class="txt col-xs-2">确认密码：</span>
                    <span class="txtInfo col-xs-10 new_pwd_sure">
                        <input type="password" class="form-control"
                               placeholder="请确认密码" maxlength="32">
                    </span>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                <button type="button" class="btn btn-success" onclick="pwdModifySure();">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
