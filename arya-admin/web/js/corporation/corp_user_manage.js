/**
 * Created by Administrator on 2018/1/2.
 * corp_user_manage.js
 */

var $corp_user_container = $(".corp_user_container");//企业用户管理
var $tb_corp_manager = $corp_user_container.find("#tb_corp_manager");//
var $corp_user_info_modal = $("#corp_user_info_modal");//用户信息弹框
var $corp_user_permission_modal = $(".corp_user_permission_modal");//权限

var corp_user_manage = {

    cur_corp_id: "",//当前公司id

    //初始化
    init: function () {

        corp_user_manage.initTbCorpManager([]);//初始化 管理员列表
        corp_user_manage.emptyQRcode();//清空入职码和二维码

        //新增用户 弹框出现后
        $corp_user_info_modal.on("show.bs.modal", function () {
            $corp_user_info_modal.find(".modal-title").text("新增管理员");
            $corp_user_info_modal.find(":input").val("");
        });

        //用户权限 弹框显示
        $corp_user_permission_modal.unbind("show.bs.modal").on("show.bs.modal", function () {

            var obj = {
                corp_user_id: corp_user_info.user_id
            };
            var url = urlGroup.corp_user_permission + "?" + jsonParseParam(obj);

            loadingInit();

            aryaGetRequest(
                url,
                function (data) {

                    if (data.code === RESPONSE_OK_CODE) {

                        if (data.result && data.result.length > 0) {

                            var treeId = $corp_user_permission_modal.find("#corp_user_permission");
                            var organizationList = [];
                            // data.result = [
                            //     {
                            //         id: "1",
                            //         name: "父级_1",
                            //         permissionCode: "",
                            //         permDesc: "",
                            //         parentId: "0",
                            //         selected: 2
                            //     },
                            //     {
                            //         id: "2",
                            //         name: "父级_2",
                            //         permissionCode: "",
                            //         permDesc: "",
                            //         parentId: "0",
                            //         selected: 1
                            //     },
                            //     {
                            //         id: "3",
                            //         name: "子节点子节点子节点子",
                            //         permissionCode: "aaaaaaaa",
                            //         permDesc: "",
                            //         parentId: "1",
                            //         selected: 0
                            //     },
                            //     {
                            //         id: "4",
                            //         name: "子节点_2",
                            //         permissionCode: "fdsafsdafdsafsa",
                            //         permDesc: "防守打法",
                            //         parentId: "1",
                            //         selected: 1
                            //     }
                            // ];

                            $.each(data.result, function (index, item) {

                                var id = item.id ? item.id : "";//
                                var name = item.name ? item.name : "";//
                                var permissionCode = item.permissionCode ? item.permissionCode : "";//
                                var permDesc = item.permDesc ? item.permDesc : "";//
                                var parentId = item.parentId ? item.parentId : 0;//
                                var selected = item.selected ? item.selected : 0;//0:未选中 1:选中 2:半选
                                var checked = selected === 1;

                                var full_name = "<div class='perm_name'>" + name + "</div>" +
                                    // "<div class='perm_icon'>---</div>" +
                                    "<div class='perm_code'>" + permissionCode + "</div>" +
                                    // "<div class='perm_icon'>---</div>" +
                                    "<div class='perm_desc'>" + permDesc + "</div>";

                                var obj = {
                                    id: id,
                                    title: name,
                                    name: name,
                                    fullName: full_name,
                                    pId: parentId,
                                    permDesc: permDesc,
                                    permissionCode: permissionCode,
                                    selected: selected,
                                    checked: checked
                                };

                                organizationList.push(obj);

                            });

                            corp_user_manage.showTree(treeId, organizationList);
                        }
                    }
                    else {
                        toastr.warning(data.msg);
                    }

                },
                function (error) {
                    messageCue(error);
                }
            );

        });

    },
    //显示树结构
    showTree: function (treeId, organizationList) {

        var zNodes = organizationList;
        var setting = {
            view: {
                selectedMulti: false,//禁止选择多项
                dblClickExpand: false,//双击节点 不切换 展开状态
                //addHoverDom: department.addHoverDom,//当鼠标移动到节点上时，显示用户自定义控件
                //removeHoverDom: department.removeHoverDom //当鼠标移出到节点时，隐藏用户自定义控件
                showIcon: false,    //是否显示icon
                nameIsHTML: true,   //name是否支持HTML脚本
            },
            edit: {
                enable: false,//是否可编辑
                editNameSelectAll: true,//txt 内容是否为全选状态
                showRemoveBtn: true,
                removeTitle: "删除节点",
                showRenameBtn: true,
                renameTitle: "编辑节点名称"
            },
            data: {
                simpleData: {   //简单数据格式
                    enable: true
                },
                key: {
                    title: "title",
                    name: "fullName"
                }
            },
            check: {
                enable: true,           //true / false 分别表示 显示 / 不显示 复选框或单选框
                chkStyle: "checkbox",   //
                chkboxType: {"Y": "", "N": ""}, //"p" 表示操作会影响父级节点；"s" 表示操作会影响子级节点。
                // chkboxType: {"Y": "s", "N": "ps"}, //"p" 表示操作会影响父级节点；"s" 表示操作会影响子级节点。
                autoCheckTrigger: true      //关联勾选时是否触发 beforeCheck / onCheck 事件回调函数
            },
            callback: {

                onClick: function (event, treeId, treeNode) {
                    console.log("当前节点selected值：" + treeNode.selected);
                    // if (onclickFunc) {
                    //     onclickFunc(treeNode);//点击事件回调
                    // }
                },

                beforeCheck: function (treeId, treeNode) {

                    // console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
                    // console.log(treeNode.getParentNode());
                    // console.log(treeNode.getParentNode().getCheckStatus());

                },

                onCheck: function (event, treeId, treeNode) {
                    return;

                    console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);

                    var node = treeNode;//当前选中的节点
                    var level = node.level;//当前节点的层级
                    var treeObj = $.fn.zTree.getZTreeObj("corp_user_permission");

                    /**
                     *  将检查的节点下的子节点selected值都改为 指定的值
                     *  opt_node:检查的节点
                     *  selected:0 取消选中 1 选中
                     * */
                    var updateNode = function (opt_node, selected) {

                        //节点自身 改变状态
                        node.selected = selected;
                        treeObj.updateNode(node);

                        if (opt_node.isParent) {

                            for (var i = 0; i < opt_node.children.length; i++) {

                                opt_node.children[i].selected = selected;
                                treeObj.updateNode(opt_node);
                                updateNode(opt_node.children[i]);

                            }

                        }

                    };

                    /**
                     *  判断当前节点是否选中
                     *  如果已经选中了，则当前节点的selected值改为1，且子节点的selected值都改为1
                     *  如果取消选中了，则当前节点的selected值改为0，且子节点的selected值都改为0
                     * */
                    if (node.checked) {
                        // node.selected = 1;
                        // treeObj.updateNode(node);

                        updateNode(node, 1);//将当前节点下的子节点selected值都改为1

                    }
                    else {

                        // node.selected = 0;
                        // treeObj.updateNode(node);

                        updateNode(node, 0);//将当前节点下的子节点selected值都改为0

                    }

                    // 如果不是根节点
                    if (level > 0) {

                        for (var i = 0; i < level; i++) {

                            node = node.getParentNode();
                            if (node) {

                                /**
                                 * -1   不存在子节点 或 子节点全部设置为 nocheck = true
                                 * 0    无 子节点被勾选
                                 * 1    部分 子节点被勾选
                                 * 2    全部 子节点被勾选
                                 */
                                var status = node.check_Child_State;

                                //如果全部选中
                                if (status === 2) {
                                    node.checked = true;
                                    treeObj.updateNode(node);
                                }
                                else {
                                    node.checked = false;
                                    node.selected = 0;
                                    treeObj.updateNode(node);
                                }

                            }

                        }

                    }

                }

            }
        };

        var zTreeObj = $.fn.zTree.init($(treeId), setting, zNodes);
        zTreeObj.expandAll(true);//全部展开

        return;
        var treeObj = $.fn.zTree.getZTreeObj("corp_user_permission");
        var nodes = treeObj.getNodes();
        nodes = treeObj.transformToArray(nodes);
        /**
         * -1   不存在子节点 或 子节点全部设置为 nocheck = true
         * 0    无 子节点被勾选
         * 1    部分 子节点被勾选
         * 2    全部 子节点被勾选
         */
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];

            var status = node.check_Child_State;
            //如果全部选中
            if (status === 2) {
                node.checked = true;
                treeObj.updateNode(node);
            }

        }

    },

    //树结构点击事件
    organizationTreeOnclick: function (node) {
        loadingInit();

        corp_user_manage.cur_corp_id = node["id"];//当前公司id

        corp_user_manage.qrCodeGet();//获取入职码和二维码
        corp_user_manage.corpManagerList();//获取 管理员列表

    },

    //获取 管理员列表
    corpManagerList: function () {

        var url = urlGroup.corp_user_list_url;
        if (corp_user_manage.cur_corp_id) {
            url += "?corp_id=" + corp_user_manage.cur_corp_id;
        }

        aryaGetRequest(
            url,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    var tb_data = [];
                    if (data.result && data.result.users) {

                        var users = data.result.users;
                        for (var i = 0; i < users.length; i++) {
                            var $item = users[i];

                            var id = $item.id;//用户id
                            var login_name = $item.account ? $item.account : "";//用户帐号
                            var user_email = $item.email ? $item.email : "";//邮箱
                            var nick_name = $item.nick_name ? $item.nick_name : "";//昵称
                            var create_time = $item.create_time ? $item.create_time : "";//创建时间
                            var last_login_time = $item.last_login_time;//最后登录时间
                            // var last_login_ip = $item.last_login_ip;//最后登录ip
                            var today_login_count = $item.try_login_times_today ? $item.try_login_times_today : 0;//今日登录次数

                            var obj = {

                                id: id,
                                login_name: login_name,
                                user_email: user_email,
                                nick_name: nick_name,
                                create_time: create_time,
                                last_login_time: last_login_time,
                                today_login_count: today_login_count

                            };
                            tb_data.push(obj);

                        }

                    }

                    corp_user_manage.initTbCorpManager(tb_data);//初始化 管理员列表


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
    //初始化 管理员列表
    initTbCorpManager: function (data) {

        $tb_corp_manager.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_corp_manager.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 600,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: 'login_name',
                    title: '登录名',
                    align: "center",
                    class: "login_name",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'user_email',
                    title: 'email',
                    align: "center",
                    class: "user_email",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'nick_name',
                    title: '昵称',
                    align: "center",
                    class: "nick_name",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'create_time',
                    title: '创建时间',
                    align: "center",
                    class: "create_time",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + timeInit1(value) + "'>" + timeInit1(value) + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'last_login_time',
                    title: '最后登录时间',
                    align: "center",
                    class: "last_login_time",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + timeInit1(value) + "'>" + timeInit1(value) + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'today_login_count',
                    title: '今天登录次数',
                    align: "center",
                    class: "today_login_count",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "0";
                        }

                        return html;

                    }
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: "center",
                    class: "operate",
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        html += "<div class='btn btn-danger btn-sm btn_del'>删除</div>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //删除
                        "click .btn_del": function (e, value, row, index) {
                            e.stopImmediatePropagation();

                            var txt = "确定要删除\"" + row.login_name + "\"吗？";
                            delWarning(
                                txt,
                                function () {

                                    loadingInit();

                                    var obj = {
                                        ids: [{
                                            id: row.id
                                        }]
                                    };

                                    aryaPostRequest(
                                        urlGroup.corp_user_del,
                                        obj,
                                        function (data) {
                                            //alert(JSON.stringify(data));

                                            if (data.code === RESPONSE_OK_CODE) {
                                                toastr.success("删除成功！");

                                                corp_user_manage.corpManagerList();//获取管理员
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
                                ""
                            );

                        }


                    }
                }

            ],

            onClickRow: function (row, $element, field) {

                if ($element.hasClass("active")) {
                    $element.removeClass("active");
                    corp_user_info.user_id = "";
                }
                else {
                    $element.siblings().removeClass("active");
                    $element.addClass("active");
                    corp_user_info.user_id = row.id;
                }

            }

        });

    },

    //新增管理员 弹框显示
    addUserModalShow: function () {
        if (!corp_user_manage.cur_corp_id) {
            toastr.info("请先选择公司！");
            return;
        }

        corp_user_info.user_id = "";//当前员工id 为""

        $corp_user_info_modal.modal("show");
    },
    //编辑管理员 弹框显示
    modifyUser: function () {
        if (!corp_user_manage.checkUserIsChoose()) {
            return;
        }

        var $selected_item = $tb_corp_manager.find("tr.active");

        $corp_user_info_modal.modal("show");
        $corp_user_info_modal.find(".modal-title").text("编辑管理员");

        $("#entry_corp_account").val($selected_item.find(".login_name").text());
        $("#entry_corp_email").val($selected_item.find(".user_email").text());
        $("#entry_corp_nick_name").val($selected_item.find(".nick_name").text());

    },
    //检查是否选中管理员
    checkUserIsChoose: function () {
        var flag = false;

        var $selected_item = $tb_corp_manager.find("tbody tr.active");

        if ($selected_item.length === 0 || !corp_user_info.user_id) {
            toastr.info("请先选择管理员！");
        }
        else {
            flag = true;
        }

        return flag;
    },

    //新增管理员 保存
    userInfoSave: function () {

        //新增管理员 检查参数
        if (!corp_user_manage.checkParamsByUserInfo()) {
            return
        }

        var obj = {
            corp_id: corp_user_manage.cur_corp_id,
            id: corp_user_info.user_id,
            account: corp_user_info.login_name,
            password: corp_user_info.user_pwd,
            nick_name: corp_user_info.user_nick_name,
            email: corp_user_info.user_email
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.corp_user_add_or_modify,
            obj,
            function (data) {
                //console.log(JSON.stringify(data));

                if (data.code === ERR_CODE_OK) {

                    var txt = "新增成功！";
                    if (corp_user_info.user_id) {
                        txt = "修改成功！";
                    }
                    toastr.success(txt);
                    $corp_user_info_modal.modal("hide");
                    corp_user_manage.corpManagerList();//获取管理员列表

                }
                else {
                    toastr.error(data.msg);

                }
            },
            function (data) {

            }
        );

    },
    //新增管理员 检查参数
    checkParamsByUserInfo: function () {
        var flag = false;
        var txt = "";

        corp_user_info.login_name = $("#entry_corp_account").val();
        corp_user_info.user_pwd = $("#entry_corp_password").val();
        corp_user_info.user_pwd_sure = $("#entry_corp_password_confirm").val();
        corp_user_info.user_email = $("#entry_corp_email").val();
        corp_user_info.user_nick_name = $("#entry_corp_nick_name").val();

        if (!corp_user_info.login_name) {
            txt = "请输入账号！";
        }
        else if (!corp_user_info.user_id && !corp_user_info.user_pwd && !corp_user_info.user_pwd_sure) {		//新增时
            txt = "请输入密码！";
        }
        else if (corp_user_info.user_pwd !== corp_user_info.user_pwd_sure) {
            txt = "两次输入密码不正确！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.info(txt);
        }

        return flag;

    },

    //清空入职码和二维码
    emptyQRcode: function () {
        $("#check_in_code").val("");//清空入职码
        $("#check_in_QRCode").empty();//清空二维码
    },
    //获取入职码和二维码
    qrCodeGet: function () {
        corp_user_manage.emptyQRcode();//清空入职码和二维码

        var obj = {
            corp_id: corp_user_manage.cur_corp_id
        };

        var url = urlGroup.corp_check_in_code_and_qrCode + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {

                if (data.code === ERR_CODE_OK) {
                    $("#check_in_code").val(data.result["code"]);//赋值企业码
                    $("#check_in_QRCode").empty();
                    var img = $("<img>").appendTo($("#check_in_QRCode"));
                    img.attr("src", data.result["qrcode_url"]);
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

    //清空 登录次数
    clearTryLoginTimes: function () {
        if (!corp_user_manage.checkUserIsChoose()) {
            return;
        }

        swal({
                title: "确定要重置登录吗",
                //text: "删除后将无法恢复，请谨慎操作！",
                //type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#337ab7",
                confirmButtonText: "确定",
                closeOnConfirm: true
            },
            function () {

                var obj = {
                    id: corp_user_info.user_id
                };

                aryaPostRequest(
                    urlGroup.clear_try_login_times,
                    obj,
                    function (data) {
                        if (data.code === ERR_CODE_OK) {
                            toastr.success("重置用户的尝试登录次数成功!");

                            corp_user_manage.corpManagerList();//获取管理员列表
                        }
                        else {
                            toastr.error(data.msg);
                        }
                    },
                    function (data) {

                    })

            });

    },

    //用户权限弹框显示
    corpUserPermissionModalShow: function () {
        if (!corp_user_manage.checkUserIsChoose()) {
            return;
        }

        $corp_user_permission_modal.modal("show");

    },

    //用户权限 保存
    corpUserPermissionSave: function () {

        var treeObj = $.fn.zTree.getZTreeObj("corp_user_permission");
        var nodes = treeObj.getNodes();
        nodes = treeObj.transformToArray(nodes);
        var perms = [];

        /**
         * -1   不存在子节点 或 子节点全部设置为 nocheck = true
         * 0    无 子节点被勾选
         * 1    部分 子节点被勾选
         * 2    全部 子节点被勾选
         */
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];

            var id = node.id ? node.id : "";
            var name = node.name ? node.name : "";
            var permissionCode = node.permissionCode ? node.permissionCode : "";
            var permDesc = node.permDesc ? node.permDesc : "";
            var parentId = node.pId ? node.pId : "";
            var checked = node.checked;

            var selected;
            /**
             *  如果节点没有被选中，则selected为0
             *  如果节点选中了，则selected取值 该节点 原有的selected值（即node.selected）
             */
            if (!checked) {
                selected = 0;
            }
            else {
                // selected = node.selected;
                selected = 1;
            }

            perms.push({
                id: id,
                name: name,
                permissionCode: permissionCode,
                permDesc: permDesc,
                parentId: parentId,
                selected: selected
            })

        }

        var obj = {
            corp_user_id: corp_user_info.user_id,
            perms: perms
        };

        loadingInit();

        aryaPostRequest(
            urlGroup.corp_user_permission_save,
            obj,
            function (data) {
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    $corp_user_permission_modal.modal("hide");
                    toastr.success("更新权限成功！");

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

//管理员 信息 param
var corp_user_info = {
    user_id: "",
    login_name: "",
    user_pwd: "",
    user_pwd_sure: "",
    user_email: "",
    user_nick_name: ""
};

$(document).ready(function () {

    var treeId = ".corp_user_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_user_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_user_group_tree_url;//获取树结构 url
    var treeClickFunc = corp_user_manage.organizationTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_user_manage.init;	//清空 内容

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构

    corp_user_manage.init();//企业用户 管理 初始化

});


