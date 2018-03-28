/**
 * Created by CuiMengxin on 2015/11/16.
 */
var soinOrderManager = {
	SOIN_TYPE_LIST_SELECTOR: "#soin_order_manager_soin_type_list",
	ORDER_LIST_SELECTOR: "#soin_order_list",
	SOIN_TYPE_SELECTOR: "#soin_order_manager_soin_type_text",
	DISTRICT_TREE_SELECTOR: "#soin_order_manager_district_tree",
	ORDER_STATUS_SELECTOR: "#soin_order_manager_status_chosen",
	ORDER_DETAIL_SELECTOR: "#order_m_",
	ORDER_DETAIL_ID: "order_m_",

	//ORDER_LIST_URL: "admin/soin/order/manage/list/handle",//查询当前需要办理的订单
	//ORDER_DETAIL_URL: "admin/soin/order/manage/detail?order_id=",//查询订单详情URL
	//SOIN_TYPE_ALL_LIST_URL: "admin/soin/district/type_all",//社保类型
	//SOIN_SALESMAN_ALL_LIST_URL: "admin/soin/order/manage/salesman/list",//业务员 列表
	//SOIN_SET_SALESMAN_SUPPLIER_URL: "admin/soin/order/manage/set_salesman_supplier",//设置 业务员 和 供应商
	//SOIN_DISTCIT_SUPPLIER_LIST_URL: "admin/soin/order/manage/suppliers/list?district_id=",//供应商 列表

	districtTreeSelectedNode: null,//选中的节点
	tabelTrList: ["soinPersonName", "district", "soinType", "year", "startMonth", "createTime",
		"statusCode", "aryaUserId", "soinPersonName", "injury", "medical", "pregnancy", "pension", "unemployment", "houseFund", "fee", "count", "payment"],
	dataTable: null,
	soinTypeDataTable: null,//社保类型的dataTable
	soinTypeTable: null,//社保类型的DataTable
	districtId: null,//查询社保类型需要的地区id
	isSoinTypeSelectedAll: false,
	soinTypeRowsSelected: [],
	currentOrderId: null,
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
	},

	//请求已开通社保的地区树，并设置树节点的点击事件
	getSoinDistricTree: function () {
		if (null == soinOrderManager.districtTreeSelectedNode)

			soin.getSoinDistricTree(
				soinOrderManager.DISTRICT_TREE_SELECTOR,
				function (districtTreeData) {

					soin.setSoinDistrictTree(
						soinOrderManager.DISTRICT_TREE_SELECTOR,
						districtTreeData,
						function (event, selectedNode) {//给地区树赋值
							soinOrderManager.districtTreeSelectedNode = selectedNode;
							var districtId = selectedNode["href"];
							if (districtId != null) {
								soinOrderManager.getSoinTypes(districtId);//点击事件，请求地区社保类型
								soinOrderManager.soinTypeRowsSelected = [];//清空所有选择的类型
								//取消全选的情况
								if ($(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').is(':checked'))
									$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').trigger("click");
							}
						}
					);

				}
			);

	},

	//请求社保类型
	getSoinTypes: function (districtId) {
		soin.getSoinTypes(districtId, function (data) {
			if (data.code == RESPONSE_OK_CODE) {
				soinOrderManager.districtId = districtId;
				soinOrderManager.soinTypeTable.ajax.reload();
			}
			else {
				//请求社保类型异常，暂未处理
			}
		});
	},

	//确定选中社保类型事件
	chosedSoinType: function () {
		$(soinOrderManager.SOIN_TYPE_SELECTOR).empty();
		$(soinOrderManager.SOIN_TYPE_SELECTOR).append("地区：");

		//从树中查出选中的地区
		var districtNames = new Array();
		var nodeIndex = 0;
		var parentNode = soinOrderManager.districtTreeSelectedNode;
		while (parentNode.nodeId != null) {
			districtNames[nodeIndex] = parentNode.text;
			nodeIndex++;
			parentNode = $(soinOrderManager.DISTRICT_TREE_SELECTOR).treeview('getParent', parentNode);
		}
		//显示出地区名称组合
		for (var i = districtNames.length - 1; i >= 0; i--) {
			$(soinOrderManager.SOIN_TYPE_SELECTOR).append(districtNames[i]);
			if (i > 0)
				$(soinOrderManager.SOIN_TYPE_SELECTOR).append("-");
		}

		//添加上社保类型名称
		$(soinOrderManager.SOIN_TYPE_SELECTOR).append("     类型：");
		var soinTypes = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData();
		if (soinOrderManager.soinTypeRowsSelected.length > 0)
			for (var i = 0; i < soinOrderManager.soinTypeRowsSelected.length; i++) {
				if (i >= 2) {
					$(soinOrderManager.SOIN_TYPE_SELECTOR).append("等共" + soinOrderManager.soinTypeRowsSelected.length + " 个");
					break;
				}
				for (var j = 0; j < soinTypes.length; j++) {
					if (soinTypes[j]["type_id"] == soinOrderManager.soinTypeRowsSelected[i]) {
						$(soinOrderManager.SOIN_TYPE_SELECTOR).append((i > 0 ? "，" : "") + soinTypes[j]["type_name"]);
						break;
					}
				}
			}
		else
			$(soinOrderManager.SOIN_TYPE_SELECTOR).append("无");
	},

	/**
	 * 组装订单详情表格并返回
	 * @param nTr
	 * @returns {*|jQuery|HTMLElement}
	 */
	fnFormatDetails: function (rowData) {
		var div = $("<div></div>");
		div.attr("style", "background-color: white;margin-left:5px");
		div.attr("id", soinOrderManager.ORDER_DETAIL_ID + rowData["order_no"]);
		div.append(componentLoadHUD);//显示加载动画
		soinOrderManager.currentOrderId = rowData["order_id"];

		var obj = {
			order_id: rowData["order_id"]
		};

		var url = urlGroup.soin_order_detail + "?" + jsonParseParam(obj);

		//请求订单详情
		aryaGetRequest(
			url,
			function (data) {
				dismissHUD(soinOrderManager.ORDER_DETAIL_SELECTOR + rowData["order_no"]);//隐藏加载动画
				if (data["code"] == RESPONSE_OK_CODE) {
					//如果订单欠费，则突出该行
					if (data.result["arrearage"] == soin_order_status_manager.ORDER_ARREARAGE)
						$("#" + rowData["order_no"]).attr("style", "background-color:LightPink");
					else if (data.result["arrearage"] == soin_order_status_manager.ORDER_NOT_ARREARAGE)
						$("#" + rowData["order_no"]).removeAttr("style");

					var detailDiv = $(soinOrderManager.ORDER_DETAIL_SELECTOR + data.result["order_no"]);
					//缴纳详情
					detailDiv.append(soinOrderDetail.formatOrderDetail(data));
					//缴纳进度
					detailDiv.append(soinOrderDetail.formatPaymentStep(data.result["paymonth_details"], data.result["order_id"], data.result["order_no"], data.result["status_code"], data.result["version"], true));
					//操作按钮组
					detailDiv.append(soin_order_status_manager.getStautsButtons(data.result));
				} else {
					var detailDiv = $(soinOrderManager.ORDER_DETAIL_SELECTOR + rowData["order_no"]);
					var span = $("<span></span>").appendTo(detailDiv);
					span.text(data["msg"]);
				}
			},
			function (data) {

			}
		)
		;
		return div;
	},

	/**
	 * 当翻页的时候，将之前选中的行重新选中（社保类型选择表）
	 */
	setSelectedRow: function () {
		$($(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable().fnSettings().aoData).each(function () {
			var data = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData(this.nTr);//取出每行数据
			//判断该行是否在选中的数组中，是则将选中状态显示出来，否将选中状态取消
			if ($.inArray(data["type_id"], soinOrderManager.soinTypeRowsSelected) != -1) {
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
	 * 查询订单列表
	 */
	getOrderList: function () {
		//loadingInit();
		$(soinOrderManager.ORDER_LIST_SELECTOR).DataTable().ajax.reload();
	},

	/**
	 * 清空所有查询条件
	 */
	clearAllFilterCondition: function () {
		$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:checked').trigger('click');
		soinOrderManager.soinTypeRowsSelected = [];
		$(soinOrderManager.SOIN_TYPE_SELECTOR).empty();
		$(soinOrderManager.ORDER_STATUS_SELECTOR).val("");
		$(soinOrderManager.ORDER_STATUS_SELECTOR).trigger('chosen:updated');
	},
	/**
	 *调整业务员和供应商
	 */
	adjustSalesmanAndSupplier: function (districtId) {
		$("#soin_order_salesman_select").empty();
		$("#soin_order_supplier_select").empty();
		$("#soin_order_manager_chose_salesman_supplier_modal").modal("show");

		aryaGetRequest(
			urlGroup.soin_salesman_list,
			function (data) {
				if (data.code == ERR_CODE_OK) {
					$.each(data.result["salesman"], function (index, salesman) {
						var option = $("<option></option>").appendTo($("#soin_order_salesman_select"));
						option.val(salesman["id"]);
						option.text(salesman["name"]);
					})
				} else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			}
		);

		var obj = {
			district_id: districtId
		};
		var url = urlGroup.soin_supplier_list + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				if (data.code == ERR_CODE_OK) {
					$.each(data.result["suppliers"], function (index, suppliers) {
						var option = $("<option></option>").appendTo($("#soin_order_supplier_select"));
						option.val(suppliers["id"]);
						option.text(suppliers["name"]);
					})
				} else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			}
		);

	},

	/**
	 * 确认调整业务员和供应商
	 * @param district
	 */
	confirmAdjustSalesmanAndSupplier: function () {
		var params = {
			"order_id": soinOrderManager.currentOrderId,
			"salesman_id": $("#soin_order_salesman_select").val(),
			"supplier_id": $("#soin_order_supplier_select").val()
		};

		aryaPostRequest(
			urlGroup.soin_salesman_and_supplier_set,
			params,
			function (data) {
				if (data.code == ERR_CODE_OK) {
					$("#soin_order_manager_chose_salesman_supplier_modal").modal("hide");
					toastr.success("业务员或供应商调整成功!");
				} else {
					toastr.error(data.msg);
				}
			},
			function (data) {

			}
		);

	}


};

$(document).ready(function () {
	soinOrderManager.dataTable = $(soinOrderManager.ORDER_LIST_SELECTOR).dataTable({
		'processing': true,
		'serverSide': true,
		"bPaginate": true,
		'bStateSave': true,
		'searching': false,
		'ordering': false,
		"language": {
			"url": DATATABLES_CHINESE_LANGUAGE
		},
		'ajax': {
			'url': urlGroup.soin_order_list,
			type: "GET",
			data: function (data) {
				//社保类型参数
				if (soinOrderManager.soinTypeRowsSelected.length == $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData().length) {
					data.district_id = soinOrderManager.districtId;
				}
				else {
					data.soin_type_id = soinOrderManager.soinTypeRowsSelected.join(":");
				}
				data.keyword = $("#soin_order_manage_person_idcardno_or_phone").val();


				//订单状态参数
				var status = $(soinOrderManager.ORDER_STATUS_SELECTOR).val();
				if (status != null) {
					var bitOrResult = 0;
					for (var i = 0; i < status.length; i++)
						bitOrResult = bitOrResult | status[i];
					data.order_status_code = bitOrResult;
				}
			}
		},
		"aoColumns": [
			{"sTitle": "订单编号", "mData": 'order_no', "sWidth": "25px"},
			{"sTitle": "姓名", "mData": 'person_name'},
			{"sTitle": "参保地区", "mData": 'district'},
			{"sTitle": "社保类型", "mData": 'type_name'},
			{"sTitle": "年", "mData": 'start_year'},
			{"sTitle": "月", "mData": 'start_month'},
			{"sTitle": "期数", "mData": 'count'},
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
		"fnCreatedRow": function (nRow, aData, iDataIndex) {
			$(nRow).attr('id', aData["order_no"]);//为tr添加id
			//如果订单欠费则标红
			if (aData["arrearage"] == soin_order_status_manager.ORDER_ARREARAGE) {
				$(nRow).attr("style", "background-color:LightPink");
			}
		}
	});
	$(soinOrderManager.ORDER_LIST_SELECTOR + ' tbody').on('click', 'tr', function () {
		if ($(this).attr("class") == null || $(this).attr("class") == "detail") {
			//阻止子表格的点击事件
			return;
		}
		var table = $(soinOrderManager.ORDER_LIST_SELECTOR).DataTable();
		if ($(this).hasClass('selected')) {
			soinOrderManager.dataTable.fnClose($(this));//折叠该行
			$(this).removeClass('selected');
		}
		else {
			soinOrderManager.dataTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			var nTr = table.row('.selected');
			var rowData = soinOrderManager.dataTable.fnGetData(nTr);
			if ($(soinOrderManager.ORDER_DETAIL_SELECTOR + rowData["order_no"]).length > 0) {
				soinOrderManager.dataTable.fnClose($(this));//已经展开的行折叠该行
				$(this).removeClass('selected');
			}
			else
				soinOrderManager.dataTable.fnOpen(nTr, soinOrderManager.fnFormatDetails(rowData));//展开该行
		}
	});

	/**
	 * 以下为社保类型表格
	 * @type {*|jQuery}
	 */
	soinOrderManager.soinTypeDataTable = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable({
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
				data.district_id = soinOrderManager.districtId;
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

	soinOrderManager.soinTypeTable = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).DataTable();
	var table = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).DataTable();

	//checkbox点击事件
	$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).on('click', 'input[type="checkbox"]', function (e) {
		var $row = $(this).closest('tr');

		var data = table.row($row).data();

		var rowId = data["type_id"];

		var index = $.inArray(rowId, soinOrderManager.soinTypeRowsSelected);

		if (this.checked && index === -1) {
			soinOrderManager.soinTypeRowsSelected.push(rowId);
		} else if (!this.checked && index != -1) {
			soinOrderManager.soinTypeRowsSelected.splice(index, 1);
		}

		if (this.checked) {
			$row.addClass('selected');
		} else {
			$row.removeClass('selected');
			//取消全选
			if ($(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').is(':checked')) {
				soinOrderManager.isSoinTypeSelectedAll = false;
				$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').attr("checked", false);
			}
		}
		e.stopPropagation();
	});

	//表格全选反选
	$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' thead input[name="select_all"]').on('click', function (e) {
		soinOrderManager.soinTypeRowsSelected = [];//清空所有选择的类型
		if (this.checked) {
			var soinTypes = $(soinOrderManager.SOIN_TYPE_LIST_SELECTOR).dataTable().fnGetData();
			for (var i = 0; i < soinTypes.length; i++) {
				soinOrderManager.soinTypeRowsSelected[i] = soinTypes[i]["type_id"];
			}
			soinOrderManager.isSoinTypeSelectedAll = true;
			$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:not(:checked)').trigger('click');
		} else {
			soinOrderManager.isSoinTypeSelectedAll = false;
			$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:checked').trigger('click');
		}
		// Prevent click event from propagating to parent
		e.stopPropagation();
	});

	// 表格重新绘制事件
	table.on('draw', function () {
		if (soinOrderManager.isSoinTypeSelectedAll) {
			$(soinOrderManager.SOIN_TYPE_LIST_SELECTOR + ' tbody input[type="checkbox"]:not(:checked)').trigger('click');
		} else {
			soinOrderManager.setSelectedRow();//刷新选中的行
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

