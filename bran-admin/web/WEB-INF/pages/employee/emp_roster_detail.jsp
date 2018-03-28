<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/15
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--公用--%>
<script src="<%=contextPath%>/js/bran/employee/emp_detail.js"></script>
<%--本页面--%>
<script src="<%=contextPath%>/js/bran/employee/emp_roster_detail.js"></script>

<div class="emp_roster_detail_container detail_container">

	<div class="head">
        <span class="item">
            <span class="txt">资料完整度</span>
            <span class="total_complete_degree">
                <span class="completed_degree"></span>
            </span>
        </span>

        <span class="btn_list">
            <span class="btn btn-small btn-orange btn_down"
				  onclick="emp_roster_detail.enclosureDown()">下载附件</span>
        </span>

	</div>

	<div class="content">

		<div class="info_container">

			<div class="user_info">

				<div class="head border-bottom">

					<span class="user_name item"></span>

					<span class="item">
						<span>性别：</span>
						<span class="user_sex"></span>
					</span>

					<span class="item">
						<span>年龄：</span>
						<span class="user_age"></span>
					</span>

					<span class="item">
						<span>民族：</span>
						<span class="user_nation"></span>
					</span>

					<span class="item">
						<span>联系方式：</span>
						<span class="user_phone"></span>
					</span>

					<span class="item">
						<span>身份证号码：</span>
						<span class="user_idCard"></span>
					</span>

					<%--<div class="row">--%>
					<%--<span class="user_sex">男</span>--%>
					<%--<span class="user_age">32</span>--%>
					<%--<span class="user_birth">1992-09-21</span>--%>
					<%--<span class="user_marital_status">已婚</span>--%>
					<%--<span class="user_family_name">汉</span>--%>
					<%--<span class="user_idCard">32123131231231</span>--%>
					<%--</div>--%>
				</div>

				<div class="user_face_img">
					<img src="../../../image/1.jpg">
				</div>

				<div class="content">

					<span class="row col-xs-4">
						<span class="txt">紧急联系人：</span>
						<span class="user_urgent_contact"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">开户行信息：</span>
						<span class="user_bank_info"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">面试时间：</span>
						<span class="user_interview_time"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">紧急联系方式：</span>
						<span class="user_urgent_contact_phone"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">开户行卡号：</span>
						<span class="user_bank_card_no"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">招聘来源：</span>
						<span class="user_interview_source"></span>
					</span>

					<span class="row col-xs-8">
						<span class="txt">班车点：</span>
						<span class="user_bus_address"></span>
					</span>

					<span class="row col-xs-4">
						<span class="txt">员工性质：</span>
						<span class="user_nature"></span>
					</span>

					<span class="row col-xs-12">
						<span class="txt">户籍地：</span>
						<span class="user_domicile"></span>
					</span>

					<span class="row col-xs-12">
						<span class="txt">现居地：</span>
						<span class="user_address"></span>
					</span>

					<div class="custom_info"></div>


				</div>

			</div>

			<div class="work_experience">

				<div class="head border-bottom">工作经验</div>

				<div class="content">

					<table class="table table-striped table-bordered table-hover dataTable">
						<thead>
						<tr>
							<th>公司</th>
							<th>职位</th>
							<th>在职时间</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td colspan='3'>暂无数据</td>
						</tr>
						</tbody>
					</table>

				</div>

			</div>

			<div class="education_experience">

				<div class="head border-bottom">教育经历</div>

				<div class="content">

					<table class="table table-striped table-bordered table-hover dataTable">
						<thead>
						<tr>
							<th>学校</th>
							<th>专业</th>
							<th>学历</th>
							<th>教育时间</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td colspan='4'>暂无数据</td>
						</tr>
						</tbody>
					</table>

				</div>

			</div>

		</div>

		<div class="enclosure_container">
			<div class="head">附件区：</div>

			<div class="content">

				<%--<span class="item">--%>
				<%--<img src="image/1.jpg">--%>
				<%--</span>--%>

				<%--<span class="item">--%>
				<%--<img src="image/1.jpg">--%>
				<%--</span>--%>

				<%--<span class="item">--%>
				<%--<img src="image/1.jpg">--%>
				<%--</span>--%>

			</div>
		</div>

	</div>

</div>