package com.bumu.arya.admin.salary.service;

import com.bumu.arya.admin.salary.model.CorpSalaryStatisticsStructure;
import com.bumu.arya.admin.salary.model.SalaryCalculateExcelFileReadResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.admin.salary.model.SalaryModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface SalaryFileService {


    /**
     * 存储薪资计算的Excel文件
     *
     * @param fileName
     * @param file
     */
    void saveSalaryCalculateExcelFile(String fileName, MultipartFile file) throws AryaServiceException;

    /**
     * 读取薪资计算的Excel文件
     *
     * @param fileName
     * @throws AryaServiceException
     */
    SalaryCalculateExcelFileReadResult readSalaryCalculateExcelFile(String fileName) throws AryaServiceException;

    /**
     * 生成薪资计算的Excel文件
     *
     * @return
     * @throws AryaServiceException
     */
    HSSFWorkbook generateSalaryCalculateExcelFile(List<SalaryModel> salaryModels) throws AryaServiceException;

    /**
     * 生成公司薪资统计Excel文件
     *
     * @param salaryStatisticsStructures
     * @return
     * @throws AryaServiceException
     */
    HSSFWorkbook generateSalaryCalculateStatisticsExcelFile(Collection<CorpSalaryStatisticsStructure> salaryStatisticsStructures) throws AryaServiceException;

    /**
     * 复制一份导出文件
     *
     * @param workbook
     * @param fileName
     */
    void copySalaryCalculateExportExcelFile(HSSFWorkbook workbook, String fileName);

}
