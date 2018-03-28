<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/12
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/employee/emp_prospective.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/employee/emp_prospective.js"></script>

<div class="import_container emp_import_container">

    <div class="item btn_import col-xs-2" onclick="emp_import.empImportModalShow()">
        <i class="glyphicon glyphicon-open"></i>
        <div class="txt">导入员工信息</div>
    </div>

    <div class="item btn_add col-xs-2" onclick="emp_add.empAddModalShow();">
        <%--<i class="glyphicon glyphicon-plus"></i>--%>
        <i class="icon icon-emp_add"></i>
        <div class="txt">新增员工信息</div>
    </div>

</div>

<div class="container emp_prospective_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">待入职员工</div>
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
                <input class="form-control layer-date beginTime" id="prospective_beginTime"
                       placeholder="YYYY-MM-DD">
                <span class="input-group-addon">至</span>
                <input class="form-control layer-date endTime" id="prospective_endTime"
                       placeholder="YYYY-MM-DD">
            </div>

            <span class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字：</span>
				<input type="text" class="form-control searchCondition" placeholder="姓名/手机号">
				<span class="add-on"><i class="icon-remove"></i></span>
			</span>

            <div class="col-xs-3 item btn_list">

                <div class="btn btn-sm btn-orange btn-search"
                     onclick="emp_prospective.btnSearchClick();">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">

            <table class="table table-striped table-bordered table-hover dataTable">

                <thead>
                <tr>
                    <td class="choose_item"></td>
                    <td>姓名</td>
                    <td>手机号</td>
                    <td>入职时间</td>
                    <td>职位</td>
                    <td>班组</td>
                    <td>工段</td>
                    <td>部门</td>
                    <td>资料进度</td>
                    <td>来源</td>
                    <td>入职状态</td>
                    <td>备注</td>
                    <td>操作</td>
                </tr>
                </thead>

                <tbody>

                <%--<tr class="item emp_item" data-employeeId="12">--%>
                <%--<td class="choose_item" onclick="emp_prospective.chooseItem(this)">--%>
                <%--<img src="image/UnChoose.png"/>--%>
                <%--</td>--%>
                <%--<td class="emp_no">1</td>--%>
                <%--<td class="emp_name">张三</td>--%>
                <%--<td class="emp_phone">手机号</td>--%>
                <%--<td class="emp_check_in_time">入职时间</td>--%>
                <%--<td class="emp_post">职位</td>--%>
                <%--<td class="emp_workShift">班组</td>--%>
                <%--<td class="emp_workLine">工段</td>--%>
                <%--<td class="emp_dept">部门</td>--%>
                <%--<td class="emp_infoCompleteDegree">40%</td>--%>
                <%--<td class="remark">备注</td>--%>
                <%--<td class="emp_physical_info">已提交</td>--%>
                <%--<td class="operate">--%>
                <%--<span class="btn btn-sm btn-success btn_modify"--%>
                <%--onclick="employeeAdd.EmployeeInfoModify()">修改</span>--%>
                <%--<span class="btn btn-sm btn-success btn_del"--%>
                <%--onclick="employeeAdd.EmployeeDelId(12)">删除</span>--%>
                <%--<span class="btn btn-sm btn-success btn_"--%>
                <%--onclick="employeeAdd.EmployeeDelId(12)">同意入职</span>--%>
                <%--</td>--%>
                <%--</tr>--%>

                </tbody>

            </table>

        </div>

        <div class="pager_container"></div>

    </div>

    <div class="foot">

        <div class="choose_container" onclick="emp_prospective.chooseAll()">
            <img src="image/UnChoose.png"/>
            <span>全选</span>
        </div>

        <div class="btn_list">

            <div class="btn btn-sm btn-default btn_agree"
                 onclick="emp_prospective.entryMore()">同意入职
            </div>

            <div class="btn btn-sm btn-default btn_del"
                 onclick="emp_prospective.empDelMore()">删除
            </div>

            <div class="btn btn-sm btn-success btn_export"
                 onclick="emp_prospective.exportEmpList()">
                导出全部
            </div>

        </div>

    </div>


</div>

<div class="modal fade emp_import_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:800px;">
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

                        <div class="btn btn_upload" onclick="emp_import.chooseFileClick()">
                            <i class="glyphicon glyphicon-open"></i>
                            上传文件
                        </div>

                        <div class="txt">上传Excel</div>

                    </div>

                    <div class="info_prompt">

                        <div class="txt">温馨提示</div>

                        <div class="txt_1">
                            1.后缀名为xls或xlsx
                        </div>

                        <div class="txt_1">
                            2.查看或下载
                            <span onclick="emp_import.empImportTemplateDown()">
								待入职员工导入模板
							</span>
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

                    <div class="emp_import_table">

                        <table class="table table-striped table-bordered table-hover dataTable">
                            <thead>
                            <tr>
                                <td style="width:50px;">序号</td>
                                <td style="width:62px;">姓名</td>
                                <td>职位</td>
                                <td>班组</td>
                                <td>工段</td>
                                <td>部门</td>
                                <td>手机号</td>
                                <td>入职时间</td>
                            </tr>
                            </thead>
                            <tbody>

                            <%--<tr class="item emp_item">--%>
                            <%--<td class="emp_no">1</td>--%>
                            <%--<td class="emp_name">张三</td>--%>
                            <%--<td class="emp_post">职位</td>--%>
                            <%--<td class="emp_workShift">班组</td>--%>
                            <%--<td class="emp_workLine">工段</td>--%>
                            <%--<td class="emp_dept">部门</td>--%>
                            <%--<td class="emp_phone">手机号</td>--%>
                            <%--<td class="emp_check_in_time">入职时间</td>--%>

                            <%--</tr>--%>

                            </tbody>
                        </table>

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

                <div class="btn btn-white btn_prev" onclick="emp_import.stepPrev()">
                    上一步
                </div>

                <div class="btn btn-orange btn_next" onclick="emp_import.stepNext()">
                    下一步
                </div>

                <div class="btn btn-orange btn_confirm" onclick="emp_import.empImportConfirm()">
                    完成
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal fade emp_add_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">待入职员工新增</h4>
            </div>
            <div class="modal-body">

                <table class="table table-striped table-bordered table-hover dataTable">
                    <thead>
                    <tr>
                        <td style="width:50px;">序号</td>
                        <td>姓名</td>
                        <td>手机号</td>
                        <td>入职时间</td>
                        <td>职位</td>
                        <td>班组</td>
                        <td>工段</td>
                        <td>部门</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>

                    <%--<tr class="item emp_item">--%>
                    <%--<td class="emp_no" style="width:50px;">1</td>--%>
                    <%--<td>姓名</td>--%>
                    <%--<td>手机号</td>--%>
                    <%--<td>入职时间</td>--%>
                    <%--<td>职位</td>--%>
                    <%--<td>班组</td>--%>
                    <%--<td>工段</td>--%>
                    <%--<td>部门</td>--%>
                    <%--<td class="operate">--%>

                    <%--<button class="btn btn-sm btn-success btn_modify">--%>
                    <%--修改--%>
                    <%--</button>--%>

                    <%--<button class="btn btn-sm btn-success btn_del">--%>
                    <%--删除--%>
                    <%--</button>--%>

                    <%--</td>--%>
                    <%--</tr>--%>

                    </tbody>
                </table>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>

                <div class="btn btn-orange btn_add" onclick="emp_add.empAddLineShow()">
                    新增
                </div>

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

