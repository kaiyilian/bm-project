<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/3/1
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/women_day_activity.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/women_day_activity.js"></script>

<div class="women_day_activity_container container">

    <div class="head border-bottom">
        <div class="txt">3.8活动列表</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">开始时间：</span>
                <input class="form-control layer-date beginTime" placeholder="YYYY-MM-DD"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">结束时间：</span>
                <input class="form-control layer-date endTime" placeholder="YYYY-MM-DD"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">手机号码：</span>
                <input type="text" class="form-control user_phone" placeholder="手机号" maxlength="11"
                       onkeyup="this.value=this.value.replace(/\D/g,'')">
                <span class="add-on"><i class="icon-remove"></i></span>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">项目：</span>
                <input type="text" class="form-control project_name"
                       placeholder="请输入项目名称" maxlength="16">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search"
                     onclick="women_day_activity.btnSearchClick()">查询
                </div>

                <div class="btn btn-sm btn-primary btn_export"
                     onclick="women_day_activity.exportList()">
                    导出
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_women_day_activity"></table>

        </div>

    </div>

</div>