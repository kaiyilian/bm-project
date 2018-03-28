package com.bumu.bran.admin.login.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.result.CorpUserInfoResult;
import com.bumu.bran.admin.login.controller.command.ChangePasswordCommand;
import com.bumu.bran.admin.login.result.GetCaptchaResult;
import com.bumu.bran.admin.login.service.BranCorpUserService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author CuiMengxin
 * @date 2016/5/10
 */
@Api(tags = {"企业用户管理CorpUser"})
@Controller
public class UserController {

    @Autowired
    BranCorpUserService corpUserService;

    @Autowired
    Producer captchaProducer;

    /**
     * 获取用户的验证码图片URL，刷新验证码
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse getCaptchaImageUrl(@RequestParam("account") String account, HttpServletRequest request)
            throws Exception {
        GetCaptchaResult result = new GetCaptchaResult();
        result.setUrl(corpUserService.getUserCaptchaURL(account, request.getRequestedSessionId()));
        return new HttpResponse(result);
    }

    /**
     * 获取验证码图片
     *
     * @param capId
     * @param response
     */
    @RequestMapping(value = "/captcha/image", method = RequestMethod.GET)
    @ResponseBody
    public void getCaptchaImage(@RequestParam("cap_id") String capId, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotBlank(capId)) {
            ImageIO.write(captchaProducer.createImage(corpUserService.getCaptchaText(capId)), "jpg",
                    response.getOutputStream());
        }
    }

    /**
     * 获取企业用户个人信息
     *
     * @return
     */
    @ApiOperation(value = "获取企业用户个人信息")
    @RequestMapping(value = {"/admin/user/info/detail", "/admin/home/user/info/detail"}, method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<CorpUserInfoResult> getCorpUserInfo() throws Exception {
        Session session = SecurityUtils.getSubject().getSession();
        String currentUserId = session.getAttribute("user_id").toString();
        Long lastLoginTime = null;
        String lastLoginIp = null;
        if (session.getAttribute("last_login_time") != null) {
            lastLoginTime = Long.parseLong(session.getAttribute("last_login_time").toString());
        }

        if (session.getAttribute("last_login_ip") != null) {
            lastLoginIp = session.getAttribute("last_login_ip").toString();
        }
        return new HttpResponse(corpUserService.getCorpUserInfo(currentUserId, lastLoginTime, lastLoginIp));
    }

    /**
     * 修改密码
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/user/pwd/change", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse changePwd(@RequestBody ChangePasswordCommand command) throws Exception {
        corpUserService.changePassword(command);
        return new HttpResponse();
    }
}
