<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/10/11
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/expire_remind/entry_expire_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/expire_remind/entry_expire_manage.js"></script>

<div class="entry_expire_container container">

	<div class="head border-bottom">
		<div class="txt">同意入职</div>
	</div>

	<div class="content">

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td class="choose_item"></td>
					<td style="width:50px;">序号</td>
					<td class="order_item">姓名</td>
					<td class="order_item">手机号</td>
					<td class="order_item">入职时间</td>
					<td class="order_item">职位</td>
					<td class="order_item">班组</td>
					<td class="order_item">工段</td>
					<td class="order_item">部门</td>
					<td>备注</td>
					<td>体检信息</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>

				<%--<tr class="item employee_item" data-employeeId="12" data-version="">--%>
					<%--<td class="choose_item" onclick="entry_expire_manage.ChooseItem(this)">--%>
						<%--<img src="image/UnChoose.png"/>--%>
					<%--</td>--%>
					<%--<td class="employee_no">1</td>--%>
					<%--<td class="employee_name">张三</td>--%>
					<%--<td class="employee_post">工程师</td>--%>
					<%--<td class="employee_team">A组</td>--%>
					<%--<td class="employee_workLine">工段</td>--%>
					<%--<td class="employee_dept">开发部</td>--%>
					<%--<td class="employee_phone">13115111111</td>--%>
					<%--<td class="employee_check_in_time">2015-03-12</td>--%>
					<%--<td class="remark"></td>--%>
					<%--<td class="physical_examination"></td>--%>
					<%--<td class="employee_operate">--%>
						<%--<span class="btn btn-sm btn-success btn_agree"--%>
							  <%--onclick="entry_expire_manage.entryModalShow()">同意入职</span>--%>

						<%--<span class="btn btn-sm btn-success btn_detail"--%>
							  <%--onclick="">查看详情</span>--%>
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
		<div class="choose_container" onclick="entry_expire_manage.chooseAll()">
			<img src="image/UnChoose.png"/>
			<span>全选</span>
		</div>

		<div class="btn_list">

			<div class="btn btn-sm btn-default btn_agree"
				 onclick="entry_expire_manage.entryMore()">
				同意入职
			</div>

		</div>

	</div>

</div>
