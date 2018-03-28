<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/11/10
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/soin/soin_base_info_manage.css" rel="stylesheet">
<script src="<%=contextPath%>/js/soin/soin_base_info_manage.js"></script>

<div class="row animated fadeIn soin_baseInfo_container">

	<div class="col-sm-3 soinBaseZtreeContainer">

		<div class="ibox float-e-margins" style="margin:0;">

			<div class="ibox-title">
				<h5>参保地区</h5>
			</div>

			<div class="row col-xs-12 operate_list">

				<div class="btn btn-sm btn-primary btn_add"
					 onclick="soin_base_info_manage.initNoSoinAreaList()">
					<i class="fa fa-plus"></i>
					添加
				</div>

				<div class="btn btn-sm btn-danger btn_del"
					 onclick="soin_base_info_manage.soinAreaDel()">
					<i class="fa fa-minus"></i>
					删除
				</div>

				<div class="btn btn-sm btn-primary btn_down"
					 onclick="soin_base_info_manage.soinAreaExport()">
					<i class="fa fa-download"></i>
					下载
				</div>

			</div>

			<div id="soin_area_choose" class="ibox-content profile-content">
				<div id="soin_area_tree_hud"></div>
				<ul id="soin_area_tree" class="ztree"></ul>
			</div>

			<div class="up_list_container">

				<div class="btn btn-sm btn-primary btn_up">
					向上级并列
				</div>

				<div class="btn btn-sm btn-primary btn_up_cancel">
					取消并列
				</div>

			</div>

		</div>

	</div>

	<div class="col-sm-9 soin_base_info_container">

		<div class="row soin_type_container">

			<div class="name">
				社保类型
			</div>

			<div class="btn_list_container">

				<div class="btn btn-sm btn-success f_right btn_add"
					 onclick="soin_base_info_manage.soinTypeAddModalShow()">
					增加类型
				</div>

				<div class="btn btn-sm btn-danger f_right btn_del"
					 onclick="soin_base_info_manage.soinTypeDel()">
					删除
				</div>

				<div class="soin_type_list btn_list">

					<%--<div class="btn btn-sm btn-primary">社保类型item</div>--%>
					<%--<div class="btn btn-sm btn-primary">社保类型item</div>--%>
					<%--<div class="btn btn-sm btn-primary">社保类型item</div>--%>

				</div>

			</div>

			<div class="soin_type_detail_container">

				<div class="name">
					类型详情
				</div>

				<div class="soin_type_detail">

					<div class="row">

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">类型名称</span>
								<input type="text" class="form-control soin_type_name" name="soin_type_name"/>
							</div>
							<%--<input type="text" id="soin_type_id" hidden="hidden" readonly="readonly" name="type_id">--%>
						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">管理费</span>
								<input type="number" class="form-control soin_fees">
                                <span class="input-group-addon">
									<i class="fa fa-yen"></i>
								</span>
							</div>
						</div>

						<div class="col-md-4">

							<div class="input-group">

								<span class="input-group-addon">是否启用</span>

                                <span class="form-control soin_type_is_use togglebutton_container">
									<div class="togglebutton">
										<label>
											<input type="checkbox"/>
											<span class="toggle"></span>
										</label>
									</div>
								</span>

								<%--<span class="input-group-addon txt">启用</span>--%>

							</div>

						</div>

						<div class="col-md-4">

							<div class="input-group">

								<span class="input-group-addon">是否必须缴纳公积金</span>

                                <span class="form-control soin_house_fund_must togglebutton_container">
									<div class="togglebutton">
										<label>
											<input type="checkbox"/>
											<span class="toggle"></span>
										</label>
									</div>
								</span>

								<%--<span class="input-group-addon txt">启用</span>--%>

							</div>

						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">截止</span>
								<input type="number" class="form-control soin_type_last_day">
								<span class="input-group-addon">日</span>
							</div>
						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">提前</span>
								<input type="number" class="form-control soin_type_forward_month">
								<span class="input-group-addon">月</span>
							</div>
						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">至少</span>
								<input type="number" class="form-control soin_type_least_month">
								<span class="input-group-addon">月</span>
							</div>
						</div>

						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-addon">至多</span>
								<input type="number" class="form-control soin_type_most_month">
								<span class="input-group-addon">月</span>
							</div>
						</div>

						<div class="col-md-8">
							<div class="input-group">
								<span class="input-group-addon">社保类型描述</span>
								<input type="text" class="form-control soin_type_desc">
							</div>
						</div>

						<div class="col-md-12">

							<div class="input-group" style="line-height:40px;">
								<span>社保提示</span>
							</div>

							<div class="input-group">
                                <textarea class="form-control soin_type_hint" rows="5"
										  cols="100" required=""
										  aria-required="true" name="type_hint">
								</textarea>
							</div>

						</div>

					</div>

					<button class="btn btn-primary submit_soin_type_detail btn_submit"
							type="button" onclick="soin_base_info_manage.soinTypeDetailSave()">
						<i class="fa fa-check"></i>&nbsp;
						提交
					</button>

				</div>

			</div>

		</div>

		<div class="row soin_type_version_container">

			<div class="name">
				类型版本
			</div>

			<ul class="nav nav-tabs" role="tablist" id="myTab">

				<li role="presentation" class="active">
					<a href="#back_version" role="tab" data-toggle="tab">
						补缴版本
					</a>
				</li>

				<li role="presentation">
					<a href="#normal_version" role="tab" data-toggle="tab">
						正常版本
					</a>
				</li>

			</ul>

			<div class="tab-content">

				<div role="tabpanel" class="tab-pane active" id="back_version">

					<div class="btn_list_container">

						<div class="btn btn-sm btn-success f_right btn_add"
							 onclick="soin_base_info_manage.soinTypeVerBackAddModalShow()">
							增加版本
						</div>

						<div class="btn btn-sm btn-danger f_right btn_del"
							 onclick="soin_base_info_manage.soinTypeVerBackDel()">
							删除
						</div>

						<div class="soin_type_version_list btn_list">

							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>
							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>
							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>

						</div>

					</div>

					<div class="soin_type_version_detail_container">

						<div class="name">
							版本详情
						</div>

						<div class="soin_type_version_detail">

							<div class="row">

								<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">生效年</span>
										<input type="number" class="form-control soin_type_version_effect_year">
										<span class="input-group-addon">年</span>
									</div>
								</div>

								<div class="col-md-4">
									<div class="input-group">
										<div class="input-group-addon">生效月</div>
										<input type="number" class="form-control soin_type_version_effect_month">
										<div class="input-group-addon">月</div>
									</div>
								</div>

								<div class="col-md-4">

									<div class="input-group">

										<div class="input-group-addon">是否启用</div>

										<div class="form-control togglebutton_container
										soin_type_version_is_use">
											<div class="togglebutton">
												<label>
													<input type="checkbox"/>
													<span class="toggle"></span>
												</label>
											</div>
										</div>

									</div>

								</div>

								<div class="col-md-4">

									<div class="input-group">

										<div class="input-group-addon">是否跨年</div>

										<div class="form-control togglebutton_container
										soin_type_version_is_cross_year">

											<div class="togglebutton">
												<label>
													<input type="checkbox"/>
													<span class="toggle"></span>
												</label>
											</div>

										</div>

									</div>

								</div>

								<div class="col-md-5">

									<div class="input-group">

										<div class="input-group-addon">社保与公积金联动(基数)</div>

										<div class="form-control base_accordant_checkbox togglebutton_container">
											<div class="togglebutton">
												<label>
													<input type="checkbox"/>
													<span class="toggle"></span>
												</label>
											</div>
										</div>

									</div>

								</div>

								<div class="col-md-4">
									<div class="input-group">
										<div class="input-group-addon">至少</div>
										<input type="number" class="form-control
										soin_type_version_least_month">
										<div class="input-group-addon">月</div>
									</div>
								</div>

								<div class="col-md-4">
									<div class="input-group">
										<div class="input-group-addon">至多</div>
										<input type="number" class="form-control
										soin_type_version_most_month">
										<div class="input-group-addon">月</div>
									</div>
								</div>

								<div class="col-md-4">
									<div class="input-group">
										<div class="input-group-addon">滞纳金</div>
										<input type="number" class="form-control
										soin_type_version_late_fee">
										<div class="input-group-addon">
											<i class="fa fa-yen"></i>
										</div>
									</div>
								</div>


							</div>

							<div class="row">

								<div class="col-md-12">

									<table class="soin_type_version_detail_table"
										   border="1">
										<thead>
										<tr>
											<th rowspan="2">险种</th>
											<th colspan="2">比例（%）</th>
											<th colspan="2">固定金额（元）</th>
											<th colspan="2">基数（元）</th>
											<th colspan="2">其他</th>
										</tr>
										<tr>
											<th>公司</th>
											<th>个人</th>
											<th>公司</th>
											<th>个人</th>
											<th>最低</th>
											<th>最高</th>
											<th>描述</th>
											<th>缴纳月份</th>
										</tr>
										</thead>

										<tbody>

										<tr>
											<td>
												养老
												<input class="pension_id" type="hidden">
											</td>
											<td>
												<input class="pension_percentage_corp" type="number">
											</td>
											<td>
												<input class="pension_percentage_person" type="number">
											</td>
											<td>
												<input class="pension_extra_corp" type="number">
											</td>
											<td>
												<input class="pension_extra_person" type="number">
											</td>
											<td>
												<input class="pension_min_base" type="number">
											</td>
											<td>
												<input class="pension_max_base" type="number">
											</td>
											<td>
												<input class="pension_desc" type="text">
											</td>
											<td>
												<input class="pension_pay_month" type="number">
											</td>
										</tr>

										<tr>
											<td>
												医疗
												<input class="medical_id" type="hidden"></td>
											<td>
												<input class="medical_percentage_corp" type="number"></td>
											<td>
												<input class="medical_percentage_person" type="number"></td>
											<td>
												<input class="medical_extra_corp" type="number"></td>
											<td>
												<input class="medical_extra_person" type="number"></td>
											<td>
												<input class="medical_min_base" type="number"></td>
											<td>
												<input class="medical_max_base" type="number"></td>
											<td>
												<input class="medical_desc" type="text"></td>
											<td>
												<input class="medical_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												工伤
												<input class="injury_id" type="hidden"></td>
											<td><input class="injury_percentage_corp" type="number"></td>
											<td><input class="injury_percentage_person" type="number"></td>
											<td><input class="injury_extra_corp" type="number"></td>
											<td><input class="injury_extra_person" type="number"></td>
											<td><input class="injury_min_base" type="number"></td>
											<td><input class="injury_max_base" type="number"></td>
											<td><input class="injury_desc" type="text"></td>
											<td><input class="injury_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>生育<input class="pregnancy_id" type="hidden"></td>
											<td><input class="pregnancy_percentage_corp" type="number"></td>
											<td><input class="pregnancy_percentage_person" type="number"></td>
											<td><input class="pregnancy_extra_corp" type="number"></td>
											<td><input class="pregnancy_extra_person" type="number"></td>
											<td><input class="pregnancy_min_base" type="number"></td>
											<td><input class="pregnancy_max_base" type="number"></td>
											<td><input class="pregnancy_desc" type="text"></td>
											<td><input class="pregnancy_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												失业
												<input class="unemployment_id" type="hidden">
											</td>
											<td>
												<input class="unemployment_percentage_corp" type="number">
											</td>
											<td>
												<input class="unemployment_percentage_person" type="number">
											</td>
											<td>
												<input class="unemployment_extra_corp" type="number">
											</td>
											<td>
												<input class="unemployment_extra_person" type="number"></td>
											<td>
												<input class="unemployment_min_base" type="number"></td>
											<td>
												<input class="unemployment_max_base" type="number"></td>
											<td>
												<input class="unemployment_desc" type="text"></td>
											<td>
												<input class="unemployment_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>公积金<input class="house_fund_id" type="hidden"></td>
											<td><input class="house_fund_percentage_corp" type="number"></td>
											<td><input class="house_fund_percentage_person" type="number"></td>
											<td><input class="house_fund_extra_corp" type="number"></td>
											<td><input class="house_fund_extra_person" type="number"></td>
											<td><input class="house_fund_min_base" type="number"></td>
											<td><input class="house_fund_max_base" type="number"></td>
											<td><input class="house_fund_desc" type="text"></td>
											<td><input class="house_fund_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>大病医疗<input class="severe_illness_id" type="hidden"></td>
											<td>
												<input class="severe_illness_percentage_corp" type="number"></td>
											<td>
												<input class="severe_illness_percentage_person" type="number"></td>
											<td>
												<input class="severe_illness_extra_corp" type="number"></td>
											<td>
												<input class="severe_illness_extra_person" type="number"></td>
											<td>
												<input class="severe_illness_min_base" type="number"></td>
											<td>
												<input class="severe_illness_max_base" type="number"></td>
											<td>
												<input class="severe_illness_desc" type="text"></td>
											<td>
												<input class="severe_illness_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>残保<input class="disability_id" type="hidden"></td>
											<td>
												<input class="disability_percentage_corp" type="number"></td>
											<td>
												<input class="disability_percentage_person" type="number"></td>
											<td>
												<input class="disability_extra_corp" type="number"></td>
											<td>
												<input class="disability_extra_person" type="number"></td>
											<td>
												<input class="disability_min_base" type="number"></td>
											<td>
												<input class="disability_max_base" type="number"></td>
											<td>
												<input class="disability_desc" type="text"></td>
											<td>
												<input class="disability_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>工伤补充<input class="injury_addition_id" type="hidden"></td>
											<td>
												<input class="injury_addition_percentage_corp" type="number"></td>
											<td>
												<input class="injury_addition_percentage_person" type="number"></td>
											<td>
												<input class="injury_addition_extra_corp" type="number"></td>
											<td>
												<input class="injury_addition_extra_person" type="number"></td>
											<td>
												<input class="injury_addition_min_base" type="number"></td>
											<td>
												<input class="injury_addition_max_base" type="number"></td>
											<td>
												<input class="injury_addition_desc" type="text"></td>
											<td>
												<input class="injury_addition_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												补充公积金
												<input class="house_fund_addition_id" type="hidden"></td>
											<td>
												<input class="house_fund_addition_percentage_corp" type="number"></td>
											<td>
												<input class="house_fund_addition_percentage_person" type="number"></td>
											<td>
												<input class="house_fund_addition_extra_corp" type="number"></td>
											<td>
												<input class="house_fund_addition_extra_person" type="number"></td>
											<td>
												<input class="house_fund_addition_min_base" type="number"></td>
											<td>
												<input class="house_fund_addition_max_base" type="number"></td>
											<td>
												<input class="house_fund_addition_desc" type="text"></td>
											<td>
												<input class="house_fund_addition_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												采暖费
												<input class="heating_id" type="hidden"></td>
											<td>
												<input class="heating_percentage_corp" type="number"></td>
											<td>
												<input class="heating_percentage_person" type="number"></td>
											<td>
												<input class="heating_extra_corp" type="number"></td>
											<td>
												<input class="heating_extra_person" type="number"></td>
											<td>
												<input class="heating_min_base" type="number"></td>
											<td>
												<input class="heating_max_base" type="number"></td>
											<td>
												<input class="heating_desc" type="text"></td>
											<td>
												<input class="heating_pay_month" type="number"></td>
										</tr>

										</tbody>

									</table>

								</div>

							</div>

							<button class="btn btn-primary btn_submit submit_soin_type_version_detail"
									type="button"
									onclick="soin_base_info_manage.soinTypeVerBackDetailSave()">
								<i class="fa fa-check"></i>&nbsp;
								提交
							</button>

						</div>

					</div>

				</div>

				<div role="tabpanel" class="tab-pane" id="normal_version">

					<div class="btn_list_container">

						<div class="btn btn-sm btn-success f_right btn_add"
							 onclick="soin_base_info_manage.soinTypeVerNormalAddModalShow()">
							增加版本
						</div>

						<div class="btn btn-sm btn-danger f_right btn_del"
							 onclick="soin_base_info_manage.soinTypeVerNormalDel()">
							删除
						</div>

						<div class="soin_type_version_list btn_list">

							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>
							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>
							<%--<div class="btn btn-sm btn-primary">版本item</div>--%>

						</div>

					</div>

					<div class="soin_type_version_detail_container">

						<div class="name">
							版本详情
						</div>

						<div class="soin_type_version_detail">

							<div class="row">

								<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">生效年</span>
										<input type="number" class="form-control soin_type_version_effect_year">
										<span class="input-group-addon">年</span>
									</div>
								</div>

								<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">生效月</span>
										<input type="number" class="form-control soin_type_version_effect_month">
										<span class="input-group-addon">月</span>
									</div>
								</div>

								<div class="col-md-4">

									<div class="input-group">

										<span class="input-group-addon">是否启用</span>

                                        <span class="form-control soin_type_version_is_use togglebutton_container">
									<div class="togglebutton">
										<label>
											<input type="checkbox"/>
											<span class="toggle"></span>
										</label>
									</div>
								</span>

									</div>

								</div>

								<div class="col-md-5">

									<div class="input-group">

										<span class="input-group-addon">社保与公积金联动(基数)</span>

                                        <span class="form-control base_accordant_checkbox togglebutton_container">
									<div class="togglebutton">
										<label>
											<input type="checkbox"/>
											<span class="toggle"></span>
										</label>
									</div>
								</span>

									</div>

								</div>

							</div>

							<div class="row">

								<div class="col-md-12">
									<table class="soin_type_version_detail_table"
										   border="1">
										<thead>
										<tr>
											<th rowspan="2">险种</th>
											<th colspan="2">比例（%）</th>
											<th colspan="2">固定金额（元）</th>
											<th colspan="2">基数（元）</th>
											<th colspan="2">其他</th>
										</tr>
										<tr>
											<th>公司</th>
											<th>个人</th>
											<th>公司</th>
											<th>个人</th>
											<th>最低</th>
											<th>最高</th>
											<th>描述</th>
											<th>缴纳月份</th>
										</tr>
										</thead>

										<tbody>

										<tr>
											<td>
												养老
												<input class="pension_id" type="hidden">
											</td>
											<td>
												<input class="pension_percentage_corp" type="number">
											</td>
											<td>
												<input class="pension_percentage_person" type="number">
											</td>
											<td>
												<input class="pension_extra_corp" type="number">
											</td>
											<td>
												<input class="pension_extra_person" type="number">
											</td>
											<td>
												<input class="pension_min_base" type="number">
											</td>
											<td>
												<input class="pension_max_base" type="number">
											</td>
											<td>
												<input class="pension_desc" type="text">
											</td>
											<td>
												<input class="pension_pay_month" type="number">
											</td>
										</tr>

										<tr>
											<td>
												医疗
												<input class="medical_id" type="hidden"></td>
											<td>
												<input class="medical_percentage_corp" type="number"></td>
											<td>
												<input class="medical_percentage_person" type="number"></td>
											<td>
												<input class="medical_extra_corp" type="number"></td>
											<td>
												<input class="medical_extra_person" type="number"></td>
											<td>
												<input class="medical_min_base" type="number"></td>
											<td>
												<input class="medical_max_base" type="number"></td>
											<td>
												<input class="medical_desc" type="text"></td>
											<td>
												<input class="medical_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												工伤
												<input class="injury_id" type="hidden"></td>
											<td><input class="injury_percentage_corp" type="number"></td>
											<td><input class="injury_percentage_person" type="number"></td>
											<td><input class="injury_extra_corp" type="number"></td>
											<td><input class="injury_extra_person" type="number"></td>
											<td><input class="injury_min_base" type="number"></td>
											<td><input class="injury_max_base" type="number"></td>
											<td><input class="injury_desc" type="text"></td>
											<td><input class="injury_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>生育<input class="pregnancy_id" type="hidden"></td>
											<td><input class="pregnancy_percentage_corp" type="number"></td>
											<td><input class="pregnancy_percentage_person" type="number"></td>
											<td><input class="pregnancy_extra_corp" type="number"></td>
											<td><input class="pregnancy_extra_person" type="number"></td>
											<td><input class="pregnancy_min_base" type="number"></td>
											<td><input class="pregnancy_max_base" type="number"></td>
											<td><input class="pregnancy_desc" type="text"></td>
											<td><input class="pregnancy_pay_month" type="number"></td>
										</tr>


										<tr>
											<td>
												失业
												<input class="unemployment_id" type="hidden">
											</td>
											<td>
												<input class="unemployment_percentage_corp" type="number">
											</td>
											<td>
												<input class="unemployment_percentage_person" type="number">
											</td>
											<td>
												<input class="unemployment_extra_corp" type="number">
											</td>
											<td>
												<input class="unemployment_extra_person" type="number"></td>
											<td>
												<input class="unemployment_min_base" type="number"></td>
											<td>
												<input class="unemployment_max_base" type="number"></td>
											<td>
												<input class="unemployment_desc" type="text"></td>
											<td>
												<input class="unemployment_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>公积金<input class="house_fund_id" type="hidden"></td>
											<td><input class="house_fund_percentage_corp" type="number"></td>
											<td><input class="house_fund_percentage_person" type="number"></td>
											<td><input class="house_fund_extra_corp" type="number"></td>
											<td><input class="house_fund_extra_person" type="number"></td>
											<td><input class="house_fund_min_base" type="number"></td>
											<td><input class="house_fund_max_base" type="number"></td>
											<td><input class="house_fund_desc" type="text"></td>
											<td><input class="house_fund_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>大病医疗<input class="severe_illness_id" type="hidden"></td>
											<td>
												<input class="severe_illness_percentage_corp" type="number"></td>
											<td>
												<input class="severe_illness_percentage_person" type="number"></td>
											<td>
												<input class="severe_illness_extra_corp" type="number"></td>
											<td>
												<input class="severe_illness_extra_person" type="number"></td>
											<td>
												<input class="severe_illness_min_base" type="number"></td>
											<td>
												<input class="severe_illness_max_base" type="number"></td>
											<td>
												<input class="severe_illness_desc" type="text"></td>
											<td>
												<input class="severe_illness_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>残保<input class="disability_id" type="hidden"></td>
											<td>
												<input class="disability_percentage_corp" type="number"></td>
											<td>
												<input class="disability_percentage_person" type="number"></td>
											<td>
												<input class="disability_extra_corp" type="number"></td>
											<td>
												<input class="disability_extra_person" type="number"></td>
											<td>
												<input class="disability_min_base" type="number"></td>
											<td>
												<input class="disability_max_base" type="number"></td>
											<td>
												<input class="disability_desc" type="text"></td>
											<td>
												<input class="disability_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>工伤补充<input class="injury_addition_id" type="hidden"></td>
											<td>
												<input class="injury_addition_percentage_corp" type="number"></td>
											<td>
												<input class="injury_addition_percentage_person" type="number"></td>
											<td>
												<input class="injury_addition_extra_corp" type="number"></td>
											<td>
												<input class="injury_addition_extra_person" type="number"></td>
											<td>
												<input class="injury_addition_min_base" type="number"></td>
											<td>
												<input class="injury_addition_max_base" type="number"></td>
											<td>
												<input class="injury_addition_desc" type="text"></td>
											<td>
												<input class="injury_addition_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												补充公积金
												<input class="house_fund_addition_id" type="hidden"></td>
											<td>
												<input class="house_fund_addition_percentage_corp" type="number"></td>
											<td>
												<input class="house_fund_addition_percentage_person" type="number"></td>
											<td>
												<input class="house_fund_addition_extra_corp" type="number"></td>
											<td>
												<input class="house_fund_addition_extra_person" type="number"></td>
											<td>
												<input class="house_fund_addition_min_base" type="number"></td>
											<td>
												<input class="house_fund_addition_max_base" type="number"></td>
											<td>
												<input class="house_fund_addition_desc" type="text"></td>
											<td>
												<input class="house_fund_addition_pay_month" type="number"></td>
										</tr>

										<tr>
											<td>
												采暖费
												<input class="heating_id" type="hidden"></td>
											<td>
												<input class="heating_percentage_corp" type="number"></td>
											<td>
												<input class="heating_percentage_person" type="number"></td>
											<td>
												<input class="heating_extra_corp" type="number"></td>
											<td>
												<input class="heating_extra_person" type="number"></td>
											<td>
												<input class="heating_min_base" type="number"></td>
											<td>
												<input class="heating_max_base" type="number"></td>
											<td>
												<input class="heating_desc" type="text"></td>
											<td>
												<input class="heating_pay_month" type="number"></td>
										</tr>

										</tbody>

									</table>

								</div>

							</div>

							<button class="btn btn-primary btn_submit submit_soin_type_version_detail"
									type="button"
									onclick="soin_base_info_manage.soinTypeVerNormalDetailSave()">
								<i class="fa fa-check"></i>&nbsp;
								提交
							</button>

						</div>

					</div>

				</div>

			</div>


		</div>

	</div>

</div>

<div class="modal fade add_soin_district_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加参保地区</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-sm-2" style="line-height:34px;">地区列表</div>

					<div class="col-sm-10">

						<select class="form-control soin_area_list" name="account">
							<option>苏州</option>
							<option>苏州</option>
							<option>苏州</option>
						</select>

					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn_save"
						onclick="soin_base_info_manage.soinAreaSave()">
					保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade add_soin_type_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加社保类型</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="col-sm-3" style="line-height:34px;">类型名称：</div>

					<div class="col-sm-9">

						<input class="form-control type_name" placeholder="请输入社保类型名称">

					</div>
				</div>

				<div class="row">
					<div class="col-sm-3" style="line-height:34px;">复制源：</div>

					<div class="col-sm-9">

						<select data-placeholder="请选择社保类型" multiple
								class="chosen-select soin_type_list">
							<%--<option>1</option>--%>
						</select>

					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn_save"
						onclick="soin_base_info_manage.soinTypeAdd()">
					保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade add_soin_type_version_back_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加社保类型版本（补缴）</h4>
			</div>
			<div class="modal-body">

				<div class="row">

					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">生效年</span>
							<input type="number" class="form-control soin_type_version_effect_year">
							<span class="input-group-addon">年</span>
						</div>
					</div>

					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">生效月</span>
							<input type="number" class="form-control soin_type_version_effect_month">
							<span class="input-group-addon">月</span>
						</div>
					</div>

					<%--<div class="col-md-6">--%>
					<%--<div class="input-group">--%>
					<%--<span class="input-group-addon">至少</span>--%>
					<%--<input type="number" class="form-control soin_type_version_least_month">--%>
					<%--<span class="input-group-addon">月</span>--%>
					<%--</div>--%>
					<%--</div>--%>

					<%--<div class="col-md-6">--%>
					<%--<div class="input-group">--%>
					<%--<span class="input-group-addon">至多</span>--%>
					<%--<input type="number" class="form-control soin_type_version_most_month">--%>
					<%--<span class="input-group-addon">月</span>--%>
					<%--</div>--%>
					<%--</div>--%>

					<%--<div class="col-md-6">--%>
					<%--<div class="input-group">--%>
					<%--<span class="input-group-addon">滞纳金</span>--%>
					<%--<input type="number" class="form-control soin_type_version_late_fee">--%>
					<%--<span class="input-group-addon">--%>
					<%--<i class="fa fa-yen"></i>--%>
					<%--</span>--%>
					<%--</div>--%>
					<%--</div>--%>

					<%--<div class="col-md-6">--%>

					<%--<div class="input-group">--%>

					<%--<span class="input-group-addon">社保与公积金联动(基数)</span>--%>

					<%--<span class="form-control base_accordant_checkbox togglebutton_container">--%>
					<%--<div class="togglebutton">--%>
					<%--<label>--%>
					<%--<input type="checkbox"/>--%>
					<%--<span class="toggle"></span>--%>
					<%--</label>--%>
					<%--</div>--%>
					<%--</span>--%>

					<%--</div>--%>

					<%--</div>--%>

					<%--<div class="col-md-6">--%>

					<%--<div class="input-group">--%>

					<%--<span class="input-group-addon">是否跨年</span>--%>

					<%--<div class="form-control togglebutton_container--%>
					<%--is_cross_year">--%>
					<%--<div class="togglebutton">--%>
					<%--<label>--%>
					<%--<input type="checkbox"/>--%>
					<%--<span class="toggle"></span>--%>
					<%--</label>--%>
					<%--</div>--%>
					<%--</div>--%>

					<%--</div>--%>

					<%--</div>--%>

				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn_save"
						onclick="soin_base_info_manage.soinTypeVerBackAdd()">
					保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<div class="modal fade add_soin_type_version_normal_modal" style="background-color:rgba(0,0,0,0.50);">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
						aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加社保类型版本（正常）</h4>
			</div>
			<div class="modal-body">

				<div class="row">

					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">生效年</span>
							<input type="number" class="form-control soin_type_version_effect_year">
							<span class="input-group-addon">年</span>
						</div>
					</div>

					<div class="col-md-6">
						<div class="input-group">
							<span class="input-group-addon">生效月</span>
							<input type="number" class="form-control soin_type_version_effect_month">
							<span class="input-group-addon">月</span>
						</div>
					</div>

					<%--<div class="col-md-6">--%>

					<%--<div class="input-group">--%>

					<%--<span class="input-group-addon">社保与公积金联动(基数)</span>--%>

					<%--<span class="form-control base_accordant_checkbox togglebutton_container">--%>
					<%--<div class="togglebutton">--%>
					<%--<label>--%>
					<%--<input type="checkbox"/>--%>
					<%--<span class="toggle"></span>--%>
					<%--</label>--%>
					<%--</div>--%>
					<%--</span>--%>

					<%--</div>--%>

					<%--</div>--%>

				</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary btn_save"
						onclick="soin_base_info_manage.soinTypeVerNormalAdd()">
					保存
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

