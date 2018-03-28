<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2016/5/24
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>


</head>
<body>
<script>
    function demo_create() {
        var ref = $('#jstree_demo').jstree(true),//获取一个实例 不创建
                sel = ref.get_selected();

        alert(sel.length)

        if (!sel.length) {
            return false;
        }
        sel = sel[0];
        sel = ref.create_node(sel, {"type": "file"});
        if (sel) {
            ref.edit(sel);
        }
    }
    ;
    function demo_rename() {
        var ref = $('#jstree_demo').jstree(true),
                sel = ref.get_selected();
        if (!sel.length) {
            return false;
        }
        sel = sel[0];
        ref.edit(sel);
    }
    ;
    function demo_delete() {
        var ref = $('#jstree_demo').jstree(true),
                sel = ref.get_selected();
        if (!sel.length) {
            return false;
        }
        ref.delete_node(sel);
    }
    ;
    $(function () {
        var to = false;
        $('#demo_q').keyup(function () {
            if (to) {
                clearTimeout(to);
            }
            to = setTimeout(function () {
                var v = $('#demo_q').val();
                $('#jstree_demo').jstree(true).search(v);
            }, 250);
        });

        $('#jstree_demo')
                .jstree({
                    "core": {
                        "animation": 0,
                        "check_callback": true,
                        'force_text': true,
                        "themes": {"stripes": true},
                        'data': [
                            'Simple root node',
                            {
                                'id': 'node_2',
                                'text': 'Root node with options',
                                'state': {'opened': true, 'selected': true},
                                'children': [{'text': 'Child 1'}, 'Child 2']
                            }
                        ]
                    },
//              "types" : {
//                "#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
//                "root" : { "icon" : "/static/3.3.1/assets/images/tree_icon.png", "valid_children" : ["default"] },
//                "default" : { "valid_children" : ["default","file"] },
//                "file" : { "icon" : "glyphicon glyphicon-file", "valid_children" : [] }
//              },
                    "plugins": ["contextmenu", "dnd", "search", "state", "types", "wholerow"]
                });
    });
</script>

<div id="jstree_demo" class="demo" style="margin-top:1em; min-height:200px;"></div>
</body>
</html>
