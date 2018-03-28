<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/26
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/activity_register_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/activity_register_manage.js"></script>

<div class="activity_register_manage_container container">

    <div class="head border-bottom">
        <div class="txt">活动报名列表</div>
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

            <div class="input-group col-xs-6 item">
                <span class="input-group-addon">活动名称：</span>
                <select class="form-control chosen-select activity_name_list" multiple
                        data-placeholder="请选择活动名称">
                    <%--<option>板块1</option>--%>
                </select>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">手机号码：</span>
                <input type="text" class="form-control user_phone" placeholder="手机号" maxlength="11"
                       onkeyup="this.value=this.value.replace(/\D/g,'')">
                <span class="add-on"><i class="icon-remove"></i></span>
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search"
                     onclick="activity_register_manage.btnSearchClick()">查询
                </div>

                <div class="btn btn-sm btn-primary btn_export"
                     onclick="activity_register_manage.exportList()">
                    导出
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_activity_register"></table>

        </div>

    </div>

</div>