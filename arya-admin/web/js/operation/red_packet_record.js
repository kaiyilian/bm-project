/**
 * Created by Administrator on 2017/12/26.
 */

var $red_packet_record_container = $(".red_packet_record_container");
var $tb_red_packet_record = $red_packet_record_container.find("#tb_red_packet_record");//表格id

var red_packet_record = {

    //初始化
    init: function () {

        red_packet_record.btnSearchClick();//

    },

    btnSearchClick: function () {
        red_packet_record.initTb();//订单查询 - 列表
    },

    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (red_packet_record_param.start && red_packet_record_param.end &&
            red_packet_record_param.start > red_packet_record_param.end) {
            txt = "开始时间不能大于结束时间！";
        }
        else if (red_packet_record_param.redEnvelopeBalance && isNaN(red_packet_record_param.redEnvelopeBalance)) {
            txt = "红包余额只能输入数字！";
        }
        // else if (red_packet_record_param.phone && !phone_reg.test(red_packet_record_param.phone)) {
        //     txt = "手机号格式不对！";
        // }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },
    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        red_packet_record_param.paramSet();//赋值查询参数

        //检查查询参数是否正确
        if (!red_packet_record.checkParam()) {
            return;
        }

        $tb_red_packet_record.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_red_packet_record.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

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
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 600,
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
                    field: 'getTime',
                    title: '领取时间',
                    align: "center",
                    class: "getTime",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'user_name',
                    title: '用户名',
                    // sortable: true,
                    align: "center",
                    class: "user_name",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'user_phone',
                    title: '手机号码',
                    // sortable: true,
                    align: "center",
                    class: "user_phone",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'redEnvelopeBalance',
                    title: '红包金额',
                    // sortable: true,
                    align: "center",
                    class: "redEnvelopeBalance",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'balanceTotalTime',
                    title: '余额时长',
                    // sortable: true,
                    align: "center",
                    class: "balanceTotalTime",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'curTimeBalance',
                    title: '当时余额',
                    // sortable: true,
                    align: "center",
                    class: "curTimeBalance",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'sendStatus',
                    title: '发放状态',
                    // sortable: true,
                    align: "center",
                    class: "sendStatus",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.red_packet_record_list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                var obj = {

                    start: red_packet_record_param.start,
                    end: red_packet_record_param.end,
                    redEnvelopeBalance: red_packet_record_param.redEnvelopeBalance,
                    phone: red_packet_record_param.phone,
                    page: params.pageNumber,
                    pageSize: params.pageSize

                };

                // console.log(obj);
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
                var total_rows = 0;//总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows ? res.result.total_rows : 0;//总条数

                        if (res.result.result) {
                            $.each(res.result.result, function (i, item) {

                                // var id = item.id ? item.id : "";//
                                var getTime = item.getTimeFormatStr ? item.getTimeFormatStr : "";//
                                var realName = item.realName ? item.realName : "";//
                                var phone = item.phone ? item.phone : "";//
                                var redEnvelopeBalance = item.redEnvelopeBalanceFormatStr ? item.redEnvelopeBalanceFormatStr : "";//
                                var balanceTotalTime = item.balanceTotalTimeFormatStr ? item.balanceTotalTimeFormatStr : "";//
                                var curTimeBalance = item.curTimeBalanceFormatStr ? item.curTimeBalanceFormatStr : "";//
                                var sendStatus = item.sendStatusFormatStr ? item.sendStatusFormatStr : "失败";//发送状态 0:成功 1:失败

                                var obj = {

                                    // id: id,
                                    getTime: getTime,
                                    user_name: realName,
                                    user_phone: phone,
                                    redEnvelopeBalance: redEnvelopeBalance,
                                    balanceTotalTime: balanceTotalTime,
                                    curTimeBalance: curTimeBalance,
                                    sendStatus: sendStatus

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

            }

        });
    },

    //领红包记录 导出
    exportRecord: function () {

        var obj = {
            start: red_packet_record_param.start,
            end: red_packet_record_param.end,
            redEnvelopeBalance: red_packet_record_param.redEnvelopeBalance,
            phone: red_packet_record_param.phone
        };
        var url = urlGroup.red_packet_record_export + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var url = data.result.url ? data.result.url : "";
                        if (!url) {
                            toastr.warning("无法下载，下载链接为空！");
                            return;
                        }

                        toastr.success("导出成功！");

                        var aLink = document.createElement('a');
                        aLink.download = "";
                        aLink.href = url;
                        aLink.click();

                    }

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

};
//保存时 参数
var red_packet_record_param = {

    start: null,                    //开始时间
    end: null,                      //结束时间
    redEnvelopeBalance: null,       //红包金额
    phone: null,                    //手机号

    //参数赋值
    paramSet: function () {

        var $search_container = $red_packet_record_container.find(".search_container");

        red_packet_record_param.start = $.trim($search_container.find(".beginTime").val());
        red_packet_record_param.start = red_packet_record_param.start ?
            new Date(red_packet_record_param.start).getTime() : "";

        red_packet_record_param.end = $.trim($search_container.find(".endTime").val());
        red_packet_record_param.end = red_packet_record_param.end ?
            new Date(red_packet_record_param.end).getTime() : "";

        red_packet_record_param.redEnvelopeBalance = $.trim($search_container.find(".balance").val());
        red_packet_record_param.phone = $.trim($search_container.find(".user_phone").val());

    }

};

$(function () {
    red_packet_record.init();
});
