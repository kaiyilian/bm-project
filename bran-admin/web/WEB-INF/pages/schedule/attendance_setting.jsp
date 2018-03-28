<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/16
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/attendance_setting.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/attendance_cycle.js"></script>
<script src="<%=contextPath%>/js/bran/schedule/attendance_clock.js"></script>
<script src="<%=contextPath%>/js/bran/schedule/attendance_setting.js"></script>


<div class="container attendance_setting_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">出勤配置</div>
    </div>

    <div class="content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs nav_temp" role="tablist">

            <li role="presentation" class="">

                <a href="#attendance_cycle" role="tab" data-toggle="tab" data-href="attendance_cycle">
                    出勤周期
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#holiday_set" role="tab" data-toggle="tab" data-href="holiday_set">
                    假期设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#overTime_set" role="tab" data-toggle="tab" data-href="overTime_set">
                    加班设置
                </a>

            </li>


            <li role="presentation" class="">

                <a href="#attendance_clock" role="tab" data-toggle="tab" data-href="attendance_clock">
                    手机考勤设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#attendance_machine" role="tab" data-toggle="tab" data-href="attendance_machine">
                    考勤机配置
                </a>

            </li>

        </ul>

        <!-- Tab panes -->
        <div class="tab-content">

            <div role="tabpanel" id="attendance_cycle" class="tab-pane fade in">

                <div class="import_container">

                    <div class="item col-xs-2 bind_manage">
                        <i class="icon icon-user"></i>
                        <div class="txt" onclick="attendance_cycle.bindManagerModalShow()">绑定管理员</div>
                    </div>

                </div>

                <div class="container attendance_cycle_container">

                    <div class="head border-bottom">
                        <i class="icon icon-emp"></i>
                        <div class="txt">出勤周期</div>
                    </div>

                    <div class="content">

                        <div class="search_container">

                            <div class="row">

                                <div class="input-group col-xs-3 item workShift_container">
                                    <div class="input-group-addon">班组：</div>
                                    <select class="form-control"></select>
                                </div>

                                <div class="col-xs-3 item btn_list">

                                    <div class="btn btn-sm btn-orange btn_search"
                                         onclick="attendance_cycle.btnSearchClick()">
                                        查询
                                    </div>

                                </div>

                            </div>

                        </div>

                        <div class="table_container">

                            <table id="tb_attendance_cycle"></table>

                        </div>

                    </div>

                    <div class="foot">

                        <div class="btn_list">

                            <div class="btn btn-sm btn-success btn_add"
                                 onclick="attendance_cycle.attendSetAddModalShow()">
                                新增
                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <div role="tabpanel" id="holiday_set" class="tab-pane fade">

                <div class="row holiday_type_container">

                    <div class="col-xs-2" style="text-align: center;font-size: 14px;">
                        假期类型：
                    </div>

                    <div class="col-xs-9">
                        <div class="btn btn-sm btn-default btn_all" onclick="holiday_set.chooseAll()">
                            全部
                        </div>
                    </div>

                    <div class="col-xs-9 col-xs-offset-2 holiday_type_list">
                        <%--<div class="btn btn-sm btn-default">班组1</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                    </div>

                    <div class="col-xs-9 col-xs-offset-2" style="margin-top: 25px;">
                        <div class="btn btn-sm btn-success btn_save" onclick="holiday_set.holidaySave()">
                            保存
                        </div>

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true" data-toggle="tooltip" data-placement="right" title=""
                             data-original-title="<p'>员工手机审批申请请假将能选择这里了勾选的请假类型</p>">
                    </div>

                </div>

            </div>

            <div role="tabpanel" id="overTime_set" class="tab-pane fade">

                <div class="row overTime_type_container">

                    <div class="col-xs-2" style="text-align: center;font-size: 14px;">
                        加班类型：
                    </div>

                    <div class="col-xs-9">
                        <div class="btn btn-sm btn-default btn_all" onclick="overTime_set.chooseAll()">
                            全部
                        </div>
                    </div>

                    <div class="col-xs-9 col-xs-offset-2 overTime_type_list">
                        <%--<div class="btn btn-sm btn-default">班组1</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                    </div>

                    <div class="col-xs-9 col-xs-offset-2" style="margin-top: 25px;">

                        <div class="btn btn-sm btn-success btn_save" onclick="overTime_set.OverTimeSave()">
                            保存
                        </div>

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true" data-toggle="tooltip" data-placement="right" title=""
                             data-original-title="<p'>员工手机审批申请加班将能选择这里了勾选的加班类型</p>">

                    </div>

                </div>

            </div>

            <div role="tabpanel" id="attendance_clock" class="tab-pane fade">

                <div class="row clock_by_area">

                    <div class="c_txt f_left">

                        1.根据办公地点考勤(最多可添加30个地址)

                        <div class="btn btn-sm btn-success btn_add"
                             onclick="attendance_clock.clockAreaModalShow()">
                            新增考勤地点
                        </div>

                    </div>

                    <div class="table_container">

                        <table id="tb_clock_by_area"></table>

                    </div>

                </div>

                <div class="row clock_by_wifi">

                    <div class="c_txt">

                        2.通过考勤wifi打卡（最多可添加30个地址）

                        <div class="btn btn-sm btn-success btn_add"
                             onclick="attendance_clock.clockWiFiModalShow()">
                            新增考勤WiFi
                        </div>

                    </div>

                    <div class="table_container">

                        <table id="tb_clock_by_wifi"></table>

                    </div>

                </div>

            </div>

            <div role="tabpanel" id="attendance_machine" class="tab-pane fade">

                <div class="row attendance_machine_container">

                    <div class="table_container">
                        <table id="td_attendance_machine"></table>
                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<div class="modal fade attendance_cycle_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">出勤周期</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3">班组：</div>
                    <div class="col-xs-9 workShift_list">

                        <%--<div class="workShift_item active" data-id="1">班组1</div>--%>
                        <%--<div class="workShift_item" data-id="2">班组2</div>--%>
                        <%--<div class="workShift_item" data-id="3">班组3</div>--%>

                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">出勤起始时间：</div>
                    <div class="col-xs-9 attend_start">
                        <select class="form-control"></select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">出勤结束时间：</div>
                    <div class="col-xs-9 attend_end">
                        <select class="form-control"></select>
                    </div>
                    <div class="col-xs-offset-3 col-xs-9 is_next_month">
                        <img src="image/UnChoose.png">
                        次月
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" onclick="attendance_cycle.attendSetSave()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade bind_manager_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">绑定管理员</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3">管理员姓名：</div>
                    <div class="col-xs-9 manager_name">
                        <input type="text" class="form-control" maxlength="32"
                               placeholder="请输入管理员姓名">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">手机号：</div>
                    <div class="col-xs-9 manager_phone">
                        <input type="text" class="form-control" maxlength="11"
                               placeholder="请输入手机号"
                               onkeyup="this.value=this.value.replace(/\D/g,'')">
                    </div>
                    <div class="col-xs-offset-3 col-xs-9" style="line-height:44px;font-size: 12px;">
                        * 手机号用于接收员工申诉提示
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" onclick="attendance_cycle.bindManager()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade clock_area_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">考勤地址</h4>
            </div>
            <div class="modal-body">

                <div id="area-map">
                    <%--<div class="search_container"></div>--%>
                </div>

                <div class="row work_shift_container">
                    <div class="col-xs-3">班组：</div>
                    <div class="col-xs-9 ">
                        <div class="btn btn-sm btn-default btn_all"
                             onclick="clock_area.chooseWorkShiftAll()">
                            全部
                        </div>
                    </div>
                    <div class="col-xs-9 col-xs-offset-3 work_shift_list">
                        <%--<div class="btn btn-sm btn-default">班组1</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">有效范围：</div>
                    <div class="col-xs-9 effect_distance">
                        <select class="form-control">
                            <%--<option>200米</option>--%>
                            <%--<option>500米</option>--%>
                            <%--<option>1000米</option>--%>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">地址名称：</div>
                    <div class="col-xs-9 area_name">
                        <input type="text" class="form-control" maxlength="32"
                        <%--id="area_short_name"--%>
                               placeholder="请选择地址">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">详细地址：</div>
                    <div class="col-xs-9 area_detail_name">
                        <input type="text" class="form-control" maxlength="128"
                               placeholder="请输入详细地址">
                    </div>
                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" onclick="attendance_clock.clockAreaSave()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade clock_wifi_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加办公WiFi</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3">名称：</div>
                    <div class="col-xs-9 wifi_name">
                        <input type="text" class="form-control" maxlength="15"
                               placeholder="最多不超过15个字">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">MAC地址：</div>
                    <div class="col-xs-9 mac_name">
                        <input type="text" class="form-control" maxlength="17"
                               placeholder="例子：02:SC:D9:s1:ba:23">
                    </div>
                </div>

                <div class="row work_shift_container">
                    <div class="col-xs-3">班组：</div>
                    <div class="col-xs-9 ">
                        <div class="btn btn-sm btn-default btn_all"
                             onclick="clock_wifi.chooseWorkShiftAll()">
                            全部
                        </div>
                    </div>
                    <div class="col-xs-9 col-xs-offset-3 work_shift_list">
                        <%--<div class="btn btn-sm btn-default">班组1</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                    </div>
                </div>

                <div class="col-xs-12" style="color:#999;">
                    名称尽量保持与考勤WiFi名称一致，避免员工产生误解
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" onclick="attendance_clock.clockWiFiSave()">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade attendance_machine_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑考勤机</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3">设备名称：</div>
                    <div class="col-xs-9 machine_name">
                        <input type="text" class="form-control" maxlength="10"
                               placeholder="最多不超过10个字">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">设备编号：</div>
                    <div class="col-xs-9 machine_no" style="line-height: 34px;">
                        <%--134LMJK2354543DF32--%>
                    </div>
                </div>

                <div class="row work_shift_container">
                    <div class="col-xs-3">班组：</div>
                    <div class="col-xs-9 ">
                        <div class="btn btn-sm btn-default btn_all"
                             onclick="attendance_machine.chooseWorkShiftAll()">
                            全部
                        </div>
                    </div>
                    <div class="col-xs-9 col-xs-offset-3 work_shift_list">
                        <%--<div class="btn btn-sm btn-default">班组1</div>--%>
                        <%--<div class="btn btn-sm btn-default">班组2</div>--%>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-orange" onclick="attendance_machine.save()">保存</button>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
