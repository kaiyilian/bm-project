<%--
  Created by IntelliJ IDEA.
  User: bumu-zhz
  Date: 2015/11/4
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=contextPath%>/css/salary/salary_import.css" rel="stylesheet" type="text/css"/>
<script src="<%=contextPath%>/js/bootstrap-table.js"></script>
<script src="<%=contextPath%>/js/ajaxfileupload.js"></script>
<script src="<%=contextPath%>/js/salary/salary_import.js"></script>


<div class="row animated fadeIn">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>导入配置</h5>
        </div>
        <div class="ibox-content profile-content">
            <div class="row">
                <label class="col-sm-1 control-label text-right" for="company_name">公司</label>
                <div class="col-sm-4">
                    <select id="company_name" class="form-control">
                        <option></option>
                    </select>
                </div>
                <label class="col-sm-1 control-label text-right" for="fileSalary">文件</label>
                <div class="col-sm-4">
                    <input id="fileSalary" type="file" name="file" class="form-control">
                </div>

                <div class="col-sm-1">
                    <button id="import_but" class="btn btn-primary" type="submit"
                            onclick="salary_import.import_but();">
                        验证
                    </button>
                </div>
                <div class="col-sm-1">
                    <button id="confirm_but" class="btn btn-primary" type="submit"
                            onclick="salary_import.confirm_but();">导入
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>导入结果</h5>
        </div>
        <div class="ibox-content profile-content">
            <div class="container-fluid">
                <div class="row-fluid">
                    <table id="table_import_salary" class="table table-striped table-bordered table-hover">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--------------------------添加/修改信息的弹出层---------------------------->
<div id="add" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <table id="table_import_salary_confirm" class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
</div>