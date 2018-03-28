<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/12
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/contract/contract_create.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/contract/contract_create.js"></script>

<div class="container contract_create_container">

    <div class="head border-bottom">
        <i class="icon icon-e_contract"></i>
        <div class="txt">新建合同</div>
    </div>

    <div class="content">

        <section class="page1">

            <div class="block">

                <div class="col-xs-12 b_head contract_type_container">

                    <div class="contract_type_item active" data-type="0">劳动合同</div>
                    <%--<div class="contract_type_item" data-type="1">社保合同</div>--%>

                </div>

                <div class="contract_temp_container">

                    <%--<div class="contract_temp_item" data-id="1">--%>

                    <%--<img src="image/face_empty.jpg" class="temp_img">--%>

                    <%--<img src="image/icon_contract/icon_preview.png" class="icon_preview">--%>

                    <%--<div class="icon_choose_bg">--%>
                    <%--<img src="image/icon_contract/icon_choose.png">--%>
                    <%--</div>--%>

                    <%--</div>--%>

                </div>

            </div>

            <div class="block">

                <div class="col-xs-12 b_head">
                    <div class="txt">合同设置</div>
                </div>

                <div class="table_container">
                    <table class="table table-striped table-bordered table-hover dataTable">
                        <thead>
                        <tr>
                            <td>合同签署人手机号</td>
                            <td>合同签署有效期</td>
                            <td>合同签署人姓名</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>

                        <%--<tr class="item">--%>
                        <%--<td class="user_phone">--%>
                        <%--<div>--%>
                        <%--<input class="form-control" placeholder="请输入手机号" maxlength="11"--%>
                        <%--onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                        <%--</div>--%>
                        <%--</td>--%>
                        <%--<td class="effective_date">--%>
                        <%--<div>--%>
                        <%--<input class="form-control" placeholder="" maxlength="3"--%>
                        <%--onkeyup="this.value=this.value.replace(/\D/g,'')">--%>
                        <%--<span>天</span>--%>

                        <%--</div>--%>
                        <%--</td>--%>
                        <%--<td class="operate">--%>
                        <%--<div class="btn btn-sm btn-success btn_del">删除</div>--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        </tbody>
                    </table>
                </div>

                <div class="btn btn-sm btn-success btn_add" onclick="contract_create.addContractItem()">
                    新增
                </div>


            </div>

            <div class="foot">

                <div class="btn btn-sm btn-success btn_next" onclick="contract_create.stepNext()">
                    下一步
                </div>

            </div>

        </section>

        <section class="page2">

            <div class="block">

                <div class="col-xs-12 b_head">
                    <div class="txt">
                        填写信息
                        <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                             data-html="true"
                             data-toggle="tooltip"
                             data-placement="right"
                             title="<p'>谨慎填写，劳动合同一旦生成具有法律效应</p>"/>
                    </div>
                </div>

                <div class="col-xs-12 b_body fill_content">

                    <%--<div class="col-xs-12 item">--%>
                    <%--<div class="col-xs-3 txt">测试：</div>--%>
                    <%--<div class="col-xs-9">--%>
                    <%--<input class="form-control">--%>
                    <%--</div>--%>
                    <%--</div>--%>

                </div>

            </div>

            <div class="block">

                <div class="col-xs-12 b_head">
                    <div class="txt">公司印章</div>
                </div>

                <div class="col-xs-12 b_body">

                    <div class="seal_img_container">

                        <%--<div class="add_container" onclick="contract_create.sealTempModalShow()">--%>

                        <%--<img src="image/icon_contract/icon_seal_add.png">--%>
                        <%--<div>从模板库中选择</div>--%>

                        <%--</div>--%>

                    </div>

                </div>


            </div>

            <div class="foot">

                <div class="btn btn-sm btn-success btn_prev" onclick="contract_create.stepPrev();">
                    上一步
                </div>

                <div class="btn btn-sm btn-success btn_save" onclick="contract_create.contractSave(0);">
                    保存
                </div>

                <div class="btn btn-sm btn-success btn_save_and_send" onclick="contract_create.contractSave(1);">
                    保存并发送
                </div>

            </div>

        </section>

    </div>


</div>

<div class="modal fade seal_temp_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择印章</h4>
            </div>
            <div class="modal-body">

                <div class="seal_temp_container">

                    <%--<div class="item active" data-id="1">--%>

                    <%--<img src="image/face_empty.jpg" class="seal_img">--%>

                    <%--<div class="icon_choose_bg">--%>
                    <%--<img src="image/icon_contract/icon_choose.png">--%>
                    <%--</div>--%>

                    <%--</div>--%>


                </div>


            </div>
            <div class="modal-footer">

                <div class="btn btn-orange btn_save" onclick="contract_create.sealTempSave()">
                    保存
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>