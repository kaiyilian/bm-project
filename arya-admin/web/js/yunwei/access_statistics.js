// var $access_statistics_container=$(".access_statistics_container");

var access_statistics = {

    init: function () {

        access_statistics.echartsInit("nHour_load", "nHour", urlGroup.operation_manage.access_statistics.nHour, "line");//最近24h 访问量
        access_statistics.echartsInit("nMonth_load", "nMonth", urlGroup.operation_manage.access_statistics.nMonth, "line");//最近30天 访问量
        access_statistics.echartsInit("eHour_load", "eHour", urlGroup.operation_manage.access_statistics.eHour, "line");//各个时段24小时 访问量
        access_statistics.echartsInit("eWeek_load", "eWeek", urlGroup.operation_manage.access_statistics.eWeek, "line");//每周各天 访问量
        access_statistics.echartsInit("eVersion_load", "eVersion", urlGroup.operation_manage.access_statistics.eVersion, "bar");//各个版本 访问量
        access_statistics.echartsInit("eDevice_load", "eDevice", urlGroup.operation_manage.access_statistics.eDevice, "bar");//各个设备 访问量

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

    }

};

$(function () {
    access_statistics.init();
});