package com.bumu.bran.admin.workshifttype;

import com.bumu.bran.model.entity.workshifttype.ValidClockTimeSettingEntity;
import com.bumu.exception.AryaServiceException;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 班次-设置-有效打卡-单元测试
 *
 * @author majun
 * @date 2017/9/20
 * @email 351264830@qq.com
 */
public class ValidClockTimeSettingTest extends WorkShiftTypeSettingTest{

    private static Logger logger = LoggerFactory.getLogger(ValidClockTimeSettingTest.class);


    /**
     * 目的:上班开始前没有设置 validClockTimeSettingEntity == null 使用默认数据
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:120min
     * 打卡日期
     * 上班打卡:6:29
     * 下班打卡:17:30
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting01() {

        workShiftTypeEntity.setValidClockTimeSettingEntity(null);

        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为无效打卡");
    }


    /**
     * 目的:上班开始前没有设置 beforeMinutes == null 使用默认数据
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:120min
     * 打卡日期
     * 上班打卡:6:29
     * 下班打卡:17:30
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting02() {

        workShiftTypeEntity.setValidClockTimeSettingEntity(new ValidClockTimeSettingEntity());
        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为无效打卡");
    }


    /**
     * 目的:设置上班开始前 > 上班打卡时间
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:120min
     * 打卡日期
     * 上班打卡:6:29
     * 下班打卡:17:30
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting03() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setBeforeMinutes(120);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());
        logger.info("本次为无效打卡");
    }

    /**
     * 目的:设置上班开始前 = 上班打卡时间 边界值 正好在边界值
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:60*23
     * 打卡日期
     * 上班打卡:昨天的9:30
     * 下班打卡:17:30
     * 输出:true(有效打卡)
     */
    @Test
    public void validClockTimeSetting04() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setBeforeMinutes(validClockTimeSettingEntity.maxBeforeAndAfterMinutes);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();

        workStart = workStart.withTime(8, 30, 0, 0).minusHours(23);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);

        // 有效
        Assert.assertFalse(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为有效打卡");
    }

    /**
     * 目的:设置上班开始前 < 上班打卡时间 大于边界值
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:60*23+1
     * 打卡日期
     * 上班打卡:昨天的9:30
     * 下班打卡:17:30
     * 输出:抛出异常
     */
    @Test
    public void validClockTimeSetting09() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setBeforeMinutes(validClockTimeSettingEntity.maxBeforeAndAfterMinutes + 1);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();

        workStart = workStart.withTime(8, 30, 0, 0).minusHours(23);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);
        thrown.expect(AryaServiceException.class);
        workAttendanceInvalidBuilder.canBuilder();
        logger.info("本次设置抛出异常");

    }

    /**
     * 目的:设置上班开始前 <= 上班打卡时间 正常情况测试 上班有效打卡
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 上班开始前:120min
     * 打卡日期
     * 上班打卡:6:31
     * 下班打卡:17:30
     * 输出:true(有效打卡)
     */
    @Test
    public void validClockTimeSetting05() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setBeforeMinutes(120);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();
        workStart = workStart.withTime(6, 31, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);

        // 有效
        Assert.assertFalse(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为有效打卡");
    }

    /**
     * 目的:下班结束后没有设置 afterMinutes == null 使用默认数据
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 下班结束后:使用默认时间 2小时
     * 打卡日期
     * 上班打卡:6:30
     * 下班打卡:19:31
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting06() {
        workShiftTypeEntity.setValidClockTimeSettingEntity(null);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();
        workStart = workStart.withTime(6, 30, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);
        DateTime workEnd = workAttendanceInvalidBuilder.getModifyDateRange().getEndDateTime();
        workEnd = workEnd.withTime(19, 31, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setEndDateTime(workEnd);

        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为无效打卡");

    }

    /**
     * 目的:打卡时间 > 最大值 边界值测试
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 下班结束后:23小时
     * 打卡日期
     * 上班打卡:6:30
     * 下班打卡:10:31
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting07() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setAfterMinutes(300);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();
        workStart = workStart.withTime(6, 30, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);
        DateTime workEnd = workAttendanceInvalidBuilder.getModifyDateRange().getEndDateTime();
        workEnd = workEnd.withTime(22, 31, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setEndDateTime(workEnd);

        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为无效打卡");
    }

    /**
     * 目的:打卡时间 <= 最大值
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 下班结束后:300min
     * 打卡日期
     * 上班打卡:6:30
     * 下班打卡:10:30
     * 输出:true(有效打卡)
     */
    @Test
    public void validClockTimeSetting08() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setAfterMinutes(300);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();
        workStart = workStart.withTime(6, 30, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);
        DateTime workEnd = workAttendanceInvalidBuilder.getModifyDateRange().getEndDateTime();
        workEnd = workEnd.withTime(22, 30, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setEndDateTime(workEnd);

        // 有效
        Assert.assertFalse(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为有效打卡");

    }

    /**
     * 目的:设置下班有效时间 > 最大值
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 下班结束后:明天16:31
     * 打卡日期
     * 上班打卡:6:30
     * 下班打卡:10:30
     * 输出:抛出异常
     */
    @Test
    public void validClockTimeSetting10() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setAfterMinutes(validClockTimeSettingEntity.maxBeforeAndAfterMinutes + 1);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        thrown.expect(AryaServiceException.class);
        workAttendanceInvalidBuilder.canBuilder();
        logger.info("本次为抛出异常");
    }

    /**
     * 目的:下班打卡时间 > 下班打卡最大时间
     * 输入
     * 考勤计算日期 当天 now()
     * 班次
     * 上班时间:8:30
     * 下班时间:17:30
     * 下班结束后:明天16:30
     * 打卡日期
     * 上班打卡:6:30
     * 下班打卡:明天16:31
     * 输出:false(无效打卡)
     */
    @Test
    public void validClockTimeSetting11() {
        ValidClockTimeSettingEntity validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
        validClockTimeSettingEntity.setAfterMinutes(validClockTimeSettingEntity.maxBeforeAndAfterMinutes);
        workShiftTypeEntity.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        DateTime workStart = workAttendanceInvalidBuilder.getModifyDateRange().getStartDateTime();
        workStart = workStart.withTime(6, 30, 0, 0);
        workAttendanceInvalidBuilder.getModifyDateRange().setStartDateTime(workStart);
        DateTime workEnd = workAttendanceInvalidBuilder.getModifyDateRange().getEndDateTime();
        workEnd = workEnd.withTime(17, 31, 0, 0).plusHours(23);
        workAttendanceInvalidBuilder.getModifyDateRange().setEndDateTime(workEnd);

        // 无效
        Assert.assertTrue(workAttendanceInvalidBuilder.canBuilder());

        logger.info("本次为无效打卡");
    }
}
