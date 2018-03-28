<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  User: CuiMengxin
  Date: 2016/2/1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/table_page_header.jsp" %>
<link href="<%=contextPath%>/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bootstrap-treeview.js"></script>
<script src="<%=contextPath%>/js/soin/soin.js"></script>
<script src="<%=contextPath%>/js/soin/soin_order_status_manager.js"></script>
<script src="<%=contextPath%>/js/soin/soin_order_detail.js"></script>
<!-- Chosen -->
<link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>

<div class="row animated fadeIn">
	<div class="col-md-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="row">
					<div class="col-md-4">
						<input id="soin_order_querier_user_idcardno_or_phone" type="text" placeholder="请输入用户身份证号或手机号或姓名" class="form-control">
					</div>
					<div class="col-md-4">
						<input id="soin_order_querier_person_idcardno_or_phone" type="text" placeholder="请输入参保人身份证号或手机号或姓名" class="form-control">
					</div>
					<div class="col-md-2">
						<input id="soin_order_querier_order_no" type="text" placeholder="请输入订单编号" class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 hr-line-dashed"></div>
					<div id="div_order_querier_soin_type" class="col-md-4">
						<span class="font-bold">社保类型：城市地区社保类型</span>
						<button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
								data-target="#soin_order_querier_chose_soin_type_modal"
								onclick="soinOrderQuerier.getSoinDistricTree()">选择
						</button>
						<p id="soin_order_querier_soin_type_text"></p>
					</div>
					<div class="col-md-3">
						<select id="soin_order_querier_status_chosen" data-placeholder="请选择订单状态" class="chosen-select"
								multiple style="width:100%;"
								tabindex="4">
							<c:forEach var="item" items="${order_status}">
								<option value="${item.key}" hassubinfo="true">${item.value}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<button type="button" class="btn btn-primary btn-sm" style="margin-top: 5px"
								onclick="refreshList(soinOrderQuerier)">查询
						</button>
						<button type="button" class="btn btn-primary btn-sm" style="margin-top: 5px"
								onclick="soinOrderQuerier.clearAllFilterCondition()">清空
						</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 hr-line-dashed"></div>
					<div id="soin_order_querier_list_content" class="col-md-12">
						<table id="soin_order_querier_list" class="display" cellspacing="0" style="width: 100%">
						</table>
					</div>
					<div class="col-md-12">
						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
								onclick="refreshDataTable(soinOrderQuerier)"> 刷新
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%--选择社保类型的Modal窗口--%>
<div class="modal inmodal" id="soin_order_querier_chose_soin_type_modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated fadeIn">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
						class="sr-only">Close</span></button>
				<h4 class="modal-title">选择社保类型</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="soin_order_querier_district_tree" class="col-md-5"></div>
					<div class="col-md-7">
						<table id="soin_order_querier_soin_type_list" class="display" cellspacing="0"
							   style="width: 100%">
							<thead>
							<tr>
								<th><input name="select_all" value="1" type="checkbox"></th>
								<th>名称</th>
								<th>描述</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-white" data-dismiss="modal"
						onclick="soinOrderQuerier.chosedSoinType()">确定
				</button>
				<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<script src="<%=contextPath%>/js/soin/soin_order_querier.js"></script>
