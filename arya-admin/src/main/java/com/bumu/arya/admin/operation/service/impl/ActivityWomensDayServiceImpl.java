package com.bumu.arya.admin.operation.service.impl;

import com.bumu.arya.activity.command.ActivityWomenDayCommend;
import com.bumu.arya.activity.model.ActivityWomensDayDao;
import com.bumu.arya.activity.model.entity.WomensDayEntity;
import com.bumu.arya.activity.result.ActivityWomensDayResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.FileDownService;
import com.bumu.arya.admin.operation.service.ActivityWomensDayService;
import com.bumu.common.result.DownloadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yousihang
 * @date 2018/2/28
 */
@Service
@Transactional
public class ActivityWomensDayServiceImpl implements ActivityWomensDayService {

    private static Logger logger = LoggerFactory.getLogger(ActivityWomensDayServiceImpl.class);

    @Autowired
    private ActivityWomensDayDao activityWomensDayDao;

    @Autowired
    private FileDownService fileDownService;



    @Override
    public ActivityWomensDayResult findByPage(ActivityWomenDayCommend womenDayCommend) {
        return activityWomensDayDao.findByPage(womenDayCommend);
    }

    @Override
    public DownloadResult exportOrders(ActivityWomenDayCommend activityWomenDayCommend, HttpServletResponse response) {
        String url = "";
        try {
            List<ActivityWomensDayResult.WomensDayResult> list = activityWomensDayDao.exportOrders(activityWomenDayCommend);
            url = fileDownService.getExelDownUrl(list,"妇女节活动",AryaAdminConfigService.ACTIVITY_WOMENS_DAY_IMPORT);

            logger.info("url: " + url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DownloadResult(url);
    }



    @Override
    public void updateById(ActivityWomenDayCommend womenDayCommend) {

        List<WomensDayEntity> list= activityWomensDayDao.findByParam("id",womenDayCommend.getId());
        if(!list.isEmpty()){
            list.get(0).setIsShow(womenDayCommend.getIsShow());
            activityWomensDayDao.update(list.get(0));
        }
    }
}
