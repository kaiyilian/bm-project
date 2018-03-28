/**
 * Created by CuiMengxin on 2017/1/5.
 * 订单批量顺延
 */

var order_batch_extend = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面

	init: function () {
		order_batch_extend.containerName = ".order_batch_extend_container";

		//初始化 客户 列表
		order_batch_extend.initUserList();

		//订单查询 - 按钮点击
		//order_batch_extend.btnSearchClick();
	},
	//初始化 客户 列表
	initUserList: function () {
		var $search_container = $(order_batch_extend.containerName).find(".search_container");
		var $user_list = $search_container.find(".user_list");

		loadingInit();

		aryaGetRequest(
			urlGroup.order_batch_extend_custom_list,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					var list = "";
					var arr = data.result.customer;
					if (!arr || arr.length == 0) {
					}
					else {
						for (var i = 0; i < arr.length; i++) {
							var $item = arr[i];

							var id = $item.id;
							var name = $item.name;//

							list += "<option value='" + id + "'>" + name + "</option>"

						}
					}

					$user_list.html(list);

					$user_list.chosen({
						allow_single_deselect: true,//选择之后 是否可以取消
						max_selected_options: 1,//最多只能选择1个
						width: "100%",//select框 宽度
						no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
					});

					$user_list.siblings(".chosen-container").addClass("form-control")
						.css("padding", "0");

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

	//订单查询 - 按钮点击
	btnSearchClick: function () {
		order_batch_extend.currentPage = 1;
		order_batch_extend.orderList();//订单查询 - 列表
	},
	//订单查询 - 列表
	orderList: function () {
		var $search_container = $(order_batch_extend.containerName).find(".search_container");
		var $table_container = $(order_batch_extend.containerName).find(".table_container");
		var $table = $table_container.find("table");

		var obj = {};
		obj.id = $search_container.find(".user_list").val() ?
			$search_container.find(".user_list").val()[0] : "";
		obj.page = order_batch_extend.currentPage;
		obj.page_size = "10";
		var url = urlGroup.order_batch_extend_list + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					order_batch_extend.totalPage = data.result["pages"];

					var list = "";
					var orders = data.result.orders;//
					if (!orders || orders.length == 0) {
						list = "<tr>" +
							"<td colspan='13'>暂无订单</td>" +
							"</tr>";
					}
					else {
						$.each(orders, function (index, orders) {

							var version = orders.version;//
							var order_id = orders.id;
							var postpone_due = orders.postpone_due ? orders.postpone_due : 0;//顺延是否到期
							var order_subject = orders.subject ? orders.subject : "";// 缴纳主体
							var order_name = orders.name ? orders.name : "";// 参保人姓名
							var order_idcard = orders.idcard ? orders.idcard : "";// 参保人身份证号码

							var order_soin_district = orders.soin_district ? orders.soin_district : "";// 参保地区
							var order_soin_type = orders.soin_type ? orders.soin_type : "";// 参保类型
							var order_service_month = orders.service_month ? orders.service_month : "";// 服务月份

							// 缴纳月份
							var order_pay_month_content = "";
							var order_pay_month_status = "";//缴纳月份 状态 0代表正常，1代表错误
							if (orders.pay_month) {
								var status = orders.pay_month.status ? orders.pay_month.status : "0";
								var content = orders.pay_month.content ? orders.pay_month.content : "";

								order_pay_month_status = status;

								if (order_pay_month_status == 0) {
									order_pay_month_content = "<div>" + content + "</div>";
								}
								else {
									order_pay_month_content = "<div class='is_red'>" + content + "</div>";
								}
							}

							// 补缴月份
							var order_back_month_content = "无";
							if (orders.back_month && orders.back_month.length > 0) {
								order_back_month_content = "";

								for (var i = 0; i < orders.back_month.length; i++) {
									var item = orders.back_month[i];

									//0代表正常，1代表错误
									var status = item.status ? item.status : "0";
									var content = item.content ? item.content : "";

									if (status == 0) {
										order_back_month_content += "<div>" + content + "</div>";
									}
									else {
										order_back_month_content += "<div class='is_red'>" + content + "</div>";
									}
								}

							}

							var order_collection_total = orders.collection_subtotal ? orders.collection_subtotal : "";// 收账总计
							var order_charge_total = orders.charge_subtotal ? orders.charge_subtotal : "";// 出账总计

							var order_salesman = orders.salesman ? orders.salesman : "";// 业务员名称
							var order_supplier = orders.supplier ? orders.supplier : "";// 供应商名称
							var increase_or_decrease = orders.modify ? orders.modify : "";// 增减员


							list +=
								"<tr class='item order_item' " +
								"data-id='" + order_id + "' " +
								"data-version='" + version + "' " +
								"data-postpone_due='" + postpone_due + "' " +
								">" +
								"<td class='order_subject'>" + order_subject + "</td>" +
								"<td class='order_user_name'>" + order_name + "</td>" +
								"<td class='order_user_idCard'>" + order_idcard + "</td>" +
								"<td class='order_soin_district'>" + order_soin_district + "</td>" +
								"<td class='order_soin_type'>" + order_soin_type + "</td>" +
								"<td class='order_service_month'>" + order_service_month + "</td>" +
								"<td class='order_pay_month' data-status='" + order_pay_month_status + "'>" +
								order_pay_month_content + "</td>" +
								"<td class='order_back_month'>" +
								order_back_month_content + "</td>" +
								"<td class='order_collection_total'>" + order_collection_total + "</td>" +
								"<td class='order_charge_total'>" + order_charge_total + "</td>" +
								"<td class='order_salesman'>" + order_salesman + "</td>" +
								"<td class='order_supplier'>" + order_supplier + "</td>" +
								"<td class='increase_or_decrease'>" + increase_or_decrease + "</td>" +
								"</tr>";
						});
					}

					$table.find("tbody").html(list);


					order_batch_extend.orderListInit();

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
	//订单列表 初始化
	orderListInit: function () {
		var $item = $(order_batch_extend.containerName).find("tbody .item");
		var $pager_container = $(order_batch_extend.containerName).find(".pager_container");

		//判断查询 结果为空
		if ($item.length == 0) {
			$pager_container.hide();
			return
		}

		$item.each(function () {

			var postpone_due = $(this).attr("data-postpone_due");

			if (postpone_due == 0) {
				$(this).removeClass("is_expire");
			}
			//1 顺延到期
			if (postpone_due == 1) {
				$(this).addClass("is_expire");
			}

		});
		var $item_is_expire = $(order_batch_extend.containerName).find("tbody .item.is_expire");


		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: order_batch_extend.currentPage, //当前页数
			totalPages: order_batch_extend.totalPage, //总页数
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

				order_batch_extend.currentPage = page;
				order_batch_extend.orderList();//查询 满足条件的订单

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);

		//如果有过期的数据
		if ($item_is_expire.length > 0) {
			order_batch_extend.expireMsg();
		}

	},

	//到期提示
	expireMsg: function () {
		msgShow("有客户顺延到期，请减员！")
	},

	//订单 批量顺延
	orderBatchExtend: function () {
		var $search_container = $(order_batch_extend.containerName).find(".search_container");
		//var $table_container = $(order_batch_extend.containerName).find(".table_container");
		//var $item = $table_container.find("tbody .item");

		var id = $search_container.find(".user_list").val() ?
			$search_container.find(".user_list").val()[0] : "";

		if (!id) {
			toastr.warning("暂无订单可批量顺延！");
			return;
		}

		var $item = $(order_batch_extend.containerName).find("tbody .item.is_expire");
		if ($item.length > 0) {
			toastr.warning("有客户顺延到期了，请先减员！");
			return;
		}

		operateShow("是否确认批量顺延？", function () {

			loadingInit();

			var obj = {};
			obj.id = id;

			aryaPostRequest(
				urlGroup.order_batch_extend,
				obj,
				function (data) {
					//console.log("获取日志：");
					//console.log(data);

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("批量顺延成功！");
						order_batch_extend.orderList();
					}
					else {
						messageCue(data.msg);
					}

				},
				function (error) {
					messageCue(error);
				}
			);

		});

	}

};

$(function () {
	order_batch_extend.init();
});
