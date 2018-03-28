<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/30
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/project_apply/project_create.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/project_apply/project_create.js"></script>


<div class="container project_create_container">

    <div class="head border-bottom">
        <div class="txt">新增 - 立项申请</div>
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
                <div class="col-xs-3">薪资总额：</div>
                <div class="col-xs-9">
                    <input class="form-control salary_total" placeholder="请输入薪资总额"
                           maxlength="10">
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
                <div class="col-xs-3">客户联系人：</div>
                <div class="col-xs-9">
                    <input class="form-control contact_name" placeholder="请输入客户联系人">
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
                           maxlength="10">
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
                    <input class="form-control email" type="email" placeholder="请输入邮箱">
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-xs-6">
                <div class="col-xs-3">申请日期：</div>
                <div class="col-xs-9">
                    <input class="form-control layer-date apply_date" id="apply_date"
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
                <div class="col-xs-3">地址：</div>
                <div class="col-xs-9">
                    <input class="form-control address" placeholder="请输入地址">
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

            <div class="col-xs-6">
                <div class="col-xs-3">操作记录：</div>
                <div class="col-xs-9">
                    <textarea class="form-control operate_record" placeholder=""></textarea>
                </div>
            </div>

        </div>

        <div class="btn btn-primary btn_save" onclick="project_create.projectSave()">保存</div>

    </div>

</div>

