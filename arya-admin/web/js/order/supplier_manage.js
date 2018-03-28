/**
 * Created by CuiMengxin on 2016/8/2.
 * 供应商 管理
 */

var supplier_manage = {
	manage_containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面
	DelArray: "",//订单删除 -  数组

	//初始化
	Init: function () {
		supplier_manage.manage_containerName = ".supplier_manage .supplier_manage_container";
		supplier_manage.DelArray = "";

		supplier_manage.supplierListGet();//供应商查询 - 列表

		//新增供应商 - 弹框显示
		$(".supplier_add_modal").on("shown.bs.modal", function () {
			var $row = $(".supplier_add_modal").find(".modal-body .row");

			$row.find(".supplier_name input").val("");
			$row.find(".manage_cost input").val("");
		});

		//编辑供应商 - 弹框显示
		$(".supplier_modify_modal").on("shown.bs.modal", function () {
			var $row = $(".supplier_modify_modal").find(".modal-body .row");
			var $item = $(supplier_manage.manage_containerName).find(".item.modify_item");

			var supplier_name = $item.find(".supplier_name").html();
			var manage_cost = $item.find(".manage_cost").attr("data-cost");

			$row.find(".supplier_name input").val(supplier_name);
			$row.find(".manage_cost input").val(manage_cost);
		});

	},

	//供应商查询 - 列表
	supplierListGet: function () {
		loadingInit();//加载框 出现

		var obj = new Object();
		obj.page = supplier_manage.currentPage;
		obj.page_size = "10";
		var url = urlGroup.supplier_manage_list_get_url + "?" + jsonParseParam(obj);
		//alert(url);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					supplier_manage.totalPage = data.result.pages;//总页数

					var supplier_list = "";//
					var suppliers = data.result.suppliers;
					if (suppliers == null || suppliers.length == 0) {
					}
					else {
						for (var i = 0; i < suppliers.length; i++) {
							var item = suppliers[i];

							var id = item.id;//供应商id
							var name = item.name;//供应商名称
							var fee = item.fee;//管理费
							var district_count = item.district_count;//服务地区数
							var districts = item.districts;//服务地区
							var district_list = "";
							if (district_count > 0) {
								for (var j = 0; j < district_count; j++) {
									var district_item = districts[j];

									var district_id = district_item.id;//
									var district_name = district_item.name;//

									district_list += "<div class='item' data-id='" + district_id + "'>" +
										district_name + "</div>"
								}
							}
							sessionStorage.setItem("district_list_" + id, district_list);//

							supplier_list +=
								"<tr class='item supplier_item' data-id='" + id + "'>" +
								"<td class='choose_item' onclick='supplier_manage.ChooseItem(this)'>" +
								"<img src='img/icon_Unchecked.png'/>" +
								"</td>" +
								"<td class='supplier_name'>" + name + "</td>" +
								"<td class='manage_cost' data-cost='" + fee + "'>￥" + fee + "</td>" +
								"<td class='supplier_city'>" +
								"<div class='city_list' data-count='" + district_count + "'>" +
								district_count +
								"</div>" +
								"</td>" +
								"<td class='supplier_operate'>" +
								"<span class='btn btn-sm btn-primary btn_modify'>编辑</span>" +
								"<span class='btn btn-sm btn-danger btn_del'>删除</span>" +
								"</td>" +
								"</tr>";
						}
					}

					$(supplier_manage.manage_containerName).find(".table_container")
						.find("table tbody").html(supplier_list);
					supplier_manage.supplierListInit();//供应商列表 初始化
				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//供应商列表 初始化
	supplierListInit: function () {
		var $item = $(supplier_manage.manage_containerName).find("tbody .item");
		var $pager_container = $(supplier_manage.manage_containerName).find(".pager_container");

		//判断查询 结果为空
		if ($item.length == 0) {
			$pager_container.hide();
		}
		else {
			$item.each(function () {

				//开通城市列表
				var $city = $(this).find(".supplier_city .city_list");
				var count = $city.attr("data-count");
				if (count > 0) {
					$city.addClass("already_open");
					$city.click(function () {
						supplier_manage.supplierBindCityModalShow(this);
					});
				}
				else {
					$city.addClass("not_open");
				}

				//编辑
				$(this).find(".supplier_operate .btn_modify").click(function () {
					supplier_manage.supplierModifyModalShow(this);
				});

				//删除
				$(this).find(".supplier_operate .btn_del").click(function () {
					supplier_manage.supplierDelByOnly(this);
				});

			});

			var options = {
				bootstrapMajorVersion: 3, //版本  3是ul  2 是div
				//containerClass:"sdfsaf",
				//size: "small",//大小
				alignment: "right",//对齐方式
				currentPage: supplier_manage.currentPage, //当前页数
				totalPages: supplier_manage.totalPage, //总页数
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

					supplier_manage.currentPage = page;
					supplier_manage.supplierListGet();//查询 满足条件的订单

				}
			};

			var ul = '<ul class="pagenation" style="float:right;"></ul>';
			$pager_container.show();
			$pager_container.html(ul);
			$pager_container.find(".pagenation").bootstrapPaginator(options);
		}
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

		supplier_manage.IsChooseAll();//是否 已经全部选择
	},
	//选择全部
	ChooseAll: function () {
		var $choose_container = $(supplier_manage.manage_containerName).find(".foot .choose_container");
		var $tbody_item = $(supplier_manage.manage_containerName).find("table tbody .item");

		if ($choose_container.hasClass("active")) {   //已经选中
			$choose_container.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png");
			$tbody_item.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png")
		}
		else {
			$choose_container.addClass("active").find("img").attr("src", "img/icon_checked.png");
			$tbody_item.addClass("active").find("img").attr("src", "img/icon_checked.png")
		}

		supplier_manage.CheckEmployeeIsChoose();//检查是否有选中的 员工
	},
	//是否 已经全部选择
	IsChooseAll: function () {
		var chooseNo = 0;//选中的个数
		var $item = $(supplier_manage.manage_containerName).find("tbody .item");
		for (var i = 0; i < $item.length; i++) {
			if ($item.eq(i).hasClass("active")) { //如果 是选中的
				chooseNo += 1;
			}
		}

		var $choose_container = $(supplier_manage.manage_containerName).find(".choose_container");
		//没有全部选中
		if (chooseNo == 0 ||
			chooseNo < $(supplier_manage.manage_containerName)
				.find("tbody .item").length) {
			$choose_container.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png");
		}
		else {
			$choose_container.addClass("active").find("img").attr("src", "img/icon_checked.png");
		}

		supplier_manage.CheckEmployeeIsChoose();//检查是否有选中的 员工
	},
	//检查是否有选中的 选项(底部按钮颜色变化)
	CheckEmployeeIsChoose: function () {
		var $foot = $(supplier_manage.manage_containerName).find(".foot");

		if ($(supplier_manage.manage_containerName).find("tbody .item.active").length > 0) {
			$foot.find(".btn_del").addClass("btn-danger").removeClass("btn-default");
		}
		else {
			$foot.find(".btn_del").addClass("btn-default").removeClass("btn-danger");
		}
	},

	//供应商删除 - 单项删除
	supplierDelByOnly: function (self) {
		var id = $(self).closest(".item").attr("data-id");

		supplier_manage.DelArray = "[{'id':'" + id + "'}]";
		supplier_manage.DelArray = eval("(" + supplier_manage.DelArray + ")");
		supplier_manage.supplierDel();//供应商删除 - 确认
	},
	//供应商删除 - 多项删除
	supplierDelByMore: function () {
		var $active_item = $(supplier_manage.manage_containerName).find("tbody .item.active");
		var $length = $active_item.length;
		if ($length <= 0) {
			messageCue("请选择订单！");
			return;
		}

		for (var i = 0; i < $length; i++) {
			var item = $active_item.eq(i);

			var id = item.attr("data-id");
			supplier_manage.DelArray += supplier_manage.DelArray == "" ?
				(
					"{" +
					"'id':'" + id + "'" +
					"}"
				) :
				(
					",{" +
					"'id':'" + id + "'" +
					"}"
				)

		}
		supplier_manage.DelArray = "[" + supplier_manage.DelArray + "]";
		supplier_manage.DelArray = eval("(" + supplier_manage.DelArray + ")");

		supplier_manage.supplierDel();//供应商删除 - 确认
	},
	//供应商删除 - 确认
	supplierDel: function () {
		delWarning("供应商", function () {
			loadingInit();//加载框 出现

			var obj = new Object();
			obj.ids = supplier_manage.DelArray;

			aryaPostRequest(
				urlGroup.supplier_manage_del_url,
				obj,
				function (data) {
					//alert(JSON.stringify(data));

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除成功！");
					}
					else {
						messageCue(data.msg);
					}
					supplier_manage.Init();
				},
				function (error) {
					messageCue(error);
				});
		})
	},

	//供应商新增 - 弹框显示
	supplierAddModalShow: function () {
		$(".supplier_add_modal").modal({
			backdrop: false,
			keyboard: false
		});
	},
	//供应商新增 - 确认
	supplierAddSure: function () {
		if (!supplier_manage.checkParamBySupplierAdd()) {
			return;
		}

		loadingInit();//加载框 出现

		var $row = $(".supplier_add_modal").find(".modal-body .row");
		var supplier_name = $row.find(".supplier_name input").val();
		var manage_cost = $row.find(".manage_cost input").val();

		var obj = new Object();
		obj.name = supplier_name;
		obj.fee = manage_cost;

		aryaPostRequest(urlGroup.supplier_manage_add_url, obj,
			function (data) {

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("新增成功！");
					$(".supplier_add_modal").modal("hide");

					supplier_manage.supplierListGet();//供应商查询 - 列表
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	},
	//供应商新增 - 检查参数
	checkParamBySupplierAdd: function () {
		var flag = false;
		var txt = "";
		var $row = $(".supplier_add_modal").find(".modal-body .row");

		var supplier_name = $row.find(".supplier_name input").val();
		var manage_cost = $row.find(".manage_cost input").val();
		//var contact_name = $row.find(".contact_name input").val();

		if (supplier_name == "") {
			txt = "供应商名称不能为空！";
		}
		else if (manage_cost == "") {
			txt = "管理费不能为空！";
		}
		//else if (contact_name == "") {
		//	txt = "联系人不能为空！";
		//}
		else {
			flag = true;
		}

		if (txt != "") {
			messageCue(txt);
		}

		return flag;
	},

	//供应商编辑 - 弹框显示
	supplierModifyModalShow: function (self) {
		$(self).closest(".item").addClass("modify_item").siblings(".item").removeClass("modify_item");

		//var $item = $(self).closest(".item");
		//sessionStorage.setItem("SupplierName_modify", $item.find(".supplier_name").html());
		//sessionStorage.setItem("ManageCost_modify", $item.find(".manage_cost").attr("data-cost"));
		//sessionStorage.setItem("SupplierContract_modify", $item.find(".supplier_contact").html());

		$(".supplier_modify_modal").modal({
			backdrop: false,
			keyboard: false
		});
	},
	//供应商编辑 - 确认
	supplierModifySure: function () {
		if (!supplier_manage.checkParamBySupplierModify()) {
			return;
		}

		loadingInit();//加载框 出现

		var $row = $(".supplier_modify_modal").find(".modal-body .row");
		var supplier_name = $row.find(".supplier_name input").val();
		var manage_cost = $row.find(".manage_cost input").val();

		var obj = new Object();
		obj.id = $(supplier_manage.manage_containerName).find(".item.modify_item").attr("data-id");
		obj.name = supplier_name;
		obj.fee = manage_cost;

		aryaPostRequest(urlGroup.supplier_manage_update_url, obj,
			function (data) {

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("编辑成功！");

					var $item = $(supplier_manage.manage_containerName).find(".item.modify_item");
					$item.find(".supplier_name").html(supplier_name);
					$item.find(".manage_cost").attr("data-cost", manage_cost).html("￥" + manage_cost);
					$(supplier_manage.manage_containerName).find(".item.modify_item")
						.removeClass(".modify_item");

					$(".supplier_modify_modal").modal("hide");
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	},
	//供应商编辑 - 检查参数
	checkParamBySupplierModify: function () {
		var flag = false;
		var txt = "";
		var $row = $(".supplier_modify_modal").find(".modal-body .row");

		var supplier_name = $row.find(".supplier_name input").val();
		var manage_cost = $row.find(".manage_cost input").val();
		//var contact_name = $row.find(".contact_name input").val();

		if (supplier_name == "") {
			txt = "供应商名称不能为空！";
		}
		else if (manage_cost == "") {
			txt = "管理费不能为空！";
		}
		//else if (contact_name == "") {
		//	txt = "联系人不能为空！";
		//}
		else {
			flag = true;
		}

		if (txt != "") {
			messageCue(txt);
		}

		return flag;
	},

	//供应商 绑定城市 列表 - 弹框显示
	supplierBindCityModalShow: function (self) {
		var id = $(self).closest(".item").attr("data-id");
		var district_list = sessionStorage.getItem("district_list_" + id);
		$(".supplier_bind_city_modal").find(".modal-body").html(district_list);

		$(".supplier_bind_city_modal").modal({
			backdrop: false,
			keyboard: false
		});
	},

	//供应商分配页面 - 显示
	supplierAllotShow: function () {
		$(supplier_manage.manage_containerName).hide().siblings(".container").show();
	}

};

var supplier_allot_manage = {
	allot_manage_containerName: "",
	currentTreeNode: "",//当前选中的 参保地区
	currentPage: "1",//当前页面
	totalPage: "3",//总页面

	Init: function () {
		supplier_allot_manage.allot_manage_containerName = ".supplier_manage .supplier_allot_container";

		supplier_allot_manage.initSoinArea();//初始化 参保地区

		//该城市未绑定供应商列表 - 弹框显示
		$(".supplier_unused_city_modal").on("shown.bs.modal", function () {
			supplier_allot_manage.currentPage = 1;
			supplier_allot_manage.supplierListByUnused();//获取 该城市里面 未添加的 供应商 列表
		});
	},

	//供应商列表页面 - 显示
	supplierListShow: function () {
		$(supplier_allot_manage.allot_manage_containerName).hide().siblings(".container").show();
	},

	//初始化 参保地区
	initSoinArea: function () {
		//获取 地区列表
		initAreaTree(
			supplier_allot_manage.allot_manage_containerName + " .soin_used_area_tree",
			supplier_allot_manage.allot_manage_containerName + " .area_tree_hud",
			urlGroup.supplier_manage_district_list_get_url,
			function (treeNode) {
				supplier_allot_manage.ChooseCity(treeNode);
			});

	},
	//选择 城市
	ChooseCity: function (treeNode) {
		//alert(JSON.stringify(treeNode));

		loadingInit();//加载框 出现

		supplier_allot_manage.currentPage = 1;//

		supplier_allot_manage.currentTreeNode = treeNode;
		var $right_side = $(supplier_allot_manage.allot_manage_containerName).find(".content .right_side");

		var id = treeNode.id;//
		//全国
		if (id == 100000) {
			var txt = "请选择具体的城市获取供应商信息";
			$right_side.find(".prompt").show().html(txt);
			$right_side.find(".prompt").siblings().hide();
			loadingRemove();//
			return;
		}
		$right_side.find(".prompt").hide();//提示信息隐藏

		$right_side.find(".btn_list").show();//添加供应商 按钮显示

		supplier_allot_manage.supplierListByArea();//获取对应城市里面的 供应商列表(已添加的供应商)

	},
	//获取对应城市里面的 供应商列表(已添加的供应商)
	supplierListByArea: function () {
		var $right_side = $(supplier_allot_manage.allot_manage_containerName).find(".content .right_side");

		var id = supplier_allot_manage.currentTreeNode.id;
		//调用接口 获取供应商 列表
		var obj = new Object();
		obj.district_id = id;
		var url = urlGroup.supplier_manage_added_supplier_url + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					var supplier_list = "";//
					var suppliers = data.result.suppliers;
					if (suppliers == null || suppliers.length == 0) {
					}
					else {
						for (var i = 0; i < suppliers.length; i++) {
							var item = suppliers[i];

							var id = item.id;//供应商id
							var name = item.name;//供应商名称
							var fee = item.fee;//管理费
							var is_first = item.is_default;//1 为首选

							supplier_list +=
								"<tr class='item supplier_item' data-id='" + id + "' " +
								"data-is_first='" + is_first + "'>" +
								"<td class='supplier_name'>" + name + "</td>" +
								"<td class='manage_cost' data-cost='" + fee + "'>￥" + fee + "</td>" +
								"<td class='is_first'>" + "<div class='icon'></div>" + "</td>" +
								"<td class='supplier_operate'>" +
								"<span class='btn btn-sm btn-primary btn_first'>设为首选</span>" +
								"<span class='btn btn-sm btn-danger btn_del'>移除</span>" +
								"</td>" +
								"</tr>";
						}
					}

					$right_side.find(".table_container").show();
					$right_side.find(".table_container").find("tbody").html(supplier_list);
					supplier_allot_manage.supplierListInit();//获取 城市对应供应商列表 初始化
				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});
	},
	//获取 城市对应供应商列表 初始化
	supplierListInit: function () {
		var $right_side = $(supplier_allot_manage.allot_manage_containerName).find(".content .right_side");

		//如果 该城市 没有供应商
		if ($right_side.find(".table_container").find(".item").length <= 0) {
			var txt = "暂无供应商信息";
			$right_side.find(".prompt").show().html(txt);//显示提示信息

			$right_side.find(".table_container").hide();
		}
		else {
			$right_side.find(".prompt").hide();//提示信息 隐藏

			$right_side.find(".item").each(function () {

				var is_first = $(this).attr("data-is_first");//1 首选
				if (is_first == 1) {
					$(this).addClass("first_item");
				}

				//设为首选
				$(this).find(".btn_first").click(function () {
					supplier_allot_manage.supplierFirSetting(this);
				});

				//移除
				$(this).find(".btn_del").click(function () {
					supplier_allot_manage.supplierDel(this);
				});

			});
		}

	},

	//设置首选供应商
	supplierFirSetting: function (self) {
		var $item = $(self).closest(".item");
		//$item.addClass("active").siblings(".item").removeClass("active");
		var name = $item.find(".supplier_name").html();

		swal({
				title: "确定要将\"" + name + "\"设为首选供应商吗？",
				//text: "删除后将无法恢复，请谨慎操作！",
				//type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {
				var obj = new Object();
				obj.district_id = supplier_allot_manage.currentTreeNode.id;
				obj.id = $item.attr("data-id");

				aryaPostRequest(
					urlGroup.supplier_manage_first_supplier_url,
					obj,
					function (data) {
						//alert(JSON.stringify(data));

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("设置首选供应商成功！");

							$item.addClass("first_item").siblings(".item").removeClass("first_item");
						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});
			});
	},

	//供应商 - 移除
	supplierDel: function (self) {
		var $item = $(self).closest(".item");
		var name = $item.find(".supplier_name").html();

		swal({
				title: "确定要将\"" + name + "\"供应商移除吗？",
				text: "移除后将无法恢复，请谨慎操作！",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {

				var obj = new Object();
				obj.district_id = supplier_allot_manage.currentTreeNode.id;
				obj.id = $item.attr("data-id");

				//alert(JSON.stringify(obj));

				aryaPostRequest(
					urlGroup.supplier_manage_del_supplier_url,
					obj,
					function (data) {
						//alert(JSON.stringify(data));

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("移除成功！");

							//获取对应城市里面的 供应商列表(已添加的供应商)
							supplier_allot_manage.supplierListByArea();
						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});
			});
	},

	//该城市未绑定供应商列表 - 弹框显示
	supplierListByUnusedModalShow: function () {
		$(".supplier_unused_city_modal").modal({
			backdrop: false,
			keyboard: false
		});
	},
	//获取 该城市里面 未添加的 供应商 列表
	supplierListByUnused: function () {
		loadingInit();//加载框 出现

		var id = supplier_allot_manage.currentTreeNode.id;
		var obj = new Object();
		obj.district_id = id;
		obj.page = supplier_allot_manage.currentPage;
		obj.page_size = "10";
		var url = urlGroup.supplier_manage_no_add_supplier_url + "?" + jsonParseParam(obj);
		//alert(url);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					supplier_allot_manage.totalPage = data.result.pages;//

					var supplier_list = "";//
					var suppliers = data.result.suppliers;
					if (suppliers == null || suppliers.length == 0) {
					}
					else {
						for (var i = 0; i < suppliers.length; i++) {
							var item = suppliers[i];

							var id = item.id;//供应商id
							var name = item.name;//供应商名称
							var fee = item.fee;//管理费
							var district_count = item.district_count;//已绑定城市
							//var is_first = item.is_default;//1 为首选

							supplier_list +=
								"<tr class='item supplier_item' data-id='" + id + "'>" +
								"<td class='choose_item' onclick='supplier_allot_manage.ChooseItem(this)'>" +
								"<img src='img/icon_Unchecked.png'/>" +
								"</td>" +
								"<td class='supplier_name'>" + name + "</td>" +
								"<td class='manage_cost' data-cost='" + fee + "'>￥" + fee + "</td>" +
								"<td class='supplier_city'>" + district_count + "</td>" +
								"</tr>";
						}
					}

					var $tbody = $(".supplier_unused_city_modal").find("table tbody");
					$tbody.html(supplier_list);

					//获取 该城市里面 未添加的 供应商 列表 - 初始化
					supplier_allot_manage.supplierListByUnusedInit();

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});
	},
	//获取 该城市里面 未添加的 供应商 列表 - 初始化
	supplierListByUnusedInit: function () {
		var $modal = $(".supplier_unused_city_modal");
		var $pager_container = $modal.find(".pager_container");

		//判断查询 结果为空
		if ($modal.find("tbody .item").length == 0) {
			$pager_container.hide();
			$modal.find(".table_container").hide();
			$modal.find(".prompt").show().html("暂无供应商可添加！");
			return
		}
		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: supplier_allot_manage.currentPage, //当前页数
			totalPages: supplier_allot_manage.totalPage, //总页数
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

				supplier_allot_manage.currentPage = page;
				supplier_allot_manage.supplierListByUnused();//获取 该城市里面 未添加的 供应商 列表

			}
		};

		$modal.find(".prompt").hide();//隐藏提示信息
		$modal.find(".table_container").show();//城市中 对应的未绑定供应商列表展示
		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);
	},

	//未添加的供应商列表 - 单项选择
	ChooseItem: function (self) {
		var $item = $(self).closest(".item");

		if ($item.hasClass("active")) { //如果选中行
			$item.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png")
		}
		else { //如果未选中
			$item.addClass("active").find("img").attr("src", "img/icon_checked.png");
			$item.siblings(".item").removeClass("active")
				.find("img").attr("src", "img/icon_Unchecked.png")
		}
	},
	//添加供应商 - 确认
	supplierAddSure: function () {
		var $active_item = $(".supplier_unused_city_modal").find("tbody .item.active");
		if ($active_item.length <= 0) {
			toastr.info("请选择供应商！");
			return;
		}

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.district_id = supplier_allot_manage.currentTreeNode.id;
		obj.id = $active_item.attr("data-id");

		//alert(JSON.stringify(obj));

		aryaPostRequest(
			urlGroup.supplier_manage_add_supplier_url,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("添加成功！");
					$(".supplier_unused_city_modal").modal("hide");
					supplier_allot_manage.supplierListByArea();//获取 对应城市的 已绑定的供应商
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	}

};

$(function () {
	supplier_manage.Init();

	supplier_allot_manage.Init();
});