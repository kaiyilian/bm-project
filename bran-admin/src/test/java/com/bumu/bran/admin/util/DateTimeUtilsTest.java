package com.bumu.bran.admin.util;

import com.bumu.SysUtils;
import com.bumu.common.util.DateTimeUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类-单元测试
 *
 * @author majun
 * @date 2017/9/21
 * @email 351264830@qq.com
 */
public class DateTimeUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(DateTimeUtilsTest.class);

    /**
     * 两个时间分差,应该与秒无关
     */
    @Test
    public void minusMinutesTest() {
        DateTime start = new DateTime(2017, 9, 21, 13, 43, 59, 00);
        DateTime end = new DateTime(2017, 9, 21, 13, 44, 00, 00);
        Assert.assertTrue(DateTimeUtils.minusMinutes(start, end) == 1);
    }

    @Test
    public void getAgeByDate() {
        logger.info(SysUtils.getAgeByDate(new DateTime(2018, 04, 9, 0, 0, 0).toDate()) + "");
    }


    @Test
    public void timeToDate() {
        long start = 607996800000L;
        long end = 608083200000L;
        logger.info(SysUtils.getDateStringFormTimestamp(start, "yyyy.MM.dd"));
        logger.info(SysUtils.getDateStringFormTimestamp(end, "yyyy.MM.dd"));
    }
}
