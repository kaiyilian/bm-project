/**
 * 员工配置
 * */

var $emp_setting_container = $(".emp_setting_container");
var $common_set_modal = $(".common_set_modal");//通用弹框
var $work_sn_prefix_modal = $(".work_sn_prefix_modal");//工号设置 弹框
var $roster_custom_modal = $(".roster_custom_modal");//花名册自定义 弹框

var emp_setting = {
    max: 30,//最多可以设置的数量

    init: function () {

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {

            var href = $(this).attr("data-href");
            if (href === "work_line") {
                work_line.init();//初始化
            }

            if (href === "work_shift") {
                work_shift.init();//初始化
            }

            if (href === "position") {
                position.init();//初始化
            }

            if (href === "work_sn_prefix") {
                work_sn_prefix.init();//初始化
            }

            if (href === "roster_custom") {
                roster_custom.init();//初始化
            }

            if (href === "leave_reason") {
                leave_reason.init();//初始化
            }

            if (href === "message") {
                message.init();//初始化
            }


        });

        $('a[href="#work_line"]').tab('show'); //默认显示

    },

    //公共弹框 初始化
    initCommonSetModal: function (title, txt, name, clickFn, isBad) {

        $common_set_modal.modal("show");
        $common_set_modal.find(".modal-title").text(title);
        $common_set_modal.find(".modal-body > .row > .txt").text(txt + "：");
        $common_set_modal.find(".modal-body .name input").val(name);

        $common_set_modal.find(".modal-footer .btn_save").unbind("click").bind("click", function () {
            clickFn();
        });

        if (txt.indexOf("离职原因") > -1) {
            $common_set_modal.find(".leave_reason_container").removeClass("hide");

            var $is_bad_container = $common_set_modal.find(".leave_reason_container .is_bad_container");

            if (isBad) {
                $is_bad_container.addClass("active");
                $is_bad_container.find("img").attr("src", "image/Choosed.png");
            }
            else {
                $is_bad_container.removeClass("active");
                $is_bad_container.find("img").attr("src", "image/UnChoose.png");
            }
        }
        else {
            $common_set_modal.find(".leave_reason_container").addClass("hide");
        }

    },

    //初始化 列表
    initTb: function ($tb, columns, data) {

        $tb.bootstrapTable("destroy");
        //表格的初始化
        $tb.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

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
            // height: 330,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: columns

        });

    },

    //字段删除
    paramDel: function (txt, url, obj, fn) {

        delWarning(
            txt,
            function () {
                loadingInit();

                branPostRequest(
                    url,
                    obj,
                    function (res) {

                        if (res.code === RESPONSE_OK_CODE) {
                            toastr.success("删除成功！");
                            fn();
                        }
                        else {
                            toastr.warning(res.msg);
                        }

                    },
                    function (err) {

                    }
                );

            }
        );

    },

    //新增字段
    paramAdd: function ($model, url, obj, fn) {

        loadingInit();

        branPostRequest(
            url,
            obj,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("新增成功！");
                    $model.modal("hide");

                    fn(data);
                }
                else {
                    branError(data.msg);
                }

            },
            function (error) {
                branError(error);
            }
        );
    },

    //编辑字段
    paramModify: function ($model, url, obj, fn) {

        loadingInit();

        branPostRequest(
            url,
            obj,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("更新成功");
                    $model.modal("hide");

                    fn(data);

                }
                else {
                    branError(data.msg);
                }
            },
            function (error) {
                branError(error)
            }
        );

    },

    //新增参数 检查
    checkParam: function () {
        var flag = false;
        var txt = "";


        //输入名称
        var name = $.trim($common_set_modal.find(".name input").val());

        if (!name) {
            txt = "名称不能为空";
        }
        else if (name.length > 30) {
            txt = "名称不能超过30个字";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    }

};

//工段设置
var work_line = {
    row: null,
    $tb: null,

    init: function () {

        work_line.$tb = $emp_setting_container.find("#work_line").find("#tb_work_line");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "name",
                title: "工段名称",
                align: "center",
                // sortable: true,
                class: "name",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        html = "<div title='" + value + "'>" + value + "</div>";
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        work_line.row = row;

                        emp_setting.initCommonSetModal(
                            "编辑工段",
                            "工段名称",
                            row.name,
                            work_line.modify
                        );

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该工段吗？",
                            urlGroup.employee.setting.workLine.del,
                            {
                                work_line_id: row.id,
                                version: row.version
                            },
                            function () {

                                work_line.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.workLine.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    var arr = res.result;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            var version = item.version ? item.version : 0;//
                            var id = item.work_line_id ? item.work_line_id : "";//
                            var name = item.work_line_name ? item.work_line_name : "";//

                            var obj = {

                                id: id,
                                index: i,
                                version: version,
                                name: name

                            };
                            tb_data.push(obj);

                        }

                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    work_line.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = work_line.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        emp_setting.initCommonSetModal(
            "新增工段",
            "工段名称",
            "",
            work_line.add
        );

    },

    //工段新增
    add: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            work_line_name: $common_set_modal.find(".name input").val()
        };

        emp_setting.paramAdd(
            $common_set_modal,
            urlGroup.employee.setting.workLine.add,
            obj,
            function (data) {
                work_line.init();
                // work_line.$tb.bootstrapTable("insertRow", {
                //     index: 0,
                //     row: {
                //         id: data.result.id ? data.result.id : "",
                //         name: obj.work_line_name,
                //         version: 0
                //     }
                // })
            }
        );

    },

    //工段編輯
    modify: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            work_line_id: work_line.row.id,
            work_line_name: $common_set_modal.find(".name input").val(),
            version: work_line.row.version
        };

        emp_setting.paramModify(
            $common_set_modal,
            urlGroup.employee.setting.workLine.modify,
            obj,
            function (data) {
                work_line.init();
                // work_line.$tb.bootstrapTable("updateRow", {
                //     index: work_line.row.index,
                //     row: {
                //         name: obj.work_line_name,
                //         version: data.result.version ? data.result.version : 0
                //     }
                // })
            }
        );
    }

};

//班组设置
var work_shift = {
    row: null,
    $tb: null,

    init: function () {

        work_shift.$tb = $emp_setting_container.find("#work_shift").find("#tb_work_shift");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "name",
                title: "班组名称",
                align: "center",
                // sortable: true,
                class: "name",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        html = "<div title='" + value + "'>" + value + "</div>";
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        work_shift.row = row;

                        emp_setting.initCommonSetModal(
                            "编辑班组",
                            "班组名称",
                            row.name,
                            work_shift.modify
                        );

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该班组吗？",
                            urlGroup.employee.setting.workShift.del,
                            {
                                work_shift_id: row.id,
                                version: row.version
                            },
                            function () {

                                work_shift.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.workShift.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    var arr = res.result;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            var version = item.version ? item.version : 0;//
                            var id = item.work_shift_id ? item.work_shift_id : "";//
                            var name = item.work_shift_name ? item.work_shift_name : "";//

                            var obj = {

                                id: id,
                                index: i,
                                version: version,
                                name: name

                            };
                            tb_data.push(obj);

                        }

                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    work_shift.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = work_line.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        emp_setting.initCommonSetModal(
            "新增班组",
            "班组名称",
            "",
            work_shift.add
        );

    },

    //新增
    add: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            work_shift_name: $common_set_modal.find(".name input").val()
        };

        emp_setting.paramAdd(
            $common_set_modal,
            urlGroup.employee.setting.workShift.add,
            obj,
            function (data) {
                work_shift.init();
                // work_shift.$tb.bootstrapTable("insertRow", {
                //     index: 0,
                //     row: {
                //         id: data.result.id ? data.result.id : "",
                //         name: obj.work_shift_name,
                //         version: 0
                //     }
                // })
            }
        );

    },

    //编辑
    modify: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            work_shift_id: work_shift.row.id,
            work_shift_name: $common_set_modal.find(".name input").val(),
            version: work_shift.row.version
        };

        emp_setting.paramModify(
            $common_set_modal,
            urlGroup.employee.setting.workShift.modify,
            obj,
            function (data) {
                work_shift.init();
                // work_shift.$tb.bootstrapTable("updateRow", {
                //     index: work_shift.row.index,
                //     row: {
                //         name: obj.work_shift_name,
                //         version: data.result.version ? data.result.version : 0
                //     }
                // })
            }
        );
    }

};

//职位设置
var position = {
    row: null,
    $tb: null,

    init: function () {

        position.$tb = $emp_setting_container.find("#position").find("#tb_position");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "name",
                title: "职位名称",
                align: "center",
                // sortable: true,
                class: "name",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        html = "<div title='" + value + "'>" + value + "</div>";
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        position.row = row;

                        emp_setting.initCommonSetModal(
                            "编辑职位",
                            "职位名称",
                            row.name,
                            position.modify
                        );

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该职位吗？",
                            urlGroup.employee.setting.position.del,
                            {
                                position_id: row.id,
                                version: row.version
                            },
                            function () {

                                position.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.position.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    var arr = res.result;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            var version = item.version ? item.version : 0;//
                            var id = item.position_id ? item.position_id : "";//
                            var name = item.position_name ? item.position_name : "";//

                            var obj = {

                                id: id,
                                index: i,
                                version: version,
                                name: name

                            };
                            tb_data.push(obj);

                        }

                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    position.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = position.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        emp_setting.initCommonSetModal(
            "新增职位",
            "职位名称",
            "",
            position.add
        );

    },

    //新增
    add: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            position_name: $common_set_modal.find(".name input").val()
        };

        emp_setting.paramAdd(
            $common_set_modal,
            urlGroup.employee.setting.position.add,
            obj,
            function (data) {
                position.init();
                // position.$tb.bootstrapTable("insertRow", {
                //     index: 0,
                //     row: {
                //         id: data.result.id ? data.result.id : "",
                //         name: obj.position_name,
                //         version: 0
                //     }
                // })
            }
        );

    },

    //编辑
    modify: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            position_id: position.row.id,
            position_name: $common_set_modal.find(".name input").val(),
            version: position.row.version
        };

        emp_setting.paramModify(
            $common_set_modal,
            urlGroup.employee.setting.position.modify,
            obj,
            function (data) {
                position.init();
                // position.$tb.bootstrapTable("updateRow", {
                //     index: position.row.index,
                //     row: {
                //         name: obj.position_name,
                //         version: data.result.version ? data.result.version : 0
                //     }
                // })
            }
        );
    }
};

//工号前缀
var work_sn_prefix = {
    row: null,
    $tb: null,

    init: function () {

        work_sn_prefix.$tb = $emp_setting_container.find("#work_sn_prefix").find("#tb_work_sn_prefix");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "work_sn_prefix",
                title: "工号前缀",
                align: "center",
                // sortable: true,
                class: "work_sn_prefix",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        html = "<div title='" + value + "'>" + value + "</div>";
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "work_sn_length",
                title: "后缀工号位数",
                align: "center",
                // sortable: true,
                class: "work_sn_length",
                formatter: function (value, row, index) {
                    // console.log(value);
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
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        work_sn_prefix.row = row;

                        $work_sn_prefix_modal.modal("show");
                        $work_sn_prefix_modal.find(".modal-title").text("编辑工号类型");
                        $work_sn_prefix_modal.find(".modal-body .work_sn_prefix input").val(row.work_sn_prefix);
                        $work_sn_prefix_modal.find(".modal-body .work_sn_length select").val(row.work_sn_length);

                        $work_sn_prefix_modal.find(".modal-footer .btn_save").unbind("click").bind("click", function () {
                            work_sn_prefix.modify();
                        });

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该工号类型吗？",
                            urlGroup.employee.setting.work_sn_prefix.del,
                            {
                                idVersions: [
                                    {
                                        id: row.id,
                                        version: row.version
                                    }
                                ]
                            },
                            function () {

                                work_sn_prefix.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.work_sn_prefix.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {

                        var arr = res.result.models ? res.result.models : [];
                        if (arr && arr.length > 0) {

                            for (var i = 0; i < arr.length; i++) {
                                var item = arr[i];

                                var version = item.version ? item.version : 0;//
                                var id = item.id ? item.id : "";//
                                var name = item.name ? item.name : "";//
                                var digit = item.digit ? item.digit : 1;//

                                var obj = {

                                    index: i,
                                    id: id,
                                    version: version,
                                    work_sn_prefix: name,
                                    work_sn_length: digit

                                };
                                tb_data.push(obj);

                            }

                        }
                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    work_sn_prefix.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = work_sn_prefix.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        $work_sn_prefix_modal.modal("show");
        $work_sn_prefix_modal.find(".modal-title").text("新增工号类型");
        $work_sn_prefix_modal.find(".modal-body .work_sn_prefix input").val("");
        $work_sn_prefix_modal.find(".modal-body .work_sn_length select").val("0");

        $work_sn_prefix_modal.find(".modal-footer .btn_save").unbind("click").bind("click", function () {
            work_sn_prefix.add();
        });

    },

    //新增
    add: function () {

        if (!work_sn_prefix.checkParam()) {
            return
        }

        var obj = {
            name: $work_sn_prefix_modal.find(".work_sn_prefix input").val(),
            digit: parseInt($work_sn_prefix_modal.find(".work_sn_length select").val())
        };

        emp_setting.paramAdd(
            $work_sn_prefix_modal,
            urlGroup.employee.setting.work_sn_prefix.add,
            obj,
            function (data) {

                work_sn_prefix.init();

                // work_sn_prefix.$tb.bootstrapTable("insertRow", {
                //     index: 0,
                //     row: {
                //         id: data.result.id ? data.result.id : "",
                //         work_sn_prefix: obj.name,
                //         work_sn_length: obj.digit,
                //         version: 0
                //     }
                // });
            }
        );

    },

    //编辑
    modify: function () {

        if (!work_sn_prefix.checkParam()) {
            return
        }

        var obj = {
            id: work_sn_prefix.row.id,
            name: $work_sn_prefix_modal.find(".work_sn_prefix input").val(),
            digit: parseInt($work_sn_prefix_modal.find(".work_sn_length select").val()),
            version: work_sn_prefix.row.version
        };

        emp_setting.paramModify(
            $work_sn_prefix_modal,
            urlGroup.employee.setting.work_sn_prefix.modify,
            obj,
            function (data) {
                work_sn_prefix.init();
            }
        );
    },

    //新增参数 检查
    checkParam: function () {
        var flag = false;
        var txt = "";


        //输入名称
        var name = $work_sn_prefix_modal.find(".work_sn_prefix input").val();
        var digit = parseInt($work_sn_prefix_modal.find(".work_sn_length select").val());

        if (!name) {
            txt = "工号前缀不能为空";
        }
        else if (name.length > 8) {
            txt = "工号前缀不能超过8个字";
        }
        else if (digit <= 0) {
            txt = "请选择工号位数";//工号位数必须大于0
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    }

};

//花名册自定义
var roster_custom = {
    row: null,
    $tb: null,

    init: function () {

        roster_custom.$tb = $emp_setting_container.find("#roster_custom").find("#tb_roster_custom");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "name",
                title: "花名册自定义名称",
                align: "center",
                class: "name",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        html = "<div title='" + value + "'>" + value + "</div>";
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "type",
                title: "数据类型",
                align: "center",
                // sortable: true,
                class: "type",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";

                    switch (value) {
                        case 0:
                            value = "数字类型";
                            break;
                        case 1:
                            value = "文本类型";
                            break;
                        case 2:
                            value = "日期类型";
                            break;
                        default:
                            value = "文本类型";
                            break;
                    }

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
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        roster_custom.row = row;

                        $roster_custom_modal.modal("show");
                        $roster_custom_modal.find(".modal-title").text("编辑花名册自定义");
                        $roster_custom_modal.find(".modal-body .name input").val(row.name);
                        $roster_custom_modal.find(".modal-body .type select").val(row.type);

                        $roster_custom_modal.find(".modal-footer .btn_save").unbind("click").bind("click", function () {
                            roster_custom.modify();
                        });

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该花名册自定义字段吗？",
                            urlGroup.employee.setting.roster_custom.del,
                            {
                                id: row.id,
                                version: row.version
                            },
                            function () {

                                roster_custom.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.roster_custom.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    var arr = res.result;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            var version = item.version ? item.version : 0;//
                            var id = item.id ? item.id : "";//
                            var name = item.colName ? item.colName : "";//
                            var type = item.type ? item.type : item.type === 0 ? 0 : 1;//

                            var obj = {

                                id: id,
                                index: i,
                                version: version,
                                name: name,
                                type: type

                            };
                            tb_data.push(obj);

                        }

                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    roster_custom.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = roster_custom.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        $roster_custom_modal.modal("show");
        $roster_custom_modal.find(".modal-title").text("新增花名册自定义");
        $roster_custom_modal.find(".modal-body .name input").val("");
        $roster_custom_modal.find(".modal-body .type select").val("0");

        $roster_custom_modal.find(".modal-footer .btn_save").unbind("click").bind("click", function () {
            roster_custom.add();
        });

    },

    //新增
    add: function () {

        if (!roster_custom.checkParam()) {
            return
        }

        var obj = {
            colName: $roster_custom_modal.find(".name input").val(),
            type: parseInt($roster_custom_modal.find(".type select").val())
        };

        emp_setting.paramAdd(
            $roster_custom_modal,
            urlGroup.employee.setting.roster_custom.add,
            obj,
            function (data) {

                roster_custom.init();

            }
        );

    },

    //编辑
    modify: function () {

        if (!roster_custom.checkParam()) {
            return
        }

        var obj = {
            id: roster_custom.row.id,
            colName: $roster_custom_modal.find(".name input").val(),
            type: parseInt($roster_custom_modal.find(".type select").val()),
            version: roster_custom.row.version
        };

        emp_setting.paramModify(
            $roster_custom_modal,
            urlGroup.employee.setting.roster_custom.modify,
            obj,
            function (data) {
                roster_custom.init();
            }
        );
    },

    //新增参数 检查
    checkParam: function () {
        var flag = false;
        var txt = "";


        //输入名称
        var name = $roster_custom_modal.find(".name input").val();
        // var digit = parseInt($work_sn_prefix_modal.find(".work_sn_length select").val());

        if (!name) {
            txt = "花名册自定义名称不能为空";
        }
        else if (name.length > 10) {
            txt = "花名册自定义名称不能超过10个字";
        }
        // else if (digit <= 0) {
        //     txt = "请选择工号位数";//工号位数必须大于0
        // }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    }

};

//离职原因
var leave_reason = {
    row: null,
    $tb: null,

    init: function () {

        leave_reason.$tb = $emp_setting_container.find("#leave_reason").find("#tb_leave_reason");

        var columns = [

            {
                field: "index",
                title: "序号",
                align: "center",
                // sortable: true,
                class: "index",
                formatter: function (value, row, index) {

                    return index + 1;
                }
            },
            {
                field: "name",
                title: "离职原因",
                align: "center",
                // sortable: true,
                class: "name",
                formatter: function (value, row, index) {
                    // console.log(value);
                    var html = "";
                    if (value) {
                        if (row.isBad) {
                            html = "<div class='is_bad' title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                    }
                    else {
                        html = "<div></div>";
                    }

                    return html;
                }
            },
            {
                field: "operate",
                title: "操作",
                align: "center",
                class: "operate",
                formatter: function (value, row, index) {

                    var html = "";
                    html += "<div class='operate'>";

                    //编辑
                    html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                    //删除
                    html += "<div class='btn btn-success btn-sm btn_del'>删除</div>";

                    html += "</div>";

                    return html;

                },
                events: {

                    //编辑
                    "click .btn_modify": function (e, value, row, index) {

                        leave_reason.row = row;

                        emp_setting.initCommonSetModal(
                            "编辑离职原因",
                            "离职原因名称",
                            leave_reason.row.name,
                            leave_reason.modify,
                            row.isBad
                        );

                    },
                    //删除
                    "click .btn_del": function (e, value, row, index) {

                        emp_setting.paramDel(
                            "确认删除该离职原因吗？",
                            urlGroup.employee.setting.leave_reason.del,
                            {
                                leave_reason_id: row.id,
                                version: row.version
                            },
                            function () {

                                leave_reason.$tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });

                            }
                        );

                    }

                }
            }

        ];

        branGetRequest(
            urlGroup.employee.setting.leave_reason.list,
            function (res) {

                var tb_data = [];

                if (res.code === RESPONSE_OK_CODE) {

                    var arr = res.result;
                    if (arr && arr.length > 0) {

                        for (var i = 0; i < arr.length; i++) {
                            var item = arr[i];

                            var version = item.version ? item.version : 0;//
                            var id = item.leave_reason_id ? item.leave_reason_id : "";//
                            var name = item.leave_reason_name ? item.leave_reason_name : "";//
                            var isBad = item.is_not_good ? item.is_not_good : 0;//0 正常  1 不良原因

                            var obj = {

                                id: id,
                                index: i,
                                version: version,
                                name: name,
                                isBad: isBad

                            };
                            tb_data.push(obj);

                        }

                    }

                }
                else {
                    toastr.warning(data.msg);
                }

                emp_setting.initTb(
                    leave_reason.$tb,
                    columns,
                    tb_data
                );

            },
            function (error) {
                branError(error);
            }
        );

    },

    //新增按钮 点击事件
    modalShow: function () {
        var data = leave_reason.$tb.bootstrapTable('getData');
        if (data.length > emp_setting.max) {
            var txt = "数量超过最大限制！最多设置" + emp_setting.max + "个";
            toastr.warning(txt);
            return;
        }

        emp_setting.initCommonSetModal(
            "新增离职原因",
            "离职原因名称",
            "",
            leave_reason.add
        );

    },

    //选中 是否为不良离职原因
    chooseReasonType: function (self) {
        //已经选为不良原因
        if ($(self).hasClass("active")) {
            $(self).removeClass("active");
            $(self).find("img").attr("src", "image/UnChoose.png");
        }
        else {
            $(self).addClass("active");
            $(self).find("img").attr("src", "image/Choosed.png")
        }
    },

    //新增
    add: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            leave_reason_name: $common_set_modal.find(".name input").val(),
            is_not_good: $common_set_modal.find(".is_bad_container").hasClass("active") ? 1 : 0
        };

        emp_setting.paramAdd(
            $common_set_modal,
            urlGroup.employee.setting.leave_reason.add,
            obj,
            function (data) {
                leave_reason.init();
                // leave_reason.$tb.bootstrapTable("insertRow", {
                //     index: 0,
                //     row: {
                //         id: data.result.leave_reason_id ? data.result.leave_reason_id : "",
                //         name: obj.leave_reason_name,
                //         version: 0,
                //         isBad: obj.is_not_good
                //     }
                // })
            }
        );

    },

    //编辑
    modify: function () {

        if (!emp_setting.checkParam()) {
            return
        }

        var obj = {
            leave_reason_id: leave_reason.row.id,
            leave_reason_name: $common_set_modal.find(".name input").val(),
            version: leave_reason.row.version,
            is_not_good: $common_set_modal.find(".is_bad_container").hasClass("active") ? 1 : 0
        };

        emp_setting.paramModify(
            $common_set_modal,
            urlGroup.employee.setting.leave_reason.modify,
            obj,
            function (data) {
                leave_reason.init();
                // leave_reason.$tb.bootstrapTable("updateRow", {
                //     index: leave_reason.row.index,
                //     row: {
                //         name: obj.leave_reason_name,
                //         version: data.result.version ? data.result.version : 0,
                //         isBad: obj.is_not_good
                //     }
                // })
            }
        );
    }

};

//消息提醒
var message = {
    $container: null,

    //初始化
    init: function () {
        message.$container = $emp_setting_container.find("#message");

        message.initDay();//初始化 天
        message.initHour();//初始化 时

        message.entryInfoGet();//获取入职消息提醒
    },
    //初始化 天
    initDay: function () {
        //设置天数
        var dayList = "";//
        for (var i = 1; i < 11; i++) {
            dayList += "<option value='" + i + "'>" + i + "</option>";
        }

        message.$container.find(".day_container select").html(dayList);

    },
    //初始化 时
    initHour: function () {
        //设置时数
        var hourList = "";//
        for (var j = 6; j < 22; j++) {
            hourList += "<option value='" + j + "'>" + j + "</option>";
        }
        message.$container.find(".hour_container select").html(hourList);

    },

    //获取入职消息提醒
    entryInfoGet: function () {
        loadingInit();

        branGetRequest(
            urlGroup.employee.setting.message.get,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    var item = data.result;
                    var version = item.version ? item.version : 0;//版本
                    var content = item.message_content ? item.message_content :
                        "暂无提醒";
                    var day = item.before_check_in_day ? item.before_check_in_day : 1;
                    var hour = item.post_hour ? item.post_hour : 8;
                    var isWorking = item.is_working ? item.is_working : 0;//0 不启用 1 启用

                    message.$container.attr("data-version", version);
                    message.$container.find(".entry_prompt_info").text(content);
                    message.$container.find(".day_container select").val(day);
                    message.$container.find(".hour_container select").val(hour);

                    if (isWorking === 1) {
                        message.$container.find(".choose_item").addClass("active")
                            .find("img").attr("src", "image/Choosed.png")
                    }
                    else {
                        message.$container.find(".choose_item").removeClass("active")
                            .find("img").attr("src", "image/UnChoose.png");
                    }

                }
                else {
                    branError(data.msg);
                }

            },
            function (error) {
                branError(error);
            }
        );
    },
    //选中行
    chooseItem: function (self) {
        //已经选中
        if ($(self).hasClass("active")) {
            $(self).removeClass("active");
            $(self).find("img").attr("src", "image/UnChoose.png");
        }
        else {
            $(self).addClass("active");
            $(self).find("img").attr("src", "image/Choosed.png")
        }
    },
    //提交
    entryInfoSubmit: function () {

        loadingInit();

        var obj = {
            version: message.$container.attr("data-version"),
            message_content: message.$container.find(".entry_prompt_info").text(),
            is_working: message.$container.find(".choose_item").hasClass("active") ? 1 : 0,
            before_check_in_day: message.$container.find(".day_container select")
                .find("option:selected").val(),
            post_hour: message.$container.find(".hour_container select")
                .find("option:selected").val()
        };

        branPostRequest(
            urlGroup.employee.setting.message.save,
            obj,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("提交成功");
                    message.init();
                }
                else {
                    branError(data.msg);
                }

            },
            function (error) {
                branError(error);
            }
        );

    }

};

$(function () {
    $emp_setting_container = $(".emp_setting_container");
    emp_setting.init();
});


