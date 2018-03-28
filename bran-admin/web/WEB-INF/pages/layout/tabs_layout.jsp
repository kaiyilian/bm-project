<%--
  Created by IntelliJ IDEA.
  User: LiuJie
  Date: 2016/5/11
  Time: 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row content-tabs" style="margin:0;">
    <%--左移按钮--%>
    <button class="roll-nav roll-left J_tabLeft" onclick="PrevLineTab()">
        <i class="fa fa-backward"></i>
    </button>
    <%--中间tab--%>
    <nav class="page-tabs J_menuTabs">
        <div class="page-tabs-content" id="div_page_tabs">
            <a href="javascript:;" id="tab_index" class="active J_menuTab" onclick="indexTabOnclick()">首页</a>
        </div>
    </nav>
    <%--右移按钮--%>
    <button class="roll-nav roll-right J_tabRight" onclick="NextLineTab()">
        <i class="fa fa-forward"></i>
    </button>
    <button class="roll-nav roll-right dropdown J_tabClose">
        <span class="dropdown-toggle" data-toggle="dropdown">
            关闭操作
            <span class="caret"></span>
        </span>
        <ul role="menu" class="dropdown-menu dropdown-menu-right">
            <%--<li class="J_tabShowActive">--%>
            <%--<a>定位当前选项卡</a>--%>
            <%--</li>--%>
            <%--<li class="divider"></li>--%>
            <li class="J_tabCloseAll" onclick="closeAllTabs()">
                <a>关闭全部选项卡</a>
            </li>
            <li class="J_tabCloseOther" onclick="closeOtherTabs()">
                <a>关闭其他选项卡</a>
            </li>
        </ul>
    </button>
    <%--<a href="login.html" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>--%>
</div>

