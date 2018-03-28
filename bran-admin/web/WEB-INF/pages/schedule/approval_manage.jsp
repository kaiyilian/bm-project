<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/14
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/approval_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/approval_manage.js"></script>

<div class="container approval_manage_container">

    <div class="head border-bottom">
        <%--<i class="icon icon-attendance_setting"></i>--%>
        <i class="icon icon-emp"></i>
        <div class="txt">审批管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="row">

                <div class="input-group col-xs-6 item">
                    <span class="input-group-addon">开始时间:</span>
                    <input class="form-control layer-date beginTime" id="approval_beginTime"
                           placeholder="YYYY-MM-DD">
                    <span class="input-group-addon">至</span>
                    <input class="form-control layer-date endTime" id="approval_endTime"
                           placeholder="YYYY-MM-DD">
                </div>

                <div class="input-group col-xs-3 item">
                    <span class="input-group-addon">审批分类：</span>
                    <select class="form-control approval_type">
                        <%--<option>全部</option>--%>
                        <%--<option>请假</option>--%>
                        <%--<option>缺卡</option>--%>
                        <%--<option>加班</option>--%>
                    </select>
                </div>

                <div class="input-group col-xs-3 item">
                    <span class="input-group-addon">处理状态：</span>
                    <select class="form-control approval_status">
                        <%--<option>全部</option>--%>
                        <%--<option>待处理</option>--%>
                        <%--<option>通过</option>--%>
                        <%--<option>未通过</option>--%>
                        <%--<option>已撤销</option>--%>
                    </select>
                </div>

                <div class="input-group col-xs-6 item">
                    <span class="input-group-addon">快速搜索：</span>
                    <select data-placeholder="请选择员工姓名、工号或工段" multiple
                            class="chosen-select user_list">
                        <%--<option>1</option>--%>
                    </select>
                </div>

                <div class="col-xs-3 item btn_list">

                    <div class="btn btn-sm btn-orange btn_search"
                         onclick="approval_manage.btnSearchClick()">
                        查询
                    </div>

                    <div class="btn btn-sm btn-orange btn_reset"
                         onclick="approval_manage.resetParam()">
                        重置
                    </div>

                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_approval_manage"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_pass" onclick="approval_manage.approvalDeal(2)">
                批量通过
            </div>


            <div class="btn btn-sm btn-success btn_fail" onclick="approval_manage.approvalDeal(1)">
                批量不通过
            </div>

        </div>

    </div>


</div>

<div class="modal fade approval_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">审批单</h4>
            </div>
            <div class="modal-body">

                <div class="row border-bottom">

                    <div class="col-xs-4">
                        <div class="txt">申请时间：</div>
                        <div class="apply_time">
                            <%--2017-05-02 08:12:23--%>
                        </div>
                    </div>

                    <div class="col-xs-2">
                        <div class="txt">员工：</div>
                        <div class="user_name"></div>
                    </div>

                    <div class="col-xs-3">
                        <div class="txt">部门：</div>
                        <div class="department_name"></div>
                    </div>

                    <div class="col-xs-3">
                        <div class="txt">班组：</div>
                        <div class="work_shift_name"></div>
                    </div>

                </div>

                <div class="apply_info row border-bottom">
                    <div class="col-xs-2" style="font-weight: bold;">申请信息</div>
                    <div class="col-xs-10">

                        <div class="row">
                            <div class="txt">申请类型：</div>
                            <div class="apply_type"></div>
                        </div>

                        <div class="row">

                            <div class="col-xs-6">
                                <div class="txt">开始时间：</div>
                                <div class="begin_time"></div>
                            </div>

                            <div class="col-xs-6">
                                <div class="txt">结束时间：</div>
                                <div class="end_time"></div>
                            </div>

                            <div class="fill_card_container" style="line-height:34px;">
                                <div class="txt">补卡时间：</div>
                                <div class="fill_card_time"></div>
                            </div>

                        </div>

                        <div class="row total_time_container">

                            <div class="txt">合计跨时长（小时）：</div>
                            <div class="total_time"></div>

                        </div>

                        <div class="row">

                            <div class="txt">申请理由：</div>
                            <div class="apply_reason"></div>

                        </div>

                    </div>
                </div>

                <div class="approval_info row">
                    <div class="col-xs-2" style="font-weight: bold;">审批信息</div>
                    <div class="col-xs-10">

                        <div class="row">
                            <div class="txt">审批时间：</div>
                            <div class="approval_time"></div>
                        </div>

                        <div class="row">
                            <div class="txt">审批结果：</div>
                            <div class="approval_status"></div>
                        </div>

                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">关闭</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
