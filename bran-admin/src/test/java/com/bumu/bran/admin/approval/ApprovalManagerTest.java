package com.bumu.bran.admin.approval;

import com.bumu.approval.helper.ApprovalHelper;
import com.bumu.bran.approval.engine.ApprovalEngine;
import com.bumu.bran.attendance.model.dao.AttendceDetailDao;
import com.bumu.bran.attendance.model.dao.WorkAttendanceDao;
import com.bumu.bran.model.entity.attendance.WorkAttendanceDetailEntity;
import com.bumu.bran.model.entity.attendance.WorkAttendanceEntity;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 审批管理相关测试
 *
 * @author majun
 * @date 2017/10/24
 * @email 351264830@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:common/test_common_spring.xml",
        "classpath*:ac_datasource.xml",
        "classpath:common/test_common_trx.xml",
        "classpath*:ac_mybatis.xml",
        "classpath*:ac_mongodb.xml",
        "classpath:common/test_common_schedule.xml",
        "classpath*:ac_common_sms.xml",
        "classpath:common/test_common_captcha.xml"
})
public class ApprovalManagerTest {

    private static Logger logger = LoggerFactory.getLogger(ApprovalDaoTest.class);

    @Autowired
    private ApprovalEngine approvalEngine;

    @Autowired
    private WorkAttendanceDao workAttendanceDao;

    @Autowired
    private AttendceDetailDao attendceDetailDao;

    @Autowired
    private ApprovalHelper approvalHelper;

    /**
     * 上班补卡通过
     */
    @Test
    @Rollback(false)
    public void passWorkInApproval() {
        approvalEngine.generate("workIn");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete("088f9f0a812243ea8c27a4510aa2b172");
        Assert.assertNotNull(workAttendanceEntity);
        Assert.assertNotNull(workAttendanceEntity.getAttendStartTime());
        DateTime startTime = new DateTime(workAttendanceEntity.getAttendStartTime());
        Assert.assertTrue(startTime.getMillis() == new DateTime(2017, 10, 18, 8, 30).getMillis());
    }

    /**
     * 下班补卡通过
     */
    @Test
    @Rollback(false)
    public void passWorkOutApproval() {
        approvalEngine.generate("workOut");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete("088f9f0a812243ea8c27a4510aa2b172");
        Assert.assertNotNull(workAttendanceEntity);
        Assert.assertNotNull(workAttendanceEntity.getAttendStartTime());
        Assert.assertNotNull(workAttendanceEntity.getAttendEndTime());
        DateTime startTime = new DateTime(workAttendanceEntity.getAttendStartTime());
        DateTime endTime = new DateTime(workAttendanceEntity.getAttendEndTime());
        Assert.assertTrue(startTime.getMillis() == new DateTime(2017, 10, 18, 8, 30).getMillis());
        Assert.assertTrue(endTime.getMillis() == new DateTime(2017, 10, 18, 17, 30).getMillis());
    }

    /**
     * 当天请假通过
     */
    @Test
    @Rollback(false)
    public void passCurDayLeaveApproval() {
        approvalEngine.generate("curDayLeave");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete("088f9f0a812243ea8c27a4510aa2b172");
        Assert.assertNotNull(workAttendanceEntity);
        WorkAttendanceDetailEntity workAttendanceDetailEntity = attendceDetailDao.findByDetailId(
                workAttendanceEntity.getWorkAttendanceDetailId());
        Assert.assertNotNull(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLeave() == 60);
    }

    /**
     * 当天加班通过
     */
    @Test
    @Rollback(false)
    public void passCurDayOverTimeApproval() {
        approvalEngine.generate("overTime");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete("088f9f0a812243ea8c27a4510aa2b172");
        Assert.assertNotNull(workAttendanceEntity);
        WorkAttendanceDetailEntity workAttendanceDetailEntity = attendceDetailDao.findByDetailId(
                workAttendanceEntity.getWorkAttendanceDetailId());
        Assert.assertNotNull(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getOverTime() == 90);
    }

    @Test
    public void generateApprovalNoTest() {
        // 删除全部的记录,第一条记录
        String appNo = approvalHelper.generateApprovalNo("dd1b6d20dd214fe296c8593537d9f124", new DateTime());
        Assert.assertEquals(appNo,"20171026000");
    }

    @Test
    public void generateApprovalNextNoTest() {
        // 添加一条记录
        String appNo = approvalHelper.generateApprovalNo("dd1b6d20dd214fe296c8593537d9f124", new DateTime());
        Assert.assertEquals(appNo,"20171026001");
    }
}
