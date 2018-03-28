/**
 * Created by CuiMengxin on 2015/11/3.
 */

var DATATABLES_CHINESE_LANGUAGE = "json/Chinese.json";
var RESPONSE_OK_CODE = "1000";
var PERMISSION_DENIED_CODE = "2003";
var SESSION_TIMEOUT_CODE = "2004";
var ALL_COUNTRY = "100000";
var phone_reg = /^(1)[0-9]{10}$/;//手机正则校验

var ajaxSetup = function () {
    $.ajaxSetup({
        accept: 'application/json',
        cache: false,
        contentType: 'application/json;charset=UTF-8'
    });
};

/*
 *POST方法
 *Parmas:url请求路径，params参数，successFunc请求成功回调方法，errorFunc请求失败回调方法
 */
var aryaPostRequest = function (url, params, successFunc, errorFunc) {
    ajaxSetup();
    $.ajax({
        url: url,
        method: 'POST',
        data: JSON.stringify(params),
        success: function (data, status, jqXHR) {

            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            if (data.code === ERR_CODE_VALIDATION) {
                var errors = data.result;
                var errStr = data.msg;
                if (errors) {
                    for (var i = 0; i < errors.length; i++) {
                        var err = errors[i];
                        errStr += "<br/>" + getValidationTargetNameByKey(err.key) + "-" + err.msg;
                    }
                    toastr.error(errStr);
                }
            }
            else if (data["code"] === PERMISSION_DENIED_CODE) {
                toastr.error("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc)
                    successFunc(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrow) {

            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            var flag = ajax_msg(XMLHttpRequest);//判断ajax 异常错误

            if (flag) {
                //toastr.error("请求异常");
                if (errorFunc)
                    errorFunc();
            }

        }
    });
};

/*
 *GET方法
 *Parmas:url请求路径，successFunc请求成功回调方法，errorFunc请求失败回调方法
 */
var aryaGetRequest = function (url, successFunc, errorFunc) {
    ajaxSetup();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data, status, jqXHR) {

            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);
            if (data.code === ERR_CODE_VALIDATION) {
                var errors = data.result;
                var errStr = data.msg;
                if (errors) {
                    for (var i = 0; i < errors.length; i++) {
                        var err = errors[i];
                        errStr += "<br/>" + getValidationTargetNameByKey(err.key) + "-" + err.msg;
                    }
                    toastr.error(errStr);
                }
            }
            else if (data["code"] === PERMISSION_DENIED_CODE) {
                toastr.error("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc) {
                    successFunc(data);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrow) {

            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            // toastr.error("请求异常");
            // if (errorFunc)
            //     errorFunc(XMLHttpRequest);


            var flag = ajax_msg(XMLHttpRequest);//判断ajax 异常错误

            if (flag) {
                if (errorFunc)
                    errorFunc();
            }
        }
    });
};

var aryaGetRequest_new = function (url, successFunc, errorFunc) {

    new Promise(function (resolve, reject) {

        ajaxSetup();
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data, status, jqXHR) {
                //console.info(status);
                //console.info(jqXHR);

                setTimeout(function () {
                    loadingRemove();//加载中 - 移除logo
                }, 500);

                resolve(data);

            },
            error: function (XMLHttpRequest, textStatus, errorThrow) {

                setTimeout(function () {
                    loadingRemove();//加载中 - 移除logo
                }, 500);

                reject(XMLHttpRequest, textStatus, errorThrow);

            }
        });

    })
        .then(function (data) {

            if (data["code"] === PERMISSION_DENIED_CODE) {
                toastr.error("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc) {
                    successFunc(data);
                }
            }


        })
        .catch(function (XMLHttpRequest, textStatus, errorThrow) {

            var flag = ajax_msg(XMLHttpRequest);//判断ajax 异常错误

            if (flag) {
                //toastr.error("请求异常");
                if (errorFunc)
                    errorFunc();
            }

        });

};

/**
 * PUT方法 - 全部更新
 * Parmas:
 * url请求路径，
 * successFunc请求成功回调方法，
 * errorFunc请求失败回调方法
 */
var aryaPutRequest = function (url, params, successFunc, errorFunc) {

    ajaxSetup();
    $.ajax({
        url: url,
        method: 'PUT',
        data: JSON.stringify(params),
        success: function (data, status, jqXHR) {
            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            if (jqXHR.readyState === 0 && jqXHR.status === 0) {	//
                console.log("请求未发送！");
            }

            if (typeof data === "string") {
                data = eval("(" + data + ")");
            }

            if (data["code"] === PERMISSION_DENIED_CODE) {
                alert("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc) {
                    successFunc(data);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrow) {
            loadingRemove();//加载中 - 移除logo

            if (XMLHttpRequest.readyState == 0 && XMLHttpRequest.status == 0) {
                toastr.error("网络请求异常！");
            }
            else {
                errorFunc(XMLHttpRequest);
            }
        }
    });

};

/**
 * PATCH方法 - 局部更新
 * Parmas:
 * url请求路径，
 * successFunc请求成功回调方法，
 * errorFunc请求失败回调方法
 */
var aryaPatchRequest = function (url, params, successFunc, errorFunc) {

    ajaxSetup();
    $.ajax({
        url: url,
        method: 'PATCH',
        data: JSON.stringify(params),
        success: function (data, status, jqXHR) {
            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            if (jqXHR.readyState === 0 && jqXHR.status === 0) {	//
                console.log("请求未发送！");
            }

            if (typeof data === "string") {
                data = eval("(" + data + ")");
            }

            if (data["code"] === PERMISSION_DENIED_CODE) {
                alert("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc) {
                    successFunc(data);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrow) {
            loadingRemove();//加载中 - 移除logo

            if (XMLHttpRequest.readyState == 0 && XMLHttpRequest.status == 0) {
                toastr.error("网络请求异常！");
            }
            else {
                errorFunc(XMLHttpRequest);
            }
        }
    });

};

/**
 * DELETE方法
 * Parmas:
 * url请求路径，
 * successFunc请求成功回调方法，
 * errorFunc请求失败回调方法
 */
var aryaDeleteRequest = function (url, params, successFunc, errorFunc) {

    ajaxSetup();
    $.ajax({
        url: url,
        method: 'DELETE',
        data: JSON.stringify(params),
        success: function (data, status, jqXHR) {
            setTimeout(function () {
                loadingRemove();//加载中 - 移除logo
            }, 500);

            if (jqXHR.readyState === 0 && jqXHR.status === 0) {	//
                console.log("请求未发送！");
            }

            if (typeof data === "string") {
                data = eval("(" + data + ")");
            }

            if (data["code"] === PERMISSION_DENIED_CODE) {
                alert("接口没有访问权限");
            }
            else if (data["code"] === SESSION_TIMEOUT_CODE) {
                location.href = "login";
            }
            else {
                if (successFunc) {
                    successFunc(data);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrow) {
            loadingRemove();//加载中 - 移除logo

            if (XMLHttpRequest.readyState == 0 && XMLHttpRequest.status == 0) {
                toastr.error("网络请求异常！");
            }
            else {
                errorFunc(XMLHttpRequest);
            }
        }
    });

};

//判断ajax 异常错误
var ajax_msg = function (req) {
    console.info("请求失败：状态码 ---- " + req.status);
    console.log(req);

    var status = req.status;
    var msg = "";

    switch (status) {
        case 200:
            msg = "";
            break;
        case 404:
            msg = "接口无法请求(url不对)";
            break;
        case 500:
            msg = "系统错误";
            break;
        default:
            msg = "接口请求失败";//
            break;
    }

    if (msg) {
        toastr.warning(msg + "(" + req.status + ")");
        return false;
    }
    else {
        return true;
    }

};

//json格式转换为字符串
var jsonParseParam = function (param, key) {
    var paramStr = "";

    if (param instanceof String || param instanceof Number || param instanceof Boolean) {
        paramStr += "&" + key + "=" + encodeURIComponent(param);
    }
    else {
        $.each(param, function (i) {
            var k = key == null ? i : key + (param instanceof Array ? "[" + i + "]" : "." + i);
            paramStr += '&' + jsonParseParam(this, k);
        });
    }

    return paramStr.substr(1);


};

//加载中 - logo出现
var loadingInit = function () {
    if ($("body").find(".load-container").length > 0) {
        $("body").find(".load-container").remove();
    }
    var $load = $("<div>").addClass("load-container").addClass("load");
    var $loader = $("<div>").addClass("loader");
    $loader.appendTo($load);
    $load.appendTo($("body"));

};
//加载中 - 移除logo
var loadingRemove = function () {
    $("body").find(".load-container").remove();
};

//页面加载动画
var pageLoadHUD = $("<div></div>");
pageLoadHUD.addClass("sk-spinner sk-spinner-wave");
pageLoadHUD.attr("style", "margin-top:200px");
for (var i = 1; i < 6; i++) {
    var div = $("<div></div>").appendTo(pageLoadHUD);
    div.addClass("sk-rect" + i);
    pageLoadHUD.append("&nbsp;");
}

//显示页面加载动画
var showPageLoadHUD = function (divId) {
    if (divId) {
        $(divId).html(pageLoadHUD);
    }
};

//组件加载动画
var componentLoadHUD = $("<div></div>");
componentLoadHUD.addClass("sk-spinner sk-spinner-circle");
for (var i = 1; i <= 12; i++) {
    var div = $("<div></div>").appendTo(componentLoadHUD);
    div.addClass("sk-circle" + i + " sk-circle");
}

//隐藏组件加载动画
var dismissHUD = function (divId) {
    if (divId)
        $(divId).empty();
};

//显示组件加载动画
var showHUD = function (divId) {
    if (divId) {
        dismissHUD(divId);
        $(divId).append(componentLoadHUD);
    }
};


/**
 * 清除表单中内容
 * @param ele
 */
function clearForm(ele) {
    $(ele).find(':input').each(function () {
        switch (this.type) {
            case 'text':
                $(this).val('');
                break;
            case 'hidden':
                $(this).val('');
                break;
            case 'password':
                $(this).val('');
                break;
            case 'textarea':
                $(this).val('');
                break;
            case 'radio':
                this.checked = false;
            case 'checkbox':
                this.checked = false;
            case 'select-multiple':
            case 'select-one':
        }
    });
}

//将 时间戳转换格式 为 YYYY-MM-DD
var timeInit = function (time) {
    if (time == 0 || !time) {
        return "";
    }
    //debugger
    time = new Date(time).toLocaleDateString();//转换为 YYYY/MM/DD

    time = time.split("/");
    var timeList = "";
    for (var i = 0; i < time.length; i++) {
        if (parseInt(time[i]) < 10) {
            time[i] = "0" + time[i];
        }
        timeList += timeList == "" ? time[i] : ("-" + time[i]);
    }

    //alert(timeList)
    return timeList;

};

//将 时间戳转换格式 为 YYYY-MM-DD HH:MM:SS
var timeInit1 = function (time) {
    var timeList = "";

    if (time == 0 || time == "" || time == null) {

    }
    else {
        time = parseInt(time);
        //转换为 YYYY/MM/DD HH:MM:SS
        var data = new Date(time);

        var year = data.getFullYear();
        var month = data.getMonth();
        month += 1;
        month = month < 10 ? "0" + month : month;
        var day = data.getDate();
        day = day < 10 ? "0" + day : day;

        var hour = data.getHours();
        hour = hour < 10 ? "0" + hour : hour;
        var minute = data.getMinutes();
        minute = minute < 10 ? "0" + minute : minute;
        var second = data.getSeconds();
        second = second < 10 ? "0" + second : second;

        timeList = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    return timeList;

};

//将时间 转为 当天0时0分0秒 的时间戳
var timeInit3 = function (time) {

    if (time) {
        time = new Date(time);//.toLocaleString();//转换为 YYYY/MM/DD

        var year = time.getFullYear();
        var month = (time.getMonth() + 1) < 10 ? "0" + (time.getMonth() + 1) :
            (time.getMonth() + 1);
        var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();

        time = year + "/" + month + "/" + day + " 00:00:00";
        time = new Date(time).getTime();
    }
    else {
        time = "";
    }

    // console.log("时间：" + time);
    return time;

};

//将时间 转为 当天23时59分59秒 的时间戳
var timeInit4 = function (time) {

    if (time) {
        time = new Date(time);//.toLocaleString();//转换为 YYYY/MM/DD

        var year = time.getFullYear();
        var month = (time.getMonth() + 1) < 10 ? "0" + (time.getMonth() + 1) :
            (time.getMonth() + 1);
        var day = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();

        time = year + "/" + month + "/" + day + " 23:59:59";
        time = new Date(time).getTime();
    }
    else {
        time = "";
    }

    // console.log("时间：" + time);
    return time;

};

//显示提示信息
var messageCue = function (obj) {

    if (typeof obj === "undefined") {
        toastr.error("请求异常,请联系管理员");
    }
    else if (typeof obj === "string") {
        toastr.warning(obj);
    }
    else {
        if (obj.status) {
            if (obj.status === 500) {
                toastr.error("系统错误，请联系管理员！");
            }
            else if (obj.status === 302) {
                toastr.error("网络连接已断开！");
            }
            else if (obj.status === 404) {
                toastr.error("请求URL不存在！");
            }
            else {
                toastr.error("请求异常");
            }
        }
        else {
            toastr.error(JSON.stringify(obj))
        }
    }

};

//信息 弹框 提示
var msgShow = function (msg) {
    swal({
        title: msg,
        //text: "删除后将无法恢复，请谨慎操作！",
        //type: "warning",
        //showCancelButton: true,
        confirmButtonColor: "#337ab7",
        confirmButtonText: "确定",
        closeOnConfirm: true
    })
};

//操作 提示
var operateShow = function (name, func) {

    swal({
        title: name,
        //text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#337ab7",
        confirmButtonText: "确定",
        closeOnConfirm: true
    }, function () {
        if (func) {
            func();
        }
    });
};
//导出提示
var exportWarning = function (name, func) {
    if (name.indexOf("确定要导出") < 0) {
        name = "确定要导出  \"" + name + "\"  吗"
    }

    swal({
        title: name,
        //text: "删除后将无法恢复，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#337ab7",
        confirmButtonText: "确定",
        closeOnConfirm: true
    }, function () {
        if (func) {
            func();
        }
    });
};


/**
 * 获取验证时的 key 对应的 label 的信息
 * @param key
 */
function getValidationTargetNameByKey(key) {
    var elLabel = $('#' + key + "_label")[0];
    if (!elLabel) {
        label = $('#' + key).attr("placeholder");
        if (label) {
            return label;
        }
        else {
            return "N/A";
        }
    }
    else {
        var label = elLabel.textContent;
        if (label) {
            return label;
        }
        else {
            return "N/A";
        }
    }

}

//删除提示
var delWarning = function (name, func, text) {
    //if (name.indexOf("确定要删除") < 0) {
    //	name = "确定要删除  \"" + name + "\"  吗"
    //}

    if (!text) {
        text = "删除后将无法恢复，请谨慎操作！";
    }

    swal({
        title: name,
        text: text,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "删除",
        closeOnConfirm: true
    }, function () {
        if (func) {
            func();
        }
    });
};

//公司 服务类型
var corp_service_type = {
    soin: 1,
    salary: 2,
    entry: 4,
    fk: 8,
    attendance: 16,
    contract: 32
};
