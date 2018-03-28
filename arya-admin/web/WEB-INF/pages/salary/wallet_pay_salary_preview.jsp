<%--
  Created by IntelliJ IDEA.
  User: xiexuefeng
  Date: 2018/3/21
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<%--<link href="<%=contextPath%>/css/operation/wallet_pay_salary_apply.css" rel="stylesheet"/>--%>
<script src="<%=contextPath%>/js/salary/wallet_pay_salary_preview.js"></script>

<div class="wallet_pay_salary_preview container">

    <div class="head border-bottom">
        <div class="txt">钱包发薪导入</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">发薪月份:</span>
                <select  class="form-control m-b projectId">
                    <option value="2018">2018</option>
                    <option value="2019">2019</option>
                    <option value="2020">2020</option>
                    <option value="2021">2021</option>
                </select>

                <select  class="form-control m-b projectId">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">所属项目：</span>
                <select  class="form-control m-b projectId"  id="projectId">

                </select>
            </div>

            <div class="upload_container">

                <span class="txt">选择本地文件</span>

                <span class="file_path"></span>

                <div class="btn_list">
                    <div class="btn btn-sm btn-primary btn_upload"
                         onclick="wallet_pay_salary_preview.ChooseFileClick()">
                        选择文件
                    </div>

                    <div class="btn btn-sm btn-default btn_sure"
                         onclick="wallet_pay_salary_preview.ImportFileCalc()">确定
                    </div>


                    <div class="btn-group">
                        <button class="btn btn-sm btn-primary dropdown-toggle"
                                type="button" data-toggle="dropdown" onclick="wallet_pay_salary_preview.TemplateDown()">
                            下载模板
                        </button>
                    </div>

                </div>
            </div>

            <form class="upload_excel" enctype="multipart/form-data" style="display: none;">
                <input type="file" name="file" accept=".xls" >
            </form>

        </div>

        <div class="table_container">
            <table id="tb_wallet_pay_salary_preview"></table>
        </div>

    </div>

</div>
