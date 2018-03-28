package com.bumu.bran.admin.attendance.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.attendance.service.WorkAttendanceEmpService;
import com.bumu.common.SessionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 正式员工考勤controller
 *
 * @author majun
 * @date 2017/3/22
 */
@Api(tags = {"考勤管理-正式员工考勤接口AttendanceEmp"})
@Controller
@RequestMapping()
public class WorkAttendanceEmpController {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceEmpController.class);

    @Autowired
    private WorkAttendanceEmpService service;

    @ApiOperation(value = "正式员工一键录入考勤机")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/admin/employee/roster/work_attendance/put", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<Object> putEmpToWorkAttendanceDevice(SessionInfo sessionInfo) throws Exception{
        logger.info("正式员工一键录入考勤机 ...");
        service.putEmpToWorkAttendanceDevice(sessionInfo);
        return new HttpResponse<>();
    }
}
