<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<title>推送测试</title>
	<script src="js/jquery-2.1.1.min.js"></script>
</HEAD>
<body>


<script>

	var TEST_PUSH_URL = 'admin/test_push';

	function testPush() {
		var userId = $('#user_id').val();
		var clientType = $('#client_type').val();
		var jumpType = $('#jump_type').val();
		$.ajaxSetup({
			accept: 'application/json',
			cache: false,
			contentType: 'application/json;charset=UTF-8'
		});

		var params = {
			user_id: userId,
			client_type: clientType,
			jumpType: jumpType
		};

		$.ajax({
			url: TEST_PUSH_URL,
			method: 'POST',
			data: JSON.stringify(params),
			success: function (data, status, jqXHR) {
				if (data["code"] == PERMISSION_DENIED_CODE) {
					toastr.error("没有访问权限");
				}
				else if (data["code"] == SESSION_TIMEOUT_CODE) {
					location.href = "login";
				}
				else {

				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrow) {

			}
		});
	}
</script>

<table>
	<thead>
	<td>ID</td>
	<td>手机</td>
	<td>昵称</td>
	</thead>
	<c:forEach items="${all_users}" var="user">
		<tr>
			<td>${user.id}</td>
			<td>${user.phoneNo}</td>
			<td>${user.nickName}</td>
		</tr>
	</c:forEach>
</table>

<div>
	<form id="frm_push" method="post" action="do_push">
		<table cellpadding="5">
			<tr>
				<td>用户ID</td>
				<td><input class="easyui-textbox" type="text" id="user_id" name="user_id"
						   data-options="required:true"/></td>
			</tr>
			<tr>
				<td>标题</td>
				<td><input class="easyui-textbox" type="text" id="msg_title" name="msg_title"
						   data-options="required:true" value="招才进宝提醒您"/></td>
			</tr>
			<tr>
				<td>内容</td>
				<td><input class="easyui-textbox" type="text" id="msg_body" name="msg_body"
						   data-options="required:true" value="您的订单xxx已完成"/></td>
			</tr>
			<tr>
				<td>终端类型:</td>
				<td>
					<select id="client_type" name="client_type">
						<option value="1">Android</option>
						<option value="2">iOS</option>
						<option value="4">Windows Phone</option>
						<option value="8">微信</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>跳转页面:</td>
				<td>
					<select id="jump_type" name="jump_type">
						<option value="20">社保</option>
						<option value="30">个人代缴</option>
						<option value="31">订单管理</option>
						<option value="32">参保人</option>
						<option value="33">企业代缴</option>
						<option value="40">薪资查询</option>
						<option value="50">招聘广告</option>
						<option value="60">培训课程</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="推送消息"/>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
<script>
	console.log("OK ARYA");
</script>
</html>