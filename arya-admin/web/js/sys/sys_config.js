/**
 * Created by user on 2017/2/14.
 * 配置管理
 */

var $sys_config_manage_container = $(".sys_config_manage_container");
var $tb_config_manage = $sys_config_manage_container.find("#tb_config_manage");//表格

var $sys_config_modal = $(".sys_config_modal");//配置 modal

var sys_config_manage = {

    cur_operate: "add",//add   modify
    row: null,//

    init: function () {

        sys_config_manage.initTb();
        $sys_config_modal.on("show.bs.modal", function () {

            var $modal_body = $sys_config_modal.find(".modal-body");
            var $row = $modal_body.find(".row");
            var $key_container = $modal_body.find(".key_container");

            $row.find("input").val("");
            $row.find("textarea").val("");
            $row.find("select").val("no");

            //如果是新增
            if (sys_config_manage.cur_operate === "add") {
                // $key_container.show();
                $sys_config_modal.find(".modal-title").html("新增配置");

                $row.find(".key").removeAttr("disabled");

            }
            else {
                // $key_container.hide();
                $sys_config_modal.find(".modal-title").html("编辑配置");

                $row.find(".key").val(sys_config_manage.row.key).attr("disabled", "disabled");
                $row.find(".value").val(sys_config_manage.row.value);
                $row.find(".memo").val(sys_config_manage.row.memo);
                $row.find(".manage_cost select").val(sys_config_manage.row.is_deprecated);
            }


        });

    },

    //初始化 表格
    initTb: function () {
        $tb_config_manage.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_config_manage.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: false,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 6,                       //每页的记录行数（*）
            pageList: [6, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            height: 600,
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
                    field: 'index',
                    title: '序号',
                    align: "center",
                    class: "index",
                    width: 200,
                    formatter: function (value, row, index) {

                        return "<div>" + (index + 1) + "</div>";
                    }
                },

                {
                    field: 'key',
                    title: 'key',
                    // sortable: true,
                    align: "center",
                    class: "key",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'value_abbreviate',
                    title: 'value',
                    // sortable: true,
                    align: "center",
                    class: "value",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'memo',
                    title: '备注',
                    // sortable: true,
                    align: "center",
                    class: "remark",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'is_deprecated',
                    title: '废弃',
                    // sortable: true,
                    align: "center",
                    class: "is_deprecated",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'operate',
                    title: '操作',
                    // sortable: true,
                    align: "center",
                    class: "operate",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        html += "<div class='btn btn-primary btn-sm btn_modify'>编辑</div>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //编辑
                        "click .btn_modify": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // console.log(row);

                            sys_config_manage.cur_operate = "modify";
                            sys_config_manage.row = row;

                            $sys_config_modal.modal("show");

                        }

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.sys_config_list_get_url,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                var obj = {};

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
                        total_rows = res.result.configs.length;//总条数

                        $.each(res.result.configs, function (i, item) {

                            item["index"] = i;
                            tb_data.push(item);

                        });

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

    //保存配置
    sysConfigSave: function () {

        //检查参数
        if (!sys_config_manage.checkParams()) {
            return;
        }

        loadingInit();

        var $modal_body = $sys_config_modal.find(".modal-body");
        var $row = $modal_body.find(".row");

        var obj = {
            value: $.trim($row.find(".value").val()),
            memo: $.trim($row.find(".memo").val()),
            is_deprecated: $row.find(".manage_cost select").val()
        };
        var url;

        //如果是新增
        if (sys_config_manage.cur_operate === "add") {
            url = urlGroup.sys_config_add;
            obj["key"] = $.trim($row.find(".key").val());
        }
        else {
            url = urlGroup.sys_config_modify;
            obj["id"] = sys_config_manage.row.id;
        }

        aryaPostRequest(
            url,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    $sys_config_modal.modal("hide");

                    //如果是新增
                    if (sys_config_manage.cur_operate === "modify") {

                        $tb_config_manage.bootstrapTable("updateRow", {
                            index: sys_config_manage.row.index,
                            row: {
                                // key: obj["key"],
                                value: obj["value"],
                                memo: obj["memo"],
                                is_deprecated: obj["is_deprecated"]
                            }
                        });

                    }
                    else {
                        sys_config_manage.initTb();
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

    },
    //检查参数
    checkParams: function () {
        var flag = false;
        var txt = "";

        var $modal_body = $sys_config_modal.find(".modal-body");
        var $row = $modal_body.find(".row");


        var key = $row.find(".key").val();
        var value = $row.find(".value").val();
        var memo = $row.find(".memo").val();
        // var is_deprecated = $row.find(".manage_cost select").val();

        if (sys_config_manage.cur_operate === "add" && !key) {
            txt = "key不能为空！";
        }
        if (!value) {
            txt = "value不能为空！";
        }
        else if (!memo) {
            txt = "描述不能为空！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;
    },

    //新增弹框
    sysConfigAddModalShow: function () {

        sys_config_manage.cur_operate = "add";
        sys_config_manage.row = null;
        $sys_config_modal.modal("show");

    }

};

$(function () {
    sys_config_manage.init();
});

