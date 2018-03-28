<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--左侧导航开始--%>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">

            <li class="nav-header">
                <div class="row border-bottom">
                    <div class="dropdown profile-element col-sm-6">
                        <span><img alt="image" class="img-circle" src="img/user.png"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<span class="clear">
						<span class="block m-t-xs">
						   <strong class="font-bold">
							   <c:out value="${login_name}"/>
						   </strong>
                        </span>
						<span class="text-muted text-xs block">
								<c:out value="${role_name}"/></span>
						</span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" onclick="showChangePwdModel()">修改密码</a>
                            </li>
                            <li class="divider"></li>
                            <li><a onclick="logOut()">注销</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">招才进宝</div>

                    <div class="col-sm-6" style="padding-left: 30px">
						<span class="navbar-minimalize btn btn-primary" href="#">
							<a class="fa fa-bars"></a>
						</span>
                    </div>
                </div>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-home"></i>
                    <span class="nav-label">社保管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="soin_person_manager"
                           onclick="getInsidePageDiv(urlGroup.soin_person_page,
						   'soin_person_manager','参保人信息管理')">参保人信息管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" data-id="soin_order_manager"
                           onclick="getInsidePageDiv(urlGroup.soin_order_manage_page,
						   'soin_order_manager','个人社保订单处理')">个人社保订单处理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" data-id="soin_order_querier"
                           onclick="getInsidePageDiv(urlGroup.soin_order_query_page,
						   'soin_order_querier','个人社保订单查询')">个人社保订单查询</a>
                    </li>
                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="soin_info_import"--%>
                    <%--onclick="getInsidePageDiv('admin/soin/soin_info_import/index','soin_info_import','社保信息导入')">社保信息导入</a>--%>
                    <%--</li>--%>
                    <li>
                        <a class="J_menuItem" data-id="base_info_manager"
                           onclick="getInsidePageDiv(urlGroup.soin_base_info_page,
						   'base_info_manager','社保基础数据管理')">社保基础数据管理</a>
                    </li>

                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="base_info_manager"--%>
                    <%--onclick="getInsidePageDiv('admin/soin/base_info/index/new',--%>
                    <%--'soin_base_info_manage','社保新-基础数据管理')">社保基础数据管理-新</a>--%>
                    <%--</li>--%>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa-exchange"></i>
                    <span class="nav-label">社保对账管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="soin_order_calculate_import"
                           onclick="getInsidePageDiv(urlGroup.order_import_index,
                           'soin_order_calculate_import','订单计算导入')">订单计算导入</a>
                    </li>
                    <li>
                        <a class="J_menuItem" data-id="soin_bill_manage"
                           onclick="getInsidePageDiv(urlGroup.order_manage_index,
                           'soin_bill_manager','订单对账导出')">订单对账导出</a>
                    </li>
                    <li>
                        <a class="J_menuItem" data-id="soin_suppliers"
                           onclick="getInsidePageDiv(urlGroup.soin_in_or_decrease_index,
                           'soin_in_or_decrease','增减员导出 ')">增减员导出</a>
                    </li>

                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="soin_bill_batch_manager"--%>
                    <%--onclick="getInsidePageDiv(urlGroup.order_batch_manage_index,--%>
                    <%--'soin_bill_batch_manager','订单批次删除',function() {--%>
                    <%--order_batch_manage.initData();--%>
                    <%--})">订单批次删除</a>--%>
                    <%--</li>--%>

                    <li>
                        <a class="J_menuItem" data-id="soin_bill_batch_del"
                           onclick="getInsidePageDiv(
						   urlGroup.order_batch_del_index,
                           'soin_bill_batch_del','订单批量删除',
                           function() {
                             order_batch_del.initData();
                           })">
                            订单批量删除
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="soin_bill_batch_manager"
                           onclick="getInsidePageDiv(
						   urlGroup.order_batch_extend_index,
                           'soin_bill_batch_extend','订单批量顺延',function() {})">
                            订单批量顺延
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="soin_suppliers"
                           onclick="getInsidePageDiv(urlGroup.supplier_manage_index,
                           'soin_suppliers','供应商管理 ')">供应商管理 </a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-suitcase"></i>
                    <span class="nav-label">企业管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="corp_user_permission"--%>
                    <%--onclick="getInsidePageDiv(urlGroup.corp_user_page,--%>
                    <%--'corp_user_permission', '企业用户管理')">企业用户管理</a>--%>
                    <%--</li>--%>

                    <li>
                        <a class="J_menuItem" data-id="corp_user_manage"
                           onclick="getInsidePageDiv(urlGroup.corp_user_manage_page,
                    'corp_user_manage', '企业用户管理')">企业用户管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="corp_info_manage_new"
                           onclick="getInsidePageDiv(urlGroup.corp_manage_new_page,
						   'corp_info_manage_new', '企业信息管理')">企业信息管理</a>
                    </li>

                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="corp_service_soin"--%>
                    <%--onclick="getInsidePageDiv(urlGroup.corp_service_soin_page,--%>
                    <%--'corp_service_soin', '社保服务')">--%>
                    <%--社保服务--%>
                    <%--</a>--%>
                    <%--</li>--%>

                    <li>
                        <a class="J_menuItem" data-id="corp_service_entry"
                           onclick="getInsidePageDiv(urlGroup.corp_service_entry_page,
						   'corp_service_entry', '一键入职服务')">
                            一键入职服务
                        </a>
                    </li>

                    <%--<li>--%>
                    <%--<a class="J_menuItem" data-id="corp_service_salary"--%>
                    <%--onclick="getInsidePageDiv(urlGroup.corp_service_salary_page,--%>
                    <%--'corp_service_salary', '薪资代发服务')">--%>
                    <%--薪资代发服务--%>
                    <%--</a>--%>
                    <%--</li>--%>

                    <li>
                        <a class="J_menuItem" data-id="corp_service_fk"
                           onclick="getInsidePageDiv(urlGroup.corp_service_fk_page,
						   'corp_service_fk', '福库服务')">
                            福库服务
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="corp_service_contract"
                           onclick="getInsidePageDiv(urlGroup.corp_service_contract_page,
						   'corp_service_contract', '电子合同服务')">
                            电子合同服务
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="corp_service_attendance"
                           onclick="getInsidePageDiv(urlGroup.corp_service_attendance_page,
							'corp_service_attendance', '考勤服务')">
                            考勤服务
                        </a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">客户商机</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="customer_salary"
                           onclick="getInsidePageDiv(
						   urlGroup.customer_salary_page,'customer_salary', '薪酬')">
                            薪酬
                        </a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">电子工资单</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="electronic_payroll"
                           onclick="getInsidePageDiv(
						   urlGroup.electronic_payroll_page,'electronic_payroll', '电子工资单')">
                            电子工资单
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="wallet_pay_preview"
                           onclick="getInsidePageDiv(
						   urlGroup.wallet_pay_salary_preview_index,'wallet_pay_preview', '钱包发薪预览')">
                            钱包发薪
                        </a>
                    </li>

                </ul>
            </li>

            <shiro:hasPermission name="econtact:template:*">

                <li>
                    <a href="#">
                        <i class="fa fa fa-money"></i>
                        <span class="nav-label">电子合同</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" data-id="contract_template"
                               onclick="getInsidePageDiv(
						   urlGroup.contract_template_manage_page,'contract_template', '模板管理')">
                                模板管理
                            </a>
                        </li>

                    </ul>
                </li>

            </shiro:hasPermission>

            <li>
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">薪资管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="salary_calculate"
                           onclick="getInsidePageDiv(urlGroup.salary_calc_page,
						   'salary_calculate', '薪资计算导入')">薪资计算导入</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="wallet_pay_salary_apply"
                           onclick="getInsidePageDiv(urlGroup.wallet_pay_salary_apply_page,
						   'wallet_pay_salary_apply', '发放批次记录')">发放批次记录</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-bar-chart-o"></i>
                    <span class="nav-label">运营管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="operation_advert_manage"
                           onclick="getInsidePageDiv(urlGroup.advert_manage_page,
						'operation_advert_manage', '广告管理')">广告管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="operation_fk_good_manage"
                           onclick="getInsidePageDiv(urlGroup.fk_good_page,
						'operation_fk_good_manage', '福库商品管理')">福库商品管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="operation_fk_order_manage"
                           onclick="getInsidePageDiv(urlGroup.fk_order_page,
						'operation_fk_order_manage', '福库订单管理')">福库订单管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="operation_fk_notice_manage"
                           onclick="getInsidePageDiv(urlGroup.fk_notice_page,
						'operation_fk_notice_manage', '福库公告')">福库公告</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="operation_fk_coupon_manage"
                           onclick="getInsidePageDiv(urlGroup.fk_coupon_manage_index,
					'operation_fk_coupon_manage', '福库券管理')">福库券管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="operation_push_msg_manage"
                           onclick="getInsidePageDiv(urlGroup.notification_page,
						'operation_push_msg_manage', '推送管理',function() {

						})">推送管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="news_manage"
                           onclick="getInsidePageDiv(urlGroup.news_manage_page,
						'news_manage', '新闻管理',function() {

						})">
                            新闻管理
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="red_packet_record_page"
                           onclick="getInsidePageDiv(urlGroup.red_packet_record_page,
						'red_packet_record', '领红包记录',function() {

						})">
                            领红包记录
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="activity_register_page"
                           onclick="getInsidePageDiv(urlGroup.operation.activity_register.index,
						'activity_register', '活动报名列表',function() {

						})">
                            活动报名列表
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="women_day_activity_page"
                           onclick="getInsidePageDiv(
                               urlGroup.operation.women_day_activity.index,
						'women_day_activity', '3.8活动',function() {

						})">
                            3.8活动
                        </a>
                    </li>

                    <%--<li>
                        <a class="J_menuItem" data-id="wallet_change_record_page"
                           onclick="getInsidePageDiv(
                               urlGroup.operation.wallet_change_record.index,
						'wallet_change_record', '钱包充值提现记录',function() {

						})">
                            钱包充值提现记录
                        </a>
                    </li>--%>
                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-cog"></i>
                    <span class="nav-label">系统管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="admin_sys_user"
                           onclick="getInsidePageDiv(urlGroup.sys_user_manage_page,
						   'admin_sys_user', '系统用户管理')">系统用户管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="admin_sys_role"
                           onclick="getInsidePageDiv(urlGroup.sys_role_manage_page,
						   'admin_sys_role', '角色管理')">角色管理</a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="admin_sys_log"
                           onclick="getInsidePageDiv(urlGroup.sys_log_manage_index,
						    'admin_sys_log', '日志管理')">
                            日志管理
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="admin_sys_config"
                           onclick="getInsidePageDiv(urlGroup.sys_config_page,
						    'admin_sys_config', '配置管理')">配置管理</a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-male"></i>
                    <span class="nav-label">用户管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <li>
                        <a class="J_menuItem" data-id="admin_app_user"
                           onclick="getInsidePageDiv(urlGroup.user_manage.app_user_manage.index,
						    'admin_app_user', 'App用户管理')">
                            App用户管理
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="admin_user_info"
                           onclick="getInsidePageDiv(urlGroup.user_manage.user_info.index,
						    'admin_user_info', '用户信息查询')">
                            用户信息查询
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="wallet_user_info"
                           onclick="getInsidePageDiv(urlGroup.user_manage.wallet.index,
						    'wallet_user_info', '钱包用户查询')">
                            钱包用户查询
                        </a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-male"></i>
                    <span class="nav-label">犯罪记录查询</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="criminal_record_query"
                           onclick="getInsidePageDiv(urlGroup.criminal_record_query_index,
						    'criminal_record_query', '犯罪记录查询')">
                            犯罪记录查询
                        </a>
                    </li>

                </ul>
            </li>

            <li>
                <a href="#">
                    <i class="fa fa fa-male"></i>
                    <span class="nav-label">运维管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem" data-id="log_down"
                           onclick="getInsidePageDiv(urlGroup.operation_manage.log.index,
						    'log_down', '日志下载')">
                            日志下载
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="log_down"
                           onclick="getInsidePageDiv(urlGroup.operation_manage.access_statistics.index,
						    'access_statistics', '访问统计')">
                            访问统计
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="log_down"
                           onclick="getInsidePageDiv(urlGroup.operation_manage.access_detail.index,
						    'access_detail', '访问详情')">
                            访问详情
                        </a>
                    </li>

                </ul>
            </li>

            <li style="display: block;">
                <a href="#">
                    <i class="fa fa fa-male"></i>
                    <span class="nav-label">官网管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <li>
                        <a class="J_menuItem" data-id="recruit_manage"
                           onclick="getInsidePageDiv(urlGroup.bumu_website_manage.recruit_manage.index,
						    'recruit_manage', '招聘管理')">
                            招聘管理
                        </a>
                    </li>

                    <li>
                        <a class="J_menuItem" data-id="bmWebsite_news_manage"
                           onclick="getInsidePageDiv(urlGroup.bumu_website_manage.news_manage.index,
						    'bmWebsite_news_manage', '新闻管理')">
                            新闻管理
                        </a>
                    </li>

                </ul>
            </li>

            <li style="display: block;">
                <a href="#">
                    <i class="fa fa fa-male"></i>
                    <span class="nav-label">组件管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <li>
                        <a class="J_menuItem" data-id="recruit_manage"
                           onclick="getInsidePageDiv(urlGroup.assembly_manage.assembly_list.index,
						    'assembly_list', '组件列表')">
                            组件列表
                        </a>
                    </li>

                </ul>
            </li>

        </ul>
    </div>
</nav>

<!--左侧导航结束-->
<style type="text/css">
    nav #div_page_tabs li.fa {
        position: absolute;
        right: 5px;
        top: 13px;
    }
</style>

