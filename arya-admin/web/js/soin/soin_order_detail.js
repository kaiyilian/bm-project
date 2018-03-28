/**
 * Created by CuiMengxin on 2016/2/3.
 */
var soinOrderDetail = {

    ORDER_DETAIL_DICTIONARY: {
        "injury": "工伤",
        "medical": "医疗",
        "pregnancy": "生育",
        "pension": "养老",
        "unemployment": "失业",
        "disability": "残疾人保障金",
        "severe_illness": "大病医疗",
        "injury_addition": "工伤补充",
        "house_fund": "公积金",
        "house_fund_addition": "补充公积金",
        "fees": "管理费",
        "month_payment": "总金额",
    },

    /**
     * 格式化缴纳进度
     * @param data
     * @returns {*|jQuery|HTMLElement}
     */
    formatPaymentStep: function (data, orderId, orderNo, orderStatusCode, version,
                                 isNeedOperation) {
        var div = $("<div></div>");
        div.addClass("container-fluid");
        var paymentRow = $("<div></div>").appendTo(div);
        paymentRow.addClass("row-fluid");
        var paymentColRow = $("<div></div>").appendTo(paymentRow);
        paymentColRow.addClass("span12");
        paymentColRow.attr("style", "width:100%;height:80px;padding-top:20px");
        //显示缴纳情况
        $.each(data, function (index, value) {
            var label = $("<label></label>").appendTo(paymentColRow);
            if (value["is_payed"] == 1) {
                label.text(value["paymonth"]);//已缴纳
                label.append("</br>已缴纳");
            }
            if (isNeedOperation != null) {
                if (value["is_payed"] == 0 && value["is_current_paymonth"] == 0) {
                    label.text(value["paymonth"]);//未缴纳
                    label.append("</br>未缴纳");
                }
                if (value["is_payed"] == 0 && value["is_current_paymonth"] == 1) {
                    label.text(value["paymonth"]);
                    label.append(soin_order_status_manager.getUnderWayButton(value["soin_id"], orderId, orderNo, orderStatusCode, value["paymonth"], version));//需要缴纳
                }
            }
            else {
                if (value["is_payed"] == 0) {
                    label.text(value["paymonth"]);//未缴纳
                    label.append("</br>未缴纳");
                }
            }
            label.attr("style", "width:80px");
        });
        return div;
    },

    /**
     * 格式化订单详情
     * @param data
     * @returns {*|jQuery|HTMLElement}
     */
    formatOrderDetail: function (data) {
        var rowDiv = $("<div></div>");
        rowDiv.addClass("row");
        //用户信息表
        {
            var subDiv = $("<div></div>").appendTo(rowDiv);
            subDiv.addClass("col-md-3");
            subDiv.attr("id", "detail_" + data.result["order_id"]);
            var table = $("<table></table>").appendTo(subDiv);
            table.attr("style", "width: 100%;border:0");
            var th = $("<tr></tr>").appendTo(table);
            var thTd = $("<td></td>").appendTo(th);
            thTd.addClass("text-center");
            thTd.attr("colspan", "2");
            var strong = $("<strong></strong>").appendTo(thTd);
            strong.text("用户信息");
            table.append(soinOrderDetail.formatRow(data.result["creator_phone_no"], '创建者账号'));
            table.append(soinOrderDetail.formatRow(data.result["person_name"], '参保人姓名'));
            table.append(soinOrderDetail.formatRow(data.result["person_phone_no"], '参保人手机号'));
            table.append(soinOrderDetail.formatRow(data.result["person_idcard_no"], '参保人身份证号'));
            table.append(soinOrderDetail.formatRow(data.result["person_hukou"], '参保人户口'));
            table.append(soinOrderDetail.formatRow(data.result["person_hukou_type"], '参保人户口性质'));
            table.append(soinOrderDetail.formatMoneyRow(data.result["other_payment"], '其他费用'));
            table.append(soinOrderDetail.formatMoneyRow(data.result["actual_payment"], '实付款'));
            table.append(soinOrderDetail.formatMoneyRow(data.result["refund"], '已退款'));
            table.append(soinOrderDetail.formatRow(data.result["salesman"], '业务员'));
            table.append(soinOrderDetail.formatRow(data.result["supplier"], '供应商'));
            table.append(soinOrderDetail.formatRow(data.result["service_year_month"], '服务年月'));
        }

        //订单基数，比例，金额详情表
        {
            var subDiv = $("<div></div>").appendTo(rowDiv);
            subDiv.addClass("col-md-9");
            var table = $("<table></table>").appendTo(subDiv);
            table.attr("style", "border:0");
            //表头
            {
                var th = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(th);
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        var thTd = $("<td></td>").appendTo(th);
                        thTd.addClass("text-center");
                        var strong = $("<strong></strong>").appendTo(thTd);
                        strong.text(value);
                    }
                });
            }
            //每月明细
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("每月明细");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        tr.append(soinOrderDetail.formatMoneyTd(data.result[key]));
                    }
                });
            }

            //小计
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("小计");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        tr.append(soinOrderDetail.formatMoneyTd(data.result[key + "_total"]));
                    }
                });
            }
            //基数
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("基数");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        tr.append(soinOrderDetail.formatMoneyTd(data.result[key + "_base"]));
                    }
                });
            }

            //个人比例
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("个人比例");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        var personPercentage = data.result[key + "_percentage"];
                        if (personPercentage != null)
                            tr.append(soinOrderDetail.formatPercentageTd(personPercentage["percentage_person"], personPercentage["extra_person"]));
                    }
                });
            }

            //公司比例
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("公司比例");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        var corpPercentage = data.result[key + "_percentage"];
                        if (corpPercentage != null)
                            tr.append(soinOrderDetail.formatPercentageTd(corpPercentage["percentage_corp"], corpPercentage["extra_corp"]));
                    }
                });
            }

            //比例之和
            {
                var tr = $("<tr></tr>").appendTo(table);
                var tdHead = $("<td></td>").appendTo(tr);
                var strong = $("<strong></strong>").appendTo(tdHead);
                strong.text("比例之和");
                $.each(soinOrderDetail.ORDER_DETAIL_DICTIONARY, function (key, value) {
                    if (data.result[key] != null) {
                        var totalPercentage = data.result[key + "_percentage"];
                        if (totalPercentage != null)
                            tr.append(soinOrderDetail.formatPercentageTd(totalPercentage["percentage_total"], totalPercentage["extra_total"]));
                    }
                });
            }
        }
        return rowDiv;
    },

    /**
     * 格式化普通行
     * @param rowData
     * @param rowName
     * @returns {*}
     */
    formatRow: function (rowData, rowName) {
        if (rowData != null) {
            var tr = $("<tr></tr>");
            tr.addClass("detail");
            var nameTd = $("<td></td>").appendTo(tr);
            nameTd.attr("style", "text-align:right;");
            nameTd.text(rowName);
            var dataTd = $("<td></td>").appendTo(tr);
            dataTd.attr("style", "text-align:right;");
            dataTd.text(rowData);
            return tr;
        }
        else
            return null;
    },

    /**
     * 格式化金额行
     * @param rowData
     * @param rowName
     * @returns {*}
     */
    formatMoneyRow: function (rowData, rowName) {
        if (rowData != null) {
            var tr = $("<tr></tr>");
            tr.addClass("detail");
            var nameTd = $("<td></td>").appendTo(tr);
            nameTd.attr("style", "text-align:right;");
            nameTd.text(rowName);
            var dataTd = $("<td></td>").appendTo(tr);
            dataTd.attr("style", "text-align:right;");
            dataTd.text(rowData + "元");
            return tr;
        }
        else
            return null;
    },

    /**
     * 格式化金额列
     * @param rowData
     * @returns {*}
     */
    formatMoneyTd: function (data) {
        var td = $("<td></td>");
        td.addClass("text-right");
        if (data != null) {
            td.text(data + "元");
            return td;
        }
        else
            return td;
    },

    /**
     * 格式化比例列
     * @param rowData
     * @param rowName
     * @returns {*}
     */
    formatPercentageTd: function (rowPercentage, rowExtra) {
        var td = $("<td></td>");
        td.addClass("text-center");
        if (rowPercentage != null) {
            td.text(rowPercentage + "% ") + (rowExtra == null ? "" : ("+" + rowExtra + "元"));
            return (td);
        }
        else
            return td;
    }
};
