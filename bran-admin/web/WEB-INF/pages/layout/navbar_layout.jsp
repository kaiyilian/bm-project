<%--
  User: LiuJie
  Date: 2016/5/11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="bumu" uri="/WEB-INF/bumu.tld" %>

<%--左侧菜单栏--%>
<script src="<%=contextPath%>/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<!-- 自定义js 左侧菜单栏-->
<script src="<%=contextPath%>/js/hplus.min.js?v=3.0.0"></script>

<script>
    $(function () {
        $(".sidebar-collapse").height("calc(100%-130px)");
    });
</script>

<div id="userPermissions" style="display:none">
    <bumu:userPermissions></bumu:userPermissions>
</div>

<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation" id="navbar_layout">
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">

            <li class="active">

                <a href="">
                    <i class="fa icon-index"></i>
                    <span class="nav-label" onclick="getInsidePageDiv(urlGroup.home.index,'index')">
						首页
					</span>
                    <%--<span class="fa arrow"></span>--%>
                </a>

            </li>

            <li>

                <a href="">
                    <i class="fa icon-emp-manage"></i>
                    <span class="nav-label">员工管理</span>
                    <span class="fa arrow"></span>
                </a>

                <ul class="nav nav-second-level">

                    <bumu:hasPermission name="employee:prospective:*">
                        <li>
                            <a class="J_menuItem" data-id="emp_prospective_list"
                               onclick="getInsidePageDiv(urlGroup.employee.prospective.index,
                           'emp_prospective_list','员工入职')">
                                员工入职
                            </a>
                        </li>
                    </bumu:hasPermission>

                    <bumu:hasPermission name="employee:roster:*">

                        <%--<li>--%>
                        <%--<a class="J_menuItem" data-id="emp_roster_list"--%>
                        <%--onclick="getInsidePageDiv(urlGroup.employee.roster.index,--%>
                        <%--'emp_roster_list', '花名册')">花名册</a>--%>
                        <%--</li>--%>

                        <li>
                            <a class="J_menuItem" data-id="emp_roster"
                               onclick="getInsidePageDiv(urlGroup.employee.roster.index,
                           'emp_roster', '花名册')">
                                花名册
                            </a>
                        </li>

                    </bumu:hasPermission>

                    <bumu:hasPermission name="employee:leave:*">

                        <li>
                            <a class="J_menuItem" data-id="emp_leave_list"
                               onclick="getInsidePageDiv(urlGroup.employee.leave.index,
                           'emp_leave_list', '离职员工')">离职员工</a>
                        </li>

                    </bumu:hasPermission>

                    <bumu:hasPermission name="employee:structure:*">
                        <li>
                            <a class="J_menuItem" data-id="department_structure"
                               onclick="getInsidePageDiv(
							   urlGroup.employee.department_structure.index,'department_structure',
							   '部门')">组织架构</a>
                        </li>
                    </bumu:hasPermission>

                    <bumu:hasPermission name="employee:setting:*">
                        <%--<li>--%>
                        <%--<a class="J_menuItem" data-id="common_setting"--%>
                        <%--onclick="getInsidePageDiv(--%>
                        <%--urlGroup.employee.setting.index,'common_setting',--%>
                        <%--'设置')">员工配置</a>--%>
                        <%--</li>--%>

                        <li>
                            <a class="J_menuItem" data-id="emp_setting"
                               onclick="getInsidePageDiv(
							   urlGroup.employee.setting.index,'emp_setting',
							   '员工配置')">员工配置</a>
                        </li>
                    </bumu:hasPermission>

                </ul>

            </li>

            <li>
                <a href="">
                    <i class="fa icon-schedule-manage"></i>
                    <span class="nav-label">考勤管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <bumu:hasPermission name="attendance:workShiftType:*">

                        <li>
                            <a class="J_menuItem" data-id="schedule_manage"
                               onclick="getInsidePageDiv(urlGroup.attendance.schedule.index,
							   'schedule_manage','班次管理')">班次管理</a>
                        </li>

                    </bumu:hasPermission>

                    <bumu:hasPermission name="attendance:rule:*">

                        <li>
                            <a class="J_menuItem" data-id="schedule_rule"
                               onclick="getInsidePageDiv(urlGroup.attendance.schedule_rule.index,
							   'schedule_rule')">排班规律</a>
                        </li>

                    </bumu:hasPermission>

                    <bumu:hasPermission name="attendance:schedule_view:*">

                        <li>
                            <a class="J_menuItem" data-id="schedule_view"
                               onclick="getInsidePageDiv(urlGroup.attendance.schedule_view.index,
						   'schedule_view','排班视图')">排班视图</a>
                        </li>

                    </bumu:hasPermission>

                    <bumu:hasPermission name="attendance:detail:*">

                        <li>
                            <a class="J_menuItem" data-id="attendance_detail"
                               onclick="getInsidePageDiv(urlGroup.attendance.attendance_detail.index,
                    'attendance_detail','出勤明细')">
                                出勤明细
                            </a>
                        </li>
                    </bumu:hasPermission>

                    <bumu:hasPermission name="attendance:summary:*">

                        <li>
                            <a class="J_menuItem" data-id="attendance_summary"
                               onclick="getInsidePageDiv(urlGroup.attendance.summary.index,
						   'attendance_summary','出勤汇总')">
                                出勤汇总
                            </a>
                        </li>
                    </bumu:hasPermission>

                    <%--<bumu:hasPermission name="attendance:detail:*">--%>
                    <%--&lt;%&ndash;<li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a class="J_menuItem" data-id="attendance"&ndash;%&gt;--%>
                    <%--&lt;%&ndash;onclick="getInsidePageDiv(urlGroup.attendance_index,&ndash;%&gt;--%>
                    <%--&lt;%&ndash;'attendance','出勤')">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;出勤&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                    <%--</bumu:hasPermission>--%>

                    <%--<bumu:hasPermission name="attendance:feedback:*">--%>
                    <%--&lt;%&ndash;<li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a class="J_menuItem" data-id="attendance_feedback"&ndash;%&gt;--%>
                    <%--&lt;%&ndash;onclick="getInsidePageDiv(urlGroup.attendance_feedback_index,&ndash;%&gt;--%>
                    <%--&lt;%&ndash;'attendance_feedback','出勤反馈')">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;出勤反馈&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                    <%--</bumu:hasPermission>--%>

                    <%--<bumu:hasPermission name="attendance:appeal:*">--%>
                    <%--&lt;%&ndash;<li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a class="J_menuItem" data-id="attendance_appeal"&ndash;%&gt;--%>
                    <%--&lt;%&ndash;onclick="getInsidePageDiv(urlGroup.attendance_appeal_index,&ndash;%&gt;--%>
                    <%--&lt;%&ndash;'attendance_appeal','出勤申诉')">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;出勤申诉&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
                    <%--</bumu:hasPermission>--%>

                    <bumu:hasPermission name="attendance:approval:*">
                        <li>
                            <a class="J_menuItem" data-id="approval_manage"
                               onclick="getInsidePageDiv(urlGroup.attendance.approval.index,
							   'approval_manage','审批管理')">
                                审批管理
                            </a>
                        </li>
                    </bumu:hasPermission>

                    <bumu:hasPermission name="attendance:setting:*">
                        <li>
                            <a class="J_menuItem" data-id="attendance_setting"
                               onclick="getInsidePageDiv(urlGroup.attendance.setting.index,
						   'attendance_setting','出勤配置')">
                                出勤配置
                            </a>
                        </li>
                    </bumu:hasPermission>

                </ul>
            </li>

            <li>
                <a href="">
                    <i class="fa icon-salary-manage"></i>
                    <span class="nav-label">薪资管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <bumu:hasPermission name="salary:*">

                        <li>
                            <a class="J_menuItem" data-id="salary_create"
                               onclick="getInsidePageDiv(urlGroup.salary.create.index,
								   'salary_create','导入薪资单')">
                                导入薪资单
                            </a>
                        </li>


                        <li>
                            <a class="J_menuItem" data-id="salary_history"
                               onclick="getInsidePageDiv(urlGroup.salary.history.index,'salary_history','历史薪资单')">
                                历史薪资单
                            </a>
                        </li>

                    </bumu:hasPermission>
                </ul>
            </li>

            <li>
                <a href="">
                    <i class="fa icon-contract-manage"></i>
                    <span class="nav-label">电子合同</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <bumu:hasPermission name="e_contract:*">
                        <li>
                            <a class="J_menuItem" data-id="contract_manage"
                               onclick="getInsidePageDiv(urlGroup.e_contract.manage.index,
							   'contract_manage','合同管理')">
                                合同管理
                            </a>
                        </li>
                    </bumu:hasPermission>


                </ul>
            </li>

            <li>
                <a href="">
                    <i class="fa icon-corp-manage"></i>
                    <span class="nav-label">企业管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <bumu:hasPermission name="corp:info:*">
                        <li>
                            <a class="J_menuItem" data-id="corporation_info"
                               onclick="getInsidePageDiv(urlGroup.corp.info.index,
                           'corporation_info', '企业信息')">企业信息</a>
                        </li>
                    </bumu:hasPermission>

                    <bumu:hasPermission name="corp:notice:*">
                        <li>
                            <a class="J_menuItem" data-id="corp_notice"
                               onclick="getInsidePageDiv(urlGroup.corp.notice.index,
                           'corp_notice', '企业公告')">企业公告</a>
                        </li>
                    </bumu:hasPermission>
                </ul>
            </li>

            <li>
                <a href="">
                    <i class="fa icon-setting-manage"></i>
                    <span class="nav-label">设置中心</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">

                    <bumu:hasPermission name="setting:op_log:*">
                        <li>
                            <a class="J_menuItem" data-id="op_log"
                               onclick="getInsidePageDiv(urlGroup.setting.log.index,
                           'op_log', '操作日志')">操作日志</a>
                        </li>
                    </bumu:hasPermission>

                </ul>
            </li>

            <li>
                <a href="">
                    <i class="fa icon-jurisdiction-manage"></i>
                    <span class="nav-label">权限管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <bumu:hasPermission name="user:account:*">
                        <li>
                            <a class="J_menuItem" data-id="user_account_manage"
                               onclick="getInsidePageDiv(
							   urlGroup.perm.account.index,
							   'user_account_manage','账号管理')">账号管理</a>
                        </li>
                    </bumu:hasPermission>
                </ul>
            </li>

            <li style="display: none;">
                <a href="#">
                    <i class="fa fa-home"></i>
                    <span class="nav-label">测试</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level">
                    <li>
                        <a class="J_menuItem"
                           onclick="getInsidePageDiv('admin/test','test','测试-标签名称')">测试网页</a>
                    </li>
                </ul>
            </li>

        </ul>
    </div>
</nav>
<!--左侧导航结束-->

