/**
 * Created by Administrator on 2017/11/8.
 * 后台新闻管理
 */

var news_manage = {

    init: function () {
        var $tb = $("#tb_news_careful");//
        var data = news_manage_param.careful_news;
        news_manage_param.type = "choice";

        news_manage.newsList();//获取新闻

        $('a[href="#careful_news"]').tab('show'); //默认显示 合同模板
        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {

            //console.log($(this).html());

            //新闻类型 初始化
            var href = $(this).attr("data-href");
            switch (href) {
                case"careful_news":
                    $tb = $("#tb_news_careful");
                    data = news_manage_param.careful_news;
                    news_manage_param.type = "choice";
                    break;
                case"corp_news":
                    $tb = $("#tb_news_corp");
                    data = news_manage_param.corp_news;
                    news_manage_param.type = "teach";
                    break;
                case"entertainment_news":
                    $tb = $("#tb_news_entertainment");
                    data = news_manage_param.entertainment_news;
                    news_manage_param.type = "entertainment";
                    break;
                case"sport_news":
                    $tb = $("#tb_news_sport");
                    data = news_manage_param.sport_news;
                    news_manage_param.type = "sports";
                    break;
                default:
                    break;
            }

            news_manage.initTbNews($tb, data);//初始化 新闻

        });

    },
    //获取新闻
    newsList: function () {

        var url = urlGroup.news_manage_list;
        aryaGetRequest(
            url,
            function (data) {
                //console.log("获取日志：");
                //console.log(data);

                if (data.code === RESPONSE_OK_CODE) {

                    //如果有结果
                    if (data.result && data.result.length > 0) {

                        $.each(data.result, function (i, item) {

                            var type = item.type ? item.type : "";//新闻类型
                            var newsResultList = item.newsResultList ? item.newsResultList : [];//
                            var arr = [];//数组

                            $.each(newsResultList, function (i, $item) {

                                var id = $item.id ? $item.id : "";//
                                var title = $item.title ? $item.title : "";//
                                var uri = $item.uri ? $item.uri : "";//
                                var imgSrc = $item.imgSrc ? $item.imgSrc : "";//

                                var obj = {
                                    id: id,
                                    title: title,
                                    img_url: imgSrc,
                                    content_url: uri,
                                    status: 0       //1 编辑中 0 已完成
                                };

                                arr.push(obj);

                            });

                            switch (type) {
                                case "choice":
                                    news_manage_param.careful_news = arr;
                                    break;
                                case "teach":
                                    news_manage_param.corp_news = arr;
                                    break;
                                case "entertainment":
                                    news_manage_param.entertainment_news = arr;
                                    break;
                                case "sports":
                                    news_manage_param.sport_news = arr;
                                    break;
                            }

                        });

                    }

                    var $tb = $("#tb_news_careful");//
                    var tb_data = news_manage_param.careful_news;
                    news_manage.initTbNews($tb, tb_data);//初始化 新闻

                }
                else {
                    //console.log("获取日志-----error：");
                    //console.log(data.msg);

                    toastr.warning(data.msg);
                }

            },
            function (error) {
                toastr.error("系统错误：" + error);
            }
        );

    },

    //初始化
    initTbNews: function ($tb, data) {

        $tb.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb.bootstrapTable({

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
            pageSize: 6,                       //每页的记录行数（*）
            pageList: [6, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            height: 400,
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
                    field: 'index',
                    title: '序号',
                    align: "center",
                    class: "index",
                    // width: 200,
                    formatter: function (value, row, index) {

                        return "<div>" + (index + 1 ) + "</div>";

                    }
                },

                {
                    field: 'title',
                    title: '标题',
                    sortable: true,
                    align: "center",
                    class: "title",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";

                        //编辑完成
                        if (row.status === 0) {
                            html = "<div>" + value + "</div>";
                        }

                        //编辑中
                        if (row.status === 1) {
                            html = "<div><input type='text' class='form-control' value='" + value + "' maxlength='200'></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'img_url',
                    title: '图片URL',
                    sortable: true,
                    align: "center",
                    class: "img_url",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";

                        //编辑完成
                        if (row.status === 0) {
                            html = "<div>" + value + "</div>";
                        }

                        //编辑中
                        if (row.status === 1) {
                            html = "<div><input type='text' class='form-control' value='" + value + "' maxlength='200'></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'content_url',
                    title: '内容URL',
                    sortable: true,
                    align: "center",
                    class: "content_url",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "";

                        //编辑完成
                        if (row.status === 0) {
                            html = "<div>" + value + "</div>";
                        }

                        //编辑中
                        if (row.status === 1) {
                            html = "<div><input type='text' class='form-control' value='" + value + "' maxlength='200'></div>";
                        }

                        return html;

                    }
                },

                {
                    field: 'operate',
                    title: '操作',
                    // sortable: true,
                    align: "center",
                    class: "operate",
                    width: 200,
                    formatter: function (value, row, index) {

                        var html = "<div class='operate'>";

                        //已完成
                        if (row.status === 0) {
                            html += "<div class='btn btn-primary btn-sm btn_modify'>编辑</div>";
                            html += "<div class='btn btn-danger btn-sm btn_del'>删除</div>";
                        }

                        //编辑中
                        if (row.status === 1) {
                            html += "<button class='btn btn-primary btn-sm btn_save'>保存</button>";
                            html += "<button class='btn btn-primary btn-sm btn_cancel'>取消</button>";
                        }

                        html += "</div>";

                        return html;

                    },
                    events: {

                        //编辑
                        "click .btn_modify": function (e, value, row, index) {

                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");
                            news_manage_param.id = row.id;

                            $tb.bootstrapTable("updateRow", {
                                index: index,
                                row: {
                                    status: 1
                                }
                            });

                        },
                        //取消
                        "click .btn_cancel": function (e, value, row, index) {
                            // console.log(row);
                            // console.log(e);
                            // var $e = $(e.currentTarget);
                            // var $item = $e.closest(".item");

                            //如果是 编辑的取消
                            if (row.id) {
                                $tb.bootstrapTable("updateRow", {
                                    index: index,
                                    row: {
                                        status: 0
                                    }
                                })
                            }
                            else {
                                $tb.bootstrapTable('remove', {
                                    field: 'id',
                                    values: [row.id]
                                });
                            }
                        },
                        //保存
                        "click .btn_save": function (e, value, row, index) {

                            // console.log(e);
                            var $e = $(e.currentTarget);
                            var $item = $e.closest(".item");

                            news_manage_param.paramSet($item);//参数赋值

                            var txt = "";
                            if (!news_manage_param.title) {
                                txt = "新闻标题不能为空！";
                            }
                            else if (!news_manage_param.img_url) {
                                txt = "新闻图片url不能为空！";
                            }
                            else if (!news_manage_param.content_url) {
                                txt = "新闻内容url不能为空！";
                            }

                            if (txt) {
                                toastr.warning(txt);
                                return;
                            }

                            var obj = {
                                TITLE: news_manage_param.title,
                                URL: news_manage_param.content_url,
                                IMG_SRC: news_manage_param.img_url,
                                TYPE: news_manage_param.type
                            };
                            var url = urlGroup.news_manage_add;
                            //判断是新增还是 编辑
                            if (news_manage_param.id) {
                                url = urlGroup.news_manage_modify;
                                obj["ID"] = news_manage_param.id;
                            }

                            aryaPostRequest(
                                url,
                                obj,
                                function (data) {
                                    //console.log(data);

                                    if (data.code === RESPONSE_OK_CODE) {

                                        toastr.success("保存成功！");

                                        //如果 有id返回，则当前操作为新增，否则为编辑
                                        var id;
                                        if (news_manage_param.id) {
                                            id = news_manage_param.id;
                                        }
                                        else {
                                            id = data.result.id ? data.result.id : "";
                                        }

                                        $tb.bootstrapTable("updateRow", {
                                            index: index,
                                            row: {
                                                status: 0,
                                                id: id,
                                                type: news_manage_param.type,
                                                title: news_manage_param.title,
                                                img_url: news_manage_param.img_url,
                                                content_url: news_manage_param.content_url
                                            }
                                        });

                                    }
                                    else {
                                        toastr.warning(data.msg);
                                    }

                                },
                                function (error) {
                                    toastr.warning(error);
                                }
                            );

                        },
                        //删除
                        "click .btn_del": function (e, value, row, index) {

                            delWarning(
                                "确定要删除吗？",
                                function () {
                                    loadingInit();

                                    var obj = {
                                        id: [
                                            row.id
                                        ]
                                    };

                                    var url = urlGroup.news_manage_del;

                                    aryaPostRequest(
                                        url,
                                        obj,
                                        function (data) {
                                            //console.log(data);

                                            if (data.code === RESPONSE_OK_CODE) {

                                                // swal(
                                                //     '删除成功',
                                                //     '',
                                                //     'success'
                                                // )
                                                toastr.success("删除成功！");

                                                $tb.bootstrapTable('remove', {
                                                    field: 'id',
                                                    values: [row.id]
                                                });

                                            }
                                            else {
                                                toastr.warning(data.msg);
                                            }

                                        },
                                        function (error) {
                                            toastr.warning(error);
                                        }
                                    );
                                },
                                ""
                            );

                        }


                    }
                }

            ]

        });

    },

    //新增新闻
    addNews: function (className) {
        news_manage_param.id = "";//新闻id

        var tbName = "#" + className;
        var $tb = $(tbName);

        var data = $tb.bootstrapTable("getData");
        if (data.length >= 6) {
            toastr.warning("最多6条！");
            return;
        }

        var count = 0;
        $.each(data, function (index, item) {

            //如果是编辑中
            if (item.status === 1) {
                count += 1
            }

        });
        if (count > 0) {
            toastr.warning("有数据正在编辑中。。。");
            return;
        }

        $tb.bootstrapTable('selectPage', 1); //Jump to the first page

        $tb.bootstrapTable("insertRow", {
            index: 0,
            row: {
                id: news_manage_param.id,
                type: news_manage_param.type,
                title: '',
                img_url: '',
                content_url: '',
                status: 1
            }
        });

    }


};

var news_manage_param = {

    id: "",
    type: "choice",//新闻类别 choice、sports、entertainment、teach
    title: "",
    img_url: "",
    content_url: "",
    status: 0,//0 编辑完成 1 编辑中


    careful_news: [],//精选
    corp_news: [],//企业
    entertainment_news: [],//娱乐
    sport_news: [],//体育

    paramSet: function ($e) {

        news_manage_param.title = $.trim($e.find(".title input").val());
        news_manage_param.img_url = $.trim($e.find(".img_url input").val());
        news_manage_param.content_url = $.trim($e.find(".content_url input").val());

    }

};

$(function () {
    news_manage.init();
});

