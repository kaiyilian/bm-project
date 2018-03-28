<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/3/9
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<% String contextPath = request.getContextPath().toString(); %>

<%--&lt;%&ndash;ueditor配置&ndash;%&gt;--%>
<%--<script>--%>
    <%--window.UEDITOR_HOME_URL = "/arya-admin/js/plugins/ueditor/utf8-jsp/";--%>
<%--</script>--%>
<%--<!-- 配置文件 -->--%>
<%--<script type="text/javascript" src="<%=contextPath%>/js/plugins/ueditor/utf8-jsp/ueditor.config.js"></script>--%>
<%--<!-- 编辑器源码文件 -->--%>
<%--<script type="text/javascript" src="<%=contextPath%>/js/plugins/ueditor/utf8-jsp/ueditor.all.js"></script>--%>

<%--本页面--%>
<link href="<%=contextPath%>/css/bumu_website_manage/news_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/bumu_website_manage/news_manage.js"></script>

<div class="website_news_manage_container container">

    <div class="head border-bottom">
        <div class="txt">新闻管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_add"
                     onclick="news_manage.newsInfoModalShow()">
                    新增
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_website_news"></table>

        </div>

    </div>

</div>

<div class="modal fade news_info_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:1100px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增新闻</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-1 txt">新闻标题：</div>
                    <div class="col-xs-11 txtInfo n_title">
                        <input class="form-control">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-1 txt">发布时间：</div>
                    <div class="col-xs-11 txtInfo pub_time">
                        <input class="form-control layer-date pub_time" placeholder="YYYY-MM-DD"
                               onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:MM:ss'})">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-1 txt">作者：</div>
                    <div class="col-xs-11 txtInfo author">
                        <input class="form-control">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-1 txt">新闻内容：</div>
                    <div class="col-xs-11 txtInfo news_content">
                        <div id="news_content">
                            <!--&lt;!&ndash; 加载编辑器的容器 &ndash;&gt;-->
                            <!--<script id="container" name="content" type="text/plain">-->
                            <!--这里写你的初始化内容-->
                            <!--</script>-->
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary"
                        onclick="news_manage.newsSave()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

