<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/26
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/electronic_payroll/electronic_payroll.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/electronic_payroll/electronic_payroll.js"></script>

<div class="electronic_payroll_container container">

    <div class="head border-bottom">
        <div class="txt">用户管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">关键字：</span>
                <input class="form-control searchCondition" placeholder="企业名称、用户姓名">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="electronic_payroll.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">

            <%--<table class="table table-striped table-bordered table-hover dataTable">--%>

            <%--<thead>--%>
            <%--<tr>--%>
            <%--<td>手机号</td>--%>
            <%--<td>企业名称</td>--%>
            <%--<td>联系人姓名</td>--%>
            <%--<td>首次使用日期</td>--%>
            <%--</tr>--%>
            <%--</thead>--%>

            <%--<tbody>--%>

            <%--<tr class="item" data-id="1">--%>
            <%--<td class="contact_phone">13115100011</td>--%>
            <%--<td class="corp_name">aa公司</td>--%>
            <%--<td class="contact_name">名字</td>--%>
            <%--<td class="first_login_time">已处理</td>--%>
            <%--</tr>--%>


            <%--</tbody>--%>
            <%--</table>--%>

            <table id="tb_el_payroll"></table>

        </div>

    </div>

    <%--<div class="pager_container">--%>
        <%--&lt;%&ndash;<ul class="pagenation" style="float:right;"></ul>&ndash;%&gt;--%>
    <%--</div>--%>

</div>
