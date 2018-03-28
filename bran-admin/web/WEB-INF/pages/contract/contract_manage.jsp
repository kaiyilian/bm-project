<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/5/23
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/contract/contract_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/contract/contract_manage.js"></script>

<div class="container contract_manage_container">

    <div class="head border-bottom">
        <%--<i class="icon icon-attendance_setting"></i>--%>
        <i class="icon icon-e_contract"></i>
        <div class="txt">合同管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-6 item">
                <span class="input-group-addon">创建时间:</span>
                <input class="form-control layer-date beginTime" id="contract_create_beginTime"
                       placeholder="YYYY-MM-DD">
                <span class="input-group-addon">至</span>
                <input class="form-control layer-date endTime" id="contract_create_endTime"
                       placeholder="YYYY-MM-DD">
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">合同类型：</span>
                <select class="form-control contract_type">
                    <%--<option>全部</option>--%>
                    <%--<option>社保合同</option>--%>
                </select>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">合同状态：</span>
                <select class="form-control contract_status">
                    <%--<option>全部</option>--%>
                    <%--<option>未发送</option>--%>
                    <%--<option>已发送</option>--%>
                    <%--<option>待审核</option>--%>
                    <%--<option>已生效</option>--%>
                    <%--<option>已过期</option>--%>
                    <%--<option>已作废</option>--%>
                </select>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">手机号码：</span>
                <input class="form-control user_phone" type="text" maxlength="11"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       placeholder="请输入手机号码">
            </div>

            <div class="col-xs-3 item btn_list">

                <div class="btn btn-sm btn-orange btn_search"
                     onclick="contract_manage.btnSearchClick()">
                    查询
                </div>

                <div class="btn btn-sm btn-orange btn_new"
                     onclick="contract_manage.goNewContractPage()">
                    新建合同
                </div>

            </div>


        </div>

        <div class="table_container">

            <div class="col-xs-2 batch_operate" style="margin:8px 0;">
                <select class="form-control" onchange="contract_manage.batchOperate(this)">
                    <option value="">请选择</option>
                    <option value="1" class="send_operate" disabled>发送</option>
                    <option value="2" class="del_operate" disabled>删除</option>
                </select>
            </div>

            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td class="choose_item" onclick="contract_manage.chooseCurAll()">
                        <img src="image/UnChoose.png"
                             data-html="true"
                             data-toggle="tooltip"
                             data-placement="right"
                             title="<p style='width:80px;'>选择当前页所有选项</p>"/>
                    </td>
                    <td>合同编号</td>
                    <td>合同类型</td>
                    <td>合同接收人</td>
                    <td>手机号码</td>
                    <td>创建时间</td>
                    <td>合同状态</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>

                <tr class="item" data-id="contract_id_1" data-status="1">
                    <td class="choose_item" onclick="">
                        <img src="image/UnChoose.png"/>
                    </td>
                    <td class="contract_no">合同编号</td>
                    <td class="contract_type">合同类型</td>
                    <td class="contract_receipt">合同接收人</td>
                    <td class="user_phone">手机号码</td>
                    <td class="create_time">创建时间</td>
                    <td class="contract_status">未发送</td>
                    <td class="operate">
                        <%--<div class="btn btn-sm btn-success btn_detail">查看详情</div>--%>
                        <%--<div class="btn btn-sm btn-success btn_send">发送</div>--%>
                        <%--<div class="btn btn-sm btn-success btn_invalid">作废</div>--%>
                    </td>
                </tr>

                <%--<tr class="item" data-id="" data-status="2">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="contract_no">合同编号</td>--%>
                <%--<td class="contract_type">合同类型</td>--%>
                <%--<td class="contract_receipt">合同接收人</td>--%>
                <%--<td class="user_phone">手机号码</td>--%>
                <%--<td class="create_time">创建时间</td>--%>
                <%--<td class="contract_status">未发送</td>--%>
                <%--<td class="operate">--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_detail">查看详情</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_send">发送</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_invalid">作废</div>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr class="item" data-id="" data-status="3">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="contract_no">合同编号</td>--%>
                <%--<td class="contract_type">合同类型</td>--%>
                <%--<td class="contract_receipt">合同接收人</td>--%>
                <%--<td class="user_phone">手机号码</td>--%>
                <%--<td class="create_time">创建时间</td>--%>
                <%--<td class="contract_status">未发送</td>--%>
                <%--<td class="operate">--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_detail">查看详情</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_send">发送</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_invalid">作废</div>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr class="item" data-id="" data-status="4">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="contract_no">合同编号</td>--%>
                <%--<td class="contract_type">合同类型</td>--%>
                <%--<td class="contract_receipt">合同接收人</td>--%>
                <%--<td class="user_phone">手机号码</td>--%>
                <%--<td class="create_time">创建时间</td>--%>
                <%--<td class="contract_status">未发送</td>--%>
                <%--<td class="operate">--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_detail">查看详情</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_send">发送</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_invalid">作废</div>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr class="item" data-id="" data-status="5">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="contract_no">合同编号</td>--%>
                <%--<td class="contract_type">合同类型</td>--%>
                <%--<td class="contract_receipt">合同接收人</td>--%>
                <%--<td class="user_phone">手机号码</td>--%>
                <%--<td class="create_time">创建时间</td>--%>
                <%--<td class="contract_status">未发送</td>--%>
                <%--<td class="operate">--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_detail">查看详情</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_send">发送</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_invalid">作废</div>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr class="item" data-id="" data-status="6">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="contract_no">合同编号</td>--%>
                <%--<td class="contract_type">合同类型</td>--%>
                <%--<td class="contract_receipt">合同接收人</td>--%>
                <%--<td class="user_phone">手机号码</td>--%>
                <%--<td class="create_time">创建时间</td>--%>
                <%--<td class="contract_status">未发送</td>--%>
                <%--<td class="operate">--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_detail">查看详情</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_send">发送</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="btn btn-sm btn-success btn_invalid">作废</div>&ndash;%&gt;--%>
                <%--</td>--%>
                <%--</tr>--%>

                </tbody>
            </table>
        </div>

        <div class="pager_container">
            <%--<ul class="pagenation" style="float:right;"></ul>--%>
        </div>

    </div>

</div>

<div class="modal fade contract_resend_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">合同重新发送</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    系统默认
                    <div>
                        合同有效期
                        <span class="effective_date">7</span>
                        天
                    </div>
                    重新计算，确认要按此规则重新发送？
                </div>

            </div>
            <div class="modal-footer">

                <div class="btn btn-white btn_modify" onclick="contract_manage.contractModifyFirst()">
                    我要先编辑
                </div>

                <div class="btn btn-orange btn_save" onclick="contract_manage.contractResend()">
                    确认
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

