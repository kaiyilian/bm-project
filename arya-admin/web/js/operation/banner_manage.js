/**
 * Created by Administrator on 2016/6/12.
 * 广告管理
 */

var $advert_manage = $(".advertise_manage_container");
var $advert_info_modal = $(".advert_info_modal");

var advert_manage = {

    currentPage: 1,    //当前页数
    totalPage: 0,      //总页数
    containerName: "",
    advertData: null,//广告列表 data
    jumpTypeMap: null,//跳转类型
    advert_id: "",//广告 id

    //页面初始化
    init: function () {

        advert_manage.initJumpType();  //初始化 跳转类型
        advert_manage.advertList();  //获取 广告列表

        $advert_info_modal.on("show.bs.modal", function () {

            var $modal_body = $advert_info_modal.find(".modal-body");
            $advert_info_modal.find("h4").text("新增广告");

            //缩略图
            var img = "<img src='img/icon_advert_img_add.png' />";
            $modal_body.find(".advert_img_add").attr("data-fileName", "").html(img);

            //跳转类型 列表
            var $jumpType = $modal_body.find(".jump_url_type_add select");
            $jumpType.empty();
            for (var i = 0; i < advert_manage.jumpTypeMap.keySet().length; i++) {
                var key = advert_manage.jumpTypeMap.keySet()[i];
                var val = advert_manage.jumpTypeMap.get(key);

                var $option = $("<option>");
                $option.attr("data-id", key);
                $option.attr("value", key);
                $option.text(val);
                $option.appendTo($jumpType);

            }

            //设备类型
            var btnList =
                "<button id='adv_android' class='btn btn-sm btn-default btn_ios' data-id='1'>android</button>" +
                "<button id='adv_ios' class='btn btn-sm btn-default btn_android' data-id='2'>ios</button>" +
                "<button id='adv_winphone' class='btn btn-sm btn-default btn_winphone' data-id='4'>winphone</button>" +
                "<button id='adv_wechat' class='btn btn-sm btn-default btn_wechat' data-id='8'>wechat</button>";

            $modal_body.find(".device_type_add").html(btnList);
            $modal_body.find(".device_type_add .btn").each(function () {
                $(this).click(function () {

                    if ($(this).hasClass("active")) {
                        $(this).removeClass("active").addClass("btn-default").removeClass("btn-primary");
                    }
                    else {
                        $(this).addClass("active").addClass("btn-primary").removeClass("btn-default");
                    }
                });

            });

            //是否 启用
            $modal_body.find(".isEnable_add input:checkbox").attr("checked", "checked");

            $modal_body.find(".hint_add input").val("");
            $modal_body.find(".jump_url_add input").val("");
            $modal_body.find(".min_version_add input").val("");
            $modal_body.find(".max_version_add input").val("");

            //删除按钮
            $advert_info_modal.find("#adv_delete").addClass("arya-hide");

        });

    },
    //初始化 跳转类型
    initJumpType: function () {
        advert_manage.jumpTypeMap = new Map();

        //advert_manage.jumpTypeMap.put("0", "无");
        advert_manage.jumpTypeMap.put("1", "跳首页");
        advert_manage.jumpTypeMap.put("2", "跳设置页面");
        advert_manage.jumpTypeMap.put("3", "系统消息");
        advert_manage.jumpTypeMap.put("10", "内嵌浏览器显示Web页面");
        advert_manage.jumpTypeMap.put("20", "跳查社保页面");
        advert_manage.jumpTypeMap.put("30", "跳个人代缴页面");
        advert_manage.jumpTypeMap.put("31", "跳个人代缴订单管理页面");
        advert_manage.jumpTypeMap.put("32", "跳参保人管理页面");
        advert_manage.jumpTypeMap.put("33", "跳企业代缴页面");
        advert_manage.jumpTypeMap.put("34", "跳”如何在招才进宝缴纳社保”页面");
        advert_manage.jumpTypeMap.put("40", "跳薪资查询页面");
        advert_manage.jumpTypeMap.put("50", "跳招聘广告页面");
        advert_manage.jumpTypeMap.put("60", "跳培训课程页面");
        advert_manage.jumpTypeMap.put("73", "跳“如何一键入职”介绍页面");
        advert_manage.jumpTypeMap.put("80", "跳福库");
        advert_manage.jumpTypeMap.put("100", "跳体检");

    },
    //设备类型 根据 类型值
    initDeviceType: function (id) {
        //alert(2 & 255)
        //1ios,2 android,4 winphone,8 wechat,255所有设备

        var deviceType = "";
        if ((1 & id) > 0) {
            deviceType += deviceType == "" ? "android" : ",android";
        }
        if ((2 & id) > 0) {
            deviceType += deviceType == "" ? "ios" : ",ios";
        }
        if ((4 & id) > 0) {
            deviceType += deviceType == "" ? "winphone" : ",winphone";
        }
        if ((8 & id) > 0) {
            deviceType += deviceType == "" ? "wechat" : ",wechat";
        }

        return deviceType;
    },

    //清空 广告列表
    clearAdvertList: function () {
        var $tbody = $advert_manage.find(".table_container table tbody");

        var msg = "<tr><td colspan='10'>暂无广告</td></tr>";
        $tbody.html(msg);
    },
    //获取 广告列表
    advertList: function () {
        advert_manage.clearAdvertList();//清空 广告列表
        var $tbody = $advert_manage.find(".table_container table tbody");

        loadingInit();

        var obj = {
            page: advert_manage.currentPage,
            page_size: "10"
        };
        var url = urlGroup.advert_list + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data))
                //console.info("广告列表：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    advert_manage.totalPage = data.result.pages ? data.result.pages : 1;
                    advert_manage.advertData = data.result.ads;

                    var list = "";
                    if (!advert_manage.advertData || advert_manage.advertData.length === 0) {
                    }
                    else {

                        for (var i = 0; i < advert_manage.advertData.length; i++) {
                            var item = advert_manage.advertData[i];

                            var id = item.id;//广告id
                            var advert_img_url = item.pic_url;//图片url
                            var file_name = item.file_name;//图片url name
                            var advert_hint = item.hint;//广告提示
                            var advert_isEnable = item.active;//0表示否，1表示是
                            var advert_isEnable_msg = advert_isEnable == 1 ? "启用" : "禁用";
                            var advert_jump_url = item.jump_url;//跳转URL
                            var advert_jump_type_id = item.jump_type;//内容跳转类型
                            var advert_jump_type = advert_manage.jumpTypeMap.get(advert_jump_type_id);
                            advert_jump_type = advert_jump_type ? advert_jump_type : "";
                            var advert_device_type_id = item.device_type;//目标设备类型 值
                            var advert_device_type = advert_manage.initDeviceType(advert_device_type_id);
                            advert_device_type = advert_device_type ? advert_device_type : "";
                            var advert_district = item.distrcit;//目标地区
                            var advert_min_version = item.min_version ? item.min_version : "";//最低版本
                            var advert_max_version = item.max_version ? item.max_version : "";//最高版本

                            list +=
                                "<tr class='advert_item item' " +
                                "data-id='" + id + "'" +
                                "data-file_name='" + file_name + "'" +
                                "data-isenable='" + advert_isEnable + "'" +
                                "data-jumpTypeId='" + advert_jump_type_id + "'" +
                                "data-deviceTypeId='" + advert_device_type_id + "'" +
                                ">" +
                                "<td class='thumb_img'>" +
                                "<img src='" + advert_img_url + "'>" +
                                "</td>" +
                                "<td class='hint'>" + advert_hint + "</td>" +
                                "<td class='isEnable'>" +
                                "<div>" + advert_isEnable_msg + "</div>" +
                                "</td>" +
                                "<td class='url'>" +
                                "<div>" + advert_jump_url + "</div>" +
                                "</td>" +
                                "<td class='url_type'>" + advert_jump_type + "</td>" +
                                "<td class='device_type'>" +
                                "<div>" + advert_device_type + "</div>" +
                                "</td>" +
                                "<td class='district'>" + advert_district + "</td>" +
                                "<td class='min'>" + advert_min_version + "</td>" +
                                "<td class='max'>" + advert_max_version + "</td>" +
                                "<td class='operate'></td>" +
                                "</tr>";

                            ////禁用
                            //if (advert_isEnable == 0) {
                            //	advertItem.find(".btn_disable").hide();
                            //}
                            //else {
                            //	advertItem.find(".btn_enable").hide();
                            //}
                            //
                            ////第一页 第一条
                            //if (i == 0 && obj.page == 1) {
                            //	advertItem.find(".btn_up").hide();
                            //}
                            //
                            //if (i == (advert_manage.advertData.length - 1)
                            //	&& obj.page == advert_manage.totalPage) {
                            //	advertItem.find(".btn_down").hide();
                            //}

                        }

                        $tbody.html(list);
                        advert_manage.advertListInit();//广告列表 初始化

                    }

                }
                else {
                    // alert(data.msg)
                }

            },
            function (error) {

            }
        );

    },
    //广告列表 初始化
    advertListInit: function () {

        var $table_container = $advert_manage.find(".table_container");
        var $item = $table_container.find("tbody .item");
        var $pager_container = $advert_manage.find(".pager_container");

        if ($item.length == 0) {
            $pager_container.hide();
        }
        else {
            $item.each(function () {

                var $this = $(this);
                var is_enable = parseInt($this.attr("data-isEnable"));
                console.log(is_enable);
                var $operate = $this.find(".operate");
                $operate.empty();

                //向上
                var $btn_up = $("<div>");
                $btn_up.addClass("btn");
                $btn_up.addClass("btn-info");
                $btn_up.addClass("btn-sm");
                $btn_up.addClass("btn_up");
                $btn_up.unbind("click").bind("click", function () {
                    advert_manage.AdvertModifyByMove(this, 1);
                });
                $btn_up.text("上");
                $btn_up.appendTo($operate);

                //向下
                var $btn_down = $("<div>");
                $btn_down.addClass("btn");
                $btn_down.addClass("btn-info");
                $btn_down.addClass("btn-sm");
                $btn_down.addClass("btn_down");
                $btn_down.unbind("click").bind("click", function () {
                    advert_manage.AdvertModifyByMove(this, 2);
                });
                $btn_down.text("下");
                $btn_down.appendTo($operate);

                //启用
                var $btn_enable = $("<div>");
                $btn_enable.addClass("btn");
                $btn_enable.addClass("btn-primary");
                $btn_enable.addClass("btn-sm");
                $btn_enable.addClass("btn_enable");
                $btn_enable.unbind("click").bind("click", function () {
                    advert_manage.AdvertModifyByIsEnable(this, 1);
                });
                $btn_enable.text("启用");
                $btn_enable.appendTo($operate);

                //禁用
                var $btn_disable = $("<div>");
                $btn_disable.addClass("btn");
                $btn_disable.addClass("btn-danger");
                $btn_disable.addClass("btn-sm");
                $btn_disable.addClass("btn_disable");
                $btn_disable.unbind("click").bind("click", function () {
                    advert_manage.AdvertModifyByIsEnable(this, 0);
                });
                $btn_disable.text("禁用");
                $btn_disable.appendTo($operate);

                //编辑
                var $btn_modify = $("<div>");
                $btn_modify.addClass("btn");
                $btn_modify.addClass("btn-primary");
                $btn_modify.addClass("btn-sm");
                $btn_modify.addClass("btn_modify");
                $btn_modify.unbind("click").bind("click", function () {
                    advert_manage.ShowEditAdvertModal(this);
                });
                $btn_modify.text("编辑");
                $btn_modify.appendTo($operate);

                if (is_enable) {
                    $operate.find(".btn_enable").remove();
                }
                else {
                    $operate.find(".btn_disable").remove();
                }

                //第一页 第一条
                if ($this.index() == 0 && advert_manage.currentPage == 1) {
                    $operate.find(".btn_up").hide();
                }

                //第一页 第一条
                if ($this.index() == ($item.length - 1) &&
                    advert_manage.currentPage == advert_manage.totalPage) {
                    $operate.find(".btn_down").hide();
                }

            });

            var options = {
                bootstrapMajorVersion: 3, //版本  3是ul  2 是div
                //containerClass:"sdfsaf",
                //size: "small",//大小
                alignment: "right",//对齐方式
                currentPage: advert_manage.currentPage, //当前页数
                totalPages: advert_manage.totalPage, //总页数
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

                    advert_manage.currentPage = page;
                    advert_manage.advertList();//

                }
            };

            var ul = '<ul class="pagenation" style="float:right;"></ul>';
            $pager_container.show();
            $pager_container.html(ul);
            $pager_container.find(".pagenation").bootstrapPaginator(options);
        }
    },

    //移动位置后 调用接口
    AdvertModifyByMove: function (self, direction) {

        //参数 赋值
        advert_param.advertParamSet(self);

        var obj = {
            id: advert_param.id,
            file_name: advert_param.file_name,
            jump_type: advert_param.jump_type,
            device_type: advert_param.device_type,
            hint: advert_param.hint,
            jump_url: advert_param.jump_url,
            direction: direction,
            min_version: advert_param.min_version,
            max_version: advert_param.max_version,
            active: advert_param.active
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.advert_add_or_modify,
            obj,
            function (data) {
                //alert(JSON.stringify(data))
                if (data.code == 1000) {
                    advert_manage.init();// 初始化

                    toastr.success("操作成功!");
                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (error) {

            }
        );

    },
    //改变状态后 调用接口
    AdvertModifyByIsEnable: function (self, isEnable) {

        //参数 赋值
        advert_param.advertParamSet(self);

        var obj = {
            id: advert_param.id,
            file_name: advert_param.file_name,
            jump_type: advert_param.jump_type,
            device_type: advert_param.device_type,
            hint: advert_param.hint,
            jump_url: advert_param.jump_url,
            direction: advert_param.direction,
            min_version: advert_param.min_version,
            max_version: advert_param.max_version,
            active: isEnable
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.advert_add_or_modify,
            obj,
            function (data) {
                if (data.code == 1000) {
                    advert_manage.init();// 初始化
                    if (isEnable == 0) {
                        toastr.success("禁用成功!");
                    }
                    else {
                        toastr.success("启用成功!");
                    }
                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (error) {

            }
        );
    },

    //新增广告 弹框显示
    AdvertAddModalShow: function () {

        //参数初始化
        advert_param.init();

        $advert_info_modal.modal("show");

    },
    //上传图片 - 按钮点击
    ChooseImgClick: function () {
        if ($advert_info_modal.find(".upload_img")) {
            $advert_info_modal.find(".upload_img").remove();
        }

        var form = $("<form>");
        form.addClass("upload_img");
        form.attr("enctype", "multipart/form-data");
        form.appendTo($advert_info_modal);
        //form.attr("method", "get");
        form.hide();

        var input = $("<input>");
        input.attr("type", "file");
        input.attr("name", "pic_file");
        input.attr("onchange", "advert_manage.ChooseFile(this)");
        input.appendTo(form);

        input.click();

    },
    //选择文件 - 弹框显示
    ChooseFile: function (self) {
        //alert(self.files)
        if (self.files) {
            for (var i = 0; i < self.files.length; i++) {
                var file = self.files[i];

                //判断是否是图片格式
                if (/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG|BMP)$/.test(file.name)) {

                    $advert_info_modal.find(".upload_img").ajaxSubmit({
                        url: urlGroup.advert_img_upload,
                        type: 'post',
                        success: function (data) {
                            //alert(JSON.stringify(data))

                            if (data.code == 1000) {
                                var url = data.result.url;//图片URL
                                var name = data.result.file_name;//图片名称

                                var img = "<img src='" + url + "' />";

                                $advert_info_modal.find(".modal-body").find(".advert_img_add")
                                    .attr("data-filename", name).html(img);
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

    //编辑
    ShowEditAdvertModal: function (self) {
        //参数 赋值
        advert_param.advertParamSet(self);

        $advert_info_modal.modal("show");
        $advert_info_modal.find("#adv_delete").removeClass("arya-hide");

        var $item = $(self).closest(".item");

        $advert_info_modal.find("h4").text("编辑广告");

        $advert_info_modal.find(".advert_img_add img").attr("src", $item.find(".thumb_img img").attr("src"));
        $advert_info_modal.find(".advert_img_add").attr("data-filename", advert_param.file_name);
        $advert_info_modal.find(".hint_add > input").val(advert_param.hint);
        $advert_info_modal.find(".jump_url_add > input").val(advert_param.jump_url);
        $advert_info_modal.find("#jump_type_select").val(advert_param.jump_type);
        $advert_info_modal.find(".min_version_add > input").val(advert_param.min_version);
        $advert_info_modal.find(".max_version_add > input").val(advert_param.max_version);

        if (advert_param.active == 1) {
            $advert_info_modal.find(".isEnable_add input:checkbox").attr("checked", "checked");
        }
        else {
            $advert_info_modal.find(".isEnable_add input:checkbox").removeAttr("checked");
        }

        var deviceType = advert_param.device_type;
        if ((1 & deviceType) > 0) {
            $("#adv_android").addClass("active").addClass("btn-primary")
                .removeClass("btn-default");
        }
        if ((2 & deviceType) > 0) {
            $("#adv_ios").addClass("active").addClass("btn-primary").removeClass("btn-default");
        }
        if ((4 & deviceType) > 0) {
            $("#adv_winphone").addClass("active").addClass("btn-primary").removeClass("btn-default");
        }
        if ((8 & deviceType) > 0) {
            $("#adv_wechat").addClass("active").addClass("btn-primary").removeClass("btn-default");
        }


    },

    //保存 新增广告
    AdvertAddSave: function () {
        //debugger
        if (!advert_manage.CheckParamByAdvertAdd()) {
            return;
        }

        loadingInit();

        var obj = {
            id: advert_param.id,
            file_name: advert_param.file_name,
            jump_type: advert_param.jump_type,
            device_type: advert_param.device_type,
            hint: advert_param.hint,
            jump_url: advert_param.jump_url,
            direction: advert_param.direction,
            min_version: advert_param.min_version,
            max_version: advert_param.max_version,
            active: advert_param.active
        };


        //var obj = new Object();
        //obj.id = advId;//'广告id',有则修改无则新增广告
        //obj.file_name = $adverAdd.find(".advert_img_add").attr("data-filename");//广告文件名
        //obj.hint = $adverAdd.find(".hint_add input").val();//提示
        //obj.jump_url = $adverAdd.find(".jump_url_add input").val();//跳转URL
        //obj.jump_type = $adverAdd.find(".jump_url_type_add select option:selected")
        //	.attr("data-id");//参考内容跳转类型定义
        //obj.device_type = device_type_count;//编号的或值,1ios,2android,4winphone,8wechat,255所有设备
        //obj.direction = "";//'广告位移方向'，1向上，2向下，不移动则不填
        //obj.min_version = $adverAdd.find(".min_version_add input").val();//'app最低版本',不填则不限制
        //obj.max_version = $adverAdd.find(".max_version_add input").val();//'app最高版本',不填则不限制
        //obj.active = $adverAdd.find("#fixednavbar").is(":checked") ? 1 : 0;//'是否启用',0表示否，1表示是

        aryaPostRequest(
            urlGroup.advert_add_or_modify,
            obj,
            function (data) {
                //alert(JSON.stringify(data))

                if (data.code == 1000) {
                    advert_manage.init();//
                    toastr.success("操作成功!");
                    $advert_info_modal.modal("hide");

                }
                else {
                    toastr.error(data.msg);
                }
            },
            function (error) {

            }
        );
    },
    //检查 新增的广告参数
    CheckParamByAdvertAdd: function () {
        var flag = false;
        var txt = "";

        var $modal_body = $advert_info_modal.find(".modal-body");

        //设备类型
        var device_type_count = 0;
        if ($modal_body.find(".device_type_add .btn.active").length ==
            $modal_body.find(".device_type_add .btn").length) {
            device_type_count = 255;
        }
        else {
            for (var i = 0; i < $modal_body.find(".device_type_add .btn.active").length; i++) {
                var id = $modal_body.find(".device_type_add .btn.active").eq(i).attr("data-id");
                device_type_count += parseInt(id);
            }
        }

        advert_param.file_name = $modal_body.find(".advert_img_add").attr("data-filename");
        advert_param.jump_type = $modal_body.find(".jump_url_type_add select option:selected").val();
        advert_param.device_type = device_type_count;
        advert_param.active = $modal_body.find("#fixednavbar").is(":checked") ? 1 : 0;

        advert_param.hint = $modal_body.find(".hint_add input").val();
        advert_param.jump_url = $modal_body.find(".jump_url_add input").val();
        advert_param.direction = "";
        advert_param.min_version = $modal_body.find(".min_version_add input").val();
        advert_param.max_version = $modal_body.find(".max_version_add input").val();


        if (advert_param.file_name == "") {
            txt = "请上传图片";
        }
        else if (advert_param.hint == "") {
            txt = "请输入hint提示";
        }
        else if (advert_param.jump_url == "") {
            txt = "请输入跳转URL";
        }
        else if (advert_param.device_type == 0) {
            txt = "请选择设备类型";
        }
        else {
            flag = true;
        }


        if (txt != "")
            toastr.warning(txt);

        return flag;

    },


    //删除 广告
    deleteAdv: function () {

        delWarning("确定要删除该条广告吗?", function () {
            loadingInit();

            var obj = {
                id: advert_param.id
            };

            aryaPostRequest(
                urlGroup.advert_del,
                obj,
                function (data) {

                    if (data.code == ERR_CODE_OK) {
                        toastr.success("删除成功!");
                        advert_manage.advertList();
                        $advert_info_modal.modal("hide");
                    }
                    else {
                        toastr.error(data.msg);
                    }
                },
                function (data) {
                    $advert_info_modal.modal("hide");
                }
            );
        });

    }

};

var advert_param = {

    id: "",
    file_name: "",
    jump_type: "",
    device_type: "",
    hint: "",
    jump_url: "",
    direction: "",
    min_version: "",
    max_version: "",
    active: "",

    init: function () {
        advert_param.id = "";
        advert_param.file_name = "";
        advert_param.jump_type = "";
        advert_param.device_type = "";
        advert_param.active = "";

        advert_param.hint = "";
        advert_param.jump_url = "";
        advert_param.direction = "";
        advert_param.min_version = "";
        advert_param.max_version = "";
    },

    //参数 赋值
    advertParamSet: function (self) {

        advert_param.init();

        var $item = $(self).closest(".item");

        advert_param.id = $item.attr("data-id");
        advert_param.file_name = $item.attr("data-file_name");
        advert_param.jump_type = $item.attr("data-jumptypeid");
        advert_param.device_type = $item.attr("data-devicetypeid");
        advert_param.active = $item.attr("data-isenable");

        advert_param.hint = $item.find(".hint").text();
        advert_param.jump_url = $item.find(".url").text();
        advert_param.direction = "";
        advert_param.min_version = $item.find(".min").text();
        advert_param.max_version = $item.find(".max").text();

    }


};

$(function () {
    advert_manage.init();
});