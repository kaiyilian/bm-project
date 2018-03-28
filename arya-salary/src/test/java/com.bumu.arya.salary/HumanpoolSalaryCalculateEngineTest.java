package com.bumu.arya.salary;

import com.bumu.arya.salary.calculate.SalaryCalculateEngine;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.GlobalConfig;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.factor.general.SalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.*;
import com.bumu.arya.salary.calculate.model.InfoModel;
import com.bumu.arya.salary.calculate.model.InfoModel.Info;
import com.bumu.arya.salary.calculate.model.SalaryModel;
import com.bumu.arya.salary.calculate.suite.HumanpoolCalculateSuite;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 不木薪资计算测试 TODO
 * Created by allen on 2017/7/6.
 */
public class HumanpoolSalaryCalculateEngineTest {

    GlobalConfig globalConfig = new GlobalConfig();

    @Test
    public void test() {
        SalaryCalculateEngine calculateEngine = SalaryCalculateEngine.getInstance();

        List<SalaryModel> salaryModelList = new ArrayList<>();
        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("BASE_SALARY", new Value<Float>(2000f, "基本工资"));
            salaryModel.addValue("BASE_SALARY_OVERTIME", new Value<Float>(1900f, "加班基数"));
            salaryModel.addValue("STAFF_STATUS", new Value<String>("非新进离职", "员工状态(非新进离职|离职|新进)"));
            salaryModel.addValue("SCHEDULE_DAYS", new Value<Float>(21f, "当月应出勤天数(排班天数)"));
            salaryModel.addValue("ILL_DAYS", new Value<Float>(0f, "病假天数"));
            salaryModel.addValue("AFFAIR_DAYS", new Value<Float>(0f, "事假天数"));
            salaryModel.addValue("ABSENSE_DAYS", new Value<Float>(0f, "旷工天数"));
            salaryModel.addValue("ANNUAL_DAYS", new Value<Float>(0f, "年假天数"));
            salaryModel.addValue("PRECREATE_DAYS", new Value<Float>(0f, "产假天数"));
            salaryModel.addValue("MARRY_DAYS", new Value<Float>(0f, "婚丧假天数"));
            salaryModel.addValue("NEW_LEAVE_DAYS", new Value<Float>(0f, "新进离职/缺勤天数"));
            salaryModel.addValue("WORK_HOURS", new Value<Float>(168f, "正常出勤小时数（通过考勤表统计）"));
            salaryModel.addValue("WORKDAY_OVERTIME", new Value<Float>(58f, "平时加班工时"));
            salaryModel.addValue("WEEKEND_OVERTIME", new Value<Float>(19f, "休日加班工时"));
            salaryModel.addValue("NATIONAL_OVERTIME", new Value<Float>(0f, "国假加班工时"));

            // 多值 PLUS
            Set<Value<Float>> plusSet = new HashSet<>();
            plusSet.add(new Value<Float>(100f, "岗位津贴"));
            plusSet.add(new Value<Float>(450f, "绩效津贴"));
            plusSet.add(new Value<Float>(100f, "奖励金额"));
            plusSet.add(new Value<Float>(0f, "通讯补贴"));
            plusSet.add(new Value<Float>(0f, "员工生日福利"));
            plusSet.add(new Value<Float>(0f, "奖励金额"));
            plusSet.add(new Value<Float>(100f, "其他增项"));
            plusSet.add(new Value<Float>(0f, "体检费报销"));

            // 多值 SUBSTRACT
            Set<Value<Float>> subSet = new HashSet<>();
            subSet.add(new Value<Float>(0f, "惩罚金额"));
            subSet.add(new Value<Float>(0f, "离职物品扣款"));
            subSet.add(new Value<Float>(0f, "代扣房租"));
            subSet.add(new Value<Float>(0f, "代扣水电费"));
            subSet.add(new Value<Float>(0f, "社保公积金（个人）"));
            subSet.add(new Value<Float>(0f, "其他减项"));

            salaryModel.addMultiValue("PLUS", plusSet);
            salaryModel.addMultiValue("SUBSTRACT", subSet);
            salaryModelList.add(salaryModel);
        }

        {
            SalaryModel salaryModel = new SalaryModel();
            salaryModel.addValue("BASE_SALARY", new Value<Float>(2700f, "基本工资"));
            salaryModel.addValue("BASE_SALARY_OVERTIME", new Value<Float>(2500f, "加班基数"));
            salaryModel.addValue("STAFF_STATUS", new Value<String>("非新进离职", "员工状态(非新进离职|离职|新进)"));
            salaryModel.addValue("SCHEDULE_DAYS", new Value<Float>(21f, "当月应出勤天数(排班天数)"));
            salaryModel.addValue("ILL_DAYS", new Value<Float>(1f, "病假天数"));
            salaryModel.addValue("AFFAIR_DAYS", new Value<Float>(0f, "事假天数"));
            salaryModel.addValue("ABSENSE_DAYS", new Value<Float>(1f, "旷工天数"));
            salaryModel.addValue("ANNUAL_DAYS", new Value<Float>(1f, "年假天数"));
            salaryModel.addValue("PRECREATE_DAYS", new Value<Float>(1f, "产假天数"));
            salaryModel.addValue("MARRY_DAYS", new Value<Float>(1f, "婚丧假天数"));
            salaryModel.addValue("NEW_LEAVE_DAYS", new Value<Float>(0f, "新进离职/缺勤天数"));
            salaryModel.addValue("WORK_HOURS", new Value<Float>(168f, "正常出勤小时数（通过考勤表统计）"));
            salaryModel.addValue("WORKDAY_OVERTIME", new Value<Float>(30f, "平时加班工时"));
            salaryModel.addValue("WEEKEND_OVERTIME", new Value<Float>(20f, "休日加班工时"));
            salaryModel.addValue("NATIONAL_OVERTIME", new Value<Float>(10f, "国假加班工时"));

            // 多值 PLUS
            Set<Value<Float>> plusSet = new HashSet<>();
            plusSet.add(new Value<Float>(100f, "岗位津贴"));
            plusSet.add(new Value<Float>(450f, "绩效津贴"));
            plusSet.add(new Value<Float>(5f, "奖励金额"));
            plusSet.add(new Value<Float>(5f, "通讯补贴"));
            plusSet.add(new Value<Float>(5f, "员工生日福利"));
            plusSet.add(new Value<Float>(5f, "奖励金额"));
            plusSet.add(new Value<Float>(100f, "其他增项"));
            plusSet.add(new Value<Float>(5f, "体检费报销"));

            // 多值 SUBSTRACT
            Set<Value<Float>> subSet = new HashSet<>();
            subSet.add(new Value<Float>(0f, "惩罚金额"));
            subSet.add(new Value<Float>(5f, "离职物品扣款"));
            subSet.add(new Value<Float>(5f, "代扣房租"));
            subSet.add(new Value<Float>(5f, "代扣水电费"));
            subSet.add(new Value<Float>(150f, "社保公积金（个人）"));
            subSet.add(new Value<Float>(0f, "其他减项"));

            salaryModel.addMultiValue("PLUS", plusSet);
            salaryModel.addMultiValue("SUBSTRACT", subSet);
            salaryModelList.add(salaryModel);
        }


        HumanpoolCalculateSuite calculateSuite = new HumanpoolCalculateSuite();
        calculateSuite.setHourSalaryFactor(new HourSalaryFactor());
        calculateSuite.setWorkSalaryFactor(new WorkSalaryFactor());
        calculateSuite.setNewLeaveHoursFactor(new NewLeaveHoursFactor());
        calculateSuite.setWorkdayOvertimeSalaryFactor(new WorkdayOvertimeSalaryFactor());
        calculateSuite.setWeekendOvertimeSalaryFactor(new WeekendOvertimeSalaryFactor());
        calculateSuite.setNationalOvertimeSalaryFactor(new NationalOvertimeSalaryFactor());
        calculateSuite.setFulltimeBonusFactor(new FulltimeBonusFactor());
        calculateSuite.setAffairSubFactor(new AffairSubFactor());
        calculateSuite.setIllSubFactor(new IllSubFactor());
        calculateSuite.setAbsenseSubFactor(new AbsenseSubFactor());
        calculateSuite.setNewLeaveSubFactor(new NewLeaveSubFactor());
        calculateSuite.setSalaryBeforeFactor(new SalaryBeforeFactor());
        calculateSuite.setTaxFactor(new TaxFactor());
        calculateSuite.setHumanpoolSalaryAfterFactor(new HumanpoolSalaryAfterFactor());

        calculateSuite.init();

        HumanpoolSalaryConfig humanpoolSalaryConfig = new HumanpoolSalaryConfig(globalConfig);
        humanpoolSalaryConfig.setIllSubRatio(0.4f);


        List<SalaryModel> result = calculateEngine.calculate(calculateSuite, humanpoolSalaryConfig, salaryModelList);


        InfoModel calculateInfo = calculateEngine.getCalculateInfo();

        Set<Integer> infoRowNums = calculateInfo.getInfoRowNums();
        for (Integer i : infoRowNums) {
            List<Info> infos = calculateInfo.getInfos(i);
            for (Info info : infos) {
                System.out.println(info);
            }
        }

        Assert.assertEquals(2, result.size());

        for (SalaryModel model : result) {
            System.out.println();
            for (String k : model.getValues().keySet()) {
                System.out.printf("%s[%s]=%s%n",
                        StringUtils.rightPad(k, 24),
                        model.getValue(k).getTitle(),
                        model.getValue(k).getValue());
            }

            for (String s : model.getMultiValues().keySet()) {
                Set<Value<Float>> k = model.getMultiValue(s);
                for (Value<Float> floatValue : k) {
                    System.out.printf("%s[%s]=%s%n",
                            StringUtils.rightPad(s, 24),
                            floatValue.getTitle(),
                            floatValue.getValue());
                }
            }
        }

    }
}
