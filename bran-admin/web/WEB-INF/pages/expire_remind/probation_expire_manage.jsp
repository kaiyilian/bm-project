<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/13
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/expire_remind/probation_expire_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/expire_remind/probation_expire_manage.js"></script>

<div class="probation_expire_container container">

	<div class="head border-bottom">
		<div class="txt">试用期到期</div>
	</div>

	<div class="content">

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item"></td>
					<td>序号</td>
					<td>姓名</td>
					<td>试用期开始时间</td>
					<td>试用期结束时间</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="employee_item" data-employeeId="1" data-version="2">--%>
				<%--<td class="isTrue" onclick="probation_expire_manage.ChooseItem(this)">--%>
				<%--<img src="image/UnChoose.png"/>--%>
				<%--</td>--%>
				<%--<td class="employee_no">1</td>--%>
				<%--<td class="employee_name">1</td>--%>
				<%--<td class="employee_probation_begin_time">1</td>--%>
				<%--<td class="employee_probation_end_time">1</td>--%>
				<%--<td class="btn_operate">--%>
				<%--<span class="btn btn-sm btn-success btn_accept"--%>
				<%--onclick="probation_expire_manage.EmployeeAcceptOnly(this)">受理</span>--%>
				<%--</td>--%>

				<%--</tr>--%>
				</tbody>
			</table>
		</div>

	</div>

	<div class="foot">
		<div class="choose_container" onclick="probation_expire_manage.chooseAll()">
			<img src="image/UnChoose.png"/>
			<span>全选</span>
		</div>

		<div class="btn_list">

			<div class="btn btn-sm btn-default btn_accept"
				 onclick="probation_expire_manage.empAcceptMore()">受理
			</div>

		</div>

	</div>

	<div class="pager_container">
		<%--<ul class="pagenation" style="float:right;"></ul>--%>
	</div>

</div>
