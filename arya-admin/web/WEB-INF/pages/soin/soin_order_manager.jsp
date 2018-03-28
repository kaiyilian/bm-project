<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by CuiMengxin.
  Date: 2015/11/10
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
                        <input id="soin_order_manage_person_idcardno_or_phone" type="text"
                               placeholder="请输入参保人身份证号或手机号或姓名"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 hr-line-dashed"></div>
                    <div id="div_soin_type" class="col-md-4">
                        <span class="font-bold">社保类型：城市地区社保类型</span>
                        <button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
                                data-target="#soin_order_manager_chose_soin_type_modal"
                                onclick="soinOrderManager.getSoinDistricTree()">选择
                        </button>
                        <p id="soin_order_manager_soin_type_text"></p>
                    </div>
                    <div class="col-md-4">
                        <span class="font-bold">订单状态：</span>
                        <select id="soin_order_manager_status_chosen" data-placeholder="选择订单状态" class="chosen-select"
                                multiple style="width:250px;"
                                tabindex="4">
                            <c:forEach var="item" items="${order_status}">
                                <option value="${item.key}" hassubinfo="true">${item.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-primary btn-sm" style="margin-top: 5px"
                                onclick="soinOrderManager.getOrderList()">查询
                        </button>
                        <button type="button" class="btn btn-primary btn-sm" style="margin-top: 5px"
                                onclick="soinOrderManager.clearAllFilterCondition()">清空
                        </button>
                    </div>
                    <div class="col-md-12 hr-line-dashed"></div>
                </div>
                <div class="row">
                    <div id="soin_order_list_content" class="col-md-12">
                        <table id="soin_order_list" class="display" cellspacing="0" style="width: 100%">
                        </table>
                    </div>
                    <div class="col-md-12">
                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                onclick="refreshDataTable(soinOrderManager)"> 刷新
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--选择社保类型的Modal窗口--%>
<div class="modal inmodal" id="soin_order_manager_chose_soin_type_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">选择社保类型</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div id="soin_order_manager_district_tree" class="col-md-5"></div>
                    <div class="col-md-7">
                        <table id="soin_order_manager_soin_type_list" class="display" cellspacing="0"
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
                        onclick="soinOrderManager.chosedSoinType()">确定
                </button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--确认操作的模态窗口--%>
<div class="modal inmodal" id="soin_order_confirm_change_status_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">确认操作</h4>
            </div>
            <div class="modal-body">
                <div id="status_change_hint" style="text-align:center">
                    确定将订单 <span id="status_change_hint_order_no" class="label label-primary"></span> 从 <span
                        id="current_status"></span> 状态变更为 <span id="to_status"></span>
                    状态吗？
                </div>
                <div id="under_way_hint" style="text-align:center">
                    确定缴纳订单<span id="under_way_order_no" class="label label-primary"></span>的<span
                        id="under_way_time" class="label label-primary"></span>社保吗？
                </div>
                <div id="order_money_hit">
                    <div class="input-group m-b" style="width:40%;margin:10px auto;">
                        <span class="input-group-addon">请输入金额</span>
                        <input type="text" class="form-control" id="order_money_input" order_money
                               placeholder="输入金额">
                        <span class="input-group-addon">元</span>
                    </div>
                </div>
                <div>
                    <div id="order_money_hit_hud" class="input-group m-b" style="width:40%;margin:10px auto;">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirm_change_status_btn" type="button" class="btn btn-white" data-dismiss="modal">确定
                </button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--选择业务员和供应商的Modal窗口--%>
<div class="modal inmodal" id="soin_order_manager_chose_salesman_supplier_modal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">选择业务员和供应商</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-2">业务员</div>
                    <div class="col-md-3">
                        <select id="soin_order_salesman_select">

                        </select>
                    </div>
                    <div class="col-md-2">供应商</div>
                    <div class="col-md-3">
                        <select id="soin_order_supplier_select">

                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal"
                        onclick="soinOrderManager.confirmAdjustSalesmanAndSupplier()">确定
                </button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script src="<%=contextPath%>/js/soin/soin_order_manager.js"></script>