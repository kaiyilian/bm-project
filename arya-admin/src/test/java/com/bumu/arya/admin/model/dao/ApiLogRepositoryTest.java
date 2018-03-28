package com.bumu.arya.admin.model.dao;

import com.bumu.arya.admin.devops.model.dao.ApiLogRepository;
import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.github.swiftech.swifttime.Time;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test_ac_devops_dao.xml", "/ac_mongo.xml"})
@Aspect
public class ApiLogRepositoryTest {

    @Autowired
    ApiLogRepository apiLogRepository;

    @Before("execution(* com.bumu.arya.admin.devops.model.dao.*.*(..))")
//    @After("execution(* com.bumu.arya.admin.stats.model.dao.*.*(..))")
    private void timeProfile() {
        System.out.println("单个查询耗时：" + System.currentTimeMillis());
    }

    @Test
    public void test() {
        System.out.println("测试");

        Time start = new Time().increaseDates(-999999);
        Time end = new Time();

        List<ApiLogDocument> lastVisitBetween = apiLogRepository.findLastVisitBetween(start, end);

        System.out.println("查询结果数量：" + lastVisitBetween.size());

        for (ApiLogDocument apiLogDocument : lastVisitBetween) {
            System.out.println(apiLogDocument.getContextMap().get("user_id") + " - " + new Time(apiLogDocument.getMillis()));
            System.out.println(apiLogDocument.getContextMap().size());
        }

    }

}
