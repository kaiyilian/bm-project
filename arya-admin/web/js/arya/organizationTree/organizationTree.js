/**
 * Created by CuiMengxin on 2016/7/14.
 */

/**
 * 初始化 - 组织树
 * @param treeId     容器id
 * @param hudId     loading_id
 * @param url       接口url
 * @param onclickFunc   点击事件
 */
var initOrganizationTree = function (treeId, hudId, url, onclickFunc) {
    //showHUD(hudId);
    loadingInit();

    aryaGetRequest(
        url,
        function (data) {
            //alert(JSON.stringify(data));

            if (data.code == 1000) {
                var organizationList = [];
                var treeData = data.result["tree"];
                if (!treeData || treeData.length == 0) {
                    //dismissHUD(hudId);
                    return;
                }

                //console.log(treeData);

                $.each(treeData, function (index, item) {
                    //var item = treeData[i];

                    var pid = item["parent_id"];//
                    if (!pid || pid == "null") {
                        pid = 0;
                    }
                    var id = item["id"];//
                    var name = item["name"];//
                    var type = item["type"];//组织类型:1集团，2子公司，3通用部门，4一级公司
                    var iconSkin = pid == 0 ? "rootNode_" + type : "subNode_" + type;

                    var obj = {
                        id: id,
                        type: type,
                        name: name,
                        full_name: name,
                        iconSkin: iconSkin,
                        pId: pid
                    };

                    //debugger

                    organizationList.push(obj);

                });

                showOrganizationTree(treeId, organizationList, onclickFunc);
            }
            //dismissHUD(hudId);
        },
        function (data) {
            //dismissHUD(hudId);
        }
    );

};

/**
 * 显示组织树
 * @param treeId                容器id
 * @param organizationList      数据数组
 * @param onclickFunc           点击事件
 */
var showOrganizationTree = function (treeId, organizationList, onclickFunc) {
    //if (organizationList == "") {   //部门为空
    //	organizationList = [];
    //}

    var zNodes = organizationList;
    var setting = {
        view: {
            selectedMulti: false,//禁止选择多项
            dblClickExpand: false,//双击节点 不切换 展开状态
            //addHoverDom: department.addHoverDom,//当鼠标移动到节点上时，显示用户自定义控件
            //removeHoverDom: department.removeHoverDom //当鼠标移出到节点时，隐藏用户自定义控件
        },
        edit: {
            enable: false,//是否可编辑
            editNameSelectAll: true,//txt 内容是否为全选状态
            showRemoveBtn: true,
            removeTitle: "删除节点",
            showRenameBtn: true,
            renameTitle: "编辑节点名称"
        },
        callback: {
            //编辑
            //beforeRename: department.beforeRename,
            //onRename: department.onRename,
            //beforeEditName: department.beforeEditName,

            //删除
            //beforeRemove: department.beforeRemove,
            //onRemove: department.onRemove

            //双击事件
            //onDblClick: function (event, treeId, treeNode) {
            //
            //    var zTree = $.fn.zTree.getZTreeObj("tree");
            //    zTree.editName(treeNode)
            //},
            onClick: function (event, treeId, treeNode) {
                if (onclickFunc) {
                    onclickFunc(treeNode);//点击事件回调
                }
            }
        },
        data: {
            simpleData: {   //简单数据格式
                enable: true
            }
        }
    };

    var zTreeObj = $.fn.zTree.init($(treeId), setting, zNodes);

    //zTreeObj.expandAll(true);//全部展开
};

var organizationTree = {

    /**
     * 初始化 - 树结构
     *  @param treeId        容器id
     *  @param searchId     搜索框id
     *  @param url          树结构url
     *  @param treeClickFunc   树结构点击事件
     * @param clearInfoByTreeClickFunc      清空点击树结构出现的信息
     * */
    init: function (treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc) {

        //左侧树形结构
        organizationTree.initOrganizationTree(
            url,
            function (organizationList) {

                //显示 树结构
                organizationTree.showOrganizationTree(
                    treeId,
                    organizationList,
                    treeClickFunc,
                    clearInfoByTreeClickFunc
                );

                //查询框 搜索事件
                $(searchId).bind('input propertychange', function () {

                    // var v = $(this).val() + "----" + $(this).val().length + "----" + ' characters';
                    // console.log(v);

                    //初始化 查询结果
                    organizationTree.initOrganizationTreeSearch(
                        treeId,
                        searchId,
                        organizationList,
                        treeClickFunc,
                        clearInfoByTreeClickFunc
                    );

                });

            }
        );

    },

    /**
     * 初始化 - 组织树
     * @param url       接口url
     * @param successFun   接口调用成功后，回调事件
     */
    initOrganizationTree: function (url, successFun) {
        console.log("获取左侧树结构：" + new Date().getTime());

        loadingInit();

        aryaGetRequest(
            url,
            function (data) {

                if (data.code === RESPONSE_OK_CODE) {

                    var treeData = data.result["tree"] ? data.result["tree"] : [];

                    var organizationList = [];
                    if (treeData && treeData.length > 0) {

                        $.each(treeData, function (index, item) {

                            var pid = item["parent_id"];//
                            if (!pid || pid === "null") {
                                pid = 0;
                            }
                            var id = item["id"] ? item["id"] : "";//
                            var name = item["name"] ? item["name"] : "";//
                            var type = item["type"] ? item["type"] : "1";//组织类型:1集团，2子公司，3通用部门，4一级公司
                            var iconSkin = pid === 0 ? "rootNode_" + type : "subNode_" + type;

                            var obj = {
                                id: id,
                                type: type,
                                name: name,
                                full_name: name,
                                iconSkin: iconSkin,
                                pId: pid
                            };

                            organizationList.push(obj);

                        });

                    }

                    if (successFun) {
                        successFun(organizationList);
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

    },

    /**
     * 初始化 - 树结构查询
     *  @param treeId     容器id
     *  @param searchId     搜索框id
     *  @param tree_list     原有的树结构
     *  @param onclickFunc   点击事件
     * @param clearInfoByTreeClickFunc      清空点击树结构出现的信息
     * */
    initOrganizationTreeSearch: function (treeId, searchId, tree_list, onclickFunc, clearInfoByTreeClickFunc) {

        var val = $.trim($(searchId).val());//输入的查询内容
        var new_tree_list = [];//查询后的 树结构

        //判断是否有值
        if (val) {

            $.each(tree_list, function (i, $item) {

                if ($item && $item.name !== null && $item.name.indexOf(val) > -1) {
                    new_tree_list.push($item);
                }

            });

        }
        else {
            new_tree_list = tree_list;
        }

        //显示 树结构
        organizationTree.showOrganizationTree(
            treeId,
            new_tree_list,
            onclickFunc,
            clearInfoByTreeClickFunc
        );

    },

    /**
     * 显示组织树
     * @param treeId                容器id
     * @param organizationList      数据数组
     * @param onclickFunc           点击事件
     * @param clearInfoByTreeClickFunc      清空点击树结构出现的信息
     */
    showOrganizationTree: function (treeId, organizationList, onclickFunc, clearInfoByTreeClickFunc) {

        //页面初始化
        if (clearInfoByTreeClickFunc) {
            clearInfoByTreeClickFunc();
        }

        var zNodes = organizationList;
        var setting = {
            view: {
                selectedMulti: false,//禁止选择多项
                dblClickExpand: false,//双击节点 不切换 展开状态
                //addHoverDom: department.addHoverDom,//当鼠标移动到节点上时，显示用户自定义控件
                //removeHoverDom: department.removeHoverDom //当鼠标移出到节点时，隐藏用户自定义控件
            },
            edit: {
                enable: false,//是否可编辑
                editNameSelectAll: true,//txt 内容是否为全选状态
                showRemoveBtn: true,
                removeTitle: "删除节点",
                showRenameBtn: true,
                renameTitle: "编辑节点名称"
            },
            callback: {
                //编辑
                //beforeRename: department.beforeRename,
                //onRename: department.onRename,
                //beforeEditName: department.beforeEditName,

                //删除
                //beforeRemove: department.beforeRemove,
                //onRemove: department.onRemove

                //双击事件
                //onDblClick: function (event, treeId, treeNode) {
                //
                //    var zTree = $.fn.zTree.getZTreeObj("tree");
                //    zTree.editName(treeNode)
                //},
                onClick: function (event, treeId, treeNode) {
                    if (onclickFunc) {
                        onclickFunc(treeNode);//点击事件回调
                    }
                }
            },
            data: {
                simpleData: {   //简单数据格式
                    enable: true
                }
            }
        };

        var zTreeObj = $.fn.zTree.init($(treeId), setting, zNodes);
        //zTreeObj.expandAll(true);//全部展开

    }

};

