/**
 * Created by Administrator on 2017/7/3.
 */

var $project_detail_container;//项目详情 container
var $operate_record_modal;//操作记录 modal

var project_detail = {

    corp_id: "",//立项申请id

    init: function () {

        project_detail_param.initParam();//初始化参数
        project_detail.initTime();//初始化 时间插件
        project_detail.initInvoiceType();//初始化 发票类型
        project_detail.initInvoiceProject();//初始化 发票项目

        project_detail.initBtn();//初始化 操作按钮
        project_detail.disableOperate();//禁用 操作

        project_detail.projectDetail();//获取详情
        project_detail.recordList();//获取 跟进记录列表

        //跟进记录 弹框初始化
        $operate_record_modal.on("shown.bs.modal", function () {

            $operate_record_modal.find(".record_content").val("");

        });

    },
    //初始化 时间插件
    initTime: function () {

        var id = $project_detail_container.find(".apply_date").attr("id");
        id = "#" + id;

        //入职时间 开始
        var start = {
            elem: id,//"#apply_date"
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

        laydate(start);


    },
    //初始化 发票类型
    initInvoiceType: function () {

        var $row = $project_detail_container.find(".content .row");
        var $invoice_type = $row.find(".invoice_type");
        $invoice_type.empty();

        invoice_info.initInvoiceType($invoice_type, function () {
            // setTimeout(function () {
            //     $invoice_type.val("fullFare");
            // }, 100);
        });

    },
    //初始化 发票项目
    initInvoiceProject: function () {


        var $row = $project_detail_container.find(".content .row");
        var $invoice_project_1 = $row.find(".invoice_project_1");
        $invoice_project_1.empty();
        var $invoice_project_2 = $row.find(".invoice_project_2");
        $invoice_project_2.empty();


        invoice_info.initInvoiceProject($invoice_project_1, function () {
            // setTimeout(function () {
            //     $invoice_project_1.val("salary");
            // }, 100);
        });

        invoice_info.initInvoiceProject($invoice_project_2, function () {
            // setTimeout(function () {
            //     $invoice_project_2.val("salary");
            // }, 100);
        });

    },
    //初始化 操作按钮
    initBtn: function () {
        var $btn_list = $project_detail_container.find(".content .btn_list");
        $btn_list.find(".btn_modify").show().siblings().hide();
    },

    //解禁 操作
    enableOperate: function () {
        var $row = $project_detail_container.find(".content .row");
        $row.find("input").removeAttr("disabled");
        $row.find("select").removeAttr("disabled");
        $row.find("textarea").removeAttr("disabled");
    },
    //禁用 操作
    disableOperate: function () {
        var $row = $project_detail_container.find(".content .row");
        $row.find("input").attr("disabled", "disabled");
        $row.find("select").attr("disabled", "disabled");
        $row.find("textarea").attr("disabled", "disabled");
    },

    //获取详情
    projectDetail: function () {
        var $row = $project_detail_container.find(".content .row");
        project_detail_param.clearParam(); //参数 置空

        var obj = {
            id: project_detail.corp_id
        };
        var url = urlGroup.project_apply_corp_detail + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    var id = $item.id ? $item.id : "";
                    var salesMan = $item.salesMan ? $item.salesMan : "";//销售人员
                    var salesDepartment = $item.salesDepartment ? $item.salesDepartment : "";//销售部门
                    var customerName = $item.customerName ? $item.customerName : "";//客户名称
                    var shortName = $item.shortName? $item.shortName: "";//客户简称
                    var salaryerNum = $item.salaryerNum ? $item.salaryerNum : "";//薪资人数

                    var salarySum = $item.salarySum ? $item.salarySum : "";//薪资总额
                    var billType = $item.billType ? $item.billType : "fullFare";//发票类型
                    var billProjectOne = $item.billProjectOne ? $item.billProjectOne : "isNull";//发票项目1
                    var billProjectTwo = $item.billProjectTwo ? $item.billProjectTwo : "isNull";//发票项目2
                    var profitBudget = $item.profitBudget ? $item.profitBudget : "";//利润预算
                    var eMail = $item.eMail ? $item.eMail : "";//邮箱

                    var applyDate = $item.applyDate ? $item.applyDate : "";//申请日期
                    applyDate = timeInit(applyDate);
                    var address = $item.address ? $item.address : "";//地址
                    var telphone = $item.telphone ? $item.telphone : "";//联系方式
                    var linkMan = $item.linkMan ? $item.linkMan : "";//联系人
                    var bussinessCase = $item.bussinessCase ? $item.bussinessCase : "";//运营方案


                    $row.find(".sales_man").val(salesMan);
                    $row.find(".sales_dept").val(salesDepartment);
                    $row.find(".customer_name").val(customerName);
                    $row.find(".customer_shortName").val(shortName);
                    $row.find(".salary_count").val(salaryerNum);

                    $row.find(".salary_total").val(salarySum);
                    $row.find(".invoice_type").val(billType);
                    $row.find(".invoice_project_1").val(billProjectOne);
                    $row.find(".profit_budget").val(profitBudget);
                    $row.find(".invoice_project_2").val(billProjectTwo);
                    $row.find(".email").val(eMail);

                    $row.find(".apply_date").val(applyDate);
                    $row.find(".contact_phone").val(telphone);
                    $row.find(".address").val(address);
                    $row.find(".contact_name").val(linkMan);
                    $row.find(".operation_plan").val(bussinessCase);

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
    //清空 跟进记录列表
    clearRecordList: function () {

        var $table_container = $project_detail_container.find(".table_container");
        var msg = "<tr><td colspan='3'>暂无跟进记录</td></tr>";

        $table_container.find("tbody").html(msg);

    },
    //获取 跟进记录列表
    recordList: function () {
        project_detail.clearRecordList();//清空 跟进记录列表

        var $table_container = $project_detail_container.find(".table_container");

        var obj = {
            id: project_detail.corp_id
        };
        var url = urlGroup.project_apply_operate_record_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var list = "";
                    var arr = data.result;
                    if (!arr || arr.length === 0) {
                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var followInfo = $item.followInfo ? $item.followInfo : "";//
                            var createTime = $item.createTime ? $item.createTime : "";//
                            createTime = timeInit1(createTime);

                            list +=
                                "<tr class='item'>" +
                                "<td>" + (i + 1) + "</td>" +
                                "<td class='record_content'>" + followInfo + "</td>" +
                                "<td class=''>" + createTime + "</td>" +
                                "</tr>"

                        }

                        $table_container.find("tbody").html(list);

                    }

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

    //编辑
    projectModify: function () {

        var $btn_list = $project_detail_container.find(".content .btn_list");
        $btn_list.find(".btn_modify").hide().siblings().show();

        project_detail.enableOperate();    //解禁 操作

    },
    //取消
    projectCancel: function () {

        project_detail.initBtn();//初始化 操作按钮
        project_detail.disableOperate();//禁用 操作
        project_detail.projectDetail();//获取详情

    },
    //保存
    projectSave: function () {
        // var $row = $project_detail_container.find(".content .row");

        project_detail_param.paramSet();//参数赋值

        //检查 参数
        if (!project_detail.checkParam()) {
            return;
        }

        var obj = {

            id: project_detail.corp_id,
            salesMan: project_detail_param.sales_man,
            salesDepartment: project_detail_param.sales_dept,
            customerName: project_detail_param.customer_name,
            shortName: project_detail_param.customer_shortName,
            salaryerNum: parseInt(project_detail_param.salary_count),

            salarySum: project_detail_param.salary_total,
            billType: project_detail_param.invoice_type,
            billProjectOne: project_detail_param.invoice_project_1,
            billProjectTwo: project_detail_param.invoice_project_2,

            profitBudget: project_detail_param.profit_budget,
            eMail: project_detail_param.email,
            applyDate: new Date(project_detail_param.apply_date).getTime(),
            address: project_detail_param.address,

            telphone: project_detail_param.contact_phone,
            linkMan: project_detail_param.contact_name,
            bussinessCase: project_detail_param.operation_plan

        };

        loadingInit();

        aryaPutRequest(
            urlGroup.project_apply_update,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("保存成功！");

                    project_detail.initBtn();//初始化 操作按钮
                    project_detail.disableOperate();//禁用 操作
                    project_detail.projectDetail();//获取详情

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
    //检查 参数
    checkParam: function () {
        var flag = false;
        var txt;

        if (!project_detail_param.sales_man) {
            txt = "销售人员姓名不能为空！";
        }
        else if (!project_detail_param.sales_dept) {
            txt = "销售部门不能为空！";
        }
        else if (!project_detail_param.customer_name) {
            txt = "客户名称不能为空！";
        }
        else if (!project_detail_param.customer_shortName) {
            txt = "客户简称不能为空！";
        }
        else if (!project_detail_param.salary_count) {
            txt = "薪资人数不能为空！";
        }
        else if (!project_detail_param.salary_total) {
            txt = "薪资总额不能为空！";
        }
        else if (!project_detail_param.profit_budget) {
            txt = "利润预算不能为空！";
        }
        else if (!project_detail_param.email) {
            txt = "邮箱不能为空！";
        }
        else if (!project_detail_param.apply_date) {
            txt = "申请日期不能为空！";
        }
        else if (!project_detail_param.contact_phone) {
            txt = "联系电话不能为空！";
        }
        else if (!project_detail_param.address) {
            txt = "地址不能为空！";
        }
        else if (!project_detail_param.contact_name) {
            txt = "联系人不能为空！";
        }
        else if (!project_detail_param.operation_plan) {
            txt = "运营方案不能为空！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //跟进记录 弹框显示
    recordModalShow: function () {
        $operate_record_modal.modal("show");
    },
    //跟进记录 新增
    recordAdd: function () {

        var obj = {
            projectId: project_detail.corp_id,
            followInfo: $operate_record_modal.find(".record_content").val()
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.project_apply_operate_record_add,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    $operate_record_modal.modal("hide");
                    toastr.success("添加成功！");

                    project_detail.recordList();//获取跟进记录 列表

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


};

var project_detail_param = {

    sales_man: "",  //销售人员
    sales_dept: "",  //销售部门
    customer_name: "",  //客户名称
    customer_shortName: "",  //客户简称
    salary_count: "",  //薪资人数
    salary_total: "",  //薪资总额   string
    invoice_type: "",  //发票类型
    invoice_project_1: "",  //发票项目1
    profit_budget: "",  //利润预算
    invoice_project_2: "",  //发票项目2
    email: "",  //邮箱
    apply_date: "",  //申请日期
    contact_phone: "",  //联系电话
    address: "",  //地址
    contact_name: "",  //客户联系人
    operation_plan: "",  //运营方案

    //初始化参数
    initParam: function () {

        project_detail_param.sales_man = "";
        project_detail_param.sales_dept = "";
        project_detail_param.customer_name = "";
        project_detail_param.customer_shortName = "";
        project_detail_param.salary_count = "";

        project_detail_param.salary_total = "";
        project_detail_param.invoice_type = "";
        project_detail_param.invoice_project_1 = "";
        project_detail_param.profit_budget = "";
        project_detail_param.invoice_project_2 = "";
        project_detail_param.email = "";

        project_detail_param.apply_date = "";
        project_detail_param.contact_phone = "";
        project_detail_param.address = "";
        project_detail_param.contact_name = "";
        project_detail_param.operation_plan = "";

    },

    //参数 置空
    clearParam: function () {

        var $row = $project_detail_container.find(".content .row");

        $row.find(".sales_man").val("");
        $row.find(".sales_dept").val("");
        $row.find(".customer_name").val("");
        $row.find(".customer_shortName").val("");
        $row.find(".salary_count").val("");

        $row.find(".salary_total").val("");
        $row.find(".invoice_type").val("1");
        $row.find(".invoice_project_1").val("1");
        $row.find(".profit_budget").val("");
        $row.find(".invoice_project_2").val("1");
        $row.find(".email").val("");

        $row.find(".apply_date").val("");
        $row.find(".contact_phone").val("");
        $row.find(".address").val("");
        $row.find(".contact_name").val("");
        $row.find(".operation_plan").val("");

    },

    //参数赋值
    paramSet: function () {

        var $row = $project_detail_container.find(".content .row");

        project_detail_param.sales_man = $row.find(".sales_man").val();
        project_detail_param.sales_dept = $row.find(".sales_dept").val();
        project_detail_param.customer_name = $row.find(".customer_name").val();
        project_detail_param.customer_shortName = $row.find(".customer_shortName").val();
        project_detail_param.salary_count = $row.find(".salary_count").val();

        project_detail_param.salary_total = $row.find(".salary_total").val();
        project_detail_param.invoice_type = $row.find(".invoice_type").val();
        project_detail_param.invoice_project_1 = $row.find(".invoice_project_1").val();
        project_detail_param.profit_budget = $row.find(".profit_budget").val();
        project_detail_param.invoice_project_2 = $row.find(".invoice_project_2").val();
        project_detail_param.email = $row.find(".email").val();

        project_detail_param.apply_date = $row.find(".apply_date").val();
        project_detail_param.contact_phone = $row.find(".contact_phone").val();
        project_detail_param.address = $row.find(".address").val();
        project_detail_param.contact_name = $row.find(".contact_name").val();
        project_detail_param.operation_plan = $row.find(".operation_plan").val();

    }

};

$(function () {

    project_detail.corp_id = sessionStorage.getItem("corp_id");
    sessionStorage.removeItem("corp_id");//
    $project_detail_container = $("#page_project_detail_" + project_detail.corp_id).find(".project_detail_container");

    //设置申请日期container的ID
    $project_detail_container.find(".apply_date").attr("id", "apply_date" + project_detail.corp_id);
    //操作记录 modal
    $operate_record_modal = $project_detail_container.find(".operate_record_modal");

    project_detail.init();//

});
