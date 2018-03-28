/**
 * Created by bumu-zhz on 2015/11/5.
 */
var salary_import = {
    CORPORATION_LIST_URL: 'admin/corporation/name_list',
    VERIFY_URL: 'admin/salary/import/verify',
    EXECUTE_URL: 'admin/salary/import/execute',
    corporationId: null,
    fileId: null,
    /**
     * Excel数据信息
     */
    columnsExcel: [{
        field: 'realName',
        title: '姓名'
    }, {
        field: 'cardId',
        title: '身份证'
    }, {
        field: 'phone',
        title: '手机号码'
    }, {
        field: 'month',
        title: '月份'
    }, {
        field: 'beanSalary',
        title: '基本工资'
    }, {
        field: 'workdayHours',
        title: '工作日出勤时间(小时)'
    }, {
        field: 'workdayOvertimeSalary',
        title: '平时加班工资'
    }, {
        field: 'overtimeHours',
        title: '平时加班工作时间(小时)'
    }, {
        field: 'restDayOvertimeSalary',
        title: '休息日加班工资'
    }, {
        field: 'restDayOvertimeHours',
        title: '休息日工作时间(小时)'
    }, {
        field: 'legalHolidayOvertimeSalary',
        title: '国假加班工资'
    }, {
        field: 'legalHolidayOvertimeHours',
        title: '国假加班时间(小时)'
    }, {
        field: 'performanceBonus',
        title: '绩效奖金'
    }, {
        field: 'subsidy',
        title: '津贴（餐补/车补/住房）'
    }, {
        field: 'casualLeaveCut',
        title: '事假扣款'
    }, {
        field: 'casualLeaveDays',
        title: '事假天数'
    }, {
        field: 'sickLeaveCut',
        title: '病假扣款'
    }, {
        field: 'sickLeaveDays',
        title: '病假天数'
    }, {
        field: 'otherCut',
        title: '其他扣款'
    }, {
        field: 'repayment',
        title: '补款'
    }, {
        field: 'soinPersonal',
        title: '社保（个人）'
    }, {
        field: 'fundPersonal',
        title: '公积金（个人）'
    }, {
        field: 'taxableSalary',
        title: '应税工资'
    }, {
        field: 'personalTax',
        title: '个税（个人）'
    }, {
        field: 'grossSalary',
        title: '应发工资'
    }, {
        field: 'netSalary',
        title: '实发工资'
    }],

    /**
     * 错误信息msg
     */
    columnsMsg: [{
        field: 'columnNo',
        title: '行号'
    }, {
        field: 'msg',
        title: '错误信息'
    }],

    import_but: function () {
        var company_name = $('#company_name').val();//公司名称
        var file = $('#fileSalary').val();//文件名称
        if (company_name == null || company_name == undefined || company_name == '') {
            swal("请选择公司");
            return;
        }

        if (file == null || file == undefined || file == '') {
            swal("请选择文件");
            return;
        }
        salary_import.corporationId = company_name;
        $.ajaxFileUpload({
            url: salary_import.VERIFY_URL,
            data: {
                "corporation_id": salary_import.corporationId
            },
            fileElementId: 'fileSalary',
            dataType: 'json',
            success: function (data) {
                salary_import.fileId = data.result["file_id"];
                salary_import.loadData(data);
            },
            error: function (data) {
                salary_import.request_error();
            }
        });
    },

    confirm_but: function () {
        var params = {"file_id":salary_import.fileId,"corporation_id":salary_import.corporationId}
        aryaPostRequest(salary_import.EXECUTE_URL,params, function (data) {
            salary_import.confirm_fun(data);
        }, function () {
            salary_import.request_error();
        });
    },

    /**
     * 加载导入数据
     * @param dataResult
     */
    loadData: function (data) {
        if (data) {
            if (data.code == 1000) {
                document.getElementById("confirm_but").disabled = false;
                $('#table_import_salary').bootstrapTable('destroy').bootstrapTable({
                    columns: salary_import.columnsExcel,
                    data: data.result["excel_datas"]
                });
            } else {
                document.getElementById("confirm_but").disabled = true;
                $('#table_import_salary').bootstrapTable('destroy').bootstrapTable({
                    columns: salary_import.columnsMsg,
                    data: data.result["err_msgs"]
                });
            }
        }
    },

    /**
     * 请求异常处理
     */
    request_error: function () {
        swal("请求异常，请稍后再试");
    },

    /**
     * 导入回调处理
     */
    confirm_fun: function (data) {

        if (data) {
            if (data.code == 1000) {
                document.getElementById("confirm_but").disabled = true;
                swal("薪资导入成功");
            } else {
                document.getElementById("confirm_but").disabled = false;
                if (data.result["err_msgs"] && data.result["err_msgs"].length > 0) {
                    $('#table_import_salary_confirm').bootstrapTable('destroy').bootstrapTable({
                        columns: [{
                            field: 'msg',
                            title: '错误信息'
                        }],
                        data: data.result["err_msgs"]
                    });
                    $("#add").modal("show");
                } else {
                    salary_import.request_error();
                }
            }
        }
    }
}

$(document).ready(function () {
    //默认禁用确认按钮
    document.getElementById("confirm_but").disabled = true;
    //加载所有公司
    aryaGetRequest(salary_import.CORPORATION_LIST_URL, function (data) {
        $('#company_name').empty();
        $('#company_name').append("<option value=''></option>");
        var result = data.result;
        console.log(result);
        if (result.corporations && result.corporations.length > 0) {
            for (var i = 0; i < result.corporations.length; i++) {
                $('#company_name').append("<option value=" + result.corporations[i].id + ">" + result.corporations[i].name + "</option>");
            }
        }
    }, function () {

    });
});




