package com.bumu.arya.admin.service;

import com.bumu.arya.admin.devops.result.UserActivityResult;
import com.bumu.arya.admin.devops.result.UserActivityResult.UserActivity;
import com.bumu.arya.admin.devops.service.UserActivityService;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @deprecated 无法测试
 * @author Allen 2018-02-28
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test_ac_devops.xml", "/ac_mongo.xml", "/test_ac_trx.xml", "/ac_datasource.xml"})
@Aspect
public class ApiLogServiceTest {

    @Autowired
    UserActivityService userActivityService;

    @Test
    public void test() {
        UserActivityResult d = userActivityService.getActivitis();
        for (UserActivity userActivity : d.getUserActivities()) {
            System.out.println(userActivity.toString());
        }

    }
}
