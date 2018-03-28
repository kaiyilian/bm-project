/**
 * Created by Administrator on 2017/11/28.
 * 首页
 */

var $index_manage_container = $(".index_manage_container");//

var index = {

    init: function () {

        index.echartsInit("nHour_load", "nHour", urlGroup.nHour_statistic, "line");//最近24h 访问量
        index.echartsInit("nMonth_load", "nMonth", urlGroup.nMonth_statistic, "line");//最近30天 访问量
        index.echartsInit("eHour_load", "eHour", urlGroup.eHour_statistic, "line");//各个时段24小时 访问量
        index.echartsInit("eWeek_load", "eWeek", urlGroup.eWeek_statistic, "line");//每周各天 访问量
        index.echartsInit("eVersion_load", "eVersion", urlGroup.eVersion_statistic, "bar");//各个版本 访问量
        index.echartsInit("eDevice_load", "eDevice", urlGroup.eDevice_statistic, "bar");//各个设备 访问量

        index.initTbStatistic();//访问url 统计

    },

    //初始化 echarts
    echartsInit: function (load_id, id, url, statistic_type) {

        load_id = "#" + load_id;
        showHUD(load_id);

        aryaGetRequest(
            url,
            function (data) {

                dismissHUD(load_id);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {
                        // console.log(data.result);
                        // debugger

                        var $result = data.result;
                        var title = $result.chart_name ? $result.chart_name : "";//图表名称

                        //x轴坐标
                        var x_data = [];
                        if ($result.dimension && $result.dimension.values) {
                            // x_data = $result.dimension.values ? $result.dimension.values : [];
                            $.each($result.dimension.values, function (i, item) {

                                var val = item ? item : "未知";
                                x_data.push(val);

                            });

                        }

                        var legend_data = [];//分类名称 列表
                        var series = [];//具体数据 列表
                        if ($result.data && $result.data.length > 0) {

                            $.each($result.data, function (i, $item) {

                                var name = $item.name ? $item.name : "";//名称
                                var values = $item.values ? $item.values : [];//数组

                                legend_data.push(name);

                                var obj = {
                                    name: name,
                                    type: statistic_type,
                                    // stack: '总量',
                                    data: values,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    }
                                };
                                series.push(obj);

                            });

                        }

                        var option;//选项
                        if (statistic_type === "line") {    //折线图
                            option = {
                                title: {
                                    text: title
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: legend_data
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                toolbox: {
                                    feature: {
                                        saveAsImage: {}
                                    }
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: x_data
                                },
                                yAxis: {
                                    type: 'value'
                                },
                                series: series
                            };
                        }
                        if (statistic_type === "bar") {     //柱状图
                            option = {
                                title: {
                                    text: title
                                },
                                tooltip: {
                                    trigger: 'axis',
                                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                    }
                                },
                                legend: {
                                    data: legend_data
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                xAxis: [
                                    {
                                        type: 'category',
                                        data: x_data
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: series
                            };
                        }

                        //如果x轴有数据
                        if (x_data.length > 0) {

                            $("#" + id).css("height", "400px");//动态设置 图表高度

                            // 基于准备好的dom，初始化 echarts 实例并绘制图表。
                            echarts.init(document.getElementById(id)).setOption(option);

                        }

                    }
                    else {
                        toastr.warning(data.msg);
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

    },


    //访问url 统计
    initTbStatistic: function () {

        var $tb = $index_manage_container.find("#tb_url_statistic");

        aryaGetRequest(
            urlGroup.eUrl_statistic,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var name = data.result.chart_name ? data.result.chart_name : "";
                    if (name)
                        $index_manage_container.find(".url_statistic .name").text(name);


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
    index.init();
});

var debug = {

    //最近24h 访问量
    nHour: function () {

        aryaGetRequest(
            urlGroup.nHour_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var $result = data.result;
                        var title = $result.chartName ? $result.chartName : "";//图表名称

                        //x轴坐标
                        var x_data = [];
                        if ($result.dimension) {
                            x_data = $result.dimension.values ? $result.dimension.values : [];
                        }

                        var legend_data = [];//分类名称 列表
                        var series = [];//具体数据 列表
                        if ($result.data && $result.data.length > 0) {

                            $.each($result.data, function (i, $item) {

                                var name = $item.name ? $item.name : "";//名称
                                var values = $item.values ? $item.values : [];//数组

                                legend_data.push(name);

                                var obj = {
                                    name: name,
                                    type: 'line',
                                    stack: '总量',
                                    data: values,
                                    markPoint: {
                                        data: [
                                            {type: 'max', name: '最大值'},
                                            {type: 'min', name: '最小值'}
                                        ]
                                    }
                                };
                                series.push(obj);

                            });

                        }


                        var option = {
                            title: {
                                text: title
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data: legend_data
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: x_data
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: series
                        };

                        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
                        echarts.init(document.getElementById('nHour')).setOption(option);

                    }
                    else {
                        toastr.warning(data.msg);
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

    },

    //最近30天 访问量
    nMonth: function () {

        aryaGetRequest(
            urlGroup.nMonth_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        //初始化 echarts
                        index.echartsInit(data.result, "nMonth", "line");

                    }
                    else {
                        toastr.warning(data.msg);
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

        return;

        var option = {
            title: {
                text: '最近30天访问量'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['PV', 'UV']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'PV',
                    type: 'line',
                    stack: '总量',
                    data: [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name: 'UV',
                    type: 'line',
                    stack: '总量',
                    data: [220, 182, 191, 234, 290, 330, 310]
                }
            ]
        };

        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
        echarts.init(document.getElementById('nMonth')).setOption(option);
    },

    //各个时段24小时 访问量
    eHour: function () {

        aryaGetRequest(
            urlGroup.eHour_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        //初始化 echarts
                        index.echartsInit(data.result, "eHour", "line");

                    }
                    else {
                        toastr.warning(data.msg);
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

        return;

        var option = {
            title: {
                text: '各个时段访问量'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['PV', 'UV']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'PV',
                    type: 'line',
                    stack: '总量',
                    data: [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name: 'UV',
                    type: 'line',
                    stack: '总量',
                    data: [220, 182, 191, 234, 290, 330, 310]
                }
            ]
        };

        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
        echarts.init(document.getElementById('eHour')).setOption(option);
    },

    //每周各天 访问量
    eWeek: function () {
        aryaGetRequest(
            urlGroup.eWeek_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        //初始化 echarts
                        index.echartsInit(data.result, "eWeek", "line");

                    }
                    else {
                        toastr.warning(data.msg);
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

        return;


        var option = {
            title: {
                text: '每周各天访问量'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['PV', 'UV']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'PV',
                    type: 'line',
                    stack: '总量',
                    data: [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name: 'UV',
                    type: 'line',
                    stack: '总量',
                    data: [220, 182, 191, 234, 290, 330, 310]
                }
            ]
        };

        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
        echarts.init(document.getElementById('eWeek')).setOption(option);

    },

    //各个版本 访问量（柱状图）
    eVersion: function () {

        aryaGetRequest(
            urlGroup.eVersion_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        //初始化 echarts
                        index.echartsInit(data.result, "eVersion", "bar");

                    }
                    else {
                        toastr.warning(data.msg);
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

        return;

        var option = {
            title: {
                text: '各个版本访问量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['PV', 'UV']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['2.21', '2.22', '2.23', '2.3', '2.31', '2.32', '2.4', '2.5', '2.6', '2.7']
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'PV',
                    type: 'bar',
                    data: [320, 332, 301, 334, 390, 330, 320, 390, 330, 320],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    }
                },
                {
                    name: 'UV',
                    type: 'bar',
                    stack: '广告',
                    data: [120, 132, 101, 134, 90, 230, 210, 220, 182, 191],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    }
                }
            ]
        };

        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
        echarts.init(document.getElementById('eVersion')).setOption(option);

    },

    //各个设备 访问量（柱状图）
    eDevice: function () {

        aryaGetRequest(
            urlGroup.eDevice_statistic,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        //初始化 echarts
                        index.echartsInit(data.result, "eDevice", "bar");

                    }
                    else {
                        toastr.warning(data.msg);
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

        return;

        var option = {
            title: {
                text: '各个设备访问量'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['PV', 'UV']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['ios', 'android', '平板', '其他']
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'PV',
                    type: 'bar',
                    data: [320, 390, 330, 320],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    }
                },
                {
                    name: 'UV',
                    type: 'bar',
                    stack: '广告',
                    data: [90, 230, 210, 182],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    }
                }
            ]
        };

        // 基于准备好的dom，初始化 echarts 实例并绘制图表。
        echarts.init(document.getElementById('eDevice')).setOption(option);

    },
};

