/**
 * Created by CuiMengxin on 2017/3/9.
 */

var $criminal_record_batch_query_modal = $(".criminal_record_batch_query_modal");

//犯罪记录 批量查询
var criminal_record_batch_query = {

	//弹框显示
	ModalShow: function () {

		$criminal_record_batch_query_modal.modal({
			backdrop: false,
			keyboard: false
		});

		criminal_record_batch_query.clearFile();//清空文件

	},

	//导入模板 下载
	templateDown: function () {

		aryaGetRequest(
			urlGroup.criminal_record_template,
			function (data) {
				//console.log("获取日志：");
				//console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					if (data.result) {
						var url = data.result.url ? data.result.url : "";

						if (!url) {
							toastr.warning("无法下载，下载链接为空！");
							return;
						}

						var aLink = document.createElement('a');
						aLink.download = "";
						aLink.href = url;
						aLink.click();

					}

				}
				else {
					//console.log("获取日志-----error：");
					//console.log(data.msg);

					toastr.warning(data.msg);
				}
			},
			function (error) {
				toastr.error(error);
			}
		);
	},

	//选择文件 点击
	chooseFileClick: function () {
		$criminal_record_batch_query_modal.find(".upload_file input").click();
	},

	//选择文件
	chooseFile: function (self) {
		//console.info("订单导入文件：");
		//console.log(self.value + "\n" + typeof  self.value);
		//console.log(self.files);

		var $upload_container = $criminal_record_batch_query_modal.find(".upload_container");

		if (self.value) {

			if (self.files.length > 0) {

				var file = self.files[0];

				$upload_container.find(".file_path").html(file.name);

			}

		}
		else {
			criminal_record_batch_query.clearFile();//清空文件
		}

	},
	//清空文件
	clearFile: function () {
		var $upload_container = $criminal_record_batch_query_modal.find(".upload_container");

		$upload_container.find(".file_path").html("");
	},

	//确认文件 导入、导出下载
	criminalRecordBatchExport: function () {

		var $form = $criminal_record_batch_query_modal.find("form.upload_file");
		var val = $form.find("input").val();
		if (!val) {
			toastr.warning("请选择文件!");
			return
		}

		loadingInit();

		//console.log(val);
		//console.info($form.serialize());

		$form.ajaxSubmit({
			url: urlGroup.criminal_record_export,
			type: 'post',
			clearForm: true,  //成功提交后，清除所有表单元素的值
			resetForm: true, //成功提交后，重置所有表单元素的值
			//data: $form.serialize(),
			success: function (data) {

				loadingRemove();

				criminal_record_batch_query.clearFile();//清空文件

				if (data.code == 1000) {

					if (data.result) {
						var url = data.result.url ? data.result.url : "";

						if (!url) {
							toastr.warning("无法下载，下载链接为空！");
							return;
						}

						$criminal_record_batch_query_modal.modal("hide");

						var aLink = document.createElement('a');
						aLink.download = "";
						aLink.href = url;
						aLink.click();

					}

				}
				else {
					toastr.warning(data.msg);
				}

			},
			error: function (error) {
				//console.log("失败：");
				//console.log(data);
				loadingRemove();

				toastr.error(error);
			}
		});

	}


};

//犯罪记录 单个查询
var criminal_record_query_manage = {

	containerName: "",//

	init: function () {
		criminal_record_query_manage.containerName = ".criminal_record_query_container";

	},

	//犯罪记录查询
	criminalRecordQuery: function () {

		if (!criminal_record_query_manage.checkParamByQuery()) {
			return;
		}

		var $table_container = $(criminal_record_query_manage.containerName).find(".table_container");
		var $search_container = $(criminal_record_query_manage.containerName).find(".search_container");
		var user_name = $.trim($search_container.find(".user_name").val());
		var user_idCardNo = $.trim($search_container.find(".idCardNo").val());

		var obj = {
			name: user_name,
			idCardNo: user_idCardNo
		};

		var url = urlGroup.criminal_record_query_one + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				console.log("获取日志：");
				console.log(data);

				if (data.code == RESPONSE_OK_CODE) {

					var list = "<tr><td colspan='4'>暂无信息</td></tr>";

					if (data.result) {

						var $item = data.result;

						var id = $item.id ? $item.id : "";//
						var version = $item.version ? $item.version : "";//
						var name = $item.name ? $item.name : "";//
						var idCardNo = $item.idCardNo ? $item.idCardNo : "";//
						var criminalDetail = $item.criminalDetail ? $item.criminalDetail : "";//
						var queryStatus = $item.queryStatus ? "失败" : "成功";//0:成功 1:失败

						list = "<tr class='item user_item' " +
							"data-id='" + id + "' " +
							"data-version='" + version + "' " +
							">" +
							"<td class='user_name'>" + name + "</td>" +
							"<td class='user_idCardNo'>" + idCardNo + "</td>" +
							"<td class='user_criminal_record'>" + criminalDetail + "</td>" +
							"<td class='query_status'>" + queryStatus + "</td>" +
							"</tr>";

					}

					$table_container.find("tbody").html(list);

					$search_container.find(".user_name").val("");
					$search_container.find(".idCardNo").val("");

				}
				else {
					//console.log("获取日志-----error：");
					//console.log(data.msg);

					toastr.warning(data.msg);
				}
			},
			function (error) {
				toastr.error(error);
			}
		);

	},

	//检查输入参数
	checkParamByQuery: function () {
		var txt = "";
		var flag = false;

		var $search_container = $(criminal_record_query_manage.containerName).find(".search_container");
		var user_name = $.trim($search_container.find(".user_name").val());
		var user_idCardNo = $.trim($search_container.find(".idCardNo").val());

		if (!user_name) {
			txt = "请输入用户姓名！";
		}
		else if (!user_idCardNo) {
			txt = "请输入用户身份证！";
		}
		else if (!criminal_record_query_manage.check(user_idCardNo)) {
		}
		else {
			flag = true;
		}


		if (txt) {
			toastr.warning(txt);
		}

		return flag;

	},

	check: function (user_idCardNo) {
		var txt = "";
		var flag = false;
		var iSum = 0;
		var aCity = {
			11: "北京",
			12: "天津",
			13: "河北",
			14: "山西",
			15: "内蒙古",
			21: "辽宁",
			22: "吉林",
			23: "黑龙江",
			31: "上海",
			32: "江苏",
			33: "浙江",
			34: "安徽",
			35: "福建",
			36: "江西",
			37: "山东",
			41: "河南",
			42: "湖北",
			43: "湖南",
			44: "广东",
			45: "广西",
			46: "海南",
			50: "重庆",
			51: "四川",
			52: "贵州",
			53: "云南",
			54: "西藏",
			61: "陕西",
			62: "甘肃",
			63: "青海",
			64: "宁夏",
			65: "新疆",
			71: "台湾",
			81: "香港",
			82: "澳门",
			91: "国外"
		};

		if (!/^\d{17}(\d|x)$/i.test(user_idCardNo))
			txt = "你输入的身份证长度或格式错误";

		user_idCardNo = user_idCardNo.replace(/x$/i, "a");

		if (aCity[parseInt(user_idCardNo.substr(0, 2))] == null) {
			txt = "你的身份证地区错误";
		}

		var sBirthday = user_idCardNo.substr(6, 4) + "-" + Number(user_idCardNo.substr(10, 2)) +
			"-" + Number(user_idCardNo.substr(12, 2));
		var d = new Date(sBirthday.replace(/-/g, "/"));

		if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
			txt = "身份证上的出生日期错误";
		}

		for (var i = 17; i >= 0; i--) {
			iSum += (Math.pow(2, i) % 11) * parseInt(user_idCardNo.charAt(17 - i), 11);
		}

		if (iSum % 11 != 1) {
			txt = "你输入的身份证号错误";
		}

		if (txt) {
			toastr.warning(txt);
		}
		else {
			flag = true;
		}

		return flag

	}


};

$(document).ready(function () {
	criminal_record_query_manage.init();
});