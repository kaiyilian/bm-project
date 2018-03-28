<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/4/27
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/corporation/corp_service_attendance.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_service_attendance.js"></script>

<div class="container corp_container corp_service_attendance_container">

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>企业列表</h5>
        </div>

        <div class="ztree_search_container">
            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div class="ztreeContainer">
            <ul class="ztree" id="corp_group_attendance_tree"></ul>
        </div>

    </div>

    <div class="col-xs-10 corp_content">

        <div class="col-xs-12 block attendance_info_container">

            <!-- Nav tabs -->
            <ul class="nav nav-tabs nav_attendance" role="tablist">

                <li role="presentation" class="">

                    <a href="#attendance_machine_method"
                       role="tab" data-toggle="tab">
                        考勤机打卡
                    </a>

                </li>

                <li role="presentation" class="">

                    <a href="#phone_method"
                       role="tab" data-toggle="tab">
                        手机打卡
                    </a>

                </li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">

                <div role="tabpanel" id="attendance_machine_method"
                     class="tab-pane fade in ">

                    <div class="row is_enable">
                        <img src="img/icon_checked.png">
                        是否启用
                    </div>

                    <div class="table_container">
                        <table class="table table-striped table-bordered table-hover dataTable attendance_machine_list">
                            <thead>
                            <tr>
                                <td>考勤机编号</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <%--<tr class="item" data-id="">--%>
                            <%--<td class="deviceNo">3423dD354354</td>--%>
                            <%--<td class="operate">--%>
                            <%--<div class="btn btn-sm btn-default btn_modify">编辑</div>--%>
                            <%--<div class="btn btn-sm btn-default btn_del">删除</div>--%>
                            <%--</td>--%>
                            <%--</tr>--%>
                            </tbody>
                        </table>
                    </div>

                    <div class="btn btn-sm btn-default btn_add">新增</div>


                    <div class="attendance_sync" style="margin-top: 20px;">

                        <div class="row" style="line-height:40px;margin:0;font-size: 18px;padding-left: 10px;">
                            手动同步考勤数据
                        </div>

                        <div class="row" style="line-height:40px;margin:0;display: flex;">

                            <input class="form-control layer-date sync_time"
                                   placeholder="YYYY-MM-DD"
                                   onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">

                            <div class="btn btn-primary btn_sync" style="margin-left: 10px;">
                                立即同步
                            </div>

                        </div>


                    </div>

                    <div class="row" style="line-height:40px;margin:0;">
                        同步当天正常班次、跨天班次的所有上下班打卡数据。
                        5分钟内只能同步一次。
                    </div>


                </div>

                <div role="tabpanel" id="phone_method"
                     class="tab-pane fade">

                    <div class="row is_enable">
                        <img src="img/icon_Unchecked.png">
                        是否启用
                    </div>

                </div>

            </div>

            <div class="row btn_operate">

                <div data-value="8" class="btn btn-primary btn_save"
                     onclick="corp_service_attendance.attendanceInfoSave()">
                    保存
                </div>

                <div data-value="16" class="btn btn-primary btn_modify"
                     onclick="corp_service_attendance.attendanceInfoModify()">
                    编辑
                </div>

                <div data-value="32" class="btn btn-primary btn_cancel"
                     onclick="corp_service_attendance.attendanceInfoCancelByModify()">
                    取消
                </div>

            </div>

        </div>

    </div>

</div>
