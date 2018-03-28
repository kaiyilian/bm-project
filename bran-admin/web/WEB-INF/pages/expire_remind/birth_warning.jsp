<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/16
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<%--<link href="<%=contextPath%>/css/bran/expire_remind/contract_expire_manage.css" rel="stylesheet">--%>
<script src="<%=contextPath%>/js/bran/expire_remind/birth_warning.js"></script>

<div class="birth_warning_container container">

    <div class="head border-bottom">
        <div class="txt">生日提醒</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_birth_warning"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_deal" onclick="birth_warning.dispose()">
                生日提醒
            </div>

        </div>

    </div>

</div>