
var $access_detail_container=$(".access_detail_container");

var access_detail={
    init:function () {
        access_detail.initTbStatistic();
    },

    //访问url 统计
    initTbStatistic: function () {

        var $tb = $access_detail_container.find("#tb_url_statistic");

        aryaGetRequest(
            urlGroup.operation_manage.access_detail.eUrl,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var name = data.result.chart_name ? data.result.chart_name : "";
                    if (name)
                        $access_detail_container.find(".url_statistic .name").text(name);


                    var tb_data = [];
                    var arr = data.result.data;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            // var id = item.id ? item.id : "";//
                            var tag = item.tag ? item.tag : "";//
                            var pv = item.pv ? item.pv : "";//
                            var pv_ratio = item.pv_ratio ? item.pv_ratio : "";//
                            var uv = item.uv ? item.uv : "";//
                            var uv_ratio = item.uv_ratio ? item.uv_ratio : "";//

                            var obj = {

                                url: tag,
                                pv: pv,
                                pv_percent: pv_ratio,
                                uv: uv,
                                uv_percent: uv_ratio

                            };
                            tb_data.push(obj);

                        }

                    }

                    $tb.bootstrapTable("destroy");
                    //表格的初始化
                    $tb.bootstrapTable({

                        undefinedText: "",                   //当数据为 undefined 时显示的字符
                        striped: false,                      //是否显示行间隔色
                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

                        data: tb_data,                         //直接从本地数据初始化表格
                        uniqueId: "url",

                        //分页
                        pagination: false,                   //是否显示分页（*）
                        onlyPagination: true,               //只显示分页 页码
                        pageNumber: 1,                      //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [5, 10, 15],              //可供选择的每页的行数（*）

                        //排序
                        sortable: true,                     //所有列的排序 是否开启
                        sortOrder: "asc",                   //排序方式
                        // silentSort: false,                   //排序方式
                        // sortName: "emp_work_sn",                    //定义排序列,通过url方式获取数据填写字段名，否则填写下标

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
                                field: "url",
                                title: "访问url",
                                align: "center",
                                sortable: true,
                                class: "url",
                                formatter: function (value, row, index) {
                                    // console.log(value);
                                    var html = "";
                                    if (value) {
                                        html = "<div title='" + value + "'>" + value + "</div>";
                                    }

                                    return html;
                                }
                            },
                            {
                                field: "pv",
                                title: "PV",
                                align: "center",
                                sortable: true,
                                class: "pv",
                                formatter: function (value, row, index) {
                                    // console.log(value);
                                    var html = "";
                                    if (value) {
                                        html = "<div title='" + value + "'>" + value + "</div>";
                                    }

                                    return html;
                                }
                            },
                            {
                                field: "pv_percent",
                                title: "占比",
                                align: "center",
                                sortable: true,
                                class: "pv_percent",
                                formatter: function (value, row, index) {
                                    // console.log(value);
                                    var html = "";
                                    if (value) {
                                        value = value.toFixed(2);
                                        html = "<div title='" + value + "'>" + value + "%</div>";
                                    }

                                    return html;
                                }
                            },
                            {
                                field: "uv",
                                title: "UV",
                                align: "center",
                                sortable: true,
                                class: "uv",
                                formatter: function (value, row, index) {
                                    // console.log(value);
                                    var html = "";
                                    if (value) {
                                        html = "<div title='" + value + "'>" + value + "</div>";
                                    }

                                    return html;
                                }
                            },
                            {
                                field: "uv_percent",
                                title: "占比",
                                align: "center",
                                sortable: true,
                                class: "uv_percent",
                                formatter: function (value, row, index) {
                                    // console.log(value);
                                    var html = "";
                                    if (value) {
                                        value = value.toFixed(2);
                                        html = "<div title='" + value + "'>" + value + "%</div>";
                                    }

                                    return html;
                                }
                            }

                        ],

                        formatNoMatches: function () {
                            return '暂无统计信息';
                        }

                    });

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

$(function () {
    access_detail.init();
});