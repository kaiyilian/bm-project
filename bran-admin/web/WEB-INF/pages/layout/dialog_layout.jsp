<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/10/12
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--本页面--%>
<script src="<%=contextPath%>/js/bran/layout/dialog_layout.js"></script>

<div class="modal fade entry_info_modal" role="dialog" aria-labelledby="gridSystemModalLabel"
     style="background-color: rgba(0,0,0,0.50);">
    <div class="modal-dialog" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">同意入职</h4>
            </div>
            <div class="modal-body">

                <div class="default_info f_left">

                    <div class="row">
					<span class="col-xs-3 txt">
						<span class="icon">*</span>
						起始工号：
					</span>

                        <span class="col-xs-9 txtInfo employee_no_begin">

                        <select class="form-control col-xs-6"
                                onchange="entry_info.WorkSnChange()">
							<%--<option>1</option>--%>
							<%--<option>1</option>--%>
							<%--<option>1</option>--%>
						</select>

                        <span class="col-xs-1">+</span>

                        <input type="text" class="form-control col-xs-5"
                               onblur="entry_info.CheckWorkSnIsRight()">
                    </span>

                    </div>

                    <div class="row">
					<span class="col-xs-3 txt">
						<span class="icon">*</span>
						合同开始日期：
					</span>

                        <span class="col-xs-9">
                         <span class="txtInfo entry_info_begin_time form-control"
                               id="entry_info_begin_time" data-time=""></span>
                         <span class="laydate-icon inline demoicon icon_begin"></span>
                    </span>

                    </div>

                    <div class="row">
					<span class="col-xs-3 txt">
						<span class="icon">*</span>
						合同结束日期：
					</span>
                        <span class="col-xs-9">

						<span class="col-xs-12" style="padding:0;">
							<span class="txtInfo entry_info_end_time form-control"
                                  id="entry_info_end_time" data-time=""></span>
                        	<span class="laydate-icon inline demoicon icon_end"></span>
						</span>

						<span class="col-xs-12 entry_end_date_list" style="">

							<span class="col-xs-3 item" data-time="1">
								<img src="image/UnChoose.png">
								<span>一年</span>
							</span>

							<span class="col-xs-3 item" data-time="2">
								<img src="image/UnChoose.png">
								<span>二年</span>
							</span>

							<span class="col-xs-3 item" data-time="3">
								<img src="image/UnChoose.png">
								<span>三年</span>
							</span>

							<span class="col-xs-3 item" data-time="0" style="padding:0;">
								<img src="image/UnChoose.png">
								<span>无期限</span>
							</span>

						</span>

                    </span>
                    </div>

                    <div class="row">
					<span class="col-xs-3 txt">
						<%--<span class="icon">*</span>--%>
						面试日期：</span>
                        <span class="col-xs-9">
                        <span class="txtInfo entry_info_interview_time form-control"
                              id="entry_info_interview_time" data-time=""></span>
                        <span class="laydate-icon inline demoicon icon_interview"></span>
                    </span>
                    </div>

                    <div class="row">
					<span class="col-xs-3 txt">
						<span class="icon">*</span>
						试用期：
					</span>
                        <span class="col-xs-9 probation_expire_container">
                         <select class="form-control">
							 <%--<option value="0">0个月</option>--%>
							 <%--<option value="1">1个月</option>--%>
							 <%--<option value="2">2个月</option>--%>
							 <%--<option value="3">3个月</option>--%>
							 <%--<option value="4">4个月</option>--%>
							 <%--<option value="5">5个月</option>--%>
							 <%--<option value="6">6个月</option>--%>
						 </select>
                    </span>
                    </div>

                    <div class="row">
                        <span class="col-xs-3 txt">供应来源：</span>

                        <span class="col-xs-9">
                         <input class="form-control interview_source" placeholder="例：外部供应商、直招">
                    </span>

                    </div>

                    <div class="row">
                        <span class="col-xs-3 txt">员工性质：</span>

                        <span class="col-xs-9">
                         <input class="form-control employee_nature"
                                placeholder="例：长期员工、短期员工、派遣员工">
                    </span>

                    </div>

                </div>

                <div class="custom_info f_right clr_d87c11">

                </div>

                <div class="row">
					<span class="" style="float: right;line-height: 34px;">
						<span>注：</span>
						<span class="icon">*</span>
						<span>为必填项</span>
						<%--<span class="clr_d87c11">--%>
							<%--橙色为花名册自定义选项--%>
						<%--</span>--%>
					</span>
                </div>

                <div class="line">
                    <span>共分配</span>
                    <span class="employee_no_count"></span>
                    <span>个工号，</span>

                    <span class="entry_only" style="display: block;">
                        <span class="employee_no"></span>
                        <span class="employee_name"></span>
                    </span>

                    <span class="entry_more" style="display: block;">
                        <span>从</span>
                        <span class="employee_no start_work_sn">
                            <%--e34343--%>
                        </span>
                        <span class="start_people">
                            <%--（张三）--%>
                        </span>
                        <span style="margin:0 10px;">到</span>
                        <span class="employee_no end_work_sn">
                            <%--34322432--%>
                        </span>
                        <span class="end_people">
                            <%--（李四）--%>
                        </span>
                    </span>

                </div>

            </div>
            <div class="modal-footer">
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--<button type="button" class="btn btn-success btn_agree"--%>
                <%--onclick="employee_prospective_list_detail.EntryAgree()">--%>
                <%--确定--%>
                <%--</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<style>
    .f_left {
        float: left;
    }

    .f_right {
        float: right;
    }

    .entry_info_modal {
    }

    .entry_info_modal .modal-body {
        padding-bottom: 60px;
    }

    .entry_info_modal .modal-body .default_info {
        width: 100%;
    }

    .entry_info_modal .modal-body.custom_info_active .default_info {
        width: 55%;
    }

    .entry_info_modal .modal-body.custom_info_active .custom_info {
        width: 40%;
    }

    .entry_info_modal .modal-body .custom_info .row .txt {
        padding: 0;
    }

    .entry_info_modal .modal-body .row {
        position: relative;
        width: 100%;
        margin: 0 0 10px;
        clear: both;
    }

    .entry_info_modal .modal-body .row .icon {
        color: red;
    }

    .entry_info_modal .modal-body .row .txt > .icon {
        position: absolute;
        left: 0;
        top: 0;
    }

    .entry_info_modal .modal-body .row .txt {
        position: relative;
        /*width: 100px;*/
        /*text-align: center;*/
        line-height: 34px;
    }

    .entry_info_modal .modal-body .row .txtInfo {
        position: relative;
        height: 34px;
        line-height: 34px;
    }

    .entry_info_modal .modal-body .row .employee_no_begin select {
        width: 50%;
    }

    .entry_info_modal .modal-body .row .employee_no_begin span {
        width: 10%;
        text-align: center;
    }

    .entry_info_modal .modal-body .row .employee_no_begin input {
        width: 40%;
    }

    .entry_info_modal .modal-body .row .entry_info_begin_time,
    .entry_info_modal .modal-body .row .entry_info_interview_time,
    .entry_info_modal .modal-body .row .entry_info_end_time {
        border: 1px solid rgba(0, 0, 0, 0.10);
        border-radius: 3px;
        line-height: 32px;
        padding: 0 10px;
    }

    .entry_info_modal .modal-body .row .laydate-icon {
        position: absolute;
        right: 15px;
    }

    .entry_info_modal .modal-body .row .laydate-icon.icon_end {
        right: 0;
    }

    .entry_info_modal .modal-body .row .entry_end_date_list {
        padding: 0;
        line-height: 30px;
    }

    .entry_info_modal .modal-body .row .entry_end_date_list img {
        width: 14px;
        height: 14px;
        float: left;
        margin: 8px;
    }

    .entry_info_modal .modal-body .row .entry_end_date_list > span {
        cursor: pointer;
    }

    .entry_info_modal .modal-body .row .employee_no_begin.isBad {
        color: red;
    }

    .entry_info_modal .modal-body .line {
        position: relative;
        width: 100%;
        float: left;
        padding-left: 100px;
        line-height: 30px;
    }

    .entry_info_modal .modal-body .line .employee_no_count {
        width: 30px;
        padding: 0 5px;
        text-align: center;
        color: #2273b4;
    }

    .entry_info_modal .modal-body .line .employee_no {
        border-bottom: 1px solid rgba(0, 0, 0, 0.20);
        padding: 0 10px;
        height: 30px;
        color: #2273b4;
    }
</style>
