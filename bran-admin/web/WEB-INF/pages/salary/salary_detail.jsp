<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/4
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/salary/el_payroll.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/salary/step.css" rel="stylesheet">

<link href="<%=contextPath%>/css/bran/salary/salary_detail.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/salary/salary_detail.js"></script>

<div class="container salary_detail_container">

    <div class="head border-bottom">
        <i class="icon icon-salary"></i>
        <div class="txt">薪资条</div>
    </div>

    <div class="content">

        <div class="col-xs-12 salary_info_container">

            <div class="left_side col-xs-4">

                <div class="head">

                    <div class="name">
                        <%--5月薪资--%>
                    </div>

                    <div class="salary_send_time">
                        <%--2016/07/10--%>
                    </div>

                </div>

                <div class="content">

                    <div class="search_container">
                        <select data-placeholder="请选择员工" multiple
                                class="user_list_search chosen-select">
                            <!--<option value="1">公司1</option>-->
                            <!--<option value="3">公司3</option>-->
                            <!--<option value="2">公司2</option>-->
                        </select>
                    </div>

                    <div class="user_list"></div>

                    <div class="operate_container col-xs-12">

                        <div class="choose_item" onclick="salary_detail.chooseAll()">
                            <img src="image/icon_salary/icon_Unchecked.png">
                            全选
                        </div>

                        <%--<div class="btn btn-sm btn-primary  btn_export" onclick="salary_detail.userSignExport();">--%>
                        <%--导出签收表--%>
                        <%--</div>--%>

                        <div class="btn btn-sm btn-success pull-right btn_del" onclick="salary_detail.userDel();">
                            删除
                        </div>

                    </div>

                </div>

            </div>

            <div class="right_side col-xs-8">

                <div class="head">

                    <div class="txt">

                        <div class="name"></div>
                        <div class="txt_1">的工资单</div>

                    </div>

                    <div class="salary_corp_name_container">
                        <label>代发：</label>
                        <label class="salary_corp_name">
                            <!--苏州不木科技子公司-->
                        </label>
                    </div>

                </div>

                <div class="content">

                    <div class="salary_detail_container">

                        <div class="row txt">
                            <div class="col-xs-3 ">工资条明细</div>
                            <div class="col-xs-3 pull-right btn_detail" onclick="salary_detail.userDetail()">
                                查看详情
                            </div>
                        </div>

                        <div class="salary_detail">

                            <!--<div class="row">-->
                            <!--<div class="col-xs-3 key">实发：</div>-->
                            <!--<div class="col-xs-3 pull-right value">15415.12</div>-->
                            <!--</div>-->

                            <!--<div class="row">-->
                            <!--<div class="col-xs-3 key">实发：</div>-->
                            <!--<div class="col-xs-3 pull-right value">15415.12</div>-->
                            <!--</div>-->

                        </div>

                    </div>

                    <div class="block salary_prompt">

                        <div class="txt">
                            发送完成
                        </div>

                        <div class="send_time">
                            <!--2017/02/12 13:12:02-->
                        </div>

                        <div class="txt_1">
                            您的这一批工资条已发送完成！
                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

