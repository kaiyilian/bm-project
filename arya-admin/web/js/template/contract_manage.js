/**
 * Created by CuiMengxin on 2017/5/31.
 */

var $contract_temp = $("#contract_temp");//合同模板 temp
var $contract_temp_info_modal = $(".contract_temp_info_modal");//合同模板 弹框
var $seal_temp = $("#seal_temp");//印章模板 temp
var $seal_temp_info_modal = $(".seal_temp_info_modal");//印章模板 弹框

//合同管理页面
var contract_manage = {

    //初始化
    init: function () {

        $('a[href="#contract_temp"]').tab('show'); //默认显示 合同模板

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {

            //console.log($(this).html());

            var href = $(this).attr("data-href");
            if (href === "contract_temp") {
                contract_temp.init();//合同模板 初始化
            }
            if (href === "seal_temp") {
                seal_temp.init();//印章模板 初始化
            }

        });

        //合同模板 初始化
        contract_temp.init();
    }

};

//合同模板
var contract_temp = {

    currentPage: "1",//当前页面
    totalPage: "10",//总页面
    cur_operate: "",//当前操作 add /  modify
    contract_type_map: null,

    //合同模板 初始化
    init: function () {

        //初始化 公司列表
        contract_temp.initCorpList();
        //清空 合同模板列表
        contract_temp.clearContractTempList();
        contract_temp.initContractType();//初始化 合同类型
        // contract_temp.contractTempList();//获取 合同模板列表

        //合同模板 弹框显示
        $contract_temp_info_modal.on("show.bs.modal", function () {

            var $row = $contract_temp_info_modal.find(".modal-body > .row");

            contract_temp.initCorpListInModal();//初始化 企业列表 (弹框中)
            contract_temp.initContractType();//初始化 合同类型
            contract_temp.initTempOperate();//初始化 上传模板 操作
            contract_temp.initContractItem();//初始化 合同填写项

            $row.find(".temp_id").val("");//模板id 置空

        });

    },

    //初始化 公司列表
    initCorpList: function () {

        var $search_container = $contract_temp.find(".search_container");
        var $corp_list = $search_container.find(".corp_list");

        var obj = {
            "business_type": corp_service_type.contract
        };
        var url = urlGroup.corp_service_tree1_url + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var list = "<option value=''>全部</option>";
                    var arr = data.result.tree;
                    if (!arr || arr.length === 0) {
                    }
                    else {
                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id;
                            var name = $item.name;//

                            list += "<option value='" + id + "'>" + name + "</option>"

                        }
                    }

                    if ($corp_list.siblings(".chosen-container").length > 0) {
                        $corp_list.chosen("destroy");
                    }

                    $corp_list.html(list);

                    $corp_list.chosen({
                        allow_single_deselect: true,//选择之后 是否可以取消
                        max_selected_options: 1,//最多只能选择1个
                        width: "100%",//select框 宽度
                        no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
                    });

                    $corp_list.siblings(".chosen-container").addClass("form-control")
                        .css("padding", "0");

                }
                else {
                    console.log("获取日志-----error：");
                    console.log(data.msg);

                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },

    //初始化 企业列表 (弹框中)
    initCorpListInModal: function () {

        var obj = {
            "business_type": corp_service_type.contract
        };
        var url = urlGroup.corp_service_tree1_url + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var list = "<option value=''>请选择</option>";
                    var arr = data.result.tree;
                    if (!arr || arr.length === 0) {
                    }
                    else {
                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id;
                            var name = $item.name;//

                            list += "<option value='" + id + "'>" + name + "</option>"

                        }
                    }

                    $contract_temp_info_modal.find(".corp_list").html(list);

                    if (contract_temp.cur_operate === "modify") {
                        contract_temp.contractTempDetail();//获取 详情
                    }

                }
                else {
                    console.log("获取日志-----error：");
                    console.log(data.msg);

                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },
    //初始化 合同类型
    initContractType: function () {
        var $contract_type = $contract_temp_info_modal.find(".contract_type");
        $contract_type.empty();

        contract_temp.contract_type_map = new Map();
        contract_temp.contract_type_map.put("0", "劳动合同");
        // contract_temp.contract_type_map.put("1", "社保合同");

        for (var i = 0; i < contract_temp.contract_type_map.keySet().length; i++) {
            var key = contract_temp.contract_type_map.keySet()[i];
            var val = contract_temp.contract_type_map.get(key);

            var $option = $("<option>");
            $option.attr("value", key);
            $option.text(val);
            $option.appendTo($contract_type);

        }

    },
    //初始化 上传模板 操作
    initTempOperate: function () {

        var $temp_operate = $contract_temp_info_modal.find(".temp_operate");
        $temp_operate.attr("data-id", "");
        $temp_operate.attr("data-url", "");
        $temp_operate.find("div").hide();

        $temp_operate.find(".btn_upload").show();
        $temp_operate.find(".txt").show();

    },
    //初始化 合同填写项
    initContractItem: function () {

        var $table_container = $contract_temp_info_modal.find(".table_container");
        var $tbody = $table_container.find("tbody");
        $tbody.empty();

        for (var i = 0; i < 5; i++) {

            //新增 一行填写项
            contract_temp.addContractItem();

        }


        $tbody.find("tr.item").first().find(".btn").remove();

    },
    //新增 一行填写项
    addContractItem: function () {
        var $table_container = $contract_temp_info_modal.find(".table_container");
        var $tbody = $table_container.find("tbody");
        //$tbody.empty();

        var $tr = $("<tr>");
        $tr.addClass("item");
        $tr.appendTo($tbody);

        //loops_id
        var $td_1 = $("<td>");
        $td_1.addClass("loops_id");
        $td_1.appendTo($tr);
        var $div_1 = $("<div>");
        $div_1.appendTo($td_1);
        var $input_1 = $("<input>");
        $input_1.addClass("form-control");
        $input_1.attr("placeholder", "请输入loops_id");
        $input_1.appendTo($div_1);

        //loops_name
        var $td_2 = $("<td>");
        $td_2.addClass("loops_name");
        $td_2.appendTo($tr);
        var $div_2 = $("<div>");
        $div_2.appendTo($td_2);
        var $input_2 = $("<input>");
        $input_2.addClass("form-control");
        $input_2.attr("placeholder", "请输入loops_name");
        $input_2.appendTo($div_2);

        //填写方
        var $td_3 = $("<td>");
        $td_3.addClass("loops_type");
        $td_3.appendTo($tr);
        var $div_3 = $("<div>");
        $div_3.appendTo($td_3);
        var $select_3 = $("<select>");
        $select_3.addClass("form-control");
        $select_3.appendTo($div_3);
        var loops_type = [
            "甲方",
            "乙方"
        ];
        $.each(loops_type, function (index, item) {
            var $option = $("<option>");
            $option.attr("value", index);
            $option.text(item);
            $option.appendTo($select_3);
        });

        //删除
        var $td_4 = $("<td>");
        $td_4.addClass("operate");
        $td_4.appendTo($tr);
        var $div_4 = $("<div>");
        $div_4.addClass("btn");
        $div_4.addClass("btn-sm");
        $div_4.addClass("btn-danger");
        $div_4.addClass("btn_del");
        $div_4.text("删除");
        $div_4.unbind("click").bind("click", function () {
            contract_temp.delContractItem(this);
        });
        $div_4.appendTo($td_4);

    },
    //删除 一行填写项
    delContractItem: function (self) {
        var $item = $(self).closest(".item");
        $item.remove();
    },
    //上传文件 - 按钮点击
    ChooseFileClick: function () {
        var $row = $contract_temp_info_modal.find(".modal-body > .row");
        var $temp_operate = $row.find(".temp_operate");

        if ($temp_operate.find(".upload_file").length > 0) {
            $temp_operate.find(".upload_file").remove();
        }
        //debugger
        var form = $("<form>");
        form.addClass("upload_file");
        form.attr("enctype", "multipart/form-data");
        form.appendTo($temp_operate);
        form.hide();

        var file_input = $("<input>");
        file_input.attr("type", "file");
        file_input.attr("name", "file");
        file_input.change(function () {
            contract_temp.ChooseFile(this);
        });
        file_input.appendTo(form);

        var type_input = $("<input>");
        type_input.attr("type", "text");
        type_input.attr("name", "type");
        type_input.val(4);
        type_input.appendTo(form);

        //默认文件类型 为 html
        var npt_2 = $("<input>");
        npt_2.attr("type", "text");
        npt_2.attr("name", "suffix");
        npt_2.val("html");
        npt_2.appendTo(form);

        file_input.click();

    },
    //选择文件 - 弹框显示
    ChooseFile: function (self) {
        var $row = $contract_temp_info_modal.find(".modal-body > .row");
        var $temp_operate = $row.find(".temp_operate");

        //alert(self.files)
        if (self.files) {
            for (var i = 0; i < self.files.length; i++) {
                var file = self.files[i];

                //判断是否是图片格式
                if (/\.html$/.test(file.name)) {

                    $temp_operate.find(".upload_file").ajaxSubmit({
                        url: urlGroup.file_upload,
                        type: 'post',
                        success: function (data) {
                            //alert(JSON.stringify(data))
                            //console.log(data);

                            if (data.code === RESPONSE_OK_CODE) {
                                toastr.success("上传成功！");

                                var id = data.result.id ? data.result.id : "";
                                var url = data.result.url ? data.result.url : "";


                                var $temp_operate = $contract_temp_info_modal.find(".temp_operate");
                                $temp_operate.attr("data-id", id);
                                $temp_operate.attr("data-url", url);
                                $temp_operate.find("div").hide();

                                $temp_operate.find(".btn_preview").show();
                                $temp_operate.find(".btn_clear").show();

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
                    toastr.error("请上传html文件")
                }
            }
        }

    },
    //预览文件 （弹框中）
    tempPreviewInModal: function () {
        var $temp_operate = $contract_temp_info_modal.find(".temp_operate");
        var url = $temp_operate.attr("data-url");

        if (url) {
            window.open(url);
        }
        else {
            toastr.warning("暂无文件可预览");
        }

    },

    //查询
    btnSearchClick: function () {
        contract_temp.currentPage = "1";

        contract_temp.contractTempList();//获取 合同模板列表
    },
    //清空 合同模板列表
    clearContractTempList: function () {
        var $tbody = $contract_temp.find(".table_container table tbody");

        var msg = "<tr><td colspan='4'>暂无合同模板</td></tr>";
        $tbody.html(msg);

        var $pager_container = $contract_temp.find(".pager_container");
        $pager_container.hide();

    },
    //获取 合同模板列表
    contractTempList: function () {
        console.log("获取表格：" + new Date().getTime());

        //清空 合同模板列表
        contract_temp.clearContractTempList();

        var $table_container = $contract_temp.find(".table_container");
        var $search_container = $contract_temp.find(".search_container");
        var $corp_list = $search_container.find(".corp_list");

        var corp_id = $corp_list.val() ? $corp_list.val()[0] : "";//公司id

        var obj = {
            arya_corp_id: corp_id,
            page: contract_temp.currentPage,
            page_size: "10"
        };
        var url = urlGroup.contract_temp_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    contract_temp.totalPage = data.result.pages ? data.result.pages : 1;//总页数
                    if (contract_temp.currentPage > contract_temp.totalPage) {
                        contract_temp.currentPage -= 1;
                        contract_temp.contractTempList();
                        return;
                    }

                    var list = "";
                    var arr = data.result.result;
                    if (!arr || arr.length === 0) {
                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            //var txVersion = $item.txVersion ? $item.txVersion : "";//
                            var aryaCorpName = $item.aryaCorpName ? $item.aryaCorpName : "";//
                            var uploadTime = $item.uploadTime ? $item.uploadTime : "";//
                            uploadTime = timeInit1(uploadTime);
                            var contractType = $item.contractType ? $item.contractType : "0";//
                            contractType = contract_temp.contract_type_map.get(contractType);
                            var url = $item.url ? $item.url : "";//

                            list +=
                                "<tr class='item' " +
                                "data-id='" + id + "' " +
                                "data-url='" + url + "' " +
                                ">" +
                                "<td class='corpName'>" + aryaCorpName + "</td>" +
                                "<td class='uploadTime'>" + uploadTime + "</td>" +
                                "<td class='contractType'>" + contractType + "</td>" +
                                "<td class='operate'>" +
                                "<div class='btn btn-sm btn-primary btn_preview'>预览</div>" +
                                "<div class='btn btn-sm btn-primary btn_modify'>编辑</div>" +
                                "<div class='btn btn-sm btn-primary btn_del'>删除</div>" +
                                "</td>" +
                                "</tr>"

                        }
                        $table_container.find("tbody").html(list);

                    }

                    contract_temp.contractTempListInit();	//福库券 列表初始化

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
    //合同模板 列表初始化
    contractTempListInit: function () {
        var $table_container = $contract_temp.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $contract_temp.find(".pager_container");

        if ($item.length === 0) {
            $pager_container.hide();
            return
        }

        $item.each(function () {
            var $this = $(this);
            var id = $this.attr("data-id");
            var url = $this.attr("data-url");

            //预览
            $this.find(".btn_preview").unbind("click").bind("click", function () {

                if (url) {
                    window.open(url);
                }

            });

            //编辑
            $this.find(".btn_modify").unbind("click").bind("click", function () {

                contract_temp_param.id = id;
                contract_temp.modifyContractTempModalShow();

            });

            //删除
            $this.find(".btn_del").unbind("click").bind("click", function () {

                contract_temp_param.id = id;
                contract_temp.contractTempDel();

            });

        });

        // console.log(contract_temp.currentPage)
        // console.log(contract_temp.totalPage)

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: contract_temp.currentPage, //当前页数
            totalPages: contract_temp.totalPage, //总页数
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
            onPageClicked: function (event, originalEvent, type, page) { //点击事件
                //alert(page)

                var currentTarget = $(event.currentTarget);

                contract_temp.currentPage = page;
                contract_temp.contractTempList();//查询

            }

        };

        var ul = '<ul class="pagenation" style="float:right;"></ul>';
        $pager_container.show();
        $pager_container.html(ul);
        $pager_container.find(".pagenation").bootstrapPaginator(options);

    },

    //新增 弹框 显示
    addContractTempModalShow: function () {
        contract_temp.cur_operate = "add";

        $contract_temp_info_modal.modal("show");
    },
    //编辑 弹框显示
    modifyContractTempModalShow: function () {
        contract_temp.cur_operate = "modify";
        $contract_temp_info_modal.modal("show");
    },
    //获取详情
    contractTempDetail: function () {

        var obj = {
            contract_template_id: contract_temp_param.id
        };
        var url = urlGroup.contract_temp_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                // console.log("获取日志：");
                // console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    contract_temp_param.id = $item.id ? $item.id : "";//
                    contract_temp_param.contract_type_id = $item.contractType ? $item.contractType : "0";//
                    contract_temp_param.corp_id = $item.aryaCorpId ? $item.aryaCorpId : "";//
                    contract_temp_param.temp_id = $item.yunSignTemplateId ? $item.yunSignTemplateId : "";//
                    contract_temp_param.file_id = $item.file_id ? $item.file_id : "";//
                    contract_temp_param.temp_url = $item.url ? $item.url : "";//
                    contract_temp_param.contract_item_arr = $item.loops ? $item.loops : [];//

                    contract_temp.contractTempSet();//赋值参数 到弹框中
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
    //赋值 弹框
    contractTempSet: function () {

        var $row = $contract_temp_info_modal.find(".modal-body > .row");

        $row.find(".corp_list").val(contract_temp_param.corp_id);
        $row.find(".contract_type").val(contract_temp_param.contract_type_id);
        $row.find(".temp_id").val(contract_temp_param.temp_id);
        $row.find(".temp_operate").attr("data-id", contract_temp_param.file_id);
        $row.find(".temp_operate").attr("data-url", contract_temp_param.temp_url);

        var $table_container = $row.find(".table_container");
        var $tbody = $table_container.find("tbody");
        $tbody.empty();
        //合同填写项
        for (var i = 0; i < contract_temp_param.contract_item_arr.length; i++) {
            var $param = contract_temp_param.contract_item_arr[i];

            var writerType = $param.writerType ? $param.writerType : "0";//填写方
            var loopParam = $param.loopParam ? $param.loopParam : "";//loops id
            var loopName = $param.loopName ? $param.loopName : "";//参数名

            //新增 一行填写项
            contract_temp.addContractItem();

            var $item = $tbody.find("tr.item").eq(i);
            $item.find(".loops_id").find("input").val(loopParam);
            $item.find(".loops_name").find("input").val(loopName);
            $item.find(".loops_type").find("select").val(writerType);

        }
        $tbody.find("tr.item").first().find(".btn").remove();

        //显示预览
        $row.find(".temp_operate").find("div").hide();
        $row.find(".temp_operate").find(".btn_preview").show();
        $row.find(".temp_operate").find(".btn_clear").show();

    },
    //保存 合同模板
    contractTempSave: function () {

        if (!contract_temp.checkParamBySave()) {
            return
        }

        var obj = {
            aryaCorpId: contract_temp_param.corp_id,
            contractType: contract_temp_param.contract_type_id,
            yunSignTemplateId: contract_temp_param.temp_id,
            uploadFileId: contract_temp_param.file_id,
            loops: contract_temp_param.contract_item_arr
        };

        var url;
        if (contract_temp.cur_operate === "add") {
            url = urlGroup.contract_temp_add;
        }
        else {
            url = urlGroup.contract_temp_modify;
            obj["id"] = contract_temp_param.id;
        }

        aryaPostRequest(
            url,
            obj,
            function (data) {
                // console.log("获取日志：");
                // console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("操作成功！");

                    $contract_temp_info_modal.modal("hide");
                    contract_temp.contractTempList();//获取

                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
                toastr.error(error);
            }
        );

    },
    //检查参数
    checkParamBySave: function () {
        var flag = false;
        var txt;
        var $row = $contract_temp_info_modal.find(".modal-body > .row");

        contract_temp_param.contract_type_id = $row.find(".contract_type").val();
        contract_temp_param.corp_id = $row.find(".corp_list").val();
        contract_temp_param.temp_id = $row.find(".temp_id").val();
        contract_temp_param.file_id = $row.find(".temp_operate").attr("data-id");
        contract_temp_param.temp_url = $row.find(".temp_operate").attr("data-url");
        contract_temp_param.contract_item_arr = [];//
        var contract_item = $row.find(".table_container").find("tbody tr.item");
        var contract_item_arr = true;//合同填写项 是否输入完整
        for (var i = 0; i < contract_item.length; i++) {
            var $item = contract_item.eq(i);

            // var id = $item.attr("data-id") ? $item.attr("data-id") : "";//如果已经填写过 则该条数据的id
            var writerType = $item.find(".loops_type").find("select").val();//填写方 0 甲方 1 乙方
            var loopParam = $item.find(".loops_id").find("input").val();//loops id
            var loopName = $item.find(".loops_name").find("input").val();//loops name

            if (!writerType || !loopParam || !loopName) {
                contract_item_arr = false;
                break;
            }
            else {
                var obj = {
                    writerType: writerType,
                    loopParam: loopParam,
                    loopName: loopName
                };
                contract_temp_param.contract_item_arr.push(obj);
            }

        }

        if (!contract_temp_param.corp_id) {
            txt = "请选择企业名称！";
        }
        else if (!contract_temp_param.contract_type_id) {
            txt = "请选择合同类型！";
        }
        else if (!contract_temp_param.temp_id) {
            txt = "请输入模板ID！";
        }
        else if (!contract_temp_param.file_id) {
            txt = "请上传模板！";
        }
        else if (!contract_item_arr) {
            txt = "合同填写项请输入完整！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //删除 模板
    contractTempDel: function () {

        delWarning("确定要删除该模板吗?", function () {

            loadingInit();

            var obj = {
                batch: [{
                    id: contract_temp_param.id
                }]
            };

            aryaPostRequest(
                urlGroup.contract_temp_del,
                obj,
                function (data) {
                    //console.log(data);

                    if (data.code === RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");
                        contract_temp.contractTempList();
                    }
                    else {
                        messageCue(data.msg);
                    }

                },
                function (error) {
                    messageCue(error);
                }
            );


        })

    }


};

var contract_temp_param = {
    id: "",
    corp_id: "",//公司ID
    contract_type_id: "",//合同类型ID
    temp_id: "",//模板id
    file_id: "",//上传模板 文件ID
    contract_item_arr: [],//合同填写项
    temp_url: "" //上传模板 文件URL
};

//印章模板
var seal_temp = {

    seal_temp_id: "",
    currentPage: "1",//当前页面
    totalPage: "10",//总页面

    init: function () {

        seal_temp.initCorpList();//初始化 公司列表
        seal_temp.clearSealTempList();//清空 印章模板列表
        seal_temp.initCorpListInModal();//初始化 企业列表 (弹框中)

        $seal_temp_info_modal.on("show.bs.modal", function () {
            var $row = $seal_temp_info_modal.find(".modal-body > .row");
            var $img_list = $row.find(".img_list");

            $row.find(".corp_list").val("");
            $img_list.find(".item").remove();
        });

    },

    //初始化 公司列表
    initCorpList: function () {

        var $search_container = $seal_temp.find(".search_container");
        var $corp_list = $search_container.find(".corp_list");

        var obj = {
            "business_type": corp_service_type.contract
        };
        var url = urlGroup.corp_service_tree1_url + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var list = "<option value=''>全部</option>";
                    var arr = data.result.tree;
                    if (!arr || arr.length === 0) {
                    }
                    else {
                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id;
                            var name = $item.name;//

                            list += "<option value='" + id + "'>" + name + "</option>"

                        }
                    }

                    if ($corp_list.siblings(".chosen-container").length > 0) {
                        $corp_list.chosen("destroy");
                    }

                    $corp_list.html(list);

                    $corp_list.chosen({
                        allow_single_deselect: true,//选择之后 是否可以取消
                        max_selected_options: 1,//最多只能选择1个
                        width: "100%",//select框 宽度
                        no_results_text: "找不到 " //输入的 内容查询不到时的提示信息
                    });

                    $corp_list.siblings(".chosen-container").addClass("form-control")
                        .css("padding", "0");

                }
                else {
                    console.log("获取日志-----error：");
                    console.log(data.msg);

                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },
    //初始化 企业列表 (弹框中)
    initCorpListInModal: function () {

        var obj = {
            "business_type": corp_service_type.contract
        };
        var url = urlGroup.corp_service_tree1_url + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var list = "<option value=''>请选择</option>";
                    var arr = data.result.tree;
                    if (!arr || arr.length === 0) {
                    }
                    else {
                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id;
                            var name = $item.name;//

                            list += "<option value='" + id + "'>" + name + "</option>"

                        }
                    }

                    $seal_temp_info_modal.find(".corp_list").html(list);

                }
                else {
                    console.log("获取日志-----error：");
                    console.log(data.msg);

                    messageCue(data.msg);
                }
            },
            function (error) {
                messageCue(error);
            }
        );

    },

    //查询
    btnSearchClick: function () {
        seal_temp.currentPage = "1";

        seal_temp.sealTempList();//获取 印章模板列表
    },
    //清空 印章模板列表
    clearSealTempList: function () {
        var $seal_list_container = $seal_temp.find(".seal_list_container");

        var msg = "<div class='msg'>暂无印章</div>";
        $seal_list_container.html(msg);

        var $pager_container = $seal_temp.find(".pager_container");
        $pager_container.hide();

    },
    //获取 印章模板列表
    sealTempList: function () {

        //清空 印章模板列表
        seal_temp.clearSealTempList();

        var $seal_list_container = $seal_temp.find(".seal_list_container");
        var $search_container = $seal_temp.find(".search_container");
        var $corp_list = $search_container.find(".corp_list");

        var corp_id = $corp_list.val() ? $corp_list.val()[0] : "";//公司id

        var obj = {
            arya_corp_id: corp_id,
            page: seal_temp.currentPage,
            page_size: "10"
        };
        var url = urlGroup.seal_temp_list + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    seal_temp.totalPage = data.result.pages;//总页数
                    if (seal_temp.currentPage > seal_temp.totalPage) {
                        seal_temp.currentPage -= 1;
                        seal_temp.sealTempList();
                        return;
                    }

                    var list = "";
                    var arr = data.result.result;
                    if (!arr || arr.length === 0) {
                    }
                    else {

                        for (var i = 0; i < arr.length; i++) {
                            var $item = arr[i];

                            var id = $item.id ? $item.id : "";//
                            var img_url = $item.url ? $item.url : "";//
                            var aryaCorpName = $item.aryaCorpName ? $item.aryaCorpName : "";//
                            var uploadTime = $item.uploadTime ? $item.uploadTime : "";//
                            uploadTime = timeInit(uploadTime);

                            list +=
                                "<div class='col-xs-6 item' " +
                                "data-id='" + id + "' " +
                                ">" +
                                "<div class='col-xs-6 seal_img'>" +
                                "<img src='" + img_url + "'>" +
                                "</div>" +
                                "<div class='col-xs-6 seal_info'>" +
                                "<div class='corp_name'>" + aryaCorpName + "</div>" +
                                "<div class='date'>" + uploadTime + "</div>" +
                                "<div class='btn btn-primary btn-sm btn-danger btn_del'>删除</div>" +
                                "</div>" +
                                "</div>";

                        }

                        $seal_list_container.html(list);
                    }

                    seal_temp.sealTempListInit();	//列表初始化

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
    //印章模板 列表初始化
    sealTempListInit: function () {
        var $seal_list_container = $seal_temp.find(".seal_list_container");
        var $item = $seal_list_container.find(".item");
        var $pager_container = $seal_temp.find(".pager_container");

        if ($item.length === 0) {
            $pager_container.hide();
            return
        }

        $item.each(function () {
            var $this = $(this);
            var id = $this.attr("data-id");

            //删除
            $this.find(".btn_del").unbind("click").bind("click", function () {

                seal_temp.seal_temp_id = id;
                seal_temp.sealTempDel(this);

            });

        });

        var options = {
            bootstrapMajorVersion: 3, //版本  3是ul  2 是div
            //containerClass:"sdfsaf",
            //size: "small",//大小
            alignment: "right",//对齐方式
            currentPage: seal_temp.currentPage, //当前页数
            totalPages: seal_temp.totalPage, //总页数
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
            onPageClicked: function (event, originalEvent, type, page) { //点击事件
                //alert(page)

                var currentTarget = $(event.currentTarget);

                seal_temp.currentPage = page;
                seal_temp.sealTempList();//查询

            }

        };

        var ul = '<ul class="pagenation" style="float:right;"></ul>';
        $pager_container.show();
        $pager_container.html(ul);
        $pager_container.find(".pagenation").bootstrapPaginator(options);

    },

    //新增 弹框 显示
    addSealTempModalShow: function () {
        $seal_temp_info_modal.modal("show");
    },

    //上传文件 - 按钮点击
    ChooseFileClick: function () {
        var $row = $seal_temp_info_modal.find(".modal-body > .row");
        var $img_list = $row.find(".img_list");

        if ($img_list.find(".upload_file").length > 0) {
            $img_list.find(".upload_file").remove();
        }
        //debugger
        var form = $("<form>");
        form.addClass("upload_file");
        form.attr("enctype", "multipart/form-data");
        form.appendTo($img_list);
        form.hide();

        var file_input = $("<input>");
        file_input.attr("type", "file");
        file_input.attr("name", "file");
        file_input.change(function () {
            seal_temp.ChooseFile(this);
        });
        file_input.appendTo(form);

        var type_input = $("<input>");
        type_input.attr("type", "text");
        type_input.attr("name", "type");
        type_input.val(3);
        type_input.appendTo(form);

        var img_width_npt = $("<input>");
        img_width_npt.attr("type", "text");
        img_width_npt.attr("name", "filter_width");
        img_width_npt.val(150);
        img_width_npt.appendTo(form);

        var img_height_npt = $("<input>");
        img_height_npt.attr("type", "text");
        img_height_npt.attr("name", "filter_height");
        img_height_npt.val(150);
        img_height_npt.appendTo(form);


        file_input.click();

    },
    //选择文件 - 弹框显示
    ChooseFile: function (self) {
        var $row = $seal_temp_info_modal.find(".modal-body > .row");
        var $img_list = $row.find(".img_list");

        //alert(self.files)
        if (self.files) {
            for (var i = 0; i < self.files.length; i++) {
                var file = self.files[i];

                // if (file.size > 22500) {
                //     toastr.warning("图片格式不能超过22k");
                //     return;
                // }

                //判断是否是图片格式
                if (/\.(png|PNG)$/.test(file.name)) {

                    $img_list.find(".upload_file").ajaxSubmit({
                        url: urlGroup.file_upload,
                        type: 'post',
                        success: function (data) {
                            // alert(JSON.stringify(data))
                            // console.log(data);

                            if (data.code === RESPONSE_OK_CODE) {
                                toastr.success("上传成功！");

                                var id = data.result.id ? data.result.id : "";
                                var url = data.result.url ? data.result.url : "img/empty_img.png";

                                var $item = $("<div>");
                                $item.addClass("col-xs-6");
                                $item.addClass("col-md-3");
                                $item.addClass("item");
                                $item.attr("data-id", id);
                                $item.prependTo($img_list);

                                //
                                var $a = $("<a>");
                                $a.addClass("thumbnail");
                                $a.appendTo($item);
                                var $img = $("<img>");
                                $img.attr("src", url);
                                $img.appendTo($a);

                                //删除 icon
                                var $icon_del = $("<div>");
                                $icon_del.addClass("icon_del");
                                $icon_del.appendTo($item);
                                var $img_del = $("<img>");
                                $img_del.attr("src", "img/icon_setting_dept_del_active.png");
                                $img_del.bind("click", function () {
                                    seal_temp.imgDel(this);
                                });
                                $img_del.appendTo($icon_del);

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
                    toastr.warning("文件格式不对，请上传png格式文件")
                }
            }
        }

    },
    //删除 图片
    imgDel: function (self) {

        delWarning("确定要删除该图片吗？", function () {
            $(self).closest(".item").remove()
        })

    },

    //印章 保存
    sealTempSave: function () {

        if (!seal_temp.checkBySealTempSave()) {
            return
        }

        var aryaCorpId = $seal_temp_info_modal.find(".corp_list").val();

        var sealFileNames = [];//ID 数组
        var $item = $seal_temp_info_modal.find(".img_list").find(".item");
        for (var i = 0; i < $item.length; i++) {

            var id = $item.eq(i).attr("data-id");
            sealFileNames.push(id);

        }

        var obj = {
            aryaCorpId: aryaCorpId,
            sealFileNames: sealFileNames,
            // filter_width: 150,
            // filter_height: 150
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.seal_temp_add,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    $seal_temp_info_modal.modal("hide");
                    toastr.success("新增成功！");
                    seal_temp.sealTempList();//获取 印章模板列表

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
    checkBySealTempSave: function () {
        var flag = false;
        var txt;

        var aryaCorpId = $seal_temp_info_modal.find(".corp_list").val();
        var $item = $seal_temp_info_modal.find(".img_list").find(".item");
        if (!aryaCorpId) {
            txt = "请选择公司！";
        }
        else if ($item.length === 0) {
            txt = "请至少上传1张图片！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //删除 印章
    sealTempDel: function (self) {

        delWarning("确定要删除该印章模板吗?", function () {

            loadingInit();

            var obj = {
                batch: [{
                    id: seal_temp.seal_temp_id
                }]
            };

            aryaPostRequest(
                urlGroup.seal_temp_del,
                obj,
                function (data) {
                    //console.log(data);

                    if (data.code === RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");
                        $(self).closest(".item").remove();

                        seal_temp.sealTempListInit();
                    }
                    else {
                        messageCue(data.msg);
                    }

                },
                function (error) {
                    messageCue(error);
                }
            );

        });

    }

};

$(function () {
    contract_manage.init();
});