<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/19
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/corporation/corp_notice.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/corporation/corp_notice.js"></script>

<script src="<%=contextPath%>/js/bran/underscore.min.js"></script>

<div class="container corp_notice_container">

    <div class="head border-bottom">
        <i class="icon icon-corp_info"></i>
        <div class="txt">企业公告</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td class="choose_item"></td>
                    <td>序号</td>
                    <td>标题</td>
                    <td>内容</td>
                    <td>发布部门</td>
                    <td>发布人</td>
                    <td>时间</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>

        <div class="pager_container">
            <%--<ul class="pagenation" style="float:right;"></ul>--%>
        </div>

    </div>

    <div class="foot">
        <div class="choose_container" onclick="corp_notice.chooseAll()">
            <img src="image/UnChoose.png"/>
            <span>全选</span>
        </div>

        <div class="btn_list">

            <div class="btn btn-sm btn-default btn_del"
                 onclick="corp_notice.notificationDelMore()">
                删除
            </div>

            <div class="btn btn-sm btn-success"
                 onclick="corp_notice.notificationReleaseShow()">
                发布消息
            </div>

        </div>

    </div>

</div>

<div class="modal fade corp_notice_add_modal" role="dialog"
     style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">发布消息</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">标题：</div>
                    <div class="col-xs-9 txtInfo notification_title_add">
                        <input type="text" class="form-control" placeholder="请输入标题" maxlength="32">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">发布人：</div>
                    <div class="col-xs-9 txtInfo notification_author_add">
                        <input type="text" class="form-control" placeholder="请输入发布人姓名" maxlength="32">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">发布部门：</div>
                    <div class="col-xs-9 txtInfo notification_dept_add">
                        <select class="form-control">
                            <%--<option>1</option>--%>
                            <%--<option>1</option>--%>
                            <%--<option>1</option>--%>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">内容：</div>
                    <div class="col-xs-9 txtInfo notification_content_add">
                        <textarea class="form-control" placeholder="请输入内容" maxlength="256"></textarea>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <div class="btn btn-orange btn_release"
                     onclick="corp_notice.notificationRelease()">发布
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade corp_notice_info_modal" role="dialog"
     style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">消息详情</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <span class="col-xs-3 txt">标题：</span>
                    <span class="col-xs-9 txtInfo notification_title_show">
                        <%--标题--%>
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3 txt">发布人：</span>
                    <span class="col-xs-9 txtInfo notification_author_show">
                        <%--作者--%>
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3 txt">发布部门：</span>
                    <span class="col-xs-9 txtInfo notification_dept_show">
                        <%--作者--%>
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3 txt">内容：</span>
                    <span class="col-xs-9 txtInfo notification_content_show">
                        <%--内容详情--%>
                    </span>
                </div>

            </div>
            <div class="modal-footer">
                <div class="btn btn-orange" data-dismiss="modal">关闭</div>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
