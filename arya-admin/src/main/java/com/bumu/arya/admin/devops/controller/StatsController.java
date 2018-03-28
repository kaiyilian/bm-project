package com.bumu.arya.admin.devops.controller;

import com.bumu.arya.admin.devops.result.*;
import com.bumu.arya.admin.devops.model.ApiVisitsRatio;
import com.bumu.arya.admin.devops.model.dao.ApiStatsRepository;
import com.bumu.arya.response.HttpResponse;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Allen 2017-11-24
 **/
@Controller()
@Api(tags = {"客户端访问统计ApiStats"})
public class StatsController {

    Logger log = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    ApiStatsRepository apiStatsRepository;

    @RequestMapping(value = "admin/stats/visit/total/today", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "今天访问PV总数", value = "今天访问PV总数")
    public HttpResponse<TotalVisitsResult> visitStatsTotalToday() {
        TotalVisitsResult visits = apiStatsRepository.findTotalVisitsToday();
        return new HttpResponse<>(visits);
    }

    @RequestMapping(value = "admin/stats/visit/total/week", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "本周访问PV总数", value = "本周访问PV总数")
    public HttpResponse<TotalVisitsResult> visitStatsTotalWeek() {
        TotalVisitsResult visits = apiStatsRepository.findTotalVisitsThisWeek();
        return new HttpResponse<>(visits);
    }

    @RequestMapping(value = "admin/stats/visit/total/month", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "本月访问PV总数", value = "本月访问PV总数")
    public HttpResponse<TotalVisitsResult> visitStatsTotaMonth() {
        TotalVisitsResult visits = apiStatsRepository.findTotalVisitsThisMonth();
        return new HttpResponse<>(visits);
    }

    @RequestMapping(value = "admin/stats/visit/total/year", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "今年访问PV总数", value = "今年访问PV总数")
    public HttpResponse<TotalVisitsResult> visitStatsTotalYear() {
        TotalVisitsResult visits = apiStatsRepository.findTotalVisitsThisYear();
        return new HttpResponse<>(visits);
    }

    @RequestMapping(value = "admin/stats/visit/total/all", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "访问PV总数", value = "访问PV总数")
    public HttpResponse<TotalVisitsResult> visitStatsTotalAll() {
        TotalVisitsResult visits = apiStatsRepository.findTotalVisitsAll();
        return new HttpResponse<>(visits);
    }

    @RequestMapping(value = "admin/stats/visit/device_type", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "按照设备类型访问统计", value = "按照设备类型访问统计")
    public HttpResponse<ChartResult> visitStatsByDeviceType() {

        PvUvDimensionData visitsByDeviceName = apiStatsRepository.findVisitsByDeviceName();

        ChartResult chartResult = convert("设备类型访问统计", "设备类型", visitsByDeviceName);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/app_version", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "按照App版本访问统计", value = "按照App版本访问统计")
    public HttpResponse<ChartResult> visitStatsByAppVersion() {

        PvUvDimensionData visitsByVersion = apiStatsRepository.findVisitsByVersion();

        ChartResult chartResult = convert("App版本访问统计", "App版本", visitsByVersion);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/last_24_hours", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "统计最近24小时的访问", value = "统计最近24小时的访问")
    public HttpResponse<ChartResult> visitStatsLast24Hours() {
        log.info("统计最近24小时的访问");
        PvUvDimensionData visitsByVersion = apiStatsRepository.findVisitsLast24Hours();

        ChartResult chartResult = convert("最近24小时的访问", "24小时", visitsByVersion);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/last_30_days", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "统计最近30天的访问", value = "统计最近30天的访问")
    public HttpResponse<ChartResult> visitStatsLast30Days() {
        log.info("统计最近30天的访问");
        PvUvDimensionData visitsByVersion = apiStatsRepository.findVisitsLast30Days();

        ChartResult chartResult = convert("最近30天的访问", "30天", visitsByVersion);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/weekly", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "统计周一至周日的访问", value = "统计周一至周日的访问")
    public HttpResponse<ChartResult> visitStatsWeekly() {
        PvUvDimensionData visitsByVersion = apiStatsRepository.findVisitsWeekly();

        ChartResult chartResult = convert("周一至周日的访问", "周", visitsByVersion);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/hourly", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "统计每天各个时间段的访问", value = "统计每天各个时间段的访问")
    public HttpResponse<ChartResult> visitStatsHourly() {
        PvUvDimensionData visitsByVersion = apiStatsRepository.findVisitsHourly();

        ChartResult chartResult = convert("每天各个时间段的访问", "时段", visitsByVersion);

        return new HttpResponse<>(chartResult);
    }

    @RequestMapping(value = "admin/stats/visit/path", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "统计每个API路径的访问", value = "统计每个API路径的访问")
    public HttpResponse<ApiVisitsResult> visitStatsPath() {
        List<ApiVisitsRatio> apiPvUv = apiStatsRepository.findApiPvUv();
        ApiVisitsResult apiVisitsResult = new ApiVisitsResult();
        apiVisitsResult.setChartName("统计每个API路径的访问");
        apiVisitsResult.setData(apiPvUv);
        return new HttpResponse<>(apiVisitsResult);
    }


    private ChartResult convert(String chartName, String dimensionName, PvUvDimensionData pvUvDimensionData) {
        ChartResult chartResult = new ChartResult();
        chartResult.setChartName(chartName);

        Dimension dimension = new Dimension();
        dimension.setName(dimensionName);
        dimension.setValues(pvUvDimensionData.getDimensions());
        chartResult.setDimension(dimension);

        chartResult.setData(new ArrayList<>());
        {
            DimensionData<Long> dimensionData = new DimensionData<>();
            dimensionData.setName(pvUvDimensionData.getPvDimensionData().getName());
            dimensionData.setType(pvUvDimensionData.getPvDimensionData().getType());
            dimensionData.setValues(pvUvDimensionData.getPvDimensionData().getValues());
            chartResult.getData().add(dimensionData);
        }
        {
            DimensionData<Long> dimensionData = new DimensionData<>();
            dimensionData.setName(pvUvDimensionData.getUvDimensionData().getName());
            dimensionData.setType(pvUvDimensionData.getUvDimensionData().getType());
            dimensionData.setValues(pvUvDimensionData.getUvDimensionData().getValues());
            chartResult.getData().add(dimensionData);
        }
        return chartResult;
    }

}
