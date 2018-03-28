<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/12
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/banner_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/banner_manage.js"></script>


<div class="advertise_manage_container container">

	<div class="head border-bottom">
		<div class="txt">广告管理</div>
	</div>

	<div class="content">

		<div class="table_container" >
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
				<tr>
					<td>缩略图</td>
					<td>hint</td>
					<td>是否启用</td>
					<td>跳转URL</td>
					<td>跳转类型</td>
					<td>设备类型</td>
					<td>目标区域</td>
					<td>最低版本</td>
					<td>最高版本</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody>

				<%--<tr class="advert_item item">--%>
				<%--<td class="thumb_img">--%>
				<%--<img src="img/empty_img.png">--%>
				<%--</td>--%>
				<%--<td class="hint">提示信息</td>--%>
				<%--<td class="isEnable">启用</td>--%>
				<%--<td class="url">http://www.baidu.com</td>--%>
				<%--<td class="url_type">--%>
				<%--&lt;%&ndash;跳转类型&ndash;%&gt;--%>
				<%--</td>--%>
				<%--<td class="device_type">--%>
				<%--&lt;%&ndash;ios,android&ndash;%&gt;--%>
				<%--</td>--%>
				<%--<td class="district">全国</td>--%>
				<%--<td class="min"></td>--%>
				<%--<td class="max"></td>--%>
				<%--<td class="operate">--%>
				<%--<button class="btn btn-info btn-sm btn_up"--%>
				<%--onclick="advert_manage.AdvertGoUp(this)">--%>
				<%--上--%>
				<%--</button>--%>

				<%--<button class="btn btn-info btn-sm btn_down"--%>
				<%--onclick="advert_manage.AdvertGoDown(this)">--%>
				<%--下--%>
				<%--</button>--%>

				<%--<button class="btn btn-primary btn-sm btn_enable"--%>
				<%--onclick="advert_manage.AdvertEnable(this)">--%>
				<%--启用--%>
				<%--</button>--%>

				<%--<button class="btn btn-danger btn-sm btn_disable"--%>
				<%--onclick="advert_manage.AdvertDisable(this)">--%>
				<%--禁用--%>
				<%--</button>--%>

				<%--<button class="btn btn-primary btn-sm btn_modify"--%>
				<%--onclick="advert_manage.ShowEditAdvertModal(this)">--%>
				<%--编辑--%>
				<%--</button>--%>

				<%--</td>--%>
				<%--</tr>--%>


				</tbody>
			</table>
		</div>

	</div>

	<div class="foot">

		<div class="btn_list">

			<div class="btn btn-sm btn-primary btn_add"
				 onclick="advert_manage.AdvertAddModalShow();">
				新增
			</div>

		</div>

	</div>

	<div class="pager_container">
		<ul class="pagenation" style="float:right;"></ul>
	</div>

</div>


<div class="modal fade advert_info_modal" role="dialog" style="background-color: rgba(0,0,0,0.50);">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增广告</h4>
			</div>
			<div class="modal-body form-horizontal">

				<div class="advert_img_add" onclick="advert_manage.ChooseImgClick();"
					 data-fileName="">
					<img src="img/icon_advert_img_add.png"/>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">hint</label>
					<%--<input id="adv_id_input" type="text" class="form-control arya-hide" style="display: none">--%>
					<div class="col-sm-10 txtInfo hint_add">
						<input type="text" class="form-control" placeholder="请输入提示信息" maxlength="256">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">跳转URL</label>

					<div class="col-sm-10 txtInfo jump_url_add">
						<input type="text" class="form-control" placeholder="请输入跳转URL" maxlength="256">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">跳转URL类型</label>

					<div class="col-sm-10 txtInfo jump_url_type_add">
						<select id="jump_type_select" class="form-control">
							<%--<option>1</option>--%>
							<%--<option>1</option>--%>
							<%--<option>1</option>--%>
							<%--<option>1</option>--%>
						</select>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">设备类型</label>

					<div class="col-sm-10 txtInfo device_type_add">
						<%--<button class="btn btn-sm btn-default btn_ios" data-id="1">ios</button>--%>
						<%--<button class="btn btn-sm btn-default btn_android" data-id="2">android</button>--%>
						<%--<button class="btn btn-sm btn-default btn_winphone" data-id="4">winphone</button>--%>
						<%--<button class="btn btn-sm btn-default btn_wechat" data-id="8">wechat</button>--%>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">目标区域</label>

					<div class="col-sm-10 txtInfo">全国</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">最低版本</label>

					<div class="col-sm-10 txtInfo min_version_add">
						<input type="text" class="form-control" placeholder="请输入最低版本"
							   maxlength="32" onkeyup="this.value=this.value.replace(/\D/g,'')">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">最高版本</label>

					<div class="col-sm-10 txtInfo max_version_add">
						<input type="text" class="form-control" placeholder="请输入最高版本"
							   maxlength="32" onkeyup="this.value=this.value.replace(/\D/g,'')">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 txt">是否启用</label>

					<div class="col-sm-10 txtInfo isEnable_add">
						<div class="switch">
							<div class="onoffswitch">
								<input type="checkbox" name="fixednavbar"
									   class="onoffswitch-checkbox" id="fixednavbar">
								<label class="onoffswitch-label" for="fixednavbar">
									<span class="onoffswitch-inner"></span>
									<span class="onoffswitch-switch"></span>
								</label>
							</div>
						</div>
					</div>
				</div>

				<div id="adv_delete" class="form-group row arya-hide">
					<div class="col-sm-2">
						<button type="button" class="btn btn-danger" onclick="advert_manage.deleteAdv()">
							删除
						</button>
					</div>

				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="advert_manage.AdvertAddSave()">保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

