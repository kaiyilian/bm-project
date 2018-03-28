
var $wallet_pay_salary_preview_container = $(".wallet_pay_salary_preview_container");
var $tb_wallet_pay_salary_preview = $wallet_pay_salary_preview_container.find("#tb_wallet_pay_salary_apply");//表格id

var $form = $(".search_container").find("form.upload_excel");


var wallet_pay_salary_preview = {

    import_File_Name: "",//上传文件 名称(返回值)
    import_File_batch: "",//上传文件 批次
    import_File_batch_id: "",//上传文件 批次id
    import_File_msg: "",//上传文件 错误信息提示
    containerName: "",//

    //初始化
    init: function () {
        wallet_pay_salary_preview.btnSearchClick();//

    },

    btnSearchClick: function () {
        wallet_pay_salary_preview.initTb();//列表
    },



    //选择文件 - 按钮点击
    ChooseFileClick: function () {
        $form.find("input").click();
    },

    ImportFileCalc: function () {

        //如果没有选择文件
        if ($(wallet_pay_salary_preview.containerName).find(".btn_calc").hasClass("btn-default")) {
            messageCue("请选择文件！");
            return;
        }

        var $table_container = $(wallet_pay_salary_preview.containerName).find(".table_container");

        loadingInit();//加载框 出现

        console.log($form.find("input").val());

        //上传xls到预览 返回Json
        $form.ajaxSubmit({
            url: urlGroup.wallet_pay_salary_import_template,
            type: 'post',
            success: function (data) {
                 console.log(data);

                loadingRemove();//加载框 隐藏

                if (data.code == 1000) {

                    wallet_pay_salary_preview.ImportFilePreview();//预览 导入的文件
                }
                else {
                    messageCue(data.msg);
                }

            },
            error: function (error) {
                loadingRemove();//加载框 隐藏
                messageCue(error);
            }
        });

    },


    //选择文件
    ChooseFile: function (self) {
        console.info("订单导入文件：");
        console.log(self.value + "\n" + typeof  self.value);

        if (self.value) {
            if (self.files) {

                for (var i = 0; i < self.files.length; i++) {
                    var file = self.files[i];

                    //判断是否是xls格式
                    if (/\.(xls)$/.test(file.name)) {
                        $(wallet_pay_salary_preview.containerName).find(".file_path").html(file.name);
                        //如果上传的是excel，“计算”按钮显示“蓝色”，可以被点击
                        $(wallet_pay_salary_preview.containerName).find(".btn_calc").addClass("btn-primary")
                            .removeClass("btn-default");
                        wallet_pay_salary_preview.import_File_msg = "";//提示消息置空
                    }
                    else {
                        messageCue("请上传2007版excel文档，以.xls结尾");
                    }

                }

            }
        }
        else {
            wallet_pay_salary_preview.init();
        }
    },


    TemplateDown: function (type) {
        var $body = $("body");

        if ($body.find(".temp_down")) {
            $body.find(".temp_down").remove();
        }

        var form = $("<form>");
        form.addClass("temp_down");
        form.attr("enctype", "multipart/form-data");
        form.appendTo($body);
        form.attr("method", "get");
        form.attr("action", urlGroup.wallet_pay_salary_export_template);
        form.hide();

        var npt = $("<input>");
        npt.attr("name", "template_type");
        npt.val(type);
        npt.appendTo(form);

        form.submit();

    },



    //检查参数是否正确
    checkParam: function () {

        var flag = false;
        var txt;

        if (wallet_pay_salary_preview_param.begin_time && wallet_pay_salary_preview_param.end_time &&
            wallet_pay_salary_preview_param.begin_time > wallet_pay_salary_preview_param.end_time) {
            txt = "开始时间不能大于结束时间！";
        }
        else {
            flag = true;
        }

        if (txt) {
            toastr.warning(txt);
        }

        return flag;

    },
    //初始化 表格
    initTb: function () {
        aryaGetRequest(
            urlGroup.wallet_pay_salary_preview_corp,
            function (data) {
                if (data.code === RESPONSE_OK_CODE) {
                     console.log(data)
                }
                else {
                    toastr.warning(data.msg);
                }

            },
            function (error) {
            }
        );
    },



};
//保存时 参数
var wallet_pay_salary_preview_param = {

    begin_time: null,                    //开始时间
    end_time: null,                      //结束时间
    project_id: null,                    //手机号
    batch_no: null,                  //项目名称

    //参数赋值
    paramSet: function () {

        var $search_container = $wallet_pay_salary_preview_container.find(".search_container");

        wallet_pay_salary_preview_param.begin_time = $.trim($search_container.find(".beginTime").val());
        wallet_pay_salary_preview_param.begin_time = wallet_pay_salary_preview_param.begin_time ?
            timeInit3(wallet_pay_salary_apply_param.begin_time) : "";

        wallet_pay_salary_preview_param.end_time = $.trim($search_container.find(".endTime").val());
        wallet_pay_salary_preview_param.end_time = wallet_pay_salary_preview_param.end_time ?
            timeInit4(wallet_pay_salary_preview_param.end_time) : "";

        wallet_pay_salary_preview_param.project_id = $.trim($search_container.find(".projectId").val());

        wallet_pay_salary_preview_param.batch_no = $.trim($search_container.find(".batchNo").val());

    }

};

$(function () {
    wallet_pay_salary_preview.init();
});
