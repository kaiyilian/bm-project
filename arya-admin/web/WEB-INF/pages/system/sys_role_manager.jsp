<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ include file="../layout/table_page_header.jsp" %>

<script src="<%=contextPath%>/js/sys/sys_role_manager.js"></script>

<div class="row animated fadeIn">
    <div class="col-sm-12">
        <div class="ibox-content">
            <div class="row">
                <div id="sys_role_list_content" class="col-sm-12">

                    <table id="sys_role_list" class="display" cellspacing="0" style="width: 100%"></table>

                    <div id="arya_toolbar row" style="width:100%;height:64px;">

                        <div class="col-xs-4">
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="refreshList(roleManager)">
                                刷新
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="create(roleManager)">
                                新增
                            </button>

                        </div>

                        <div class="col-xs-8" style="text-align:right;">

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="edit(roleManager)">
                                编辑
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="changeStatus(roleManager, '删除', '删除角色后无法恢复!', function(rid) {roleManager.submitDeleteSysRole(rid)})">
                                删除
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="assignSysUserRole(roleManager)">
                                用户角色
                            </button>

                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    onclick="sysRolePermission(roleManager)">
                                角色权限
                            </button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<%--角色新增编辑对话框--%>
<div class="modal fade" id="dlg_sys_role" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><span id="dlg_sys_role_label"></span>系统角色</h4>
            </div>
            <div class="modal-body">
                <div class="easyui-dialog" style="width: 360px; height: 180px; padding: 10px 20px;">
                    <form id="sys_role_form" method="post" action="create">
                        <input type="hidden" id="rid" name="rid" value=""/>
                        <table cellpadding="5">
                            <tr>
                                <td>角色名称:</td>
                                <td><input class="easyui-textbox" type="text" id="role_name" name="role_name"
                                           data-options="required:true"/></td>
                            </tr>
                            <tr>
                                <td>角色描述:</td>
                                <td><input class="easyui-textbox" type="text" id="role_desc" name="role_desc"
                                           data-options="required:true"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <div id="label_err_msg" class="alert alert-danger" style="display:none;">Hello Error!</div>
                <button type="button" class="btn btn-default" onclick="clearForm()" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="roleManager.submitCreateEditForm()">保存
                </button>
            </div>
        </div>
    </div>
</div>


<%--用户角色分配对话框--%>
<div class="modal fade" id="dlg_sys_role_user" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" style="width:950px;height:540px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">
                    给
                    <span id="dlg_sys_role_user_label"></span>
                    角色添加用户
                </h4>
            </div>
            <div class="modal-body">

                <div class="easyui-dialog row" style="height:260px;">

                    <div class="col-xs-4">
                        <div>已添加用户</div>
                        <div style="height:240px;overflow-y:scroll;overflow-x:hidden;">
                            <table id="sys_role_user_list" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>登录名</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>

                    <div class="col-xs-2" style="float:left;height:auto;padding: 60px 20px;text-align: center;">
                        <div>
                            <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                    onclick="addSysUserToRole()">
                                &leftarrow;添加
                            </button>
                        </div>
                        <div style="margin: 20px auto;">
                            <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                    onclick="removeSysUserToRole()">
                                移除&rightarrow;
                            </button>
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div>所有可用的用户</div>
                        <div style="height:240px;overflow-y:scroll;overflow-x:hidden;">

                            <table id="sys_role_user_list_others" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>登录名</th>
                                </tr>
                                </thead>
                            </table>

                        </div>
                    </div>


                </div>
            </div>
            <div class="modal-footer">
                <div id="label_err_msg2" class="alert alert-danger" style="display: none;"></div>
                <button type="button" class="btn btn-default" onclick="" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<%--用户权限分配对话框--%>
<div class="modal fade" id="dlg_sys_role_permission" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" style="width:900px;height:400px;" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">给<span id="dlg_sys_role_permission_label"></span>角色添加权限</h4>
            </div>
            <div class="modal-body">
                <div class="easyui-dialog row" style="height:260px;">

                    <div class="col-xs-4">
                        <div>已添加权限</div>
                        <div style="height:240px;overflow-y:scroll;overflow-x:hidden;">
                            <table id="sys_role_permission_list" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>代码</th>
                                    <th>描述</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>

                    <div class="col-xs-2" style="height:auto;padding: 60px 20px;text-align: center;">
                        <div>
                            <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                    onclick="addSysPermissionToRole()">
                                &leftarrow;添加
                            </button>
                        </div>
                        <div style="margin: 20px auto;">
                            <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                    onclick="removeSysPermissionToRole()">
                                移除&rightarrow;
                            </button>
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div>所有可用的权限</div>
                        <div style="height:240px;overflow-y:scroll;overflow-x:hidden;">

                            <table id="sys_role_permission_list_others" class="display" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>代码</th>
                                    <th>描述</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer" style="width:100%;">
                <div id="label_err_msg_show" class="alert alert-danger"
                     style="float:left;width:80%;display:none;"></div>
                <div style="float:left;width:20%;text-align:right;">
                    <button type="button" class="btn btn-default" onclick="" data-dismiss="modal">关闭
                    </button>
                </div>

            </div>
        </div>
    </div>
</div>

