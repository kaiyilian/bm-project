<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/9
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/contract/contract_detail.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/contract/contract_detail.js"></script>

<div class="container contract_detail_container">

    <div class="head border-bottom">
        <i class="icon icon-e_contract"></i>

        <div class="col-xs-4">
            <span>合同编号：</span>
            <span class="contract_no">
                <%--5465456--%>
            </span>
        </div>
        <div class="col-xs-4">
            <span>创建于：</span>
            <span class="contract_create_time">

            </span>
        </div>
        <div class="col-xs-4">
            <span>合同类型：</span>
            <span class="contract_type">

            </span>
        </div>
    </div>

    <div class="content">

        <div class="user_info signer_a">

            <div class="user_type">甲方填写</div>

            <div class="user_fill_content">

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

            </div>

        </div>

        <div class="user_info signer_b">

            <div class="user_type">乙方填写</div>

            <div class="user_fill_content">

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

                <%--<div class="col-xs-6">--%>
                <%--<span>甲方(单位名称)：</span>--%>
                <%--<span>上海齐家网</span>--%>
                <%--</div>--%>

            </div>

        </div>

        <div class="btn_operate">
            <%--<div class="btn btn-sm btn-success btn_invalid">作废</div>--%>
            <%--<div class="btn btn-sm btn-success btn_send">发送</div>--%>
        </div>

        <%--<img src="image/icon_contract/icon_invalid.png" class="icon_status">--%>

    </div>

    <div class="foot">
        <i onclick="contract_detail.contractPreview()">
            预览合同
        </i>
    </div>

</div>

<div class="modal fade examine_reject_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">审核驳回</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt" style="line-height:34px;">
                        驳回理由：
                    </div>
                    <div class="col-xs-9">
                        <textarea class="form-control reject_reason" maxlength="30"
                                  placeholder="请输入驳回理由"></textarea>
                    </div>
                </div>


            </div>
            <div class="modal-footer">

                <div class="btn btn-orange btn_save" onclick="contract_detail.contractExamineReject()">
                    保存
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
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

                <div class="btn btn-white btn_modify" onclick="contract_detail.contractModifyFirst()">
                    我要先编辑
                </div>

                <div class="btn btn-orange btn_save" onclick="contract_detail.contractResend()">
                    确认
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>