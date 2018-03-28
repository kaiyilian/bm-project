/**
 * Created by Administrator on 2017/10/16.
 * 出勤配置
 */

var $attendance_setting_container = $(".attendance_setting_container");//考勤设置container
var $attendance_machine_modal = $(".attendance_machine_modal");//考勤机设置 弹框
var $holiday_set = $attendance_setting_container.find("#holiday_set");//假期设置
var $overTime_set = $attendance_setting_container.find("#overTime_set");//加班设置

var attendance_setting = {

    approvalTypeMap: null,//审批类型 map

    init: function () {
        $("[data-toggle='tooltip']").tooltip();

        $('a[href="#attendance_cycle"]').tab('show'); //默认显示 合同模板

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {

            //console.log($(this).html());

            var href = $(this).attr("data-href");
            if (href === "attendance_cycle") {
                attendance_cycle.init();//考勤周期 初始化
            }
            if (href === "holiday_set") {
                holiday_set.init();//假期设置 初始化
            }
            if (href === "overTime_set") {
                overTime_set.init();//加班设置 初始化
            }
            if (href === "attendance_clock") {
                attendance_clock.init();//手机考勤 初始化
            }
            if (href === "attendance_machine") {
                attendance_machine.init();//考勤机设置 初始化
            }

        });

        attendance_setting.initApprovalTypeMap();//初始化 审批明细类型map

        attendance_cycle.init();//考勤周期 初始化
    },

    //初始化 审批明细类型map
    initApprovalTypeMap: function () {

        attendance_setting.approvalTypeMap = new Map();
        attendance_setting.approvalTypeMap.put("", "无");
        attendance_setting.approvalTypeMap.put(0, "调休");
        attendance_setting.approvalTypeMap.put(1, "事假");
        attendance_setting.approvalTypeMap.put(2, "年假");
        attendance_setting.approvalTypeMap.put(3, "病假");
        attendance_setting.approvalTypeMap.put(4, "产检假");
        attendance_setting.approvalTypeMap.put(5, "工伤假");
        attendance_setting.approvalTypeMap.put(6, "婚假");
        attendance_setting.approvalTypeMap.put(7, "产假");
        attendance_setting.approvalTypeMap.put(8, "哺乳假");
        attendance_setting.approvalTypeMap.put(9, "丧假");
        attendance_setting.approvalTypeMap.put(10, "工作日加班");
        attendance_setting.approvalTypeMap.put(11, "休息日加班");
        attendance_setting.approvalTypeMap.put(12, "节假日加班");

    }

};

//假期设置
var holiday_set = {

    init: function () {
        holiday_set.initHolidayList();//初始化 假期列表
    },

    //初始化 假期列表
    initHolidayList: function () {

        var $holiday_type_list = $holiday_set.find(".holiday_type_list");
        $holiday_type_list.empty();

        branGetRequest(
            urlGroup.attendance.setting.holiday.list_all,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result && data.result.length > 0) {

                        $.each(data.result, function (index, $item) {

                            // var id = $item.id ? $item.id : "";//
                            var approvalTypeDetail = $item.approvalTypeDetail ? $item.approvalTypeDetail :
                                ($item.approvalTypeDetail === 0 ? 0 : "");//审批明细
                            var name = attendance_setting.approvalTypeMap.get(approvalTypeDetail);

                            var $div = $("<div>");
                            $div.appendTo($holiday_type_list);
                            $div.addClass("btn");
                            $div.addClass("btn-sm");
                            $div.addClass("btn-default");
                            $div.attr("data-type", approvalTypeDetail);
                            $div.text(name);
                            $div.unbind("click").bind("click", function () {

                                var $this = $(this);

                                //事假 为必选，不能取消
                                var type = parseInt($this.attr("data-type"));
                                if (type === 1) {
                                    toastr.warning("事假类型不能取消！");
                                }
                                else {

                                    if ($this.hasClass("btn-default")) {
                                        $this.removeClass("btn-default");
                                        $this.addClass("btn-success");
                                    }
                                    else {
                                        $this.removeClass("btn-success");
                                        $this.addClass("btn-default");
                                    }

                                }

                                holiday_set.checkIsChooseAll();//检查  是否被 全选

                            });

                        });

                        holiday_set.holidayList();//假期列表

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

    },

    //获取列表
    holidayList: function () {

        var $holiday_type_list = $holiday_set.find(".holiday_type_list");

        branGetRequest(
            urlGroup.attendance.setting.holiday.list,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var types = [];//已经保存的 type数组
                        $.each(data.result, function (index, $item) {

                            var approvalTypeDetail = $item.approvalTypeDetail ? $item.approvalTypeDetail :
                                ($item.approvalTypeDetail === 0 ? 0 : "");//审批明细
                            types.push(approvalTypeDetail);

                        });

                        //检查 是否有 事假，如果没有事假，则加上
                        if (types.indexOf(1) < 0) {
                            types.push(1);
                        }

                        //遍历所有btn，检查是否被选择
                        $holiday_type_list.find(".btn").each(function () {
                            var $this = $(this);

                            var type = parseInt($this.attr("data-type"));

                            if (types.indexOf(type) > -1) {
                                $this.addClass("btn-success").removeClass("btn-default");
                            }

                        });

                        holiday_set.checkIsChooseAll();//检查  是否被 全选

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

    },

    //全选
    chooseAll: function () {
        var $btn_all = $holiday_set.find(".btn_all");//全选  按钮
        var $btn = $holiday_set.find(".holiday_type_list .btn");// item

        if ($btn_all.hasClass("btn-success")) {
            $btn_all.addClass("btn-default").removeClass("btn-success");

            $holiday_set.find(".holiday_type_list").find(".btn[data-type!='1']")
                .addClass("btn-default").removeClass("btn-success");

        }
        else {
            $btn_all.addClass("btn-success").removeClass("btn-default");
            $btn.addClass("btn-success").removeClass("btn-default");
        }

    },
    //检查 是否被 全选
    checkIsChooseAll: function () {
        var $btn_all = $holiday_set.find(".btn_all");//全选  按钮
        var $btn = $holiday_set.find(".holiday_type_list .btn");// item
        var $btn_active = $holiday_set.find(".holiday_type_list .btn.btn-success");// 已选中

        if ($btn.length === $btn_active.length) {
            $btn_all.addClass("btn-success").removeClass("btn-default");
        }
        else {
            $btn_all.addClass("btn-default").removeClass("btn-success");
        }

    },


    //假期保存
    holidaySave: function () {

        if (!holiday_set.checkParam()) {
            return
        }

        var ids = [];
        var $btn_active = $holiday_set.find(".holiday_type_list .btn.btn-success");// 已选中
        $btn_active.each(function () {

            var $this = $(this);
            // var id = $this.attr("data-id");
            var approvalTypeDetail = $this.attr("data-type");

            var obj = {
                // id: id,
                approvalTypeDetail: approvalTypeDetail
            };

            ids.push(obj);

        });

        var obj = {
            ids: ids
        };

        loadingInit();

        branPostRequest(
            urlGroup.attendance.setting.holiday.set,
            obj,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("保存成功！");

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                branError(error);
            }
        );


    },
    //检查参数
    checkParam: function () {

        var flag = false;
        var txt = "";

        var $btn_active = $holiday_set.find(".holiday_type_list .btn.btn-success");// 已选中

        if ($btn_active.length <= 0) {
            txt = "请选择假期类型！";
        }
        else if ($holiday_set.find(".holiday_type_list .btn[data-type='1']").hasClass("btn-default")) {
            txt = "事假是必选项，请选择！";
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

//加班设置
var overTime_set = {

    init: function () {
        overTime_set.initOverTimeList();//初始化 加班列表
    },

    //初始化 加班列表
    initOverTimeList: function () {

        var $overTime_type_list = $overTime_set.find(".overTime_type_list");
        $overTime_type_list.empty();

        branGetRequest(
            urlGroup.attendance.setting.overTime.list_all,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result && data.result.length > 0) {

                        $.each(data.result, function (index, $item) {

                            // var id = $item.id ? $item.id : "";//
                            var approvalTypeDetail = $item.approvalTypeDetail ? $item.approvalTypeDetail :
                                ($item.approvalTypeDetail === 0 ? 0 : "");//审批明细
                            var name = attendance_setting.approvalTypeMap.get(approvalTypeDetail);

                            var $div = $("<div>");
                            $div.appendTo($overTime_type_list);
                            $div.addClass("btn");
                            $div.addClass("btn-sm");
                            $div.addClass("btn-default");
                            // $div.attr("data-id", id);
                            $div.attr("data-type", approvalTypeDetail);
                            $div.text(name);
                            $div.unbind("click").bind("click", function () {

                                var $this = $(this);

                                //工作日加班 为必选，不能取消
                                var type = parseInt($this.attr("data-type"));
                                if (type === 10) {
                                    toastr.warning("工作日加班类型不能取消！");
                                }
                                else {

                                    if ($this.hasClass("btn-default")) {
                                        $this.removeClass("btn-default");
                                        $this.addClass("btn-success");
                                    }
                                    else {
                                        $this.removeClass("btn-success");
                                        $this.addClass("btn-default");
                                    }

                                }

                                overTime_set.checkIsChooseAll();//检查  是否被 全选

                            });

                        });

                        overTime_set.OverTimeList();//获取列表

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

    },

    //获取列表
    OverTimeList: function () {

        var $overTime_type_list = $overTime_set.find(".overTime_type_list");

        branGetRequest(
            urlGroup.attendance.setting.overTime.list,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var types = [];//已经保存的 ids
                        $.each(data.result, function (index, $item) {

                            var approvalTypeDetail = $item.approvalTypeDetail ? $item.approvalTypeDetail :
                                ($item.approvalTypeDetail === 0 ? 0 : "");//审批明细
                            types.push(approvalTypeDetail);

                        });

                        //检查 是否有 工作日加班，如果没有，则加上
                        if (types.indexOf(10) < 0) {
                            types.push(10);
                        }

                        //遍历所有btn，检查是否被选择
                        $overTime_type_list.find(".btn").each(function () {
                            var $this = $(this);

                            var type = parseInt($this.attr("data-type"));

                            if (types.indexOf(type) > -1) {
                                $this.addClass("btn-success").removeClass("btn-default");
                            }

                        });
                        overTime_set.checkIsChooseAll();//检查  是否被 全选

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

    },

    //全选
    chooseAll: function () {
        var $btn_all = $overTime_set.find(".btn_all");//全选  按钮
        var $btn = $overTime_set.find(".overTime_type_list .btn");// item

        if ($btn_all.hasClass("btn-success")) {
            $btn_all.addClass("btn-default").removeClass("btn-success");
            // $btn.addClass("btn-default").removeClass("btn-success");

            $overTime_set.find(".overTime_type_list").find(".btn[data-type!='10']")
                .addClass("btn-default").removeClass("btn-success");

        }
        else {
            $btn_all.addClass("btn-success").removeClass("btn-default");
            $btn.addClass("btn-success").removeClass("btn-default");
        }

    },
    //检查 是否被 全选
    checkIsChooseAll: function () {
        var $btn_all = $overTime_set.find(".btn_all");//全选  按钮
        var $btn = $overTime_set.find(".overTime_type_list .btn");// item
        var $btn_active = $overTime_set.find(".overTime_type_list .btn.btn-success");// 已选中

        if ($btn.length === $btn_active.length) {
            $btn_all.addClass("btn-success").removeClass("btn-default");
        }
        else {
            $btn_all.addClass("btn-default").removeClass("btn-success");
        }

    },


    //加班保存
    OverTimeSave: function () {

        if (!overTime_set.checkParam()) {
            return
        }

        var ids = [];
        var $btn_active = $overTime_set.find(".overTime_type_list .btn.btn-success");// 已选中
        $btn_active.each(function () {

            var $this = $(this);
            // var id = $this.attr("data-id");
            var approvalTypeDetail = $this.attr("data-type");

            var obj = {
                // id: id,
                approvalTypeDetail: approvalTypeDetail
            };

            ids.push(obj);

        });

        var obj = {
            ids: ids
        };

        loadingInit();

        branPostRequest(
            urlGroup.attendance.setting.overTime.set,
            obj,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("保存成功！");

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                branError(error);
            }
        );


    },
    //检查参数
    checkParam: function () {

        var flag = false;
        var txt = "";

        var $btn_active = $overTime_set.find(".overTime_type_list .btn.btn-success");// 已选中

        if ($btn_active.length <= 0) {
            txt = "请选择加班类型！";
        }
        else if ($overTime_set.find(".overTime_type_list .btn[data-type='10']").hasClass("btn-default")) {
            txt = "工作日加班是必选项，请选择！";
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

//考勤机打卡 配置
var attendance_machine = {

    work_shift_map: null,//所有班组列表 map
    work_shift_exist: null,//已有 班组列表 Array
    row: null,  //行数据

    init: function () {
        // attendance_machine.attendanceMachineList();//考勤机列表
        // attendance_machine.initWorkShift();//获取所有班组

        // function test() {
        //
        //     for (var i = 0; i < 5; i++) {
        //
        //         setTimeout(function () {
        //             console.log(i);
        //         }, 0)
        //
        //     }
        //
        // }
        //
        // test();
        //
        //
        // function fun(n, o) {
        //
        //     console.log(o);
        //     return {
        //         fun: function (m, n) {
        //             fun(n)
        //         }
        //     }
        //
        // }
        //
        // var s = fun(1).fun(2)
        // console.log(s);

        attendance_machine.initTb();//初始化 表格

        //考勤机 弹框初始化
        $attendance_machine_modal.unbind("shown.bs.modal").on("shown.bs.modal", function () {

            console.log("考勤机编辑：" + new Date().getTime());

            var $row = $attendance_machine_modal.find(".modal-body > .row");
            $row.find(".machine_name input").val(attendance_machine.row.deviceName);
            $row.find(".machine_no").text(attendance_machine.row.deviceNo);

            //初始化 班组
            attendance_machine.initWorkShift(function () {

                //已经保存过的 班组
                attendance_machine.work_shift_exist = [];
                for (var i = 0; i < attendance_machine.row.workShiftResults.length; i++) {
                    var id = attendance_machine.row.workShiftResults[i].workShiftId;
                    id = id ? id : "";
                    attendance_machine.work_shift_exist.push(id);
                }

                $attendance_machine_modal.find(".work_shift_list .btn").each(function () {
                    var $this = $(this);

                    var id = $this.attr("data-id");

                    if (attendance_machine.work_shift_exist.indexOf(id) > -1) {
                        $this.addClass("active");
                    }

                });
                attendance_machine.checkIsChooseAll();//检查 班组 是否被 全选

            });

        });

    },

    //初始化 考勤机列表
    initTb: function () {
        var $tb = $attendance_setting_container.find("#attendance_machine").find("#td_attendance_machine");

        branGetRequest(
            urlGroup.attendance.setting.attendanceMachine.list,
            function (res) {

                var tb_data = [];//考勤机列表 数组

                if (res.code === RESPONSE_OK_CODE) {

                    //设置列表
                    var arr = res.result ? res.result : [];
                    $.each(arr, function (index, item) {

                        var deviceName = item.deviceName ? item.deviceName : "";//
                        var deviceNo = item.deviceNo ? item.deviceNo : "";//
                        var workShiftResults = item.workShiftResults ? item.workShiftResults : [];//

                        tb_data.push({
                            deviceName: deviceName,
                            deviceNo: deviceNo,
                            workShiftResults: workShiftResults
                        });

                    });

                }
                else {
                    toastr.warning(res.msg);
                }

                $tb.bootstrapTable("destroy");//表格摧毁
                //表格的初始化
                $tb.bootstrapTable({

                    undefinedText: "",                   //当数据为 undefined 时显示的字符
                    striped: false,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

                    data: tb_data,                         //直接从本地数据初始化表格
                    uniqueId: "deviceNo",

                    //分页
                    pagination: true,                   //是否显示分页（*）
                    onlyPagination: true,               //只显示分页 页码
                    sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
                    pageNumber: 1,                      //初始化加载第一页，默认第一页
                    pageSize: 10,                       //每页的记录行数（*）
                    pageList: [5, 10, 15],        //可供选择的每页的行数（*）

                    //排序
                    sortable: true,                     //所有列的排序 是否开启
                    sortOrder: "desc",                   //排序方式

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
                            field: 'deviceName',
                            title: '考勤机名称',
                            align: "center",
                            class: "deviceName",
                            formatter: function (value, row, index) {

                                var html = "";
                                if (value) {
                                    html = "<div title='" + value + "'>" + value + "</div>";
                                }
                                else {
                                    html = "<div>无</div>";
                                }

                                return html;

                            }
                        },
                        {
                            field: 'deviceNo',
                            title: '设备编号',
                            // sortable: true,
                            align: "center",
                            class: "deviceNo",
                            formatter: function (value, row, index) {

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
                            field: 'work_shift',
                            title: '班组',
                            align: "center",
                            class: "work_shift",
                            formatter: function (value, row, index) {

                                var html = "";

                                if (row.workShiftResults.length > 0) {

                                    var names = "";
                                    for (var i = 0; i < row.workShiftResults.length; i++) {

                                        // var id = row.workShiftResults[i].workShiftId;
                                        // id = id ? id : "";
                                        // var name = attendance_machine.work_shift_map.get(id);

                                        var name = row.workShiftResults[i].workShiftName;
                                        name = name ? name : "";

                                        names += names ? ("," + name) : name;
                                    }

                                    html += "<div>" + names + "</div>"
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
                            // sortable: true,
                            align: "center",
                            class: "operate",
                            formatter: function (value, row, index) {

                                var html = "<div class='operate'>";

                                //编辑
                                html += "<div class='btn btn-success btn-sm btn_modify'>编辑</div>";

                                html += "</div>";

                                return html;

                            },
                            events: {

                                //编辑
                                "click .btn_modify": function (e, value, row, index) {

                                    // console.log(e);
                                    // var $e = $(e.currentTarget);
                                    // var $item = $e.closest(".item");

                                    attendance_machine.row = row;
                                    $attendance_machine_modal.modal("show");

                                    // //考勤机 弹框初始化
                                    // $attendance_machine_modal.on("shown.bs.modal", function () {
                                    //
                                    //     var $row = $attendance_machine_modal.find(".modal-body > .row");
                                    //
                                    //     $row.find(".machine_name input").val(row.deviceName);
                                    //     $row.find(".machine_no").text(row.deviceNo);
                                    //     $row.find(".btn_all").removeClass("active");
                                    //
                                    //     //已经保存过的 班组
                                    //     attendance_machine.work_shift_exist = [];
                                    //     for (var i = 0; i < row.workShiftResults.length; i++) {
                                    //         var id = row.workShiftResults[i].workShiftId;
                                    //         id = id ? id : "";
                                    //         attendance_machine.work_shift_exist.push(id);
                                    //     }
                                    //
                                    //     //班组列表(所有的)
                                    //     var $work_shift = $row.find(".work_shift_list");
                                    //     $work_shift.empty();
                                    //     branGetRequest(
                                    //         urlGroup.attendance.setting.attendanceMachine.work_shift_list,
                                    //         function (res) {
                                    //
                                    //             if (res.code === RESPONSE_OK_CODE) {
                                    //
                                    //                 var w_shift = res.result ? res.result : [];//
                                    //                 $.each(w_shift, function (index, item) {
                                    //
                                    //                     var id = item.work_shift_id ? item.work_shift_id : "";//
                                    //                     var name = item.work_shift_name ? item.work_shift_name : "";//
                                    //
                                    //                     // attendance_machine.work_shift_map.put(id, name);
                                    //
                                    //                     var $btn = $("<div>");
                                    //                     $btn.addClass("btn");
                                    //                     $btn.addClass("btn-sm");
                                    //                     $btn.addClass("btn-default");
                                    //                     $btn.attr("data-id", id);
                                    //                     $btn.text(name);
                                    //                     $btn.bind("click", function () {
                                    //                         //选择 单个 班组
                                    //                         attendance_machine.chooseWorkShiftOne(this);
                                    //                     });
                                    //                     $btn.appendTo($work_shift);
                                    //
                                    //                     if (attendance_machine.work_shift_exist.indexOf(id) > -1) {
                                    //                         $btn.addClass("active");
                                    //                     }
                                    //
                                    //                 });
                                    //
                                    //             }
                                    //             else {
                                    //                 toastr.warning(data.msg);
                                    //             }
                                    //
                                    //         },
                                    //         function (error) {
                                    //             branError(error);
                                    //         }
                                    //     );
                                    //
                                    //
                                    // });

                                }

                            }
                        }

                    ]

                });

            },
            function (error) {
                branError(error);
            }
        );

    },
    //初始化 班组
    initWorkShift: function (successFn) {

        loadingInit();

        //初始化 全部按钮
        $attendance_machine_modal.find(".btn_all").removeClass("active");

        branGetRequest(
            urlGroup.attendance.setting.attendanceMachine.work_shift_list,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {

                    var $row = $attendance_machine_modal.find(".modal-body > .row");
                    var $work_shift = $row.find(".work_shift_list");
                    $work_shift.empty();

                    var w_shift = res.result ? res.result : [];//
                    $.each(w_shift, function (index, item) {

                        var id = item.work_shift_id ? item.work_shift_id : "";//
                        var name = item.work_shift_name ? item.work_shift_name : "";//

                        // attendance_machine.work_shift_map.put(id, name);

                        var $btn = $("<div>");
                        $btn.addClass("btn");
                        $btn.addClass("btn-sm");
                        $btn.addClass("btn-default");
                        $btn.attr("data-id", id);
                        $btn.text(name);
                        $btn.bind("click", function () {
                            //选择 单个 班组
                            attendance_machine.chooseWorkShiftOne(this);
                        });
                        $btn.appendTo($work_shift);

                        // if (attendance_machine.work_shift_exist.indexOf(id) > -1) {
                        //     $btn.addClass("active");
                        // }

                    });

                    if (typeof successFn === "function") {
                        successFn();
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


    },

    //选择 单个 班组
    chooseWorkShiftOne: function (self) {

        var $self = $(self);
        //如果 已经选中
        if ($self.hasClass("active")) {
            $self.removeClass("active");
        }
        else {
            $self.addClass("active");
        }

        //检查 班组 是否被 全选
        attendance_machine.checkIsChooseAll();
    },
    //选择 全部 班组
    chooseWorkShiftAll: function () {
        var $container = $attendance_machine_modal.find(".work_shift_container");
        var $btn_all = $container.find(".btn_all");//全选  按钮
        var $btn = $container.find(".work_shift_list .btn");//班组 item

        //如果已经 全选了
        if ($btn_all.hasClass("active")) {
            $btn_all.removeClass("active");
            $btn.removeClass("active");
        }
        else {
            $btn_all.addClass("active");
            $btn.addClass("active");
        }
    },
    //检查 班组 是否被 全选
    checkIsChooseAll: function () {
        var $container = $attendance_machine_modal.find(".work_shift_container");
        var $btn_all = $container.find(".btn_all");//全选  按钮
        var $btn = $container.find(".work_shift_list .btn");//班组 item
        var $btn_active = $container.find(".work_shift_list .btn.active");//班组 已选中

        if ($btn.length === $btn_active.length) {
            $btn_all.addClass("active");
        }
        else {
            $btn_all.removeClass("active");
        }

    },

    //保存 对应的班组
    save: function () {
        var $row = $attendance_machine_modal.find(".modal-body > .row");

        //班组
        var workShiftIds = [];
        $row.find(".work_shift_list .btn").each(function () {
            var $this = $(this);

            if ($this.hasClass("active")) {
                var id = $this.attr("data-id");

                workShiftIds.push(id);
            }

        });

        //设备名称
        var name = $.trim($row.find(".machine_name input").val());
        //设备编号
        var deviceNo = $row.find(".machine_no").text();

        var obj = {
            deviceName: name,
            deviceNo: deviceNo,
            workShiftIds: workShiftIds
        };

        loadingInit();

        branPostRequest(
            urlGroup.attendance.setting.attendanceMachine.save,
            obj,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");
                    $attendance_machine_modal.modal("hide");

                    attendance_machine.initTb();
                }
                else {
                    toastr.warning(res.msg);
                }

            },
            function (err) {

            }
        );

    }


};

$(function () {
    attendance_setting.init();
});

