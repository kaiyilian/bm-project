<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/12
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/employee/emp_leave.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/employee/emp_leave.js"></script>

<div class="container emp_leave_container">

    <div class="head border-bottom">
        <i class="icon icon-emp"></i>
        <div class="txt">离职员工列表</div>
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
                <span class="input-group-addon">离职时间:</span>
                <input class="form-control layer-date beginTime" id="emp_leave_beginTime"
                       placeholder="YYYY-MM-DD">
                <span class="input-group-addon">至</span>
                <input class="form-control layer-date endTime" id="emp_leave_endTime"
                       placeholder="YYYY-MM-DD">
            </div>

            <span class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字：</span>
				<input type="text" class="form-control searchCondition" placeholder="姓名/手机号">
				<span class="add-on"><i class="icon-remove"></i></span>
			</span>

            <div class="col-xs-3 item btn_list">

                <div class="btn btn-sm btn-orange"
                     onclick="emp_leave.btnSearchClick();">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_emp_leave" style="min-width:1200px;"></table>
        </div>

        <%--<div class="pager_container">--%>
        <%--<ul class="pagenation" style="float:right;"></ul>--%>
        <%--</div>--%>

    </div>

    <div class="foot">
        <%--<div class="choose_container" onclick="emp_leave.chooseAll()">--%>
        <%--<img src="image/UnChoose.png"/>--%>
        <%--<span>全选</span>--%>
        <%--</div>--%>

        <div class="btn_list">

            <div class="btn btn-sm btn-success btn_export" onclick="emp_leave.exportLeaveList()">
                导出全部
            </div>

            <div class="btn btn-sm btn-default btn_del" onclick="emp_leave.empDel()">删除
            </div>

        </div>

    </div>

</div>
