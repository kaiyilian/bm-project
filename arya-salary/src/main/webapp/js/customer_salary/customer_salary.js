/**
 * Created by Administrator on 2017/6/13.
 */

var $customer_salary_container = $(".customer_salary_container");

var customer_salary = {

    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    status_map: null,//处理状态
    userIds: [],//用户id数字

    init: function () {

        //初始化 处理状态
        customer_salary.initStatus();
        //获取 用户列表
        customer_salary.userList();

    },
    //初始化 处理状态
    initStatus: function () {
        customer_salary.status_map = new Map();

        customer_salary.status_map.put("0", "");
        customer_salary.status_map.put("1", "已处理");
    },

    //查询
    btnSearchClick: function () {
        customer_salary.currentPage = "1";

        customer_salary.userList();//获取 用户列表
    },
    //清空 用户列表
    clearUserList: function () {
        var $tbody = $customer_salary_container.find(".table_container table tbody");

        var msg = "<tr><td colspan='7'>暂无需求信息</td></tr>";
        $tbody.html(msg);
    },
    //获取 用户列表
    userList: function () {

        //清空 用户列表
        customer_salary.clearUserList();

        var $table_container = $customer_salary_container.find(".table_container");
        var $search_container = $customer_salary_container.find(".search_container");

        var obj = {
            key_word: $search_container.find(".searchCondition").val(),
            page: customer_salary.currentPage,
            page_size: "10"
        };

        var url = urlGroup.customer_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {

                    customer_salary.totalPage = data.result.pages ? data.result.pages : 1;//总页数
                    if (customer_salary.currentPage > customer_salary.totalPage) {
                        customer_salary.currentPage -= 1;
                        customer_salary.userList();
                        return;
                    }

                    var list = "";
                    var arr = data.result.result;
                    if (!arr || arr.length === 0) {
                    }
                    else {
                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var corpName = $item.corpName ? $item.corpName : "";//
                            var userName = $item.userName ? $item.userName : "";//
                            var tel = $item.tel ? $item.tel : "";//
                            var location = $item.location ? $item.location : "";//
                            var status = $item.state ? $item.state : "0";//
                            var state_msg = customer_salary.status_map.get(status);

                            list +=
                                "<tr class='item' " +
                                "data-id='" + id + "' " +
                                "data-status='" + status + "' " +
                                ">" +
                                "<td class='choose_item' onclick='customer_salary.chooseItem(this)'>" +
                                "<img src='img/icon_Unchecked.png'/>" +
                                "</td>" +
                                "<td class='corpName'>" + corpName + "</td>" +
                                "<td class='contact_name'>" + userName + "</td>" +
                                "<td class='contact_phone'>" + tel + "</td>" +
                                "<td class='corp_area'>" + location + "</td>" +
                                "<td class='status'>" + state_msg + "</td>" +
                                "<td class='operate'>" +
                                "<div class='btn btn-sm btn-primary btn_deal'>确认处理</div>" +
                                "<div class='btn btn-sm btn-primary btn_del'>删除</div>" +
                                "</td>" +
                                "</tr>"

                        }

                        $table_container.find("tbody").html(list);

                    }

                    customer_salary.userListInit();	//列表初始化

                }
                else {
                    //console.log("获取日志-----error：");
                    //console.log(data.msg);

                    toastr.warning(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },
    //用户列表 初始化
    userListInit: function () {
        var $table_container = $customer_salary_container.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $customer_salary_container.find(".pager_container");

        if ($item.length == 0) {
            $pager_container.hide();
            return
        }

        $item.each(function () {
            var $this = $(this);
            var id = $this.attr("data-id");
            var status = $this.attr("data-status");

            //已处理
            if (status === "1") {
                $this.find(".btn_deal").remove();
            }
            else {
                // $this.find(".btn_deal").addClass().removeClass("btn-primary");
            }

            //处理
            $this.find(".btn_deal").unbind("click").bind("click", function () {
                customer_salary.userDeal(this);
            });

            //删除
            $this.find(".btn_del").unbind("click").bind("click", function () {

                customer_salary.userDel(this);

            });

        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: customer_salary.currentPage, //当前页数
            totalPages: customer_salary.totalPage, //总页数
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

                customer_salary.currentPage = page;
                customer_salary.userList();//查询

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

    },
    //确认处理 - 单个
    userDeal: function (self) {
        var id = $(self).closest(".item").attr("data-id");
        customer_salary.userIds = [{
            id: id
        }];

        operateShow("确定要处理该条需求吗？", function () {

            loadingInit();

            var obj = {
                batch: customer_salary.userIds
            };

            aryaPostRequest(
                urlGroup.customer_deal_confirm,
                obj,
                function (data) {
                    //console.log(data);

                    if (data.code === RESPONSE_OK_CODE) {
                        toastr.success("处理成功！");
                        customer_salary.userList();
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

    },
    //确认处理 - 多个
    userDealMore: function () {
        var $table_container = $customer_salary_container.find(".table_container");
        var $item_active = $table_container.find("tbody .item.active");

        if ($item_active.length === 0) {
            toastr.warning("请先选择需求！");
        }
        else {
// debugger
            var isTrue = true;

            customer_salary.userIds = [];
            for (var i = 0; i < $item_active.length; i++) {

                var id = $item_active.eq(i).attr("data-id");
                var status = $item_active.eq(i).attr("data-status");

                if (status === "1") {
                    isTrue = false;
                    break;
                }

                var obj = {
                    id: id
                };
                customer_salary.userIds.push(obj);

            }

            if (!isTrue) {
                toastr.warning("选择的需求有部分已处理！");

                return
            }

            operateShow("确定要处理该条需求吗？", function () {

                loadingInit();

                var obj = {
                    batch: customer_salary.userIds
                };

                aryaPostRequest(
                    urlGroup.customer_deal_confirm,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {
                            toastr.success("处理成功！");
                            customer_salary.userList();
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

    },
    //删除 - 单个
    userDel: function (self) {
        var id = $(self).closest(".item").attr("data-id");
        customer_salary.userIds = [{
            id: id
        }];

        delWarning("确定要删除该条需求吗？", function () {

            loadingInit();

            var obj = {
                batch: customer_salary.userIds
            };

            aryaPostRequest(
                urlGroup.customer_del,
                obj,
                function (data) {
                    //console.log(data);

                    if (data.code === RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");
                        customer_salary.userList();
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

    },
    //删除 - 多个
    userDelMore: function () {
        var $table_container = $customer_salary_container.find(".table_container");
        var $item_active = $table_container.find("tbody .item.active");

        if ($item_active.length === 0) {
            toastr.warning("请先选择需求！");
        }
        else {

            customer_salary.userIds = [];
            $item_active.each(function () {
                var id = $(this).attr("data-id");

                var obj = {
                    id: id
                };
                customer_salary.userIds.push(obj);

            });

            delWarning("确定要删除选中的需求吗？", function () {

                loadingInit();

                var obj = {
                    batch: customer_salary.userIds
                };

                aryaPostRequest(
                    urlGroup.customer_del,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {
                            toastr.success("删除成功！");
                            customer_salary.userList();
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

    }


};

$(function () {
    customer_salary.init();
});
