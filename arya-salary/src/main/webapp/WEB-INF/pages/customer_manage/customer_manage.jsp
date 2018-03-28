<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/3
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--dropzone--%>
<link href="<%=contextPath%>/js/plugins/dropzone/dropzone.min.css" rel="stylesheet"/>
<link href="<%=contextPath%>/js/plugins/dropzone/basic.min.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/plugins/dropzone/dropzone.js"></script>


<link href="<%=contextPath%>/css/arya/customer_manage/customer_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/customer_manage/customer_manage.js"></script>

<div class="row animated fadeIn customer_manage_container">

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="title">
            客户列表
        </div>

        <div class="search">
            <input type="text" id="customer_search" class="form-control" placeholder="请输入客户名称"
                   onkeyup="customer_manage.enterSearch(event)">
            <i class="glyphicon glyphicon-search" onclick="customer_manage.organizationTreeList()"></i>
        </div>

        <div class="tree_content">
            <ul id="customer_group_tree" class="ztree"></ul>
        </div>

    </div>

    <div class="col-xs-10 container customer_info_container">

        <div class="content">

            <div class="customer_info">

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">客户名称：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control customer_name" placeholder="请输入客户名称">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">发票类型：</div>
                        <div class="col-xs-9 txtInfo">
                            <select class="form-control invoice_type">
                                <%--<option value="1">全额专票</option>--%>
                                <%--<option value="2">差额普票</option>--%>
                            </select>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">联系人：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control contact_name" placeholder="请输入客户联系人">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">发票项目1：</div>
                        <div class="col-xs-9 txtInfo">
                            <select class="form-control invoice_project_1">
                                <%--<option value="1">工资</option>--%>
                                <%--<option value="2">劳动费</option>--%>
                                <%--<option value="3">管理费</option>--%>
                                <%--<option value="4">服务费</option>--%>
                                <%--<option value="5">个税</option>--%>
                                <%--<option value="6">其他</option>--%>
                                <%--<option value="7">无</option>--%>
                            </select>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">联系电话：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control contact_phone" placeholder="请输入联系电话"
                                   onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">发票项目2：</div>
                        <div class="col-xs-9 txtInfo">
                            <select class="form-control invoice_project_2">
                                <%--<option value="1">工资</option>--%>
                                <%--<option value="2">劳动费</option>--%>
                                <%--<option value="3">管理费</option>--%>
                                <%--<option value="4">服务费</option>--%>
                                <%--<option value="5">个税</option>--%>
                                <%--<option value="6">其他</option>--%>
                                <%--<option value="7">无</option>--%>
                            </select>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">邮箱：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control email" placeholder="请输入邮箱">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">地址：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control address" placeholder="请输入地址">
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">销售人员：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control sales_man" placeholder="请输入销售人员姓名">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">销售部门：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control sales_dept" placeholder="请输入销售部门">
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-6">
                        <div class="col-xs-3">合同开始时间：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control layer-date customer_contract_begin" data-time=""
                                   id="customer_contract_begin" placeholder="YYYY-MM-DD">
                        </div>
                    </div>

                    <div class="col-xs-6">
                        <div class="col-xs-3">合同结束时间：</div>
                        <div class="col-xs-9 txtInfo">
                            <input class="form-control layer-date customer_contract_end" data-time=""
                                   id="customer_contract_end" placeholder="YYYY-MM-DD">
                        </div>
                    </div>

                </div>

                <div class="btn_list">

                    <%--<div class="btn btn-sm btn-primary btn_save" onclick="customer_manage.customInfoSave()">保存</div>--%>
                    <%--<div class="btn btn-sm btn-default btn_cancel" onclick="customer_manage.customInfoCancel()">取消</div>--%>

                    <%--<div class="btn btn-sm btn-primary btn_modify" onclick="customer_manage.customInfoModify()">编辑</div>--%>
                    <div class="btn btn-sm btn-primary btn_detail" onclick="customer_manage.customerDetail()">查看详情</div>

                </div>

            </div>

            <div class="contract_info_container" data-url="">

                <div class="row">
                    <div class="col-xs-3" style="font-size: 16px;">公司合同：</div>
                    <div class="col-xs-9 txtInfo">

                        <div class="btn btn-sm btn-primary btn_upload" onclick="customer_manage.contractModalShow()">
                            上传合同
                        </div>

                        <div class="btn btn-sm btn-primary btn_down" onclick="customer_manage.tempDown()">
                            下载合同
                        </div>

                        <%--<div class="btn_clear" onclick="customer_manage.initContractOperate();">清除</div>--%>

                    </div>
                    <div class="col-xs-9 col-xs-offset-3 contract_img_list"></div>
                </div>

            </div>

            <div class="account_info_container">

                <div class="btn btn-sm btn-primary btn_recharge" onclick="customer_manage.accountRechargeModalShow()">
                    账户充值
                </div>

                <div class="btn btn-sm btn-primary btn_ledger" onclick="customer_manage.goLedgerPage()">
                    台账
                </div>

            </div>

            <div class="salary_rule_container">

                <div class="t_title">
                    薪资计算规则
                </div>

                <div class="rule_type_container">

                    <label class="radio-inline rule_item">
                        <input type="radio" name="inlineRadioOptions" class="custom_rule"
                               onclick="salary_calc_rule.initCustomRule()">
                        自定义计算规则
                    </label>

                    <label class="radio-inline rule_item">
                        <input type="radio" name="inlineRadioOptions" class="normal_rule"
                               onclick="salary_calc_rule.initNormalRule()">
                        标准计算规则
                    </label>

                    <label class="radio-inline rule_item">
                        <input type="radio" name="inlineRadioOptions" class="blueCollar_rule"
                               onclick="salary_calc_rule.initBlueCollarRule()">
                        蓝领薪资计算
                    </label>

                    <label class="radio-inline rule_item">
                        <input type="radio" name="inlineRadioOptions" class="general_rule"
                               onclick="salary_calc_rule.initGeneralRule()">
                        普通薪资计算
                    </label>

                </div>

                <div class="rule_info_container">

                    <div class="calc_rule_container custom_calc_rule_container">

                        <div class="btn btn-sm btn-primary btn_add_tax_rate" onclick="salary_calc_rule.addTaxRate()">
                            新增计税档
                        </div>

                        <div class="tax_gears_list">


                        </div>

                        <%--<div class="row">--%>

                        <%--<div class="col-md-6">--%>
                        <%--<div class="input-group m-b">--%>
                        <%--<span class="input-group-addon">个税服务费率</span>--%>
                        <%--<input type="number" class="form-control service_charge_tax_rate"--%>
                        <%--name="service_charge_tax_rate">--%>
                        <%--<span class="input-group-addon">%</span>--%>
                        <%--</div>--%>
                        <%--</div>--%>

                        <%--</div>--%>

                        <div class="row">

                            <div class="col-md-6">
                                <div class="input-group m-b">
                                    <span class="input-group-addon">薪资服务费率</span>
                                    <input type="number" class="form-control brokerage_rate"
                                           name="brokerage_rate">
                                    <span class="input-group-addon">%</span>
                                </div>
                            </div>

                            <div class="col-md-6">

                                <select class="brokerage_rate_type">
                                    <option value="company">公司</option>
                                    <option value="personal">个人</option>
                                </select>

                            </div>

                        </div>

                    </div>

                    <div class="calc_rule_container normal_calc_rule_container">

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">起征点</span>
                                    <input type="number" class="form-control threshold_tax">
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">薪资服务费</span>
                                    <input type="number" class="form-control brokerage" name="brokerage">
                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="calc_rule_container blueCollar_calc_rule_container">

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">病假扣款比例</span>
                                    <input type="number" class="form-control ill_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">非新进离职员工旷工扣款比例</span>
                                    <input type="number" class="form-control absence_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">新进离职员工旷工扣款比例</span>
                                    <input type="number" class="form-control new_leave_absence_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">事假扣款比例</span>
                                    <input type="number" class="form-control affair_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row fullTime_bonus" data-level="1">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">全勤奖金：第一档</span>
                                    <input type="number" class="form-control fullTime_bonus_fir">
                                </div>

                            </div>

                        </div>

                        <div class="row fullTime_bonus" data-level="2">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">全勤奖金：第二档</span>
                                    <input type="number" class="form-control fullTime_bonus_sec">
                                </div>

                            </div>

                        </div>

                        <div class="row fullTime_bonus" data-level="3">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">全勤奖金：第三档</span>
                                    <input type="number" class="form-control fullTime_bonus_three">
                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="calc_rule_container general_calc_rule_container">

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">病假扣款比例</span>
                                    <input type="number" class="form-control ill_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">事假扣款比例</span>
                                    <input type="number" class="form-control affair_sub_ratio">
                                    <span class="input-group-addon">%</span>
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">起征点</span>
                                    <input type="number" class="form-control threshold_tax">
                                </div>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-md-6">

                                <div class="input-group m-b">
                                    <span class="input-group-addon">薪资服务费</span>
                                    <input type="number" class="form-control brokerage">
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="btn_list">

                    <div class="btn btn-primary btn_save">
                        保存
                    </div>

                    <%--<div class="btn btn-danger btn_del">--%>
                    <%--删除规则--%>
                    <%--</div>--%>

                </div>

            </div>

        </div>

    </div>

</div>

<div class="modal fade customer_operate_record_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">跟进记录</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">记录内容：</div>
                    <div class="col-xs-9 txtInfo">
                        <textarea class="form-control record_content"></textarea>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="customer_record.recordAdd()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade account_recharge_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">账户充值</h4>
            </div>
            <div class="modal-body">

                <%--<div class="row" style="margin-bottom: 20px;">--%>
                <%--<div class="col-xs-3 txt">日期：</div>--%>
                <%--<div class="col-xs-9 txtInfo">--%>
                <%--<input class="form-control layer-date recharge_date" id="recharge_date"--%>
                <%--placeholder="YYYY-MM-DD" style="max-width: inherit;">--%>
                <%--</div>--%>
                <%--</div>--%>

                <div class="row">
                    <div class="col-xs-3 txt">金额：</div>
                    <div class="col-xs-9 txtInfo">
                        <input type="number" class="form-control recharge_money" max="99999">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="customer_manage.accountRecharge()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade contract_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">上传合同</h4>
            </div>
            <div class="modal-body">

                <div class="dropzone"></div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="customer_manage.contractUpload()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div id="tax_gear_row" class="row item" hidden>

    <div class="col-md-5">
        <div class="input-group">
            <span class="input-group-addon">计税档 >=</span>
            <input type="number" class="form-control tax_gear">
        </div>
    </div>

    <div class="col-md-5">
        <div class="input-group">
            <span class="input-group-addon">税率</span>
            <input type="number" class="form-control tax_rate">
            <span class="input-group-addon">%</span>
        </div>
    </div>

    <div class="col-md-2">
        <a class="btn btn-danger delete_tax_rate ">删除</a>
    </div>

</div>

