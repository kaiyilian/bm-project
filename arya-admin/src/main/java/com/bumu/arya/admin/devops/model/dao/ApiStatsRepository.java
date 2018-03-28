package com.bumu.arya.admin.devops.model.dao;

import com.bumu.arya.admin.devops.model.ApiVisitsRatio;
import com.bumu.arya.admin.devops.result.PvUvDimensionData;
import com.bumu.arya.admin.devops.result.TotalVisitsResult;

import java.util.List;

/**
 * API 访问统计
 * @author Allen 2017-11-27
 **/
public interface ApiStatsRepository {

    TotalVisitsResult findTotalVisitsToday();

    TotalVisitsResult findTotalVisitsThisWeek();

    TotalVisitsResult findTotalVisitsThisMonth();

    TotalVisitsResult findTotalVisitsThisYear();

    TotalVisitsResult findTotalVisitsAll();

    PvUvDimensionData findVisitsLast24Hours();

    PvUvDimensionData findVisitsLast30Days();

    PvUvDimensionData findVisitsWeekly();

    PvUvDimensionData findVisitsHourly();

    PvUvDimensionData findVisitsByDeviceName();

    PvUvDimensionData findVisitsByVersion();

    List<ApiVisitsRatio> findApiPvUv();

    void printProfiles();

}
