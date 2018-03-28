<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/4
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/bran/salary/el_payroll.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bran/salary/step.css" rel="stylesheet">

<link href="<%=contextPath%>/css/bran/salary/salary_create.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/salary/salary_create.js"></script>

<section class="container salary_create_container">

    <ul class="nav nav-pills nav-justified step step-round">

        <li class="active">
            <a>上传Excel表格</a>
        </li>

        <li>
            <a>预览核对工资</a>
        </li>

        <li>
            <a>发送工资条</a>
        </li>

    </ul>

    <div class="step_list">

        <div class="step_item step_1 hide">

            <div class="tl">直接上传您的日常工资表</div>
            <div class="t_preview" onclick="salary_create.previewShow()">
                查看表格规则
            </div>

            <div class="salary_info_container">

                <div class="row">

                    <div>
                        薪资月份：
                    </div>

                    <div class="year_month_container">

                        <select class="year">
                            <option>2017年</option>
                        </select>

                        <select class="month">
                            <option>7月</option>
                        </select>

                    </div>

                </div>

                <div class="row">

                    <div>
                        薪资名称：
                    </div>

                    <div class="salary_name">
                        <input class="" placeholder="工资单、春节福利等，最多8个字" maxlength="8">
                    </div>

                </div>

                <div class="row salary_corp_name_container">

                    <div>
                        子公司名：
                    </div>

                    <div class="salary_corp_name">
                        <input class="" placeholder="代发子公司的名称，最多20个字" maxlength="20">
                    </div>

                </div>

            </div>

            <div class="btn_list">

                <div class="btn btn-orange btn_upload" onclick="salary_create.ChooseFileClick()">
                    上传工资单
                </div>

                <div class="btn_replace active" onclick="salary_create.salaryReplace()">
                    代发
                </div>

            </div>

            <div class="info_prompt">

                <div class="txt">特别声明：</div>

                <ul>
                    <li> 薪资加密存储，任何人都无法查看</li>
                    <li> 支持无痕撤回，线上数据即时删除</li>
                    <li>
                        查看
                        <a href="webPage/html/el_payroll/pc/agreement.html" class="c_1490d2" target="_blank">
                            《招才进宝工资单法律声明及隐私条款》
                        </a>
                    </li>
                </ul>

            </div>

        </div>

        <div class="step_item step_2 hide">

            <div class="salary_excel_info hide">

                <div class="file_name"></div>

                <div class="table_container">
                    <table id="tb_salary"></table>
                </div>

                <div class="tl_check_container">

                    <div class="row">

                        <div class="txt">标题检验</div>

                        <div class="t_preview c_1490d2" onclick="salary_create.previewShow()">
                            查看表格规则
                        </div>

                    </div>

                    <div class="tl_check_list"></div>

                </div>

                <div class="err_msg_container hide">

                    <div class="txt">
                        错误提示
                    </div>

                    <div class="err_msg_list">

                        <!--<div class="item"></div>-->

                    </div>

                </div>

            </div>

            <div class="salary_excel_error hide">

                <div class="tl">
                    实在抱歉，竭尽全力也没能把小主的表格解析出来 ，一定是格式的姿势不对~
                </div>

                <div class="tlc">
                    请依照下方提示检查您的工资表哪里有问题哦！
                </div>

                <div class="btn btn-orange btn_prev_upload" onclick="salary_create.stepPrevUpload()">
                    返回上一步重新上传
                </div>

                <div class="salary_rule_img">
                    <img src="image/icon_salary/payroll_preview.png">
                </div>

            </div>

            <div class="btn_list">

                <div class="btn btn-orange btn_prev_upload" onclick="salary_create.stepPrevUpload()">
                    返回上一步重新上传
                </div>

                <div class="col-xs-offset-1 btn btn-orange btn_next" onclick="salary_create.stepNext()">
                    下一步发送
                </div>

            </div>

        </div>

        <div class="step_item step_3 hide">

            <div class="salary_name">
                <!--2017年9月 标题-->
            </div>

            <div class="payroll_info">

                <div class="pull-left left_side">

                    <div class="txt">员工名单</div>

                    <div class="user_list">

                        <!--<div class="user_item" data-id="1">-->
                        <!--<span class="no">1</span>-->
                        <!--<span>张三</span>-->
                        <!--<div class="user_phone">13115109071</div>-->
                        <!--</div>-->

                    </div>

                </div>

                <div class="pull-right right_side">

                    <div class="txt real_salary_container">
                    <span class="key">
                        <!--实发薪资：-->
                    </span>
                        <span class="value real_salary">
                        <!--18521.21-->
                    </span>
                    </div>

                    <div class="salary_detail">

                        <!--<div class="item">-->
                        <!--<div class="key">身份证号</div>-->
                        <!--<div class="value">32283199200003333</div>-->
                        <!--</div>-->

                        <!--<div class="item">-->
                        <!--<div class="key">年份</div>-->
                        <!--<div class="value">2017</div>-->
                        <!--</div>-->

                        <!--<div class="item">-->
                        <!--<div class="key">年份</div>-->
                        <!--<div class="value">2017</div>-->
                        <!--</div>-->

                    </div>

                </div>

            </div>

            <div class="btn_list">

                <div class="col-xs-offset-1 btn btn-orange btn_send" onclick="salary_create.payrollSend()">
                    发送
                </div>

                <div class="col-xs-offset-2 btn btn-orange btn_prev" onclick="salary_create.stepPrev()">
                    返回上一步
                </div>

            </div>

            <%--<div class="row user_agreement active">--%>

            <%--<div class="choose_item" onclick="salary_create.chooseAgreement()">--%>
            <%--<img src="image/icon_salary/icon_checked.png">--%>
            <%--</div>--%>

            <%--使用工资条即代表您同意--%>

            <%--<a href="webPage/html/el_payroll/pc/agreement.html" target="_blank">--%>
            <%--《招才进宝工资单法律声明及隐私条款》--%>
            <%--</a>--%>

            <%--</div>--%>

        </div>

    </div>

</section>

<div class="modal fade payroll_preview_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--<div class="modal-header">-->
            <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span-->
            <!--aria-hidden="true">&times;</span></button>-->
            <!--</div>-->
            <div class="modal-body">

                <img src="image/icon_salary/payroll_preview.png">

            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div style="display:none">
    <script src="https://s19.cnzz.com/z_stat.php?id=1262291682&web_id=1262291682" language="JavaScript"></script>
</div>
