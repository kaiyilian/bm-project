<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2015/11/2
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--右侧边栏开始-->
<div id="right-sidebar">
  <div class="sidebar-container">
    <div class="tab-content">
    </div>
  </div>
</div>
<!--右侧边栏结束-->

<%--修改密码Modal窗口--%>
<div class="modal inmodal" id="change_pwd_modal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated fadeIn">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                class="sr-only">Close</span></button>
        <h4 class="modal-title">修改密码</h4>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="form-group">
            <label class="col-sm-3 control-label">密码：</label>
            <div class="col-sm-8">
              <input id="main_password" name="main_password" class="form-control" type="password">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">新密码：</label>
            <div class="col-sm-8">
              <input id="main_new_password" name="main_new_password" class="form-control" type="password">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">确认密码：</label>
            <div class="col-sm-8">
              <input id="main_confirm_password" name="main_confirm_password" class="form-control" type="password">
              <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-white" onclick="changePwd()">确定
        </button>
        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
