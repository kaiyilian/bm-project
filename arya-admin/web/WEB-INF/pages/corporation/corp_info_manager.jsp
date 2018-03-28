<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/5/3
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/corporation/corp_info_manager.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_info_manager.js"></script>

<div class="container corp_container corp_info_container">

    <div class="col-sm-2 aryaZtreeContainer">

        <div class="ibox-title">
            <h5>企业列表</h5>
        </div>

        <div class="ztree_operate_container">

			<span class="btn btn-sm btn-primary" onclick="corp_info_manage.CorpAdd()">
				新增企业
			</span>

            <span class="btn btn-sm btn-default btn_com_sec_add"
                  onclick="corp_info_manage.CorpAddComSec()">
				新增二级公司
			</span>

        </div>

        <div class="ztree_search_container">
            <input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
        </div>

        <div class="ztreeContainer">
            <ul class="ztree" id="corp_group_tree"></ul>
        </div>

    </div>

    <div class="col-xs-10 corp_content">

        <div class="col-xs-12 block bg_white">

            <div class="corp_or_dept_name">
                <h5>企业信息管理</h5>
            </div>

            <div class="col-xs-12 block corp_basic_info">

                <div class="row">

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">名称：</span>
                        <input type="text" class="form-control editable corp_name" name="name">
                    </div>

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">简称：</span>
                        <input type="text" class="form-control editable corp_short_name" name="short_name">
                    </div>

                </div>

                <div class="row">

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">联系人：</span>
                        <input type="text" class="form-control editable corp_contract" name="contact_name">
                    </div>

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">电话：</span>
                        <input type="text" class="form-control editable corp_contract_phone"
                               name="contact_phone" maxlength="11"
                               onkeyup="this.value=this.value.replace(/\D/g,'')">
                    </div>


                </div>

                <div class="row choose_container service_container">

                    <div class="col-xs-2">开通服务：</div>
                    <div class="col-xs-10">
                        <%--onclick="info_manage.chooseService(this)"--%>
                        <div class="btn btn-default item" data-value="1">
                            社保业务
                        </div>

                        <div class="btn btn-default item" data-value="2">
                            薪资代发
                        </div>

                        <div class="btn btn-default item" data-value="4">
                            一键入职
                        </div>

                        <div class="btn btn-default item" data-value="8">
                            福库
                        </div>

                        <div class="btn btn-default item" data-value="16">
                            考勤服务
                        </div>

                        <div class="btn btn-default item" data-value="32">
                            电子合同
                        </div>

                    </div>

                </div>

                <div class="row choose_container corp_type_container">

                    <div class="col-xs-2">公司类型：</div>
                    <div class="col-xs-4">

                        <div class="btn btn-default item type_group" data-value="1">
                            集团
                        </div>

                        <div class="btn btn-default item type_com_fir" data-value="0">
                            一级公司
                        </div>
                        <%--onclick="info_manage.chooseGroupOrComFir(this)"--%>
                    </div>

                </div>

                <div class="row choose_container project_type_container">

                    <div class="col-xs-2" style="padding-right: 0;">是否是汇思项目：</div>
                    <div class="col-xs-4">

                        <div class="btn btn-default item is_humanpool_project" data-value="1">
                            是
                        </div>

                        <div class="btn btn-default item not_humanpool_project" data-value="0">
                            否
                        </div>

                    </div>

                </div>

                <div class="row btn_operate">

                    <div data-value="2" class="btn btn-primary btn_add_dept"
                         onclick="corp_info_manage.deptAddModalShow()">
                        新增通用部门
                    </div>

                    <div data-value="4" class="btn btn-danger btn_del"
                         onclick="corp_info_manage.corpDel()">
                        删除
                    </div>

                    <div data-value="8" class="btn btn-primary btn_save"
                         onclick="corp_info_manage.corpInfoSave()">
                        保存
                    </div>

                    <div data-value="16" class="btn btn-primary btn_modify"
                         onclick="corp_info_manage.corpInfoModify()">
                        编辑
                    </div>

                    <div data-value="32" class="btn btn-primary btn_cancel"
                         onclick="corp_info_manage.corpInfoCancelByModify()">
                        取消
                    </div>

                </div>

            </div>

            <div class="col-xs-12 block dept_info_container">

                <div class="row">
                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">部门名称：</span>
                        <input type="text" class="form-control editable dept_name">
                    </div>

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">创建时间：</span>
                        <input type="text" class="form-control editable dept_create_time">
                    </div>

                    <div class="input-group col-xs-4">
                        <span class="input-group-addon">修改时间：</span>
                        <input type="text" class="form-control editable dept_update_time">
                    </div>

                </div>

                <div class="row btn_operate col-xs-12">
                    <span class="btn btn-sm btn-primary btn_modify" onclick="">编辑</span>
                    <span class="btn btn-sm btn-danger btn_del">删除</span>
                </div>

            </div>

        </div>

    </div>

</div>

<div class="modal fade dept_info_add_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增部门</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-xs-3 txt">部门名称：</div>
                    <div class="col-xs-9 txtInfo dept_name_add">
                        <input type="text" class="form-control">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn_add"
                        onclick="corp_info_manage.DeptAddSave()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

