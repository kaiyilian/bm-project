package com.bumu.arya.salary.controller;

import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
//import com.bumu.arya.salary.service.CalculateEngineDemoService;
import com.bumu.arya.salary.service.SalaryConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


/**
 * Created by allen on 15/10/10.
 */
@Api(value = "AdminController", tags = {"系统管理平台接口Admin"})
@Controller
public class AdminController {

    Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SalaryConfigService salaryConfigService;

    /**
     * 管理后台首页
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(ModelMap map) {String loginName = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isBlank(loginName)) {
            return "main";
        }
        SysUserEntity sysUserEntity = sysUserDao.findByLoginName(loginName);
        map.put("login_name", loginName);
        if (sysUserEntity != null) {
            if (StringUtils.isNotBlank(sysUserEntity.getRoleNames()) && sysUserEntity.getRoleNames().length() > 1) {
                map.put("role_name", sysUserEntity.getRoleNames().charAt(0) == ',' ? sysUserEntity.getRoleNames().substring(1) : sysUserEntity.getRoleNames());
            } else {
                map.put("role_name", sysUserEntity.getRoleNames());
            }
        }
        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map) {
        return main(map);
    }

    @ApiOperation(value = "退出")
    @RequestMapping(value = "signout", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<String> signout() {
        String url = salaryConfigService.getSsoSignoutUrl();
        log.info("注销地址：" + url);
        return new HttpResponse(ErrorCode.CODE_OK, url);
    }


    @ApiOperation(value = "带有异常的页面", tags = {"带有异常的页面"})
    @RequestMapping(value = "/salary/protected/page/with/exception", method = RequestMethod.GET)
    public @ResponseBody
    HttpResponse<Void>
    protectedPageWithException(@RequestParam("ret_exp") String returnExp) {
        log.debug("进入受保护的内容（异常）");
        if ("yes".equals(returnExp)) {
            return new HttpResponse<>(ErrorCode.CODE_SYS_ERR);
        }
        return new HttpResponse<>();
    }

    /**
     * 非保护页面，一般是登录页
     *
     * @return
     */
    @RequestMapping(value = "/unsafe/page", method = RequestMethod.GET)
    public String unsafePage() {
        return "unsafe";
    }

    /**
     * 权限失败页面
     *
     * @return
     */
    @RequestMapping(value = "/permission_fail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> permissionFail() {
        return new HttpResponse<>(ErrorCode.CODE_NO_PERMISSION, "当前用户无该操作权限");
    }


    @ApiOperation(value = "受保护页面", tags = {"受保护页面"})
    @RequestMapping(value = "/salary/protected/page", method = RequestMethod.GET)
    public @ResponseBody
    HttpResponse<Void>
    protectedPage() {
        log.debug("进入受保护的内容");
        return new HttpResponse<>();
    }

    @RequestMapping(value = "/salary/protected/page/free", method = RequestMethod.GET)
    public String protectedPageFree() {
        log.debug("进入受保护的自由内容");
        return "protected_page";
    }

//    @Autowired
//    CalculateEngineDemoService calculateEngineDemoService;
//
//    @RequestMapping(value = "/calc_engine", method = RequestMethod.GET)
//    public @ResponseBody HttpResponse<Void> calculateEngineTest() {
//        calculateEngineDemoService.doit();
//        return new HttpResponse<>();
//    }
}
