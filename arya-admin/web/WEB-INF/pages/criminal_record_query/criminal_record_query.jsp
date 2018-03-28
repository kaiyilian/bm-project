<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/3/9
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/criminal_record_query/criminal_record_query.css" rel="stylesheet">
<script src="<%=contextPath%>/js/criminal_record_query/criminal_record_query.js"></script>

<div class="import_container criminal_record_batch_query_container">

	<div class="item btn_batch_query col-xs-2" onclick="criminal_record_batch_query.ModalShow()">
		<i class="glyphicon glyphicon-open"></i>
		<div class="txt">批量查询</div>
	</div>

</div>

<div class="container criminal_record_query_container">

	<div class="head border-bottom">
		<div class="txt">犯罪记录查询</div>
	</div>

	<div class="content">

		<div class="search_container">

			<div class="input-group col-xs-4 item">
				<span class="input-group-addon">姓名</span>
				<input class="form-control user_name" placeholder="请输入姓名" maxlength="8">
			</div>

			<div class="input-group col-xs-4 item">
				<span class="input-group-addon">身份证号</span>
				<input class="form-control idCardNo" placeholder="请输入身份证号(15~18位)"
					   maxlength="18">
			</div>

			<div class="btn_list">

				<div class="btn btn-sm btn-primary btn_search"
					 onclick="criminal_record_query_manage.criminalRecordQuery()">
					查询
				</div>

			</div>

		</div>

		<div class="table_container">
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td>姓名</td>
					<td>身份证号</td>
					<td>犯罪</td>
					<td>查询状态</td>
				</tr>
				</thead>
				<tbody>
				<%--<tr class="item user_item" data-id="">--%>
				<%--<td class="user_name">姓名</td>--%>
				<%--<td class="user_idCardNo">身份证号</td>--%>
				<%--<td class="user_criminal_record">无</td>--%>
				<%--<td class="query_status">成功</td>--%>
				<%--</tr>--%>
				</tbody>
			</table>
		</div>

	</div>

</div>

<div class="modal fade criminal_record_batch_query_modal"
	 style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog" style="width:800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">批量查询</h4>
			</div>
			<div class="modal-body">

				<div class="upload_container">

					<span class="txt">选择本地文件</span>

					<span class="file_path"></span>

					<div class="btn_list">

						<div class="btn btn-sm btn-primary btn_upload"
							 onclick="criminal_record_batch_query.chooseFileClick()">
							选择文件
						</div>

						<form class="upload_file" enctype="multipart/form-data" style="display: none;">
							<input type="file" name="file" accept=".xlsx"
								   onchange="criminal_record_batch_query.chooseFile(this)">
						</form>

					</div>

				</div>

				<div class="info_prompt">

					<div class="txt">温馨提示</div>

					<div class="txt_1">
						1.Excel(07版本)文件导入
					</div>

					<div class="txt_1">
						2.查看或下载
						<div class="clr_1c84c6 template_down"
							 onclick="criminal_record_batch_query.templateDown()">
							犯罪记录查询导入模板
						</div>
					</div>

				</div>

			</div>
			<div class="modal-footer">

				<button type="button" class="btn btn-primary btn_confirm"
						onclick="criminal_record_batch_query.criminalRecordBatchExport()">
					确认
				</button>


			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

