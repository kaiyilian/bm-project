<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i>
    </div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">

            <li class="nav-header">
                <div class="row border-bottom">
                    <div class="dropdown profile-element col-sm-6">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<span class="clear">

                            <span>
                                <img alt="image" class="img-circle" src="img/user.png"/>
                            </span>

						<span class="block m-t-xs">
						   <strong class="font-bold">
							   <c:out value="${login_name}"/>
						   </strong></span>
						<span class="text-muted text-xs block">
								<c:out value="${role_name}"/></span>
						</span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <%--<li>--%>
                            <%--<a class="J_menuItem" onclick="showChangePwdModel()">修改密码</a>--%>
                            <%--</li>--%>
                            <%--<li class="divider"></li>--%>
                            <li>
                                <a onclick="logOut()">注销</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">招才进宝</div>

                    <div class="col-sm-6" style=" padding-left: 30px">
						<span class="navbar-minimalize btn btn-primary" href="#">
							<a class="fa fa-bars"></a>
						</span>
                    </div>

                </div>
            </li>

            <li data-id="project_apply" onclick="getInsidePageDiv(urlGroup.project_apply_page,
            'project_apply', '立项申请')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">立项申请</span>
                    <%--<span class="fa arrow"></span>--%>
                </a>
            </li>

            <li data-id="salary_calculate" onclick="getInsidePageDiv(urlGroup.salary_calc_page,
            'salary_calculate', '薪资计算导入')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">薪资计算导入</span>
                    <%--<span class="fa arrow"></span>--%>
                </a>
            </li>

            <li data-id="project_apply" onclick="getInsidePageDiv(urlGroup.customer_manage_page,
            'customer_manage', '客户管理')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">客户管理</span>
                    <%--<span class="fa arrow"></span>--%>
                </a>
            </li>

            <li data-id="salary_operate_record" onclick="getInsidePageDiv(urlGroup.salary_operate_record_page,
            'salary_operate_record', '薪资操作反馈记录')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">薪资操作反馈记录</span>
                    <%--<span class="fa arrow"></span>--%>
                </a>
            </li>

            <li data-id="customer_info" onclick="getInsidePageDiv(urlGroup.customer_info_page,
            'customer_info', '客户资料')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">客户资料</span>
                </a>
            </li>

            <li data-id="customer_info" onclick="getInsidePageDiv(urlGroup.bill_record_page,
            'bill_record', '开票记录')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">开票记录</span>
                </a>
            </li>

            <li data-id="customer_info" onclick="getInsidePageDiv(urlGroup.ledger_summary_page,
            'ledger_summary', '台账汇总')">
                <a href="#">
                    <i class="fa fa fa-money"></i>
                    <span class="nav-label">台账汇总</span>
                </a>
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