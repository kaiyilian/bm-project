<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/20
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/setting/setting.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/setting/setting.js"></script>

<div class="container general_container">

	<div class="col-xs-2 center" style="height:100px;font-size: 16px;font-weight: bold;">
		入职消息提醒：
	</div>

	<div class="col-xs-5">

		<div class="entry_prompt_info">
			<%--xxx提醒您，距离xxxx年xx月xx日入职还有x天。--%>
		</div>

		<div class="row">

			<div class="choose_item" onclick="general.chooseItem(this)">
				<img src="image/UnChoose.png"/>
			</div>

			<div class="txt">设置入职前</div>

			<div class="day_container time_container">
				<select>
					<option>1</option>
				</select>
			</div>

			<div class="txt">天</div>

			<div class="hour_container time_container">
				<select>
					<option>1</option>
				</select>
			</div>

			<div class="txt">时，入职消息提醒</div>

		</div>

	</div>

	<div class="col-xs-5">

		<div class="btn btn-orange btn_submit" onclick="general.entryInfoSubmit()">
			提交
		</div>

	</div>

</div>

<div class="container setting_container">

	<div class="col-xs-2 param_name_container">

		<div class="item active" onclick="setting_workLine.init();">工段</div>
		<div class="item" onclick="setting_workShift.init();">班组</div>
		<div class="item" onclick="setting_position.init();">职位</div>
		<div class="item" onclick="setting_job_num.init();">工号前缀</div>
		<div class="item" onclick="setting_leave_reason.init();">离职原因</div>
		<div class="item" onclick="setting_roster_custom.init();">花名册自定义</div>

	</div>

	<div class="col-xs-5 param_add_container center">

		<div class="add_block">

			<div class="row">

				<div class="col-xs-9 name_add">
					<input type="text" class="form-control" placeholder="请输入职位名称"
						   maxlength="32">
				</div>

				<div class="col-xs-3 btn-orange btn_add" onclick="">
					<i class="glyphicon glyphicon-plus"></i>
					增加
				</div>

			</div>

			<div class="row">

				<div class="prompt">

					<div class="row">此字段设置，要与排班字段相匹配</div>

				</div>

			</div>

		</div>

	</div>

	<div class="col-xs-5 param_content_container">

		<div class="list_container">

			<div class="item active">
				<div class="name">班组1</div>
				<i class="glyphicon glyphicon-pencil btn_modify"></i>
				<i class="glyphicon glyphicon-minus btn_del"></i>
			</div>

			<div class="item">
				<div class="name">班组1</div>
			</div>

			<div class="item active">
				<div class="name">班组1</div>
				<i class="glyphicon glyphicon-ok btn_save"></i>
				<i class="glyphicon glyphicon-remove btn_cancel"></i>
			</div>

			<div class="item">
				<div class="name">班组1</div>
			</div>

			<div class="item">
				<div class="name">班组1</div>
			</div>


		</div>

	</div>

</div>
