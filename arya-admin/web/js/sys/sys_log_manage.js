/**
 * Created by CuiMengxin on 2016/10/18.
 * 系统日志管理
 */

var sys_log_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面

	//初始化
	Init: function () {
		sys_log_manage.containerName = ".sys_log_manage_container";

		sys_log_manage.initSearchCondition();//一键重置

	},
	//一键重置
	initSearchCondition: function () {

		sys_log_manage.initOperateTypeList();//获取 操作类型

		sys_log_manage.initOperatorList();

		$(sys_log_manage.containerName).find(".search_container").find(".opRealName").val("");

		//清空查询条件
		$(sys_log_manage.containerName).find(".search_container").find(".searchCondition").val("");

		//内容清空
		var tbody = "<tr><td colspan='5'>暂无日志</td></tr>";
		$(sys_log_manage.containerName).find("table tbody").html(tbody);

	},

	initOperatorList: function() {
		var operator_list = $(sys_log_manage.containerName).find(".search_container")
			.find(".operator_list");

		var params = {
			page: 0,
			page_size: -1,
			status: 1
		};

		var url = urlGroup.sys_user_list_raw + "?" + jsonParseParam(params);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("操作类型：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var sysUserList = data.result;
					var user_list = "";
					if (!sysUserList || sysUserList.length == 0) {
						user_list = "<option value=''>暂无操作人</option>";
					}
					else {
						for (var i = 0; i < sysUserList.length; i++) {
							var user = sysUserList[i];

							var loginName = user.loginName; //
							var name = user.realName; //

							user_list += "<option value='" + loginName + "'>" + name + "</option>";
						}
					}

					operator_list.html(user_list);

					operator_list.chosen({
						allow_single_deselect: true,//选择之后 是否可以取消
						max_selected_options: 1,//最多只能选择1个
						width: "100%",//select框 宽度
						no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
					});

					operator_list.siblings(".chosen-container").addClass("form-control")
						.css("padding", "0");
				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},

	//获取 操作类型
	initOperateTypeList: function () {
		var $module_list = $(sys_log_manage.containerName).find(".search_container")
			.find(".module_list");

		aryaGetRequest(
			urlGroup.operate_type_list,
			function (data) {
				//console.log("操作类型：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var types = data.result.types;
					var type_list = "";
					if (!types || types.length == 0) {
						type_list = "<option value=''>暂无类型</option>";
					}
					else {
						for (var i = 0; i < types.length; i++) {
							var $item = types[i];

							var id = $item.id;//
							var name = $item.name;//

							type_list += "<option value='" + id + "'>" + name + "</option>";

						}
					}

					$module_list.html(type_list);

					$module_list.chosen({
						allow_single_deselect: true,//选择之后 是否可以取消
						max_selected_options: 1,//最多只能选择1个
						width: "100%",//select框 宽度
						no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
					});

					$module_list.siblings(".chosen-container").addClass("form-control")
						.css("padding", "0");

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},

	//查询 按钮 click
	btnSearchClick: function () {
		sys_log_manage.currentPage = 1;
		sys_log_manage.getLogList();//获取 日志列表
	},

	//获取 日志列表
	getLogList: function () {

		loadingInit();
		var opRealName = $.trim($(sys_log_manage.containerName).find(".operator_list").val());
		var keyword = $.trim($(sys_log_manage.containerName).find(".searchCondition").val());
		var type = $(sys_log_manage.containerName).find(".module_list").val();
		type = type ? type[0] : "";
		//console.log(type)

		var params = {};
		params.op_login_name = opRealName; // TODO 替换为登录名
		params.keyword = keyword;
		params.operate_type = type;
		params.page = sys_log_manage.currentPage;
		params.page_size = "10";
		// var url = urlGroup.sys_log_list + "?" + jsonParseParam(obj);

		aryaPostRequest(
			urlGroup.sys_log_list,
			params,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					sys_log_manage.totalPage = data.result.pages;

					var logs = data.result.logs;
					var log_list = "";
					if (!logs || logs.length == 0) {
						log_list = "<tr><td colspan='5'>暂无日志</td></tr>";
					}
					else {
						for (var i = 0; i < logs.length; i++) {
							var $item = logs[i];

							var id = $item.id;//
							var operate_type_name = $item.operate_type_name ? $item.operate_type_name : "";//
							var operator = $item.operator ? $item.operator : "";//
							var login_name = $item.login_name ? $item.login_name : "";//
							var content = $item.content ? $item.content : "";//
							var operate_time = $item.operate_time ? $item.operate_time : "";//
							operate_time = timeInit1(operate_time);

							log_list += "<tr class='item log_item' data-id='" + id + "'>" +
								"<td class='module_name'><div>" + operate_type_name + "</div></td>" +
								"<td class='log_operator'><div>" + operator + "</div></td>" +
								"<td class='login_name'><div>" + login_name + "</div></td>" +
								"<td class='log_content'><div>" + content + "</div></td>" +
								"<td class='log_time'><div>" + operate_time + "</div></td>" +
								"</tr>";

						}
					}

					$(sys_log_manage.containerName).find("table tbody").html(log_list);
					sys_log_manage.logListInit();//日志列表 初始化

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

	//订单列表 初始化
	logListInit: function () {

		var $item = $(sys_log_manage.containerName).find("tbody .item");
		var $pager_container = $(sys_log_manage.containerName).find(".pager_container");
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
			currentPage: sys_log_manage.currentPage, //当前页数
			totalPages: sys_log_manage.totalPage, //总页数
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

				sys_log_manage.currentPage = page;
				sys_log_manage.getLogList();//查询

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);

	},

};

$(function () {
	sys_log_manage.Init();//
});