<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/2
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/corporation/corp_user_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_user_manage.js"></script>

<div class="container corp_container corp_user_container">

    <div class="col-sm-3 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>集团公司列表</h5>
        </div>

        <div class="ztree_search_container">
            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div id="entry_group_chooser" class="ztreeContainer">
            <ul id="entry_group_tree" class="ztree"></ul>
        </div>

    </div>

    <div class="col-sm-9 corp_content">

        <div class="row">

            <div class="col-sm-12">

                <div class="ibox-title">
                    <h5>企业管理员列表</h5>
                </div>

                <div class="ibox-content">

                    <div class="table_container">
                        <table id="tb_corp_manager"></table>
                    </div>

                    <div id="arya_toolbar" class="row">

                        <div class="col-sm-12" style="margin-top:10px;">

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="corp_user_manage.addUserModalShow()">
                                新增
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="corp_user_manage.modifyUser()">
                                编辑
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="corp_user_manage.corpUserPermissionModalShow()">
                                设置权限
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="corp_user_manage.clearTryLoginTimes()">
                                重置登录
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox-title">
                    <h5>企业入职码</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="input-group m-b">
                                <span class="input-group-addon">入职码</span>
                                <input id="check_in_code" type="number" class="form-control" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div id="check_in_QRCode" class="col-md-3">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>

<%--新增用户对话框--%>
<div class="modal inmodal" id="corp_user_info_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 id="corp_admin_add_edit_title" class="modal-title">添加管理员</h4>
            </div>
            <div class="modal-body">
                <div class="form-group row">
                    <label class="col-sm-2 control-label">账号</label>

                    <div class="col-sm-4">
                        <%--<input type="text" class="form-control m-b" id="entry_corp_id" style="display: none"/>--%>
                        <input type="text" class="form-control m-b" id="entry_corp_account"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 control-label">密码</label>

                    <div class="col-sm-4">
                        <input type="password" class="form-control m-b" id="entry_corp_password"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 control-label">确认密码</label>

                    <div class="col-sm-4">
                        <input type="password" class="form-control m-b" id="entry_corp_password_confirm"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 control-label">邮箱</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control m-b" id="entry_corp_email"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 control-label">昵称</label>

                    <div class="col-sm-4">
                        <input type="text" class="form-control m-b" id="entry_corp_nick_name"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="corp_user_manage.userInfoSave()">提交
                </button>
            </div>
        </div>
    </div>
</div>

<%--权限--%>
<div class="modal fade corp_user_permission_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">用户权限</h4>
            </div>
            <div class="modal-body" style="height:500px;overflow: auto;">

                <ul id="corp_user_permission" class="ztree"></ul>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary"
                        onclick="corp_user_manage.corpUserPermissionSave()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


