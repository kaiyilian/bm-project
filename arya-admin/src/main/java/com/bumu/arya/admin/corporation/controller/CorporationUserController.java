package com.bumu.arya.admin.corporation.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.arya.admin.corporation.controller.command.*;
import com.bumu.arya.admin.corporation.result.CorpUserListResult;
import com.bumu.arya.admin.corporation.result.CorpUserPermResult;
import com.bumu.arya.admin.corporation.service.CorpUserService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.model.CorpPermissionDao;
import com.bumu.arya.admin.corporation.controller.command.CorpPermissionToUserCommand;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业用户相关的接口
 * Created by allen on 16/5/13.
 */
@Api(tags = "企业权限接口, 字典 selected 0:未选中 1:选中 2:半选")
@Controller
public class CorporationUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CorporationUserController.class);

    @Autowired
    private CorpUserService corpUserService;

    @Autowired
    private CorpPermissionDao corpPermissionDao;


    /**
     * 用户管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "admin/corporation/corp_user_permission", method = RequestMethod.GET)
    public String corporationUserPermissionPage(ModelMap map) {
        return "corporation/corp_user_manage";
    }

    /**
     * 企业用户列表
     *
     * @return 返回企业用户信息，包括企业的信息
     */
    @RequestMapping(value = "admin/corporation/user/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<CorpUserListResult> corporationUserList(CorpUserListCommand command) {
        try {
            if (StringUtils.isAnyBlank(command.getCorp_id())) {
                throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
            }
            return new HttpResponse<>(corpUserService.getCorporationUserList(command.getCorp_id(), command.getPage(), command.getPage_size()));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    @ApiOperation(httpMethod = "GET", value = "获取权限")
    @RequestMapping(value = "admin/corporation/permission/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CorpUserPermResult>> corporationPermissionList(
            @ApiParam(value = "企业用户id") @RequestParam(value = "corp_user_id") String branCorpUserId)
            throws Exception {
        HttpResponse<List<CorpUserPermResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, corpUserService.findCorpPermsWithCorpUser(branCorpUserId));
        log.info("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(httpMethod = "POST", value = "保存权限")
    @RequestMapping(value = "admin/corporation/user/permission/adjust", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> adjustPermissionToCorpUser(@ApiParam @RequestBody AdjustCorpUserPermCommand command) {
        corpUserService.adjustCorpPermToCorpUser(command.getUserId(), command.getPerms());
        return new HttpResponse<>();
    }

    /**
     * 企业用户已有权限列表
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/permission/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<List<CorpUserPermResult>> corporationUserPermissionList(GetCorpUserPermsCommand command) throws Exception {
//        CorpUserPermListResult result = new CorpUserPermListResult();
//        String corpUserId = command.getCorp_user_id();
//
//        if (StringUtils.isBlank(corpUserId)) {
//            return new HttpResponse<>(result);
//        }
//
//
//        List<CorpUserPermResult> corpPermsWithCorpUser = corpUserService.findCorpPermsWithCorpUser(corpUserId);
//        if (corpPermsWithCorpUser == null) {
//            return new HttpResponse<>(result);
//        }
//        result.setPages(Utils.calculatePages(corpPermsWithCorpUser.size(), command.getPage_size()));
////        result.setPermissions(corpPermsWithCorpUser);
//        return new HttpResponse<>(ErrorCode.CODE_OK,corpUserId);
        return null;
    }

    /**
     * 分配权限给企业用户（默认角色）
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/permission/assign", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse assignPermissionToCorpUser(@RequestBody CorpPermissionToUserCommand command) {
        try {
            corpUserService.assignCorpPermToCorpUser(command.getCorpUserId(), command.getCorpPermissionId());
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR);
        }

        return new HttpResponse();
    }

    /**
     * 分配所有权限给企业用户（默认角色）
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/permission/assign_all", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse assignAllPermissionToCorpUser(@RequestBody CorpPermissionToUserCommand command) {
        try {
            corpUserService.assignAllCorpPermToCorpUser(command.getCorpUserId());
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR);
        }

        return new HttpResponse();
    }

    /**
     * 移除企业用户的权限
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/permission/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse removePermissionFromCorpUser(@RequestBody CorpPermissionToUserCommand command) {
        try {
            corpUserService.removeCorpPermFromCorpUser(command.getCorpUserId(), command.getCorpPermissionId());
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR);
        }
        return new HttpResponse();
    }


    /**
     * 移除企业用户的权限
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/permission/remove_all", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse removeAllPermissionFromCorpUser(@RequestBody CorpPermissionToUserCommand command) {
        try {
            corpUserService.removeAllCorpPermFromCorpUser(command.getCorpUserId());
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR);
        }

        return new HttpResponse();
    }

    /**
     * 清空尝试登录次数
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/try_login_times/rest", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse restTryLoginTimes(@RequestBody RestTryLoginTimesCommand command) {
        try {
            corpUserService.restUserTryLoginTimes(command.getId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除企业用户
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "admin/corporation/user/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteCorpUser(@RequestBody IdsCommand command) {
        try {
            corpUserService.deleteCorpUser(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }
}
