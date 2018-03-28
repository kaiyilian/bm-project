package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.admin.corporation.controller.command.GetNotificationUsersCountCommand;
import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateNotificationCommand;
import com.bumu.arya.admin.misc.result.GetAllTagsResult;
import com.bumu.arya.admin.misc.result.NotificationDetailResult;
import com.bumu.arya.admin.misc.result.NotificationJumpTypeList;
import com.bumu.arya.admin.misc.result.NotificationListResult;
import com.bumu.arya.admin.misc.service.AdminNotificationService;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.response.SimpleIdNameResult;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuiMengxin on 2016/9/29.
 * 推送管理相关接口
 */
@Controller
@Api(tags = "推送Notification")
public class NotificationController {

    @Autowired
    AdminNotificationService adminNotificationService;

    /**
     * 获取推送管理页面
     *
     * @return
     */
    @ApiOperation(value = "获取推送管理页面")
    @RequestMapping(value = "admin/notification/index", method = RequestMethod.GET)
    public String notificationPage() {
        return "operation/push_msg_manage";
    }

    /**
     * 获取推送列表
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "获取推送列表")
    @RequestMapping(value = "admin/notification/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<NotificationListResult> getNotificationList(PagerCommand command) {
        try {
            return new HttpResponse<>(adminNotificationService.getNotificationList(command.getPage(), command.getPage_size()));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取推送详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取推送详情")
    @RequestMapping(value = "admin/notification/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<NotificationDetailResult> getNotificationDetail(@RequestParam String id) {
        try {
            return new HttpResponse<>(adminNotificationService.getNotificationDetail(id));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 删除推送
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "删除推送")
    @RequestMapping(value = "admin/notification/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> deleteNotification(@RequestBody IdsCommand command) {
        try {
            adminNotificationService.delete(command);
            return new HttpResponse<>();
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取所有跳转类型
     *
     * @return
     */
    @ApiOperation(value = "获取所有跳转类型")
    @RequestMapping(value = {"admin/notification/jump/type/list", "admin/jump/type/list"}, method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<NotificationJumpTypeList> getAllJumpTypes() {
        try {
            return new HttpResponse<>(adminNotificationService.getAllJumpTypeList());
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 新增或修改推送
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "新增或修改推送")
    @RequestMapping(value = "admin/notification/create_update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SimpleIdNameResult> createUpdateNotification(@RequestBody CreateOrUpdateNotificationCommand command) {
        try {
            SimpleIdNameResult idNameResult = new SimpleIdNameResult();
            if (StringUtils.isNotBlank(command.getId())) {
                idNameResult.setId(adminNotificationService.update(command));
            } else {
                idNameResult.setId(adminNotificationService.create(command));
            }
            return new HttpResponse<>(idNameResult);
        } catch (Exception e) {
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR, e.getMessage());
        }
    }


    /**
     * 获取所有标签
     *
     * @return
     */
    @ApiOperation(value = "获取所有标签")
    @RequestMapping(value = {"/admin/notification/filter/category/tag/list"}, method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<GetAllTagsResult> getAllTags() {
        try {
            return new HttpResponse<>(adminNotificationService.getAllTags());
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 5.1.7 查询推送人数
     *
     * @return
     */
    @ApiOperation(value = "查询推送人数")
    @RequestMapping(value = {"/admin/notification/user/count"}, method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Map> countNotifyUsers(@RequestBody GetNotificationUsersCountCommand command) {
        try {
            Map result = new HashMap();
            result.put("count", adminNotificationService.getNotificationUsers(command.getFilterTags()).getCount());
            return new HttpResponse<>(result);
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

}
