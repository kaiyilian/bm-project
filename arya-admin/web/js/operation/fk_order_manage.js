/**
 * Created by CuiMengxin on 2016/9/7.
 * 福库订单管理
 */

var $order_modify_modal = $(".order_modify_modal");
var $order_category_info_modal = $(".order_category_info_modal");

var fk_order_manage = {
    containerName: "",
    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    current_order_category: "",//当前订单的 分类列表

    current_good_id: "",//当前订单中商品的id
    current_order_id: "",//当前订单id
    current_category_id: "",//当前选中的 分类id
    current_category_map: "",//当前分类map

    //初始化
    Init: function () {

        fk_order_manage.initParams();//初始化参数
        fk_order_manage.initGoodList();//初始化 产品名称列表
        fk_order_manage.initCorpNameList();//初始化 企业名称 列表
        fk_order_manage.initOrderStatusList();//初始化 订单状态 列表
        fk_order_manage.orderListGet();//订单查询 - 列表

    },
    //初始化 产品名称列表
    initGoodList: function () {
        var $search_container = $(fk_order_manage.containerName).find(".search_container");
        var $good_list = $search_container.find(".good_list");

        aryaGetRequest(
            urlGroup.fk_order_product_name_list,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {

                    var list = "<option value=' '>全部</option>";
                    var $goods = data.result.goods;
                    if ($goods == null || $goods.length == 0) {
                    }
                    else {
                        for (var i = 0; i < $goods.length; i++) {
                            var $item = $goods[i];
                            var id = $item.id;
                            var name = $item.name;//

                            list += "<option value='" + id + "'>" + name + "</option>"

                        }
                    }

                    $good_list.html(list);

                    $good_list.chosen({
                        allow_single_deselect: true,//选择之后 是否可以取消
                        max_selected_options: 1,//最多只能选择1个
                        width: "100%",//select框 宽度
                        no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
                    });

                    $good_list.siblings(".chosen-container").addClass("form-control")
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
    //初始化 企业 列表
    initCorpNameList: function () {
        var $search_container = $(fk_order_manage.containerName).find(".search_container");
        var $corp_list = $search_container.find(".corp_list");

        var obj = {};
        obj.biz_type = "";
        var url = urlGroup.fk_order_corp_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));

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
                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            });


    },
    //初始化 订单状态 列表
    initOrderStatusList: function () {
        var $search_container = $(fk_order_manage.containerName).find(".search_container");
        var $order_status_list = $search_container.find(".order_status_list");

        var list =
            "<option value=''>全部</option>" +
            "<option value='1'>待付款</option>" +
            "<option value='2'>已支付</option>" +
            "<option value='3'>已发货</option>" +
            "<option value='4'>已退款</option>" +
            "<option value='5'>已退货</option>" +
            "<option value='6'>已取消</option>";

        $order_status_list.html(list);

        $order_status_list.chosen({
            allow_single_deselect: true,//选择之后 是否可以取消
            max_selected_options: 1,//最多只能选择1个
            width: "100%",//select框 宽度
            no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
        });

        $order_status_list.siblings(".chosen-container").addClass("form-control")
            .css("padding", "0");


    },
    //重置搜索条件
    initSearchCondition: function () {
        fk_order_manage.initGoodList();//初始化 产品名称列表

        var $search_container = $(fk_order_manage.containerName).find(".search_container");
        $search_container.find(".beginTime").val("");
        $search_container.find(".endTime").val("");
        $search_container.find(".searchCondition").val("");

        //清除 选中的标签
        $('.chosen-select').find("option:selected").removeAttr("selected");//清空选中状态
        $('.chosen-select').trigger("chosen:updated");   //每次对 select操作后必须要执行 此 方法
    },
    //初始化参数
    initParams: function () {
        fk_order_manage.containerName = ".fk_order_manage_container";
        fk_order_manage.current_order_id = "";
        fk_order_manage.current_category_map = new Map();
        fk_order_manage.current_category_id = "";
    },

    //查询
    btnSearchClick: function () {
        fk_order_manage.currentPage = 1;

        fk_order_manage.orderListGet();//订单查询 - 列表
    },
    //订单查询 - 列表
    orderListGet: function () {
        var $search_container = $(fk_order_manage.containerName).find(".search_container");
        var beginTime = $.trim($search_container.find(".beginTime").val());
        beginTime = beginTime == "" ? "" : (new Date(beginTime).getTime());
        var endTime = $.trim($search_container.find(".endTime").val());
        endTime = endTime == "" ? "" : (new Date(endTime).getTime());
        if (beginTime != "" && endTime != "") {
            var b_time = new Date(beginTime).getTime();
            var e_time = new Date(endTime).getTime();
            if (b_time > e_time) {
                messageCue("开始时间不能大于结束时间！");
                var msg = "<tr><td colspan='10'>暂无订单信息</td></tr>";
                $(fk_order_manage.containerName).find("tbody").html(msg);
                return
            }
        }

        //console.log(typeof $search_container.find(".good_list").val());
        //console.log($search_container.find(".good_list").val());

        loadingInit();//加载框 出现

        var good_id = $search_container.find(".good_list").val() ?
            $search_container.find(".good_list").val()[0] : "";
        var corp_id = $search_container.find(".corp_list").val() ?
            $search_container.find(".corp_list").val()[0] : "";
        var order_status_id = $search_container.find(".order_status_list").val() ?
            $search_container.find(".order_status_list").val()[0] : "";
        var keyword = $.trim($search_container.find(".searchCondition").val());

        var obj = {};
        obj.goods_id = good_id;
        obj.receiver_key_word = keyword;
        obj.begin_time = beginTime;
        obj.end_time = endTime;
        obj.page = fk_order_manage.currentPage;
        obj.page_size = "10";
        obj.corp_id = corp_id;
        obj.order_status = order_status_id;
        var url = urlGroup.fk_order_list + "?" + jsonParseParam(obj);
        //alert(url)
        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    fk_order_manage.totalPage = data.result.pages;

                    var order_list = "";
                    var orders = data.result.orders;
                    if (orders == null || orders.length == 0) {
                        order_list = "<tr><td colspan='11'>暂无订单信息</td></tr>";
                    }
                    else {

                        for (var i = 0; i < orders.length; i++) {
                            var $item = orders[i];

                            var good_id = $item.goods_id;//
                            var order_id = $item.id;//
                            var order_no = $item.order_no;//
                            var order_status = $item.order_status ? $item.order_status : "";//
                            var receiver_name = $item.receiver_name;//
                            var receiver_phone = $item.receiver_phone;//
                            var receiver_address = $item.receiver_address;//
                            var good_name = $item.good_name;//
                            var good_spec_name = $item.good_spec_name;//
                            var good_count = $item.good_count;//
                            var payment = $item.payment;//

                            var pay_platform_type = $item.pay_platform_type ? $item.pay_platform_type : 0;//支付平台 0:支付宝 1：福库劵 2：钱包余额 3：钱包银行卡
                            var pay_balance = $item.pay_balance ? $item.pay_balance : "0";//福库券支付金额
                            var pay_online = $item.pay_online ? $item.pay_online : "0";//在线支付金额
                            var pay_method = "";
                            switch (pay_platform_type) {
                                case 0:
                                    pay_method = "支付宝";
                                    break;
                                case 2:
                                    pay_method = "钱包余额";
                                    break;
                                case 3:
                                    pay_method = "钱包银行卡";
                                    break;
                                default:
                                    pay_method = "支付宝";
                                    break;
                            }

                            var create_time = $item.create_time;//
                            create_time = timeInit1(create_time);

                            order_list +=
                                "<tr class='item fk_order_item' " +
                                "data-good_id='" + good_id + "' " +
                                "data-order_id='" + order_id + "' " +
                                "data-order_status='" + order_status + "' " +
                                ">" +
                                "<td class='order_no'>" + order_no + "</td>" +
                                "<td class='order_consignee'>" + receiver_name + "</td>" +
                                "<td class='order_consignee_phone'>" + receiver_phone + "</td>" +
                                "<td class='order_address'>" + receiver_address + "</td>" +
                                "<td class='order_product_name'>" + good_name + "</td>" +
                                "<td class='order_count'>" + good_count + "</td>" +
                                "<td class='order_unit'>" + good_spec_name + "</td>" +
                                "<td class='order_money'>" +
                                "<div class='pay_balance'>福库券：" + pay_balance + "</div>" +
                                "<div class='pay_online'>" + pay_method + "：" + pay_online + "</div>" +
                                "</td>" +
                                "<td class='order_create_time'>" + create_time + "</td>" +
                                "<td class='order_status'></td>" +
                                "<td class='order_operate'>" +
                                "<span class='btn btn-sm btn-primary btn_modify' >编辑</span>" +
                                "<span class='btn btn-sm btn-danger btn_del' >删除</span>" +
                                "<span class='btn btn-sm btn-default btn_refund' >退款</span>" +
                                "</td>" +
                                "</tr>";

                        }

                    }

                    $(fk_order_manage.containerName).find("tbody").html(order_list);
                    fk_order_manage.orderListInit();//
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

        var $item = $(fk_order_manage.containerName).find("tbody .item");
        var $pager_container = $(fk_order_manage.containerName).find(".pager_container");

        //判断查询 结果为空
        if ($item.length == 0) {
            $pager_container.hide();
            return
        }
        //订单列表 初始化
        $item.each(function () {
            var status = parseInt($(this).closest(".item").attr("data-order_status"));
            //已取消、待支付、已退款三种状态的订单操作中禁用退款按钮
            if (status == 1 || status == 4 || status == 6) {
                $(this).find(".btn_refund").addClass("btn-default")
                    .removeClass("btn-primary");
            }
            else {
                $(this).find(".btn_refund").addClass("btn-primary")
                    .removeClass("btn-default");
            }

            var txt = "";
            switch (status) {
                case 1:
                    txt = "待支付";
                    break;
                case 2:
                    txt = "已支付";
                    break;
                case 3:
                    txt = "已发货";
                    break;
                case 4:
                    txt = "已退款";
                    break;
                case 5:
                    txt = "已退货";
                    break;
                case 6:
                    txt = "已取消";
                    break;
                default:
                    txt = "无状态";
            }
            $(this).find(".order_status").html(txt);

            //编辑
            $(this).find(".btn_modify").click(function () {
                fk_order_manage.orderModifyModalShow(this);
            });

            //删除
            $(this).find(".btn_del").click(function () {
                fk_order_manage.orderDel(this);
            });

            //退款
            $(this).find(".btn_refund").click(function () {
                fk_order_manage.orderRefund(this);
            });


        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: fk_order_manage.currentPage, //当前页数
            totalPages: fk_order_manage.totalPage, //总页数
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

                fk_order_manage.currentPage = page;
                fk_order_manage.orderListGet();//查询 满足条件的订单

            }
        };


        $pager_container.show();
        //<ul class="pagenation" style="float:right;"></ul>
        var $ul = $("<ul>").addClass("pagenation").css("float", "right");
        $pager_container.html($ul);
        //$ul.appendTo($pager_container);
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

        fk_order_manage.IsChooseAll();//是否 已经全部选择
    },
    //选择全部
    ChooseAll: function () {
        var $choose_container = $(fk_order_manage.containerName).find(".foot .choose_container");

        if ($choose_container.hasClass("active")) {   //已经选中
            $choose_container.removeClass("active").find("img")
                .attr("src", "img/icon_Unchecked.png");
            $(fk_order_manage.containerName).find("table tbody .item").removeClass("active")
                .find("img").attr("src", "img/icon_Unchecked.png")
        }
        else {
            $choose_container.addClass("active").find("img").attr("src", "img/icon_checked.png");
            $(fk_order_manage.containerName).find("table tbody .item").addClass("active")
                .find("img").attr("src", "img/icon_checked.png")
        }

    },
    //是否 已经全部选择
    IsChooseAll: function () {
        var chooseNo = 0;//选中的个数
        var $item = $(fk_order_manage.containerName).find("tbody .item");
        for (var i = 0; i < $item.length; i++) {
            if ($item.eq(i).hasClass("active")) { //如果 是选中的
                chooseNo += 1;
            }
        }

        //没有全部选中
        if (chooseNo == 0 ||
            chooseNo < $(fk_order_manage.containerName)
                .find("tbody .item").length) {
            $(fk_order_manage.containerName).find(".choose_container").removeClass("active")
                .find("img").attr("src", "img/icon_Unchecked.png");
        }
        else {
            $(fk_order_manage.containerName).find(".choose_container").addClass("active")
                .find("img").attr("src", "img/icon_checked.png");
        }

    },

    //订单编辑 弹框显示
    orderModifyModalShow: function (self) {
        fk_order_manage.current_good_id = $(self).closest(".item").attr("data-good_id");
        fk_order_manage.current_order_id = $(self).closest(".item").attr("data-order_id");

        fk_order_manage.current_category_map = new Map();

        $order_modify_modal.modal({
            backdrop: false,
            keyboard: false
        });

        fk_order_manage.initGoodCategory();//初始化 商品对应的分类

        //根据id 获取 订单 已选择的分类
        //。。。。


        ////获取  该订单 已经选择的分类（放入数组）
        //fk_order_manage.current_order_category = [
        //	{
        //		"id": "color",
        //		"name": "",
        //		"list": [
        //			{
        //				"id": "red"
        //			}
        //		]
        //	},
        //	{
        //		"id": "size",
        //		"name": "",
        //		"list": [
        //			{
        //				"id": "size_18"
        //			}
        //		]
        //	}
        //];
        //var arr = new Array();
        //for (var i = 0; i < fk_order_manage.current_order_category.length; i++) {
        //	arr.push(fk_order_manage.current_order_category[i].id);
        //}
        //
        ////遍历所有的 分类
        //var $item = $order_modify_modal.find(".modal-body .row").find(".category_list").find(".item");
        //for (var j = 0; j < $item.length; j++) {
        //	var id = $item.eq(j).attr("data-id");
        //	//debugger
        //	if (arr.indexOf(id) > -1) {
        //		$item.eq(j).addClass("is_choose");
        //	}
        //}

    },

    //初始化 商品对应的分类
    initGoodCategory: function () {

        var url = urlGroup.fk_order_good_category + "?goods_id=" + fk_order_manage.current_good_id;
        aryaGetRequest(
            url,
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
                                "onclick='fk_order_manage.getCategoryInfo(this)'>" + name + "</span>"
                        }

                    }

                    $order_modify_modal.find(".modal-body .row").find(".category_list")
                        .html(category_list);

                    fk_order_manage.initOrderCategory();//初始化 订单对应的分类
                }
                else {
                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            });

    },
    //初始化 订单对应的分类
    initOrderCategory: function () {

        var url = urlGroup.fk_order_order_category + "?id=" + fk_order_manage.current_order_id;
        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {
                    var $category = data.result.categories;
                    //var category_list = "";
                    if ($category == null || $category.length == 0) {
                    }
                    else {

                        //获取  该订单 已经选择的分类（放入map）
                        for (var k = 0; k < $category.length; k++) {
                            var category_id = $category[k].id;
                            var value = $category[k].spec_ids;

                            fk_order_manage.current_category_map.put(category_id, value);

                        }

                        fk_order_manage.checkCategoryIsChoose();//检查 商品对应的分类 是否被选中

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
    //检查 商品对应的分类 是否被选中
    checkCategoryIsChoose: function () {

        ////如果 没有选择 任何分类（该订单）
        //if (fk_order_manage.current_category_map.keySet().length <= 0) {
        //	return;
        //}

        var $category_item = $order_modify_modal.find(".modal-body .row")
            .find(".category_list").find(".item");
        //遍历分类 是否被选中
        for (var j = 0; j < $category_item.length; j++) {
            var category_id = $category_item.eq(j).attr("data-id");

            if (fk_order_manage.current_category_map.keySet().indexOf(category_id) > -1) {
                $category_item.eq(j).addClass("is_choose");
            }
            else {
                $category_item.eq(j).removeClass("is_choose");
            }

        }

    },

    //点击具体的 分类，显示分类的 对应 规格
    getCategoryInfo: function (self) {
        fk_order_manage.current_category_id = $(self).attr("data-id");//该分类 id

        $order_category_info_modal.modal({
            backdrop: false,
            keyboard: false
        });

        //调用接口 获取对应分类的数据
        var url = urlGroup.fk_order_category_unit_list_get + "?category_id=" +
            fk_order_manage.current_category_id;
        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {

                    var $specs = data.result.specs;
                    var specs_list = "";
                    if ($specs == null || $specs.length == 0) {
                        specs_list = "暂无规格";
                    }
                    else {
                        for (var i = 0; i < $specs.length; i++) {
                            var $item = $specs[i];

                            var id = $item.id;//
                            var name = $item.name;//

                            specs_list +=
                                "<span class='item btn btn-sm btn-default' data-id='" + id + "' " +
                                "onclick='fk_order_manage.chooseUnitByCategory(this)'>" + name +
                                "</span>";
                        }

                    }

                    $order_category_info_modal.find(".modal-body .unit_list").html(specs_list);
                    fk_order_manage.checkUnitIsChoose();//检查分类中的 规格是否被选中
                }
                else {
                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            });

    },
    //检查分类中的 规格是否被选中
    checkUnitIsChoose: function () {

        //获取该分类中已经选中的 规格
        var unit_choosed = fk_order_manage.current_category_map
            .get(fk_order_manage.current_category_id);

        //如果该分类 被设置过
        if (unit_choosed) {
            //遍历所有的 分类
            var $item = $order_category_info_modal.find(".unit_list").find(".item");
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
            $(self).addClass("is_choose").siblings().removeClass("is_choose");
        }

    },
    //清空 分类中 选中的规格
    clearUnitByChoosed: function () {
        $order_category_info_modal.find(".modal-body .unit_list")
            .find(".is_choose").removeClass("is_choose");
    },
    //保存规格 （对应分类中的）
    unitSave: function () {

        var choose_item = $order_category_info_modal.find(".unit_list").find(".is_choose");

        //重新赋值 map
        var list = [];
        for (var i = 0; i < choose_item.length; i++) {
            var $item = choose_item.eq(i);
            var id = $item.attr("data-id");
            list.push(id);
        }
        if (list.length > 0) {
            fk_order_manage.current_category_map.put(fk_order_manage.current_category_id, list);
        }
        else {
            fk_order_manage.current_category_map.remove(fk_order_manage.current_category_id);
        }

        $order_category_info_modal.modal("hide");

        fk_order_manage.checkCategoryIsChoose();//检查 商品对应的分类 是否被选中

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

    //订单编辑后 保存
    orderSaveByModify: function () {

        if ($order_modify_modal.find(".category_list .is_choose").length <
            $order_modify_modal.find(".category_list .item").length) {
            msgShow("请选择产品分类");
            return
        }

        var obj = new Object();
        obj.order_id = fk_order_manage.current_order_id;
        obj.categories = fk_order_manage.mapToObj(fk_order_manage.current_category_map);

        //alert(JSON.stringify(obj));

        aryaPostRequest(
            urlGroup.fk_order_modify_save,
            obj,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    $order_modify_modal.modal("hide");

                    fk_order_manage.initParams();//初始化参数
                    fk_order_manage.orderListGet();//
                }
                else {
                    messageCue(data.msg);
                }

            },
            function (error) {
                messageCue(error);
            });
    },

    //订单删除
    orderDel: function (self) {

        delWarning("订单", function () {
            var id = $(self).closest(".item").attr("data-order_id");
            //alert(id);

            var obj = new Object();
            obj.id = id;

            aryaPostRequest(
                urlGroup.fk_order_del,
                obj,
                function (data) {
                    //alert(JSON.stringify(data));

                    if (data.code == RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");

                        fk_order_manage.initParams();//初始化参数
                        fk_order_manage.orderListGet();//
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

    //订单退款
    orderRefund: function (self) {
        var $item = $(self).closest(".item");
        var status = $item.attr("data-order_status");

        //已取消、待支付、已退款三种状态的订单操作中禁用退款按钮
        if (status == 1 || status == 4 || status == 6) {
            toastr.warning("无法退款！");
            return
        }

        swal({
            title: "确认要退款吗？",
            text: $item.find(".order_money").html(),
            html: true,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#1c84c6",
            confirmButtonText: "确定",
            closeOnConfirm: true
        }, function () {

            loadingInit();

            var obj = {
                order_id: $item.attr("data-order_id")
            };

            aryaPostRequest(
                urlGroup.fk_order_refund,
                obj,
                function (data) {
                    //alert(JSON.stringify(data));

                    if (data.code == RESPONSE_OK_CODE) {

                        toastr.success("退款成功！");
                        fk_order_manage.initParams();//初始化参数
                        fk_order_manage.orderListGet();//订单列表 获取

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

    //订单导出
    orderExport: function () {
        /*
         page_id 判断是下载当前页 还是 所有页面
         1 当前页
         0 所有页面
         */
        if ($(fk_order_manage.containerName).find("tbody .item").length <= 0) {
            toastr.warning("暂无订单信息可导出");
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
            form.attr("action", urlGroup.fk_order_export);
            form.attr("method", "get");
            form.hide();

            var $search_container = $(fk_order_manage.containerName).find(".search_container");
            //var val=$("select").val()?$("select").val()[0]:"";
            var goods_id = $search_container.find(".good_list").val() ?
                $search_container.find(".good_list").val()[0] : "";

            var corp_id = $search_container.find(".corp_list").val() ?
                $search_container.find(".corp_list").val()[0] : "";

            var status = $search_container.find(".order_status_list").val() ?
                $search_container.find(".order_status_list").val()[0] : "";

            var receiver_key_word = $.trim($search_container.find(".searchCondition").val());
            var begin_time = $.trim($search_container.find(".beginTime").val());
            begin_time = begin_time == "" ? "" : (new Date(begin_time).getTime());
            var end_time = $.trim($search_container.find(".endTime").val());
            end_time = end_time == "" ? "" : (new Date(end_time).getTime());

            form.append($("<input>").attr("name", "goods_id").attr("value", goods_id));
            form.append($("<input>").attr("name", "corp_id").attr("value", corp_id));
            form.append($("<input>").attr("name", "order_status").attr("value", status));
            form.append($("<input>").attr("name", "receiver_key_word").attr("value", receiver_key_word));
            form.append($("<input>").attr("name", "begin_time").attr("value", begin_time));
            form.append($("<input>").attr("name", "end_time").attr("value", end_time));

            form.submit();

        });

    },

    //签收单 导出
    receiptFormExport: function () {
        /*
         page_id 判断是下载当前页 还是 所有页面
         1 当前页
         0 所有页面
         */
        if ($(fk_order_manage.containerName).find("tbody .item").length <= 0) {
            messageCue("暂无订单信息可导出");
            return;
        }

        exportWarning("全部订单的签收单", function () {

            var $body = $("body");

            if ($body.find(".export_excel")) {
                $body.find(".export_excel").remove();
            }

            var form = $("<form>").addClass("export_excel");
            form.appendTo($body);
            form.attr("enctype", "multipart/form-data");
            form.attr("action", urlGroup.fk_receipt_form_export);
            form.attr("method", "get");
            form.hide();

            var $search_container = $(fk_order_manage.containerName).find(".search_container");

            var goods_id = $search_container.find(".good_list").val() ?
                $search_container.find(".good_list").val()[0] : "";

            var corp_id = $search_container.find(".corp_list").val() ?
                $search_container.find(".corp_list").val()[0] : "";

            var status = $search_container.find(".order_status_list").val() ?
                $search_container.find(".order_status_list").val()[0] : "";

            var receiver_key_word = $.trim($search_container.find(".searchCondition").val());
            var begin_time = $.trim($search_container.find(".beginTime").val());
            begin_time = begin_time == "" ? "" : (new Date(begin_time).getTime());
            var end_time = $.trim($search_container.find(".endTime").val());
            end_time = end_time == "" ? "" : (new Date(end_time).getTime());

            form.append($("<input>").attr("name", "goods_id").attr("value", goods_id));
            form.append($("<input>").attr("name", "corp_id").attr("value", corp_id));
            form.append($("<input>").attr("name", "order_status").attr("value", status));
            form.append($("<input>").attr("name", "receiver_key_word").attr("value", receiver_key_word));
            form.append($("<input>").attr("name", "begin_time").attr("value", begin_time));
            form.append($("<input>").attr("name", "end_time").attr("value", end_time));

            form.submit();

        });

    }


};

$(function () {
    fk_order_manage.Init();
});