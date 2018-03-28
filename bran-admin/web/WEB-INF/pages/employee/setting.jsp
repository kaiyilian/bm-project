<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/24
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/employee/setting.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/employee/setting.js"></script>

<div class="container emp_setting_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">员工配置</div>
    </div>

    <div class="content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs nav_temp" role="tablist">

            <li role="presentation" class="">

                <a href="#work_line" role="tab" data-toggle="tab"
                   data-href="work_line">
                    工段设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#work_shift" role="tab" data-toggle="tab"
                   data-href="work_shift">
                    班组设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#position" role="tab" data-toggle="tab"
                   data-href="position">
                    职位设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#work_sn_prefix" role="tab" data-toggle="tab"
                   data-href="work_sn_prefix">
                    工号设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#roster_custom" role="tab" data-toggle="tab"
                   data-href="roster_custom">
                    花名册设置
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#leave_reason" role="tab" data-toggle="tab"
                   data-href="leave_reason">
                    离职原因
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#message" role="tab" data-toggle="tab"
                   data-href="message">
                    消息提醒
                </a>

            </li>

        </ul>

        <!-- Tab panes -->
        <div class="tab-content">

            <div role="tabpanel" id="work_line" class="tab-pane fade">

                <div class="row">

                    <div class="btn btn-orange btn_add"
                         onclick="work_line.modalShow()">
                        新增工段
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_work_line"></table>
                </div>

            </div>

            <div role="tabpanel" id="work_shift" class="tab-pane fade">

                <div class="row">

                    <div class="btn btn-orange btn_add"
                         onclick="work_shift.modalShow()">
                        新增班组
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_work_shift"></table>
                </div>

            </div>

            <div role="tabpanel" id="position" class="tab-pane fade">

                <div class="row">

                    <div class="btn btn-orange btn_add"
                         onclick="position.modalShow()">
                        新增职位
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_position"></table>
                </div>

            </div>

            <div role="tabpanel" id="work_sn_prefix" class="tab-pane fade">

                <div class="row">

                    <div class="btn btn-orange btn_add"
                         onclick="work_sn_prefix.modalShow()">
                        新增工号类型
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_work_sn_prefix"></table>
                </div>

            </div>

            <div role="tabpanel" id="roster_custom" class="tab-pane fade">

                <div class="row">

                    <div class="btn btn-orange btn_add"
                         onclick="roster_custom.modalShow()">
                        新增花名册字段
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_roster_custom"></table>
                </div>

            </div>

            <div role="tabpanel" id="leave_reason" class="tab-pane fade">

                <div class="row" style="display: flex;">

                    <div class="btn btn-orange btn_add"
                         onclick="leave_reason.modalShow()">
                        新增离职原因
                    </div>

                    <div class="txt clr_red" style="margin-left: 10px;">
                        <span>*</span>
                        <span class="txt">红色字体代表不良原因</span>
                    </div>

                </div>

                <div class="table_container">
                    <table id="tb_leave_reason"></table>
                </div>

            </div>

            <div role="tabpanel" id="message" class="tab-pane fade">

                <div class="row">

                    <div class="col-xs-2 center" style="height:100px;font-size: 16px;font-weight: bold;">
                        入职消息提醒：
                    </div>

                    <div class="col-xs-5">

                        <div class="entry_prompt_info">
                            <%--xxx提醒您，距离xxxx年xx月xx日入职还有x天。--%>
                        </div>

                        <div class="row">

                            <div class="choose_item" onclick="message.chooseItem(this)">
                                <img src="image/UnChoose.png"/>
                            </div>

                            <div class="txt">设置入职前</div>

                            <div class="day_container time_container">
                                <select>
                                    <option>1</option>
                                </select>
                            </div>

                            <div class="txt">天</div>

                            <div class="hour_container time_container">
                                <select>
                                    <option>1</option>
                                </select>
                            </div>

                            <div class="txt">时，入职消息提醒</div>

                        </div>

                    </div>

                    <div class="col-xs-5">

                        <div class="btn btn-orange btn_submit" onclick="message.entryInfoSubmit()">
                            提交
                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<div class="modal fade common_set_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="txt col-xs-3">工段名称：</div>
                    <div class="txtInfo col-xs-9 name">
                        <input type="text" class="form-control">
                    </div>
                </div>

                <div class="row leave_reason_container hide">

                    <div class="prompt col-xs-9 col-xs-offset-3">
                        <div class="is_bad_container" onclick="leave_reason.chooseReasonType(this)">
                            <div class="choose_item f_left">
                                <img src="image/UnChoose.png">
                            </div>
                            <div class="txt">是否为不良离职原因</div>
                        </div>
                        <%--<div class="row clr_red">--%>
                        <%--<span>*</span>--%>
                        <%--<span class="txt">红色字体代表不良原因</span>--%>
                        <%--</div>--%>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-success btn_save">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade work_sn_prefix_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">工号类型</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="txt col-xs-3">工号前缀：</div>
                    <div class="txtInfo col-xs-9 work_sn_prefix">
                        <input type="text" class="form-control" maxlength="8">
                    </div>
                </div>

                <div class="row">
                    <div class="txt col-xs-3">工号位数：</div>
                    <div class="txtInfo col-xs-9 work_sn_length">
                        <select class="form-control">
                            <option value="0">工号位数</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                        </select>
                    </div>
                    <div class="txtInfo col-xs-9 col-xs-offset-3 clr_red" style="line-height:30px;">
                        位数不含前缀，前缀一旦被使用，位数将只能增加不能降低。
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-success btn_save">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade roster_custom_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">花名册自定义</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="txt col-xs-3">自定义名称：</div>
                    <div class="txtInfo col-xs-9 name">
                        <input type="text" class="form-control" maxlength="10"
                               placeholder="输入10个字以内的自定义名称">
                    </div>
                </div>

                <div class="row">
                    <div class="txt col-xs-3">信息类型：</div>
                    <div class="txtInfo col-xs-9 type">
                        <select class="form-control">
                            <option value="0">数字类型</option>
                            <option value="1">文本类型</option>
                            <option value="2">日期类型</option>
                        </select>
                    </div>
                    <div class="txtInfo col-xs-9 col-xs-offset-3 clr_red" style="line-height:30px;">
                        该花名册自定义信息一旦被使用过，类型不可以再次修改！
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-success btn_save">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


