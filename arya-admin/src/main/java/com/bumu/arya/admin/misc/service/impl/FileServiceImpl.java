package com.bumu.arya.admin.misc.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.impl.BaseExcelFileServiceImpl;
import com.bumu.arya.admin.misc.service.FileService;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.service.ReadFileResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author CuiMengxin
 * @date 2016/3/28
 */
@Service
public class FileServiceImpl extends BaseExcelFileServiceImpl implements FileService {

    Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    ReadFileResponseService readFileResponseService;

    @Autowired
    AryaAdminConfigService configService;

    @Autowired
    SysUserService sysUserService;

    @Override
    public String saveAdsPic(MultipartFile file) throws AryaServiceException {
        String fileName = Utils.makeUUID() + file.getOriginalFilename().substring
                (file.getOriginalFilename().lastIndexOf("."));
        String filePath = configService.getAdsImagePath() + fileName;
        try {
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FILE_SAVE_FAILD);
        }
        return fileName;
    }


    /**
     * 临时存储文件
     *
     * @param file
     * @param type
     * @return
     * @throws AryaServiceException
     */
    public String tempSaveFile(MultipartFile file, Integer type) throws AryaServiceException {
        return tempSaveFile(file, type, null);
    }

    @Override
    public String tempSaveFile(MultipartFile file, Integer type, String suffix) throws AryaServiceException {
        String fileName = Utils.makeUUID();
        String filePath = null;
        filePath = configService.getFilePath("temp", type) + fileName + "." + suffix;
        log.info("filePath: " + filePath);
        try {
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_IMAGE_SAVE_FAILED);
        }
        return fileName;
    }


    @Override
    public String getTempFileUrl(String fileName, Integer type) throws AryaServiceException {
        return configService.getFilePath("temp", type) + fileName + ".jpg";
    }


}