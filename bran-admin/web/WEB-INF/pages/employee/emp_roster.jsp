<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/30
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--bootstrap Table fixed--%>
<link href="<%=contextPath%>/js/plugins/bootstrap-table-fixed-column/bootstrap-table-fixed-columns.css"
      rel="stylesheet">
<script src="<%=contextPath%>/js/plugins/bootstrap-table-fixed-column/bootstrap-table-fixed-columns.js"></script>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/employee/emp_roster.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/employee/emp_roster.js"></script>

<div class="import_container emp_roster_import_container">

    <div class="item col-xs-2 btn_import" onclick="emp_roster_import.empImportModalShow()">
        <i class="glyphicon glyphicon-open"></i>
        <div class="txt">批量导入</div>
    </div>

    <div class="item col-xs-2">
        <i class="glyphicon glyphicon-open"></i>
        <div class="txt" onclick="emp_roster.attendPut()">同步考勤机</div>
    </div>

</div>

<div class="emp_roster_container container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">花名册</div>
    </div>

    <div class="content">

        <div class="search_container">

			<span class="input-group col-xs-3 item dept_container">
				<span class="input-group-addon">部门：</span>
				<select class="form-control"></select>
			</span>

            <span class="input-group col-xs-3 item workLine_container">
				<span class="input-group-addon">工段：</span>
				<select class="form-control"></select>
			</span>

            <span class="input-group col-xs-3 item workShift_container">
				<span class="input-group-addon">班组：</span>
				<select class="form-control"></select>
			</span>

            <span class="input-group col-xs-3 item post_container">
				<span class="input-group-addon">职位：</span>
				<select class="form-control"></select>
			</span>

            <div class="input-group col-xs-6 item">
                <span class="input-group-addon">入职时间:</span>
                <input class="form-control layer-date beginTime" id="roster_beginTime"
                       placeholder="YYYY-MM-DD">
                <span class="input-group-addon">至</span>
                <input class="form-control layer-date endTime" id="roster_endTime"
                       placeholder="YYYY-MM-DD">
            </div>

            <%--<span class="input-group col-xs-3 item">--%>
            <%--<span class="input-group-addon">快速搜索：</span>--%>
            <%--<input type="text" class="form-control searchCondition" placeholder="员工姓名/工号/工段">--%>
            <%--<span class="add-on"><i class="icon-remove"></i></span>--%>
            <%--</span>--%>
            <div class="input-group col-xs-6 item">
                <span class="input-group-addon">快速搜索：</span>
                <select data-placeholder="请选择员工姓名、工号或工段" multiple
                        class="chosen-select user_list">
                    <%--<option>1</option>--%>
                </select>
            </div>

            <div class="col-xs-3 item btn_list">

                <div class="btn btn-sm btn-orange" onclick="emp_roster.btnSearchClick();">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container" style="overflow: inherit;">
            <table id="tb_emp_roster"></table>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-sm  btn-success btn_export" onclick="emp_roster.exportEmpList()">导出全部</div>
            <div class="btn btn-sm btn-default btn_down" onclick="emp_roster.enclosureDown()">下载附件</div>
            <div class="btn btn-sm btn-default btn_dismiss" onclick="emp_roster.dismissMore()">退工</div>
            <div class="btn btn-sm btn-default btn_renew" onclick="emp_roster.renewModalShow()">续签</div>

            <%--<div class="btn btn-sm btn-success btn_renew" onclick="emp_roster.getSelectAll()">--%>
                <%--获取选中的员工--%>
            <%--</div>--%>

        </div>

    </div>

</div>

<div class="modal fade emp_roster_import_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">导入本地Excel</h4>
            </div>
            <div class="modal-body">

                <ul class="nav nav-pills nav-justified step step-round">

                    <li class="active">
                        <a>上传Excel</a>
                    </li>

                    <li>
                        <a>数据预览</a>
                    </li>

                </ul>

                <div class="step_1">

                    <div class="upload_container center">

                        <div class="btn btn_upload" onclick="emp_roster_import.chooseFileClick()">
                            <i class="glyphicon glyphicon-open"></i>
                            上传文件
                        </div>

                        <div class="txt">上传Excel</div>

                    </div>

                    <div class="info_prompt">

                        <div class="txt">温馨提示</div>

                        <div class="txt_1">

                            <p> 1.导入前请先在【员工管理-员工配置】中完成配置；</p>
                            <p> 2.前9项：姓名、身份证号、注册账号、工号前缀、工号、部门、职位、班组、工段必填；</p>
                            <p> 3.身份证、注册手机号码在花名册中不能重复；</p>
                            <p>4.工号前缀：必须为系统中已经设置的前缀，没有前缀请填无；工号为纯数字，组合起来为完整工号；</p>
                            <p>5.部门、职位、班组、工段必须与系统中预先设置的相同；</p>
                            <p>6.后方选填的可以为空，如果填了请按照正确的格式填写；</p>
                            <p>7.请不要修改导入列的名称，或者您可以删除选填的列；</p>
                            <p>
                                8.查看或下载
                                <span onclick="emp_roster_import.empImportTemplateDown()">
								花名册导入模板
							    </span>
                            </p>

                        </div>

                    </div>

                </div>

                <div class="step_2">

                    <div class="row">
						<span class="icon">
							<img src='image/error_prompt.png'>
						</span>
                        <span class="clr_red">红色字</span>
                        <span>代表导入出错，请修改后 重新导入</span>
                    </div>

                    <div class="table_container">
                        <table id="tb_emp_roster_import"></table>
                    </div>

                    <div class="prompt">
                        本次导入
                        <span class='total_count clr_ff6600'>100</span>
                        条，有
                        <span class='error_count clr_ff6600'>5</span>
                        条错误，请修改后重新导入！
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>

                <div class="btn btn-white btn_prev" onclick="emp_roster_import.stepPrev()">
                    上一步
                </div>

                <div class="btn btn-orange btn_next" onclick="emp_roster_import.stepNext()">
                    下一步
                </div>

                <div class="btn btn-orange btn_confirm" onclick="emp_roster_import.empImportConfirm()">
                    完成
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade emp_renew_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">续约</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <span class="col-xs-3">合同开始日期：</span>
                    <span class="col-xs-9 begin_time">
                        <input class="form-control layer-date" placeholder="YYYY-MM-DD"
                               id="emp_contract_begin_time">
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3">合同结束日期：</span>
                    <span class="col-xs-9 end_time">
                        <input class="form-control layer-date" placeholder="YYYY-MM-DD"
                               id="emp_contract_end_time">
                    </span>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <div class="btn btn-orange btn_renew" onclick="emp_roster.renew()">续约
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade emp_dismiss_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">退工</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <span class="col-xs-3">退工时间：</span>
                    <span class="col-xs-9 dismiss_time">
                        <input class="form-control layer-date" placeholder="YYYY-MM-DD"
                               id="emp_dismiss_time" style="width:100%;max-width: 100%;">
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3">退工原因：</span>
                    <span class="col-xs-9 leave_reason_list">
                        <select class="form-control">
                            <%--<option>dafsaf</option>--%>
                        </select>
                    </span>
                </div>

                <div class="row">
                    <span class="col-xs-3">内容：</span>
                    <span class="col-xs-9 dismiss_remark">
                        <textarea class="form-control" maxlength="140"></textarea>
                    </span>
                    <%--<span class="col-xs-10 dismiss_remark" contenteditable="true"--%>
                    <%--onfocus="emp_roster.txtHide(this)"--%>
                    <%--onblur="emp_roster.CheckContent(this)">--%>
                    <%--<span class="txt">请输入内容</span>--%>
                    <%--</span>--%>
                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <div class="btn btn-orange btn_dismiss" onclick="emp_roster.dismiss()">
                    退工
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade emp_roster_info_modify_modal" role="dialog"
     style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑员工信息</h4>
            </div>
            <div class="modal-body" style="padding:20px 0;width: 100%;height:400px;overflow: auto;">

                <div class="col-xs-12 block basic_info">

                    <div class="border-bottom head">
                        基本信息
                    </div>

                    <div class="content show">

                        <div class="row">

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">注册账号：</div>
                                <div class="col-xs-8 txt_info register_account">
                                    <input type="text" class="form-control" maxlength="11" disabled
                                           onkeyup="this.value=this.value.replace(/\D/g,'')"/>
                                </div>

                                <img src="image/icon_contract/icon_msg.png" class="icon_msg"
                                     data-html="true" data-toggle="tooltip" data-placement="right" title=""
                                     data-original-title="<p' style='width:200px;'>员工注册app的手机账号，用于登录APP和查看薪资；
                                     一旦绑定，由员工自行在app上修改</p>">

                            </div>

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">部门：</div>
                                <div class="col-xs-9 txt_info department_list">
                                    <select class="form-control">
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">姓名：</div>
                                <div class="col-xs-9 txt_info emp_name">
                                    <input type="text" class="form-control" maxlength="8"
                                           placeholder="请输入员工姓名"/>
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">职位：</div>
                                <div class="col-xs-9 txt_info position_list">
                                    <select class="form-control">
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">身份证：</div>
                                <div class="col-xs-9 txt_info emp_idCard">
                                    <input type="text" class="form-control" maxlength="18"
                                           placeholder="请输入身份证号"/>
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">班组：</div>
                                <div class="col-xs-9 txt_info work_shift_list">
                                    <select class="form-control">
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">工号：</div>
                                <div class="col-xs-9 txt_info employee_no_begin">
                                    <select class="form-control col-xs-6"
                                            onchange="emp_roster_modify.WorkSnChange()">
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                    </select>

                                    <span class="col-xs-1">+</span>

                                    <input type="text" class="form-control col-xs-5" maxlength="8"
                                           onblur="emp_roster_modify.CheckWorkSnIsRight()"
                                           onkeyup="this.value=this.value.replace(/\D/g,'')">
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <div class="col-xs-3 txt">工段：</div>
                                <div class="col-xs-9 txt_info work_line_list">
                                    <select class="form-control">
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                        <%--<option>1</option>--%>
                                    </select>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-xs-12 block personal_info">

                    <div class="border-bottom head">
                        个人信息

                        <div class="txt">
                            展开详细↓
                        </div>

                    </div>

                    <div class="content">

                        <div class="col-xs-6 left_side">

                            <div class="row">

                                <div class="col-xs-3 txt">性别：</div>
                                <div class="col-xs-9 txt_info emp_sex">
                                    <select class="form-control">
                                        <option value="">性别</option>
                                        <option value="0">男</option>
                                        <option value="1">女</option>
                                    </select>
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">婚姻状况：</div>
                                <div class="col-xs-9 txt_info emp_marry">
                                    <select class="form-control">
                                        <option value="">婚姻状况</option>
                                        <option value="1">未婚</option>
                                        <option value="2">已婚</option>
                                        <option value="3">已婚已育</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">政治面貌：</div>
                                <div class="col-xs-9 txt_info emp_political_status">
                                    <select class="form-control">
                                        <option value="">政治面貌</option>
                                        <option value="0">党员</option>
                                        <option value="1">团员</option>
                                        <option value="2">群众</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">民族：</div>
                                <div class="col-xs-9 txt_info emp_nation">
                                    <input type="text" class="form-control" maxlength="8"
                                           placeholder="请输入民族">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">户籍地：</div>
                                <div class="col-xs-9 txt_info emp_register_address">
                                    <input type="text" class="form-control" maxlength="50"
                                           placeholder="请输入户籍地">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">出生年月：</div>
                                <div class="col-xs-9 txt_info emp_birth_date">
                                    <input class="form-control layer-date" id="emp_birth_date"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#emp_birth_date'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">社保类型：</div>
                                <div class="col-xs-9 txt_info emp_soin_type">
                                    <input type="text" class="form-control" maxlength="50"
                                           placeholder="请输入社保类型">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">紧急联系人：</div>
                                <div class="col-xs-9 txt_info emp_urgent_contact">
                                    <input type="text" class="form-control" maxlength="8"
                                           placeholder="紧急联系人姓名"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">紧急联系手机：</div>
                                <div class="col-xs-9 txt_info emp_urgent_contact_phone">
                                    <input type="text" class="form-control" maxlength="11"
                                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                                           placeholder="紧急联系人手机"/>
                                </div>
                            </div>

                        </div>

                        <div class="col-xs-6 right_side">

                            <div class="row">
                                <div class="col-xs-3 txt">班车点：</div>
                                <div class="col-xs-9 txt_info bus_address">
                                    <input type="text" class="form-control" maxlength="40"
                                           placeholder="员工乘坐的班车路线"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">联系方式：</div>
                                <div class="col-xs-9 txt_info emp_contact_phone">
                                    <input type="text" class="form-control" maxlength="11"
                                           placeholder="请输入联系方式"
                                           onkeyup="this.value=this.value.replace(/\D/g,'')">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">居住地：</div>
                                <div class="col-xs-9 txt_info" style="padding:0;">

                                    <div class="col-xs-4">
                                        <select class="form-control province_list"
                                                onchange="emp_roster_modify.ProvinceChange()">
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                        </select>
                                    </div>

                                    <div class="col-xs-4">
                                        <select class="form-control city_list"
                                                onchange="emp_roster_modify.CityChange()">
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                        </select>
                                    </div>

                                    <div class="col-xs-4">
                                        <select class="form-control area_list">
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                            <%--<option>1</option>--%>
                                        </select>
                                    </div>

                                </div>
                                <div class="col-xs-9 txt_info col-xs-offset-3 emp_address" style="margin-top: 10px;">
                                    <input type="text" class="form-control" maxlength="50"
                                           placeholder="员工居住详细街道地址"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">开户行信息：</div>
                                <div class="col-xs-9 txt_info emp_bank_info">
                                    <input type="text" class="form-control"
                                           maxlength="20" placeholder="开户行信息，具体到支行"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">银行卡号：</div>
                                <div class="col-xs-9 txt_info emp_bank_card_no">
                                    <input type="text" class="form-control" maxlength="21"
                                           placeholder="银行卡号"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt txt_1">身份证有效期开始时间：</div>
                                <div class="col-xs-offset-3 col-xs-9 txt_info idCard_validity_start_time">
                                    <input class="form-control layer-date" id="idCard_validity_start_time"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#idCard_validity_start_time'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt txt_1">身份证有效期结束时间：</div>
                                <div class="col-xs-offset-3 col-xs-6 txt_info idCard_validity_end_time">
                                    <input class="form-control layer-date" id="idCard_validity_end_time"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#idCard_validity_end_time'})">
                                </div>
                                <div class="col-xs-3 txt_info idCard_validity_end_time_permanent">
                                    <img src="image/UnChoose.png">
                                    <span>长期</span>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-xs-12 block edu_info">

                    <div class="border-bottom head">
                        教育信息

                        <div class="txt">
                            展开详细↓
                        </div>

                    </div>

                    <div class="content">

                        <div class="col-xs-6 left_side">

                            <div class="row">
                                <div class="col-xs-3 txt">毕业学校：</div>
                                <div class="col-xs-9 txt_info emp_graduate_school">
                                    <input class="form-control" placeholder="最高毕业学校名称">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">文化程度：</div>
                                <div class="col-xs-9 txt_info emp_edu_degree">
                                    <select class="form-control">
                                        <option value="">学历</option>
                                        <option value="0">小学</option>
                                        <option value="1">初中</option>
                                        <option value="2">高中</option>
                                        <option value="3">中专</option>
                                        <option value="4">大专</option>
                                        <option value="5">本科</option>
                                        <option value="6">硕士研究生</option>
                                        <option value="7">博士研究生</option>
                                        <option value="8">其他</option>
                                    </select>
                                </div>
                            </div>

                        </div>

                        <div class="col-xs-6 right_side">

                            <div class="row">
                                <div class="col-xs-3 txt">毕业时间：</div>
                                <div class="col-xs-9 txt_info emp_graduate_time">
                                    <input class="form-control layer-date" id="emp_graduate_time"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#emp_graduate_time'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">专业类别：</div>
                                <div class="col-xs-9 txt_info emp_major_category">
                                    <input type="text" class="form-control" placeholder="请输入专业类别">
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-xs-12 block contract_info">

                    <div class="border-bottom head">
                        合同信息

                        <div class="txt">
                            展开详细↓
                        </div>

                    </div>

                    <div class="content">

                        <div class="col-xs-6 left_side">

                            <div class="row">
                                <div class="col-xs-3 txt">面试时间：</div>
                                <div class="col-xs-9 txt_info emp_interview_date">
                                    <input class="form-control layer-date" id="interview_date"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#interview_date'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">合同开始：</div>
                                <div class="col-xs-9 txt_info emp_contract_start_time">
                                    <input class="form-control layer-date" id="contract_start_time"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#contract_start_time'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">供应来源：</div>
                                <div class="col-xs-9 txt_info emp_interview_source">
                                    <input type="text" class="form-control" maxlength="20"
                                           placeholder="例：外部供应商、直招"/>
                                </div>
                            </div>

                        </div>

                        <div class="col-xs-6 right_side">

                            <div class="row">
                                <div class="col-xs-3 txt">入职时间：</div>
                                <div class="col-xs-9 txt_info emp_check_in_date">
                                    <input class="form-control layer-date" id="check_in_date"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#check_in_date'})">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">合同结束：</div>
                                <div class="col-xs-6 txt_info emp_contract_end_time">
                                    <input class="form-control layer-date" id="contract_end_time"
                                           placeholder="YYYY-MM-DD"
                                           onclick="laydate({elem:'#contract_end_time'})">
                                </div>
                                <div class="col-xs-3 txt_info emp_contract_end_time_permanent">
                                    <img src="image/UnChoose.png">
                                    <span>无期限</span>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3 txt">员工性质：</div>
                                <div class="col-xs-9 txt_info emp_nature">
                                    <input type="text" class="form-control" maxlength="20"
                                           placeholder="例：长期员工、短期员工、派遣员工"/>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

                <div class="col-xs-12 block custom_info">

                    <div class="border-bottom head">
                        其他信息
                        <div class="txt">
                            展开详细↓
                        </div>
                    </div>

                    <div class="content clr_d87c11">


                    </div>

                </div>

            </div>
            <div class="modal-footer">

                <div class="prompt">

                    <div class="clr_d87c11">
                        1.橙色代表花名册自定义选项
                    </div>

                    <div class="clr_red" style="color:red;">
                        2."*" 红色星号项必填
                    </div>

                </div>

                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <div class="btn btn-orange btn_save" onclick="emp_roster_modify.empInfoSaveByModify()">
                    保存
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

