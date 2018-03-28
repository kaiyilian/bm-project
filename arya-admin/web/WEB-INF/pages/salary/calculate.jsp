<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/3/24
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/table_page_header.jsp" %>
<link href="<%=contextPath%>/css/style.min.css" rel="stylesheet">

<script src="<%=contextPath%>/js/jquery.form.js"></script>
<%--月份选择插件--%>
<link href="<%=contextPath%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="<%=contextPath%>/js/plugins/cropper/cropper.min.js"></script>
<!-- 文件选择Prettyfile -->
<script src="<%=contextPath%>/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>

<link href="<%=contextPath%>/css/organizationTree/tree.css" rel="stylesheet"/>
<link href="<%=contextPath%>/css/salary/salary_calculate.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/salary/salary_calculate.js"></script>

<div class="container salary_calculate_container">
    <%--row animated fadeIn--%>
    <%--<div class="col-sm-2">--%>
    <%--<div class="aryaZtreeContainer">--%>
    <%--<div class="ibox-title">--%>
    <%--<h5>集团部门列表</h5>--%>
    <%--</div>--%>
    <%--<div id="salary_group_chooser" class="ibox-content profile-content">--%>
    <%--<div id="salary_group_tree_hud"></div>--%>
    <%--<ul id="salary_group_tree" class="ztree"></ul>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>企业列表</h5>
        </div>

        <div class="ztree_search_container">
            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div class="ztreeContainer">
            <ul class="ztree" id="corp_group_attendance_tree"></ul>
        </div>

    </div>

    <div class="col-sm-10 corp_content">

        <div class="ibox float-e-margins">

            <div class="search_or_import_container">

                <div class="ibox-title">
                    <h5>薪资导入或查询</h5>
                </div>
                <div class="ibox-content profile-content">
                    <form id="upload_form" enctype="multipart/form-data">
                        <input name="page" type="hidden" class="form-control page" value="1">
                        <input name="page_size" type="hidden" class="form-control" value="10">

                        <div class="row">

                            <div class="col-lg-3 col-xs-4 item">
                                <div id="file-pretty">
                                    <div class="form-group" style="margin:0;">
                                        <input id="pretty_file" name="salary_file"
                                               type="file" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-xs-6 item">

                                <a id="calculate_btn" class="btn btn-primary"
                                   onclick="salaryCalculate.salaryCalc()">
                                    计算
                                </a>

                                <a id="import_btn" class="btn btn-primary btn_import"
                                   onclick="salaryCalculate.showConfirmImportSalary()">
                                    导入
                                </a>

                                <a class="btn btn-primary" onclick="salaryCalculate.templateDown()">
                                    下载模板
                                </a>

                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-3 col-xs-4 input-group item">
                                <div class="input-group-addon">组织：</div>
                                <div id="organization_name" class=" form-control"></div>
                                <input type="text" class="organization_type" hidden>
                            </div>

                            <div class="col-lg-3 col-xs-4 input-group item">
                                <label class="input-group-addon">结算周期：</label>
                                <select class="form-control m-b settlement_interval"
                                        name="settlement_interval">
                                    <option value="1">批次</option>
                                    <option value="2">月</option>
                                </select>
                            </div>

                            <div class="col-lg-3 col-xs-4 input-group item">
                                <div class="input-group-addon">月份：</div>
                                <div id="date_picker">
                                    <input id="calculate_month" name="year_month"
                                           type="text" class="form-control year_month" readonly>
                                </div>
                            </div>

                            <div class="col-lg-3 col-xs-4 input-group item batch_list_container">
                                <div class="input-group-addon">批次：</div>
                                <select name="week" id="week_select" class="form-control m-b batch_list"></select>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-4 col-xs-4 input-group item">
                                <div class="input-group-addon">关键字：</div>
                                <input name="key_word" type="text" placeholder="姓名/身份证/税前薪资"
                                       class="form-control key_word">
                            </div>

                            <div class="col-lg-4 col-xs-4 item btn_list">

                                <div id="query_but" class="btn btn-primary btn_search"
                                     onclick="salaryCalculate.btnSearchClick()">
                                    查询
                                </div>

                            </div>

                        </div>

                    </form>
                </div>

            </div>

            <div class="salary_calc_container">

                <div class="head">

                    <div class="title">薪资计算结果</div>

                    <a style="margin-left: 40px" data-toggle="modal"
                       data-target="#status_modal">
                        查看操作反馈
                    </a>

                    <a style="float: right;" onclick="salaryCalculate.salary_calc_prompt()">
                        提示信息
                    </a>

                </div>

                <div class="content">

                    <div class="table_container">

                        <table class="table table-striped table-bordered table-hover dataTable">
                            <thead>
                            <tr>
                                <td class="choose_item" onclick="salaryCalculate.chooseCurAll()">
                                    <img src="img/icon_Unchecked.png" data-html="true"
                                         data-toggle="tooltip" data-placement="right" title=""
                                         data-original-title="<p style='width:80px;'>选择当前页所有选项</p>">
                                </td>
                                <td>城市</td>
                                <td>姓名</td>
                                <td>身份证</td>
                                <td>手机</td>
                                <td>账号</td>
                                <td>税前薪资</td>
                                <td class="">个税处理费</td>
                                <td class="">个税服务费</td>
                                <td>税后</td>
                                <td>薪资服务费</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>


                            </tbody>
                        </table>

                    </div>

                    <div class="pager_container">
                        <%--<ul class="pagenation" style="float:right;"></ul>--%>
                    </div>

                </div>

                <div class="foot">

                    <%--<div class="choose_item">--%>
                    <%--<img src="img/icon_Unchecked.png">--%>
                    <%--全选--%>
                    <%--</div>--%>

                    <div class="btn_list">

                        <div class="btn btn-default btn_del"
                             onclick="salaryCalculate.SalaryDetailListDel()">
                            删除
                        </div>

                        <div class="btn btn-primary btn_export"
                             onclick="salaryCalculate.salaryCalcResultExport()">
                            导出
                        </div>

                    </div>

                </div>

            </div>

            <div class="salary_statistic_container">

                <div class="head">

                    <div class="title">薪资统计结果</div>

                </div>

                <div class="content">

                    <div class="table_container">

                        <table class="table table-striped table-bordered table-hover dataTable">
                            <thead>
                            <tr>
                                <td>城市</td>
                                <td>公司</td>
                                <td>部门</td>
                                <td>人数</td>
                                <td>税前薪资总额</td>
                                <td class="">个税处理费总额</td>
                                <td class="">个税服务费总额</td>
                                <td>税后薪资总额</td>
                                <td>薪资服务费</td>
                            </tr>
                            </thead>
                            <tbody>


                            </tbody>
                        </table>

                    </div>

                    <div class="pager_container">
                        <%--<ul class="pagenation" style="float:right;"></ul>--%>
                    </div>

                </div>

                <div class="foot">

                    <div class="btn_list">

                        <div class="btn btn-primary btn_export"
                             onclick="salaryCalculate.salaryStatisticsResultExport()">
                            导出
                        </div>

                    </div>

                </div>

            </div>

            <div class="salary_rule_container">

                <div class="ibox-title">
                    <h5><span id="group_or_department"></span>薪资计算规则</h5>
                </div>
                <div class="ibox-content profile-content calc_rule_container">

                    <div class="row">
                        <div id="show_salary_rule_btn" class="btn btn-primary hide btn_custom_calc_rule"
                             onclick="salaryCalculate.showCustomCalcRule()">
                            自定义计算规则
                        </div>


                        <div id="salary_calculate_op_hud2" class="col-md-1"
                             style="display: inline">
                        </div>

                        <div class="btn btn-primary hide btn_general_calc_rule"
                             onclick="salaryCalculate.showGeneralCalcRule()">
                            标准计算规则
                        </div>
                    </div>

                    <div class="col-md-12 hr-line-dashed"></div>

                    <div class="row">

                        <div class="col-md-7 hide custom_calc_rule_container"
                             id="calculate_rule">

                            <div id="salary_add_tax_gear" class="btn btn-primary hide btn_add_tax_rate"
                                 onclick="salaryCalculate.addTaxRate()">
                                新增计税档
                            </div>

                            <div id="tax_gears" class="tax_gears_list">

                            </div>

                            <div class="col-md-12 hr-line-dashed"></div>

                            <div class="row">

                                <div class="col-md-6">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">个税服务费率</span>
                                        <input id="service_charge_tax_rate" type="number" class="form-control"
                                               name="service_charge_tax_rate">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-md-6">
                                    <div class="input-group m-b">
                                        <span class="input-group-addon">薪资服务费率</span>
                                        <input id="brokerage_rate" type="number" class="form-control"
                                               name="brokerage_rate">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-md-12">

                                    <div id="salary_edit_rule" class="btn btn-primary btn_edit"
                                         onclick="salaryCalculate.editCustomCalcRule()">
                                        编辑
                                    </div>

                                    <div id="salary_cancel_edit_rule" class="btn btn-warning hide btn_cancel"
                                         onclick="salaryCalculate.cancelEditCustomCalcRule()">
                                        取消
                                    </div>

                                    <div id="salary_save_rule" class="btn btn-primary hide btn_save"
                                         onclick="salaryCalculate.saveCustomCalcRule()"
                                         disabled="disabled">
                                        保存
                                    </div>

                                    <div id="salary_delete_rule" class="btn btn-danger hide btn_del"
                                         onclick="salaryCalculate.deleteCustomCalcRule()">
                                        删除规则
                                    </div>

                                </div>

                            </div>

                        </div>

                        <div class="col-md-7 hide general_calc_rule_container">

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

                            <div class="row">
                                <div class="col-md-12">

                                    <div class="btn btn-primary btn_edit"
                                         onclick="salaryCalculate.editGeneralCalcRule()">
                                        编辑
                                    </div>

                                    <div class="btn btn-warning hide btn_cancel"
                                         onclick="salaryCalculate.cancelEditGeneralCalcRule()">
                                        取消
                                    </div>

                                    <div class="btn btn-primary hide btn_save"
                                         onclick="salaryCalculate.saveGeneralCalcRule()" disabled="disabled">
                                        保存
                                    </div>

                                    <div class="btn btn-danger hide btn_del"
                                         onclick="salaryCalculate.deleteGeneralCalcRule()">
                                        删除规则
                                    </div>

                                </div>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<%--记录窗口--%>
<div class="modal inmodal operate_status_modal" id="status_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title"><span id="feed_back_title"></span>反馈</h4>
            </div>
            <div class="modal-body">
				<textarea id="operate_status">

				</textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div id="tax_gear_row" class="row" hidden>

    <div class="col-md-5">
        <div class="input-group m-b">
            <span class="input-group-addon">计税档 >=</span>
            <input type="number" class="form-control tax_gear" readonly>
        </div>
    </div>

    <div class="col-md-5">
        <div class="input-group m-b">
            <span class="input-group-addon">税率</span>
            <input type="number" class="form-control tax_rate" readonly>
            <span class="input-group-addon">%</span>
        </div>
    </div>

    <div class="col-md-2">
        <a class="btn btn-danger delete_tax_rate hide">删除</a>
    </div>

</div>

<%--编辑用户信息窗口--%>
<div class="modal inmodal user_info_modal" id="user_info_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-md-4">
                        <div class="input-group m-b">
                            <span class="input-group-addon">姓名</span>
                            <input type="text" class="form-control user_name">
                            <%--<input id="salary_id" type="text" class="form-control" style="display: none;">--%>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-7">
                        <div class="input-group m-b">
                            <span class="input-group-addon">手机号</span>
                            <input type="number" class="form-control user_phone_no" readonly>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group m-b">
                            <span class="input-group-addon">身份证号码</span>
                            <input type="text" class="form-control user_idcard_no">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group m-b">
                            <span class="input-group-addon">银行账号</span>
                            <input type="text" class="form-control user_bank_account">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="salaryCalculate.userInfoModify()">提交</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
