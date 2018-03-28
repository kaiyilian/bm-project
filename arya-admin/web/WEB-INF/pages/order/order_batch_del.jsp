<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/1/17
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<script src="<%=contextPath%>/js/order/order_batch_del.js"></script>

<div class="order_batch_del_container container">

	<div class="head border-bottom">
		<div class="txt">订单批量删除</div>
	</div>

	<div class="content">

		<div class="search_container">

			<div class="input-group col-xs-4 item">
				<span class="input-group-addon">用户类型：</span>
				<select data-placeholder="请选择用户类型" multiple
						class="chosen-select user_type_list">

				</select>
			</div>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">增减员：</span>
				<select class="form-control increase_or_decrease">
					<option value="1">增员</option>
					<%--<option value="2">减员</option>--%>
				</select>
			</div>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字：</span>
				<input class="form-control key_word" placeholder="姓名/身份证号">
			</div>

			<div class="col-xs-2 item btn_list">
				<div class="btn btn-sm btn-primary btn_search"
					 onclick="order_batch_del.btnSearchClick()">
					查询
				</div>
			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item" onclick="order_batch_del.chooseCurAll()">
						<img src="img/icon_Unchecked.png"
							 data-html="true"
							 data-toggle="tooltip"
							 data-placement="right"
							 title="<p style='width:80px;'>选择当前页所有选项</p>"/>
					</td>
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
					<td>供应商</td>
					<td>增减员</td>
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

		<div class="choose_item" onclick="order_batch_del.chooseAll()"
			 data-html="true"
			 data-toggle="tooltip"
			 data-placement="top"
			 title="<p style='width:80px;'>选择查询条件下的所有内容</p>">
			<img src="img/icon_Unchecked.png"/>
			<span>全选</span>
		</div>

		<div class="btn_list">

			<div class="btn btn-sm btn-default btn_del" onclick="order_batch_del.orderBatchDel()">
				批量删除
			</div>

			<div class="btn btn-sm btn-default btn_recover">
				恢复
			</div>

		</div>

	</div>

	<div class="pager_container">
		<%--<ul class="pagenation" style="float:right;"></ul>--%>
	</div>

</div>