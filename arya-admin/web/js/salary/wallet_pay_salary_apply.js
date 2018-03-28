
var $wallet_pay_salary_apply_container = $(".wallet_pay_salary_apply_container");
var $tb_wallet_pay_salary_apply = $wallet_pay_salary_apply_container.find("#tb_wallet_pay_salary_apply");//表格id

var wallet_pay_salary_apply = {

    //初始化
    init: function () {
        wallet_pay_salary_apply.btnSearchClick();//

    },

    btnSearchClick: function () {
        wallet_pay_salary_apply.initTb();//列表
    },

    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (wallet_pay_salary_apply_param.begin_time && wallet_pay_salary_apply_param.end_time &&
            wallet_pay_salary_apply_param.begin_time > wallet_pay_salary_apply_param.end_time) {
            txt = "开始时间不能大于结束时间！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },
    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        wallet_pay_salary_apply_param.paramSet();//赋值查询参数

        //检查查询参数是否正确
        if (!wallet_pay_salary_apply.checkParam()) {
            return;
        }

        $tb_wallet_pay_salary_apply.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_wallet_pay_salary_apply.bootstrapTable({
            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

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
                    field: 'index',
                    title: 'S/NO.',
                    align: "center",
                    class: "index",
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
                    field: 'batchNo',
                    title: '批次号',
                    align: "center",
                    class: "batchNo",
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
                    field: 'applyTime',
                    title: '操作时间',
                    align: "center",
                    class: "applyTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            value = timeInit1(value);
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'projectName',
                    title: '所属项目',
                    // sortable: true,
                    align: "center",
                    class: "projectName",
                    // width: 200,
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
                    field: 'payTime',
                    title: '发薪年月',
                    // sortable: true,
                    align: "center",
                    class: "payTime",
                    // width: 200,
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
                    field: 'totalAmount',
                    title: '金额总计',
                    // sortable: true,
                    align: "center",
                    class: "totalAmount",
                    // width: 200,
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
                    field: 'statusDesc',
                    title: '状态',
                    // sortable: true,
                    align: "center",
                    class: "statusDesc",
                    // width: 200,
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
                    field: 'totalPerson',
                    title: '发薪人数',
                    // sortable: true,
                    align: "center",
                    class: "totalPerson",
                    // width: 200,
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
                    field: "statusAndId",
                    title: "操作",
                    align: "center",
                    class: "statusAndId",
                    formatter: function (value, row, index) {
                        var html = "";
                        html += "<div class='operate'>";

                        var txt;
                        var s=value.split(",");
                        if(s[0]==1){
                            txt='发薪审核'
                        }else{
                            txt='查看明细'
                        }
                        //编辑
                        html += "<div class='btn btn-success btn-sm btn_modify' onclick='wallet_pay_salary_apply.showDetail("+s[1]+")')>"+txt+"</div>";

                        html += "</div>";

                        return html;

                    }
                }
            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.wallet_pay_salary_apply_query,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                return {

                    beginTime: wallet_pay_salary_apply_param.begin_time,
                    endTime: wallet_pay_salary_apply_param.end_time,
                    projectId: wallet_pay_salary_apply_param.project_id,
                    batchNo: wallet_pay_salary_apply_param.batch_no,
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
                                var batchNo = item.batchNo ? item.batchNo : "";//
                                var applyTime = item.applyTime ? item.applyTime : "";//
                                var projectName = item.projectName ? item.projectName : "";//
                                var payTime = item.payTime ? item.payTime : "";//
                                var totalAmount = item.totalAmount ? item.totalAmount : "";//
                                var statusDesc = item.statusDesc ? item.statusDesc : "";
                                var totalPerson = item.totalPerson ? item.totalPerson : "";
                                var statusAndId=item.status+","+item.id;
                                var obj = {
                                    index: (res.result.pages-1)*res.result.pageSize+i+1,
                                    batchNo: batchNo,
                                    applyTime: applyTime,
                                    projectName: projectName,
                                    payTime: payTime,
                                    totalAmount: totalAmount,
                                    statusDesc: statusDesc,
                                    totalPerson: totalPerson,
                                    statusAndId:statusAndId
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
    reset: function () {
        var $search_container = $wallet_pay_salary_apply_container.find(".search_container");
        $search_container.find(".beginTime").val('')
        $search_container.find(".endTime").val('')
        $search_container.find(".projectId").val('')
        $search_container.find(".batchNo").val('')

    },

    showDetail:function(applyId){
        getInsidePageDiv(urlGroup.wallet_pay_salary_detail_page+"?applyId="+applyId, 'wallet_pay_salary_detail', '发放明细记录')
    }

};
//保存时 参数
var wallet_pay_salary_apply_param = {

    begin_time: null,                    //开始时间
    end_time: null,                      //结束时间
    project_id: null,                    //手机号
    batch_no: null,                  //项目名称

    //参数赋值
    paramSet: function () {

        var $search_container = $wallet_pay_salary_apply_container.find(".search_container");

        wallet_pay_salary_apply_param.begin_time = $.trim($search_container.find(".beginTime").val());
        wallet_pay_salary_apply_param.begin_time = wallet_pay_salary_apply_param.begin_time ?
            timeInit3(wallet_pay_salary_apply_param.begin_time) : "";

        wallet_pay_salary_apply_param.end_time = $.trim($search_container.find(".endTime").val());
        wallet_pay_salary_apply_param.end_time = wallet_pay_salary_apply_param.end_time ?
            timeInit4(wallet_pay_salary_apply_param.end_time) : "";

        wallet_pay_salary_apply_param.project_id = $.trim($search_container.find(".projectId").val());

        wallet_pay_salary_apply_param.batch_no = $.trim($search_container.find(".batchNo").val());

    }

};

$(function () {
    wallet_pay_salary_apply.init();
});
