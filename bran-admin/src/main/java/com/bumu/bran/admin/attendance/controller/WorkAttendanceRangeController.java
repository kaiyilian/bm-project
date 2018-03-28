package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceManagerDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceWorkShiftDao;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceManagerEntity;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceWorkShiftEntity;
import com.bumu.bran.admin.corporation.result.WorkShiftListResult;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.attendance.command.setting.WorkAttendanceAddLocationCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceAddWifiCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceDeleteLocationCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceDeleteWifiCommand;
import com.bumu.bran.attendance.result.WorkAttenOfficeLocationDetailResult;
import com.bumu.bran.attendance.result.WorkAttenOfficeWifiDetailResult;
import com.bumu.bran.attendance.result.WorkAttendanceOfficeLocationResult;
import com.bumu.bran.attendance.result.WorkAttendanceOfficeWifiResult;
import com.bumu.bran.attendance.service.WorkAttendanceRangeService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤范围
 * Created by DaiAoXiang on 2017/5/8.
 */
@Api(tags = {"考勤管理-考勤范围AttendanceRange"})
@Controller
@RequestMapping(value = "/admin/attendance/setting/range")
public class WorkAttendanceRangeController {

    @Autowired
    WorkAttendanceRangeService workAttendanceRangeService;

    @Autowired
    private BranCorpService branCorpService;

    @Autowired
    private WorkAttendanceDeviceWorkShiftDao workAttendanceDeviceWorkShiftDao;

    @Autowired
    private WorkAttendanceDeviceManagerDao workAttendanceDeviceManagerDao;

    private Logger logger = LoggerFactory.getLogger(WorkAttendanceRangeController.class);

    @ApiOperation(httpMethod = "GET", value = "获取办公地点考勤列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/location/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceOfficeLocationResult> getOfficeLocationList(@ApiParam("当前页") @RequestParam("page") Integer page,
                                                                                  @ApiParam("一页显示的数量") @RequestParam("page_size") Integer pageSize,
                                                                                  SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceRangeService.getOfficeLocationList(page, pageSize, sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "获取可以选择的班组")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "work_shift/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkShiftListResult> getOfficeLocationDetail(SessionInfo sessionInfo) throws Exception {
        //所有班组
        WorkShiftListResult allWorkShiftList = branCorpService.getCorpAllWorkShifts(sessionInfo.getCorpId());
        //配置的考勤机
        List<WorkAttendanceDeviceManagerEntity> deviceManagerEntityList = workAttendanceDeviceManagerDao.findByBranCorpId(sessionInfo.getCorpId());
        Map<String, WorkShiftListResult.WorkShiftResult> resultMap = new HashMap<>();
        allWorkShiftList.forEach(tmp -> resultMap.put(tmp.getWorkShiftId(), tmp));
        if (CollectionUtils.isNotEmpty(deviceManagerEntityList)) {
            List<String> deviceNos = new ArrayList<>();
            deviceManagerEntityList.forEach(entity -> deviceNos.add(entity.getDeviceNo()));
            //考勤机配置的班组
            List<WorkAttendanceDeviceWorkShiftEntity> deviceWorkShiftEntityList = workAttendanceDeviceWorkShiftDao.findByCorpIdDeviceNos(sessionInfo.getCorpId(), deviceNos);
            logger.info("删除手机打卡选择的班组");
            deviceWorkShiftEntityList.forEach(deviceWorkShift -> resultMap.remove(deviceWorkShift.getWorkShiftId()));
        }

        WorkShiftListResult result = new WorkShiftListResult();
        result.addAll(resultMap.values());
        return new HttpResponse<>(result);
    }


    @ApiOperation(httpMethod = "GET", value = "获取办公地点考勤详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/location/detail", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttenOfficeLocationDetailResult> getOfficeLocationDetail(@ApiParam("办公地点考勤id") @RequestParam("office_location_id") String officeLocationId,
                                                                                     SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceRangeService.getOfficeLocationDetail(officeLocationId));
    }

    @ApiOperation(httpMethod = "POST", value = "保存办公地点考勤")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/location/save", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<Object> addOrUpdateOfficeLocation(
            @Valid @ApiParam("保存办公地点考勤请求参数") @RequestBody WorkAttendanceAddLocationCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {

        workAttendanceRangeService.addOrUpdateOffficeLocation(command, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(httpMethod = "POST", value = "删除办公地点考勤")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/location/delete", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Object> deleteOfficeLocation(
            @ApiParam("办公地点考勤id") @RequestBody WorkAttendanceDeleteLocationCommand command,
            SessionInfo sessionInfo) throws Exception {
        workAttendanceRangeService.deleteOffficeLocation(command, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(httpMethod = "GET", value = "获取办公WIFI考勤列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/wifi/list", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttendanceOfficeWifiResult> getOfficeWifiList(
            @ApiParam("当前页") @RequestParam("page") Integer page,
            @ApiParam("一页显示的数量") @RequestParam("page_size") Integer pageSize,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceRangeService.getOfficeWifiList(page, pageSize, sessionInfo));
    }

    @ApiOperation(httpMethod = "GET", value = "获取办公wifi考勤详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/wifi/detail", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<WorkAttenOfficeWifiDetailResult> getOfficeWifiDetail(
            @ApiParam("办公wifi考勤id") @RequestParam("office_wifi_id") String officeWifiId,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(workAttendanceRangeService.getOfficeWifiDetail(officeWifiId));
    }

    @ApiOperation(httpMethod = "POST", value = "保存办公wifi考勤")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/wifi/save", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    @ParamCheck
    public HttpResponse<Object> addOrUpdateOfficeWifi(
            @Valid @ApiParam("保存办公wifi考勤请求参数") @RequestBody WorkAttendanceAddWifiCommand command,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        workAttendanceRangeService.addOrUpdateOfficeWifi(command, sessionInfo);
        return new HttpResponse<>();
    }

    @ApiOperation(httpMethod = "POST", value = "删除办公wifi考勤")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "office/wifi/delete", method = RequestMethod.POST)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Object> deleteOfficeWifi(
            @ApiParam("办公地点考勤id") @RequestBody WorkAttendanceDeleteWifiCommand command,
            SessionInfo sessionInfo) throws Exception {
        workAttendanceRangeService.deleteOfficeWifi(command, sessionInfo);
        return new HttpResponse<>();
    }
}
