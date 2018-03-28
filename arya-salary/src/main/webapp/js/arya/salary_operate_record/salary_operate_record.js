/**
 * Created by Administrator on 2017/7/11.
 * 薪资操作记录
 */

var $salary_operate_record_container = $(".salary_operate_record_container");
var $tb_record = $salary_operate_record_container.find("#tb_record");//反馈记录 list

var salary_operate_record = {

    init: function () {

        salary_operate_record.btnSearchClick();

    },

    //查询
    btnSearchClick: function () {
        salary_operate_record.recordList();//获取 记录列表
    },
    //获取 记录列表
    recordList: function () {

        var $search_container = $salary_operate_record_container.find(".search_container");

        var obj = {
            condition: $.trim($search_container.find(".search_condition").val()),
            // page: "",
            // page_size: "10"
        };
        var url = urlGroup.salary_operate_record_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var tb_data = [];

                    if (data.result) {

                        var arr = data.result.result ? data.result.result : [];
                        if (arr.length > 0) {
                            for (var i = 0; i < arr.length; i++) {
                                var $item = arr[i];

                                var id = $item.id ? $item.id : "";//
                                var customerName = $item.customerName ? $item.customerName : "";//
                                var districtName = $item.districtName ? $item.districtName : "";//
                                var logInfo = $item.logInfo ? $item.logInfo : "";//
                                var createTime = $item.createTime ? $item.createTime : "";//
                                createTime = timeInit(createTime);
                                var remark = $item.remark ? $item.remark : "";//

                                var obj = {

                                    id: id,

                                    customerName: customerName,//客户名称
                                    districtName: districtName,//城市
                                    logInfo: logInfo,//计算反馈（错误信息）
                                    createTime: createTime,//导入时间
                                    remark: remark      //备注

                                };

                                tb_data.push(obj);

                            }
                        }

                    }

                    salary_operate_record.initTbData(tb_data);	//记录列表

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
    //dataTable 初始化
    initTbData: function (data) {

        $tb_record.bootstrapTable("destroy");
        //表格的初始化
        $tb_record.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

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
            // height: 400,
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
                    field: 'index',
                    title: '序号',
                    align: "center",
                    class: "index",
                    formatter: function (value, row, index) {

                        var html = "<div style='width:30px;'>" + (index + 1 ) + "</div>";

                        return html;

                    }
                },
                {
                    field: 'customerName',
                    title: '客户名称',
                    // sortable: true,
                    align: "center",
                    class: "customerName",
                    width: 200
                },
                {
                    field: 'districtName',
                    title: '城市',
                    // sortable: true,
                    align: "center",
                    class: "districtName",
                    width: 200
                },
                {
                    field: 'logInfo',
                    title: '计算反馈',
                    // sortable: true,
                    align: "center",
                    class: "logInfo",
                    width: 200
                },
                {
                    field: 'createTime',
                    title: '导入时间',
                    // sortable: true,
                    align: "center",
                    class: "createTime",
                    width: 200
                },
                {
                    field: 'remark',
                    title: '备注',
                    align: "center",
                    class: "remark",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";
                        return html;

                    }
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: "center",
                    class: "operate",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        //编辑
                        html += "<button class='btn btn-primary btn-sm btn_modify'>编辑</button>";
                        //删除
                        html += "<button class='btn btn-primary btn-sm btn_del'>删除</button>";
                        //保存
                        html += "<button class='btn btn-primary btn-sm hide btn_save'>保存</button>";
                        //取消
                        html += "<button class='btn btn-primary btn-sm hide btn_cancel'>取消</button>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //编辑
                        "click .btn_modify": function (e, value, row, index) {

                            // console.log(e);
                            var $e = $(e.currentTarget);
                            var $item = $e.closest(".item");

                            var $div = $("<div>");
                            var $np = $("<input>");
                            $np.addClass("form-control");
                            $np.val(row.remark);
                            $np.appendTo($div);

                            $item.find(".remark").html($div);

                            //operate
                            var $operate = $item.find(".operate");
                            $operate.find(".btn_modify").addClass("hide");
                            $operate.find(".btn_del").addClass("hide");
                            $operate.find(".btn_save").removeClass("hide");
                            $operate.find(".btn_cancel").removeClass("hide");

                        },
                        //删除
                        "click .btn_del": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");

                            var delArr = [row.id];

                            delWarning(
                                "确认要删除吗？",
                                null,
                                function () {

                                    var obj = {
                                        salaryIds: delArr
                                    };

                                    loadingInit();

                                    aryaPostRequest(
                                        urlGroup.salary_operate_record_del,
                                        obj,
                                        function (data) {
                                            //console.log(data);

                                            if (data.code === RESPONSE_OK_CODE) {

                                                toastr.success("删除成功！");
                                                $tb_record.bootstrapTable('remove', {
                                                    field: 'id',
                                                    values: delArr
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
                        //取消
                        "click .btn_cancel": function (e, value, row, index) {

                            $tb_record.bootstrapTable("updateRow", {
                                index: index,
                                row: {}
                            });

                        },
                        //保存
                        "click .btn_save": function (e, value, row, index) {

                            // console.log(e);
                            var $e = $(e.currentTarget);
                            var $item = $e.closest(".item");

                            //备注
                            var remark = $item.find(".remark").find("input").val();

                            var obj = {
                                id: row.id,
                                remark: remark
                            };

                            loadingInit();

                            aryaPostRequest(
                                urlGroup.salary_operate_record_update,
                                obj,
                                function (data) {
                                    //console.log(data);

                                    if (data.code === RESPONSE_OK_CODE) {

                                        toastr.success("编辑成功！");

                                        $tb_record.bootstrapTable("updateRow", {
                                            index: index,
                                            row: {
                                                remark: remark
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

                        }

                    }
                }

            ]

        });

    },

    //导出
    recordExport: function () {

        operateShow(
            "确认要导出数据吗？",
            null,
            function () {

                loadingInit();

                var $search_container = $salary_operate_record_container.find(".search_container");

                var obj = {
                    condition: $.trim($search_container.find(".search_condition").val())
                };

                aryaPostRequest(
                    urlGroup.salary_operate_record_export,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {

                                var url = data.result.url ? data.result.url : "";

                                if (!url) {
                                    toastr.warning("无法下载，下载链接为空！");
                                    return;
                                }

                                var aLink = document.createElement('a');
                                aLink.download = "";
                                aLink.href = url;
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
        )
    },
    //删除
    recordDel: function () {

        var data = $tb_record.bootstrapTable("getSelections");
        if (data.length <= 0) {
            toastr.warning('请先选择记录！');
            return;
        }

        var delArr = [];

        for (var i = 0; i < data.length; i++) {

            delArr.push(data[i].id);

        }

        delWarning(
            "确认要删除选中的记录吗？",
            null,
            function () {

                var obj = {
                    salaryIds: delArr
                };

                loadingInit();

                aryaPostRequest(
                    urlGroup.salary_operate_record_del,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("删除成功！");
                            $tb_record.bootstrapTable('remove', {
                                field: 'id',
                                values: delArr
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

};

$(function () {
    salary_operate_record.init();
});

var debug = {
    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    delArr: [],//删除 数组

    init: function () {

        salary_operate_record.btnSearchClick();

    },

    //查询
    btnSearchClick: function () {
        salary_operate_record.currentPage = "1";

        salary_operate_record.recordList();//获取 记录列表
    },
    //清空 记录列表
    clearRecordList: function () {

        var $table_container = $salary_operate_record_container.find(".table_container");
        var msg = "<tr><td colspan='6'>暂无信息</td></tr>";
        $table_container.find("tbody").html(msg);

    },
    //获取 记录列表
    recordList: function () {
        salary_operate_record.clearRecordList();//清空 记录列表

        var $table_container = $salary_operate_record_container.find(".table_container");
        var $search_container = $salary_operate_record_container.find(".search_container");

        var obj = {
            condition: $.trim($search_container.find(".search_condition").val()),
            page: salary_operate_record.currentPage,
            page_size: "10"
        };
        var url = urlGroup.salary_operate_record_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    salary_operate_record.totalPage = data.result.pages ? data.result.pages : 1;//总页数
                    if (salary_operate_record.currentPage > salary_operate_record.totalPage) {
                        salary_operate_record.currentPage -= 1;
                        salary_operate_record.recordList();//获取 记录列表
                        return;
                    }

                    var list = "";
                    var arr = data.result.result;
                    if (!arr || arr.length === 0) {

                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var customerName = $item.customerName ? $item.customerName : "";//
                            var districtName = $item.districtName ? $item.districtName : "";//
                            var logInfo = $item.logInfo ? $item.logInfo : "";//
                            var createTime = $item.createTime ? $item.createTime : "";//
                            createTime = timeInit(createTime);
                            var remark = $item.remark ? $item.remark : "";//

                            list +=
                                "<tr class='item' " +
                                "data-id='" + id + "' " +
                                ">" +
                                "<td class='choose_item' onclick='salary_operate_record.chooseItem(this)'>" +
                                "<img src='img/icon_Unchecked.png'/>" +
                                "</td>" +
                                "<td>" + (i + 1) + "</td>" +
                                "<td class='corp_name'>" + customerName + "</td>" +
                                "<td class='city'>" + districtName + "</td>" +
                                "<td class='record'>" + logInfo + "</td>" +
                                "<td class='createTime'>" + createTime + "</td>" +
                                "<td class='remark'>" + remark + "</td>" +
                                "<td class='operate'>" +
                                "<div class='btn btn-sm btn-primary btn_modify'>编辑</div>" +
                                "<div class='btn btn-sm btn-danger btn_del'>删除</div>" +
                                "</td>" +
                                "</tr>"

                        }

                        $table_container.find("tbody").html(list);

                    }

                    salary_operate_record.recordListInit();	//记录列表 初始化

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
    //记录列表 初始化
    recordListInit: function () {
        var $table_container = $salary_operate_record_container.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $salary_operate_record_container.find(".pager_container");

        if ($item.length === 0) {
            $pager_container.hide();
            return
        }

        $item.each(function () {

            //删除
            $(this).find(".btn_del").unbind("click").bind("click", function () {
                salary_operate_record.recordDelOnly(this);
            });

        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: salary_operate_record.currentPage, //当前页数
            totalPages: salary_operate_record.totalPage, //总页数
            numberOfPages: 5,//每页显示的 页数
            useBootstrapTooltip: true,//是否使用 bootstrap 自带的提示框
            itemContainerClass: function (type, page, currentpage) {  //每项的类名
                //alert(type + "  " + page + "  " + currentpage)
                var classname = "p_item ";

                switch (type) {
                    case "first":
                        classname += "p_first";
                        break;
                    case "last":
                        classname += "p_last";
                        break;
                    case "prev":
                        classname += "p_prev";
                        break;
                    case "next":
                        classname += "p_next";
                        break;
                    case "page":
                        classname += "p_page";
                        break;
                }

                if (page == currentpage) {
                    classname += " active "
                }

                return classname;
            },
            itemTexts: function (type, page, current) {  //
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            },
            tooltipTitles: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "去首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "去末页";
                    case "page":
                        return page === current ? "当前页数 " + page : "前往第 " + page + " 页"
                }
            },
            onPageClicked: function (event, originalEvent, type, page) { //点击事件
                //alert(page)

                var currentTarget = $(event.currentTarget);

                salary_operate_record.currentPage = page;
                salary_operate_record.recordList();//获取 记录列表

            }

        };

        var ul = '<ul class="pagenation" style="float:right;"></ul>';
        $pager_container.show();
        $pager_container.html(ul);
        $pager_container.find(".pagenation").bootstrapPaginator(options);

    },

    //选中单行
    chooseItem: function (self) {
        var $item = $(self).closest(".item");

        if ($item.hasClass("active")) { //如果选中行
            $item.removeClass("active");
            $(self).find("img").attr("src", "img/icon_Unchecked.png");
        }
        else { //如果未选中
            $item.addClass("active");
            $(self).find("img").attr("src", "img/icon_checked.png");
        }

        salary_operate_record.isChooseAll();//是否 已经全部选择

    },
    //选择全部(查询条件下)
    chooseAll: function () {
        var $table_container = $salary_operate_record_container.find(".table_container");
        var $item = $table_container.find("tbody .item");//tbody item
        var $tbody_choose_item = $table_container.find("tbody .choose_item");//tbody choose_item
        var $foot_choose_item = $salary_operate_record_container.find(".foot .choose_item");

        if ($foot_choose_item.hasClass("active")) {   //已经选中

            $foot_choose_item.removeClass("active");
            $foot_choose_item.find("img").attr("src", "img/icon_Unchecked.png");
            $item.removeClass("active");
            $tbody_choose_item.find("img").attr("src", "img/icon_Unchecked.png");

        }
        else {

            $foot_choose_item.addClass("active");
            $foot_choose_item.find("img").attr("src", "img/icon_checked.png");
            $item.addClass("active");
            $tbody_choose_item.find("img").attr("src", "img/icon_checked.png");

        }

    },
    //是否 已经全部选择
    isChooseAll: function () {

        var $table_container = $salary_operate_record_container.find(".table_container");
        var $item = $table_container.find("tbody .item");//tbody item
        var $foot_choose_item = $salary_operate_record_container.find(".foot .choose_item");

        var chooseNo = 0;//选中的个数
        for (var i = 0; i < $item.length; i++) {
            if ($item.eq(i).hasClass("active")) { //如果 是选中的
                chooseNo += 1;
            }
        }

        //没有全部选中
        if (chooseNo === 0 || chooseNo < $item.length) {
            $foot_choose_item.removeClass("active");
            $foot_choose_item.find("img").attr("src", "img/icon_Unchecked.png");
        }
        else {
            $foot_choose_item.addClass("active");
            $foot_choose_item.find("img").attr("src", "img/icon_checked.png");
        }

    },

    //导出
    recordExport: function () {

        operateShow(
            "确认要导出数据吗？",
            null,
            function () {

                loadingInit();

                var $search_container = $salary_operate_record_container.find(".search_container");

                var obj = {
                    condition: $.trim($search_container.find(".search_condition").val())
                };

                aryaPostRequest(
                    urlGroup.salary_operate_record_export,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {

                                var url = data.result.url ? data.result.url : "";

                                if (!url) {
                                    toastr.warning("无法下载，下载链接为空！");
                                    return;
                                }

                                var aLink = document.createElement('a');
                                aLink.download = "";
                                aLink.href = url;
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
        )
    },
    //删除
    recordDelOnly: function (self) {
        salary_operate_record.delArr = [];

        var id = $(self).closest(".item").attr("data-id");
        salary_operate_record.delArr.push(id);

        delWarning(
            "确认要删除吗？",
            null,
            function () {

                var obj = {
                    salaryIds: salary_operate_record.delArr
                };

                loadingInit();

                aryaPostRequest(
                    urlGroup.salary_operate_record_del,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("删除成功！");
                            salary_operate_record.recordList();//

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
    //删除
    recordDel: function () {
        salary_operate_record.delArr = [];

        var $table_container = $salary_operate_record_container.find(".table_container");
        var $item_active = $table_container.find("tbody .item.active");//tbody item

        if ($item_active.length <= 0) {
            toastr.warning('请先选择记录！');
            return;
        }

        for (var i = 0; i < $item_active.length; i++) {

            var id = $item_active.eq(i).attr("data-id");
            salary_operate_record.delArr.push(id);

        }

        delWarning(
            "确认要删除选中的记录吗？",
            null,
            function () {

                var obj = {
                    salaryIds: salary_operate_record.delArr
                };

                loadingInit();

                aryaPostRequest(
                    urlGroup.salary_operate_record_del,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("删除成功！");
                            salary_operate_record.recordList();//

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
};
