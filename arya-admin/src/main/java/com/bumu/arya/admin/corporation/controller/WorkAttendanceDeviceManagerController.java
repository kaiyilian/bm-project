package com.bumu.arya.admin.corporation.controller;

import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.controller.command.WorkAttendanceCommand;
import com.bumu.arya.admin.corporation.result.CorpListResult;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.corporation.service.WorkAttendanceDeviceManagerService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.attendance.result.WorkAttendanceDeviceManagerResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.WorkAttendanceDeviceManagerCommand;
import com.bumu.common.constant.RedisConstants;
import com.bumu.common.result.BaseResult;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.StringUtil;
import com.bumu.common.validator.annotation.AddValidatedGroup;
import com.bumu.common.validator.annotation.FindValidatedGroup;
import com.bumu.common.validator.annotation.UpdateValidatedGroup;
import com.bumu.utils.DateUtil;
import com.bumu.utils.HttpClientUtils;
import com.bumu.utils.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤设备管理
 *
 * @author majun
 * @date 2017/3/22
 */
@Api(value = "WorkAttendanceDeviceManagerController", tags = {"考勤设备管理AttendanceDevice"})
@Controller
@RequestMapping(value = "/admin/work_attendance/device/manager")
public class WorkAttendanceDeviceManagerController {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceDeviceManagerController.class);

    @Autowired
    private WorkAttendanceDeviceManagerService service;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private CorporationService corporationService;

    private static final Integer LIMIT_TIME = 60 * 5;

    @ApiOperation(value = "查询某个公司所有的考勤机设备")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "get/list", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    public HttpResponse<List<WorkAttendanceDeviceManagerResult>> getList(@Validated(value = FindValidatedGroup.class) @ApiParam
                                                                                 WorkAttendanceDeviceManagerCommand.Base command, BindingResult bindingResult) {
        return new HttpResponse(service.getList(new SessionInfo(null, command.getAryaCorpId())));
    }

    @ApiOperation(value = "新增考勤机设备")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<BaseResult.IDResult> add(
            @Validated(value = AddValidatedGroup.class) @ApiParam @RequestBody WorkAttendanceDeviceManagerCommand.Add command,
            BindingResult bindingResult) throws Exception {

        logger.info("请求参数: ");
        logger.info("command.getDeviceNo(): " + command.getDeviceNo());
        logger.info("command.getAryaCorpId: " + command.getAryaCorpId());

        HttpResponse<BaseResult.IDResult> httpResponse = new HttpResponse(ErrorCode.CODE_OK, service.add(command, new SessionInfo(null, command.getAryaCorpId())));
        logger.info("result: " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "修改考勤机设备")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<BaseResult.IDResult> update(
            @Validated(value = UpdateValidatedGroup.class) @ApiParam @RequestBody WorkAttendanceDeviceManagerCommand.Update command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) {
        return new HttpResponse(service.update(command, new SessionInfo(null, command.getAryaCorpId())));
    }

    @ApiOperation(value = "删除考勤机设备")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "batch/delete", method = RequestMethod.POST)
    @ResponseBody
    @ParamCheck
    public HttpResponse<Object> batchDelete(@Valid @ApiParam @RequestBody BaseCommand.BatchIds command,
                                            BindingResult bindingResult) {
        service.batchDelete(command, new SessionInfo());
        return new HttpResponse();
    }

    @ApiOperation(value = "后台手动同步考勤数据")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/attendance/sync/record", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse attendanceSyncRecord(@RequestBody WorkAttendanceCommand command) {

        String date_time = command.getDateTime();
        String bran_corp_id = command.getBranCorpId();

        Date date= DateUtil.addDay(DateUtil.getDateFromString(DateUtil.DATE_STRING_FORMAT_DAY,date_time),1);
        date_time=  DateUtil.format(DateUtil.DATE_STRING_FORMAT_DAY,date);
        if (!StringUtil.isAllNotEmpty(date_time, bran_corp_id)) {
            return new HttpResponse(ErrorCode.CODE_PARAMS_ERROR);
        }
        CorpListResult.CorpInfo info= corporationService.getCorpDetail(bran_corp_id);
        logger.info("==>syncAttendanceRecord by corpid:" + bran_corp_id + " time:" + date_time);

        String key = RedisConstants.BRAN_ATTENDANCE_SYNC + "_C_" + bran_corp_id + "_T_" + date_time;
        if (redisService.exists(key)) {
            return new HttpResponse(ErrorCode.CODE_SMS_CODE_NO_REPEAT);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("date_time", date_time);
            map.put("bran_corp_id", info.getBranCorpId());
            HttpResult result = HttpClientUtils.doPost(configService.getConfigByKey(CorpConstants.SYNC_URL), map, 3000);
            logger.info("==> result:" + result + " redis key:" + key);
            redisService.setex(key, LIMIT_TIME, "1");
        }

        return new HttpResponse(ErrorCode.CODE_OK);
    }
}
