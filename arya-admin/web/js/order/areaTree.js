/**
 * Created by CuiMengxin on 2016/8/2.
 * 显示参保地区 - 树形结构
 */

var initAreaTree = function (treeId, hudId, url, onclickFunc) {

	showHUD(hudId);
	aryaGetRequest(
		url,
		function (data) {
			//alert(JSON.stringify(data));

			if (data.code == 1000) {
				var organizationList = "";
				var treeData = data.result.districts;
				if (treeData == null || treeData.length == 0) {
					dismissHUD(hudId);
					return;
				}
				for (var i = 0; i < treeData.length; i++) {
					var item = treeData[i];

					var pid = item["parent_id"];//
					if (pid == "" || pid == null || pid == "null") {
						pid = 0;
					}
					var id = item["id"];//
					var name = item["name"];//

					if (organizationList == "") {
						organizationList += pid == 0 ?
							("{'id':'" + id + "'," +
								//"'type':'" + type + "'," +
							"'name':'" + name + "'," +
								//"'full_name':'" + full_name + "'," +
								//"'iconSkin':'rootNode" + "_" + type + "'," +
							"'pId':'" + pid + "'}") :
							("{'id':'" + id + "'," +
								//"'type':'" + type + "'," +
							"'name':'" + name + "'," +
								//"'full_name':'" + full_name + "'," +
								//"'iconSkin':'subNode" + "_" + type + "'," +
							"'pId':'" + pid + "'}")
					}
					else {
						organizationList += pid == 0 ?
							(",{'id':'" + id + "'," +
								//"'type':'" + type + "'," +
							"'name':'" + name + "'," +
								//"'full_name':'" + full_name + "'," +
								//"'iconSkin':'rootNode" + "_" + type + "'," +
							"'pId':'" + pid + "'}") :
							(",{'id':'" + id + "'," +
								//"'type':'" + type + "'," +
							"'name':'" + name + "'," +
								//"'full_name':'" + full_name + "'," +
								//"'iconSkin':'subNode" + "_" + type + "'," +
							"'pId':'" + pid + "'}");
					}

				}
				organizationList = "[" + organizationList + "]";
				//alert(organizationList);
				organizationList = eval("(" + organizationList + ")");

				showAreaTree(treeId, organizationList, onclickFunc);
			}
			dismissHUD(hudId);
		},
		function (data) {
			dismissHUD(hudId);
		})
};

/**
 * 显示组织树
 * @param treeId
 * @param organizationList
 */
var showAreaTree = function (treeId, organizationList, onclickFunc) {
	//alert(treeId);
	if (organizationList == "") {   //树内容为空
		organizationList = [];
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
				//alert(onclickFunc);

				if (onclickFunc) {
					//alert(treeId);
					//alert(JSON.stringify(treeNode));
					//alert(treeNode.tId);

					onclickFunc(treeNode);//点击事件回调
				}
			}
		},
		data: {
			simpleData: {   //简单数据格式
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			}
		}
	};
	//initFunc(treeId, setting, zNodes);//初始化树结构
	var zTreeObj = $.fn.zTree.init($(treeId), setting, zNodes);
	zTreeObj.expandAll(true);//全部展开
};