/**
 * Created by CuiMengxin on 2015/11/12.
 */

var PERMISSION_DENIED_CODE = "2003";
var SESSION_TIMEOUT = "2004";

//加载动画2
var showLoadPageHUD = function () {
    $('#content-main').append(hud);
}

var dismissLoadPageHUD = function () {
    $('#div_load_hud').remove();
}

//选项卡及Tab内容相关JS
var existedTabIdArrya = new Array("index");//保存选项卡的数组
var selectedTabId = null;

var divTabShowWidth = 0;//tab一行可展示的宽度
var tabLineIndex = 1;//tab在当前页  从第1页开始
var tabLineCount = 0;//判断tab一行可以 有几个item
var tabLineTotal = 0;//tab一共的页数
var $itemTabWidth = 0;//每个tab的宽度（含padding）

$(function () {

    $itemTabWidth = $("#page-wrapper > .content-tabs .J_menuTab").width() + 30 + 1;
    //tab可展示的长度
    divTabShowWidth = $("#page-wrapper > .content-tabs").width() -
        ($("#page-wrapper > .content-tabs .J_tabLeft").width() + 12) -
        ($("#page-wrapper > .content-tabs .J_tabRight").width() + 12) -
        ($("#page-wrapper > .content-tabs .J_tabClose").width() + 12);
    //判断tab一行可以 有几个item
    tabLineCount = Math.floor(divTabShowWidth / $itemTabWidth);//

    initIndex();//首页初始化

});

//首页初始化
var initIndex = function () {

    var pageTabId = "index";

    //页面内容
    var div = $("<div></div>").appendTo($('#content-main'));
    div.addClass("wrapper wrapper-content");
    div.attr("id", "page_" + pageTabId);
    showPageLoadHUD('#page_' + pageTabId);//加载动画

    ajaxSetup();
    $.ajax(urlGroup.index_manage_page, {
        async: true,
        success: function (data, status, jqXHR) {
            if (data["code"] === PERMISSION_DENIED_CODE) {
                toastr.error("页面没有访问权限");
                removeTab(pageTabId);
                console.log("remove" + pageTabId);
            }
            else if (data["code"] === SESSION_TIMEOUT) {
                location.href = "login";
            }
            else {
                $('#page_' + pageTabId).html(data); //填充内容
                menuTabOnclick(pageTabId);//
            }
        }
    });

};

//取消选中的选项卡
var cancelActiveMenuTab = function (pageTabId) {
    $('#div_page_tabs >a').each(function () {
        if ($(this).attr("id") != pageTabId) {
            $(this).attr("class", "J_menuTab");
            $('#page_' + $(this).attr("id")).hide();
        }
        else {
            $(this).attr("class", "active J_menuTab");
        }
    });
};

//寻找当前选中的tab的索引
var findSelectedTabIndex = function findSelectedTabIndex() {
    var selectIndex = 0;
    $('#div_page_tabs >a').each(function () {
        if ($(this).attr("class") != "J_menuTab") {
            selectIndex = $.inArray($(this).attr("id"), existedTabIdArrya);
        }
    });
    return selectIndex;
};

/**
 * 点击左侧菜单栏，请求tab的内容div
 * @param requestURL 请求actioin的路由。
 * @param pageTabId pageTabId:选项卡Id。
 * @param pageName pageName:选项卡名称
 */
var getInsidePageDiv = function (requestURL, pageTabId, pageName, succFun) {
    if ($.inArray(pageTabId, existedTabIdArrya) < 0) {
        existedTabIdArrya.push(pageTabId);

        //添加选项卡 tab
        var a = $("<a></a>").appendTo($('#div_page_tabs'));
        a.text(pageName);
        a.attr("id", pageTabId);
        a.addClass("active J_menuTab");
        a.click(function () {
            menuTabOnclick(pageTabId, succFun);//
        });

        //删除按钮 tab中
        var li = $("<li></li>").appendTo(a);
        li.addClass("fa fa-times-circle");
        li.attr("onclick", "removeTab('" + pageTabId + "')");

        //页面内容
        var div = $("<div></div>").appendTo($('#content-main'));
        div.addClass("wrapper wrapper-content");
        div.attr("id", "page_" + pageTabId);
        //div.attr("style","overflow-y:hidden");
        showPageLoadHUD('#page_' + pageTabId);//加载动画

        ajaxSetup();
        $.ajax(requestURL, {
            async: true,
            success: function (data, status, jqXHR) {
                if (data["code"] === PERMISSION_DENIED_CODE) {
                    toastr.error("页面没有访问权限");
                    removeTab(pageTabId);
                    console.log("remove" + pageTabId);
                }
                else if (data["code"] == SESSION_TIMEOUT) {
                    location.href = "login";
                }
                else {
                    $('#page_' + pageTabId).html(data); //填充内容
                    menuTabOnclick(pageTabId, succFun);//
                }
            }
        });
    }
    else {
        menuTabOnclick(pageTabId, succFun);
    }
};

//选项卡选中事件
var menuTabOnclick = function (pageTabId, succFun) {

    //防止点击关闭选项卡事件后触发这个事件
    if ($.inArray(pageTabId, existedTabIdArrya) >= 0) {
        $('#' + pageTabId).attr("class", "active J_menuTab");
        selectedTabId = pageTabId;
        cancelActiveMenuTab(pageTabId);//取消选中的选项卡
        $('#page_' + pageTabId).show();//当前tab对应的页面 显示

        tabInit(pageTabId);//tab内容初始化

        //当前tab对应的navbar 显示
        $("#side-menu > li > ul > li > a").each(function () {

            var id = $(this).attr("data-id");
            if (id == pageTabId) {
                //$(this).closest("li").click();
                $(this).closest("ul").addClass("in");
                $(this).closest("ul").parent("li").addClass("active")
                    .siblings("li").removeClass("active").find(".in").removeClass("in");
            }

        });

        //如果有回掉，就执行
        if (succFun) {
            succFun();
        }
    }

};

//tab内容初始化
var tabInit = function (pageTabId) {
    var divTabContentWidth = $("#div_page_tabs").width();//所有tab的总的长度
    tabLineTotal = Math.ceil($("#page-wrapper > .content-tabs .J_menuTab").length / tabLineCount);//总行数
    tabLineIndex = Math.ceil(($("#" + pageTabId).index() + 1) / tabLineCount);//从第一页开始

    var $itemTab = $("#page-wrapper > .content-tabs #div_page_tabs .J_menuTab");//每项tab

    //第一步 判断当前标签是否在一屏内

    var currentTabIndex = $("#" + pageTabId).index();//当前标签index

    var prevTabWidth = 0;//当前标签之前标签的总长度

    //遍历 该项标签之前的标签（包含当前标签）
    for (var i = 0; i <= currentTabIndex; i++) {
        var itemWidth = $itemTab.eq(i).width() + 30 + 1;
        prevTabWidth += itemWidth;//获取之前标签的总长度
    }
    //如果当前总长度小于 可展示长度
    if (prevTabWidth <= divTabShowWidth) {
        //前方左右标签显示
        $("#page-wrapper > .content-tabs #div_page_tabs").animate({marginLeft: 0}, "fast");
    }
    else {
        var marginLeft = 0;//向左移动的长度

        //如果当前标签之后 还有标签
        if ($("#" + pageTabId).next(".J_menuTab").length > 0) {

            var showTabWidth = 0;//需要显示的tab的总长度

            //遍历 该项标签(后一个标签) 之前的标签
            for (var i = 0; i <= currentTabIndex + 1; i++) {
                var itemWidth = $itemTab.eq(i).width() + 30 + 1;
                showTabWidth += itemWidth;//获取 要显示标签的总长度
            }

            var moreWidth = showTabWidth - divTabShowWidth;//超出的内容

        }
        else {   //如果当前标签之后 没有标签
            var moreWidth = divTabContentWidth - divTabShowWidth;//超出的内容
        }

        $itemTab.each(function () {
            var itemWidth = $(this).width() + 30 + 1;//对应标签的长度

            if (moreWidth > 0) {
                marginLeft += itemWidth;
                moreWidth = moreWidth - itemWidth;
            }
            else {
                return;
            }
        });
        $("#page-wrapper > .content-tabs #div_page_tabs")
            .animate({marginLeft: -marginLeft}, "fast")

    }

};

//关闭选项卡事件
var removeTab = function (pageTabId) {
    var tabIndex = $.inArray(pageTabId, existedTabIdArrya);
    if (tabIndex > 0) {//不能关闭首页
        existedTabIdArrya.splice(tabIndex, 1);//先从数组中删除
        $('#' + pageTabId).remove();//删除tab
        var tabDivId = "page_" + pageTabId;
        $('#' + tabDivId).remove();//删除div
        if (existedTabIdArrya.length <= tabIndex)
            menuTabOnclick(existedTabIdArrya[--tabIndex]);//选中前面的tab
        else
            menuTabOnclick(existedTabIdArrya[tabIndex]);//选中后面的tab
    }
};

//选中上一个tab事件
var selectBackwardTab = function () {
    var selecedIndex = findSelectedTabIndex();
    if (selecedIndex > 0) {
        menuTabOnclick(existedTabIdArrya[--selecedIndex]);
    }
    else menuTabOnclick(existedTabIdArrya[0]);
};

//选中下一个tab事件
var selectForwardTab = function () {
    var selecedIndex = findSelectedTabIndex();
    if (selecedIndex < existedTabIdArrya.length - 1) {
        menuTabOnclick(existedTabIdArrya[++selecedIndex]);
    }
    else menuTabOnclick(existedTabIdArrya[existedTabIdArrya.length - 1]);
};

//关闭当前选项卡
var closeSelectedTab = function () {
    $('#div_page_tabs >a').each(function () {
        if ($(this).attr("class") != "J_menuTab") {
            removeTab($(this).attr("id"));
        }
    });
};

//关闭全部选项卡
var closeAllTabs = function () {
    $('#div_page_tabs >a').each(function () {
        removeTab($(this).attr("id"));
    });
};

//关闭其他选项卡
var closeUnSelectTabs = function () {
    if ($.inArray(selectedTabId, existedTabIdArrya) == 0) {
        closeAllTabs();
        return;
    }

    if (existedTabIdArrya.length > 2) {
        for (var i = 2; i < existedTabIdArrya.length; i++) {
            if (existedTabIdArrya[i] == selectedTabId) {
                var temp = existedTabIdArrya[1];
                existedTabIdArrya[1] = selectedTabId;
                existedTabIdArrya[i] = temp;
                break;
            }
        }
        while (existedTabIdArrya.length > 2) {
            removeTab(existedTabIdArrya[2]);
        }
    }
};


var showChangePwdModel = function () {
    $("#change_pwd_modal").modal("show");
};

/**
 * 修改密码
 */
var changePwd = function () {
    var pwd = $("#main_password").val();
    var pwd_new = $("#main_new_password").val();
    var pwd_new_confirm = $("#main_confirm_password").val();
    if (pwd_new != pwd_new_confirm) {
        toastr.error("两次密码不一致！");
    } else {
        var params = {"old_pwd": pwd, "new_pwd": pwd_new};
        aryaPostRequest("admin/change_pwd", params, function (data) {
            if (data["code"] == RESPONSE_OK_CODE) {
                $("#change_pwd_modal").modal("hide");
                toastr.success("修改密码成功!");
            } else
                toastr.error(data["msg"]);
        }, function (data) {
            toastr.error(data["msg"]);
        });
    }
};

/**
 * 退出登录
 */
var logOut = function () {
    aryaPostRequest("signout", null, function (data) {
        if (data["code"] == RESPONSE_OK_CODE) {
            location.href = "login";
        } else
            toastr.error(data["msg"]);
    }, function (data) {

    });
};

$(document).ready(function () {
    //$(window).bind('beforeunload', function () {
    //	return '注意：离开或刷新整个页面即退出登录！';
    //});
    //$(window).unload(function () {
    //	aryaPostRequest("signout", null, function (data) {
    //	}, function (data) {
    //
    //	});
    //});
});


/**
 * 点击左侧菜单栏，请求tab的内容div
 * @param requestURL 请求actioin的路由。
 * @param pageTabId pageTabId:选项卡Id。
 * @param pageName pageName:选项卡名称
 */
var getInsidePageDiv1 = function (requestURL, pageTabId, pageName, succFun) {

    if ($.inArray(pageTabId, existedTabIdArrya) < 0) {
        existedTabIdArrya.push(pageTabId);

        //添加选项卡 tab
        var a = $("<a></a>").appendTo($('#div_page_tabs'));
        a.text(pageName);
        a.attr("id", pageTabId);
        a.addClass("active J_menuTab");
        a.click(function () {
            menuTabOnclick(pageTabId, succFun);//
        });

        //删除按钮 tab中
        var li = $("<li></li>").appendTo(a);
        li.addClass("fa fa-times-circle");
        li.attr("onclick", "removeTab('" + pageTabId + "')");

        //页面内容
        var div = $("<div></div>").appendTo($('#content-main'));
        div.addClass("wrapper wrapper-content");
        div.attr("id", "page_" + pageTabId);
        //div.attr("style","overflow-y:hidden");
        showPageLoadHUD('#page_' + pageTabId);//加载动画

        debugger


        //var file = page_url_map.get(requestURL);
        //
        //var s = include(file);
        $('#page_' + pageTabId).load(requestURL);

        //var data = "<%@ include file='" + file + "' %>";

        ////$('#page_' + pageTabId).html(data); //填充内容
        //menuTabOnclick(pageTabId, succFun);//

        //location.href = file;


        //$.ajax({
        //	type: "GET",
        //	url: file,
        //	dataType: "html",
        //	async: false,
        //	success: function (data) {
        //		console.log(data);
        //	},
        //	error: function (data) {
        //		console.log("error");
        //		console.log(data);
        //	},
        //	complete: function (data) {
        //		console.log("数据");
        //		console.log(data)
        //	}
        //});


    }
    else {
        menuTabOnclick(pageTabId, succFun);
    }

};
