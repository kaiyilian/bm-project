package com.bumu.arya.admin.misc.service;

import com.bumu.arya.admin.misc.result.CriminalRecordExcelReadResult;
import com.bumu.exception.AryaServiceException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public interface CriminalFileService {



    String saveCriminalRecordExcelFile(Workbook workbook, String fileName);





    /**
     * 保存犯罪记录批量查询并返回文件名
     *
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String saveCriminalRecorExcelFile(MultipartFile file) throws AryaServiceException;

    /**
     * 读取批量查询犯罪记录的excel文件
     *
     * @param fileName
     * @return
     * @throws AryaServiceException
     */
    CriminalRecordExcelReadResult readCriminalRecordExcelFile(String fileName) throws Exception;

}
