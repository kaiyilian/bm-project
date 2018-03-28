/**
 * Created by Administrator on 2018/1/11.
 * 运维管理 - 日志管理
 */

var $log_manage_container = $(".log_manage_container");//日志container
var $tb_yunwei_log = $log_manage_container.find("#tb_yunwei_log");//日志表格

var log_manage = {

    //初始化
    init: function () {

        log_manage.initProjectList();//获取应用程序列表
        log_manage.btnSearchClick();//

    },
    //获取应用程序列表
    initProjectList: function () {
        var $project_list = $log_manage_container.find(".search_container").find(".project_list");
        $project_list.empty();

        if ($project_list.siblings(".chosen-container").length > 0) {
            $project_list.chosen("destroy");
        }

        aryaGetRequest(
            urlGroup.operation_manage.log.project_list,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result && data.result.length > 0) {

                        for (var i = 0; i < data.result.length; i++) {
                            var $item = data.result[i];

                            var key = $item.projectCode ? $item.projectCode : "";//
                            var value = $item.projectName ? $item.projectName : "";//

                            var $option = $("<option>");
                            $option.val(key);
                            $option.text(value);
                            $option.appendTo($project_list);

                        }

                        $project_list.chosen({
                            allow_single_deselect: true,//选择之后 是否可以取消
                            max_selected_options: 1,//最多只能选择1个
                            width: "100%",//select框 宽度
                            no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
                        });

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

    },

    btnSearchClick: function () {
        log_manage.initTb();//订单查询 - 列表
    },
    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        $tb_yunwei_log.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_yunwei_log.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            // data: data,                         //直接从本地数据初始化表格
            // uniqueId: "id",

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
                    field: 'fileName',
                    title: '文件名',
                    align: "center",
                    class: "fileName",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'fileSize',
                    title: '文件大小',
                    align: "center",
                    class: "fileSize",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'fileDate',
                    title: '时间',
                    align: "center",
                    class: "fileDate",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + timeInit1(value) + "</div>";
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

                        var html = "";
                        html += "<div class='operate'>";

                        //查看明细
                        html += "<div class='btn btn-success btn-sm btn_down'>下载</div>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //下载
                        "click .btn_down": function (e, value, row, index) {

                            var $project_list = $log_manage_container.find(".search_container").find(".project_list");
                            var val = $project_list.val() ? $project_list.val()[0] : "";

                            var obj = {
                                fileName: row.fileName,
                                projectCode:val,
                                fileDate: row.fileDate,
                                filePart: row.filePart
                            };

                            aryaPostRequest(
                                urlGroup.operation_manage.log.down,
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

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.operation_manage.log.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);
                var $project_list = $log_manage_container.find(".search_container").find(".project_list");
                var val = $project_list.val() ? $project_list.val()[0] : "";

                var obj = {

                    projectCode: val,
                    page: params.pageNumber,
                    page_size: params.pageSize

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
                var total_rows = 0;//总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows ? res.result.total_rows : 0;//总条数

                        if (res.result.onlineLogResults) {
                            $.each(res.result.onlineLogResults, function (i, item) {

                                var fileName = item.fileName ? item.fileName : "";//
                                var fileDate = item.fileDate ? item.fileDate : "";//
                                var filePart = item.filePart ? item.filePart : "";//
                                var fileSize = item.fileSize ? item.fileSize : "";//

                                var obj = {

                                    fileName: fileName,
                                    fileDate: fileDate,
                                    filePart: filePart,
                                    fileSize: fileSize

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
    }

};

$(function () {
    log_manage.init();
});
