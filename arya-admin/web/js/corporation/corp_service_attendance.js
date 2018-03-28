/**
 * Created by CuiMengxin on 2017/5/4.
 * 考勤服务
 */

var $corp_service_attendance_container = $(".corp_service_attendance_container");
var $attendance_info_container = $corp_service_attendance_container.find(".attendance_info_container");
var $attendance_machine_method = $attendance_info_container.find("#attendance_machine_method");//考勤机打卡 container
var $phone_method = $attendance_info_container.find("#phone_method");//手机打卡 container

var corp_service_attendance = {

    node: "",
    organize_type: "",//组织类型:1集团，2子公司，3通用部门，4一级公司
    organize_id: "",//组织id
    organize_pid: "",//组织pid
    btn_value: "0",//
    clockTypes: [],//打卡类型 0.没有选择打卡方式 1.考勤机打卡 2.手机打卡

    //初始化
    init: function () {

        //默认显示 考勤机打卡
        $attendance_info_container.find('.nav_attendance a[href="#attendance_machine_method"]').tab('show');

        corp_service_attendance.clearAttendanceInfo();	//清空 内容
        attendance_machine.initMachineList();		//清空 考勤机列表

    },

    //树形菜单 - 点击事件
    groupTreeOnclick: function (node) {
        //console.log(JSON.stringify(node));

        loadingInit();//加载中 弹框显示

        corp_service_attendance.node = node;
        corp_service_attendance.organize_id = node.id;//组织id
        corp_service_attendance.organize_pid = node.pid ? node.pid : 0;//父级id
        corp_service_attendance.organize_type = node.type;//组织类型:1集团，2子公司，3通用部门，4一级公司

        corp_service_attendance.attendanceInfo();//查询 考勤信息
        corp_service_attendance.checkAttendanceSync();//考勤机列表

        attendance_machine.attendanceMachineList();//考勤机列表
    },

    //清空 内容
    clearAttendanceInfo: function () {

        //隐藏 打卡类型 保存按钮
        $attendance_info_container.find(".btn_operate").find(".btn").hide();

        //打卡方式 是否启用 禁用
        $attendance_info_container.find(".is_enable").unbind("click");
        $attendance_info_container.find(".is_enable").removeClass("active")
            .find("img").attr("src", "img/icon_Unchecked.png");

        //初始化 考勤机打卡 (移除绑定事件)
        $attendance_machine_method.find(".table_container .btn").unbind("click");
        $attendance_machine_method.find(".btn_add").unbind("click");

        $attendance_machine_method.find(".btn_add").addClass("btn-default")
            .removeClass("btn-primary");
        $attendance_machine_method.find(".table_container .btn")
            .addClass("btn-default")
            .removeClass("btn-primary")
            .removeClass("btn-danger");

        var $sync_time = $attendance_info_container.find(".attendance_sync .sync_time");
        var $btn_sync = $attendance_info_container.find(".attendance_sync .btn_sync");
        $sync_time.attr("disabled", "disabled");
        $btn_sync.addClass("btn-default").removeClass("btn-primary");
        $btn_sync.unbind("click");


    },
    //初始化 页面 - 绑定事件
    initBind: function () {

        //打卡方式 是否启用 绑定事件
        $attendance_info_container.find(".is_enable")
            .unbind("click").bind("click", function () {
            corp_service_attendance.chooseIsEnable(this);
        });

        //初始化 考勤机打卡 (绑定事件)

        //新增
        $attendance_machine_method.find(".btn_add").addClass("btn-primary")
            .removeClass("btn-default")
            .unbind("click").bind("click", function () {
            attendance_machine.add();
        });

        //编辑
        $attendance_machine_method.find(".btn_modify").addClass("btn-primary")
            .removeClass("btn-default")
            .unbind("click").bind("click", function () {
            attendance_machine.modify(this);
        });

        //删除
        $attendance_machine_method.find(".btn_del").addClass("btn-danger")
            .removeClass("btn-default")
            .unbind("click").bind("click", function () {
            attendance_machine.del(this);
        });


    },
    //选择 是否启用
    chooseIsEnable: function (self) {
        var $self = $(self);

        if ($self.hasClass("active")) {
            $self.removeClass("active");
            $self.find("img").attr("src", "img/icon_Unchecked.png");
        }
        else {
            $self.addClass("active");
            $self.find("img").attr("src", "img/icon_checked.png");
        }

    },

    //考勤信息 查询
    attendanceInfo: function () {
        corp_service_attendance.clearAttendanceInfo();//清空 内容

        var obj = {
            corp_id: corp_service_attendance.organize_id
        };
        var url = urlGroup.corp_service_attendance_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.info("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    corp_service_attendance.clockTypes = $item.clockTypes ? $item.clockTypes : [];
                    $.each(corp_service_attendance.clockTypes, function (i, item) {

                        // var item = corp_service_attendance.clockTypes[0];

                        //打卡类型 0.没有选择打卡方式 1.考勤机打卡 2.手机打卡
                        var type = item.type ? item.type : 0;

                        if (type === 1) {

                            $attendance_machine_method.find(".is_enable")
                                .addClass("active")
                                .find("img").attr("src", "img/icon_checked.png");

                        }

                        if (type === 2) {

                            $phone_method.find(".is_enable")
                                .addClass("active")
                                .find("img").attr("src", "img/icon_checked.png");

                            // //显示
                            // $attendance_info_container.find('.nav_attendance a[href="#phone_method"]').tab('show');

                        }

                    });

                    corp_service_attendance.attendanceInfoInit();//考勤信息 - 详情 初始化

                }
                else {
                    toastr.info(data.msg);
                }

            },
            function () {
                toastr.error("系统错误，请联系管理员！");
            }
        );

    },
    //考勤信息 - 详情 初始化
    attendanceInfoInit: function () {

        var $btn_operate = $attendance_info_container.find(".btn_operate");
        corp_service_attendance.btn_value = $btn_operate.find(".btn_modify").attr("data-value");

        corp_service_attendance.BtnOperateInit();//底部按钮 初始化
    },

    //考勤信息 编辑
    attendanceInfoModify: function () {
        corp_service_attendance.initBind();//初始化 页面 - 绑定事件

        var $btn_operate = $attendance_info_container.find(".btn_operate");
        //保存
        var value_1 = $btn_operate.find(".btn_save").attr("data-value");
        //取消
        var value_2 = $btn_operate.find(".btn_cancel").attr("data-value");

        //底部 按钮
        corp_service_attendance.btn_value = value_1 | value_2;

        corp_service_attendance.BtnOperateInit();//底部按钮 初始化

    },
    //考勤信息 - 取消编辑
    attendanceInfoCancelByModify: function () {
        corp_service_attendance.attendanceInfo();//考勤信息 查询
    },
    //考勤信息 - 保存
    attendanceInfoSave: function () {

        corp_service_attendance.checkParamByAttendanceSave();

        // if (!corp_service_attendance.checkParamByAttendanceSave()) {
        //     return
        // }

        var txt = "确认保存吗？";
        // if (corp_service_attendance.clockTypes[0].type == 0) {
        //     txt = "确认不选择打卡类型吗？";
        // }
        // if (corp_service_attendance.clockTypes[0].type == 1) {
        //     txt = "确认选择'考勤机打卡'吗？";
        // }
        // if (corp_service_attendance.clockTypes[0].type == 2) {
        //     txt = "确认选择'手机打卡'吗？";
        // }

        operateShow(txt, function () {

            loadingInit();//加载中 弹框显示

            var obj = {
                corpId: corp_service_attendance.organize_id,
                clockTypes: corp_service_attendance.clockTypes
            };

            aryaPostRequest(
                urlGroup.corp_service_attendance_save,
                obj,
                function (data) {
                    //console.log("获取日志：");
                    //console.log(data);

                    if (data.code === RESPONSE_OK_CODE) {

                        toastr.success("设置成功");
                        corp_service_attendance.attendanceInfo();//考勤信息 查询

                    }
                    else {
                        toastr.warning(data.msg);
                    }

                },
                function (error) {
                    toastr.error(error);
                }
            );

        })

    },
    //检查 考勤信息 保存参数
    checkParamByAttendanceSave: function () {
        // var flag = false;
        // var txt = "";

        corp_service_attendance.clockTypes = [];

        if ($attendance_machine_method.find(".is_enable").hasClass("active")) {
            corp_service_attendance.clockTypes.push({
                "type": 1
            });
        }
        if ($phone_method.find(".is_enable").hasClass("active")) {
            corp_service_attendance.clockTypes.push({
                "type": 2
            });
        }

        if (corp_service_attendance.clockTypes.length === 0) {
            corp_service_attendance.clockTypes.push({
                "type": 0
            });
        }

        // if (corp_service_attendance.clockTypes.length > 1) {
        //     txt = "只能选择一种打卡方式";
        // }
        // else {
        //     flag = true;
        // }

        // if (txt) {
        //     toastr.warning(txt);
        // }
        //
        // return flag;

    },

    //底部按钮 初始化
    BtnOperateInit: function () {

        $attendance_info_container.find(".btn_operate").find(".btn").hide();

        $attendance_info_container.find(".btn_operate").find(".btn").each(function () {
            var $this = $(this);

            var value = $this.attr("data-value");
            if (value & corp_service_attendance.btn_value) {
                $this.show();
            }

        });

    },

    //检查考勤同步
    checkAttendanceSync: function () {

        var $sync_time = $attendance_info_container.find(".attendance_sync .sync_time");
        var $btn_sync = $attendance_info_container.find(".attendance_sync .btn_sync");

        // localStorage.setItem("attendance_sync_time", "1520498145016");
        //上次点击按钮的时间
        var attendance_sync_time = localStorage.getItem("attendance_sync_time") ?
            parseInt(localStorage.getItem("attendance_sync_time")) : 0;
        var cur_time = new Date().getTime();//当前时间
        var interval_time = cur_time - attendance_sync_time;//间隔时间

        console.log(interval_time);

        //如果在5分钟之内
        if (interval_time < (5 * 60 * 1000)) {
            $sync_time.attr("disabled", "disabled");
            $btn_sync.addClass("btn-default").removeClass("btn-primary");
            $btn_sync.unbind("click");

        }
        else {

            $sync_time.removeAttr("disabled").val("");
            $btn_sync.addClass("btn-primary").removeClass("btn-default");
            $btn_sync.unbind("click").bind("click", function () {
                corp_service_attendance.attendanceSync();//考勤同步
            });

        }

    },
    //考勤同步
    attendanceSync: function () {

        var $sync_time = $attendance_info_container.find(".attendance_sync .sync_time");
        var sync_time = $.trim($sync_time.val());//考勤同步时间
        // if (sync_time) {
        //     sync_time = new Date(sync_time).getTime();
        // }

        //调用接口、设置考勤机同步
        loadingInit();

        var obj = {
            bran_corp_id: corp_service_attendance.organize_id,
            date_time: sync_time
        };

        aryaPostRequest(
            urlGroup.corp_service_attendance_sync,
            obj,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("同步成功！");

                    if (sync_time) {
                        sync_time = new Date(sync_time).getTime();
                    }
                    localStorage.setItem("attendance_sync_time", new Date().getTime());

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                toastr.error(error);
            }
        );


    }

};

//考勤机 打卡
var attendance_machine = {

    id: "",//
    deviceNo: "",//考勤机 编号

    //清空 考勤机列表
    initMachineList: function () {
        var $table = $attendance_machine_method.find(".table_container table");
        var $tbody = $table.find("tbody");
        $tbody.empty();

    },
    //考勤机列表
    attendanceMachineList: function () {
        var $table = $attendance_machine_method.find(".table_container table");
        var $tbody = $table.find("tbody");
        $tbody.empty();

        var obj = {
            aryaCorpId: corp_service_attendance.organize_id
        };
        var url = urlGroup.corp_service_attendance_machine_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {

                    var types = data.result ? data.result : [];
                    $.each(types, function (index, item) {

                        var id = item.id ? item.id : "";//
                        //var txVersion = item.txVersion ? item.txVersion : "";//
                        var deviceNo = item.deviceNo ? item.deviceNo : "";//

                        //item
                        var $item = $("<tr>");
                        $item.addClass("item");
                        $item.attr("data-id", id);
                        //$item.attr("data-version", txVersion);
                        $item.appendTo($tbody);

                        //考勤机 id
                        var $td = $("<td>");
                        $td.addClass("deviceNo");
                        $td.text(deviceNo);
                        $td.appendTo($item);

                        //操作
                        var $operate = $("<td>");
                        $operate.addClass("operate");
                        $operate.appendTo($item);

                        //编辑
                        var $btn_modify = $("<div>");
                        $btn_modify.addClass("btn");
                        $btn_modify.addClass("btn-sm");
                        $btn_modify.addClass("btn-default");
                        $btn_modify.addClass("btn_modify");
                        $btn_modify.text("编辑");
                        $btn_modify.appendTo($operate);

                        //删除
                        var $btn_del = $("<div>");
                        $btn_del.addClass("btn");
                        $btn_del.addClass("btn-sm");
                        $btn_del.addClass("btn-default");
                        $btn_del.addClass("btn_del");
                        $btn_del.text("删除");
                        $btn_del.appendTo($operate);

                    });

                }
                else {
                    console.log("获取日志-----error：");
                    console.log(data.msg);

                    toastr.warning(data.msg);
                }
            },
            function (error) {
                toastr.error(error);
            }
        );

    },

    //编辑
    modify: function (self) {
        var $self = $(self);
        var $item = $self.closest(".item");

        attendance_machine.id = $item.attr("data-id");
        attendance_machine.deviceNo = $item.find(".deviceNo").html();

        var $input = $("<input>");
        $input.addClass("form-control");
        $input.attr("maxlength", "16");
        $input.val(attendance_machine.deviceNo);
        $item.find(".deviceNo").html($input);

        //操作
        var $operate = $item.find(".operate");
        $operate.empty();

        //保存
        var $btn_save = $("<div>");
        $btn_save.addClass("btn");
        $btn_save.addClass("btn-sm");
        $btn_save.addClass("btn-primary");
        $btn_save.addClass("btn_save");
        $btn_save.text("保存");
        $btn_save.bind("click", function () {
            attendance_machine.save(this);
        });
        $btn_save.appendTo($operate);

        //取消
        var $btn_cancel = $("<div>");
        $btn_cancel.addClass("btn");
        $btn_cancel.addClass("btn-sm");
        $btn_cancel.addClass("btn-primary");
        $btn_cancel.addClass("btn_cancel");
        $btn_cancel.text("取消");
        $btn_cancel.bind("click", function () {
            attendance_machine.cancel(this);
        });
        $btn_cancel.appendTo($operate);

    },
    //删除
    del: function (self) {
        var $self = $(self);
        var $item = $self.closest(".item");

        attendance_machine.id = $item.attr("data-id");

        delWarning(
            "确定要删除该考勤机吗？",
            function () {

                loadingInit();

                var obj = {
                    batch: [
                        {
                            id: attendance_machine.id
                        }
                    ]
                };

                aryaPostRequest(
                    urlGroup.corp_service_attendance_machine_del,
                    obj,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code == RESPONSE_OK_CODE) {
                            toastr.success("删除成功！");
                            $item.remove();
                        }
                        else {
                            toastr.warning(data.msg);
                        }

                    },
                    function (error) {
                        toastr.error(error);
                    }
                );

            },
            "该操作可能导致考勤数据丢失，确定要删除吗？"
        );

    },
    //取消编辑
    cancel: function (self) {
        var $self = $(self);
        var $item = $self.closest(".item");

        $item.find(".deviceNo").html(attendance_machine.deviceNo);

        //操作
        var $operate = $item.find(".operate");
        $operate.empty();

        //编辑
        var $btn_modify = $("<div>");
        $btn_modify.addClass("btn");
        $btn_modify.addClass("btn-sm");
        $btn_modify.addClass("btn-primary");
        $btn_modify.addClass("btn_modify");
        $btn_modify.text("编辑");
        $btn_modify.unbind("click").bind("click", function () {
            attendance_machine.modify(this);
        });
        $btn_modify.appendTo($operate);

        //删除
        var $btn_del = $("<div>");
        $btn_del.addClass("btn");
        $btn_del.addClass("btn-sm");
        $btn_del.addClass("btn-danger");
        $btn_del.addClass("btn_del");
        $btn_del.text("删除");
        $btn_del.unbind("click").bind("click", function () {
            attendance_machine.del(this);
        });
        $btn_del.appendTo($operate);

    },
    //保存
    save: function (self) {
        var $self = $(self);
        var $item = $self.closest(".item");

        attendance_machine.deviceNo = $item.find(".deviceNo input").val();

        var obj = {
            aryaCorpId: corp_service_attendance.organize_id,
            deviceNo: attendance_machine.deviceNo
        };
        var url;

        //编辑后保存
        if (attendance_machine.id) {
            url = urlGroup.corp_service_attendance_machine_modify;
            obj["id"] = attendance_machine.id;
        }
        else {
            url = urlGroup.corp_service_attendance_machine_add;
        }

        aryaPostRequest(
            url,
            obj,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {
                    var id = data.result.id ? data.result.id : "";

                    toastr.success("保存成功！");
                    //attendance_machine.attendanceMachineList();//

                    //return;
                    $item.removeClass("add_item");
                    $item.attr("data-id", id);
                    $item.find(".deviceNo").html(attendance_machine.deviceNo);

                    //操作
                    var $operate = $item.find(".operate");
                    $operate.empty();

                    //编辑
                    var $btn_modify = $("<div>");
                    $btn_modify.addClass("btn");
                    $btn_modify.addClass("btn-sm");
                    $btn_modify.addClass("btn-primary");
                    $btn_modify.addClass("btn_modify");
                    $btn_modify.text("编辑");
                    $btn_modify.unbind("click").bind("click", function () {
                        attendance_machine.modify(this);
                    });
                    $btn_modify.appendTo($operate);

                    //删除
                    var $btn_del = $("<div>");
                    $btn_del.addClass("btn");
                    $btn_del.addClass("btn-sm");
                    $btn_del.addClass("btn-danger");
                    $btn_del.addClass("btn_del");
                    $btn_del.text("删除");
                    $btn_del.unbind("click").bind("click", function () {
                        attendance_machine.del(this);
                    });
                    $btn_del.appendTo($operate);

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                toastr.error(error);
            }
        );


    },
    //新增
    add: function () {
        attendance_machine.id = "";

        var $table = $attendance_machine_method.find(".table_container table");
        var $tbody = $table.find("tbody");

        if ($tbody.find(".add_item").length > 0) {
            toastr.warning("有新增的考勤机正在操作，请先保存！");
            return;
        }

        //item
        var $add = $("<tr>");
        $add.addClass("item");
        $add.addClass("add_item");
        $add.appendTo($tbody);

        //考勤机 编号
        var $td = $("<td>");
        $td.addClass("deviceNo");
        $td.appendTo($add);

        var $input = $("<input>");
        $input.addClass("form-control");
        $input.val("");
        $input.attr("placeholder", "请输入考勤机编号");
        $input.attr("maxlength", "16");
        $input.appendTo($td);

        //操作
        var $operate = $("<td>");
        $operate.addClass("operate");
        $operate.appendTo($add);

        //保存
        var $btn_save = $("<div>");
        $btn_save.addClass("btn");
        $btn_save.addClass("btn-sm");
        $btn_save.addClass("btn-primary");
        $btn_save.addClass("btn_save");
        $btn_save.text("保存");
        $btn_save.bind("click", function () {
            attendance_machine.save(this);
        });
        $btn_save.appendTo($operate);

        //取消
        var $btn_cancel = $("<div>");
        $btn_cancel.addClass("btn");
        $btn_cancel.addClass("btn-sm");
        $btn_cancel.addClass("btn-primary");
        $btn_cancel.addClass("btn_cancel");
        $btn_cancel.text("取消");
        $btn_cancel.bind("click", function () {
            $(this).closest(".item").remove();
        });
        $btn_cancel.appendTo($operate);

    }

};

$(document).ready(function () {

    var treeId = ".corp_service_attendance_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_service_attendance_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_service_tree1_url + "?business_type=" + corp_service_type.attendance;//获取树结构 url
    var treeClickFunc = corp_service_attendance.groupTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_service_attendance.init;	//初始化 页面

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构

    corp_service_attendance.init();

});
