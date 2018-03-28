/**
 * Created by Administrator on 2017/7/10.
 */

var $corp_service_contract_container = $(".corp_service_contract_container");
var $corp_contract_container = $corp_service_contract_container.find(".corp_contract_container");

var corp_service_contract = {

    node: "",
    organize_type: "",//组织类型:1集团，2子公司，3通用部门，4一级公司
    organize_id: "",//组织id
    organize_pid: "",//组织pid
    btn_value: "0",//
    is_first: true,//是否是 第一次编辑营业执照

    //初始化
    init: function () {

        corp_service_contract.clearContractInfo();	//清空 内容

    },
    //初始化 图片点击
    initImgClick: function () {

        corp_service_contract.bandImageInput(
            // ".corp_service_contract_container .corp_license_container .corp_license_file",
            ".corp_service_contract_container .corp_license_container .corp_license"
        );

    },

    //清空 内容
    clearContractInfo: function () {

        //禁用所有 input
        $corp_contract_container.find(".editable").attr("disabled", "disabled");
        $corp_contract_container.find(".editable").val("");

        //禁用 图片点击
        $corp_contract_container.find(".img_upload_container").find(".img_upload_lbl").unbind("click");

        //隐藏所有按钮
        $corp_contract_container.find(".btn_operate").find(".btn").hide();

    },

    //企业 信息获取
    corpInfo: function () {
        corp_service_contract.clearContractInfo();//清空 内容

        var obj = {
            arya_corp_id: corp_service_contract.organize_id
        };
        var url = urlGroup.corp_service_contract_detail + "?" + jsonParseParam(obj);

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {
                //console.info("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result.info;
                    var license_url = data.result.url ? data.result.url : "";//营业执照url

                    var corp_fullName = $item.eCorpName ? $item.eCorpName : "";//企业全称
                    var corp_legal_person = $item.eCorpUserName ? $item.eCorpUserName : "";//企业法人
                    var corp_legal_person_idCard = $item.eCorpUserCardNo ? $item.eCorpUserCardNo : "";//企业法人 身份证
                    var corp_legal_person_phone = $item.eCorpUserPhone ? $item.eCorpUserPhone : "";//企业法人 手机号
                    var license_code = $item.eCorpLicenseCode ? $item.eCorpLicenseCode : "";//企业营业执照编号
                    corp_service_contract.is_first = !(corp_legal_person || corp_legal_person_idCard);//是否是第一次编辑

                    $corp_contract_container.find(".corp_fullName").val(corp_fullName);
                    $corp_contract_container.find(".corp_licenses_code").val(license_code);
                    $corp_contract_container.find(".corp_legal_person").val(corp_legal_person);
                    $corp_contract_container.find(".corp_legal_person_idCard").val(corp_legal_person_idCard);
                    $corp_contract_container.find(".corp_legal_person_phone").val(corp_legal_person_phone);
                    $corp_contract_container.find(".corp_license").attr("data-src", license_url);

                    corp_service_contract.corpInfoInit();//详情 初始化

                }
                else {
                    toastr.warning(data.msg);
                }
            },
            function () {
                toastr.error("系统错误，请联系管理员！");
            }
        );

    },
    //企业 - 详情 初始化
    corpInfoInit: function () {

        //imgContainer 图片显示
        var $img_upload_container = $corp_contract_container.find(".img_upload_container");
        $img_upload_container.each(function () {
            var $this = $(this);

            var url = $this.find(".img_upload_lbl").attr("data-src");
            if (!url) {
                $this.find(".img_upload_lbl").empty().html("暂无图片");
            }
            else {
                var img = "<img src='" + url + "'>";
                $this.find(".img_upload_lbl").empty().html(img);
            }

        });

        //如果 是第一次编辑
        if (corp_service_contract.is_first) {

            var $btn_operate = $corp_contract_container.find(".btn_operate");
            corp_service_contract.btn_value = $btn_operate.find(".btn_modify").attr("data-value");

            corp_service_contract.BtnOperateInit();//底部按钮 初始化

        }
        else {

            //隐藏所有按钮
            $corp_contract_container.find(".btn_operate").find(".btn").hide();

        }

    },

    // 信息编辑
    corpInfoModify: function () {
        $corp_contract_container.find(".editable").removeAttr("disabled");//

        corp_service_contract.initImgClick();//初始化 图片点击

        //imgContainer 图片显示
        var $img_upload_container = $corp_contract_container.find(".img_upload_container");
        $img_upload_container.each(function () {
            var $this = $(this);
            $this.find(".img_upload_lbl").css("cursor", "point");

            var url = $this.find(".img_upload_lbl").attr("data-src");
            if (!url) {
                $this.find(".img_upload_lbl").empty().html("请选择图片");
            }

        });

        var $btn_operate = $corp_contract_container.find(".btn_operate");
        //保存
        var value_1 = $btn_operate.find(".btn_save").attr("data-value");
        //取消
        var value_2 = $btn_operate.find(".btn_cancel").attr("data-value");

        //底部 按钮
        corp_service_contract.btn_value = value_1 | value_2;

        corp_service_contract.BtnOperateInit();//底部按钮 初始化

    },
    //信息 - 取消编辑
    corpInfoCancelByModify: function () {
        corp_service_contract.corpInfo();
    },
    //信息保存
    corpInfoSave: function () {

        //检查参数 是否为空
        if (!corp_service_contract.checkParam()) {
            return;
        }

        operateShow(
            "信息提交后不能修改哦！",
            function () {

                loadingInit();//加载中 弹框显示

                var obj = {
                    aryaCorpId: corp_service_contract.organize_id,
                    info: {
                        eCorpName: corp_service_contract_param.eCorpName,
                        eCorpLicenseCode: corp_service_contract_param.eCorpLicenseCode,
                        eCorpUserName: corp_service_contract_param.eCorpUserName,
                        eCorpUserCardNo: corp_service_contract_param.eCorpUserCardNo,
                        eCorpUserPhone: corp_service_contract_param.eCorpUserPhone,
                        eCorpLicenseFileName: corp_service_contract_param.eCorpLicenseFileName
                    }
                };

                aryaPutRequest(
                    urlGroup.corp_service_contract_save,
                    obj,
                    function (data) {
                        //console.log(data);

                        if (data.code === RESPONSE_OK_CODE) {

                            toastr.success("保存成功！");
                            corp_service_contract.corpInfo();

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
    //检查参数 是否为空
    checkParam: function () {

        var flag = false;
        var txt;

        corp_service_contract_param.paramSet();

        if (!corp_service_contract_param.eCorpName) {
            txt = "请输入企业全称！"
        }
        else if (!corp_service_contract_param.eCorpLicenseCode) {
            txt = "请输入营业执照编号！"
        }
        else if (!corp_service_contract_param.eCorpUserName) {
            txt = "请输入企业法人姓名！"
        }
        else if (!corp_service_contract_param.eCorpUserCardNo) {
            txt = "请输入企业法人身份证！"
        }
        else if (!corp_service_contract_param.eCorpUserPhone) {
            txt = "请输入企业法人手机号！"
        }
        else if (!corp_service_contract_param.eCorpLicenseFileName) {
            txt = "请上传营业执照！"
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },

    //底部按钮 初始化
    BtnOperateInit: function () {

        $corp_contract_container.find(".btn_operate").find(".btn").hide();

        $corp_contract_container.find(".btn_operate").find(".btn").each(function () {
            var $this = $(this);

            var value = $this.attr("data-value");
            if (value & corp_service_contract.btn_value) {
                $this.show();
            }

        });

    },


    //选择图片事件
    bandImageInput: function (previewSelector) {
        //debugger
        $(previewSelector).unbind("click").bind("click", function () {

            var $img_upload_container = $(previewSelector).closest(".img_upload_container");

            if ($img_upload_container.find(".upload_img")) {
                $img_upload_container.find(".upload_img").remove();
            }
            //debugger
            var form = $("<form>");
            form.addClass("upload_img");
            form.attr("enctype", "multipart/form-data");
            form.appendTo($img_upload_container);
            form.hide();

            var type_input = $("<input>");
            type_input.attr("type", "text");
            type_input.attr("name", "type");
            type_input.val("2");
            type_input.appendTo(form);

            var file_input = $("<input>");
            file_input.attr("type", "file");
            file_input.attr("name", "file");
            file_input.attr("accept", "image/png,image/jpg");
            file_input.change(function () {
                corp_service_contract.ChooseFile(this);
            });
            file_input.appendTo(form);

            file_input.click();

        });

    },
    //选择文件 - 弹框显示
    ChooseFile: function (self) {
        var $img_upload_container = $(self).closest(".img_upload_container");

        //alert(self.files)
        if (self.files) {
            for (var i = 0; i < self.files.length; i++) {
                var file = self.files[i];

                //判断是否是图片格式
                if (/\.(png|PNG)$/.test(file.name)) {

                    $img_upload_container.find(".upload_img").ajaxSubmit({
                        url: urlGroup.file_upload,
                        type: 'post',
                        success: function (data) {
                            //alert(JSON.stringify(data))
                            // console.log(data);

                            if (data.code === RESPONSE_OK_CODE) {

                                toastr.success("上传成功！");

                                corp_service_contract_param.eCorpLicenseFileName = data.result.id;//图片 id

                                var url = data.result.url;//图片URL
                                var $img = $("<img>");
                                $img.attr("src", url);
                                $img_upload_container.find(".img_upload_lbl").empty().append($img);
                                $img_upload_container.find(".img_upload_lbl").attr("data-src", url);

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
                    toastr.error("请上传图片")
                }
            }
        }
    },

    //树形菜单 - 点击事件
    groupTreeOnclick: function (node) {
        //console.log(JSON.stringify(node));

        loadingInit();//加载中 弹框显示

        corp_service_contract.node = node;
        corp_service_contract.organize_id = node.id;//组织id
        corp_service_contract.organize_pid = node.pid ? node.pid : 0;//父级id
        corp_service_contract.organize_type = node.type;//组织类型:1集团，2子公司，3通用部门，4一级公司

        corp_service_contract.corpInfo();//查询
        // corp_service_contract.corpInfoInit();//查询

    }

};

//参数
var corp_service_contract_param = {

    eCorpName: null,//企业全称
    eCorpLicenseCode: null,//营业执照编号
    eCorpUserName: null,//企业法人 姓名
    eCorpUserCardNo: null,//企业法人 身份证
    eCorpUserPhone: null,//企业法人 手机号
    eCorpLicenseFileName: null, //营业执照 名称

    paramSet: function () {

        corp_service_contract_param.eCorpName = $corp_contract_container.find(".corp_fullName").val();
        corp_service_contract_param.eCorpLicenseCode = $corp_contract_container.find(".corp_licenses_code").val();
        corp_service_contract_param.eCorpUserName = $corp_contract_container.find(".corp_legal_person").val();
        corp_service_contract_param.eCorpUserCardNo = $corp_contract_container.find(".corp_legal_person_idCard").val();
        corp_service_contract_param.eCorpUserPhone = $corp_contract_container.find(".corp_legal_person_phone").val();
        // corp_service_contract_param.eCorpLicenseFileName = $corp_contract_container.find(".corp_license").attr("data-name");

    }

};

$(document).ready(function () {

    // //左侧树形结构
    // initOrganizationTree(
    //     ".corp_service_contract_container .aryaZtreeContainer .ztree",
    //     "",
    //     urlGroup.corp_service_tree1_url + "?business_type=" + corp_service_type.contract,
    //     corp_service_contract.groupTreeOnclick
    // );

    var treeId = ".corp_service_contract_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_service_contract_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_service_tree1_url + "?business_type=" + corp_service_type.contract;//获取树结构 url
    var treeClickFunc = corp_service_contract.groupTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_service_contract.init;	//初始化 页面

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构


    corp_service_contract.init();

});
