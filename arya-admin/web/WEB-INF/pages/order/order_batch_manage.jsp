<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/8/2
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<script src="<%=contextPath%>/js/order/order_batch_manage.js"></script>
<link href="<%=contextPath%>/css/order/order_batch_manage.css" rel="stylesheet"/>

<div class="order_batch_manage_container container">

	<div class="head border-bottom">
		<div class="txt">订单批次删除</div>
	</div>

	<div class="content">

		<div class="search_container">

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">操作人：</span>
				<select class="form-control salesman_list">
					<%--<option value="">请选择业务员</option>--%>
					<%--<option value="1">小张</option>--%>
					<%--<option value="2">小李</option>--%>
					<%--<option value="3">小王</option>--%>
				</select>
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">批次列表：</span>
				<select class="form-control batch_list">
					<%--<option value="">请选择批次</option>--%>
					<%--<option value="1">a001</option>--%>
					<%--<option value="2">a004</option>--%>
					<%--<option value="3">a002</option>--%>
				</select>
			</span>

			<div class="btn btn-sm btn-primary btn_search"
				 onclick="order_batch_manage.btnSearchClick()">查询
			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td>缴纳主体</td>
					<td>姓名</td>
					<td>身份证</td>
					<td>参保地区</td>
					<td>参保类型</td>
					<td>服务月份</td>
					<td>缴纳月份</td>
					<td>总计(收账)</td>
					<td>总计(出账)</td>
					<td>业务员</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="item order_item" data-id="订单id">--%>
					<%--<td class="order_subject">缴纳主体</td>--%>
					<%--<td class="order_user_name">姓名</td>--%>
					<%--<td class="order_user_idCard">身份证</td>--%>
					<%--<td class="order_soin_district">参保地区</td>--%>
					<%--<td class="order_soin_type">参保类型</td>--%>
					<%--<td class="order_service_month">服务月份</td>--%>
					<%--<td class="order_pay_month">缴纳月份</td>--%>
					<%--<td class="order_collection_total">总计(收账)</td>--%>
					<%--<td class="order_charge_total">总计(出账)</td>--%>
					<%--<td class="order_salesman">业务员</td>--%>
				<%--</tr>--%>

				</tbody>
			</table>
		</div>


	</div>

	<div class="foot">


		<div class="btn_list">
			<div class="btn btn-sm btn-default btn_del">
				批次删除
			</div>
		</div>
	</div>

	<div class="pager_container">
		<%--<ul class="pagenation" style="float:right;"></ul>--%>
	</div>

</div>