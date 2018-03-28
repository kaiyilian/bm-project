<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/30
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/bill_record/bill_record.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/bill_record/bill_record.js"></script>

<div class="container bill_record_container">

    <div class="head border-bottom">
        <div class="txt">开票记录</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-4 item">
                <span class="input-group-addon">关键字</span>
                <input class="form-control search_condition" placeholder="客户抬头／开票总金额" maxlength="8">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="bill_record.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_bill_record"></table>

        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-primary btn-sm btn_del" onclick="bill_record.billRecordDel()">删除</div>
            <div class="btn btn-primary btn-sm btn_down" onclick="bill_record.billRecordExport()">导出</div>

        </div>

    </div>

</div>

<div class="modal fade bill_info_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">客户信息</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">开票日期：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date bill_date" data-time=""
                               id="bill_date" placeholder="YYYY-MM-DD">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">邮寄日期：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date mail_date" data-time=""
                               id="mail_date" placeholder="YYYY-MM-DD">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">收件人：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date addressee">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">签收日期：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date receipt_date" data-time=""
                               id="receipt_date" placeholder="YYYY-MM-DD">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">签收情况：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date receipt_info">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">回款情况：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date payment_info">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3 txt">备注：</div>
                    <div class="col-xs-9 txtInfo">
                        <input class="form-control layer-date remark">
                    </div>
                </div>


            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-primary" onclick="bill_record.billRecordUpdate()">保存</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
