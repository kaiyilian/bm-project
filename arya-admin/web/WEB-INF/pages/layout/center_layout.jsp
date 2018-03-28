<%--s
  User: CuiMengxin
  Date: 2015/11/2
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="page-wrapper" class="gray-bg dashbard-1">

    <%--顶测Tab选项栏--%>
    <div class="row content-tabs" style="z-index: 4;">
        <button class="roll-nav roll-left J_tabLeft" onclick="selectBackwardTab()">
            <i class="fa fa-backward"></i>
        </button>
        <nav class="page-tabs J_menuTabs">
            <div class="page-tabs-content" id="div_page_tabs">
                <a href="javascript:;" class="active J_menuTab" id="index"
                   onclick="menuTabOnclick('index')">首页</a>
            </div>
        </nav>
        <button class="roll-nav roll-right J_tabRight" onclick="selectForwardTab()">
            <i class="fa fa-forward"></i>
        </button>
        <button class="roll-nav roll-right dropdown J_tabClose">
            <span class="dropdown-toggle" data-toggle="dropdown">关闭操作<span class="caret"></span></span>
            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                <li class="J_tabShowActive" onclick="closeSelectedTab()"><a>关闭当前选项卡</a>
                </li>
                <li class="divider"></li>
                <li class="J_tabCloseAll" onclick="closeAllTabs()"><a>关闭全部选项卡</a>
                </li>
                <li class="J_tabCloseOther" onclick="closeUnSelectTabs()"><a>关闭其他选项卡</a>
                </li>
            </ul>
        </button>
    </div>
    <!--顶侧部分结束-->

    <%--content start--%>
    <div class="row J_mainContent" id="content-main" style="overflow:auto;"></div>
    <%--content end--%>

    <div class="footer">
        <div class="pull-left">
            版权所有&copy; 2015-2017
            <a href="http://bumuyun.com/" target="_blank">不木科技</a>
        </div>
        <div class="pull-right" onclick="window.open('webPage/html/versionInfo.html')">
            V2.6
        </div>
    </div>

</div>

