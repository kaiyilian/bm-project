package com.bumu.bran.admin.workshifttype;

import org.junit.Test;

/**
 * 班次-设置-休息-单元测试
 *
 * @author majun
 * @date 2017/9/20
 * @email 351264830@qq.com
 */
// todo 完善
public class RestTimeSettingTest extends WorkShiftTypeSettingTest {

    // 默认没有休息时间
    // 设置1个休息时间 在上班时间之内 计算休息时间
    // 设置1个休息时间 不在上班时间之内 计算休息时间
    // 设置2个休息时间 时间重叠 计算休息时间
    // 设置2个休息时间 时间不重叠 计算休息时间

    @Test
    public void test01(){

        org.junit.Assert.assertTrue("A".matches("^.{1,8}$"));

    }
}
