/**
 * Created by CuiMengxin on 2016/11/10.
 */

var zTreeObj;//树 结构容器
var $add_soin_district_modal = $(".add_soin_district_modal");
var $add_soin_type_modal = $(".add_soin_type_modal");
var $add_soin_type_ver_back_modal = $(".add_soin_type_version_back_modal");
var $add_soin_type_ver_normal_modal = $(".add_soin_type_version_normal_modal");

var soin_base_info_manage = {
	user_jurisdiction: "1",	//用户权限 1是，0否
	containerName: "",
	current_treeNode: "",//选中的 树节点
	current_soin_type_id: '',//社保类型id
	current_soin_type_ver_type: '',//社保类型 版本 类型 0 补缴 1 正常
	current_soin_type_ver_back_id: '',//社保类型 版本 (补缴) id
	current_soin_type_ver_normal_id: '',//社保类型 版本 (正常) id

	//初始化 方法
	Init: function () {
		soin_base_info_manage.containerName = ".soin_baseInfo_container";

		soin_base_info_manage.initUserJurisdiction();//初始化 用户权限
		soin_base_info_manage.initTree();//初始化 树结构
		soin_base_info_manage.soinTypeDetailClear();//
		soin_base_info_manage.hideVerContainer();//隐藏 社保类型 版本详情 内容

		//新增 社保类型 弹框显示
		$add_soin_type_modal.on("shown.bs.modal", function () {

			var $soin_type_list = $add_soin_type_modal.find(".soin_type_list");

			//类型名称 置空
			$add_soin_type_modal.find(".type_name").val("");

			var obj = {};
			obj.district_id = soin_base_info_manage.current_treeNode.id;
			var url = urlGroup.soin_type_list + "?" + jsonParseParam(obj);

			loadingInit();

			aryaGetRequest(
				url,
				function (data) {
					//console.log("获取日志：");
					//console.log(data);

					if (data.code == RESPONSE_OK_CODE) {

						//判断是否有值，有值则初始化为空
						if ($soin_type_list.siblings(".chosen-container").length > 0) {
							$soin_type_list.chosen("destroy");
						}
						var list = "";

						if (data.result) {

							var types = data.result;
							if (!types || types.length == 0) {
							}
							else {

								for (var i = 0; i < types.length; i++) {
									var $item = types[i];

									var id = $item.id;//
									var name = $item.text;//

									list += "<option value='" + id + "'>" + name + "</option>";

								}

							}

							//如果有类型
							if (list) {
								$soin_type_list.attr("data-placeholder", "请选择社保类型");
								$soin_type_list.removeAttr("disabled");

								$soin_type_list.html(list);

								$soin_type_list.chosen({
									allow_single_deselect: true,//选择之后 是否可以取消
									max_selected_options: 1,//最多只能选择1个
									width: "100%",//select框 宽度
									no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
								});

								$soin_type_list.siblings(".chosen-container").addClass("form-control")
									.css("padding", "0");

							}
							else {
								soinTypeEmpty();
							}

						}
						else {
							soinTypeEmpty();
						}

						//如果没有类型 ，则显示暂无类型
						function soinTypeEmpty() {

							$soin_type_list.attr("data-placeholder", "暂无类型可选");
							$soin_type_list.attr("disabled", "disabled");

							$soin_type_list.html(list);

							$soin_type_list.chosen({
								//display_disabled_options:true,
								allow_single_deselect: true,//选择之后 是否可以取消
								max_selected_options: 1,//最多只能选择1个
								width: "100%",//select框 宽度
								no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
							});

							$soin_type_list.siblings(".chosen-container").addClass("form-control")
								.css("padding", "0");
						}

					}
					else {
						console.log("获取日志-----error：");
						console.log(data.msg);

						messageCue(data.msg);
					}
				},
				function (error) {
					messageCue(error);
				}
			);

		});

		//新增 社保类型 版本类型（补缴） 弹框显示
		$add_soin_type_ver_back_modal.on("shown.bs.modal", function () {
			$add_soin_type_ver_back_modal.find(".soin_type_version_effect_year").val("");
			$add_soin_type_ver_back_modal.find(".soin_type_version_effect_month").val("");
		});

		//新增 社保类型 版本类型（正常） 弹框显示
		$add_soin_type_ver_normal_modal.on("shown.bs.modal", function () {
			$add_soin_type_ver_normal_modal.find(".soin_type_version_effect_year").val("");
			$add_soin_type_ver_normal_modal.find(".soin_type_version_effect_month").val("");
		});

		//初始化 补缴版本/正常版本 点击事件
		$("#myTab a").click(function (e) {
			e.preventDefault();
			$(this).tab('show')
		});
	},

	//初始化用户权限
	initUserJurisdiction: function () {

		loadingInit();

		aryaGetRequest(
			urlGroup.check_user_jurisdiction,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					//用户权限 1是，0否
					soin_base_info_manage.user_jurisdiction = data.result.has_auth;//

					//if (soin_base_info_manage.user_jurisdiction == 0) {
					//	$(soin_base_info_manage.containerName).find("input")
					//		.attr("disabled", "disabled");
					//	$(soin_base_info_manage.containerName).find("textarea").attr("disabled", "disabled");
					//}
					//else {
					//	$(soin_base_info_manage.containerName).find("input").removeAttr("disabled");
					//	$(soin_base_info_manage.containerName).find("textarea").removeAttr("disabled");
					//
					//}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//检查用户权限
	checkUserJurisdiction: function () {

		return soin_base_info_manage.user_jurisdiction;

	},
	//根据用户权限 判断是否显示 新增、删除按钮
	initAddAndDelBtn: function ($container) {

		//有权限
		if (soin_base_info_manage.checkUserJurisdiction()) {
			$container.find(".btn_add").show();	//新增按钮 显示
			$container.find(".btn_del").show();	//删除按钮 显示
		}
		else {
			$container.find(".btn_add").hide();//新增按钮 隐藏
			$container.find(".btn_del").hide();//删除按钮 隐藏
		}

	},

	//初始化 树结构
	initTree: function () {
		//初始化 树结构
		initSoinBaseTree(
			$("#soin_area_tree"),
			$("#soin_area_tree_hud"),
			urlGroup.soin_district_tree_url,
			soin_base_info_manage.treeCheck,
			soin_base_info_manage.treeClick
		);

	},
	//树 选择事件
	treeCheck: function (treeNode) {

		if (treeNode.pId == null) {		//根节点
			if (treeNode.getCheckStatus().checked)
				zTreeObj.checkAllNodes(true);
			else {
				zTreeObj.checkAllNodes(false);
			}
		}
		else {

			//如果取消了 一个节点的选中,判断 根节点是否是 选中状态
			//如果是选中状态，则取消 根节点的 选中状态
			if (!treeNode.getCheckStatus().checked) {

				//获取 根节点
				var node = zTreeObj.getNodesByFilter(function (node) {
					return node.level == 0
				}, true);

				if (node.getCheckStatus().checked)
					zTreeObj.checkNode(node, false);

			}


		}
	},
	//树 点击事件
	treeClick: function (treeNode) {
		soin_base_info_manage.current_treeNode = treeNode;

		soin_base_info_manage.initSoinAreaUpBtn();//初始化 向上级是否并列 按钮
		soin_base_info_manage.soinTypeList();//获取 社保类型 列表
		soin_base_info_manage.soinTypeDetailClear();//清空 社保类型 详情内容
		soin_base_info_manage.hideTypeSubBtn();//隐藏 社保类型 提交 按钮

		soin_base_info_manage.hideVerContainer();//隐藏 社保类型 版本详情 内容

	},
	//初始化 向上级是否并列 按钮
	initSoinAreaUpBtn: function () {
		var can_up_super = soin_base_info_manage.current_treeNode.can_up_super;//是否显示
		var up_super = soin_base_info_manage.current_treeNode.up_super;//是否 已经向上一级并列
		var $up_list_container = $(".soinBaseZtreeContainer").find(".up_list_container");

		//是否显示 向上级并列
		if (can_up_super == 1) {
			$up_list_container.show();

			//如果已经并列
			if (up_super == 1) {

				//向上并列 不可操作
				$up_list_container.find(".btn_up")
					.addClass("btn-default").removeClass("btn-primary")
					.removeAttr("onclick");

				//取消并列 可操作
				$up_list_container.find(".btn_up_cancel")
					.addClass("btn-primary").removeClass("btn-default")
					.attr("onclick", "soin_base_info_manage.soinAreaUpCancel()");

			}

			//如果 还未并列
			if (up_super == 0) {

				//向上并列 可操作
				$up_list_container.find(".btn_up")
					.addClass("btn-primary").removeClass("btn-default")
					.attr("onclick", "soin_base_info_manage.soinAreaUp()");

				//取消并列 不可操作
				$up_list_container.find(".btn_up_cancel")
					.addClass("btn-default").removeClass("btn-primary")
					.removeAttr("onclick");

			}

		}
		else {
			$up_list_container.hide();
		}
	},

	//向上级 并列
	soinAreaUp: function () {
		var zTree = $.fn.zTree.getZTreeObj("soin_area_tree");

		var obj = {
			district_id: soin_base_info_manage.current_treeNode.id
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_area_Up,
			obj,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("向上级并列成功！");

					soin_base_info_manage.current_treeNode.up_super = 1;
					zTree.updateNode(soin_base_info_manage.current_treeNode);
					soin_base_info_manage.initSoinAreaUpBtn();//初始化 向上级是否并列 按钮

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//取消 向上级 并列
	soinAreaUpCancel: function () {
		var zTree = $.fn.zTree.getZTreeObj("soin_area_tree");

		var obj = {
			district_id: soin_base_info_manage.current_treeNode.id
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_area_Up_Cancel,
			obj,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("取消向上级并列成功！");

					soin_base_info_manage.current_treeNode.up_super = 0;
					zTree.updateNode(soin_base_info_manage.current_treeNode);
					soin_base_info_manage.initSoinAreaUpBtn();//初始化 向上级是否并列 按钮

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			}
		);

	},

	//初始化 未参保地区列表
	initNoSoinAreaList: function () {

		//如果没有 选中参保地区
		if (!soin_base_info_manage.current_treeNode.id) {
			msgShow("请先选择参保地区");
			return
		}

		var obj = {};
		obj.district_id = soin_base_info_manage.current_treeNode.id;
		var url = urlGroup.soin_not_open_district_url + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var $soin_area_list = $add_soin_district_modal.find(".soin_area_list").empty();

					if (data.result) {

						var trees = data.result.tree;
						if (!trees || trees.length == 0) {
							msgShow("'" + soin_base_info_manage.current_treeNode.name + "'" +
								"下的地区已经全部参保！")
						}
						else {
							for (var i = 0; i < trees.length; i++) {
								var $item = trees[i];

								var id = $item.id;//
								var name = $item.name;

								var option = $("<option>");
								option.val(id);
								option.text(name);

								option.appendTo($soin_area_list);

							}

							$add_soin_district_modal.modal("show");

						}

					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//参保地区 - 新增 保存
	soinAreaSave: function () {
		var $soin_area_list = $add_soin_district_modal.find(".soin_area_list");

		var obj = {};
		obj.district_id = $soin_area_list.find("option:selected").val();

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_district_add,
			obj,
			function (data) {
				//console.log("新增地区：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					$add_soin_district_modal.modal("hide");
					toastr.success("保存成功！");

					soin_base_info_manage.initTree();//
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});

	},
	//参保地区 - 删除
	soinAreaDel: function () {
		//如果没有 选中参保地区
		if (!soin_base_info_manage.current_treeNode.id) {
			msgShow("请先选择参保地区");
			return
		}

		var name = "确定要删除\"" + soin_base_info_manage.current_treeNode.name + "\"吗？";
		delWarning(
			name,
			function () {

				var obj = {
					"district_id": soin_base_info_manage.current_treeNode.id
				};

				loadingInit();

				aryaPostRequest(
					urlGroup.soin_district_del,
					obj,
					function (data) {
						//alert(JSON.stringify(data));

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("删除成功！");

							soin_base_info_manage.initTree();
						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});

			}
		);

	},
	//参保地区 - 导出
	soinAreaExport: function () {
		var choose_list = zTreeObj.getCheckedNodes(true);
		if (choose_list.length == 0) {
			msgShow("请先选择参保地区！");
			return;
		}

		exportWarning("已选择的地区", function () {
			//console.log(zTreeObj.getCheckedNodes(true));

			var ids = "";
			for (var i = 0; i < choose_list.length; i++) {
				var id = choose_list[i].id;

				ids += ids == "" ? '"' + id + '"' : ',"' + id + '"'
			}
			ids = "[" + ids + "]";

			var $body = $("body");

			if ($body.find(".soin_export")) {
				$body.find(".soin_export").remove();
			}

			var form = $("<form>");
			form.addClass("soin_export");
			form.attr("enctype", "multipart/form-data");
			form.attr("method", "post");
			form.attr("action", urlGroup.soin_district_export);
			form.hide();
			form.appendTo($body);

			var input = $("<input name='ids'>");
			input.val(ids);
			input.appendTo(form);

			form.submit();


		});

	},

	//隐藏 社保类型 提交 按钮
	hideTypeSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_container")
			.find(".btn_submit").hide();
	},
	//显示 社保类型 提交 按钮
	showTypeSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_container")
			.find(".btn_submit").show();
	},

	//获取 社保类型 列表
	soinTypeList: function () {
		var $soin_type_container = $(soin_base_info_manage.containerName)
			.find(".soin_type_container");

		var obj = {};
		obj.district_id = soin_base_info_manage.current_treeNode.id;
		var url = urlGroup.soin_type_list + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					//根据用户权限 判断是否显示 新增、删除按钮
					soin_base_info_manage.initAddAndDelBtn($soin_type_container);

					var $typeContainer = $soin_type_container
						.find(".btn_list_container .btn_list").empty();

					if (data.result) {

						var types = data.result;
						if (!types || types.length == 0) {
						}
						else {

							for (var i = 0; i < types.length; i++) {
								var $item = types[i];

								var id = $item.id;//
								var name = $item.text;//

								var type_item = $("<span>");
								type_item.addClass("btn");
								type_item.addClass("btn-sm");
								type_item.addClass("btn-default");
								type_item.attr("data-id", id);
								type_item.text(name);

								//社保类型 按钮click
								type_item.click(function () {

									soin_base_info_manage.current_soin_type_id = $(this).data("id");

									$(this).addClass("btn-primary").removeClass("btn-default");
									$(this).siblings().removeClass("btn-primary").addClass("btn-default");

									//获取 社保类型 详情
									soin_base_info_manage.soinTypeDetail();

									//类型 版本详情 显示
									soin_base_info_manage.showVerContainer();

									//初始化 类型版本 （补缴）
									soin_base_info_manage.initSoinTypeVer();

									//有权限
									if (soin_base_info_manage.checkUserJurisdiction()) {

										$soin_type_container.find("input").removeAttr("disabled");
										$soin_type_container.find("textarea").removeAttr("disabled");

										//社保类型 提交按钮 显示
										soin_base_info_manage.showTypeSubBtn();
									}
									else {

										$soin_type_container.find("input").attr("disabled", "disabled");
										$soin_type_container.find("textarea").attr("disabled", "disabled");

										//社保类型 提交按钮 隐藏
										soin_base_info_manage.hideTypeSubBtn();

									}


								});
								type_item.appendTo($typeContainer);

							}

						}

					}

				}
				else {
					console.log("获取日志-----error：");
					console.log(data.msg);

					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//清空 社保类型 详情内容
	soinTypeDetailClear: function () {
		var $soin_type_container = $(soin_base_info_manage.containerName)
			.find(".soin_type_container");


		$soin_type_container.find("input").val("").attr("disabled", "disabled");
		$soin_type_container.find("textarea").val("").attr("disabled", "disabled");
		$soin_type_container.find(".togglebutton_container input").prop("checked", false);
	},
	//获取 社保类型 详情
	soinTypeDetail: function () {

		var $soin_type_container = $(soin_base_info_manage.containerName)
			.find(".soin_type_container");

		var obj = {};
		obj.type_id = soin_base_info_manage.current_soin_type_id;
		var url = urlGroup.soin_type_detail + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {

						var $item = data.result;

						var type_name = $item.type_name ? $item.type_name : "";//
						var fees = $item.fees ? $item.fees : "";//
						var is_active = $item.is_active ? $item.is_active : false;//

						var is_house_fund_must = $item.is_house_fund_must ?
							$item.is_house_fund_must : false;//
						var last_day = $item.last_day ? $item.last_day : "";//
						var forward_month = $item.forward_month ? $item.forward_month : "";//
						var least_month = $item.least_month ? $item.least_month : "";//
						var most_month = $item.most_month ? $item.most_month : "";//

						var type_desc = $item.type_desc ? $item.type_desc : "";//
						var type_hint = $item.type_hint ? $item.type_hint : "";//

						$soin_type_container.find(".soin_type_is_use").find("input")
							.prop("checked", is_active);
						$soin_type_container.find(".soin_house_fund_must").find("input")
							.prop("checked", is_house_fund_must);

						$soin_type_container.find(".soin_type_name").val(type_name);
						$soin_type_container.find(".soin_fees").val(fees);

						$soin_type_container.find(".soin_type_last_day").val(last_day);
						$soin_type_container.find(".soin_type_forward_month").val(forward_month);
						$soin_type_container.find(".soin_type_least_month").val(least_month);
						$soin_type_container.find(".soin_type_most_month").val(most_month);

						$soin_type_container.find(".soin_type_desc").val(type_desc);
						$soin_type_container.find(".soin_type_hint").val(type_hint);

					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//社保类型 编辑后保存
	soinTypeDetailSave: function () {
		var $soin_type_container = $(soin_base_info_manage.containerName)
			.find(".soin_type_container");
		//var $typeContainer = $soin_type_container.find(".soin_type_list");

		var obj = {};
		obj.id = soin_base_info_manage.current_soin_type_id;
		obj.type_name = $.trim($soin_type_container.find(".soin_type_name").val());
		obj.fees = $.trim($soin_type_container.find(".soin_fees").val());
		obj.last_day = $.trim($soin_type_container.find(".soin_type_last_day").val());
		obj.forward_month = $.trim($soin_type_container.find(".soin_type_forward_month").val());
		obj.least_month = $.trim($soin_type_container.find(".soin_type_least_month").val());
		obj.most_month = $.trim($soin_type_container.find(".soin_type_most_month").val());
		obj.type_desc = $.trim($soin_type_container.find(".soin_type_desc").val());
		obj.type_hint = $.trim($soin_type_container.find(".soin_type_hint").val());
		obj.is_house_fund_must = $soin_type_container.find(".soin_house_fund_must")
			.find("input").is(":checked");
		obj.is_active = $soin_type_container.find(".soin_type_is_use")
			.find("input").is(":checked") ? 1 : 0;

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_type_detail_save,
			obj,
			function (data) {
				//console.log("保存 类型详情：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					toastr.success("社保类型详情 保存成功！");

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});

	},
	//社保类型 新增 - 弹框显示
	soinTypeAddModalShow: function () {
		//如果没有 选中参保地区
		if (!soin_base_info_manage.current_treeNode.id) {
			msgShow("请先选择参保地区");
			return
		}

		$add_soin_type_modal.modal("show");
	},
	//社保类型 新增
	soinTypeAdd: function () {

		//选择的 社保类型 id
		var copy_soin_type_id = $add_soin_type_modal.find(".soin_type_list").val();

		var obj = {};
		obj.district_id = soin_base_info_manage.current_treeNode.id;
		obj.name = $add_soin_type_modal.find(".type_name").val();
		obj.copy_soin_type_id = copy_soin_type_id ? copy_soin_type_id[0] : "";

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_type_add,
			obj,
			function (data) {
				//console.log("新增类型：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("新增社保类型成功！");
					$add_soin_type_modal.modal("hide");

					soin_base_info_manage.treeClick(soin_base_info_manage.current_treeNode);

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//社保类型 删除
	soinTypeDel: function () {

		//如果没有 选中参保地区
		if (!soin_base_info_manage.current_treeNode.id) {
			msgShow("请先选择参保地区");
			return
		}

		var $soin_type_container = $(soin_base_info_manage.containerName)
			.find(".soin_type_container");

		var $soin_type_list = $soin_type_container.find(".btn_list_container .soin_type_list");

		//如果没有 选中参保地区
		if ($soin_type_list.find(".btn-primary").length <= 0) {
			msgShow("请先选择社保类型");
			return
		}

		var txt = $soin_type_list.find(".btn-primary").text();

		delWarning("确定要删除" + "\"" + txt + "\"" + "类型吗？", function () {

			loadingInit();

			var obj = {
				type_id: $soin_type_list.find(".btn-primary").attr("data-id")
			};

			aryaPostRequest(
				urlGroup.soin_type_del,
				obj,
				function (data) {
					console.log("获取日志：");
					console.log(data);

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除社保类型成功！");

						soin_base_info_manage.treeClick(soin_base_info_manage.current_treeNode);
					}
					else {
						toastr.warning(data.msg);
					}

				},
				function (error) {
					toastr.error(error);
				}
			);

		})


	},


	//隐藏 社保类型 版本详情 内容
	hideVerContainer: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container").hide();
	},
	//显示 社保类型 版本详情 内容
	showVerContainer: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container").show();
	},

	//隐藏 社保类型 版本详情 提交按钮（补缴）
	hideVerBackSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container")
			.find("#back_version").find(".btn_submit").hide();
	},
	//显示 社保类型 版本详情 提交按钮（补缴）
	showVerBackSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container")
			.find("#back_version").find(".btn_submit").show();
	},
	//隐藏 社保类型 版本详情 提交按钮（正常）
	hideVerNormalSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container")
			.find("#normal_version").find(".btn_submit").hide();
	},
	//显示 社保类型 版本详情 提交按钮（正常）
	showVerNormalSubBtn: function () {
		$(soin_base_info_manage.containerName).find(".soin_type_version_container")
			.find("#normal_version").find(".btn_submit").show();
	},


	//初始化 类型版本
	initSoinTypeVer: function () {
		soin_base_info_manage.initSoinTypeVerBack();//初始化 类型版本 （补缴）
		soin_base_info_manage.initSoinTypeVerNormal();//初始化 类型版本 （正常）
	},

	//初始化 类型版本 （补缴）
	initSoinTypeVerBack: function () {

		soin_base_info_manage.soinTypeVerBackListGet();//获取 社保类型 版本列表（补缴）
		soin_base_info_manage.soinTypeVerBackDetailClear();//清空 社保类型 版本详情（补缴）
		soin_base_info_manage.hideVerBackSubBtn();//隐藏 社保类型 版本详情 提交按钮（补缴）

	},
	//获取 社保类型 版本列表（补缴）
	soinTypeVerBackListGet: function () {

		var $verBack = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#back_version");

		var obj = {};
		obj.type_id = soin_base_info_manage.current_soin_type_id;
		var url = urlGroup.soin_type_version_back_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					//根据用户权限 判断是否显示 新增、删除按钮
					soin_base_info_manage.initAddAndDelBtn($verBack);

					////有权限
					//if (soin_base_info_manage.checkUserJurisdiction()) {
					//	//新增类型 版本 （补缴） 按钮 显示
					//	$verBack.find(".btn_add").show();
					//}
					//else {
					//	//新增类型 版本 （补缴） 按钮  隐藏
					//	$verBack.find(".btn_add").hide();
					//}

					var $verTypeContainer = $verBack.find(".btn_list_container .btn_list").empty();

					if (data.result) {

						var types = data.result;
						if (!types || types.length == 0) {
						}
						else {

							for (var i = 0; i < types.length; i++) {
								var $item = types[i];

								var id = $item.id;//
								var effect_year = $item.effect_year;//
								var effect_month = $item.effect_month;//
								var name = effect_year + "年" + effect_month + "月起生效";//

								var type_item = $("<span>");
								type_item.addClass("btn");
								type_item.addClass("btn-sm");
								type_item.addClass("btn-default");
								type_item.attr("data-id", id);
								type_item.text(name);
								type_item.click(function () {

									soin_base_info_manage.current_soin_type_ver_back_id =
										$(this).attr("data-id");

									$(this).addClass("btn-primary").removeClass("btn-default");
									$(this).siblings().removeClass("btn-primary").addClass("btn-default");

									//获取 社保类型 版本详情（补缴）
									soin_base_info_manage.soinTypeVerBackDetail();

									//有权限
									if (soin_base_info_manage.checkUserJurisdiction()) {

										$verBack.find("input").removeAttr("disabled");
										$verBack.find("textarea").removeAttr("disabled");

										//社保类型 版本（补缴） 提交按钮 显示
										soin_base_info_manage.showVerBackSubBtn();
									}
									else {

										$verBack.find("input")
											.attr("disabled", "disabled");
										$verBack.find("textarea")
											.attr("disabled", "disabled");

										//社保类型 版本（补缴） 提交按钮 隐藏
										soin_base_info_manage.hideVerBackSubBtn();

									}

								});

								type_item.appendTo($verTypeContainer);

							}

						}

					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});


	},
	//清空 社保类型 版本详情（补缴）
	soinTypeVerBackDetailClear: function () {
		var $verBack = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#back_version");

		$verBack.find("input").val("").attr("disabled", "disabled");
		$verBack.find("textarea").val("").attr("disabled", "disabled");
		$verBack.find(".togglebutton_container input").prop("checked", false);
	},
	//获取 社保类型 版本详情（补缴）
	soinTypeVerBackDetail: function () {
		var $verBack = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#back_version");

		var obj = {};
		obj.version_id = soin_base_info_manage.current_soin_type_ver_back_id;
		var url = urlGroup.soin_type_version_back_detail + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {

						var $item = data.result;

						var effect_year = $item.effect_year ? $item.effect_year : "";//生效年
						var effect_month = $item.effect_month ? $item.effect_month : "";//生效月
						var at_least = $item.at_least ? $item.at_least : "";//至少 月
						var at_most = $item.at_most ? $item.at_most : "";//至多 月
						var late_fee = $item.late_fee ? $item.late_fee : "";//滞纳金
						var is_active = $item.is_active ? $item.is_active : false;//是否 启用
						var base_accordant = $item.base_accordant ? $item.base_accordant : false;//社保与公积金联动(基数)
						var cross_year = $item.cross_year ? $item.cross_year : false;//是否允许跨年


						$verBack.find(".soin_type_version_effect_year").val(effect_year);
						$verBack.find(".soin_type_version_effect_month").val(effect_month);
						$verBack.find(".soin_type_version_least_month").val(at_least);
						$verBack.find(".soin_type_version_most_month").val(at_most);
						$verBack.find(".soin_type_version_late_fee").val(late_fee);
						$verBack.find(".soin_type_version_is_use").find("input")
							.prop("checked", is_active);
						$verBack.find(".soin_type_version_is_cross_year").find("input")
							.prop("checked", cross_year);
						$verBack.find(".base_accordant_checkbox").find("input")
							.prop("checked", base_accordant);

						//赋值 险种 信息
						soin_base_info_manage.setRuleInfo($verBack, data.result);


					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//社保类型 版本详情 保存（补缴）
	soinTypeVerBackDetailSave: function () {
		var $verBack = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#back_version");

		var rulePension = soin_base_info_manage.getRuleInfo($verBack, "pension");
		var ruleMedical = soin_base_info_manage.getRuleInfo($verBack, "medical");
		var ruleUnemployment = soin_base_info_manage.getRuleInfo($verBack, "unemployment");
		var ruleInjury = soin_base_info_manage.getRuleInfo($verBack, "injury");
		var rulePregnancy = soin_base_info_manage.getRuleInfo($verBack, "pregnancy");
		var ruleDisability = soin_base_info_manage.getRuleInfo($verBack, "disability");
		var ruleSevereIllness = soin_base_info_manage.getRuleInfo($verBack, "severe_illness");
		var ruleInjuryAddition = soin_base_info_manage.getRuleInfo($verBack, "injury_addition");
		var ruleHouseFund = soin_base_info_manage.getRuleInfo($verBack, "house_fund");
		var ruleHouseFundAddition = soin_base_info_manage.getRuleInfo($verBack, "house_fund_addition");
		var ruleHeating = soin_base_info_manage.getRuleInfo($verBack, "heating");

		var obj = {
			"id": soin_base_info_manage.current_soin_type_ver_back_id,
			"effect_year": $verBack.find(".soin_type_version_effect_year").val(),
			"effect_month": $verBack.find(".soin_type_version_effect_month").val(),
			"at_least": $verBack.find(".soin_type_version_least_month").val(),
			"at_most": $verBack.find(".soin_type_version_most_month").val(),
			"late_fee": $verBack.find(".soin_type_version_late_fee").val(),
			"base_accordant": $verBack.find(".base_accordant_checkbox input").is(":checked")
				? 1 : 0,
			"is_active": $verBack.find(".soin_type_version_is_use input").is(":checked")
				? 1 : 0,
			"cross_year": $verBack.find(".soin_type_version_is_cross_year input").is(":checked")
				? 1 : 0,
			"rule_pension": rulePension,
			"rule_medical": ruleMedical,
			"rule_unemployment": ruleUnemployment,
			"rule_injury": ruleInjury,
			"rule_pregnancy": rulePregnancy,
			"rule_disability": ruleDisability,
			"rule_severe_illness": ruleSevereIllness,
			"rule_injury_addition": ruleInjuryAddition,
			"rule_house_fund": ruleHouseFund,
			"rule_house_fund_addition": ruleHouseFundAddition,
			"rule_heating": ruleHeating
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_type_version_back_save,
			obj,
			function (data) {

				//console.log("修改 版本详情：");
				//console.log(data);
				if (data.code == 1000) {
					toastr.success("社保类型 版本详情 保存成功！");
				}
				else {
					toastr.warning(data.msg);
				}

			},
			function () {
				toastr.error("提交失败：请重试！");
			});

	},
	//社保类型 版本详情 新增 - 弹框显示（补缴）
	soinTypeVerBackAddModalShow: function () {
		$add_soin_type_ver_back_modal.modal("show");
	},
	//社保类型 版本详情 新增（补缴）
	soinTypeVerBackAdd: function () {

		var obj = {
			"type_id": soin_base_info_manage.current_soin_type_id,
			"effect_year": $add_soin_type_ver_back_modal.find(".soin_type_version_effect_year").val(),
			"effect_month": $add_soin_type_ver_back_modal.find(".soin_type_version_effect_month").val()
		};

		aryaPostRequest(
			urlGroup.soin_type_version_back_add,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("新增社保版本类型（补缴）成功！");
					$add_soin_type_ver_back_modal.modal("hide");

					soin_base_info_manage.initSoinTypeVerBack();//初始化 类型版本 （补缴）

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//社保类型 版本详情 删除（补缴）
	soinTypeVerBackDel: function () {
		var $verBack = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#back_version");

		var $soin_type_ver_list = $verBack.find(".btn_list_container .soin_type_version_list");

		//如果没有 选中补缴版本
		if ($soin_type_ver_list.find(".btn-primary").length <= 0) {
			msgShow("请先选择补缴版本");
			return
		}

		var txt = $soin_type_ver_list.find(".btn-primary").text();

		delWarning("确定要删除" + "\"" + txt + "\"" + "补缴版本吗？", function () {

			loadingInit();

			var obj = {
				type_version_id: $soin_type_ver_list.find(".btn-primary").attr("data-id")
			};

			aryaPostRequest(
				urlGroup.soin_type_version_del,
				obj,
				function (data) {
					//console.log("获取日志：");
					//console.log(data);

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除社保版本类型（补缴）成功！");

						soin_base_info_manage.initSoinTypeVerBack();//初始化 类型版本 （补缴）

					}
					else {
						toastr.warning(data.msg);
					}

				},
				function (error) {
					toastr.error(error);
				}
			);

		});

	},


	//初始化 类型版本 （正常）
	initSoinTypeVerNormal: function () {
		//var $verNormal = $(soin_base_info_manage.containerName)
		//	.find(".soin_type_version_container").find("#normal_version");
		soin_base_info_manage.soinTypeVerNormalListGet();//获取 社保类型 版本列表（正常）
		soin_base_info_manage.soinTypeVerNormalDetailClear();//清空 社保类型 版本详情（正常）
		soin_base_info_manage.hideVerNormalSubBtn();//隐藏 社保类型 版本详情 提交按钮（正常）

	},
	//获取 社保类型 版本列表（正常）
	soinTypeVerNormalListGet: function () {

		var $verNormal = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#normal_version");

		var obj = {};
		obj.type_id = soin_base_info_manage.current_soin_type_id;
		var url = urlGroup.soin_type_version_normal_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					//根据用户权限 判断是否显示 新增、删除按钮
					soin_base_info_manage.initAddAndDelBtn($verNormal);

					var $verTypeContainer = $verNormal
						.find(".btn_list_container .btn_list").empty();

					if (data.result) {

						var types = data.result;
						if (!types || types.length == 0) {
						}
						else {

							for (var i = 0; i < types.length; i++) {
								var $item = types[i];

								var id = $item.id;//
								var effect_year = $item.effect_year;//
								var effect_month = $item.effect_month;//
								var name = effect_year + "年" + effect_month + "月起生效";//

								var type_item = $("<span>");
								type_item.addClass("btn");
								type_item.addClass("btn-sm");
								type_item.addClass("btn-default");
								type_item.attr("data-id", id);
								type_item.text(name);
								type_item.click(function () {

									soin_base_info_manage.current_soin_type_ver_normal_id =
										$(this).attr("data-id");
									$(this).addClass("btn-primary").removeClass("btn-default");
									$(this).siblings().removeClass("btn-primary").addClass("btn-default");

									//获取 社保类型 版本详情（正常）
									soin_base_info_manage.soinTypeVerNormalDetail();

									//有权限
									if (soin_base_info_manage.checkUserJurisdiction()) {

										$verNormal.find("input").removeAttr("disabled");
										$verNormal.find("textarea").removeAttr("disabled");

										//社保类型 版本（正常） 提交按钮 显示
										soin_base_info_manage.showVerNormalSubBtn();
									}
									else {

										$verNormal.find("input")
											.attr("disabled", "disabled");
										$verNormal.find("textarea")
											.attr("disabled", "disabled");

										//社保类型 版本（正常） 提交按钮 隐藏
										soin_base_info_manage.hideVerNormalSubBtn();

									}

								});

								type_item.appendTo($verTypeContainer);

							}

						}

					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			}
		);

	},
	//清空 社保类型 版本详情（正常）
	soinTypeVerNormalDetailClear: function () {
		var $verNormal = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#normal_version");

		$verNormal.find("input").val("").attr("disabled", "disabled");
		$verNormal.find("textarea").val("").attr("disabled", "disabled");
		$verNormal.find(".togglebutton_container input").prop("checked", false);
	},
	//获取 社保类型 版本详情（正常）
	soinTypeVerNormalDetail: function () {
		var $verNormal = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#normal_version");

		var obj = {};
		obj.version_id = soin_base_info_manage.current_soin_type_ver_normal_id;
		var url = urlGroup.soin_type_version_normal_detail + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {

						var $item = data.result;

						var effect_year = $item.effect_year ? $item.effect_year : "";//
						var effect_month = $item.effect_month ? $item.effect_month : "";//
						var is_active = $item.is_active ? $item.is_active : false;//
						var base_accordant = $item.base_accordant ? $item.base_accordant : false;//

						$verNormal.find(".soin_type_version_effect_year").val(effect_year);
						$verNormal.find(".soin_type_version_effect_month").val(effect_month);
						$verNormal.find(".soin_type_version_is_use").find("input")
							.prop("checked", is_active);
						$verNormal.find(".base_accordant_checkbox").find("input")
							.prop("checked", base_accordant);

						//赋值 险种 信息
						soin_base_info_manage.setRuleInfo($verNormal, data.result);

					}

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//社保类型 版本详情 保存（正常）
	soinTypeVerNormalDetailSave: function () {
		var $verNormal = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#normal_version");

		var baseAccordant = $verNormal.find(".base_accordant_checkbox input").is(":checked")
			? 1 : 0;
		var is_active = $verNormal.find(".soin_type_version_is_use input").is(":checked")
			? 1 : 0;

		var rulePension = soin_base_info_manage.getRuleInfo($verNormal, "pension");
		var ruleMedical = soin_base_info_manage.getRuleInfo($verNormal, "medical");
		var ruleUnemployment = soin_base_info_manage.getRuleInfo($verNormal, "unemployment");
		var ruleInjury = soin_base_info_manage.getRuleInfo($verNormal, "injury");
		var rulePregnancy = soin_base_info_manage.getRuleInfo($verNormal, "pregnancy");
		var ruleDisability = soin_base_info_manage.getRuleInfo($verNormal, "disability");
		var ruleSevereIllness = soin_base_info_manage.getRuleInfo($verNormal, "severe_illness");
		var ruleInjuryAddition = soin_base_info_manage.getRuleInfo($verNormal, "injury_addition");
		var ruleHouseFund = soin_base_info_manage.getRuleInfo($verNormal, "house_fund");
		var ruleHouseFundAddition = soin_base_info_manage.getRuleInfo($verNormal, "house_fund_addition");
		var ruleHeating = soin_base_info_manage.getRuleInfo($verNormal, "heating");

		var obj = {
			"id": soin_base_info_manage.current_soin_type_ver_normal_id,
			"effect_year": $verNormal.find(".soin_type_version_effect_year").val(),
			"effect_month": $verNormal.find(".soin_type_version_effect_month").val(),
			"base_accordant": baseAccordant,
			"is_active": is_active,
			"rule_pension": rulePension,
			"rule_medical": ruleMedical,
			"rule_unemployment": ruleUnemployment,
			"rule_injury": ruleInjury,
			"rule_pregnancy": rulePregnancy,
			"rule_disability": ruleDisability,
			"rule_severe_illness": ruleSevereIllness,
			"rule_injury_addition": ruleInjuryAddition,
			"rule_house_fund": ruleHouseFund,
			"rule_house_fund_addition": ruleHouseFundAddition,
			"rule_heating": ruleHeating
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.soin_type_version_normal_save,
			obj,
			function (data) {

				//console.log("修改 版本详情：");
				//console.log(data);
				if (data.code == 1000) {
					toastr.success("社保类型 版本详情 保存成功！");
				}
				else {
					toastr.warning(data.msg);
				}

			},
			function () {
				toastr.error("提交失败：请重试！");
			});

	},
	//社保类型 版本详情 新增 - 弹框显示（正常）
	soinTypeVerNormalAddModalShow: function () {
		$add_soin_type_ver_normal_modal.modal("show");
	},
	//社保类型 版本详情 新增（正常）
	soinTypeVerNormalAdd: function () {

		var obj = {
			"type_id": soin_base_info_manage.current_soin_type_id,
			"effect_year": $add_soin_type_ver_normal_modal.find(".soin_type_version_effect_year").val(),
			"effect_month": $add_soin_type_ver_normal_modal.find(".soin_type_version_effect_month").val()
		};

		aryaPostRequest(
			urlGroup.soin_type_version_normal_add,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("新增社保版本类型（正常）成功！");
					$add_soin_type_ver_normal_modal.modal("hide");

					soin_base_info_manage.initSoinTypeVerNormal();//初始化 类型版本 （正常）

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	},
	//社保类型 版本详情 删除（正常）
	soinTypeVerNormalDel: function () {
		var $verNormal = $(soin_base_info_manage.containerName)
			.find(".soin_type_version_container").find("#normal_version");

		var $soin_type_ver_list = $verNormal.find(".btn_list_container .soin_type_version_list");

		//如果没有 选中补缴版本
		if ($soin_type_ver_list.find(".btn-primary").length <= 0) {
			msgShow("请先选择正常版本");
			return
		}

		var txt = $soin_type_ver_list.find(".btn-primary").text();

		delWarning("确定要删除" + "\"" + txt + "\"" + "正常版本吗？", function () {

			loadingInit();

			var obj = {
				type_version_id: $soin_type_ver_list.find(".btn-primary").attr("data-id")
			};

			aryaPostRequest(
				urlGroup.soin_type_version_del,
				obj,
				function (data) {
					//console.log("获取日志：");
					//console.log(data);

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除社保版本类型（正常）成功！");

						soin_base_info_manage.initSoinTypeVerNormal();//初始化 类型版本 （正常）

					}
					else {
						toastr.warning(data.msg);
					}

				},
				function (error) {
					toastr.error(error);
				}
			);

		});

	},

	//赋值 险种 信息
	setRuleInfo: function (container, data) {
		var $table = container.find(".soin_type_version_detail_table").find("tbody");
		//debugger
		$table.find("input").val("");//清空默认值

		$.each(data, function (key, value) {
			//如果包含rule_则是险种
			if (key.indexOf("rule_") != -1) {

				$.each(value, function (subKey, subValue) {

					var keyName = "." + key.replace("rule_", "") + "_" + subKey;
					$table.find(keyName).val(subValue);

				});

			}

		});

	},
	//获取 险种详情
	getRuleInfo: function (container, ruleTypeName) {
		var isNullObj = false;
		var $table = container.find(".soin_type_version_detail_table").find("tbody");

		var rule = {};
		rule["percentage_person"] = $table.find("." + ruleTypeName + "_percentage_person").val();
		rule["percentage_corp"] = $table.find("." + ruleTypeName + "_percentage_corp").val();
		rule["extra_person"] = $table.find("." + ruleTypeName + "_extra_person").val();
		rule["extra_corp"] = $table.find("." + ruleTypeName + "_extra_corp").val();
		rule["min_base"] = $table.find("." + ruleTypeName + "_min_base").val();
		rule["max_base"] = $table.find("." + ruleTypeName + "_max_base").val();
		rule["desc"] = $table.find("." + ruleTypeName + "_desc").val();
		rule["pay_month"] = $table.find("." + ruleTypeName + "_pay_month").val();

		$.each(rule, function (key, value) {
			if (value) {
				isNullObj = true;
			}
		});

		if (isNullObj) {
			return rule;
		}
		else {
			return null;
		}

	}


};

$(function () {

	soin_base_info_manage.Init();//初始化 方法

});

function initSoinBaseTree(treeId, hudId, url, oncheckFunc, onclickFunc) {
	showHUD(hudId);

	aryaGetRequest(
		url,
		function (data) {
			//console.log("获取日志：");
			//console.log(data);

			dismissHUD(hudId);//

			if (data.code == RESPONSE_OK_CODE) {

				var zNodes = [];

				var trees = data.result.tree;
				if (!trees && trees.length == 0) {
				}
				else {
					for (var i = 0; i < trees.length; i++) {
						var $item = trees[i];

						var id = $item.id;//
						var name = $item.name;//
						var pId = $item.parent_id;//
						var can_up_super = $item.can_up_super;//能否允许向上一级并列',0不允许，1允许
						var up_super = $item.up_super;//是否已经向上一级并列',0否1是

						var tree_item = {
							"id": id,
							"pId": pId,
							"name": name,
							"can_up_super": can_up_super,
							"up_super": up_super
						};

						zNodes.push(tree_item);

					}
				}

				showTree(treeId, zNodes, oncheckFunc, onclickFunc);

			}
			else {
				//console.log("获取日志-----error：");
				//console.log(data.msg);

				toastr.warning(data.msg);
			}
		},
		function (error) {
			toastr.error(error);

			dismissHUD(hudId);//

		}
	);

}

function showTree(treeId, zNodes, oncheckFunc, onclickFunc) {
	if (!zNodes) {   //数据为空
		zNodes = [];
	}
	//
	//zNodes = [
	//	{"id": 1, "pId": 0, "name": "test1"},
	//	{"id": 11, "pId": 1, "name": "test11"},
	//	{"id": 12, "pId": 1, "name": "test12"},
	//	{"id": 111, "pId": 11, "name": "test111"},
	//	{"id": 112, "pId": 11, "name": "test112"},
	//	{"id": 1121, "pId": 112, "name": "test1121"}
	//];

	var setting = {
		view: {
			//selectedMulti: false,//禁止选择多项
			//dblClickExpand: false,//双击节点 不切换 展开状态
			showIcon: false,
			showLine: false
		},
		callback: {

			onClick: function (event, treeId, treeNode) {
				if (onclickFunc)
					onclickFunc(treeNode);
			},
			onCheck: function (event, treeId, treeNode) {		//选中节点后 执行方法

				if (oncheckFunc)
					oncheckFunc(treeNode);

			}
		},
		check: {
			enable: true,	//是否显示 勾选框
			chkStyle: "checkbox",	//多选框
			chkboxType: {"Y": "", "N": ""},		//
			autoCheckTrigger: true		//选中/取消 节点后 触发 beforeCheck / onCheck
		},
		data: {
			simpleData: {   //简单数据格式
				enable: true
			}
		}
	};

	zTreeObj = $.fn.zTree.init(treeId, setting, zNodes);
	zTreeObj.expandAll(true);//全部展开
}

