/**
 * Created by CuiMengxin on 2015/12/22.
 */
var soin_order_status_manager = {
	ORDER_ALL: 0,
	ORDER_NEED_TO_PAY: 1,
	ORDER_PAYED: 2,
	ORDER_ABNORMAL: 4,
	ORDER_REFUNDED: 8,
	ORDER_UNDER_WAY: 16,
	ORDER_STOPED: 32,
	ORDER_FINISHED: 64,
	ORDER_CANCELED: 128,
	ORDER_REFUNDING: 256,

	ORDER_NOT_ARREARAGE: 1,//订单不欠费
	ORDER_ARREARAGE: 2,//订单欠费

	ORDER_STATUS_DICTIONARY: {
		0: "所有",
		1: "待支付",
		2: "已支付",
		4: "订单异常",
		8: "已退款",
		16: "缴纳中",
		32: "已停缴",
		64: "已完成",
		128: "已取消",
		256: "退款中"
	},
	ORDER_STATUS_OPERATION_DICTIONARY: {
		1: "待支付",
		2: "支付",
		4: "订单异常",
		8: "退款",
		16: "缴纳中",
		32: "停缴",
		64: "完成订单",
		128: "取消订单",
		256: "退款中"
	},
	ORDER_STATUS_OPERATION_ARRAY: [2, 32, 8, 16, 4, 64, 128, 256],
	ORDER_STATUS_STYLE_DICTIONARY: {
		0: "",
		1: "label label-info",//浅绿色
		2: "label label-success",
		4: "label label-danger",//红色
		8: "label label-warning",//橘黄色
		16: "label label-success",
		32: "label label-inverse",//黑色
		64: "label label-success",//蓝色
		128: "label label-warning",
		256: "label label-warning",//橘黄色
	},

	//URL 暂时 找不到引用
	SUPPLY_URL: "admin/soin/order/manage/supply",//补款
	RECOVER_URL: "admin/soin/order/manage/recover",//订单异常恢复
	CANCEL_URL: "admin/soin/order/manage/cancel",//取消订单


	CONFIRM_STATUS_MODAL: "#soin_order_confirm_change_status_modal",//确认变更的模态窗口
	STATUS_CHANGE_HINT: "#status_change_hint",//状态变更提示
	UNDER_WAY_HINT: "#under_way_hint",//部分缴纳提示
	ORDER_MONEY_HINT: "#order_money_hit",//金额提示
	ORDER_MONEY_HINT_HUD: "#order_money_hit_hud",//金额提示的菊花
	STATUS_CHANGE_HINT_ORDER_NO: "#status_change_hint_order_no",
	CURRENT_STATUS: "#current_status",
	TO_STATUS: "#to_status",
	UNDER_WAY_ORDER_NO: "#under_way_order_no",
	UNDER_WAY_TIME: "#under_way_time",
	ORDER_MONEY_INPUT: "#order_money_input",
	CONFIRM_CHANGE_STATUS_BTN: "#confirm_change_status_btn",

	/**
	 * 得到订单的状态变更按钮组和调整供应商按钮
	 * @param rowData
	 * @returns {*|jQuery|HTMLElement}
	 */
	getStautsButtons: function (rowData) {
		var adjustSalesmanBtn = $("<button></button>").appendTo($("#detail_" + rowData["order_id"]));
		adjustSalesmanBtn.addClass("btn btn-primary btn-sm");
		adjustSalesmanBtn.text("调整业务员和供应商");
		adjustSalesmanBtn.attr("onclick", "soinOrderManager.adjustSalesmanAndSupplier(" + rowData["soin_district_code"] + ")");

		var currentStatusCode = rowData["status_code"];
		var orderNo = rowData["order_no"];
		var orderId = rowData["order_id"];
		var rowDiv = $("<div></div>");
		rowDiv.addClass("row");
		var buttonDiv = $("<div class='col-md-12'></div>").appendTo(rowDiv);
		var transformableStatusArray = soin_order_status_manager.getTransformableStatusArray(currentStatusCode);
		$.each(soin_order_status_manager.ORDER_STATUS_OPERATION_ARRAY, function (index, key) {
			var statusBtn = $("<button></button>").appendTo(buttonDiv);
			statusBtn.addClass("btn btn-primary btn-sm");
			statusBtn.attr("style", "margin-left:8px;margin-top:22px");
			statusBtn.attr("disabled", true);
			if (soin_order_status_manager.inArray(key, transformableStatusArray) > -1) {
				statusBtn.removeAttr("disabled");//显示可以点击的按钮
			}
			statusBtn.text(soin_order_status_manager.ORDER_STATUS_OPERATION_DICTIONARY[key]);
			//设置按钮点击事件(弹窗)
			switch (key) {
				//支付
				case soin_order_status_manager.ORDER_PAYED:
				{
					statusBtn.attr("onclick", "soin_order_status_manager.showConfirmPayedModal('" + orderId + "','" + orderNo + "'," + currentStatusCode + "," + key + "," + rowData["payment"] + "," + rowData["version"] + ")");
					break;
				}
				//退款
				case soin_order_status_manager.ORDER_REFUNDED:
				{
					statusBtn.attr("onclick", "soin_order_status_manager.showConfirmRefundCompleteModal('" + orderId + "','" + orderNo + "'," + currentStatusCode + "," + rowData["version"] + ")");
					break;
				}
				//其他
				default:
				{
					statusBtn.attr("onclick", "soin_order_status_manager.showChangeStatusModel('" + orderId + "','" + orderNo + "'," + currentStatusCode + "," + key + "," + rowData["version"] + ")");
				}
			}
		});
		return rowDiv;
	},

	/**
	 * 获得缴纳按钮
	 * @returns {*|jQuery|HTMLElement}
	 */
	getUnderWayButton: function (soinId, orderId, orderNo, orderStatusCode, timeText, version) {
		var underWayButton = $("<button></button>");
		underWayButton.addClass("btn btn-primary btn-sm");
		underWayButton.attr("style", "margin-left:8px");
		//判断订单状态是否可以缴纳
		if (soin_order_status_manager.inArray(soin_order_status_manager.ORDER_UNDER_WAY, soin_order_status_manager.getTransformableStatusArray(orderStatusCode)) > -1)
			underWayButton.attr("disabled", false);//可以缴纳
		else
			underWayButton.attr("disabled", true);//不可以缴纳
		underWayButton.attr("onclick", "soin_order_status_manager.showConfirmPartialCompleteModal('" + soinId + "','" + orderId + "','" + orderNo + "','" + timeText + "'," + version + ")");
		underWayButton.text("缴纳");
		return underWayButton;
	},

	/**
	 * 获取能够转向的状态集
	 * @param currnetStatusCode
	 * @returns {Array}
	 */
	getTransformableStatusArray: function (currentStatusCode) {
		switch (currentStatusCode) {
			case soin_order_status_manager.ORDER_NEED_TO_PAY:
				return [soin_order_status_manager.ORDER_CANCELED, soin_order_status_manager.ORDER_ABNORMAL, soin_order_status_manager.ORDER_PAYED];
			case soin_order_status_manager.ORDER_PAYED:
				return [soin_order_status_manager.ORDER_ABNORMAL, soin_order_status_manager.ORDER_REFUNDING, soin_order_status_manager.ORDER_FINISHED, soin_order_status_manager.ORDER_UNDER_WAY];
			case soin_order_status_manager.ORDER_ABNORMAL:
				return [soin_order_status_manager.ORDER_CANCELED, soin_order_status_manager.ORDER_PAYED, soin_order_status_manager.ORDER_REFUNDING];
			case soin_order_status_manager.ORDER_REFUNDED:
				return [];
			case soin_order_status_manager.ORDER_UNDER_WAY:
				return [soin_order_status_manager.ORDER_STOPED, soin_order_status_manager.ORDER_FINISHED, soin_order_status_manager.ORDER_UNDER_WAY];
			case soin_order_status_manager.ORDER_STOPED:
				return [soin_order_status_manager.ORDER_REFUNDING, soin_order_status_manager.ORDER_UNDER_WAY];
			case soin_order_status_manager.ORDER_FINISHED:
				return [];
			case soin_order_status_manager.ORDER_CANCELED:
				return [];
			case soin_order_status_manager.ORDER_REFUNDING:
				return [soin_order_status_manager.ORDER_REFUNDED];
		}
	}
	,

	/**
	 * 判断元素是否在数组中
	 * @param key
	 * @param array
	 * @returns {number}
	 */
	inArray: function (key, array) {
		for (var i = 0; i < array.length; i++) {
			if (key == array[i])
				return i;
		}
		return -1;
	}
	,

	/**
	 * 显示确认变更状态模态窗口（通用）
	 * @param orderNo
	 * @param currentStatus
	 * @param toStatus
	 */
	showConfirmStatusModal: function (orderId, orderNo, currentStatus, toStatus) {
		$(soin_order_status_manager.UNDER_WAY_HINT).attr("hidden", "hidden");//隐藏缴纳某次社保提示
		$(soin_order_status_manager.ORDER_MONEY_HINT).attr("hidden", "hidden");//隐藏金额输入框
		$(soin_order_status_manager.STATUS_CHANGE_HINT).removeAttr("hidden");//取消状态变更提示的隐藏
		$(soin_order_status_manager.CONFIRM_STATUS_MODAL).modal('show');//显示窗口

		//显示订单编号
		$(soin_order_status_manager.STATUS_CHANGE_HINT_ORDER_NO).text(orderNo);
		//当前状态
		$(soin_order_status_manager.CURRENT_STATUS).text(soin_order_status_manager.ORDER_STATUS_DICTIONARY[currentStatus]);
		$(soin_order_status_manager.CURRENT_STATUS).removeClass();
		$(soin_order_status_manager.CURRENT_STATUS).addClass(soin_order_status_manager.ORDER_STATUS_STYLE_DICTIONARY[currentStatus]);

		//转向转状态
		$(soin_order_status_manager.TO_STATUS).text(soin_order_status_manager.ORDER_STATUS_DICTIONARY[toStatus]);
		$(soin_order_status_manager.TO_STATUS).removeClass();
		$(soin_order_status_manager.TO_STATUS).addClass(soin_order_status_manager.ORDER_STATUS_STYLE_DICTIONARY[toStatus]);
	},

	/**
	 * 显示简单状态变更确认窗口
	 * @param orderId
	 * @param orderNo
	 * @param currentStatus
	 * @param toStatus
	 * @param version
	 */
	showChangeStatusModel: function (orderId, orderNo, currentStatus, toStatus, version) {
		soin_order_status_manager.showConfirmStatusModal(orderId, orderNo, currentStatus, toStatus);
		$(soin_order_status_manager.CONFIRM_CHANGE_STATUS_BTN).attr("onclick", "soin_order_status_manager.confirmChangeStatus('" + orderId + "'," + toStatus + "," + version + ")");
	},

	/**
	 * 确认变更简单状态（除付款，退款和缴纳单独处理之外的状态变更）
	 * @param orderId
	 * @param orderNo
	 * @param version
	 */
	confirmChangeStatus: function (orderId, toStatus, version) {
		var params = {"order_id": orderId, "version": version};
		var requestUrl;
		switch (toStatus) {
			//订单完成
			case soin_order_status_manager.ORDER_FINISHED:
			{
				requestUrl = urlGroup.soin_order_complete;
				break;
			}
			//订单停缴
			case soin_order_status_manager.ORDER_STOPED:
			{
				requestUrl = urlGroup.soin_order_stop;
				break;
			}
			//订单取消
			case soin_order_status_manager.ORDER_CANCELED:
			{
				requestUrl = urlGroup.soin_order_cancel;
				break;
			}
			//订单异常
			case soin_order_status_manager.ORDER_ABNORMAL:
			{
				requestUrl = urlGroup.soin_order_exception;
				break;
			}
			//退款中
			case soin_order_status_manager.ORDER_REFUNDING:
			{
				requestUrl = urlGroup.soin_order_refunding;
				break;
			}
			//缴纳中
			case soin_order_status_manager.ORDER_UNDER_WAY:
			{
				requestUrl = urlGroup.soin_order_underway;
				break;
			}
		}
		aryaPostRequest(requestUrl, params, function (data) {
			if (data["code"] == RESPONSE_OK_CODE) {
				refreshDataTable(soinOrderManager);
				toastr.success(data.result["msg"]);
			} else {
				toastr.error(data["msg"]);
			}
		}, function (data) {

		})
	},

	/**
	 * 显示付款窗口
	 * @param orderId
	 * @param orderNo
	 * @param currentStatus
	 * @param toStatus
	 * @param payment
	 */
	showConfirmPayedModal: function (orderId, orderNo, currentStatus, toStatus, payment, version) {
		soin_order_status_manager.showConfirmStatusModal(orderId, orderNo, currentStatus, toStatus);
		$(soin_order_status_manager.ORDER_MONEY_HINT).removeAttr("hidden");//显示金额输入框
		$(soin_order_status_manager.ORDER_MONEY_INPUT).val(payment);
		$(soin_order_status_manager.CONFIRM_CHANGE_STATUS_BTN).attr("onclick", "soin_order_status_manager.confirmPayOrder('" + orderId + "'," + version + ")");
	},

	/**
	 * 确认给某个订单付款
	 * @param orderId
	 * @param orderNo
	 */
	confirmPayOrder: function (orderId, version) {
		var money = $(soin_order_status_manager.ORDER_MONEY_INPUT).val();
		var params = {"order_id": orderId, "money": money, "version": version};
		aryaPostRequest(
			urlGroup.soin_order_payment_complete,
			params,
			function (data) {
				if (data["code"] == RESPONSE_OK_CODE) {
					refreshDataTable(soinOrderManager);
					toastr.success(data.result["msg"]);
				} else {
					toastr.error(data["msg"]);
				}
			},
			function (data) {

			}
		);
	},

	/**
	 * 显示确认缴纳某月社保模态窗口
	 * @param soinId
	 * @param orderId
	 * @param orderNo
	 * @param timeText
	 */
	showConfirmPartialCompleteModal: function (soinId, orderId, orderNo, timeText, version) {
		$(soin_order_status_manager.STATUS_CHANGE_HINT).attr("hidden", "hidden");
		$(soin_order_status_manager.ORDER_MONEY_HINT).attr("hidden", "hidden");
		$(soin_order_status_manager.UNDER_WAY_HINT).removeAttr("hidden");
		$(soin_order_status_manager.CONFIRM_STATUS_MODAL).modal('show');
		$(soin_order_status_manager.UNDER_WAY_ORDER_NO).text(orderNo);
		//当前状态，用于显示缴纳年月
		$(soin_order_status_manager.UNDER_WAY_TIME).text(timeText);
		//改变确认按钮的点击事件
		$(soin_order_status_manager.CONFIRM_CHANGE_STATUS_BTN).attr("onclick", "soin_order_status_manager.confirmPartialComplete('" + soinId + "','" + orderId + "','" + timeText + "'," + version + ")");
	},

	/**
	 * 确认缴纳某次社保
	 * @param soinId
	 * @param orderId
	 * @param orderNo
	 * @param timeText
	 */
	confirmPartialComplete: function (soinId, orderId, timeText, version) {
		var params = {"order_id": orderId, "soin_id": soinId, "version": version};
		aryaPostRequest(
			urlGroup.soin_order_partial_complete,
			params,
			function (data) {
				if (data["code"] == RESPONSE_OK_CODE) {
					refreshDataTable(soinOrderManager);
					toastr.success(data.result["msg"]);
				} else {
					toastr.error(data["msg"]);
				}
			},
			function (data) {

			}
		);

	},

	/**
	 * 显示退款窗口
	 * @param orderId
	 * @param orderNo
	 */
	showConfirmRefundCompleteModal: function (orderId, orderNo, currentStatus, version) {
		showHUD(soin_order_status_manager.ORDER_MONEY_HINT_HUD);//显示加载动画

		soin_order_status_manager.showConfirmStatusModal(
			orderId,
			orderNo,
			currentStatus,
			soin_order_status_manager.ORDER_REFUNDED
		);

		var obj = {
			order_id: orderId
		};
		var url = urlGroup.soin_order_residual_amount + "?" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				if (data["code"] == RESPONSE_OK_CODE) {
					dismissHUD(soin_order_status_manager.ORDER_MONEY_HINT_HUD);//取消加载动画
					$(soin_order_status_manager.ORDER_MONEY_HINT).removeAttr("hidden");//显示金额输入框
					$(soin_order_status_manager.ORDER_MONEY_INPUT).val(data.result['amount']);
				} else {
					toastr.error(data["msg"]);
				}
			},
			function (data) {

			}
		);

		$(soin_order_status_manager.CONFIRM_CHANGE_STATUS_BTN).attr("onclick",
			"soin_order_status_manager.confirmRefundComplete('" + orderId + "'," + version + ")");
	},

	/**
	 * 转向已退款状态
	 * @param orderNo
	 * @param money
	 */
	confirmRefundComplete: function (orderId, version) {
		var money = $(soin_order_status_manager.ORDER_MONEY_INPUT).val();
		var params = {"order_id": orderId, "money": money, "version": version};
		aryaPostRequest(
			urlGroup.soin_order_refund_complete,
			params,
			function (data) {
				if (data["code"] == RESPONSE_OK_CODE) {
					refreshDataTable(soinOrderManager);
					toastr.success(data.result["msg"]);
				} else {
					toastr.error(data["msg"]);
				}
			},
			function (data) {

			}
		);

	},


};