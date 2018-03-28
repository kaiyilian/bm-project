<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/13
  Time: 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/ledger_manage/ledger_summary.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/ledger_manage/ledger_summary.js"></script>

<div class="container ledger_summary_container">

    <div class="head border-bottom">
        <div class="txt">台账汇总</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-4 item">
                <span class="input-group-addon">起始日期:</span>
                <input class="form-control layer-date beginTime" id="ledger_beginTime" placeholder="YYYY-MM-DD">
            </div>

            <div class="input-group col-xs-4 item">
                <span class="input-group-addon">截止日期:</span>
                <input class="form-control layer-date endTime" id="ledger_endTime" placeholder="YYYY-MM-DD">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="ledger_summary.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="ledger_summary"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-primary btn-sm btn_down" onclick="ledger_summary.ledgerSummaryExport()">导出</div>

        </div>

    </div>

</div>