<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/18
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<script src="<%=contextPath%>/js/bran/schedule/approval_type.js"></script>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/schedule_detail.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/schedule_detail.js"></script>

<div class="container schedule_detail_container">

    <div class="head border-bottom">
        <i class="icon icon-schedule_manage"></i>
        <div class="txt">班次详情</div>
    </div>

    <div class="content">

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                班次名称：
            </div>

            <div class="col-xs-3 txtInfo schedule_name">

                <input type="text" placeholder="请输入班次名称" class="form-control">

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                班次简称：
            </div>

            <div class="col-xs-3 txtInfo schedule_shortName">

                <input type="text" placeholder="请输入班次简称" class="form-control">

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                班次颜色：
            </div>

            <div class="col-xs-10 txtInfo schedule_color">

                <%--<div class="item" style="background-color: #4B89DC;"></div>--%>
                <%--<div class="item" style="background-color: #D870AD;"></div>--%>
                <%--<div class="item" style="background-color: #AAB2BD;"></div>--%>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                考勤时间：
            </div>

            <div class="col-xs-10 txtInfo schedule_time">

                <div class="input-group col-xs-5 item begin_time">
                    <div class="input-group-addon">上班：</div>
                    <input class="form-control hour" type="number" max="23" min="0" value=""
                           onblur="schedule_detail.checkHour(this)">
                    <div class="input-group-addon">:</div>
                    <input class="form-control minute" type="number" max="59" min="0" value=""
                           onblur="schedule_detail.checkMinute(this)">
                </div>

                <div class="input-group col-xs-5 item end_time">
                    <div class="input-group-addon">下班：</div>
                    <input class="form-control hour" type="number" max="23" min="0" value=""
                           onblur="schedule_detail.checkHour(this)">
                    <div class="input-group-addon">:</div>
                    <input class="form-control minute" type="number" max="59" min="0" value=""
                           onblur="schedule_detail.checkMinute(this)">
                </div>

                <div class="col-xs-2 next_day end_time_next_day">

                    <div class="choose_item">
                        <img src="image/icon_checkbox_unCheck.png">
                    </div>
                    次日

                </div>

                <div class="input-group col-xs-5 item middle_time">
                    <div class="input-group-addon">打卡中间点：</div>
                    <input class="form-control hour" type="number" max="23" min="0" value=""
                           onblur="schedule_detail.checkHour(this)">
                    <div class="input-group-addon">:</div>
                    <input class="form-control minute" type="number" max="59" min="0" value=""
                           onblur="schedule_detail.checkMinute(this)">
                </div>

                <div class="col-xs-2 next_day middle_time_next_day">

                    <div class="choose_item">
                        <img src="image/icon_checkbox_unCheck.png">
                    </div>
                    次日

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="right"
                         title="<p'>打卡中间点：一旦时间过了打卡中间点，将不再进行上班打卡;默认
                         上下班时间中间的时间点。</p>"/>

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                有效打卡：
            </div>

            <div class="col-xs-10 txtInfo schedule_valid_time">

                <div class="input-group col-xs-5 item begin_time">
                    <div class="input-group-addon">上班开始前：</div>
                    <input class="form-control hour" type="number" max="23" min="0"
                           onblur="schedule_detail.checkHour_1(this)">
                    <div class="input-group-addon">小时</div>
                </div>

                <div class="input-group col-xs-5 item end_time">
                    <div class="input-group-addon">下班结束后：</div>
                    <input class="form-control hour" type="number" max="23" min="0"
                           onblur="schedule_detail.checkHour_1(this)">
                    <div class="input-group-addon">小时</div>
                </div>

                <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                     data-html="true"
                     data-toggle="tooltip"
                     data-placement="top"
                     title="<p'>有效打卡：设置上班提前打卡和下班延后打卡的有效时间，加班时间
                     必须包含在下班有效打卡时间之前。</p>"/>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                人性打卡：
            </div>

            <div class="col-xs-10 txtInfo punch_card">

                <div class="row">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="input-group col-xs-5 item late_time">
                        <div class="input-group-addon">允许迟到：</div>
                        <input class="form-control" type="number" max="999" min="0"
                               onblur="schedule_detail.checkMinute(this)">
                        <div class="input-group-addon">分钟</div>
                    </div>

                    <div class="input-group col-xs-5 item leave_early_time">
                        <div class="input-group-addon">允许早退：</div>
                        <input class="form-control" type="number" max="999" min="0"
                               onblur="schedule_detail.checkMinute(this)">
                        <div class="input-group-addon">分钟</div>
                    </div>

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="left"
                         title="<p'>允许早退：在允许迟到和早退的时间内打卡，记为正常打卡。</p>"/>

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                休息时间：
            </div>

            <div class="col-xs-10 txtInfo rest_time">

                <div class="row" data-id="">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="input-group col-xs-4 item begin_time">
                        <div class="input-group-addon">开始：</div>
                        <input class="form-control hour" type="number" max="23" min="0" value=""
                               onblur="schedule_detail.checkHour(this)">
                        <div class="input-group-addon">:</div>
                        <input class="form-control minute" type="number" max="59" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                    </div>

                    <div class="col-xs-1 next_day begin_time_next_day" onclick="">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        次日

                    </div>

                    <div class="input-group col-xs-4 item end_time">
                        <div class="input-group-addon">结束：</div>
                        <input class="form-control hour" type="number" max="23" min="0" value=""
                               onblur="schedule_detail.checkHour(this)">
                        <div class="input-group-addon">:</div>
                        <input class="form-control minute" type="number" max="59" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                    </div>

                    <div class="col-xs-1 next_day end_time_next_day">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        次日

                    </div>

                    <div class="col-xs-2 is_normal_work_time">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        计入正常工时

                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true"
                             data-toggle="tooltip"
                             data-placement="top"
                             title="<p'>休息时间：上班过程中的休息时间段，两次休息不能重叠，
                         默认休息时间不计入正常工时。</p>"/>

                    </div>

                </div>

                <div class="row">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="input-group col-xs-4 item begin_time">
                        <div class="input-group-addon">开始：</div>
                        <input class="form-control hour" type="number" max="23" min="0" value=""
                               onblur="schedule_detail.checkHour(this)">
                        <div class="input-group-addon">:</div>
                        <input class="form-control minute" type="number" max="59" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                    </div>

                    <div class="col-xs-1 next_day begin_time_next_day" onclick="">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        次日

                    </div>

                    <div class="input-group col-xs-4 item end_time">
                        <div class="input-group-addon">结束：</div>
                        <input class="form-control hour" type="number" max="23" min="0" value=""
                               onblur="schedule_detail.checkHour(this)">
                        <div class="input-group-addon">:</div>
                        <input class="form-control minute" type="number" max="59" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                    </div>

                    <div class="col-xs-1 next_day end_time_next_day">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        次日

                    </div>

                    <div class="col-xs-2 is_normal_work_time">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>
                        计入正常工时

                    </div>

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                旷工设置：
            </div>

            <div class="col-xs-10 txtInfo absenteeism">

                <div class="row">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="col-xs-2">
                        迟到旷工
                    </div>

                    <div class="input-group col-xs-6 item late" data-type="0" data-id="">
                        <div class="input-group-addon">迟到：</div>
                        <input class="form-control" type="number" max="999" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                        <div class="input-group-addon">分钟 记为旷工</div>
                        <select class="form-control">
                            <%--<option>0.5</option>--%>
                            <%--<option>1.0</option>--%>
                        </select>
                        <div class="input-group-addon">天</div>
                    </div>

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="top"
                         title="<p'>迟到旷工：如果开启了人性打卡，迟到时间将从人性打卡的时间点开始计算。</p>"/>

                </div>

                <div class="row">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="col-xs-2">
                        早退旷工
                    </div>

                    <div class="input-group col-xs-6 item leave_early" data-type="1" data-id="">
                        <div class="input-group-addon">早退：</div>
                        <input class="form-control" type="number" max="999" min="0" value=""
                               onblur="schedule_detail.checkMinute(this)">
                        <div class="input-group-addon">分钟 记为旷工</div>
                        <select class="form-control">
                            <%--<option>0.5</option>--%>
                            <%--<option>1.0</option>--%>
                        </select>
                        <div class="input-group-addon">天</div>
                    </div>

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="top"
                         title="<p'>早退旷工：如果开启了人性打卡，早退时间将从人性打卡的时间点开始计算。</p>"/>

                </div>

                <div class="row">

                    <div class="togglebutton col-xs-1">
                        <label>
                            <input type="checkbox" checked="">
                            <span class="toggle"></span>
                        </label>
                    </div>

                    <div class="col-xs-2">
                        缺卡旷工
                    </div>

                    <div class="col-xs-9 miss_card">

                        <div class="row miss_card_on_work" data-type="2" data-id="">

                            <div class="col-xs-2 choose_container">

                                <div class="choose_item">
                                    <img src="image/icon_checkbox_unCheck.png">
                                </div>

                                上班缺卡

                            </div>

                            <div class="input-group col-xs-6 " style="float: left;">
                                <div class="input-group-addon">记为旷工</div>
                                <select class="form-control">
                                    <%--<option>0.5</option>--%>
                                    <%--<option>1.0</option>--%>
                                </select>
                                <div class="input-group-addon">天</div>
                            </div>

                            <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                                 data-html="true"
                                 data-toggle="tooltip"
                                 data-placement="top"
                                 title="<p'>缺卡旷工：如果开启一天缺卡记1.5天，则上下班同时缺卡自动记旷工2次，
                                 旷工1.5天。</p>"/>

                        </div>

                        <div class="row miss_card_off_work" data-type="3" data-id="">

                            <div class="col-xs-2 choose_container" onclick="">

                                <div class="choose_item">
                                    <img src="image/icon_checkbox_unCheck.png">
                                </div>

                                下班缺卡

                            </div>

                            <div class="input-group col-xs-6 ">
                                <div class="input-group-addon">记为旷工</div>
                                <select class="form-control">
                                    <%--<option>0.5</option>--%>
                                    <%--<option>1.0</option>--%>
                                </select>
                                <div class="input-group-addon">天</div>
                            </div>

                        </div>

                        <div class="row miss_card_all_work" data-type="4" data-id="">

                            <div class="col-xs-2 choose_container" onclick="">

                                <div class="choose_item">
                                    <img src="image/icon_checkbox_unCheck.png">
                                </div>

                                一天缺卡

                            </div>

                            <div class="input-group col-xs-6 ">
                                <div class="input-group-addon">记为旷工</div>
                                <select class="form-control">
                                    <%--<option>0.5</option>--%>
                                    <%--<option>1.0</option>--%>
                                </select>
                                <div class="input-group-addon">天</div>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                加班设置：
            </div>

            <div class="col-xs-10 txtInfo overTime_set">

                <%--<div class="row">--%>

                <div class="togglebutton col-xs-1">
                    <label>
                        <input type="checkbox" checked="">
                        <span class="toggle"></span>
                    </label>
                </div>

                <div class="col-xs-11">

                    <div class="row"> 在此设置下班后加班工时计算规则</div>

                    <div class="row">

                        <div class="input-group col-xs-6 item">
                            <div class="input-group-addon">下班后</div>
                            <input class="form-control default_time" type="number" max="999" min="0"
                                   placeholder="默认时间" onblur="schedule_detail.overTimeDefaultTime(this)">
                            <div class="input-group-addon">分钟开始计算加班</div>
                        </div>

                        <div class="input-group col-xs-5 item">
                            <div class="input-group-addon">加班累计间隔</div>
                            <input class="form-control interval_time" type="number" max="999" min="0"
                                   onblur="schedule_detail.checkMinute(this)">
                            <div class="input-group-addon">分钟</div>
                        </div>

                    </div>

                    <div class="row" style="float: left;">

                        <div class="choose_item">
                            <img src="image/icon_checkbox_unCheck.png">
                        </div>

                        <span class="default"></span>
                        <span class="txt">(默认时间)</span>
                        <span>分钟计入加班时间</span>

                    </div>

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="top"
                         title="<p'>规则举例：60分钟计入加班，则下班后的前60分钟直接计入加班，60分钟后
                         按照考勤时间30分钟累加。</p>"/>

                </div>

                <%--</div>--%>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                上班工时：
            </div>

            <div class="col-xs-10 txtInfo work_time">

                <div class="row" data-type="0">

                    <div class="choose_row">
                        <img src="image/icon_radio_unCheck.png">
                    </div>

                    全部计为正常工时

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                         data-html="true"
                         data-toggle="tooltip"
                         data-placement="top"
                         title="<p'>上班工时：上下班之间的有效工时会按照3中规则计算，如果上班工时8小时，3小时
                         记为加班，则正常工时5小时，加班3小时。</p>"/>

                </div>

                <div class="row" data-type="1">

                    <div class="choose_row">
                        <img src="image/icon_radio_check.png">
                    </div>

                    全部计为加班工时

                </div>

                <div class="row" data-type="2">

                    <div class="choose_row">
                        <img src="image/icon_radio_unCheck.png">
                    </div>

                    <div class="input-group col-xs-5 item overTime">
                        <div class="input-group-addon">其中</div>
                        <input class="form-control hour" type="number" max="" min="0"
                               onblur="schedule_detail.checkHour_1(this)">
                        <div class="input-group-addon">小时记为加班工时</div>
                    </div>

                    <div class="col-xs-8 overTime_deduct">

                        <span>早退/迟到/请假优先扣除：</span>

                        <div class="item" data-type="1">

                            <div class="choose_item">
                                <img src="image/icon_radio_unCheck.png">
                            </div>
                            加班工时

                        </div>

                        <div class="item" data-type="2">

                            <div class="choose_item">
                                <img src="image/icon_radio_unCheck.png">
                            </div>
                            正常工时

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-2 txt">
                加班工时：
            </div>

            <div class="col-xs-10 txtInfo overTime_type_container">

                <div class="row">

                    <div class="col-xs-2"> 全部计为</div>
                    <select class="form-control col-xs-3 overTime_type">
                        <option>工作日加班</option>
                    </select>

                    <img src="image/icon_contract/icon_msg.png" class="icon_msg" data-html="true"
                         data-toggle="tooltip" data-placement="top" title=""
                         data-original-title="<p'>设置本班次产生的所有加班工时的所属加班分类</p>">

                </div>

            </div>

        </div>

        <div class="row border-bottom">

            <div class="col-xs-offset-2 btn_list">

                <div class="btn btn-success btn_save" onclick="schedule_detail.scheduleSave()">保存</div>

            </div>

        </div>

    </div>

</div>
