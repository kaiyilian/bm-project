<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/11/21
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/fk_coupon_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/fk_coupon_manage.js"></script>

<div class="fk_coupon_manage_container container">

	<div class="head border-bottom">
		<div class="txt">福库券管理</div>
	</div>

	<div class="content">

		<span class="search_container">

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">企业名称：</span>
				<select data-placeholder="请选择公司" multiple
						class="corp_list chosen-select form-control">
					<%--<option>aa</option>--%>
					<%--<option>bb</option>--%>
					<%--<option>cc</option>--%>
				</select>
			</span>

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">创建时间：</span>
				<input class="form-control layer-date createTime" placeholder="YYYY/MM/DD"
					   onclick="laydate({istime: true, format: 'YYYY/MM/DD'})">
			</span>

			<div class="btn_list">
				<span class="btn btn-sm btn-primary btn_search"
					  onclick="fk_coupon_manage.btnSearchClick()">查询
				</span>
			</div>

		</span>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item" onclick="fk_coupon_manage.chooseCurAll()">
						<img src="img/icon_Unchecked.png"
							 data-html="true"
							 data-toggle="tooltip"
							 data-placement="right"
							 title="<p style='width:80px;'>选择当前页所有选项</p>"/>
					</td>
					<td>福库券</td>
					<td>企业</td>
					<td>数量</td>
					<td>开始时间</td>
					<td>结束时间</td>
					<td>金额</td>
					<td>状态</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>

				<%--<tr class="item fk_coupon_item" data-id="1">--%>
				<%--<td class="choose_item" onclick="fk_coupon_manage.chooseItem(this)">--%>
				<%--<img src="img/icon_Unchecked.png"/>--%>
				<%--</td>--%>
				<%--<td class="coupon_img">--%>
				<%--<img src="img/2.jpg">--%>
				<%--</td>--%>
				<%--<td class="corp_name">aa公司</td>--%>
				<%--<td class="coupon_count">1213</td>--%>
				<%--<td class="coupon_beginTime">2015-02-12</td>--%>
				<%--<td class="coupon_endTime">2015-03-12</td>--%>
				<%--<td class="coupon_money">12</td>--%>
				<%--<td class="coupon_status">已导出</td>--%>
				<%--<td class="coupon_createTime">2015-01-12</td>--%>
				<%--<td class="operate">--%>

				<%--<div class="btn btn-sm btn-primary btn_modify"--%>
				<%--onclick="fk_coupon_manage.couponModifyModalShow(this)">--%>
				<%--编辑--%>
				<%--</div>--%>

				<%--<div class="btn btn-sm btn-danger btn_del"--%>
				<%--onclick="fk_coupon_manage.couponDel(this)">--%>
				<%--删除--%>
				<%--</div>--%>

				<%--</td>--%>

				<%--</tr>--%>


				</tbody>
			</table>
		</div>

	</div>

	<div class="foot">

		<%--<div class="choose_item" onclick="fk_coupon_manage.chooseAll()"--%>
		<%--data-html="true"--%>
		<%--data-toggle="tooltip"--%>
		<%--data-placement="top"--%>
		<%--title="<p style='width:80px;'>选择查询条件下的所有内容</p>">--%>
		<%--<img src="img/icon_Unchecked.png"/>--%>
		<%--<span>全选</span>--%>
		<%--</div>--%>

		<div class="btn_list">

			<div class="btn btn-sm btn-primary btn_add"
				 onclick="fk_coupon_manage.couponAddModalShow()">
				新增
			</div>

			<div class="btn btn-sm btn-primary btn_add"
				 onclick="fk_coupon_manage.couponExport()">
				导出
			</div>

		</div>

	</div>

	<div class="pager_container">
		<ul class="pagenation" style="float:right;"></ul>
	</div>

</div>

<div class="modal fade coupon_info_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增福库券</h4>
			</div>
			<div class="modal-body">

				<div class="row">

					<div class="form-control img_thumb ">
						<img src="img/img_add_default.png" class="coupon_bg">
					</div>

				</div>

				<div class="row">

					<div class="col-xs-2">企业</div>
					<div class="col-xs-10 corp_list_container">
						<select data-placeholder="请选择公司" multiple
								class="corp_list chosen-select">
							<%--<option value="1">公司1</option>--%>
							<%--<option value="3">公司3</option>--%>
							<%--<option value="2">公司2</option>--%>
						</select>
					</div>

				</div>

				<div class="row">

					<div class="col-xs-2">数量</div>
					<div class="col-xs-10">
						<input type="text" placeholder="请输入数量"
							   class="form-control coupon_count" maxlength="5"
							   onkeyup="this.value=this.value.replace(/\D/g,'')"
							   onblur="this.value=this.value.replace(/\D/g,'')">
					</div>

				</div>

				<div class="row">

					<div class="col-xs-2">金额</div>
					<div class="col-xs-10">
						<input type="text" placeholder="请输入金额"
							   class="form-control coupon_money" maxlength="4"
							   onkeyup="this.value=this.value.replace(/\D/g,'')"
							   onblur="this.value=this.value.replace(/\D/g,'')">
					</div>

				</div>

				<div class="row">

					<div class="btn btn-sm btn-default btn_timeSet"
						 onclick="fk_coupon_manage.timeSet()">
						设置时间
					</div>

					<div class="col-xs-12 time_container">

						<div class="row">
							<div class="col-xs-2">开始时间</div>
							<div class="col-xs-10">
								<input type="text" placeholder="请输入开始时间"
									   class="form-control coupon_beginTime" id="coupon_beginTime">
								<%--onclick="laydate({istime: true, format: 'YYYY/MM/DD hh:mm:ss'})"--%>
							</div>
						</div>

						<div class="row">
							<div class="col-xs-2">结束时间</div>
							<div class="col-xs-10">
								<input type="text" placeholder="请输入结束时间"
									   class="form-control coupon_endTime" id="coupon_endTime">
								<%--onclick="laydate({istime: true, format: 'YYYY/MM/DD'})"--%>
							</div>
						</div>

					</div>


				</div>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="fk_coupon_manage.couponSave()">保存</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>