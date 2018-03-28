<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/4
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/ledger_manage/ledger_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/ledger_manage/ledger_manage.js"></script>

<div class="container ledger_manage_container">

    <div class="head border-bottom">
        <div class="txt">台账管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="col-lg-3 col-xs-4 input-group item">
                <div class="input-group-addon">月份：</div>
                <span class="form-control year_month" readonly></span>
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="ledger_manage.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td>序号</td>
                    <td>到账日</td>
                    <td>到账金额</td>
                    <td>清单日期</td>
                    <td>税前薪资</td>
                    <td>个税处理费</td>
                    <td>税后薪资</td>
                    <td>薪资服务费</td>
                    <td>可用余额</td>
                    <td>开票金额</td>
                    <td>备注</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>

                <%--<tr class="item" data-id="12">--%>
                <%--<td>1</td>--%>
                <%--<td>到账日</td>--%>
                <%--<td>到账金额</td>--%>
                <%--<td>清单日期</td>--%>
                <%--<td>税前薪资</td>--%>
                <%--<td>个税处理费</td>--%>
                <%--<td>税后薪资</td>--%>
                <%--<td>薪资服务费</td>--%>
                <%--<td>可用余额</td>--%>
                <%--<td class="bill_money">开票金额</td>--%>
                <%--<td class="remark">备注</td>--%>
                <%--<td class="operate">--%>

                <%--<button class="btn btn-sm btn-primary btn_modify" onclick="">--%>
                <%--编辑--%>
                <%--</button>--%>

                <%--<button class="btn btn-sm btn-primary btn_save" onclick="">--%>
                <%--保存--%>
                <%--</button>--%>

                <%--<button class="btn btn-sm btn-default btn_cancel" onclick="">--%>
                <%--取消--%>
                <%--</button>--%>

                <%--</td>--%>
                <%--</tr>--%>

                <%--<tr class="item" data-id="11">--%>
                <%--<td>1</td>--%>
                <%--<td>到账日</td>--%>
                <%--<td>到账金额</td>--%>
                <%--<td>清单日期</td>--%>
                <%--<td>税前薪资</td>--%>
                <%--<td>个税处理费</td>--%>
                <%--<td>税后薪资</td>--%>
                <%--<td>薪资服务费</td>--%>
                <%--<td>可用余额</td>--%>
                <%--<td class="bill_money">开票金额</td>--%>
                <%--<td class="remark">备注</td>--%>
                <%--<td class="operate">--%>

                <%--<button class="btn btn-sm btn-primary btn_modify" onclick="">--%>
                <%--编辑--%>
                <%--</button>--%>

                <%--<button class="btn btn-sm btn-primary btn_save" onclick="">--%>
                <%--保存--%>
                <%--</button>--%>

                <%--<button class="btn btn-sm btn-default btn_cancel" onclick="">--%>
                <%--取消--%>
                <%--</button>--%>

                <%--</td>--%>
                <%--</tr>--%>

                </tbody>
            </table>
        </div>

        <div class="pager_container">
            <%--<ul class="pagenation" style="float:right;"></ul>--%>
        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-primary btn-sm btn_down" onclick="ledger_manage.ledgerDown()">下载</div>

        </div>

    </div>

</div>
