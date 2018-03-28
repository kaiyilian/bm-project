/**
 * Created by CuiMengxin on 2016/8/2.
 * 订单批次管理
 */

var order_batch_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面
	DelArray: "",//订单删除 -  数组

	//初始化
	Init: function () {
		order_batch_manage.containerName = ".order_batch_manage_container";

		order_batch_manage.initSalesmanList();//初始化 业务员列表

		//order_batch_manage.orderListGet();

		//业务员 列表 change
		$(order_batch_manage.containerName).find(".salesman_list").change(function () {
			$(order_batch_manage.containerName).find("tbody").html("");

			order_batch_manage.initBatchList();//初始化 批次列表
			order_batch_manage.initOrderDelBtn();//检查订单是否 可以删除(初始化 删除按钮)
		});

		//批次 列表 change
		$(order_batch_manage.containerName).find(".batch_list").change(function () {
			$(order_batch_manage.containerName).find("tbody").html("");

			order_batch_manage.initOrderDelBtn();//检查订单是否 可以删除(初始化 删除按钮)
		});
	},
	//检查数据是否 有更新
	initData: function () {
		//alert(11);
	},
	//初始化 业务员列表
	initSalesmanList: function () {
		var $search_container = $(order_batch_manage.containerName).find(".search_container");

		aryaGetRequest(
			urlGroup.order_batch_manage_salesman_list_get_url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					var salesman_list = "";
					var salesman = data.result.salesman;
					if (salesman == null || salesman.length == 0) {
						messageCue("暂无业务员！");
						return;
					}
					else {
						for (var i = 0; i < salesman.length; i++) {
							var item = salesman[i];

							var id = item.id;//
							var name = item.name;//

							salesman_list += "<option value='" + id + "'>" + name + "</option>";

						}
					}

					$search_container.find(".salesman_list").html(salesman_list);

					order_batch_manage.initBatchList();//初始化 批次列表
				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});
	},
	//初始化 批次列表
	initBatchList: function () {
		var $search_container = $(order_batch_manage.containerName).find(".search_container");

		var obj = new Object();
		obj.salesman_id = $search_container.find(".salesman_list").find("option:selected").val();
		var url = urlGroup.order_batch_manage_batch_list_get_url + "?" + jsonParseParam(obj);

		aryaGetRequest(url, function (data) {
			//alert(JSON.stringify(data));

			if (data.code == RESPONSE_OK_CODE) {

				var batch_list = "";
				var batch = data.result.batchs;
				if (batch == null || batch.length == 0) {
					messageCue("暂无批次列表！");
				}
				else {
					for (var i = 0; i < batch.length; i++) {
						var item = batch[i];

						var id = item.id;//
						var name = item.name;//

						batch_list += "<option value='" + id + "'>" + name + "</option>";

					}
				}

				$search_container.find(".batch_list").html(batch_list);

			}
			else {
				messageCue(data.msg);
			}
		}, function (error) {
			messageCue(error);
		});
	},

	//订单查询 - 按钮点击
	btnSearchClick: function () {
		if (!order_batch_manage.CheckParamSearch()) {
			return;
		}

		order_batch_manage.currentPage = 1;
		order_batch_manage.orderListGet();//订单查询 - 列表
	},
	//订单查询 - 检查参数
	CheckParamSearch: function () {
		var flag = false;
		var txt = "";

		var $search_container = $(order_batch_manage.containerName).find(".search_container");

		var salesman_id = $search_container.find(".salesman_list option:selected").val();
		var batch_id = $search_container.find(".batch_list option:selected").val();

		if (salesman_id == undefined) {
			txt = "暂无业务员列表，无法查询！";
		}
		else if (salesman_id == "") {
			txt = "请选择业务员！";
		}
		else if (batch_id == undefined) {
			txt = "暂无批次列表，无法查询！";
		}
		else if (batch_id == "") {
			txt = "请选择批次！";
		}
		else {
			flag = true;
		}

		if (txt != "") {
			messageCue(txt);
		}

		return flag;
	},
	//订单查询 - 列表
	orderListGet: function () {
		var $search_container = $(order_batch_manage.containerName).find(".search_container");
		//var salesman_id = $search_container.find(".salesman_list option:selected").val();
		var batch_id = $search_container.find(".batch_list option:selected").val();
		if (!batch_id) {
			return;
		}

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.batch_id = batch_id;
		obj.page = order_batch_manage.currentPage;
		obj.page_size = "10";
		var url = urlGroup.order_batch_manage_order_list_get_url + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					order_batch_manage.totalPage = data.result["pages"];

					var order_list = "";
					var orders = data.result.orders;//
					if (orders == null || orders.length == 0) {
						order_list = "<tr>" +
							"<td colspan='10'>暂无批次信息</td>" +
							"</tr>";
					}
					else {
						$.each(orders, function (index, orders) {

							var version = orders.version;//
							var order_id = orders.id;
							var order_subject = orders.subject;// 缴纳主体

							var order_soin_district = orders.soin_district;// 参保地区
							var order_soin_type = orders.soin_type;// 参保类型
							var order_service_month = orders.service_month;// 服务月份
							var order_pay_month = orders.pay_month;// 缴纳月份

							var order_salesman = orders.salesman;// 业务员名称

							var order_collection_total = orders.collection_total;// 收账总计
							var order_charge_total = orders.charge_total;// 出账总计

							var personal_info = orders.personResult;//个人信息
							var order_name = personal_info.name;// 参保人姓名
							var order_idcard = personal_info.idcard;// 参保人身份证号码

							order_list += "<tr class='item order_item' data-id='" + order_id + "' " +
								"data-version='" + version + "'>" +
								"<td class='order_subject'>" + order_subject + "</td>" +
								"<td class='order_user_name'>" + order_name + "</td>" +
								"<td class='order_user_idCard'>" + order_idcard + "</td>" +
								"<td class='order_soin_district'>" + order_soin_district + "</td>" +
								"<td class='order_soin_type'>" + order_soin_type + "</td>" +
								"<td class='order_service_month'>" + order_service_month + "</td>" +
								"<td class='order_pay_month'>" + order_pay_month + "</td>" +
								"<td class='order_collection_total'>" + order_collection_total + "</td>" +
								"<td class='order_charge_total'>" + order_charge_total + "</td>" +
								"<td class='order_salesman'>" + order_salesman + "</td>" +
								"</tr>";
						});
					}

					$(order_batch_manage.containerName).find(".table_container tbody").html(order_list);

					order_batch_manage.orderListInit();

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});


		order_batch_manage.orderListInit();
	},
	//订单列表 初始化
	orderListInit: function () {
		order_batch_manage.initOrderDelBtn();//检查订单是否 可以删除(初始化 删除按钮)

		var $pager_container = $(order_batch_manage.containerName).find(".pager_container");
		//判断查询 结果为空
		if ($(order_batch_manage.containerName).find("tbody .item").length == 0) {
			$pager_container.hide();
			return
		}
		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: order_batch_manage.currentPage, //当前页数
			totalPages: order_batch_manage.totalPage, //总页数
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

				order_batch_manage.currentPage = page;
				order_batch_manage.orderListGet();//查询 满足条件的订单

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);
	},

	//检查订单是否 可以删除(初始化 删除按钮)
	initOrderDelBtn: function () {
		var $foot = $(order_batch_manage.containerName).find(".foot");

		//如果有 订单
		if ($(order_batch_manage.containerName).find("tbody .item").length > 0) {
			$foot.find(".btn_del").addClass("btn-danger").removeClass("btn-default");
			$foot.find(".btn_del").attr("onclick", "order_batch_manage.orderBatchDel()");
		}
		else {
			$foot.find(".btn_del").addClass("btn-default").removeClass("btn-danger");
			$foot.find(".btn_del").removeAttr("onclick");
		}
	},
	//订单批次删除
	orderBatchDel: function () {
		var $search_container = $(order_batch_manage.containerName).find(".search_container");
		var salesman_name = $search_container.find(".salesman_list option:selected").text();
		var batch_name = $search_container.find(".batch_list option:selected").text();
		var name = "业务员：" + salesman_name + "\n" + "批次号：" + batch_name + "\n的订单";

		delWarning(name, function () {
			loadingInit();//加载框 出现

			var obj = new Object();
			obj.batch_id = $search_container.find(".batch_list option:selected").val();
			//var url = urlGroup.order_batch_manage_del_url + "?" + jsonParseParam(obj);

			aryaPostRequest(
				urlGroup.order_batch_manage_del_url,
				obj,
				function (data) {

					if (data.code == RESPONSE_OK_CODE) {
						messageCue("删除成功！");
						order_batch_manage.initBatchList();//初始化批次列表

						$(order_batch_manage.containerName).find("tbody").html("");
						order_batch_manage.initOrderDelBtn();//检查订单是否 可以删除(初始化 删除按钮)
					}
					else {
						messageCue(data.msg);
					}

				},
				function (error) {
					messageCue(error);
				});

		})
	}


};

$(function () {
	order_batch_manage.Init();
});