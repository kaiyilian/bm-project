<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/11
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/schedule_rule.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/schedule_rule.js"></script>

<div class="container schedule_rule_container">

    <div class="head border-bottom">
        <i class="icon icon-schedule_rule"></i>
        <div class="txt">班组规律</div>

        <div class="schedule_rule_add" onclick="schedule_rule.goScheduleRuleInfoPage()">
            <i class="glyphicon glyphicon-plus"></i>
            <div>新增排班</div>
        </div>

    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_schedule_rule"></table>
            <%--<table id="tb"></table>--%>
        </div>

    </div>

</div>

<div class="modal fade schedule_rule_list_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">排班规律</h4>
            </div>
            <div class="modal-body">

                <%--<div class="row">--%>

                <%--<div class="txt">--%>
                <%--1-7天：--%>
                <%--</div>--%>

                <%--<div class="work_type_list">--%>
                <%--<div class="item" style="background-color: #0a6aa1">早</div>--%>
                <%--</div>--%>

                <%--</div>--%>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
