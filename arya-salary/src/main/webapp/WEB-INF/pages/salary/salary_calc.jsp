<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/28
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--<link href="<%=contextPath%>/css/style.min.css" rel="stylesheet">--%>

<link href="<%=contextPath%>/css/arya/salary/salary_calculate.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/salary/salary_calculate.js"></script>

<div class="row animated fadeIn">

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="title">
            客户列表
        </div>

        <div class="search">
            <input type="text" id="salary_customer_search" class="form-control" placeholder="请输入客户名称"
                   onkeyup="salary_calc_manage.enterSearch(event)">
            <i class="glyphicon glyphicon-search" onclick="salary_calc_manage.organizationTreeList()"></i>
        </div>

        <div id="salary_group_chooser" class="tree_content">
            <ul id="salary_group_tree" class="ztree"></ul>
        </div>

    </div>

    <div class="col-xs-10 container salary_info_container">

        <div class="content">

            <div class="corp_name">
                薪资导入或查询
            </div>

            <div class="upload_container">

                <span class="txt">选择本地文件</span>

                <span class="file_path"></span>

                <div class="btn_list">

                    <div class="btn btn-sm btn-primary btn_upload" onclick="salary_import.ChooseFileClick()">
                        选择文件
                    </div>

                    <div class="btn btn-sm btn-default btn_calc" onclick="salary_import.salaryPreviewClick()">
                        预览
                    </div>

                    <div class="btn btn-sm btn-default btn_import" onclick="salary_import.salaryImport()">
                        导入
                    </div>

                    <a class="btn_down" onclick="salary_import.templateDown()">下载模板</a>

                </div>

            </div>

            <div class="search_container">

                <div class="row" style="margin:0;">

                    <div class="col-lg-3 col-xs-4 input-group item">
                        <label class="input-group-addon">结算周期：</label>
                        <select class="form-control m-b settlement_interval">
                            <option value="1">月</option>
                            <option value="2">批次</option>
                        </select>
                    </div>

                    <div class="col-lg-3 col-xs-4 input-group item">
                        <div class="input-group-addon">月份：</div>
                        <span class="form-control year_month" readonly></span>
                    </div>

                    <div class="col-lg-3 col-xs-4 input-group item batch_list_container" style="display: none;">
                        <div class="input-group-addon">批次：</div>
                        <select class="form-control m-b batch_list"></select>
                    </div>

                </div>

                <div class="col-lg-4 col-xs-4 input-group item">
                    <div class="input-group-addon">关键字：</div>
                    <input name="key_word" type="text" placeholder="姓名/身份证"
                           class="form-control key_word">
                </div>

                <div class="col-lg-4 col-xs-4 item btn_list">

                    <div class="btn btn-sm btn-primary btn_search" onclick="salary_calc_manage.btnSearchClick()">
                        查询
                    </div>

                </div>

            </div>

            <div class="salary_calc_container">

                <div class="head">

                    <div class="title">薪资计算结果</div>

                    <a style="margin-left: 40px"
                       onclick="$('.operate_status_modal').modal('show')">
                        查看操作反馈
                    </a>

                    <%--<a style="float: right;" onclick="salary_calc_result.salary_calc_prompt()">--%>
                    <%--提示信息--%>
                    <%--</a>--%>

                </div>

                <div class="content">

                    <div class="table_container">

                        <table class="table table-striped table-bordered table-hover dataTable">
                            <thead>
                            <tr>
                                <td class="choose_item" onclick="salary_calc_result.chooseCurAll()">
                                    <img src="img/icon_Unchecked.png" data-html="true"
                                         data-toggle="tooltip" data-placement="right" title=""
                                         data-original-title="<p style='width:80px;'>选择当前页所有选项</p>">
                                </td>
                                <td>城市</td>
                                <td>姓名</td>
                                <td>身份证</td>
                                <td>手机</td>
                                <td>账号</td>
                                <td>开户行</td>
                                <td>税前薪资</td>
                                <td>个税处理费</td>
                                <td>税后薪资</td>
                                <td>薪资服务费</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>

                            <%--<tr>--%>
                            <%--<td class="choose_item" onclick="salary_calc_result.chooseItem(this)">--%>
                            <%--<img src="img/icon_Unchecked.png">--%>
                            <%--</td>--%>
                            <%--<td>城市</td>--%>
                            <%--<td>姓名</td>--%>
                            <%--<td>身份证</td>--%>
                            <%--<td>手机</td>--%>
                            <%--<td>账号</td>--%>
                            <%--<td>税前薪资</td>--%>
                            <%--<td class="">个税处理费</td>--%>
                            <%--<td class="">个税服务费</td>--%>
                            <%--<td>税后</td>--%>
                            <%--<td>薪资服务费</td>--%>
                            <%--<td>操作</td>--%>
                            <%--</tr>--%>

                            </tbody>
                        </table>

                    </div>

                    <div class="pager_container">
                        <%--<ul class="pagenation" style="float:right;"></ul>--%>
                    </div>

                </div>

                <div class="foot">

                    <div class="btn_list">

                        <div class="btn btn-sm btn-danger btn_del"
                             onclick="salary_calc_result.SalaryDetailListDel()">
                            删除
                        </div>

                        <div class="btn btn-sm btn-primary btn_export"
                             onclick="salary_calc_result.salaryCalcResultExport()">
                            导出
                        </div>

                    </div>

                </div>

            </div>

            <div class="salary_statistic_container">

                <div class="head">

                    <div class="title">薪资统计结果</div>
                    <div class="btn btn-sm btn-primary btn_ledger" onclick="salary_statistics_result.goLedgerPage();">
                        台账
                    </div>

                </div>

                <div class="content">

                    <div class="table_container">

                        <table class="table table-striped table-bordered table-hover dataTable">
                            <thead>
                            <tr>
                                <td>城市</td>
                                <td>公司</td>
                                <td>人数</td>
                                <td>税前薪资总额</td>
                                <td>个税处理费总额</td>
                                <%--<td class="">个税服务费总额</td>--%>
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

                        <div class="btn btn-sm btn-primary btn_export"
                             onclick="salary_statistics_result.salaryStatisticsResultExport()">
                            导出
                        </div>

                        <select class="export_type">
                            <option value="2">导出薪资统计结果</option>
                            <option value="3">导出开票申请</option>
                            <option value="4">导出发票回执单</option>
                        </select>

                        <div class="btn btn-sm btn-primary btn_deduct_sure"
                             onclick="salary_statistics_result.salaryDeduct();">
                            确认扣款
                        </div>

                    </div>

                </div>

            </div>

        </div>

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
                            <input type="number" class="form-control user_phone_no" readonly maxlength="11">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group m-b">
                            <span class="input-group-addon">身份证号码</span>
                            <input type="text" class="form-control user_idCard_no" maxlength="18">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group m-b">
                            <span class="input-group-addon">银行账号</span>
                            <input type="text" class="form-control user_bank_account" maxlength="20">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8">
                        <div class="input-group m-b">
                            <span class="input-group-addon">开户行</span>
                            <input type="text" class="form-control user_bank_address" maxlength="40">
                        </div>
                    </div>
                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="salary_calc_result.userInfoModify()">提交</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade operate_status_modal" id="operate_status_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">操作反馈</h4>
            </div>
            <div class="modal-body">

                <div class="table_container">

                    <table class="table table-striped table-bordered table-hover dataTable">
                        <thead>
                        <tr>
                            <td style="width:50px;">序号</td>
                            <td style="width:70px;">错误行数</td>
                            <td>内容</td>
                        </tr>
                        </thead>

                        <tbody>

                        <%--<tr>--%>

                        <%--<td>1</td>--%>
                        <%--<td>--%>
                        <%--薪资服务费薪资服务费薪资服务费薪资服务费薪资服务费--%>
                        <%--薪资服务费薪资服务费薪资服务费薪资服务费薪资服务费--%>
                        <%--</td>--%>

                        <%--</tr>--%>

                        <%--<tr>--%>

                        <%--<td>1</td>--%>
                        <%--<td>--%>
                        <%--薪资服务费薪资服务费薪资服务费薪资服务费薪资服务费--%>
                        <%--薪资服务费薪资服务费薪资服务费薪资服务费薪资服务费--%>
                        <%--</td>--%>

                        <%--</tr>--%>

                        </tbody>

                    </table>

                </div>

                <div class="row">

                    本次操作
                    总共 <span class="total_count">0</span> 条，
                    成功 <span class="success_count">0</span> 条，
                    失败 <span class="fail_count">0</span> 条

                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <%--<button type="button" class="btn btn-primary">确定</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

