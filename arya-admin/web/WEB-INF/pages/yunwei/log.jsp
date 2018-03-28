<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/11
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link rel="stylesheet" href="<%=contextPath%>/css/yunwei/log.css">
<script src="<%=contextPath%>/js/yunwei/log.js"></script>

<div class="log_manage_container container">

    <div class="head border-bottom">
        <div class="txt">日志下载</div>
    </div>

    <div class="content">

        <div class="search_container">

            <span class="input-group col-xs-5 item">
                <span class="input-group-addon">应用程序：</span>
                <select class="form-control chosen-select project_list" multiple
                        data-placeholder="请选择应用程序" style="height: 34px;">
                    <%--<option>板块1</option>--%>
                </select>
            </span>

            <div class="btn_list">

				<span class="btn btn-sm btn-primary btn_search"
                      onclick="log_manage.btnSearchClick()">查询
				</span>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_yunwei_log"></table>
        </div>


    </div>

</div>