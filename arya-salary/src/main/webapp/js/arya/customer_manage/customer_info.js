/**
 * Created by Administrator on 2017/8/29.
 */

var $customer_info_container = $(".customer_info_container");
var $tb_customer_info = $customer_info_container.find("#tb_customer_info");//table

var customer_info = {

    init: function () {

        customer_info.btnSearchClick();
        // customer_info.initTbData([]);

    },

    //查询
    btnSearchClick: function () {
        customer_info.customerList();//获取 列表
    },
    //获取 用户列表
    customerList: function () {

        // var $table_container = $customer_info_container.find(".table_container");
        var $search_container = $customer_info_container.find(".search_container");

        var obj = {
            condition: $search_container.find(".searchCondition").val(),
            page: 1,
            pageSize: "10"
        };
        var url = urlGroup.customer_info_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var tb_data = [];

                    if (data.result && data.result.length > 0) {

                        var arr = data.result;

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var sales_dept = $item.salesDepartment ? $item.salesDepartment : "";//
                            var sales_man = $item.salesMan ? $item.salesMan : "";//
                            var customer_name = $item.customerName ? $item.customerName : "";//
                            var shortName = $item.shortName ? $item.shortName : "";//
                            var contact = $item.linkMan ? $item.linkMan : "";//
                            var contact_phone = $item.telphone ? $item.telphone : "";//
                            var address = $item.address ? $item.address : "";//
                            var contract_end_date = $item.contractDateEnd ? $item.contractDateEnd : "";//
                            contract_end_date = timeInit(contract_end_date);
                            var remark = $item.remark ? $item.remark : "";//

                            var obj = {

                                id: id,
                                sales_dept: sales_dept,             //销售部门
                                sales_man: sales_man,             //销售人员
                                customer_name: customer_name,             //客户名称
                                shortName: shortName,             //客户简称
                                contact: contact,             //联系人
                                contact_phone: contact_phone,             //联系电话
                                address: address,             //地址
                                contract_end_date: contract_end_date,             //合同结束日期
                                remark: remark             //备注

                            };

                            tb_data.push(obj);

                        }

                    }

                    //dataTable 初始化
                    customer_info.initTbData(tb_data);	//列表初始化


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

        // data = [
        //     {
        //
        //         id: "111",
        //         sales_dept: "销售部门",             //销售部门
        //         sales_man: "销售人员",             //销售人员
        //         customer_name: "客户名称",             //客户名称
        //         contact: "联系人",             //联系人
        //         contact_phone: 123152632459,             //联系电话
        //         address: "地址",             //地址
        //         contract_end_date: "2018-05-25",             //合同结束日期
        //         remark: "备注"             //备注
        //
        //     }
        // ];

        $tb_customer_info.bootstrapTable("destroy");
        //表格的初始化
        $tb_customer_info.bootstrapTable({

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
                    field: 'index',
                    title: '序号',
                    align: "center",
                    class: "index",
                    width: 100,
                    formatter: function (value, row, index) {

                        var html = "<div style='width:30px;'>" + (index + 1 ) + "</div>";

                        return html;

                    }
                },
                {
                    field: 'sales_dept',
                    title: '销售部门',
                    // sortable: true,
                    align: "center",
                    class: "sales_dept",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";
                        return html;

                    }
                },
                {
                    field: 'sales_man',
                    title: '销售人员',
                    // sortable: true,
                    align: "center",
                    class: "sales_man",
                    width: 100,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";

                        return html;

                    }
                },
                {
                    field: 'customer_name',
                    title: '客户名称',
                    // sortable: true,
                    align: "center",
                    class: "customer_name",
                    width: 100,
                    formatter: function (value, row, index) {

                        var html = "<div title='" + value + "'>" + row.shortName + "</div>";

                        return html;

                    }
                },
                {
                    field: 'contact',
                    title: '联系人',
                    // sortable: true,
                    align: "center",
                    class: "contact",
                    width: 200
                },
                {
                    field: 'contact_phone',
                    title: '联系电话',
                    // sortable: true,
                    align: "center",
                    class: "contact_phone",
                    width: 200
                },
                {
                    field: 'address',
                    title: '地址',
                    // sortable: true,
                    align: "center",
                    class: "address",
                    width: 300,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";
                        return html;

                    }
                },
                {
                    field: 'contract_end_date',
                    title: '合同结束日期',
                    sortable: true,
                    align: "center",
                    class: "contract_end_date",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div style='width:80px;'>" + value + "</div>";

                        return html;

                    }
                },
                {
                    field: 'remark',
                    title: '备注',
                    // sortable: true,
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
                    sortable: true,
                    align: "center",
                    class: "operate",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        //编辑
                        html += "<div class='btn btn-primary btn-sm btn_modify'>编辑</div>";
                        //保存
                        html += "<div class='btn btn-primary btn-sm hide btn_save'>保存</div>";
                        //取消
                        html += "<div class='btn btn-primary btn-sm hide btn_cancel'>取消</div>";

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
                            $operate.find(".btn_modify").addClass("hide")
                                .siblings().removeClass("hide");

                        },
                        //取消
                        "click .btn_cancel": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");

                            $tb_customer_info.bootstrapTable("updateRow", {
                                index: index,
                                row: {
                                    // remark: row.remark
                                }
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
                                urlGroup.customer_info_update,
                                obj,
                                function (data) {
                                    //console.log(data);

                                    if (data.code === RESPONSE_OK_CODE) {

                                        toastr.success("编辑成功！");

                                        $tb_customer_info.bootstrapTable("updateRow", {
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

    //下载
    customerInfoDown: function () {

        operateShow(
            "确认要导出吗？",
            null,
            function () {
                loadingInit();

                aryaPostRequest(
                    urlGroup.customer_info_down,
                    {},
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
        );

    }

};

$(function () {
    customer_info.init();
});


