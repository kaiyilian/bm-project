package com.bumu.arya.admin.system.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.command.ChangePwdWithCaptchaCommand;
import com.bumu.arya.admin.misc.controller.InitBinderController;
import com.bumu.arya.admin.service.SysUserCommonService;
import com.bumu.arya.admin.command.ForgetPwdCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.annotation.ConvertValidateResult;
import com.bumu.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Allen 2018-01-17
 **/
@Api(tags = {"系统用户密码AdminPwd"})
@Controller
public class AdminPwdController extends InitBinderController {

    @Autowired
    SysUserCommonService sysUserCommonService;

    @ApiOperation(value = "忘记密码页面")
    @RequestMapping(value = "/forget_pwd.html", method = RequestMethod.GET)
    public String pageForgetPwd() {

        return "forget_pwd";
    }

    @ApiOperation(value = "找回密码")
    @RequestMapping(value = "/forget_pwd", method = RequestMethod.POST)
    @ConvertValidateResult
    public
    @ResponseBody
    HttpResponse<Void> forgetPwd(
            @ApiParam @RequestBody @Valid ForgetPwdCommand cmd,
            BindingResult bindingResult,
            HttpServletRequest request) {
        String captcha = Utils.makeUUID();
        String basePath = WebUtil.getBasePath(request);
        String url = basePath + "verify_change_pwd?captcha=" + captcha;
        sysUserCommonService.sendForgetPwdEmail(cmd.getEmail(), url, captcha);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "修改密码验证")
    @RequestMapping(value = "/verify_change_pwd", method = RequestMethod.GET)
    public String verifyChangePwd(
            ModelMap model,
            @ApiParam @RequestParam("captcha") String captcha) {
        if (!sysUserCommonService.verifyChangePwd(captcha)) {
            model.put("error", "修改密码已失效，请重新再试");
            return "pwd";
        }
        model.put("captcha", captcha);
        return "pwd";
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/change_pwd", method = RequestMethod.POST)
    @ConvertValidateResult
    public
    @ResponseBody
    HttpResponse<Void> changePwd(
            @ApiParam @RequestBody @Valid ChangePwdWithCaptchaCommand command,
            BindingResult bindingResult) {
        // 校验密码是否相同
        if (StringUtils.isAnyBlank(command.getNewPwd(), command.getNewPwdAgain())
                || !command.getNewPwd().equals(command.getNewPwdAgain())) {
            return new HttpResponse<>(ErrorCode.CODE_PWD_ERR);
        }
        sysUserCommonService.saveNewPwd(command.getCaptcha(), command.getNewPwd());

        sysUserCommonService.cleanForgetPwdSession(command.getCaptcha());
        return new HttpResponse<>();
    }

}
