<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/21
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<%--<link href="<%=contextPath%>/css/bran/employee/employee_list.css" rel="stylesheet">--%>
<script src="<%=contextPath%>/js/bran/setting/log_operation.js"></script>

<div class="container log_operation_container">

	<div class="head border-bottom">
		<i class="icon icon-setting"></i>
		<div class="txt">操作日志</div>
	</div>

	<div class="content">

		<div class="search_container">

			<div class="row">

				<div class="input-group col-xs-3 item operation_module_container">
					<span class="input-group-addon">操作模块：</span>
					<select class="form-control" onchange="log_operation.getOpTypeList()">
						<%--<option>待入职</option>--%>
						<%--<option>删</option>--%>
						<%--<option>改</option>--%>
						<%--<option>查</option>--%>
					</select>
				</div>

				<div class="input-group col-xs-3 item operation_type_container">
					<span class="input-group-addon">操作类型:</span>
					<select class="form-control" onchange="log_operation.opTypeListChange()">
						<%--<option>增</option>--%>
						<%--<option>删</option>--%>
						<%--<option>改</option>--%>
						<%--<option>查</option>--%>
					</select>
				</div>

			</div>

			<div class="row">

				<div class="input-group col-xs-6 item">
					<span class="input-group-addon">开始时间:</span>
					<input class="form-control layer-date beginTime" id="log_beginTime"
						   placeholder="YYYY-MM-DD">
					<span class="input-group-addon">结束时间:</span>
					<input class="form-control layer-date endTime" id="log_endTime"
						   placeholder="YYYY-MM-DD">
				</div>

				<div class="col-xs-3 btn_list">

					<div class="btn btn-sm btn-orange" onclick="log_operation.btnSearchClick();">
						查询
					</div>

				</div>

			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead style="background-color: #F3F3F4">
				<tr>
					<td style="width:42px;">序号</td>
					<td>操作人</td>
					<td>操作内容</td>
					<td>操作时间</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="log_item">--%>
				<%--<td class="log_time"></td>--%>
				<%--<td class="log_content"></td>--%>
				<%--<td class="log_operator"></td>--%>
				<%--</tr>--%>
				</tbody>
			</table>
		</div>

		<div class="pager_container">
			<ul class="pagenation" style="float:right;"></ul>
		</div>

	</div>


</div>

<style type="text/css">
	.log_operation_container .content table tbody .log_item .log_operator {
		width: 120px;
	}

	.log_operation_container .content table tbody .log_item .log_time {
		width: 200px;
	}

	.log_operation_container .content table tbody .log_item .log_content {
		text-align: left;
		padding-left: 10px;
	}
</style>