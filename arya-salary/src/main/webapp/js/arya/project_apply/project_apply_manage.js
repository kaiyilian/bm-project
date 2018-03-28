/**
 * Created by Administrator on 2017/6/30.
 */

var $pAManage_container = $(".project_apply_manage_container");//page container
var $contract_period_modal = $(".contract_period_modal");//合同期限 modal

var project_apply_manage = {

    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    corp_id: "",//公司ID

    init: function () {

        $pAManage_container.find(".search_condition").val("");

        project_apply_manage.btnSearchClick();//

        //合同期限 弹框 初始化
        $contract_period_modal.on("shown.bs.modal", function () {

            project_apply_manage.initTime();//初始化 时间
            project_apply_manage.initChooseEndDate();//初始化 选择结束日期

        });

    },
    //初始化 时间
    initTime: function () {
        var $row = $contract_period_modal.find(".modal-body .row");

        //开始时间 click
        $row.find(".contract_begin").html("").attr("data-time", "");
        $row.find(".contract_begin").click(function () {
            chooseBeginTime();
        });
        $row.find(".icon_begin").click(function () {
            chooseBeginTime();
        });

        //结束时间 click
        $row.find(".contract_end").html("").attr("data-time", "");
        $row.find(".contract_end").click(function () {
            chooseEndTime();
        });
        $row.find(".icon_end").click(function () {
            chooseEndTime();
        });

        var chooseBeginTime = function () {
            var opt = {
                elem: "#contract_begin",
                istoday: false, //是否显示今天
                choose: function (date) { //选择日期完毕的回调

                    var time = new Date(date).getTime();
                    $row.find(".contract_begin").attr("data-time", time);

                    //合同结束日期 初始化
                    $row.find(".contract_end").attr("data-time", "");//
                    $row.find(".contract_end").html("");
                    //取消选中状态
                    $row.find(".contract_end_date_list").find(".active").removeClass("active");
                    $row.find(".contract_end_date_list").find("img")
                        .attr("src", "img/icon_Unchecked.png");

                }
            };

            laydate(opt);
        };

        var chooseEndTime = function () {
            var opt = {
                elem: "#contract_end",
                istoday: false, //是否显示今天
                choose: function (date) { //选择日期完毕的回调
                    var time = new Date(date).getTime();
                    $row.find(".contract_end").attr("data-time", time);//

                    //取消选中状态
                    $row.find(".contract_end_date_list").find(".active").removeClass("active");
                    $row.find(".contract_end_date_list").find("img")
                        .attr("src", "img/icon_Unchecked.png");
                }
            };

            laydate(opt)
        };

    },
    //初始化 选择结束日期
    initChooseEndDate: function () {
        var $row = $contract_period_modal.find(".modal-body > .row");

        //取消选中状态
        $row.find(".contract_end_date_list").find(".active").removeClass("active");
        $row.find(".contract_end_date_list").find("img").attr("src", "img/icon_Unchecked.png");

        //选择结束日期
        $row.find(".contract_end_date_list > .item").unbind("click").bind("click", function () {
            project_apply_manage.chooseEndDate(this);
        });


    },
    //选择结束日期
    chooseEndDate: function (self) {
        var begin = $contract_period_modal.find(".contract_begin").html();
        if (!begin) {
            toastr.warning("请先选择开始日期！");
            return
        }

        $(self).addClass("active").siblings(".item").removeClass("active");
        $(self).find("img").attr("src", "img/icon_checked.png");
        $(self).siblings(".item").find("img").attr("src", "img/icon_Unchecked.png");


        var date = parseInt($(self).data("time"));//选择的日期
        var end_time = "";//结束日期
        var time = "";//结束日期的 时间戳

        //无期限
        if (date === 0) {
            end_time = "无期限";
            time = "253402185600000";//写死 默认的的 无期限时间戳
        }
        else {
            begin = new Date(begin);
            end_time = new Date(begin);
            end_time.setFullYear(end_time.getFullYear() + date);
            end_time.setDate(end_time.getDate() - 1);
            end_time = new Date(end_time);

            var year = end_time.getFullYear();//计算 结束日期年份
            var month = end_time.getMonth() + 1;
            month = month < 10 ? "0" + month : month;
            var day = end_time.getDate();//结束日期 天
            day = day < 10 ? "0" + day : day;

            end_time = year + "-" + month + "-" + day;//
            time = new Date(end_time).getTime();

        }

        $contract_period_modal.find(".contract_end").html(end_time);
        $contract_period_modal.find(".contract_end").attr("data-time", time);
    },

    //查询
    btnSearchClick: function () {
        project_apply_manage.currentPage = 1;

        project_apply_manage.corpList();//公司 - 列表
        // project_apply_manage.corpListInit();//
    },
    //清空 公司列表
    clearCorpList: function () {

        var $table_container = $pAManage_container.find(".table_container");
        var msg = "<tr><td colspan='6'>暂无数据</td></tr>";

        $table_container.find("tbody").html(msg);

    },
    //公司 - 列表
    corpList: function () {
        project_apply_manage.clearCorpList();//清空 公司列表

        var $search_container = $pAManage_container.find(".search_container");
        var $table_container = $pAManage_container.find(".table_container");

        var obj = {
            condition: $search_container.find(".search_condition").val(),
            page: project_apply_manage.currentPage,
            page_size: "10"
        };
        var url = urlGroup.project_apply_corp_list + "?" + jsonParseParam(obj);

        loadingInit();//加载框 出现

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    project_apply_manage.totalPage = data.result.pages ? data.result.pages : 1;

                    var list = "";
                    var arr = data.result.result;
                    if (!arr || arr.length === 0) {
                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var sales_man = $item.salesMan ? $item.salesMan : "";//
                            var sales_dept = $item.salesDepartment ? $item.salesDepartment : "";//
                            var corp_name = $item.customerName ? $item.customerName : "";//
                            var shortName = $item.shortName ? $item.shortName : "";//
                            var apply_date = $item.applyDate ? $item.applyDate : "";//
                            apply_date = timeInit(apply_date);

                            list +=
                                "<tr class='item' data-id='" + id + "'>" +
                                "<td>" + (i + 1) + "</td>" +
                                "<td class='sales_man'>" + sales_man + "</td>" +
                                "<td class='sales_dept'>" + sales_dept + "</td>" +
                                "<td class='corp_name' title='" + corp_name + "'>" + shortName + "</td>" +
                                "<td class='apply_date'>" + apply_date + "</td>" +
                                "<td class='operate'>" +
                                "<button class='btn btn-sm btn-primary btn_detail'>详情</button>" +
                                "<button class='btn btn-sm btn-primary btn_upgrade'>成为正式客户</button>" +
                                "</td>" +
                                "</tr>"

                        }

                        $table_container.find("tbody").html(list);

                    }

                    project_apply_manage.corpListInit();//公司列表 初始化

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
    //公司列表 初始化
    corpListInit: function () {
        var $table_container = $pAManage_container.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $pAManage_container.find(".pager_container");

        //判断查询 结果为空
        if ($item.length === 0) {
            $pager_container.hide();
            return
        }

        //列表 初始化
        $item.each(function () {
            var $this = $(this);

            var id = $this.attr("data-id");

            //查看详情
            $this.find(".btn_detail").unbind("click").bind("click", function () {

                project_apply_manage.corp_id = id;
                project_apply_manage.sessionSet();

                //公司 详情
                project_apply_manage.corpDetail(this);

            });

            //升级 为正式客户
            $this.find(".btn_upgrade").unbind("click").bind("click", function () {

                project_apply_manage.corp_id = id;
                project_apply_manage.corpUpgradeModalShow();

            });

        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: project_apply_manage.currentPage, //当前页数
            totalPages: project_apply_manage.totalPage, //总页数
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

                project_apply_manage.currentPage = page;
                project_apply_manage.corpList();//查询

            }
        };

        var ul = '<ul class="pagenation" style="float:right;"></ul>';
        $pager_container.show();
        $pager_container.html(ul);
        $pager_container.find(".pagenation").bootstrapPaginator(options);

    },

    //公司 详情
    corpDetail: function (self) {

        var name = $(self).closest(".item").find(".corp_name").html();

        var tabId = "project_detail_" + project_apply_manage.corp_id;//tab中的id
        var pageName = name + "的详情";

        getInsidePageDiv(urlGroup.project_apply_corp_detail_page, tabId, pageName);

    },
    //升级为正式客户 弹框显示
    corpUpgradeModalShow: function () {
        $contract_period_modal.modal("show");
    },
    //升级为正式客户 确定
    corpUpgrade: function () {

        if (!project_apply_manage.checkTimeIsChoose()) {
            return;
        }

        loadingInit();

        var obj = {
            "projectId": project_apply_manage.corp_id,
            "contractDateStart": $contract_period_modal.find(".contract_begin").attr("data-time"),
            "contractDateEnd": $contract_period_modal.find(".contract_end").attr("data-time")
        };

        aryaPatchRequest(
            urlGroup.project_apply_corp_upgrade,
            obj,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    $contract_period_modal.modal("hide");
                    toastr.success("升级成功！");
                    project_apply_manage.corpList();//

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                toastr.error(error);
            }
        );

    },
    //检查 是否选择了时间
    checkTimeIsChoose: function () {
        var $row = $contract_period_modal.find(".row");

        var start = $row.find(".contract_begin").attr("data-time");
        var end = $row.find(".contract_end").attr("data-time");

        var flag = false;
        var txt;

        if (!start) {
            txt = "请选择开始时间！";
        }
        else if (!end) {
            txt = "请选择结束时间！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //新建立项申请 页面
    goCorpCreatePage: function () {

        var tabId = "project_create";//tab中的id
        var pageName = "新增立项申请";

        getInsidePageDiv(urlGroup.project_apply_corp_create_page, tabId, pageName);

    },

    //赋值 session
    sessionSet: function () {
        sessionStorage.setItem("corp_id", project_apply_manage.corp_id);
    }

};

$(function () {
    project_apply_manage.init();
});
