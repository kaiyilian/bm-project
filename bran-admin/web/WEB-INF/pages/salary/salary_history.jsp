<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/4
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/salary/el_payroll.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/salary/step.css" rel="stylesheet">

<link href="<%=contextPath%>/css/bran/salary/salary_history.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/salary/salary_history.js"></script>

<div class="container salary_history_container">

    <div class="head border-bottom">
        <i class="icon icon-salary"></i>
        <div class="txt">薪资条</div>
    </div>

    <div class="content">

        <div class="table_container">
            <table id="tb_salary_history"></table>
        </div>

        <div class="salary_validity_container">

            薪资单有效期
            <div class="salary_validity">
                <%--3个月--%>
            </div>
            ，
            <div class="c_567ec0" onclick="salary_history.salaryValidityModalShow()">设置</div>
        </div>

    </div>

</div>

<div class="modal fade salary_validity_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">薪资单有效期</h4>
            </div>
            <div class="modal-body" style="padding:50px 0;">

                <div class="row">

                    <div class="col-xs-4" style="line-height:34px;text-align: right;">薪资单有效期：</div>
                    <div class="col-xs-6 salary_validity">
                        <select class="form-control">
                            <option value="0">永久</option>
                            <option value="1">1个月</option>
                            <option value="2">2个月</option>
                            <option value="6">6个月</option>
                            <option value="12">一年</option>
                        </select>
                    </div>

                    <div class="col-xs-offset-4 col-xs-6 c_orange" style="margin-top: 30px;line-height:20px;">
                        超时之后，员工将无法查看历史薪资单
                    </div>

                </div>

            </div>
            <div class="modal-footer" style="text-align: center;">
                <!--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-orange" onclick="salary_history.salaryValiditySave()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

