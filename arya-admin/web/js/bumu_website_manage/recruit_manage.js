//招聘管理

var $website_recruit_manage_container = $(".website_recruit_manage_container");
var $tb_website_recruit = $website_recruit_manage_container.find("#tb_website_recruit");
var $recruit_info_modal = $(".recruit_info_modal");

var recruit_manage = {
    row: "",

    //初始化
    init: function () {

        recruit_manage.initTb();//列表

        $recruit_info_modal.on("shown.bs.modal", function () {
            $recruit_info_modal.find(".modal-title").text("新增招聘信息");
            var $row = $recruit_info_modal.find(".modal-body .row");

            $row.find(".post_name input").val("");
            // $row.find(".post_duties textarea").val("");
            // $row.find(".post_require textarea").val("");
            post_duties.execCommand('cleardoc');
            post_require.execCommand('cleardoc');

            //如果是编辑
            if (recruit_manage.row) {
                $recruit_info_modal.find(".modal-title").text("编辑招聘信息");

                $row.find(".post_name input").val(recruit_manage.row.jobName);
                // $row.find(".post_duties textarea").val(recruit_manage.row.jobDuty);
                // $row.find(".post_require textarea").val(recruit_manage.row.jobRequire);
                post_duties.ready(function () {
                    post_duties.setContent(recruit_manage.row.jobDuty);
                });
                post_require.ready(function () {
                    post_require.setContent(recruit_manage.row.jobRequire);
                });

            }

        });

    },

    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        $tb_website_recruit.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_website_recruit.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            // data: data,                         //直接从本地数据初始化表格
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

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: 'createTime',
                    title: '发布时间',
                    align: "center",
                    class: "createTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            value = timeInit1(value);
                            html = "<div title='" + value + "' style='width:120px;'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'jobName',
                    title: '职位名称',
                    align: "center",
                    class: "jobName",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div style='width:120px;'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'jobDuty',
                    title: '岗位职责',
                    align: "center",
                    class: "jobDuty",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div style='text-align: left;'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'jobRequire',
                    title: '职位需求',
                    align: "center",
                    class: "jobRequire",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div style='text-align: left;'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
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

                        var html = "<div class='operate' style='width:100px;'>";

                        html += "<button class='btn btn-primary btn-sm btn_modify'>编辑</button>";
                        html += "<button class='btn btn-danger btn-sm btn_del'>删除</button>";

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //前台 编辑
                        "click .btn_modify": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // news_manage_param.id = row.id;

                            recruit_manage.row = row;
                            $recruit_info_modal.modal("show");

                        },
                        //前台 删除
                        "click .btn_del": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            // news_manage_param.id = row.id;

                            operateShow(
                                "确定要删除吗？",
                                function () {

                                    loadingInit();

                                    var obj = {
                                        id: [
                                            row.id
                                        ]
                                    };

                                    aryaPostRequest(
                                        urlGroup.bumu_website_manage.recruit_manage.del,
                                        obj,
                                        function (data) {
                                            //console.log("获取日志：");
                                            //console.log(data);

                                            if (data.code === RESPONSE_OK_CODE) {
                                                toastr.success("删除成功！");

                                                recruit_manage.initTb();
                                            }
                                            else {
                                                toastr.warning(data.msg);
                                            }

                                        },
                                        function (error) {
                                            toastr.error(error);
                                        }
                                    );

                                }
                            )

                        }


                    }
                }

            ],

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.bumu_website_manage.recruit_manage.list,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                // console.log(params);

                return {

                    page: params.pageNumber,
                    page_size: params.pageSize

                };
            },
            onLoadSuccess: function () {  //加载成功时执行

                // toastr.success("成功！");

            },
            onLoadError: function () {  //加载失败时执行
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {

                setTimeout(function () {
                    loadingRemove();
                }, 500);

                var tb_data = [];
                var total_rows = 0;//总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        total_rows = res.result.total_rows ? res.result.total_rows : 0;//总条数

                        if (res.result.records) {
                            $.each(res.result.records, function (i, item) {

                                var id = item.id ? item.id : "";//
                                var createTime = item.createTime ? item.createTime : "";//
                                var jobName = item.jobName ? item.jobName : "";//
                                var jobDuty = item.jobDuty ? item.jobDuty : "";//
                                var jobRequire = item.jobRequire ? item.jobRequire : "";//


                                var obj = {

                                    index: i,
                                    id: id,
                                    createTime: createTime,
                                    jobName: jobName,
                                    jobDuty: jobDuty,
                                    jobRequire: jobRequire

                                };
                                tb_data.push(obj);

                            });
                        }

                    }

                }
                else {
                    toastr.warning(res.msg);
                }

                return {
                    total: total_rows,
                    rows: tb_data
                };

            }

        });
    },

    //新增
    recruitInfoModalShow: function () {

        var data = $tb_website_recruit.bootstrapTable("getData");

        if (data.length >= 10) {
            toastr.warning("最多保存10条！");
            return
        }

        recruit_manage.row = null;
        $recruit_info_modal.modal("show");
    },

    //新增保存
    recruitSave: function () {
        //检查参数
        if (!recruit_manage.checkParams()) {
            return
        }

        var $row = $recruit_info_modal.find(".modal-body .row");

        loadingInit();
        var url;

        if (recruit_manage.row) {
            url = urlGroup.bumu_website_manage.recruit_manage.update
        }
        else {
            url = urlGroup.bumu_website_manage.recruit_manage.save
        }

        var obj = {
            "id": recruit_manage.row ? recruit_manage.row.id : "",
            "jobName": $.trim($row.find(".post_name input").val()),
            "jobDuty": post_duties.getContent(),
            "jobRequire": post_require.getContent()
        };

        aryaPostRequest(
            url,
            obj,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    $recruit_info_modal.modal("hide");
                    recruit_manage.initTb();

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
    //检查参数
    checkParams: function () {
        var flag = false;
        var txt = "";

        var $row = $recruit_info_modal.find(".modal-body .row");

        var post_name = $.trim($row.find(".post_name input").val());

        if (!post_name) {
            txt = "请输入职位名称！";
        }
        else if (!post_duties.getContent()) {
            txt = "请输入岗位职责！";
        }
        else if (!post_require.getContent()) {
            txt = "请输入职位需求！";
        }

        if (txt) {
            toastr.warning(txt);
        }

        if (!txt) {
            flag = true;
        }

        return flag;

    }

};

var post_duties, post_require;

$(function () {
    recruit_manage.init();

    //招聘职责
    post_duties = UE.getEditor('post_duties', {
        toolbars: [
            [
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'indent', //首行缩进
                'italic', //斜体
                'underline', //下划线
                'formatmatch', //格式刷
                'pasteplain', //纯文本粘贴模式
                'selectall', //全选
                'preview', //预览
                'horizontal', //分隔线
                'removeformat', //清除格式
                'time', //时间
                'date', //日期
                'cleardoc', //清空文档
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'searchreplace', //查询替换
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对齐
                'justifyjustify', //两端对齐
                'forecolor', //字体颜色
                'backcolor', //背景色
            ]
        ],
        autoHeightEnabled: true,
        autoFloatEnabled: true
    });
    //招聘需求
    post_require = UE.getEditor('post_require', {
        toolbars: [
            [
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'indent', //首行缩进
                'italic', //斜体
                'underline', //下划线
                'formatmatch', //格式刷
                'pasteplain', //纯文本粘贴模式
                'selectall', //全选
                'preview', //预览
                'horizontal', //分隔线
                'removeformat', //清除格式
                'time', //时间
                'date', //日期
                'cleardoc', //清空文档
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'searchreplace', //查询替换
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对齐
                'justifyjustify', //两端对齐
                'forecolor', //字体颜色
                'backcolor', //背景色
            ]
        ],
        autoHeightEnabled: true,
        autoFloatEnabled: true
    });

});
