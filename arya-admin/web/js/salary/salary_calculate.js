/**
 * Created by CuiMengxin on 2016/3/24.
 * 薪资计算导入
 */

var $operate_status_modal = $(".operate_status_modal");//操作记录 弹框
var $user_info_modal = $(".user_info_modal");//用户信息 弹框
var $custom_calc_rule_container = $(".custom_calc_rule_container");//自定义 计算规则
var $general_calc_rule_container = $(".general_calc_rule_container");//标准 计算规则
var $salary_calc_container = $(".salary_calc_container");//薪资计算结果 container
var $salary_statistic_container = $(".salary_statistic_container");//薪资统计结果 container

var salaryCalculate = {
    CORP_LIST_URL: "admin/corporation/group/list",

    //UPLOAD_FILE_AND_CALCULATE_URL: "admin/salary/calculate/upload2Calculate",
    //IMPORT_URL: "admin/salary/calculate/import",
    //QUERY_URL: "admin/salary/calculate/query",
    //EXPORT_URL: "admin/salary/calculate/export",
    //EXPORT_STATISTICS_URL: "admin/salary/calculate/statistics/export",

    //GROUP_TREE_URL: "admin/salary/calculate/organization/tree",
    //SALARY_CALCULATE_RULE_URL: "admin/salary/calculate/rule",
    //UPDATE_SALARY_CALCULATE_RULE_URL: "admin/salary/calculate/rule/update",
    //ADD_SALARY_CALCULATE_RULE_URL: "admin/salary/calculate/rule/add",
    //DELETE_SALARY_CALCULATE_RULE_URL: "admin/salary/calculate/rule/delete",
    //DELETE_SALARY_DETAIL_LIST_URL: "admin/salary/calculate/delete",
    //UPDATE_USER_INFO_URL: "admin/salary/calculate/user/info/update",
    //QUERY_SALARY_BATCH_LIST_URL: "admin/salary/calculate/batch/list",
    //getRuleType_url: "admin/salary/calculate/getRuleType",

    //salaryDay: 0,//发薪日，暂定为星期一
    //numberCHN: ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
    //calculateResultTable: null,
    //calculateStatisticsResultTable: null,
    //calculateResultTableDataSource: [],
    //calculateStatisticsResultTableDataSource: [],

    uploadedFileName: "",//上传文件名称
    //salaryImportStatusWidth: "20px",
    salaryCalculateRule: null,//薪资计算规则 array
    salaryCalculateRuleChanged: false,//薪资计算结果 是否改变
    SETTLEMENT_INTERVAL_WEEK: 1,
    SETTLEMENT_INTERVAL_MONTH: 2,
    tempCount: 0,//计税档 count
    rule_type: "",//规则类型， 1 自定义计算规则   2 国家个税计算标准规则
    currentPage: "1",//当前页数 （薪资计算结果）
    totalPage: "1",//总页数 （薪资计算结果）
    search_or_calc: "",//当前操作是 查询还是 计算
    is_salary_calc_first: true,//是否是 第一次 计算薪资

    //初始化
    init: function () {

        //显示 提示框
        $("[data-toggle='tooltip']").tooltip();

        salaryCalculate.initMonthPicker();//初始化 月份选择器
        salaryCalculate.initSettlementInterval();//初始化 结算周期
        salaryCalculate.initPrettyFile();//初始化文件选择组件

        salaryCalculate.hideOperateBtn();//隐藏 操作按钮

    },
    //下载模板
    templateDown: function () {
        //var url = urlGroup.salary_template_down

        aryaGetRequest(
            urlGroup.salary_template_down,
            function (data) {
                console.log("获取日志：");
                console.log(data);

                if (data.code == RESPONSE_OK_CODE) {

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

                }
                else {
                    //console.log("获取日志-----error：");
                    //console.log(data.msg);

                    toastr.warning(data.msg);
                }
            },
            function (error) {
                toastr.error(error);
            }
        );

    },
    //初始化月份选择器
    initMonthPicker: function () {
        $("#date_picker #calculate_month")
            .datepicker({
                minViewMode: 1,
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayHighlight: true,
                format: 'yyyy-mm'
            })
            .on("changeMonth", function (e) {
                //更换月份事件
                var choseDate = e.date;
                var last = new Date(choseDate.getFullYear(), choseDate.getMonth() + 1, 0);//获取目标月最后一天时间
                var y = last.getYear();
                var m = last.getMonth() + 1;
                var d = last.getDate();

                salaryCalculate.getImportBatches(choseDate.getFullYear(), m, false);

            });

    },
    //初始化 结算周期
    initSettlementInterval: function () {

        //批次 列表
        var $batch_list_container = $(".search_or_import_container")
            .find(".batch_list_container");

        $(".settlement_interval").change(function () {
            //结算周期为 批次 的话显示周的选择
            if ($(this).val() == 1) {
                $batch_list_container.show();
            }
            else {
                $batch_list_container.hide();
            }
        });

    },
    //初始化文件选择组件
    initPrettyFile: function () {
        $("#import_btn").attr("disabled", "disabled");
        $('#pretty_file').prettyFile();
        $('#pretty_file').change(function () {
            $("#import_btn").removeAttr("disabled")
        });
    },
    //根据年月 获取批次列表
    getImportBatches: function (year, month, selectLastOne, successFunc) {

        if (!salary_calc_param.currentTreeNode) {
            toastr.warning("请先选择集团或部门！");
            return
        }

        salary_calc_param.currentYear = year;
        salary_calc_param.currentMonth = month;

        var obj = {};
        obj.year = salary_calc_param.currentYear;
        obj.month = salary_calc_param.currentMonth;
        obj.group_department_id = salary_calc_param.currentTreeNode.id;

        var url = urlGroup.salary_calc_batch_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log(data);

                if (data.code == ERR_CODE_OK) {
                    var $batch_list = $(".search_or_import_container .batch_list");

                    //批次列表 清空
                    $batch_list.empty();

                    if (data.result) {

                        for (var i = 0; i < data.result.length; i++) {
                            var option = $("<option></option>");
                            option.appendTo($batch_list);
                            option.val(data.result[i]);
                            var batchNo = data.result[i] + "";
                            if (batchNo.length == 7) {
                                batchNo = "0" + batchNo;
                            }
                            if (batchNo.length > 2) {
                                batchNo = batchNo.substring(0, 6);
                            }
                            option.text(batchNo);

                            //最后一个 是否 默认选中
                            if (i == data.result.length - 1 && selectLastOne) {
                                option.attr("selected", true);
                            }

                        }
                    }

                    var option = $("<option></option>");
                    option.appendTo($batch_list);
                    option.text("新增");
                    option.val(0);

                    if (successFunc) {
                        successFunc();
                    }

                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (data) {

            }
        );

    },

    //显示 操作按钮
    showOperateBtn: function () {

        var $calc_foot = $salary_calc_container.find(".foot");
        var $statistic_foot = $salary_statistic_container.find(".foot");

        $calc_foot.show();
        $statistic_foot.show();

        //var $btn_export = $(".btn_export");
        //
        //$btn_export.removeAttr("disabled");
    },
    //隐藏 操作按钮
    hideOperateBtn: function () {

        var $calc_foot = $salary_calc_container.find(".foot");
        var $statistic_foot = $salary_statistic_container.find(".foot");

        $calc_foot.hide();
        $statistic_foot.hide();

        //var $btn_export = $(".btn_export");
        //
        //$btn_export.attr("disabled", "disabled");

    },
    //清空所有
    clearAll: function () {
        //清空 薪资计算结果
        $salary_calc_container.find(".content .table_container").find("table tbody").empty();
        $salary_calc_container.find(".pager_container").hide();

        //清空 薪资统计结果
        $salary_statistic_container.find(".content .table_container")
            .find("table tbody").empty();

        //禁止导入
        $(".btn_import").attr("disabled", "disabled");
        salaryCalculate.hideOperateBtn();//隐藏 操作按钮
    },
    //清空并隐藏 计算规则
    clearCalculateRuleFully: function () {

        salaryCalculate.hideCustomCalcRule();//隐藏 自定义计算规则
        salaryCalculate.hideGeneralCalcRule();//隐藏 通用计算规则

        salaryCalculate.salaryCalculateRuleChanged = false;//
    },
    //验证用户的选择
    checkUserSelect: function () {

        var flag = false;

        if (!salary_calc_param.currentTreeNode) {
            toastr.warning("请选择集团或部门！");
        }
        else if (!salary_calc_param.yearMonth) {
            toastr.warning("请选择月份！");
        }
        else {
            flag = true;
        }

        return flag;

    },
    //信息提示
    salary_calc_prompt: function () {
        var msg =
            "<div style='color:green;text-align: left;'>" +
            "<span>绿色</span>" +
            " - " +
            "<span>代表 新增</span>" +
            "</div>" +
            "<div style='color:blue;text-align: left;'>" +
            "<span>蓝色</span>" +
            " - " +
            "<span>代表 系统生成</span>" +
            "</div>" +
            "<div style='color:#F00078;text-align: left;'>" +
            "<span>粉色</span>" +
            " - " +
            "<span>代表 与他人冲突</span>" +
            "</div>" +
            "<div style='color:red;text-align: left;'>" +
            "<span>红色</span>" +
            " - " +
            "<span>代表 错误</span>" +
            "</div>" +
            "<div style='background-color:#d0d0d0;text-align: left;'>" +
            "<span>灰色背景</span>" +
            " - " +
            "<span>代表 该条数据重复</span>" +
            "</div>";

        swal({
            title: "",
            text: msg,
            //type: "warning",
            html: true,
            showCancelButton: false,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            closeOnConfirm: true
        }, function () {

            //loadingInit();//加载框 出现


        });
    },
    //初始化 操作提示
    initOperateMsg: function (title, content) {
        //清空内容
        $operate_status_modal.find("#operate_status").html("");

        if (title) {
            $operate_status_modal.find("#feed_back_title").text(title);
        }

        if (content) {
            $operate_status_modal.find("#operate_status").html(content);
        }

    },

    //计算按钮 click
    salaryCalc: function () {

        var $row = $(".search_or_import_container").find(".row");

        if (!salary_calc_param.currentTreeNode) {
            toastr.warning("请选择集团或部门！");
            return;
        }

        if ($row.find(".year_month").val() == "") {
            toastr.warning("请选择月份");
            return;
        }

        if ($row.find("#pretty_file").val() == "") {
            toastr.warning("请选择文件");
            return;
        }

        if ($row.find("#pretty_file").val().indexOf(".xls") < 0) {
            toastr.warning("请选择正确格式的文件");
            return;
        }

        //初始化 操作提示
        salaryCalculate.initOperateMsg("计算");

        salaryCalculate.is_salary_calc_first = true;//第一次 计算 薪资
        salaryCalculate.currentPage = 1;//
        salaryCalculate.clearAll();	//清空所有

        salaryCalculate.uploadFile();//计算 上传文件，获取计算结果

    },
    //计算 上传文件，获取计算结果
    uploadFile: function () {
        var $row = $(".search_or_import_container").find(".row");
        var $upload_form = $("#upload_form");

        $upload_form.find(".page").val(salaryCalculate.currentPage);

        loadingInit();

        $upload_form.ajaxSubmit({
            url: urlGroup.salary_result_get_by_upload,
            type: 'post',
            dataType: 'json',
            data: $upload_form.fieldSerialize(),
            beforeSend: function () {
                //记录用户所选的选项
                salary_calc_param.settlementInterval = $row.find(".settlement_interval").val();
                salary_calc_param.yearMonth = $row.find(".year_month").val();
                salary_calc_param.batch_no = $row.find(".batch_list").val();
            },
            uploadProgress: function (event, position, total, percentComplete) {
                var percentVal = percentComplete + '%';
            },
            success: function (data) {
                loadingRemove();

                if (data.code == RESPONSE_OK_CODE) {

                    //如果 可以导入
                    if (data.result["can_import"] == true) {
                        $row.find(".btn_import").removeAttr("disabled");
                    }

                    salaryCalculate.uploadedFileName = data.result["file_name"];
                    //显示计算结果
                    //salaryCalculate.salaryImportStatusWidth = "20px";
                    //当前操作 为 计算
                    salaryCalculate.search_or_calc = "calc";

                    salaryCalculate.totalPage = data.result.pages ? data.result.pages : 1;
                    salaryCalculate.showCalculateResult(data.result["calculate_result"]);
                    salaryCalculate.showStatisticsResult(data.result["statistics"]);

                    //是否是 第一次 计算薪资
                    if (salaryCalculate.is_salary_calc_first) {
                        salaryCalculate.is_salary_calc_first = false;//已经计算过薪资
                        $operate_status_modal.modal("show");//操作反馈 弹框显示
                        salaryCalculate.initOperateMsg("", data.result["log"]);//初始化 操作提示
                    }

                }
                else {
                    toastr.error(data.msg);
                }
            },
            complete: function (xhr) {
            }
        });
    },
    //导入 弹框显示
    showConfirmImportSalary: function () {
        var title = "确定导入" + salary_calc_param.yearMonth;
        if (salary_calc_param.settlementInterval ==
            salaryCalculate.SETTLEMENT_INTERVAL_WEEK) {
            if (salary_calc_param.batch_no != 0) {
                title += "第" + salary_calc_param.batch_no + "批次";
            }
            else {
                title += "新增";
            }

        }
        title += "薪资吗？";
        var warningText;
        swal({
            title: title,
            text: warningText,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "导入",
            closeOnConfirm: true
        }, function () {
            salaryCalculate.importSalary();
        });
    },
    //导入薪资
    importSalary: function () {
        loadingInit();

        //初始化 操作提示
        salaryCalculate.initOperateMsg("导入", "");

        var params;
        if (salary_calc_param.currentTreeNode.type == 1) {
            params = {
                "group_id": salary_calc_param.currentTreeNode.id,
                "settlement_interval": salary_calc_param.settlementInterval,
                "year_month": salary_calc_param.yearMonth,
                "week": salary_calc_param.batch_no,
                "file_name": salaryCalculate.uploadedFileName
            };
        }
        else {
            params = {
                "department_id": salary_calc_param.currentTreeNode.id,
                "settlement_interval": salary_calc_param.settlementInterval,
                "year_month": salary_calc_param.yearMonth,
                "week": salary_calc_param.batch_no,
                "file_name": salaryCalculate.uploadedFileName
            };
        }

        aryaPostRequest(
            urlGroup.salary_import,
            params,
            function (data) {

                if (data.code == RESPONSE_OK_CODE) {
                    $operate_status_modal.modal("show");
                    //初始化 操作提示
                    salaryCalculate.initOperateMsg("", data.result["log"]);

                    //禁止 导入
                    $(".btn_import").attr("disabled", "disabled");

                    salaryCalculate.getImportBatches(
                        salary_calc_param.currentYear,
                        salary_calc_param.currentMonth,
                        true,
                        function () {
                            salaryCalculate.btnSearchClick();//查询
                        }
                    );

                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (data) {
                loadingRemove();
            }
        );

    },

    //查询 按钮 click
    btnSearchClick: function () {
        salaryCalculate.currentPage = 1;
        salaryCalculate.salaryQuery();//薪资 查询
    },
    //薪资 查询
    salaryQuery: function () {

        //清空所有
        salaryCalculate.clearAll();
        //赋值 查询字段
        salaryCalculate.setSalaryCalcParam();
        //验证用户的选择
        if (!salaryCalculate.checkUserSelect()) {
            return
        }

        loadingInit();

        var obj = {};
        obj.settlement_interval = salary_calc_param.settlementInterval;
        obj.year_month = salary_calc_param.yearMonth;
        obj.week = salary_calc_param.batch_no;
        obj.key_word = salary_calc_param.key_word;
        obj.page = salaryCalculate.currentPage;
        obj.page_size = "10";

        //如果是 集团
        if (salary_calc_param.currentTreeNode.type == 1) {
            obj.group_id = salary_calc_param.currentTreeNode.id;
        }
        else {
            obj.department_id = salary_calc_param.currentTreeNode.id;
        }

        var url = urlGroup.salary_query + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {
                    //当前操作 为 计算
                    salaryCalculate.search_or_calc = "search";

                    salaryCalculate.showOperateBtn();//显示 操作按钮

                    salaryCalculate.totalPage = data.result.pages ? data.result.pages : 1;

                    //显示结果
                    salaryCalculate.showCalculateResult(data.result["calculate_result"]);
                    salaryCalculate.showStatisticsResult(data.result["statistics"]);

                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (data) {
                loadingRemove();
            }
        );

    },
    //赋值 查询字段
    setSalaryCalcParam: function () {

        var $container = $(".search_or_import_container");

        salary_calc_param.settlementInterval =
            $container.find(".settlement_interval").val();//结算周期
        salary_calc_param.yearMonth = $container.find(".year_month").val();//选择的 年月
        salary_calc_param.batch_no = $container.find(".batch_list").val() ?
            $container.find(".batch_list").val() : "";//批次
        salary_calc_param.key_word = $container.find(".key_word").val();//关键字
    },

    //显示 薪资计算结果
    showCalculateResult: function (data) {
        //console.log(data);
        var $table = $(".salary_calc_container .content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var list = "";
        if (data && data.length > 0) {

            //初始化 薪资计算结果 操作按钮
            salaryCalculate.initSalaryCalcResultBtn();

            for (var i = 0; i < data.length; i++) {
                var item = data[i];

                var id = item.id;//
                var city = item.city ? item.city : "";//城市
                if (city && city.city)
                    city = city.city;
                var name = item.name ? item.name : "";//姓名
                if (name && name.name)
                    name = name.name;
                var idcard_no = item.idcard_no ? item.idcard_no : "";//身份证
                if (idcard_no && idcard_no.idcard_no)
                    idcard_no = idcard_no.idcard_no;
                //1.新身份证号码 2.身份证号码错误 3.身份证号与他人冲突
                var idcard_status = "0";//
                if (idcard_no && idcard_no.idcard_status)
                    idcard_status = idcard_no.idcard_status;
                var phone = item.phone ? item.phone : "";//手机号
                if (phone && phone.phone_no)
                    phone = phone.phone_no;
                // 1.新手机号 2.手机号格式错误 3.手机号与他人冲突 4.系统生成手机号
                var phone_status = "0";
                if (phone && phone.phone_status)
                    phone_status = phone.phone_status;
                var bank_account = item.bank_account ? item.bank_account : "";//账号
                if (bank_account && bank_account.bank_account_id)
                    bank_account = bank_account.bank_account_id;
                var taxable_salary = item.taxable_salary ? item.taxable_salary : "";//税前薪资
                if (taxable_salary && taxable_salary.taxable_salary)
                    taxable_salary = taxable_salary.taxable_salary;
                var personal_tax = item.personal_tax ? item.personal_tax : "";//个税处理费
                if (personal_tax && personal_tax.personal_tax)
                    personal_tax = personal_tax.personal_tax;
                var service_charge = item.service_charge ? item.service_charge : "";//个税服务费
                if (service_charge && service_charge.service_charge)
                    service_charge = service_charge.service_charge;
                var net_salary = item.net_salary ? item.net_salary : "";//税后
                if (net_salary && net_salary.net_salary)
                    net_salary = net_salary.net_salary;
                var brokerage = item.brokerage ? item.brokerage : "";//薪资服务费
                if (brokerage && brokerage.brokerage)
                    brokerage = brokerage.brokerage;

                list +=
                    "<tr class='item' data-id='" + id + "'>" +
                    "<td class='choose_item' onclick='salaryCalculate.chooseItem(this)'>" +
                    "<img src='img/icon_Unchecked.png'>" +
                    "</td>" +
                    "<td><div style='width:50px;'>" + city + "</div></td>" +
                    "<td><div class='user_name' style='width:50px;'>" + name + "</div></td>" +
                    "<td><div class='user_idcard_no' data-status='" + idcard_status + "'>" +
                    idcard_no +
                    "</div></td>" +
                    "<td><div class='user_phone_no' data-status='" + phone_status + "'>" +
                    phone +
                    "</div></td>" +
                    "<td><div class='user_bank_account'>" + bank_account + "</div></td>" +
                    "<td><div>" + taxable_salary + "</div></td>" +
                    "<td><div>" + personal_tax + "</div></td>" +
                    "<td><div>" + service_charge + "</div></td>" +
                    "<td><div>" + net_salary + "</div></td>" +
                    "<td><div>" + brokerage + "</div></td>" +
                    "<td class='operate'>" +
                    "<div class='btn btn-sm btn-primary' " +
                    "onclick = 'salaryCalculate.userInfoModifyModalShow(this)'>编辑</div>" +
                    "</td>" +
                    "</tr>";

            }

        }
        else {
            list = "<tr><td colspan='12'>暂无计算结果</td></tr>";
        }

        $tbody.html(list);
        salaryCalculate.salaryCalcResultInit();//薪资计算结果 初始化

    },
    //薪资计算结果 初始化
    salaryCalcResultInit: function () {
        salaryCalculate.clearChooseItem();//清除选中 状态

        var $table = $salary_calc_container.find(".table_container table");
        var $item = $table.find("tbody .item");
        var $pager_container = $salary_calc_container.find('.pager_container');

        if ($item.length == 0) {
            $pager_container.hide();
        }
        else {
            $item.each(function () {
                var className = "";

                // 1.新手机号 2.手机号格式错误 3.手机号与他人冲突 4.系统生成手机号
                var phone_status = $(this).find(".user_phone_no").attr("data-status");
                if (phone_status == 1) {
                    className = "new_obj"
                }
                else if (phone_status == 2) {
                    className = "wrong_obj"
                }
                else if (phone_status == 3) {
                    className = "conflict_obj"
                }
                else if (phone_status == 4) {
                    className = "sys_generate_obj"
                }
                else {
                    className = "right_obj";
                }

                $(this).find(".user_phone_no").addClass(className);

                //1.新身份证号码 2.身份证号码错误 3.身份证号与他人冲突
                var idcard_status = $(this).find(".user_idcard_no").attr("data-status");
                if (idcard_status == 1) {
                    className = "new_obj"
                }
                else if (idcard_status == 2) {
                    className = "wrong_obj"
                }
                else if (idcard_status == 3) {
                    className = "conflict_obj"
                }
                else {
                    className = "right_obj";
                }

                $(this).find(".user_idcard_no").addClass(className);

            });

            var options = {
                bootstrapMajorVersion: 3, //版本  3是ul  2 是div
                //containerClass:"sdfsaf",
                //size: "small",//大小
                alignment: "left",//对齐方式
                currentPage: salaryCalculate.currentPage, //当前页数
                totalPages: salaryCalculate.totalPage, //总页数
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
                onPageClicked: function (event, originalEvent, type, page) {
                    //点击事件

                    //var currentTarget = $(event.currentTarget);
                    salaryCalculate.currentPage = page;

                    //当前是 查询
                    if (salaryCalculate.search_or_calc == "search") {
                        salaryCalculate.salaryQuery();
                    }
                    //当前是 计算
                    if (salaryCalculate.search_or_calc == "calc") {
                        salaryCalculate.uploadFile();
                    }

                }

            };

            var ul = "<ul class='pagenation' style='float:right;'></ul>";
            $pager_container.show();
            $pager_container.html(ul);
            $pager_container.find('ul').bootstrapPaginator(options);
        }

    },

    //薪资计算结果 - 单选
    chooseItem: function (self) {

        var $item = $(self).closest(".item");

        if ($item.hasClass("active")) { //如果选中行
            $item.removeClass("active");
            $(self).find("img").attr("src", "img/icon_Unchecked.png")
        }
        else { //如果未选中
            $item.addClass("active");
            $(self).find("img").attr("src", "img/icon_checked.png")
        }

        salaryCalculate.initSalaryCalcResultBtn();//检查是否有选中的
    },
    //薪资计算结果 - 当前页 多选
    chooseCurAll: function () {
        var $table_container = $salary_calc_container.find(".table_container");
        var $cur = $table_container.find("thead .choose_item");//thead choose_item
        var $item = $table_container.find("tbody .item");//tbody item
        var $choose_item = $table_container.find(".choose_item");//table choose_item

        if ($cur.hasClass("active")) { //如果选中
            $cur.removeClass("active");//
            $item.removeClass("active");//tbody item移除active
            $choose_item.find("img").attr("src", "img/icon_Unchecked.png");
        }
        else { //如果未选中
            $cur.addClass("active");
            $item.addClass("active");//tbody item加上active
            $choose_item.find("img").attr("src", "img/icon_checked.png");
        }

        salaryCalculate.initSalaryCalcResultBtn();//检查是否有选中的

        //fk_coupon_manage.is_Choose_all_page = "0";
        //移除 选择全部的选中状态
        //$(fk_coupon_manage.containerName).find(".foot .choose_item").removeClass("active")
        //	.find("img").attr("src", "img/icon_Unchecked.png");
    },
    //初始化 薪资计算结果 操作按钮
    initSalaryCalcResultBtn: function () {
        var $table = $salary_calc_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");
        var $item_active = $tbody.find(".item.active");
        var $foot = $salary_calc_container.find(".foot");
        var $btn_del = $foot.find(".btn_del");

        if ($item_active.length > 0) {
            $btn_del.addClass("btn-danger").removeClass("btn-default");
        }
        else {
            $btn_del.addClass("btn-default").removeClass("btn-danger");
        }
    },
    //清除 选中 状态
    clearChooseItem: function () {
        var $table_container = $salary_calc_container.find(".table_container");
        var $cur = $table_container.find("thead .choose_item");//thead choose_item
        var $item = $table_container.find("tbody .item");//tbody item
        var $choose_item = $table_container.find(".choose_item");//table choose_item

        $cur.removeClass("active");//
        $item.removeClass("active");//tbody item移除active
        $choose_item.find("img").attr("src", "img/icon_Unchecked.png");
    },

    //编辑事件
    userInfoModifyModalShow: function (self) {
        $user_info_modal.find("input").val("");
        var $row = $user_info_modal.find(".modal-body .row");

        var $item = $(self).closest(".item");
        salary_calc_param.user_id = $item.attr("data-id");
        salary_calc_param.user_name = $item.find(".user_name").text();
        salary_calc_param.user_phone_no = $item.find(".user_phone_no").text();
        salary_calc_param.user_idcard_no = $item.find(".user_idcard_no").text();
        salary_calc_param.user_bank_account = $item.find(".user_bank_account").text();

        $user_info_modal.modal("show");
        $row.find('.user_name').val(salary_calc_param.user_name);
        $row.find('.user_phone_no').val(salary_calc_param.user_phone_no);
        $row.find('.user_idcard_no').val(salary_calc_param.user_idcard_no);
        $row.find('.user_bank_account').val(salary_calc_param.user_bank_account);
    },
    //更新用户信息
    userInfoModify: function () {
        loadingInit();

        var $row = $user_info_modal.find(".modal-body .row");
        var user_id = salary_calc_param.user_id;
        var user_name = $row.find('.user_name').val();
        var user_phone_no = $row.find('.user_phone_no').val();
        var user_idcard_no = $row.find('.user_idcard_no').val();
        var user_bank_account = $row.find('.user_bank_account').val();

        var pa = {
            "salary_id": user_id,
            "name": user_name,
            "phone_no": user_phone_no,
            "idcard_no": user_idcard_no,
            "bank_account": user_bank_account
        };

        aryaPostRequest(
            urlGroup.salary_user_info_modify,
            pa,
            function (data) {
                if (data.code == ERR_CODE_OK) {
                    toastr.success("修改" + salary_calc_param.user_name + "的信息成功!");
                    $user_info_modal.modal("hide");

                    $salary_calc_container.find("tbody tr").each(function () {

                        var id = $(this).attr("data-id");
                        if (id == user_id) {
                            $(this).find(".user_name").text(user_name);
                            $(this).find(".user_phone_no").text(user_phone_no);
                            $(this).find(".user_idcard_no").text(user_idcard_no);
                            $(this).find(".user_bank_account").text(user_bank_account);
                        }

                    });

                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (data) {

            }
        );

    },

    //薪资计算结果 删除
    SalaryDetailListDel: function () {

        var $table = $(".salary_calc_container .content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find("tr.active").length;
        if (length <= 0) {
            toastr.warning("您没有选择数据！");
            return
        }

        delWarning("确定要删除薪资计算结果吗？", function () {

            loadingInit();

            var ids = "";
            var $item = $tbody.find("tr.active");
            for (var i = 0; i < $item.length; i++) {
                var id = $item.eq(i).attr("data-id");
                ids += ids == "" ? ("'" + id + "'") : (",'" + id + "'")
            }

            ids = "[" + ids + "]";
            ids = eval("(" + ids + ")");

            var obj = {};
            obj.delete_ids = ids;

            aryaPostRequest(
                urlGroup.salary_calc_result_del,
                obj,
                function (data) {
                    //alert(JSON.stringify(data))

                    if (data.code == RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");

                        salaryCalculate.clearChooseItem();//清除 选择状态
                        salaryCalculate.salaryQuery();//薪资 查询
                    }
                    else {
                        toastr.info(data.msg);
                    }

                },
                function () {
                    toastr.error("系统错误，请联系管理员！");
                }
            );

        });

    },
    //薪资计算结果 导出
    salaryCalcResultExport: function () {
        salaryCalculate.checkUserSelect();//验证用户的选择

        var $table = $salary_calc_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find(".item").length;
        if (length <= 0) {
            toastr.warning("暂无可导出的数据！");
            return
        }

        operateShow("确认导出薪资计算结果？", function () {

            var form = $("<form>");
            form.appendTo($("body"));//定义一个form表单
            form.attr("style", "display:none");
            form.attr("method", "get");
            form.attr("action", urlGroup.salary_calc_result_export);

            //id
            var organizationId = $("<input>");
            organizationId.appendTo(form);
            if (salary_calc_param.currentTreeNode.type == 1) {
                organizationId.attr("name", "group_id");
            }
            else {
                organizationId.attr("name", "department_id");
            }
            organizationId.attr("type", "hidden");
            organizationId.attr("value", salary_calc_param.currentTreeNode.id);

            //计算周期
            var settlementInterval = $("<input>");
            settlementInterval.appendTo(form);
            settlementInterval.attr("type", "hidden");
            settlementInterval.attr("name", "settlement_interval");
            settlementInterval.attr("value", salary_calc_param.settlementInterval);

            //年月
            var yearMonth = $("<input>");
            yearMonth.appendTo(form);
            yearMonth.attr("type", "hidden");
            yearMonth.attr("name", "year_month");
            yearMonth.attr("value", salary_calc_param.yearMonth);

            //批次号
            var batch_no = $("<input>");
            batch_no.appendTo(form);
            batch_no.attr("type", "hidden");
            batch_no.attr("name", "week");
            batch_no.attr("value", salary_calc_param.batch_no);

            form.submit();//表单提交

        });

    },

    //显示 薪资统计结果
    showStatisticsResult: function (data) {

        if (data) {

            var $table_container = $salary_statistic_container.find(".content .table_container");
            var $tbody = $table_container.find("table tbody");

            var list = "";
            if (data && data.length > 0) {

                for (var i = 0; i < data.length; i++) {
                    var item = data[i];

                    var city = item.district ? item.district : "";//城市
                    var corp = item.corp ? item.corp : "";//公司
                    var department_name = item.department_name ? item.department_name : "";//部门
                    var staff_count = item.staff_count ? item.staff_count : "";//人数
                    var taxable_salary_total = item.taxable_salary_total ? item.taxable_salary_total : "";//税前薪资总额
                    var personal_tax_total = item.personal_tax_total ? item.personal_tax_total : "";//个税处理费总额
                    var service_charge_total = item.service_charge_total ? item.service_charge_total : "";//个税服务费总额
                    var net_salary_total = item.net_salary_total ? item.net_salary_total : "";//税后薪资总额
                    var brokerage_total = item.brokerage_total ? item.brokerage_total : "";//薪资服务费

                    list +=
                        "<tr class='item'>" +
                        "<td><div>" + city + "</div></td>" +
                        "<td><div>" + corp + "</div></td>" +
                        "<td><div>" + department_name + "</div></td>" +
                        "<td><div>" + staff_count + "</div></td>" +
                        "<td><div>" + taxable_salary_total + "</div></td>" +
                        "<td><div>" + personal_tax_total + "</div></td>" +
                        "<td><div>" + service_charge_total + "</div></td>" +
                        "<td><div>" + net_salary_total + "</div></td>" +
                        "<td><div>" + brokerage_total + "</div></td>" +
                        "</tr>";

                }

            }
            else {
                list = "<tr><td colspan='9'>暂无统计结果</td></tr>";
            }

            $tbody.html(list);

        }

    },
    //薪资统计结果 导出
    salaryStatisticsResultExport: function () {
        salaryCalculate.checkUserSelect();//验证用户的选择

        salaryCalculate.checkUserSelect();//验证用户的选择

        var $table = $salary_statistic_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find(".item").length;
        if (length <= 0) {
            toastr.warning("暂无可导出的数据！");
            return
        }

        operateShow("确认导出薪资统计结果？", function () {

            var form = $("<form>");
            form.appendTo($("body"));//定义一个form表单
            form.attr("style", "display:none");
            form.attr("method", "get");
            form.attr("action", urlGroup.salary_statistics_result_export);

            //id
            var organizationId = $("<input>");
            organizationId.appendTo(form);
            if (salary_calc_param.currentTreeNode.type == 1) {
                organizationId.attr("name", "group_id");
            }
            else {
                organizationId.attr("name", "department_id");
            }
            organizationId.attr("type", "hidden");
            organizationId.attr("value", salary_calc_param.currentTreeNode.id);

            //计算周期
            var settlementInterval = $("<input>");
            settlementInterval.appendTo(form);
            settlementInterval.attr("type", "hidden");
            settlementInterval.attr("name", "settlement_interval");
            settlementInterval.attr("value", salary_calc_param.settlementInterval);

            //年月
            var yearMonth = $("<input>");
            yearMonth.appendTo(form);
            yearMonth.attr("type", "hidden");
            yearMonth.attr("name", "year_month");
            yearMonth.attr("value", salary_calc_param.yearMonth);

            //批次号
            var batch_no = $("<input>");
            batch_no.appendTo(form);
            batch_no.attr("type", "hidden");
            batch_no.attr("name", "week");
            batch_no.attr("value", salary_calc_param.batch_no);

            form.submit();//表单提交

        });

    },

    //组织树的点击事件
    groupTreeOnclick: function (node) {
        //console.log(node);
        //salaryCalculate.currentTreeNode = node;
        salary_calc_param.currentTreeNode = node;//
        //salaryCalculate.organizationId = salaryCalculate.currentTreeNode["id"];
        //salaryCalculate.organizationType = salaryCalculate.currentTreeNode["type"];

        //初始化 薪资计算结果 操作按钮
        salaryCalculate.initSalaryCalcResultBtn();
        //清空所有
        salaryCalculate.clearAll();
        //初始化 计算规则
        salaryCalculate.initCalcRule();


        if (node["type"] == 1) {
            $(".organization_type").attr("name", "group_id");
        }
        else {
            $(".organization_type").attr("name", "department_id");
        }
        $(".organization_type").val(salary_calc_param.currentTreeNode["id"]);
        $("#organization_name").text(salary_calc_param.currentTreeNode["name"]);//

        if (salary_calc_param.currentYear && salary_calc_param.currentMonth &&
            salary_calc_param.currentTreeNode.id) {

            //根据年月 获取批次列表
            salaryCalculate.getImportBatches(
                salary_calc_param.currentYear,
                salary_calc_param.currentMonth,
                false
            );
        }

    },


    /**
     * 薪资 计算规则
     */

    //初始化 计算规则（根据集团id获取规则）
    initCalcRule: function () {
        var id = salary_calc_param.currentTreeNode.id;//集团、部门 id

        var obj = {};
        //集团
        if (salary_calc_param.currentTreeNode.type == 1) {
            obj.group_id = id;
            obj.department_id = "";
        }
        else {
            obj.group_id = "";
            obj.department_id = id;
        }
        var url = urlGroup.salary_rule_type + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {
                    //清空并隐藏 计算规则
                    salaryCalculate.clearCalculateRuleFully();

                    var rule_type = data.result.rule_type;//规则类型

                    //未设置 规则
                    if (rule_type == "") {
                        $(".btn_custom_calc_rule").removeClass("hide");//显示 自定义计算规则 按钮
                        $(".btn_general_calc_rule").removeClass("hide");//显示 标准计算规则 按钮
                    }
                    //自定义计算规则
                    if (rule_type == "1") {
                        $(".btn_custom_calc_rule").removeClass("hide");//显示 自定义计算规则 按钮
                        $(".btn_general_calc_rule").addClass("hide");//显示 标准计算规则 按钮
                    }
                    //标准计算规则
                    if (rule_type == "2") {
                        $(".btn_custom_calc_rule").addClass("hide");//显示 自定义计算规则 按钮
                        $(".btn_general_calc_rule").removeClass("hide");//显示 标准计算规则 按钮
                    }
                }
                else {
                    //console.log("获取日志-----error：");
                    //console.log(data.msg);

                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },
    //显示 自定义计算规则
    showCustomCalcRule: function () {
        salaryCalculate.rule_type = 1;//自定义 计算规则

        salaryCalculate.requestCalculateRule(
            function (data) {

                //隐藏 标准计算规则
                salaryCalculate.hideGeneralCalcRule();

                salaryCalculate.salaryCalculateRule = data.result;
                salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);

                //自定义计算规则 显示
                $custom_calc_rule_container.removeClass("hide");

                //自定义计算规则 按钮
                var $btn_custom_calc_rule = $(".btn_custom_calc_rule");
                $btn_custom_calc_rule.removeClass("btn-primary");
                $btn_custom_calc_rule.addClass("btn-warning");
                $btn_custom_calc_rule.attr("onclick", "salaryCalculate.hideCustomCalcRule()");
                $btn_custom_calc_rule.text("隐藏自定义计算规则");

            },
            function (data) {

            }
        );

    },
    //隐藏 自定义计算规则
    hideCustomCalcRule: function () {

        //自定义 计算规则 隐藏
        $custom_calc_rule_container.addClass("hide");

        //自定义计算规则 按钮
        var $btn_custom_calc_rule = $(".btn_custom_calc_rule");
        $btn_custom_calc_rule.addClass("btn-primary");
        $btn_custom_calc_rule.removeClass("btn-warning");
        $btn_custom_calc_rule.attr("onclick", "salaryCalculate.showCustomCalcRule()");
        $btn_custom_calc_rule.text("自定义计算规则");

    },
    //显示 通用计算规则
    showGeneralCalcRule: function () {
        salaryCalculate.rule_type = 2;//通用计算规则

        salaryCalculate.requestCalculateRule(
            function (data) {

                //隐藏 自定义计算规则
                salaryCalculate.hideCustomCalcRule();

                salaryCalculate.salaryCalculateRule = data.result;
                salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);

                //自定义计算规则 显示
                $general_calc_rule_container.removeClass("hide");

                //通用计算规则 按钮
                var $btn_general_calc_rule = $(".btn_general_calc_rule");
                $btn_general_calc_rule.removeClass("btn-primary");
                $btn_general_calc_rule.addClass("btn-warning");
                $btn_general_calc_rule.attr("onclick", "salaryCalculate.hideGeneralCalcRule()");
                $btn_general_calc_rule.text("隐藏标准计算规则");

            },
            function (data) {

            }
        );
    },
    //隐藏 通用计算规则
    hideGeneralCalcRule: function () {

        //通用计算规则 隐藏
        $general_calc_rule_container.addClass("hide");

        //通用计算规则 按钮
        var $btn_general_calc_rule = $(".btn_general_calc_rule");
        $btn_general_calc_rule.addClass("btn-primary");
        $btn_general_calc_rule.removeClass("btn-warning");
        $btn_general_calc_rule.attr("onclick", "salaryCalculate.showGeneralCalcRule()");
        $btn_general_calc_rule.text("标准计算规则");

    },

    //获取 计算规则
    requestCalculateRule: function (successFunc, errorFunc) {
        var node = salary_calc_param.currentTreeNode;
        if (node == null) {
            toastr.warning("请先选中集团或部门!");
            return;
        }

        salaryCalculate.salaryCalculateRule = null;

        var params = {
            rule_type: salaryCalculate.rule_type
        };
        //集团
        if (node["type"] == 1) {
            params["group_id"] = node["id"];
            //params["department_id"] = "";
        }
        //通用部门
        if (node["type"] == 3) {
            params["department_id"] = node["id"];
            //params["group_id"] = "";
        }

        //请求计算规则
        var url = urlGroup.salary_calc_rule + "?" + jsonParseParam(params);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {

                if (data.code == ERR_CODE_OK) {

                    successFunc(data);

                }
                else {
                    toastr.warning(data.msg);
                    errorFunc();
                }

            },
            function (data) {
                errorFunc(data);
            }
        );

    },
    //赋值 计算规则
    setSalaryCalculateRule: function (ruleDetail) {
        salaryCalculate.salaryCalculateRuleChanged = false;

        //自定义 计算规则
        if (salaryCalculate.rule_type == 1) {

            //保存按钮 不可点击
            $custom_calc_rule_container.find(".btn_save").attr("disabled", "disabled");

            salaryCalculate.clearCustomCalcRule();//自定义计算规则 清空
            salaryCalculate.cancelEditCustomCalcRule();//自定义计算规则 取消编辑

            //计税档 列表
            var taxGears = $custom_calc_rule_container.find(".tax_gears_list");
            if (ruleDetail["tax_gears"] && ruleDetail["tax_gears"].length > 0) {

                $.each(ruleDetail["tax_gears"], function (index, gear) {
                    var gearRow = $("#tax_gear_row").clone(true);
                    gearRow.appendTo(taxGears);
                    var id = "tax_gear_" + gear["gear"];
                    gearRow.attr("id", id);
                    gearRow.removeAttr("hidden");
                    gearRow.find(".tax_gear").val(gear["gear"]);
                    gearRow.find(".tax_rate").val(gear["tax_rate"]);
                    gearRow.find(".delete_tax_rate").attr("onclick", "salaryCalculate.deleteTaxRate('#" + id + "')");
                });

            }

            //个税服务费
            var service_charge_tax_rate = ruleDetail["service_charge_tax_rate"] ? ruleDetail["service_charge_tax_rate"] : "";
            $custom_calc_rule_container.find("#service_charge_tax_rate")
                .val(service_charge_tax_rate);

            //薪资服务费率
            var brokerage_rate = ruleDetail["brokerage_rate"] ? ruleDetail["brokerage_rate"] : "";
            $custom_calc_rule_container.find("#brokerage_rate").val(brokerage_rate);

            //遍历 输入框
            $custom_calc_rule_container.find("input[type='number']").change(function () {
                salaryCalculate.salaryCalculateRuleChanged = true;//

                //保存 按钮 可点击
                $custom_calc_rule_container.find(".btn_save").removeAttr("disabled");
            });

        }

        //标准 计算规则
        if (salaryCalculate.rule_type == 2) {

            //保存按钮 不可点击
            $general_calc_rule_container.find(".btn_save").attr("disabled", "disabled");

            salaryCalculate.clearGeneralCalcRule();//标准计算规则 清空
            salaryCalculate.cancelEditGeneralCalcRule();//标准计算规则 取消编辑


            //起征点
            var threshold_tax = ruleDetail["threshold_tax"] ? ruleDetail["threshold_tax"] : "";
            $general_calc_rule_container.find(".threshold_tax").val(threshold_tax);

            //薪资服务费
            var brokerage = ruleDetail["brokerage"] ? ruleDetail["brokerage"] : "";
            $general_calc_rule_container.find(".brokerage").val(brokerage);

            //遍历 输入框
            $general_calc_rule_container.find("input[type='number']").change(function () {
                salaryCalculate.salaryCalculateRuleChanged = true;//

                //保存 按钮 可点击
                $general_calc_rule_container.find(".btn_save").removeAttr("disabled");
            });

        }

    },

    //增加计税档输入框
    addTaxRate: function () {
        //计税档 列表
        var taxGears = $custom_calc_rule_container.find(".tax_gears_list");

        var gearRow = $("#tax_gear_row").clone(true);
        gearRow.prependTo(taxGears);

        gearRow.find(".tax_gear").removeAttr("readonly", "readonly");
        gearRow.find(".tax_rate").removeAttr("readonly", "readonly");
        var id = "tax_gear_" + salaryCalculate.tempCount;
        gearRow.removeAttr("hidden");
        gearRow.attr("id", id);
        gearRow.find(".delete_tax_rate").attr("onclick", "salaryCalculate.deleteTaxRate('#" + id + "')");
        salaryCalculate.tempCount++;

        salaryCalculate.salaryCalculateRuleChanged = true;//计算规则 发生改变
        $custom_calc_rule_container.find(".btn_save").removeAttr("disabled");
    },
    //删除计税档
    deleteTaxRate: function (rowId) {
        $(rowId).remove();
        salaryCalculate.salaryCalculateRuleChanged = true;
        $custom_calc_rule_container.find(".btn_save").removeAttr("disabled");
    },
    //自定义计算规则 清空
    clearCustomCalcRule: function () {

        //计税档 置空
        $custom_calc_rule_container.find(".tax_gears_list").empty();
        //个税服务费率 置空
        $custom_calc_rule_container.find("#service_charge_tax_rate").val("");
        //薪资服务费率 置空
        $custom_calc_rule_container.find("#brokerage_rate").val("");
        //所有输入框 不可输入值
        $custom_calc_rule_container.find("input[type='number']").each(function () {
            $(this).attr("readonly", "readonly");
        });

    },
    //自定义计算规则 编辑
    editCustomCalcRule: function () {

        $custom_calc_rule_container.find(".btn_edit").addClass("hide");
        $custom_calc_rule_container.find(".btn_cancel").removeClass("hide");
        $custom_calc_rule_container.find(".btn_save").removeClass("hide");
        $custom_calc_rule_container.find(".btn_del").removeClass("hide");
        $custom_calc_rule_container.find(".btn_add_tax_rate").removeClass("hide");

        $custom_calc_rule_container.find("input[type='number']").each(function () {
            $(this).removeAttr("readonly");
        });

        $(".delete_tax_rate").removeClass("hide");
    },
    //自定义计算规则 取消编辑
    cancelEditCustomCalcRule: function () {

        //编辑按钮 显示
        $custom_calc_rule_container.find(".btn_edit").removeClass("hide");
        //新增计税档 按钮 隐藏
        $custom_calc_rule_container.find(".btn_add_tax_rate").addClass("hide");
        //取消编辑按钮 隐藏
        $custom_calc_rule_container.find(".btn_cancel").addClass("hide");
        //保存按钮 隐藏
        $custom_calc_rule_container.find(".btn_save").addClass("hide");
        //删除按钮 隐藏
        $custom_calc_rule_container.find(".btn_del").addClass("hide");

        //输入框 禁止输入
        $custom_calc_rule_container.find("input[type='number']").each(function () {
            $(this).attr("readonly", "readonly");
        });
        //计税档列表中 删除按钮 隐藏
        $(".delete_tax_rate").addClass("hide");

        //如果规则发生改变 ，重新赋值规则
        if (salaryCalculate.salaryCalculateRuleChanged) {
            //debugger
            salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);
        }

    },
    //自定义计算规则 保存
    saveCustomCalcRule: function () {
        $custom_calc_rule_container.find(".btn_save").attr("disabled", "disabled");
        if (!salaryCalculate.salaryCalculateRuleChanged) {
            return;
        }

        loadingInit();

        var serviceChargeRate = $custom_calc_rule_container.find("#service_charge_tax_rate").val();
        var brokerageRate = $custom_calc_rule_container.find("#brokerage_rate").val();

        var departmentId = "";
        var groupId = "";
        if (salary_calc_param.currentTreeNode.type == 1) {
            groupId = salary_calc_param.currentTreeNode.id;
        }
        else {
            departmentId = salary_calc_param.currentTreeNode.id;
        }

        //计税档 列表
        var taxGears = new Array();
        $custom_calc_rule_container.find(".tax_gears_list .row").each(function () {
            var gearInput = $(this).find(".tax_gear");
            var gear = gearInput.val();
            var rateInput = $(this).find(".tax_rate");
            var rate = rateInput.val();
            var taxGear = {"gear": gear, "tax_rate": rate};
            taxGears.push(taxGear);
        });

        //参数
        var params = {
            "group_id": groupId,
            "department_id": departmentId,
            "rule_name": "自定义计算规则",
            "service_charge_tax_rate": serviceChargeRate,
            "brokerage_rate": brokerageRate,
            "tax_gears": taxGears,
            "rule_type": salaryCalculate.rule_type,
            "threshold_tax": "",
            "brokerage": ""
        };
        //URL 默认是 新增
        var url = urlGroup.salary_calc_rule_add;

        //编辑 状态
        if (salaryCalculate.salaryCalculateRule && salaryCalculate.salaryCalculateRule["id"]) {

            params["id"] = salaryCalculate.salaryCalculateRule["id"];//
            url = urlGroup.salary_calc_rule_modify;

        }

        aryaPostRequest(
            url,
            params,
            function (data) {
                if (data.code == ERR_CODE_OK) {
                    //salaryCalculate.salaryCalculateRule = data.result;
                    //salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);

                    toastr.success("自定义计算规则保存成功！");

                    salaryCalculate.initCalcRule();//初始化 计算规则
                    salaryCalculate.showCustomCalcRule();//显示 自定义计算规则

                }
                else {
                    toastr.error(data.msg);
                }

            },
            function (data) {
            }
        );

    },
    //自定义计算规则 删除
    deleteCustomCalcRule: function () {
        if (!salaryCalculate.salaryCalculateRule || !salaryCalculate.salaryCalculateRule["id"]) {
            toastr.warning("暂无规则，请先新增规则！");
            return;
        }

        var name = "确定要删除" + salary_calc_param.currentTreeNode["name"] + "的计算规则吗?";

        delWarning(name, function () {
            loadingInit();

            var departmentId = "";
            var groupId = "";
            if (salary_calc_param.currentTreeNode.type == 1) {
                groupId = salary_calc_param.currentTreeNode.id;
            }
            else {
                departmentId = salary_calc_param.currentTreeNode.id;
            }

            var params = {
                "id": salaryCalculate.salaryCalculateRule["id"],
                "group_id": groupId,
                "department_id": departmentId
            };

            aryaPostRequest(
                urlGroup.salary_calc_rule_del,
                params,
                function (data) {
                    if (data.code == ERR_CODE_OK) {
                        //salaryCalculate.clearCustomCalcRule();//清空 自定义计算规则
                        //salaryCalculate.cancelEditCustomCalcRule();//取消编辑 自定义计算规则
                        //salaryCalculate.hideCustomCalcRule();//隐藏 自定义计算规则

                        toastr.success("删除自定义计算规则成功！");
                        salaryCalculate.initCalcRule();//初始化 计算规则
                    }
                    else {
                        toastr.warning(data.msg);
                    }
                },
                function (data) {
                }
            );

        });

    },

    //标准计算规则 清空
    clearGeneralCalcRule: function () {

        //起征点 置空
        $general_calc_rule_container.find(".threshold_tax").val("");
        //薪资服务费 置空
        $general_calc_rule_container.find(".brokerage").val("");
        //所有输入框 不可输入值
        $general_calc_rule_container.find("input").each(function () {
            $(this).attr("readonly", "readonly");
        });

    },
    //标准计算规则 取消编辑
    cancelEditGeneralCalcRule: function () {

        //编辑按钮 显示
        $general_calc_rule_container.find(".btn_edit").removeClass("hide");
        //取消编辑按钮 隐藏
        $general_calc_rule_container.find(".btn_cancel").addClass("hide");
        //保存按钮 隐藏
        $general_calc_rule_container.find(".btn_save").addClass("hide");
        //删除按钮 隐藏
        $general_calc_rule_container.find(".btn_del").addClass("hide");

        //输入框 禁止输入
        $general_calc_rule_container.find("input").each(function () {
            $(this).attr("readonly", "readonly");
        });

        //如果规则发生改变 ，重新赋值规则
        if (salaryCalculate.salaryCalculateRuleChanged) {
            //debugger
            salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);
        }

    },
    //标准计算规则 编辑
    editGeneralCalcRule: function () {

        $general_calc_rule_container.find(".btn_edit").addClass("hide");
        $general_calc_rule_container.find(".btn_cancel").removeClass("hide");
        $general_calc_rule_container.find(".btn_save").removeClass("hide");
        $general_calc_rule_container.find(".btn_del").removeClass("hide");

        $general_calc_rule_container.find("input[type='number']").each(function () {
            $(this).removeAttr("readonly");
        });

    },
    //标准计算规则 保存
    saveGeneralCalcRule: function () {
        $general_calc_rule_container.find(".btn_save").attr("disabled", "disabled");
        if (!salaryCalculate.salaryCalculateRuleChanged) {
            return;
        }

        loadingInit();

        var threshold_tax = $general_calc_rule_container.find(".threshold_tax").val();
        var brokerage = $general_calc_rule_container.find(".brokerage").val();

        var departmentId = "";
        var groupId = "";
        if (salary_calc_param.currentTreeNode.type == 1) {
            groupId = salary_calc_param.currentTreeNode.id;
        }
        else {
            departmentId = salary_calc_param.currentTreeNode.id;
        }

        //参数
        var params = {
            "group_id": groupId,
            "department_id": departmentId,
            "rule_name": "标准计算规则",
            "rule_type": salaryCalculate.rule_type,
            "service_charge_tax_rate": "",
            "brokerage_rate": "",
            "tax_gears": [],
            "threshold_tax": threshold_tax,
            "brokerage": brokerage
        };
        //URL 默认是 新增
        var url = urlGroup.salary_calc_rule_add;

        //编辑 状态
        if (salaryCalculate.salaryCalculateRule &&
            salaryCalculate.salaryCalculateRule["id"]) {

            params["id"] = salaryCalculate.salaryCalculateRule["id"];//
            url = urlGroup.salary_calc_rule_modify;

        }

        aryaPostRequest(
            url,
            params,
            function (data) {
                if (data.code == ERR_CODE_OK) {
                    //salaryCalculate.salaryCalculateRule = data.result;
                    //salaryCalculate.setSalaryCalculateRule(salaryCalculate.salaryCalculateRule);

                    toastr.success("标准计算规则保存成功！");

                    salaryCalculate.initCalcRule();//初始化 计算规则
                    salaryCalculate.showGeneralCalcRule();//显示 标准计算规则

                }
                else {
                    toastr.error(data.msg);
                }

            },
            function (data) {
            }
        );

    },
    //标准计算规则 删除
    deleteGeneralCalcRule: function () {
        if (!salaryCalculate.salaryCalculateRule || !salaryCalculate.salaryCalculateRule["id"]) {
            toastr.warning("暂无规则，请先新增规则！");
            return;
        }

        var name = "确定要删除" + salary_calc_param.currentTreeNode["name"] + "的计算规则吗?";

        delWarning(name, function () {
            loadingInit();

            var departmentId = "";
            var groupId = "";
            if (salary_calc_param.currentTreeNode.type == 1) {
                groupId = salary_calc_param.currentTreeNode.id;
            }
            else {
                departmentId = salary_calc_param.currentTreeNode.id;
            }

            var params = {
                "id": salaryCalculate.salaryCalculateRule["id"],
                "group_id": groupId,
                "department_id": departmentId
            };

            aryaPostRequest(
                urlGroup.salary_calc_rule_del,
                params,
                function (data) {
                    if (data.code == ERR_CODE_OK) {
                        //salaryCalculate.hideGeneralCalcRule();//隐藏 标准计算规则

                        toastr.success("删除标准计算规则成功！");

                        salaryCalculate.initCalcRule();//初始化 计算规则
                    }
                    else {
                        toastr.warning(data.msg);
                    }
                },
                function (data) {
                }
            );

        });

    }

};

var salary_calc_param = {
    currentTreeNode: null,//选中 节点
    settlementInterval: 0,//计算周期
    yearMonth: "",//年月
    batch_no: 0,//批次 号 week

    currentYear: null,//当前年份
    currentMonth: null,//当前月份
    key_word: "",//查询条件

    user_id: "",//用户 id
    user_name: "",//用户 姓名
    user_phone_no: "",//用户 手机
    user_idcard_no: "",//用户 身份证
    user_bank_account: "",//用户 卡号

};

$(document).ready(function () {

    // //初始化 树结构
    // initOrganizationTree(
    //     "#salary_group_tree",
    //     "#salary_group_tree_hud",
    //     urlGroup.salary_group_tree_url,
    //     salaryCalculate.groupTreeOnclick
    // );

    var treeId = ".salary_calculate_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".salary_calculate_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.salary_group_tree_url;//获取树结构 url
    var treeClickFunc = salaryCalculate.groupTreeOnclick;//树结构 click事件
    // var clearInfoByTreeClickFunc = salaryCalculate.init;	//初始化 页面

    organizationTree.init(treeId, searchId, url, treeClickFunc, function () {

    });//初始化 树结构


    //初始化 薪资计算
    salaryCalculate.init();
});
