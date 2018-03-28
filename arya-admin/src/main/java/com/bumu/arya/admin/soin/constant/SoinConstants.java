package com.bumu.arya.admin.soin.constant;

import java.math.BigDecimal;

public class SoinConstants {
    /**
     * 记录返回值状态
     */
    public static final int RESULT_NEW = 1;//新增
    public static final int RESULT_USER = 2;//使用用户给出的数据
    public static final int RESULT_WRONG = 4;//错误
    public static final int RESULT_DUPLICATE = 8;//重复
    /**
     * 读取excel单元格的数值时格式不正确
     */
    public static final BigDecimal NUMBER_INVALID_FORMAT = new BigDecimal("4444444444");
}
