/**
 * Created by CuiMengxin on 2016/8/2.
 * 订单导入
 */

var $form = $(".order_import_container").find("form.upload_excel");

var order_import = {
	import_File_Name: "",//上传文件 名称(返回值)
	import_File_batch: "",//上传文件 批次
	import_File_batch_id: "",//上传文件 批次id
	import_File_msg: "",//上传文件 错误信息提示
	containerName: "",//


	//初始化
	Init: function () {
		order_import.containerName = ".order_import_container";

		order_import.FileInit();//上传文件 - 初始化
	},
	//上传文件 - 初始化
	FileInit: function () {
		order_import.import_File_Name = "";
		order_import.import_File_batch = "";
		order_import.import_File_msg = "";//上传文件 错误信息提示

		$(order_import.containerName).find(".file_path").html("");
		$(order_import.containerName).find(".btn_calc").addClass("btn-default").removeClass("btn-primary");
		$(order_import.containerName).find(".btn_sure").addClass("btn-default").removeClass("btn-primary");

		//$(order_import.containerName).find(".batch_no_container").hide();
		//$(order_import.containerName).find(".btn_export").hide();

	},
	//下载模板
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
		form.attr("action", urlGroup.order_import_template_down_url);
		form.hide();

		var npt = $("<input>");
		npt.attr("name", "template_type");
		npt.val(type);
		npt.appendTo(form);

		form.submit();

	},
	//信息提示
	msgPrompt: function () {
		var msg = "灰色-代表已经导入过的订单" + "  绿色-代表新增" + "  蓝色-代表用户自己输入的值" + "  红色-代表错误" + "\n";

		if (order_import.import_File_msg) {
			order_import.import_File_msg = order_import.import_File_msg
				.replace(/\/n/g, "\n");
			$("#order_bill_import_status_text").text(msg + order_import.import_File_msg);
		}
		else {
			$("#order_bill_import_status_text").text(msg);
		}

		$("#order_bill_import_status_modal").modal("show");
	},

	//选择文件 - 按钮点击
	ChooseFileClick: function () {

		//var $body = $("body");
		//
		//if ($body.find(".upload_excel")) {
		//	$body.find(".upload_excel").remove();
		//}
		//
		//var form = $("<form>");
		//form.addClass("upload_excel");
		//form.attr("enctype", "multipart/form-data");
		//form.appendTo($body);
		////form.attr("method", "get");
		//form.hide();
		//
		//var input = $("<input>");
		//input.attr("type", "file");
		//input.attr("name", "file");
		//input.attr("onchange", "order_import.ChooseFile(this)");
		//input.appendTo(form);
		//
		//input.click();

		$form.find("input").click();

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
					if (/\.(xlsx)$/.test(file.name)) {
						$(order_import.containerName).find(".file_path").html(file.name);
						//如果上传的是excel，“计算”按钮显示“蓝色”，可以被点击
						$(order_import.containerName).find(".btn_calc").addClass("btn-primary")
							.removeClass("btn-default");
						order_import.import_File_msg = "";//提示消息置空
					}
					else {
						messageCue("请上传2007版excel文档，以.xlsx结尾");
					}

				}

			}
		}
		else {
			order_import.Init();
		}
	},

	//导入的订单 - 计算
	ImportFileCalc: function () {

		//如果没有选择文件
		if ($(order_import.containerName).find(".btn_calc").hasClass("btn-default")) {
			messageCue("请选择文件！");
			return;
		}

		var $table_container = $(order_import.containerName).find(".table_container");

		loadingInit();//加载框 出现

		console.log($form.find("input").val());

		//上传xls到预览 返回Json
		$form.ajaxSubmit({
			url: urlGroup.order_calc_by_import_url,
			type: 'post',
			success: function (data) {
				//console.log(data);

				loadingRemove();//加载框 隐藏

				if (data.code == 1000) {

					order_import.import_File_batch_id = data.result.batch_id;//批次号
					order_import.import_File_batch = data.result.batch;//批次号
					order_import.import_File_Name = data.result.file_name;//文件名称
					order_import.import_File_msg = data.result.msg;//错误信息
					var total_row_count = data.result.total_rows_count;//返回的 总结果 条数
					var wrong_rows_count = data.result.wrong_rows_count;//返回的 错误结果 条数
					var template_type = data.result.template_type;//模板类型 personal 个人模板 company 公司模板

					//个人模板
					if (template_type == "personal") {
						$table_container.find("thead .postpone_month").show();
					}
					else {
						$table_container.find("thead .postpone_month").hide();
					}

					var itemList = "";
					var orders = data.result.orders;
					if (!orders || orders.length == 0) {
					}
					else {
						for (var i = 0; i < orders.length; i++) {
							var item = orders[i];

							var content = "";//

							//缴纳主体
							if (item.subject) {
								content += "<td class='order_subject' " +
									"data-status='" + item.subject.status + "'>" +
									"<span>" + item.subject.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_subject is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//姓名
							if (item.name) {
								content += "<td class='order_user_name' " +
									"data-status='" + item.name.status + "'>" +
									"<span>" + item.name.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_user_name is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//身份证
							if (item.idcard) {
								content += "<td class='order_user_idCard' " +
									"data-status='" + item.idcard.status + "'>" +
									"<span>" + item.idcard.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_user_idCard is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//参保地区
							if (item.soin_district) {
								content += "<td class='order_soin_district' " +
									"data-status='" + item.soin_district.status + "'>" +
									"<span>" + item.soin_district.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_soin_district is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//参保类型
							if (item.soin_type) {
								content += "<td class='order_soin_type' " +
									"data-status='" + item.soin_type.status + "'>" +
									"<span>" + item.soin_type.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_soin_type is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//服务月份
							if (item.service_month) {
								content += "<td class='order_service_month' " +
									"data-status='" + item.service_month.status + "'>" +
									"<span>" + item.service_month.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_service_month is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//缴纳月份
							if (item.pay_month) {
								content += "<td class='order_pay_month' " +
									"data-status='" + item.pay_month.status + "'>" +
									"<span>" + item.pay_month.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_pay_month is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//社保编号
							if (item.soin_code) {
								content += "<td class='order_soin_code' " +
									"data-status='" + item.soin_code.status + "'>" +
									"<span>" + item.soin_code.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_soin_code is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//社保基数
							if (item.soin_base) {
								content += "<td class='order_soin_base' " +
									"data-status='" + item.soin_base.status + "'>" +
									"<span>" + item.soin_base.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_soin_base is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//公积金编号
							if (item.housefund_code) {
								content += "<td class='order_housefund_code' " +
									"data-status='" + item.housefund_code.status + "'>" +
									"<span>" + item.housefund_code.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_housefund_code is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//公积金基数
							if (item.housefund_base) {
								content += "<td class='order_housefund_base' " +
									"data-status='" + item.housefund_base.status + "'>" +
									"<span>" + item.housefund_base.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_housefund_base is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//公积金比例
							if (item.housefund_percent) {
								content += "<td class='order_housefund_percent' " +
									"data-status='" + item.housefund_percent.status + "'>" +
									"<span>" + item.housefund_percent.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_housefund_percent is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//户口性质
							if (item.hukou_type) {
								content += "<td class='order_hukou_type' " +
									"data-status='" + item.hukou_type.status + "'>" +
									"<span>" + item.hukou_type.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_hukou_type is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//户籍地址
							if (item.hukou_district) {
								content += "<td class='order_hukou_district' " +
									"data-status='" + item.hukou_district.status + "'>" +
									"<span>" + item.hukou_district.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_hukou_district is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//服务费(收账)
							if (item.collection_service_fee) {
								content += "<td class='order_collection_service_fee' " +
									"data-status='" + item.collection_service_fee.status + "'>" +
									"<span>" + item.collection_service_fee.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_collection_service_fee is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//服务费(出账)
							if (item.charge_service_fee) {
								content += "<td class='order_charge_service_fee' " +
									"data-status='" + item.charge_service_fee.status + "'>" +
									"<span>" + item.charge_service_fee.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_charge_service_fee is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//总计(收账)
							if (item.collection_subtotal) {
								content += "<td class='order_collection_subtotal' " +
									"data-status='" + item.collection_subtotal.status + "'>" +
									"<span>" + item.collection_subtotal.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_collection_subtotal is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//总计(出账)
							if (item.charge_subtotal) {
								content += "<td class='order_charge_subtotal' " +
									"data-status='" + item.charge_subtotal.status + "'>" +
									"<span>" + item.charge_subtotal.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_charge_subtotal is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//业务员
							if (item.salesman) {
								content += "<td class='order_salesman' " +
									"data-status='" + item.salesman.status + "'>" +
									"<span>" + item.salesman.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_salesman is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//供应商
							if (item.supplier) {
								content += "<td class='order_supplier' " +
									"data-status='" + item.supplier.status + "'>" +
									"<span>" + item.supplier.content + "</span>" +
									"</td>";
							}
							else {
								content += "<td class='order_supplier is_error'>" +
									"<span>" + "该字段为空" + "</span>" +
									"</td>";
							}

							//如果是 个人模板
							if (template_type == "personal") {
								//顺延月
								if (item.postpone_month) {

									var postpone_month_status = item.postpone_month.status ? item.postpone_month.status : "0";
									var postpone_month_content = item.postpone_month.content ? item.postpone_month.content : "-";

									content += "<td class='order_postpone_month' " +
										"data-status='" + postpone_month_status + "'>" +
										"<span>" + postpone_month_content + "</span>" +
										"</td>";
								}
								else {
									content += "<td class='order_postpone_month is_error'>" +
										"<span>" + "该字段为空" + "</span>" +
										"</td>";
								}
							}

							var id = item.id;//临时订单id
							var status = item.status;//订单状态

							itemList += "<tr class='item order_import_item' " +
								"data-id='" + id + "' " +
								"data-status='" + status +
								"'>" +
								content +
								"</tr>";

						}
					}

					$(order_import.containerName).find(".table_container").show();
					$(order_import.containerName).find(".table_container").find("table")
						.attr({
							"data-total_row_count": total_row_count,
							"data-wrong_rows_count": wrong_rows_count
						});
					$(order_import.containerName).find("table tbody").html(itemList);

					order_import.ImportFilePreview();//预览 导入的文件
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
	//预览 导入的文件
	ImportFilePreview: function () {

		//如果 批次号存在 ，显示批次号
		if (order_import.import_File_batch) {

			$(order_import.containerName).find(".batch_no_container").show()
				.find(".batch_no").html(order_import.import_File_batch);
		}

		//if ($(order_import.containerName).find("table tbody").find("tr").length <= 0) {
		//    toastr.info("您选择的xls文件中无效！");
		//    return;
		//}

		//检查订单状态
		$(order_import.containerName).find("table tbody tr").each(function () {
			var status = $(this).attr("data-status");//订单状态 8为重复订单背景颜色置为灰色
			if (status == 8) {
				$(this).addClass("is_repeat");
			}

			$(this).find("td").each(function () {


				/* status
				 1新增标记为绿色，
				 2使用用户给出的数据标记为蓝色，
				 4为错误标记为红色
				 */

				var status = $(this).attr("data-status");

				if (status) {
					if (status == 1) {
						$(this).addClass("is_green");
					}
					if (status == 2) {
						$(this).addClass("is_blue");
					}
					if (status == 4) {
						$(this).addClass("is_error");
						var img = "<img src='img/icon_error_prompt.png'>";
						$(this).append(img);
					}
				}

			});
		});

		//如果有 错误，就弹框显示
		if (order_import.import_File_msg) {
			order_import.msgPrompt();//
		}

		//初始化 确认按钮
		if ($(order_import.containerName).find("table tbody").find(".is_error").length <= 0) {
			$(order_import.containerName).find(".btn_sure").addClass("btn-primary")
				.removeClass("btn-default");
		}
		else {
			$(order_import.containerName).find(".btn_sure")
				.addClass("btn-default").removeClass("btn-primary");
		}
	},

	//确认保存 导入的excel
	ChooseFileConfirm: function () {

		//如果 文件名称&文件批次号 为空
		if (!order_import.import_File_Name && !order_import.import_File_batch) {
			toastr.info("请先选择文件并计算！");
			return;
		}

		if ($(order_import.containerName).find("table").attr("data-total_row_count") == 0) {
			toastr.info("您选择的xls文件中无效！请重新选择文件");
			return;
		}

		if ($(order_import.containerName).find("table tbody").find(".is_error").length > 0) {
			toastr.error("上传文件中有错误，请修改后重新提交");
			return;
		}

		loadingInit();//加载框 出现

		var obj = new Object();
		obj.file_name = order_import.import_File_Name;
		obj.batch = order_import.import_File_batch;
		obj.batch_id = order_import.import_File_batch_id;

		$(order_import.containerName).find(".btn_export").hide();//隐藏 导出框

		aryaPostRequest(
			urlGroup.order_import_sure_url,
			obj,
			function (data) {
				//alert(JSON.stringify(data));

				if (data.code == RESPONSE_OK_CODE) {
					toastr.success("提交成功！");
					order_import.Init();

					order_import.import_File_msg = data.result.msg;//提示信息
					order_import.msgPrompt();
					//导出按钮显示
					$(order_import.containerName).find(".btn_export").show();
				}
				else {
					messageCue(data.msg);
				}

			},
			function (error) {
				messageCue(error);
			})
	},
	//导出 Excel
	exportFile: function () {

		exportWarning("该批次", function () {

			var $body = $("body");

			if ($body.find(".export_excel")) {
				$body.find(".export_excel").remove();
			}

			var form = $("<form>").addClass("export_excel");
			form.appendTo($body);
			form.attr("enctype", "multipart/form-data");
			form.attr("action", urlGroup.order_export_batch);
			form.attr("method", "get");
			form.hide();

			form.append($("<input>").attr("name", "batch_id").attr("value",
				order_import.import_File_batch_id));

			form.submit();

		})

	}
};

$(function () {
	order_import.Init();
});
