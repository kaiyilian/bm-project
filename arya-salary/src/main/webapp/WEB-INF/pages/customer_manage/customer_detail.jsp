<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/24
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/customer_manage/customer_detail.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/customer_manage/customer_detail.js"></script>

<div class="container customer_detail_container">

    <div class="head border-bottom">
        <div class="txt">客户管理 - 详情</div>
    </div>

    <div class="content">

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">销售人员：</div>
                <div class="col-xs-9">
                    <input class="form-control sales_man" placeholder="请输入销售人员姓名">
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">销售部门：</div>
                <div class="col-xs-9">
                    <input class="form-control sales_dept" placeholder="请输入销售部门">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">客户名称：</div>
                <div class="col-xs-9">
                    <input class="form-control customer_name" placeholder="请输入客户名称">
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">客户简称：</div>
                <div class="col-xs-9">
                    <input class="form-control customer_shortName" placeholder="请输入客户简称">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">薪资人数：</div>
                <div class="col-xs-9">
                    <input class="form-control salary_count" placeholder="请输入薪资人数"
                           onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="6">
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">申请日期：</div>
                <div class="col-xs-9">
                    <input class="form-control layer-date apply_date" placeholder="YYYY-MM-DD">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">发票类型：</div>
                <div class="col-xs-9">
                    <select class="form-control invoice_type">
                        <%--<option value="1">全额专票</option>--%>
                        <%--<option value="2">差额普票</option>--%>
                    </select>
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">薪资总额：</div>
                <div class="col-xs-9">
                    <input class="form-control salary_total" placeholder="请输入薪资总额"
                           maxlength="11">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">发票项目1：</div>
                <div class="col-xs-9">
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

            <div class="col-xs-6">
                <div class="col-xs-3">利润预算：</div>
                <div class="col-xs-9">
                    <input class="form-control profit_budget" placeholder="请输入利润预算"
                           maxlength="11">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">发票项目2：</div>
                <div class="col-xs-9">
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

            <div class="col-xs-6">
                <div class="col-xs-3">邮箱：</div>
                <div class="col-xs-9">
                    <input class="form-control email" placeholder="请输入邮箱">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">地址：</div>
                <div class="col-xs-9">
                    <input class="form-control address" placeholder="请输入地址">
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">客户联系人：</div>
                <div class="col-xs-9">
                    <input class="form-control contact_name" placeholder="请输入客户联系人">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">合同开始时间：</div>
                <div class="col-xs-9 txtInfo">
                    <input class="form-control layer-date customer_contract_begin" data-time=""
                           placeholder="YYYY-MM-DD">
                </div>
            </div>

            <div class="col-xs-6">
                <div class="col-xs-3">联系电话：</div>
                <div class="col-xs-9">
                    <input class="form-control contact_phone" placeholder="请输入联系电话"
                           onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">合同结束时间：</div>
                <div class="col-xs-9 txtInfo">
                    <input class="form-control layer-date customer_contract_end" data-time=""
                           placeholder="YYYY-MM-DD">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">运营方案：</div>
                <div class="col-xs-9">
                    <textarea class="form-control operation_plan" placeholder="请输入运营方案"></textarea>
                </div>
            </div>

        </div>

        <div class="btn_list">

            <div class="btn btn-primary btn_modify" onclick="customer_detail.customerModify()">编辑</div>
            <div class="btn btn-primary btn_save" onclick="customer_detail.customerSave()">保存</div>
            <div class="btn btn-default btn_cancel" onclick="customer_detail.customerCancel()">取消</div>

        </div>

        <div class="table_container">

            <div class="t_title">
                跟进记录
            </div>

            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td>序号</td>
                    <td>记录内容</td>
                    <td>记录日期</td>
                </tr>
                </thead>
                <tbody>

                <%--<tr class="item" data-id="">--%>
                <%--<td>1</td>--%>
                <%--<td class="record_content">--%>
                <%--记录详情记录详情记录详情记录详情记录详情记录详情记录详情--%>
                <%--记录详情记录详情记录详情记录详情记录详情记录详情记录详情--%>
                <%--</td>--%>
                <%--<td class="record_date">--%>
                <%--2017-06-21--%>
                <%--</td>--%>
                <%--</tr>--%>

                </tbody>
            </table>

            <div class="btn btn-primary btn_add" onclick="customer_detail.recordModalShow()">添加跟进记录</div>

        </div>

    </div>

    <div class="modal fade operate_record_modal" style="background-color:rgba(0,0,0,0.50);">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
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
                    <button type="button" class="btn btn-primary" onclick="customer_detail.recordAdd()">
                        确定
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

</div>

