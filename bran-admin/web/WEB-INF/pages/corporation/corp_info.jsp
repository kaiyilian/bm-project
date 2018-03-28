<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2016/12/19
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--本页面--%>
<link href="<%=contextPath%>/css/bran/corporation/corp_info.css" rel="stylesheet">
<script src="<%=contextPath%>/js/bran/corporation/corp_info.js"></script>

<div id="corp_info_container">

    <div class="container corp_info_container">

        <div class="head border-bottom">
            <div class="txt">企业信息</div>
        </div>

        <div class="content">

            <div class="company_img">
                <img src="image/attachment_empty.jpg">
            </div>

            <div class="company_info">

                <div class="row">

                    <div class="col-xs-4">
                        <div class="txt col-xs-4">公司：</div>
                        <div class="txtInfo col-xs-8 company_name">公司名称</div>
                    </div>

                    <div class="col-xs-4 ">
                        <div class="txt col-xs-4">性质：</div>
                        <div class="txtInfo col-xs-8 company_type">私营企业</div>
                    </div>

                    <div class="col-xs-4">
                        <div class="txt col-xs-6">企业入职码：</div>
                        <div class="txtInfo col-xs-6 company_checkin_code">324324</div>
                    </div>

                </div>

                <div class="col-xs-12 item">
                    <span class="txt">地址：</span>
                    <span class="txtInfo company_address"></span>
                </div>

                <div class="col-xs-12 item">
                    <span class="txt">热线：</span>
                    <span class="txtInfo company_phone"></span>
                </div>

                <div class="col-xs-12 item">
                    <span class="txt">传真：</span>
                    <span class="txtInfo company_fax"></span>
                </div>

                <div class="col-xs-12 item">
                    <span class="txt">邮箱：</span>
                    <span class="txtInfo company_email"></span>
                </div>

                <div class="company_qrCode">
                    <img src="">
                </div>

            </div>

        </div>

    </div>

    <div class="container" style="border: none;">

        <div class="corp_route_container">

            <div class="head border-bottom">
                <div class="txt">厂车路线</div>
            </div>

            <div class="content">

                <div class="upload_container">

                    <div class="file_name">
                        选择本地文件
                    </div>

                    <div class="btn_list">

                        <div class="btn btn-sm btn-success btn_upload"
                             onclick="corp_route.chooseFileClick();">
                            <i class="icon icon-folder"></i>
                            选择文件
                        </div>

                        <div class="btn btn-sm btn-default btn_preview"
                             onclick="corp_route.corpRoutePreview()">
                            浏览
                        </div>

                        <div class="btn btn-sm btn-default btn_sure"
                             onclick="corp_route.corpRouteSave()">
                            确定
                        </div>

                    </div>

                </div>

                <div class="prompt">
                    *仅支持word、pdf格式上传，为了预览效果更佳，建议使用pdf格式。
                </div>

            </div>

            <div class="foot border-top">

                <div class="btn btn-sm btn-orange btn_browse"
                     onclick="corp_route.corpRouteBrowse()">
                    浏览厂车路线
                    <i class="fa arrow"></i>
                </div>

            </div>

        </div>

        <div class="corp_handbook_container">

            <div class="head border-bottom">
                <div class="txt">员工手册</div>
            </div>

            <div class="content">
                <div class="upload_container">

                    <div class="file_name">
                        选择本地文件
                    </div>

                    <div class="btn_list">

                        <div class="btn btn-sm btn-success btn_upload"
                             onclick="corp_handbook.chooseFileClick();">
                            <i class="icon icon-folder"></i>
                            选择文件
                        </div>

                        <div class="btn btn-sm btn-default btn_preview"
                             onclick="corp_handbook.corpHandbookPreview()">
                            浏览
                        </div>

                        <div class="btn btn-sm btn-default btn_sure"
                             onclick="corp_handbook.corpHandbookSave()">
                            确定
                        </div>

                    </div>

                </div>

                <div class="prompt">
                    *仅支持word、pdf格式上传，为了预览效果更佳，建议使用pdf格式。
                </div>

            </div>

            <div class="foot border-top">

                <div class="btn btn-sm btn-orange btn-browse"
                     onclick="corp_handbook.corpHandbookBrowse()">
                    浏览员工手册
                    <i class="fa arrow"></i>
                </div>

            </div>

        </div>

    </div>


    <div class="container corp_news_container hide">

        <div class="head border-bottom">
            <div class="txt">消息中心</div>
            <div class="txtR" onclick="corp_news.getNewsMore()">
                <span>查看更多>></span>
            </div>
        </div>

        <div class="content">

            <div class="table_container">
                <table class="table table-striped table-bordered table-hover dataTable">
                    <thead>
                    <tr>
                        <td style="width:100px;">标题</td>
                        <td style="">内容</td>
                        <td style="width:100px;">发布部门</td>
                        <td style="width:100px;">发布人</td>
                    </tr>
                    </thead>
                    <tbody>
                    <%--<tr class="item">--%>
                    <%--<td class="news_title">标题</td>--%>
                    <%--<td class="news_content">内容</td>--%>
                    <%--<td class="news_dept">部门</td>--%>
                    <%--<td class="news_author">发布人</td>--%>
                    <%--</tr>--%>
                    </tbody>
                </table>
            </div>

        </div>

    </div>

</div>
