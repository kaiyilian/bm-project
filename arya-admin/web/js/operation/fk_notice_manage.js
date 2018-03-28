/**
 * Created by CuiMengxin on 2016/9/7.
 * 福库公告管理
 */

var fk_notice_manage = {
	containerName: "",

	//
	Init: function () {
		fk_notice_manage.containerName = ".fk_notice_manage_container";
		fk_notice_manage.noticeGet();//获取公告
	},

	//获取公告
	noticeGet: function () {

		aryaGetRequest(urlGroup.fk_notice_get, function (data) {
			//alert(JSON.stringify(data));

			if (data.code == RESPONSE_OK_CODE) {

				var $item = data.result;
				var begin_time = $item.begin_time;
				begin_time = timeInit1(begin_time);
				var end_time = $item.end_time;
				end_time = timeInit1(end_time);
				var content = $item.content;
				var pre_content = $item.pre_content;

				var $row = $(fk_notice_manage.containerName).find(".content");
				$row.find(".beginTime").val(begin_time);
				$row.find(".endTime").val(end_time);
				$row.find(".activity_notice").val(content);
				$row.find(".activity_forenotice").val(pre_content);


			}
			else {
				messageCue(data.msg);
			}
		}, function (error) {
			messageCue(error);
		});
	},

	//编辑公告
	noticeModify: function () {
		var $row = $(fk_notice_manage.containerName).find(".content");
		var begin_time = $.trim($row.find(".beginTime").val());
		var end_time = $.trim($row.find(".endTime").val());
		var content = $.trim($row.find(".activity_notice").val());
		var pre_content = $.trim($row.find(".activity_forenotice").val());

		if (begin_time == "" || end_time == "" || content == "" || pre_content == "") {
			messageCue("公告时间、内容不能为空！");
			return false;
		}

		swal({
				title: "确定要发布吗",
				//text: "删除后将无法恢复，请谨慎操作！",
				//type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#337ab7",
				confirmButtonText: "确定",
				closeOnConfirm: true
			},
			function () {
				loadingInit();//

				var obj = new Object();
				obj.begin_time = new Date(begin_time).getTime();
				obj.end_time = new Date(end_time).getTime();
				obj.content = content;
				obj.pre_content = pre_content;

				aryaPostRequest(
					urlGroup.fk_notice_save_by_modify,
					obj,
					function (data) {
						//alert(JSON.stringify(data));

						if (data.code == RESPONSE_OK_CODE) {
							toastr.success("发布成功！");
						}
						else {
							messageCue(data.msg);
						}

					},
					function (error) {
						messageCue(error);
					});
			});


	}


};

$(function () {
	fk_notice_manage.Init();
});