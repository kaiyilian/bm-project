package com.bumu.arya.salary.calculate.context;

import org.apache.commons.lang3.Range;

import java.util.HashMap;
import java.util.Map;

/**
 * 薪资计算的所有配置，通过 SalaryContext 传递
 * TODO 还有更多配置
 * Created by allen on 2017/7/6.
 */
public class StandardSalaryConfig extends BaseSalaryConfig implements SalaryConfig{

    public StandardSalaryConfig(GlobalConfig globalConfig) {
        super(globalConfig);
    }

}
