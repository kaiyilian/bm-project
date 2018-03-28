/**
 * Created by CuiMengxin on 2016/11/21.
 */

var $coupon_info_modal = $(".coupon_info_modal");

var fk_coupon_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面
	is_Choose_all_page: "0",//是否是选择 所有（查询条件下的）	0 不是 1 是

	coupon_id: "",//福库券 id
	coupon_img_id: "",//福库券 图片 id
	corp_list: "",//企业列表
	cur_opt: "add",//当前操作是新增、编辑

	Init: function () {
		fk_coupon_manage.containerName = ".fk_coupon_manage_container";

		fk_coupon_manage.initParam();//初始化 参数
		fk_coupon_manage.initCorpList();//初始化 公司列表
		fk_coupon_manage.couponList();//获取 福库券列表

		//显示 提示框
		$("[data-toggle='tooltip']").tooltip();

		//福库券信息 弹框 弹出
		$coupon_info_modal.on("shown.bs.modal", function () {
			fk_coupon_manage.coupon_img_id = "";//图片 id为空

			$coupon_info_modal.find(".modal-title").html("新增福库券");

			var $row = $coupon_info_modal.find(".modal-body > .row");
			$row.find("input").removeAttr("disabled");

			//图片
			var $img = $row.find(".img_thumb").empty();

			//默认图片
			var $default_img = $("<img>");
			$default_img.addClass("coupon_bg");
			$default_img.attr("src", "img/img_add_default.png");
			$default_img.click(function () {
				//上传图片 - 按钮点击
				fk_coupon_manage.ChooseImgClick();
			});
			$default_img.appendTo($img);

			//初始化 二维码 位置
			fk_coupon_manage.initQrCode();

			//初始化 公司列表（弹框中）
			fk_coupon_manage.initCorpListInModal();

			//初始化 数量
			$row.find(".coupon_count").val("");

			//初始化 金额
			$row.find(".coupon_money").val("");

			//初始化 时间
			fk_coupon_manage.initTimeContainer();

			if (fk_coupon_manage.cur_opt == "modify") {
				fk_coupon_manage.couponDetail();//福库券 获取券 详情
			}

		});

	},

	//初始化 参数
	initParam: function () {
		fk_coupon_manage.coupon_id = "";
		fk_coupon_manage.coupon_img_id = "";
		fk_coupon_manage.is_Choose_all_page = "0";//默认 不选择所有福库券	0 否 1 是
	},
	//初始化 公司列表
	initCorpList: function () {

		var $search_container = $(fk_coupon_manage.containerName)
			.find(".search_container");
		var $corp_list = $search_container.find(".corp_list");

		var obj = {
			"biz_type": ""
		};
		var url = urlGroup.fk_order_corp_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var list = "<option value=''>全部</option>";
					var $corps = data.result.corps;
					if ($corps == null || $corps.length == 0) {
					}
					else {
						for (var i = 0; i < $corps.length; i++) {
							var $item = $corps[i];

							var id = $item.id;
							var name = $item.name;//

							list += "<option value='" + id + "'>" + name + "</option>"

						}
					}

					$corp_list.html(list);

					$corp_list.chosen({
						allow_single_deselect: true,//选择之后 是否可以取消
						max_selected_options: 1,//最多只能选择1个
						width: "100%",//select框 宽度
						no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
					});

					$corp_list.siblings(".chosen-container").addClass("form-control")
						.css("padding", "0");

				}
				else {
					console.log("获取日志-----error：");
					console.log(data.msg);

					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//初始化 二维码 位置
	initQrCode: function () {
		var $row = $coupon_info_modal.find(".modal-body > .row");

		//图片
		var $img = $row.find(".img_thumb");

		var $qrCode_img = $("<img>");
		$qrCode_img.addClass("qrCode_img");
		$qrCode_img.attr("src", "img/qrcode.jpg");
		$qrCode_img.appendTo($img);

		//return;

		aryaGetRequest(
			urlGroup.fk_coupon_layout,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {
						var $item = data.result;

						var coupon_width_height = $item.coupon_width_height ? $item.coupon_width_height : 1;
						var qrcode_x_coupon_width = $item.qrcode_x_coupon_width ? $item.qrcode_x_coupon_width : 1;
						var qrcode_y_coupon_height = $item.qrcode_y_coupon_height ? $item.qrcode_y_coupon_height : 1;
						var qrcode_width_coupon_width = $item.qrcode_width_coupon_width ? $item.qrcode_width_coupon_width : 1;
						var qrcode_height_coupon_height = $item.qrcode_height_coupon_height ? $item.qrcode_height_coupon_height : 1;

						var width = $img.width();//背景图 宽度
						var height = width / coupon_width_height;//背景图 高度
						var qr_width = width * qrcode_width_coupon_width;
						var qr_height = height * qrcode_height_coupon_height;
						var qr_left = width * qrcode_x_coupon_width;
						var qr_top = height * qrcode_y_coupon_height;

						$img.css({
							width: width,
							height: height
						});

						$img.find(".qrCode_img").css({
							width: qr_width,
							height: qr_height,
							top: qr_top,
							left: qr_left
						});

						//var coupon_width = $item.coupon_width ? $item.coupon_width : "";//
						//var coupon_height = $item.coupon_height ? $item.coupon_height : "";//
						//var qr_code_width = $item.qr_code_width ? $item.qr_code_width : "";//
						//var qr_code_height = $item.qr_code_height ? $item.qr_code_height : "";//
						//var qr_code_x = $item.qr_code_x ? $item.qr_code_x : "";//
						//var qr_code_y = $item.qr_code_y ? $item.qr_code_y : "";//
						//


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
			});

	},
	//初始化 公司列表（弹框中）
	initCorpListInModal: function () {

		var obj = {
			"biz_type": "8"		//福库券所有的 公司
		};
		var url = urlGroup.fk_order_corp_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var $row = $coupon_info_modal.find(".modal-body > .row");
					var $corp_list_container = $row.find(".corp_list_container").empty();


					var list = "<option value=''>全部</option>";
					var $corps = data.result.corps;
					if ($corps == null || $corps.length == 0) {
					}
					else {
						for (var i = 0; i < $corps.length; i++) {
							var $item = $corps[i];

							var id = $item.id;
							var name = $item.name;//

							list += "<option value='" + id + "'>" + name + "</option>"

						}
					}

					var $corp_list = $("<select>");
					$corp_list.addClass("corp_list");
					$corp_list.addClass("chosen-select");
					$corp_list.attr("data-placeholder", "请选择公司");
					$corp_list.attr("multiple", "multiple");
					$corp_list.appendTo($corp_list_container);

					$corp_list.html(list);

					$corp_list.chosen({
						allow_single_deselect: true,//选择之后 是否可以取消
						max_selected_options: 1,//最多只能选择1个
						width: "100%",//select框 宽度
						no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
					});


				}
				else {
					console.log("获取日志-----error：");
					console.log(data.msg);

					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//初始化 时间
	initTimeContainer: function () {
		var $row = $coupon_info_modal.find(".modal-body > .row");

		$row.find(".btn_timeSet").removeClass("active");
		$row.find(".time_container").hide().find("input").val("");

		var start = {
			elem: "#coupon_beginTime",
			event: 'focus', //触发事件
			format: 'YYYY-MM-DD hh:mm:ss',
			//min: laydate.now(), //设定最小日期为当前日期
			max: '2099-06-16 23:59:59', //最大日期
			istime: true,//是否开启时间选择
			istoday: false, //是否显示今天
			choose: function (datas) {
				//end.min = datas; //开始日选好后，重置结束日的最小日期
				//end.start = datas;//将结束日的初始值设定为开始日


			}
		};

		var end = {
			elem: "#coupon_endTime",
			event: 'focus', //触发事件
			format: 'YYYY-MM-DD hh:mm:ss',
			//min: laydate.now(),
			max: '2099-06-16 23:59:59',
			istime: true,
			istoday: false,
			choose: function (datas) {

				var s = new Date(datas);
				s = s.setDate(s.getDate() + 1);
				s = new Date(s).toLocaleDateString();
				s = new Date(s).getTime();

				var time = timeInit1(s - 1000);

				$("#coupon_endTime").val(time);
				//
				//start.max = time; //结束日选好后，重置开始日的最大日期
			}
		};

		laydate(start);
		laydate(end);
	},

	//查询
	btnSearchClick: function () {
		fk_coupon_manage.currentPage = "1";

		fk_coupon_manage.couponList();//获取 福库券列表
	},
	//获取 福库券列表
	couponList: function () {
		var $table_container = $(fk_coupon_manage.containerName)
			.find(".table_container");
		var $search_container = $(fk_coupon_manage.containerName)
			.find(".search_container");
		var $corp_list = $search_container.find(".corp_list");

		var corp_id = $corp_list.val() ? $corp_list.val()[0] : "";//公司id
		var create_date = $search_container.find(".createTime").val();//创建时间
		create_date = create_date ? ( new Date(create_date).getTime()) : "";

		var obj = {};
		obj.corp_id = corp_id;
		obj.create_date = create_date;
		obj.page = fk_coupon_manage.currentPage;
		obj.page_size = "10";

		//console.log(obj);
		var url = urlGroup.fk_coupon_list + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					fk_coupon_manage.totalPage = data.result.pages;//总页数
					if (fk_coupon_manage.currentPage > fk_coupon_manage.totalPage) {
						fk_coupon_manage.currentPage -= 1;
						fk_coupon_manage.couponList();
						return;
					}

					var list = "";
					var coupons = data.result.coupon_defs;
					if (!coupons || coupons.length == 0) {
						list = "<tr><td colspan='10'>暂无福库券</td></tr>";
					}
					else {
						for (var i = 0; i < coupons.length; i++) {
							var $item = coupons[i];

							var id = $item.coupon_def_id ? $item.coupon_def_id : "";//
							var thumbnail_url = $item.thumbnail_url ? $item.thumbnail_url : "";//
							var corp_name = $item.corp_name ? $item.corp_name : "";//
							var count = $item.count ? $item.count : "";//
							var active_time = $item.active_time ? $item.active_time : "";//
							active_time = timeInit1(active_time);
							var expire_time = $item.expire_time ? $item.expire_time : "";//
							expire_time = timeInit1(expire_time);
							var price = $item.price ? $item.price : "";//
							var is_exported = $item.is_exported ? $item.is_exported : "0";//
							var create_time = $item.create_time ? $item.create_time : "";//
							create_time = timeInit(create_time);

							list +=
								"<tr class='item fk_coupon_item' " +
								"data-id='" + id + "' " +
								"data-is_export='" + is_exported + "' " +
								">" +
								"<td class='choose_item' onclick='fk_coupon_manage.chooseItem(this)'>" +
								"<img src='img/icon_Unchecked.png'/>" +
								"</td>" +
								"<td class='coupon_img'>" +
								"<img src='" + thumbnail_url + "'>" +
								"</td>" +
								"<td class='corp_name'>" + corp_name + "</td>" +
								"<td class='coupon_count'>" + count + "</td>" +
								"<td class='coupon_beginTime'>" + active_time + "</td>" +
								"<td class='coupon_endTime'>" + expire_time + "</td>" +
								"<td class='coupon_money'>" + price + "</td>" +
								"<td class='is_export'>未导出</td>" +
								"<td class='coupon_createTime'>" + create_time + "</td>" +
								"<td class='operate'>" +
								"<div class='btn btn-sm btn-primary btn_modify'>编辑</div>" +
								"<div class='btn btn-sm btn-danger btn_del'>删除</div>" +
								"</td>" +
								"</tr>"

						}
					}

					$table_container.find("tbody").html(list);
					fk_coupon_manage.couponListInit();	//福库券 列表初始化

				}
				else {
					//console.log("获取日志-----error：");
					//console.log(data.msg);

					toastr.warning(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//福库券 列表初始化
	couponListInit: function () {
		var $table_container = $(fk_coupon_manage.containerName)
			.find(".table_container");
		var $item = $table_container.find("tbody .item");
		var $pager_container = $(fk_coupon_manage.containerName).find(".pager_container");

		if ($item.length == 0) {
			$pager_container.hide();
			return
		}

		$item.each(function () {
			//var $item = $(this).closest(".item");
			var is_export = $(this).attr("data-is_export");//0 未导出  1 已导出

			//已导出
			if (is_export == 1) {
				$(this).addClass("exported");
				$(this).find(".is_export").text("已导出");
			}

			//编辑
			$(this).find(".btn_modify").click(function () {
				fk_coupon_manage.couponModifyModalShow(this);
			});

			//删除
			$(this).find(".btn_del").click(function () {
				fk_coupon_manage.couponDel(this);
			});


		});

		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: fk_coupon_manage.currentPage, //当前页数
			totalPages: fk_coupon_manage.totalPage, //总页数
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

				fk_coupon_manage.currentPage = page;
				fk_coupon_manage.couponList();//查询

			}

		};

		var ul = '<ul class="pagenation" style="float:right;"></ul>';
		$pager_container.show();
		$pager_container.html(ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);

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

		fk_coupon_manage.isChooseAll();//是否 已经全部选择
	},
	//选择当前页 全部
	chooseCurAll: function () {
		var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
		var $cur = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		var $choose_item = $table_container.find(".choose_item");//table choose_item


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

		fk_coupon_manage.is_Choose_all_page = "0";
		//移除 选择全部的选中状态
		$(fk_coupon_manage.containerName).find(".foot .choose_item").removeClass("active")
			.find("img").attr("src", "img/icon_Unchecked.png");

	},
	//选择全部(查询条件下)
	chooseAll: function () {
		var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
		var $thead_choose_item = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		var $tbody_choose_item = $table_container.find("tbody .choose_item");//tbody choose_item
		var $foot_choose_item = $(fk_coupon_manage.containerName).find(".foot .choose_item");

		$thead_choose_item.removeClass("active");
		$thead_choose_item.find("img").attr("src", "img/icon_Unchecked.png");

		if ($foot_choose_item.hasClass("active")) {   //已经选中
			fk_coupon_manage.is_Choose_all_page = "0";

			$foot_choose_item.removeClass("active");
			$foot_choose_item.find("img").attr("src", "img/icon_Unchecked.png");
			$item.removeClass("active");
			$tbody_choose_item.find("img").attr("src", "img/icon_Unchecked.png")
		}
		else {
			fk_coupon_manage.is_Choose_all_page = "1";

			$foot_choose_item.addClass("active");
			$foot_choose_item.find("img").attr("src", "img/icon_checked.png");
			$item.addClass("active");
			$tbody_choose_item.find("img").attr("src", "img/icon_checked.png")
		}

	},
	//是否 已经全部选择(当前页)
	isChooseAll: function () {
		var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
		var $cur = $table_container.find("thead .choose_item");//thead choose_item
		var $item = $table_container.find("tbody .item");//tbody item
		//var $choose_item = $table_container.find(".choose_item");//table choose_item


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

		fk_coupon_manage.is_Choose_all_page = "0";//
		//移除 选择全部的选中状态
		$(fk_coupon_manage.containerName).find(".foot .choose_item").removeClass("active")
			.find("img").attr("src", "img/icon_Unchecked.png");
	},

	//福库券 新增弹框 显示
	couponAddModalShow: function () {
		fk_coupon_manage.cur_opt = "add";
		fk_coupon_manage.coupon_id = "";//

		$coupon_info_modal.modal("show");

	},
	//上传图片 - 按钮点击
	ChooseImgClick: function () {
		var $row = $coupon_info_modal.find(".modal-body > .row");
		var $img_thumb = $row.find(".img_thumb");

		if ($img_thumb.find(".upload_img")) {
			$img_thumb.find(".upload_img").remove();
		}
		//debugger
		var form = $("<form>");
		form.addClass("upload_img");
		form.attr("enctype", "multipart/form-data");
		form.appendTo($img_thumb);
		form.hide();

		var type_input = $("<input>");
		type_input.attr("type", "text");
		type_input.attr("name", "type");
		type_input.val("2");
		type_input.appendTo(form);

		var file_input = $("<input>");
		file_input.attr("type", "file");
		file_input.attr("name", "file");
		file_input.change(function () {
			fk_coupon_manage.ChooseFile(this);
		});
		file_input.appendTo(form);

		file_input.click();

	},
	//选择文件 - 弹框显示
	ChooseFile: function (self) {
		var $row = $coupon_info_modal.find(".modal-body > .row");
		var $img_thumb = $row.find(".img_thumb");

		//alert(self.files)
		if (self.files) {
			for (var i = 0; i < self.files.length; i++) {
				var file = self.files[i];

				//判断是否是图片格式
				if (/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG|BMP)$/.test(file.name)) {

					$img_thumb.find(".upload_img").ajaxSubmit({
						url: urlGroup.file_upload,
						type: 'post',
						success: function (data) {
							//alert(JSON.stringify(data))
							//console.log(data);

							if (data.code == 1000) {
								fk_coupon_manage.coupon_img_id = data.result.id;//图片 id
								var url = data.result.url;//图片URL

								$img_thumb.find(".coupon_bg").attr("src", url);

							}
							else {
								toastr.error(data.msg)
							}

						},
						error: function (error) {
							// alert(error)
							toastr.error(error);
						}
					});


				}
				else {
					toastr.error("请上传图片")
				}
			}
		}
	},
	//设置时间
	timeSet: function () {
		var $btn = $coupon_info_modal.find(".btn_timeSet");

		if ($btn.hasClass("active")) {
			$btn.removeClass("active");
			$btn.siblings(".time_container").hide();
		}
		else {
			$btn.addClass("active");
			$btn.siblings(".time_container").show();
		}

		$btn.siblings(".time_container").find("input").val("");
	},

	//福库券 编辑弹框 显示
	couponModifyModalShow: function (self) {
		fk_coupon_manage.cur_opt = "modify";

		fk_coupon_manage.coupon_id = $(self).closest(".item").attr("data-id");

		$coupon_info_modal.modal("show");


		//setTimeout(function () {
		//	fk_coupon_manage.couponDetail();//福库券 获取券 详情
		//}, 500);


	},
	//福库券 获取券 详情
	couponDetail: function () {

		if (!fk_coupon_manage.coupon_id) {
			toastr.warning("没有获取到 福库券id");
			return
		}

		var obj = {};
		obj.coupon_def_id = fk_coupon_manage.coupon_id;

		var url = urlGroup.fk_coupon_detail + "?" + jsonParseParam(obj);

		loadingInit();

		aryaGetRequest(
			url,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {

						var $item = data.result;

						var is_exported = $item.is_exported;//
						var coupon_background_url = $item.coupon_background_url ? $item.coupon_background_url : "";//
						var corp_id = $item.corp_id ? $item.corp_id : "";//
						var corp_name = $item.corp_name ? $item.corp_name : "";//
						var count = $item.count ? $item.count : "";//

						var active_time = $item.active_time ? $item.active_time : "";//
						active_time = timeInit1(active_time);
						var expire_time = $item.expire_time ? $item.expire_time : "";//
						expire_time = timeInit1(expire_time);
						var price = $item.price ? $item.price : "";//

						//console.log(coupon_background_url);

						$coupon_info_modal.find(".modal-title").html("编辑福库券");
						var $row = $coupon_info_modal.find(".modal-body > .row");

						//图片
						var $img = $row.find(".img_thumb");
						$img.find(".coupon_bg").attr("src", coupon_background_url);

						//赋值 企业id
						$row.find(".corp_list_container .corp_list")
							.find("option[value='" + corp_id + "']")
							.attr("selected", "selected");

						//赋值 福库券数量、金额
						$row.find(".coupon_count").val(count);
						$row.find(".coupon_money").val(price);

						//如果 输入了起始时间
						if (active_time || expire_time) {
							$row.find(".btn_timeSet").addClass("active");
							$row.find(".time_container").show();
							$row.find(".coupon_beginTime").val(active_time);
							$row.find(".coupon_endTime").val(expire_time);
						}

						//是否  已导出
						if (is_exported) {
							$row.find(".coupon_count").attr("disabled", "disabled");
							$row.find(".coupon_money").attr("disabled", "disabled");
							$row.find(".corp_list_container .corp_list")
								.attr("disabled", "disabled");

							$img.find(".coupon_bg").unbind("click");

						}

						//公司
						$row.find(".corp_list_container .corp_list")
							.trigger("chosen:updated");   //每次对 select操作后必须要执行 此 方法

					}


				}
				else {
					//console.log("获取日志-----error：");
					//console.log(data.msg);

					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},

	//福库券 保存
	couponSave: function () {
		if (!fk_coupon_manage.checkParamsByCouponInfo()) {
			return
		}

		var $row = $coupon_info_modal.find(".modal-body > .row");

		var corp_id = $row.find(".corp_list").val() ? $row.find(".corp_list").val()[0] : "";
		var count = $row.find(".coupon_count").val();
		var money = $row.find(".coupon_money").val();
		var begin_time = $row.find(".coupon_beginTime").val();
		begin_time = begin_time ? (new Date(begin_time).getTime()) : "";
		var end_time = $row.find(".coupon_endTime").val();
		end_time = end_time ? (new Date(end_time).getTime()) : "";

		var obj = {
			coupon_def_id: fk_coupon_manage.coupon_id,
			coupon_background_file_id: fk_coupon_manage.coupon_img_id,
			corp_id: corp_id,
			count: count,
			active_time: begin_time,
			expire_time: end_time,
			price: money
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.fk_coupon_save,
			obj,
			function (data) {
				//console.log("保存：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("保存成功！");
					$coupon_info_modal.modal("hide");

					fk_coupon_manage.initParam();//初始化 参数
					fk_coupon_manage.couponList();//初始化 福库券列表

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});

	},
	//福库券 保存（检查参数）
	checkParamsByCouponInfo: function () {
		var flag = false;
		var txt = "";

		var $row = $coupon_info_modal.find(".modal-body > .row");

		var corp_id = $row.find(".corp_list").val() ? $row.find(".corp_list").val()[0] : "";
		var count = $row.find(".coupon_count").val();
		var money = $row.find(".coupon_money").val();
		var begin_time = $row.find(".coupon_beginTime").val();
		var end_time = $row.find(".coupon_endTime").val();

		if (!fk_coupon_manage.coupon_id && !fk_coupon_manage.coupon_img_id) {
			txt = "请选择底图！";
		}
		else if (!corp_id) {
			txt = "请选择企业！";
		}
		else if (!count) {
			txt = "请输入数量！";
		}
		else if (!money) {
			txt = "请输入金额！";
		}
		else if ($row.find(".btn_timeSet").hasClass("active") && !begin_time) {
			txt = "请输入开始时间！";
		}
		else if ($row.find(".btn_timeSet").hasClass("active") && !end_time) {
			txt = "请输入结束时间！";
		}
		else if ($row.find(".btn_timeSet").hasClass("active") &&
			begin_time && end_time && new Date(begin_time) >= new Date(end_time)) {
			txt = "开始时间不能大于结束时间！";
		}
		else {
			flag = true;
		}

		if (txt) {
			toastr.warning(txt);
		}

		return flag;


	},

	//福库券 删除
	couponDel: function (self) {

		delWarning("确定要删除该条福库券吗?", function () {

			var id = $(self).closest(".item").attr("data-id");

			var obj = {
				"ids": [{"id": id}]
			};

			aryaPostRequest(
				urlGroup.fk_coupon_del,
				obj,
				function (data) {
					//alert(JSON.stringify(data));
					//console.log("删除：");
					//console.log(data);

					if (data.code == RESPONSE_OK_CODE) {

						toastr.success("删除成功！");

						fk_coupon_manage.initParam();//
						fk_coupon_manage.couponList();//

					}
					else {
						messageCue(data.msg);
					}

				},
				function (error) {
					messageCue(error);
				});

		});

		//if (fk_coupon_manage.is_Choose_all_page == "0") {		//选择了当前页 部分
		//
		//	delWarning("确定要删除当前页已选中福库券吗?", function () {
		//
		//	});
		//
		//}

		//if (fk_coupon_manage.is_Choose_all_page == "1") {		//选择了查询条件下内容
		//
		//	delWarning("确定要删除查询条件下所有福库券吗?", function () {
		//
		//	});
		//
		//}

	},

	//福库券 导出
	couponExport: function () {

		//var url_0 = "http://localhost:8083/admin/employee/prospective/detail/face?file_name=40353118ad394230a6a88d858bd1b561&bran_user_id=81e7c1b18ac84dbb9cc4fa7b5dfd1375";
		//var url_1 = "http://localhost:8083/admin/employee/prospective/manage/attachment/download?employee_ids=18626193203&type=1";
		//
		//for (var i = 0; i < 2; i++) {
		//	//var item = data.result[i];
		//
		//	var aLink = document.createElement('a');
		//	aLink.download = "胖子！";
		//	aLink.href = "http://localhost:8083/admin/employee/prospective/detail/face?file_name=40353118ad394230a6a88d858bd1b561&bran_user_id=81e7c1b18ac84dbb9cc4fa7b5dfd1375";
		//	aLink.click();
		//
		//}
		//
		//return;

		var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
		var $item = $table_container.find("tbody .item.active");

		if ($item.length <= 0) {
			toastr.warning("请先选择福库券！");
			return
		}

		exportWarning("确定要导出当前页已选中福库券吗?", function () {

			var $body = $("body");

			if ($body.find(".export_excel")) {
				$body.find(".export_excel").remove();
			}

			var form = $("<form>").addClass("export_excel");
			form.appendTo($body);
			form.attr("enctype", "multipart/form-data");
			//form.attr("action", urlGroup.fk_coupon_export);
			form.attr("method", "post");
			form.hide();

			$item.each(function () {
				var id = $(this).attr("data-id");

				var $input = $("<input>");
				$input.attr("name", "coupon_def_ids");
				$input.attr("value", id);
				$input.appendTo(form);

			});

			loadingInit();
			//console.info("福库券导出：");
			//console.log($body.find(".export_excel").serialize());

			$body.find(".export_excel").ajaxSubmit({
				url: urlGroup.fk_coupon_export,
				type: 'post',
				//dataType: 'text',
				resetForm: true,
				//dataType: 'json',
				data: $body.find(".export_excel").serialize(),
				success: function (data) {
					//console.log("成功：");
					//console.log(data);
					loadingRemove();

					if (data.code == 1000) {

						if (data.result) {

							//console.log(data);
							var url = data.result.coupon_url;
							var aLink = document.createElement('a');
							aLink.download = "";
							aLink.href = url;
							aLink.click();
							//for (var i = 0; i < urlList.length; i++) {
							//
							//	var aLink = document.createElement('a');
							//	aLink.download = "";
							//	aLink.href = urlList[i];
							//	aLink.click();
							//
							//}

							setTimeout(function () {
								fk_coupon_manage.couponList();
							}, 1000);

						}

					}
					else {
						toastr.warning(data.msg);
					}

				},
				error: function (data) {
					//console.log("失败：");
					//console.log(data);
					loadingRemove();

					toastr.error("导出失败！");
				}
			});

		});

	}

};

$(function () {
	fk_coupon_manage.Init();
});