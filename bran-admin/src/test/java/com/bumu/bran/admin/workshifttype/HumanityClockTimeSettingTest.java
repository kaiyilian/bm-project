package com.bumu.bran.admin.workshifttype;

import com.bumu.bran.model.entity.workshifttype.HumanityClockTimeSettingEntity;
import com.bumu.exception.AryaServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 班次-设置-人性打卡-单元测试
 *
 * @author majun
 * @date 2017/9/20
 * @email 351264830@qq.com
 */
public class HumanityClockTimeSettingTest extends WorkShiftTypeSettingTest {

    private static Logger logger = LoggerFactory.getLogger(HumanityClockTimeSettingTest.class);

    /**
     * 默认的不设置人性打卡 测试迟到
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 12:30:00:00
     * 打卡时间 08:35:00:00-17:30:00:00
     * 迟到5分钟
     */
    @Test
    public void LateNotSet() {
        workShiftTypeEntity.setHumanityClockTimeSettingEntity(null);
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 35, 0, 0));
        workAttendanceLateCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLate() == 5);
    }

    /**
     * 设置人性打卡 测试迟到 迟到时间>人性打卡时间
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 2
     * 打卡时间 08:35:00:00-17:30:00:00
     * 迟到3分钟
     */
    @Test
    public void lateMinutesGeClockTime() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowLateMinutes(2);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 35, 0, 0));
        workAttendanceLateCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLate() == 3);
    }

    /**
     * 设置人性打卡 测试迟到 迟到时间<人性打卡时间
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 10
     * 打卡时间 08:35:00:00-17:30:00:00
     * 迟到0分钟
     */
    @Test
    public void lateMinutesLeClockTime() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowLateMinutes(10);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 35, 0, 0));
        workAttendanceLateCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLate() == 0);
    }

    /**
     * 设置人性打卡 测试迟到设置范围 = 边界值测试
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 30
     * 打卡时间 08:35:00:00-17:30:00:00
     * 迟到0分钟
     */
    @Test
    public void lateMinutesEqualsMaxMinutes() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowLateMinutes(30);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 35, 0, 0));
        workAttendanceLateCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLate() == 0);
    }

    /**
     * 设置人性打卡 测试迟到设置范围 > 最大值 边界值测试
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 31
     * 打卡时间 08:35:00:00-17:30:00:00
     * 抛出异常
     */
    @Test
    public void lateMinutesGeMaxMinutes() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowLateMinutes(31);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);
        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 35, 0, 0));
        thrown.expect(AryaServiceException.class);
        workAttendanceLateCalculate.withDetailCalculate(workAttendanceDetailEntity);
    }


    /**
     * 默认的不设置人性打卡 测试早退
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡早退时间:不设置
     * 打卡时间 08:30:00:00-17:25:00:00
     * 早退5分钟
     */
    @Test
    public void leaveEarlyNotSet() {
        workShiftTypeEntity.setHumanityClockTimeSettingEntity(null);
        workDateRange.setEndDateTime(workDateRange.getStartDateTime().withTime(17, 25, 0, 0));
        workAttendanceLeaveEarlyCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLeaveEarly() == 5);
    }

    /**
     * 设置人性打卡 测试早退 早退时间>人性打卡时间
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡早退时间 2
     * 打卡时间 08:30:00:00-17:25:00:00
     * 早退3分钟
     */
    @Test
    public void leaveEarlyMinutesGeClockTime() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowEarlyLeaveMinutes(2);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 25, 0, 0));
        workAttendanceLeaveEarlyCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLeaveEarly() == 3);
    }

    /**
     * 设置人性打卡 测试迟到 早退时间<人性打卡时间
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡早退时间 10
     * 打卡时间 08:30:00:00-17:25:00:00
     * 早退0分钟
     */
    @Test
    public void leaveEarlyMinutesLeClockTime() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowEarlyLeaveMinutes(10);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 25, 0, 0));
        workAttendanceLeaveEarlyCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLeaveEarly() == 0);
    }

    /**
     * 设置人性打卡 测试早退设置范围 = 边界值测试
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 30
     * 打卡时间 08:00:00:00-17:25:00:00
     * 迟到0分钟
     */
    @Test
    public void leaveEarlyMinutesEqualsMaxMinutes() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowEarlyLeaveMinutes(30);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 25, 0, 0));
        workAttendanceLeaveEarlyCalculate.withDetailCalculate(workAttendanceDetailEntity);
        Assert.assertTrue(workAttendanceDetailEntity.getLeaveEarly() == 0);
    }

    /**
     * 设置人性打卡 测试早退设置范围 > 最大值 边界值测试
     * 班次 08:30:00:00-17:30:00:00
     * 人性打卡 31
     * 打卡时间 08:00:00:00-17:25:00:00
     * 抛出异常
     */
    @Test
    public void leaveEarlyMinutesGeMaxMinutes() {
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
        humanityClockTimeSettingEntity.setAllowEarlyLeaveMinutes(31);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(1);

        workShiftTypeEntity.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 25, 0, 0));
        thrown.expect(AryaServiceException.class);
        workAttendanceLeaveEarlyCalculate.withDetailCalculate(workAttendanceDetailEntity);
    }
}
