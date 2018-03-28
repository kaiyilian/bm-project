//3.8活动

var $women_day_activity_container = $(".women_day_activity_container");
var $tb_women_day_activity = $women_day_activity_container.find("#tb_women_day_activity");//表格id

var women_day_activity = {

    //初始化
    init: function () {

        women_day_activity.btnSearchClick();//

    },

    btnSearchClick: function () {
        women_day_activity.initTb();//列表
    },

    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (women_day_activity_param.begin_time && women_day_activity_param.end_time &&
            women_day_activity_param.begin_time > women_day_activity_param.end_time) {
            txt = "开始时间不能大于结束时间！";
        }
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

        women_day_activity_param.paramSet();//赋值查询参数

        //检查查询参数是否正确
        if (!women_day_activity.checkParam()) {
            return;
        }

        $tb_women_day_activity.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_women_day_activity.bootstrapTable({

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
                    field: 'submitTime',
                    title: '提交时间',
                    align: "center",
                    class: "submitTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            value = timeInit1(value);
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
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
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'mobile',
                    title: '手机号码',
                    // sortable: true,
                    align: "center",
                    class: "mobile",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'sex',
                    title: '性别',
                    align: "center",
                    class: "sex",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'company',
                    title: '所属项目',
                    align: "center",
                    class: "company",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'receiver',
                    title: '祝福对象',
                    align: "center",
                    class: "receiver",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'bless_content',
                    title: '祝福内容',
                    align: "center",
                    class: "bless_content",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'img_src',
                    title: '图片',
                    align: "center",
                    class: "img_src",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {

                            value = value.split(",")[0];

                            html = "<div>" +
                                "<img src='" + value + "'>" +
                                "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'operate',
                    title: '操作',
                    align: "center",
                    class: "operate",
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        //不显示在前台
                        if (row.is_show === 0) {
                            html += "<button class='btn btn-primary btn-sm btn_show'>前台显示</button>";
                        }

                        //显示在前台
                        if (row.is_show === 1) {
                            html += "<button class='btn btn-danger btn-sm btn_hide'>前台隐藏</button>";
                        }

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //前台 显示
                        "click .btn_show": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // news_manage_param.id = row.id;

                            operateShow(
                                "确定要在前台显示吗？",
                                function () {

                                    loadingInit();

                                    var obj = {
                                        id: row.id,
                                        is_show: 1
                                    };
                                    var url = urlGroup.operation.women_day_activity.modify + "?" + jsonParseParam(obj);

                                    aryaGetRequest(
                                        url,
                                        function (res) {

                                            if (res.code === RESPONSE_OK_CODE) {

                                                $tb_women_day_activity.bootstrapTable("updateRow", {
                                                    index: row.index,
                                                    row: {
                                                        is_show: 1
                                                    }
                                                })

                                            }
                                            else {
                                                toastr.warning(data.msg);
                                            }

                                        },
                                        function (error) {
                                        }
                                    );

                                }
                            )

                        },
                        //前台 隐藏
                        "click .btn_hide": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // news_manage_param.id = row.id;

                            operateShow(
                                "确定要在前台隐藏吗？",
                                function () {

                                    loadingInit();

                                    var obj = {
                                        id: row.id,
                                        is_show: 0
                                    };
                                    var url = urlGroup.operation.women_day_activity.modify + "?" + jsonParseParam(obj);

                                    aryaGetRequest(
                                        url,
                                        function (res) {

                                            if (res.code === RESPONSE_OK_CODE) {

                                                $tb_women_day_activity.bootstrapTable("updateRow", {
                                                    index: row.index,
                                                    row: {
                                                        is_show: 0
                                                    }
                                                })

                                            }
                                            else {
                                                toastr.warning(data.msg);
                                            }

                                        },
                                        function (error) {
                                        }
                                    );

                                }
                            )

                        }


                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.operation.women_day_activity.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                return {

                    begin_time: women_day_activity_param.begin_time,
                    end_time: women_day_activity_param.end_time,
                    mobile: women_day_activity_param.mobile,
                    project: women_day_activity_param.project_name,
                    page: params.pageNumber,
                    page_size: params.pageSize

                };
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

                        if (res.result.records) {
                            $.each(res.result.records, function (i, item) {

                                var id = item.id ? item.id : "";//
                                var submitTime = item.create_time ? item.create_time : "";//提交时间
                                var user_name = item.blessing_from ? item.blessing_from : "";//
                                var mobile = item.mobile ? item.mobile : "";//
                                var sex = item.gender ? item.gender : "";//
                                var company = item.project ? item.project : "";//
                                var receiver = item.blessing_to ? item.blessing_to : "";//祝福对象
                                var bless_content = item.blessing_conent ? item.blessing_conent : "";//祝福内容
                                var img_src = item.images ? item.images : "";//图片路径
                                var is_show = item.is_show ? item.is_show : 0;//是否显示

                                var obj = {

                                    index: i,
                                    id: id,
                                    submitTime: submitTime,
                                    user_name: user_name,
                                    mobile: mobile,

                                    sex: sex,
                                    company: company,
                                    receiver: receiver,
                                    bless_content: bless_content,
                                    img_src: img_src,
                                    is_show: is_show

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

    //导出
    exportList: function () {

        exportWarning(
            "确定要导出该活动吗?",
            function () {

                loadingInit();

                var obj = {
                    begin_time: women_day_activity_param.begin_time,
                    end_time: women_day_activity_param.end_time,
                    mobile: women_day_activity_param.mobile,
                    project: women_day_activity_param.project_name
                };
                var url = urlGroup.operation.women_day_activity.excel_export + "?" + jsonParseParam(obj);

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
                            else {
                                toastr.warning("无法下载，下载链接为空！");
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
        );

    }

};
//保存时 参数
var women_day_activity_param = {

    begin_time: null,                    //开始时间
    end_time: null,                      //结束时间
    mobile: null,                    //手机号
    project_name: null,                  //项目名称

    //参数赋值
    paramSet: function () {

        var $search_container = $women_day_activity_container.find(".search_container");

        women_day_activity_param.begin_time = $.trim($search_container.find(".beginTime").val());
        women_day_activity_param.begin_time = women_day_activity_param.begin_time ?
            timeInit3(women_day_activity_param.begin_time) : "";

        women_day_activity_param.end_time = $.trim($search_container.find(".endTime").val());
        women_day_activity_param.end_time = women_day_activity_param.end_time ?
            timeInit4(women_day_activity_param.end_time) : "";

        women_day_activity_param.mobile = $.trim($search_container.find(".user_phone").val());

        women_day_activity_param.project_name = $.trim($search_container.find(".project_name").val());

    }

};

$(function () {
    women_day_activity.init();
});
