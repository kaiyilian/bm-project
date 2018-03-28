package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.SalaryBillUpdateCommand;
import com.bumu.arya.salary.model.entity.SalaryBillEntity;
import com.bumu.arya.salary.result.SalaryBillResult;
import com.bumu.arya.salary.result.SalaryCalculateCountResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Transactional
public interface SalaryBillService {

    List<SalaryBillResult> getList(String condition);

    Pager<SalaryBillResult> getPager(String condition, Integer page, Integer pageSize);

    void update(SalaryBillUpdateCommand salaryBillUpdateCommand);

    List<SalaryBillEntity> getAll();

    void delete(List<String> ids);

    void saveBillApply(List<SalaryCalculateCountResult> salaryCalculateResultList, String customerId);

}
