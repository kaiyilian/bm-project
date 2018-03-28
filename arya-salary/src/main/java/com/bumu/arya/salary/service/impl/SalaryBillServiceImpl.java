package com.bumu.arya.salary.service.impl;

import com.bumu.common.util.ListUtils;
import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.command.SalaryBillUpdateCommand;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.dao.CustomerSalaryRuleDao;
import com.bumu.arya.salary.dao.SalaryBillDao;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.arya.salary.model.entity.CustomerSalaryRuleEntity;
import com.bumu.arya.salary.model.entity.SalaryBillEntity;
import com.bumu.arya.salary.result.*;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryBillService;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/10
 */
@Service
public class SalaryBillServiceImpl implements SalaryBillService {

    @Autowired
    private SalaryBillDao salaryBillDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSalaryRuleDao customerSalaryRuleDao;

    @Autowired
    private SysUserService sysUserService;


    @Override
    public List<SalaryBillResult> getList(String condition) {
        List<SalaryBillEntity> list = salaryBillDao.getList(condition);
        List<SalaryBillResult> result = new ArrayList<>();
        if (ListUtils.checkNullOrEmpty(list)) {
            return result;
        }
        result.addAll(list.stream().map(
                entity -> {
                    SalaryBillResult salaryBillResult = new SalaryBillResult();
                    SysUtils.copyProperties(salaryBillResult, entity);
                    return salaryBillResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Pager<SalaryBillResult> getPager(String condition, Integer page, Integer pageSize) {
        Pager<SalaryBillEntity> pager = salaryBillDao.getPager(condition, page, pageSize);
        Pager<SalaryBillResult> result = new Pager<>();
        if (ListUtils.checkNullOrEmpty(pager.getResult())) {
            return result;
        }
        result.setPageSize(pageSize);
        result.setRowCount(pager.getRowCount());
        result.setResult(pager.getResult().stream().map(
                entity -> {
                    SalaryBillResult salaryBillResult = new SalaryBillResult();
                    SysUtils.copyProperties(salaryBillResult, entity);
                    return salaryBillResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void update(SalaryBillUpdateCommand salaryBillUpdateCommand) {
        SalaryBillEntity salaryBillEntity = salaryBillDao.findByIdNotDelete(salaryBillUpdateCommand.getId());
        if (null == salaryBillEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "开票申请记录不存在");
        }
        SysUtils.copyProperties(salaryBillEntity, salaryBillUpdateCommand);
        salaryBillDao.update(salaryBillEntity);
    }

    @Override
    public List<SalaryBillEntity> getAll() {
        return salaryBillDao.findAllNotDelete();
    }

    @Override
    public void delete(List<String> ids) {
        List<SalaryBillEntity> salaryBillEntityList = salaryBillDao.findList(ids);
        for (SalaryBillEntity salaryBillEntity : salaryBillEntityList) {
            salaryBillEntity.setIsDelete(Constants.TRUE);
        }
        salaryBillDao.update(salaryBillEntityList);
    }

    @Override
    public void saveBillApply(List<SalaryCalculateCountResult> salaryCalculateCountResultList, String customerId) {
        SalaryCalculateCountResult total = salaryCalculateCountResultList.get(salaryCalculateCountResultList.size() - 1);
        CustomerEntity customerEntity = customerService.getOne(customerId);
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
        SalaryBillEntity salaryBillEntity = new SalaryBillEntity();
        salaryBillEntity.setId(Utils.makeUUID());
        salaryBillEntity.setIsDelete(Constants.FALSE);
        salaryBillEntity.setCreateUser(sysUserService.getCurrentSysUser().getId());
        salaryBillEntity.setCreateTime(System.currentTimeMillis());
        salaryBillEntity.setBillApplyDate(System.currentTimeMillis());
        salaryBillEntity.setCorpName(com.bumu.arya.salary.common.Constants.SALARY_CORP_NAME);
        salaryBillEntity.setCustomerName(customerEntity.getCustomerName());
        String totalMoney = "";
        if (customerEntity.getBillType() == SalaryEnum.BillType.fullFare || customerEntity.getBillType() == SalaryEnum.BillType.fullSheet){
            totalMoney = total.getTaxableSalaryTotal().add(
                    customerSalaryRuleEntity.getCostBearing() == SalaryEnum.CostBearing.company
                            ? total.getBrokerageTotal() : new BigDecimal(0))
                    .toString();
            salaryBillEntity.setManagerFee("");
        }
        if (customerEntity.getBillType() == SalaryEnum.BillType.balanceFare || customerEntity.getBillType() == SalaryEnum.BillType.balanceSheet){
            totalMoney = total.getNetSalaryTotal().add(total.getPersonalTaxTotal()).add(total.getBrokerageTotal()).toString();
            if (customerEntity.getBillProjectTwo() == SalaryEnum.BillProject.manager) {
                salaryBillEntity.setManagerFee(total.getPersonalTaxTotal().add(total.getBrokerageTotal()).toString());
            }
        }
        salaryBillEntity.setNetSalary(null == total.getNetSalaryTotal() ? "0" : total.getNetSalaryTotal().toString());
        salaryBillEntity.setPersonalTax(null == total.getPersonalTaxTotal() ? "0" : total.getPersonalTaxTotal().toString());
        salaryBillEntity.setTotalMoney(totalMoney);
        salaryBillDao.create(salaryBillEntity);
    }
}
