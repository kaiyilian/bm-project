/**
 * Created by CuiMengxin on 2017/4/27.
 */

var $corp_service_entry_container = $(".corp_service_entry_container");

var corp_service_entry = {

    node: "",
    organize_type: "",//组织类型:1集团，2子公司，3通用部门，4一级公司
    organize_id: "",//组织id
    organize_pid: "",//组织pid
    district_id: "",//地区 id
    btn_value: "0",//底部按钮 要显示的 值

    //初始化 公司服务 - 一键入职
    init: function () {

        corp_service_entry.initCorpProperty();	//初始化 公司性质
        corp_service_entry.clearCorpInfo();	//清空 内容

    },
    //初始化 公司性质
    initCorpProperty: function () {
        var map = new Map();
        map.put("1", "国有企业");
        map.put("2", "集体企业");
        map.put("3", "联营企业");
        map.put("4", "股份合作制企业");
        map.put("5", "私营企业");
        map.put("6", "个体企业");
        map.put("7", "合伙企业");
        map.put("8", "有限责任企业");
        map.put("9", "股份有限企业");

        var $select = $corp_service_entry_container.find(".corp_property_container .corp_property");
        $select.empty();

        for (var i = 0; i < map.keySet().length; i++) {

            var key = map.keySet()[i];
            var value = map.get(key);

            var $option = $("<option>");
            $option.attr("value", key);
            $option.text(value);
            $option.appendTo($select);

        }

    },
    //初始化 图片点击
    initImgClick: function () {

        //初始化 上传图片
        corp_service_entry.bandImageInput(
            ".corp_service_entry_container .corp_logo_container .corp_logo_file",
            ".corp_service_entry_container .corp_logo_container .corp_logo"
        );
        corp_service_entry.bandImageInput(
            ".corp_service_entry_container .corp_img_container .corp_img_file",
            ".corp_service_entry_container .corp_img_container .corp_img"
        );
        corp_service_entry.bandImageInput(
            ".corp_service_entry_container .corp_license_container .corp_license_file",
            ".corp_service_entry_container .corp_license_container .corp_license"
        );

    },

    //树形菜单 - 点击事件
    groupTreeOnclick: function (node) {
        //console.log(JSON.stringify(node));

        loadingInit();//加载中 弹框显示

        corp_service_entry.node = node;
        corp_service_entry.organize_id = node.id;//组织id
        corp_service_entry.organize_pid = node.pid ? node.pid : 0;//父级id
        corp_service_entry.organize_type = node.type;//组织类型:1集团，2子公司，3通用部门，4一级公司

        corp_service_entry.corpInfo();//查询公司详情（集团、子公司或一级公司）

    },

    //公司/集团 - 详情获取（集团、二级公司或一级公司）
    corpInfo: function () {
        corp_service_entry.clearCorpInfo();//
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form

        var obj = {
            corp_id: corp_service_entry.organize_id
        };
        var url = urlGroup.corp_service_entry_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.info("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    var $item = data.result;

                    var id = $item.id ? $item.id : "";//
                    var enterprise_nature = $item.enterprise_nature ? $item.enterprise_nature : 1;//公司性质
                    var contact_mail = $item.contact_mail ? $item.contact_mail : "";//企业联系人邮箱

                    var district = $item.district ? $item.district : "";//地区名称字符串
                    var district_id = $item.district_id ? $item.district_id : "";//地区id字符串 :
                    var address = $item.address ? $item.address : "";//企业地址

                    var longitude = $item.longitude;//经度
                    var latitude = $item.latitude;//纬度
                    var create_time = $item.create_time ? $item.create_time : "";//创建时间
                    if (create_time)
                        create_time = new Date(create_time).toLocaleDateString();

                    var checkin_code = $item.checkin_code ? $item.checkin_code : "";//企业入职码
                    var salarySmsHours = $item.salarySmsHours ? $item.salarySmsHours : 48;//企业入职码
                    var desc = $item.desc ? $item.desc : "";//简介

                    var corp_logo_url = $item.logo_url ? $item.logo_url : "";//logo图片地址
                    var corp_image_url = $item.corp_image_url ? $item.corp_image_url : "";//公司图片地址
                    // var license_url = $item.license_url ? $item.license_url : "";//营业执照url
                    // var license_code = $item.license_code ? $item.license_code : "";//企业营业执照编号

                    corp_service_entry.organize_id = id;//

                    $corp_info_form.find("select.corp_property").val(enterprise_nature);
                    $corp_info_form.find(".corp_contract_email").val(contact_mail);

                    $corp_info_form.find(".corp_address").val(district);
                    $corp_info_form.find(".corp_address_id").val(district_id);
                    $corp_info_form.find(".corp_detail_address").val(address);

                    $corp_info_form.find(".corp_longitude").val(longitude);
                    $corp_info_form.find(".corp_latitude").val(latitude);
                    $corp_info_form.find(".corp_create_time").val(create_time);

                    $corp_info_form.find(".corp_checkin_code").val(checkin_code);
                    $corp_info_form.find(".salarySmsHours").val(salarySmsHours);
                    $corp_info_form.find(".corp_desc").val(desc);

                    $corp_info_form.find(".corp_logo").attr("data-src", corp_logo_url);
                    $corp_info_form.find(".corp_img").attr("data-src", corp_image_url);

                    corp_service_entry.corpInfoInit();//公司/集团 - 详情 初始化

                }
                else {
                    toastr.info(data.msg);
                }
            },
            function () {
                toastr.error("系统错误，请联系管理员！");
            }
        );

    },
    //公司/集团 - 详情 初始化
    corpInfoInit: function () {
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form

        //imgContainer 图片显示
        var $img_upload_container = $corp_info_form.find(".img_upload_container");
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

            $this.find("input[type='file']").val("");//清空文件内容

            // var file = $this.find("input[type='file']").val();
            // console.info("第一次：");
            // console.log($this.find("input[type='file']").val());
            // $this.find("input[type='file']").val("");
            // console.info("第二次：");
            // console.log($this.find("input[type='file']").val());

        });

        var $btn_operate = $corp_info_form.find(".btn_operate");
        corp_service_entry.btn_value = $btn_operate.find(".btn_modify").attr("data-value");

        corp_service_entry.BtnOperateInit();//底部按钮 初始化
    },

    //清空 内容
    clearCorpInfo: function () {
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form
        $corp_info_form.find(".editable").attr("disabled", "disabled");//禁用所有 点击

        //imgContainer
        var $img_upload_container = $corp_info_form.find(".img_upload_container");
        $img_upload_container.find(".img_upload_lbl").html("暂无图片");
        $img_upload_container.find(".img_upload_lbl").unbind("click");

        //隐藏所有按钮
        $corp_info_form.find(".btn_operate").find(".btn").hide();
    },

    //显示 地区
    showDistrictList: function () {
        var $corp_address_container = $corp_service_entry_container.find(".corp_address_container");

        if ($corp_address_container.find(".corp_district_list").length > 0) {
            $corp_address_container.find(".corp_district_list").remove();
        }

        var $corp_district_list = $("<div>");
        $corp_district_list.addClass("corp_district_list");
        $corp_district_list.appendTo($corp_address_container);

        corp_service_entry.showProvinceList($corp_address_container.find(".corp_district_list"));

        var $corp_address = $corp_address_container.find(".corp_address");
        var $corp_address_id = $corp_address_container.find(".corp_address_id");
        $corp_address.val("");
        $corp_address_id.val("");

    },
    //获取省份列表
    showProvinceList: function (selector) {
        $(selector).empty();

        $.each(districts, function (index, districtData) {

            //如果是 省级
            if (districtData["PARENT_ID"] == COUNTRY_ID) {

                var id = districtData["ID"];
                var name = districtData["DISTRICT_NAME"];

                var $a = $("<a></a>");
                $a.appendTo($(selector));
                $a.text(name);
                $a.unbind("click").bind("click", function () {

                    corp_service_entry.showSelectDistrict(name, id);//显示地址
                    corp_service_entry.showCityList(selector, id);//获取 城市 列表

                });

            }

        });
    },
    //获取 城市 列表
    showCityList: function (selector, provinceId) {
        $(selector).empty();
        $.each(districts, function (index, districtData) {

            //省级下面 对应的 市级
            if (districtData["PARENT_ID"] == provinceId) {

                var id = districtData["ID"];
                var name = districtData["DISTRICT_NAME"];

                var $a = $("<a></a>");
                $a.appendTo($(selector));
                $a.text(name);
                $a.unbind("click").bind("click", function () {

                    corp_service_entry.showSelectDistrict(name, id);//显示地址
                    corp_service_entry.showCountyList(selector, id);//获取 区域 列表

                });

            }

        });
    },
    //获取 区域 列表
    showCountyList: function (selector, cityId) {
        $(selector).empty();
        $.each(districts, function (index, districtData) {

            //省级下面 对应的 市级
            if (districtData["PARENT_ID"] == cityId) {

                var id = districtData["ID"];
                var name = districtData["DISTRICT_NAME"];

                var $a = $("<a></a>");
                $a.appendTo($(selector));
                $a.text(name);
                $a.unbind("click").bind("click", function () {

                    corp_service_entry.showSelectDistrict(name, id);//显示地址

                    var $corp_address_container = $corp_service_entry_container.find(".corp_address_container");
                    $corp_address_container.find(".corp_district_list").remove();

                });

            }

        });
    },
    //显示地址
    showSelectDistrict: function (name, id) {
        var $corp_address_container = $corp_service_entry_container.find(".corp_address_container");
        var $corp_address = $corp_address_container.find(".corp_address");
        var $corp_address_id = $corp_address_container.find(".corp_address_id");
        var address_val = $.trim($corp_address.val());
        var address_id_val = $.trim($corp_address_id.val());
        address_val = address_val ? address_val + "-" + name : name;
        address_id_val = address_id_val ? address_id_val + ":" + id : id;

        $corp_address.val(address_val);
        $corp_address_id.val(address_id_val);
        //console.log(address_id_val);

    },

    //公司/集团 信息编辑
    corpInfoModify: function () {

        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form
        $corp_info_form.find(".editable").removeAttr("disabled");//

        corp_service_entry.initImgClick();//初始化 图片点击

        //赋值企业id
        $corp_info_form.find(".corp_id").val(corp_service_entry.organize_id);
        $corp_info_form.find(".corp_parent_id").val(corp_service_entry.organize_pid);

        //imgContainer 图片显示
        var $img_upload_container = $corp_info_form.find(".img_upload_container");
        $img_upload_container.each(function () {
            var $this = $(this);
            $this.find(".img_upload_lbl").css("cursor", "point");

            var url = $this.find(".img_upload_lbl").attr("data-src");
            if (!url) {
                $this.find(".img_upload_lbl").empty().html("请选择图片");
            }

        });


        var $btn_operate = $corp_info_form.find(".btn_operate");
        //保存
        var value_1 = $btn_operate.find(".btn_save").attr("data-value");
        //取消
        var value_2 = $btn_operate.find(".btn_cancel").attr("data-value");

        //底部 按钮
        corp_service_entry.btn_value = value_1 | value_2;

        corp_service_entry.BtnOperateInit();//底部按钮 初始化

    },
    //检查短信发送时间
    checkSalarySmsTime: function () {
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form

        var time = $corp_info_form.find(".salarySmsHours").val();
        time = parseInt(time);

        if (isNaN(time) || time > 48) {
            time = 48;
        }
        else if (time < 1) {
            time = 1;
        }

        $corp_info_form.find(".salarySmsHours").val(time);
    },
    //公司/集团 信息 - 取消编辑
    corpInfoCancelByModify: function () {
        corp_service_entry.corpInfo();//重新获取 公司/集团 信息
    },
    //公司/集团 - 信息保存
    corpInfoSave: function () {
        //alert($corp_info_form.find(".corp_type_container .is_group").val());
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form

        loadingInit();//加载中 弹框显示

        $corp_info_form.ajaxSubmit({
            url: urlGroup.corp_service_entry_save,
            type: 'post',
            dataType: 'json',
            data: $corp_info_form.fieldSerialize(),
            beforeSend: function () {
            },
            uploadProgress: function (event, position, total, percentComplete) {
                //var percentVal = percentComplete + '%';
            },
            success: function (data) {
                //alert(JSON.stringify(data));

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    corp_service_entry.corpInfo();//重新获取 公司/集团 信息

                }
                else {
                    loadingRemove();//加载中 - 移除logo

                    toastr.error("操作失败：" + data.msg);
                }

            },
            complete: function (xhr) {
            }
        });
    },


    //选择图片事件
    bandImageInput: function (inputSelector, previewSelector) {
        //debugger
        $(previewSelector).unbind("click").bind("click", function () {
            $(inputSelector).click();
        });

        $(inputSelector).change(function (e) {
            //debugger
            var file = e.target.files[0];

            //var imgSrc = URL.createObjectURL(file);
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file);
            }
            else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file);
            }
            else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file);
            }

            var img = "<img src='" + url + "'>";
            $(previewSelector).html(img);

        });
    },

    //底部按钮 初始化
    BtnOperateInit: function () {
        var $corp_info_form = $corp_service_entry_container.find(".corp_container_form");//企业信息 form

        $corp_info_form.find(".btn_operate").find(".btn").hide();

        $corp_info_form.find(".btn_operate").find(".btn").each(function () {
            var $this = $(this);

            var value = $this.attr("data-value");
            if (value & corp_service_entry.btn_value) {
                $this.show();
            }

        });

    }


};

$(document).ready(function () {

    var treeId = ".corp_service_entry_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_service_entry_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_service_tree1_url + "?business_type=" + corp_service_type.entry;//获取树结构 url
    var treeClickFunc = corp_service_entry.groupTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_service_entry.clearCorpInfo;	//清空 内容

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构

    corp_service_entry.init();

});
