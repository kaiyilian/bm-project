package com.bumu.bran.admin.main.controller;

import com.bumu.arya.command.MybatisPagerCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.main.controller.command.EmpIdCardNoExpireTimeSetCommand;
import com.bumu.bran.admin.main.service.IndexService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.home.result.EmpBirthdayWarningResult;
import com.bumu.bran.home.result.EmpProspectiveWarningResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author majun
 * @date 2017/2/9
 */
@Api(value = "IndexController", tags = {"企业管理平台首页"})
@RestController
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "/admin/home/index/schedule/view")
    @SetParams
    public HttpResponse getScheduleViews(ModelCommand modelCommand, BindingResult bindingResult) throws Exception {
        return new HttpResponse(ErrorCode.CODE_OK, indexService.getScheduleViews(modelCommand));
    }

    @ApiOperation(value = "员工生日提醒")
    @GetMapping(value = "/admin/home/index/employee/birthday/warning")
    @GetSessionInfo
    public HttpResponse<Pager<EmpBirthdayWarningResult>> getEmpBirthdayWarning(@ApiParam @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                               @ApiParam @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
                                                                               SessionInfo sessionInfo) throws Exception {
        logger.debug("getEmpBirthdayWarning start");
        PagerCommand pagerCommand = new MybatisPagerCommand(page, pageSize);
        HttpResponse<Pager<EmpBirthdayWarningResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, indexService.getEmpBirthdayWarning(pagerCommand, sessionInfo));
        logger.debug("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "处理员工生日提醒")
    @PostMapping(value = "/admin/home/index/employee/birthday/warning/dispose")
    @GetSessionInfo
    public HttpResponse<Void> disposeEmpBirthdayWarning(@ApiParam(value = "员工id") @RequestBody BatchCommand batch,
                                                        SessionInfo sessionInfo) throws Exception {

        logger.debug("disposeEmpBirthdayWarning start ...");
        indexService.disposeEmpBirthdayWarning(batch.getBatch(), sessionInfo);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "员工身份证号提醒")
    @GetMapping(value = "/admin/home/index/employee/idCardNo/warning")
    @GetSessionInfo
    public HttpResponse<Pager<EmpBirthdayWarningResult>> getEmpIdCardNoWarning(@ApiParam @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                               @ApiParam @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
                                                                               SessionInfo sessionInfo) throws Exception {
        logger.debug("getEmpIdCardNoWarning start");
        PagerCommand pagerCommand = new MybatisPagerCommand(page, pageSize);
        HttpResponse<Pager<EmpBirthdayWarningResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, indexService.getEmpIdCardNoWarning(pagerCommand, sessionInfo));
        logger.debug("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "更新身份证有效期")
    @PostMapping(value = "/admin/home/index/employee/idCardNo/expireTime/set")
    @GetSessionInfo
    public HttpResponse<Void> setEmpIdCardNoExpireTime(@ApiParam @RequestBody EmpIdCardNoExpireTimeSetCommand empIdCardNoExpireTimeSetCommand,
                                                       SessionInfo sessionInfo) throws Exception {
        indexService.setEmpIdCardNoExpireTime(empIdCardNoExpireTimeSetCommand, sessionInfo);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "待入职员工-入职提醒-列表")
    @GetMapping(value = "/admin/home/index/employee/prospective/warning")
    @GetSessionInfo
    public HttpResponse<Pager<EmpProspectiveWarningResult>> getEmpProspectiveWarning(@ApiParam @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                                     @ApiParam @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
                                                                                     SessionInfo sessionInfo) throws Exception {
        logger.debug("getEmpProspectiveWarning start");
        PagerCommand pagerCommand = new MybatisPagerCommand(page, pageSize);
        HttpResponse<Pager<EmpProspectiveWarningResult>> httpResponse = new HttpResponse(ErrorCode.CODE_OK, indexService.getEmpProspectiveWarning(pagerCommand, sessionInfo));
        logger.debug("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }


    @ApiOperation(value = "待入职员工-入职提醒-处理")
    @PostMapping(value = "/admin/home/index/employee/prospective/dispose")
    @GetSessionInfo
    public HttpResponse<Void> disposeEmpProspectiveWarning(@ApiParam(value = "员工id") @RequestBody BatchCommand batch, SessionInfo sessionInfo) throws Exception {

        logger.debug("disposeEmpProspectiveWarning start");
        indexService.disposeEmpProspectiveWarning(batch, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "待入职员工-入职提醒-导出")
    @GetMapping(value = "/admin/home/index/employee/prospective/export")
    @GetSessionInfo
    public HttpResponse<FileUploadFileResult> exportEmpProspectiveWarning(SessionInfo sessionInfo) throws Exception {

        logger.debug("exportEmpProspectiveWarning start");
        HttpResponse<FileUploadFileResult> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, indexService.exportEmpProspectiveWarning(sessionInfo));
        logger.debug("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }
}
