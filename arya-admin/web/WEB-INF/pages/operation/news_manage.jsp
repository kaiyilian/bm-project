<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/20
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>

<%--本页面--%>
<link href="<%=contextPath%>/css/operation/news_manage.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/operation/news_manage.js"></script>

<div class="news_manage_container col-xs-12">

    <!-- Nav tabs -->
    <ul class="nav nav-tabs nav_temp" role="tablist">

        <li role="presentation" class="">

            <a href="#careful_news" role="tab" data-toggle="tab" data-href="careful_news">
                手机兼职
            </a>

        </li>

        <li role="presentation" class="">

            <a href="#corp_news" role="tab" data-toggle="tab" data-href="corp_news">
                网络营销
            </a>

        </li>

        <li role="presentation" class="">

            <a href="#entertainment_news" role="tab" data-toggle="tab" data-href="entertainment_news">
                问卷调查
            </a>

        </li>

        <li role="presentation" class="">

            <a href="#sport_news" role="tab" data-toggle="tab" data-href="sport_news">
                其他兼职
            </a>

        </li>

    </ul>

    <!-- Tab panes -->
    <div class="tab-content">

        <div role="tabpanel" id="careful_news" class="tab-pane fade in ">

            <div class="row">
                <button type="button" class="btn btn-sm btn-primary btn_add_news_careful"
                        onclick="news_manage.addNews('tb_news_careful')">
                    新增
                </button>
            </div>

            <div class="table_container">
                <table id="tb_news_careful"></table>
            </div>

        </div>

        <div role="tabpanel" id="corp_news" class="tab-pane fade">

            <div class="row">
                <button type="button" class="btn btn-sm btn-primary" onclick="news_manage.addNews('tb_news_corp')">
                    新增
                </button>
            </div>

            <div class="table_container">
                <table id="tb_news_corp"></table>
            </div>

        </div>

        <div role="tabpanel" id="entertainment_news" class="tab-pane fade">

            <div class="row">
                <button type="button" class="btn btn-sm btn-primary"
                        onclick="news_manage.addNews('tb_news_entertainment')">
                    新增
                </button>
            </div>

            <div class="table_container">
                <table id="tb_news_entertainment"></table>
            </div>

        </div>

        <div role="tabpanel" id="sport_news" class="tab-pane fade">

            <div class="row">
                <button type="button" class="btn btn-sm btn-primary" onclick="news_manage.addNews('tb_news_sport')">
                    新增
                </button>
            </div>

            <div class="table_container">
                <table id="tb_news_sport"></table>
            </div>

        </div>

    </div>

</div>