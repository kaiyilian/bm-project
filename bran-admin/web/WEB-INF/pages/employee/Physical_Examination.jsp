<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/8/3
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/employee/Physical_Examination.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/employee/Physical_Examination.js"></script>

<div class="" style="margin:0 10%;background-color: #fff;float: left;">
	<div class="physical_examination_report col-xs-12">

		<div class="title">体检报告单</div>

		<div class="block user_info_container col-xs-12">

			<div class="title">个人信息</div>

			<div class="content user_info_content col-xs-12">

				<div class="row">

					<div class="col-xs-6 txt">
						<img src="<%=contextPath%>/webPage/images/Physical_Examination/icon_user_name.png"/>
						姓名
					</div>

					<div class="col-xs-6 txtInfo user_name">

					</div>

				</div>

				<div class="row">

					<div class="col-xs-6 txt">
						<img src="<%=contextPath%>/webPage/images/Physical_Examination/icon_physical_examination_date.png"/>
						体检日期
					</div>

					<div class="col-xs-6 txtInfo physical_examination_date">
						<%--2019-04-12--%>
					</div>

				</div>

				<div class="row">

					<div class="col-xs-6 txt">
						<img src="<%=contextPath%>/webPage/images/Physical_Examination/icon_physical_examination_no.png"/>
						体检编号
					</div>

					<div class="col-xs-6 txtInfo physical_examination_no">
						<%--13433213232--%>
					</div>

				</div>

			</div>

		</div>

		<div class="block physical_examination_result_container col-xs-12">

			<div class="title">体检结果</div>

			<div class="content physical_examination_result_content col-xs-12">

				<div class="row">

					<div class="col-xs-6 txt">
						<img src="<%=contextPath%>/webPage/images/Physical_Examination/icon_physical_examination_result.png"/>
						体检结果
					</div>

					<div class="col-xs-6 txtInfo physical_examination_result">
						<%--正常--%>
					</div>

				</div>

				<div class="row">

					<div class="col-xs-6 txt">
						<img src="<%=contextPath%>/webPage/images/Physical_Examination/icon_physical_examination_result.png"/>
						体检建议
					</div>

					<div class="col-xs-6 txtInfo physical_examination_advice">
						<%--正常--%>
					</div>

				</div>

			</div>

		</div>

	</div>

	<div class="physical_examination_detail col-xs-12">

		<%--<div class="block eys_examination_container col-xs-12">--%>

			<%--<div class="title">眼科一般项目</div>--%>

			<%--<div class="head col-xs-12">--%>
				<%--<div class="col-xs-3">项目</div>--%>
				<%--<div class="col-xs-3">结果</div>--%>
				<%--<div class="col-xs-3">参考标准</div>--%>
				<%--<div class="col-xs-3">单位</div>--%>
			<%--</div>--%>

			<%--<div class="content col-xs-12">--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result is_bad">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

			<%--</div>--%>

		<%--</div>--%>

		<%--<div class="block eys_examination_container col-xs-12">--%>

			<%--<div class="title">眼科一般项目</div>--%>

			<%--<div class="head col-xs-12">--%>
				<%--<div class="col-xs-3">项目</div>--%>
				<%--<div class="col-xs-3">结果</div>--%>
				<%--<div class="col-xs-3">参考标准</div>--%>
				<%--<div class="col-xs-3">单位</div>--%>
			<%--</div>--%>

			<%--<div class="content col-xs-12">--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result is_bad">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

				<%--<div class="row eye_examination_item">--%>
					<%--<div class="col-xs-3 eye_examination_name">左侧听力</div>--%>
					<%--<div class="col-xs-3 eye_examination_result">正常</div>--%>
					<%--<div class="col-xs-3 eye_examination_standard">- -</div>--%>
					<%--<div class="col-xs-3 eye_examination_unit">- -</div>--%>
				<%--</div>--%>

			<%--</div>--%>

		<%--</div>--%>

	</div>
</div>