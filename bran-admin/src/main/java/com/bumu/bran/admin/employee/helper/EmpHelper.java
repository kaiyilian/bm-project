package com.bumu.bran.admin.employee.helper;

import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.employee.helper.EmpCommonHelper;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by majun on 2016/7/27.
 */
@Component
public class EmpHelper extends EmpCommonHelper {
    private Logger logger = LoggerFactory.getLogger(EmpHelper.class);

    @Resource
    private BranAdminConfigService branAdminConfigService;

    @Autowired
    private BranUserDao branUserDao;



    @Override
    public void saveImg(String branUserId, String empId, String formFileName, String branCorpId, String desFileName, int type) {
        logger.info("start saveImg");
        String typePath = null;
        switch (type) {
            case 0: {
                // 身份证
                typePath = branAdminConfigService.getInductionUploadPath(branUserId);
                break;
            }
            case 1: {
                // 学历
                typePath = branAdminConfigService.getCertUploadPath(branUserId);
                break;
            }
            case 2: {
                // 离职
                typePath = branAdminConfigService.getLeaveUploadPath(branUserId);
                break;
            }
            case 3: {
                // 人脸识别照
                typePath = branAdminConfigService.getFaceFileUploadPath(branUserId);
                break;
            }
            case 4: {
                // 银行卡照片
                typePath = branAdminConfigService.getBankCardUploadPath(branUserId);
                break;
            }
        }
        File path = new File(typePath + formFileName + ".jpg");
        File des = new File(branAdminConfigService.getCorpPhotoPath(branCorpId, empId) + File.separator + desFileName + ".jpg");
        try {
            logger.info("path" + path);
            if (path.exists()) {
                logger.info("保存照片");
                logger.info("地址:" + des.getPath());
                FileUtils.copyFile(path, des);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AryaServiceException("复制照片错误");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException("系统错误");
        }
    }

    @Override
    public void saveImgEmp(String branUserId, String empId, String leaveId, String formFileName, String branCorpId) {
        logger.info("start saveImgEmp");
        String typePath = branAdminConfigService.getCorpPhotoPath(branCorpId, empId) + File.separator + formFileName + ".jpg";
        File path = new File(typePath);
        File des = new File(branAdminConfigService.getCorpPhotoPath(branCorpId, leaveId) + File.separator + formFileName + ".jpg");
        try {
            logger.info("path" + path);
            if (path.exists()) {
                logger.info("保存照片");
                logger.info("地址:" + des.getPath());
                FileUtils.copyFile(path, des);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AryaServiceException("复制照片错误");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException("系统错误");
        }
    }

    public String getRegisterAccount(EmployeeEntity employeeEntity) {
        if (StringUtils.isNotBlank(employeeEntity.getBranUserId())) {
            // 查询branUserId
            BranUserEntity branUserEntity = branUserDao.findByIdNotDelete(employeeEntity.getBranUserId());
            if (branUserEntity != null) {
                return branUserEntity.getTelephone();
            }
        }
        return employeeEntity.getRegisterAccount();
    }

}
