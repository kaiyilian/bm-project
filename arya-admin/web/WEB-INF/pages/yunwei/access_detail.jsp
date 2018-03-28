<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/23
  Time: 8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link rel="stylesheet" href="<%=contextPath%>/css/yunwei/access_detail.css">
<script src="<%=contextPath%>/js/yunwei/access_detail.js"></script>

<div class="access_detail_container container">

    <div class="head border-bottom">
        <div class="txt">访问详情</div>
    </div>

    <div class="content">

        <div class="url_statistic">

            <div class="name"></div>

            <div class="table_container">
                <table id="tb_url_statistic"></table>
            </div>

        </div>

    </div>

</div>

