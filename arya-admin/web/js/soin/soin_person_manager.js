/**
 * Created by CuiMengxin on 2015/11/16.
 */

var soinPersonManager = {
	TB_ID: "soin_person_list",
	DLG_ID: "dlg_soin_person",
	FORM_ID: "soin_person_detail_form",
	INPUT_ID_PRE: "#soin_person_",
	//参保人返字段定义
	CREATE_TIME: "create_time",
	PERSON_NAME: "name",
	ID_CARD_NO: "idcard_no",
	ID_CARD_FRONT_URL: "id_card_front_url",
	ID_CARD_BACK_URL: "id_card_back_url",
	VERIFY_STATUS: "verify_status",
	HUKOU_NAME: "hukou_name",
	HUKOU_TYPE_NAME: "hukou_type_name",

	//元素ID定义
	CREATE_TIME_ELEMENT_ID: "#soin_person_create_time",
	PERSON_NAME_ELEMENT_ID: "#soin_person_name",
	ID_CARD_NO_ELEMENT_ID: "#soin_person_idcard_no",
	ID_CARD_FRONT_URL_ELEMENT_ID: "#soin_person_id_card_front_url",
	ID_CARD_FRONT_BIG_URL_ELEMENT_ID: "#soin_person_id_card_front_url_big",
	ID_CARD_BACK_URL_ELEMENT_ID: "#soin_person_id_card_back_url",
	ID_CARD_BACK_BIG_URL_ELEMENT_ID: "#soin_person_id_card_back_url_big",
	VERIFY_STATUS_ELEMENT_ID: "#soin_person_verify_status",


	// URL定义
	//LIST_URL: 'admin/soin/person/list',
	//DETAIL_URL: 'admin/soin/person?person_id=',
	//DETAIL_UPDATE_URL: 'admin/soin/person/update',

	// 全局DOM对象
	table: null, // JQuery Datatables
	dataTable: null,
	detail_cache: null,

	//参保人状态映射
	PERSON_STATUS_DICTIONARY: {
		1: "待审核",
		2: "审核通过",
		3: "身份证未上传",
		4: "审核未通过"
	},

	//参保人状态颜色
	PERSON_STATUS_STYLE_DICTIONARY: {
		1: "label label-success",//浅蓝色
		2: "label label-info",//浅绿色
		3: "label label-warning",//橘黄色
		4: "label label-danger",//红色
	},

	getSoinPersonDetail: function () {
		soinPersonManager.clearSoinPersonDetail();
		var row = soinPersonManager.table.row('.selected');
		if (row.data()) {
			getSelectedRowData(soinPersonManager.table, "", function (rowData) {
				if (rowData) {

					soinPersonManager.dialog.modal('show');

					var obj = {
						person_id: rowData.insured_person_id
					};

					var url = urlGroup.soin_person_detail + "?" + jsonParseParam(obj);

					aryaGetRequest(
						url,
						function (data) {
							//console.log(data);

							if (data.code == 1000)
								soinPersonManager.setSoinPersonDetail(data.result);
							else {
								toastr.warning(data.msg);
							}
						},
						function () {
						}
					);

				}
			});
		}
	},

	setSoinPersonDetail: function (detail) {
		soinPersonManager.detail_cache = detail;
		$.each(detail, function (name, value) {
			$(soinPersonManager.INPUT_ID_PRE + name).val(value);
		});
		$(soinPersonManager.CREATE_TIME_ELEMENT_ID).text(formatUnixTime(detail["create_time"]));
		$(soinPersonManager.ID_CARD_FRONT_URL_ELEMENT_ID).attr("src", detail[soinPersonManager.ID_CARD_FRONT_URL]);
		$(soinPersonManager.ID_CARD_FRONT_BIG_URL_ELEMENT_ID).attr("href", detail[soinPersonManager.ID_CARD_FRONT_URL]);
		$(soinPersonManager.ID_CARD_FRONT_BIG_URL_ELEMENT_ID).attr("title", detail[soinPersonManager.PERSON_NAME] + "  " + detail[soinPersonManager.ID_CARD_NO] + "  " + detail[soinPersonManager.HUKOU_NAME] + "  " + detail[soinPersonManager.HUKOU_TYPE_NAME]);
		$(soinPersonManager.ID_CARD_BACK_URL_ELEMENT_ID).attr("src", detail[soinPersonManager.ID_CARD_BACK_URL]);
		$(soinPersonManager.ID_CARD_BACK_BIG_URL_ELEMENT_ID).attr("href", detail[soinPersonManager.ID_CARD_BACK_URL]);
		$(soinPersonManager.ID_CARD_BACK_BIG_URL_ELEMENT_ID).attr("title", $(soinPersonManager.ID_CARD_FRONT_BIG_URL_ELEMENT_ID).attr("title"));
		$(soinPersonManager.VERIFY_STATUS_ELEMENT_ID).val(detail[soinPersonManager.VERIFY_STATUS]);
	},

	clearSoinPersonDetail: function () {
		$('#' + soinPersonManager.FORM_ID + ' :input').val("");
		$(soinPersonManager.CREATE_TIME_ELEMENT_ID).text("");
		$(soinPersonManager.ID_CARD_FRONT_URL_ELEMENT_ID).removeAttr("src");
		$(soinPersonManager.ID_CARD_FRONT_BIG_URL_ELEMENT_ID).removeAttr("href");
		$(soinPersonManager.ID_CARD_FRONT_BIG_URL_ELEMENT_ID).removeAttr("title");
		$(soinPersonManager.ID_CARD_BACK_URL_ELEMENT_ID).removeAttr("src");
		$(soinPersonManager.ID_CARD_BACK_BIG_URL_ELEMENT_ID).removeAttr("href");
		$(soinPersonManager.ID_CARD_BACK_BIG_URL_ELEMENT_ID).removeAttr("title");
		$(soinPersonManager.VERIFY_STATUS_ELEMENT_ID).val("");
	},

	updateSoinPersonDetail: function () {
		if ($(soinPersonManager.INPUT_ID_PRE + "verify_status").val() != soinPersonManager.detail_cache["verify_status"]) {

			var params = {
				"insured_person_id": soinPersonManager.detail_cache["insured_person_id"],
				"verify_status": $(soinPersonManager.INPUT_ID_PRE + "verify_status").val()
			};

			aryaPostRequest(
				urlGroup.soin_person_modify,
				params,
				function (data) {
					//soinPersonManager.dataTable.fnDraw();
					refreshDataTable(soinPersonManager);
					soinPersonManager.dialog.modal('hide');
				},
				function () {
				}
			);
		}
	},
};

$(document).ready(function () {
	soinPersonManager.dialog = $('#' + soinPersonManager.DLG_ID);

	soinPersonManager.dataTable = $('#' + soinPersonManager.TB_ID).dataTable({
		'processing': true,
		'serverSide': true,
		'showRowNumber': true,
		"bPaginate": true,
		'bStateSave': true,
		'searching': false,
		'ordering': false,
		"language": {
			"url": DATATABLES_CHINESE_LANGUAGE
		},
		'ajax': {
			'url': urlGroup.soin_person_list,
			type: "GET",
			data: function (data) {
				//console.log(data);

				//参保人状态参数
				var status = $("#soin_person_manager_status_chosen").val();
				if (status != null) {
					var joinResult = status.join(":");
					data.person_status_codes = joinResult;
				}
			}
		},
		"aoColumns": [
			{"sTitle": "ID", "mData": 'insured_person_id', "bVisible": false, "aTargets": [0]},
			{"sTitle": "姓名", "mData": 'name'},
			{"sTitle": "手机号", "mData": 'phone_no'},
			{"sTitle": "身份证号", "mData": 'idcard_no'},
			{"sTitle": "户口所在地区", "mData": 'hukou'},
			{"sTitle": "户口类型", "mData": 'hukou_type_name'},
			{
				"sTitle": "状态", "mData": "verify_status", "mRender": function (data) {
				return "<span class='" + soinPersonManager.PERSON_STATUS_STYLE_DICTIONARY[data] + "'>" +
					soinPersonManager.PERSON_STATUS_DICTIONARY[data] +
					"</span>";
			}
			},
			{"sTitle": "订单数", "mData": 'order_count'},
			{
				"sTitle": "创建时间", "mData": "create_time", "mRender": function (data) {
				return formatUnixTime(data);
			}
			}
		]
	});
	soinPersonManager.table = $('#' + soinPersonManager.TB_ID).DataTable();

	enableSingleSelection(soinPersonManager.TB_ID);
	$('#soin_person_list').click(function () {
		soinPersonManager.getSoinPersonDetail();
	});

	/**
	 * chosen多选框
	 */
	var chosenConfig = {
		".chosen-select": {},
		".chosen-select-deselect": {allow_single_deselect: true},
		".chosen-select-no-single": {disable_search_threshold: 10},
		".chosen-select-no-results": {no_results_text: "Oops, nothing found!"},
		".chosen-select-width": {width: "95%"}
	};

	for (var selector in chosenConfig) {
		$(selector).chosen(chosenConfig[selector]);
	}

});
