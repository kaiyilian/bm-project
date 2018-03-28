/**
 * Created by CuiMengxin on 2015/11/16.
 */


var soin = {
	//请求开通社保的地区树
	getSoinDistricTree: function (hudDivId, callBack) {
		showHUD(hudDivId);//显示加载动画
		aryaPostRequest(
			urlGroup.soin_district_tree_url,
			" ",
			function (data) {
				dismissHUD(hudDivId);
				if (callBack)
					callBack(data);
			},
			function () {

			}
		);

	},

	//给地区树赋值和设置点击事件
	setSoinDistrictTree: function (districtTreeDivId, treeData, onclickFunc) {
		$(districtTreeDivId).treeview({
			data: treeData
		});
		//treeview点击事件
		$(districtTreeDivId).on('nodeSelected', function (event, data) {
			if (onclickFunc)
				onclickFunc(event, data);
		});
	},

	//请求社保类型
	getSoinTypes: function (districtId, func) {
		var obj = {
			district_id: districtId
		};
		var url = urlGroup.soin_type_list + "" + jsonParseParam(obj);

		aryaGetRequest(
			url,
			function (data) {
				if (func) {
					func(data);
				}
			},
			function () {

			},
			function (data) {

			}
		);
	}

};
