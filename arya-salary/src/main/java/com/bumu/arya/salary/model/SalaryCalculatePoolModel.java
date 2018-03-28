package com.bumu.arya.salary.model;

import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.salary.model.entity.CustomerDistrictEntity;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import com.bumu.engine.excelimport.model.ICModel;
import com.bumu.engine.excelimport.model.ICResult;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
public class SalaryCalculatePoolModel {

    public String customerId;
    //错误的数据
    public List<ICModel> errImportCheckResult = new ArrayList<>();
    //需要新增的客户-地区数据
    public List<CustomerDistrictEntity> newCustomerDistrictEntityList = new ArrayList<>();
    //客户-地区List
    public List<CustomerDistrictEntity> customerDistrictEntityList = new ArrayList<>();
    //public Map<String, CustomerDistrictEntity> customerDistrictEntityMap = new HashMap<>();
    //地区Map key：地区名称 value地区的值
    public Map<String, DistrictEntity> districtEntityMap = new HashedMap();
    //客户-地区Map key：地区名称 value：地区客户
    public Map<String, List<String>> customerDistrictEntityMap = new HashMap<>();
    //导入引擎过滤成功的数据
    public List<ICModel> dataModelList = new ArrayList<>();
    //导入引擎过滤结果
    public ICResult importResultModel = new ICResult();
    //身份证:importCheckResult
    public Map<String, ICModel> idCardRowMap = new HashMap<>();

    public Map<String, SalaryExcelMode> idCardExcelMap = new HashMap<>();
    //身份证List
    public List<String> idCardList = new ArrayList<>();

    public Map<String, SalaryUserEntity> userEntityMap;

    /**
     * 薪资计算规则
     */
    public SalaryCalculateRuleModel salaryCalculateRuleModel = new SalaryCalculateRuleModel();

    /**
     * 客户地区List 转化成MAP
     * @param list
     * @return
     */
    /*public Map<String, CustomerDistrictEntity> customerDistrictEntityListToMap(List<CustomerDistrictEntity> list) {
        Map<String, CustomerDistrictEntity> map = new HashMap<>();
        list.stream().forEach(entity -> {
            map.put(entity.getDistrictName(), entity);
        });
        return map;
    }*/

    public Map<String, List<String>> customerDistrictEntityListToMap(List<CustomerDistrictEntity> list) {
        Map<String, List<String>> result = new HashMap<>();
        for (CustomerDistrictEntity entity : list) {
            if (null == result.get(entity.getDistrictName())) {
                List<String> customerDistrictNameList = new ArrayList<>();
                customerDistrictNameList.add(entity.getCustomerDistrictName());
                result.put(entity.getDistrictName(), customerDistrictNameList);
            } else {
                result.get(entity.getDistrictName()).add(entity.getCustomerDistrictName());
            }
        }
        return result;
    }

    public void importResultToFail(ICModel importCheckResult, String errInfo){
        importCheckResult.setSuccess(false);
        if (-1 == errImportCheckResult.indexOf(importCheckResult)) {
            importCheckResult.getErrStringList().add(errInfo);
            errImportCheckResult.add(importCheckResult);
        } else {
            errImportCheckResult.get(errImportCheckResult.indexOf(importCheckResult)).getErrStringList().add(errInfo);
        }
    }
}
