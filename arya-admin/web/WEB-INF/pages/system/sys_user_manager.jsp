<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ include file="../layout/inside_page_header.jsp" %>--%>
<%@ include file="../layout/table_page_header.jsp" %>

<script src="<%=contextPath%>/js/sys/sys_user_manager.js"></script>

<div class="row animated fadeIn">
	<div class="col-sm-12">
		<div class="ibox-content">
			<div class="row">
				<div id="sys_user_list_content" class="col-sm-12">
					<table id="sys_user_list" class="display" cellspacing="0" style="width: 100%">
						<thead>
						</thead>
					</table>
				</div>
				<div id="arya_toolbar" style="width:100%;height:64px;">
					<div style="float:left;width:30%;">
						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="refreshList(sysUserManager)">
							刷新
						</button>

						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="create(sysUserManager)">
							新增
						</button>
					</div>

					<div style="float:left;width:70%;text-align:right;">

						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="edit(sysUserManager)">
							编辑
						</button>

						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="changeStatus(sysUserManager, '删除', '删除用户后无法恢复!', function(uid) {sysUserManager.submitDeleteSysUser(uid)})">
							删除
						</button>

						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="changeStatus(sysUserManager, '冻结', '冻结后该用户将无法登录系统!', function(uid) {sysUserManager.submitFreezeSysUser(uid, 2)})">
							冻结
						</button>
						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="changeStatus(sysUserManager, '解冻', '解冻后该用户可以登录系统!', function(uid) {sysUserManager.submitFreezeSysUser(uid, 1)})">
							解冻
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="dlg_sys_user" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title"><span id="dlg_sys_user_label"></span>系统用户</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form class="form-horizontal" id="sys_user_form" method="post" action="create">
						<input type="hidden" id="uid" name="uid" value=""/>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right" for="login_name">用户名：</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="login_name" name="login_name"
									   data-options="required:true" placeholder="用户名"/>
								<span class="help-block m-b-none">提示信息</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right" for="login_pwd">密码：</label>
							<div class="col-sm-8">
								<input class="form-control" type="password" id="login_pwd" name="login_pwd"
									   data-options="required:true" placeholder="密码"/>
								<span class="help-block m-b-none"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">姓名：</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="real_name" name="real_name"
									   data-options="required:true" placeholder="姓名"/>
								<span class="help-block m-b-none"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label text-right">邮箱：</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="email" name="email"
									   data-options="required:true" placeholder="邮箱"/>
								<span class="help-block m-b-none"></span>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<div id="label_err_msg" class="alert alert-danger" style="display:none;"></div>
				<button type="button" class="btn btn-default" onclick="clearForm()" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" onclick="sysUserManager.submitCreateEditForm()">保存
				</button>
			</div>
		</div>
	</div>
</div>