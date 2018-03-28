/**
 * Created by Administrator on 2017/7/23.
 */


var invoice_info = {

    invoice_type_map: null,//发票类型
    invoice_type_option: null,//发票类型

    invoice_project_map: null,//发票项目
    invoice_project_option: null,//发票类型

    //初始化 发票类型
    initInvoiceType: function ($select, succFun) {
        invoice_info.invoice_type_option = "";

        invoice_info.invoice_type_map = new Map();
        invoice_info.invoice_type_map.put("fullFare", "全额专票");
        invoice_info.invoice_type_map.put("balanceFare", "差额专票");
        invoice_info.invoice_type_map.put("fullSheet", "全额普票");
        invoice_info.invoice_type_map.put("balanceSheet", "差额普票");

        for (var i = 0; i < invoice_info.invoice_type_map.keySet().length; i++) {

            var key = invoice_info.invoice_type_map.keySet()[i];
            var value = invoice_info.invoice_type_map.get(key);


            invoice_info.invoice_type_option += "<option value='" + key + "'>" + value + "</option>";

        }

        $select.html(invoice_info.invoice_type_option);

        if (succFun) {
            succFun();
        }

    },
    //初始化 发票项目
    initInvoiceProject: function ($select, succFun) {
        invoice_info.invoice_project_option = "";

        invoice_info.invoice_project_map = new Map();
        invoice_info.invoice_project_map.put("salary", "工资");
        invoice_info.invoice_project_map.put("work", "劳务费");
        invoice_info.invoice_project_map.put("manager", "管理费");
        invoice_info.invoice_project_map.put("service", "服务费");
        invoice_info.invoice_project_map.put("personTax", "个税");
        invoice_info.invoice_project_map.put("other", "其他");
        invoice_info.invoice_project_map.put("isNull", "无");

        for (var i = 0; i < invoice_info.invoice_project_map.keySet().length; i++) {

            var key = invoice_info.invoice_project_map.keySet()[i];
            var value = invoice_info.invoice_project_map.get(key);

            invoice_info.invoice_project_option += "<option value='" + key + "'>" + value + "</option>";

        }

        $select.html(invoice_info.invoice_project_option);

        if (succFun) {
            succFun();
        }

    }

};
