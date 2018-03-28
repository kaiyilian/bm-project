/**
 * Created by Administrator on 2017/8/30.
 */

var $bill_record_container = $(".bill_record_container");
var $tb_bill_record = $bill_record_container.find("#tb_bill_record");//table
var $bill_info_modal = $(".bill_info_modal");//开票信息 弹框

var bill_record = {

    row: null,//当前操作的 行

    init: function () {

        bill_record.btnSearchClick();//查询
        bill_record.initTime();//初始化 弹框中的时间


        //开票信息 弹框 初始化
        $bill_info_modal.on("shown.bs.modal", function () {

            var $row = $bill_info_modal.find(".modal-body .row");
            $row.find("input").val("");

            //初始化 弹框时，如果有内容，则赋值
            if (bill_record.row) {

                //开票日期
                var bill_date = bill_record.row.bill_date;
                if (bill_date) {
                    bill_date = timeInit(bill_date);
                }

                //邮寄日期
                var mail_date = bill_record.row.mail_date;
                if (mail_date) {
                    mail_date = timeInit(mail_date);
                }

                //签收日期
                var receipt_date = bill_record.row.receipt_date;
                if (receipt_date) {
                    receipt_date = timeInit(receipt_date);
                }

                $row.find(".bill_date").val(bill_date);
                $row.find(".mail_date").val(mail_date);
                $row.find(".addressee").val(bill_record.row.addressee);
                $row.find(".receipt_date").val(receipt_date);
                $row.find(".receipt_info").val(bill_record.row.receipt_info);
                $row.find(".payment_info").val(bill_record.row.payment_info);
                $row.find(".remark").val(bill_record.row.remark);

            }

        });

    },
    //初始化 时间（弹框）
    initTime: function () {

        //开票日期
        var bill_date = {
            elem: "#bill_date",
            event: 'focus', //触发事件
            format: 'YYYY-MM-DD',
            min: "", //设定最小日期为当前日期
            max: '', //最大日期
            istime: false,//是否开启时间选择
            istoday: false, //是否显示今天
            choose: function (datas) {
                //end.min = datas; //开始日选好后，重置结束日的最小日期
                //end.start = datas;//将结束日的初始值设定为开始日
            }
        };
        laydate(bill_date);

        //邮寄日期
        var mail_date = {
            elem: "#mail_date",
            event: 'focus', //触发事件
            format: 'YYYY-MM-DD',
            min: "", //设定最小日期为当前日期
            max: '', //最大日期
            istime: false,//是否开启时间选择
            istoday: false, //是否显示今天
            choose: function (datas) {
                //end.min = datas; //开始日选好后，重置结束日的最小日期
                //end.start = datas;//将结束日的初始值设定为开始日
            }
        };
        laydate(mail_date);

        //签收日期
        var receipt_date = {
            elem: "#receipt_date",
            event: 'focus', //触发事件
            format: 'YYYY-MM-DD',
            min: "", //设定最小日期为当前日期
            max: '', //最大日期
            istime: false,//是否开启时间选择
            istoday: false, //是否显示今天
            choose: function (datas) {
                //end.min = datas; //开始日选好后，重置结束日的最小日期
                //end.start = datas;//将结束日的初始值设定为开始日
            }
        };
        laydate(receipt_date);

    },

    //查询
    btnSearchClick: function () {
        bill_record.billRecordList();//获取 列表
    },
    //获取 用户列表
    billRecordList: function () {

        var $search_container = $bill_record_container.find(".search_container");

        var obj = {
            condition: $search_container.find(".search_condition").val(),
            page: 1,
            pageSize: "10"
        };
        var url = urlGroup.bill_record_list + "?" + jsonParseParam(obj);

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
                            var customerName = $item.customerName ? $item.customerName : "";//客户名称
                            var corpName = $item.corpName ? $item.corpName : "";//开票公司
                            var totalMoney = $item.totalMoney ? $item.totalMoney : "";//开票金额
                            var managerFee = $item.managerFee ? $item.managerFee : "";//管理费

                            var netSalary = $item.netSalary ? $item.netSalary : "";//税前工资
                            var personalTax = $item.personalTax ? $item.personalTax : "";//个税
                            var billApplyDate = $item.billApplyDate ? $item.billApplyDate : "";//开票申请日期
                            var billDate = $item.billDate ? $item.billDate : "";//开票日期

                            var mailDate = $item.mailDate ? $item.mailDate : "";//邮寄日期
                            var receiver = $item.receiver ? $item.receiver : "";//接收人
                            var receiveDate = $item.receiveDate ? $item.receiveDate : "";//接受日期
                            var receiveInfo = $item.receiveInfo ? $item.receiveInfo : "";//签收信息

                            var backInfo = $item.backInfo ? $item.backInfo : "";//回款情况
                            var remark = $item.remark ? $item.remark : "";//

                            var obj = {

                                id: id,
                                index: i,//

                                bill_company: corpName,//开票公司
                                customer_header: customerName,//客户抬头
                                salary: netSalary,//税前工资
                                personal_tax: personalTax,//个税

                                manage_fee: managerFee,//管理费
                                bill_total_money: totalMoney,//开票金额
                                bill_apply_date: billApplyDate,//开票申请日期
                                bill_date: billDate,//开票日期

                                mail_date: mailDate,//邮寄日期
                                addressee: receiver,//接收人
                                receipt_date: receiveDate,//接受日期
                                receipt_info: receiveInfo,//签收信息

                                payment_info: backInfo,//回款情况
                                remark: remark      //备注

                            };

                            tb_data.push(obj);

                        }

                    }

                    //dataTable 初始化
                    bill_record.initTbData(tb_data);	//列表初始化


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

        $tb_bill_record.bootstrapTable("destroy");
        //表格的初始化
        $tb_bill_record.bootstrapTable({

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
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div style='width:30px;'>" + (index + 1 ) + "</div>";

                        return html;

                    }
                },
                {
                    field: 'bill_company',
                    title: '开票公司',
                    sortable: true,
                    align: "center",
                    class: "bill_company",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";
                        return html;

                    }
                },
                {
                    field: 'customer_header',
                    title: '客户抬头',
                    sortable: true,
                    align: "center",
                    class: "customer_header",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div>" + value + "</div>";
                        return html;

                    }
                },
                {
                    field: 'salary',
                    title: '工资',
                    sortable: true,
                    align: "center",
                    class: "salary",
                    width: 200
                },
                {
                    field: 'personal_tax',
                    title: '个税',
                    sortable: true,
                    align: "center",
                    class: "personal_tax",
                    width: 200
                },
                {
                    field: 'manage_fee',
                    title: '管理费',
                    sortable: true,
                    align: "center",
                    class: "manage_fee",
                    width: 200
                },
                {
                    field: 'bill_total_money',
                    title: '开票总金额',
                    sortable: true,
                    align: "center",
                    class: "bill_total_money",
                    width: 200
                },
                {
                    field: 'bill_apply_date',
                    title: '申请开票日期',
                    sortable: true,
                    align: "center",
                    class: "bill_apply_date",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = timeInit(value);
                        }

                        return html;

                    }
                },
                {
                    field: 'bill_date',
                    title: '开票日期',
                    sortable: true,
                    align: "center",
                    class: "bill_date",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = timeInit(value);
                        }

                        return html;

                    }
                },
                {
                    field: 'mail_date',
                    title: '邮寄日期',
                    sortable: true,
                    align: "center",
                    class: "mail_date",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = timeInit(value);
                        }

                        return html;

                    }
                },
                {
                    field: 'addressee',
                    title: '收件人',
                    sortable: true,
                    align: "center",
                    class: "addressee",
                    width: 200
                },
                {
                    field: 'receipt_date',
                    title: '签收日期',
                    sortable: true,
                    align: "center",
                    class: "receipt_date",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = timeInit(value);
                        }

                        return html;

                    }
                },
                {
                    field: 'receipt_info',
                    title: '签收情况',
                    sortable: true,
                    align: "center",
                    class: "receipt_info",
                    width: 200
                },
                {
                    field: 'payment_info',
                    title: '回款情况',
                    sortable: true,
                    align: "center",
                    class: "payment_info",
                    width: 200
                },
                {
                    field: 'remark',
                    title: '备注',
                    sortable: true,
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
                        html += "<button class='btn btn-primary btn-sm btn_modify'>编辑</button>";
                        //删除
                        html += "<button class='btn btn-primary btn-sm btn_del'>删除</button>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //编辑
                        "click .btn_modify": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");

                            bill_record.row = row;
                            bill_record.row.index = index;
                            $bill_info_modal.modal("show");

                            // $tb_bill_record.bootstrapTable("updateRow", {
                            //     index: bill_record.row.index,
                            //     row: {
                            //         bill_date: 1504636000000,
                            //         mail_date: 0,
                            //         addressee: "",
                            //         receipt_date: 0,
                            //         receipt_info: "",
                            //         payment_info: "rewr",
                            //         remark: "rewlrjewljr"
                            //     }
                            // });


                        },
                        //删除
                        "click .btn_del": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");

                            var delArr = [row.id];

                            delWarning(
                                "确定要删除吗？",
                                null,
                                function () {
                                    loadingInit();

                                    var obj = {
                                        ids: delArr
                                    };

                                    aryaPostRequest(
                                        urlGroup.bill_record_del,
                                        obj,
                                        function (data) {
                                            //console.log(data);

                                            if (data.code === RESPONSE_OK_CODE) {

                                                toastr.success("删除成功！");
                                                $tb_bill_record.bootstrapTable('remove', {field: 'id', values: delArr});

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

            ]

        });

    },

    //保存编辑信息
    billRecordUpdate: function () {

        var $row = $bill_info_modal.find(".modal-body .row");

        var bill_date = $row.find(".bill_date").val();
        bill_date = bill_date ? new Date(bill_date).getTime() : "";
        var mail_date = $row.find(".mail_date").val();
        mail_date = mail_date ? new Date(mail_date).getTime() : "";
        var addressee = $row.find(".addressee").val();
        var receipt_date = $row.find(".receipt_date").val();
        receipt_date = receipt_date ? new Date(receipt_date).getTime() : "";
        var receipt_info = $row.find(".receipt_info").val();
        var payment_info = $row.find(".payment_info").val();
        var remark = $row.find(".remark").val();

        var obj = {
            id: bill_record.row.id,
            billDate: bill_date,
            mailDate: mail_date,
            receiver: addressee,
            receiveDate: receipt_date,
            receiveInfo: receipt_info,
            backInfo: payment_info,
            remark: remark
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.bill_record_save,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("编辑成功！");
                    $bill_info_modal.modal("hide");

                    $tb_bill_record.bootstrapTable("updateRow", {
                        index: bill_record.row.index,
                        row: {
                            bill_date: bill_date,
                            mail_date: mail_date,
                            addressee: addressee,
                            receipt_date: receipt_date,
                            receipt_info: receipt_info,
                            payment_info: payment_info,
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


    },


    //多项删除
    billRecordDel: function () {

        var data = $tb_bill_record.bootstrapTable('getSelections');//获取选中的数据

        //如果没有选中数据
        if (data.length <= 0) {
            toastr.warning("请先选择数据！");
            return;
        }

        var delArr = [];
        //赋值 要删除
        for (var i = 0; i < data.length; i++) {

            delArr.push(data[i].id);

        }

        delWarning(
            "确定要删除吗？",
            null,
            function () {
                loadingInit();

                var obj = {
                    ids: delArr
                };

                aryaPostRequest(
                    urlGroup.bill_record_del,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("删除成功！");
                            $tb_bill_record.bootstrapTable('remove', {field: 'id', values: delArr});

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

    //导出
    billRecordExport: function () {

        operateShow(
            "确认要导出吗？",
            null,
            function () {

                loadingInit();

                aryaPostRequest(
                    urlGroup.bill_record_export,
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
    bill_record.init();
});
