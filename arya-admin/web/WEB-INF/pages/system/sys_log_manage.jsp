<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/10/17
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<% String contextPath = request.getContextPath().toString(); %>
<script src="<%=contextPath%>/js/sys/sys_log_manage.js"></script>

<div class="sys_log_manage_container container" style="overflow: inherit;">

    <div class="head border-bottom">
        <div class="txt">日志管理</div>
    </div>

    <div class="content">

        <div class="search_container">

			<span class="input-group col-xs-3 item">
				<span class="input-group-addon">板块：</span>
				<select class="form-control chosen-select module_list" multiple
                        data-placeholder="请选择板块">
                    <%--<option>板块1</option>--%>
                </select>
			</span>

            <span class="input-group col-xs-3 item">
				<span class="input-group-addon">操作人</span>
					<select class="form-control chosen-select operator_list" multiple
                            data-placeholder="请选择操作人">
					</select>
				<%--<input class="form-control opRealName" placeholder="请输入操作人姓名">--%>
			</span>

            <span class="input-group col-xs-3 item">
				<span class="input-group-addon">关键字</span>
				<input class="form-control searchCondition" placeholder="模糊查询日志信息">
			</span>

            <div class="btn_list">

				<span class="btn btn-sm btn-primary btn_search"
                      onclick="sys_log_manage.btnSearchClick()">查询
				</span>

                <span class="btn btn-sm btn-primary btn_reset"
                      onclick="sys_log_manage.initSearchCondition()">重置
				</span>

            </div>

        </div>

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td>板块</td>
                    <td>操作人</td>
                    <td>登录名</td>
                    <td>内容</td>
                    <td>时间</td>
                </tr>
                </thead>
                <tbody>
                <%--<tr class="item log_item" data-id="">--%>
                <%--<td class="module_name">模块名称</td>--%>
                <%--<td class="log_operator">模块名称</td>--%>
                <%--<td class="log_content">模块名称</td>--%>
                <%--<td class="log_time">模块名称</td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>


    </div>

    <div class="pager_container">
        <%--<ul class="pagenation" style="float:right;"></ul>--%>
    </div>

</div>

<style type="text/css">
    .sys_log_manage_container .content .table_container table tbody .item td.module_name div,
    .sys_log_manage_container .content .table_container table tbody .item td.log_operator div,
    .sys_log_manage_container .content .table_container table tbody .item td.login_name div {
        width: 80px;
        word-wrap: break-word;
    }

    .sys_log_manage_container .content .table_container table tbody .item td.log_time div {
        width: 130px;
    }

    .sys_log_manage_container .content .table_container table tbody .item td.log_content div {
        word-break: break-all;
        padding: 0 8px;
    }
</style>