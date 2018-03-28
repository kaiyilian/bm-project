<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/29
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/customer_manage/customer_info.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/customer_manage/customer_info.js"></script>

<div class="container customer_info_container">

    <div class="head border-bottom">
        <div class="txt">客户资料</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">关键字：</span>
                <input class="form-control searchCondition" placeholder="客户名称/简称">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="customer_info.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_customer_info"></table>

            <%--<table class="table table-striped table-bordered table-hover dataTable">--%>
            <%--<thead>--%>
            <%--<tr>--%>
            <%--<td>序号</td>--%>
            <%--<td>到账日</td>--%>
            <%--<td>到账金额</td>--%>
            <%--<td>清单日期</td>--%>
            <%--<td>税前薪资</td>--%>
            <%--<td>个税处理费</td>--%>
            <%--<td>税后薪资</td>--%>
            <%--<td>薪资服务费</td>--%>
            <%--<td>可用余额</td>--%>
            <%--<td>开票金额</td>--%>
            <%--<td>备注</td>--%>
            <%--<td>操作</td>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>

            <%--&lt;%&ndash;<tr class="item" data-id="12">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>1</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>到账日</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>到账金额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>清单日期</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>税前薪资</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>个税处理费</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>税后薪资</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>薪资服务费</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>可用余额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="bill_money">开票金额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="remark">备注</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="operate">&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-primary btn_modify" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;编辑&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-primary btn_save" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;保存&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-default btn_cancel" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;取消&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<tr class="item" data-id="11">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>1</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>到账日</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>到账金额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>清单日期</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>税前薪资</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>个税处理费</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>税后薪资</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>薪资服务费</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>可用余额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="bill_money">开票金额</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="remark">备注</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="operate">&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-primary btn_modify" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;编辑&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-primary btn_save" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;保存&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-default btn_cancel" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;取消&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>

            <%--</tbody>--%>
            <%--</table>--%>

        </div>

    </div>

    <div class="foot">

        <div class="btn_list">

            <div class="btn btn-primary btn-sm btn_down" onclick="customer_info.customerInfoDown()">导出</div>

        </div>

    </div>

</div>

