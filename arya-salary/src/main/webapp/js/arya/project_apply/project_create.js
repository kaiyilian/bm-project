/**
 * Created by Administrator on 2017/7/3.
 */

var $project_create_container = $(".project_create_container");//项目新建 container

var project_create = {

    init: function () {
        project_create_param.initParam();//初始化参数
        project_create_param.clearParam(); //参数 置空

        project_create.initTime();//初始化 时间插件
        project_create.initInvoiceType();//初始化 发票类型
        project_create.initInvoiceProject();//初始化 发票项目

    },
    //初始化 时间插件
    initTime: function () {

        //入职时间 开始
        var start = {
            elem: "#apply_date",
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

        var $row = $project_create_container.find(".content .row");
        var $invoice_type = $row.find(".invoice_type");
        $invoice_type.empty();

        invoice_info.initInvoiceType($invoice_type, function () {
            setTimeout(function () {
                $invoice_type.val("fullFare");
            }, 100);
        });

    },
    //初始化 发票项目
    initInvoiceProject: function () {

        var $row = $project_create_container.find(".content .row");
        var $invoice_project_1 = $row.find(".invoice_project_1");
        $invoice_project_1.empty();
        var $invoice_project_2 = $row.find(".invoice_project_2");
        $invoice_project_2.empty();

        invoice_info.initInvoiceProject($invoice_project_1, function () {
            setTimeout(function () {
                $invoice_project_1.val("salary");
            }, 100);
        });

        invoice_info.initInvoiceProject($invoice_project_2, function () {
            setTimeout(function () {
                $invoice_project_2.val("salary");
            }, 100);
        });

    },

    //保存
    projectSave: function () {

        project_create_param.paramSet();//参数赋值

        //检查 参数
        if (!project_create.checkParam()) {
            return;
        }

        var obj = {

            salesMan: project_create_param.sales_man,
            salesDepartment: project_create_param.sales_dept,
            customerName: project_create_param.customer_name,
            shortName: project_create_param.customer_shortName,
            salaryerNum: parseInt(project_create_param.salary_count),

            salarySum: project_create_param.salary_total,
            billType: project_create_param.invoice_type,
            billProjectOne: project_create_param.invoice_project_1,
            billProjectTwo: project_create_param.invoice_project_2,

            profitBudget: project_create_param.profit_budget,
            eMail: project_create_param.email,
            applyDate: new Date(project_create_param.apply_date).getTime(),
            address: project_create_param.address,

            telphone: project_create_param.contact_phone,
            linkMan: project_create_param.contact_name,
            bussinessCase: project_create_param.operation_plan,
            followInfo: project_create_param.operate_record

        };

        loadingInit();

        aryaPostRequest(
            urlGroup.project_apply_add,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("添加成功！");

                    //删除 项目新建 tab
                    var pageTabId = "project_create";
                    var tabIndex = $.inArray(pageTabId, existedTabIdArrya);
                    existedTabIdArrya.splice(tabIndex, 1);//先从数组中删除
                    $('#' + pageTabId).remove();//删除tab
                    var tabDivId = "page_" + pageTabId;
                    $('#' + tabDivId).remove();//删除 页面div

                    getInsidePageDiv(urlGroup.project_apply_page, 'project_apply', '立项申请');
                    project_apply_manage.btnSearchClick();//

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

        if (!project_create_param.sales_man) {
            txt = "销售人员姓名不能为空！";
        }
        else if (!project_create_param.sales_dept) {
            txt = "销售部门不能为空！";
        }
        else if (!project_create_param.customer_name) {
            txt = "客户名称不能为空！";
        }
        else if (!project_create_param.customer_shortName) {
            txt = "客户简称不能为空！";
        }
        else if (!project_create_param.salary_count) {
            txt = "薪资人数不能为空！";
        }
        else if (!project_create_param.salary_total) {
            txt = "薪资总额不能为空！";
        }
        else if (!project_create_param.profit_budget) {
            txt = "利润预算不能为空！";
        }
        else if (!project_create_param.email) {
            txt = "邮箱不能为空！";
        }
        else if (!project_create_param.apply_date) {
            txt = "申请日期不能为空！";
        }
        else if (!project_create_param.contact_phone) {
            txt = "联系电话不能为空！";
        }
        else if (!project_create_param.address) {
            txt = "地址不能为空！";
        }
        else if (!project_create_param.contact_name) {
            txt = "联系人不能为空！";
        }
        else if (!project_create_param.operation_plan) {
            txt = "运营方案不能为空！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    }

};

var project_create_param = {

    sales_man: "",  //销售人员
    sales_dept: "",  //销售部门
    customer_name: "",  //客户名称
    customer_shortName: "",  //客户简称

    salary_count: "",  //薪资人数
    salary_total: "",  //薪资总额 string
    invoice_type: "",  //发票类型
    invoice_project_1: "",  //发票项目1
    profit_budget: "",  //利润预算  string
    invoice_project_2: "",  //发票项目2
    email: "",  //邮箱
    apply_date: "",  //申请日期
    contact_phone: "",  //联系电话
    address: "",  //地址
    contact_name: "",  //客户联系人
    operation_plan: "",  //运营方案
    operate_record: "",  //操作记录


    //初始化参数
    initParam: function () {

        project_create_param.sales_man = "";
        project_create_param.sales_dept = "";
        project_create_param.customer_name = "";
        project_create_param.customer_shortName = "";
        project_create_param.salary_count = "";

        project_create_param.salary_total = "";
        project_create_param.invoice_type = "";
        project_create_param.invoice_project_1 = "";
        project_create_param.profit_budget = "";
        project_create_param.invoice_project_2 = "";
        project_create_param.email = "";

        project_create_param.apply_date = "";
        project_create_param.contact_phone = "";
        project_create_param.address = "";
        project_create_param.contact_name = "";
        project_create_param.operation_plan = "";
        project_create_param.operate_record = "";

    },

    //参数 置空
    clearParam: function () {

        var $row = $project_create_container.find(".content .row");

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
        $row.find(".operate_record").val("");

    },

    //参数赋值
    paramSet: function () {

        var $row = $project_create_container.find(".content .row");

        project_create_param.sales_man = $row.find(".sales_man").val();
        project_create_param.sales_dept = $row.find(".sales_dept").val();
        project_create_param.customer_name = $row.find(".customer_name").val();
        project_create_param.customer_shortName = $row.find(".customer_shortName").val();
        project_create_param.salary_count = $row.find(".salary_count").val();

        project_create_param.salary_total = $row.find(".salary_total").val();
        project_create_param.invoice_type = $row.find(".invoice_type").val();
        project_create_param.invoice_project_1 = $row.find(".invoice_project_1").val();
        project_create_param.profit_budget = $row.find(".profit_budget").val();
        project_create_param.invoice_project_2 = $row.find(".invoice_project_2").val();
        project_create_param.email = $row.find(".email").val();

        project_create_param.apply_date = $row.find(".apply_date").val();
        project_create_param.contact_phone = $row.find(".contact_phone").val();
        project_create_param.address = $row.find(".address").val();
        project_create_param.contact_name = $row.find(".contact_name").val();
        project_create_param.operation_plan = $row.find(".operation_plan").val();
        project_create_param.operate_record = $row.find(".operate_record").val();

    }

};

$(function () {
    project_create.init();
});
