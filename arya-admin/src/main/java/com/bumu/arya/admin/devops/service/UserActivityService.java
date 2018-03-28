package com.bumu.arya.admin.devops.service;

import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.bumu.arya.admin.devops.result.UserActivityResult;

import java.util.List;

/**
 * @author Allen 2018-02-27
 **/
public interface UserActivityService {

    /**
     * 获取最近的访问记录
     * @return
     */
    List<ApiLogDocument> findLastVisit();

    /**
     * 获取最近活跃的用户的工资单等信息
     * @return
     */
    UserActivityResult getActivitis();

    /**
     * 获取最近活跃的电子工资单员工信息
     * @return
     */
    UserActivityResult getPayrollActivities();
}
