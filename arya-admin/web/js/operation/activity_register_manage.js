/**
 * Created by Administrator on 2017/12/26.
 * 活动报名列表 管理
 */

var $activity_register_manage_container = $(".activity_register_manage_container");
var $tb_activity_register = $activity_register_manage_container.find("#tb_activity_register");//表格id

var activity_register_manage = {

    //初始化
    init: function () {

        activity_register_manage.initActivityNameList();//获取 活动名称列表
        activity_register_manage.btnSearchClick();//

    },
    //获取 活动名称列表
    initActivityNameList: function () {
        var $activity_name_list = $activity_register_manage_container.find(".search_container").find(".activity_name_list");
        $activity_name_list.empty();

        if ($activity_name_list.siblings(".chosen-container").length > 0) {
            $activity_name_list.chosen("destroy");
        }

        aryaGetRequest(
            urlGroup.operation.activity_register.name_list,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result && data.result.length > 0) {

                        for (var i = 0; i < data.result.length; i++) {
                            var value = data.result[i];

                            var $option = $("<option>");
                            // $option.val(key);
                            $option.text(value);
                            $option.appendTo($activity_name_list);

                        }

                    }
                    $activity_name_list.chosen({
                        allow_single_deselect: true,//选择之后 是否可以取消
                        max_selected_options: 1,//最多只能选择1个
                        width: "100%",//select框 宽度
                        no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
                    });

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

    btnSearchClick: function () {
        activity_register_manage.initTb();//订单查询 - 列表
    },

    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (activity_register_param.begin_time && activity_register_param.end_time &&
            activity_register_param.begin_time > activity_register_param.end_time) {
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

        activity_register_param.paramSet();//赋值查询参数

        //检查查询参数是否正确
        if (!activity_register_manage.checkParam()) {
            return;
        }

        $tb_activity_register.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_activity_register.bootstrapTable({

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
                    field: 'createTime',
                    title: '报名时间',
                    align: "center",
                    class: "createTime",
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
                    title: '姓名',
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

                        return html;

                    }
                },
                {
                    field: 'company',
                    title: '所在公司',
                    // sortable: true,
                    align: "center",
                    class: "company",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }

                        return html;

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.operation.activity_register.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                var obj = {

                    begin_time: activity_register_param.begin_time,
                    end_time: activity_register_param.end_time,
                    mobile: activity_register_param.mobile,
                    ac_name: activity_register_param.activity_name,
                    page: params.pageNumber,
                    page_size: params.pageSize

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

                        if (res.result.records) {
                            $.each(res.result.records, function (i, item) {

                                var id = item.id ? item.id : "";//
                                var createTime = item.createTimeStr ? item.createTimeStr : "";//
                                var user_name = item.user_name ? item.user_name : "";//
                                var mobile = item.mobile ? item.mobile : "";//
                                var company = item.company ? item.company : "";//

                                var obj = {

                                    id: id,
                                    createTime: createTime,
                                    user_name: user_name,
                                    mobile: mobile,
                                    company: company

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

    //活动报名列表 导出
    exportList: function () {


        exportWarning(
            "确定要导出该活动吗?",
            function () {

                loadingInit();

                var obj = {
                    begin_time: activity_register_param.begin_time,
                    end_time: activity_register_param.end_time,
                    mobile: activity_register_param.mobile,
                    ac_name: activity_register_param.activity_name
                };
                var url = urlGroup.operation.activity_register.excel_export + "?" + jsonParseParam(obj);

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
var activity_register_param = {

    begin_time: null,                    //开始时间
    end_time: null,                      //结束时间
    mobile: null,                    //手机号
    activity_name: null,                  //活动名称

    //参数赋值
    paramSet: function () {

        var $search_container = $activity_register_manage_container.find(".search_container");

        activity_register_param.begin_time = $.trim($search_container.find(".beginTime").val());
        activity_register_param.begin_time = activity_register_param.begin_time ?
            timeInit3(activity_register_param.begin_time) : "";

        activity_register_param.end_time = $.trim($search_container.find(".endTime").val());
        activity_register_param.end_time = activity_register_param.end_time ?
            timeInit4(activity_register_param.end_time) : "";

        activity_register_param.mobile = $.trim($search_container.find(".user_phone").val());

        var $activity_name_list = $search_container.find(".activity_name_list");
        activity_register_param.activity_name = $activity_name_list.val() ? $activity_name_list.val()[0] : "";

    }

};

$(function () {
    activity_register_manage.init();
});

