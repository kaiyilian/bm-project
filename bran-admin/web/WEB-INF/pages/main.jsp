<%--
  User: LiuJie
  Date: 2016/5/11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--bootstrap样式--%>
<link href="<%=contextPath%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<%--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">--%>
<link href="<%=contextPath%>/css/bootstrap/font-awesome.min.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bootstrap/style.min.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bootstrap/animate.min.css" rel="stylesheet">

<!-- 全局js -->
<script src="<%=contextPath%>/js/jquery/jquery-2.1.1.min.js"></script>
<script src="<%=contextPath%>/js/jquery/jquery.form.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="<%=contextPath%>/js/bootstrap/bootstrap.min.js"></script>
<script src="<%=contextPath%>/js/bootstrap/bootstrap-paginator.min.js"></script>
<script src="https://api.map.baidu.com/api?v=2.0&ak=X2Vu9AdvTO8MCi6dRZ9iNKEf&s=1"
        type="text/javascript"></script>


<%--tab中滑动块js--%>
<script src="<%=contextPath%>/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<%--日期--%>
<link href="<%=contextPath%>/js/plugins/layer/laydate/need/laydate.css" rel="stylesheet">
<link href="<%=contextPath%>/js/plugins/layer/laydate/skins/default/laydate.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/layer/layer.min.js"></script>
<script src="<%=contextPath%>/js/plugins/layer/laydate/laydate.js"></script>
<%--bootstrap时间插件--%>
<link href="<%=contextPath%>/js/plugins/bootstrap-datetime/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/bootstrap-datetime/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=contextPath%>/js/plugins/bootstrap-datetime/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

<%--bootstrap table--%>
<link href="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>


<!-- Peity -->
<script src="<%=contextPath%>/js/plugins/peity/jquery.peity.min.js"></script>
<!-- Sweet alert 弹框 -->
<link href="<%=contextPath%>/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/sweetalert/sweetalert.min.js"></script>
<%-- 消息提示 --%>
<link href="<%=contextPath%>/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/toastr/toastr.min.js"></script>
<!-- Chosen -->
<link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>

<%--公用--%>
<link href="<%=contextPath%>/css/bran/togglebutton.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/bootstrap-process.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/main.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/bran.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/loading.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/list.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/detailContainer.css" rel="stylesheet">

<script src="<%=contextPath%>/js/bran/urlGroup.js"></script>
<script src="<%=contextPath%>/js/bran/bran.js"></script>
<script src="<%=contextPath%>/js/bran/map.js"></script>
<script src="<%=contextPath%>/js/bran/main.js"></script>
<script src="<%=contextPath%>/js/bran/getBasicList.js"></script>
<script src="<%=contextPath%>/js/bran/optChoose.js"></script>
<script src="<%=contextPath%>/js/plugins/echarts/echarts-all.js"></script>


<html>

<head>
    <title>招才进宝企业管理平台</title>
    <link rel="shortcut icon" href="<%=contextPath%>/image/favicon.ico"/>
    <link rel="bookmark" href="<%=contextPath%>/image/favicon.ico" type="image/x-icon"/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">

<div id="wrapper" style="">
    <%@ include file="layout/head_layout.jsp" %>
    <%@ include file="layout/navbar_layout.jsp" %>

    <div id="page-wrapper" class="gray-bg dashbard-1">
        <%--<%@ include file="layout/tabs_layout.jsp" %>--%>

        <div id="content-main">
            <div class="wrapper wrapper-content" id="page_index">
                <%@ include file="index.jsp" %>
            </div>
        </div>
    </div>

    <%@ include file="layout/foot_layout.jsp" %>

    <%@ include file="layout/dialog_layout.jsp" %>

</div>

<script>
    $(window).resize(function () {
        if ($(window).width() > 768) {
            $("body").removeClass("mini-navbar");
        }
    });

    window.onload = function () {
//debugger

        var contextPath = "<%=contextPath%>";
        var index = window.location.href.indexOf(contextPath);
        var urlPrefix = window.location.href.substr(0, index + contextPath.length);
        console.log(urlPrefix);
        localStorage.setItem("contextPath", urlPrefix);
        localStorage.setItem('pdfUrl', "/admin/corporation/bus/preview/html?suffix=.pdf");
    }

</script>

</body>
</html>
