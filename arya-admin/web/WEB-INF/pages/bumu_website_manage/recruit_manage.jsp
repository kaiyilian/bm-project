<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/3/9
  Time: 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bumu_website_manage/recruit_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/bumu_website_manage/recruit_manage.js"></script>

<div class="website_recruit_manage_container container">

    <div class="head border-bottom">
        <div class="txt">招聘管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_add"
                     onclick="recruit_manage.recruitInfoModalShow()">
                    新增
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_website_recruit"></table>

        </div>

    </div>

</div>

<div class="modal fade recruit_info_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增职位信息</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">职位名称：</div>
                    <div class="col-xs-9 txtInfo post_name">
                        <input class="form-control" maxlength="12">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">岗位职责：</div>
                    <div class="col-xs-9 txtInfo post_duties">
                        <%--<textarea class="form-control"></textarea>--%>
                        <div id="post_duties">
                            <!--&lt;!&ndash; 加载编辑器的容器 &ndash;&gt;-->
                            <!--<script id="container" name="content" type="text/plain">-->
                            <!--这里写你的初始化内容-->
                            <!--</script>-->
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">职位要求：</div>
                    <div class="col-xs-9 txtInfo post_require">
                        <%--<textarea class="form-control"></textarea>--%>
                        <div id="post_require">
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
                        onclick="recruit_manage.recruitSave()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

