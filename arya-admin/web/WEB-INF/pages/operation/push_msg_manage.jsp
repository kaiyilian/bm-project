<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/9/29
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--<!-- Chosen -->--%>
<%--<link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">--%>
<%--<script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>--%>
<%--本页面--%>
<link href="<%=contextPath%>/css/operation/push_msg.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/push_msg.js"></script>

<div class="push_msg_manage_container container">

    <div class="head border-bottom">
        <div class="txt">推送管理</div>
    </div>

    <div class="content">

        <div class="table_container">

            <table id="tb_push_msg"></table>

            <%--<table class="table table-striped table-bordered table-hover dataTable">--%>
            <%--<thead>--%>
            <%--<tr>--%>
            <%--<td class="choose_item"></td>--%>
            <%--<td>推送时间</td>--%>
            <%--<td>推送标题</td>--%>
            <%--<td>推送详情</td>--%>
            <%--<td>推送状态</td>--%>
            <%--<td>操作</td>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--<tr class="item notification_item">--%>
            <%--<td class="choose_item" onclick="notification_manage.ChooseItem(this)">--%>
            <%--<img src="img/icon_Unchecked.png"/>--%>
            <%--</td>--%>
            <%--<td>推送时间</td>--%>
            <%--<td>推送详情</td>--%>
            <%--<td>推送状态</td>--%>
            <%--<td class="operate">--%>
            <%--<span class="btn btn-sm btn-primary"--%>
            <%--onclick="notification_manage.notificationModifyModalShow(this)">编辑</span>--%>
            <%--<span class="btn btn-sm btn-danger"--%>
            <%--onclick="notification_manage.notificationDelByOnly(this)">删除</span>--%>
            <%--</td>--%>
            <%--</tr>--%>
            <%--</tbody>--%>
            <%--</table>--%>

        </div>

    </div>

    <div class="foot">

        <%--<div class="choose_container" onclick="notification_manage.ChooseAll()">--%>
        <%--<img src="img/icon_Unchecked.png"/>--%>
        <%--<span>全选</span>--%>
        <%--</div>--%>

        <div class="btn_list">
            <div class="btn btn-sm btn-default btn_del"
                 onclick="notification_manage.notificationDel()">
                删除
            </div>
            <div class="btn btn-sm btn-primary btn_add"
                 onclick="notification_manage.notificationAddModalShow()">
                新增
            </div>
        </div>
    </div>

    <%--<div class="pager_container">--%>
    <%--<ul class="pagenation" style="float:right;"></ul>--%>
    <%--</div>--%>

</div>

<div class="modal fade push_msg_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">推送信息</h4>
            </div>

            <div class="modal-body">

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">标题</div>

                        <div class="col-xs-10">
                            <input type="text" class="form-control notification_title"
                                   placeholder="请输入标题"/>
                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">推送信息</div>

                        <div class="col-xs-10">
							<textarea class="form-control notification_info" placeholder="请输入推送信息">
                            </textarea>
                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">跳转类型</div>

                        <div class="col-xs-10 jump_type_list">
                            <select name="corp" data-placeholder="请选择跳转类型" multiple
                                    id="" class="jump_type_select chosen-select">
                                <%--<option value="1">公司1</option>--%>
                                <%--<option value="3">公司3</option>--%>
                                <%--<option value="2">公司2</option>--%>
                            </select>
                        </div>


                    </div>
                </div>

                <div class="row margin_b_10 notification_url_container">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">URL</div>

                        <div class="col-xs-10">
                            <input type="text" class="form-control notification_url"
                                   placeholder="请输入URL"/>
                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">推送类型</div>

                        <div class="col-xs-10 push_type_list">
                            <div class="btn btn-sm btn-default" data-type="1">状态栏通知</div>
                            <div class="btn btn-sm btn-default" data-type="2">页面显示通知</div>
                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">
                            标签
                        </div>

                        <div class="col-xs-10 ym_tags">
                            <select name="corp" data-placeholder="请选择标签" multiple
                                    class="ym_tags_select chosen-select">
                                <%--<option>v3.0</option>--%>
                            </select>
                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">
                            定时推送
                        </div>

                        <div class="col-xs-10">

							<span class="btn btn-sm btn-default btn_timing">
								定时推送
							</span>

                            <div class="setTimeContainer">
                                <input class="form-control layer-date setTime"
                                       placeholder="YYYY-MM-DD hh:mm:ss"
                                       onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
                            </div>

                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">推送人群</div>

                        <div class="col-xs-10">

                            <div class="btn btn-sm btn-default btn_all_people">所有人</div>

                            <div class="row col-xs-12 corp_list" style="padding:0;margin:10px 0;">
                                <select name="corp" data-placeholder="请选择公司" multiple
                                        id="corp" class="corp_select chosen-select">
                                    <%--<option value="1">公司1</option>--%>
                                    <%--<option value="3">公司3</option>--%>
                                    <%--<option value="2">公司2</option>--%>
                                </select>
                            </div>

                            <div class="row col-xs-12 category_tag" style="padding:0;margin:0;">

                                <%--<span class="col-xs-6 item" data-id="">--%>
                                <%--<select data-placeholder="员工归属(单选)" multiple--%>
                                <%--class="radio_select chosen-select">--%>
                                <%--<option value="1">汇思员工</option>--%>
                                <%--<option value="0">非汇思员工</option>--%>
                                <%--</select>--%>
                                <%--</span>--%>

                            </div>

                        </div>

                    </div>
                </div>

                <div class="row margin_b_10 ">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">推送人数</div>

                        <div class="col-xs-10 push_count_container">



                        </div>

                    </div>
                </div>


            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn_close" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="notification_manage.notificationSave()">
                    保存
                </button>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

