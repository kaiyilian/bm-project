package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.attendance.service.WorkAttendanceEmpService;
import com.bumu.bran.webservice.attendance.WorkAttendanceHttpEmpCommand;
import com.bumu.bran.webservice.attendance.WorkAttendanceSyncRecordCommand;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.service.RedisService;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 考勤开放接口
 *
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-考勤数据同步AttendanceSync"})
@RestController
public class WorkAttendanceApiController {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceApiController.class);

    @Autowired
    private WorkAttendanceEmpService service;
    @Autowired
    private RedisService redisService;

    private static final Integer LIMIT_TIME = 60 * 5;


    @ApiOperation(value = "正式员工一键录入考勤机回调接口")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/api/device/attendance/save/callback", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Object> putEmpToWorkAttendanceDeviceCallBack(
            @ApiParam @RequestBody @Valid WorkAttendanceHttpEmpCommand command) {
        logger.info("enter putEmpToWorkAttendanceDeviceCallBack ...");
        service.putEmpToWorkAttendanceDeviceCallBack(command);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "正式员工考勤机退工回调接口")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/api/device/attendance/retired/callback", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Object> retiredEmpForWorkAttendanceDeviceCallBack(
            @ApiParam @RequestBody @Valid WorkAttendanceHttpEmpCommand command) {
        logger.info("enter retiredEmpForWorkAttendanceDeviceCallBack ...");
        service.retiredEmpForWorkAttendanceDeviceCallBack(command);
        return new HttpResponse<>();
    }

    @ApiOperation(value = "手动同步考勤数据")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/api/device/attendance/sync/record", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse syncWorkAttendanceRecord(
            @ApiParam @RequestBody @Valid WorkAttendanceSyncRecordCommand command,
            BindingResult bindingResult) throws IOException {
        logger.info("enter syncWorkAttendanceRecord ...");
        service.syncWorkAttendanceRecord(command);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "手动同步所有公司的考勤数据")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/api/device/attendance/sync/record/all", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse syncWorkAttendanceAllRecord(
            @ApiParam(value = "同步指定时间前一天的考勤数据，格式：'YYYY-MM-dd' 例如'2017-05-23'") String dateTime) throws IOException {
        logger.info("enter syncWorkAttendanceAllRecord ...");
        service.syncWorkAttendanceAllRecord(dateTime);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "根据公司手动退工")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/api/device/attendance/retired", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse retiredByBranCorpId(@ApiParam(value = "公司id")
                                            @RequestParam(value = "bran_corp_id", required = false)
                                                    String branCorpId) throws Exception {
        logger.info("enter retiredByBranCorpId ...");
        logger.info("参数: " + branCorpId);
        if (StringUtils.isBlank(branCorpId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "退工的公司id必填");
        }
        return new HttpResponse(ErrorCode.CODE_OK, service.retiredByBranCorpId(branCorpId));
    }

    @ApiOperation(value = "后台手动同步考勤数据")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "api/attendance/sync/record", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse attendanceSyncRecord(
            @RequestParam(value = "date_time") String dateTime,
            @RequestParam(value = "bran_corp_id") String branCorpId, HttpSession session) {

            WorkAttendanceSyncRecordCommand command=new WorkAttendanceSyncRecordCommand();
            command.setDateTime(dateTime);
            command.setBranCorpId(branCorpId);
            service.syncWorkAttendance(command);

            return new HttpResponse(ErrorCode.CODE_OK);
    }
}
