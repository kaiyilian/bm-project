package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.ErrLogExportCommand;
import com.bumu.arya.salary.command.SalaryExportCommand;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.result.ContractUploadResult;
import com.bumu.arya.salary.result.CustomerAccountTotalResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.FileUploadFileResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/21
 */
@Transactional
public interface SalaryFileService {

    void downTemplate(String customerID, HttpServletResponse response);

    FileUploadFileResult templateFile(String customerId);

    FileUploadFileResult exportSalaryFile(SalaryExportCommand salaryExportCommand) throws Exception;

    ContractUploadResult uploadCustomerContract(MultipartFile file);

    List<ContractUploadResult> uploadCustomerContracts(MultipartFile[] files, String customerId);

    void customerContractsZip(String customerId) throws Exception;

    /**
     * 获取下载合同的地址
     * @param customerId
     */
    FileUploadFileResult downloadContractUrl(String customerId);

    void downloadCustomerContract(String customerId, HttpServletResponse response);

    void readCustomerContract(String fileId, String suffix, String customerId, HttpServletResponse response);

    void saveSalaryCalculateExcelFile(String fileName, MultipartFile file) throws AryaServiceException;

    File readSalaryCalculateExcelFile(String fileName) throws Exception;

    void readSalaryFile(String fileId, String type, SalaryEnum.exportType exportType, String customerId, HttpServletResponse response);

    FileUploadFileResult exportErrLog(ErrLogExportCommand errLogExportCommand);

    FileUploadFileResult exportAccount(String customerId, String yearMonth) throws Exception;

    FileUploadFileResult exportCustomer() throws Exception;

    FileUploadFileResult exportSalaryBill() throws Exception;

    FileUploadFileResult exportCustomerAccountTotal(CustomerAccountTotalResult customerAccountTotalResult) throws Exception;
}
