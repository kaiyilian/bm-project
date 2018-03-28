package com.bumu.arya.admin.payroll.service;

import com.bumu.arya.admin.payroll.controller.command.ESalaryCommand;
import com.bumu.arya.admin.payroll.result.PayrollManagerResult;
import com.bumu.arya.admin.payroll.result.PayrollDetailResult;
import com.bumu.arya.command.OrderCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liangjun on 17-7-26.
 */
@Transactional
public interface PayrollService {

    PayrollManagerResult getCorpUser(ESalaryCommand eSalaryCommand, PagerCommand pagerCommand, OrderCommand orderCommand) throws Exception;

    List<PayrollDetailResult> getSalaryInfo(String corpId);

    String downloadFile(String salaryId);

    void downloadFile(HttpServletResponse response,String realFileName,String fileName)throws AryaServiceException;

}
