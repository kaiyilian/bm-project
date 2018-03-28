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

    <link href="<%=contextPath%>/css/bootstrap.min.css" rel="stylesheet">

    <!-- 全局js -->
    <script src="<%=contextPath%>/js/jquery-2.1.1.min.js"></script>
    <script src="<%=contextPath%>/js/bootstrap.min.js"></script>
    <script src="<%=contextPath%>/js/bootstrap/bootstrap-paginator.min.js"></script>

    <%--bootstrap-table--%>
    <link href="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table.js"></script>
    <script src="<%=contextPath%>/js/plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>

    <link href="<%=contextPath%>/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="<%=contextPath%>/css/animate.min.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/style.min.css?v=3.0.0" rel="stylesheet">
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
    <script src="<%=contextPath%>/js/main/util.js"></script>

    <%--zTree插件--%>
    <link href="<%=contextPath%>/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/ztree/js/jquery.ztree.all.min.js"></script>
    <script src="<%=contextPath%>/js/arya/organizationTree/organizationTree.js"></script>
    <%--<link href="<%=contextPath%>/css/organizationTree/organizationTree.css" rel="stylesheet">--%>

    <!-- 插件js -->
    <script src="<%=contextPath%>/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="<%=contextPath%>/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <%--日期--%>
    <script src="<%=contextPath%>/js/plugins/layer/layer.min.js"></script>
    <script src="<%=contextPath%>/js/plugins/layer/laydate/laydate.js"></script>
    <!-- Chosen -->
    <link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>
    <!-- 自定义js -->
    <script src="<%=contextPath%>/js/hplus.min.js?v=3.0.0"></script>
    <%--jquery.form--%>
    <script src="<%=contextPath%>/js/jquery.form.js"></script>

    <%--ueditor配置--%>
    <script>
        window.UEDITOR_HOME_URL = "/arya-admin/js/plugins/ueditor/utf8-jsp/";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="<%=contextPath%>/js/plugins/ueditor/utf8-jsp/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<%=contextPath%>/js/plugins/ueditor/utf8-jsp/ueditor.all.js"></script>


    <!-- Arya -->
    <script src="<%=contextPath%>/js/arya.js"></script>
    <link href="<%=contextPath%>/css/arya.css" rel="stylesheet">
    <%--Main--%>
    <script src="<%=contextPath%>/js/main/main.js"></script>

    <%--是否启用 css--%>
    <link href="<%=contextPath%>/css/togglebutton.css" rel="stylesheet">
    <%--企业管理 左侧tree--%>
    <link href="<%=contextPath%>/css/corporation/corp_style.css" rel="stylesheet">
    <%--加载中--%>
    <link href="<%=contextPath%>/css/loading.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/list.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/common.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/urlGroup.js"></script>
    <script src="<%=contextPath%>/js/map.js"></script>
    <script src="<%=contextPath%>/js/page_urlGroup.js"></script>

</head>