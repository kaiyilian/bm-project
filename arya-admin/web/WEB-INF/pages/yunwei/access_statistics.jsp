<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/23
  Time: 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<script src="<%=contextPath%>/js/yunwei/access_statistics.js"></script>

<div class="access_statistics_container container">

    <div class="head border-bottom">
        <div class="txt">访问统计</div>
    </div>

    <div class="content">

        <div class="chart_item">
            <div id="nHour_load" class="loading"></div>
            <div id="nHour"></div>
        </div>

        <div class="chart_item">
            <div id="nMonth_load" class="loading"></div>
            <div id="nMonth"></div>
        </div>

        <div class="chart_item">
            <div id="eHour_load" class="loading"></div>
            <div id="eHour"></div>
        </div>

        <div class="chart_item">
            <div id="eWeek_load" class="loading"></div>
            <div id="eWeek"></div>
        </div>

        <div class="chart_item">
            <div id="eVersion_load" class="loading"></div>
            <div id="eVersion"></div>
        </div>

        <div class="chart_item">
            <div id="eDevice_load" class="loading"></div>
            <div id="eDevice"></div>
        </div>

    </div>

</div>