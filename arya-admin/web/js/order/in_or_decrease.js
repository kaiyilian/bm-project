/**
 * Created by xiaomi on 17/2/22.
 */
/**
 * Created by CuiMengxin on 2016/8/2.
 * 订单管理
 */

var $soin_area_modals = $(".soin_area_modals");//参保地区 弹框
var $soin_pay_fail_modal = $(".soin_pay_fail_modal");//缴纳失败 原因
var $soin_pay_fail_all_modal = $(".soin_pay_fail_all_modal");//
var $order_detail_modals = $(".order_detail_modals");//订单详情

//订单管理
var in_or_decrease_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面
	alreadyChooseMonth: "",//已选择的月份
	alreadyEndChooseMonth: "",//已选择的月份

	currentTreeNode: "",//当前选中的 参保地区
	sort_name: "",//排序字段 名称
	sort_code: "",//排序 正序1 倒序2

	//初始化
	Init: function () {
		in_or_decrease_manage.containerName = ".in_or_decrease_manage_container";

		in_or_decrease_manage.initMonthChoose();//初始化 月份  选择
		in_or_decrease_manage.initSearchCondition();//一键重置

		//缴纳失败 显示
		$soin_pay_fail_modal.on("shown.bs.modal", function () {
			$soin_pay_fail_modal.find(".pay_fail_reason input").val("");
		});

		//缴纳失败(所有用户) 显示
		$soin_pay_fail_all_modal.on("shown.bs.modal", function () {
			$soin_pay_fail_all_modal.find(".pay_fail_reason input").val("");
		});

	},

	//查询条件 一键复原 - 初始化
	initSearchCondition: function () {
		in_or_decrease_manage.initUserType();//初始化 用户类型
		in_or_decrease_manage.initSoinArea();//初始化 参保地区
		in_or_decrease_manage.initSupplierList("");//初始化 供应商列表
		in_or_decrease_manage.initPayStatus();//初始化 缴纳状态
		in_or_decrease_manage.initIncreaseOrDecrease();//初始化 增减员
		//order_manage.IsChooseAll();//是否 已经全部选择

		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		//初始化 月份  选择
		$search_container.find(".order_month").html("请选择开始月份");
		$search_container.find(".order_end_month").html("请选择结束月份");
		in_or_decrease_manage.alreadyChooseMonth = null;
		in_or_decrease_manage.alreadyEndChooseMonth = null;

		//关键字 置空
		$search_container.find(".search_condition").val("");

		//清除 选中的标签
		$('.chosen-select').find("option:selected").removeAttr("selected");//清空选中状态
		$('.chosen-select').trigger("chosen:updated");   //每次对 select操作后必须要执行 此 方法

	},
	//初始化 用户类型
	initUserType: function () {
		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		var $user_type_list = $search_container.find(".user_type_list");

		loadingInit();

		aryaGetRequest(
			urlGroup.order_manage_cus_list_get_url,
			function (data) {
				//alert(JSON.stringify(data));
				if (data.code == ERR_CODE_OK) {
					var list = "<option value=''>全部</option>";

					if (data.result && data.result.customer) {

						$.each(data.result["customer"], function (index, customer) {
							list += "<option value='" + customer.id + "'>" +
								customer.name +
								"</option>";
						});

						$user_type_list.html(list);

						$user_type_list.chosen({
							allow_single_deselect: true,//选择之后 是否可以取消
							max_selected_options: 1,//最多只能选择1个
							width: "100%",//select框 宽度
							no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
						});

						$user_type_list.siblings(".chosen-container").addClass("form-control")
							.css("padding", "0");

					}

				}
				else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			}
		);

	},
	//初始化 参保地区
	initSoinArea: function () {
		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		var $soin_area = $search_container.find(".soin_area");

		$soin_area.attr("data-district_id", "");
		$soin_area.val("请选择地区");

		//获取 地区列表
		initAreaTree(
			".soin_area_modals .soin_all_area_trees",//tree id
			".soin_area_modals .all_area_tree_hud",//动画 id
			urlGroup.order_manage_district_list_get_url,
			function (treeNode) {
				in_or_decrease_manage.ChooseCity(treeNode);
			}
		);

	},
	//初始化 供应商列表
	initSupplierList: function (districtId) {


		//清除 选中的标签
		//$('.supplier_list').find("option:selected").removeAttr("selected");//清空选中状态
		//$('.supplier_list').trigger("chosen:updated");   //每次对 select操作后必须要执行 此 方法

		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		var $supplier_list = $search_container.find(".supplier_list");

		//判断 是否已经存在chosen
		if ($supplier_list.siblings(".chosen-container").length > 0) {
			$supplier_list.chosen("destroy");
		}

		loadingInit();

		var url = urlGroup.order_manage_supplier_list_get_url +
			"?district_id=" + districtId;

		aryaGetRequest(
			url,
			function (data) {
				console.log(data);

				if (data.code == ERR_CODE_OK) {

					if (data.result && data.result.suppliers) {

						$supplier_list.empty();
						var option = $("<option value=''>全部</option>");
						option.appendTo($supplier_list);

						if (data.result.suppliers.length > 0) {
							$.each(data.result["suppliers"], function (index, customer) {
								var option = $("<option></option>");
								option.appendTo($supplier_list);
								option.val(customer.id);
								option.text(customer.name);
							});
						}

						$supplier_list.chosen({
							allow_single_deselect: true,//选择之后 是否可以取消
							max_selected_options: 1,//最多只能选择1个
							width: "100%",//select框 宽度
							no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
						});

						$supplier_list.siblings(".chosen-container").addClass("form-control")
							.css("padding", "0");

					}

				}
				else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			}
		);

	},
	//初始化 月份  选择
	initMonthChoose: function () {
		$(in_or_decrease_manage.containerName).find(".order_month")
			.datepicker({
				minViewMode: 1,
				keyboardNavigation: false,
				forceParse: false,
				autoclose: true,
				todayHighlight: true,
				format: 'yyyymm'
			})
			.on("changeMonth", function (e) {
				//alert(JSON.stringify(e));

				//更换月份事件
				var choseDate = e.date;
				choseDate = new Date(choseDate);
				var year = choseDate.getFullYear().toString();
				var month = (choseDate.getMonth() + 1) < 10 ? ("0" + (choseDate.getMonth() + 1)) :
					(choseDate.getMonth() + 1);
				month = month.toString();
				var yearMonth = year + month;

				//选择了不同的月份
				if (yearMonth != in_or_decrease_manage.alreadyChooseMonth) {
					in_or_decrease_manage.alreadyChooseMonth = yearMonth;
					$(in_or_decrease_manage.containerName).find(".order_month").html(yearMonth);
				}
				else {
					$(in_or_decrease_manage.containerName).find(".order_month").html("请选择开始月份");
					in_or_decrease_manage.alreadyChooseMonth = null;
				}

			});
		$(in_or_decrease_manage.containerName).find(".order_end_month")
			.datepicker({
				minViewMode: 1,
				keyboardNavigation: false,
				forceParse: false,
				autoclose: true,
				todayHighlight: true,
				format: 'yyyymm'
			})
			.on("changeMonth", function (e) {
				//alert(JSON.stringify(e));

				//更换月份事件
				var choseDate = e.date;
				choseDate = new Date(choseDate);
				var year = choseDate.getFullYear().toString();
				var month = (choseDate.getMonth() + 1) < 10 ? ("0" + (choseDate.getMonth() + 1)) :
					(choseDate.getMonth() + 1);
				month = month.toString();
				var yearMonth = year + month;

				//选择了不同的月份
				if (yearMonth != in_or_decrease_manage.alreadyEndChooseMonth) {
					in_or_decrease_manage.alreadyEndChooseMonth = yearMonth;
					$(in_or_decrease_manage.containerName).find(".order_end_month").html(yearMonth);
				}
				else {
					$(in_or_decrease_manage.containerName).find(".order_end_month").html("请选择结束月份");
					in_or_decrease_manage.alreadyEndChooseMonth = null;
				}

			});
	},
	//初始化 缴纳状态
	initPayStatus: function () {

		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		var $order_pay_status = $search_container.find(".order_pay_status");

		var option = "<option value=''>全部</option>" +
			"<option value='1'>缴纳成功</option>" +
			"<option value='2'>缴纳失败</option>" +
			"<option value='3'>未操作</option>";

		$order_pay_status.html(option);

		$order_pay_status.chosen({
			allow_single_deselect: true,//选择之后 是否可以取消
			max_selected_options: 1,//最多只能选择1个
			width: "100%",//select框 宽度
			no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
		});

		$order_pay_status.siblings(".chosen-container").addClass("form-control")
			.css("padding", "0");

	},
	//初始化 增减员
	initIncreaseOrDecrease: function () {

		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");
		var $increase_or_decrease_status = $search_container.find(".increase_or_decrease_status");

		var option = "<option value='1'>增员</option>" +
			"<option value='2'>减员</option>" +
			"<option value='3'>非顺延</option>";

		$increase_or_decrease_status.html(option);

		$increase_or_decrease_status.chosen({
			allow_single_deselect: true,//选择之后 是否可以取消
			max_selected_options: 1,//最多只能选择1个
			width: "100%",//select框 宽度
			no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
		});

		$increase_or_decrease_status.siblings(".chosen-container").addClass("form-control")
			.css("padding", "0");

	},

	//参保地区 - 弹框显示
	SoinAreaModalShows: function () {
		$soin_area_modals.modal("show");
	},
	//选中对应的城市
	ChooseCity: function (treeNode) {
		in_or_decrease_manage.currentTreeNode = treeNode;
	},
	//参保地区 - 确认选中
	SoinAreaSure: function () {
		//如果没有选择 地区
		if (in_or_decrease_manage.currentTreeNode == "") {
			toastr.warning("请选择地区！");
			return;
		}

		$soin_area_modals.modal("hide");

		loadingInit();//加载框 出现

		var id = in_or_decrease_manage.currentTreeNode.id;//地区id
		var name = in_or_decrease_manage.currentTreeNode.name;

		$(in_or_decrease_manage.containerName).find(".search_container .soin_area")
			.val(name).attr("data-district_id", id);

		in_or_decrease_manage.initSupplierList(id);//初始化 供应商列表
	},

	//订单查询 - 按钮点击
	btnSearchClick: function () {
		in_or_decrease_manage.currentPage = 1;
		in_or_decrease_manage.orderList();//订单查询 - 列表
	},
	//排序
	orderSort: function (self) {
		var sort_name = $(self).closest(".sort_list").attr("data-sortName");
		var sort_code = $(self).attr("data-sort");

		in_or_decrease_manage.sort_code = sort_code;
		in_or_decrease_manage.sort_name = sort_name;
		in_or_decrease_manage.btnSearchClick();//查询
	},
	//订单查询 - 列表
	orderList: function () {
		var $table_container = $(in_or_decrease_manage.containerName).find(".table_container");
		var $table = $table_container.find("table");

		//请求
		if ((in_or_decrease_manage.alreadyChooseMonth == null ||
			in_or_decrease_manage.alreadyChooseMonth == "") &&
			(in_or_decrease_manage.alreadyEndChooseMonth == null ||
			in_or_decrease_manage.alreadyEndChooseMonth == "")) {
			toastr.warning("请选择服务开年月或结束年月!");
			return;
		}
		if (in_or_decrease_manage.alreadyChooseMonth != null && in_or_decrease_manage.alreadyEndChooseMonth != null) {
			var b_time = Number(in_or_decrease_manage.alreadyChooseMonth);
			var e_time = Number(in_or_decrease_manage.alreadyEndChooseMonth);
			if (b_time > e_time) {
				toastr.warning("开始时间不能大于结束时间！");

				// messageCue("开始时间不能大于结束时间！");
				return
			}
		}

		loadingInit();//加载框 出现

		in_or_decrease_manage.searchConditionSet();//赋值 查询条件

		var obj = {};
		obj.customer_id = searchCondition_group.user_type_id;
		obj.district_id = searchCondition_group.soin_arya_id;//地区id
		obj.supplier_id = searchCondition_group.supplier_id;//供应商id
		obj.year_month = searchCondition_group.choose_month;
		obj.end_year_month = searchCondition_group.choose_end_month;
		// obj.payed_status = searchCondition_group.pay_status_id;
		obj.modify = searchCondition_group.increase_or_decrease_status_id;
		obj.keyword = searchCondition_group.search_condition;
		obj.sort_name = in_or_decrease_manage.sort_name;
		obj.sort_code = in_or_decrease_manage.sort_code;
		obj.page = in_or_decrease_manage.currentPage;
		obj.page_size = "10";

		var url = urlGroup.order_in_or_decrease_list_get_url + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log(data);
				console.log(JSON.stringify(data));

				if (data.code == ERR_CODE_OK) {

					if (data.result) {

						in_or_decrease_manage.totalPage = data.result["pages"];

						var order_list = "";
						var orders = data.result.orders;//
						if (!orders || orders.length == 0) {
							order_list = "<tr>" +
								"<td colspan='12'>暂无订单信息</td>" +
								"</tr>";
						}
						else {
							$.each(orders, function (index, orders) {

								var order_id = orders.id;
								var order_subject = orders.subject ? orders.subject : "";// 缴纳主体
								var order_name = orders.name ? orders.name : "";// 参保人姓名
								var order_idcard = orders.idcard ? orders.idcard : "";// 参保人身份证号码
								var order_soin_district = orders.district ? orders.district : "";// 参保地区
								var order_soin_type = orders.soin_type ? orders.soin_type : "";// 参保类型
								var order_service_month = orders.service_year_month ? orders.service_year_month : "";// 服务月份
								var order_pay_year_month = orders.pay_year_month ? orders.pay_year_month : "";// 缴纳月份

								// 缴纳月份
								// var order_pay_month_content = "";
								// var order_pay_month_status = "0";//缴纳月份 状态 0代表正常，1代表错误
								// if (orders.pay_year_month) {
								// 	order_pay_month_status = orders.pay_year_month.status;
								//
								// 	if (order_pay_month_status == 0) {
								// 		order_pay_month_content += "<div>" + orders.pay_year_month.content + "</div>";
								// 	}
								// 	else {
								// 		order_pay_month_content += "<div class='is_red'>" + orders.pay_year_month.content + "</div>";
								// 	}
								// }

								// 补缴月份
								var order_back_start_year_month = orders.back_start_year_month ? orders.back_start_year_month : "无";// 补缴开始月份
								var order_back_end_year_month = orders.back_end_year_month ? orders.back_end_year_month : "无";// 补缴结束月份

								var order_house_fund_percent = orders.house_fund_percent ? orders.house_fund_percent : " ";;// 公积金比例
								var order_fee_in = orders.fee_in;// 收账服务费
								var increase_or_decrease = orders.modify ? orders.modify : "";// 增减员


								order_list += "<tr class='item order_item'" +
									" data-id='" + order_id + "' "+
									">" +
									"<td class='order_subject'>" + order_subject + "</td>" +
									"<td class='order_user_name'>" + order_name + "</td>" +
									"<td class='order_user_idCard'>" + order_idcard + "</td>" +
									"<td class='order_soin_district'>" + order_soin_district + "</td>" +
									"<td class='order_soin_type'>" + order_soin_type + "</td>" +
									"<td class='order_service_month'>" + order_service_month + "</td>" +
									"<td class='order_pay_month'>" + order_pay_year_month + "</td>" +
									"<td class='order_back_pay_month'>" + order_back_start_year_month + "</td>" +
									"<td class='order_back_end_year_month'>" + order_back_end_year_month + "</td>" +
									"<td class='increase_or_decrease'>" + increase_or_decrease + "</td>" +
									"<td class='order_house_fund_percent'>" + order_house_fund_percent + "</td>" +
									"<td class='order_fee_in'>" + order_fee_in + "</td>" +
									"</tr>";

							});
						}

						$table.find("tbody").html(order_list);

						in_or_decrease_manage.orderListInit();

					}
					else {
						var content = "<tr>" +
							"<td colspan='12'>暂无订单信息</td>" +
							"</tr>";
						$table.find("tbody").html(content);
					}
				}
				else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			});

	},
	//订单列表 初始化
	orderListInit: function () {

		var $item = $(in_or_decrease_manage.containerName).find("tbody .item");
		var $pager_container = $(in_or_decrease_manage.containerName).find(".pager_container");

		//判断查询 结果为空
		if ($item.length == 0) {
			$pager_container.hide();
			return
		}

		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: in_or_decrease_manage.currentPage, //当前页数
			totalPages: in_or_decrease_manage.totalPage, //总页数
			numberOfPages: 5,//每页显示的 页数
			useBootstrapTooltip: true,//是否使用 bootstrap 自带的提示框
			itemContainerClass: function (type, page, currentpage) {  //每项的类名
				//alert(type + "  " + page + "  " + currentpage)
				var classname = "p_item ";

				switch (type) {
					case "first":
						classname += "p_first";
						break;
					case "last":
						classname += "p_last";
						break;
					case "prev":
						classname += "p_prev";
						break;
					case "next":
						classname += "p_next";
						break;
					case "page":
						classname += "p_page";
						break;
				}

				if (page == currentpage) {
					classname += " active "
				}

				return classname;
			},
			itemTexts: function (type, page, current) {  //
				switch (type) {
					case "first":
						return "首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "末页";
					case "page":
						return page;
				}
			},
			tooltipTitles: function (type, page, current) {
				switch (type) {
					case "first":
						return "去首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "去末页";
					case "page":
						return page === current ? "当前页数 " + page : "前往第 " + page + " 页"
				}
			},
			onPageClicked: function (event, originalEvent, type, page) { //点击事件
				//alert(page)

				var currentTarget = $(event.currentTarget);

				in_or_decrease_manage.currentPage = page;
				in_or_decrease_manage.orderList();//查询 满足条件的订单

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);
	},
	//赋值 查询条件
	searchConditionSet: function () {
		var $search_container = $(in_or_decrease_manage.containerName).find(".search_container");

		//客户类型id
		var user_type_id = $search_container.find(".user_type_list").val() ?
			$search_container.find(".user_type_list").val()[0] : "";
		//地区id
		var arya_id = $search_container.find(".soin_area").attr("data-district_id");
		//供应商id
		var supplier_id = $search_container.find(".supplier_list").val() ?
			$search_container.find(".supplier_list").val()[0] : "";
		//选择的月份
		var choose_month = in_or_decrease_manage.alreadyChooseMonth ?
			in_or_decrease_manage.alreadyChooseMonth : "";
		var choose_end_month = in_or_decrease_manage.alreadyEndChooseMonth ?
			in_or_decrease_manage.alreadyEndChooseMonth : "";
		//订单状态id
		var pay_status_id = $search_container.find(".order_pay_status").val() ?
			$search_container.find(".order_pay_status").val()[0] : "";
		//增减员状态id
		var increase_or_decrease_status_id = $search_container.find(".increase_or_decrease_status").val() ?
			$search_container.find(".increase_or_decrease_status").val()[0] : "";

		searchCondition_group.user_type_id = user_type_id;
		searchCondition_group.soin_arya_id = arya_id;
		searchCondition_group.supplier_id = supplier_id;
		searchCondition_group.choose_month = choose_month;
		searchCondition_group.choose_end_month = choose_end_month;

		searchCondition_group.pay_status_id = pay_status_id;
		searchCondition_group.increase_or_decrease_status_id = increase_or_decrease_status_id;
		searchCondition_group.search_condition = $.trim($search_container.find(".search_condition").val());

	},

	//订单导出
	orderExport: function () {

		if ($(in_or_decrease_manage.containerName).find("tbody .item").length <= 0) {
			messageCue("暂无订单信息可导出");
			return;
		}

		exportWarning("全部订单", function () {

			var $body = $("body");

			if ($body.find(".export_excel")) {
				$body.find(".export_excel").remove();
			}

			var form = $("<form>").addClass("export_excel");
			form.appendTo($body);
			form.attr("enctype", "multipart/form-data");
			form.attr("action", urlGroup.order_in_or_decrease_export_url);
			form.attr("method", "get");
			form.hide();

			form.append(
				$("<input>").attr("name", "customer_id")
					.attr("value", searchCondition_group.user_type_id)
			);
			form.append(
				$("<input>").attr("name", "district_id")
					.attr("value", searchCondition_group.soin_arya_id)
			);
			form.append(
				$("<input>").attr("name", "supplier_id")
					.attr("value", searchCondition_group.supplier_id)
			);
			form.append(
				$("<input>").attr("name", "year_month")
					.attr("value", searchCondition_group.choose_month)
			);
			form.append(
				$("<input>").attr("name", "end_year_month")
					.attr("value", searchCondition_group.choose_end_month)
			);
			// form.append(
			// 	$("<input>").attr("name", "payed_status")
			// 		.attr("value", searchCondition_group.pay_status_id)
			// );
			form.append(
				$("<input>").attr("name", "modify")
					.attr("value", searchCondition_group.increase_or_decrease_status_id)
			);
			form.append(
				$("<input>").attr("name", "keyword")
					.attr("value", searchCondition_group.search_condition)
			);

			form.submit();

		});

	}

};

//订单详情 弹框
// var order_detail = {
// 	order_id: "",//订单 id
// 	current_month_order_id: "",//当前月份 的 订单id
//
// 	init: function () {
//
// 		//订单详情 弹框显示
// 		$order_detail_modals.on("shown.bs.modal", function () {
//
// 			//获取月份列表
// 			order_detail.monthList();
//
// 		});
//
// 	},
//
// 	//订单 详情 - 弹框
// 	orderDetailModalShow: function (self) {
// 		//暂时不需要
// 		order_detail.order_id = $(self).attr("data-id");
//
// 		$order_detail_modals.modal("show");
// 	},
//
// 	//获取月份列表
// 	monthList: function () {
// 		var $modal_body = $order_detail_modals.find(".modal-body");
//
// 		var obj = {
// 			id: order_detail.order_id
// 		};
// 		var url = urlGroup.order_detail_month_list + "?" + jsonParseParam(obj);
//
// 		loadingInit();
//
// 		aryaGetRequest(
// 			url,
// 			function (data) {
// 				//console.log("获取日志：");
// 				//console.log(data);
//
// 				if (data.code == RESPONSE_OK_CODE) {
//
// 					var arr = data.result.details;
// 					var list = "";
// 					if (!arr || arr.length == 0) {
// 					}
// 					else {
// 						for (var i = 0; i < arr.length; i++) {
// 							var item = arr[i];
// 							var id = item.id;
// 							var name = item.name;
//
// 							list +=
// 								"<li role='presentation' data-id='" + id + "'"+
// 								"<a role='tab' data-toggle='tab'>" +
// 								name +
// 								"</a>" +
// 								"</li>"
//
// 						}
//
// 						$modal_body.find("#myTab").empty().html(list);
// 						$modal_body.find("#myTab").find("li").first().addClass("active");
// 						$modal_body.find("#myTab").find("li").first().click();
//
// 					}
//
// 				}
// 				else {
// 					//console.log("获取日志-----error：");
// 					//console.log(data.msg);
//
// 					messageCue(data.msg);
// 				}
// 			},
// 			function (error) {
// 				messageCue(error);
// 			}
// 		);
//
//
// 	},
// 	//获取 订单详情（具体到月份）
// 	orderDetail: function (self) {
//
// 		var $modal_body = $order_detail_modals.find(".modal-body");
// 		var $order_detail_container = $modal_body.find(".order_detail_container");
// 		var $table = $modal_body.find(".row table");
//
// 		order_detail.current_month_order_id = self ?
// 			$(self).attr("data-id") :
// 			$modal_body.find("#myTab li.active").attr("data-id");
//
// 		var obj = {
// 			id: order_detail.current_month_order_id
// 		};
// 		var url = urlGroup.order_detail_info + "?" + jsonParseParam(obj);
//
// 		loadingInit();
//
// 		aryaGetRequest(
// 			url,
// 			function (data) {
// 				//console.log("获取日志：");
// 				//console.log(data);
//
// 				if (data.code == RESPONSE_OK_CODE) {
//
// 					if (data.result) {
//
// 						var detail = data.result;
//
// 						var order_manage_fee = detail.fees ? detail.fees : "0";//管理费
// 						var order_total_in = detail.total_in ? detail.total_in : "0";//总金额
// 						var reason = detail.reason ? detail.reason : "无";//缴纳失败 原因
// 						var status = detail.status ? detail.status : "3";//缴纳状态
// 						var status_content = "";//
// 						//1是成功，2是失败 ,3 未操作
// 						if (status == 1) {		//成功
//
// 							status_content = "<div class='txt'>已缴纳成功</div>" +
// 								"<div class='btn btn-sm btn-primary' onclick='order_detail.payFailModalShow()'>" +
// 								"缴纳失败" +
// 								"</div>"
// 						}
// 						else if (status == 2) {		//失败
//
// 							status_content = "<div class='txt pay_fail' data-fail_reason='" + reason + "' " +
// 								"onclick='order_detail.payFailMsg(this)'>已缴纳失败</div>" +
// 								"<div class='btn btn-sm btn-primary' " +
// 								"onclick='order_detail.paySuccess()'>缴纳成功</div>"
// 						}
// 						else if (status == 3) {  //暂未缴纳 (显示 缴纳失败、缴纳成功)
// 							status_content = "<div class='btn btn-sm btn-primary' " +
// 								"onclick='order_detail.payFailModalShow()'>缴纳失败</div>" +
// 								"<div class='btn btn-sm btn-primary' " +
// 								"onclick='order_detail.paySuccess()'>缴纳成功</div>"
// 						}
//
// 						$order_detail_container.find(".order_manage_fee").html(order_manage_fee);
// 						$order_detail_container.find(".order_total_fee").html(order_total_in);
// 						$order_detail_container.find(".order_status").html(status_content);
//
// 						var list = "";
// 						//表格
// 						if (detail.rule_details && detail.rule_details.length > 0) {
//
// 							var arr = detail.rule_details;
// 							for (var i = 0; i < arr.length; i++) {
// 								var item = arr[i];
//
// 								var name = item.name ? item.name : "";//名称
// 								var injury = item.injury ? item.injury : "-";//工伤
// 								var medical = item.medical ? item.medical : "-";//医疗
// 								var pregnancy = item.pregnancy ? item.pregnancy : "-";//生育
// 								var pension = item.pension ? item.pension : "-";//养老
// 								var unemployment = item.unemployment ? item.unemployment : "-";//失业
// 								var house_fund = item.house_fund ? item.house_fund : "-";//公积金
// 								var disable = item.disable ? item.disable : "-";//残保
// 								var severe_illness = item.severe_illness ? item.severe_illness : "-";//大病医疗
// 								var injury_addition = item.injury_addition ? item.injury_addition : "-";//工伤补充
// 								var heating = item.heating ? item.heating : "-";//采暖费
// 								var house_fund_addition = item.house_fund_addition ? item.house_fund_addition : "-";//补充公积金
//
// 								list +=
// 									"<tr>" +
// 									"<td>" + name + "</td>" +
// 									"<td>" + injury + "</td>" +
// 									"<td>" + medical + "</td>" +
// 									"<td>" + pregnancy + "</td>" +
// 									"<td>" + pension + "</td>" +
// 									"<td>" + unemployment + "</td>" +
// 									"<td>" + house_fund + "</td>" +
// 									"<td>" + disable + "</td>" +
// 									"<td>" + severe_illness + "</td>" +
// 									"<td>" + injury_addition + "</td>" +
// 									"<td>" + heating + "</td>" +
// 									"<td>" + house_fund_addition + "</td>" +
// 									"</tr>"
// 							}
//
// 						}
// 						else {
// 							list = "<tr><td colspan='11'>暂无明细</td></tr>";
// 						}
//
// 						$table.find("tbody").html(list);
//
// 					}
//
// 				}
// 				else {
// 					console.log("获取日志-----error：");
// 					console.log(data.msg);
//
// 					messageCue(data.msg);
// 				}
// 			},
// 			function (error) {
// 				messageCue(error);
// 			}
// 		);
//
// 	},
//
// 	//缴纳成功
// 	paySuccess: function () {
//
// 		var arr = [];
// 		var obj = {
// 			id: order_detail.current_month_order_id
// 		};
// 		arr.push(obj);
//
// 		operateShow("是否确定缴纳成功!", function () {
//
// 			loadingInit();//加载框 出现
//
// 			var obj = {};
// 			obj.ids = arr;
//
// 			aryaPostRequest(
// 				urlGroup.order_manage_order_pay_success_url,
// 				obj,
// 				function (data) {
// 					//console.log("缴纳成功 操作成功：");
// 					//console.log(data);
//
// 					if (data.code == RESPONSE_OK_CODE) {
// 						toastr.success("缴纳成功 操作成功！");
// 						order_detail.orderDetail();//获取 订单详情（具体到月份）
// 					}
// 					else {
// 						messageCue(data.msg);
// 					}
//
// 				},
// 				function (error) {
// 					toastr.error(error);
// 				}
// 			);
//
// 		});
//
// 	},
// 	//缴纳失败 - 弹框
// 	payFailModalShow: function () {
// 		$soin_pay_fail_modal.modal("show");
// 	},
// 	//缴纳失败
// 	payFail: function () {
//
// 		var reason = $.trim($soin_pay_fail_modal.find(".pay_fail_reason input").val());
// 		if (reason == "") {
// 			toastr.warning("请输入原因");
// 			return;
// 		}
//
// 		var arr = [{
// 			id: order_detail.current_month_order_id
// 		}];
//
// 		loadingInit();//加载框 出现
//
// 		var obj = {};
// 		obj.ids = arr;
// 		obj.reason = reason;
//
// 		aryaPostRequest(
// 			urlGroup.order_manage_order_pay_fail_url,
// 			obj,
// 			function (data) {
// 				//alert(JSON.stringify(data));
//
// 				if (data.code == RESPONSE_OK_CODE) {
// 					toastr.success("缴纳失败操作成功！");
// 					$soin_pay_fail_modal.modal("hide");
// 					order_detail.orderDetail();//获取 订单详情（具体到月份）
// 				}
// 				else {
// 					messageCue(data.msg);
// 				}
//
// 			},
// 			function (error) {
// 				messageCue(error);
// 			}
// 		);
//
// 	},
// 	//缴纳失败 原因 - 弹框
// 	payFailMsg: function (self) {
// 		var reason = $(self).attr("data-fail_reason");
//
// 		msgShow(reason);
// 	}
//
//
// };

//查询 条件
var searchCondition_group = {
	user_type_id: "",	//用户类型
	soin_arya_id: "",	//参保地区id
	supplier_id: "",	//供应商id
	choose_month: "",	//选中的月份
	choose_end_month: "",	//选中的结束月份

	pay_status_id: "",	//订单状态id
	increase_or_decrease_status_id: "",	//增减员id
	search_condition: "",	//关键字

	init: function () {

	}

};

$(function () {
	in_or_decrease_manage.Init();
	// order_detail.init();


	$(".in_or_decrease_manage_container .content .table_container table thead tr td").each(function () {
		$(this).hover(
			function () {
				if ($(this).find(".sort_list").length > 0) {
					$(this).find(".sort_list").show();


				}
			},
			function () {
				if ($(this).find(".sort_list").length > 0) {
					$(this).find(".sort_list").hide();
				}
			}
		);

		$(this).find(".sort_list").find("img").each(function () {
			$(this).click(function () {
				in_or_decrease_manage.orderSort(this);
			});
		});
	});
});

var debug = {
	DelArray: "",//订单删除 -  数组
	CancelArray: "",//订单取消 -  数组
	PayFailArray: "",//缴纳失败 - 数组
	PaySuccessArray: "",//成功 - 数组

	//订单查询 - 列表
	orderList: function () {
		var $table_container = $(in_or_decrease_manage.containerName).find(".table_container");
		var $table = $table_container.find("table");

		//请求
		if ((in_or_decrease_manage.alreadyChooseMonth == null ||
			in_or_decrease_manage.alreadyChooseMonth == "") &&
			(in_or_decrease_manage.alreadyEndChooseMonth == null ||
			in_or_decrease_manage.alreadyEndChooseMonth == "")) {
			toastr.warning("请选择服务开年月或结束年月!");
			return;
		}
		if (in_or_decrease_manage.alreadyChooseMonth != null && in_or_decrease_manage.alreadyEndChooseMonth != null) {
			var b_time = Number(in_or_decrease_manage.alreadyChooseMonth);
			var e_time = Number(in_or_decrease_manage.alreadyEndChooseMonth);
			if (b_time > e_time) {
				toastr.warning("开始时间不能大于结束时间！");

				// messageCue("开始时间不能大于结束时间！");
				return
			}
		}

		loadingInit();//加载框 出现

		in_or_decrease_manage.searchConditionSet();//赋值 查询条件

		var obj = {};
		obj.customer_id = searchCondition_group.user_type_id;
		obj.district_id = searchCondition_group.soin_arya_id;//地区id
		obj.supplier_id = searchCondition_group.supplier_id;//供应商id
		obj.year_month = searchCondition_group.choose_month;
		obj.end_year_month = searchCondition_group.choose_end_month;

		obj.payed_status = searchCondition_group.pay_status_id;
		obj.sort_name = in_or_decrease_manage.sort_name;
		obj.sort_code = in_or_decrease_manage.sort_code;
		obj.page = in_or_decrease_manage.currentPage;
		obj.page_size = "10";

		var url = urlGroup.order_manage_list_get_url + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));
				//console.log(data);
				//console.log(JSON.stringify(data));

				if (data.code == ERR_CODE_OK) {

					in_or_decrease_manage.totalPage = data.result["pages"];

					var order_list = "";
					var orders = data.result.orders;//
					if (!orders || orders.length == 0) {
						order_list = "<tr>" +
							"<td colspan='12'>暂无订单信息</td>" +
							"</tr>";
					}
					else {
						$.each(orders, function (index, orders) {

							var order_id = orders.id;

							var order_payed_status;//'缴纳状态'，0是全部，1是成功，2是失败，3暂未缴纳(null/空)
							if (orders.payed_status) {
								order_payed_status = orders.payed_status;
							}
							else {
								order_payed_status = 3;
							}

							//var order_failed_reason;//: ' 缴纳失败原因',
							//if (orders.failed_reason) {
							//	order_failed_reason = orders.failed_reason;
							//}
							//else {
							//	order_failed_reason = "";
							//}

							var order_subject = orders.subject;// 缴纳主体
							var order_source = orders.source;// 订单来源 5 线下

							var order_soin_district = orders.soin_district;// 参保地区
							var order_soin_type = orders.soin_type;// 参保类型
							var order_service_month = orders.service_month;// 服务月份
							var order_pay_month = orders.pay_month;// 缴纳月份

							//var order_soin_base = orders.soin_base;// 社保基数
							//var order_soin_code = orders.soin_code;//社保编号

							//var order_housefund_code = orders.housefund_code;//公积金编号
							//var order_housefund_base;// 公积金基数
							//if (orders.housefund_base) {
							//	order_housefund_base = orders.housefund_base;
							//}
							//else {
							//	order_housefund_base = "-";
							//}
							//
							//var order_housefund_percent;// 公积金比例
							//if (orders.housefund_percent) {
							//	order_housefund_percent = orders.housefund_percent;
							//}
							//else {
							//	order_housefund_percent = "-";
							//}

							//var order_corp_subtotal = orders.corp_subtotal;// 企业部分社保费用小计
							//var order_person_subtotal = orders.person_subtotal;// 个人部分社保费用小计
							//var order_other_payment = orders.other_payment;// 其他费用
							//var order_collection_service_fee = orders.collection_service_fee;// 收账服务费
							//var order_charge_service_fee = orders.charge_service_fee;// 出账服务费
							var order_collection_total = orders.collection_total;// 收账总计
							var order_charge_total = orders.charge_total;// 出账总计
							var order_salesman = orders.salesman;// 业务员名称
							var order_supplier = orders.supplier;// 供应商名称

							var personal_info = orders.personResult;//个人信息
							var order_name = personal_info.name;// 参保人姓名
							var order_idcard = personal_info.idcard;// 参保人身份证号码

							//var order_hukou_type;// 户口类型
							//if (personal_info.hukou_type) {
							//	order_hukou_type = personal_info.hukou_type;
							//}
							//else {
							//	order_hukou_type = "-";
							//}
							//
							//var order_hukou_district;// 户籍地址
							//if (personal_info.hukou_district) {
							//	order_hukou_district = personal_info.hukou_district;
							//}
							//else {
							//	order_hukou_district = "-";
							//}

							order_list += "<tr class='item order_item' data-id='" + order_id + "' " +
								"data-pay_status='" + order_payed_status + "' " +
								//"data-reason='" + order_failed_reason + "' " +
								"data-source='" + order_source + "'>" +
								//"<td class='choose_item' onclick='order_manage.ChooseItem(this)'>" +
								//"<img src='img/icon_Unchecked.png'/>" +
								//"</td>" +
								"<td class='order_subject'>" + order_subject + "</td>" +
								"<td class='order_user_name'>" + order_name + "</td>" +
								"<td class='order_user_idCard'>" + order_idcard + "</td>" +
								"<td class='order_soin_district'>" + order_soin_district + "</td>" +
								"<td class='order_soin_type'>" + order_soin_type + "</td>" +
								"<td class='order_service_month'>" + order_service_month + "</td>" +
								"<td class='order_pay_month'>" + order_pay_month + "</td>" +
								"<td class='order_back_pay_month'>" + "补缴月份" + "</td>" +
								//"<td class='order_soin_code'>" + order_soin_code + "</td>" +
								//"<td class='order_soin_base'>" + order_soin_base + "</td>" +
								//"<td class='order_housefund_code'>" + order_housefund_code + "</td>" +
								//"<td class='order_housefund_base'>" + order_housefund_base + "</td>" +
								//"<td class='order_housefund_percent'>" + order_housefund_percent + "</td>" +
								//"<td class='order_hukou_type'>" + order_hukou_type + "</td>" +
								//"<td class='order_hukou_district'>" + order_hukou_district + "</td>" +
								//"<td class='order_collection_service_fee'>" + order_collection_service_fee + "</td>" +
								//"<td class='order_charge_service_fee'>" + order_charge_service_fee + "</td>" +
								//"<td class='order_corp_subtotal'>" + order_corp_subtotal + "</td>" +
								//"<td class='order_person_subtotal'>" + order_person_subtotal + "</td>" +
								//"<td class='order_other_payment' title='双击可更改'>" + order_other_payment + "</td>" +
								"<td class='order_collection_total'>" + order_collection_total + "</td>" +
								"<td class='order_charge_total'>" + order_charge_total + "</td>" +
								"<td class='order_salesman'>" + order_salesman + "</td>" +
								"<td class='order_supplier'>" + order_supplier + "</td>" +
								//"<td class='order_pay_status'>" +
								//	//"<span class='btn btn-sm btn-primary' " +
								//	//"onclick='order_manage.payFailOnly()'>缴纳失败" +
								//	//"<span>" +
								//"</td>" +
								//"<td class='order_operate'>" +
								//"<span class='btn btn-sm btn-danger' " +
								//"onclick='order_manage.orderDelByOnly(this)'>删除订单" +
								//"<span>" +
								//"</td>" +
								"</tr>";

						});
					}

					$table.find("tbody").html(order_list);

					in_or_decrease_manage.orderListInit();

				}
				else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			});

	},
	//订单列表 初始化
	orderListInit: function () {

		var $item = $(in_or_decrease_manage.containerName).find("tbody .item");
		var $pager_container = $(in_or_decrease_manage.containerName).find(".pager_container");

		//判断查询 结果为空
		if ($item.length == 0) {
			$pager_container.hide();
			return
		}

		$item.each(function () {
			var status = $(this).attr("data-pay_status");//'缴纳状态'，0是全部，1是成功，2是失败
			var reason = $(this).attr("data-reason");//
			var source = $(this).attr("data-source");// 订单来源 5 线下

			var pay_status_spn = "";//缴纳状态
			if (status == 1) {		//成功
				$(this).addClass("pay_success");

				pay_status_spn = "<span class='txt'>已缴纳成功</span>" +
					"<span class='btn btn-sm btn-primary' " +
					"onclick='in_or_decrease_manage.payFailOnly(this)'>缴纳失败</span>"
			}
			else if (status == 2) {		//失败
				$(this).addClass("pay_fail");

				pay_status_spn = "<span class='txt' data-fail_reason='" + reason + "' " +
					"onclick='in_or_decrease_manage.payFailMsgShow(this)'>已缴纳失败</span>" +
					"<span class='btn btn-sm btn-primary' " +
					"onclick='in_or_decrease_manage.paySuccessOnly(this)'>缴纳成功</span>"
			}
			else if (status == 3) {  //暂未缴纳 (显示 缴纳失败、缴纳成功)
				pay_status_spn = "<span class='btn btn-sm btn-primary' " +
					"onclick='in_or_decrease_manage.payFailOnly(this)'>缴纳失败</span>" +
					"<span class='btn btn-sm btn-primary' " +
					"onclick='in_or_decrease_manage.paySuccessOnly(this)'>缴纳成功</span>"
			}

			$(this).find(".order_pay_status").html(pay_status_spn);

			//点击编辑 其他费用
			$(this).find(".order_other_payment").dblclick(function () {
				//var $self = $(this);
				$(this).focus();
				var val = $(this).html();

				var input = $("<input>").val(val).blur(function () {
					var val = $.trim($(this).val());

					var id = $(this).closest(".item").attr("data-id");
					var ids = "[{'id':'" + id + "'}]";
					ids = eval("(" + ids + ")");

					var obj = new Object();
					obj.ids = ids;
					obj.other_payment = val;

					aryaPostRequest(
						urlGroup.order_manage_other_pay_update,
						obj,
						function (data) {
							//alert(JSON.stringify(data));

							if (data.code == 1000) {
								toastr.success("编辑成功！");
								in_or_decrease_manage.orderList();//重新获取当前页数据
							}
							else {
								messageCue(data.msg);
							}

						},
						function (error) {
							messageCue(error);
						});


				}).keyup(function () {
					this.value = this.value.replace(/\D/g, '')
				});

				$(this).html(input)

			});

		});

		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: in_or_decrease_manage.currentPage, //当前页数
			totalPages: in_or_decrease_manage.totalPage, //总页数
			numberOfPages: 5,//每页显示的 页数
			useBootstrapTooltip: true,//是否使用 bootstrap 自带的提示框
			itemContainerClass: function (type, page, currentpage) {  //每项的类名
				//alert(type + "  " + page + "  " + currentpage)
				var classname = "p_item ";

				switch (type) {
					case "first":
						classname += "p_first";
						break;
					case "last":
						classname += "p_last";
						break;
					case "prev":
						classname += "p_prev";
						break;
					case "next":
						classname += "p_next";
						break;
					case "page":
						classname += "p_page";
						break;
				}

				if (page == currentpage) {
					classname += " active "
				}

				return classname;
			},
			itemTexts: function (type, page, current) {  //
				switch (type) {
					case "first":
						return "首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "末页";
					case "page":
						return page;
				}
			},
			tooltipTitles: function (type, page, current) {
				switch (type) {
					case "first":
						return "去首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "去末页";
					case "page":
						return page === current ? "当前页数 " + page : "前往第 " + page + " 页"
				}
			},
			onPageClicked: function (event, originalEvent, type, page) { //点击事件
				//alert(page)

				var currentTarget = $(event.currentTarget);

				in_or_decrease_manage.currentPage = page;
				in_or_decrease_manage.orderList();//查询 满足条件的订单

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);
	},

	//选中单行
	ChooseItem: function (self) {
		if ($(self).closest(".item").hasClass("active")) { //如果选中行
			$(self).closest(".item").removeClass("active");
			$(self).find("img").attr("src", "img/icon_Unchecked.png")
		}
		else { //如果未选中
			$(self).closest(".item").addClass("active");
			$(self).find("img").attr("src", "img/icon_checked.png")
		}

		in_or_decrease_manage.IsChooseAll();//是否 已经全部选择
	},
	//选择全部
	ChooseAll: function () {
		var $choose_container = $(in_or_decrease_manage.containerName).find(".foot .choose_container");

		if ($choose_container.hasClass("active")) {   //已经选中
			$choose_container.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png")
			$(in_or_decrease_manage.containerName).find("table tbody .item").removeClass("active")
				.find("img").attr("src", "img/icon_Unchecked.png")
		}
		else {
			$choose_container.addClass("active").find("img").attr("src", "img/icon_checked.png")
			$(in_or_decrease_manage.containerName).find("table tbody .item").addClass("active")
				.find("img").attr("src", "img/icon_checked.png")
		}

		in_or_decrease_manage.btnListInit();//底部按钮初始化
	},
	//是否 已经全部选择
	IsChooseAll: function () {
		var chooseNo = 0;//选中的个数
		var $item = $(in_or_decrease_manage.containerName).find("tbody .item");
		for (var i = 0; i < $item.length; i++) {
			if ($item.eq(i).hasClass("active")) { //如果 是选中的
				chooseNo += 1;
			}
		}

		//没有全部选中
		if (chooseNo == 0 ||
			chooseNo < $(in_or_decrease_manage.containerName)
				.find("tbody .item").length) {
			$(in_or_decrease_manage.containerName).find(".choose_container").removeClass("active")
				.find("img").attr("src", "img/icon_Unchecked.png");
		}
		else {
			$(in_or_decrease_manage.containerName).find(".choose_container").addClass("active")
				.find("img").attr("src", "img/icon_checked.png");
		}

		in_or_decrease_manage.btnListInit();//底部按钮初始化
	},
	//底部按钮初始化 (底部按钮颜色变化)
	btnListInit: function () {
		var $foot = $(in_or_decrease_manage.containerName).find(".foot");
		//选中的item
		var $activity_item = $(in_or_decrease_manage.containerName).find("tbody .item.active");
		//选中的 缴纳失败的订单
		var $pay_fail_item = $(in_or_decrease_manage.containerName).find("tbody .item.active.pay_fail");
		//选中的 缴纳成功的订单
		var $pay_success_item = $(in_or_decrease_manage.containerName).find("tbody .item.active.pay_success");
		//当前查询条件 - 缴纳状态
		var pay_status = $(in_or_decrease_manage.containerName).find(".search_container")
			.find(".order_pay_status option:selected").val();

		//如果当前查询状态是 未操作状态
		if (pay_status == 3) {
			//未操作 状态下 ，可以 缴纳失败、缴纳成功 所有
			$foot.find(".btn_pay_fail_all").addClass("btn-primary").removeClass("btn-default");
			$foot.find(".btn_pay_success_all").addClass("btn-primary").removeClass("btn-default");

			//已经选中了 订单
			if ($activity_item.length > 0) {
				$foot.find(".btn_del").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_fail").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_success").addClass("btn-primary").removeClass("btn-default");
			}
			else {
				$foot.find(".btn_del").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_fail").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_success").addClass("btn-default").removeClass("btn-primary");
			}

		}

		//缴纳成功
		if (pay_status == 1) {
			$foot.find(".btn_pay_fail_all").addClass("btn-default").removeClass("btn-primary");
			$foot.find(".btn_pay_success_all").addClass("btn-default").removeClass("btn-primary");
			$foot.find(".btn_pay_success").addClass("btn-default").removeClass("btn-primary");

			//已经选中了 订单
			if ($activity_item.length > 0) {
				$foot.find(".btn_del").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_fail").addClass("btn-primary").removeClass("btn-default");
			}
			else {
				$foot.find(".btn_del").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_fail").addClass("btn-default").removeClass("btn-primary");
			}

		}

		//缴纳失败
		if (pay_status == 2) {
			$foot.find(".btn_pay_fail_all").addClass("btn-default").removeClass("btn-primary");
			$foot.find(".btn_pay_success_all").addClass("btn-default").removeClass("btn-primary");
			$foot.find(".btn_pay_fail").addClass("btn-default").removeClass("btn-primary");

			//已经选中了 订单
			if ($activity_item.length > 0) {
				$foot.find(".btn_del").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_success").addClass("btn-primary").removeClass("btn-default");
			}
			else {
				$foot.find(".btn_del").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_success").addClass("btn-default").removeClass("btn-primary");
			}

		}

		//全部 订单
		if (pay_status == "") {
			$foot.find(".btn_pay_fail_all").addClass("btn-default").removeClass("btn-primary");
			$foot.find(".btn_pay_success_all").addClass("btn-default").removeClass("btn-primary");

			//已经选中了 订单
			if ($activity_item.length > 0) {
				$foot.find(".btn_del").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_fail").addClass("btn-primary").removeClass("btn-default");
				$foot.find(".btn_pay_success").addClass("btn-primary").removeClass("btn-default");

				//如果 选择了 缴纳失败的 订单
				if ($pay_fail_item.length > 0) {
					$foot.find(".btn_pay_fail").addClass("btn-default").removeClass("btn-primary");
				}

				//如果 选择了 缴纳成功的 订单
				if ($pay_success_item.length > 0) {
					$foot.find(".btn_pay_success").addClass("btn-default").removeClass("btn-primary");
				}


			}
			else {
				$foot.find(".btn_del").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_success").addClass("btn-default").removeClass("btn-primary");
				$foot.find(".btn_pay_fail").addClass("btn-default").removeClass("btn-primary");
			}

		}

	},

	//缴纳失败 - 点击显示提示信息
	payFailMsgShow: function (self) {
		var msg = $(self).attr("data-fail_reason");
		msgShow(msg);
	},

	//缴纳失败 - 单个
	payFailOnly: function (self) {

		var id = $(self).closest(".item").attr("data-id");

		in_or_decrease_manage.PayFailArray = "{'id':'" + id + "'}";
		in_or_decrease_manage.PayFailArray = "[" + in_or_decrease_manage.PayFailArray + "]";
		in_or_decrease_manage.PayFailArray = eval("(" + in_or_decrease_manage.PayFailArray + ")");

		in_or_decrease_manage.payFailModalShow();//缴纳失败 - 弹框显示

	},
	//缴纳失败 - 多个
	payFailMore: function () {
		var $item = $(in_or_decrease_manage.containerName).find("tbody .item.active");
		if ($item.length <= 0) {
			toastr.info("请选择订单！");
			return;
		}

		if ($item.hasClass("pay_fail")) {
			toastr.info("选中的订单中有的已经是缴纳失败！");
			return;
		}

		$item.each(function () {
			var id = $(this).attr("data-id");

			in_or_decrease_manage.PayFailArray += in_or_decrease_manage.PayFailArray == "" ? ("{'id':'" + id + "'}")
				: (",{'id':'" + id + "'}")
		});

		in_or_decrease_manage.PayFailArray = "[" + in_or_decrease_manage.PayFailArray + "]";
		in_or_decrease_manage.PayFailArray = eval("(" + in_or_decrease_manage.PayFailArray + ")");

		in_or_decrease_manage.payFailModalShow();//缴纳失败 - 弹框显示
	},
	//缴纳失败 - 弹框显示
	payFailModalShow: function () {
		$soin_pay_fail_modal.modal({
			backdrop: false,
			keyboard: false
		});
	},
	//缴纳失败 - 确认
	payFailSure: function () {
		var reason = $.trim($soin_pay_fail_modal.find(".pay_fail_reason input").val());
		if (reason == "") {
			messageCue("请输入原因");
			return;
		}

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.ids = in_or_decrease_manage.PayFailArray;
		obj.reason = reason;

		aryaPostRequest(
			urlGroup.order_manage_order_pay_fail_url,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("缴纳失败操作成功！");
					$soin_pay_fail_modal.modal("hide");
					in_or_decrease_manage.PayFailArray = "";//初始化 赋值
					in_or_decrease_manage.btnSearchClick();//订单列表获取
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	},
	//缴纳失败 弹框显示- 所有用户(符合条件的)
	payFailAllModalShow: function () {

		var $foot = $(in_or_decrease_manage.containerName).find(".foot");

		//如果 不能 缴纳失败（所有用户）
		if ($foot.find(".btn_pay_fail_all").hasClass("btn-default")) {
			toastr.info("只有 查询条件中缴纳状态为 '未操作'状态，才能 缴纳失败(所有)");
			return
		}

		//如果 不能 缴纳失败（所有用户）
		if ($(in_or_decrease_manage.containerName).find("tbody .item").length <= 0) {
			toastr.info("暂时没有用户");
			return
		}


		swal(
			{
				title: "是否确定缴纳失败(所有用户)！",
				text: "",
				type: "warning",
				showCancelButton: true,
				cancelButtonText: "取消",
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {

				$soin_pay_fail_all_modal.modal("show");

			}
		);

	},
	//缴纳失败 确认 - 所有用户(符合条件的)
	payFailAllSure: function () {

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.customer_id = searchCondition_group.user_type_id;//客户类型id
		obj.district_id = searchCondition_group.soin_arya_id;//地区id
		obj.supplier_id = searchCondition_group.supplier_id;//供应商id
		obj.year_month = searchCondition_group.choose_month;
		obj.end_year_month = searchCondition_group.choose_end_month;

		obj.payed_status = searchCondition_group.pay_status_id;
		obj.reason = $.trim($soin_pay_fail_all_modal.find(".pay_fail_reason input").val());

		aryaPostRequest(
			urlGroup.order_manage_order_pay_fail_all_url,
			obj,
			function (data) {
				console.log("缴纳失败：");
				console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("所有员工 缴纳失败 操作成功！");
					$soin_pay_fail_all_modal.modal("hide");
					in_or_decrease_manage.btnSearchClick();

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

	//缴纳成功 - 单个
	paySuccessOnly: function (self) {

		var id = $(self).closest(".item").attr("data-id");

		in_or_decrease_manage.PaySuccessArray = "{'id':'" + id + "'}";
		in_or_decrease_manage.PaySuccessArray = "[" + in_or_decrease_manage.PaySuccessArray + "]";
		in_or_decrease_manage.PaySuccessArray = eval("(" + in_or_decrease_manage.PaySuccessArray + ")");

		in_or_decrease_manage.paySuccessSure();//缴纳成功 - 确认 弹框显示

	},
	//缴纳成功 - 多个
	paySuccessMore: function () {
		var $item = $(in_or_decrease_manage.containerName).find("tbody .item.active");
		if ($item.length <= 0) {
			toastr.info("请选择订单！");
			return;
		}

		if ($item.hasClass("pay_success")) {
			toastr.info("选中的订单中有的已经是缴纳成功！");
			return;
		}

		$item.each(function () {

			var id = $(this).attr("data-id");

			in_or_decrease_manage.PaySuccessArray += in_or_decrease_manage.PaySuccessArray == ""
				? ("{'id':'" + id + "'}")
				: (",{'id':'" + id + "'}")

		});

		in_or_decrease_manage.PaySuccessArray = "[" + in_or_decrease_manage.PaySuccessArray + "]";
		in_or_decrease_manage.PaySuccessArray = eval("(" + in_or_decrease_manage.PaySuccessArray + ")");

		in_or_decrease_manage.paySuccessSure();//缴纳成功 - 确认 弹框显示
	},
	//缴纳成功 - 确认
	paySuccessSure: function () {

		swal(
			{
				title: "是否确定缴纳成功！",
				text: "",
				type: "warning",
				showCancelButton: true,
				cancelButtonText: "取消",
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {

				loadingInit();//加载框 出现

				var obj = new Object();
				obj.ids = in_or_decrease_manage.PaySuccessArray;
				console.log(obj);
				console.log(JSON.stringify(obj));

				aryaPostRequest(
					urlGroup.order_manage_order_pay_success_url,
					obj,
					function (data) {
						//console.log("缴纳成功 操作成功：");
						//console.log(data);

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("缴纳成功 操作成功！");
							in_or_decrease_manage.PaySuccessArray = "";//初始化 赋值
							in_or_decrease_manage.btnSearchClick();//订单列表获取

						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						toastr.error(error);
					});

			}
		);

	},
	//缴纳成功 - 所有用户(符合条件的)
	paySuccessAll: function () {

		var $foot = $(in_or_decrease_manage.containerName).find(".foot");

		//如果 不能 缴纳成功（所有用户）
		if ($foot.find(".btn_pay_success_all").hasClass("btn-default")) {
			toastr.info("只有 查询条件中缴纳状态为 '未操作'状态，才能 缴纳成功(所有)");
			return
		}

		//如果 不能 缴纳成功（所有用户）
		if ($(in_or_decrease_manage.containerName).find("tbody .item").length <= 0) {
			toastr.info("暂时没有用户");
			return
		}

		swal(
			{
				title: "是否确定缴纳成功(所有用户)！",
				text: "",
				type: "warning",
				showCancelButton: true,
				cancelButtonText: "取消",
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {

				loadingInit();//加载框 出现

				var obj = new Object();
				obj.customer_id = searchCondition_group.user_type_id;//客户类型id
				obj.district_id = searchCondition_group.soin_arya_id;//地区id
				obj.supplier_id = searchCondition_group.supplier_id;//供应商id
				obj.year_month = searchCondition_group.choose_month;
				obj.end_year_month = searchCondition_group.choose_end_month;

				obj.payed_status = searchCondition_group.pay_status_id;

				aryaPostRequest(
					urlGroup.order_manage_order_pay_success_all_url,
					obj,
					function (data) {
						console.log("缴纳成功：");
						console.log(data);

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("所有员工 缴纳成功 操作成功！");
							in_or_decrease_manage.btnSearchClick();
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


	//订单删除 - 单项删除
	orderDelByOnly: function (self) {
		var id = $(self).closest(".item").attr("data-id");

		in_or_decrease_manage.DelArray = "{'id':'" + id + "'}";
		in_or_decrease_manage.DelArray = "[" + in_or_decrease_manage.DelArray + "]";
		in_or_decrease_manage.DelArray = eval("(" + in_or_decrease_manage.DelArray + ")");

		in_or_decrease_manage.orderDel();//订单删除 - 确认
	},
	//订单删除 - 多项删除
	orderDelByMore: function () {
		var $item = $(in_or_decrease_manage.containerName).find("tbody .item.active");
		if ($item.length <= 0) {
			messageCue("请选择订单！");
			return;
		}

		$item.each(function () {
			var id = $(this).attr("data-id");

			in_or_decrease_manage.DelArray += in_or_decrease_manage.DelArray == "" ? ("{'id':'" + id + "'}")
				: (",{'id':'" + id + "'}")
		});

		in_or_decrease_manage.DelArray = "[" + in_or_decrease_manage.DelArray + "]";
		in_or_decrease_manage.DelArray = eval("(" + in_or_decrease_manage.DelArray + ")");

		in_or_decrease_manage.orderDel();//订单删除 - 确认
	},
	//订单删除 - 确认
	orderDel: function () {
		delWarning("订单", function () {
			loadingInit();//加载框 出现

			var obj = new Object();
			obj.ids = in_or_decrease_manage.DelArray;

			aryaPostRequest(
				urlGroup.order_manage_del_url,
				obj,
				function (data) {
					//alert(JSON.stringify(data));

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除成功！");

						in_or_decrease_manage.btnSearchClick();//订单列表获取
					}
					else {
						messageCue(data.msg);
					}

				},
				function (error) {
					messageCue(error);
				});
		})
	},
};
