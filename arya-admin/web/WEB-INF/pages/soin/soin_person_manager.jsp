<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: CuiMengxin
  Date: 2015/11/16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="../layout/table_page_header.jsp" %>

<link href="<%=contextPath%>/css/plugins/blueimp/css/blueimp-gallery.min.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
<!-- Chosen -->
<link href="<%=contextPath%>/css/plugins/chosen/chosen.css" rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/chosen/chosen.jquery.js"></script>

<div class="row animated fadeIn">
    <div class="col-sm-12">
        <%--过滤条件开始--%>
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="row">
                    <div class="col-md-5">
                        <span class="font-bold">参保人状态：</span>
                        <select id="soin_person_manager_status_chosen" data-placeholder="选择参保人状态" class="chosen-select"
                                multiple style="width:250px;"
                                tabindex="4">
                            <c:forEach var="item" items="${person_status}">
                                <option value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-xs btn-primary" style="margin-top: 5px"
                                onclick="refreshList(soinPersonManager)">查询
                        </button>
                    </div>
                    <div class="col-md-12 hr-line-dashed"></div>
                    <div id="soin_person_list_content" class="col-sm-12">
                        <table id="soin_person_list" class="display" cellspacing="0" style="width: 100%">
                        </table>
                    </div>
                    <div class="col-sm-12">
                        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                onclick="refreshDataTable(soinPersonManager)"> 刷新
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%--参保人详情模态窗口--%>
<div class="modal fade" id="dlg_soin_person" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><span id="dlg_soin_person_label"></span>参保人详情</h4>
            </div>
            <div class="modal-body row">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="input-group m-b">
                                <span class="input-group-addon">姓名</span>
                                <input id="soin_person_name" name="firstname" class="form-control" type="text"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group m-b">
                                <span class="input-group-addon">手机号</span>
                                <input id="soin_person_phone_no" name="lastname" class="form-control" type="text"
                                       class="valid" readonly="readonly">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="input-group m-b">
                                <span class="input-group-addon">订单数</span>
                                <input id="soin_person_order_count" class="form-control" type="text"
                                       readonly="readonly">
                            </div>
                        </div>
                        <div class="col-sm-4 text-left">
                            <div class="input-group m-b" style="margin-top: 7px">
                                <span>创建时间：</span>
                                <small class="text-muted" id="soin_person_create_time"></small>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="input-group m-b">
                                <span class="input-group-addon">身份证号</span>
                                <input id="soin_person_idcard_no" name="username" class="form-control" type="text"
                                       class="error" readonly="readonly">
                            </div>
                        </div>

                        <div class="col-sm-5">
                            <div class="input-group m-b">
                                <span class="input-group-addon">户口所在地区</span>
                                <input id="soin_person_hukou_name" class="form-control" type="text"
                                       readonly="readonly">
                                <input id="soin_person_hukou" class="form-control" type="hidden">
                            </div>
                        </div>

                        <div class="col-sm-3">
                            <div class="input-group m-b">
                                <span class="input-group-addon">户口类型</span>
                                <input id="soin_person_hukou_type_name" name="confirm_password" class="form-control"
                                       type="text"
                                       readonly="readonly">
                                <input id="soin_person_hukou_type" name="confirm_password" class="form-control"
                                       type="hidden">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="lightBoxGallery">
                            <div class="col-sm-6">
                                <a data-gallery="" id="soin_person_id_card_front_url_big">
                                    <img alt="image" class="img-responsive" id="soin_person_id_card_front_url">
                                </a>
                            </div>
                            <div class="col-sm-6">
                                <a data-gallery="" id="soin_person_id_card_back_url_big">
                                    <img alt="image" class="img-responsive" id="soin_person_id_card_back_url">
                                </a>
                            </div>
                            <div id="blueimp-gallery" class="blueimp-gallery">
                                <div class="slides"></div>
                                <h3 class="title"></h3>
                                <a class="prev">‹</a>
                                <a class="next">›</a>
                                <a class="close">×</a>
                                <a class="play-pause"></a>
                                <ol class="indicator"></ol>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-9 control-label"></label>
                        <div class="col-sm-3">
                            <select class="form-control m-b" name="account" id="soin_person_verify_status">
                                <c:forEach var="item" items="${person_status}">
                                    <option value="${item.key}">${item.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <%--<div id="label_err_msg" class="alert alert-danger">Hello Error!</div>--%>
                <button type="button" class="btn btn-default" onclick="clearForm()" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="soinPersonManager.updateSoinPersonDetail()">提交
                </button>
            </div>
        </div>
    </div>
</div>

<script src="<%=contextPath%>/js/soin/soin_person_manager.js"></script>