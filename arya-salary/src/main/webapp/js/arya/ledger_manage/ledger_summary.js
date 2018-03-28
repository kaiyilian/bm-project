/**
 * Created by Administrator on 2017/10/13.
 */

var $ledger_summary_container = $(".ledger_summary_container");//台账汇总 container
var $ledger_summary = $ledger_summary_container.find("#ledger_summary");//表格

//台账汇总
var ledger_summary = {

    beginTime: null,
    endTime: null,

    init: function () {
        ledger_summary.initTime();//初始化 时间插件
        ledger_summary.ledgerList();//查询
    },

    //初始化 时间插件
    initTime: function () {

        var $search_container = $ledger_summary_container.find(".search_container");

        var start = {
            elem: '#ledger_beginTime',
            format: 'YYYY/MM/DD',
            // min: laydate.now(), //设定最小日期为当前日期
            // max: '2099-06-16 23:59:59', //最大日期
            istime: false,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas;//将结束日的初始值设定为开始日

                // var time = new Date(datas).getTime();
                // $search_container.find(".beginTime").attr("data-time", time);
            }
        };
        var end = {
            elem: '#ledger_endTime',
            format: 'YYYY/MM/DD',
            // min: laydate.now(),
            // max: '2099-06-16 23:59:59',
            istime: false,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期


            }
        };
        laydate(start);
        laydate(end);

        //初始化 开始、结束时间
        var now = new Date().toLocaleDateString();
        var year = new Date().getFullYear();

        $search_container.find(".beginTime").val(year + "/01/01");
        $search_container.find(".endTime").val(now);

    },

    //查询按钮 点击事件
    btnSearchClick: function () {
        ledger_summary.ledgerList();//查询
    },
    //查询
    ledgerList: function () {

        //检查查询参数
        if (!ledger_summary.checkParamBySearch()) {
            return
        }

        loadingInit();

        $ledger_summary.bootstrapTable("destroy");
        //表格的初始化
        $ledger_summary.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],              //可供选择的每页的行数（*）

            //排序
            sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            showFooter: true,                   //显示footer

            width: "100%",
            height: 400,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: "customer",
                    title: "客户",
                    align: "center",
                    class: "customer",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;
                    },
                    footerFormatter: "总计"
                },
                {
                    field: "arrivalAmount",
                    title: "到账金额",
                    align: "center",
                    class: "arrivalAmount",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        return html;
                    },
                    footerFormatter: function (value) {

                        var html = "";
                        if (value.length > 0) {
                            html = "<div>" + value[0].summary.transAccountAmountTotal + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: "preTaxAmount",
                    title: "税前金额",
                    align: "center",
                    class: "preTaxAmount",
                    formatter: function (value, row, index) {
                        // console.log(value);

                        var html = "";

                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;
                    },
                    footerFormatter: function (value) {

                        var html = "";
                        if (value.length > 0) {
                            html = "<div>" + value[0].summary.salaryBeforeTaxTotal + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: "personalTax",
                    title: "个税服务费",
                    align: "center",
                    class: "personalTax",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        return html;
                    },
                    footerFormatter: function (value) {

                        var html = "";
                        if (value.length > 0) {
                            html = "<div>" + value[0].summary.personalTaxFeeTotal + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: "afterTaxAmount",
                    title: "税后薪资",
                    align: "center",
                    class: "afterTaxAmount",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        return html;
                    },
                    footerFormatter: function (value) {

                        var html = "";
                        if (value.length > 0) {
                            html = "<div>" + value[0].summary.salaryAfterTaxTotal + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: "brokerage",
                    title: "薪资服务费",
                    align: "center",
                    class: "brokerage",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        return html;
                    },
                    footerFormatter: function (value) {

                        var html = "";
                        if (value.length > 0) {
                            html = "<div>" + value[0].summary.salaryFeeTotal + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: "availableAmount",
                    title: "可用余额",
                    align: "center",
                    class: "availableAmount",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        return html;
                    },
                    footerFormatter: function (value) {

                        // var html = "";
                        // if (value.length > 0) {
                        //     html = "<div>" + value[0].summary.remainAccount + "</div>";
                        // }

                        return "-";

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.ledger_summary_list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数
                var obj = {
                    page: params.pageNumber,
                    page_size: params.pageSize,
                    startTime: ledger_summary.beginTime,
                    endTime: ledger_summary.endTime
                };

                return obj;
            },
            onLoadSuccess: function () {  //加载成功时执行
                // toastr.success("成功！");

            },
            onLoadError: function () {  //加载失败时执行
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {

                setTimeout(function () {
                    loadingRemove();
                }, 500);

                var tb_data = [];
                var total_rows = 0;//数据总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows;//

                        var arr = res.result.resultPager;
                        if (arr && arr.length > 0) {

                            for (var i = 0; i < arr.length; i++) {
                                var $item = arr[i];

                                var id = $item.customerId ? $item.customerId : "";//
                                var customer = $item.customerName ? $item.customerName : "";//客户
                                var arrivalAmount = $item.transAccountAmountTotal ? $item.transAccountAmountTotal : "";//到账金额
                                var preTaxAmount = $item.salaryBeforeTaxTotal ? $item.salaryBeforeTaxTotal : "";//税前金额
                                var personalTax = $item.personalTaxFeeTotal ? $item.personalTaxFeeTotal : "";//个税服务费
                                var afterTaxAmount = $item.salaryAfterTaxTotal ? $item.salaryAfterTaxTotal : "";//税后金额
                                var brokerage = $item.salaryFeeTotal ? $item.salaryFeeTotal : "";//薪资服务费
                                var availableAmount = $item.remainAccount ? $item.remainAccount : "";//可用余额

                                var obj = {

                                    id: id,
                                    customer: customer,
                                    arrivalAmount: arrivalAmount,
                                    preTaxAmount: preTaxAmount,

                                    personalTax: personalTax,
                                    afterTaxAmount: afterTaxAmount,
                                    brokerage: brokerage,
                                    availableAmount: availableAmount,

                                    summary: res.result.resultCount

                                };
                                tb_data.push(obj);

                            }

                        }

                    }

                }
                else {
                    toastr.warning(res.msg);
                }

                return {
                    total: total_rows,
                    rows: tb_data
                };

            }

        });

    },
    //检查 查询参数
    checkParamBySearch: function () {
        var flag = false;
        var txt = "";
        var $search_container = $ledger_summary_container.find(".search_container");

        ledger_summary.beginTime = $search_container.find(".beginTime").val();
        ledger_summary.beginTime = ledger_summary.beginTime ? new Date(ledger_summary.beginTime).getTime() : "";
        ledger_summary.endTime = $search_container.find(".endTime").val();
        ledger_summary.endTime = ledger_summary.endTime ? new Date(ledger_summary.endTime).getTime() : "";

        if (!ledger_summary.beginTime) {
            txt = "请选择起始日期";
        }
        else if (!ledger_summary.endTime) {
            txt = "请选择截止日期";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },


    //薪资统计结果 导出
    ledgerSummaryExport: function () {

        operateShow(
            "确认要导出吗？",
            null,
            function () {

                loadingInit();

                var obj = {
                    startTime: ledger_summary.beginTime,
                    endTime: ledger_summary.endTime
                };

                aryaPostRequest(
                    urlGroup.ledger_summary_export,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result && data.result.url) {

                                var aLink = document.createElement('a');
                                aLink.download = "";
                                aLink.href = data.result.url;
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
        );

    }

};

$(function () {
    ledger_summary.init();
});