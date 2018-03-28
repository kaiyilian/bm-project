<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/3/2
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<script src="<%=contextPath%>/js/user_manage/wallet_user_manage.js"></script>

<style>
    .app_user_manage_container .content .table_container table td.user_use_phone_no {
        width: 160px;
        word-break: break-all; /*非正常字符 的转行*/
    }
</style>

<div class="wallet_user_manage_container container">

    <div class="head border-bottom">
        <div class="txt">钱包用户管理</div>
    </div>

    <div class="content">

        <div class="search_container">

			<span class="input-group col-xs-5 item">
				<span class="input-group-addon">关键字</span>
				<input class="form-control searchCondition"
                       placeholder="请输入App手机账号、钱包账户、姓名、身份证">
			</span>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search"
                        onclick="wallet_user_manage.btnSearchClick()">
                    查询
                </div>

                <div class="btn btn-sm btn-primary btn_export"
                     onclick="wallet_user_manage.excelExport()">
                    导出
                </div>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_wallet_user_manage"></table>
        </div>

    </div>

</div>