package com.bumu.bran.admin.attendance;

import com.bumu.bran.attendance.model.dao.WorkAttendanceDao;
import com.bumu.bran.model.entity.attendance.WorkAttendanceEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/10/24
 * @email 351264830@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:attendance/test_attendance_spring.xml",
        "classpath*:ac_datasource.xml",
        "classpath:attendance/test_attendance_trx.xml"
})
@Transactional
public class AttendanceDaoTest {

    @Autowired
    private WorkAttendanceDao workAttendanceDao;

    /**
     * 增加一条考勤记录信息
     */
    @Test
    @Rollback(value = false)
    public void addWorkAttendance() {
        WorkAttendanceEntity workAttendanceEntity = new WorkAttendanceEntity();
        workAttendanceEntity.setId("junitTest");
        workAttendanceEntity.setEmpId("");
        workAttendanceEntity.setWorkShiftTypeId("");
        workAttendanceEntity.setWorkAttendanceDetailId("");
        workAttendanceEntity.setWorkAttendanceClockSettingRelationId("");
//        workAttendanceEntity.setAttendDay();
//        workAttendanceEntity.setAttendCurDayState();
//        workAttendanceEntity.setAttendStartState();
//        workAttendanceEntity.setAttendEndState();
//        workAttendanceEntity.setAttendStartTime();
//        workAttendanceEntity.setAttendEndTime();
    }
}
