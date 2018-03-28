<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/9/6
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/fk_notice_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/fk_notice_manage.js"></script>

<div class="fk_notice_manage_container container">

	<div class="head border-bottom">
		<div class="txt">福库公告管理</div>
	</div>

	<div class="content">

		<div class="col-xs-12 margin_t_10 margin_b_10 time_setting">

			<span class="col-xs-2 f14 f-bold line-height-34">活动时间设置：</span>

			<span class="col-xs-10">

				<span class="input-group col-xs-6 pull-left item">
					<span class="input-group-addon">开始时间：</span>
					<input class="form-control layer-date beginTime" placeholder="YYYY-MM-DD hh:mm:ss"
						   onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</span>

				<span class="input-group col-xs-6 pull-left item">
					<span class="input-group-addon">结束时间：</span>
					<input class="form-control layer-date endTime" placeholder="YYYY-MM-DD hh:mm:ss"
						   onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
				</span>

			</span>

		</div>

		<div class="col-xs-12 margin_t_10 margin_b_10">

			<span class="col-xs-2 f14 f-bold line-height-34">活动时间公告：</span>

			<span class="col-xs-10">

				<textarea class="form-control activity_notice"></textarea>

			</span>

		</div>

		<div class="col-xs-12 margin_t_10 margin_b_10">

			<span class="col-xs-2 f14 f-bold line-height-34">活动预告公告：</span>

			<span class="col-xs-10">

				<textarea class="form-control activity_forenotice"></textarea>

			</span>

		</div>

		<div class="col-xs-12 margin_t_10 ">
			<span class="col-xs-2"></span>
			<span class="col-xs-10">
				<div class="btn btn-primary" onclick="fk_notice_manage.noticeModify();">发布</div>
			</span>
		</div>


	</div>

</div>