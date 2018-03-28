/**
 * 身份证到期提醒
 * */

var $idCard_expire_manage_container = $(".idCard_expire_manage_container");//
var $tb_idCard_expire = $idCard_expire_manage_container.find("#tb_idCard_expire");
var $idCard_validity_set_modal = $(".idCard_validity_set_modal");

//身份证到期提醒
var idCard_expire_manage = {
    row: null,

    //初始化
    init: function () {
        idCard_expire_manage.initDT();//初始化 表格信息

        $idCard_validity_set_modal.on("shown.bs.modal", function () {

            idCard_expire_manage.initTime();//初始化 日期插件
            idCard_expire_manage.initChooseEndDate();//初始化 选择结束日期

        });

        // $idCard_validity_set_modal.modal("show");
    },
    //初始化 表格信息
    initDT: function () {
        // console.log(salary_create.salary_excel_result);
        loadingInit();

        //初始化 column
        var columns = [];
        columns.push({
            field: "realName",
            title: "姓名",
            align: "center",
            class: "realName",
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
        });
        columns.push({
            field: "telephone",
            title: "注册账号",
            align: "center",
            class: "telephone",
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
        });
        columns.push({
            field: "checkinTime",
            title: "入职时间",
            align: "center",
            class: "checkinTime",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                if (value) {
                    html = "<div>" + timeInit(value) + "</div>";
                }
                else {
                    html = "<div></div>";
                }

                return html;
            }
        });
        columns.push({
            field: "positionName",
            title: "职位",
            align: "center",
            class: "positionName",
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
        });
        columns.push({
            field: "workShiftName",
            title: "班组",
            align: "center",
            class: "workShiftName",
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
        });
        columns.push({
            field: "workLineName",
            title: "工段",
            align: "center",
            class: "workLineName",
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
        });
        columns.push({
            field: "departmentName",
            title: "部门",
            align: "center",
            class: "departmentName",
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
        });
        columns.push({
            field: "expireTime",
            title: "到期时间",
            align: "center",
            class: "expireTime",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                if (value) {
                    html = "<div>" + timeInit(value) + "</div>";
                }
                else {
                    html = "<div></div>";
                }

                return html;
            }
        });
        columns.push({
            field: "warningDays",
            title: "剩余天数",
            align: "center",
            class: "warningDays",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }
                else {
                    html = "<div>0</div>";
                }

                return html;
            }
        });
        columns.push({
            field: "operate",
            title: "操作",
            align: "center",
            class: "operate",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                html += "<div class='operate'>";

                //退工
                html += "<div class='btn btn-success btn-sm btn_update'>更新</div>";

                html += "</div>";

                return html;
            },
            events: {

                //更新
                "click .btn_update": function (e, value, row, index) {

                    idCard_expire_manage.row = row;

                    $idCard_validity_set_modal.modal("show");
                    var time = timeInit(row.expireTime);
                    $idCard_validity_set_modal.find(".modal-body .expire_time").text(time);

                }

            }
        });

        $tb_idCard_expire.bootstrapTable("destroy");
        //表格的初始化
        $tb_idCard_expire.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

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
            sortable: false,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 300,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: columns,

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.home.idCard.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                console.log(params);
                return {
                    page: params.pageNumber,
                    page_size: params.pageSize
                };

            },
            onLoadSuccess: function () {  //加载成功时执行

            },
            onLoadError: function () {  //加载失败时执行
                console.log("加载失败！");
                // debugger
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {
                console.log("获取最近一年身份证到期员工：" + new Date().getTime());

                setTimeout(function () {
                    loadingRemove();
                }, 500);

                var tb_data = [];
                var total_rows = 0;//总条数

                // tb_data = [{
                //     expireTime: "1516164789620"
                // }];

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows ? res.result.total_rows : 0;//总条数

                        var arr = res.result.result;
                        if (arr && arr.length > 0) {

                            for (var i = 0; i < arr.length; i++) {
                                var item = arr[i];

                                var id = item.id ? item.id : "";//
                                var realName = item.realName ? item.realName : "";//
                                var telephone = item.telephone ? item.telephone : "";//
                                var checkinTime = item.checkinTime ? item.checkinTime : 0;//

                                var positionName = item.positionName ? item.positionName : "";//
                                var workShiftName = item.workShiftName ? item.workShiftName : "";//
                                var workLineName = item.workLineName ? item.workLineName : "";//
                                var departmentName = item.departmentName ? item.departmentName : "";//

                                var expireTime = item.expireTime ? item.expireTime : 0;//
                                var warningDays = item.warningDays ? item.warningDays : 0;//

                                var obj = {

                                    id: id,
                                    realName: realName,
                                    telephone: telephone,
                                    checkinTime: checkinTime,

                                    positionName: positionName,
                                    workShiftName: workShiftName,
                                    workLineName: workLineName,
                                    departmentName: departmentName,

                                    expireTime: expireTime,
                                    warningDays: warningDays

                                };
                                tb_data.push(obj);

                            }

                        }

                    }
                    else {
                        // toastr.warning(res.msg);
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

            formatNoMatches: function () {
                return '暂无一年内身份证到期的员工';
            }

        });

    },
    //初始化 日期插件
    initTime: function () {
        var $row = $idCard_validity_set_modal.find(".modal-body .row");

        //开始时间 click
        $row.find(".idCard_begin_time").html("").attr("data-time", "");
        $row.find(".idCard_begin_time").click(function () {
            chooseBeginTime();
        });
        $row.find(".icon_begin").click(function () {
            chooseBeginTime();
        });

        //结束时间 click
        $row.find(".idCard_end_time").html("").attr("data-time", "");
        $row.find(".idCard_end_time").click(function () {
            chooseEndTime();
        });
        $row.find(".icon_end").click(function () {
            chooseEndTime();
        });

        var chooseBeginTime = function () {
            var opt = {
                elem: "#idCard_begin_time",
                istoday: false, //是否显示今天
                choose: function (date) { //选择日期完毕的回调
                    var time = new Date(date).getTime();
                    $row.find(".idCard_begin_time").attr("data-time", time);

                    //合同结束日期 初始化
                    $row.find(".idCard_end_time").html("").attr("data-time", "");
                    idCard_expire_manage.initChooseEndDate();//初始化 选择结束日期

                }
            };

            laydate(opt);

        };

        var chooseEndTime = function () {

            var begin = $idCard_validity_set_modal.find(".idCard_begin_time").html();
            if (!begin) {
                toastr.warning("请先选择开始日期！");
                return
            }

            var opt = {
                elem: "#idCard_end_time",
                istoday: false, //是否显示今天
                choose: function (date) { //选择日期完毕的回调
                    var time = new Date(date).getTime();
                    $row.find(".idCard_end_time").attr("data-time", time);//

                    idCard_expire_manage.initChooseEndDate();//初始化 选择结束日期
                }
            };

            laydate(opt)
        };


    },
    //初始化 选择结束日期
    initChooseEndDate: function () {

        //置为初始状态
        $idCard_validity_set_modal.find(".end_date_list").find(".active").removeClass("active");
        $idCard_validity_set_modal.find(".end_date_list").find("img").attr("src", "image/UnChoose.png");

        //选择结束日期
        $idCard_validity_set_modal.find(".end_date_list > .item").unbind("click").bind("click", function () {

            var $self = $(this);

            var begin = $idCard_validity_set_modal.find(".idCard_begin_time").html();
            if (!begin) {
                toastr.warning("请先选择开始日期！");
                return
            }

            $self.addClass("active").siblings(".item").removeClass("active");
            $self.find("img").attr("src", "image/Choosed.png");
            $self.siblings(".item").find("img").attr("src", "image/UnChoose.png");

            var date = parseInt($self.data("time"));//选择的日期
            var end_time = "";//结束日期
            var time = "";//结束日期的 时间戳

            //无期限
            if (date === 0) {
                end_time = "无期限";
                time = "253402185600000";//写死 默认的的 无期限时间戳
            }
            else {
                begin = new Date(begin);
                end_time = new Date(begin);
                end_time.setFullYear(end_time.getFullYear() + date);
                end_time.setDate(end_time.getDate() - 1);
                end_time = new Date(end_time);

                var year = end_time.getFullYear();//计算 结束日期年份
                var month = end_time.getMonth() + 1;
                month = month < 10 ? "0" + month : month;
                var day = end_time.getDate();//结束日期 天
                day = day < 10 ? "0" + day : day;

                end_time = year + "-" + month + "-" + day;//
                time = new Date(end_time).getTime();

            }

            $idCard_validity_set_modal.find(".idCard_end_time").html(end_time);
            $idCard_validity_set_modal.find(".idCard_end_time").attr("data-time", time);

        });

    },

    //更新身份证有效期
    idCardValidityUpdate: function () {
        var $row = $idCard_validity_set_modal.find(".modal-body .row");

        var start = $row.find(".idCard_begin_time").text();
        start = start ? new Date(start).getTime() : "";
        var end = $row.find(".idCard_end_time").attr("data-time");
        // var end = $row.find(".idCard_end_time").text();
        // end = end ? new Date(end).getTime() : "";
        var validity = parseInt($idCard_validity_set_modal.find(".end_date_list").find(".active").attr("data-time"));
        var isLongTerm = validity ? 1 : 0;

        var obj = {
            id: idCard_expire_manage.row.id,
            start: start,
            end: end,
            isLongTerm: isLongTerm
        };

        branPostRequest(
            urlGroup.home.idCard.set,
            obj,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {
                    toastr.success("更新成功！");
                    $idCard_validity_set_modal.modal("hide");
                    idCard_expire_manage.initDT();//初始化 表格信息
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
    idCard_expire_manage.init();
});