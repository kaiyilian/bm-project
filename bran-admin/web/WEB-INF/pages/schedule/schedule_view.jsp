<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/7
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/schedule/schedule_view.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/schedule/schedule_view.js"></script>

<div class="container schedule_view_container" onclick="$('.schedule_list').hide();">

    <div class="head border-bottom">
        <i class="icon icon-schedule_view"></i>
        <div class="txt">排班视图</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="row">

                <div class="input-group input-append date col-xs-3 item">
                    <span class="input-group-addon">月份：</span>
                    <span class="form-control month" data-time="">YYYY-MM</span>
                    <span class="add-on" onclick="schedule_view.clearMonth();">
                        <img src="image/icon_remove.png">
                    </span>
                </div>

                <%--<div class="input-group col-xs-3 item schedule_name">--%>
                <%--<div class="input-group-addon">排班名称：</div>--%>
                <%--<select class="form-control"></select>--%>
                <%--</div>--%>

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
                    <select class="form-control"></select>
                </div>

            </div>

            <div class="row">

                <%--<div class="input-group col-xs-3 item search_condition">--%>
                <%--<div class="input-group-addon">快速搜索：</div>--%>
                <%--<input class="form-control" placeholder="员工姓名/工号/工段"/>--%>
                <%--</div>--%>
                <div class="input-group col-xs-6 item">
                    <span class="input-group-addon">快速搜索：</span>
                    <select data-placeholder="姓名/工号/工段" multiple
                            class="chosen-select user_list">
                        <%--<option>1</option>--%>
                    </select>
                </div>

                <div class="col-xs-3 item btn_list">

                    <div class="btn btn-sm btn-orange btn_search"
                         onclick="schedule_view.btnSearchClick()">
                        查询
                    </div>

                    <div class="btn btn-sm btn-orange btn_reset"
                         onclick="schedule_view.resetParam()">
                        重置
                    </div>

                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_schedule_view"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">
            <div class="btn btn-success btn_pub" onclick="schedule_view.schedulePub()">发布</div>
        </div>

    </div>

</div>


