//入职提醒

var $entry_remind_container = $(".entry_remind_container");//
var $tb_entry_remind = $entry_remind_container.find("#tb_entry_remind");//表格

var entry_remind_manage = {

    init: function () {
        entry_remind_manage.getEmpList();//获取 员工列表
    },

    //员工列表（入职提醒）
    getEmpList: function () {

        $tb_entry_remind.bootstrapTable("destroy");
        //表格的初始化
        $tb_entry_remind.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
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
            height: 400,
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
                    field: "emp_name",
                    title: "姓名",
                    align: "center",
                    // sortable: true,
                    class: "emp_name",
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
                    field: "emp_phone",
                    title: "手机号",
                    align: "center",
                    // sortable: true,
                    class: "emp_phone",
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
                    field: "check_in_time",
                    title: "入职时间",
                    align: "center",
                    // sortable: true,
                    class: "check_in_time",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var html = "";
                        if (value) {
                            value = timeInit(value);
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;
                    }
                },

                {
                    field: "position_name",
                    title: "职位",
                    align: "center",
                    // sortable: true,
                    class: "position_name",
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
                    field: "work_shift_name",
                    title: "班组",
                    align: "center",
                    // sortable: true,
                    class: "work_shift_name",
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
                    field: "work_line_name",
                    title: "工段",
                    align: "center",
                    // sortable: true,
                    class: "work_line_name",
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
                    field: "department_name",
                    title: "部门",
                    align: "center",
                    // sortable: true,
                    class: "department_name",
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
                    field: "remind_state",
                    title: "通知状态",
                    align: "center",
                    // sortable: true,
                    class: "remind_state",
                    formatter: function (value, row, index) {
                        // console.log(value);
                        var msg_status = "";//打卡状态
                        switch (value) {
                            case 0:
                                msg_status = "未通知未确认";
                                break;
                            case 1:
                                msg_status = "已通知未确认";
                                break;
                            case 2:
                                msg_status = "已通知已确认";
                                break;
                            default:
                                msg_status = "未通知未确认";
                        }

                        return "<div title='" + msg_status + "'>" + msg_status + "</div>";

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

                        //未通知未确认
                        if (row.remind_state === 0) {
                            html += "<div class='btn btn-success btn-sm btn_remind_fir'>现在通知</div>";
                        }

                        //已通知未确认
                        if (row.remind_state === 1) {
                            html += "<div class='btn btn-success btn-sm btn_remind_sec'>再次通知</div>";
                        }


                        html += "</div>";

                        return html;

                    },
                    events: {

                        //现在通知
                        "click .btn_remind_fir": function (e, value, row, index) {

                            operateMsgShow(
                                "是否确认现在通知该员工?",
                                function () {

                                    loadingInit();

                                    var obj = {
                                        batch: [
                                            {
                                                version: row.version,
                                                id: row.id,
                                                name: row.emp_name
                                            }
                                        ]
                                    };

                                    branPostRequest(
                                        urlGroup.home.entry_remind.dispose,
                                        obj,
                                        function (res) {

                                            if (res.code === RESPONSE_OK_CODE) {

                                                toastr.success("通知成功！");
                                                entry_remind_manage.getEmpList();

                                            }
                                            else {
                                                toastr.warning(res.msg);
                                            }

                                        },
                                        function (err) {

                                        }
                                    );

                                }
                            )

                        },
                        //再次通知
                        "click .btn_remind_sec": function (e, value, row, index) {

                            operateMsgShow(
                                "是否确认再次通知该员工?",
                                function () {


                                    loadingInit();

                                    var obj = {
                                        batch: [
                                            {
                                                version: row.version,
                                                id: row.id,
                                                name: row.emp_name
                                            }
                                        ]
                                    };

                                    branPostRequest(
                                        urlGroup.home.entry_remind.dispose,
                                        obj,
                                        function (res) {

                                            if (res.code === RESPONSE_OK_CODE) {

                                                toastr.success("通知成功！");
                                                entry_remind_manage.getEmpList();

                                            }
                                            else {
                                                toastr.warning(res.msg);
                                            }

                                        },
                                        function (err) {

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
            url: urlGroup.home.entry_remind.list,// urlGroup.employee.roster.list
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                return {
                    page: params.pageNumber,
                    page_size: params.pageSize

                };
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

                        var arr = res.result.result;
                        if (arr && arr.length > 0) {

                            for (var i = 0; i < arr.length; i++) {
                                var item = arr[i];

                                var version = item.version ? item.version : 0;//
                                var id = item.id ? item.id : "";//员工id

                                var emp_name = item.realName ? item.realName : "";//员工姓名
                                var emp_phone = item.telephone ? item.telephone : "";//手机
                                var check_in_time = item.checkinTime ? item.checkinTime : 0;//入职时间

                                var department_name = item.departmentName ? item.departmentName : "";//部门名称
                                var position_name = item.positionName ? item.positionName : "";//职位名称
                                var work_shift_name = item.workShiftName ? item.workShiftName : "";//班组名称
                                var work_line_name = item.workLineName ? item.workLineName : "";//工段名称

                                var remind_state = item.notifyState;//通知状态

                                var obj = {

                                    id: id,
                                    version: version,

                                    emp_name: emp_name,
                                    emp_phone: emp_phone,
                                    check_in_time: check_in_time,

                                    department_name: department_name,
                                    position_name: position_name,
                                    work_shift_name: work_shift_name,
                                    work_line_name: work_line_name,

                                    remind_state: remind_state

                                };
                                tb_data.push(obj);

                            }

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

    //通知 多个员工
    empRemindMore: function () {
        var data = $tb_entry_remind.bootstrapTable("getAllSelections");
        if (data.length === 0) {
            toastr.warning("您没有选择员工！");
            return
        }

        var arr = [];//要通知的员工
        $.each(data, function (i, item) {

            var id = item.id ? item.id : "";
            var version = item.version ? item.version : "";
            var name = item.emp_name ? item.emp_name : "";
            var state = item.remind_state;

            var obj = {
                version: version,
                id: id,
                name: name
            };

            //如果该员工不是已通知已确认
            if (state !== 2) {
                arr.push(obj);
            }

        });

        if (arr.length === 0) {
            toastr.warning("选择的员工都已经确认！");
            return
        }

        operateMsgShow(
            "是否确认通知已经选择的员工?",
            function () {
                loadingInit();

                branPostRequest(
                    urlGroup.home.entry_remind.dispose,
                    {
                        batch: arr
                    },
                    function (res) {

                        if (res.code === RESPONSE_OK_CODE) {

                            toastr.success("通知成功！");
                            entry_remind_manage.getEmpList();

                        }
                        else {
                            toastr.warning(res.msg);
                        }

                    },
                    function (err) {

                    }
                );

            }
        )

    },

    //导出
    empExport: function () {

        exportModalShow(
            "确定要导出所有入职提醒的员工吗?",
            function () {
                loadingInit();

                branGetRequest(
                    urlGroup.home.entry_remind.empExport,
                    function (res) {

                        if (res.code === RESPONSE_OK_CODE) {

                            if (res.result) {

                                var url = res.result.url ? res.result.url : "";
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
                        branError(error);
                    }
                );

            }
        )

    }

};

$(function () {
    entry_remind_manage.init();
});
