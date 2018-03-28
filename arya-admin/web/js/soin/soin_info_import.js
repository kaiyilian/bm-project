/**
 * Created by CuiMengxin on 2015/11/10.
 */
var soin_import = {
	CORPORATION_LIST_URL: 'admin/corporation/name_list',
	SOIN_IMPORT_EXECUTE_URL: 'admin/soin/import/execute',
	VERIFY_URL: 'admin/soin/import/verify',
	districtTreeSelectedNodeInSoinInfoImport: null,

	uploadedFileId: null,
	corporationId: null,
	distirctId: null,
	soinTypeId: null,

	//请求已开通社保的地区树
	getDistricTreeInSoinInfoImport: function (districtName) {
		if (null == soin_import.districtTreeSelectedNodeInSoinInfoImport)
			soin.getSoinDistricTree("#soin_info_import_district_tree", function (data) {
				soin.setSoinDistrictTree('#soin_info_import_district_tree', data, function (event, selectedNode) {//给地区树赋值和设置点击事件方法体
					soin_import.districtTreeSelectedNodeInSoinInfoImport = selectedNode;
					var districtId = selectedNode["href"];
					if (districtId != null) {
						soin_import.getSoinTypesInSoinInfoImport(districtId);
					}
				});
			});
	},

	//请求社保类型
	getSoinTypesInSoinInfoImport: function (districtId) {
		soin.getSoinTypes(districtId,function (data) {
			if (data.code == '1000') {
				soin_import.showSoinTypesInSoinInfoImport(data.result, districtId);
			}
			else {
				//请求社保类型异常，暂未处理
			}
		});
	},

	//显示社保类型按钮组
	showSoinTypesInSoinInfoImport: function (typeList, districtId) {
		if (typeList != null) {
			for (var i = 0; i < typeList.length; i++) {
				var id = typeList[i].id;
				var name = typeList[i].text;
				$('#div_soin_type_btns').append("<button type=\"button\" class=\"btn btn-w-m btn-primary\" id='" + id + "'  onclick=\"soin_import.chosedSoinTypeInSoinInfoImport('" + id + "','" + typeList[i].text + "')\" ' data-dismiss=\"modal\">   " + typeList[i].text + "</button>  ");
			}
			return;
		}
		$('#div_soin_type_btns').append("无社保类型");
	},

	//选中社保类型触发事件
	chosedSoinTypeInSoinInfoImport: function (typeId, typeName) {
		$('#p_distirct_soin_type').empty();
		$('#p_distirct_id').empty();
		$('#p_soin_type_id').empty();

		var districtNames = new Array();
		var nodeIndex = 0;
		var parentNodeInSoinInfoImport = $('#soin_info_import_district_tree').treeview('getParent', soin_import.districtTreeSelectedNodeInSoinInfoImport);
		districtNames[nodeIndex] = parentNodeInSoinInfoImport.text;
		while (parentNodeInSoinInfoImport.nodeId > 1) {
			nodeIndex++;
			parentNodeInSoinInfoImport = $('#soin_info_import_district_tree').treeview('getParent', parentNodeInSoinInfoImport);
			districtNames[nodeIndex] = parentNodeInSoinInfoImport.text;
		}

		for (var i = districtNames.length - 1; i >= 0; i--) {
			$('#p_distirct_soin_type').append(districtNames[i] + "-");
		}
		$('#p_distirct_soin_type').append(soin_import.districtTreeSelectedNodeInSoinInfoImport.text);
		$('#p_distirct_soin_type').append("  " + typeName);

		$('#p_distirct_id').append(soin_import.districtTreeSelectedNodeInSoinInfoImport.href);//城市id
		$('#p_soin_type_id').append(typeId);//社保类型id

		/* //改变导入按钮的disabled状态
		 soin_import.changeImportButDisabled();*/
	},


	/**
	 * 公司选择点击确认按钮
	 */
	addSoinCompany: function () {
		$("#add_soinimport_company_modal").modal("hide");
		var company_id = $('#soinimport_company').val();//公司id
		var company_name = $('#soinimport_company').find("option:selected").text();//公司名称

		$('#p_company_id').empty();//清空公司id
		$('#p_company_name').empty();//清空公司名称

		if (company_id != null && company_id != undefined && company_id != '') {
			$('#p_company_id').append(company_id);
		}

		if (company_name != null && company_name != undefined && company_name != '') {
			$('#p_company_name').append(company_name)
		}

		/*//改变导入按钮的disabled状态
		 soin_import.changeImportButDisabled();*/
	},

	/**
	 * 文件选择点击确认按钮
	 */
	addSoinFile: function () {
		$("#add_soinimport_file_modal").modal("hide");
		var fileName = $('#fileSoin').val();//文件名称id

		$('#p_upload_file_location').empty();//清空文件
		if (fileName != null && fileName != undefined && fileName != '') {
			$('#p_upload_file_location').append(fileName);
		}

		/*//改变导入按钮的disabled状态
		 soin_import.changeImportButDisabled();*/
	},

	import_but: function () {
		var companyId = $('#p_company_id').html();//公司名称
		var distirctId = $('#p_distirct_id').html();//城市区域
		var soinTypeId = $('#p_soin_type_id').html();//社保类型
		var file = $('#fileSoin').val();//文件名称
		if (companyId == null || companyId == undefined || companyId == '') {
			swal("请选择公司");
			return;
		}
		if (distirctId == null || distirctId == undefined || distirctId == '') {
			swal("请通过社保类型选择城市区域");
			return;
		}
		if (soinTypeId == null || soinTypeId == undefined || soinTypeId == '') {
			swal("请选择社保类型");
			return;
		}
		if (file == null || file == undefined || file == '') {
			swal("请选择文件");
			return;
		}

		soin_import.corporationId = companyId;
		soin_import.distirctId = distirctId;
		soin_import.soinTypeId = soinTypeId;
		$.ajaxFileUpload({
			url: soin_import.VERIFY_URL,
			data: {
				"corporation_id": soin_import.corporationId,
				"district_id": soin_import.distirctId,
				"soin_type_id": soin_import.soinTypeId
			},
			fileElementId: 'fileSoin',
			dataType: 'json',
			success: function (data) {
				soin_import.uploadedFileId = data.result["file_id"];
				soin_import.loadData(data);
			},
			error: function (data) {
				soin_import.request_error();
			}
		});
	},

	confirm_but: function () {
		var params = {
			"corporation_id": soin_import.corporationId,
			"district_id": soin_import.distirctId,
			"soin_type_id": soin_import.soinTypeId,
			"file_id": soin_import.uploadedFileId
		};
		aryaPostRequest(soin_import.SOIN_IMPORT_EXECUTE_URL, params, function (data) {
			soin_import.confirm_fun(data);
		}, function (data) {
			soin_import.request_error();
		});
	},

	/**
	 * 加载导入数据
	 * @param dataResult
	 */
	loadData: function (data) {
		if (data) {
			if (data.code == 1000) {
				document.getElementById("soinimport_confirm").disabled = false;
				$('#table_import_soin').bootstrapTable('destroy').bootstrapTable({
					columns: soin_import.columnsExcel,
					data: data.result.data
				});
			}
			else {
				document.getElementById("soinimport_confirm").disabled = true;
				$('#table_import_soin').bootstrapTable('destroy').bootstrapTable({
					columns: soin_import.columnsMsg,
					data: data.result["err_msgs"]
				});
			}
		}
	}
	,

	/**
	 * 请求异常处理
	 */
	request_error: function () {
		swal("请求异常，请稍后再试");
	}
	,

	/**
	 * 改变导入按钮的disabled状态
	 */
	changeImportButDisabled: function () {
		var companyId = $('#p_company_id').html();//公司名称
		var distirctId = $('#p_distirct_id').html();//城市区域
		var soinTypeId = $('#p_soin_type_id').html();//社保类型
		var file = $('#fileSoin').val();//文件名称
		if (companyId != null && companyId != undefined && companyId != ''
			&& distirctId != null && distirctId != undefined && distirctId != ''
			&& soinTypeId != null && soinTypeId != undefined && soinTypeId != '') {
			document.getElementById("soinimport_import").disabled = false;
		} else {
			document.getElementById("soinimport_import").disabled = true;
		}
	}
	,

	/**
	 * 导入回调处理
	 */
	confirm_fun: function (data) {
		if (data) {
			if (data.result["err_msgs"] == null) {
				document.getElementById("soinimport_confirm").disabled = true;
				swal("社保导入成功");
			} else {
				document.getElementById("soinimport_confirm").disabled = false;
				if (data.msg && data.msg.length > 0) {
					$('#table_import_soin_confirm').bootstrapTable('destroy').bootstrapTable({
						columns: [{
							field: 'msg',
							title: '错误信息'
						}],
						data: data.result["err_msgs"]
					});
					$("#addSoin").modal("show");
				} else {
					soin_import.request_error();
				}
			}
		}
	}
	,

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
		field: 'soinBase',
		title: '社保缴纳基数'
	}, {
		field: 'houseFundBase',
		title: '公积金缴纳基数'
	}, {
		field: 'personalPension',
		title: '养老（个人）'
	}, {
		field: 'personalUnemployment',
		title: '个人-失业'
	}, {
		field: 'personalMedical',
		title: '个人-医疗'
	}, {
		field: 'personalInjury',
		title: '个人-工伤'
	}, {
		field: 'personalPregnancy',
		title: '个人-生育'
	}, {
		field: 'personalHouseFund',
		title: '个人-住房'
	}, {
		field: 'companyPension',
		title: '企业-养老'
	}, {
		field: 'companyUnemployment',
		title: '企业-失业'
	}, {
		field: 'companyMedical',
		title: '企业-医疗'
	}, {
		field: 'companyInjury',
		title: '企业-工伤'
	}, {
		field: 'companyPregnancy',
		title: '企业-生育'
	}, {
		field: 'companyHouseFund',
		title: '企业-住房'
	}, {
		field: 'disability',
		title: '残保'
	}, {
		field: 'fees',
		title: '管理费'
	}, {
		field: 'totalPayment',
		title: '总金额'
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
	}]
};

$(document).ready(function () {
	//document.getElementById("soinimport_import").disabled = true;
	document.getElementById("soinimport_confirm").disabled = true;
	//加载所有公司
	aryaGetRequest(soin_import.CORPORATION_LIST_URL, function (data) {
		$('#soinimport_company').empty();
		$('#soinimport_company').append("<option value=''></option>");
		var result = data.result;
		if (result.corporations && result.corporations.length > 0) {
			for (var i = 0; i < result.corporations.length; i++) {
				$('#soinimport_company').append("<option value=" + result.corporations[i].id + ">" + result.corporations[i].name + "</option>");
			}
		}
	}, function () {

	})
});
