<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/2/11
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<%--<link href="<%=contextPath%>/css/bran/expire_remind/entry_remind_manage.css" rel="stylesheet">--%>
<script src="<%=contextPath%>/js/bran/expire_remind/entry_remind_manage.js"></script>

<div class="entry_remind_container container">

    <div class="head border-bottom">
        <div class="txt">入职提醒</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_entry_remind"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_agree"
                 onclick="entry_remind_manage.empRemindMore()">
                通知
            </div>

            <div class="btn btn-sm btn-success btn_agree"
                 onclick="entry_remind_manage.empExport()">
                导出
            </div>

        </div>

    </div>

</div>