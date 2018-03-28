/**
 * Created by Administrator on 2017/7/26.
 */

var $electronic_payroll_container = $(".electronic_payroll_container");
var $tb_el_payroll = $electronic_payroll_container.find("#tb_el_payroll");//table

var electronic_payroll = {

    init: function () {
        electronic_payroll.btnSearchClick();
    },

    //查询
    btnSearchClick: function () {
        electronic_payroll.initTbData();//获取 用户列表
    },
    //用户列表 dataTable 初始化
    initTbData: function () {

        $tb_el_payroll.bootstrapTable("destroy");
        //表格的初始化
        $tb_el_payroll.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 400,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: 'loginName',
                    title: '登录名',
                    sortable: true,
                    align: "center",
                    class: "loginName",
                    width: 200
                },
                {
                    field: 'corp_name',
                    title: '企业名称',
                    sortable: true,
                    align: "center",
                    class: "corp_name",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'user_name',
                    title: '联系人姓名',
                    sortable: true,
                    align: "center",
                    class: "user_name",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'first_login_time',
                    title: '首次使用日期',
                    sortable: true,
                    align: "center",
                    class: "first_login_time",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'use_count',
                    title: '使用次数',
                    sortable: true,
                    align: "center",
                    class: "use_count",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'payroll_number',
                    title: '发薪人数',
                    sortable: true,
                    align: "center",
                    class: "payroll_number",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.electronic_payroll_user_list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                var orderParam = params.sortName;//
                switch (orderParam) {
                    case "loginName":
                        orderParam = "loginName";
                        break;
                    case "corp_name":
                        orderParam = "corpName";
                        break;
                    case "user_name":
                        orderParam = "hrName";
                        break;
                    case "first_login_time":
                        orderParam = "createTime";
                        break;
                    case "use_count":
                        orderParam = "useTimes";
                        break;
                    case "payroll_number":
                        orderParam = "payrollNumber";
                        break;
                    default:
                        orderParam = "loginName";
                        break;
                }
                var order = params.sortOrder === "asc" ? 1 : 0;//升序、降序
                var $search_container = $electronic_payroll_container.find(".search_container");

                var obj = {
                    page: params.pageNumber,
                    pageSize: params.pageSize,
                    keyword: $search_container.find(".searchCondition").val(),
                    orderParam: orderParam,
                    order: order
                };

                // console.log(obj);
                return obj;

            },
            onLoadSuccess: function () {  //加载成功时执行

                // toastr.success("成功！");

            },
            onLoadError: function () {  //加载失败时执行
                debugger
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {

                setTimeout(function () {
                    loadingRemove();
                }, 500);

                var tb_data = [];
                var total_rows = 0;//总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows ? res.result.total_rows : 0;//总条数

                        if (res.result.geteSalaryCorpUsers) {
                            $.each(res.result.geteSalaryCorpUsers, function (i, $item) {

                                var id = $item.id ? $item.id : "";//
                                var loginName = $item.loginName ? $item.loginName : "";//
                                var corpName = $item.corpName ? $item.corpName : "";//
                                var userName = $item.userName ? $item.userName : "";//
                                var time = $item.time ? $item.time : "";//
                                var use_count = $item.useTimes ? $item.useTimes : "";//使用次数
                                var payroll_number = $item.payrollNumber ? $item.payrollNumber : "";//一共发薪的 人数

                                var obj = {

                                    id: id,
                                    loginName: loginName,          //登录名
                                    corp_name: corpName,                //企业名称
                                    user_name: userName,                  //员工姓名
                                    first_login_time: time,          //首次使用
                                    use_count: use_count,          //使用次数
                                    payroll_number: payroll_number          //一共发薪的 人数

                                };

                                tb_data.push(obj);

                            });
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

            },

            //注册加载子表的事件。注意下这里的三个参数！
            onExpandRow: function (index, row, $detail) {

                $detail.empty();

                var $table = $("<table>").appendTo($detail);
                $table.attr("id", "el_detail_" + index);

                var obj = {
                    corpId: row.id
                };
                var url = urlGroup.electronic_payroll_info + "?" + jsonParseParam(obj);
                aryaGetRequest(
                    url,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            // console.log(data);

                            var tb_data = [];

                            if (data.result && data.result.length > 0) {

                                tb_data = data.result;

                                $.each(data.result, function (i, $item) {

                                    // var importTime = $item.importTime ? $item.importTime : "";//导入时间、发薪月份
                                    // var sendTime = $item.sendTime ? $item.sendTime : "";//发送时间
                                    // var sendNum = $item.sendNum ? $item.sendNum : "";//发送人数
                                    // var url = $item.url ? $item.url : "";//下载的url
                                    //
                                    // var $tr = $("<tr>");
                                    // $tr.appendTo($tbody);
                                    //
                                    // //发薪月份
                                    // var $salary_month = $("<td>");
                                    // $salary_month.text(importTime);
                                    // $salary_month.appendTo($tr);
                                    //
                                    // //使用时间
                                    // var $send_time = $("<td>");
                                    // $send_time.text(sendTime);
                                    // $send_time.appendTo($tr);
                                    //
                                    // //发薪人数
                                    // var $send_num = $("<td>");
                                    // $send_num.text(sendNum);
                                    // $send_num.appendTo($tr);
                                    //
                                    // //下载的url
                                    // var $down = $("<td>");
                                    // $down.appendTo($tr);
                                    // var $a = $("<a>");
                                    // $a.appendTo($down);
                                    // $a.text("下载");
                                    // $a.attr("href", url);
                                    // $a.attr("data-url", url);

                                });

                            }


                            //初始化 子表，详情
                            electronic_payroll.initExpandRow(index, row, $detail, tb_data);

                        }
                        else {
                            toastr.warning(data.msg);
                        }

                    },
                    function (error) {
                        messageCue(error);
                    }
                );

            }

        });

    },

    //初始化 子表，详情
    initExpandRow: function (index, row, $detail, data) {

        var $tb_detail = $detail.find("table");

        //表格的初始化
        $tb_detail.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: false,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            height: 200,
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
                    field: 'importTime',
                    title: '发薪月份',
                    sortable: true,
                    align: "center",
                    class: "importTime",
                    width: 200
                },
                {
                    field: 'sendTime',
                    title: '使用时间',
                    sortable: true,
                    align: "center",
                    class: "sendTime",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'sendNum',
                    title: '发薪人数',
                    sortable: true,
                    align: "center",
                    class: "sendNum",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'url',
                    title: '下载',
                    sortable: true,
                    align: "center",
                    class: "url",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";

                        html = "<a class='download'>下载</a>";

                        return html;

                    },
                    events: {

                        "click .download": function (e, value, row, index) {

                            // debugger
                            // console.log(row.url)

                            aryaGetRequest(
                                row.url,
                                function (data) {
                                    //console.log("获取日志：");
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
                                        //console.log("获取日志-----error：");
                                        //console.log(data.msg);

                                        toastr.warning(data.msg);
                                    }
                                },
                                function (error) {
                                    messageCue(error);
                                }
                            );

                        }

                    }
                }

            ]

        });

    }

};

$(function () {
    $tb_el_payroll = $electronic_payroll_container.find("#tb_el_payroll");//table
    electronic_payroll.init();
});


var debug_ = {
    //查询
    btnSearchClick: function () {
        electronic_payroll.userList();//获取 用户列表
    },

    //获取 用户列表
    userList: function () {

        var $search_container = $electronic_payroll_container.find(".search_container");

        var obj = {
            keyword: $search_container.find(".searchCondition").val()
        };
        var url = urlGroup.electronic_payroll_user_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    var tb_data = [];

                    if (data.result && data.result.geteSalaryCorpUsers) {

                        var arr = data.result.geteSalaryCorpUsers;

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var loginName = $item.loginName ? $item.loginName : "";//
                            var corpName = $item.corpName ? $item.corpName : "";//
                            var userName = $item.userName ? $item.userName : "";//
                            var time = $item.time ? $item.time : "";//
                            var use_count = $item.useTimes ? $item.useTimes : "";//使用次数
                            var payroll_number = $item.payrollNumber ? $item.payrollNumber : "";//一共发薪的 人数

                            var obj = {

                                id: id,
                                loginName: loginName,          //登录名
                                corp_name: corpName,                //企业名称
                                user_name: userName,                  //员工姓名
                                first_login_time: time,          //首次使用
                                use_count: use_count,          //使用次数
                                payroll_number: payroll_number,          //一共发薪的 人数

                            };

                            tb_data.push(obj);

                        }

                    }

                    //dataTable 初始化
                    electronic_payroll.initTbData(tb_data);	//列表初始化


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
    //dataTable 初始化
    initTbData: function (data) {

        $tb_el_payroll.bootstrapTable("destroy");
        //表格的初始化
        $tb_el_payroll.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 400,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: 'loginName',
                    title: '登录名',
                    sortable: true,
                    align: "center",
                    class: "loginName",
                    width: 200
                },
                {
                    field: 'corp_name',
                    title: '企业名称',
                    sortable: true,
                    align: "center",
                    class: "corp_name",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'user_name',
                    title: '联系人姓名',
                    sortable: true,
                    align: "center",
                    class: "user_name",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'first_login_time',
                    title: '首次使用日期',
                    sortable: true,
                    align: "center",
                    class: "first_login_time",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = value;
                        if (value && value.length > 10) {
                            html = "<div title='" + value + "'>" + value.substr(0, 10) + "..." + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'use_count',
                    title: '使用次数',
                    sortable: true,
                    align: "center",
                    class: "use_count",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'payroll_number',
                    title: '发薪人数',
                    sortable: true,
                    align: "center",
                    class: "payroll_number",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                }

            ],

            //注册加载子表的事件。注意下这里的三个参数！
            onExpandRow: function (index, row, $detail) {

                $detail.empty();

                var $table = $("<table>").appendTo($detail);
                $table.attr("id", "el_detail_" + index);

                var obj = {
                    corpId: row.id
                };
                var url = urlGroup.electronic_payroll_info + "?" + jsonParseParam(obj);
                aryaGetRequest(
                    url,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            // console.log(data);

                            var tb_data = [];

                            if (data.result && data.result.length > 0) {

                                tb_data = data.result;

                                $.each(data.result, function (i, $item) {

                                    // var importTime = $item.importTime ? $item.importTime : "";//导入时间、发薪月份
                                    // var sendTime = $item.sendTime ? $item.sendTime : "";//发送时间
                                    // var sendNum = $item.sendNum ? $item.sendNum : "";//发送人数
                                    // var url = $item.url ? $item.url : "";//下载的url
                                    //
                                    // var $tr = $("<tr>");
                                    // $tr.appendTo($tbody);
                                    //
                                    // //发薪月份
                                    // var $salary_month = $("<td>");
                                    // $salary_month.text(importTime);
                                    // $salary_month.appendTo($tr);
                                    //
                                    // //使用时间
                                    // var $send_time = $("<td>");
                                    // $send_time.text(sendTime);
                                    // $send_time.appendTo($tr);
                                    //
                                    // //发薪人数
                                    // var $send_num = $("<td>");
                                    // $send_num.text(sendNum);
                                    // $send_num.appendTo($tr);
                                    //
                                    // //下载的url
                                    // var $down = $("<td>");
                                    // $down.appendTo($tr);
                                    // var $a = $("<a>");
                                    // $a.appendTo($down);
                                    // $a.text("下载");
                                    // $a.attr("href", url);
                                    // $a.attr("data-url", url);

                                });

                            }


                            //初始化 子表，详情
                            electronic_payroll.initExpandRow(index, row, $detail, tb_data);

                        }
                        else {
                            toastr.warning(data.msg);
                        }

                    },
                    function (error) {
                        messageCue(error);
                    }
                );

            }

        });

    },

    //初始化 子表，详情
    initExpandRow: function (index, row, $detail, data) {

        var $tb_detail = $detail.find("table");

        //表格的初始化
        $tb_detail.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: false,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            height: 200,
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
                    field: 'importTime',
                    title: '发薪月份',
                    sortable: true,
                    align: "center",
                    class: "importTime",
                    width: 200
                },
                {
                    field: 'sendTime',
                    title: '使用时间',
                    sortable: true,
                    align: "center",
                    class: "sendTime",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'sendNum',
                    title: '发薪人数',
                    sortable: true,
                    align: "center",
                    class: "sendNum",
                    width: 200,
                    formatter: function (value, row, index) {

                        return value;

                    }
                },
                {
                    field: 'url',
                    title: '下载',
                    sortable: true,
                    align: "center",
                    class: "url",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";

                        html = "<a class='download'>下载</a>";

                        return html;

                    },
                    events: {

                        "click .download": function (e, value, row, index) {

                            // debugger
                            // console.log(row.url)

                            aryaGetRequest(
                                row.url,
                                function (data) {
                                    //console.log("获取日志：");
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
                                        //console.log("获取日志-----error：");
                                        //console.log(data.msg);

                                        toastr.warning(data.msg);
                                    }
                                },
                                function (error) {
                                    messageCue(error);
                                }
                            );

                        }

                    }
                }

            ]

        });

    }
};

