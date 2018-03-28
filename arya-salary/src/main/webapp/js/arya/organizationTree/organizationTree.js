/**
 * Created by CuiMengxin on 2016/7/14.
 */
var initOrganizationTree = function (treeId, url, onclickFunc) {
    // showOrganizationTree(treeId, null, onclickFunc);
    // return;

    //showHUD(hudId);
    loadingInit();

    aryaGetRequest(
        url,
        function (data) {
            //alert(JSON.stringify(data));

            if (data.code === RESPONSE_OK_CODE) {
                var organizationList = [];
                var treeData = data.result;
                if (!treeData || treeData.length === 0) {
                    //dismissHUD(hudId);
                    $(treeId).empty();
                    return;
                }

                //console.log(treeData);

                $.each(treeData, function (index, item) {
                    //var item = treeData[i];

                    // var pid = item["parent_id"];//
                    // if (!pid || pid === "null") {
                    //     pid = 0;
                    // }
                    var id = item["id"];//
                    var full_name = item["customerName"];//全称
                    var shortName = item["shortName"];//简称
                    // var type = item["type"];//组织类型:1集团，2子公司，3通用部门，4一级公司
                    var iconSkin = "rootNode_4";

                    var obj = {
                        id: id,
                        // type: type,
                        name: shortName,
                        title: full_name,
                        iconSkin: iconSkin
                        // pId: pid
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
 * @param treeId
 * @param organizationList
 */
var showOrganizationTree = function (treeId, organizationList, onclickFunc) {
    // if (organizationList == "") {   //部门为空
    // 	organizationList = [];
    // }

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
            },
            showTitle: true, //是否显示节点title信息提示 默认为true
            key: {
                title: "title" //设置title提示信息对应的属性名称 也就是节点相关的某个属性
            }
        }
    };

    var zTreeObj = $.fn.zTree.init($(treeId), setting, zNodes);

    //zTreeObj.expandAll(true);//全部展开
};

// var organizationList = [
//     {
//         id: "1",
//         type: 4,
//         name: "不木科技有限公司",
//         full_name: "不木科技有限公司",
//         iconSkin: "rootNode_4",
//         pId: 0
//     },
//     {
//         id: "2",
//         type: 4,
//         name: "不木科技有限公司2",
//         full_name: "不木科技有限公司2",
//         iconSkin: "rootNode_4",
//         pId: 0
//     },
//     {
//         id: "3",
//         type: 4,
//         name: "不木科技有限公司3",
//         full_name: "不木科技有限公司3",
//         iconSkin: "rootNode_4",
//         pId: 0
//     }
// ];
