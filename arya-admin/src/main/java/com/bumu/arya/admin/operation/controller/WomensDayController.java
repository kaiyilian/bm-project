package com.bumu.arya.admin.operation.controller;

import com.bumu.arya.activity.command.ActivityWomenDayCommend;
import com.bumu.arya.activity.result.ActivityWomensDayResult;
import com.bumu.arya.admin.operation.service.ActivityWomensDayService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yousihang
 * @date 2018/3/1
 */
@Api(value = "activity", tags = "妇女节活动")
@Controller
@RequestMapping(value = "/admin")
public class WomensDayController {

    Logger log = LoggerFactory.getLogger(WomensDayController.class);

    @Autowired
    private ActivityWomensDayService activityWomensDayService;


    /**
     * 报名列表
     *
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "妇女节活动列表", value = "妇女节活动列表")
    @RequestMapping(value = "/activity/womens/records", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<ActivityWomensDayResult> getRecords(
            @ApiParam(value = "开始时间") @RequestParam(value = "begin_time", required = false) Long beginTime,
            @ApiParam(value = "结束时间") @RequestParam(value = "end_time", required = false) Long endTime,
            @ApiParam(value = "手机号") @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "页码") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "每页数量") @RequestParam(value = "page_size", required = false) Integer pageSize,
            @ApiParam(value = "活动名称") @RequestParam(value = "project", required = false) String project
    ) {
        try {
            if (page == null) {
                page = 1;
            }
            page = page - 1;

            if (pageSize == null) {
                pageSize = 10;
            }

            ActivityWomenDayCommend activityWomenDayCommend = new ActivityWomenDayCommend();
            activityWomenDayCommend.setBeginTime(beginTime);
            activityWomenDayCommend.setEndTime(endTime);
            activityWomenDayCommend.setMobile(mobile);
            activityWomenDayCommend.setProject(project);
            activityWomenDayCommend.setPageSize(pageSize);
            activityWomenDayCommend.setPage(page);

            return new HttpResponse(activityWomensDayService.findByPage(activityWomenDayCommend));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 报名列表导出
     *
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "妇女节活动导出", value = "妇女节活动导出")
    @RequestMapping(value = "activity/womens/records/export", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse recordsExport(
            @ApiParam(value = "开始时间") @RequestParam(value = "begin_time", required = false) Long beginTime,
            @ApiParam(value = "结束时间") @RequestParam(value = "end_time", required = false) Long endTime,
            @ApiParam(value = "手机号") @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "活动名称") @RequestParam(value = "project", required = false) String project
            , HttpServletResponse response) {
        try {
            ActivityWomenDayCommend activityWomenDayCommend = new ActivityWomenDayCommend();
            activityWomenDayCommend.setBeginTime(beginTime);
            activityWomenDayCommend.setEndTime(endTime);
            activityWomenDayCommend.setMobile(mobile);
            activityWomenDayCommend.setProject(project);

            return new HttpResponse(ErrorCode.CODE_OK, activityWomensDayService.exportOrders(activityWomenDayCommend, response));

        } catch (Exception e) {
            return new HttpResponse();
        }
    }

    /**
     * 报名列表导出
     *
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "妇女节活动编辑", value = "妇女节活动编辑")
    @RequestMapping(value = "activity/womens/edit", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse recordEdit(
            @ApiParam(value = "ID") @RequestParam(value = "id") String id,
            @ApiParam(value = "是否显示") @RequestParam(value = "is_show") Integer isShow
            , HttpServletResponse response) {
        try {
            ActivityWomenDayCommend womenDayCommend = new ActivityWomenDayCommend();

            womenDayCommend.setId(id);
            womenDayCommend.setIsShow(isShow);
            activityWomensDayService.updateById(womenDayCommend);

            return new HttpResponse(ErrorCode.CODE_OK);

        } catch (Exception e) {
            return new HttpResponse();
        }
    }

}
