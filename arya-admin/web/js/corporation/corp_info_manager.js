/**
 * Created by CuiMengxin on 2017/5/3.
 */

var $corp_info_container = $(".corp_info_container");
var $dept_info_add_modal = $(".dept_info_add_modal");//部门信息 新增弹框
var $corp_basic_info = $corp_info_container.find(".corp_basic_info");//企业基本信息 container
var $dept_info_container = $corp_info_container.find(".dept_info_container");//部门信息 container

// 企业信息管理
var corp_info_manage = {

    tree_list: null,//左侧树结构 数据
    node: "",
    organize_type: "",//组织类型:1集团，2子公司，3通用部门，4一级公司
    organize_id: "",//组织id
    organize_pid: "",//组织pid
    btn_value: "0",//底部按钮 要显示的 值

    Init: function () {

        corp_info_manage.clearCorpInfo();//清空 企业信息 内容

        //新增部门
        $dept_info_add_modal.on("shown.bs.modal", function () {

            $dept_info_add_modal.find(".dept_name_add input").val("");

        });

    },
    //初始化树结构 参数
    initTreeParams: function () {
        //参数 置空
        corp_info_manage.organize_id = "";
        corp_info_manage.organize_pid = "";
        corp_info_manage.organize_type = "";
    },

    //树形菜单 - 点击事件
    groupTreeOnclick: function (node) {
        //alert(JSON.stringify(node));
        loadingInit();//加载中 弹框显示

        //var name = node.full_name;//名称

        $(".corp_content .corp_or_dept_name h5").html(name + "信息管理");

        corp_info_manage.node = node;
        corp_info_manage.organize_id = node.id;//组织id
        corp_info_manage.organize_pid = node.pid ? node.pid : 0;//父级id
        corp_info_manage.organize_type = node.type;//组织类型:1集团，2子公司，3通用部门，4一级公司

        corp_info_manage.checkComSecIsShow();//检查“新增二级公司”按钮，是否可选

        //如果 是 部门
        if (corp_info_manage.organize_type == 3) {
            corp_info_manage.deptInfo();//获取部门信息
        }
        else {
            corp_info_manage.corpInfo();//查询 公司/集团 详情
        }

    },
    //检查“新增二级公司”按钮，是否可选
    checkComSecIsShow: function () {
        var $sec_add = $corp_info_container
            .find(".aryaZtreeContainer .ztree_operate_container")
            .find(".btn_com_sec_add");

        //如果 是 集团，则显示 “新增二级公司”
        if (corp_info_manage.organize_type == 1) {
            $sec_add.addClass("btn-primary").removeClass("btn-default");
        }
        else {
            $sec_add.addClass("btn-default").removeClass("btn-primary");
        }
    },

    //清空 内容
    clearCorpInfo: function () {
        $dept_info_container.hide();//隐藏 部门信息container
        $corp_basic_info.show();//企业信息 显示

        //禁用所有 点击
        $corp_basic_info.find(".editable").attr("disabled", "disabled");
        $corp_basic_info.find(".editable").val("");
        //移除 click 事件
        $corp_basic_info.find(".choose_container .item").unbind("click");
        //隐藏所有按钮
        $corp_basic_info.find(".btn_operate").find(".btn").hide();

    },

    //公司/集团 - 详情获取
    corpInfo: function () {
        corp_info_manage.clearCorpInfo();//清空 企业信息 内容

        var obj = {
            corp_id: corp_info_manage.organize_id
        };
        var url = urlGroup.corp_info_detail_new + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.info("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {

                    var $item = data.result;

                    //var id = $item.id;//

                    //赋值

                    corp_info_param.corpId = corp_info_manage.organize_id;//id
                    corp_info_param.parent_id = corp_info_manage.organize_pid;//公司名称

                    corp_info_param.name = $item.name;//公司名称
                    corp_info_param.short_name = $item.short_name;//公司简称
                    corp_info_param.contact_name = $item.contact_name;//联系人姓名
                    corp_info_param.contact_phone = $item.contact_phone;//联系人电话

                    corp_info_param.com_type = $item.is_group;//公司类型
                    corp_info_param.service_type = $item.business_type;//已开通业务类型
                    corp_info_param.project_type = $item.is_human_pool_project;//项目类型 0 非汇思项目


                    $corp_basic_info.find(".corp_name").val(corp_info_param.name);
                    $corp_basic_info.find(".corp_short_name").val(corp_info_param.short_name);
                    $corp_basic_info.find(".corp_contract").val(corp_info_param.contact_name);
                    $corp_basic_info.find(".corp_contract_phone").val(corp_info_param.contact_phone);

                    corp_info_manage.cropInfoInit();//公司/集团 - 详情 初始化

                }
                else {
                    toastr.info(data.msg);
                }
            },
            function () {
                toastr.error("系统错误，请联系管理员！");
            });

    },
    //公司/集团 - 详情 初始化
    cropInfoInit: function () {

        //如果是 集团/一级公司
        if (corp_info_manage.organize_type == 1 ||
            corp_info_manage.organize_type == 4) {

            //可选类型 显示
            $corp_basic_info.find(".choose_container").show();

            // 公司类型  一级公司 0 , 集团 1
            var $corp_type_container = $corp_basic_info.find(".corp_type_container");
            $corp_type_container.find(".item").each(function () {
                var $this = $(this);

                var val = $this.attr("data-value");//公司类型
                if (val == corp_info_param.com_type) {

                    $this.addClass("btn-primary").removeClass("btn-default");
                    $this.siblings(".item")
                        .removeClass("btn-primary").addClass("btn-default");

                }

            });

            //开通服务
            var $service_container = $corp_basic_info.find(".service_container");
            $service_container.find(".item").each(function () {
                var $this = $(this);

                var val = $this.attr("data-value");//服务类型
                if (val & corp_info_param.service_type) {
                    $this.addClass("btn-primary").removeClass("btn-default");
                }
                else {
                    $this.removeClass("btn-primary").addClass("btn-default");
                }

            });

            // 项目类型  0 非汇思项目, 1 汇思项目
            var $project_type_container = $corp_basic_info.find(".project_type_container");
            $project_type_container.find(".item").each(function () {
                var $this = $(this);

                var val = $this.attr("data-value");//项目类型
                if (val == corp_info_param.project_type) {

                    $this.addClass("btn-primary").removeClass("btn-default");
                    $this.siblings(".item")
                        .removeClass("btn-primary").addClass("btn-default");

                }

            });

        }
        else {	//如果是 二级公司

            //隐藏可选类型
            $corp_basic_info.find(".choose_container").hide();

            ////corp_info_manage.initCorpType();//初始化  (公司/集团 类型)
            //$corp_basic_info.find(".corp_type_container").hide();
            //
            ////corp_info_manage.initServiceType();//初始化 服务类型
            //$corp_basic_info.find(".service_container").hide();
            //
            ////corp_info_manage.initProjectType();//初始化 项目类型
            //$corp_basic_info.find(".project_type_container").hide();

        }

        var $btn_operate = $corp_basic_info.find(".btn_operate");
        //新增通用部门
        var value_2 = $btn_operate.find(".btn_add_dept").attr("data-value");
        //删除
        var value_3 = $btn_operate.find(".btn_del").attr("data-value");
        //编辑
        var value_4 = $btn_operate.find(".btn_modify").attr("data-value");

        //底部 按钮
        if (corp_info_manage.organize_type == 1) {	//集团
            corp_info_manage.btn_value = value_2 | value_3 | value_4;
        }
        if (corp_info_manage.organize_type == 2) {	//子公司
            corp_info_manage.btn_value = value_3 | value_4;
        }
        if (corp_info_manage.organize_type == 4) {	//一级公司
            corp_info_manage.btn_value = value_3 | value_4;
        }

        corp_info_manage.BtnOperateInit();//底部按钮 初始化

        //console.log(corp_info_param);
    },

    //初始化 公司类型 (集团/一级公司)
    initCorpType: function () {

        var $corp_type_container = $corp_basic_info.find(".corp_type_container");

        //企业类型 显示
        $corp_type_container.show();

        $corp_type_container.find(".item").unbind("click").bind("click", function () {

            var $this = $(this);

            //没有选中
            if ($this.hasClass("btn-default")) {
                $this.addClass("btn-primary").removeClass("btn-default")
                    .siblings().removeClass("btn-primary").addClass("btn-default");
            }

            //赋值 公司类型
            corp_info_param.com_type = $corp_type_container
                .find(".btn-primary").attr("data-value");

        });


        ////公司类型 默认集团
        //$corp_info_form.find(".corp_type_container .is_group").val("1");
        //
        ////默认集团 被选中
        //$corp_info_form.find(".corp_type_container .type_group")
        //	.addClass("btn-primary").removeClass("btn-default")
        //	.siblings(".btn").addClass("btn-default").removeClass("btn-primary");


    },
    //初始化 服务类型 (集团/一级公司)
    initServiceType: function () {

        var $service_container = $corp_basic_info.find(".service_container");

        //服务类型 显示
        $service_container.show();

        $service_container.find(".item").unbind("click").bind("click", function () {

            var $this = $(this);

            //已经选中了
            if ($this.hasClass("btn-primary")) {
                $this.removeClass("btn-primary").addClass("btn-default");
            }
            else {	//没有选中
                $this.removeClass("btn-default").addClass("btn-primary");
            }

            corp_info_param.service_type = "0";//业务类型 初始化
            $service_container.find(".btn-primary").each(function () {
                var val = $(this).attr("data-value");
                corp_info_param.service_type = corp_info_param.service_type | val;
            });

        });

        ////服务类型 空值
        //$corp_info_form.find(".service_container .business_type").val("");
        //$corp_info_form.find(".service_container .service_item")
        //	.removeClass("btn-primary").addClass("btn-default");
        //
        //$corp_info_form.find(".service_container .service_item")
        //	.attr("onclick", "info_manage.chooseService(this)");
    },
    //初始化 项目类型  (集团/一级公司)
    initProjectType: function () {
        var $project_type_container = $corp_basic_info.find(".project_type_container");

        //项目类型 显示
        $project_type_container.show();

        $project_type_container.find(".item").unbind("click")
            .bind("click", function () {

                var $this = $(this);

                //没有选中
                if ($this.hasClass("btn-default")) {
                    $this.addClass("btn-primary").removeClass("btn-default")
                        .siblings().removeClass("btn-primary").addClass("btn-default");
                }

                //赋值 项目类型
                corp_info_param.project_type = $project_type_container
                    .find(".btn-primary").attr("data-value");

            });


        ////项目类型 默认 是汇思项目
        //$project_type_container.find(".project_type").val("1");
        //
        ////默认 是汇思项目
        //$project_type_container.find(".is_humanpool_project")
        //	.addClass("btn-primary").removeClass("btn-default")
        //	.siblings(".btn").addClass("btn-default").removeClass("btn-primary");
        //
        ////click 事件
        //$project_type_container.find(".item").attr("onclick", "info_manage.chooseProjectType(this)");

    },
    //底部按钮 初始化
    BtnOperateInit: function () {

        $corp_basic_info.find(".btn_operate").find(".btn").hide();

        $corp_basic_info.find(".btn_operate").find(".btn").each(function () {
            var $this = $(this);

            var value = $this.attr("data-value");
            if (value & corp_info_manage.btn_value) {
                $this.show();
            }

        });

    },

    //公司/集团 - 删除
    corpDel: function () {

        delWarning("确定要删除该集团/公司吗？", function () {

            loadingInit();

            var obj = {};
            obj.id = corp_info_manage.organize_id;

            aryaPostRequest(
                urlGroup.corp_del_new,
                obj,
                function (data) {
                    //alert(JSON.stringify(data));

                    if (data.code == RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");

                        //树形菜单中 删除该节点
                        var treeObj = $.fn.zTree.getZTreeObj("corp_group_tree");//
                        treeObj.removeNode(corp_info_manage.node);

                        corp_info_manage.Init();//初始化

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
    //公司/集团 信息编辑
    corpInfoModify: function () {
        $corp_basic_info.find(".editable").removeAttr("disabled");

        //赋值企业id
        corp_info_param.corpId = corp_info_manage.organize_id;
        corp_info_param.parent_id = corp_info_manage.organize_pid ? corp_info_manage.organize_pid : null;

        //console.log(corp_info_param);

        //如果是 集团/一级公司
        if (corp_info_manage.organize_type == 1 ||
            corp_info_manage.organize_type == 4) {

            corp_info_manage.initCorpType();//初始化  	公司类型
            corp_info_manage.initServiceType();//初始化 服务类型
            corp_info_manage.initProjectType();//初始化 项目类型

        }

        var $btn_operate = $corp_basic_info.find(".btn_operate");
        //保存
        var value_1 = $btn_operate.find(".btn_save").attr("data-value");
        //取消
        var value_2 = $btn_operate.find(".btn_cancel").attr("data-value");

        //底部 按钮
        corp_info_manage.btn_value = value_1 | value_2;

        corp_info_manage.BtnOperateInit();//底部按钮 初始化

    },
    //公司/集团 信息 - 取消编辑
    corpInfoCancelByModify: function () {
        corp_info_manage.corpInfo();//公司/集团 详情获取
    },
    //公司/集团 - 信息保存（新增/编辑）
    corpInfoSave: function () {

        if (!corp_info_manage.checkParamByCorpInfoSave()) {
            return
        }

        loadingInit();//加载中 弹框显示

        var obj = {
            corpId: corp_info_param.corpId,
            parent_id: corp_info_param.parent_id,
            name: corp_info_param.name,
            short_name: corp_info_param.short_name,
            contact_name: corp_info_param.contact_name,
            contact_phone: corp_info_param.contact_phone,
            business_type: corp_info_param.service_type,
            is_group: corp_info_param.com_type,
            is_human_pool_project: corp_info_param.project_type

        };

        //console.log(obj);

        aryaPostRequest(
            urlGroup.corp_add_or_modify_new,
            obj,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    var $item = data.result;

                    var full_name = $item.name;//企业全称
                    var name = full_name.length > 8 ? (full_name.substr(0, 8) + "...") : full_name;
                    var id = $item.corp_id;//企业id
                    var pId = $item.parent_id;//企业pid
                    if (pId == "" || pId == null || pId == "null") {
                        pId = null;
                    }
                    var type = $item.type;//企业类型
                    var iconSkin = pId == null ? ("rootNode_" + type) : ("subNode_" + type);//企业 显示标志

                    var treeObj = $.fn.zTree.getZTreeObj("corp_group_tree");//

                    //新增
                    if (data.result.is_new == 1) {
                        var newNode = {
                            id: id,
                            pId: pId,	//父节点
                            type: type,
                            name: name,
                            full_name: full_name,
                            iconSkin: iconSkin
                        };

                        if (pId == null) {	//根节点
                            newNode = treeObj.addNodes(null, newNode);
                        }
                        else {
                            newNode = treeObj.addNodes(corp_info_manage.node, newNode);
                        }

                        var node = treeObj.getNodeByParam("id", id, null);//查询新增的企业
                        treeObj.selectNode(node);
                        corp_info_manage.groupTreeOnclick(node);//触发click事件

                    }
                    //修改
                    if (data.result.is_new == 0) {
                        var nodes = treeObj.getNodeByTId(corp_info_manage.node.tId);
                        nodes.id = id;
                        nodes.pId = pId;
                        nodes.type = type;
                        nodes.name = name;
                        nodes.full_name = full_name;
                        nodes.iconSkin = iconSkin;

                        treeObj.updateNode(nodes);//

                        corp_info_manage.corpInfo();//公司/集团 - 详情获取（集团、子公司或一级公司）
                    }

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
    //保存信息时 检查参数
    checkParamByCorpInfoSave: function () {
        var flag = false;
        var txt = "";

        corp_info_param.name = $corp_basic_info.find(".corp_name").val();
        corp_info_param.short_name = $corp_basic_info.find(".corp_short_name").val();
        corp_info_param.contact_name = $corp_basic_info.find(".corp_contract").val();
        corp_info_param.contact_phone = $corp_basic_info.find(".corp_contract_phone").val();


        if (!corp_info_param.name) {
            txt = "企业名称不能为空！";
        }
        else if (!corp_info_param.short_name) {
            txt = "企业简称不能为空！";
        }
        else if (!corp_info_param.contact_name) {
            txt = "联系人姓名不能为空！";
        }
        else if (!corp_info_param.contact_phone) {
            txt = "联系方式不能为空！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },


    //初始化 (公司/集团 - 新增内容)
    initCorpAddInfo: function () {

        corp_info_param.initCorpParam();//初始化 企业信息（置空）

        $corp_basic_info.find(".editable").removeAttr("disabled");
        $corp_basic_info.find(".corp_name").val("");		//公司名称
        $corp_basic_info.find(".corp_short_name").val("");//公司简称
        $corp_basic_info.find(".corp_contract").val("");//联系人
        $corp_basic_info.find(".corp_contract_phone").val("");//联系方式

        //底部 显示按钮 "保存"
        corp_info_manage.btn_value = $corp_basic_info.find(".btn_operate")
            .find(".btn_save").attr("data-value");

        corp_info_manage.BtnOperateInit();//底部按钮 初始化

        //console.log(corp_info_param);
    },
    //新增企业
    CorpAdd: function () {
        corp_info_manage.initTreeParams();//初始化树结构 参数
        corp_info_manage.checkComSecIsShow();//检查“新增二级公司”按钮，是否可选

        $(".corp_content .corp_or_dept_name h5").html("新增企业");

        corp_info_manage.initCorpType();//初始化 公司类型 (集团/一级公司)
        corp_info_manage.initServiceType();//初始化 服务类型 (集团/一级公司)
        corp_info_manage.initProjectType();//初始化 项目类型 (集团/一级公司)

        corp_info_manage.initCorpAddInfo();//新增内容 初始化

    },
    //新增二级公司
    CorpAddComSec: function () {
        if (corp_info_manage.organize_type != 1) {	//不是 集团
            toastr.warning("只有集团可以添加二级公司！请先选择集团");
            return
        }

        $(".corp_content .corp_or_dept_name h5").html("新增二级公司");

        //隐藏 可选类型
        $corp_basic_info.find(".choose_container").hide();

        corp_info_manage.initCorpAddInfo();//新增内容 初始化
    },


    //部门 - 新增弹框显示
    deptAddModalShow: function () {
        $dept_info_add_modal.modal({
            backdrop: false,
            keyboard: false
        });
    },
    //部门 - 新增保存
    DeptAddSave: function () {
        var name = $dept_info_add_modal.find(".dept_name_add input").val();
        if (name == "") {
            toastr.warning("部门名称不能为空！");
            $dept_info_add_modal.find(".dept_name_add input").focus();
            return
        }

        $dept_info_add_modal.modal("hide");//谈框隐藏
        loadingInit();//加载中 弹框显示

        var obj = {
            group_id: corp_info_manage.organize_id,
            name: name
        };

        aryaPostRequest(
            urlGroup.corp_dept_add_new,
            obj,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {
                    toastr.success("新增部门成功！");

                    var full_name = $dept_info_add_modal
                        .find(".dept_name_add input").val();
                    var name = full_name.length > 8 ? (full_name.substr(0, 8) + "...") : full_name;
                    var id = data.result.department_id;

                    var treeObj = $.fn.zTree.getZTreeObj("corp_group_tree");//
                    var newNode = {
                        id: id,
                        type: "3",
                        name: name,
                        full_name: full_name,
                        iconSkin: "subNode_3",
                        pId: corp_info_manage.organize_id	//父节点
                    };
                    newNode = treeObj.addNodes(corp_info_manage.node, newNode);

                    var node = treeObj.getNodeByParam("id", id, null);//查询新增的部门
                    treeObj.selectNode(node);//选中该部门
                    corp_info_manage.groupTreeOnclick(node);//触发click事件

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


    //清空 部门信息
    clearDeptInfo: function () {
        $dept_info_container.show();//显示 部门信息container
        $corp_basic_info.hide();//隐藏 企业信息

        //禁用所有
        $dept_info_container.find(".editable").attr("disabled", "disabled");
        $dept_info_container.find(".editable").val("");

    },
    //获取部门对应信息
    deptInfo: function () {
        corp_info_manage.clearDeptInfo();//清空 部门信息

        var obj = {
            department_id: corp_info_manage.organize_id
        };
        var url = urlGroup.corp_dept_info_detail_new + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {

                    var $item = data.result;

                    var dept_name = $item.name;//
                    var dept_create_time = $item.create_time;//
                    dept_create_time = new Date(dept_create_time).toLocaleDateString();
                    var dept_update_time = $item.update_time;//
                    dept_update_time = new Date(dept_update_time).toLocaleDateString();

                    $dept_info_container.find(".dept_name").val(dept_name);
                    $dept_info_container.find(".dept_create_time").val(dept_create_time);
                    $dept_info_container.find(".dept_update_time").val(dept_update_time);

                    corp_info_manage.deptInfoInit();//部门内容 - 初始化

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
    //部门内容 - 初始化
    deptInfoInit: function () {

        var $btn_operate = $dept_info_container.find(".btn_operate");
        $btn_operate.empty();

        var $btn_modify = $("<div>");
        $btn_modify.addClass("btn");
        $btn_modify.addClass("btn-sm");
        $btn_modify.addClass("btn-primary");
        $btn_modify.addClass("btn_modify");
        $btn_modify.bind("click", function () {
            corp_info_manage.deptInfoModify();
        });
        $btn_modify.text("编辑");
        $btn_modify.appendTo($btn_operate);

        var $btn_del = $("<div>");
        $btn_del.addClass("btn");
        $btn_del.addClass("btn-sm");
        $btn_del.addClass("btn-danger");
        $btn_del.addClass("btn_modify");
        $btn_del.bind("click", function () {
            corp_info_manage.deptInfoDel();
        });
        $btn_del.text("删除");
        $btn_del.appendTo($btn_operate);

    },

    //部门内容编辑
    deptInfoModify: function () {
        $dept_info_container.find(".dept_name").removeAttr("disabled");

        var dept_name = $dept_info_container.find(".dept_name").val();
        $dept_info_container.find(".dept_name").attr("data-name", dept_name);
        $dept_info_container.find(".dept_name").focus();

        var $btn_operate = $dept_info_container.find(".btn_operate");
        $btn_operate.empty();

        var $btn_save = $("<div>");
        $btn_save.addClass("btn");
        $btn_save.addClass("btn-sm");
        $btn_save.addClass("btn-primary");
        $btn_save.addClass("btn_save");
        $btn_save.bind("click", function () {
            corp_info_manage.deptInfoSaveByModify();
        });
        $btn_save.text("保存");
        $btn_save.appendTo($btn_operate);

        var $btn_cancel = $("<div>");
        $btn_cancel.addClass("btn");
        $btn_cancel.addClass("btn-sm");
        $btn_cancel.addClass("btn-primary");
        $btn_cancel.addClass("btn_cancel");
        $btn_cancel.bind("click", function () {
            corp_info_manage.deptInfoCancelByModify();
        });
        $btn_cancel.text("取消");
        $btn_cancel.appendTo($btn_operate);

    },
    //部门内容编辑后 -  取消
    deptInfoCancelByModify: function () {

        var dept_name = $dept_info_container.find(".dept_name").attr("data-name");
        $dept_info_container.find(".dept_name").val(dept_name);
        $dept_info_container.find(".dept_name").attr("disabled", "disabled");

        corp_info_manage.deptInfoInit();//部门内容 - 初始化

    },
    //部门内容编辑后 -  保存
    deptInfoSaveByModify: function () {
        var name = $dept_info_container.find(".dept_name").val();
        if (name == "") {
            toastr.warning("部门名称不能为空！");
            $dept_info_container.find(".dept_name").focus();
            return
        }

        loadingInit();//加载中 弹框显示

        var obj = {
            department_id: corp_info_manage.organize_id,
            name: name
        };

        aryaPostRequest(
            urlGroup.corp_dept_info_modify_new,
            obj,
            function (data) {

                if (data.code == RESPONSE_OK_CODE) {
                    toastr.success("修改名称成功！");

                    var treeObj = $.fn.zTree.getZTreeObj("corp_group_tree");//
                    var nodes = treeObj.getNodeByTId(corp_info_manage.node.tId);
                    nodes.name = $dept_info_container.find(".dept_name").val();
                    treeObj.updateNode(nodes);

                    corp_info_manage.deptInfo();//获取部门对应信息

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

    //部门 - 删除
    deptInfoDel: function () {

        delWarning("确定要删除该部门吗？", function () {
            loadingInit();

            var obj = {};
            obj.department_id = corp_info_manage.organize_id;

            aryaPostRequest(
                urlGroup.corp_dept_del_new,
                obj,
                function (data) {
                    //alert(JSON.stringify(data));

                    if (data.code == RESPONSE_OK_CODE) {
                        toastr.success("删除成功！");

                        //树形菜单中 删除该节点
                        var treeObj = $.fn.zTree.getZTreeObj("corp_group_tree");//
                        treeObj.removeNode(corp_info_manage.node);

                        corp_info_manage.Init();//初始化

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

    }


};

//企业信息 管理 参数
var corp_info_param = {

    service_type: "",//业务类型
    com_type: "",//公司类型 		集团 1  	 一级公司 0
    project_type: "",//项目类型 	汇思项目 1   非汇思项目 0

    corpId: null,
    parent_id: null,
    name: "",
    short_name: "",
    contact_name: "",
    contact_phone: "",

    //初始化 企业信息（置空）
    initCorpParam: function () {

        corp_info_param.name = "";
        corp_info_param.short_name = "";
        corp_info_param.contact_name = "";
        corp_info_param.contact_phone = "";

        //如果 是新增 集团/一级公司
        if (!corp_info_manage.organize_type) {

            corp_info_param.corpId = null;
            corp_info_param.parent_id = null;

            corp_info_param.service_type = "";
            corp_info_param.com_type = "1";
            corp_info_param.project_type = "1";

            //服务列表 置空
            $corp_basic_info.find(".service_container .item")
                .addClass("btn-default").removeClass("btn-primary");

            //公司类型 默认集团
            $corp_basic_info.find(".corp_type_container .item").each(function () {
                var $this = $(this);

                var val = $this.attr("data-value");
                if (val == corp_info_param.com_type) {	//如果是 默认项
                    $this.addClass("btn-primary").removeClass("btn-default");
                }
                else {
                    $this.addClass("btn-default").removeClass("btn-primary");
                }

            });

            //项目类型 默认汇思项目
            $corp_basic_info.find(".project_type_container .item").each(function () {
                var $this = $(this);

                var val = $this.attr("data-value");
                if (val == corp_info_param.project_type) {	//如果是 默认项
                    $this.addClass("btn-primary").removeClass("btn-default");
                }
                else {
                    $this.addClass("btn-default").removeClass("btn-primary");
                }

            });


        }

        //如果新增二级公司
        if (corp_info_manage.organize_type) {

            corp_info_param.corpId = null;
            corp_info_param.parent_id = corp_info_manage.organize_id;

            corp_info_param.com_type = "0";//公司类型  非集团

        }

    }

};

$(document).ready(function () {

    var treeId = ".corp_info_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_info_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_group_tree_url;//获取树结构 url
    var treeClickFunc = corp_info_manage.groupTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_info_manage.clearCorpInfo;//清空 企业信息 内容

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构

    corp_info_manage.Init();

});

