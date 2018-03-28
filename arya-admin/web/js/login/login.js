/**
 * @deprecated 转为SSO
 * Created by CuiMengxin on 2016/1/11.
 */
var COOKIE_SAVE_USER = 'bumu_save_user';
var COOKIE_USER_NAME = 'bumu_user_name';
var COOKIE_USER_PWD = 'bumu_user_pwd';

/**
 * 登录请求
 */
var submitSignin = function () {
	var userName = $('#userName').val();
	var password = $('#password').val();
	var code = $('.captcha_container input').val();
	if (!userName) {
		toastr.warning("用户名不能为空");
		return;
	}
	if (!password ) {
		toastr.warning("密码不能为空");
		return;
	}
	if (!$('.captcha_container').is(":hidden") && !code) {
		toastr.warning("请输入验证码！");
		return
	}

	var obj = {
		"login_name": userName,
		"pwd": password,
		"captcha": code
	};

	aryaPostRequest(
		urlGroup.login_url,
		obj,
		function (data) {
			//console.log("获取日志：");
			//console.log(data);

			if (data.code === ERR_CODE_OK) {
				$.cookie(COOKIE_USER_NAME, null);
				$.cookie(COOKIE_USER_PWD, null);

				window.location.href = 'main';
			}
			else {

				if (data.result) {

					var try_login_times = data.result.try_login_times;//
					if (try_login_times && try_login_times >= 3) {
						$(".captcha_container").show();
						changeCaptcha();// 点击 更改验证码
					}

				}

				toastr.error(data.msg);
			}

		},
		function (error, status) {
			//var txt = "";
			//
			//if (status == 500) {
			//	txt = "后台错误";
			//}
			//else {
			//	txt = "系统错误";
			//}

			toastr.error(error);
		}
	);

	//ajaxSetup();
	//$.ajax({
	//	url: 'signin',
	//	method: 'POST',
	//	data: JSON.stringify(jsonParams),
	//	success: function (data, status, jqXHR) {
	//		console.log(data);
	//
	//		if (data.code == ERR_CODE_OK) {
	//			$.cookie(COOKIE_USER_NAME, null);
	//			$.cookie(COOKIE_USER_PWD, null);
	//			//if ($("#remeber_pwd").is(':checked')) {
	//			//	$.cookie(COOKIE_SAVE_USER, true, {expires: 7});
	//			//	$.cookie(COOKIE_USER_NAME, userName, {expires: 7});
	//			//	$.cookie(COOKIE_USER_PWD, password, {expires: 7});
	//			//} else {
	//			//	$.cookie(COOKIE_SAVE_USER, null);
	//			//	$.cookie(COOKIE_USER_PWD, null);
	//			//}
	//			window.location.href = 'main';
	//		}
	//		//else if (data.code == "2018") {		//需要验证码
	//		//	$(".captcha_container").show();
	//		//	changeCaptcha();
	//		//}
	//		//else if (data.code == ERR_CODE_VALIDATION) {
	//		//	toastr.error(data.result);
	//		//}
	//		else {
	//			if (data.result) {
	//
	//				var try_login_times = data.result.try_login_times;//? data.result.try_login_times : "";
	//				if (try_login_times && try_login_times >= 3) {
	//					$(".captcha_container").show();
	//					changeCaptcha();
	//				}
	//			}
	//
	//			toastr.error(data.msg);
	//		}
	//	},
	//	error: function (error, status) {
	//		//toastr.error(error.status);
	//		var txt = "";
	//
	//		if (status == 500) {
	//			txt = "后台错误";
	//		}
	//		else {
	//			txt = "系统错误";
	//		}
	//
	//		toastr.error(txt);
	//	}
	//});
};

/**
 * 登录结果
 * @param data
 */
var getSigninResult = function (data) {
	var result = data["result"]
	if (result) {
		if (result["isPassed"] == true)
			location.href = "main";
		else
			toastr.error("用户名或密码错误");
	}
	else {
		toastr.error("通信异常");
	}
};

// 点击 更改验证码
var changeCaptcha = function () {
	var user_name = $.trim($("#userName").val());
	if (!user_name) {
		return
	}

	var obj = {
		account: user_name
	};

	var url = urlGroup.captcha_url + "?" + jsonParseParam(obj);

	aryaGetRequest(
		url,
		function (data) {
			//alert(JSON.stringify(data))

			if (data.code == 1000) {
				var img_url = data.result.url;//

				if (img_url) {
					var img = $("<img />").attr("src", img_url);
					$(".captcha_container").show().find(".imgCode").html(img);
				}
				else {   //没有图片URL
					$(".captcha_container").hide();
				}
			}
			else {
				toastr.error(data.msg);
			}

		},
		function (error) {
			toastr.error(error);
		}
	);

};

$(document).ready(function () {
    //if ($.cookie(COOKIE_SAVE_USER) == 'true') {
    //	$("#remeber_pwd").attr("checked", 'true');
    //	$('#userName').val($.cookie(COOKIE_USER_NAME));
    //	$('#password').val($.cookie(COOKIE_USER_PWD));
    //	$('#password').attr('type','password');
    //}
    //else {
    //	$('#userName').val("");
    //	$('#password').val(null);
    //	$("#remeber_pwd").removeAttr("checked");
    //}
    document.onkeydown = function (e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode === 13) {
            $("#submitBtn").trigger('click');
        }
    };


    //获取浏览器版本
    var ver = BrowserType();
    if (ver !== "Chrome") {
        swal({
            title: "请使用Chrome浏览器",
            text: "目前仅支持Chrome浏览器",
            type: "warning",
            showCancelButton: false,
            confirmButtonColor: "#337ab7",
            confirmButtonText: "关闭",
            closeOnConfirm: true
        });
    }
});

//检查浏览器
function BrowserType() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器
    var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") === -1; //判断是否Safari浏览器
    var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器

    if (isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if (fIEVersion === 7) {
            return "IE7";
        }
        else if (fIEVersion == 8) {
            return "IE8";
        }
        else if (fIEVersion == 9) {
            return "IE9";
        }
        else if (fIEVersion == 10) {
            return "IE10";
        }
        else if (fIEVersion == 11) {
            return "IE11";
        }
        else {
            return "0"
        }//IE版本过低
    }//isIE end

    if (isFF) {
        return "FF";
    }
    if (isOpera) {
        return "Opera";
    }
    if (isSafari) {
        return "Safari";
    }
    if (isChrome) {
        return "Chrome";
    }
    if (isEdge) {
        return "Edge";
    }
}
