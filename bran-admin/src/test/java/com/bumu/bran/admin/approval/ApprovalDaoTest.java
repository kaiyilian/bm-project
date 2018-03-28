package com.bumu.bran.admin.approval;

import com.bumu.approval.command.ApprovalManagerQueryCommand;
import com.bumu.approval.model.dao.ApprovalDao;
import com.bumu.approval.model.entity.ApprovalEntity;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 审批dao相关测试
 *
 * @author majun
 * @date 2017/10/17
 * @email 351264830@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:approval/test_approval_spring.xml",
        "classpath*:ac_datasource.xml",
        "classpath:approval/test_approval_trx.xml"
})
@Transactional
public class ApprovalDaoTest {

    private static Logger logger = LoggerFactory.getLogger(ApprovalDaoTest.class);

    @Autowired
    private ApprovalDao approvalDao;

    /**
     * 用户、考勤、公司情况等
     */
    @Before
    public void init() {
    }

    /**
     * 添加一条上班补卡审批记录
     */
    @Test
    @Rollback(false)
    public void addWorkInApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("overtime");
        approvalEntity.setEmpId("5de184c71e8c417dae912cf7f2858877");
        /*approvalEntity.setWorkAttendanceId("088f9f0a812243ea8c27a4510aa2b172");*/
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        approvalEntity.setApprovalState(0);
        approvalEntity.setApprovalType(1);
        approvalEntity.setApprovalTypeDetail(10);
        approvalEntity.setStartTime(new DateTime(2017, 9, 16, 8, 40, 0).toDate());
        approvalEntity.setEndTime(new DateTime(2017, 9, 16, 9, 30, 0).toDate());
        approvalEntity.setTotalMinutes(0);
        approvalEntity.setReason("请假");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(000L);
        approvalEntity.setApprovalTime(new DateTime(2017, 9, 15,00,0,0).toDate());
        approvalEntity.setApprovalNo("20170928000");
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("overtime"));
    }

    /**
     * 添加一条下班补卡审批记录
     */
    @Test
    @Rollback(false)
    public void addWorkOutApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("workOut");
        approvalEntity.setEmpId("900a14fcb9a24545a423e6931ff950e5");
        approvalEntity.setWorkAttendanceId("088f9f0a812243ea8c27a4510aa2b172");
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        approvalEntity.setApprovalState(0);
        approvalEntity.setApprovalType(2);
        approvalEntity.setApprovalTypeDetail(14);
        approvalEntity.setStartTime(null);
        approvalEntity.setEndTime(null);
        approvalEntity.setTotalMinutes(0);
        approvalEntity.setReason("下班补卡");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(002L);
        approvalEntity.setApprovalNo("20171023002");
        approvalEntity.setStartTime(new DateTime(2017, 10, 18, 17, 30).toDate());
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("workOut"));
    }

    /**
     * 添加一条当天请假记录
     */
    @Test
    @Rollback(false)
    public void addCurDayLeaveApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("curDayLeave");
        approvalEntity.setEmpId("900a14fcb9a24545a423e6931ff950e5");
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        // 未通过
        approvalEntity.setApprovalState(0);
        // 请假
        approvalEntity.setApprovalType(0);
        // 调休
        approvalEntity.setApprovalTypeDetail(0);
        approvalEntity.setStartTime(new DateTime(2017, 10, 18, 9, 0).toDate());
        approvalEntity.setEndTime(new DateTime(2017, 10, 18, 10, 0).toDate());
        approvalEntity.setTotalMinutes(60);
        approvalEntity.setReason("当天请假一小时");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(004L);
        approvalEntity.setApprovalNo("20171023004");
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("curDayLeave"));
    }

    /**
     * 添加一条明天请假记录
     */
    @Test
    @Rollback(false)
    public void addFutureDayLeaveApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("futureDayLeave");
        approvalEntity.setEmpId("900a14fcb9a24545a423e6931ff950e5");
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        // 未通过
        approvalEntity.setApprovalState(0);
        // 请假
        approvalEntity.setApprovalType(0);
        // 调休
        approvalEntity.setApprovalTypeDetail(0);
        approvalEntity.setStartTime(new DateTime(2017, 10, 25, 9, 0).toDate());
        approvalEntity.setEndTime(new DateTime(2017, 10, 25, 10, 0).toDate());
        approvalEntity.setTotalMinutes(60);
        approvalEntity.setReason("明天请假一小时");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(005L);
        approvalEntity.setApprovalNo("20171023005");
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("futureDayLeave"));
    }

    /**
     * 添加一条曾经某一天的请假记录
     */
    @Test
    @Rollback(false)
    public void addOneDayLeaveApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("oneDayLeave");
        approvalEntity.setEmpId("900a14fcb9a24545a423e6931ff950e5");
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        // 未通过
        approvalEntity.setApprovalState(0);
        // 请假
        approvalEntity.setApprovalType(0);
        // 调休
        approvalEntity.setApprovalTypeDetail(0);
        approvalEntity.setStartTime(new DateTime(2017, 10, 24, 9, 0).toDate());
        approvalEntity.setEndTime(new DateTime(2017, 10, 24, 10, 0).toDate());
        approvalEntity.setTotalMinutes(60);
        approvalEntity.setReason("曾经请假一小时");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(approvalDao.findMaxSubApprovalNoByBranCorpId("dd1b6d20dd214fe296c8593537d9f124").getApprovalSubNo() + 1);
        approvalEntity.setApprovalNo("20171023" + approvalEntity.getApprovalSubNo());
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("oneDayLeave"));
    }

    /**
     * 添加一条加班记录
     */
    @Test
    @Rollback(false)
    public void addOverTimeApproval() {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setId("overTime");
        approvalEntity.setEmpId("900a14fcb9a24545a423e6931ff950e5");
        approvalEntity.setWorkAttendanceId("088f9f0a812243ea8c27a4510aa2b172");
        approvalEntity.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        // 未通过
        approvalEntity.setApprovalState(0);
        // 加班
        approvalEntity.setApprovalType(1);
        // 平日加班
        approvalEntity.setApprovalTypeDetail(10);
        approvalEntity.setStartTime(new DateTime(2017, 10, 18, 20, 0).toDate());
        approvalEntity.setEndTime(new DateTime(2017, 10, 18, 21, 0).toDate());
        approvalEntity.setTotalMinutes(60);
        approvalEntity.setReason("加班一小时");
        approvalEntity.setApplyTime(new DateTime().toDate());
        approvalEntity.setApprovalSubNo(004L);
        approvalEntity.setApprovalNo("20171023004");
        approvalDao.createOrUpdate(approvalEntity);
        Assert.assertNotNull(approvalDao.findByIdNotDelete("overTime"));
    }

    @Test
    public void findMaxSubApprovalNoByBranCorpIdTest() {
        ApprovalEntity approvalEntity = approvalDao.findMaxSubApprovalNoByBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        Assert.assertNotNull(approvalEntity);
    }

    @Test
    public void findViewByCommandTest() {
        ApprovalManagerQueryCommand approvalManagerQueryCommand = new ApprovalManagerQueryCommand();
        approvalManagerQueryCommand.setBranCorpId("dd1b6d20dd214fe296c8593537d9f124");
        approvalManagerQueryCommand.setQueryStartTime(new DateTime(2017,10,24,0,0,0).getMillis());
        approvalManagerQueryCommand.setQueryEndTime(new DateTime(2017,10,24,23,23,23).getMillis());
        List<ApprovalEntity> list = approvalDao.findViewByCommand(approvalManagerQueryCommand);
        Assert.assertTrue(list.size() == 22);
    }
}
