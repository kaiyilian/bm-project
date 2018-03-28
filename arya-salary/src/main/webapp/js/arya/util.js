/**
 * Created by CuiMengxin on 2015/11/19.
 */

/**
 * unix时间格式化
 * @param unixTime
 * @param defaultLabel
 * @returns {*}
 */
var formatUnixTime = function (unixTime, defaultLabel) {
	if (unixTime === 0) {
		if (defaultLabel) {
			return defaultLabel;
		}
		else {
			console.log(unixTime);
			return '00-00-00 00:00:00';
		}
	}
	else {
		var date = new Date(unixTime);
		return date.getFullYear() + '-' +
			(date.getMonth() + 1 < 10 ?'0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-' +
			(date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ' +
			(date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':' +
			(date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':' +
			(date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
	}
};

/**
 * 最后登录时间格式化
 * @param unixTime
 * @returns {*}
 */
var formatLastLoginTime = function(unixTime) {
	return formatUnixTime(unixTime, '从未登录');
};

var formatYesNo = function(val) {
	if (val == 0) {
		return "否";
	}
	else if (val == 1) {
		return "是";
	}
};

var formatSysUserStatus = function(status) {
	if (status == 1) {
		return "正常";
	}
	else if (status == 2) {
		return "已冻结";
	}
};


//吐司动画
toastr.options = {
	"closeButton": true,
	"debug": false,
	"progressBar": false,
	"positionClass": "toast-bottom-right",
	"onclick": null,
	"showDuration": "300",
	"hideDuration": "1000",
	"timeOut": "2000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
};