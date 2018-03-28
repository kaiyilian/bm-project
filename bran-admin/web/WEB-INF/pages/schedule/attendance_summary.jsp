<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/21
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/attendance_summary.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/attendance_summary.js"></script>

<div class="container attendance_summary_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">出勤汇总</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group input-append date col-xs-3 item">
                <span class="input-group-addon">月份：</span>
                <span class="form-control month" data-time="">YYYY-MM</span>
                <span class="add-on" onclick="attendance_summary.clearMonth();">
                    <img src="image/icon_remove.png">
                </span>
            </div>

            <div class="input-group col-xs-3 item attendSet_container">
                <div class="input-group-addon">出勤周期：</div>
                <select class="form-control"></select>
            </div>

            <div class="input-group col-xs-3 item department_container">
                <div class="input-group-addon">部门：</div>
                <select class="form-control"></select>
            </div>

            <div class="input-group col-xs-3 item work_shift_container">
                <div class="input-group-addon">班组：</div>
                <select class="form-control"></select>
            </div>

            <div class="input-group col-xs-3 item pub_status">
                <div class="input-group-addon">发布状态：</div>
                <select class="form-control">
                    <option value="">全部</option>
                    <option value="0">未发布</option>
                    <option value="1">已发布</option>
                </select>
            </div>

            <div class="input-group col-xs-3 item user_status">
                <div class="input-group-addon">员工状态：</div>
                <select class="form-control">
                    <option value="1">在职</option>
                    <option value="0">离职</option>
                </select>
            </div>

            <div class="input-group col-xs-3 item confirm_status">
                <div class="input-group-addon">确认状态：</div>
                <select class="form-control">
                    <option value="">全部</option>
                    <option value="0">申诉中</option>
                    <option value="1">未确认</option>
                    <option value="2">已确认</option>
                </select>
            </div>

            <div class="input-group col-xs-6 item">
                <span class="input-group-addon">快速搜索：</span>
                <select data-placeholder="请选择员工姓名、工号或工段" multiple
                        class="chosen-select user_list">
                    <%--<option>1</option>--%>
                </select>
            </div>

            <div class="col-xs-3 item btn_list">

                <div class="btn btn-sm btn-orange"
                     onclick="attendance_summary.btnSearchClick();">
                    查询
                </div>

                <div class="btn btn-sm btn-orange btn_reset"
                     onclick="attendance_summary.resetParam()">
                    重置
                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_attendance_summary"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_release"
                 onclick="attendance_summary.attendanceListPublish()">
                发布考勤
            </div>

            <div class="btn btn-sm btn-success btn_export"
                 onclick="attendance_summary.attendanceListExport()">
                导出报表
            </div>


        </div>

    </div>

</div>

<div class="modal fade emp_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">
                    <%--请假--%>
                </h4>
            </div>
            <div class="modal-body">

                <%--<div class="row">--%>
                <%--<div class="col-xs-6">2016年2月16号</div>--%>
                <%--<div class="col-xs-6">请假8小时</div>--%>
                <%--</div>--%>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" data-dismiss="modal">确定</button>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

