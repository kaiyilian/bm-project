package com.bumu.bran.admin.workshifttype;

import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.bran.attendance.engine.builder.WorkAttendanceInvalidBuilder;
import com.bumu.bran.attendance.engine.calculate.WorkAttendanceLateCalculate;
import com.bumu.bran.attendance.engine.calculate.WorkAttendanceLeaveEarlyCalculate;
import com.bumu.bran.attendance.engine.calculate.WorkAttendanceWorkInLackCalculate;
import com.bumu.bran.attendance.engine.calculate.WorkAttendanceWorkOutLackCalculate;
import com.bumu.bran.model.entity.attendance.WorkAttendanceDetailEntity;
import com.bumu.common.command.DateTimeRange;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 班次设置
 *
 * @author majun
 * @date 2017/9/20
 * @email 351264830@qq.com
 */
public class WorkShiftTypeSettingTest {

    private static Logger logger = LoggerFactory.getLogger(WorkShiftTypeSettingTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    protected WorkShiftTypeEntity workShiftTypeEntity = null;
    protected WorkAttendanceInvalidBuilder workAttendanceInvalidBuilder = null;
    protected WorkAttendanceWorkInLackCalculate workAttendanceWorkInLackCalculate = null;
    protected WorkAttendanceWorkOutLackCalculate workAttendanceWorkOutLackCalculate = null;
    protected DateTimeRange workDateRange = null;
    protected WorkAttendanceLateCalculate workAttendanceLateCalculate = null;
    protected WorkAttendanceLeaveEarlyCalculate workAttendanceLeaveEarlyCalculate = null;
    protected WorkAttendanceDetailEntity workAttendanceDetailEntity = null;


    @Before
    public void init() {
        workShiftTypeEntity = new WorkShiftTypeEntity();
        workAttendanceDetailEntity = new WorkAttendanceDetailEntity();
        // 无效打卡
        workAttendanceInvalidBuilder = new WorkAttendanceInvalidBuilder();
        // 上班缺卡
        workAttendanceWorkInLackCalculate = new WorkAttendanceWorkInLackCalculate();
        // 下班缺卡
        workAttendanceWorkOutLackCalculate = new WorkAttendanceWorkOutLackCalculate();
        // 迟到
        workAttendanceLateCalculate = new WorkAttendanceLateCalculate();
        // 早退
        workAttendanceLeaveEarlyCalculate = new WorkAttendanceLeaveEarlyCalculate();

        // 设置计算考勤的日期
        DateTime targetTime = new DateTime();
        workShiftTypeEntity.setTargetTime(targetTime);
        workShiftTypeEntity.setWorkStartTime("08:30");
        workShiftTypeEntity.setWorkEndTime("17:30");
        // 设置上下班打卡日期
        workDateRange = new DateTimeRange();
        DateTime workStart = new DateTime();
        workStart = workStart.withTime(6, 29, 59, 59);
        DateTime workEnd = new DateTime();
        workEnd = workEnd.withTime(17, 30, 59, 59);
        workDateRange.setStartDateTime(workStart);
        workDateRange.setEndDateTime(workEnd);
        // 设置参数
        workAttendanceInvalidBuilder.setModifyDateRange(workDateRange);
        workAttendanceInvalidBuilder.setWorkShiftTypeEntity(workShiftTypeEntity);
        workAttendanceWorkInLackCalculate.setModifyDateRange(workDateRange);
        workAttendanceWorkInLackCalculate.setWorkShiftTypeEntity(workShiftTypeEntity);
        workAttendanceWorkOutLackCalculate.setModifyDateRange(workDateRange);
        workAttendanceWorkOutLackCalculate.setWorkShiftTypeEntity(workShiftTypeEntity);
        workAttendanceLateCalculate.setModifyDateRange(workDateRange);
        workAttendanceLateCalculate.setWorkShiftTypeEntity(workShiftTypeEntity);
        workAttendanceLateCalculate.setDetailEntity(workAttendanceDetailEntity);
        workAttendanceLeaveEarlyCalculate.setModifyDateRange(workDateRange);
        workAttendanceLeaveEarlyCalculate.setWorkShiftTypeEntity(workShiftTypeEntity);
        workAttendanceLateCalculate.setDetailEntity(workAttendanceDetailEntity);
        logger.info("初始化参数");

    }
}
