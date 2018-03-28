/**
 * 系统用户管理类
 * @constructor
 */
function SysUserManager() {
	// 文档中的ID定义，注意不能与其他js冲突
	this.DLG_ID = 'dlg_sys_user';
	this.DLG_ROLE_USER_LABEL_ID = 'dlg_sys_user_label';
	this.DLG_ROLE_PERM_LABEL_ID = 'dlg_sys_role_permission_label';
	this.TB_ID = 'sys_user_list';
	this.FORM_ID = 'sys_user_form';
	this.ENTITY_ID = 'uid';

	// URL定义
	//this.LIST_URL = 'admin/sys/user/list';
	//this.CREATE_URL = 'admin/sys/user/create';
	//this.UPDATE_URL = 'admin/sys/user/update';
	//this.REMOVE_URL = 'admin/sys/user/delete';
	//this.FREEZE_URL = 'admin/sys/user/freeze';
	//this.UNFREEZE_URL = 'admin/sys/user/unfreeze';

	// 全局DOM对象
	this.table; // JQuery Datatables
	this.dataTable;
	this.dialog; // JQuery Modal Dialog
}

/**
 * 初始化编辑表单
 * @param rowData 要编辑的数据
 */
SysUserManager.prototype.fnInitEditForm = function (rowData) {
	console.log(rowData);
	$('#uid').val(rowData.id);
	$('#login_name').val(rowData.loginName);
	$('#real_name').val(rowData.realName);
	$('#nick_name').val(rowData.nickName);
	$('#email').val(rowData.email);
	$('#label_err_msg').css('display', 'none');
	this.dialog.modal('show');
};

/**
 * 生成状态变化相关的对话框标题
 * @param actionName 动作名称
 * @param rowData
 * @returns {*}
 */
SysUserManager.prototype.fnTitleFormatter = function (actionName, rowData) {
	if (rowData) {
		return '确定要' + actionName + rowData.loginName + '吗?'
	}
	else {
		return '';
	}
};

/**
 * 提交创建新对象或者更新对象的表单
 */
SysUserManager.prototype.submitCreateEditForm = function () {
	loadingInit();

	var uid = $('#' + this.ENTITY_ID).val();
	var loginName = $('#login_name').val();
	var loginPwd = $('#login_pwd').val();
	var realName = $('#real_name').val();
	var email = $('#email').val();

	var params = {};
	var url;

	if (uid) {
		console.log('编辑');
		url = urlGroup.sys_user_modify;
		params.uid = uid;
	}
	else {
		console.log('新建');
		url = urlGroup.sys_user_add;
	}

	params.login_name = loginName;
	if (loginPwd) {
		params.login_pwd = loginPwd;
	}
	params.real_name = realName;
	params.email = email;

	console.log(JSON.stringify(params));

	var table = this.table;
	var dialog = this.dialog;
	aryaPostRequest(url, params, function (data) {
		if (data.code == ERR_CODE_OK) {
			table.ajax.reload();
			dialog.modal('hide');
			toastr.success("操作成功");
		}
		else if (data.code == ERR_CODE_VALIDATION) {
			console.log("验证错误：");
			console.log(data.result);
			var errs = '\r';
			for (var i = 0; i < data.result.length; i++) {
				var e = data.result[i];
				console.log(e);
				console.log(e.key + ' - ' + e.msg);
				// console.log($('#' + e.key));
				var target = $('#' + e.key).attr("placeholder");
				errs += '<br/>' + target + ' - ' + e.msg;
			}
			$('#label_err_msg').css('display', 'block');
			$('#label_err_msg').html(errs);
			// swal(data.code, errs, 'error');
		}
		else {
			swal(data.code, data.msg, 'error');
		}
	});
};

/**
 * 提交删除系统用户
 * @param uid
 */
SysUserManager.prototype.submitDeleteSysUser = function (uid) {
	var params = {
		uid: uid
	};
	var table = this.table;
	submitChanges(
		params,
		urlGroup.sys_user_del,
		'账号已被删除.',
		function () {
			console.log(table.row('.selected').data);
			table.row('.selected').remove();
			table.ajax.reload();
		}
	);
};

/**
 * 提交冻结系统用户
 * @param uid
 * @params status 1解冻, 2冻结
 */
SysUserManager.prototype.submitFreezeSysUser = function (uid, status) {
	var params = {
		uid: uid,
		status: status
	};
	var txt = status == 1 ? "账号解冻成功" : "账号已被冻结";
	submitChanges(params, urlGroup.sys_user_freeze, txt, function () {
		//console.log('账号冻结成功');
	});
};

// 此处命名注意要避免和其他js冲突
var sysUserManager = new SysUserManager();

$(document).ready(function () {
	sysUserManager.dialog = $('#' + sysUserManager.DLG_ID);
	//sysUserManager.table = $('#' + sysUserManager.TB_ID).DataTable;
	sysUserManager.table = $('#' + sysUserManager.TB_ID).DataTable({
		'processing': true,
		'serverSide': true,
		'showRowNumber': true,
		'bStateSave': true,
		'searching': false,
		'ordering': false,
		"language": {
			"url": DATATABLES_CHINESE_LANGUAGE
		},
		'ajax': {
			'url': urlGroup.sys_user_list
		},
		'aoColumns': [
			{
				"sTitle": "登录名",
				"mData": "loginName"
			},
			{
				"sTitle": "真实姓名",
				"mData": 'realName'
			},
			{
				"sTitle": "电子邮件",
				"mData": 'email'
			},
			{
				"sTitle": "状态",
				"mData": 'status',
				"mRender": formatSysUserStatus
			},
			{
				"sTitle": "是否激活",
				"mData": 'isActive',
				"mRender": formatYesNo
			},
			{
				"sTitle": "创建时间",
				"mData": 'createTime',
				"mRender": formatUnixTime
			},
			{
				"sTitle": "最后登录时间",
				"mData": 'lastLoginTime',
				"mRender": formatLastLoginTime
			}
		],
		'rowCallback': function (row, data) {
			//console.log(data);
		}
	});
	enableSingleSelection(sysUserManager.TB_ID);
});
