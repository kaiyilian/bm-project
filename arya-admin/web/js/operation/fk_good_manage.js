/**
 * Created by CuiMengxin on 2016/9/7.
 * 福库商品管理
 */

var $good_info_modal = $(".good_info_modal");//商品信息 弹框
var $category_info_modal = $(".category_info_modal");//分类信息 弹框

var fk_good_manage = {
	containerName: "",
	currentPage: "1",//当前页面
	totalPage: "10",//总页面
	current_good_id: "",//当前商品id
	current_good_category_map: "",//当前分类map
	current_category_id: "",//当前选中的 分类id
	shuffling_img_idList: "",//当前轮播图 id数组
	activity_begin_time: 0,//活动 开始时间
	activity_end_time: 0,//活动 结束时间
	//current_good_category: "",//当前商品的 分类列表

	//初始化
	Init: function () {

		fk_good_manage.initParams();//初始化参数

		//商品信息 弹框显示
		$good_info_modal.on("show.bs.modal", function () {

			$good_info_modal.find(".modal-title").html("新增商品信息");

			var $row = $good_info_modal.find(".modal-body .row");

			//缩略图
			var img = $("<img>").attr("src", "img/img_add_default.png").addClass("icon_add_default");
			var label = $("<label>").addClass("thumbnail");//.attr("for", "img_upload");
			img.appendTo(label);
			label.click(function () {
				//当前点击的label增加class
				$good_info_modal.find("label.is_upload").removeClass("is_upload");
				$(this).addClass("is_upload");

				//判断input[type='file']是否存在
				var $npt = $good_info_modal.find(".modal-body").find(".img_upload");
				if ($npt.length > 0) {
					$npt.remove();
				}

				//
				var npt = $("<input>").attr({
					"id": "img_upload",
					"type": "file"
				}).addClass("img_upload").hide().change(function () {
					fk_good_manage.ChooseImg(this);
				});
				npt.appendTo($good_info_modal.find(".modal-body"));

				npt.click();

			});

			$row.find(".thumb_img").empty().html(label);
			//轮播图
			$row.find(".shuffling_img_list").empty();
			for (var i = 0; i < 4; i++) {
				var shuffling_img = $("<img>").attr("src", "img/img_add_default.png").addClass("icon_add_default");
				var shuffling_label = $("<label>").addClass("thumbnail").addClass("col-xs-6");
				shuffling_img.appendTo(shuffling_label);
				shuffling_label.click(function () {
					//当前点击的label增加class
					$good_info_modal.find("label.is_upload").removeClass("is_upload");
					$(this).addClass("is_upload");

					//判断input[type='file']是否存在
					var $npt = $good_info_modal.find(".modal-body").find(".img_upload");
					if ($npt.length > 0) {
						$npt.remove();
					}

					//
					var npt = $("<input>").attr({
						"id": "img_upload",
						"type": "file"
					}).addClass("img_upload").hide().change(function () {
						fk_good_manage.ChooseImg(this);
					});
					npt.appendTo($good_info_modal.find(".modal-body"));

					npt.click();

				});

				shuffling_label.appendTo($row.find(".shuffling_img_list"));

			}
			//商品名称
			$row.find(".good_name").val("");
			//品牌名称
			$row.find(".good_brand").val("");
			//活动价
			$row.find(".good_deal_price").val("");
			//市场价
			$row.find(".good_marketed_price").val("");
			//库存
			$row.find(".good_stock").val("");
			$row.find(".good_stock").removeAttr("disabled");
			//最大购买量
			$row.find(".good_buy_limit").val("");
			//发货时间
			$row.find(".delivery_time_desc").val("");
			//分类
			$row.find(".category_list").empty();
			fk_good_manage.initCategory();//初始化 分类列表

			//活动详情
			$row.find(".good_info_detail").val("");
			//是否启用
			$row.find(".good_is_enable").find("input[type='checkbox']").prop("checked", false);

		});

		//商品信息 弹框隐藏
		$good_info_modal.on("hidden.bs.modal", function () {
			fk_good_manage.initParams();
		});

		fk_good_manage.goodListGet();//

	},
	//初始化参数
	initParams: function () {
		fk_good_manage.containerName = ".fk_good_manage_container";
		fk_good_manage.current_good_id = "";
		fk_good_manage.current_category_id = "";
		fk_good_manage.current_good_category_map = new Map();
	},
	//初始化 分类列表
	initCategory: function () {

		aryaGetRequest(
			urlGroup.fk_good_category_list_get,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					var $category = data.result.categories;
					var category_list = "";
					if ($category == null || $category.length == 0) {
						category_list = "暂无分类";
					}
					else {
						for (var i = 0; i < $category.length; i++) {
							var $item = $category[i];

							var id = $item.id;//
							var name = $item.name;//

							category_list +=
								"<span class='item btn btn-sm btn-default' data-id='" + id + "' " +
								"onclick='fk_good_manage.getCategoryInfo(this)'>" + name + "</span>"
						}

						//category_list =
						//	"<span class='item btn btn-sm btn-default' data-id='color' " +
						//	"onclick='fk_good_manage.getCategoryInfo(this)'>颜色</span>" +
						//	"<span class='item btn btn-sm btn-default' data-id='size' " +
						//	"onclick='fk_good_manage.getCategoryInfo(this)'>尺寸</span>" +
						//	"<span class='item btn btn-sm btn-default' data-id='unit' " +
						//	"onclick='fk_good_manage.getCategoryInfo(this)'>规格</span>";
					}


					$good_info_modal.find(".modal-body .row").find(".category_list").html(category_list);
				}
				else {
					messageCue(data.msg);
				}
			}, function (error) {
				messageCue(error);
			});

	},
	//选择图片
	ChooseImg: function (self) {
		//debugger
		if (self.files) {
			for (var i = 0; i < self.files.length; i++) {
				var file = self.files[i];
				//判断是否是图片格式
				if (/\.(jpg|png|JPG|PNG)$/.test(file.name)) {

					if (file.size > 2 * 1024 * 1024) {

						alert('您所选择的档案大小超过了上传上限 2M！\n不允许您上传喔！');

					}
					else {
						fk_good_manage.UploadImg(file.name, file);
					}
				}
				else {
					msgShow("请上传jpg、png格式的图片");
				}
			}
		}
	},
	//调用接口 上传文件
	UploadImg: function (filename, fileContent) {
		var formData = new FormData();
		formData.append("file", fileContent);//图片文件

		$.ajax({
			type: "POST",
			url: urlGroup.fk_good_upload_img_url, //FileUploads
			data: formData,
			processData: false,
			contentType: false,
			success: function (data) {
				//alert(JSON.stringify(data));

				var id = data.result.id;//

				$good_info_modal.find("label.is_upload").attr("data-id", id);

				var url = null;
				if (window.createObjectURL != undefined) {
					url = window.createObjectURL(fileContent)
				}
				else if (window.URL != undefined) {
					url = window.URL.createObjectURL(fileContent)
				}
				else if (window.webkitURL != undefined) {
					url = window.webkitURL.createObjectURL(fileContent)
				}

				$good_info_modal.find("label.is_upload").find("img").attr("src", url);

				//alert($good_info_modal.find("label.is_upload").html())

			},
			error: function (msg) {
				messageCue(msg.status);
				//if (confirm("系统故障,请重新登陆")) {
				//	location.href = "/Login.html";
				//}
			}
		});
	},
	//map转换为数组
	mapToObj: function (map) {

		var list = [];
		for (var i = 0; i < map.keySet().length; i++) {
			var key = map.keySet()[i];
			var value = map.get(key);
			//alert(typeof value)

			var obj = new Object();
			obj.id = key;
			obj.spec_ids = value;
			list.push(obj);

			//list += list == ""
			//	?
			//"{" +
			//"'id':'" + key + "'" + "," +
			//"'spec_ids':'" + value + "'" +
			//"}"
			//	:
			//",{" +
			//"'id':'" + key + "'" + "," +
			//"'spec_ids':'" + value + "'" +
			//"}"

		}

		//list = "[" + list + "]";
		//list = eval("(" + list + ")");

		return list;

	},

	//订单查询 - 列表
	goodListGet: function () {
		var $table_container = $(fk_good_manage.containerName).find(".table_container");
		var $table = $table_container.find("table");

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.page = fk_good_manage.currentPage;
		obj.page_size = "10";
		var url = urlGroup.fk_good_list_get + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					fk_good_manage.totalPage = data.result.pages;//总页数
					fk_good_manage.activity_begin_time = data.result.begin_time;//活动开始时间
					fk_good_manage.activity_end_time = data.result.end_time;//活动结束时间

					var $goods = data.result.goods;//
					var good_list = "";
					if ($goods == null || $goods.length == 0) {
						good_list = "<tr><td colspan='8'>暂无商品信息</td></tr>";
					}
					else {
						for (var i = 0; i < $goods.length; i++) {
							var $item = $goods[i];

							var id = $item.id;//
							var thumb_url = $item.thumb_url;//
							var shuffling_img_url = $item.shuffling_img_url;//
							var goods_name = $item.goods_name;//
							var deal_price = $item.deal_price;//
							var marked_price = $item.marked_price;//
							var goods_detail = $item.goods_detail;//
							var on_sale = $item.on_sale;//0 下架 1 上架

							good_list +=
								"<tr class='item fk_good_item' data-id='" + id + "'>" +
								"<td class='good_thumb_img'>" +
								"<img src='" + thumb_url + "'>" +
								"</td>" +
								"<td class='good_shuffling_img'>" +
								"<img src='" + shuffling_img_url + "'>" +
								"</td>" +
								"<td class='good_name'>" + goods_name + "</td>" +
								"<td class='good_deal_price' data-price='" + deal_price + "'>￥" + deal_price + "</td>" +
								"<td class='good_marketed_price' data-price='" + marked_price + "'>￥" + marked_price + "</td>" +
								"<td class='good_detail'>" + goods_detail + "</td>" +
								"<td class='good_is_enable' data-is_sale='" + on_sale + "'>已上架</td>" +
								"<td class='operate'>" +
								"<div class='btn btn-sm btn-primary btn_up' onclick='fk_good_manage.goodGoUp(this)'>上</div>" +
								"<div class='btn btn-sm btn-primary btn_down' onclick='fk_good_manage.goodGoDown(this)'>下</div>" +
								"<div class='btn btn-sm btn-primary btn_modify' onclick='fk_good_manage.goodModifyModalShow(this)'>编辑</div>" +
								"<div class='btn btn-sm btn-danger btn_del' onclick='fk_good_manage.goodDel(this)'>删除</div>" +
								"</td>" +
								"</tr>"

						}
					}

					$table.find("tbody").html(good_list);
					fk_good_manage.goodListInit();//

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
	goodListInit: function () {

		var $item = $(fk_good_manage.containerName).find("tbody .item");
		var $pager_container = $(fk_good_manage.containerName).find(".pager_container");

		//判断查询 结果为空
		if ($item.length == 0) {
			$pager_container.hide();
			return
		}
		//商品列表 初始化
		$item.each(function () {

			var is_sale = $(this).find(".good_is_enable").attr("data-is_sale");////0 下架 1 上架
			if (is_sale == 0) {
				$(this).find(".good_is_enable").html("已下架");
			}
			else {
				$(this).find(".good_is_enable").html("已上架");
			}

			//第一页 第一行
			if ($(this).index() == 0 && fk_good_manage.currentPage == 1) {
				$(this).find(".btn_up").remove();
			}

			//最后一页 最后一行
			if ($(this).index() == ($item.length - 1) &&
				fk_good_manage.currentPage == fk_good_manage.totalPage) {
				$(this).find(".btn_down").remove();
			}

		});

		var options = {
			bootstrapMajorVersion: 3, //版本  3是ul  2 是div
			//containerClass:"sdfsaf",
			//size: "small",//大小
			alignment: "right",//对齐方式
			currentPage: fk_good_manage.currentPage, //当前页数
			totalPages: fk_good_manage.totalPage, //总页数
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

				fk_good_manage.currentPage = page;
				fk_good_manage.goodListGet();//查询 满足条件的订单

			}
		};


		$pager_container.show();
		var $ul = $("<ul>").addClass("pagenation").css("float", "right");
		$pager_container.html($ul);
		$pager_container.find(".pagenation").bootstrapPaginator(options);
	},

	//商品新增 弹框显示
	goodAddModalShow: function () {
		$good_info_modal.modal("show");
		//$good_info_modal.modal({
		//	backdrop: false,
		//	keyboard: false
		//});
	},

	//商品编辑 弹框显示
	goodModifyModalShow: function (self) {
		fk_good_manage.current_good_id = $(self).closest(".item").attr("data-id");//商品id

		fk_good_manage.current_good_category_map = new Map();

		//弹框显示
		$good_info_modal.modal("show");
		//$good_info_modal.modal({
		//	backdrop: false,
		//	keyboard: false
		//});

		//获取商品详情
		var url = urlGroup.fk_good_detail_get + "?id=" + fk_good_manage.current_good_id;
		//alert(url);
		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					var $detail = data.result;
					//debugger
					var thumb = $detail.thumb;//
					var thumb_img_id = "";
					var thumb_img_url = "";
					if (thumb) {
						thumb_img_id = thumb.id;
						thumb_img_url = thumb.url;
					}
					var images = $detail.images;//缩略图 数组
					var goods_name = $detail.goods_name;//
					var brand = $detail.brand;//
					var deal_price = $detail.deal_price;//活动价
					var marked_price = $detail.marked_price;//市场价
					var inventory_count = $detail.inventory_count;//库存
					var buy_limit = $detail.buy_limit ? $detail.buy_limit : "";//最大 购买量
					var delivery_time_desc = $detail.delivery_time_desc;//发货时间
					var desc = $detail.desc;//
					var on_sale = $detail.on_sale ? true : false;//'是否上架', 1是，0不是
					var exist_categories = $detail.exist_categories;

					$good_info_modal.find(".modal-title").html("编辑商品信息");

					var $row = $good_info_modal.find(".modal-body .row");

					//缩略图
					$row.find(".thumb_img").find(".thumbnail").attr("data-id", thumb_img_id)
						.find("img").attr("src", thumb_img_url);
					//轮播图 列表
					for (var i = 0; i < images.length; i++) {
						var img_item = images[i];

						var img_id = img_item.id;//
						var url = img_item.url;//

						$row.find(".shuffling_img_list").find(".thumbnail").eq(i)
							.attr("data-id", img_id)
							.find("img").attr("src", url);

					}
					//商品名称
					$row.find(".good_name").val(goods_name);
					//品牌名称
					$row.find(".good_brand").val(brand);
					//活动价
					$row.find(".good_deal_price").val(deal_price);
					//市场价
					$row.find(".good_marketed_price").val(marked_price);
					//库存
					$row.find(".good_stock").val(inventory_count);
					//最大购买量
					$row.find(".good_buy_limit").val(buy_limit);
					//库存是否 可编辑
					var now = new Date().getTime();
					if (now < fk_good_manage.activity_end_time &&
						now > fk_good_manage.activity_begin_time &&
						on_sale) {
						$row.find(".good_stock").attr("disabled", "disabled");
					}
					else {
						$row.find(".good_stock").removeAttr("disabled");
					}
					//发货时间
					$row.find(".delivery_time_desc").val(delivery_time_desc);
					//活动详情
					$row.find(".good_info_detail").val(desc);
					//是否启用
					$row.find(".good_is_enable").find("input[type='checkbox']")
						.prop("checked", on_sale);


					//获取 该商品 已经选择的分类（放入map）
					for (var k = 0; k < exist_categories.length; k++) {
						var category_id = exist_categories[k].id;
						var value = exist_categories[k].spec_ids;

						fk_good_manage.current_good_category_map.put(category_id, value);

					}

					fk_good_manage.checkCategoryIsChoose();//检查 分类 是否被选中

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},

	//检查 分类 是否被选中
	checkCategoryIsChoose: function () {

		var $category_item = $good_info_modal.find(".modal-body .row")
			.find(".category_list").find(".item");
		//遍历分类 是否被选中
		for (var j = 0; j < $category_item.length; j++) {
			var category_id = $category_item.eq(j).attr("data-id");

			if (fk_good_manage.current_good_category_map.keySet().indexOf(category_id) > -1) {
				$category_item.eq(j).addClass("is_choose");
			}
			else {
				$category_item.eq(j).removeClass("is_choose");
			}

		}

	},

	//点击具体的 分类，显示分类的 对应规格
	getCategoryInfo: function (self) {
		fk_good_manage.current_category_id = $(self).attr("data-id");//该分类 id

		$category_info_modal.modal({
			backdrop: false,
			keyboard: false
		});

		//调用接口 获取对应分类的数据
		var url = urlGroup.fk_good_category_unit_list_get +
			"?id=" + fk_good_manage.current_category_id;
		aryaGetRequest(
			url,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {

					var $unit = data.result.specs;
					var list = "";
					if ($unit == null || $unit.length == 0) {
						list = "暂无规格";
					}
					else {
						for (var i = 0; i < $unit.length; i++) {
							var $item = $unit[i];

							var id = $item.id;//
							var name = $item.name;//

							list +=
								"<span class='item btn btn-sm btn-default' data-id='" + id + "' " +
								"onclick='fk_good_manage.chooseUnitByCategory(this)'>" + name + "</span>"
						}

					}

					$category_info_modal.find(".modal-body .unit_list").html(list);

					fk_good_manage.checkUnitIsChoose();//检查分类中的 规格 是否被选择过

				}
				else {
					messageCue(data.msg);
				}
			},
			function (error) {
				messageCue(error);
			});

	},
	//检查分类中的 规格 是否被选中
	checkUnitIsChoose: function () {

		//if()

		//获取该分类中已经选中的 规格
		var unit_choosed = fk_good_manage.current_good_category_map
			.get(fk_good_manage.current_category_id);

		//alert(unit_choosed)

		//如果该分类 被设置过
		if (unit_choosed) {
			//遍历所有的 分类
			var $item = $category_info_modal.find(".unit_list").find(".item");
			for (var k = 0; k < $item.length; k++) {
				var unit_id = $item.eq(k).attr("data-id");
				//debugger
				if (unit_choosed.indexOf(unit_id) > -1) {
					$item.eq(k).addClass("is_choose")
				}
			}
		}
	},

	//选择 分类中的具体的 规格
	chooseUnitByCategory: function (self) {

		if ($(self).hasClass("is_choose")) {
			$(self).removeClass("is_choose");
		}
		else {
			$(self).addClass("is_choose");
		}

	},
	//清空 分类中 选中的规格
	clearUnitByChoosed: function () {
		$(".category_info_modal .modal-body .unit_list").find(".is_choose").removeClass("is_choose");
	},
	//保存规格 （对应分类中的）
	unitSave: function () {

		var choose_item = $category_info_modal.find(".unit_list").find(".is_choose");

		//重新赋值 map
		var list = [];
		for (var i = 0; i < choose_item.length; i++) {
			var $item = choose_item.eq(i);
			var id = $item.attr("data-id");
			list.push(id);
		}
		if (list.length > 0) {
			fk_good_manage.current_good_category_map.put(fk_good_manage.current_category_id, list);
		}
		else {
			fk_good_manage.current_good_category_map.remove(fk_good_manage.current_category_id);
		}
		//alert(fk_good_manage.current_good_category_map.toString());
		//alert(fk_good_manage.current_good_category_map.keySet())

		$category_info_modal.modal("hide");

		fk_good_manage.checkCategoryIsChoose();//检查 分类 是否被选中


	},

	//检查 库存输入是否正确
	checkGoodStock: function (self) {
		var val = $.trim($(self).val());

		if (val > 999999) {
			$(self).val("999999");
		}
		if (val < 0) {
			$(self).val("0");
		}
	},
	//检查 最大购买量 输入是否正确
	checkGoodBuyCount: function (self) {
		//var $row = $good_info_modal.find(".modal-body > .row");
		var val = $.trim($(self).val());
		//var stock = $.trim($row.find(".good_stock").val());

		if (val > 999999) {
			$(self).val("999999");
		}
		if (val < 0) {
			$(self).val("0");
		}
		////验证最大购买量 是否超过库存
		//if (stock && stock < max_count) {
		//	$(self).val(stock);
		//}
	},

	//商品信息 保存（新增或修改）
	goodInfoSave: function () {
		if (!fk_good_manage.checkParamsIsRight()) {
			return;
		}

		var $row = $good_info_modal.find(".modal-body .row");


		var obj = new Object();
		obj.id = fk_good_manage.current_good_id;
		obj.thumb_id = $row.find(".thumb_img .thumbnail").attr("data-id");
		obj.image_ids = fk_good_manage.shuffling_img_idList;
		obj.goods_name = $row.find(".good_name").val();
		obj.deal_price = $row.find(".good_deal_price").val();
		obj.marked_price = $row.find(".good_marketed_price").val();
		obj.inventory_count = $row.find(".good_stock").val();
		obj.buy_limit = $row.find(".good_buy_limit").val();
		obj.delivery_time_desc = $row.find(".delivery_time_desc").val();
		obj.brand = $row.find(".good_brand").val();
		obj.desc = $row.find(".good_info_detail").val();
		obj.on_sale = $row.find(".good_is_enable input[type='checkbox']").is(":checked") ? "1" : "0";
		obj.categories = fk_good_manage.mapToObj(fk_good_manage.current_good_category_map);

		//alert(JSON.stringify(obj));

		aryaPostRequest(
			urlGroup.fk_good_save_by_add_or_modify,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("保存成功！");
					$good_info_modal.modal("hide");

					fk_good_manage.goodListGet();
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			});
	},
	//检查输入参数是否 正确
	checkParamsIsRight: function () {
		var flag = false;
		var txt = "";

		var $row = $good_info_modal.find(".modal-body .row");
		var goods_name = $.trim($row.find(".good_name").val());
		var deal_price = $.trim($row.find(".good_deal_price").val());
		var marked_price = $.trim($row.find(".good_marketed_price").val());
		var good_stock = $.trim($row.find(".good_stock").val());
		var good_buy_limit = $.trim($row.find(".good_buy_limit").val());
		var delivery_time_desc = $.trim($row.find(".delivery_time_desc").val());
		var brand = $.trim($row.find(".good_brand").val());
		var desc = $.trim($row.find(".good_info_detail").val());
		fk_good_manage.shuffling_img_idList = [];
		for (var i = 0; i < $row.find(".shuffling_img_list .thumbnail").length; i++) {
			var $item = $row.find(".shuffling_img_list .thumbnail").eq(i);
			var id = $item.attr("data-id");
			//if (!id)id = "";
			if (id)
				fk_good_manage.shuffling_img_idList.push(id);
		}

		if (!$row.find(".thumb_img .thumbnail").attr("data-id")) {
			txt = "缩略图不能为空！";
		}
		else if (fk_good_manage.shuffling_img_idList.length < 1) {
			txt = "轮播图至少要上传一张";
		}
		else if (goods_name == "") {
			txt = "商品名称不能为空！";
		}
		else if (brand == "") {
			txt = "品牌不能为空！";
		}
		else if (deal_price == "") {
			txt = "活动价不能为空！";
		}
		else if (marked_price == "") {
			txt = "市场价不能为空！";
		}
		else if (good_stock == "") {
			txt = "库存不能为空！";
		}
		else if (good_buy_limit == "") {
			txt = "最大购买量不能为空！";
		}
		else if (desc == "") {
			txt = "商品详情不能为空！";
		}
		else if (isNaN(deal_price)) {
			txt = "活动价请输入数字！";
		}
		else if (isNaN(marked_price)) {
			txt = "市场价请输入数字！";
		}
		else if (deal_price == "0") {
			txt = "活动价不能为0！";
		}
		else if (marked_price == "0") {
			txt = "市场价不能为0！";
		}
		else if (parseFloat(deal_price) > parseFloat(marked_price)) {
			txt = "活动价不能高于市场价！";
		}
		else if (deal_price.split(".")[1] && deal_price.split(".")[1].length > 2) {
			txt = "活动价小数点后只能有两位！";
		}
		else if (marked_price.split(".")[1] && marked_price.split(".")[1].length > 2) {
			txt = "市场价小数点后只能有两位！";
		}
		else if (parseFloat(deal_price) >= 100000000) {
			txt = "活动价不能高于一亿！";
		}
		else if (parseFloat(marked_price) >= 100000000) {
			txt = "市场价不能高于一亿！";
		}
		else {
			flag = true;
		}


		if (txt != "") {
			messageCue(txt);
		}
		return flag;

	},

	//删除 弹框确认
	goodDel: function (self) {
		var name = $(self).closest(".item").find(".good_name").html();

		delWarning(name, function () {
			loadingInit();//加载框 出现

			fk_good_manage.current_good_id = $(self).closest(".item").attr("data-id");

			var obj = new Object();
			obj.goods_id = fk_good_manage.current_good_id;

			aryaPostRequest(
				urlGroup.fk_good_del,
				obj,
				function (data) {
					//alert(JSON.stringify(data));

					if (data.code == RESPONSE_OK_CODE) {
						toastr.success("删除成功！");

						fk_good_manage.initParams();//初始化参数
						fk_good_manage.goodListGet();//

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

	//下移一位
	goodGoDown: function (self) {
		var id = $(self).closest(".item").attr("data-id");

		fk_good_manage.DirectionMove(id, 2); //移动位置后 调用接口
	},
	//上移一位
	goodGoUp: function (self) {
		var id = $(self).closest(".item").attr("data-id");

		fk_good_manage.DirectionMove(id, 1); //移动位置后 调用接口
	},
	//移动位置
	DirectionMove: function (id, direction) {
		var obj = {
			goods_id: id,
			direction: direction
		};

		loadingInit();

		aryaPostRequest(
			urlGroup.fk_good_direction,
			obj,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					toastr.success("移动成功！");
					fk_good_manage.goodListGet();//获取列表

				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			}
		);

	}


};

$(function () {
	fk_good_manage.Init();
});
