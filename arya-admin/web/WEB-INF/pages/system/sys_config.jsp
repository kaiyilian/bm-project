<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017/2/14
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<link href="<%=contextPath%>/css/sys/sys_config.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/sys/sys_config.js"></script>

<div class="sys_config_manage_container container">

    <div class="head border-bottom">
        <div class="txt">配置管理</div>
    </div>

    <div class="content">

        <div class="search_container">

            <div class="btn_list">

				<span class="btn btn-sm btn-primary btn_add"
                      onclick="sys_config_manage.sysConfigAddModalShow()">
                    新增
				</span>

            </div>

        </div>

        <div class="table_container">
            <table id="tb_config_manage"></table>
        </div>

    </div>

</div>

<div class="modal fade sys_config_modal" style="background-color:rgba(0,0,0,0.50);">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑配置</h4>
            </div>
            <div class="modal-body">

                <div class="row key_container">
                    <div class="col-xs-2 txt">key：</div>
                    <div class="col-xs-10 ">
                        <textarea class="form-control key" placeholder="请输入key"></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-2 txt">value：</div>
                    <div class="col-xs-10 ">
                        <textarea class="form-control value" placeholder="请输入value" rows="10"></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-2 txt">备注：</div>
                    <div class="col-xs-10 txtInfo">
                        <input type="text" class="form-control memo" placeholder="请输入备注"/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-2 txt">废弃：</div>

                    <div class="col-xs-10 txtInfo manage_cost">
                        <select class="form-control">
                            <option>yes</option>
                            <option>no</option>
                        </select>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="sys_config_manage.sysConfigSave()">
                    确定
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
