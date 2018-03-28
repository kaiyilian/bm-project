<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String contextPath = request.getContextPath().toString(); %>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<title>社保类型批量删除</title>
	<script src="<%=contextPath%>/js/jquery-2.1.1.min.js"></script>
</HEAD>
<body>


<script>

	var DEL_SOIN_TYPES_URL = '<%=contextPath%>/admin/del_soin_types';

	function delTypes() {
		var days = $('#days').val();
		console.log('提交删除指令: ' + days);
		$.ajaxSetup({
			accept: 'application/json',
			cache: false,
			contentType: 'application/json;charset=UTF-8'
		});

		var params = {
			days: days
		};

		console.log('POST to : ' + DEL_SOIN_TYPES_URL);

		$.ajax({
			url: DEL_SOIN_TYPES_URL,
			method: 'POST',
			data: JSON.stringify(params),
			success: function (data, status, jqXHR) {
				console.log(status);
				if (data["code"] == PERMISSION_DENIED_CODE) {
					toastr.error("没有访问权限");
				}
				else if (data["code"] == SESSION_TIMEOUT_CODE) {
					location.href = "login";
				}
				else if (data["code"] == 1000) {
					console.log(data);
					toastr.info("成功");
				}
				else {
					toastr.error(data["msg"]);
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrow) {
				console.log(textStatus);
			}
		});
	}
</script>
<h3>${err_msg}</h3>
<h3>共有 ${count} 条记录</h3>

<c:forEach items="${soin_types}" var="district">
	<br/>
	<div>${district.districtName}</div>
	<table border="1">
		<thead>
		<td>类型名称</td>
		<td>是否禁用</td>
		<td>创建时间</td>
		</thead>
		<c:forEach items="${district.soinTypeEntities}" var="type">
			<tr>
				<td>${type.typeName}</td>
				<td>${type.disable}</td>
				<td>${type.createTime}</td>
			</tr>
		</c:forEach>
	</table>
</c:forEach>

<div>
	<input type="text" id="days" readonly="readonly" readonly value="${days}"/>
	<div onclick="delTypes()">删除查询到的结果</div>
</div>

</body>

</html>