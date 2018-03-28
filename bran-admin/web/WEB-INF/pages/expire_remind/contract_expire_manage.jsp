<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/13
  Time: 16:08
  To change this template use File | Settings | File Templates.
    合同到期
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/expire_remind/contract_expire_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/expire_remind/contract_expire_manage.js"></script>

<div class="contract_expire_container container">

	<div class="head border-bottom">
		<div class="txt">合同到期</div>
	</div>

	<div class="content">

		<div class="table_container">

			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item"></td>
					<td>序号</td>
					<td>姓名</td>
					<td>合同开始时间</td>
					<td>合同结束时间</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="employee_item" data-employeeId="1" data-version="2">--%>
				<%--<td class="isTrue" onclick="contract_expire_manage.ChooseItem(this)">--%>
				<%--<img src="image/UnChoose.png"/>--%>
				<%--</td>--%>
				<%--<td class="employee_no">1</td>--%>
				<%--<td class="employee_name">1</td>--%>
				<%--<td class="employee_contract_begin_time">1</td>--%>
				<%--<td class="employee_contract_end_time">1</td>--%>
				<%--<td class="btn_operate">--%>
				<%--<span class="btn btn-sm btn-success btn_renew"--%>
				<%--onclick="contract_expire_manage.EmployeeRenewOnly(this)">续约</span>--%>
				<%--</td>--%>

				<%--</tr>--%>
				</tbody>
			</table>

		</div>

		<div class="pager_container">
			<%--<ul class="pagenation" style="float:right;"></ul>--%>
		</div>

	</div>

	<div class="foot">
		<div class="choose_container" onclick="contract_expire_manage.chooseAll()">
			<img src="image/UnChoose.png"/>
			<span>全选</span>
		</div>

		<div class="btn_list">

			<div class="btn btn-sm btn-default btn_renew"
				 onclick="contract_expire_manage.empRenewMore()">
				续约
			</div>

		</div>

	</div>

</div>

<div class="modal fade contract_expire_renew_modal" role="dialog"
	 style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">续约</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<span class="col-xs-3 txt">合同开始日期：</span>
                    <span class="col-xs-9 txtInfo begin_time">
                        <input class="form-control layer-date" placeholder="YYYY-MM-DD"
							   id="contract_expire_beginTime">
                    </span>
				</div>

				<div class="row">
					<span class="col-xs-3 txt">合同结束日期：</span>
                    <span class="col-xs-9 txtInfo end_time">
                        <input class="form-control layer-date" placeholder="YYYY-MM-DD"
							   id="contract_expire_endTime">
                    </span>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<div class="btn btn-orange btn_renew" onclick="contract_expire_manage.renewSure()">
					续约
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
