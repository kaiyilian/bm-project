//新闻管理

var $website_news_manage_container = $(".website_news_manage_container");
var $tb_website_news = $website_news_manage_container.find("#tb_website_news");
var $news_info_modal = $(".news_info_modal");

var news_manage = {
    row: null,//

    //初始化
    init: function () {

        news_manage.initTb();//列表

        $news_info_modal.on("shown.bs.modal", function () {
            $news_info_modal.find(".modal-title").text("新增新闻");
            var $row = $news_info_modal.find(".modal-body .row");

            $row.find(".pub_time input").val("");
            $row.find(".n_title input").val("");
            $row.find(".author input").val("");

            ue.execCommand('cleardoc');

            //如果是编辑
            if (news_manage.row) {
                $news_info_modal.find(".modal-title").text("编辑新闻");

                $row.find(".pub_time input").val(timeInit1(news_manage.row.publishTime));
                $row.find(".n_title input").val(news_manage.row.title);
                $row.find(".author input").val(news_manage.row.author);
                ue.ready(function () {
                    ue.setContent(news_manage.row.newsContent);
                });

            }

        });

    },

    //初始化 表格
    initTb: function () {
        console.log("获取表格列表：" + new Date().getTime());

        $tb_website_news.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb_website_news.bootstrapTable({

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
                    title: '创建时间',
                    align: "center",
                    class: "createTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            value = timeInit1(value);
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'publishTime',
                    title: '发布时间',
                    align: "center",
                    class: "publishTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            value = timeInit1(value);
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'title',
                    title: '新闻标题',
                    align: "center",
                    class: "title",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'author',
                    title: '作者',
                    align: "center",
                    class: "author",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + value + "</div>";
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

                        var html = "<div class='operate'>";

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

                            news_manage.row = row;
                            $news_info_modal.modal("show");

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
                                        urlGroup.bumu_website_manage.news_manage.del,
                                        obj,
                                        function (data) {
                                            //console.log("获取日志：");
                                            //console.log(data);

                                            if (data.code === RESPONSE_OK_CODE) {
                                                toastr.success("删除成功！");
                                                news_manage.initTb();
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
            url: urlGroup.bumu_website_manage.news_manage.list,
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
                                var publishTime = item.publishTime ? item.publishTime : "";//
                                var title = item.title ? item.title : "";//
                                var author = item.author ? item.author : "";//
                                var newsContent = item.newsContent ? item.newsContent : "";//

                                var obj = {

                                    index: i,
                                    id: id,
                                    createTime: createTime,
                                    publishTime: publishTime,
                                    title: title,

                                    author: author,
                                    newsContent: newsContent

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
    newsInfoModalShow: function () {
        news_manage.row = null;
        $news_info_modal.modal("show");
    },

    //新增保存
    newsSave: function () {

        //检查参数
        if (!news_manage.checkParams()) {
            return
        }

        var $row = $news_info_modal.find(".modal-body .row");

        loadingInit();
        var url;

        //如果是编辑
        if (news_manage.row) {
            url = urlGroup.bumu_website_manage.news_manage.update
        }
        else {
            url = urlGroup.bumu_website_manage.news_manage.save
        }

        var obj = {
            "id": news_manage.row ? news_manage.row.id : "",
            "createTime": new Date().getTime(),
            "publishTime": new Date($row.find(".pub_time input").val()).getTime(),
            "title": $.trim($row.find(".n_title input").val()),
            "author": $.trim($row.find(".author input").val()),
            "newsContent": ue.getContent()
        };

        aryaPostRequest(
            url,
            obj,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    $news_info_modal.modal("hide");
                    news_manage.initTb();

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

        var $row = $news_info_modal.find(".modal-body .row");

        var pub_time = $row.find(".pub_time input").val();
        var n_title = $.trim($row.find(".n_title input").val());
        var author = $.trim($row.find(".author input").val());

        if (!n_title) {
            txt = "请输入标题！";
        }
        else if (!pub_time) {
            txt = "请输入发布时间！";
        }
        else if (!author) {
            txt = "请输入作者！";
        }
        else if (!ue.getContent()) {
            txt = "请输入新闻内容！";
        }
        // else {
        //     flag = true;
        // }

        if (txt) {
            toastr.warning(txt);
        }

        if (!txt) {
            flag = true;
        }

        return flag;

    }

};

var ue;

$(function () {
    news_manage.init();

    //新闻内容
    ue = UE.getEditor('news_content', {
        toolbars: [
            [
//                'anchor', //锚点
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'indent', //首行缩进
                'snapscreen', //截图
                'italic', //斜体
                'underline', //下划线
                'strikethrough', //删除线
//                'subscript', //下标
                'fontborder', //字符边框
//                'superscript', //上标
                'formatmatch', //格式刷
                'source', //源代码
                'blockquote', //引用
                'pasteplain', //纯文本粘贴模式
                'selectall', //全选
//                'print', //打印
                'preview', //预览
                'horizontal', //分隔线
                'removeformat', //清除格式
                'time', //时间
                'date', //日期
                'unlink', //取消链接
                'cleardoc', //清空文档
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'simpleupload', //单图上传
                'insertimage', //多图上传
                'link', //超链接
                'emotion', //表情
                'spechars', //特殊字符
                'searchreplace', //查询替换
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对齐
                'justifyjustify', //两端对齐
                'forecolor', //字体颜色
                'backcolor', //背景色
//                'imagecenter', //居中
            ]
        ],
        autoHeightEnabled: true,
        autoFloatEnabled: true
    });
    // ue.afterConfigReady(function () {
    //     console.log(22)
    // });
    // ue.loadServerConfig(function () {
    //     console.log(33);
    // });
    ue.ready(function () {

        ue.addListener('beforeInsertImage', function (t, arg) {

            console.log(arg[0].src);//arg就是上传图片的返回值，是个数组，如果上传多张图片，请遍历该值。
            //把图片地址赋值给页面input，我这里使用了jquery，可以根据自己的写法赋值，到这里就很简单了，会js的都会写了。
            // $("#abccc").attr("value", arg[0].src);
        });
    });

    // UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    // UE.Editor.prototype.getActionUrl = function(action) {
    //     if (action === 'uploadimage' || action === 'uploadscrawl' || action === 'uploadimage') {
    //         return 'http://bumuyun.com:8025/bumu-api/mina/v2/weibo/upload';
    //     }
    //     else {
    //         return this._bkGetActionUrl.call(this, action);
    //     }
    // }

});
