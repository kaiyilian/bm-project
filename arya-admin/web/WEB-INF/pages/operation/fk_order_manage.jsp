<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/9/6
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/fk_order_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/fk_order_manage.js"></script>

<div class="fk_order_manage_container container">

	<div class="head border-bottom">
		<div class="txt">福库订单管理</div>
	</div>

	<div class="content">

		<span class="search_container">

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">产品名称：</span>
				<select name="corp" data-placeholder="请选择产品" multiple
						class="chosen-select good_list">
					<%--<option>1</option>--%>
				</select>
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">开始时间：</span>
				<input class="form-control layer-date beginTime" placeholder="YYYY-MM-DD"
					   onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">结束时间：</span>
				<input class="form-control layer-date endTime" placeholder="YYYY-MM-DD"
					   onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
			</span>

           <span class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字：</span>
				<input type="text" class="form-control searchCondition" placeholder="姓名/订单号/手机号"
					   maxlength="20">
				<span class="add-on"><i class="icon-remove"></i></span>
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">公司名称：</span>
				<select name="corp" data-placeholder="请选择公司" multiple
						class="chosen-select corp_list">
					<option value="1">公司1</option>
					<option value="3">公司3</option>
					<option value="2">公司2</option>
				</select>
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">订单状态：</span>
				<select name="corp" data-placeholder="请选择订单状态" multiple
						class="chosen-select order_status_list">
					<option value="1">公司1</option>
					<option value="3">公司3</option>
					<option value="2">公司2</option>
				</select>
			</span>

			<div class="btn_list">
				<span class="btn btn-sm btn-primary btn_search"
					  onclick="fk_order_manage.btnSearchClick()">查询
				</span>
				<span class="btn btn-sm btn-primary btn_reset"
					  onclick="fk_order_manage.initSearchCondition()">重置
				</span>
			</div>

		</span>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable" >
				<thead>
				<tr>
					<%--<td class="choose_item"></td>--%>
					<td>订单编号</td>
					<td>姓名</td>
					<td>手机号</td>
					<td>收货地址</td>
					<td>产品名</td>
					<td>数量</td>
					<td>规格</td>
					<td>支付金额</td>
					<td>创建时间</td>
					<td>订单状态</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>

				<%--<tr class="item fk_order_item" data-id="1">--%>
				<%--<td class="choose_item" onclick="fk_order_manage.ChooseItem(this)">--%>
				<%--<img src="img/icon_Unchecked.png"/>--%>
				<%--</td>--%>
				<%--<td class="order_no">1343432432</td>--%>
				<%--<td class="order_consignee">姓名</td>--%>
				<%--<td class="order_address">收货地址</td>--%>
				<%--<td class="order_product_name">健身器</td>--%>
				<%--<td class="order_count">42</td>--%>
				<%--<td class="order_unit">规格</td>--%>
				<%--<td class="order_money">324</td>--%>
				<%--<td class="order_create_time">2016-09-23 11:23</td>--%>
				<%--<td class="order_operate">--%>

				<%--<span class="btn btn-sm btn-primary btn_modify"--%>
				<%--onclick="fk_order_manage.orderModifyModalShow(this)">编辑</span>--%>

				<%--</td>--%>
				<%--</tr>--%>

				<%--<tr class="item fk_order_item" data-id="2">--%>
				<%--<td class="choose_item" onclick="fk_order_manage.ChooseItem(this)">--%>
				<%--<img src="img/icon_Unchecked.png"/>--%>
				<%--</td>--%>
				<%--<td class="order_no">1343432432</td>--%>
				<%--<td class="order_consignee">姓名</td>--%>
				<%--<td class="order_address">收货地址</td>--%>
				<%--<td class="order_product_name">产品名</td>--%>
				<%--<td class="order_count">3</td>--%>
				<%--<td class="order_unit">规格</td>--%>
				<%--<td class="order_money">324</td>--%>
				<%--<td class="order_create_time">2016-09-23 11:23</td>--%>
				<%--<td class="order_operate">--%>

				<%--<span class="btn btn-sm btn-primary btn_modify"--%>
				<%--onclick="fk_order_manage.orderModifyModalShow(this)">编辑</span>--%>

				<%--</td>--%>
				<%--</tr>--%>

				</tbody>
			</table>
		</div>

	</div>

	<div class="foot">

		<div class="choose_container" style="display: none;" onclick="fk_order_manage.ChooseAll()">
			<img src="img/icon_Unchecked.png"/>
			<span>全选</span>
		</div>

		<div class="btn_list">

			<div class="btn btn-sm  btn-primary btn_export"
				 onclick="fk_order_manage.orderExport()">导出全部
			</div>

			<div class="btn btn-sm  btn-primary btn_export_"
				 onclick="fk_order_manage.receiptFormExport()">
				导出签收单
			</div>

		</div>

	</div>

	<div class="pager_container">
		<ul class="pagenation" style="float:right;"></ul>
	</div>

</div>

<div class="modal fade order_modify_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">订单编辑</h4>
			</div>
			<div class="modal-body">

				<%--<div class="row margin_b_10">--%>
				<%--<div class="col-xs-12">--%>

				<%--<span class="col-xs-2 line-height-34">产品数量</span>--%>

				<%--<span class="col-xs-10">--%>
				<%--<input type="text" class="form-control good_count"--%>
				<%--placeholder="请输入商品名称" maxlength="20"--%>
				<%--onkeyup="this.value=this.value.replace(/\D/g,'')"/>--%>
				<%--</span>--%>

				<%--</div>--%>
				<%--</div>--%>

				<div class="row margin_b_10">
					<div class="col-xs-12">

						<span class="col-xs-2 line-height-34">产品分类</span>

						<span class="col-xs-10 category_list">

							<%--<span class="item btn btn-sm btn-default" data-id="color"--%>
								  <%--onclick="fk_good_manage.getCategoryInfo(this)">颜色</span>--%>
							<%--<span class="item btn btn-sm btn-default" data-id="color"--%>
								  <%--onclick="fk_good_manage.getCategoryInfo(this)">颜色</span>--%>

						</span>

					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary"
						onclick="fk_order_manage.orderSaveByModify()">保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade order_category_info_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">分类</h4>
			</div>
			<div class="modal-body">
				<div class="btn btn-primary btn_clear"
					 onclick="fk_order_manage.clearUnitByChoosed()">清空
				</div>
				<div class="unit_list">

				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" onclick="fk_order_manage.unitSave()">保存</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
