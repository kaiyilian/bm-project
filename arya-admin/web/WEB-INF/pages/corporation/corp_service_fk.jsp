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
<link href="<%=contextPath%>/css/corporation/corp_service_fk.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_service_fk.js"></script>

<div class="container corp_container corp_service_fk_container">

    <div class="col-xs-2 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>企业列表</h5>
        </div>

        <div class="ztree_search_container">
            <%--<select name="corp" data-placeholder="请选择公司" multiple--%>
            <%--class="corp_select chosen-select">--%>
            <%--&lt;%&ndash;<option value="1">公司1</option>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<option value="3">公司3</option>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<option value="2">公司2</option>&ndash;%&gt;--%>
            <%--</select>--%>

            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div class="ztreeContainer">
            <ul class="ztree" id="corp_group_fk_tree"></ul>
        </div>

    </div>

    <div class="col-xs-10 corp_content">

        <form class="col-xs-12 block fk_info_container"
              enctype="multipart/form-data">

            <input type="hidden" name="id" class="corp_id">
            <input type="hidden" name="parent_id" class="corp_parent_id">

            <div class="row">

                <div class="input-group col-xs-8">

                    <span class="input-group-addon">福库公司名称：</span>
                    <input type="text" class="form-control editable welfare_corp_name"
                           name="welfare_corp_name" placeholder="请输入名称" maxlength="32">

                </div>

            </div>

            <div class="row btn_operate">

                <div data-value="8" class="btn btn-primary btn_save"
                     onclick="corp_service_fk.fkInfoSave()">
                    保存
                </div>

                <div data-value="16" class="btn btn-primary btn_modify"
                     onclick="corp_service_fk.fkInfoModify()">
                    编辑
                </div>

                <div data-value="32" class="btn btn-primary btn_cancel"
                     onclick="corp_service_fk.fkInfoCancelByModify()">
                    取消
                </div>

            </div>

        </form>

    </div>

</div>