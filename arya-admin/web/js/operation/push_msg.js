/**
 * Created by CuiMengxin on 2016/9/29.
 * 消息推送
 */

var $push_msg_manage_container = $(".push_msg_manage_container");
var $tb_push_msg = $push_msg_manage_container.find("#tb_push_msg");//表格id
var $push_msg_info_modal = $(".push_msg_info_modal");

var notification_manage = {

    current_notification_id: "",//消息id

    //初始化
    init: function () {

        notification_manage.initTb();//订单查询 - 列表

        // 弹框出现 （初始化）
        $push_msg_info_modal.on("show.bs.modal", function () {

            $push_msg_info_modal.find(".modal-title").html("新增推送信息");

            var $row = $push_msg_info_modal.find(".modal-body .row");

            //输入框
            $row.find("input").removeAttr("disabled");
            $row.find("textarea").removeAttr("disabled");

            $row.find("select").removeAttr("disabled");
            $row.find('.chosen-select').trigger("chosen:updated");

            //推送标题
            $row.find(".notification_title").val("");
            //推送内容
            $row.find(".notification_info").val("");
            //URL
            $row.find(".notification_url").val("");

            //默认 不发送给所有人
            $row.find(".btn_all_people").removeClass("btn-primary").addClass("btn-default").siblings().show();
            $row.find(".btn_all_people").unbind("click").bind("click", function () {

                var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
                var ym_tag_arr = $ym_tags_select.val();
                //如果选择了友盟标签，则推送人群就不能选择
                if (ym_tag_arr && ym_tag_arr.length > 0) {

                    toastr.warning("已选择友盟标签，不能选择推送人群！");
                    return

                }

                var $btn_all_people = $row.find(".btn_all_people");

                //检查原来是否是 选择了所有人
                if ($btn_all_people.hasClass("btn-default")) {
                    $btn_all_people.removeClass("btn-default").addClass("btn-primary");
                    $btn_all_people.siblings().hide();

                    //版本
                    var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
                    $ym_tags_select.find("option:selected").removeAttr("selected");//清空选中状态
                    $ym_tags_select.attr("disabled", "disabled");
                    $ym_tags_select.trigger("chosen:updated");

                }
                else {
                    $btn_all_people.removeClass("btn-primary").addClass("btn-default");
                    $btn_all_people.siblings().show();

                    // //清空 选中的公司
                    // $row.find('.chosen-select').val("");
                    // $row.find('.chosen-select').trigger("chosen:updated");
                    //
                    // //清空 选中的标签
                    // $row.find('.radio_select').val("");
                    // $row.find('.radio_select').trigger("chosen:updated");

                    notification_manage.initYmTag();
                    notification_manage.initTagList();

                }

            });

            //隐藏关闭按钮
            $push_msg_info_modal.find(".modal-footer").find(".btn_close").hide().siblings().show();

            notification_manage.initJumpTypeList();//初始化 跳转类型列表
            notification_manage.initTagList();//初始化 推送人群、标签(公司列表、员工归属、计加班、体检)
            notification_manage.initPushType();//初始化 推送类型
            notification_manage.initYmTag();//初始化 友盟标签tag
            notification_manage.initTimeSet();//初始化 定时推送
            notification_manage.initPushCount();//初始化 推送人数

        });

        $push_msg_info_modal.on("shown.bs.modal", function () {

            //如果 是编辑
            if (notification_manage.current_notification_id) {
                notification_manage.notificationDetail();
            }

        });


    },
    //初始化 跳转类型列表
    initJumpTypeList: function () {
        console.log("获取跳转类型：" + new Date().getTime());

        //跳转类型
        var $jump_type_select = $push_msg_info_modal.find(".jump_type_list").find('.jump_type_select');
        $jump_type_select.empty();

        //检查是否 已初始化
        if ($jump_type_select.siblings(".chosen-container").length > 0) {
            $jump_type_select.chosen("destroy");
        }

        aryaGetRequest(
            urlGroup.notification_type_list_get,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var arr = data.result.types ? data.result.types : [];//

                        $.each(arr, function (i, $item) {

                            var id = $item.id ? $item.id : "";//
                            var name = $item.name ? $item.name : "";//

                            var $option = $("<option>");
                            $option.attr("value", id);
                            $option.text(name);
                            $option.appendTo($jump_type_select);

                        });

                    }
                    else {
                        toastr.warning(data.msg);
                    }

                    $jump_type_select.chosen({
                        allow_single_deselect: true,//是否可用取消
                        max_selected_options: 1,//最多只能选择1个
                        width: "100%",
                        no_results_text: "找不到 "
                    });

                    $jump_type_select.chosen().on("change", function (evt, params) {
                        // console.log(params);

                        if (params.selected === "10") {
                            $push_msg_info_modal.find(".notification_url_container").show()
                                .find(".notification_url").val("");
                        }
                        else {
                            $push_msg_info_modal.find(".notification_url_container").hide();
                        }

                    });

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
    //初始化 推送类型
    initPushType: function () {

        $push_msg_info_modal.find(".push_type_list").find(".btn").each(function () {

            var $this = $(this);
            $this.addClass("btn-default").removeClass("btn-primary");

            $this.unbind("click").bind("click", function () {

                //没有选中
                if ($this.hasClass("btn-default")) {
                    $this.removeClass("btn-default").addClass("btn-primary");
                }
                else {
                    $this.addClass("btn-default").removeClass("btn-primary");
                }

            });

        });

    },
    //初始化 友盟标签tag
    initYmTag: function () {
        console.log("获取友盟标签：" + new Date().getTime());

        //版本
        var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
        $ym_tags_select.empty();
        $ym_tags_select.removeAttr("disabled");

        //检查是否 已初始化
        if ($ym_tags_select.siblings(".chosen-container").length > 0) {
            $ym_tags_select.chosen("destroy");
        }

        /**
         * 后期调用接口
         * 目前写死在前端
         * */

            //版本tag数组
        var arr = [
                {
                    "version": "v3.0"
                },
                {
                    "version": "v2.0"
                }
            ];
        $.each(arr, function (i, $item) {

            var version = $item.version ? $item.version : "";

            if (version) {
                var $option = $("<option>");
                $option.text(version);
                $option.appendTo($ym_tags_select);
            }

        });

        //chosen选择器 初始化
        $ym_tags_select.chosen({
            allow_single_deselect: true,//是否可用取消
            // max_selected_options: 1,//最多只能选择1个
            width: "100%",
            no_results_text: "找不到 "
        });
        $ym_tags_select.chosen().on("change", function (evt, params) {
            // console.log(params);

            var ym_tag_arr = $ym_tags_select.val();//

            //如果选择了友盟标签，则推送人群就不能选择
            if (ym_tag_arr && ym_tag_arr.length > 0) {

                var $row = $push_msg_info_modal.find(".modal-body .row");

                //公司标签
                var $corp_select = $row.find(".corp_list").find(".corp_select");//公司列表 select
                $corp_select.find("option:selected").removeAttr("selected");//清空选中状态
                $corp_select.attr("disabled", "disabled");
                $corp_select.trigger("chosen:updated");

                //自定义标签
                var $category_tag = $row.find(".category_tag");//标签 div
                $category_tag.find(".item").each(function () {

                    var $radio_select = $(this).find('.radio_select');
                    $radio_select.find("option:selected").removeAttr("selected");//清空选中状态
                    $radio_select.attr("disabled", "disabled");
                    $radio_select.trigger("chosen:updated");

                });

            }
            else {
                notification_manage.initTagList();//初始化 自定义标签列表 (公司列表、员工归属、计加班、体检)
            }

        });

    },
    //初始化 定时推送
    initTimeSet: function () {

        var $btn_timing = $push_msg_info_modal.find(".btn_timing");

        //推送时间
        $btn_timing.addClass("btn-default").removeClass("btn-primary");
        $btn_timing.unbind("click").bind("click", function () {

            //没有定时
            if ($btn_timing.hasClass("btn-default")) {
                $btn_timing.removeClass("btn-default").addClass("btn-primary");
                $btn_timing.siblings(".setTimeContainer").show().find(".setTime").val("");
            }
            else {
                $btn_timing.removeClass("btn-primary").addClass("btn-default");
                $btn_timing.siblings(".setTimeContainer").hide().find(".setTime").val("");
            }

        });
        $push_msg_info_modal.find(".setTimeContainer").hide().find(".setTime").val("");

    },
    //初始化 自定义标签列表 (公司列表、员工归属、计加班、体检)
    initTagList: function () {
        var $row = $push_msg_info_modal.find(".modal-body .row");
        var $corp_list = $row.find(".corp_list");//公司标签
        var $corp_select = $corp_list.find(".corp_select");//公司列表 select
        $corp_select.removeAttr("disabled");
        $corp_select.empty();
        var $category_tag = $row.find(".category_tag");//标签 div
        $category_tag.empty();//清空

        //检查是否 已初始化
        if ($corp_select.siblings(".chosen-container").length > 0) {
            $corp_select.chosen("destroy");
        }

        loadingInit();

        aryaGetRequest(
            urlGroup.tag_get,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var corp_tag = data.result.corp_tags ? data.result.corp_tags : null;//公司 （特殊的标签）
                        if (corp_tag) {

                            var id = corp_tag.category_id ? corp_tag.category_id : "";//标签类型id
                            var name = corp_tag.category_name ? corp_tag.category_name : "";//标签类型 name
                            $corp_list.attr("data-id", id);
                            $corp_select.attr("data-placeholder", "请选择" + name);

                            var arr = corp_tag.tags ? corp_tag.tags : [];//该标签 包含的数据

                            $.each(arr, function (i, $item) {

                                var id = $item.id ? $item.id : "";
                                var name = $item.name ? $item.name : "";

                                var $option = $("<option>");
                                $option.attr("value", id);
                                $option.text(name);
                                $option.appendTo($corp_select);

                            });

                        }

                        var filter_tags = data.result.filter_tags ? data.result.filter_tags : [];//标签列表
                        $.each(filter_tags, function (i, $filter_tags) {

                            var category_id = $filter_tags.category_id ? $filter_tags.category_id : "";
                            var category_name = $filter_tags.category_name ? $filter_tags.category_name : "";//

                            var tags = $filter_tags.tags ? $filter_tags.tags : [];//每一个标签的内容数组

                            //如果该标签内有数据，则设置一个容器放置，内容是单选
                            if (tags && tags.length > 0) {

                                //标签 item 容器
                                var $category_tag_item = $("<span>");
                                $category_tag_item.addClass("col-xs-6");
                                $category_tag_item.addClass("item");
                                $category_tag_item.attr("data-id", category_id);
                                $category_tag_item.appendTo($category_tag);

                                var $select = $("<select>");
                                $select.addClass("radio_select");
                                $select.addClass("chosen-select");
                                $select.attr("data-placeholder", category_name + "(单选)");
                                $select.attr("multiple", "multiple");
                                $select.appendTo($category_tag_item);

                                $.each(tags, function (j, $tag_item) {

                                    var id = $tag_item.id ? $tag_item.id : "";
                                    var name = $tag_item.name ? $tag_item.name : "";

                                    var $option = $("<option>");
                                    $option.attr("value", id);
                                    $option.text(name);
                                    $option.appendTo($select);

                                });

                            }

                        });

                    }

                    //公司标签 初始化
                    $corp_select.chosen({
                        allow_single_deselect: true,//是否可用取消
                        width: "100%",
                        no_results_text: "找不到 "
                    });
                    $corp_select.chosen().on("change", function (evt, params) {
                        // console.log(params);

                        var corp_arr = $corp_select.val();//

                        //如果选择了公司，则友盟标签就不能选择
                        if (corp_arr && corp_arr.length > 0) {

                            //版本
                            var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
                            $ym_tags_select.find("option:selected").removeAttr("selected");//清空选中状态
                            $ym_tags_select.attr("disabled", "disabled");
                            $ym_tags_select.trigger("chosen:updated");

                        }
                        else {
                            notification_manage.initYmTag();//初始化 友盟标签
                        }

                    });

                    //循环遍历，初始化 自定义标签
                    $category_tag.find(".item").each(function () {

                        var $radio_select = $(this).find('.radio_select');
                        $radio_select.removeAttr("disabled");
                        $radio_select.chosen({
                            allow_single_deselect: true,//是否可用取消
                            max_selected_options: 1,//最多只能选择1个
                            width: "100%",
                            no_results_text: "找不到 "
                        });
                        $radio_select.chosen().on("change", function (evt, params) {
                            // console.log(params);

                            var custom_tag_arr = $radio_select.val();//

                            //如果选择了自定义标签，则友盟标签就不能选择
                            if (custom_tag_arr && custom_tag_arr.length > 0) {

                                //版本
                                var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
                                $ym_tags_select.find("option:selected").removeAttr("selected");//清空选中状态
                                $ym_tags_select.attr("disabled", "disabled");
                                $ym_tags_select.trigger("chosen:updated");

                            }
                            else {
                                notification_manage.initYmTag();//初始化 友盟标签
                            }

                        });

                    });

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
    //初始化 推送人数
    initPushCount: function () {

        var $push_count_container = $push_msg_info_modal.find(".push_count_container");
        $push_count_container.empty();

        //推送人数 按钮初始化
        var $btn_push = $("<div>");
        $btn_push.addClass("btn");
        $btn_push.addClass("btn-sm");
        $btn_push.addClass("btn-primary");
        $btn_push.addClass("btn_search_people_count");
        $btn_push.text("查询");
        $btn_push.appendTo($push_count_container);
        $btn_push.unbind("click").bind("click", function () {

            push_msg_param.filter_tags = [];//默认发送给所有人

            var $row = $push_msg_info_modal.find(".modal-body .row");

            //如果没有 选择所有人
            if ($row.find(".btn_all_people").hasClass("btn-default")) {

                var corp_select = $row.find(".corp_select").val();
                var corp_id = $row.find(".corp_list").data("id");
                notification_manage.initPushTags(corp_id, corp_select);

                $row.find(".category_tag .item").each(function () {
                    var id = $(this).data("id");
                    var select_val = $(this).find("select").val();
                    notification_manage.initPushTags(id, select_val);
                });

                //判断是否选择了标签
                if (push_msg_param.filter_tags.length <= 0) {
                    toastr.warning("请选择标签！");
                    return;
                }

            }

            var obj = {
                filter_tags: push_msg_param.filter_tags
            };

            aryaPostRequest(
                urlGroup.notification_push_user_count,
                obj,
                function (data) {
                    //console.info("获取日志：");
                    //console.info(data);

                    if (data.code === RESPONSE_OK_CODE && data.result) {

                        var count = data.result.count ? data.result.count : 0;
                        msgShow(count + "人");

                    }
                    else {
                        messageCue(data.msg);
                    }

                },
                function (error) {
                    messageCue(error);
                }
            );

        });

    },
    //初始化 推送标签
    initPushTags: function (id, array) {

        if (id && array) {
            var obj = {};
            obj.category_id = id;
            obj.tags = array;

            push_msg_param.filter_tags.push(obj);
        }

    },

    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        $tb_push_msg.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_push_msg.bootstrapTable({

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
                    checkbox: true
                },

                {
                    field: 'send_time',
                    title: '推送时间',
                    align: "center",
                    class: "send_time",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + timeInit1(value) + "'>" + timeInit1(value) + "</div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'msg_title',
                    title: '推送标题',
                    // sortable: true,
                    align: "center",
                    class: "msg_title",
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
                    field: 'msg_content',
                    title: '推送详情',
                    // sortable: true,
                    align: "center",
                    class: "msg_content",
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
                    field: 'status',
                    title: '推送状态',
                    // sortable: true,
                    align: "center",
                    class: "status",
                    // width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (row.status_str) {
                            html = "<div title='" + row.status_str + "'>" + row.status_str + "</div>";
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
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        //查看
                        html += "<div class='btn btn-primary btn-sm btn_modify'>查看</div>";

                        //删除
                        html += "<div class='btn btn-primary btn-sm btn_del'>删除</div>";

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

                            notification_manage.current_notification_id = row.id;

                            $push_msg_info_modal.modal("show");

                        },
                        //删除
                        "click .btn_del": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // console.log(row);

                            delWarning(
                                "确定要删除该条消息吗？",
                                function () {
                                    loadingInit();//加载框 出现

                                    var obj = {
                                        ids: [{
                                            id: row.id
                                        }]
                                    };

                                    aryaPostRequest(
                                        urlGroup.notification_del,
                                        obj,
                                        function (data) {
                                            //alert(JSON.stringify(data));

                                            if (data.code === RESPONSE_OK_CODE) {

                                                toastr.success("删除成功！");

                                                //删除对应数据
                                                $tb_push_msg.bootstrapTable('remove', {
                                                    field: 'id',
                                                    values: [row.id]
                                                });

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

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.notification_list_get,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                var obj = {
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

                        $.each(res.result.notifications, function (i, item) {

                            var id = item.id ? item.id : "";//
                            var send_time = item.send_time ? item.send_time : "";//
                            var title = item.title ? item.title : "--";//
                            var content = item.content ? item.content : "--";//
                            var status = item.status ? item.status : 0;//
                            var status_str = item.status_str ? item.status_str : "";//

                            var obj = {
                                id: id,
                                send_time: send_time,
                                msg_title: title,
                                msg_content: content,
                                status: status,
                                status_str: status_str
                            };
                            tb_data.push(obj);

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

            },

            //选中单行
            onCheck: function (row) {
                notification_manage.checkIsChoose();
            },
            onUncheck: function (row) {
                notification_manage.checkIsChoose();
            },
            onCheckAll: function (rows) {
                notification_manage.checkIsChoose();
            },
            onUncheckAll: function (rows) {
                notification_manage.checkIsChoose();
            }

        });
    },
    //检查是否被选择
    checkIsChoose: function () {

        var data = $tb_push_msg.bootstrapTable("getAllSelections");

        var $btn_list = $push_msg_manage_container.find(".foot .btn_list");
        var $btn_del = $btn_list.find(".btn_del");//删除

        //删除 初始化
        if (data.length > 0) {
            $btn_del.addClass("btn-primary").removeClass("btn-default");
        }
        else {
            $btn_del.addClass("btn-default").removeClass("btn-primary");
        }

    },

    //新增 弹框 显示
    notificationAddModalShow: function () {
        notification_manage.current_notification_id = "";

        $push_msg_info_modal.modal("show");

    },
    //获取消息 详情
    notificationDetail: function () {
        console.log("获取详情：" + new Date().getTime());

        var obj = {
            id: notification_manage.current_notification_id
        };
        var url = urlGroup.notification_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    var id = $item.id;//
                    var title = $item.title ? $item.title : "";//
                    var content = $item.content ? $item.content : "";//
                    var jump_type = $item.jump_type ? $item.jump_type : "";//跳转类型
                    var jump_url = $item.jump_url ? $item.jump_url : "";//
                    var display_type = $item.display_type ? $item.display_type : 0;//推送类型
                    var is_timing = $item.is_timing ? $item.is_timing : 0;//是否定时
                    var set_send_time = $item.set_send_time;//
                    set_send_time = timeInit1(set_send_time);
                    var status = $item.status;//
                    var push_count = $item.push_count ? $item.push_count : 0;//推送人数
                    var filter_tags = $item.filter_tags ? $item.filter_tags : [];//自定义标签 列表
                    var ym_tags = $item.tags ? $item.tags : [];//友盟标签 列表
                    var can_edit = $item.can_edit ? $item.can_edit : 0;//是否可编辑 0 不可编辑 1 可编辑

                    var $row = $push_msg_info_modal.find(".modal-body .row");

                    //标题
                    $push_msg_info_modal.find(".modal-title").html("编辑推送信息");
                    //推送标题
                    $row.find(".notification_title").val(title);
                    //推送内容
                    $row.find(".notification_info").val(content);
                    //跳转类型
                    var $jump_type_select = $row.find(".jump_type_select");
                    $jump_type_select.find("option[value='" + jump_type + "']").attr("selected", "selected");
                    $jump_type_select.trigger("chosen:updated");
                    //url，如果是跳转到外部链接
                    if (jump_type === 10) {
                        $push_msg_info_modal.find(".modal-body").find(".notification_url_container").show();
                        $row.find(".notification_url").val(jump_url);
                    }
                    //推送类型
                    $row.find(".push_type_list .btn").each(function () {

                        var $this = $(this);
                        var type = parseInt($this.attr("data-type"));

                        if (type & display_type) {
                            $this.addClass("btn-primary").removeClass("btn-default");
                        }
                        else {
                            $this.addClass("btn-default").removeClass("btn-primary");
                        }

                    });
                    //是否定时
                    if (is_timing) {
                        $row.find(".btn_timing").addClass("btn-primary").removeClass("btn-default");
                        $row.find(".setTimeContainer").show();
                        $row.find(".setTimeContainer").find(".setTime").val(set_send_time);
                    }
                    //友盟标签
                    if (ym_tags.length > 0) {

                        var $ym_tags = $row.find(".ym_tags");
                        for (var keyName in ym_tags) {

                            var val = ym_tags[keyName]["tag"];
                            $ym_tags.find(".ym_tags_select option[value='" + val + "']")
                                .attr("selected", "selected");

                        }
                        $ym_tags.find(".ym_tags_select").trigger("chosen:updated");

                    }
                    //自定义标签 赋值
                    else if (filter_tags.length > 0) {

                        //所有人 默认不选择
                        $row.find(".btn_all_people").addClass("btn-default").removeClass("btn-primary");
                        $row.find(".btn_all_people").siblings().show();

                        //已经输入的标签 放入map
                        var map = new Map();
                        for (var i = 0; i < filter_tags.length; i++) {
                            var $item = filter_tags[i];

                            var category_id = $item.category_id;
                            var tags = $item.tags;
                            map.put(category_id, tags);

                        }

                        //判断是否有 公司
                        var $corp_list = $row.find(".corp_list");
                        var corp_id = $corp_list.data("id");//公司 标签id
                        if (map.keySet().indexOf(corp_id) > -1) {	//如果有公司

                            var array = map.get(corp_id);

                            for (var j = 0; j < array.length; j++) {
                                $corp_list.find(".corp_select option[value='" + array[j] + "']")
                                    .attr("selected", "selected");
                            }

                        }
                        $corp_list.find(".corp_select").trigger("chosen:updated");

                        //自定义标签 check是否存在
                        $row.find(".category_tag > .item").each(function () {

                            var id = $(this).data("id");//标签id
                            if (map.keySet().indexOf(id) > -1) {	//如果有 该标签

                                var array = map.get(id);

                                for (var j = 0; j < array.length; j++) {
                                    $(this).find("select option[value='" + array[j] + "']").attr("selected", "selected");
                                }

                            }

                        });
                        $row.find(".category_tag").find(".chosen-select").trigger("chosen:updated");

                    }
                    else if (filter_tags.length === 0) {		//推送给所有人
                        $row.find(".btn_all_people").addClass("btn-primary").removeClass("btn-default");
                        $row.find(".btn_all_people").siblings().hide();
                    }

                    //不可编辑
                    if (can_edit === 0) {

                        //标题
                        $push_msg_info_modal.find(".modal-title").html("查看推送信息");

                        //输入框
                        $row.find("input").attr("disabled", "disabled");
                        $row.find("textarea").attr("disabled", "disabled");

                        //chosen 选择框默认都不能选择
                        $row.find("select").attr("disabled", "disabled");
                        $row.find('.chosen-select').trigger("chosen:updated");

                        //推送类型
                        $row.find(".push_type_list .btn").unbind("click");

                        //定时
                        $row.find(".btn_timing").unbind("click");

                        //所有人
                        $row.find(".btn_all_people").unbind("click");

                        //推送人数
                        $row.find(".push_count_container").empty().append(push_count);

                        //显示关闭按钮
                        $push_msg_info_modal.find(".modal-footer").find(".btn_close").show().siblings().hide();

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

    //删除 （多个删除）
    notificationDel: function () {

        delWarning(
            "确定要删除选择的消息吗？",
            function () {
                loadingInit();//加载框 出现

                var data = $tb_push_msg.bootstrapTable("getAllSelections");//已选择的数据

                var ids = [];//删除的数组
                $.each(data, function (i, $item) {
                    var id = $item.id ? $item.id : "";//
                    var obj = {id: id};

                    ids.push(obj);
                });

                var obj = {
                    ids: ids
                };

                aryaPostRequest(
                    urlGroup.notification_del,
                    obj,
                    function (data) {
                        //alert(JSON.stringify(data));

                        if (data.code === RESPONSE_OK_CODE) {
                            toastr.success("删除成功！");

                            // notification_manage.initTb();//列表

                            //要删除的id数组
                            var values = [];
                            $.each(ids, function (i, item) {
                                values.push(item.id);
                            });

                            //删除对应数据
                            $tb_push_msg.bootstrapTable('remove', {
                                field: 'id',
                                values: values
                            });

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

    },

    //消息 保存
    notificationSave: function () {
        loadingInit();

        push_msg_param.paramSet();//参数 赋值

        //检查 参数
        if (!notification_manage.checkParamsBySave()) {
            loadingRemove();
            return;
        }

        var obj = {
            id: notification_manage.current_notification_id,
            title: push_msg_param.title,
            content: push_msg_param.content,
            jump_type: push_msg_param.jump_type,
            jump_url: push_msg_param.jump_url,
            display_type: push_msg_param.push_type,
            send_time: push_msg_param.send_time,
            filter_tags: push_msg_param.filter_tags,
            tags: push_msg_param.ym_tags
        };

        aryaPostRequest(
            urlGroup.notification_add_or_modify,
            obj,
            function (data) {
                //alert(JSON.stringify(data));
                console.log("保存或编辑消息：" + new Date().getTime());

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");
                    $push_msg_info_modal.modal("hide");

                    notification_manage.initTb();//获取消息列表
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
    //检查 参数
    checkParamsBySave: function () {
        var flag = false;
        var txt = "";

        var $modal_body = $push_msg_info_modal.find(".modal-body");

        if (!push_msg_param.title) {
            txt = "请输入标题！";
        }
        else if (!push_msg_param.content) {
            txt = "请输入推送信息！";
        }
        else if (!push_msg_param.jump_type) {
            txt = "请选择跳转类型！";
        }
        else if (push_msg_param.jump_type === "10" && !push_msg_param.jump_url) {
            txt = "请选择跳转URL！";
        }
        else if (push_msg_param.push_type === 0) {
            txt = "请选择推送类型！";
        }
        else if ($modal_body.find(".btn_timing").hasClass("btn-primary") && !push_msg_param.send_time) {
            txt = "请输入定时时间！";
        }
        else if ($modal_body.find(".btn_all_people").hasClass("btn-default") &&
            push_msg_param.filter_tags.length <= 0 && push_msg_param.ym_tags.length <= 0) {
            txt = "请选择标签！";
        }
        else {
            flag = true;
        }

        if (txt) {
            messageCue(txt);
        }

        return flag;

    }

};
//保存时 参数
var push_msg_param = {
    // id: null,
    title: null,            //标题
    content: null,          //内容
    jump_type: null,        //跳转类型
    jump_url: null,         //跳转url （特殊跳转类型时赋值）
    push_type: null,        //推送类型
    ym_tags: null,          //标签
    send_time: null,        //发送时间
    filter_tags: null,      //标签 数组

    //参数赋值
    paramSet: function () {

        var $row = $push_msg_info_modal.find(".modal-body .row");

        push_msg_param.title = $.trim($row.find(".notification_title").val());
        push_msg_param.content = $.trim($row.find(".notification_info").val());
        push_msg_param.jump_type = $row.find(".jump_type_select").val()
            ? $row.find(".jump_type_select").val()[0] : "";
        push_msg_param.jump_url = $.trim($row.find(".notification_url").val());

        push_msg_param.push_type = 0;
        $row.find(".push_type_list .btn-primary").each(function () {

            var type = parseInt($(this).attr("data-type"));
            push_msg_param.push_type = push_msg_param.push_type | type;

        });

        push_msg_param.send_time = $.trim($row.find(".setTime").val());
        push_msg_param.send_time = push_msg_param.send_time ? (new Date(push_msg_param.send_time).getTime()) : "";

        //友盟标签
        push_msg_param.ym_tags = [];
        var $ym_tags_select = $push_msg_info_modal.find(".ym_tags").find('.ym_tags_select');
        var ym_tag_arr = $ym_tags_select.val();//
        if (ym_tag_arr && ym_tag_arr.length > 0) {
            for (var keyName in ym_tag_arr) {

                push_msg_param.ym_tags.push({
                    tag: ym_tag_arr[keyName]
                });

            }
        }

        //如果没有 选择所有人
        push_msg_param.filter_tags = [];
        if ($row.find(".btn_all_people").hasClass("btn-default")) {

            var corp_select = $row.find(".corp_select").val();
            var corp_id = $row.find(".corp_list").data("id");
            notification_manage.initPushTags(corp_id, corp_select);

            $row.find(".category_tag .item").each(function () {
                var id = $(this).data("id");
                var select_val = $(this).find("select").val();
                notification_manage.initPushTags(id, select_val);
            });

        }

    }

};

$(function () {
    notification_manage.init();
});

