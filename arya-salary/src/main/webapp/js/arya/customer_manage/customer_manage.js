/**
 * Created by Administrator on 2017/7/3.
 */

var $customer_info_container = $(".customer_info_container");//客户管理 container
var $salary_rule_container = $customer_info_container.find(".salary_rule_container");//薪资计算规则 container
var $rule_info_container = $salary_rule_container.find(".rule_info_container");//薪资计算规则详情 container
var $customer_operate_record_modal = $(".customer_operate_record_modal");//跟进记录 弹框
var $account_recharge_modal = $(".account_recharge_modal");//账户充值 弹框
var $contract_info_modal = $(".contract_info_modal");//上传合同 弹框

var myDropzone;//上传文件

//客户信息
var customer_manage = {

    currentTreeNode: "",//选中的 公司

    init: function () {
        customer_manage.currentTreeNode = "";

        customer_manage.initCustomerInfo();//初始化 客户信息

        customer_manage.initContractUpload();//上传合同 初始化
        customer_manage.initContractImgList();//上传合同 合同图片列表

        salary_calc_rule.clearRule();//清空 计算规则

        //账户充值 弹框 初始化
        $account_recharge_modal.on("show.bs.modal", function () {

            // $account_recharge_modal.find(".recharge_date").val("");
            $account_recharge_modal.find(".recharge_money").val("");

            // $account_recharge_modal.find(".recharge_date").bind("focus", function () {
            //
            //     //合同期限 时间初始化
            //     var recharge_date = {
            //         elem: "#recharge_date",
            //         event: 'focus', //触发事件
            //         format: 'YYYY-MM-DD',
            //         min: "", //设定最小日期为当前日期
            //         max: '', //最大日期
            //         istime: false,//是否开启时间选择
            //         istoday: false, //是否显示今天
            //         choose: function (datas) {
            //             //end.min = datas; //开始日选好后，重置结束日的最小日期
            //             //end.start = datas;//将结束日的初始值设定为开始日
            //         }
            //     };
            //     laydate(recharge_date);
            //
            // });

        });

        //上传合同 弹框 初始化
        $contract_info_modal.on("show.bs.modal", function () {
            // console.log(window.Dropzone)

            // $contract_info_modal.find(".dropzone")
            // myDropzone.enable();

            // console.log(myDropzone.getQueuedFiles());//所有排队的文件
            // console.log(myDropzone.getUploadingFiles());

            myDropzone.options.headers = {
                "customerId": customer_manage.currentTreeNode.id
            };

        });
        //上传合同 弹框 - 隐藏
        $contract_info_modal.on("hidden.bs.modal", function () {
            myDropzone.removeAllFiles();//移除文件
        });

    },
    //初始化 客户信息
    initCustomerInfo: function () {

        customer_manage.initTime();//初始化 时间插件
        customer_manage.initInvoiceType();//初始化 发票类型
        customer_manage.initInvoiceProject();//初始化 发票项目

        customer_manage.clearCustomerInfo();//清空 客户信息

    },
    //初始化 时间插件
    initTime: function () {

        var $row = $customer_info_container.find(".customer_info > .row");

        var start = {
            elem: '#customer_contract_begin',
            format: 'YYYY/MM/DD',
            // min: laydate.now(), //设定最小日期为当前日期
            // max: '2099-06-16 23:59:59', //最大日期
            istime: false,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas;//将结束日的初始值设定为开始日

                var time = new Date(datas).getTime();
                $row.find(".customer_contract_begin").attr("data-time", time);
            }
        };
        var end = {
            elem: '#customer_contract_end',
            format: 'YYYY/MM/DD',
            // min: laydate.now(),
            // max: '2099-06-16 23:59:59',
            istime: false,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，重置开始日的最大日期

                var time = new Date(datas).getTime();
                $row.find(".customer_contract_end").attr("data-time", time);

            }
        };
        laydate(start);
        laydate(end);

    },
    //初始化 发票类型
    initInvoiceType: function () {

        var $row = $customer_info_container.find(".customer_info .row");
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

        var $row = $customer_info_container.find(".customer_info .row");

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

    //解禁 客户信息 操作
    enableOperate: function () {
        var $customer_info = $customer_info_container.find(".customer_info");
        var $txtInfo = $customer_info.find(".txtInfo");

        $txtInfo.find("input").removeAttr("disabled");
        $txtInfo.find("select").removeAttr("disabled");

    },
    //禁用 客户信息 操作
    disableOperate: function () {
        var $customer_info = $customer_info_container.find(".customer_info");
        var $txtInfo = $customer_info.find(".txtInfo");

        $txtInfo.find("input").attr("disabled", "disabled");
        $txtInfo.find("select").attr("disabled", "disabled");

    },

    //清空 客户信息
    clearCustomerInfo: function () {

        var $txtInfo = $customer_info_container.find(".customer_info").find(".txtInfo");
        $txtInfo.find("input").val("");
        $txtInfo.find("select").val("1");

        customer_manage.disableOperate();//禁用 操作

    },
    //客户信息 获取
    customerInfo: function () {
        customer_manage.clearCustomerInfo();//清空 客户信息

        var $customer_info = $customer_info_container.find(".customer_info");

        var obj = {
            id: customer_manage.currentTreeNode.id
        };
        var url = urlGroup.customer_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;
                    var id = $item.id ? $item.id : "";//

                    var customerName = $item.customerName ? $item.customerName : "";//
                    var linkMan = $item.linkMan ? $item.linkMan : "";//
                    var telphone = $item.telphone ? $item.telphone : "";//
                    var eMail = $item.eMail ? $item.eMail : "";//
                    var sales_man = $item.salesMan ? $item.salesMan : "";//

                    var billType = $item.billType ? $item.billType : "";//
                    var billProjectOne = $item.billProjectOne ? $item.billProjectOne : "";//
                    var billProjectTwo = $item.billProjectTwo ? $item.billProjectTwo : "";//
                    var address = $item.address ? $item.address : "";//
                    var sales_dept = $item.salesDepartment ? $item.salesDepartment : "";//

                    var contractDateStart = $item.contractDateStart ? $item.contractDateStart : "";//
                    contractDateStart = timeInit(contractDateStart);
                    var contractDateEnd = $item.contractDateEnd ? $item.contractDateEnd : "";//
                    contractDateEnd = timeInit(contractDateEnd);
                    // var contract_url = $item.contractUrl ? $item.contractUrl : "";//合同地址
                    // var contract_img_url = $item.contractDir ? $item.contractDir : "";// 合同路径

                    $customer_info.find(".customer_name").val(customerName);
                    $customer_info.find(".contact_name").val(linkMan);
                    $customer_info.find(".contact_phone").val(telphone);
                    $customer_info.find(".email").val(eMail);
                    $customer_info.find(".sales_man").val(sales_man);
                    $customer_info.find(".customer_contract_begin").val(contractDateStart);

                    $customer_info.find(".invoice_type").val(billType);
                    $customer_info.find(".invoice_project_1").val(billProjectOne);
                    $customer_info.find(".invoice_project_2").val(billProjectTwo);
                    $customer_info.find(".address").val(address);
                    $customer_info.find(".sales_dept").val(sales_dept);
                    $customer_info.find(".customer_contract_end").val(contractDateEnd);

                    // //如果有  合同
                    // if (contract_url) {
                    //
                    //     var $contract_info_container = $customer_info_container.find(".contract_info_container");
                    //     $contract_info_container.attr("data-url", contract_url);
                    //
                    //     //如果 有合同图片
                    //     if (contract_img_url) {
                    //         $contract_info_container.find(".contract_img_url img").attr("src", contract_img_url);
                    //     }
                    //
                    // }

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

    //查看 详情
    customerDetail: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        sessionStorage.setItem("corp_id", customer_manage.currentTreeNode.id);

        var name = customer_manage.currentTreeNode.name;
        var pageName = name + "公司的客户详情";
        var tabId = "customer_detail_" + customer_manage.currentTreeNode.id;//tab中的id

        getInsidePageDiv(urlGroup.customer_detail_page, tabId, pageName);

    },


    //上传合同 初始化
    initContractUpload: function () {

        myDropzone = new Dropzone(".dropzone", {
            url: urlGroup.upload_contract,
            method: "post",
            uploadMultiple: true,           //是否 Dropzone 应该在一个请求中发送多个文件


            addRemoveLinks: true,
            autoProcessQueue: false,         //是否自动调用接口，上传文件
            // maxFiles: 2,                        //设置最多上传文件 数量
            parallelUploads: 10,                 //有多少文件将上载到并行处理
            // maxFilesize: 2,                 //以MB为单位[译者注：上传文件的大小限制]
            // previewsContainer:"#adds", //显示的容器
            // acceptedFiles: ".jpg", //上传的类型  ,.gif,.png
            uploadMultiple: true,               //是否 Dropzone 应该在一个请求中发送多个文件

            //提示文本
            dictDefaultMessage: "请选择图片",
            dictCancelUpload: "取消",                //如果addRemoveLinks为 true，这段文本用来设置取消上载链接的文本
            dictCancelUploadConfirmation: "确认取消",    //如果addRemoveLinks为 true，这里设置的文本将用于确认取消上载时显示.
            dictRemoveFile: "删除",                  //如果addRemoveLinks为 true，这段文本用来设置删除文件显示文本.
            dictMaxFilesExceeded: "文件数量超过限制",            //如果设置了maxFiles ，这里设置的文本将在文件超出maxfiles设置值时显示.

            dictResponseError: '文件上传失败!',
            dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.gif,*.png。",
            dictFallbackMessage: "浏览器不受支持",
            dictFileTooBig: "文件大小超过上传文件最大限制."

        });

        //上传文件时触发的事件
        myDropzone.on("addedfile", function (file) {
            // console.info("addedfile");
            // console.log(file);
        });

        //删除文件时触发的方法
        myDropzone.on("removedfile", function (file) {
            // console.info("removedfile");
            // console.log(file);
        });

        //上传完成后触发的方法
        myDropzone.on("queuecomplete", function (file) {

            // console.info("queuecomplete");
            // console.log(file);


        });

        //上传失败 后触发的方法
        myDropzone.on("error", function (file, data) {
            console.info("上传失败：");
            // console.log(file);
            console.log(data);

            // toastr.success("上传失败！");
            // $contract_info_modal.modal("hide");
        });

        //多图片上传成功后 触发
        myDropzone.on("successmultiple", function (file, data) {
            // console.log("successmultiple");
            // console.log(file);
            // console.log(data);

            if (data.code === RESPONSE_OK_CODE) {

                toastr.success("上传完成！");
                $contract_info_modal.modal("hide");

                customer_manage.contractImgList();//获取 合同图片列表

            }
            else {
                toastr.warning(data.msg);
            }

        });

    },
    //上传合同 弹框显示
    contractModalShow: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        //检查 是否有薪资规则
        if (!salary_calc_rule_param.rule_type) {
            toastr.warning("请先选择薪资规则！");
            return
        }

        $contract_info_modal.modal("show");

    },
    //上传合同
    contractUpload: function () {

        // console.log(myDropzone.getRejectedFiles());//所有被拒绝的文件
        // console.log(myDropzone.getQueuedFiles());//所有排队的文件
        // console.log(myDropzone.getRejectedFiles());//所有被拒绝的文件

        var errorFile = myDropzone.getRejectedFiles();//所有被拒绝的文件
        if (errorFile.length > 0) {
            toastr.warning("有部分文件有问题，不能上传！");
            return
        }

        var queuedFile = myDropzone.getQueuedFiles();//所有排队的文件
        if (queuedFile.length <= 0) {
            toastr.warning("请选择文件！");
            return
        }

        myDropzone.processQueue();//上传文件
    },
    //下载文件
    tempDown: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        var $contract_info_container = $customer_info_container.find(".contract_info_container");
        var item = $contract_info_container.find(".contract_img_list").find(".item");

        if (item.length > 0) {

            var obj = {
                customerId: customer_manage.currentTreeNode.id
            };
            var url = urlGroup.contract_down + "?" + jsonParseParam(obj);

            loadingInit();

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
                        //console.log("获取日志-----error：");
                        //console.log(data.msg);

                        toastr.warning(data.msg);
                    }
                },
                function (error) {
                    messageCue(error);
                }
            );

        }
        else {
            toastr.warning("暂无文件可下载");
        }

    },

    //初始化 合同图片列表
    initContractImgList: function () {

        var $contract_info_container = $customer_info_container.find(".contract_info_container");
        $contract_info_container.find(".contract_img_list").empty();

    },
    //获取合同 图片列表
    contractImgList: function () {

        var obj = {
            customerId: customer_manage.currentTreeNode.id
        };
        var url = urlGroup.contract_img_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var arr = data.result ? data.result : [];

                    var $contract_info_container = $customer_info_container.find(".contract_info_container");
                    var $img_list = $contract_info_container.find(".contract_img_list");
                    $img_list.empty();

                    $.each(arr, function (index, $item) {

                        var id = $item.id ? $item.id : "";//
                        var url = $item.dir ? $item.dir : "";//

                        //合同item
                        var $img_item = $("<div>");
                        $img_item.appendTo($img_list);
                        $img_item.addClass("item");
                        $img_item.attr("data-id", id);
                        //合同图片
                        var $img = $("<img>");
                        $img.appendTo($img_item);
                        $img.attr("src", url);
                        //删除 icon
                        var $btn_del = $("<img>");
                        $btn_del.appendTo($img_item);
                        $btn_del.addClass("img_del");
                        $btn_del.attr("src", "img/icon_setting_dept_del_active.png");
                        $btn_del.unbind("click").bind("click", function () {
                            customer_manage.contractImgDel(this);
                        });

                    });

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
    //合同图片 删除
    contractImgDel: function (self) {
        var $self = $(self).closest(".item");

        var obj = {
            customerId: customer_manage.currentTreeNode.id,
            contractId: $self.attr("data-id")
        };

        aryaPostRequest(
            urlGroup.contract_img_del,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("删除成功！");
                    $self.remove();
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

    //账户充值 弹框显示
    accountRechargeModalShow: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        //检查 是否有薪资规则
        if (!salary_calc_rule_param.rule_type) {
            toastr.warning("请先选择薪资规则！");
            return
        }

        $account_recharge_modal.modal("show");

    },
    //账户充值
    accountRecharge: function () {

        //检查 充值参数
        if (!customer_manage.checkParamByRecharge()) {
            return;
        }

        var obj = {
            customerId: customer_manage.currentTreeNode.id,
            // rechargeDate: new Date($account_recharge_modal.find(".recharge_date").val()).getTime(),
            money: $account_recharge_modal.find(".recharge_money").val()
        };

        aryaPostRequest(
            urlGroup.customer_recharge,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {


                    toastr.success("充值成功！");
                    $account_recharge_modal.modal("hide");


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
    //检查 充值参数
    checkParamByRecharge: function () {
        var flag = false;
        var txt;

        var $row = $account_recharge_modal.find(".modal-body .row");
        // var $time = $row.find(".recharge_date").val();
        var $money = $row.find(".recharge_money").val();

        // if (!$time) {
        //     txt = "请输入日期！";
        // }
        // else
        if (!$money) {
            txt = "请输入金额！";
        }

        if (txt) {
            toastr.warning(txt);
            flag = false;
        }
        else {
            flag = true;
        }

        return flag;

    },

    //台账页面
    goLedgerPage: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        sessionStorage.setItem("corp_id", customer_manage.currentTreeNode.id);

        var name = customer_manage.currentTreeNode.name;
        var pageName = name + "公司的台账详情";
        var tabId = "ledger_manage_" + customer_manage.currentTreeNode.id;//tab中的id

        getInsidePageDiv(urlGroup.ledger_manage_page, tabId, pageName);

    },


    //组织树的点击事件
    groupTreeOnclick: function (node) {
        //console.log(node);
        //salaryCalculate.currentTreeNode = node;
        customer_manage.currentTreeNode = node;//
        //salaryCalculate.organizationId = salaryCalculate.currentTreeNode["id"];
        //salaryCalculate.organizationType = salaryCalculate.currentTreeNode["type"];

        loadingInit();

        customer_manage.customerInfo();//客户信息 获取
        customer_manage.contractImgList();//获取 合同图片列表
        salary_calc_rule.ruleInfo();//获取 计算规则


    },
    //检查 是否选择了公司
    checkIsChooseCom: function () {
        var flag = false;

        if (!customer_manage.currentTreeNode.id) {
            toastr.warning("请先选择客户公司！");
        }
        else {
            flag = true;
        }

        return flag;

    },

    //客户列表（组织树列表）
    organizationTreeList: function () {

        var obj = {
            condition: $("#customer_search").val()
        };
        var url = urlGroup.customer_list_tree_url + "?" + jsonParseParam(obj);

        //初始化 树结构
        initOrganizationTree(
            "#customer_group_tree",
            url,
            customer_manage.groupTreeOnclick
        );

    },
    //enter键 查询列表
    enterSearch: function (e) {

        var ev = document.all ? window.event : e;
        if (ev.keyCode === 13) {
            customer_manage.organizationTreeList();//初始化 组织树列表
        }

    }

};
//客户信息 参数
var customer_manage_param = {

    customerName: null,
    linkMan: null,
    telphone: null,
    eMail: null,
    salesMan: null,

    billType: null,
    billProjectOne: null,
    billProjectTwo: null,
    address: null,
    salesDepartment: null,

    contarctDateStart: null,
    contractDateEnd: null,

    paramSet: function () {

        var $row = $customer_info_container.find(".customer_info .row");

        customer_manage_param.customerName = $row.find(".customer_name").val();
        customer_manage_param.linkMan = $row.find(".contact_name").val();
        customer_manage_param.telphone = $row.find(".contact_phone").val();
        customer_manage_param.eMail = $row.find(".email").val();
        customer_manage_param.salesMan = $row.find(".sales_man").val();
        customer_manage_param.contarctDateStart = $row.find(".customer_contract_begin").val();
        customer_manage_param.contarctDateStart = customer_manage_param.contarctDateStart
            ? new Date(customer_manage_param.contarctDateStart).getTime() : "";

        customer_manage_param.billType = $row.find(".invoice_type").val();
        customer_manage_param.billProjectOne = $row.find(".invoice_project_1").val();
        customer_manage_param.billProjectTwo = $row.find(".invoice_project_2").val();
        customer_manage_param.address = $row.find(".address").val();
        customer_manage_param.salesDepartment = $row.find(".sales_dept").val();
        customer_manage_param.contractDateEnd = $row.find(".customer_contract_end").val();
        customer_manage_param.contractDateEnd = customer_manage_param.contractDateEnd
            ? new Date(customer_manage_param.contractDateEnd).getTime() : "";

    }

};

//跟进记录
var customer_record = {

    //清空 跟进记录
    clearRecord: function () {
        var $table_container = $customer_info_container.find(".opt_record_container").find(".table_container");
        var $tbody = $table_container.find("table tbody");
        var msg = "<tr><td colspan='3'>暂无跟进记录</td></tr>";

        $tbody.html(msg);

    },
    //跟进记录 获取
    recordList: function () {
        customer_record.clearRecord(); //清空 跟进记录

        var $table_container = $customer_info_container.find(".opt_record_container").find(".table_container");

        var obj = {
            id: customer_manage.currentTreeNode.id
        };
        var url = urlGroup.record_list_in_customer_manage + "?" + jsonParseParam(obj);

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

    //跟进记录 弹框显示
    recordModalShow: function () {

        //检查 是否选择了公司
        if (!customer_manage.checkIsChooseCom()) {
            return;
        }

        $customer_operate_record_modal.modal("show");
        $customer_operate_record_modal.find(".record_content").val("");

    },
    //跟进记录 新增
    recordAdd: function () {

        var obj = {
            customerId: customer_manage.currentTreeNode.id,
            followInfo: $customer_operate_record_modal.find(".record_content").val()
        };

        aryaPostRequest(
            urlGroup.record_add_in_customer_manage,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    $customer_operate_record_modal.modal("hide");
                    toastr.success("添加成功！");

                    customer_record.recordList();//跟进记录 获取
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

//计算规则
var salary_calc_rule = {

    //清空 计算规则
    clearRule: function () {

        //移除 规则选中状态
        var $rule_type_container = $salary_rule_container.find(".rule_type_container");
        $rule_type_container.find(".rule_item").show();
        $rule_type_container.find(".rule_item input").removeAttr("checked");
        //隐藏 规则container
        $salary_rule_container.find(".rule_info_container").find(".calc_rule_container").hide();
        //隐藏 操作按钮
        $salary_rule_container.find(".btn_list").hide();


    },
    //获取 规则信息
    ruleInfo: function () {

        salary_calc_rule.clearRule();//清空 计算规则
        salary_calc_rule_param.initParam(); //初始化 参数
        salary_calc_rule_param_saved.initParam();//初始化 参数

        var obj = {
            customerId: customer_manage.currentTreeNode.id
        };
        var url = urlGroup.rule_in_customer_manage + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    if (data.result) {

                        salary_calc_rule_param_saved.paramSet(data.result);//赋值 参数

                        //如果 规则类型已经保存过
                        if (salary_calc_rule_param_saved.rule_type) {

                            var $rule_type_container = $salary_rule_container.find(".rule_type_container");
                            var rule_class = "." + salary_calc_rule_param_saved.rule_type;
                            var $item = $rule_type_container.find(rule_class).closest(".rule_item");
                            $item.show().siblings().hide();

                            switch (salary_calc_rule_param_saved.rule_type) {
                                case "custom_rule":
                                    salary_calc_rule.initCustomRule();//自定义 计算规则
                                    break;
                                case "normal_rule":
                                    salary_calc_rule.initNormalRule();//标准 计算规则
                                    break;
                                case "blueCollar_rule":
                                    salary_calc_rule.initBlueCollarRule();//蓝领 计算规则
                                    break;
                                case "general_rule":
                                    salary_calc_rule.initGeneralRule();//普通 计算规则
                                    break;
                                default:
                                    // toastr.warning("暂无计算规则");
                                    // salary_calc_rule.clearRule();//清空 计算规则
                                    break;
                            }

                        }

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

    //初始化 自定义计算规则
    initCustomRule: function () {

        // 检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            salary_calc_rule.initRuleInput();//初始化 选中规则input
            return
        }

        //参数类型、参数名称 赋值
        salary_calc_rule_param.initRuleTypeAndName("custom_rule", "自定义计算规则");

        var $custom_calc_rule_container = $rule_info_container.find(".custom_calc_rule_container");
        $custom_calc_rule_container.show().siblings(".calc_rule_container").hide();
        $custom_calc_rule_container.find(".tax_gears_list").empty();//计税档 置空
        $custom_calc_rule_container.find(".brokerage_rate").val("");//薪资服务费率 置空
        $custom_calc_rule_container.find(".brokerage_rate_type").val("company");//薪资服务费率 类型

        salary_calc_rule.initRuleInput();//初始化 选中规则input
        salary_calc_rule.initOperateBtn();//初始化 按钮

        //如果 该公司是 自定义计算规则，则赋值 自定义计算规则
        if (salary_calc_rule_param_saved.rule_type === salary_calc_rule_param.rule_type) {
            salary_calc_rule.customRuleSet();//赋值 自定义计算规则
        }

    },
    //赋值 自定义计算规则
    customRuleSet: function () {

        var $custom_calc_rule_container = $rule_info_container.find(".custom_calc_rule_container");
        $custom_calc_rule_container.find(".brokerage_rate").val(salary_calc_rule_param_saved.brokerage_rate);
        $custom_calc_rule_container.find(".brokerage_rate_type").val(salary_calc_rule_param_saved.cost_bearing);

        for (var i = 0; i < salary_calc_rule_param_saved.tax_gears.length; i++) {

            // var $tax_gears_item = salary_calc_rule_param_saved.tax_gears[i];
            // var gear = $tax_gears_item.gear;
            // var tax_rate = $tax_gears_item.tax_rate;

            //添加计税档
            salary_calc_rule.addTaxRate();

            // var $item = $custom_calc_rule_container.find(".tax_gears_list").find(".item").eq(i);
            // $item.find(".tax_gear").val(gear);
            // $item.find(".tax_rate").val(tax_rate);

        }

        for (var i = 0; i < salary_calc_rule_param_saved.tax_gears.length; i++) {

            // salary_calc_rule.addTaxRate();//添加计税档

            var $tax_gears_item = salary_calc_rule_param_saved.tax_gears[i];
            var gear = $tax_gears_item.gear;
            var tax_rate = $tax_gears_item.tax_rate;

            var $item = $custom_calc_rule_container.find(".tax_gears_list").find(".item").eq(i);
            $item.find(".tax_gear").val(gear);
            $item.find(".tax_rate").val(tax_rate);

        }

    },
    //保存 自定义计算规则
    customRuleSave: function () {

        //检查参数 - 自定义规则 保存
        if (!salary_calc_rule.checkParamByCustomerRule()) {
            return
        }

        //保存规则 通用
        salary_calc_rule.ruleSave("确定要保存自定义计算规则吗");

    },
    //检查参数 - 自定义规则 保存
    checkParamByCustomerRule: function () {
        salary_calc_rule_param.initRuleParam();//初始化 规则内容 参数 - （置空）

        var flag = false;
        var txt;

        var $custom_calc_rule_container = $rule_info_container.find(".custom_calc_rule_container");

        salary_calc_rule_param.brokerage_rate = $custom_calc_rule_container.find(".brokerage_rate").val();
        salary_calc_rule_param.cost_bearing = $custom_calc_rule_container.find(".brokerage_rate_type").val();

        salary_calc_rule_param.tax_gears = [];
        var empty_input_length = 0;//空input个数
        for (var i = 0; i < $custom_calc_rule_container.find(".tax_gears_list").find(".item").length; i++) {
            var $item = $custom_calc_rule_container.find(".tax_gears_list").find(".item").eq(i);

            var gear = $.trim($item.find(".tax_gear").val());
            if (!gear) {
                empty_input_length++;
                break;
            }
            var tax_rate = $.trim($item.find(".tax_rate").val());
            if (!tax_rate) {
                empty_input_length++;
                break;
            }

            var obj = {
                gear: gear,
                tax_rate: tax_rate
            };

            salary_calc_rule_param.tax_gears.push(obj);

        }


        if (empty_input_length > 0 || salary_calc_rule_param.tax_gears.length === 0) {
            txt = "请输入计税档信息！";
        }
        else if (!salary_calc_rule_param.brokerage_rate) {
            txt = "请输入薪资服务税率！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },
    //增加计税档输入框
    addTaxRate: function () {
        var $custom_calc_rule_container = $rule_info_container.find(".custom_calc_rule_container");
        //计税档 列表
        var taxGears = $custom_calc_rule_container.find(".tax_gears_list");

        var gearRow = $("#tax_gear_row").clone(true);
        gearRow.prependTo(taxGears);
        gearRow.removeAttr("hidden");
        gearRow.removeAttr("id");
        gearRow.find(".delete_tax_rate").unbind("click").bind("click", function () {
            // "salaryCalculate.deleteTaxRate('#" + id + "')"
            salary_calc_rule.deleteTaxRate(this);
        });

    },
    //删除计税档
    deleteTaxRate: function (self) {
        $(self).closest(".item").remove();
    },


    //初始化 标准计算规则
    initNormalRule: function () {

        // 检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            salary_calc_rule.initRuleInput();//初始化 选中规则input
            return
        }

        //参数类型、参数名称 赋值
        salary_calc_rule_param.initRuleTypeAndName("normal_rule", "标准计算规则");

        var $normal_calc_rule_container = $rule_info_container.find(".normal_calc_rule_container");
        $normal_calc_rule_container.show().siblings(".calc_rule_container").hide();
        $normal_calc_rule_container.find(".threshold_tax").val("");//起征点 置空
        $normal_calc_rule_container.find(".brokerage").val("");//薪资服务费 置空

        salary_calc_rule.initRuleInput();//初始化 选中规则input
        salary_calc_rule.initOperateBtn();//初始化 按钮

        //如果 该公司是 标准计算规则，则赋值 标准计算规则
        if (salary_calc_rule_param.rule_type === salary_calc_rule_param_saved.rule_type) {
            salary_calc_rule.normalRuleSet();//赋值 标准计算规则
        }

    },
    //赋值 标准计算规则
    normalRuleSet: function () {

        var $normal_calc_rule_container = $rule_info_container.find(".normal_calc_rule_container");

        $normal_calc_rule_container.find(".threshold_tax").val(salary_calc_rule_param_saved.threshold_tax);//起征点
        $normal_calc_rule_container.find(".brokerage").val(salary_calc_rule_param_saved.brokerage);//薪资服务费

    },
    //保存 标准计算规则
    normalRuleSave: function () {

        //检查参数 - 标准计算规则 保存
        if (!salary_calc_rule.checkParamByNormalRule()) {
            return
        }

        //保存规则 通用
        salary_calc_rule.ruleSave("确定要保存标准计算规则吗");

    },
    //检查参数 - 标准计算规则 保存
    checkParamByNormalRule: function () {
        salary_calc_rule_param.initRuleParam();//初始化 规则内容 参数 - （置空）

        var flag = false;
        var txt;

        var $normal_calc_rule_container = $rule_info_container.find(".normal_calc_rule_container");

        salary_calc_rule_param.threshold_tax = $normal_calc_rule_container.find(".threshold_tax").val();//起征点
        salary_calc_rule_param.brokerage = $normal_calc_rule_container.find(".brokerage").val();//薪资服务费

        if (!salary_calc_rule_param.threshold_tax) {
            txt = "请输入起征点！";
        }
        else if (!salary_calc_rule_param.brokerage) {
            txt = "请输入薪资服务费！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //初始化 蓝领计算规则
    initBlueCollarRule: function () {

        // 检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            salary_calc_rule.initRuleInput();//初始化 选中规则input
            return
        }

        //参数类型、参数名称 赋值
        salary_calc_rule_param.initRuleTypeAndName("blueCollar_rule", "蓝领计算规则");

        var $blueCollar_calc_rule_container = $rule_info_container.find(".blueCollar_calc_rule_container");
        $blueCollar_calc_rule_container.show().siblings(".calc_rule_container").hide();

        $blueCollar_calc_rule_container.find(".ill_sub_ratio").val("0");//病假扣款比例
        $blueCollar_calc_rule_container.find(".absence_sub_ratio").val("0");//非新进离职员工旷工扣款比例
        $blueCollar_calc_rule_container.find(".new_leave_absence_sub_ratio").val("0");//新进离职员工旷工扣款比例
        $blueCollar_calc_rule_container.find(".affair_sub_ratio").val("0");//事假扣款比例
        $blueCollar_calc_rule_container.find(".fullTime_bonus_fir").val("0");//全勤奖金：第一档
        $blueCollar_calc_rule_container.find(".fullTime_bonus_sec").val("0");//全勤奖金：第二档
        $blueCollar_calc_rule_container.find(".fullTime_bonus_three").val("0");//全勤奖金：第三档

        salary_calc_rule.initRuleInput();//初始化 选中规则input
        salary_calc_rule.initOperateBtn();//初始化 按钮

        //如果 该公司是 蓝领计算规则，则赋值 蓝领计算规则
        if (salary_calc_rule_param.rule_type === salary_calc_rule_param_saved.rule_type) {
            salary_calc_rule.blueCollarRuleSet();//赋值 蓝领计算规则
        }

    },
    //赋值 蓝领计算规则
    blueCollarRuleSet: function () {


        var $blueCollar_calc_rule_container = $rule_info_container.find(".blueCollar_calc_rule_container");

        $blueCollar_calc_rule_container.find(".ill_sub_ratio").val(salary_calc_rule_param_saved.ill_sub_ratio);//病假扣款比例
        $blueCollar_calc_rule_container.find(".absence_sub_ratio").val(salary_calc_rule_param_saved.absence_sub_ratio);//非新进离职员工旷工扣款比例
        $blueCollar_calc_rule_container.find(".new_leave_absence_sub_ratio").val(salary_calc_rule_param_saved.new_leave_absence_sub_ratio);//新进离职员工旷工扣款比例
        $blueCollar_calc_rule_container.find(".affair_sub_ratio").val(salary_calc_rule_param_saved.affair_sub_ratio);//事假扣款比例

        var fu = salary_calc_rule_param_saved.fulltime_bonu_list;

        //全勤奖 一档
        $blueCollar_calc_rule_container.find(".fullTime_bonus_fir").val(fu[0].bonu);//事假扣款比例
        $blueCollar_calc_rule_container.find(".fullTime_bonus_sec").val(fu[1].bonu);//事假扣款比例
        $blueCollar_calc_rule_container.find(".fullTime_bonus_three").val(fu[2].bonu);//事假扣款比例


    },
    //保存 蓝领计算规则
    blueCollarRuleSave: function () {

        //检查参数 - 蓝领计算规则 保存
        if (!salary_calc_rule.checkParamByBlueCollarRule()) {
            return
        }

        //保存规则 通用
        salary_calc_rule.ruleSave("确定要保存蓝领计算规则吗");

    },
    //检查参数 - 蓝领计算规则 保存
    checkParamByBlueCollarRule: function () {

        salary_calc_rule_param.initRuleParam();//初始化 规则内容 参数 - （置空）

        var flag = false;
        var txt;

        var $blueCollar_calc_rule_container = $rule_info_container.find(".blueCollar_calc_rule_container");

        //蓝领规则
        salary_calc_rule_param.ill_sub_ratio = $blueCollar_calc_rule_container.find(".ill_sub_ratio").val();//病假扣款比例
        salary_calc_rule_param.absence_sub_ratio = $blueCollar_calc_rule_container.find(".absence_sub_ratio").val();//非新进离职员工旷工扣款比例
        salary_calc_rule_param.new_leave_absence_sub_ratio = $blueCollar_calc_rule_container.find(".new_leave_absence_sub_ratio").val();//新进离职员工旷工扣款比例
        salary_calc_rule_param.affair_sub_ratio = $blueCollar_calc_rule_container.find(".affair_sub_ratio").val();//事假扣款比例
        salary_calc_rule_param.fulltime_bonu_list = [];//全勤奖金;
        var $fullTime_bonus = $blueCollar_calc_rule_container.find(".fullTime_bonus");
        for (var i = 0; i < $fullTime_bonus.length; i++) {
            var $this = $fullTime_bonus.eq(i);

            var level = $this.attr("data-level");
            var val = $this.find("input").val();
            if (!val) {
                txt = "请输入全勤奖金第" + level + "档！";
                break;
            }

            var obj = {
                leval: level,
                bonu: val
            };
            salary_calc_rule_param.fulltime_bonu_list.push(obj);

        }


        if (!salary_calc_rule_param.ill_sub_ratio) {
            txt = "请输入病假扣款比例！";
        }
        else if (!salary_calc_rule_param.absence_sub_ratio) {
            txt = "请输入非新进离职员工旷工扣款比例！";
        }
        else if (!salary_calc_rule_param.new_leave_absence_sub_ratio) {
            txt = "请输入新进离职员工旷工扣款比例！";
        }
        else if (!salary_calc_rule_param.affair_sub_ratio) {
            txt = "请输入事假扣款比例！";
        }

        if (txt) {
            toastr.warning(txt);
            flag = false;
        }
        else {
            flag = true;
        }

        return flag;

    },


    //初始化 普通计算规则
    initGeneralRule: function () {

        // 检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            salary_calc_rule.initRuleInput();//初始化 选中规则input
            return
        }

        //参数类型、参数名称 赋值
        salary_calc_rule_param.initRuleTypeAndName("general_rule", "普通计算规则");

        var $general_calc_rule_container = $rule_info_container.find(".general_calc_rule_container");
        $general_calc_rule_container.show().siblings(".calc_rule_container").hide();

        $general_calc_rule_container.find(".ill_sub_ratio").val("0");//病假扣款比例
        $general_calc_rule_container.find(".affair_sub_ratio").val("0");//事假扣款比例
        $general_calc_rule_container.find(".threshold_tax").val("0");//起征点
        $general_calc_rule_container.find(".brokerage").val("0");//起征点

        salary_calc_rule.initRuleInput();//初始化 选中规则input
        salary_calc_rule.initOperateBtn();//初始化 按钮

        //如果 该公司是 普通计算规则，则赋值 普通计算规则
        if (salary_calc_rule_param.rule_type === salary_calc_rule_param_saved.rule_type) {
            salary_calc_rule.generalRuleSet();//赋值 普通计算规则
        }
    },
    //赋值 普通计算规则
    generalRuleSet: function () {

        var $general_calc_rule_container = $rule_info_container.find(".general_calc_rule_container");

        $general_calc_rule_container.find(".ill_sub_ratio").val(salary_calc_rule_param_saved.ill_sub_ratio_ordinary);//病假扣款比例
        $general_calc_rule_container.find(".affair_sub_ratio").val(salary_calc_rule_param_saved.affair_sub_ratio_ordinary);//事假扣款比例
        $general_calc_rule_container.find(".threshold_tax").val(salary_calc_rule_param_saved.threshold_tax_ordinary);//起征点
        $general_calc_rule_container.find(".brokerage").val(salary_calc_rule_param_saved.brokerage_ordinary);//薪资服务费

    },
    //保存 普通计算规则
    generalRuleSave: function () {

        //检查参数 - 普通计算规则 保存
        if (!salary_calc_rule.checkParamByGeneralRule()) {
            return
        }

        //保存规则 通用
        salary_calc_rule.ruleSave("确定要保存普通计算规则吗");

    },
    //检查参数 - 普通计算规则 保存
    checkParamByGeneralRule: function () {
        salary_calc_rule_param.initRuleParam();//初始化 规则内容 参数 - （置空）

        var flag = false;
        var txt;

        var $general_calc_rule_container = $rule_info_container.find(".general_calc_rule_container");

        salary_calc_rule_param.ill_sub_ratio_ordinary = $general_calc_rule_container.find(".ill_sub_ratio").val();//病假扣款比例
        salary_calc_rule_param.affair_sub_ratio_ordinary = $general_calc_rule_container.find(".affair_sub_ratio").val();//事假扣款比例
        salary_calc_rule_param.threshold_tax_ordinary = $general_calc_rule_container.find(".threshold_tax").val();//起征点
        salary_calc_rule_param.brokerage_ordinary = $general_calc_rule_container.find(".brokerage").val();//薪资服务费

        if (!salary_calc_rule_param.ill_sub_ratio_ordinary) {
            txt = "请输入病假扣款比例！";
        }
        else if (!salary_calc_rule_param.affair_sub_ratio_ordinary) {
            txt = "请输入事假扣款比例！";
        }
        else if (!salary_calc_rule_param.threshold_tax_ordinary) {
            txt = "请输入起征点！";
        }
        else if (!salary_calc_rule_param.brokerage_ordinary) {
            txt = "请输入薪资服务费！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },


    //保存规则 通用
    ruleSave: function (txt) {

        var obj = {
            customer_id: customer_manage.currentTreeNode.id,
            rule_type: salary_calc_rule.ruleParamChange(salary_calc_rule_param.rule_type),
            rule_name: salary_calc_rule_param.rule_name,
            //自定义 计算规则
            tax_gears: salary_calc_rule_param.tax_gears,
            brokerage_rate: salary_calc_rule_param.brokerage_rate,
            cost_bearing: salary_calc_rule_param.cost_bearing,
            //标准 计算规则
            threshold_tax: salary_calc_rule_param.threshold_tax,
            brokerage: salary_calc_rule_param.brokerage,
            //蓝领 计算规则
            ill_sub_ratio: salary_calc_rule_param.ill_sub_ratio,
            absence_sub_ratio: salary_calc_rule_param.absence_sub_ratio,
            new_leave_absence_sub_ratio: salary_calc_rule_param.new_leave_absence_sub_ratio,
            affair_sub_ratio: salary_calc_rule_param.affair_sub_ratio,
            fulltime_bonu_list: salary_calc_rule_param.fulltime_bonu_list,
            //普通 计算规则
            ill_sub_ratio_ordinary: salary_calc_rule_param.ill_sub_ratio_ordinary,
            affair_sub_ratio_ordinary: salary_calc_rule_param.affair_sub_ratio_ordinary,
            threshold_tax_ordinary: salary_calc_rule_param.threshold_tax_ordinary,
            brokerage_ordinary: salary_calc_rule_param.brokerage_ordinary
        };

        operateShow(
            txt,
            null,
            function () {

                loadingInit();

                aryaPostRequest(
                    urlGroup.rule_save_in_customer_manage,
                    obj,
                    function (data) {
                        //console.log(data);
                        // debugger
                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("保存成功！");
                            salary_calc_rule.ruleInfo();    //获取 规则信息

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


    //初始化 选中规则input[type='radio']
    initRuleInput: function () {

        var $rule_type_container = $salary_rule_container.find(".rule_type_container");
        $rule_type_container.find("input").removeAttr("checked");

        if (salary_calc_rule_param.rule_type) {
            var rule_class = "." + salary_calc_rule_param.rule_type;
            $rule_type_container.find(rule_class).prop("checked", "checked");
        }

    },
    //初始化 按钮
    initOperateBtn: function () {

        $salary_rule_container.find(".btn_list").css("display", "block");

        //保存
        $salary_rule_container.find(".btn_save").unbind("click").bind("click", function () {

            var txt = rule_txt_saved();

            //如果 当前操作的规则是正确的，或者当前公司 没有规则
            if (salary_calc_rule_param.rule_type === salary_calc_rule_param_saved.rule_type
                || !salary_calc_rule_param_saved.rule_type) {

                switch (salary_calc_rule_param.rule_type) {
                    case "custom_rule":
                        salary_calc_rule.customRuleSave();
                        break;
                    case "normal_rule":
                        salary_calc_rule.normalRuleSave();
                        break;
                    case "blueCollar_rule":
                        salary_calc_rule.blueCollarRuleSave();
                        break;
                    case "general_rule":
                        salary_calc_rule.generalRuleSave();
                        break;
                    default:
                        break;
                }

            }
            else {
                txt = "当前公司规则是" + txt + "，您操作的规则无法保存！";
                toastr.warning(txt);
            }

        });

        //获取当前保存的规则
        var rule_txt_saved = function () {

            var txt;
            switch (salary_calc_rule_param_saved.rule_type) {
                case "custom_rule":
                    txt = "自定义计算规则";
                    break;
                case "normal_rule":
                    txt = "标准计算规则";
                    break;
                case "blueCollar_rule":
                    txt = "蓝领计算规则";
                    break;
                case "general_rule":
                    txt = "普通计算规则";
                    break;
                default:
                    break;
            }

            return txt;

        }

    },

    /**
     * 将 custom_rule 转换为 defined
     * 将 normal_rule 转换为 standard
     * 将 blueCollar_rule 转换为 humanPool
     * 将 general_rule 转换为 ordinary
     * */
    ruleParamChange: function (rule_type) {

        var type;

        switch (rule_type) {
            case "custom_rule":
                type = "defined";
                break;
            case "normal_rule":
                type = "standard";
                break;
            case "blueCollar_rule":
                type = "humanPool";
                break;
            case "general_rule":
                type = "ordinary";
                break;
            default:
                break;
        }

        return type;

    },

    /**
     * 将 defined 转换为    custom_rule
     * 将 standard 转换为    normal_rule
     * 将  humanPool 转换为  blueCollar_rule
     * 将     ordinary 转换为   general_rule
     * */
    ruleParamChange2: function (rule_type) {

        var type;

        switch (rule_type) {
            case "defined":
                type = "custom_rule";
                break;
            case "standard":
                type = "normal_rule";
                break;
            case "humanPool":
                type = "blueCollar_rule";
                break;
            case "ordinary":
                type = "general_rule";
                break;
            default:
                break;
        }

        return type;

    }

};
//计算规则(保存时) - 参数
var salary_calc_rule_param = {

    rule_type: null,   //当前操作的 规则类型,
    rule_name: null,// 薪资规则名称

    //自定义规则
    tax_gears: [],//计税档 数组
    brokerage_rate: "",//薪资服务税率
    cost_bearing: null,//薪资服务费付款方 = ['company', 'personal']

    //标准规则
    brokerage: "",//薪资服务费
    threshold_tax: "",//起征点

    //蓝领规则
    ill_sub_ratio: "",//病假扣款比例
    absence_sub_ratio: "",//非新进离职员工旷工扣款比例
    new_leave_absence_sub_ratio: "",//新进离职员工旷工扣款比例
    affair_sub_ratio: "",//事假扣款比例
    fulltime_bonu_list: [],//全勤奖

    //普通规则
    ill_sub_ratio_ordinary: null,//病假扣款比例
    affair_sub_ratio_ordinary: null,//事假扣款比例
    threshold_tax_ordinary: null,//起征点
    brokerage_ordinary: null,//薪资服务费

    //初始化 参数
    initParam: function () {

        salary_calc_rule_param.rule_type = null;
        salary_calc_rule_param.rule_name = null;

        salary_calc_rule_param.tax_gears = null;
        salary_calc_rule_param.brokerage_rate = null;
        salary_calc_rule_param.cost_bearing = null;

        salary_calc_rule_param.brokerage = null;
        salary_calc_rule_param.threshold_tax = null;

        salary_calc_rule_param.ill_sub_ratio = null;
        salary_calc_rule_param.absence_sub_ratio = null;
        salary_calc_rule_param.new_leave_absence_sub_ratio = null;
        salary_calc_rule_param.affair_sub_ratio = null;
        salary_calc_rule_param.fulltime_bonu_list = null;

        salary_calc_rule_param.ill_sub_ratio_ordinary = null;
        salary_calc_rule_param.affair_sub_ratio_ordinary = null;
        salary_calc_rule_param.threshold_tax_ordinary = null;
        salary_calc_rule_param.brokerage_ordinary = null;

    },

    //初始化 规则内容 参数 - （置空）
    initRuleParam: function () {

        //自定义规则
        salary_calc_rule_param.tax_gears = null;
        salary_calc_rule_param.brokerage_rate = null;
        salary_calc_rule_param.cost_bearing = null;

        //标准规则
        salary_calc_rule_param.brokerage = null;
        salary_calc_rule_param.threshold_tax = null;

        //蓝领规则
        salary_calc_rule_param.ill_sub_ratio = null;
        salary_calc_rule_param.absence_sub_ratio = null;
        salary_calc_rule_param.new_leave_absence_sub_ratio = null;
        salary_calc_rule_param.affair_sub_ratio = null;
        salary_calc_rule_param.fulltime_bonu_list = null;

        //普通规则
        salary_calc_rule_param.ill_sub_ratio_ordinary = null;
        salary_calc_rule_param.affair_sub_ratio_ordinary = null;
        salary_calc_rule_param.threshold_tax_ordinary = null;
        salary_calc_rule_param.brokerage_ordinary = null;

    },

    //参数类型、参数名称 赋值
    initRuleTypeAndName: function (type, name) {

        salary_calc_rule_param.rule_type = type;
        salary_calc_rule_param.rule_name = name;

    }

};
//计算规则(已保存) - 参数
var salary_calc_rule_param_saved = {

    //（该公司已经保存的，从接口获取，如果没有保存，则为null）
    rule_type: null,//规则类型  ['defined', 'standard', 'blue', 'ordinary']
    rule_name: null,// 薪资规则名称

    //自定义规则
    tax_gears: [],//计税档 数组
    brokerage_rate: "",//薪资服务税率
    cost_bearing: null,//薪资服务费付款方 = ['company', 'personal']

    //标准规则
    brokerage: "",//薪资服务费
    threshold_tax: "",//起征点

    //蓝领规则
    ill_sub_ratio: "",//病假扣款比例
    absence_sub_ratio: "",//非新进离职员工旷工扣款比例
    new_leave_absence_sub_ratio: "",//新进离职员工旷工扣款比例
    affair_sub_ratio: "",//事假扣款比例
    fulltime_bonu_list: [],//全勤奖

    //普通规则
    ill_sub_ratio_ordinary: null,//病假扣款比例
    affair_sub_ratio_ordinary: null,//事假扣款比例
    threshold_tax_ordinary: null,//起征点
    brokerage_ordinary: null,//薪资服务费

    //初始化 参数
    initParam: function () {

        salary_calc_rule_param_saved.rule_type = null;
        salary_calc_rule_param_saved.rule_name = null;

        salary_calc_rule_param_saved.tax_gears = null;
        salary_calc_rule_param_saved.brokerage_rate = null;
        salary_calc_rule_param_saved.cost_bearing = null;

        salary_calc_rule_param_saved.brokerage = null;
        salary_calc_rule_param_saved.threshold_tax = null;

        salary_calc_rule_param_saved.ill_sub_ratio = null;
        salary_calc_rule_param_saved.absence_sub_ratio = null;
        salary_calc_rule_param_saved.new_leave_absence_sub_ratio = null;
        salary_calc_rule_param_saved.affair_sub_ratio = null;
        salary_calc_rule_param_saved.fulltime_bonu_list = null;

        salary_calc_rule_param_saved.ill_sub_ratio_ordinary = null;
        salary_calc_rule_param_saved.affair_sub_ratio_ordinary = null;
        salary_calc_rule_param_saved.threshold_tax_ordinary = null;
        salary_calc_rule_param_saved.brokerage_ordinary = null;

    },
    //赋值 参数
    paramSet: function (result) {

        salary_calc_rule_param_saved.rule_type = salary_calc_rule.ruleParamChange2(result.rule_type);
        salary_calc_rule_param_saved.rule_name = result.rule_name ? result.rule_name : "";

        //自定义规则
        salary_calc_rule_param_saved.tax_gears = result.tax_gears ? result.tax_gears : [];
        salary_calc_rule_param_saved.brokerage_rate = result.brokerage_rate ? result.brokerage_rate : 0;
        salary_calc_rule_param_saved.cost_bearing = result.cost_bearing ? result.cost_bearing : "company";

        //标准规则
        salary_calc_rule_param_saved.brokerage = result.brokerage ? result.brokerage : 0;
        salary_calc_rule_param_saved.threshold_tax = result.threshold_tax ? result.threshold_tax : 0;

        //蓝领规则
        salary_calc_rule_param_saved.ill_sub_ratio = result.ill_sub_ratio ? result.ill_sub_ratio : 0;
        salary_calc_rule_param_saved.absence_sub_ratio = result.absence_sub_ratio ? result.absence_sub_ratio : 0;
        salary_calc_rule_param_saved.new_leave_absence_sub_ratio = result.new_leave_absence_sub_ratio ? result.new_leave_absence_sub_ratio : 0;
        salary_calc_rule_param_saved.affair_sub_ratio = result.affair_sub_ratio ? result.affair_sub_ratio : 0;
        salary_calc_rule_param_saved.fulltime_bonu_list = result.fulltime_bonu_list ? result.fulltime_bonu_list : [];

        //普通规则
        salary_calc_rule_param_saved.ill_sub_ratio_ordinary = result.ill_sub_ratio_ordinary ? result.ill_sub_ratio_ordinary : 0;
        salary_calc_rule_param_saved.affair_sub_ratio_ordinary = result.affair_sub_ratio_ordinary ? result.affair_sub_ratio_ordinary : 0;
        salary_calc_rule_param_saved.threshold_tax_ordinary = result.threshold_tax_ordinary ? result.threshold_tax_ordinary : 0;
        salary_calc_rule_param_saved.brokerage_ordinary = result.brokerage_ordinary ? result.brokerage_ordinary : 0;

    }


};

$(document).ready(function () {

    customer_manage.organizationTreeList();//客户列表（组织树列表）

    //初始化 客户管理
    customer_manage.init();

});

var debug = {

    //初始化 操作按钮
    initCustomOpeBtn: function () {

        var $customer_info = $customer_info_container.find(".customer_info");
        var $btn_list = $customer_info.find(".btn_list");

        $btn_list.find(".btn").hide();
        $btn_list.find(".btn_modify").show();
        $btn_list.find(".btn_detail").show();

    },
    //编辑
    customInfoModify: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        var $customer_info = $customer_info_container.find(".customer_info");
        var $btn_list = $customer_info.find(".btn_list");

        $btn_list.find(".btn").show();
        $btn_list.find(".btn_modify").hide();
        $btn_list.find(".btn_detail").hide();

        customer_manage.enableOperate();    //解禁 操作

    },
    //取消
    customInfoCancel: function () {

        customer_manage.initCustomOpeBtn();//初始化 操作按钮
        customer_manage.disableOperate();//禁用 操作
        customer_manage.customerInfo();////客户信息 获取

    },
    //保存
    customInfoSave: function () {

        customer_manage_param.paramSet();//参数赋值

        //检查 参数
        if (!customer_manage.checkParam()) {
            return;
        }

        var obj = {
            id: customer_manage.currentTreeNode.id,
            customerName: customer_manage_param.customerName,
            linkMan: customer_manage_param.linkMan,
            telphone: customer_manage_param.telphone,
            eMail: customer_manage_param.eMail,
            salesMan: customer_manage_param.salesMan,
            billType: customer_manage_param.billType,
            billProjectOne: customer_manage_param.billProjectOne,
            billProjectTwo: customer_manage_param.billProjectTwo,
            address: customer_manage_param.address,
            salesDepartment: customer_manage_param.salesDepartment,
            contarctDateStart: customer_manage_param.contarctDateStart,
            contractDateEnd: customer_manage_param.contractDateEnd
        };

        aryaPutRequest(
            urlGroup.customer_info_update,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    toastr.success("保存成功！");

                    customer_manage.customerInfo();//获取 客户信息

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

        if (!customer_manage_param.customerName) {
            txt = "客户名称不能为空！";
        }
        else if (!customer_manage_param.linkMan) {
            txt = "联系人不能为空！";
        }
        else if (!customer_manage_param.telphone) {
            txt = "联系方式不能为空！";
        }
        else if (!customer_manage_param.eMail) {
            txt = "邮箱不能为空！";
        }
        else if (!customer_manage_param.billType) {
            txt = "薪资总额不能为空！";
        }
        else if (!customer_manage_param.billProjectOne) {
            txt = "发票项目1不能为空！";
        }
        else if (!customer_manage_param.billProjectTwo) {
            txt = "发票项目2不能为空！";
        }
        else if (!customer_manage_param.address) {
            txt = "地址不能为空！";
        }
        else if (!customer_manage_param.contarctDateStart) {
            txt = "合同开始时间不能为空！";
        }
        else if (!customer_manage_param.contractDateEnd) {
            txt = "合同结束时间不能为空！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //初始化 上传合同 操作
    initContractOperate: function () {

        var $contract_info_container = $customer_info_container.find(".contract_info_container");

        $contract_info_container.attr("data-url", "");
        $contract_info_container.find(".contract_img_url img").attr("src", "img/empty_img.png");

    },
    //上传文件 - 按钮点击
    ChooseFileClick: function () {
        customer_manage.contractModalShow();//
        return
        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        var $contract_info_container = $customer_info_container.find(".contract_info_container");
        var $txtInfo = $contract_info_container.find(".txtInfo");

        if ($txtInfo.find(".upload_file").length > 0) {
            $txtInfo.find(".upload_file").remove();
        }

        var form = $("<form>");
        form.addClass("upload_file");
        form.attr("enctype", "multipart/form-data");
        form.appendTo($txtInfo);
        form.hide();

        var file_input = $("<input>");
        file_input.attr("type", "file");
        file_input.attr("name", "file");
        file_input.change(function () {
            customer_manage.ChooseFile(this);
        });
        file_input.appendTo(form);

        var type_input = $("<input>");
        type_input.attr("type", "text");
        type_input.attr("name", "customerId");
        type_input.val(customer_manage.currentTreeNode.id);
        type_input.appendTo(form);

        // //默认文件类型 为 html
        // var npt_2 = $("<input>");
        // npt_2.attr("type", "text");
        // npt_2.attr("name", "suffix");
        // npt_2.val("html");
        // npt_2.appendTo(form);

        file_input.click();

    },
    //选择文件 - 弹框显示
    ChooseFile: function (self) {
        var $contract_info_container = $customer_info_container.find(".contract_info_container");
        var $txtInfo = $contract_info_container.find(".txtInfo");

        //alert(self.files)
        if (self.files) {
            for (var i = 0; i < self.files.length; i++) {
                var file = self.files[i];

                //判断是否是图片格式
                if (/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG|BMP)$/.test(file.name)) {

                    $txtInfo.find(".upload_file").ajaxSubmit({
                        url: urlGroup.upload_contract,
                        type: 'post',
                        success: function (data) {
                            //alert(JSON.stringify(data))
                            //console.log(data);

                            if (data.code === RESPONSE_OK_CODE) {
                                toastr.success("上传成功！");

                                var url = data.result.url ? data.result.url : "";
                                var contract_img_url = data.result.dir ? data.result.dir : "";

                                $contract_info_container.attr("data-url", url);

                                //如果 有合同图片
                                if (contract_img_url) {
                                    $contract_info_container.find(".contract_img_url img").attr("src", contract_img_url);
                                }


                            }
                            else {
                                toastr.error(data.msg)
                            }

                        },
                        error: function (error) {
                            // alert(error)
                            toastr.error(error);
                        }
                    });

                }
                else {
                    toastr.warning("请上传对应格式的图片！")
                }
            }
        }

    },
    //下载文件
    tempDown: function () {

        //检查 是否选中公司
        if (!customer_manage.checkIsChooseCom()) {
            return
        }

        var $contract_info_container = $customer_info_container.find(".contract_info_container");
        var url = $contract_info_container.attr("data-url");

        if (url) {

            var aLink = document.createElement('a');
            aLink.download = "";
            aLink.href = url;
            aLink.click();

        }
        else {
            toastr.warning("暂无文件可下载");
        }

    },
};
