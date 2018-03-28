package com.bumu.arya.admin.model.dao;

import com.bumu.common.ApiLogger;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.bumu.common.ApiLogger.logApiAccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test_ac_arya_admin.xml", "/ac_mongo.xml"})
public class ApiStatsMockTest {

//    ApiLogger apiLogger;

    @Test
    public void test() {
        for (int j = 0; j < 999; j++) {
            for (int i = 0; i < RandomUtils.nextInt(2000, 5000); i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                makeOneRandomLog();
            }
            System.out.println("wait for a moment...");
            try {
                Thread.sleep(RandomUtils.nextInt(600, 3000) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeOneRandomLog() {
        String randomWebAppName = RandomStringUtils.random(4, "abcdefghijklmopqrstuvwxyz");
        String randomApi = "api/mock/" + RandomStringUtils.random(2, "abcdefghijklmo");
        String randomAppVersion = "v" + RandomStringUtils.randomNumeric(1);
        String randomImei = RandomStringUtils.random(6, "ab");
        try {
            ApiLogger.ApiLog apiLog = new ApiLogger.ApiLog();
            apiLog.setMsg("http://localhost/" + randomApi);
            apiLog.setWebAppName(randomWebAppName);
            apiLog.setApi(randomApi);
            apiLog.setAppVersion(randomAppVersion);
            apiLog.setAppVersionCode(String.valueOf(99));
            apiLog.setImei(randomImei);
            apiLog.setDeviceType(System.currentTimeMillis() % 2 == 0 ? 1 : 2);
            apiLog.setDeviceName(RandomStringUtils.random(4, "ab"));
            apiLog.setUserId("xxxxxxxxxxxxxx");
            apiLog.setLocation("苏州");
            apiLog.setOsVersion("Android 6.1");
            apiLog.setChannel(RandomStringUtils.random(2, "xyz"));
            logApiAccess(apiLog);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("记录访问日志出错: %s - %s", "http://localhost/" + randomApi, e.getMessage()));
        }
        System.out.println("-");
    }
}
