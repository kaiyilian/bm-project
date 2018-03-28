/**
 * Created by CuiMengxin on 2016/10/18.
 */

var app_user_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面

	//初始化
	Init: function () {
		app_user_manage.containerName = ".app_user_manage_container";

		app_user_manage.btnSearchClick();//查询 按钮 click
	},

	//查询 按钮 click
	btnSearchClick: function () {
		app_user_manage.currentPage = 1;
		app_user_manage.getUserList();//获取 用户列表
	},

	//获取 用户列表
	getUserList: function () {

		loadingInit();

		var obj = new Object();
		obj.keyword = $.trim($(app_user_manage.containerName).find(".searchCondition").val());
		//obj.corp_name = $.trim($(app_user_manage.containerName).find(".corp_name_search").val());
		obj.page = app_user_manage.currentPage;
		obj.page_size = "10";
		var url = urlGroup.user_manage.app_user_manage.list+ "?" + jsonParseParam(obj);

		//console.log(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取app用户列表 ：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					app_user_manage.totalPage = data.result.pages;


					var users = data.result.users;
					var user_list = "";
					if (!users || users.length == 0) {
						user_list = "<tr><td colspan='11'>暂无用户</td></tr>";
					}
					else {
						for (var i = 0; i < users.length; i++) {
							var $item = users[i];

							var id = $item.id ? $item.id : "";
							var name = $item.name ? $item.name : "";
							var sex = $item.sex ? $item.sex : "";
							var nick_name = $item.nick_name ? $item.nick_name : "";
							var idcard_no = $item.idcard_no ? $item.idcard_no : "";
							var hukou = $item.hukou ? $item.hukou : "";
							var corp = $item.corp ? $item.corp : "";
							var create_time = $item.create_time ? $item.create_time : "";
							create_time = timeInit1(create_time);
							var last_login_time = $item.last_login_time ? $item.last_login_time : "";
							last_login_time = timeInit1(last_login_time);
							var phone = $item.phone_no ? $item.phone_no : "";
							var short_name = $item.short_name ? $item.short_name : "";
							var use_phone_no = $item.use_phone_no ? $item.use_phone_no : "";
							var balance = $item.balance ? $item.balance : "0";


							user_list += "<tr class='item user_item' data-id='" + id + "'>" +
								"<td class='user_nickname'>" + nick_name + "</td>" +
								"<td class='user_name'>" + name + "</td>" +
								"<td class='user_sex'>" + sex + "</td>" +
								"<td class='user_phone'>" + phone + "</td>" +
								"<td class='user_use_phone_no'>" + use_phone_no + "</td>" +
								"<td class='user_balance'>" + balance + "</td>" +
								"<td class='user_idCard'>" + idcard_no + "</td>" +
								"<td class='corp_name'>" + short_name + "</td>" +
								"<td class='user_corp_info'>" + corp + "</td>" +
								"<td class='user_create_time'>" + create_time + "</td>" +
								"<td class='user_last_login_time'>" + last_login_time + "</td>" +
								"</tr>";

						}
					}

					$(app_user_manage.containerName).find("table tbody").html(user_list);
					app_user_manage.userListInit();//用户列表 初始化

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				console.log("获取app用户列表 -----error：");
				console.log(error);

				messageCue(error);
			});

	},

	//用户列表 初始化
	userListInit: function () {

		var $item = $(app_user_manage.containerName).find("tbody .item");
		var $pager_container = $(app_user_manage.containerName).find(".pager_container");
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
			currentPage: app_user_manage.currentPage, //当前页数
			totalPages: app_user_manage.totalPage, //总页数
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

				app_user_manage.currentPage = page;
				app_user_manage.getUserList();//查询

			}
		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);

	},

};

$(function () {
	app_user_manage.Init();//
});