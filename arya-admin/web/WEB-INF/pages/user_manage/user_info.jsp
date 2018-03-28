<%--
  Created by IntelliJ IDEA.
  User: BUMU
  Date: 2018/1/18
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<link href="<%=contextPath%>/css/user_manage/user_info.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/user_manage/user_info.js"></script>

<div class="user_info_container container">

    <div class="head border-bottom">
        <div class="txt">用户信息查询</div>
    </div>

    <div class="content">

        <div class="search_container">

			<span class="input-group col-xs-5 item">
				<span class="input-group-addon">关键字</span>
				<input class="form-control searchCondition" maxlength="11"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       placeholder="请输入电话" value=""
                >
			</span>

            <div class="btn_list">

				<span class="btn btn-sm btn-primary btn_search"
                      onclick="user_info.btnSearchClick()">查询
				</span>

            </div>

        </div>

        <div class="user_info">

            <!-- Nav tabs -->
            <ul class="nav nav-tabs nav_temp" role="tablist">

                <li role="presentation" class="">

                    <a href="#personal_info" role="tab" data-toggle="tab"
                       data-href="personal_info">
                        个人信息
                    </a>

                </li>

                <li role="presentation" class="">

                    <a href="#e_salary_info" role="tab" data-toggle="tab"
                       data-href="e_salary_info">
                        电子工资单
                    </a>

                </li>

                <li role="presentation" class="">

                    <a href="#entry_info" role="tab" data-toggle="tab"
                       data-href="entry_info">
                        入职信息
                    </a>

                </li>

                <li role="presentation" class="">

                    <a href="#wallet_info" role="tab" data-toggle="tab"
                       data-href="wallet_info">
                        钱包查询
                    </a>

                </li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">

                <div role="tabpanel" id="personal_info" class="tab-pane fade">

                    <div class="row">
                        <div class="col-xs-2 txt">当前手机号码：</div>
                        <div class="col-xs-10 txtInfo cur_phone_no">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">历史手机号码：</div>
                        <div class="col-xs-10 txtInfo history_phone_no">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">姓名：</div>
                        <div class="col-xs-10 txtInfo user_name">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">身份证：</div>
                        <div class="col-xs-10 txtInfo user_idCard">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">最后登录时间：</div>
                        <div class="col-xs-10 txtInfo last_login_time">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">最后登录设备类型：</div>
                        <div class="col-xs-10 txtInfo last_login_device_type">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">App版本：</div>
                        <div class="col-xs-10 txtInfo app_version">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 txt">最后访问时间：</div>
                        <div class="col-xs-10 txtInfo last_query_time">
                            <%--13113113111--%>
                        </div>
                    </div>

                </div>

                <div role="tabpanel" id="e_salary_info" class="tab-pane fade">

                    <div class="table_container">
                        <table id="tb_e_salary"></table>
                    </div>

                </div>

                <div role="tabpanel" id="entry_info" class="tab-pane fade">

                    <div class="entry_info_block app_entry">

                        <div class="entry_info_tl">
                            App入职信息
                        </div>

                        <div class="entry_info_content">

                            <div class="app_entry_item col-xs-4">
                                <div class="txt col-xs-4">姓名：</div>
                                <div class="txtInfo col-xs-8 name">
                                </div>
                            </div>

                            <div class="app_entry_item col-xs-4">
                                <div class="txt col-xs-4">性别：</div>
                                <div class="txtInfo col-xs-8 sex">
                                </div>
                            </div>

                            <div class="app_entry_item col-xs-4">
                                <div class="txt col-xs-4">身份证有效期：</div>
                                <div class="txtInfo col-xs-8 validity">
                                </div>
                            </div>

                            <div class="app_entry_item col-xs-4">
                                <div class="txt col-xs-4">身份证：</div>
                                <div class="txtInfo col-xs-8 idCard">
                                </div>
                            </div>

                            <div class="app_entry_item col-xs-4">
                                <div class="txt col-xs-4">民族：</div>
                                <div class="txtInfo col-xs-8 nation">
                                </div>
                            </div>

                        </div>

                    </div>

                    <div class="entry_info_block emp_prospective">

                        <div class="entry_info_tl">
                            待入职列表
                        </div>

                        <div class="entry_info_content">

                            <div class="table_container">
                                <table id="tb_emp_prospective"></table>
                            </div>

                        </div>

                    </div>

                    <div class="entry_info_block emp_roster">

                        <div class="entry_info_tl">
                            花名册列表
                        </div>

                        <div class="entry_info_content">

                            <div class="table_container">
                                <table id="tb_emp_roster"></table>
                            </div>

                        </div>

                    </div>

                </div>

                <div role="tabpanel" id="wallet_info" class="tab-pane fade">

                    <div class="wallet_info_block wallet_info">

                        <div class="wallet_info_tl">
                            钱包信息
                        </div>

                        <div class="wallet_info_content">

                            <div class="wallet_info_item col-xs-6">
                                <div class="txt col-xs-4">钱包账号：</div>
                                <div class="txtInfo col-xs-8 account"></div>
                            </div>

                            <div class="wallet_info_item col-xs-6">
                                <div class="txt col-xs-4">姓名：</div>
                                <div class="txtInfo col-xs-8 name"></div>
                            </div>

                            <div class="wallet_info_item col-xs-6">
                                <div class="txt col-xs-4">钱包注册手机：</div>
                                <div class="txtInfo col-xs-8 register_phone"></div>
                            </div>

                            <div class="wallet_info_item col-xs-6">
                                <div class="txt col-xs-4">身份证：</div>
                                <div class="txtInfo col-xs-8 idCard"></div>
                            </div>

                        </div>

                    </div>

                    <div class="wallet_info_block wallet_bank_list">

                        <div class="wallet_info_tl">
                            银行卡列表
                        </div>

                        <div class="wallet_info_content">

                            <div class="table_container">
                                <table id="tb_wallet_bank"></table>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>