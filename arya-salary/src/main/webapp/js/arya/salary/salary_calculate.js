/**
 * Created by CuiMengxin on 2016/3/24.
 * 薪资计算导入
 */

var $operate_status_modal = $(".operate_status_modal");//操作记录 弹框
var $user_info_modal = $(".user_info_modal");//用户信息 弹框

var $salary_info_container = $(".salary_info_container");
var $upload_file_form;//选择文件的 form
var $salary_upload_container = $salary_info_container.find(".upload_container");//导入 薪资文件 container
var $salary_search_container = $salary_info_container.find(".search_container");//查询 薪资文件 container

var $salary_calc_container = $salary_info_container.find(".salary_calc_container");//薪资计算结果 container
var $salary_statistic_container = $salary_info_container.find(".salary_statistic_container");//薪资统计结果 container

//薪资 计算 管理
var salary_calc_manage = {

    search_or_calc: "",//当前操作是 查询 还是 计算 ( 薪资计算结果 - 分页功能)
    current_operate: "",//当前操作
    calc_interval: {        //计算周期
        month: "1",   //  月份
        batch: "2"    //  批次
    },

    //初始化
    init: function () {

        salary_import.init();//初始化 导入条件
        salary_calc_manage.initSearch();//初始化 查询container
        salary_calc_result.initSalaryCalcResult();//初始化 薪资计算结果
        salary_statistics_result.initSalaryStatisticsResult();//初始化 薪资统计结果

    },

    //初始化 查询container
    initSearch: function () {

        salary_calc_manage.initSettlementInterval();//初始化 结算周期
        salary_calc_manage.initMonthPicker();//初始化 月份选择器
        salary_calc_manage.initBatch();//初始化 批次列表
        $salary_search_container.find(".key_word").val("");

    },
    //初始化 结算周期
    initSettlementInterval: function () {

        var $settlement_interval = $salary_search_container.find(".settlement_interval");
        // var $batch_list_container = $salary_search_container.find(".batch_list_container");
        // var $batch_list = $batch_list_container.find(".batch_list");

        //默认选择 批次
        salary_calc_param.settlementInterval = salary_calc_manage.calc_interval.batch;
        $settlement_interval.val(salary_calc_param.settlementInterval);
        $settlement_interval.unbind("change").change(function () {

            //检查 是否选中公司
            if (!salary_calc_manage.checkIsChooseCom()) {
                $settlement_interval.val(salary_calc_param.settlementInterval);
                return
            }

            salary_calc_param.settlementInterval = $settlement_interval.val();//结算周期
            salary_import.init(); //初始化 导入条件（更换计算周期后）

            //如果是 批次,并且没有选择年月
            if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.batch &&
                (!salary_calc_param.year || !salary_calc_param.month)) {
                toastr.warning("请选择日期！");
                return
            }

            salary_calc_manage.initBatch();//初始化 批次列表

        });

    },
    //初始化月份选择器
    initMonthPicker: function () {

        //如果 选择了年月 ,并且 当前计算周期 是 批次
        if (salary_calc_param.year && salary_calc_param.month &&
            salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.batch) {

            // salary_calc_manage.getImportBatches(salary_calc_param.year, salary_calc_param.month, true, null);

            return;

        }

        var $year_month = $salary_search_container.find(".year_month");
        $year_month.html("");
        salary_calc_param.year = null;
        salary_calc_param.month = null;
        $year_month.datepicker('remove');//移除月份选择器

        $year_month.datepicker({
            minViewMode: 1,
            // keyboardNavigation: true,
            // forceParse: false,
            autoclose: true,//选择后 自动关闭试图
            // todayHighlight: true,
            format: 'yyyy-mm'
        });

        $year_month.unbind("show").on("show", function (e) {
            // console.log(e);
            // console.log(e.date);

            //检查 是否选中公司
            if (!salary_calc_manage.checkIsChooseCom()) {
                $year_month.datepicker("hide");
            }

        });

        $year_month.unbind("changeMonth").on("changeMonth", function (e) {

            //更换月份事件
            var choseDate = e.date;

            var date = new Date(choseDate);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m > 9 ? m : "0" + m;
            // var yearMonth = y + "-" + m;

            //如果 已经选择了该月份
            if (y === salary_calc_param.year && m === salary_calc_param.month) {
                $year_month.html("");
                salary_calc_param.year = null;
                salary_calc_param.month = null;
            }
            else {
                salary_calc_param.year = y;
                salary_calc_param.month = m;

                $year_month.html(y + "-" + m);
            }

            salary_calc_manage.initBatch();//初始化 批次列表

        });

    },
    //初始化 批次列表
    initBatch: function () {

        var $batch_list_container = $salary_search_container.find(".batch_list_container");
        var $batch_list = $batch_list_container.find(".batch_list");

        //如果 选择了年月 ,并且 当前计算周期 是 批次
        if (salary_calc_param.year && salary_calc_param.month &&
            salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.batch) {

            $batch_list_container.show();

            salary_calc_manage.getImportBatches(salary_calc_param.year, salary_calc_param.month, true, null);

        }
        else {
            $batch_list_container.hide();
        }

        // $batch_list_container.hide();//隐藏 批次列表
        $batch_list.unbind("change").change(function () {
            salary_calc_param.batch_no = $batch_list.val();//结算周期
        });

    },
    //根据年月 获取批次列表
    getImportBatches: function (year, month, selectLastOne, successFunc) {

        //检查 是否选中公司
        if (!salary_calc_manage.checkIsChooseCom()) {
            return
        }

        var obj = {
            customerId: salary_calc_param.currentTreeNode.id,
            year: salary_calc_param.year,
            month: salary_calc_param.month
        };
        var url = urlGroup.salary_calc_batch_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    var $batch_list = $salary_search_container.find(".batch_list");

                    //批次列表 清空
                    $batch_list.empty();

                    var option = $("<option></option>");
                    option.appendTo($batch_list);
                    option.text("新增");
                    option.val("");

                    if (data.result) {

                        for (var i = 0; i < data.result.length; i++) {

                            var option = $("<option></option>");
                            option.appendTo($batch_list);
                            option.val(data.result[i]);
                            option.text(data.result[i]);

                            //最后一个 是否 默认选中
                            if (i === data.result.length - 1 && selectLastOne) {
                                option.attr("selected", true);
                            }

                        }
                    }

                    if (successFunc) {
                        successFunc();
                    }

                }
                else {
                    toastr.warning(data.msg);
                }
            },
            function (data) {

            }
        );

    },

    //查询 按钮 click
    btnSearchClick: function () {
        salary_calc_result.currentPage = 1;
        salary_calc_manage.salaryQuery();//薪资 查询
    },
    //薪资 查询
    salaryQuery: function () {

        //检查 参数
        if (!salary_calc_manage.checkParamBySearch()) {
            return
        }

        //赋值 查询字段
        salary_calc_param.setSalaryCalcParam();

        salary_calc_result.initSalaryCalcResult();//初始化 薪资计算结果
        salary_statistics_result.initSalaryStatisticsResult();//初始化 薪资统计结果

        loadingInit();

        var obj = {
            customerId: salary_calc_param.currentTreeNode.id,
            settlementInterval: salary_calc_param.settlementInterval,
            year: salary_calc_param.year,
            month: salary_calc_param.month,
            week: salary_calc_param.batch_no,
            condition: salary_calc_param.key_word,
            page: salary_calc_result.currentPage,
            page_size: "10"
        };
        var url = urlGroup.salary_query + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    //当前操作 为 查询
                    salary_calc_manage.search_or_calc = "search";

                    salary_calc_result.totalPage = $item.pages ? $item.pages : 1;
                    salary_calc_result.operate_result = $item.icResult ? $item.icResult : null;//错误 提示

                    //显示结果
                    salary_calc_result.showCalculateResult($item["calculate_result_pager"]);
                    salary_statistics_result.showStatisticsResult($item["salary_calculate_count_result_list"]);

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
    //检查 参数
    checkParamBySearch: function () {

        var flag = false;

        if (!salary_calc_param.currentTreeNode) {
            toastr.warning("请先选择客户公司！");
        }
        else if (!salary_calc_param.year || !salary_calc_param.month) {
            toastr.warning("请选择月份！");
        }
        else {
            flag = true;
        }

        return flag;

    },

    //组织树的点击事件
    groupTreeOnclick: function (node) {

        salary_calc_param.currentTreeNode = node;//

        //公司名称
        var name = salary_calc_param.currentTreeNode["name"] + "的薪资信息";
        $salary_info_container.find(".corp_name").text(name);

        salary_calc_manage.init();//初始化

    },

    //检查 是否选择了公司
    checkIsChooseCom: function () {
        var flag = false;

        if (!salary_calc_param.currentTreeNode || !salary_calc_param.currentTreeNode.id) {
            toastr.warning("请先选择客户公司！");
        }
        else {
            flag = true;
        }

        return flag;

    },

    //导出
    exportSalary: function (type) {

        // 导出分类（1.导出薪资数据 2.导出统计结果 3.导出开票申请 4.导出发票回执单）

        var obj = {
            customerId: salary_calc_param.currentTreeNode.id,
            year: salary_calc_param.year,
            month: salary_calc_param.month,
            week: salary_calc_param.batch_no,
            condition: salary_calc_param.key_word,
            exportType: type
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.salary_export,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    // toastr.success("导出成功！");

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


    },

    //客户列表（组织树列表）
    organizationTreeList: function () {

        var obj = {
            condition: $("#salary_customer_search").val()
        };
        var url = urlGroup.salary_calc_tree_url + "?" + jsonParseParam(obj);

        //初始化 树结构
        initOrganizationTree(
            "#salary_group_tree",
            url,
            salary_calc_manage.groupTreeOnclick
        );

    },
    //enter键 查询列表
    enterSearch: function (e) {

        var ev = document.all ? window.event : e;
        if (ev.keyCode === 13) {
            salary_calc_manage.organizationTreeList();//初始化 组织树列表
        }

    }


};
//薪资 查询参数
var salary_calc_param = {

    currentTreeNode: null,//选中 节点

    settlementInterval: null,//计算周期 1 月 2 批次
    // yearMonth: null,//年月
    year: null,//年份
    month: null,//月份
    batch_no: null,//批次 号 week
    key_word: "",//查询条件

    //赋值 查询字段
    setSalaryCalcParam: function () {

        salary_calc_param.settlementInterval =
            $salary_search_container.find(".settlement_interval").val();//结算周期

        var yearMonth = $salary_search_container.find(".year_month").html();//选择的 年月
        if (yearMonth) {
            salary_calc_param.year = yearMonth.split("-")[0];
            salary_calc_param.month = yearMonth.split("-")[1];
        }

        salary_calc_param.batch_no = $salary_search_container.find(".batch_list").val() ?
            $salary_search_container.find(".batch_list").val() : "";//批次

        salary_calc_param.key_word = $salary_search_container.find(".key_word").val();//关键字

        //如果 当前结算周期是 月，则批次置空
        if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.month) {
            salary_calc_param.batch_no = "";
        }

    }

};

//导入
var salary_import = {

    file_name: "",//文件 本地名称

    //初始化 导入条件
    init: function () {

        salary_import.initForm();////初始化 上传文件form
        salary_import.initFileParam();//上传文件 参数初始化

    },
    //初始化 form
    initForm: function () {

        if ($salary_upload_container.find(".upload_file_form").length > 0) {
            $salary_upload_container.find(".upload_file_form").remove();
        }

        $upload_file_form = $("<form>");
        $upload_file_form.appendTo($salary_upload_container);
        $upload_file_form.addClass("upload_file_form");
        $upload_file_form.attr("enctype", "multipart/form-data");
        $upload_file_form.hide();


        //salary_file
        var $salary_file = $("<input>");
        $salary_file.appendTo($upload_file_form);
        $salary_file.addClass("upload_file");
        $salary_file.attr("type", "file");
        $salary_file.attr("name", "file");
        // $salary_file.attr("accept", ".xlsx");
        $salary_file.attr("onchange", "salary_import.ChooseFile(this)");

        // customerId
        var $customerId = $("<input>");
        $customerId.appendTo($upload_file_form);
        $customerId.addClass("customerId");
        $customerId.attr("type", "hidden");
        $customerId.attr("name", "customerId");

        //计算周期
        var $settlementInterval = $("<input>");
        $settlementInterval.appendTo($upload_file_form);
        $settlementInterval.addClass("settlement_interval");
        $settlementInterval.attr("type", "hidden");
        $settlementInterval.attr("name", "settlementInterval");

        //年份
        var $year = $("<input>");
        $year.appendTo($upload_file_form);
        $year.addClass("year");
        $year.attr("type", "hidden");
        $year.attr("name", "year");

        //月份
        var $month = $("<input>");
        $month.appendTo($upload_file_form);
        $month.addClass("month");
        $month.attr("type", "hidden");
        $month.attr("name", "month");

        //week 批次
        var $batch = $("<input>");
        $batch.appendTo($upload_file_form);
        $batch.addClass("week");
        $batch.attr("type", "hidden");
        $batch.attr("name", "week");

        //page
        var $page = $("<input>");
        $page.appendTo($upload_file_form);
        $page.addClass("page");
        $page.attr("type", "hidden");
        $page.attr("name", "page");

        //page_size
        var $page_size = $("<input>");
        $page_size.appendTo($upload_file_form);
        $page_size.attr("type", "hidden");
        $page_size.attr("name", "page_size");
        $page_size.attr("value", "10");

    },
    //上传文件 - 初始化
    initFileParam: function () {
        salary_import.file_name = "";

        $salary_upload_container.find(".file_path").html(salary_import.file_name);
        $salary_upload_container.find(".btn_calc").addClass("btn-default").removeClass("btn-primary");
        $salary_upload_container.find(".btn_import").addClass("btn-default").removeClass("btn-primary");

    },

    //下载模板
    templateDown: function () {

        //检查 是否选中公司
        if (!salary_calc_manage.checkIsChooseCom()) {
            return
        }

        var obj = {
            customerId: salary_calc_param.currentTreeNode.id
        };
        var url = urlGroup.salary_template_down + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                // console.log("获取日志：");
                // console.log(data);

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

    //选择文件 - 按钮点击
    ChooseFileClick: function () {

        //检查 是否选中公司
        if (!salary_calc_manage.checkIsChooseCom()) {
            return
        }

        //如果 计算周期 是 月
        if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.month) {
            toastr.warning("不能按月份导入文件，只可以按批次导入文件！");
            return;
        }


        $upload_file_form.find(".upload_file").click();

    },
    //选择文件
    ChooseFile: function (self) {
        // console.info("订单导入文件：");
        // console.log(self);
        // console.log(self.value + "\n" + typeof  self.value);

        if (self.value) {
            if (self.files) {

                for (var i = 0; i < self.files.length; i++) {
                    var file = self.files[i];

                    //判断是否是xls格式
                    if (/\.(xlsx|xls)$/.test(file.name)) {

                        //赋值文件名称
                        salary_import.file_name = file.name;
                        $salary_upload_container.find(".file_path").html(salary_import.file_name);

                        //如果上传的是excel，“预览”按钮显示“蓝色”，可以被点击
                        $salary_upload_container.find(".btn_calc").addClass("btn-primary")
                            .removeClass("btn-default");
                        //导入按钮 不能点击
                        $salary_upload_container.find(".btn_import").addClass("btn-default")
                            .removeClass("btn-primary");

                    }
                    else {
                        toastr.warning("请上传excel文档，以.xls或.xlsx结尾");
                    }

                }

            }
        }
        else {
            salary_import.init();
        }
    },

    //文件 - 预览按钮 点击事件
    salaryPreviewClick: function () {

        //点击 “预览按钮”时 默认page===1
        salary_calc_result.currentPage = 1;
        salary_calc_result.is_preview_btn_click = true;//预览按钮点击

        salary_import.salaryCalc(); //文件 - 计算

    },
    //文件 - 计算
    salaryCalc: function () {

        //检查参数 是否正确
        if (!salary_import.checkParams()) {
            return
        }

        //如果 当前结算周期是 月，则批次置空
        if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.month) {
            salary_calc_param.batch_no = "";
        }

        //赋值
        $upload_file_form.find(".customerId").val(salary_calc_param.currentTreeNode.id);
        $upload_file_form.find(".settlement_interval").val(salary_calc_param.settlementInterval);
        $upload_file_form.find(".year").val(salary_calc_param.year);
        $upload_file_form.find(".month").val(salary_calc_param.month);
        $upload_file_form.find(".week").val(salary_calc_param.batch_no);
        $upload_file_form.find(".page").val(salary_calc_result.currentPage);

        salary_calc_result.initSalaryCalcResult();//初始化 薪资计算结果
        salary_statistics_result.initSalaryStatisticsResult();//初始化 薪资统计结果

        loadingInit();

        $upload_file_form.ajaxSubmit({
            url: urlGroup.salary_result_get_by_upload,
            type: 'post',
            dataType: 'json',
            data: $upload_file_form.fieldSerialize(),
            beforeSend: function () {
            },
            uploadProgress: function (event, position, total, percentComplete) {
                // var percentVal = percentComplete + '%';
            },
            success: function (data) {
                loadingRemove();

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        var $item = data.result;

                        //当前操作 为 计算
                        salary_calc_manage.search_or_calc = "calc";

                        salary_calc_result.totalPage = $item.pages ? $item.pages : 1;
                        salary_calc_result.operate_result = $item.icResult ? $item.icResult : null;//错误 提示

                        salary_calc_result.showCalculateResult($item["calculate_result_pager"]);
                        salary_statistics_result.showStatisticsResult($item["salary_calculate_count_result_list"]);

                        if ($salary_calc_container.find(".table_container").find("tbody tr.item").length > 0) {
                            $salary_upload_container.find(".btn_import").addClass("btn-primary").removeClass("btn-default");
                        }

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

    //导入
    salaryImport: function () {

        //检查参数 是否正确
        if (!salary_import.checkParams()) {
            return
        }

        if ($salary_calc_container.find(".table_container").find("tbody tr.item").length <= 0) {
            toastr.warning("当前没有可以导入的薪资计算结果！");
            return
        }
        if ($salary_upload_container.find(".btn_import").hasClass("btn-default")) {
            toastr.warning("请先上传文件并预览！");
            return
        }

        salary_calc_param.batch_no = $salary_search_container.find(".batch_list").val() ?
            $salary_search_container.find(".batch_list").val() : "";//批次
        //赋值
        $upload_file_form.find(".customerId").val(salary_calc_param.currentTreeNode.id);
        $upload_file_form.find(".settlement_interval").val(salary_calc_param.settlementInterval);
        $upload_file_form.find(".year").val(salary_calc_param.year);
        $upload_file_form.find(".month").val(salary_calc_param.month);
        $upload_file_form.find(".week").val(salary_calc_param.batch_no);
        // $upload_file_form.find(".page").val(salary_calc_manage.currentPage);

        var title = "确定导入" + salary_calc_param.year + salary_calc_param.month;
        //按批次导入
        if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.batch) {

            if (salary_calc_param.batch_no) {
                title += "第" + salary_calc_param.batch_no + "批次";
            }
            else {
                title += "新增";
            }

        }
        title += "薪资吗？";

        exportWarning(
            title,
            null,
            function () {

                loadingInit();

                $upload_file_form.ajaxSubmit({
                    url: urlGroup.salary_import,
                    type: 'post',
                    dataType: 'json',
                    data: $upload_file_form.fieldSerialize(),
                    beforeSend: function () {
                        // //记录用户所选的选项
                        // salary_calc_param.settlementInterval = $row.find(".settlement_interval").val();
                        // salary_calc_param.yearMonth = $row.find(".year_month").val();
                        // salary_calc_param.batch_no = $row.find(".batch_list").val();
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        // var percentVal = percentComplete + '%';
                    },
                    success: function (data) {
                        loadingRemove();

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("导入成功！");
                            salary_import.init();//上传文件 - 初始化
                            // salary_import.initFileParam();//上传文件 - 初始化

                            //导入成功后

                            //如果是 按批次导入的
                            // 则查询最新批次后，再查询 计算、统计 结果
                            if (salary_calc_param.settlementInterval === salary_calc_manage.calc_interval.batch) {

                                salary_calc_manage.getImportBatches(
                                    salary_calc_param.year,
                                    salary_calc_param.month,
                                    true,
                                    function () {
                                        salary_calc_manage.btnSearchClick();//查询
                                    }
                                );

                            }
                            //如果是 按照月份导入的，或者是更新批次的情况
                            // 直接查询 计算、统计 结果
                            else {
                                salary_calc_manage.btnSearchClick();//查询
                            }

                        }
                        else {
                            toastr.error(data.msg);
                        }
                    },
                    complete: function (xhr) {
                    }
                });

            }
        );

    },

    //检查是否可 计算、导入
    checkParams: function () {
        var flag = false;
        var txt;

        if (!salary_calc_param.currentTreeNode) {
            txt = "请选择客户公司";
        }
        else if (!salary_calc_param.year || !salary_calc_param.month) {
            txt = "请选择月份";
        }
        else if (!salary_import.file_name) {
            txt = "请选择文件";
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

//薪资 计算结果
var salary_calc_result = {

    currentPage: "1",//当前页数 （薪资计算结果）
    totalPage: "1",//总页数 （薪资计算结果）
    operate_result: null,// 反馈 结果
    is_preview_btn_click: false,//是否是 查询按钮点击

    user_id: "",//用户id （编辑用户时 用到）

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


    //初始化 薪资计算结果
    initSalaryCalcResult: function () {

        //显示 提示框
        $("[data-toggle='tooltip']").tooltip();

        salary_calc_result.clearSalaryCalcResult();//清空 薪资计算结果
        salary_calc_result.clearChooseItem();//清除 选中 状态

        //操作反馈 显示
        $operate_status_modal.unbind("shown.bs.modal").on("shown.bs.modal", function () {

            var $modal_body = $operate_status_modal.find(".modal-body");
            var $tbody = $modal_body.find("table tbody");
            $tbody.empty();
            var $row = $modal_body.find(".row");
            $row.hide();

            //如果没有 反馈记录
            if (!salary_calc_result.operate_result || !salary_calc_result.operate_result.errDataList ||
                salary_calc_result.operate_result.errDataList.length <= 0) {

                var msg = "<tr><td colspan='3'>暂无记录</td></tr>";
                $tbody.html(msg);

            }
            else {

                // debugger;

                var total_count = salary_calc_result.operate_result.totalImportNumber
                    ? salary_calc_result.operate_result.totalImportNumber : 0;//
                var success_count = salary_calc_result.operate_result.successImportNumber
                    ? salary_calc_result.operate_result.successImportNumber : 0;//
                var fail_count = salary_calc_result.operate_result.failImportNumber
                    ? salary_calc_result.operate_result.failImportNumber : 0;//

                $row.show();
                $row.find(".total_count").html(total_count);
                $row.find(".success_count").html(success_count);
                $row.find(".fail_count").html(fail_count);

                $.each(salary_calc_result.operate_result.errDataList, function (i, $item) {

                    var row_no = $item.row ? $item.row : "";//错误的行数
                    var fail_msg_arr = $item.errStringList ? $item.errStringList : [];//错误提示 数组
                    var fail_msg = "";//错误提示 字符串
                    for (var j = 0; j < fail_msg_arr.length; j++) {

                        fail_msg += fail_msg ? "," + fail_msg_arr[j] : fail_msg_arr[j];

                    }

                    var $tr = $("<tr>");
                    $tr.appendTo($tbody);

                    //序号
                    var $no = $("<td>");
                    $no.appendTo($tr);
                    $no.text(i + 1);

                    //行数
                    var $row_no = $("<td>");
                    $row_no.appendTo($tr);
                    $row_no.text("第" + row_no + "行");

                    //错误内容
                    var $fail_msg = $("<td>");
                    $fail_msg.appendTo($tr);
                    $fail_msg.text(fail_msg);

                });

            }


        });


    },
    //清空 薪资计算结果
    clearSalaryCalcResult: function () {

        //清空 薪资计算结果
        var $tbody = $salary_calc_container.find(".content .table_container").find("table tbody");
        var msg = "<tr><td colspan='12'>暂无计算结果</td></tr>";
        $tbody.empty().html(msg);

        $salary_calc_container.find(".pager_container").hide();

        salary_calc_result.hideBtnInSalaryCalcResult();//隐藏 操作按钮 - 薪资计算结果
    },
    //隐藏 操作按钮 - 薪资计算结果
    hideBtnInSalaryCalcResult: function () {

        var $calc_foot = $salary_calc_container.find(".foot");
        $calc_foot.hide();

    },
    //显示 操作按钮 - 薪资计算结果
    showBtnInSalaryCalcResult: function () {

        var $calc_foot = $salary_calc_container.find(".foot");
        $calc_foot.show();

    },

    //显示 薪资计算结果
    showCalculateResult: function (data) {

        salary_calc_result.clearSalaryCalcResult();//清空 薪资计算结果

        //如果 有错误记录，直接显示错误记录
        if (salary_calc_result.operate_result && salary_calc_result.operate_result.errDataList &&
            salary_calc_result.operate_result.errDataList.length > 0 &&
            salary_calc_result.is_preview_btn_click) {

            salary_calc_result.is_preview_btn_click = false;
            $operate_status_modal.modal("show");

        }

        var $table = $salary_calc_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        if (data && data.length > 0) {

            var list = "";

            for (var i = 0; i < data.length; i++) {
                var item = data[i];

                var id = item.id ? item.id : "";//
                var userId = item.userId ? item.userId : "";//
                var districtName = item.districtName ? item.districtName : "";//城市
                var name = item.name ? item.name : "";//姓名
                var idcardNo = item.idcardNo ? item.idcardNo : "";//身份证
                var phone = item.phone ? item.phone : "";//手机号

                var bankAccount = item.bankAccount ? item.bankAccount : "";//账号
                var user_bank_address = item.bankName ? item.bankName : "";//开户行

                var taxableSalary = item.taxableSalary ? item.taxableSalary : "";//税前薪资
                var personalTax = item.personalTax ? item.personalTax : "";//个税处理费
                var netSalary = item.netSalary ? item.netSalary : "";//税后薪资
                var brokerage = item.brokerage ? item.brokerage : "";//薪资服务费


                list +=
                    "<tr class='item' " +
                    "data-id='" + id + "' " +
                    "data-userId='" + userId + "' " +
                    ">" +
                    "<td class='choose_item' onclick='salary_calc_result.chooseItem(this)'>" +
                    "<img src='img/icon_Unchecked.png'>" +
                    "</td>" +
                    "<td><div style='width:50px;'>" + districtName + "</div></td>" +
                    "<td class='user_name'><div>" + name + "</div></td>" +
                    "<td class='user_idCard_no'><div>" + idcardNo + "</div></td>" +
                    "<td class='user_phone_no'><div>" + phone + "</div></td>" +
                    "<td class='user_bank_account'><div>" + bankAccount + "</div></td>" +
                    "<td class='user_bank_address'><div title='" + user_bank_address + "'>" + user_bank_address + "</div></td>" +
                    "<td class='taxableSalary'><div>" + taxableSalary + "</div></td>" +
                    "<td class='personalTax'><div>" + personalTax + "</div></td>" +
                    "<td class='netSalary'><div>" + netSalary + "</div></td>" +
                    "<td class='brokerage'><div>" + brokerage + "</div></td>" +
                    "<td class='operate'>" +
                    "<div class='btn btn-sm btn-primary btn_modify'>编辑</div>" +
                    "</td>" +
                    "</tr>";

            }

            $tbody.html(list);


            //如果是 查询，则显示 操作按钮
            if (salary_calc_manage.search_or_calc === "search") {
                salary_calc_result.showBtnInSalaryCalcResult();//显示 操作按钮 - 薪资计算结果
            }

        }

        salary_calc_result.salaryCalcResultInit();//薪资计算结果 初始化

    },
    //薪资计算结果 初始化
    salaryCalcResultInit: function () {
        salary_calc_result.clearChooseItem();//清除选中 状态

        var $table = $salary_calc_container.find(".table_container table");
        var $item = $table.find("tbody .item");
        var $pager_container = $salary_calc_container.find('.pager_container');

        if ($item.length === 0) {
            $pager_container.hide();
        }
        else {

            $item.each(function () {

                //编辑
                $item.find(".btn_modify").unbind("click").bind("click", function () {

                    //预览文件时，不能进行编辑
                    if (salary_calc_manage.search_or_calc === "calc") {
                        toastr.warning("当前操作是预览，不能进行编辑。只有导入或查询后才能编辑！");
                        return
                    }
                    salary_calc_result.userInfoModifyModalShow(this)
                });

            });

            var options = {
                bootstrapMajorVersion: 3, //版本  3是ul  2 是div
                //containerClass:"sdfsaf",
                //size: "small",//大小
                alignment: "left",//对齐方式
                currentPage: salary_calc_result.currentPage, //当前页数
                totalPages: salary_calc_result.totalPage, //总页数
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

                    salary_calc_result.currentPage = page;

                    //当前是 查询（查询）
                    if (salary_calc_manage.search_or_calc === "search") {
                        salary_calc_manage.salaryQuery();
                    }

                    //当前是 计算（导入）
                    if (salary_calc_manage.search_or_calc === "calc") {
                        salary_import.salaryCalc();
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

        salary_calc_result.isChooseAll();//

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

    },
    //是否 已经全部选择
    isChooseAll: function () {

        var $table_container = $salary_calc_container.find(".table_container");
        var $cur = $table_container.find("thead .choose_item");//thead choose_item
        var $item = $table_container.find("tbody .item");//tbody item
        // var $choose_item = $table_container.find(".choose_item");//table choose_item

        var chooseNo = 0;//选中的个数
        for (var i = 0; i < $item.length; i++) {
            if ($item.eq(i).hasClass("active")) { //如果 是选中的
                chooseNo += 1;
            }
        }

        //没有全部选中
        if (chooseNo === 0 || chooseNo < $item.length) {
            $cur.removeClass("active");
            $cur.find("img").attr("src", "img/icon_Unchecked.png");
        }
        else {
            $cur.addClass("active");
            $cur.find("img").attr("src", "img/icon_checked.png");
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

        var $item = $(self).closest(".item");
        salary_calc_result.user_id = $item.attr("data-userId");
        var user_name = $item.find(".user_name div").text();
        var user_phone_no = $item.find(".user_phone_no div").text();
        var user_idCard_no = $item.find(".user_idCard_no div").text();
        var user_bank_account = $item.find(".user_bank_account div").text();
        var user_bank_address = $item.find(".user_bank_address div").text();

        $user_info_modal.modal("show");

        var $row = $user_info_modal.find(".modal-body .row");
        $row.find('.user_name').val(user_name);
        $row.find('.user_phone_no').val(user_phone_no);
        $row.find('.user_idCard_no').val(user_idCard_no);
        $row.find('.user_bank_account').val(user_bank_account);
        $row.find('.user_bank_address').val(user_bank_address);

    },
    //更新用户信息
    userInfoModify: function () {
        loadingInit();

        var $row = $user_info_modal.find(".modal-body .row");
        var userName = $row.find('.user_name').val();
        var phoneNo = $row.find('.user_phone_no').val();
        var idCardNo = $row.find('.user_idCard_no').val();
        var bankAccount = $row.find('.user_bank_account').val();
        var user_bank_address = $row.find('.user_bank_address').val();

        var obj = {
            userId: salary_calc_result.user_id,
            userName: userName,
            phoneNo: phoneNo,
            idCardNo: idCardNo,
            bankAccount: bankAccount,
            bankName: user_bank_address
        };

        aryaPostRequest(
            urlGroup.salary_user_info_modify,
            obj,
            function (data) {
                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("修改" + userName + "的信息成功!");
                    $user_info_modal.modal("hide");

                    $salary_calc_container.find("tbody tr").each(function () {

                        var $this = $(this);

                        var id = $this.attr("data-userId");
                        if (id === salary_calc_result.user_id) {
                            $this.find(".user_name div").text(userName);
                            $this.find(".user_phone_no div").text(phoneNo);
                            $this.find(".user_idCard_no div").text(idCardNo);
                            $this.find(".user_bank_account div").text(bankAccount);
                            $this.find(".user_bank_address div").text(user_bank_address);
                            $this.find(".user_bank_address div").attr("title", user_bank_address);
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

        var $table = $salary_calc_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find("tr.active").length;
        if (length <= 0) {
            toastr.warning("您没有选择数据！");
            return
        }

        delWarning(
            "确定要删除选中的薪资计算结果吗？",
            null,
            function () {

                loadingInit();

                var ids = [];
                var $item = $tbody.find("tr.active");
                for (var i = 0; i < $item.length; i++) {
                    var id = $item.eq(i).attr("data-id");
                    ids.push(id);
                }

                var obj = {
                    customerId: salary_calc_param.currentTreeNode.id,
                    year: salary_calc_param.year,
                    month: salary_calc_param.month,
                    week: isNaN(parseInt(salary_calc_param.batch_no)) ? null : parseInt(salary_calc_param.batch_no),
                    salaryIds: ids
                };

                aryaPostRequest(
                    urlGroup.salary_calc_result_del,
                    obj,
                    function (data) {
                        //alert(JSON.stringify(data))

                        if (data.code === RESPONSE_OK_CODE) {
                            toastr.success("删除成功！");

                            salary_calc_result.clearChooseItem();//清除 选择状态
                            salary_calc_manage.salaryQuery();//薪资 查询

                        }
                        else {
                            toastr.warning(data.msg);
                        }

                    },
                    function () {
                        toastr.error("系统错误，请联系管理员！");
                    }
                );

            }
        );

    },
    //薪资计算结果 导出
    salaryCalcResultExport: function () {

        //检查 参数
        if (!salary_calc_manage.checkParamBySearch()) {
            return
        }

        var $table = $salary_calc_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find(".item").length;
        if (length <= 0) {
            toastr.warning("暂无可导出的数据！");
            return
        }

        operateShow(
            "确认导出薪资计算结果？",
            null,
            function () {

                salary_calc_manage.exportSalary(1);//公共接口 导出 - 薪资计算结果

            }
        );

    }


};

//薪资 统计结果
var salary_statistics_result = {

    //初始化 薪资统计结果
    initSalaryStatisticsResult: function () {

        salary_statistics_result.clearSalaryStatisticsResult();//清空 薪资统计结果

    },
    //清空 薪资统计结果
    clearSalaryStatisticsResult: function () {

        //清空 薪资统计结果
        var $tbody = $salary_statistic_container.find(".content .table_container").find("table tbody");
        var msg = "<tr><td colspan='8'>暂无统计结果</td></tr>";
        $tbody.empty().html(msg);

        salary_statistics_result.hideBtnInSalaryStatisticsResult();//隐藏 操作按钮 - 薪资统计结果
    },
    //隐藏 操作按钮 - 薪资统计结果
    hideBtnInSalaryStatisticsResult: function () {

        var $statistic_foot = $salary_statistic_container.find(".foot");
        $statistic_foot.hide();

    },
    //显示 操作按钮 - 薪资统计结果
    showBtnInSalaryStatisticsResult: function () {

        var $statistic_foot = $salary_statistic_container.find(".foot");
        $statistic_foot.show();

    },

    //显示 薪资统计结果
    showStatisticsResult: function (data) {

        salary_statistics_result.clearSalaryStatisticsResult();//清空 薪资统计结果

        var $table_container = $salary_statistic_container.find(".content .table_container");
        var $tbody = $table_container.find("table tbody");

        if (data && data.length > 0) {
            var list = "";

            for (var i = 0; i < data.length; i++) {
                var item = data[i];

                var districtName = item.districtName ? item.districtName : "";//城市
                var corpName = item.corpName ? item.corpName : "";//公司
                var staffCount = item.staffCount ? item.staffCount : "";//人数
                var taxableSalaryTotal = item.taxableSalaryTotal ? item.taxableSalaryTotal : "";//税前薪资总额
                var personalTaxTotal = item.personalTaxTotal ? item.personalTaxTotal : "";//个税处理费总额
                var netSalaryTotal = item.netSalaryTotal ? item.netSalaryTotal : "";//税后薪资总额
                var brokerageTotal = item.brokerageTotal ? item.brokerageTotal : "";//薪资服务费

                list +=
                    "<tr class='item'>" +
                    "<td><div>" + districtName + "</div></td>" +
                    "<td><div>" + corpName + "</div></td>" +
                    "<td><div>" + staffCount + "</div></td>" +
                    "<td><div>" + taxableSalaryTotal + "</div></td>" +
                    "<td><div>" + personalTaxTotal + "</div></td>" +
                    "<td><div>" + netSalaryTotal + "</div></td>" +
                    "<td><div>" + brokerageTotal + "</div></td>" +
                    "</tr>";

            }

            $tbody.html(list);

            //如果是 查询，则显示 操作按钮
            if (salary_calc_manage.search_or_calc === "search") {
                salary_statistics_result.showBtnInSalaryStatisticsResult();//显示 操作按钮 - 薪资统计结果
            }

        }

    },
    //薪资统计结果 导出
    salaryStatisticsResultExport: function () {

        //检查 参数
        if (!salary_calc_manage.checkParamBySearch()) {
            return
        }

        var $table = $salary_statistic_container.find(".content .table_container").find("table");
        var $tbody = $table.find("tbody");

        var length = $tbody.find(".item").length;
        if (length <= 0) {
            toastr.warning("暂无可导出的数据！");
            return
        }

        var $export_type = $salary_statistic_container.find(".btn_list").find(".export_type");
        var $export_type_val = $export_type.val();
        var $export_type_text = $export_type.find("option:selected").text();
        var title = "确认要" + $export_type_text;


        operateShow(
            title,
            null,
            function () {

                salary_calc_manage.exportSalary($export_type_val);//公共接口 导出

            }
        );

    },

    //确认扣款
    salaryDeduct: function () {

        var obj = {
            customerId: salary_calc_param.currentTreeNode.id,
            year: parseInt(salary_calc_param.year),
            month: parseInt(salary_calc_param.month),
            week: isNaN(parseInt(salary_calc_param.batch_no)) ? 0 : parseInt(salary_calc_param.batch_no)
        };

        operateShow(
            "确认要扣款？",
            null,
            function () {

                loadingInit();

                aryaPostRequest(
                    urlGroup.salary_deduct_sure,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("扣款成功！");

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
    //进入台账 页面
    goLedgerPage: function () {

        //检查 参数
        if (!salary_calc_param.currentTreeNode || !salary_calc_param.currentTreeNode.id) {
            toastr.warning("请选择客户公司！");
            return
        }

        sessionStorage.setItem("corp_id", salary_calc_param.currentTreeNode.id);

        var name = salary_calc_param.currentTreeNode.name;
        var pageName = name + "公司的台账详情";
        var tabId = "ledger_manage_" + salary_calc_param.currentTreeNode.id;//tab中的id

        getInsidePageDiv(urlGroup.ledger_manage_page, tabId, pageName);

    }

};

$(document).ready(function () {

    salary_calc_manage.organizationTreeList();//初始化 组织树列表

    //初始化 薪资计算
    salary_calc_manage.init();

});

