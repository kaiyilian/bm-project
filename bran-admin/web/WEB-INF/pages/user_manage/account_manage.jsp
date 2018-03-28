<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/11
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/user_manage/account_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/user_manage/account_manage.js"></script>

<div class="container account_manage_container">

	<div class="head border-bottom">
		<i class="icon icon-jurisdiction"></i>
		<div class="txt">账号管理</div>
	</div>

	<div class="content">

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item"></td>
					<td>账号名称</td>
					<td>创建时间</td>
					<td>最后登录时间</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="account_item" data-userId="1" data-version="2" >--%>
				<%--<td class="isTrue" onclick="account_manage.ChooseItem(this)">--%>
				<%--<img src="image/UnChoose.png"/>--%>
				<%--</td>--%>
				<%--<td class="account_no">--%>
				<%--<div class="">fas</div>--%>
				<%--</td>--%>
				<%--<td class="create_time">时间</td>--%>
				<%--<td class="last_login_time">2013</td>--%>
				<%--<td class="btn_operate">--%>
				<%--<span class="btn btn-sm btn-success"--%>
				<%--onclick="account_manage.AccountModifyModalShow(this)">修改</span>--%>
				<%--</td>--%>
				<%--</tr>--%>
				</tbody>
			</table>
		</div>

		<div class="pager_container">
			<%--<ul class="pagenation" style="float:right;"></ul>--%>
		</div>

	</div>

	<div class="foot">
		<div class="choose_container" onclick="account_manage.chooseAll()">
			<img src="image/UnChoose.png"/>
			<span>全选</span>
		</div>

		<div class="btn btn-sm btn-default btn_del" onclick="account_manage.accountDelMore()">删除</div>
		<div class="btn btn-sm btn-success" onclick="account_manage.accountAddModalShow()">新增用户</div>
	</div>


</div>

<div class="modal fade user_account_add_modal" role="dialog"
	 style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增用户</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="txt col-xs-3">账号：</div>
					<div class="txtInfo col-xs-9 account_no_add">
						<input type="text" class="form-control" placeholder="请输入账号" maxlength="32">
					</div>
				</div>

				<div class="row">
					<div class="txt col-xs-3">密码：</div>
					<div class="txtInfo col-xs-9 account_pwd_add">
						<input type="password" class="form-control" placeholder="请输入密码" maxlength="32">
					</div>
					<div class="prompt col-xs-12">
						<i class="icon "></i>
						密码为8~32位的 英文和数字混合
					</div>
				</div>

				<div class="row">
					<div class="txt col-xs-3">确认密码：</div>
					<div class="txtInfo col-xs-9 account_pwd_sure">
						<input type="password" class="form-control" placeholder="请输入确认密码" maxlength="32">
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<div class="btn btn-orange btn_save" onclick="account_manage.accountAdd()">
					确认
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade user_account_modify_modal" role="dialog"
	 style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">用户密码更改</h4>
			</div>
			<div class="modal-body">

				<%--<div class="row" style="display: none;">--%>
				<%--<div class="txt col-xs-3">旧密码：</div>--%>
				<%--<div class="txtInfo col-xs-9 account_old_pwd">--%>
				<%--<input type="password" class="form-control" placeholder="请输入旧密码" maxlength="32">--%>
				<%--</div>--%>
				<%--</div>--%>

				<div class="row">
					<div class="txt col-xs-3">新密码：</div>
                    <div class="txtInfo col-xs-9 account_new_pwd">
                        <input type="password" class="form-control" placeholder="请输入新密码" maxlength="32">
                    </div>
					<div class="prompt col-xs-12">
						<i class="icon "></i>
						密码为8~32位的 英文和数字混合
					</div>
				</div>

				<div class="row">
					<div class="txt col-xs-3">确认密码：</div>
                    <div class="txtInfo col-xs-9 account_new_pwd_sure">
                        <input type="password" class="form-control" placeholder="请输入确认密码" maxlength="32">
                    </div>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<div class="btn btn-orange btn_save" onclick="account_manage.AccountModifySure()">
					确认
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>


