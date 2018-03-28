<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/18
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/schedule_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/schedule_manage.js"></script>

<div class="container schedule_manage_container">

    <div class="head border-bottom">
        <i class="icon icon-schedule_manage"></i>
        <div class="txt">班次管理</div>

        <div class="schedule_add" onclick="schedule_manage.goScheduleDetailPage()">
            <i class="glyphicon glyphicon-plus"></i>
            <div>新增班次</div>
        </div>

    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_schedule_manage"></table>
        </div>

    </div>

</div>

<div class="modal fade rest_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">休息时段</h4>
            </div>
            <div class="modal-body">

                <ul>
                    <%--<li> 08：00至12：30休息</li>--%>
                    <%--<li>01：00（次日）至09：30（次日）休息</li>--%>
                </ul>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-orange" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade absenteeism_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">旷工设置</h4>
            </div>
            <div class="modal-body">

                <ul>
                    <%--<li>迟到旷工：迟到50分钟记为旷工0.5天</li>--%>
                    <%--<li>早退旷工：未开启</li>--%>
                    <%--<li>缺卡旷工：上班缺卡记为旷工0.5天，下班缺卡记为旷工0.5天，　一天缺卡未开启</li>--%>
                </ul>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-orange" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade overTime_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">加班设置</h4>
            </div>
            <div class="modal-body">

                <div class="txt">
                    <%--下班后60分钟开始计算加班，该60分钟计入加班时间，加班累计间隔30分钟--%>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-orange" data-dismiss="modal">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

