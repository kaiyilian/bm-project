<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2016/5/24
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

    <% String contextPath = request.getContextPath().toString(); %>

    <link href="<%=contextPath%>/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
    <script src="<%=contextPath%>/js/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/js/plugins/ztree/js/jquery.ztree.all.min.js"></script>
    <style type="text/css">
        .ztree li span.button.add {
            margin-left: 2px;
            margin-right: -1px;
            background-position: -144px 0;
            vertical-align: top;
            *vertical-align: middle
        }
    </style>

    <SCRIPT type="text/javascript">
        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='add node' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_" + treeNode.tId);
            if (btn) btn.bind("click", function () {
                var zTree = $.fn.zTree.getZTreeObj("tree");
                zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++)});
                return false;
            });
        }

        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
        }


        var zTreeObj
        var setting = {
            view: {
//                        selectedMulti: false,

                //当鼠标移动到节点上时，显示用户自定义控件
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
            },
            edit: {
                enable: true,//是否可编辑
                editNameSelectAll: true,//txt 内容是否为全选状态
                showRemoveBtn: true,
                removeTitle: "删除节点",
                showRenameBtn: true,
                renameTitle: "编辑节点名称",

            },
            callback: {
                //编辑
                beforeRename: function (treeId, treeNode, newName, isCancel) {
                    var flag = true;

                    if (newName.length > 5) {
                    }
                    else {
                        alert("字数不能小于5个")
                        flag = false;
                    }
                    return flag;
                },
                onRename: function (event, treeId, treeNode, isCancel) {
//                            alert(treeId + "---" + treeNode.tId + ", " + treeNode.name);
                    alert(zTreeObj.getNodeByTId(treeNode.tId).id + "\n" +
                            zTreeObj.getNodeByTId(treeNode.tId).getParentNode().id)

                },

                //删除
                beforeRemove: function (treeId, treeNode) {
                    var flag = true;
                    if (!confirm("是否确认删除")) {
                        flag = false;
                    }
                    return flag;
                },
                onRemove: function (event, treeId, treeNode) {
                    alert(treeNode.tId + ", " + treeNode.name);
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        }
        var zNodes = [
            {id: 1, pId: 0, name: "父节点 1", open: true, cid: 1},
            {id: 11, pId: 1, name: "叶子节点 1-1", cid: 11},
            {id: 12, pId: 1, name: "叶子节点 1-2"},
//            {id: 13, pId: 1, name: "叶子节点 1-3"},
//            {id: 2, pId: 0, name: "父节点 2", open: true},
//            {id: 21, pId: 2, name: "叶子节点 2-1"},
//            {id: 22, pId: 2, name: "叶子节点 2-2"},
//            {id: 23, pId: 2, name: "叶子节点 2-3"},
//            {id: 3, pId: 0, name: "父节点 3", open: true},
//            {id: 31, pId: 3, name: "叶子节点 3-1"},
//            {id: 32, pId: 3, name: "叶子节点 3-2"},
//            {id: 33, pId: 3, name: "叶子节点 3-3"}
        ];
        var zTreeNodes = [
            {
                "name": "网站导航", open: true, children: [
                {"name": "google", "id": "test1"},//"url": "http://g.cn", "target": "_blank"
                {"name": "baidu",},//"url": "http://baidu.com", "target": "_blank"
                {"name": "sina",}// "url": "http://www.sina.com.cn", "target": "_blank"
            ]
            }
        ];

        $(document).ready(function () {
            zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
        });


    </SCRIPT>
</head>
<body>
<div class="container">

    <ul id="tree" class="ztree" style="width:230px; overflow:auto;box-sizing:inherit;"></ul>
</div>
</body>
</html>
