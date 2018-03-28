package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.model.SalaryCalculatePoolModel;
import com.bumu.arya.salary.result.ErrLogResult;
import com.bumu.arya.salary.result.SalaryCalculateListResult;
import com.bumu.arya.salary.result.SalaryUserResult;
import com.bumu.common.result.Pager;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/12
 */
@Transactional
public interface SalaryCalculateService {

    SalaryCalculatePoolModel importBumuExcel(File salaryFile, CustomerSalaryCulateCommand customerSalaryCulateCommand) throws Exception;

    SalaryCalculateListResult calculate(CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel, Integer pageSize, Integer page);

    SalaryCalculateListResult query(String condition, String customerId, Integer settlementInterval, Integer year, Integer month, Long week, Integer pageSize, Integer page) throws Exception;

    void countSalaryCalculat(SalaryCalculateListResult salaryCalculateListResult, SalaryCalculatePoolModel salaryCalculatePoolModel);

    void saveData(SalaryCalculateListResult salaryCalculateListResult, SalaryCalculatePoolModel salaryCalculatePoolModel, CustomerSalaryCulateCommand customerSalaryCulateCommand, File file);

    Pager<ErrLogResult> errLogPage(String condition, Integer pageSize, Integer page) throws Exception;

    void errLogDelete(ErrLogDeleteCommand errLogDeleteCommand);

    void errLogUpdate(ErrLogUpdateCommand errLogUpdateCommand);

    SalaryUserResult userInfo(String id);

    void updateUser(UpdateSalaryUserInfoCommand command);

    void deleteSalarys(SalaryDeleteCommand salaryDeleteCommand);

    void checkImportWeek(CustomerSalaryCulateCommand customerSalaryCulateCommand);

    void deduct(SalaryServiceCommand salaryServiceCommand);

    List<Long> getWeeks(String customerId, Integer year, Integer month);

    void refreshDetailData(HttpServletRequest request);
}
