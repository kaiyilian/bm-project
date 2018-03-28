package com.bumu.arya.admin.operation.service;

import com.bumu.arya.activity.command.ActivityWomenDayCommend;
import com.bumu.arya.activity.result.ActivityWomensDayResult;
import com.bumu.common.result.DownloadResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yousihang
 * @date 2018/2/28
 */
public interface ActivityWomensDayService {

    ActivityWomensDayResult findByPage(ActivityWomenDayCommend womenDayCommend);

    DownloadResult exportOrders(ActivityWomenDayCommend activityWomenDayCommend, HttpServletResponse response);

    void updateById(ActivityWomenDayCommend womenDayCommend);
}
