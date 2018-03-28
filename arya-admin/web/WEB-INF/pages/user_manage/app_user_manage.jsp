<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/10/17
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<script src="<%=contextPath%>/js/user_manage/app_user_manage.js"></script>

<style>
	.app_user_manage_container .content .table_container table td.user_use_phone_no {
		width: 160px;
		word-break: break-all; /*非正常字符 的转行*/
	}
</style>

<div class="app_user_manage_container container">

	<div class="head border-bottom">
		<div class="txt">App用户管理</div>
	</div>

	<div class="content">

		<div class="search_container">

			<span class="input-group col-xs-5 item">
				<span class="input-group-addon">关键字</span>
				<input class="form-control searchCondition"
					   placeholder="请输入姓名、电话、身份证、公司全称、曾用手机号">
			</span>

			<%--<span class="input-group col-xs-3 item">--%>
			<%--<span class="input-group-addon">公司全称</span>--%>
			<%--<input class="form-control corp_name_search"--%>
			<%--placeholder="请输入公司全称">--%>
			<%--</span>--%>

			<div class="btn_list">

				<span class="btn btn-sm btn-primary btn_search"
					  onclick="app_user_manage.btnSearchClick()">查询
				</span>

			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td>昵称</td>
					<td>姓名</td>
					<td>性别</td>
					<td>手机号</td>
					<td>曾用手机号</td>
					<td>余额</td>
					<td>身份证</td>
					<td>公司简称</td>
					<td>公司全称</td>
					<td>创建时间</td>
					<td>最近登录时间</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="item user_item" data-id="">--%>
				<%--<td class="user_nickname">昵称</td>--%>
				<%--<td class="user_name">姓名</td>--%>
				<%--<td class="user_sex">性别</td>--%>
				<%--<td class="user_phone">手机号</td>--%>
				<%--<td class="user_idCard">身份证</td>--%>
				<%--<td class="user_corp_info">公司信息</td>--%>
				<%--<td class="user_create_time">创建时间</td>--%>
				<%--<td class="user_last_login_time">最近登录时间</td>--%>
				<%--</tr>--%>
				</tbody>
			</table>
		</div>


	</div>

	<div class="pager_container">
		<%--<ul class="pagenation" style="float:right;"></ul>--%>
	</div>

</div>