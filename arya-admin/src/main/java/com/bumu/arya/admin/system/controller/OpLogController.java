package com.bumu.arya.admin.system.controller;

import com.bumu.arya.admin.system.controller.command.OpLogListCommand;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统操作日志查询
 * Created by CuiMengxin on 2016/10/11.
 * Changed by Allen
 */
@Controller
public class OpLogController {

    @Autowired
    OpLogService opLogService;

    /**
     * 系统日志查询页面
     *
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/admin/sys/log/index", method = RequestMethod.GET)
    public String logIndexPage() {
        return "system/sys_log_manage";
    }

    /**
     * 获取所有操作类型
     *
     * @return
     */
    @RequestMapping(value = "/admin/sys/log/operate/type/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<Void> getOperateTypeList() {
        try {
            return new HttpResponse(opLogService.getAllOperateTypeList());
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取日志分页列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/sys/log/list", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> getLogList(@RequestBody OpLogListCommand command, HttpServletRequest request) {
        System.out.println(request.getRequestURL());
        System.out.println(command.getKeyword());
        System.out.println(command.getOperateType());
        System.out.println(command.getOpLoginName());
        try {
            return new HttpResponse(opLogService.getLogList(command));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }
}
