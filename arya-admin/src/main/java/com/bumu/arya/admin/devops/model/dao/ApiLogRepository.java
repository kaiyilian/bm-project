package com.bumu.arya.admin.devops.model.dao;

import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.github.swiftech.swifttime.Time;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApiLogRepository {

    ApiLogDocument findByUserIdLastAccess(String userId);


    /**
     * 查询指定时间范围内所有的最新用户访问记录。
     * @param start
     * @param end
     * @return
     */
    List<ApiLogDocument> findLastVisitBetween(Time start, Time end);

}
