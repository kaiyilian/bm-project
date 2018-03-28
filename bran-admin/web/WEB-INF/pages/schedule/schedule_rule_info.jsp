<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/12
  Time: 8:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/schedule_rule_info.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/schedule_rule_info.js"></script>

<div class="container schedule_rule_info_container" onclick="schedule_rule_info.hideScheduleList()">

    <div class="head border-bottom">
        <i class="icon icon-schedule_rule"></i>
        <div class="txt">班组信息</div>
    </div>

    <div class="content">

        <div class="row border-bottom">

            <div class="col-xs-2">
                排班名称：
            </div>

            <div class="col-xs-3 schedule_name">

                <input type="text" placeholder="请输入排班名称" class="form-control">

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2">
                排班时间：
            </div>

            <div class="col-xs-10 schedule_rule_time">

                <div class="input-group col-xs-6">
                    <input class="form-control layer-date schedule_rule_beginTime" id="schedule_rule_beginTime"
                           placeholder="YYYY-MM-DD">
                    <span class="input-group-addon">至</span>
                    <input class="form-control layer-date schedule_rule_endTime" id="schedule_rule_endTime"
                           placeholder="YYYY-MM-DD">
                </div>

                <div class="col-xs-6 is_loop">
                    <img src="image/UnChoose.png"/>
                    <span>一直循环</span>
                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2">
                考勤员工：
            </div>

            <div class="col-xs-10 work_shift">

                <div class="btn btn-success btn-sm btn_work_shift_add"
                     onclick="schedule_rule_info.workShiftModalShow()">按班组
                </div>

                <div class="work_shift_list">

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2">
                排班周期：
            </div>

            <div class="col-xs-3 schedule_rule_cycle">

                <div class="input-group">
                    <select class="form-control" onchange="schedule_rule_info.initScheduleRuleLaws()">
                        <%--<option>1</option>--%>
                    </select>
                    <div class="input-group-addon">天</div>
                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2">
                排班规律：
            </div>

            <div class="col-xs-10 schedule_rule_laws">

                <%--<div class="item">--%>

                <%--<ul class="schedule_list">--%>
                <%--<li class="schedule_item" data-id="1" style="background-color: green;">1</li>--%>
                <%--<li class="schedule_item" data-id="1" style="background-color: red;">--%>
                <%--中班--%>
                <%--<i class="glyphicon glyphicon-ok"></i>--%>
                <%--</li>--%>
                <%--</ul>--%>

                <%--</div>--%>


            </div>

        </div>

        <div class="row btn_list">

            <div class="col-xs-2"></div>

            <%--<div class="btn btn-default btn_del" onclick="schedule_rule.scheduleRuleDel()">删除</div>--%>
            <div class="col-xs-10">
                <div class="btn btn-success btn_save" onclick="schedule_rule_info.scheduleRuleSave()">保存</div>
            </div>

        </div>

    </div>

</div>

<div class="modal fade work_shift_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">考勤员工</h4>
            </div>
            <div class="modal-body">

                <div class="row">

                    <div class="col-xs-4 work_shift_container">

                        <div class="txt">班组</div>

                        <div class="work_shift_list">

                            <%--<div class="item">--%>
                            <%--<img src="image/UnChoose.png"/>--%>
                            <%--<span>1租（132人）</span>--%>
                            <%--</div>--%>

                        </div>

                    </div>

                    <div class="col-xs-8 work_shift_user_container">

                        <div class="txt">成员列表</div>

                        <div class="table_container">
                            <table id="tb_work_shift_user"></table>
                        </div>

                    </div>

                </div>

            </div>
            <div class="modal-footer">

                <div class="choose_container" onclick="schedule_rule_info.workShiftChooseAll()">
                    <img src="image/UnChoose.png"/>
                    全选
                </div>

                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-orange" onclick="schedule_rule_info.workShiftListSave()">保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

