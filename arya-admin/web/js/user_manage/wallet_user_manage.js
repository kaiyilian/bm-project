//钱包用户管理

var $wallet_user_manage_container = $(".wallet_user_manage_container");
var $tb_wallet_user_manage = $wallet_user_manage_container.find("#tb_wallet_user_manage");//表格id

var wallet_user_manage = {

    //初始化
    init: function () {
        wallet_user_manage.btnSearchClick();//查询 按钮 click
    },

    //查询 按钮 click
    btnSearchClick: function () {
        wallet_user_manage.initTb();//初始化 表格
    },

    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        $tb_wallet_user_manage.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_wallet_user_manage.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "walletUserId",

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
            // height: 600,
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
                    field: 'no',
                    title: '序号',
                    align: "center",
                    class: "no",
                    formatter: function (value, row, index) {

                        return "<div>" + (index + 1 ) + "</div>";

                    }
                },
                {
                    field: 'walletUserId',
                    title: '钱包账户',
                    align: "center",
                    class: "walletUserId",
                    formatter: function (value, row, index) {

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
                    field: 'appPhone',
                    title: 'App手机账号',
                    align: "center",
                    class: "appPhone",
                    formatter: function (value, row, index) {

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
                    field: 'phone',
                    title: '钱包手机账号',
                    align: "center",
                    class: "phone",
                    formatter: function (value, row, index) {

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
                    field: 'validUserName',
                    title: '姓名',
                    align: "center",
                    class: "validUserName",
                    formatter: function (value, row, index) {

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
                    field: 'validCardNo',
                    title: '身份证',
                    align: "center",
                    class: "validCardNo",
                    formatter: function (value, row, index) {

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
                    field: 'bankCardNum',
                    title: '银行卡数量',
                    align: "center",
                    class: "bankCardNum",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div>0</div>";
                        }

                        return html;

                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.user_manage.wallet.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                return {

                    param: $.trim($wallet_user_manage_container.find(".searchCondition").val()),
                    page: params.pageNumber,
                    page_size: params.pageSize

                };
            },
            onLoadSuccess: function () {  //加载成功时执行

                // toastr.success("成功！");

            },
            onLoadError: function () {  //加载失败时执行
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

                        if (res.result.result) {
                            $.each(res.result.result, function (i, item) {

                                var walletUserId = item.walletUserId ? item.walletUserId : "";//
                                var appPhone = item.appPhone ? item.appPhone : "";//
                                var phone = item.phone ? item.phone : "";//
                                var validUserName = item.validUserName ? item.validUserName : "";//
                                var validCardNo = item.validCardNo ? item.validCardNo : "";//
                                var bankCardNum = item.bankCardNum ? item.bankCardNum : "";//

                                var obj = {

                                    walletUserId: walletUserId,
                                    appPhone: appPhone,
                                    phone: phone,

                                    validUserName: validUserName,
                                    validCardNo: validCardNo,
                                    bankCardNum: bankCardNum

                                };
                                tb_data.push(obj);

                            });
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

    //导出
    excelExport: function () {

        exportWarning(
            "确定要导出吗?",
            function () {

                loadingInit();

                var obj = {
                    param: $.trim($wallet_user_manage_container.find(".searchCondition").val())
                };
                var url = urlGroup.user_manage.wallet.excel_export + "?" + jsonParseParam(obj);

                aryaGetRequest(
                    url,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {

                                var url = data.result.url ? data.result.url : "";
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
                            else {
                                toastr.warning("无法下载，下载链接为空！");
                            }

                        }
                        else {
                            toastr.warning(data.msg);
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
    wallet_user_manage.init();//
});