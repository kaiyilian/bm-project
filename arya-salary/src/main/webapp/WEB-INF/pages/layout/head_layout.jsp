<%--
  User: CuiMengxin
  Date: 2015/11/2
  Time: 13:43
--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>招才进宝管理平台 - 主页</title>

    <meta name="keywords" content="不木科技">
    <meta name="description" content="不木科技">

    <!--[if lt IE 8]>
    <script>
        alert('抱歉，已不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->
    <% String contextPath = request.getContextPath().toString(); %>

    <link href="<%=contextPath%>/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- 全局js -->
    <script src="<%=contextPath%>/js/jquery/jquery-2.1.1.min.js"></script>
    <script src="<%=contextPath%>/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=contextPath%>/js/bootstrap/bootstrap-paginator.min.js"></script>
    <!-- Arya -->
    <link href="<%=contextPath%>/css/arya/arya.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/arya/arya.js"></script>
    <%--Main--%>
    <script src="<%=contextPath%>/js/arya/main.js"></script>

    <%--bootstrap-table--%>
    <link href="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.js"></script>
    <script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>

    <link href="<%=contextPath%>/css/arya/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="<%=contextPath%>/css/arya/animate.min.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/arya/style.min.css?v=3.0.0" rel="stylesheet">
    <!-- Sweet Alert -->
    <link href="<%=contextPath%>/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <%--js--%>
    <!-- Peity -->
    <script src="<%=contextPath%>/js/plugins/peity/jquery.peity.min.js"></script>
    <%--全局的配置--%>
    <script src="<%=contextPath%>/js/config.js"></script>
    <!-- Sweet alert -->
    <script src="<%=contextPath%>/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="<%=contextPath%>/js/plugins/toastr/toastr.min.js"></script>
    <script src="<%=contextPath%>/js/arya/util.js"></script>

    <%--zTree插件--%>
    <link href="<%=contextPath%>/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/ztree/js/jquery.ztree.all.min.js"></script>
    <!-- 插件js -->
    <script src="<%=contextPath%>/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="<%=contextPath%>/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <%--日期--%>
    <script src="<%=contextPath%>/js/plugins/layer/layer.min.js"></script>
    <script src="<%=contextPath%>/js/plugins/layer/laydate/laydate.js"></script>
    <%--月份选择插件--%>
    <link href="<%=contextPath%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
    <script src="<%=contextPath%>/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="<%=contextPath%>/js/plugins/cropper/cropper.min.js"></script>
    <!-- Chosen -->
    <link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>
    <!-- 自定义js -->
    <script src="<%=contextPath%>/js/hplus.min.js?v=3.0.0"></script>
    <%--jquery.form--%>
    <script src="<%=contextPath%>/js/jquery/jquery.form.js"></script>

    <%--企业管理 左侧tree--%>
    <link href="<%=contextPath%>/css/arya/organizationTree/organizationTree.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/arya/organizationTree/organizationTree.js"></script>
    <%--加载中--%>
    <link href="<%=contextPath%>/css/arya/togglebutton.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/arya/loading.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/arya/list.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/arya/common.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/arya/urlGroup.js"></script>
    <script src="<%=contextPath%>/js/arya/map.js"></script>
    <script src="<%=contextPath%>/js/arya/invoice_info.js"></script>

</head>