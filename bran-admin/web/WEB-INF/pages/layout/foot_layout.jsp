<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="foot_layout">
    <div>版权所有：苏州不木网络科技有限公司</div>
    <div>客服电话：4006-710-710</div>
    <div class="version_search" onclick="versionSearch()">
        版本号：v2.6
    </div>
</div>

<script>
    var versionSearch = function () {
        var url = '<%=contextPath%>/webPage/html/versionInfo.html';
        window.open(url);
    };
</script>