<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/30
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/project_apply/project_apply_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/project_apply/project_apply_manage.js"></script>

<div class="container project_apply_manage_container">

    <div class="head border-bottom">
        <div class="txt">立项申请</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-4 item">
                <span class="input-group-addon">查询条件</span>
                <input class="form-control search_condition" placeholder="销售人员／销售部门" maxlength="8">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="project_apply_manage.btnSearchClick();">
                    查询
                </div>

                <div class="btn btn-sm btn-primary btn_add" onclick="project_apply_manage.goCorpCreatePage();">
                    新增立项申请
                </div>

            </div>

        </div>

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td>序号</td>
                    <td>销售人员</td>
                    <td>销售部门</td>
                    <td>客户名称</td>
                    <td>申请日期</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>

                <tr class="item" data-id="12">
                    <td>1</td>
                    <td class="sales_man">张三</td>
                    <td class="sales_dept">部门一</td>
                    <td class="corp_name">不木科技</td>
                    <td class="apply_date">申请日期</td>
                    <td class="operate">

                        <button class="btn btn-sm btn-primary btn_detail" onclick="">
                            详情
                        </button>

                        <button class="btn btn-sm btn-primary btn_upgrade" onclick="">
                            成为正式客户
                        </button>

                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="pager_container">
            <%--<ul class="pagenation" style="float:right;"></ul>--%>
        </div>

    </div>

</div>

<div class="modal fade contract_period_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">升级为正式客户</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">
                        合同开始日期：
                    </div>

                    <div class="col-xs-9">
                        <div class="txtInfo form-control contract_begin" id="contract_begin" data-time=""></div>
                        <span class="laydate-icon inline demoicon icon_begin"></span>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-3 txt">
                        合同结束日期：
                    </div>

                    <div class="col-xs-9">

                        <div class="txtInfo form-control contract_end" id="contract_end" data-time=""></div>
                        <span class="laydate-icon inline demoicon icon_end"></span>

                    </div>

                    <div class="col-xs-offset-3 col-xs-9 contract_end_date_list" style="">

                        <div class="col-xs-3 item" data-time="1">
                            <img src="img/icon_Unchecked.png">
                            <span>一年</span>
                        </div>

                        <div class="col-xs-3 item" data-time="2">
                            <img src="img/icon_Unchecked.png">
                            <span>二年</span>
                        </div>

                        <div class="col-xs-3 item" data-time="3">
                            <img src="img/icon_Unchecked.png">
                            <span>三年</span>
                        </div>

                        <div class="col-xs-3 item" data-time="0" style="padding:0;">
                            <img src="img/icon_Unchecked.png">
                            <span>无期限</span>
                        </div>

                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="project_apply_manage.corpUpgrade()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
