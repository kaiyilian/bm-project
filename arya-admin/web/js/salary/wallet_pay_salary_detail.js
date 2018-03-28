
var $wallet_pay_salary_detail_container = $(".wallet_pay_salary_detail_container");
var $tb_wallet_pay_salary_detail = $wallet_pay_salary_detail_container.find("#tb_wallet_pay_salary_detail");//表格id

var wallet_pay_salary_detail = {

    //初始化
    init: function () {
        wallet_pay_salary_detail.btnSearchClick();//

    },

    btnSearchClick: function () {
        wallet_pay_salary_detail.initTb();//列表
    },

    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (wallet_pay_salary_detail_param.begin_time && wallet_pay_salary_detail_param.end_time &&
            wallet_pay_salary_detail_param.begin_time > wallet_pay_salary_detail_param.end_time) {
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

        wallet_pay_salary_detail_param.paramSet();//赋值查询参数

        //检查查询参数是否正确
        if (!wallet_pay_salary_detail.checkParam()) {
            return;
        }
        $tb_wallet_pay_salary_detail.bootstrapTable("destroy");//表格摧毁
        //表格的初始化
        $tb_wallet_pay_salary_detail.bootstrapTable({

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
                    field: 'id',
                    title: '<input  id="selectAll" type="checkbox" onclick="wallet_pay_salary_detail.selectAllCheckBox()">',
                    align: "center",
                    class: "id",
                    formatter: function (value, row, index) {
                        var html = "";
                        if (value!='') {
                            html = "<input name='ids' type='checkbox' value='"+value+"' onclick='wallet_pay_salary_detail.selectCheckBox(this)'>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
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
                    field: 'userName',
                    title: '姓名',
                    align: "center",
                    class: "userName",
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
                    field: 'cardNo',
                    title: '身份证号码',
                    align: "center",
                    class: "cardNo",
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
                    field: 'walletUserId',
                    title: '钱包账号',
                    // sortable: true,
                    align: "center",
                    class: "walletUserId",
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
                    field: 'amount',
                    title: '转账金额',
                    // sortable: true,
                    align: "center",
                    class: "amount",
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
                    field: 'remark',
                    title: '备注',
                    // sortable: true,
                    align: "center",
                    class: "remark",
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
                    field: 'tradeStatusDesc',
                    title: '交易状态',
                    // sortable: true,
                    align: "center",
                    class: "tradeStatusDesc",
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
                    field: 'tradeMsg',
                    title: '问题',
                    // sortable: true,
                    align: "center",
                    class: "tradeMsg",
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
                    field: "id",
                    title: "操作",
                    align: "center",
                    class: "id",
                    formatter: function (value, row, index) {

                        var html = "";
                        if(value!=''){
                            html += "<div class='operate'>";

                            //编辑

                            html += "<div class='btn btn-success btn-sm btn_modify' onclick='wallet_pay_salary_detail.approve("+value+")'>立即发薪</div>&nbsp;&nbsp;";

                            html += "<div class='btn btn-success btn-sm btn_modify' onclick='wallet_pay_salary_detail.reject("+value+")'>拒绝</div>";

                            html += "</div>";
                        }else{
                            html = "<div>-</div>";
                        }

                        return html;
                    }
                }
            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.wallet_pay_salary_detail_query,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                return {

                    beginTime: wallet_pay_salary_detail_param.begin_time,
                    endTime: wallet_pay_salary_detail_param.end_time,
                    tradeStatus: wallet_pay_salary_detail_param.trade_status,
                    nameOrWalletId: wallet_pay_salary_detail_param.nameOrWalletId,
                    applyId: wallet_pay_salary_detail_param.applyId,
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
                                var id = '';
                                if(item.tradeStatus=='0'){
                                    id=item.id
                                }
                                var userName = item.userName ? item.userName : "";//
                                var cardNo = item.cardNo ? item.cardNo : "";//
                                var walletUserId = item.walletUserId ? item.walletUserId : "";//
                                var amount = item.amount ? item.amount : "";//
                                var remark =  "转账";//
                                var tradeStatus = item.tradeStatus ? item.tradeStatus : "";
                                var tradeStatusDesc = item.tradeStatusDesc ? item.tradeStatusDesc : "";
                                var tradeMsg = item.tradeMsg ? item.tradeMsg : "";
                                var obj = {
                                    index: (res.result.pages-1)*res.result.pageSize+i+1,
                                    userName: userName,
                                    cardNo: cardNo,
                                    walletUserId: walletUserId,
                                    amount: amount,
                                    remark: remark,
                                    tradeStatusDesc: tradeStatusDesc,
                                    id: id,
                                    tradeMsg: tradeMsg
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
    export: function () {

        exportWarning(
            "确定要导出该记录吗?",
            function () {

                loadingInit();

                var obj = {
                    beginTime: wallet_pay_salary_detail_param.begin_time,
                    endTime: wallet_pay_salary_detail_param.end_time,
                    tradeStatus: wallet_pay_salary_detail_param.trade_status,
                    nameOrWalletId: wallet_pay_salary_detail_param.nameOrWalletId,
                    applyId: wallet_pay_salary_detail_param.applyId
                };
                var url = urlGroup.wallet_pay_salary_detail_export + "?" + jsonParseParam(obj);

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

    },
    //导出
    reset: function () {
        var $search_container = $wallet_pay_salary_detail_container.find(".search_container");
        $search_container.find(".beginTime").val('')
        $search_container.find(".endTime").val('')
        $search_container.find(".tradeStatus").val('')
        $search_container.find(".nameOrWalletId").val('')

    },


    //选中所有或取消选中所有
    selectAllCheckBox: function(){
        var allcheck=document.getElementById("selectAll");
        var ids=document.getElementsByName("ids");
        if(allcheck.checked){
            for(var i=0;i<ids.length;i++){
                ids[i].checked=true;
            }
        }else{
            for(var i=0;i<ids.length;i++){
                ids[i].checked=false;
            }
        }
    },

    //选中单个或取消选中单个
    selectCheckBox:function(object){
        var allcheck=document.getElementById("selectAll");

        if(!object.checked){
            allcheck.checked=false;
            return;
        }

        var ids=document.getElementsByName("ids");
        for(var i=0;i<ids.length;i++){
            if(!ids[i].checked){
                return;
            }
        }

        allcheck.checked=true;
    },

    //发薪
    approve:function(id){
        operateShow(
            "确定要发薪吗?",
            function () {

                loadingInit();

                var obj = {
                    id: id,
                };
                var url = urlGroup.wallet_pay_salary_detail_approve + "?" + jsonParseParam(obj);

                aryaGetRequest(
                    url,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {
                                toastr.success("操作成功！");
                                wallet_pay_salary_detail.btnSearchClick();
                            }else {
                                toastr.warning("操作失败！");
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
    },
    //拒绝
    reject:function(id){
        operateShow(
            "确定要拒绝吗?",
            function () {

                loadingInit();

                var obj = {
                    id: id,
                };
                var url = urlGroup.wallet_pay_salary_detail_reject+ "?" + jsonParseParam(obj);

                aryaGetRequest(
                    url,
                    function (data) {
                        //console.log("获取日志：");
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            if (data.result) {
                                toastr.success("操作成功！");
                                wallet_pay_salary_detail.btnSearchClick();

                            }else {
                                toastr.warning("操作失败！");
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

    },
    //批量发薪
    batchApprove:function(){
        var ids=document.getElementsByName("ids");
        var selectIds='';
        for(var i=0;i<ids.length;i++){
            if(ids[i].checked){
                selectIds=','+selectIds+ids[i].val;
            }
        }

        if(selectIds==''){
            messageCue('请选中需要发薪的记录');
            return;
        }

        selectIds=selectIds.substring(1,selectIds.length)
        wallet_pay_salary_detail.approve(selectIds)
    },

    //批量拒绝
    batchReject:function(){
        var ids=document.getElementsByName("ids");
        var selectIds='';
        for(var i=0;i<ids.length;i++){
            if(ids[i].checked){
                selectIds=','+selectIds+ids[i].val;
            }
        }

        if(selectIds==''){
            messageCue('请选中需要拒绝的记录');
            return;
        }

        selectIds=selectIds.substring(1,selectIds.length)
        wallet_pay_salary_detail.reject(selectIds)
    }
};
//保存时 参数
var wallet_pay_salary_detail_param = {

    begin_time: null,                    //开始时间
    end_time: null,                      //结束时间
    trade_status: null,                  //交易状态
    nameOrWalletId:null,                //用户
    applyId:null,
    //参数赋值
    paramSet: function () {

        var $search_container = $wallet_pay_salary_detail_container.find(".search_container");

        wallet_pay_salary_detail_param.begin_time = $.trim($search_container.find(".beginTime").val());
        wallet_pay_salary_detail_param.begin_time = wallet_pay_salary_detail_param.begin_time ?
            timeInit3(wallet_pay_salary_detail_param.begin_time) : "";

        wallet_pay_salary_detail_param.end_time = $.trim($search_container.find(".endTime").val());
        wallet_pay_salary_detail_param.end_time = wallet_pay_salary_detail_param.end_time ?
            timeInit4(wallet_pay_salary_detail_param.end_time) : "";

        wallet_pay_salary_detail_param.trade_status = $.trim($search_container.find(".tradeStatus").val());
        wallet_pay_salary_detail_param.nameOrWalletId = $.trim($search_container.find(".nameOrWalletId").val());

        wallet_pay_salary_detail_param.applyId = $search_container.find(".applyId").val();

    }

};

$(function () {
    wallet_pay_salary_detail.init();
});
