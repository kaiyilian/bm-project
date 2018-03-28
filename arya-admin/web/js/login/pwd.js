/**
 * 忘记密码
 */
var submitForgetPwd = function () {
	var email = $('#email').val();
	if (!email) {
		toastr.warning("请输入电子邮件地址");
		return;
	}

	var obj = {
		"email": email
	};
	console.log(obj);

	aryaPostRequest(
		urlGroup.forget_pwd_url,
		obj,
		function (data) {
			if (data.code === ERR_CODE_OK) {
				toastr.info("邮件已发送，请查收邮件并按照邮件提示修改密码");
				setTimeout(function () {
					location.href = "login";
				}, 3000);

			}
			else {
				toastr.error(data.msg);
			}
		},
		function (error, status) {
			toastr.error(error);
		}
	)
};


/**
 * 保存新密码（带验证码）
 */
var submitChangePwd = function () {
	var captcha = $('#captcha').val();
	var newPwd = $('#new_pwd').val();
	var newPwdAgain = $('#new_pwd_again').val();
	console.log(captcha);
	if (!captcha) {
		toastr.warning("已失效，无法修改密码");
		return;
	}

	var obj = {
		"captcha": captcha,
		"new_pwd": newPwd,
		"new_pwd_again": newPwdAgain
	};
	console.log(obj);

	aryaPostRequest(
		urlGroup.change_pwd_url,
		obj,
		function (data) {
			if (data.code === ERR_CODE_OK) {
				toastr.info("密码修改完成，请用新的密码登录");
				setTimeout(function () {
					location.href = "login";
				}, 3000);
			}
			else {
				toastr.error(data.msg);
			}
		},
		function (error, status) {
			toastr.error(error);
		}
	)
};