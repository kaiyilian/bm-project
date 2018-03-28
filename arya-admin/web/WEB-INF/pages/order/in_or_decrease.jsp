<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017/2/22
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<%--月份选择插件--%>
<link href="<%=contextPath%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<%--参保地区 树形结构--%>
<script src="<%=contextPath%>/js/order/areaTree.js"></script>
<%--本页面--%>
<link href="<%=contextPath%>/css/order/in_or_decrease.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/order/in_or_decrease.js"></script>

<div class="in_or_decrease_manage_container container">

	<div class="head border-bottom">
		<div class="txt">增减员导出</div>
	</div>

	<div class="content">

		<div class="search_container">

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">用户类型：</span>
				<select data-placeholder="请选择用户类型" multiple
						class="chosen-select user_type_list">
					<%--<option>1</option>--%>
				</select>
			</div>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">参保地区：</span>
				<input class="form-control soin_area" type="text" readonly value="请选择地区"
					   data-district_id="" onclick="in_or_decrease_manage.SoinAreaModalShows()"/>
			</div>


			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">服务开始年月：</span>
				<span class="form-control order_month" data-time="" readonly>请选择开始月份</span>
				<span class="add-on"><i class="icon-remove"></i></span>
			</div>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">服务结束年月：</span>
				<span class="form-control order_end_month" data-time="" readonly>请选择结束月份</span>
				<span class="add-on"><i class="icon-remove"></i></span>
			</div>
			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">供应商：</span>
				<select data-placeholder="请选择供应商" multiple
						class="chosen-select supplier_list">
					<%--<option>1</option>--%>
				</select>
			</div>

			<%--<div class="input-group col-xs-3 item">--%>
				<%--<span class="input-group-addon">缴纳状态：</span>--%>
				<%--<select data-placeholder="请选择缴纳状态" multiple--%>
						<%--class="chosen-select order_pay_status">--%>
					<%--&lt;%&ndash;<option>1</option>&ndash;%&gt;--%>
				<%--</select>--%>
			<%--</div>--%>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">增减员状态：</span>
				<select data-placeholder="请选择增减员状态" multiple
						class="chosen-select increase_or_decrease_status">
					<%--<option>1</option>--%>
				</select>
			</div>

			<div class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字：</span>
				<input type="text" class="form-control search_condition"
					   placeholder="姓名/身份证号/手机号">
			</div>

			<div class="btn_list">

				<span class="btn btn-sm btn-primary btn_search"
					  onclick="in_or_decrease_manage.btnSearchClick()">查询
				</span>

				<span class="btn btn-sm btn-primary btn_reset"
					  onclick="in_or_decrease_manage.initSearchCondition()">重置
				</span>

			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<%--<td class="choose_item">--%>
					<%--<img src="img/icon_Unchecked.png"--%>
					<%--data-html="true"--%>
					<%--data-toggle="tooltip"--%>
					<%--data-placement="right"--%>
					<%--title="<p style='width:80px;'>选择当前页所有选项</p>"/>--%>
					<%--</td>--%>
					<td>缴纳主体</td>
					<td>姓名</td>
					<td>身份证</td>
					<td>
						参保地区
						<div class="sort_list" data-sortName="districtId">
							<img src="img/icon_sort.png" class="sorting_asc" data-sort="1">
							<img src="img/icon_sort.png" class="sorting_desc" data-sort="2">
						</div>
					</td>
					<td>参保类型</td>
					<td>
						服务月份
						<div class="sort_list" data-sortName="serviceYearMonth">
							<img src="img/icon_sort.png" class="sorting_asc" data-sort="1">
							<img src="img/icon_sort.png" class="sorting_desc" data-sort="2">
						</div>
					</td>
					<td>缴纳月份</td>
					<td>补缴开始月份</td>
					<td>补缴结束月份</td>
					<%--<td>社保编号</td>--%>
					<%--<td>社保基数</td>--%>
					<%--<td>公积金编号</td>--%>
					<%--<td>公积金基数</td>--%>
					<%--<td>公积金比例(%)</td>--%>
					<%--<td>户口性质</td>--%>
					<%--<td>户籍地址</td>--%>
					<%--<td>服务费(收账)</td>--%>
					<%--<td>服务费(出账)</td>--%>
					<%--<td>企业小计</td>--%>
					<%--<td>个人小计</td>--%>
					<%--<td>其他费用</td>--%>
					<td>增减员</td>
					<td>公积金比例</td>
					<td>服务费收账</td>

					<%--<td>--%>
					<%--缴纳状态--%>
					<%--<div class="sort_list" data-sortName="statusCode">--%>
					<%--<img src="img/icon_sort.png" class="sorting_asc" data-sort="1">--%>
					<%--<img src="img/icon_sort.png" class="sorting_desc" data-sort="2">--%>
					<%--</div>--%>
					<%--</td>--%>
					<%--<td>操作</td>--%>
				</tr>
				</thead>
				<tbody>

				<%--<tr class="item order_item" data-id="订单id" data-pay_status="缴纳状态 1是成功，2是失败">--%>
				<%--<td class="choose_item" onclick="order_manage.ChooseItem(this)">--%>
				<%--<img src="img/icon_Unchecked.png"/>--%>
				<%--</td>--%>
				<%--<td class="order_subject">缴纳主体</td>--%>
				<%--<td class="order_user_name">姓名</td>--%>
				<%--<td class="order_user_idCard">身份证</td>--%>
				<%--<td class="order_soin_district">参保地区</td>--%>
				<%--<td class="order_soin_type">参保类型</td>--%>
				<%--<td class="order_service_month">服务月份</td>--%>
				<%--<td class="order_pay_month">缴纳月份</td>--%>
				<%--<td class="order_soin_base">社保基数</td>--%>
				<%--<td class="order_housefund_base">公积金基数</td>--%>
				<%--<td class="order_housefund_percent">公积金比例</td>--%>
				<%--<td class="order_hukou_type">户口性质</td>--%>
				<%--<td class="order_hukou_district">户籍地址</td>--%>
				<%--<td class="order_collection_service_fee">服务费(收账)</td>--%>
				<%--<td class="order_charge_service_fee">服务费(出账)</td>--%>
				<%--<td class="order_corp_subtotal">企业小计</td>--%>
				<%--<td class="order_person_subtotal">个人小计</td>--%>
				<%--<td class="order_collection_total">总计(收账)</td>--%>
				<%--<td class="order_charge_total">总计(出账)</td>--%>
				<%--<td class="order_salesman">业务员</td>--%>
				<%--<td class="order_supplier">供应商</td>--%>
				<%--<td class="order_pay_status">--%>
				<%--<span class="btn btn-sm btn-primary" onclick="order_manage.payFailModalShow()">缴纳失败</span>--%>
				<%--</td>--%>
				<%--<td class="order_operate">--%>
				<%--<span class="btn btn-sm btn-danger" onclick="order_manage.orderDelByOnly(this)">删除订单</span>--%>
				<%--</td>--%>
				<%--</tr>--%>


				</tbody>
			</table>
		</div>

	</div>

	<div class="foot">

		<%--<div class="choose_item" onclick="order_manage.chooseAll()"--%>
		<%--data-html="true"--%>
		<%--data-toggle="tooltip"--%>
		<%--data-placement="top"--%>
		<%--title="<p style='width:80px;'>选择查询条件下的所有内容</p>">--%>
		<%--<img src="img/icon_Unchecked.png"/>--%>
		<%--<span>全选</span>--%>
		<%--</div>--%>

		<div class="btn_list">

			<div class="btn btn-sm  btn-primary btn_export"
				 onclick="in_or_decrease_manage.orderExport()">
				导出
			</div>

			<%--<div class="btn btn-sm btn-default btn_del"--%>
			<%--onclick="order_manage.orderDelByMore()">--%>
			<%--删除订单--%>
			<%--</div>--%>

			<%--<div class="btn btn-sm btn-default btn_pay_fail"--%>
			<%--onclick="order_manage.payFailMore()">--%>
			<%--缴纳失败--%>
			<%--</div>--%>

			<%--<div class="btn btn-sm btn-default btn_pay_success"--%>
			<%--onclick="order_manage.paySuccessMore()">--%>
			<%--缴纳成功--%>
			<%--</div>--%>

			<%--<div class="btn btn-sm btn-default btn_pay_fail_all"--%>
			<%--onclick="order_manage.payFailAllModalShow()">缴纳失败(所有)--%>
			<%--</div>--%>

			<%--<div class="btn btn-sm btn-default btn_pay_success_all"--%>
			<%--onclick="order_manage.paySuccessAll()">缴纳成功(所有)--%>
			<%--</div>--%>

		</div>
	</div>

	<div class="pager_container">
		<%--<ul class="pagenation" style="float:right;"></ul>--%>
	</div>

</div>

<div class="modal fade soin_area_modals" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">参保地区</h4>
			</div>
			<div class="modal-body">

				<div class="all_area_tree_hud"></div>
				<div class="ztree soin_all_area_trees" id="soin_all_area_trees"></div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="in_or_decrease_manage.SoinAreaSure()">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade soin_pay_fail_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">社保缴纳失败原因</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-xs-3 txt">失败原因：</div>
					<div class="col-xs-9 txtInfo pay_fail_reason">
						<input type="text" class="form-control"/>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="order_detail.payFail()">
					确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade soin_pay_fail_all_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">社保缴纳失败原因(所有用户)</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-xs-3 txt">失败原因：</div>
					<div class="col-xs-9 txtInfo pay_fail_reason">
						<input type="text" class="form-control"/>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="in_or_decrease_manage.payFailAllSure()">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade order_detail_modals" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog" style="width:800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">订单详情</h4>
			</div>
			<div class="modal-body">

				<ul class="nav nav-tabs" role="tablist" id="myTab">

					<%--<li role="presentation" class="active">--%>
					<%--<a role="tab" data-toggle="tab">--%>
					<%--201612--%>
					<%--</a>--%>
					<%--</li>--%>

					<%--<li role="presentation" class="">--%>
					<%--<a role="tab" data-toggle="tab">--%>
					<%--201701--%>
					<%--</a>--%>
					<%--</li>--%>

				</ul>

				<div class="order_detail_container">

					<div class="row line-height-34">
						<div class="col-xs-3">管理费</div>
						<div class="col-xs-9 order_manage_fee">39.8</div>
					</div>

					<div class="row line-height-34">
						<div class="col-xs-3">总金额</div>
						<div class="col-xs-9 order_total_fee">34382</div>
					</div>

					<div class="row line-height-34">
						<div class="col-xs-3">缴纳状态</div>
						<div class="col-xs-9 order_status">

							<%--<div class='btn btn-sm btn-primary'--%>
							<%--onclick='order_detail.paySuccess()'>--%>
							<%--缴纳成功--%>
							<%--</div>--%>

							<%--<div class='btn btn-sm btn-primary'--%>
							<%--onclick='order_detail.payFailModalShow()'>--%>
							<%--缴纳失败--%>
							<%--</div>--%>

						</div>
					</div>

					<div class="row line-height-34">

						<table class="table table-striped table-bordered table-hover dataTable"
							   style="margin:20px 0 0;">
							<thead>

							<tr>
								<td></td>
								<td>工伤</td>
								<td>医疗</td>
								<td>生育</td>
								<td>养老</td>
								<td>失业</td>
								<td>公积金</td>
								<td>残保</td>
								<td>大病医疗</td>
								<td>工伤补充</td>
								<td>采暖费</td>
								<td>补充公积金</td>
							</tr>

							</thead>
							<tbody>



							</tbody>
						</table>

					</div>

				</div>

			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">
					关闭
				</button>
				<%--<button type="button" class="btn btn-primary">Save changes</button>--%>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
