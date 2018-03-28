package com.bumu.arya.admin.system.controller;

import com.bumu.arya.admin.command.LoginCommand;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.response.SigninResultResponse;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.admin.system.result.SimpleUrlResult;
import com.bumu.arya.admin.system.result.SimpleUrlResultResponse;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统管理平台基础接口，不受 Shiro 权限控制管理
 * Created by allen on 15/10/10.
 */
@Api(tags = {"系统管理平台接口Admin"})
@Controller
public class AdminController {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    Producer captchaProducer;

    /**
     * 默认页面
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultPage() {
        return "login";
    }

    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * 重新登录页面
     *
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/relogin", method = RequestMethod.GET)
    public String reloginPage() {
        return "login";
    }

    /**
     * 访问受限时跳转至此
     *
     * @return
     */
    @RequestMapping(value = "/permission_fail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<Void> permissionFail() {

        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setCode(ErrorCode.CODE_NO_PERMISSION);

        return httpResponse;
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @ApiOperation(value = "系统用户登录")
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public
    @ResponseBody
    SigninResultResponse signin(@ApiParam @RequestBody LoginCommand command, HttpServletRequest request) {
        return sysUserService.login(command, request);
    }

    /**
     * 获取用户的验证码图片URL，刷新验证码
     * 如果用户尝试登录次数超过三次 则返回验证码url 否则返回null
     *
     * @param account
     * @param request
     * @return
     */
    @ApiOperation(value = "验证码接口")
    @RequestMapping(value = "/captcha/user", method = RequestMethod.GET)
    public
    @ResponseBody
    SimpleUrlResultResponse getCaptchaImageUrlArya(@ApiParam @RequestParam("account") String account, HttpServletRequest request) {
        SimpleUrlResult result = new SimpleUrlResult();
        result.setUrl(sysUserService.getUserCaptchaURL(account, request.getRequestedSessionId()));
        return new SimpleUrlResultResponse(result);
    }

    /**
     * 获取验证码图片
     *
     * @param capId
     * @param response
     */
    @ApiOperation(value = "获取验证码图片")
    @RequestMapping(value = "/captcha/user/image", method = RequestMethod.GET)
    public
    @ResponseBody
    void getCaptchaImageArya(@RequestParam("cap_id") String capId, HttpServletResponse response) {
        try {
            if (StringUtils.isNotBlank(capId)) {
                ImageIO.write(captchaProducer.createImage(sysUserService.getCaptchaText(capId)), "jpg", response.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改退出登录
     *
     * @return
     */
    @ApiOperation(value = "修改退出登录")
    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> signout() {

        Subject currentUser = SecurityUtils.getSubject();

        System.out.println("退出登录" + currentUser.getPrincipal());

        currentUser.logout();

        return new HttpResponse<Void>();
    }

    /**
     * 管理后台首页
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "管理后台首页")
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(ModelMap map) {
        String loginName = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(loginName)) {
            return "login";
        }
        else {
            SysUserEntity sysUserEntity = sysUserDao.findByLoginName(loginName);
            map.put("login_name", loginName);
            if (sysUserEntity != null) {
                if (StringUtils.isNotBlank(sysUserEntity.getRoleNames()) && sysUserEntity.getRoleNames().length() > 1) {
                    map.put("role_name", sysUserEntity.getRoleNames().charAt(0) == ',' ? sysUserEntity.getRoleNames().substring(1) : sysUserEntity.getRoleNames());
                }
                else {
                    map.put("role_name", sysUserEntity.getRoleNames());
                }
            }
            return "main";
        }
    }

}
