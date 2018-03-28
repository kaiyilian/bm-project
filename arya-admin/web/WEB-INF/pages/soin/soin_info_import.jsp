<%--
  User: CuiMengxin
  Date: 2015/11/9
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/css/soin/soin_import.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">

<script src="<%=contextPath%>/js/bootstrap-treeview.js"></script>
<script src="<%=contextPath%>/js/bootstrap-table.js"></script>
<script src="<%=contextPath%>/js/ajaxfileupload.js"></script>
<script src="<%=contextPath%>/js/soin/soin.js"></script>
<script src="<%=contextPath%>/js/soin/soin_info_import.js"></script>

<div class="row animated fadeIn">
    <div class="col-sm-12">
        <%--配置开始--%>
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>导入配置</h5>
                <button type="button" class="btn btn-xs btn-primary" id="soinimport_import" style="margin-left: 20px"
                        onclick="soin_import.import_but();">验证
                </button>

                <button type="button" class="btn btn-xs btn-primary" id="soinimport_confirm" style="margin-left: 20px"
                        onclick="soin_import.confirm_but();">导入
                </button>
            </div>
            <div class="ibox-content profile-content">
                <div class="row">
                    <div id="div_company" class="col-sm-4">
                        <sapn class="font-bold">1.选择组织机构（公司）</sapn>
                        <button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
                                data-target="#add_soinimport_company_modal">选择
                        </button>
                        <p id="p_company_name"></p>

                        <p id="p_company_id" style="display: none"></p>
                    </div>


                    <div id="div_soin_type" class="col-sm-4">
                        <sapn class="font-bold">2.选择城市地区社保类型</sapn>
                        <button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
                                data-target="#chose_soin_type_modal"
                                onclick="soin_import.getDistricTreeInSoinInfoImport()">选择
                        </button>
                        <p id="p_distirct_soin_type"></p>

                        <p id="p_distirct_id" style="display: none"></p>

                        <p id="p_soin_type_id" style="display: none"></p>
                    </div>


                    <div id="div_upload_file" class="col-sm-4">
                        <sapn class="font-bold">3.选择导入文件</sapn>
                        <button type="button" class="btn btn-xs btn-primary" data-toggle="modal"
                                data-target="#add_soinimport_file_modal">选择
                        </button>
                        <p id="p_upload_file_location"></p>
                    </div>

                </div>
            </div>
        </div>

        <%--导入开始--%>
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>导入结果</h5>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-md-12">
                        <table id="table_import_soin" class="table table-striped table-bordered table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--选择社保类型的Modal窗口--%>
<div class="modal inmodal" id="chose_soin_type_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">选择参保类型</h4>
            </div>
            <div class="modal-body">
                <div id="soin_info_import_district_tree"></div>
                <div id="soin_info_import_district_tree_hud"></div>
                <div id="div_soin_types_in_soin_info_import">
                    <div id="div_soin_types_hud"></div>
                    <div id="div_soin_type_btns"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<%--公司选择--%>
<div class="modal inmodal" id="add_soinimport_company_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">选择组织机构（公司）</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">公司列表</label>

                    <div class="col-sm-10">
                        <select class="form-control m-b" name="account" id="soinimport_company">
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="soin_import.addSoinCompany()">确定</button>
            </div>
        </div>
    </div>
</div>

<%--文件选择--%>
<div class="modal inmodal" id="add_soinimport_file_modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">选择文件</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">文件</label>

                    <div class="col-sm-10">
                        <input id="fileSoin" type="file" name="upload_file" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="soin_import.addSoinFile()">确定</button>
            </div>
        </div>
    </div>
</div>

<!--------------------------添加/修改信息的弹出层---------------------------->
<div id="addSoin" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <table id="table_import_soin_confirm" class="table table-striped table-bordered table-hover">

            </table>
        </div>
    </div>
</div>
<%--<!-- Bootstrap-Treeview plugin javascript -->--%>
<%--<script src="js/bootstrap-treeview.js"></script>--%>
<%--</body>--%>

<%--</html>--%>
