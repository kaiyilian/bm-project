<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/4/27
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--地区列表--%>
<script src="<%=contextPath%>/js/arya/districts/districts.js"></script>
<%--本页面--%>
<link href="<%=contextPath%>/css/corporation/corp_service_entry.css" rel="stylesheet">
<script src="<%=contextPath%>/js/corporation/corp_service_entry.js"></script>


<div class="container corp_container corp_service_entry_container"
	 onclick="$('.corp_service_entry_container .corp_district_list').remove()">

	<div class="col-xs-2 aryaZtreeContainer">

		<div class="ibox-title">
			<h5>企业列表</h5>
		</div>

		<div class="ztree_search_container">
			<input type="text" class="form-control ztree_search" placeholder="请输入公司名称">
		</div>

		<div class="ztreeContainer">
			<ul class="ztree" id="corp_group_entry_tree"></ul>
		</div>

	</div>

	<div class="col-xs-10 corp_content">

		<form class="col-xs-12 block corp_container_form"
			  enctype="multipart/form-data">

			<input type="hidden" name="id" class="corp_id">
			<input type="hidden" name="parent_id" class="corp_parent_id">

			<div class="row">

				<div class="input-group col-xs-4 corp_property_container">
					<span class="input-group-addon">公司性质：</span>
					<select class="form-control editable corp_property" name="type">
						<%--<c:forEach var="item" items="${corp_type}">--%>
						<%--<option value="${item.key}">${item.value}</option>--%>
						<%--</c:forEach>--%>
					</select>
				</div>

				<div class="input-group col-xs-4">
					<span class="input-group-addon">邮箱：</span>
					<input type="email" class="form-control editable corp_contract_email"
						   name="contact_mail">
				</div>

			</div>

			<div class="row">

				<div class="input-group col-xs-4 corp_address_container" onclick="event.stopPropagation()">
					<span class="input-group-addon">地区：</span>

					<input type="text" class="form-control editable corp_address"
						   onclick="corp_service_entry.showDistrictList()" readonly>

					<input type="hidden" class="corp_address_id" name="district_id"/>

				</div>

				<div class="input-group col-xs-8">
					<span class="input-group-addon">详细地址：</span>
					<input type="text" class="form-control editable corp_detail_address"
						   name="address">
				</div>

			</div>

			<div class="row">

				<div class="input-group col-xs-4">
					<span class="input-group-addon">经度：</span>
					<input type="text" class="form-control editable corp_longitude" name="longitude">
				</div>

				<div class="input-group col-xs-4">
					<span class="input-group-addon">纬度：</span>
					<input type="text" class="form-control editable corp_latitude" name="latitude">
				</div>

				<div class="input-group col-xs-4">
					<span class="input-group-addon">创建时间：</span>
					<input type="text" class="form-control editable corp_create_time" readonly>
				</div>

			</div>

			<div class="row">

				<div class="input-group col-xs-4">
					<span class="input-group-addon">企业入职码：</span>
					<input type="text" class="form-control editable corp_checkin_code"
						   readonly name="corp_checkin_code">
				</div>

				<div class="input-group col-xs-4">
					<span class="input-group-addon">短信发送时间：</span>
					<input type="text" class="form-control editable salarySmsHours"
						   onkeyup="this.value=this.value.replace(/\D/g,'')"
						   onblur="corp_service_entry.checkSalarySmsTime()"
						   name="salarySmsHours" placeholder="1到48之间的整数(H)">
				</div>

			</div>

			<div class="row">

				<div class="input-group col-xs-12">
					<span class="input-group-addon">描述：</span>
					<textarea class="form-control editable corp_desc" name="desc"></textarea>
				</div>

			</div>

			<div class="row">

				<div class="col-xs-6 img_upload_container corp_logo_container">
					<div class="col-xs-4 txt">公司LOGO：</div>
					<div class="col-xs-8">
						<label class="img_upload_lbl corp_logo">点击选择logo</label>
						<%--<span class="img_upload_spn corp_logo_show" data-src=""></span>--%>
					</div>

					<input accept="image/png,image/jpg"
						   name="corp_logo_file" type="file"
						   class="form-control corp_logo_file" style="display: none">

				</div>

				<div class="col-xs-6 img_upload_container corp_img_container">

					<div class="col-xs-4 txt">公司图片：</div>
					<div class="col-xs-8">
						<label class="img_upload_lbl corp_img">点击选择图片</label>
						<%--<span class="img_upload_spn corp_img_show" data-src=""></span>--%>
					</div>

					<input accept="image/png,image/jpg"
						   name="corp_image_file" type="file"
						   class="form-control corp_img_file" style="display: none;">

				</div>

			</div>

			<%--<div class="row">--%>

			<%--<div class="col-xs-6 img_upload_container corp_license_container">--%>
			<%--<div class="col-xs-4 txt">营业执照：</div>--%>
			<%--<div class="col-xs-8">--%>
			<%--<label class="img_upload_lbl corp_license">点击选择营业执照</label>--%>
			<%--&lt;%&ndash;<span class="img_upload_spn corp_license_show" data-src=""></span>&ndash;%&gt;--%>
			<%--</div>--%>

			<%--<input accept="image/png,image/jpg"--%>
			<%--name="corp_license_file" type="file"--%>
			<%--class="form-control corp_license_file" style="display: none">--%>

			<%--</div>--%>

			<%--<div class="input-group col-xs-6">--%>
			<%--<div class="input-group-addon">营业执照编码：</div>--%>
			<%--<input type="text" class="form-control editable corp_licenses_code"--%>
			<%--name="corp_license_code" placeholder="请输入营业执照编码">--%>
			<%--</div>--%>

			<%--</div>--%>

			<div class="row btn_operate">

				<div data-value="8" class="btn btn-primary btn_save"
					 onclick="corp_service_entry.corpInfoSave()">
					保存
				</div>

				<div data-value="16" class="btn btn-primary btn_modify"
					 onclick="corp_service_entry.corpInfoModify()">
					编辑
				</div>

				<div data-value="32" class="btn btn-primary btn_cancel"
					 onclick="corp_service_entry.corpInfoCancelByModify()">
					取消
				</div>

			</div>

		</form>

	</div>

</div>