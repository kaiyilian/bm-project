<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/20
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/notification/notification.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/notification/notification.js"></script>

<div class="container notification_container">

    <div class="head border-bottom">
        <i class="icon icon-corp_info"></i>
        <div class="txt">消息中心</div>
    </div>

    <div class="content">

        <div class="col-xs-12 notification_list">

            <%--<div class="row item unRead">--%>
            <%--<div class="col-xs-6 title">--%>
            <%--您有一份合同等待审核（合同编号：--%>
            <%--<div class="contract_no">LS002</div>--%>
            <%--）--%>
            <%--</div>--%>
            <%--<div class="col-xs-6 time">2016-02-12 12:45</div>--%>
            <%--</div>--%>

        </div>

        <div class="pager_container">
            <%--<ul class="pagenation" style="float:right;"></ul>--%>
        </div>

    </div>

</div>