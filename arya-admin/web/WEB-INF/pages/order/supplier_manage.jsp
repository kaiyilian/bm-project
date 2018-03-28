<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/8/2
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<script src="<%=contextPath%>/js/order/areaTree.js"></script>
<%--本页面--%>
<script src="<%=contextPath%>/js/order/supplier_manage.js"></script>
<link href="<%=contextPath%>/css/order/supplier_manage.css" rel="stylesheet"/>

<div class="supplier_manage">

	<div class="supplier_manage_container container">

		<div class="head border-bottom">
			<div class="txt">供应商列表</div>
			<div class="txtR" onclick="supplier_manage.supplierAllotShow()">
				<span>供应商绑定</span>
				<span> >> </span>
			</div>
		</div>

		<div class="content">

			<div class="table_container">
				<table class="table table-striped table-bordered table-hover ">
					<thead>
					<tr>
						<td class="choose_item"></td>
						<td>名称</td>
						<td>管理费</td>
						<td>城市</td>
						<td>操作</td>
					</tr>
					</thead>
					<tbody>
					<%--<tr class="item supplier_item" data-id="">--%>
					<%--<td class="choose_item" onclick="supplier_manage.ChooseItem(this)">--%>
					<%--<img src="img/icon_Unchecked.png"/>--%>
					<%--</td>--%>
					<%--<td class="supplier_name">铭生</td>--%>
					<%--<td class="manage_cost" data-cost="143">￥143</td>--%>
					<%--<td class="supplier_city">--%>
					<%--<div class="city_list already_open"--%>
					<%--onclick="supplier_manage.supplierBindCityModalShow(this)">--%>
					<%--8--%>
					<%--</div>--%>
					<%--</td>--%>
					<%--<td class="supplier_operate">--%>
					<%--<span class="btn btn-sm btn-primary btn_modify"--%>
					<%--onclick="supplier_manage.supplierModifyModalShow(this)">编辑</span>--%>
					<%--<span class="btn btn-sm btn-danger btn_del"--%>
					<%--onclick="supplier_manage.supplierDelByOnly(this)">删除</span>--%>
					<%--</td>--%>
					<%--</tr>--%>

					</tbody>
				</table>
			</div>

		</div>

		<div class="foot">
			<div class="choose_container" onclick="supplier_manage.ChooseAll()">
				<img src="img/icon_Unchecked.png"/>
				<span>全选</span>
			</div>

			<div class="btn_list">
				<div class="btn btn-sm btn-primary btn_add"
					 onclick="supplier_manage.supplierAddModalShow()">新增
				</div>
				<div class="btn btn-sm btn-default btn_del"
					 onclick="supplier_manage.supplierDelByMore()">删除
				</div>
			</div>
		</div>

		<div class="pager_container">
			<%--<ul class="pagenation" style="float:right;"></ul>--%>
		</div>

	</div>

	<div class="supplier_allot_container container" style="">

		<div class="head border-bottom">
			<div class="txt">供应商绑定</div>
			<div class="txtR" onclick="supplier_allot_manage.supplierListShow()">
				<span>供应商列表</span>
				<span> >> </span>
			</div>
		</div>

		<div class="content" style="">

			<div class="left_side col-xs-3">
				<div style="font-size: 18px;font-weight: bold;">已开通地区</div>

				<div class="area_tree_hud"></div>
				<div class="ztree soin_used_area_tree" id="soin_used_area_tree" style=""></div>
			</div>

			<div class="col-xs-9 right_side">

				<div class="table_container">
					<table class="table table-striped table-bordered table-hover dataTable">
						<thead>
						<tr>
							<td>供应商名称</td>
							<td>管理费</td>
							<td>是否首选</td>
							<td>操作</td>
						</tr>
						</thead>
						<tbody>
						<tr class="item supplier_item first_item" data-id="">
							<td class="supplier_name">铭生</td>
							<td class="manage_cost" data-cost="12">￥13</td>
							<td class="is_first">
								<div class="icon"></div>
							</td>
							<td class="supplier_operate">
							<span class="btn btn-sm btn-primary btn_first"
								  onclick="supplier_allot_manage.supplierFirSetting(this)">设为首选</span>
							<span class="btn btn-sm btn-danger btn_del"
								  onclick="supplier_allot_manage.supplierDel(this)">移除</span>
							</td>
						</tr>

						<tr class="item supplier_item" data-id="">
							<td class="supplier_name">神魔</td>
							<td class="manage_cost" data-cost="18">￥18</td>
							<td class="is_first">
								<div class="icon"></div>
							</td>
							<td class="supplier_operate">
							<span class="btn btn-sm btn-primary btn_first"
								  onclick="supplier_allot_manage.supplierFirSetting(this)">设为首选</span>
							<span class="btn btn-sm btn-danger btn_del"
								  onclick="supplier_allot_manage.supplierDel(this)">移除</span>
							</td>
						</tr>
						</tbody>
					</table>
				</div>

				<div class="btn_list">
					<div class="btn btn-primary btn_supplier_add"
						 onclick="supplier_allot_manage.supplierListByUnusedModalShow()">
						添加供应商
					</div>
				</div>

				<div class="prompt"></div>

			</div>

		</div>

	</div>

</div>

<div class="modal fade supplier_add_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增供应商</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-xs-3 txt">姓名：</div>
					<div class="col-xs-9 txtInfo supplier_name">
						<input type="text" class="form-control" placeholder="请输入供应商名称"/>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-3 txt">管理费：</div>
					<div class="col-xs-9 txtInfo manage_cost">
						<input type="text" class="form-control" placeholder="请输入管理费"
							   onkeyup="if(isNaN(value))execCommand('undo')"/>
					</div>
				</div>

				<%--<div class="row">--%>
				<%--<div class="col-xs-3 txt">联系人：</div>--%>
				<%--<div class="col-xs-9 txtInfo contact_name">--%>
				<%--<input type="text" class="form-control" placeholder="请输入联系人名称"/>--%>
				<%--</div>--%>
				<%--</div>--%>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="supplier_manage.supplierAddSure()">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade supplier_modify_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">编辑供应商</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-xs-3 txt">姓名：</div>
					<div class="col-xs-9 txtInfo supplier_name">
						<input type="text" class="form-control" placeholder="请输入供应商名称"/>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-3 txt">管理费：</div>
					<div class="col-xs-9 txtInfo manage_cost">
						<input type="text" class="form-control" placeholder="请输入管理费"
							   onkeyup="if(isNaN(value))execCommand('undo')"/>
					</div>
				</div>

				<%--<div class="row">--%>
				<%--<div class="col-xs-3 txt">联系人：</div>--%>
				<%--<div class="col-xs-9 txtInfo contact_name">--%>
				<%--<input type="text" class="form-control" placeholder="请输入联系人名称"/>--%>
				<%--</div>--%>
				<%--</div>--%>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="supplier_manage.supplierModifySure()">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade supplier_bind_city_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">供应商绑定城市列表</h4>
			</div>
			<div class="modal-body">
				<%--范德萨--%>
			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade supplier_unused_city_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">该城市未绑定供应商列表</h4>
			</div>
			<div class="modal-body">

				<div class="table_container">
					<table class="table table-striped table-bordered table-hover dataTable">
						<thead>
						<tr>
							<td class="choose_item"></td>
							<td>名称</td>
							<td>管理费</td>
							<td>城市</td>
						</tr>
						</thead>
						<tbody>
						<%--<tr class="item supplier_item" data-id="">--%>
						<%--<td class="choose_item" onclick="supplier_allot_manage.ChooseItem(this)">--%>
						<%--<img src="img/icon_Unchecked.png"/>--%>
						<%--</td>--%>
						<%--<td class="supplier_name">铭生</td>--%>
						<%--<td class="manage_cost" data-cost="142">￥142</td>--%>
						<%--<td class="supplier_contact">张三</td>--%>
						<%--<td class="supplier_city">9</td>--%>
						<%--</tr>--%>
						<%--<tr class="item supplier_item" data-id="">--%>
						<%--<td class="choose_item" onclick="supplier_allot_manage.ChooseItem(this)">--%>
						<%--<img src="img/icon_Unchecked.png"/>--%>
						<%--</td>--%>
						<%--<td class="supplier_name">铭生</td>--%>
						<%--<td class="manage_cost" data-cost="2">￥2</td>--%>
						<%--<td class="supplier_contact">张三</td>--%>
						<%--<td class="supplier_city">0</td>--%>
						<%--</tr>--%>
						</tbody>
					</table>
				</div>

				<div class="prompt"></div>

				<div class="pager_container">
					<%--<ul class="pagenation" style="float:right;"></ul>--%>
				</div>
			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<button type="button" class="btn btn-primary"
						onclick="supplier_allot_manage.supplierAddSure()">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
