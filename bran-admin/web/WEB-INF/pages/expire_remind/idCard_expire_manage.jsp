<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/17
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/expire_remind/idCard_expire_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/expire_remind/idCard_expire_manage.js"></script>

<div class="idCard_expire_manage_container container">

    <div class="head border-bottom">
        <div class="txt">身份证到期</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_idCard_expire"></table>
        </div>

    </div>

</div>

<div class="modal fade idCard_validity_set_modal" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">更新身份证有效期</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">
                        当前到期时间：
                    </div>

                    <div class="col-xs-9 txtInfo expire_time">
                        <%--2017-12-11--%>
                    </div>

                </div>

                <div class="row">
                    <div class="col-xs-3 txt">
                        新开始时间：
                    </div>

                    <div class="col-xs-9">
                        <div class="idCard_begin_time form-control"
                             id="idCard_begin_time" data-time=""
                             onclick='laydate({istime: true, format: "YYYY-MM-DD"})'></div>
                        <div class="laydate-icon inline demoicon icon_begin"></div>
                    </div>

                </div>

                <div class="row">
                    <div class="col-xs-3 txt">
                        新结束时间：
                    </div>
                    <div class="col-xs-9">

                        <div class="col-xs-12" style="padding:0;">
                            <div class="idCard_end_time form-control"
                                 id="idCard_end_time" data-time=""></div>
                            <div class="laydate-icon inline demoicon icon_end"></div>
                        </div>

                        <div class="col-xs-12 end_date_list" style="">

                            <div class="col-xs-3 item" data-time="5">
                                <img src="image/UnChoose.png">
                                <span>五年</span>
                            </div>

                            <div class="col-xs-3 item" data-time="10">
                                <img src="image/UnChoose.png">
                                <span>十年</span>
                            </div>

                            <div class="col-xs-3 item" data-time="20">
                                <img src="image/UnChoose.png">
                                <span>二十年</span>
                            </div>

                            <div class="col-xs-3 item" data-time="0" style="padding:0;">
                                <img src="image/UnChoose.png">
                                <span>无期限</span>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-orange" onclick="idCard_expire_manage.idCardValidityUpdate()">
                    更新
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
