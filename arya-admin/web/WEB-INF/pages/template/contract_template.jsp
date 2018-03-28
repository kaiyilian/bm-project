<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/5/27
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<link href="<%=contextPath%>/css/template/contract_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/template/contract_manage.js"></script>


<div class="contract_manage_container container">

    <div class="head border-bottom">
        <div class="txt">模板管理</div>
    </div>

    <div class="content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs nav_temp" role="tablist">

            <li role="presentation" class="">

                <a href="#contract_temp" role="tab" data-toggle="tab" data-href="contract_temp">
                    合同模板
                </a>

            </li>

            <li role="presentation" class="">

                <a href="#seal_temp" role="tab" data-toggle="tab" data-href="seal_temp">
                    印章模板
                </a>

            </li>

        </ul>

        <!-- Tab panes -->
        <div class="tab-content">

            <div role="tabpanel" id="contract_temp" class="tab-pane fade in ">

                <div class="search_container">

                    <div class="input-group col-xs-6 item">
                        <span class="input-group-addon">企业列表：</span>
                        <select data-placeholder="请选择公司" multiple
                                class="corp_list chosen-select form-control">
                            <%--<option>aa</option>--%>
                            <%--<option>bb</option>--%>
                            <%--<option>cc</option>--%>
                        </select>
                    </div>

                    <div class="btn_list">

                        <div class="btn btn-sm btn-primary btn_search"
                             onclick="contract_temp.btnSearchClick()">
                            查询
                        </div>

                        <div class="btn btn-sm btn-primary btn_add"
                             onclick="contract_temp.addContractTempModalShow()">
                            新增合同模板
                        </div>

                    </div>

                </div>

                <div class="table_container">
                    <table class="table table-striped table-bordered table-hover dataTable">
                        <thead>
                        <tr>
                            <td>企业名称</td>
                            <td>上传时间</td>
                            <td>合同类型</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>

                        <tr class="item" data-id="" data-url="">
                            <td class="corpName">企业名称</td>
                            <td class="uploadTime"></td>
                            <td class="contractType"></td>
                            <td class="operate">
                                <div class="btn btn-sm btn-primary btn_preview">预览</div>
                                <div class="btn btn-sm btn-primary btn_modify">编辑</div>
                                <div class="btn btn-sm btn-primary btn_del">删除</div>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </div>

                <div class="pager_container">
                    <%--<ul class="pagenation" style="float:right;"></ul>--%>
                </div>

            </div>

            <div role="tabpanel" id="seal_temp" class="tab-pane fade">

                <div class="search_container">

                    <div class="input-group col-xs-6 item">
                        <span class="input-group-addon">企业列表：</span>
                        <select data-placeholder="请选择公司" multiple
                                class="corp_list chosen-select form-control">
                            <%--<option>aa</option>--%>
                            <%--<option>bb</option>--%>
                            <%--<option>cc</option>--%>
                        </select>
                    </div>

                    <div class="btn_list">

                        <div class="btn btn-sm btn-primary btn_search"
                             onclick="seal_temp.btnSearchClick()">
                            查询
                        </div>

                        <div class="btn btn-sm btn-primary btn_add"
                             onclick="seal_temp.addSealTempModalShow()">
                            新增印章模板
                        </div>

                    </div>

                </div>

                <div class="seal_list_container">

                    <div class="col-xs-6 seal_item">

                        <div class="col-xs-6 seal_img">
                            <img src="img/2.jpg">
                        </div>

                        <div class="col-xs-6 seal_info">
                            <div class="corp_name">印章名称</div>
                            <div class="date">印章名称</div>
                            <div class="btn btn-primary btn-sm btn-danger btn_del">删除</div>
                        </div>

                    </div>

                </div>

                <div class="pager_container">
                    <%--<ul class="pagenation" style="float:right;"></ul>--%>
                </div>

            </div>

        </div>

    </div>

</div>

<div class="modal fade contract_temp_info_modal" style="background-color:rgba(0,0,0,0.50);overflow-y:auto; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">合同模板信息</h4>
            </div>
            <div class="modal-body">

                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">企业名称：</div>

                        <div class="col-xs-10">
                            <select class="form-control corp_list">
                                <%--<option>1</option>--%>
                            </select>
                        </div>

                    </div>

                </div>

                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">合同类型：</div>

                        <div class="col-xs-10">
                            <select class="form-control contract_type">
                                <%--<option>劳动合同</option>--%>
                            </select>
                        </div>

                    </div>

                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <span class="col-xs-2 line-height-34">模板ID：</span>

                        <span class="col-xs-10">
							<input type="text" class="form-control temp_id"
                                   placeholder="请输入模板ID" maxlength="32"/>
						</span>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">上传模板：</div>

                        <div class="col-xs-10 temp_operate" data-id="" data-url="">

                            <div class="btn btn-sm btn-primary btn_upload"
                                 onclick="contract_temp.ChooseFileClick()">
                                上传
                            </div>

                            <div class="btn btn-sm btn-primary btn_preview"
                                 onclick="contract_temp.tempPreviewInModal()">
                                预览
                            </div>

                            <div class="btn_clear"
                                 onclick="contract_temp.initTempOperate()">
                                清除
                            </div>

                            <div class="txt">仅支持HTML文档格式</div>

                        </div>

                    </div>
                </div>

                <div class="row margin_b_10">
                    <div class="col-xs-12">

                        <div class="col-xs-12 line-height-34">合同填写项：</div>

                        <div class="table_container">
                            <table class="table table-striped table-bordered table-hover dataTable">
                                <thead>
                                <tr>
                                    <td>参数</td>
                                    <td>命名</td>
                                    <td>填写方</td>
                                    <td>操作</td>
                                </tr>
                                </thead>
                                <tbody>

                                <%--<tr class="item">--%>
                                <%--<td class="loops_id">--%>
                                <%--<div>--%>
                                <%--<input class="form-control" placeholder="请输入loops_id">--%>
                                <%--</div>--%>
                                <%--</td>--%>
                                <%--<td class="loops_name">--%>
                                <%--<div>--%>
                                <%--<input class="form-control" placeholder="请输入loops_name">--%>
                                <%--</div>--%>
                                <%--</td>--%>
                                <%--<td class="loops_type">--%>
                                <%--<div>--%>
                                <%--<select class="form-control">--%>
                                <%--<option>甲方</option>--%>
                                <%--<option>乙方</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <%--</td>--%>
                                <%--<td class="operate">--%>
                                <%--<div class="btn btn-sm btn-danger btn_del">删除</div>--%>
                                <%--</td>--%>
                                <%--</tr>--%>

                                </tbody>
                            </table>
                        </div>

                        <div class="btn btn-sm btn-primary btn_add"
                             onclick="contract_temp.addContractItem()">
                            新增
                        </div>

                    </div>
                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        onclick="contract_temp.contractTempSave();">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade seal_temp_info_modal" style="background-color:rgba(0,0,0,0.50);overflow-y:auto; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加印章模板</h4>
            </div>
            <div class="modal-body">

                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">企业名称：</div>

                        <div class="col-xs-10">
                            <select class="form-control corp_list">
                                <%--<option>1</option>--%>
                            </select>
                        </div>

                    </div>

                </div>

                <div class="row margin_b_10">

                    <div class="col-xs-12">

                        <div class="col-xs-2 line-height-34">本地上传：</div>

                        <div class="col-xs-10 img_list">

                            <%--<div class="col-xs-6 col-md-3 item">--%>
                            <%--<a class="thumbnail">--%>
                            <%--<img src="img/img_add_default.png" alt="">--%>
                            <%--</a>--%>
                            <%--<div class="icon_del">--%>
                            <%--<img src="img/icon_setting_dept_del_active.png">--%>
                            <%--</div>--%>
                            <%--</div>--%>

                            <div class="col-xs-6 col-md-3 img_add_item" onclick="seal_temp.ChooseFileClick()">
                                <a class="thumbnail">
                                    <img src="img/img_add_default.png" alt="">
                                </a>
                            </div>

                        </div>

                        <div class="col-xs-offset-2 col-xs-10 txt" style="color:#999;">
                            仅支持PNG图片且保证图片底色透明，大小建议在150px*150px
                        </div>

                    </div>

                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary"
                        onclick="seal_temp.sealTempSave();">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
