package com.bumu.arya.admin.model.dao;

import com.bumu.arya.admin.devops.model.dao.ApiStatsRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test_ac_arya_admin.xml", "/ac_mongo.xml"})
@Aspect
public class ApiStatsRepositoryTest {

    @Autowired
    ApiStatsRepository apiStatsRepository;

    @Before("execution(* com.bumu.arya.admin.devops.model.dao.*.*(..))")
//    @After("execution(* com.bumu.arya.admin.stats.model.dao.*.*(..))")
    private void timeProfile() {
        System.out.println("单个查询耗时：" + System.currentTimeMillis());
    }

    @Test
    public void testTotalStats() {
        System.out.println("测试总数");
        System.out.println(apiStatsRepository.findTotalVisitsToday());
        System.out.println(apiStatsRepository.findTotalVisitsThisWeek());
        System.out.println(apiStatsRepository.findTotalVisitsThisMonth());
        System.out.println(apiStatsRepository.findTotalVisitsThisYear());
        System.out.println(apiStatsRepository.findTotalVisitsAll());
    }

    @Test
    public void test() {
        System.out.println("测试");

//        start = System.currentTimeMillis();

//        apiStatsRepository.testTime();

//        profileBegin("findVisitsLast24Hours");
        apiStatsRepository.findVisitsLast24Hours();
//        profileEnd("findVisitsLast24Hours");

//        profileBegin("findVisitsLast30Days");
        apiStatsRepository.findVisitsLast30Days();
//        profileEnd("findVisitsLast30Days");

//        profileBegin("findVisitsHourly");
        apiStatsRepository.findVisitsHourly();
//        profileEnd("findVisitsHourly");

//        profileBegin("findVisitsWeekly");
        apiStatsRepository.findVisitsWeekly();
//        profileEnd("findVisitsWeekly");

//        profileBegin("findVisitsByVersion");
        apiStatsRepository.findVisitsByVersion();
//        profileEnd("findVisitsByVersion");

//        profileBegin("findVisitsByDeviceName");
        apiStatsRepository.findVisitsByDeviceName();
//        profileEnd("findVisitsByDeviceName");

//        profileBegin("findApiPvUv");
        apiStatsRepository.findApiPvUv();
//        profileEnd("findApiPvUv");

        apiStatsRepository.printProfiles();

//        DimensionData<Long> pvDimensionData = visitsByDeviceType.getPvDimensionData();
//        DimensionData<Long> uvDimensionData = visitsByDeviceType.getUvDimensionData();
//
//        System.out.printf("%s (%s)%n", pvDimensionData.getName(), pvDimensionData.getType());
//        for (Long aLong : pvDimensionData.getValues()) {
//            System.out.println("  " + aLong);
//        }
//        System.out.printf("%s (%s)%n", uvDimensionData.getName(), uvDimensionData.getType());
//        for (Long aLong : uvDimensionData.getValues()) {
//            System.out.println("  " + aLong);
//        }
    }

//    long start = System.currentTimeMillis();
//    long time = System.currentTimeMillis();
//    Map<String, Long> profile = new HashMap<>();
//
//    private void profileBegin(String name) {
//        time = System.currentTimeMillis();
//        profile.put(name, time);
//    }
//
//    private void profileEnd(String name) {
//        profile.put(name, System.currentTimeMillis() - profile.get(name));
//    }
//
//    private void printProfiles() {
//        for (String name : profile.keySet()) {
//            System.out.printf("%30s=%s%n", name, (float) profile.get(name) / 1000);
//        }
//
//        long interval = System.currentTimeMillis() - start;
//        System.out.printf("总耗时：%fs%n", (float) interval / (float) 1000);
//    }
}
