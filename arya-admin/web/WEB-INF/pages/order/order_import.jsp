<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/8/2
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<script src="<%=contextPath%>/js/order/order_import.js"></script>
<link href="<%=contextPath%>/css/order/order_import.css" rel="stylesheet"/>

<div class="order_import_container container">

	<div class="head border-bottom">
		<div class="txt">导入订单</div>
	</div>

	<div class="content">

		<div class="upload_container">

			<span class="txt">选择本地文件</span>

			<span class="file_path"></span>

			<div class="btn_list">
				<div class="btn btn-sm btn-primary btn_upload"
					 onclick="order_import.ChooseFileClick()">
					选择文件
				</div>

				<div class="btn btn-sm btn-default btn_calc"
					 onclick="order_import.ImportFileCalc()">计算
				</div>
				<div class="btn btn-sm btn-default btn_sure"
					 onclick="order_import.ChooseFileConfirm()">确定
				</div>

				<%--<div class="btn btn-sm btn-primary btn_down"--%>
				<%--onclick="order_import.TemplateDown()">下载模板--%>
				<%--</div>--%>

				<div class="btn-group">
					<button class="btn btn-sm btn-primary dropdown-toggle"
							type="button" data-toggle="dropdown">
						下载模板
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><a onclick="order_import.TemplateDown('personal')">个人模板</a></li>
						<li><a onclick="order_import.TemplateDown('company')">公司模板</a></li>
					</ul>
				</div>


			</div>
		</div>

		<div class="line">
			<div class="msg_prompt" onclick="order_import.msgPrompt()">信息提示</div>

			<div class="batch_no_container">
				<span>批次号：</span>
				<span class="batch_no">暂无批次</span>
			</div>

			<span class="btn btn-sm btn-primary btn_export" onclick="order_import.exportFile()">导出</span>
		</div>

		<div class="table_container">

			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td>缴纳主体</td>
					<td>姓名</td>
					<td>身份证</td>
					<td>参保地区</td>
					<td>参保类型</td>
					<td>服务月份</td>
					<td>缴纳月份</td>
					<td>社保编号</td>
					<td>社保基数</td>
					<td>公积金编号</td>
					<td>公积金基数</td>
					<td>公积金比例(%)</td>
					<td>户口性质</td>
					<td>户籍地址</td>
					<td>服务费(收账)</td>
					<td>服务费(出账)</td>
					<td>总计(收账)</td>
					<td>总计(出账)</td>
					<td>业务员</td>
					<td>供应商</td>
					<td class="postpone_month">顺延月</td>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>

		</div>

	</div>

	<form class="upload_excel" enctype="multipart/form-data" style="display: none;">
		<input type="file" name="file" accept=".xlsx" onchange="order_import.ChooseFile(this)">
	</form>

</div>


<%--记录窗口--%>
<div class="modal inmodal" id="order_bill_import_status_modal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog" style="height: 80%">
		<div class="modal-content animated fadeIn" id="order_bill_import_status_text_content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span
						aria-hidden="true">&times;</span><span
						class="sr-only">Close</span></button>
				<h4 class="modal-title"><span id="feed_back_title"></span>反馈</h4>
			</div>
			<div class="modal-body" style="height: 550px;">
				<textarea id="order_bill_import_status_text" style="width: 100%;height: 500px;">

				</textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

