<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/5/11
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--zTree插件--%>
<link href="<%=contextPath%>/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script type="text/javascript" src="<%=contextPath%>/js/plugins/ztree/js/jquery.ztree.all.min.js"></script>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/setting/department.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/setting/department.js"></script>

<div class="container department_setting_container">

	<div class="head border-bottom">
		<i class="icon icon-dept"></i>
		<div class="txt">组织架构</div>
	</div>

	<div class="content" style="">

		<div class="btn btn-orange" onclick="department.addRootNode()">新增部门</div>

		<ul id="tree" class="ztree" style="width:100%"></ul>
	</div>
</div>


<div class="modal fade dept_add_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增部门</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<span class="col-xs-3 txt">部门名称：</span>
                    <span class="col-xs-9 txtInfo dept_name">
                        <input type="text" class="form-control" placeholder="请输入部门名称" maxlength="32">
                    </span>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<div class="btn btn-orange" onclick="department.deptAddSave()">
					确定
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade dept_edit_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">编辑部门</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<span class="col-xs-3 txt">部门名称：</span>
                    <span class="col-xs-9 txtInfo dept_name">
                        <input type="text" class="form-control" placeholder="请输入部门名称" maxlength="32">
                    </span>
				</div>

			</div>
			<div class="modal-footer">
				<%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
				<div class="btn btn-orange" onclick="department.deptEditSave()">
					确定
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade dept_export_container" role="dialog" style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog modal-lg" role="document" style="width: 100%; height: 100%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">导出预览</h4>
			</div>
			<div class="modal-body">

				<div id="dept_export_div" class="row" style="width: 1200px;height: 800px">

				</div>

			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

