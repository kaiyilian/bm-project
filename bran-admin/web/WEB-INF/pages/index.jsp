<%--
  Created by IntelliJ IDEA.
  User: allen
  Date: 16/4/22
  Time: 下午3:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/index.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/index.js"></script>

<div class="index_container container">

    <div class="user_info">

        <div class="col-lg-5 col-xs-12" style="padding:0;">

            <div class="user_head_img">
                <img src="image/index_user_head.png"/>
            </div>

            <div class="corp_info">
                <span>您好！欢迎登陆</span>
                <span class="corp_name"></span>
                <span>企业内部管理系统。</span>
            </div>

        </div>

        <div class="col-lg-7 col-xs-12 login_param">

            <%--<div class="item col-xs-3">--%>
            <%--<span class="txt">当前用户：</span>--%>
            <%--<span class="txtInfo user_name"></span>--%>
            <%--</div>--%>

            <div class="item col-xs-6">
                <span class="txt">上次登陆时间：</span>
                <span class="txtInfo last_login_time"></span>
            </div>

            <div class="item col-xs-6">
                <span class="txt">上次登陆IP：</span>
                <span class="txtInfo last_login_ip"></span>
            </div>

        </div>

    </div>

    <div class="block block_1 col-xs-6">

        <div class="head">
            <div class="pull-left txt">合同到期</div>

            <div class="pull-right btn_detail" onclick="index.getEmpContractExpirePage()">
                查看更多
            </div>

        </div>

        <div class="content contract_expire_list">

            <%--<div class="contract_expire_item col-xs-12">--%>
            <%--<div class="employee_name col-xs-2">掌声</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同开始时间:</span>--%>
            <%--<span class="employee_contract_begin_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同到期时间</span>--%>
            <%--<span class="employee_contract_end_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--</div>--%>

        </div>

        <div class="foot">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_2 col-xs-6">

        <div class="head">
            <div class="pull-left txt">试用期到期</div>

            <div class="pull-right btn_detail" onclick="index.getEmpProbationExpirePage()">
                查看更多
            </div>
        </div>

        <div class="content probation_expire_list">

            <%--<div class="probation_expire_item col-xs-12">--%>
            <%--<div class="employee_name col-xs-2">掌声</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同开始时间:</span>--%>
            <%--<span class="employee_contract_begin_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同到期时间</span>--%>
            <%--<span class="employee_contract_end_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--</div>--%>

        </div>

        <div class="foot">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_3 col-xs-6">

        <div class="head">
            <div class="pull-left txt">同意入职</div>

            <div class="pull-right btn_detail" onclick="index.getEmpEntryPage()">
                查看更多
            </div>

        </div>

        <div class="content entry_expire_list">

            <%--<div class="probation_expire_item col-xs-12">--%>
            <%--<div class="employee_name col-xs-2">掌声</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同开始时间:</span>--%>
            <%--<span class="employee_contract_begin_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--<div class="col-xs-5">--%>
            <%--<span class="txt">合同到期时间</span>--%>
            <%--<span class="employee_contract_end_time">2015-05-02</span>--%>
            <%--</div>--%>
            <%--</div>--%>

        </div>

        <div class="foot">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_4 col-xs-6">

        <div class="head">
            <div class="pull-left txt">入职提醒</div>

            <div class="pull-right btn_detail" onclick="index.getEmpEntryRemindPage()">
                查看更多
            </div>

        </div>

        <div class="content entry_remind_list">


        </div>

        <div class="foot">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_5 col-xs-6">

        <div class="head">
            <div class="pull-left txt">生日提醒</div>

            <div class="pull-right btn_detail" onclick="index.getEmpBirthPage()">
                查看更多
            </div>

        </div>

        <div class="content">

            <div class="row list birth_list">

                <%--<div class="item col-xs-12">--%>

                <%--<div class="col-xs-3 emp_name">--%>
                <%--姓名--%>
                <%--</div>--%>

                <%--<div class="col-xs-5 emp_birth">--%>
                <%--1992-12-12--%>
                <%--</div>--%>

                <%--<div class="col-xs-4 emp_age">--%>
                <%--19岁--%>
                <%--</div>--%>

                <%--</div>--%>

            </div>

        </div>

        <div class="foot" style="height:49px;">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_6 col-xs-6">

        <div class="head">
            <div class="pull-left txt">身份证到期</div>

            <div class="pull-right btn_detail" onclick="index.getIdCardPage()">
                查看更多
            </div>

        </div>

        <div class="content">

            <div class="row list idCard_list">

                <%--<div class="item col-xs-12">--%>

                    <%--<div class="col-xs-3 emp_name">--%>
                        <%--姓名--%>
                    <%--</div>--%>

                    <%--<div class="col-xs-9 idCard_date">--%>
                        <%--身份证到期时间：1992-12-30--%>
                    <%--</div>--%>

                <%--</div>--%>

            </div>

        </div>

        <div class="foot" style="height:49px;">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

    <div class="block block_7 col-xs-6 hide">

        <div class="head">
            <div class="pull-left txt">排班管理</div>

            <div class="pull-right btn_detail" onclick="index.getScheduleRulePage();">
                查看更多
            </div>

        </div>

        <div class="content">

            <%--<div style="text-align: center;font-size: 20px;line-height:100px;">敬请期待</div>--%>

            <div class="row">

                <div class="col-xs-3 img">
                    <img src="image/index_schedule_rule.png">
                </div>

                <div class="f_left date">

                    <%--<div class="col-xs-4">2月5日</div>--%>
                    <%--<div class="col-xs-4">2月5日</div>--%>
                    <%--<div class="col-xs-4">2月5日</div>--%>

                </div>

            </div>

            <div class="row schedule_list">

                <%--<div class="item">--%>
                <%--<div class="col-xs-3 workShift_name">班组名称</div>--%>
                <%--<div class="col-xs-3">--%>
                <%--<div style="background-color: green;">早班</div>--%>
                <%--</div>--%>
                <%--<div class="col-xs-3" style="background-color: blue;">--%>
                <%--早班--%>
                <%--</div>--%>
                <%--<div class="col-xs-3" style="background-color: goldenrod;">--%>
                <%--早班--%>
                <%--</div>--%>
                <%--</div>--%>

            </div>

        </div>

        <div class="foot" style="height:49px;">

            <div class="count_container center">
                <span>一共显示</span>
                <span class="count">0</span>
                <span>条</span>
            </div>

        </div>

    </div>

</div>

