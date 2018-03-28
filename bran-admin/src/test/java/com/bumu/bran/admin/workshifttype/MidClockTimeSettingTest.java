package com.bumu.bran.admin.workshifttype;

import com.bumu.bran.model.entity.workshifttype.MidClockTimeSettingEntity;
import com.bumu.exception.AryaServiceException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 班次-设置-打卡中间点-单元测试
 *
 * @author majun
 * @date 2017/9/20
 * @email 351264830@qq.com
 */
public class MidClockTimeSettingTest extends WorkShiftTypeSettingTest {

    private static Logger logger = LoggerFactory.getLogger(MidClockTimeSettingTest.class);

    /**
     * 默认的中间打卡点
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 12:30:00:00
     */
    @Test
    public void defaultMidClockTimeSettingTest() {
        workShiftTypeEntity.setMidClockTimeSettingEntity(null);
        Assert.assertTrue(new DateTime().withTime(13, 0, 0, 0).getMillis() == workShiftTypeEntity.getMidClockTime().getMillis());
    }

    /**
     * 默认的中间打卡点
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 12:30:00:00
     */
    @Test
    public void defaultMidClockTimeStartTimeIsNull() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime(null);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(new DateTime().withTime(13, 0, 0, 0).getMillis() == workShiftTypeEntity.getMidClockTime().getMillis());
    }

    /**
     * 中间点不在打卡范围内
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:30:00:00
     */
    @Test
    public void notRange() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:30");
        midClockTimeSettingEntity.setIsNextDay(null);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        thrown.expect(AryaServiceException.class);
        workShiftTypeEntity.getMidClockTime();

    }

    /**
     * 中间点次日
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 明天8:30:00:00
     */
    @Test
    public void nextDayNotRange() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:29");
        midClockTimeSettingEntity.setIsNextDay(1);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        thrown.expect(AryaServiceException.class);
        workShiftTypeEntity.getMidClockTime();

    }

    /**
     * 正常设置中间点
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:31:00:00
     */
    @Test
    public void setNormalMidClockTime() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:31");
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(new DateTime().withTime(8, 31, 0, 0).getMillis() == workShiftTypeEntity.getMidClockTime().getMillis());
    }

    /**
     * 迟到范围
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:31:00:00
     * 迟到范围 08:30:00:00 -  8:31:59:59
     */
    @Test
    public void lateRange() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:31");
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(
                new DateTime().withTime(8, 30, 0, 0).getMillis() == workShiftTypeEntity.getTodayLateTimeRange().getStartDateTime().getMillis()
                        && new DateTime().withTime(8, 31, 59, 59).getMillis() == workShiftTypeEntity.getTodayLateTimeRange().getEndDateTime().getMillis()

        );
    }

    /**
     * 早退范围
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:31:00:00
     * 早退范围 8:31:00:00 - 17:30:59:59
     */
    @Test
    public void earlyLeaveRange() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:31");
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(
                new DateTime().withTime(8, 31, 0, 0).getMillis() == workShiftTypeEntity.getTodayLeaveEarlyTimeRange().getStartDateTime().getMillis()
                        && new DateTime().withTime(17, 30, 59, 59).getMillis() == workShiftTypeEntity.getTodayLeaveEarlyTimeRange().getEndDateTime().getMillis()

        );
    }

    /**
     * 上班缺卡
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:31:00:00
     * 打卡时间
     * 8:32:00:00 - 17:30:59:59
     */
    @Test
    public void workStartLack() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:31");
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 32, 00, 00));
        workAttendanceWorkInLackCalculate.setModifyDateRange(workDateRange);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(workAttendanceWorkInLackCalculate.isCalculate());
    }

    /**
     * 上班不缺卡
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 8:31:00:00
     * 打卡时间
     * 8:31:59:00 - 17:30:59:59
     */
    @Test
    public void workStartNotLack() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("8:31");
        workDateRange.setStartDateTime(workDateRange.getStartDateTime().withTime(8, 31, 59, 00));
        workAttendanceWorkInLackCalculate.setModifyDateRange(workDateRange);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertFalse(workAttendanceWorkInLackCalculate.isCalculate());
    }

    /**
     * 下班缺卡
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 17:29:00:00
     * 打卡时间
     * 8:31:00:00 - 17:28:59:59
     * 打卡中间点
     * 17:29:00:00 - 17:30:59:59 + 下班有效时间
     */
    @Test
    public void workEndLack() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("17:29");
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 28, 59, 59));
        workAttendanceWorkOutLackCalculate.setModifyDateRange(workDateRange);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertTrue(workAttendanceWorkOutLackCalculate.isCalculate());
    }

    /**
     * 下班不缺卡
     * 班次 08:30:00:00-17:30:00:00
     * 中间点 17:29:00:00
     * 打卡时间
     * 8:31:00:00 - 17:29:59:59
     * 打卡中间点
     * 17:29:00:00 - 17:30:59:59 + 下班有效时间
     */
    @Test
    public void workEndNotLack() {
        MidClockTimeSettingEntity midClockTimeSettingEntity = new MidClockTimeSettingEntity();
        midClockTimeSettingEntity.setStartTime("17:29");
        workDateRange.setEndDateTime(workDateRange.getEndDateTime().withTime(17, 29, 59, 59));
        workAttendanceWorkOutLackCalculate.setModifyDateRange(workDateRange);
        workShiftTypeEntity.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        Assert.assertFalse(workAttendanceWorkOutLackCalculate.isCalculate());
    }


}
