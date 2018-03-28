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
<script src="<%=contextPath%>/js/salary/wallet_pay_salary_apply.js"></script>

<div class="wallet_pay_salary_apply_container container">

    <div class="head border-bottom">
        <div class="txt">钱包发现批量转账</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">开始时间：</span>
                <input class="form-control layer-date beginTime" placeholder="YYYY-MM-DD"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">结束时间：</span>
                <input class="form-control layer-date endTime" placeholder="YYYY-MM-DD"
                       onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
            </div>



            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">所属项目：</span>
                <select  class="form-control m-b projectId">
                    <option value="">全部</option>
                    <c:forEach items="${projectList}" var="project">
                        <option value="${project.branCorpId}">${project.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="btn_list">

                <div class="input-group col-xs-3 item">
                    <span class="input-group-addon">交易状态：</span>
                    <select  class="form-control m-b projectId">
                        <option value="">全部</option>
                        <c:forEach items="${tradeList}" var="trade">
                            <option value="${trade.code}">${trade.desc}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input-group col-xs-3 item">
                    <span class="input-group-addon">批次号：</span>
                    <input type="text" class="form-control batchNo" placeholder="批次号" maxlength="50"
                           onkeyup="this.value=this.value.replace(/\D/g,'')">
                    <span class="add-on"><i class="icon-remove"></i></span>
                </div>

                <div class="btn btn-sm btn-primary btn_search"
                     onclick="wallet_pay_salary_apply.btnSearchClick()">查询
                </div>

                <div class="btn btn-sm btn-primary btn_export"
                     onclick="wallet_pay_salary_apply.reset()">
                    重置
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_wallet_pay_salary_apply"></table>

        </div>

    </div>

</div>
