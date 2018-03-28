package com.bumu.bran.admin.work_shift_type.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.work_shift_type.service.BranWorkNewShiftTypeService;
import com.bumu.bran.common.annotation.RecordLogs;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.bran.validated.user.shift.WorkShiftTypeAddValidatedGroup;
import com.bumu.bran.validated.user.shift.WorkShiftTypeDeleteValidatedGroup;
import com.bumu.bran.validated.user.shift.WorkShiftTypeFindValidatedGroup;
import com.bumu.bran.validated.user.shift.WorkShiftTypeUpdateValidatedGroup;
import com.bumu.bran.workshift.command.WorkShiftIdCommand;
import com.bumu.bran.workshift.command.WorkShiftTypeCommand;
import com.bumu.bran.workshift.command.WorkShiftTypeNewCommand;
import com.bumu.bran.workshift.result.*;
import com.bumu.common.annotation.ParamCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Controller
@Transactional // AOP中记录日志有用
@Api(tags = {"考勤管理-班次管理WorkShiftType"})
public class BranWorkShiftTypeNewController {

    private Logger logger = LoggerFactory.getLogger(BranWorkShiftTypeNewController.class);

    @Autowired
    private BranWorkNewShiftTypeService branWorkNewShiftTypeService;

    /**
     * 新建班次
     *
     * @param workShiftTypeCommand
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/add", method = RequestMethod.POST)
    @ResponseBody
    @SetParams
    @ApiOperation(value = "增加班次")
    @ApiResponse(code = 200, message = "成功")
    @RecordLogs(model = BranOpLogEntity.OP_MODULE_USER_WORK_SHIFT_TYPE, type = BranOpLogEntity.OP_TYPE_ADD, msg = "编辑班次: ")
    public HttpResponse add(
            @ApiParam(value = "增加新的班次信息  ")
            @RequestBody @Validated(value = {WorkShiftTypeAddValidatedGroup.class,WorkShiftTypeUpdateValidatedGroup.class})
                    WorkShiftTypeNewCommand workShiftTypeCommand,
            BindingResult bindingResult
    ) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/add start ... ");
        logger.debug("params: " + workShiftTypeCommand.toString());
        branWorkNewShiftTypeService.add( workShiftTypeCommand);
        logger.debug("/admin/attendance/schedule/workShiftType/add end ... ");
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK);
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }



    /**
     * 查询班次
     *
     * @param workShiftTypeCommand
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/get", method = RequestMethod.GET)
    @ResponseBody
    @SetParams
    @ApiOperation(value = "查询全部的班次信息")
    public HttpResponse<List<BranWorkShiftTypeNewResult>> get(
            WorkShiftTypeCommand workShiftTypeCommand, BindingResult bindingResult) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/get start ....");
        logger.debug("params: " + workShiftTypeCommand.toString());
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK, branWorkNewShiftTypeService.get(workShiftTypeCommand));
        logger.debug("/admin/attendance/schedule/workShiftType/get end ....");
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    /**
     * 查询班次详情
     *
     * @param workShiftIdCommand
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/get/id", method = RequestMethod.GET)
    @ResponseBody
    @ParamCheck
    @ApiOperation(value = "根据id查询班次信息")
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<BranNewWorkShiftTypeResult> getOneById(
            @ApiParam(value = "通过id 查询相应的班次  只需要班次信息  ")
            @Validated(value = WorkShiftTypeFindValidatedGroup.class)
                    WorkShiftIdCommand workShiftIdCommand,
            BindingResult bindingResult
    ) throws Exception {
        logger.debug("/admin/workShiftType/get/id/v1 start ....");
        logger.debug("params: " + workShiftIdCommand.toString());
        BranNewWorkShiftTypeResult typeServiceById = branWorkNewShiftTypeService.getById(workShiftIdCommand);
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK, typeServiceById);
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }

    @ApiOperation(value = "获取休息时间段")
    @ResponseBody
    @ParamCheck
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/get/rest", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<BranScheduleRestTimeResult>>
    getRestTime(@ApiParam(value = "需要班次的id ")
                @RequestBody
                @Validated(value = WorkShiftTypeUpdateValidatedGroup.class)
                        WorkShiftIdCommand workShiftIdCommand,
                BindingResult bindingResult

    ) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/get/rest start ....");
        logger.debug("params: " + workShiftIdCommand.toString());
        List<BranScheduleRestTimeResult> rest = branWorkNewShiftTypeService.getRest(workShiftIdCommand);
        logger.debug("/admin/attendance/schedule/workShiftType/get/rest end ....");
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK, rest);
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());

        return httpResponse;
    }

    @ApiOperation(value = "获取旷工定义")
    @ResponseBody
    @ParamCheck
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/get/abs", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<List<BranScheduleAbsenteeismResult>>
    getAbsenteeism(@ApiParam(value = "需要班次的id 和 旷工定义的id（otherId）") @RequestBody
                   @Validated(value = WorkShiftTypeUpdateValidatedGroup.class) WorkShiftIdCommand workShiftIdCommand,
                   BindingResult bindingResult

    ) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/get/abs start ....");
        List<BranScheduleAbsenteeismResult> abs = branWorkNewShiftTypeService.getAbs(workShiftIdCommand);
        logger.debug("/admin/attendance/schedule/workShiftType/get/abs end ....");
        HttpResponse<List<BranScheduleAbsenteeismResult>> listHttpResponse = new HttpResponse<>(ErrorCode.CODE_OK, abs);

        logger.debug("httpResponse.toJson(): " + listHttpResponse.toJson());
        return listHttpResponse;
    }

    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/get/overtime", method = RequestMethod.POST)
    @ApiOperation(value = "获取加班设置")
    @ResponseBody
    @ParamCheck
    @ApiResponse(code = 200, message = "成功")
    public HttpResponse<BranScheduleOvertimeResult>
    getOvertime(@ApiParam(value = "需要班次的id ") @RequestBody
                @Validated(value = WorkShiftTypeUpdateValidatedGroup.class) WorkShiftIdCommand workShiftIdCommand,
                BindingResult bindingResult
    ) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/get/overtime start ....");
        BranScheduleOvertimeResult overtime = branWorkNewShiftTypeService.getOvertime(workShiftIdCommand);
        logger.debug("/admin/attendance/schedule/workShiftType/get/overtime ....");
        HttpResponse<BranScheduleOvertimeResult> listHttpResponse = new HttpResponse<>(ErrorCode.CODE_OK, overtime);
        logger.debug("httpResponse.toJson(): " + listHttpResponse.toJson());

        return listHttpResponse;
    }

    /**
     * 删除班次
     *
     * @param workShiftTypeCommand
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/attendance/schedule/workShiftType/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除班次")
    @ApiResponse(code = 200, message = "成功")
    @ResponseBody
    @SetParams
    @RecordLogs(model = BranOpLogEntity.OP_MODULE_USER_WORK_SHIFT_TYPE, type = BranOpLogEntity.OP_TYPE_DELETE, msg = "删除班次: ")
    public HttpResponse delete(
            @ApiParam(value = "需要班次的id (id)")
            @RequestBody @Validated(value = WorkShiftTypeDeleteValidatedGroup.class)
                    WorkShiftTypeCommand workShiftTypeCommand, BindingResult bindingResult) throws Exception {
        logger.debug("/admin/attendance/schedule/workShiftType/delete start ... ");
        logger.debug("params: " + workShiftTypeCommand.toString());
        branWorkNewShiftTypeService.delete(workShiftTypeCommand);
        logger.debug("/admin/attendance/schedule/workShiftType/delete end ... ");
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK);
        logger.debug("httpResponse.toJson(): " + httpResponse.toJson());
        return httpResponse;
    }
}
