package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.arya.admin.misc.controller.command.AryaUserListCommand;
import com.bumu.arya.admin.misc.controller.command.CreateAryaUserCommand;
import com.bumu.arya.admin.misc.result.UserEmpInfo;
import com.bumu.arya.admin.misc.result.UserInfoResult;
import com.bumu.arya.admin.misc.service.AryaUserService;
import com.bumu.arya.admin.operation.result.WalletUserInfoResult;
import com.bumu.arya.admin.operation.service.WalletCntService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.response.HttpResponse;
import com.bumu.payroll.result.UserPayrollResult;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by wyx on 2015/5/5.
 */
@Api(value = "AryaUserController", tags = {"系统管理平台用户信息查询"})
@Controller
public class AryaUserController extends BaseController {

    private static Logger logger = getLogger(AryaUserController.class);

    @Autowired
    AryaUserDao aryaUserDao;

    @Autowired
    AryaUserService userService;

    @Autowired
    private WalletCntService walletCntService;

    /**
     * 获取用户查询页面
     *
     * @return
     */
    @RequestMapping(value = "admin/user/query/index", method = RequestMethod.GET)
    public String getUserQueryPage() {
        return "user_manage/app_user_manage";
    }

    /**
     * 获取用户索引页面
     */
    @RequestMapping(value = "admin/user/index", method = RequestMethod.GET)
    public String soinInfoImport() {
        return "user/user_manager";
    }

    /**
     * 修改普通状态
     *
     * @return
     */
    @RequestMapping(value = "/admin/user/status", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateUserStatus() {
        return new HttpResponse();
    }

    /**
     * 创建普通用户
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse createUser(@RequestBody CreateAryaUserCommand command) {
        return new HttpResponse();
    }

    /**
     * 查询客户端用户
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse queryUser(AryaUserListCommand command) {
        try {
            return new HttpResponse(userService.getUserList(command));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    @ApiOperation(value = "个人信息查询")
    @RequestMapping(value = "/admin/user/info/app", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<UserInfoResult> getUserInfo(@ApiParam @RequestParam(value = "tel") String tel) throws Exception {
        return new HttpResponse<>(ErrorCode.CODE_OK, userService.getUserAppInfo(tel));
    }

    @ApiOperation(value = "电子工资单查询")
    @RequestMapping(value = "/admin/user/info/payroll", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<UserPayrollResult>> getUserPayrollInfo(@ApiParam @RequestParam(value = "tel") String tel) throws Exception {
        return new HttpResponse(ErrorCode.CODE_OK, userService.getUserPayrollInfo(tel));
    }

    @ApiOperation(value = "入职信息")
    @RequestMapping(value = "/admin/user/info/emp", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<UserEmpInfo> getUserEmpInfo(@ApiParam @RequestParam(value = "tel") String tel) throws Exception {
        HttpResponse<UserEmpInfo> httpResponse = new HttpResponse(ErrorCode.CODE_OK, userService.getUserEmpInfo(tel));
        logger.debug("HttpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "钱包用户信息查询")
    @RequestMapping(value = "/admin/user/info/wallet", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<WalletUserInfoResult> getUserWalletInfo(@ApiParam @RequestParam(value = "tel") String tel) throws Exception {
        HttpResponse<WalletUserInfoResult> httpResponse = new HttpResponse(ErrorCode.CODE_OK, walletCntService.userInfo(tel));
        logger.debug("HttpResponse: " + httpResponse.toJson());
        return httpResponse;
    }
}
