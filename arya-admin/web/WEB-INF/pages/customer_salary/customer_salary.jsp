<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/13
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/customer_salary/customer_salary.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/customer_salary/customer_salary.js"></script>

<div class="customer_salary_container container">

    <div class="head border-bottom">
        <div class="txt">需求管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">关键字：</span>
                <input class="form-control searchCondition" placeholder="企业名称、姓名">
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">客户来源：</span>
                <select class="form-control custom_source">
                    <option value="">全部</option>
                    <option value="0">试用申请</option>
                    <option value="1">社保大厅</option>
                    <option value="2">客户商机</option>
                </select>
            </div>

            <div class="input-group col-xs-3 item">
                <span class="input-group-addon">处理结果：</span>
                <select class="form-control deal_result">
                    <option value="">全部</option>
                    <option value="0">意向客户</option>
                    <option value="1">无效客户</option>
                    <option value="2">洽谈中</option>
                </select>
            </div>

            <div class="btn_list">

                <div class="btn btn-sm btn-primary btn_search" onclick="customer_salary.btnSearchClick()">
                    查询
                </div>

            </div>

        </div>

        <div class="table_container">
            <table class="table table-striped table-bordered table-hover dataTable">
                <thead>
                <tr>
                    <td class="choose_item">
                        <%--<img src="img/icon_Unchecked.png"--%>
                        <%--data-html="true"--%>
                        <%--data-toggle="tooltip"--%>
                        <%--data-placement="right"--%>
                        <%--title="<p style='width:80px;'>选择当前页所有选项</p>"/>--%>
                    </td>
                    <td>提交时间</td>
                    <td>企业名称</td>
                    <td>联系人姓名</td>
                    <td>联系人手机号</td>
                    <td>企业所在地</td>
                    <td>状态</td>
                    <td>客户来源</td>
                    <td>留言</td>
                    <td>处理结果</td>
                    <td>批注</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>

                <%--<tr class="item" data-id="1">--%>
                <%--<td class="choose_item" onclick="">--%>
                <%--<img src="img/icon_Unchecked.png"/>--%>
                <%--</td>--%>
                <%--<td class="corp_name">aa公司</td>--%>
                <%--<td class="contact_name">名字</td>--%>
                <%--<td class="contact_phone">13115100011</td>--%>
                <%--<td class="corp_area">苏州</td>--%>
                <%--<td class="status">已处理</td>--%>
                <%--<td class="operate">--%>

                <%--<div class="btn btn-sm btn-primary btn_modify" onclick="fk_coupon_manage.couponModifyModalShow(this)">--%>
                <%--确认处理--%>
                <%--</div>--%>

                <%--<div class="btn btn-sm btn-danger btn_del" onclick="fk_coupon_manage.couponDel(this)">--%>
                <%--删除--%>
                <%--</div>--%>

                <%--</td>--%>

                <%--</tr>--%>


                </tbody>
            </table>
        </div>

    </div>

    <div class="foot">

        <%--<div class="choose_item" onclick="fk_coupon_manage.chooseAll()"--%>
        <%--data-html="true"--%>
        <%--data-toggle="tooltip"--%>
        <%--data-placement="top"--%>
        <%--title="<p style='width:80px;'>选择查询条件下的所有内容</p>">--%>
        <%--<img src="img/icon_Unchecked.png"/>--%>
        <%--<span>全选</span>--%>
        <%--</div>--%>

        <div class="btn_list">

            <div class="btn btn-sm btn-primary btn_deal" onclick="customer_salary.userDealMore()">
                确认处理
            </div>

            <div class="btn btn-sm btn-primary btn_del" onclick="customer_salary.userDelMore()">
                删除
            </div>

        </div>

    </div>

    <div class="pager_container">
        <%--<ul class="pagenation" style="float:right;"></ul>--%>
    </div>

</div>

<div class="modal fade customer_remark_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">批注信息</h4>
            </div>
            <div class="modal-body">

                <div class="row">

                    <div class="col-xs-3 txt">处理结果：</div>
                    <div class="col-xs-9 deal_result_list">
                        <button class="btn btn-sm btn-default" data-val="0">意向客户</button>
                        <button class="btn btn-sm btn-default" data-val="1">无效客户</button>
                        <button class="btn btn-sm btn-default" data-val="2">洽谈中</button>
                    </div>

                </div>

                <div class="row">

                    <div class="col-xs-3 txt">添加批注：</div>
                    <div class="col-xs-9">
                        <textarea class="form-control remark" placeholder="请输入批注"></textarea>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <button type="button" class="btn btn-primary" onclick="customer_salary.salaryRemarkSave()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

