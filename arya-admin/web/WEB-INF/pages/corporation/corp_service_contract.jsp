<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/10
  Time: 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/corporation/corp_service_contract.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_service_contract.js"></script>

<div class="container corp_container corp_service_contract_container">

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>企业列表</h5>
        </div>

        <div class="ztree_search_container">
            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div class="ztreeContainer">
            <ul class="ztree" id="corp_group_contract_tree"></ul>
        </div>

    </div>

    <div class="col-xs-10 corp_content">

        <div class="col-xs-12 corp_contract_container">

            <div class="row">

                <div class="input-group col-xs-6">

                    <span class="input-group-addon">企业全称：</span>
                    <input type="text" class="form-control editable corp_fullName"
                           placeholder="请输入名称" maxlength="100">

                </div>

                <div class="input-group col-xs-6">

                    <span class="input-group-addon">营业执照编码：</span>
                    <input type="text" class="form-control editable corp_licenses_code"
                           placeholder="请输入营业执照编码" maxlength="50">

                </div>

            </div>

            <div class="row">

                <div class="input-group col-xs-6">

                    <span class="input-group-addon">企业法人：</span>
                    <input type="text" class="form-control editable corp_legal_person"
                           placeholder="请输入企业法人" maxlength="20">

                </div>

                <div class="input-group col-xs-6">

                    <span class="input-group-addon">企业法人身份证：</span>
                    <input type="text" class="form-control editable corp_legal_person_idCard"
                           placeholder="请输入企业法人身份证" maxlength="18">

                </div>

            </div>

            <div class="row">

                <div class="input-group col-xs-6">

                    <span class="input-group-addon">企业法人手机号：</span>
                    <input type="text" class="form-control editable corp_legal_person_phone"
                           placeholder="请输入企业法人手机号" maxlength="11"
                           onkeyup="this.value=this.value.replace(/\D/g,'')">

                </div>

            </div>

            <div class="row">

                <div class="col-xs-6 img_upload_container corp_license_container">
                    <div class="col-xs-4 txt">营业执照：</div>
                    <div class="col-xs-8">
                        <label class="img_upload_lbl corp_license">点击选择营业执照</label>
                    </div>

                    <%--<input accept="image/png,image/jpg"--%>
                    <%--name="corp_license_file" type="file"--%>
                    <%--class="form-control corp_license_file" style="display: none">--%>

                </div>

            </div>

            <div class="btn_operate" style="margin-top: 40px;">

                <div data-value="8" class="btn btn-primary btn_save"
                     onclick="corp_service_contract.corpInfoSave()">
                    提交
                </div>

                <div data-value="16" class="btn btn-primary btn_modify"
                     onclick="corp_service_contract.corpInfoModify()">
                    编辑
                </div>

                <div data-value="32" class="btn btn-primary btn_cancel"
                     onclick="corp_service_contract.corpInfoCancelByModify()">
                    取消
                </div>

            </div>

        </div>

    </div>

</div>

