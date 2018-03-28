/**
 * Created by CuiMengxin on 2017/1/17.
 * 订单批量删除
 */


var order_batch_del = {
	containerName: "",
	currentPage: 1,//当前页面
	totalPage: "1",//总页面

	//初始化
	Init: function () {
		order_batch_del.containerName = ".order_batch_del_container";

		//显示 提示框
		$("[data-toggle='tooltip']").tooltip();

		//初始化 用户类型
		order_batch_del.initCorpList();

	},
	//检查数据是否 有更新
	initData: function () {
		//alert(11);
	},
	//初始化 用户类型
	initCorpList: function () {
		var $search_container = $(order_batch_del.containerName).find(".search_container");
		var $user_type_list = $search_container.find(".user_type_list");

		loadingInit();

		aryaGetRequest(
			urlGroup.order_manage_cus_list_get_url,
			function (data) {
				//alert(JSON.stringify(data));
				if (data.code == ERR_CODE_OK) {

					if (data.result && data.result.customer &&
						data.result.customer.length > 0) {

						var list = "";

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

						$user_type_list.siblings(".chosen-container")
							.addClass("form-control").css("padding", "0");

					}
					else {
						//list = "<option>暂无数据</option>";
						//$user_type_list.html(list);
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

	//订单查询 - 按钮点击
	btnSearchClick: function () {
		//赋值 查询参数
		order_batch_del.setQueryParam();
		if (!order_batch_del_param.user_type_id) {
			toastr.warning("请先选择用户类型！");
			return;
		}

		order_batch_del.currentPage = 1;
		order_batch_del.orderList();//订单查询 - 列表
	},
	//赋值 查询参数
	setQueryParam: function () {

		var $search_container = $(order_batch_del.containerName).find(".search_container");

		order_batch_del_param.user_type_id = $search_container.find(".user_type_list").val() ?
			$search_container.find(".user_type_list").val()[0] : "";
		order_batch_del_param.modify_id = $search_container
			.find(".increase_or_decrease option:selected").val();
		order_batch_del_param.key_word = $search_container.find(".key_word").val();

	},
	//订单查询 - 列表
	orderList: function () {
		//var $search_container = $(order_batch_del.containerName).find(".search_container");
		var $table = $(order_batch_del.containerName).find(".table_container table");

		loadingInit();//加载框 出现

		var obj = {};
		obj.id = order_batch_del_param.user_type_id;
		obj.keyword = order_batch_del_param.key_word;
		obj.page = order_batch_del.currentPage;
		obj.page_size = "10";
		var url = urlGroup.order_batch_del_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					order_batch_del.totalPage = data.result["pages"] ? data.result["pages"] : 1;

					var order_list = "";
					var orders = data.result.orders;//
					if (!orders || orders.length == 0) {
						order_list = "<tr>" +
							"<td colspan='13'>暂无订单信息</td>" +
							"</tr>";
					}
					else {
						$.each(orders, function (index, orders) {

							var version = orders.version;//
							var order_id = orders.id;
							var order_subject = orders.subject ? orders.subject : "";// 缴纳主体
							var personal_info = orders.personResult;//个人信息
							var name = "";// 参保人姓名
							if (personal_info && personal_info.name) {
								name = personal_info.name;
							}
							var idCard = "";//参保人身份证号码
							if (personal_info && personal_info.idcard) {
								idCard = personal_info.idcard;
							}
							var order_soin_district = orders.soin_district ? orders.soin_district : "";// 参保地区
							var order_soin_type = orders.soin_type ? orders.soin_type : "";// 参保类型
							var order_service_month = orders.service_month ? orders.service_month : "";// 服务月份
							var order_pay_month = orders.pay_month ? orders.pay_month : "";// 缴纳月份
							var order_collection_total = orders.collection_total ? orders.collection_total : "";// 收账总计
							var order_charge_total = orders.charge_total ? orders.charge_total : "";// 出账总计
							var salesman = orders.salesman ? orders.salesman : "";// 业务员名称
							var supplier = orders.supplier ? orders.supplier : "";// 供应商
							var modify = orders.modify ? orders.modify : "";// 增减员


							order_list += "<tr class='item order_item' data-id='" + order_id + "' " +
								"data-version='" + version + "'>" +
								"<td class='choose_item' onclick='order_batch_del.chooseItem(this)'>" +
								"<img src='img/icon_Unchecked.png'>" +
								"</td>" +
								"<td class='order_subject'>" + order_subject + "</td>" +
								"<td class='user_name'>" + name + "</td>" +
								"<td class='user_idCard'>" + idCard + "</td>" +
								"<td class='order_soin_district'>" + order_soin_district + "</td>" +
								"<td class='order_soin_type'>" + order_soin_type + "</td>" +
								"<td class='order_service_month'>" + order_service_month + "</td>" +
								"<td class='order_pay_month'>" + order_pay_month + "</td>" +
								"<td class='order_collection_total'>" + order_collection_total + "</td>" +
								"<td class='order_charge_total'>" + order_charge_total + "</td>" +
								"<td class='salesman'>" + salesman + "</td>" +
								"<td class='supplier'>" + supplier + "</td>" +
								"<td class='modify'>" + modify + "</td>" +
								"</tr>";
						});
					}

					$table.find("tbody").html(order_list);

					order_batch_del.orderListInit();

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			}
		);

		//order_batch_del.orderListInit();
	},
	//订单列表 初始化
	orderListInit: function () {

		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $table = $table_container.find("table");
		var $pager_container = $(order_batch_del.containerName).find(".pager_container");

		//判断查询 结果为空
		if ($table.find("tbody .item").length == 0) {
			$pager_container.hide();
		}
		else {

			var options = {
				bootstrapMajorVersion: 3, //版本  3是ul  2 是div
				//containerClass:"sdfsaf",
				//size: "small",//大小
				alignment: "right",//对齐方式
				currentPage: order_batch_del.currentPage, //当前页数
				totalPages: order_batch_del.totalPage, //总页数
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

					order_batch_del.currentPage = page;
					order_batch_del.orderList();//查询 满足条件的订单

				}
			};

			var ul = '<ul class="pagenation" style="float:right;"></ul>';
			$pager_container.show();
			$pager_container.html(ul);
			$pager_container.find(".pagenation").bootstrapPaginator(options);

		}

		order_batch_del.clearChoose();//清除选中状态
		order_batch_del.initOperateBtn();//初始化 操作按钮

	},

	//清除选中状态
	clearChoose: function () {
		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $choose_item = $table_container.find(".choose_item");//table choose_item
		var $choose_all = $(order_batch_del.containerName).find(".foot .choose_item");//

		if ($choose_item.hasClass("active")) {
			$choose_item.removeClass("active");
		}

		if ($choose_all.hasClass("active")) {
			$choose_all.removeClass("active");
		}

		$choose_item.find("img").attr("src", "img/icon_Unchecked.png");
		$choose_all.find("img").attr("src", "img/icon_Unchecked.png");

	},
	//选中单行
	chooseItem: function (self) {
		var $item = $(self).closest(".item");

		if ($item.hasClass("active")) { //如果选中行
			$item.removeClass("active");
			$(self).find("img").attr("src", "img/icon_Unchecked.png");
		}
		else { //如果未选中
			$item.addClass("active");
			$(self).find("img").attr("src", "img/icon_checked.png");
		}

		order_batch_del.isChooseCur();//是否 已经全部选择(当前页)
	},
	//选择当前页 全部
	chooseCurAll: function () {
		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $cur = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		var $choose_item = $table_container.find(".choose_item");//table choose_item
		var $choose_all = $(order_batch_del.containerName).find(".foot .choose_item");//

		if ($cur.hasClass("active")) { //如果选中
			$cur.removeClass("active");//
			$item.removeClass("active");//tbody item移除active
			$choose_item.find("img").attr("src", "img/icon_Unchecked.png");
		}
		else { //如果未选中
			$cur.addClass("active");
			$item.addClass("active");//tbody item加上active
			$choose_item.find("img").attr("src", "img/icon_checked.png");
		}

		//移除 选择全部的选中状态
		$choose_all.removeClass("active");
		$choose_all.find("img").attr("src", "img/icon_Unchecked.png");

		order_batch_del.initOperateBtn();//初始化 操作按钮

	},
	//选择全部(查询条件下)
	chooseAll: function () {
		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $thead_choose_item = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		var $tbody_choose_item = $table_container.find("tbody .choose_item");//tbody choose_item
		var $foot_choose_item = $(order_batch_del.containerName).find(".foot .choose_item");

		//移除 选择当前页的选中状态
		$thead_choose_item.removeClass("active");
		$thead_choose_item.find("img").attr("src", "img/icon_Unchecked.png");

		if ($foot_choose_item.hasClass("active")) {   //已经选中
			$foot_choose_item.removeClass("active");
			$foot_choose_item.find("img").attr("src", "img/icon_Unchecked.png");
			$item.removeClass("active");
			$tbody_choose_item.find("img").attr("src", "img/icon_Unchecked.png")
		}
		else {
			$foot_choose_item.addClass("active");
			$foot_choose_item.find("img").attr("src", "img/icon_checked.png");
			$item.addClass("active");
			$tbody_choose_item.find("img").attr("src", "img/icon_checked.png")
		}

		order_batch_del.initOperateBtn();//初始化 操作按钮

	},
	//是否 已经全部选择(当前页)
	isChooseCur: function () {
		var $table_container = $(order_manage.containerName).find(".table_container");
		var $cur = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		var $choose_all = $(order_batch_del.containerName).find(".foot .choose_item");//foot choose_item

		var chooseNo = 0;//选中的个数
		for (var i = 0; i < $item.length; i++) {
			if ($item.eq(i).hasClass("active")) { //如果 是选中的
				chooseNo += 1;
			}
		}

		//没有全部选中 当前页item
		if (chooseNo == 0 || chooseNo < $item.length) {
			$cur.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png");
		}
		else {
			$cur.addClass("active").find("img").attr("src", "img/icon_checked.png");
		}

		//移除 选择全部的选中状态
		$choose_all.removeClass("active");
		$choose_all.find("img").attr("src", "img/icon_Unchecked.png");

		order_batch_del.initOperateBtn();//初始化 操作按钮
	},

	//初始化 操作按钮
	initOperateBtn: function () {
		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $item_active = $table_container.find("tbody .item.active");//tbody item
		var $foot = $(order_batch_del.containerName).find(".foot");
		var $btn_del = $foot.find(".btn_del");//删除 按钮
		var $btn_recover = $foot.find(".btn_recover");//恢复 按钮

		//增员
		if (order_batch_del_param.modify_id == 1) {
			$btn_recover.hide();
			$btn_del.show();
		}
		else if (order_batch_del_param.modify_id == 2) {
			$btn_recover.show();
			$btn_del.hide();
		}

		//如果有 订单
		if ($item_active.length > 0) {
			$btn_del.addClass("btn-danger").removeClass("btn-default");
			$btn_recover.addClass("btn-primary").removeClass("btn-default");
		}
		else {
			$btn_del.addClass("btn-default").removeClass("btn-danger");
			$btn_recover.addClass("btn-default").removeClass("btn-primary");
		}


	},

	//订单批次删除
	orderBatchDel: function () {
		//var $search_container = $(order_batch_del.containerName).find(".search_container");
		var $table_container = $(order_batch_del.containerName).find(".table_container");
		var $item_active = $table_container.find("tbody .item.active");//tbody .item.active
		var $cur = $table_container.find("thead .choose_item");//thead choose_item
		var $choose_all = $(order_batch_del.containerName).find(".foot .choose_item");//

		//暂无数据
		if ($item_active.length <= 0) {
			toastr.warning("您没有选择数据，请先选择数据！");
			return
		}

		//选择 查询条件下 全部
		if ($choose_all.hasClass("active")) {

			delWarning("确定要删除所有订单吗？", function () {
				loadingInit();//加载框 出现

				var obj = {};
				obj.id = order_batch_del_param.user_type_id;
				obj.keyword = order_batch_del_param.key_word;

				aryaPostRequest(
					urlGroup.order_batch_del_by_search,
					obj,
					function (data) {

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("删除成功！");

							order_batch_del.btnSearchClick();//查询

						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});

			});

		}
		else {
			delWarning("确定要删除选中的订单吗？", function () {
				loadingInit();//加载框 出现

				var arr = [];
				for (var i = 0; i < $item_active.length; i++) {
					var id = $item_active.eq(i).attr("data-id");
					arr.push(id);
				}

				var obj = {};
				obj.ids = arr;

				aryaPostRequest(
					urlGroup.order_batch_del_by_ids,
					obj,
					function (data) {

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("删除成功！");

							order_batch_del.orderList();//查询

						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});

			});
		}

	}


};

var order_batch_del_param = {
	user_type_id: "",//用户类型id
	modify_id: "",//增减员id
	key_word: "",//关键字
};

$(function () {
	order_batch_del.Init();
});
