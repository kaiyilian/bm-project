/**
 * Created by Administrator on 2017/7/4.
 */

var $ledger_manage_container;//台账管理 container

var ledger_manage = {

    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    corp_id: "",//公司ID
    ledger_id: "",//台账id
    year_month: "",//选择的年月

    init: function () {

        ledger_manage.initMonthPicker();//初始化月份选择器

        ledger_manage.ledgerList();//台账 - 列表
        // ledger_manage.ledgerListInit();

    },
    //初始化月份选择器
    initMonthPicker: function () {

        var $salary_search_container = $ledger_manage_container.find(".search_container");//查询 container

        var $year_month = $salary_search_container.find(".year_month");
        $year_month.html("");

        $year_month.datepicker('remove');//移除月份选择器

        $year_month.datepicker({
            minViewMode: 1,
            // keyboardNavigation: true,
            // forceParse: false,
            autoclose: true,//选择后 自动关闭试图
            // todayHighlight: true,
            format: 'yyyy-mm'
        });

        $year_month.unbind("show").on("show", function (e) {

        });

        $year_month.unbind("changeMonth").on("changeMonth", function (e) {

            //更换月份事件
            var choseDate = e.date;

            var date = new Date(choseDate);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m > 9 ? m : "0" + m;
            var yearMonth = y + "-" + m;

            // $year_month.html(y + "-" + m);

            //如果 已经选择了年月
            if (ledger_manage.year_month === yearMonth) {
                ledger_manage.year_month = "";
            }
            else {
                ledger_manage.year_month = yearMonth;
            }

            $year_month.html(ledger_manage.year_month);

        });

    },

    //清空 台账列表
    clearLedgerList: function () {
        var $table_container = $ledger_manage_container.find(".table_container");
        var msg = "<tr><td colspan='12'>暂无数据</td></tr>";

        $table_container.find("tbody").html(msg);
    },
    //查询 按钮 click
    btnSearchClick: function () {
        ledger_manage.currentPage = 1;
        ledger_manage.ledgerList();//台账
    },
    //台账 - 列表
    ledgerList: function () {
        ledger_manage.clearLedgerList();//清空 台账列表

        var $table_container = $ledger_manage_container.find(".table_container");

        var obj = {
            customerId: ledger_manage.corp_id,
            yearMonth: ledger_manage.year_month,
            page: ledger_manage.currentPage,
            page_size: "10"
        };
        var url = urlGroup.ledger_list + "?" + jsonParseParam(obj);

        loadingInit();//加载框 出现

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    ledger_manage.totalPage = data.result.pages ? data.result.pages : 1;

                    var list = "";
                    var arr = data.result.datas;
                    if (!arr || arr.length === 0) {
                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var transAccountDate = $item.transAccountDate ? $item.transAccountDate : "";// 到账日期
                            transAccountDate = timeInit1(transAccountDate);
                            var transAccountAmount = $item.transAccountAmount ? $item.transAccountAmount : "";//到账金额
                            var dealDate = $item.dealDate ? $item.dealDate : "";//清单日期
                            dealDate = timeInit1(dealDate);
                            var salaryBeforeTax = $item.salaryBeforeTax ? $item.salaryBeforeTax : "";//税前金额
                            var salaryAfterTax = $item.salaryAfterTax ? $item.salaryAfterTax : "";//税后金额
                            var personalTaxFee = $item.personalTaxFee ? $item.personalTaxFee : "";//个税处理费

                            var salaryFee = $item.salaryFee ? $item.salaryFee : "";//薪资服务费
                            var billAmount = $item.billAmount ? $item.billAmount : "";//开票金额
                            var remainAccount = $item.remainAccount ? $item.remainAccount : "";//账户余额
                            var remark = $item.remark ? $item.remark : "";//备注
                            // var customerId = $item.customerId ? $item.customerId : "";//客户ID


                            list +=
                                "<tr class='item' data-id='" + id + "'>" +
                                "<td>" + (i + 1) + "</td>" +
                                "<td>" + transAccountDate + "</td>" +
                                "<td>" + transAccountAmount + "</td>" +
                                "<td>" + dealDate + "</td>" +
                                "<td>" + salaryBeforeTax + "</td>" +
                                "<td>" + personalTaxFee + "</td>" +
                                "<td>" + salaryAfterTax + "</td>" +
                                "<td>" + salaryFee + "</td>" +
                                "<td>" + remainAccount + "</td>" +
                                "<td class='bill_money'>" + billAmount + "</td>" +
                                "<td class='remark'>" + remark + "</td>" +
                                "<td class='operate'>" +
                                "<button class='btn btn-sm btn-primary btn_modify'>编辑</button>" +
                                "<button class='btn btn-sm btn-primary btn_save'>保存</button>" +
                                "<button class='btn btn-sm btn-default btn_cancel'>取消</button>" +
                                "</td>" +
                                "</tr>"

                        }

                        $table_container.find("tbody").html(list);

                    }

                    ledger_manage.ledgerListInit();//

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
    //台账列表 初始化
    ledgerListInit: function () {
        var $table_container = $ledger_manage_container.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $ledger_manage_container.find(".pager_container");

        //判断查询 结果为空
        if ($item.length === 0) {
            $pager_container.hide();
            return
        }

        //列表 初始化
        $item.each(function () {

            var $this = $(this);
            var id = $this.attr("data-id");
            $this.find(".operate").find(".btn_modify").show().siblings().hide();

            //编辑
            $this.find(".btn_modify").unbind("click").bind("click", function () {

                ledger_manage.ledger_id = id;//
                ledger_manage.ledgerModify(this);

            });

            //保存
            $this.find(".btn_save").unbind("click").bind("click", function () {

                ledger_manage.ledger_id = id;//
                ledger_manage.ledgerSave(this);

            });

            //取消
            $this.find(".btn_cancel").unbind("click").bind("click", function () {

                ledger_manage.ledgerCancel(this);

            });

        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: ledger_manage.currentPage, //当前页数
            totalPages: ledger_manage.totalPage, //总页数
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

                ledger_manage.currentPage = page;
                ledger_manage.ledgerList();//查询

            }
        };

        var ul = '<ul class="pagenation" style="float:right;"></ul>';
        $pager_container.show();
        $pager_container.html(ul);
        $pager_container.find(".pagenation").bootstrapPaginator(options);

    },

    //台账 编辑
    ledgerModify: function (self) {

        var $item = $(self).closest(".item");
        //
        // //可用余额
        // var $surplus_money = $item.find(".surplus_money");
        // var surplus_money = $surplus_money.text();
        // $surplus_money.attr("data-val", surplus_money);
        // $surplus_money.empty();
        //
        // var $dv_1 = $("<div>");
        // $dv_1.appendTo($surplus_money);
        // var $npt_1 = $("<input>");
        // $npt_1.addClass("form-control");
        // $npt_1.val(surplus_money);
        // $npt_1.appendTo($dv_1);

        //开票金额
        var $bill_money = $item.find(".bill_money");
        var bill_money = $bill_money.text();
        $bill_money.attr("data-val", bill_money);
        $bill_money.empty();

        var $dv_2 = $("<div>");
        $dv_2.appendTo($bill_money);
        var $npt_2 = $("<input>");
        $npt_2.addClass("form-control");
        $npt_2.val(bill_money);
        $npt_2.appendTo($dv_2);

        //备注
        var $remark = $item.find(".remark");
        var remark = $remark.text();
        $remark.attr("data-val", remark);
        $remark.empty();

        var $dv_3 = $("<div>");
        $dv_3.appendTo($remark);
        var $npt_3 = $("<input>");
        $npt_3.addClass("form-control");
        $npt_3.val(remark);
        $npt_3.appendTo($dv_3);

        $item.find(".operate").find(".btn_modify").hide().siblings().show();

    },
    //台账 取消
    ledgerCancel: function (self) {

        var $item = $(self).closest(".item");


        // //可用余额
        // var $surplus_money = $item.find(".surplus_money");
        // var surplus_money = $surplus_money.attr("data-val");
        // $surplus_money.html(surplus_money);

        //开票金额
        var $bill_money = $item.find(".bill_money");
        var bill_money = $bill_money.attr("data-val");
        $bill_money.html(bill_money);

        //回款
        var $remark = $item.find(".remark");
        var remark = $remark.attr("data-val");
        $remark.html(remark);

        $item.find(".operate").find(".btn_modify").show().siblings().hide();

    },
    //台账 保存
    ledgerSave: function (self) {

        var $item = $(self).closest(".item");
        var bill_money = $.trim($item.find(".bill_money").find("input").val());        //开票金额
        var remark = $.trim($item.find(".remark").find("input").val());        //备注

        var obj = {
            id: ledger_manage.ledger_id,
            billAmount: bill_money,
            remark: remark
        };

        aryaPostRequest(
            urlGroup.ledger_update,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("更新成功！");

                    $item.find(".bill_money").html(bill_money);
                    $item.find(".remark").html(remark);
                    $item.find(".operate").find(".btn_modify").show().siblings().hide();

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

    //台账 下载
    ledgerDown: function () {

        operateShow(
            "确认要导出吗？",
            null,
            function () {

                loadingInit();

                var obj = {
                    customerId: ledger_manage.corp_id,
                    yearMonth: ledger_manage.year_month
                };

                aryaPostRequest(
                    urlGroup.ledger_down,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {

                                var url = data.result.url ? data.result.url : "";

                                if (!url) {
                                    toastr.warning("下载链接为空！");
                                    return;
                                }

                                var aLink = document.createElement("a");
                                aLink.download = "";
                                aLink.href = url;
                                aLink.click();

                            }
                            else {
                                toastr.warning(data.msg);
                            }

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
        )

    }

};

$(function () {

    ledger_manage.corp_id = sessionStorage.getItem("corp_id");
    sessionStorage.removeItem("corp_id");//
    $ledger_manage_container = $("#page_ledger_manage_" + ledger_manage.corp_id).find(".ledger_manage_container");

    ledger_manage.init();

});
