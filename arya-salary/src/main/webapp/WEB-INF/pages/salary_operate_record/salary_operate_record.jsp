<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/11
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/arya/salary_operate_record/salary_operate_record.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/arya/salary_operate_record/salary_operate_record.js"></script>

<div class="container salary_operate_record_container">

    <div class="head border-bottom">
        <div class="txt">薪资操作反馈管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-4 item">
                <span class="input-group-addon">关键字</span>
                <input class="form-control search_condition" placeholder="客户名称/城市" maxlength="8">
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="salary_operate_record.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">

            <table id="tb_record"></table>
            <%--<table class="table table-striped table-bordered table-hover dataTable">--%>

            <%--<thead>--%>
            <%--<tr>--%>
            <%--<td style="width:42px;"></td>--%>
            <%--<td>序号</td>--%>
            <%--<td>客户名称</td>--%>
            <%--<td>城市</td>--%>
            <%--<td>计算反馈</td>--%>
            <%--<td>导入时间</td>--%>
            <%--<td>备注</td>--%>
            <%--<td>操作</td>--%>
            <%--</tr>--%>
            <%--</thead>--%>

            <%--<tbody>--%>

            <%--&lt;%&ndash;<tr class="item" data-id="12">&ndash;%&gt;--%>

            <%--&lt;%&ndash;<td class="choose_item" onclick="salary_operate_record.chooseItem(this)">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<img src="img/icon_Unchecked.png"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>1</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>公司名字</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>城市</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td>计算反馈</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="operate">&ndash;%&gt;--%>

            <%--&lt;%&ndash;<button class="btn btn-sm btn-danger btn_del" onclick="">&ndash;%&gt;--%>
            <%--&lt;%&ndash;删除&ndash;%&gt;--%>
            <%--&lt;%&ndash;</button>&ndash;%&gt;--%>

            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>

            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>

            <%--</tbody>--%>
            <%--</table>--%>
        </div>

        <div class="foot">

            <%--<div class="choose_item" onclick="salary_operate_record.chooseAll()"--%>
            <%--data-html="true"--%>
            <%--data-toggle="tooltip"--%>
            <%--data-placement="top"--%>
            <%--title="<p style='width:80px;'>选择查询条件下的所有内容</p>">--%>
            <%--<img src="img/icon_Unchecked.png"/>--%>
            <%--<span>全选</span>--%>
            <%--</div>--%>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_add"
                     onclick="salary_operate_record.recordExport()">
                    导出
                </div>

                <div class="btn btn-sm btn-primary btn_del"
                     onclick="salary_operate_record.recordDel()">
                    删除
                </div>

            </div>

        </div>

        <%--<div class="pager_container">--%>
        <%--<ul class="pagenation" style="float:right;"></ul>--%>
        <%--</div>--%>

    </div>

</div>

