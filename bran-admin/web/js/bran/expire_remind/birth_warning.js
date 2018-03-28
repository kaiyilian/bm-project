/**
 * 生日提醒
 * */

var $birth_warning_container = $(".birth_warning_container");
var $tb_birth_warning = $birth_warning_container.find("#tb_birth_warning");

//生日提醒
var birth_warning = {

    //初始化
    init: function () {
        birth_warning.initDT();//初始化 表格信息
    },
    //初始化 表格信息
    initDT: function () {
        // console.log(salary_create.salary_excel_result);
        loadingInit();

        //初始化 column
        var columns = [];
        columns.push({
            checkbox: true
        });
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
            title: "注册手机",
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
            field: "curYearBirthday",
            title: "生日日期",
            align: "center",
            class: "curYearBirthday",
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
            field: "age",
            title: "年龄",
            align: "center",
            class: "age",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "岁</div>";
                }
                else {
                    html = "<div>0</div>";
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
            field: "isDispose",
            title: "状态",
            align: "center",
            class: "isDispose",
            formatter: function (value, row, index) {
                // console.log(value);
                var html = "";
                if (value) {
                    html = "<div>已处理</div>";
                }
                else {
                    html = "<div>未处理</div>";
                }

                return html;
            }
        });

        $tb_birth_warning.bootstrapTable("destroy");
        //表格的初始化
        $tb_birth_warning.bootstrapTable({

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
            height: 300,
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
            url: urlGroup.home.birth.list,
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

            },
            onLoadError: function () {  //加载失败时执行
                console.log("加载失败！");
                // debugger
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {
                console.log("获取最近30天生日的员工：" + new Date().getTime());

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

                                var id = item.id ? item.id : "";//
                                var realName = item.realName ? item.realName : "";//
                                var telephone = item.telephone ? item.telephone : "";//
                                var checkinTime = item.checkinTime ? item.checkinTime : 0;//

                                var positionName = item.positionName ? item.positionName : "";//
                                var workShiftName = item.workShiftName ? item.workShiftName : "";//
                                var workLineName = item.workLineName ? item.workLineName : "";//
                                var departmentName = item.departmentName ? item.departmentName : "";//

                                var curYearBirthday = item.curYearBirthday ? item.curYearBirthday : 0;//
                                var age = item.age ? item.age : 0;//
                                var warningDays = item.warningDays ? item.warningDays : 0;//
                                var isDispose = item.isDispose ? item.isDispose : 0;//0未处理 1已处理

                                var obj = {

                                    id: id,
                                    realName: realName,
                                    telephone: telephone,
                                    checkinTime: checkinTime,

                                    positionName: positionName,
                                    workShiftName: workShiftName,
                                    workLineName: workLineName,
                                    departmentName: departmentName,

                                    curYearBirthday: curYearBirthday,
                                    age: age,
                                    warningDays: warningDays,
                                    isDispose: isDispose

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
                return '暂无30天内过生日的员工';
            }

        });

    },

    //处理
    dispose: function () {

        var data = $tb_birth_warning.bootstrapTable("getAllSelections");

        if (data.length === 0) {
            toastr.warning("您没有选择用户！");
            return
        }

        operateMsgShow(
            "确定要对选中的员工进行生日提醒吗？",
            function () {

                var batch = [];
                $.each(data, function (i, item) {
                    batch.push({
                        version: 0,
                        id: item.id,
                        name: item.realName
                    });
                });

                var obj = {
                    batch: batch
                };

                branPostRequest(
                    urlGroup.home.birth.dispose,
                    obj,
                    function (res) {

                        if (res.code === RESPONSE_OK_CODE) {
                            toastr.success("提醒成功！");
                            birth_warning.initDT();//初始化 表格信息
                        }
                        else {
                            toastr.warning(res.msg);
                        }

                    },
                    function (err) {

                    }
                );


            },
            ""
        );

    }


};

$(function () {
    birth_warning.init();
});