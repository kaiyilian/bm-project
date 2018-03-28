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
	<title>订单批量删除</title>
	<script src="<%=contextPath%>/js/jquery-2.1.1.min.js"></script>
</HEAD>
<body>


<script>

	var DEL_SOIN_ORDER_URL = '<%=contextPath%>/admin/del_soin_orders';

	function delOrders() {
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

		console.log('POST to : ' + DEL_SOIN_ORDER_URL);

		$.ajax({
			url: DEL_SOIN_ORDER_URL,
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
<h3>从 ${time_cutoff} 开始共有 ${orders_count} 条订单，查询耗时${time_consume}秒</h3>


<table border="1">
	<thead>
	<td>ID</td>
	<td>订单号</td>
	<td>参保人</td>
	<td>身份证号码</td>
	<td>地区名称</td>
	<td>服务年月</td>
	<td>销售员</td>
	<td>增减员</td>
	<td>计算类型</td>
	<td>缴纳记录数量</td>
	<td>操作人</td>
	</thead>
	<c:forEach items="${del_soin_orders}" var="order">
		<tr>
			<td>${order.orderId}</td>
			<td>${order.orderNo}</td>
			<td>${order.realName}</td>
			<td>${order.idcardName}</td>
			<td>${order.districtName}</td>
			<td>${order.serviceYearMonth}</td>
			<td>${order.salesMan}</td>
			<td>${order.increaseOrDecrease}</td>
			<td>${order.calculateType}</td>
			<td>${order.soinCount}</td>
			<td>${order.operator}</td>
		</tr>
	</c:forEach>
</table>

<div>
	<input type="text" id="days" readonly="readonly" readonly value="${days}"/>
	<div onclick="delOrders()" >删除查询到的结果</div>
</div>

</body>

</html>