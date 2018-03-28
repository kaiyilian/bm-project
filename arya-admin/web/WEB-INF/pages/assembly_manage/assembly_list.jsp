<%--
  Created by IntelliJ IDEA.
  User: shaoshuai
  Date: 2018/3/22
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String contextPath = request.getContextPath().toString(); %>
<%--月份选择插件--%>
<link href="<%=contextPath%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<style>
    p{
        margin-top: 20px;
    }
</style>

<div class="container">
    <div class="head border-bottom">
        <div class="txt">组件列表</div>
    </div>

    <div class="content">
        <p>下拉框选择器</p>
        <div class="row col-xs-12 corp_list" style="padding:0;margin:5px 0;">
            <select name="corp" data-placeholder="请选择" multiple
                    id="corp" class="corp_select chosen-select">
            </select>
        </div>

        <div style="clear: both;"></div>

        <p>日期选择</p>
        <div class="input-group col-xs-3 item">
            <span class="input-group-addon">选择时间：</span>
            <input class="form-control layer-date beginTime" placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})">
        </div>

        <p>普通输入框</p>
        <span class="input-group col-xs-3 item">
            <span class="input-group-addon">关键字：</span>
            <input type="text" class="form-control searchCondition" placeholder="姓名/订单号/手机号"
                   maxlength="20">
            <span class="add-on"><i class="icon-remove"></i></span>
        </span>

        <p>月份选择</p>
        <div class="col-lg-3 col-xs-4 input-group item">
            <div class="input-group-addon">月份：</div>
            <div id="date_picker">
                <input id="calculate_month" name="year_month"
                       type="text" class="form-control year_month" readonly>
            </div>
        </div>

        <p>开关样式选择器</p>
        <div class="col-sm-10 txtInfo isEnable_add">
            <div class="switch">
                <div class="onoffswitch">
                    <input type="checkbox" name="fixednavbar"
                           class="onoffswitch-checkbox" id="fixednavbar">
                    <label class="onoffswitch-label" for="fixednavbar">
                        <span class="onoffswitch-inner"></span>
                        <span class="onoffswitch-switch"></span>
                    </label>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function selectInit() {
        var $corp_select = $(".corp_list").find(".corp_select");//公司列表 select
        $corp_select.removeAttr("disabled");
        $corp_select.empty();

        //检查是否 已初始化
        if ($corp_select.siblings(".chosen-container").length > 0) {
            $corp_select.chosen("destroy");
        }

        //模拟接口返回数据
        var arr = [
            {id: "8f161b2fdd5b4be09d35da84d1a904aa", name: "000000"},
            {id: "ceebde89a2a94f7f97deadfa8e474b9a", name: "1"},
            {id: "25ba3d86f0e5435b98f7c69ae0ceae1d", name: "Allen测试"},
            {id: "1387ad332a32489d910f231a2a7565c6", name: "jiaoxue01"},
            {id: "ea509ac0d2f740c8bf4c1534eca754fc", name: "MYH公司名称"}
        ];

        $.each(arr, function (i, $item) {

            var id = $item.id ? $item.id : "";
            var name = $item.name ? $item.name : "";

            var $option = $("<option>");
            $option.attr("value", id);
            $option.text(name);
            $option.appendTo($corp_select);

        });

        //公司标签 初始化
        $corp_select.chosen({
            allow_single_deselect: true,//是否可用取消
            width: "100%",
            no_results_text: "找不到"
        });
        $corp_select.chosen().on("change", function (evt, params) {
            console.log(evt);
            console.log(params);
        });
    }

    function initMonthSelect() {
        $("#date_picker #calculate_month")
            .datepicker({
                minViewMode: 1,
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                todayHighlight: true,
                format: 'yyyy-mm'
            })
            .on("changeMonth", function (e) {
                //更换月份事件
            });
    }

    selectInit();
    initMonthSelect();
</script>
