package com.bumu.bran.admin.login.controller;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.login.controller.command.SigninCommand;
import com.bumu.bran.admin.login.result.BranCorpUserLoginResult;
import com.bumu.bran.admin.login.service.BranCorpUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CuiMengxin
 * @date 2016/5/10
 */
@Api(value = "HomeController", tags = {"企业管理平台登录相关接口AdminHome"})
@Controller
public class HomeController {

	@Autowired
	BranCorpUserService corpUserService;

	@Autowired
	BranCorpService branCorpService;

	/**
	 * 登录接口
	 *
	 * @return
	 */
    @ApiOperation(value = "企业管理平台登录接口")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse<BranCorpUserLoginResult> signin(
	        @ApiParam @RequestBody SigninCommand command,
            HttpServletRequest request) throws Exception {
		command.setBrowserSessionId(request.getRequestedSessionId());
		command.setIp(SysUtils.getIpAddress(request));
		return new HttpResponse(corpUserService.login(command));
	}

	/**
	 * 退出登录接口
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/signout", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse signout() throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		System.out.println("退出登录" + currentUser.getPrincipal());
		currentUser.logout();
		return new HttpResponse();
	}

	/**
	 * 访问受限时跳转至此
	 *
	 * @return
	 */
	@RequestMapping(value = "/permission_fail", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse permissionFail() throws Exception {
		return new HttpResponse(ErrorCode.CODE_NO_PERMISSION);
	}

}
