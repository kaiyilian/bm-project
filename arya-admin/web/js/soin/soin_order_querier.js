/**
 * Created by CuiMengxin on 2016/2/1.
 */
var soinOrderQuerier = {

	//ORDER_LIST_URL: "admin/soin/order/query/list",//查询订单URL
	//ORDER_DETAIL_URL: "admin/soin/order/query/detail?order_id=",//查询订单详情URL
	//SOIN_TYPE_ALL_LIST_URL: "admin/soin/district/type_all",//查询地区下所有社保类型

	ORDER_LIST_SELECTOR: "#soin_order_querier_list",
	SOIN_TYPE_LIST_SELECTOR: "#soin_order_querier_soin_type_list",
	SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR: "#soin_order_querier_soin_type_text",
	ORDER_STATUS_SELECTOR: "#soin_order_querier_status_chosen",
	DISTRICT_TREE_SELECTOR: "#soin_order_querier_district_tree",
	SOIN_DISTRICT_TREE_SELECTOR: "#soin_order_querier_district_tree",
	ORDER_NO_SELECTOR: "#soin_order_querier_order_no",
	USER_IDCARD_PHONE_SELECTOR: "#soin_order_querier_user_idcardno_or_phone",//用户身份证或手机号选择器
	PERSON_IDCARD_PHONE_SELECTOR: "#soin_order_querier_person_idcardno_or_phone",//参保人身份证或手机号选择器
	ORDER_DETAIL_SELECTOR: "#order_q_",
	ORDER_DETAIL_ID: "order_q_",

	dataTable: null,//订单dataTable表
	table: null,//订单DataTable
	soinTypeTable: null,//社保类型表

	soinTypeRowsSelected: [],//选中的社保类型ids
	districtId: null,//查询社保类型需要的地区id

	//请求已开通社保的地区树，并设置树节点的点击事件
	getSoinDistricTree: function () {
		if (soinOrderQuerier.districtTreeSelectedNode == null)
			soin.getSoinDistricTree(soinOrderQuerier.DISTRICT_TREE_SELECTOR, function (districtTreeData) {
				soin.setSoinDistrictTree(soinOrderQuerier.DISTRICT_TREE_SELECTOR, districtTreeData, function (event, selectedNode) {//给地区树赋值
					soinOrderQuerier.districtTreeSelectedNode = selectedNode;
					var districtId = selectedNode["href"];
					if (districtId != null) {
						soinOrderQuerier.getSoinTypes(districtId);//点击事件，请求地区社保类型
						soinOrderQuerier.soinTypeRowsSelected = [];//清空所有选择的类型
						//取消全选的情况
						if ($(soinOrderQuerier.ORDER_LIST_SELECTOR + ' thead input[name="select_all"]').is(':checked'))
							$(soinOrderQuerier.ORDER_LIST_SELECTOR + ' thead input[name="select_all"]').trigger("click");
					}
				});
			});
	},

	//请求社保类型
	getSoinTypes: function (districtId) {
		soin.getSoinTypes(districtId, function (data) {
			if (data.code == RESPONSE_OK_CODE) {
				soinOrderQuerier.districtId = districtId;
				soinOrderQuerier.soinTypeTable.ajax.reload();
			}
			else {
				//请求社保类型异常，暂未处理
			}
		});
	},

	//确定选中社保类型事件
	chosedSoinType: function () {
		$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).empty();
		$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append("地区：");

		//从树中查出选中的地区
		var districtNames = new Array();
		var nodeIndex = 0;
		var parentNode = soinOrderQuerier.districtTreeSelectedNode;
		while (parentNode.nodeId != null) {
			districtNames[nodeIndex] = parentNode.text;
			nodeIndex++;
			parentNode = $(soinOrderQuerier.SOIN_DISTRICT_TREE_SELECTOR).treeview('getParent', parentNode);
		}
		//显示出地区名称组合
		for (var i = districtNames.length - 1; i >= 0; i--) {
			$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append(districtNames[i]);
			if (i > 0)
				$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append("-");
		}

		//添加上社保类型名称
		$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append("      类型：");
		var soinTypes = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData();
		if (soinOrderQuerier.soinTypeRowsSelected.length > 0)
			for (var i = 0; i < soinOrderQuerier.soinTypeRowsSelected.length; i++) {
				if (i >= 2) {
					$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append("等共" + soinOrderQuerier.soinTypeRowsSelected.length + " 个");
					break;
				}
				for (var j = 0; j < soinTypes.length; j++) {
					if (soinTypes[j]["type_id"] == soinOrderQuerier.soinTypeRowsSelected[i]) {
						$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append((i > 0 ? "，" : "") + soinTypes[j]["type_name"]);
						break;
					}
				}
			}
		else {
			$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).append("无");
		}
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
	 * 组装订单详情表格并返回
	 * @param nTr
	 * @returns {*|jQuery|HTMLElement}
	 */
	fnFormatDetails: function (rowData) {
		var div = $("<div></div>");
		div.attr("style", "background-color: white;margin-left:5px");
		div.attr("id", soinOrderQuerier.ORDER_DETAIL_ID + rowData["order_no"]);
		div.append(componentLoadHUD);//显示加载动画

		var obj = {
			order_id: rowData["order_id"]
		};
		var url = urlGroup.soin_order_query_detail + "?" + jsonParseParam(obj);
		//请求订单详情
		aryaGetRequest(
			url,
			function (data) {
				dismissHUD(soinOrderQuerier.ORDER_DETAIL_SELECTOR + rowData["order_no"]);//隐藏加载动画
				if (data["code"] == RESPONSE_OK_CODE) {
					//如果订单欠费，则突出该行
					if (data.result["arrearage"] == soin_order_status_manager.ORDER_ARREARAGE)
						$("#" + rowData["order_no"]).attr("style", "background-color:LightPink");
					else if (data.result["arrearage"] == soin_order_status_manager.ORDER_NOT_ARREARAGE)
						$("#" + rowData["order_no"]).removeAttr("style");

					var detailDiv = $(soinOrderQuerier.ORDER_DETAIL_SELECTOR + data.result["order_no"]);
					//缴纳详情
					detailDiv.append(soinOrderDetail.formatOrderDetail(data));
					//缴纳进度
					detailDiv.append(soinOrderDetail.formatPaymentStep(data.result["paymonth_details"], data.result["order_id"], data.result["order_no"], data.result["status_code"], data.result["version"], null));
				} else {
					var detailDiv = $(soinOrderQuerier.ORDER_DETAIL_SELECTOR + rowData["order_no"]);
					var span = $("<span></span>").appendTo(detailDiv);
					span.text(data["msg"]);
				}
			},
			function (data) {
			}
		);

		return div;
	},

	/**
	 * 当翻页的时候，将之前选中的行重新选中
	 */
	setSelectedRow: function () {
		$($(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable().fnSettings().aoData).each(function () {
			var data = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData(this.nTr);//取出每行数据
			//判断该行是否在选中的数组中，是则将选中状态显示出来，否将选中状态取消
			if ($.inArray(data["type_id"], soinOrderQuerier.soinTypeRowsSelected) != -1) {
				$(this.nTr).find('input[type="checkbox"]').attr("checked", true);
				$(this.nTr).addClass('selected');
			} else {
				//取消掉未选中的行的选中状态
				$(this.nTr).find('input[type="checkbox"]').attr("checked", false);
				$(this.nTr).removeClass('selected');
			}
		});
	},

	/**
	 * 清空所有查询条件
	 */
	clearAllFilterCondition: function () {
		$(soinOrderQuerier.ORDER_NO_SELECTOR).val("");
		$(soinOrderQuerier.USER_IDCARD_PHONE_SELECTOR).val("");
		$(soinOrderQuerier.PERSON_IDCARD_PHONE_SELECTOR).val("");
		soinOrderQuerier.soinTypeRowsSelected = [];
		$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:checked').trigger('click');
		$(soinOrderQuerier.SOIN_DISTRICT_SOIN_TYPE_TEXT_SELECTOR).empty();
		$(soinOrderQuerier.ORDER_STATUS_SELECTOR).val("");
		$(soinOrderQuerier.ORDER_STATUS_SELECTOR).trigger('chosen:updated');
	}
};


$(document).ready(function () {
	soinOrderQuerier.dataTable = $(soinOrderQuerier.ORDER_LIST_SELECTOR).dataTable({
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
			'url': urlGroup.soin_order_query_list,
			type: "GET",
			data: function (data) {
				//社保类型参数
				if (soinOrderQuerier.soinTypeRowsSelected.length == $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData().length) {
					data.district_id = soinOrderQuerier.districtId;
				}
				else {
					data.soin_type_id = soinOrderQuerier.soinTypeRowsSelected.join(":");
				}

				//订单状态参数
				var status = $(soinOrderQuerier.ORDER_STATUS_SELECTOR).val();
				if (status != null) {
					var bitOrResult = 0;
					for (var i = 0; i < status.length; i++)
						bitOrResult = bitOrResult | status[i];
					data.order_status_code = bitOrResult;
				}

				//订单编号
				if ($(soinOrderQuerier.ORDER_NO_SELECTOR).val() != null) {
					data.order_no = $(soinOrderQuerier.ORDER_NO_SELECTOR).val();
				}

				//用户身份证号或手机号
				data.user = encodeURI($(soinOrderQuerier.USER_IDCARD_PHONE_SELECTOR).val());//处理中文编码

				//参保人身份证号或手机号
				data.person = encodeURI($(soinOrderQuerier.PERSON_IDCARD_PHONE_SELECTOR).val());
			}
		},
		"aoColumns": [
			{"sTitle": "订单编号", "mData": 'order_no', "sWidth": "25px"},
			{"sTitle": "姓名", "mData": 'person_name'},
			{"sTitle": "参保地区", "mData": 'district'},
			{"sTitle": "社保类型", "mData": 'type_name'},
			{"sTitle": "年", "mData": 'start_year', "sWidth": "5px"},
			{"sTitle": "月", "mData": 'start_month', "sWidth": "5px"},
			{"sTitle": "期数", "mData": 'count', "sWidth": "5px"},
			{
				"sTitle": "总额", "mData": 'payment', "mRender": function (data) {
				return "<span style='margin-right: 0px'>" + data + "元</span>";
			}
			},
			{
				"sTitle": "状态", "mData": 'status_code', "mRender": function (data) {
				return "<span class='" + soin_order_status_manager.ORDER_STATUS_STYLE_DICTIONARY[data] + "'>" + soin_order_status_manager.ORDER_STATUS_DICTIONARY[data] + "</span>";
			}
			},
			{"sTitle": "描述", "mData": 'desc'},
			{"sTitle": "创建时间", "mData": 'create_time'},
		],
	});


	soinOrderQuerier.table = $(soinOrderQuerier.ORDER_LIST_SELECTOR).DataTable();

	$(soinOrderQuerier.ORDER_LIST_SELECTOR + ' tbody').on('click', 'tr', function () {
		if ($(this).attr("class") == null || $(this).attr("class") == "detail") {
			return;//阻止子表格的点击事件
		}
		var table = $(soinOrderQuerier.ORDER_LIST_SELECTOR).DataTable();
		if ($(this).hasClass('selected')) {
			soinOrderQuerier.dataTable.fnClose($(this));//折叠该行
			$(this).removeClass('selected');
		}
		else {
			soinOrderQuerier.dataTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			var nTr = table.row('.selected');
			var rowData = soinOrderQuerier.dataTable.fnGetData(nTr);
			if ($(soinOrderQuerier.ORDER_DETAIL_SELECTOR + rowData["order_no"]).length > 0) {
				soinOrderQuerier.dataTable.fnClose($(this));//已经展开了折叠该行
				$(this).removeClass('selected');
			}
			else
				soinOrderQuerier.dataTable.fnOpen(nTr, soinOrderQuerier.fnFormatDetails(rowData));//展开该行
		}
	});

	/**
	 * 以下为社保类型表格
	 * @type {*|jQuery}
	 */
	soinOrderQuerier.soinTypeDataTable = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable({
		'processing': true,
		'serverSide': false,
		"bPaginate": true,
		'searching': false,
		'ordering': false,
		"language": {
			"url": DATATABLES_CHINESE_LANGUAGE
		},
		ajax: {
			url: urlGroup.soin_type_all_list,
			type: "GET",
			data: function (data) {
				data.district_id = soinOrderQuerier.districtId;
			}
		},
		"aoColumns": [
			{
				"mRender": function (data, type, full, meta) {
					return '<input type="checkbox">';
				}
			},
			{"mData": 'type_name'},
			{"mData": 'type_desc'},
		],
	});

	soinOrderQuerier.soinTypeTable = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).DataTable();
	var table = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).DataTable();

	//checkbox点击事件
	$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).on('click', 'input[type="checkbox"]', function (e) {
		var $row = $(this).closest('tr');

		var data = table.row($row).data();

		var rowId = data["type_id"];

		var index = $.inArray(rowId, soinOrderQuerier.soinTypeRowsSelected);

		if (this.checked && index === -1) {
			soinOrderQuerier.soinTypeRowsSelected.push(rowId);
		} else if (!this.checked && index != -1) {
			soinOrderQuerier.soinTypeRowsSelected.splice(index, 1);
		}

		if (this.checked) {
			$row.addClass('selected');
		} else {
			$row.removeClass('selected');
			//取消全选
			if ($(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').is(':checked')) {
				soinOrderQuerier.isSoinTypeSelectedAll = false;
				$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').attr("checked", false);
			}
		}
		e.stopPropagation();
	});

	//表格全选反选
	$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').on('click', function (e) {
		soinOrderQuerier.soinTypeRowsSelected = [];//清空所有选择的类型
		if (this.checked) {
			var soinTypes = $(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData();
			for (var i = 0; i < soinTypes.length; i++) {
				soinOrderQuerier.soinTypeRowsSelected[i] = soinTypes[i]["type_id"];
			}
			soinOrderQuerier.isSoinTypeSelectedAll = true;
			$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:not(:checked)').trigger('click');
		} else {
			soinOrderQuerier.isSoinTypeSelectedAll = false;
			$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:checked').trigger('click');
		}
		// Prevent click event from propagating to parent
		e.stopPropagation();
	});

	// 表格重新绘制事件
	table.on('draw', function () {
		if (soinOrderQuerier.isSoinTypeSelectedAll) {
			$(soinOrderQuerier.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:not(:checked)').trigger('click');
		} else {
			soinOrderQuerier.setSelectedRow();//刷新选中的行
		}
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
