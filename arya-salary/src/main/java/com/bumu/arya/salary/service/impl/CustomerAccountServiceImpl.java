package com.bumu.arya.salary.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.command.CustomerAccountTotalQueryCommand;
import com.bumu.arya.salary.command.CustomerAccountUpdateCommand;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.dao.CustomerAccountDao;
import com.bumu.arya.salary.dao.CustomerDao;
import com.bumu.arya.salary.dao.CustomerSalaryRuleDao;
import com.bumu.arya.salary.dao.mybatis.CustomerAccountTotalMybatisDao;
import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.arya.salary.model.entity.CustomerSalaryRuleEntity;
import com.bumu.arya.salary.result.CustomerAccountResult;
import com.bumu.arya.salary.result.CustomerAccountTotalQueryResult;
import com.bumu.arya.salary.result.CustomerAccountTotalResult;
import com.bumu.arya.salary.service.CustomerAccountService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import com.bumu.common.result.PagerResult;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bumu.function.VoConverter.logger;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/10
 */
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerSalaryRuleDao customerSalaryRuleDao;

    @Autowired
    private SalaryFileService salaryFileService;

    @Autowired
    private CustomerAccountTotalMybatisDao customerAccountTotalMybatisDao;

    @Override
    public synchronized void create(CustomerAccountEntity createCustomerAccountEntity, List<CustomerAccountEntity> deleteCustomerAccountEntity) {
        if (!check(createCustomerAccountEntity)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_ACCOUNT_IS_WRONG);
        }
        //customerAccountDao.persist(createCustomerAccountEntity);

        // 客户
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(createCustomerAccountEntity.getCustomerId());
        if (null == customerEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL);
        }
        if (StringUtils.isAnyBlank(customerEntity.getRuleId())) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ADD_DEPARTMENT_RULE_FIRST);
        }
        // 客户对应的计算规则
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
        if (null == customerSalaryRuleEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
        }

        Boolean isReduceFee = customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.defined
                && customerSalaryRuleEntity.getCostBearing() == SalaryEnum.CostBearing.company ? true : false;

        BigDecimal remainAccount = new BigDecimal(StringUtils.isAnyBlank(customerEntity.getRemainAccount()) ? "0" : customerEntity.getRemainAccount());

        //如果需要有删除的 需要进行删除。（重新导入会走这一步操作）
        if (CollectionUtils.isNotEmpty(deleteCustomerAccountEntity)) {
            for (CustomerAccountEntity customerAccountEntity : deleteCustomerAccountEntity) {
                customerAccountEntity.setIsDelete(Constants.TRUE);
                if (StringUtils.isAnyBlank(customerAccountEntity.getAccountAmount())) {
                    remainAccount = remainAccount.subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getTransAccountAmount()) ? "0" : createCustomerAccountEntity.getTransAccountAmount())
                            .subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getPersonalTaxFee()) ? "0" : createCustomerAccountEntity.getPersonalTaxFee()))
                            .subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getSalaryAfterTax()) ? "0" : createCustomerAccountEntity.getSalaryAfterTax())));
                    if (isReduceFee) {
                        remainAccount.add(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getSalaryFee()) ? "0" : createCustomerAccountEntity.getSalaryFee()));
                    }
                } else {
                    remainAccount = remainAccount.subtract(new BigDecimal(customerAccountEntity.getAccountAmount()));
                }
            }

            if (CollectionUtils.isNotEmpty(deleteCustomerAccountEntity)) {
                customerAccountDao.update(deleteCustomerAccountEntity);
            }
        }



        logger.info("计算薪资余额");
        //List<CustomerAccountEntity> customerAccountEntityList = customerAccountDao.getList(createCustomerAccountEntity.getCustomerId());

        //公式：用户所有的台账记录-》 到账金额 - (根据是否需要减薪资服务费)薪资服务费 - 税前薪资
        /*for (CustomerAccountEntity entity : customerAccountEntityList) {
            remainAccount = remainAccount.add(new BigDecimal(StringUtils.isAnyBlank(entity.getTransAccountAmount()) ? "0" : entity.getTransAccountAmount()))
                    .subtract(new BigDecimal(StringUtils.isAnyBlank(entity.getSalaryBeforeTax()) ? "0" : entity.getSalaryBeforeTax()));
            if (isReduceFee) {
                remainAccount = remainAccount.subtract(new BigDecimal(StringUtils.isAnyBlank(entity.getSalaryFee()) ? "0" : entity.getSalaryFee()));
            }
        }*/
        //本次操作产生的金额交易
        BigDecimal accountAmount = new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getTransAccountAmount()) ? "0" : createCustomerAccountEntity.getTransAccountAmount())
                .subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getPersonalTaxFee()) ? "0" : createCustomerAccountEntity.getPersonalTaxFee()))
                .subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getSalaryAfterTax()) ? "0" : createCustomerAccountEntity.getSalaryAfterTax()));
        if (isReduceFee) {
            accountAmount = accountAmount.subtract(new BigDecimal(StringUtils.isAnyBlank(createCustomerAccountEntity.getSalaryFee()) ? "0" : createCustomerAccountEntity.getSalaryFee()));
        }
        //加上本次的操作
        remainAccount = remainAccount.add(accountAmount);

        //更新台账记录的余额
        createCustomerAccountEntity.setAccountAmount(accountAmount.toString());
        createCustomerAccountEntity.setRemainAccount(remainAccount.toString());
        customerAccountDao.create(createCustomerAccountEntity);

        //更新用户余额
        customerEntity.setRemainAccount(remainAccount.toString());
        customerDao.update(customerEntity);
    }

    private Boolean check(CustomerAccountEntity customerAccountEntity) {
        try{
            //核对到账金额，以免充值时输入的金额为不可识别的金额。
            //其他字段经过系统计算，无意外基本是数字，所以不进行验证，如果后期有需要可以追加验证
            BigDecimal transAccountAmount = new BigDecimal(customerAccountEntity.getTransAccountAmount());
            if (StringUtils.isAnyBlank(customerAccountEntity.getCustomerId())) {
                logger.info("客户ID不能为空");
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            logger.info("金额验证不通过");
            return false;
        }
    }

    @Override
    public PagerResult<CustomerAccountResult> pageAccount(String customerId, String yearMonth, Integer page, Integer pageSize) throws Exception {
        Pager<CustomerAccountEntity> pager = customerAccountDao.getPager(customerId, yearMonth, page, pageSize);
        PagerResult<CustomerAccountResult> result = new PagerResult<>();
        if (CollectionUtils.isEmpty(pager.getResult())) {
            return result;
        }
        result.setPage(pager.getPage());
        result.setRowCount(pager.getRowCount());
        result.setPageSize(pager.getPageSize());
        result.setResult(pager.getResult().stream().map(
                entity -> {
                    CustomerAccountResult customerAccountResult = new CustomerAccountResult();
                    customerAccountResult.convert(entity);
                    return customerAccountResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void updateAccount(CustomerAccountUpdateCommand customerAccountUpdateCommand) {
        CustomerAccountEntity customerAccountEntity = customerAccountDao.findByIdNotDelete(customerAccountUpdateCommand.getId());
        customerAccountUpdateCommand.begin(customerAccountEntity, new SessionInfo());
        customerAccountDao.update(customerAccountEntity);
    }

    @Override
    public List<CustomerAccountEntity> salaryAccountList(String customerId, Long week) {
        return customerAccountDao.salaryAccountList(customerId, week);
    }

    @Override
    public CustomerAccountTotalResult customerAccountTotalList(Long startTime, Long endTime, Integer page, Integer pageSize) {
        CustomerAccountTotalResult result = new CustomerAccountTotalResult();
        CustomerAccountTotalQueryCommand customerAccountTotalCommand = new CustomerAccountTotalQueryCommand();
        customerAccountTotalCommand.setStartTime(startTime);
        customerAccountTotalCommand.setEndTime(new DateTime(endTime).plusDays(1).getMillis());
        List<CustomerAccountTotalQueryResult> resultList = customerAccountTotalMybatisDao.findList(customerAccountTotalCommand);
        result.setResultList(resultList);

        if (null != page && null != pageSize) {
            int total = resultList.size();
            result.setTotalRows(resultList.size());
            result.setPages(Utils.calculatePages(total, pageSize));
            result.setResultPager(resultList.subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
        }

        CustomerAccountTotalQueryResult resultCount = new CustomerAccountTotalQueryResult();
        for (CustomerAccountTotalQueryResult queryResult : resultList) {
            BigDecimal personalTax = new BigDecimal(queryResult.getPersonalTaxFeeTotal()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal salaryAfterTax = new BigDecimal(queryResult.getSalaryAfterTaxTotal()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal beforeTaxTotal = new BigDecimal(queryResult.getSalaryBeforeTaxTotal()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal salaryFee = new BigDecimal(queryResult.getSalaryFeeTotal()).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal transAccountAmount = new BigDecimal(queryResult.getTransAccountAmountTotal()).setScale(2, BigDecimal.ROUND_HALF_UP);

            queryResult.setPersonalTaxFeeTotal(personalTax.toString());
            queryResult.setSalaryAfterTaxTotal(salaryAfterTax.toString());
            queryResult.setSalaryBeforeTaxTotal(beforeTaxTotal.toString());
            queryResult.setSalaryFeeTotal(salaryFee.toString());
            queryResult.setTransAccountAmountTotal(transAccountAmount.toString());

            resultCount.setPersonalTaxFeeTotal(new BigDecimal(resultCount.getPersonalTaxFeeTotal()).add(personalTax).toString());
            resultCount.setSalaryAfterTaxTotal(new BigDecimal(resultCount.getSalaryAfterTaxTotal()).add(salaryAfterTax).toString());
            resultCount.setSalaryBeforeTaxTotal(new BigDecimal(resultCount.getSalaryBeforeTaxTotal()).add(beforeTaxTotal).toString());
            resultCount.setSalaryFeeTotal(new BigDecimal(resultCount.getSalaryFeeTotal()).add(salaryFee).toString());
            resultCount.setTransAccountAmountTotal(new BigDecimal(resultCount.getTransAccountAmountTotal()).add(transAccountAmount).toString());
        }
        result.setResultCount(resultCount);
        return result;
    }
}
