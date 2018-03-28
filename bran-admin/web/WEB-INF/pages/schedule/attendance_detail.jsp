<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/23
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<script src="<%=contextPath%>/js/bran/schedule/approval_type.js"></script>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/attendance_detail.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/attendance_detail.js"></script>

<div class="container attendance_detail_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">出勤明细</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group input-append date col-xs-3 item">
                <span class="input-group-addon">月份：</span>
                <input class="form-control month" data-time="">
                <%--<span class="form-control month" data-time="">YYYY-MM</span>--%>
                <span class="add-on" onclick="attendance_detail.clearMonth();">
                    <img src="image/icon_remove.png">
                </span>
            </div>

            <div class="input-group col-xs-3 item attendSet_container">
                <div class="input-group-addon">出勤周期：</div>
                <select class="form-control"></select>
            </div>

            <div class="input-group col-xs-3 item user_status">
                <div class="input-group-addon">员工状态：</div>
                <select class="form-control">
                    <option value="1">在职</option>
                    <option value="0">离职</option>
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
                     onclick="attendance_detail.btnSearchClick();">
                    查询
                </div>

                <div class="btn btn-sm btn-orange btn_reset"
                     onclick="attendance_detail.resetParam()">
                    重置
                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_attendance_detail"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_export"
                 onclick="attendance_detail.attendanceListExport()">
                导出报表
            </div>

        </div>

    </div>

</div>

<div class="modal fade attendance_detail_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改出勤</h4>
            </div>
            <div class="modal-body">

                <div class="base_info row border-bottom">

                    <div class="col-xs-3 item">
                        <div class="txt">出勤日期：</div>
                        <div class="txtInfo attendance_date">2018</div>
                    </div>

                    <div class="col-xs-3 item">
                        <div class="txt">姓名：</div>
                        <div class="txtInfo user_name">哈哈</div>
                    </div>

                    <div class="col-xs-3 item">
                        <div class="txt">班组：</div>
                        <div class="txtInfo work_shift">哈哈</div>
                    </div>

                    <div class="col-xs-3 item">
                        <div class="txt">部门：</div>
                        <div class="txtInfo department">哈哈</div>
                    </div>

                    <div class="col-xs-3 item">
                        <div class="txt">班次：</div>
                        <div class="txtInfo schedule">国安考勤白班</div>
                    </div>

                    <div class="col-xs-4 item">
                        <div class="txt">上下班：</div>
                        <div class="txtInfo work_time">08:00~18:00（次）</div>
                    </div>

                    <div class="col-xs-5 item">
                        <div class="txt">修改班次：</div>
                        <div class="txtInfo schedule_list">
                            <%--<select class="form-control">--%>
                            <%--<option>不修改</option>--%>
                            <%--</select>--%>
                        </div>

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg" data-html="true"
                             data-toggle="tooltip" data-placement="top" title="" style="margin-top: 9px;"
                             data-original-title="<p>用于当天无法及时排班的时候修改历史班次；修改班次后，
                             所有出勤将会重新计算，请注意打卡时间、请假记录、加班记录的冲突。</p>">

                    </div>


                </div>

                <div class="attendance_record_container row border-bottom">

                    <div class="txt">出勤记录：</div>

                    <div class="attendance_record">

                        <div class="item begin">

                            <div class="row">
                                <div class="txt">上班打卡：</div>
                                <div class="card_info">无记录（请假，无需打卡）</div>
                            </div>

                            <div class="row">

                                <div class="txt">修改：</div>

                                <div class="attend_status">
                                    <select class="form-control">
                                        <option value="0">不修改</option>
                                        <option value="1">上班打卡</option>
                                        <option value="2">上班缺卡</option>
                                    </select>
                                </div>

                                <div class="input-group attend_time">
                                    <div class="input-group-addon">上班：</div>
                                    <input class="form-control hour" type="number" max="23" min="0" value=""
                                           onblur="attendance_detail.checkHour(this)">
                                    <div class="input-group-addon">:</div>
                                    <input class="form-control minute" type="number" max="59" min="0" value=""
                                           onblur="attendance_detail.checkMinute(this)">
                                </div>

                                <div class="next_day begin_time_next_day">

                                    <div class="choose_item" onclick="attendance_detail.chooseNextDay(this)">
                                        <img src="image/icon_checkbox_unCheck.png">
                                    </div>
                                    次日

                                </div>

                                <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                                     data-html="true"
                                     data-toggle="tooltip"
                                     data-placement="top"
                                     title="<p>休息日上下班均有打卡时间，则员工出勤时间全部计入休息日加班时间。</p>"/>

                            </div>

                        </div>

                        <div class="item end">

                            <div class="row">
                                <div class="txt">下班打卡：</div>
                                <div class="card_info">无记录（请假，无需打卡）</div>
                            </div>

                            <div class="row">

                                <div class="txt">修改：</div>

                                <div class="attend_status">
                                    <select class="form-control">
                                        <option value="0">不修改</option>
                                        <option value="1">下班打卡</option>
                                        <option value="2">下班缺卡</option>
                                    </select>
                                </div>

                                <div class="input-group attend_time">
                                    <div class="input-group-addon">下班：</div>
                                    <input class="form-control hour" type="number" max="23" min="0" value=""
                                           onblur="attendance_detail.checkHour(this)">
                                    <div class="input-group-addon">:</div>
                                    <input class="form-control minute" type="number" max="59" min="0" value=""
                                           onblur="attendance_detail.checkMinute(this)">
                                </div>

                                <div class="next_day end_time_next_day">

                                    <div class="choose_item" onclick="attendance_detail.chooseNextDay(this)">
                                        <img src="image/icon_checkbox_unCheck.png">
                                    </div>
                                    次日

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <div class="attend_leave_container row border-bottom">

                    <div class="col-xs-2" style="line-height:34px;">
                        请假记录：
                    </div>

                    <div class="col-xs-10 btn_list">
                        <div class="btn btn-success btn_add" onclick="attendance_detail.initAttendLeave()">新增</div>

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true"
                             data-toggle="tooltip"
                             data-placement="top"
                             title="<p'>新增当天请假记录，请假时间不能重叠，必须在上下班时间内。</p>"/>

                    </div>

                    <div class="col-xs-11 col-xs-offset-1">

                        <div class="attend_leave_list"></div>

                    </div>


                </div>

                <div class="attend_overTime_container row">

                    <div class="col-xs-2" style="line-height:34px;">
                        加班记录：
                    </div>

                    <div class="col-xs-10 btn_list">
                        <div class="btn btn-success btn_add" onclick="attendance_detail.initAttendOverTime()">
                            新增
                        </div>

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true"
                             data-toggle="tooltip"
                             data-placement="top"
                             title="<p'>新增当天加班记录，根据班次加班间隔设置规则计算加班时长；
                                 请注意不要与系统自动计算的加班时间重复。</p>"/>

                    </div>

                    <div class="col-xs-11 col-xs-offset-1">

                        <div class="attend_overTime_list"></div>

                    </div>


                </div>

            </div>
            <div class="modal-footer">

                <div class="clear_attend" onclick="attendance_detail.clearAttend()">清空考勤</div>
                <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                     style="position: absolute;left:80px;top:15px;"
                     data-html="true"
                     data-toggle="tooltip"
                     data-placement="top"
                     title="<p>清空该员工当天的所有出勤记录。</p>"/>


                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-success" onclick="attendance_detail.attendanceInfoUpdate()">
                    修改出勤
                </button>
                <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                     data-html="true"
                     data-toggle="tooltip"
                     data-placement="top"
                     title="<p>提交修改后，该员工当天的出勤结果会重新计算为最新。</p>"/>


            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="item attend_detail_item" hidden data-id="">

    <div style="line-height: 34px;">修改记录：</div>

    <div class="type">
        <%--<select class="form-control">--%>
        <%--<option value="1">事假</option>--%>
        <%--<option value="1">调休假</option>--%>
        <%--<option value="1">月假</option>--%>
        <%--<option value="1">加班假</option>--%>
        <%--</select>--%>
    </div>

    <div class="input-group begin_time">
        <div class="input-group-addon">开始：</div>
        <input class="form-control hour" type="number" max="23" min="0" value=""
               onblur="attendance_detail.checkHour(this)">
        <div class="input-group-addon">:</div>
        <input class="form-control minute" type="number" max="59" min="0" value=""
               onblur="attendance_detail.checkMinute(this)">
    </div>
    <div class="next_day begin_time_next_day">

        <div class="choose_item" onclick="attendance_detail.chooseNextDay(this)">
            <img src="image/icon_checkbox_unCheck.png">
        </div>
        次日

    </div>

    <div class="input-group end_time">
        <div class="input-group-addon">结束：</div>
        <input class="form-control hour" type="number" max="23" min="0" value=""
               onblur="attendance_detail.checkHour(this)">
        <div class="input-group-addon">:</div>
        <input class="form-control minute" type="number" max="59" min="0" value=""
               onblur="attendance_detail.checkMinute(this)">
    </div>
    <div class="next_day end_time_next_day">

        <div class="choose_item" onclick="attendance_detail.chooseNextDay(this)">
            <img src="image/icon_checkbox_unCheck.png">
        </div>
        次日

    </div>

    <div class="btn btn-sm btn-success btn_del" onclick="attendance_detail.removeItem(this)">删除</div>

</div>
