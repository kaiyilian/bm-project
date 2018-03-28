<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/26
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/red_packet_record.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/red_packet_record.js"></script>

<div class="red_packet_record_container container">

    <div class="head border-bottom">
        <div class="txt">领红包记录</div>
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
                <span class="input-group-addon">红包余额：</span>
                <input type="text" class="form-control balance" placeholder="红包余额" maxlength="10">
                <span class="add-on"><i class="icon-remove"></i></span>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">手机号码：</span>
                <input type="text" class="form-control user_phone" placeholder="手机号" maxlength="11"
                       onkeyup="this.value=this.value.replace(/\D/g,'')">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search"
                     onclick="red_packet_record.btnSearchClick()">查询
                </div>

                <div class="btn btn-sm btn-primary btn_export"
                     onclick="red_packet_record.exportRecord()">
                    导出
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_red_packet_record"></table>

        </div>

    </div>

</div>
