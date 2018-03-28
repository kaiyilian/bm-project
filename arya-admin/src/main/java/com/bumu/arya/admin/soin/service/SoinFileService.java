package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.result.SoinOrderBillExcelFileReadResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface SoinFileService {


    /**
     * 保存订单对账单计算文件并返回文件名
     *
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String saveOrderCalculateExcelFile(MultipartFile file) throws AryaServiceException;


    /**
     * 读取订单对账单Excel文件
     *
     * @param fileName
     * @return
     * @throws AryaServiceException
     */
    SoinOrderBillExcelFileReadResult readSoinOrderBillCalculateExcelFile(String fileName) throws AryaServiceException;

}
