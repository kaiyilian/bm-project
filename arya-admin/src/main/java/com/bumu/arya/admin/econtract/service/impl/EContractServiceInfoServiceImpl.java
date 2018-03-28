package com.bumu.arya.admin.econtract.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.econtract.controller.command.EContractServiceInfoCommand;
import com.bumu.arya.admin.econtract.result.EContractServiceInfoResult;
import com.bumu.arya.admin.econtract.model.EContractServiceInfoVo;
import com.bumu.arya.admin.econtract.service.EContractServiceInfoService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.econtract.service.EContractCorpService;
import com.bumu.common.service.FileUploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@Service
public class EContractServiceInfoServiceImpl implements EContractServiceInfoService {

    private static Logger logger = LoggerFactory.getLogger(EContractServiceInfoServiceImpl.class);

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EContractCorpService eContractCorpService;

    @Override
    public EContractServiceInfoResult getDetail(String aryaCorpId) throws Exception {
        Assert.notBlank(aryaCorpId, "公司ID必填");
        logger.info("aryaCorpId: " + aryaCorpId);
        CorporationEntity corporationEntity = corporationDao.findCorporationById(aryaCorpId);
        Assert.notNull(corporationEntity, "没有查询到公司");
        EContractServiceInfoResult eContractServiceInfoResult = new EContractServiceInfoResult();
        EContractServiceInfoVo info = new EContractServiceInfoVo();
        SysUtils.copyProperties(info, corporationEntity);

        eContractServiceInfoResult.setInfo(info);
        if (StringUtils.isNotBlank(corporationEntity.geteCorpLicenseFileName())) {
            eContractServiceInfoResult.setUrl(fileUploadService.generateFileUrl(
                    "temp",
                    corporationEntity.geteCorpLicenseFileName(),
                    0,
                    "jpg"
            ));
        }
        return eContractServiceInfoResult;
    }

    @Override
    public void submit(EContractServiceInfoCommand command) throws Exception {
        logger.info("aryaCorpId: " + command.getAryaCorpId());
        CorporationEntity corporationEntity = corporationDao.findCorporationById(command.getAryaCorpId());
        Assert.notNull(corporationEntity, "没有查询到公司");
        EContractServiceInfoVo eContractServiceInfoVo = command.getInfo();
        SysUtils.copyProperties(corporationEntity, eContractServiceInfoVo);

        if (corporationEntity.getBusinessType() == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "未开通电子合同服务");
        }

        logger.info("判断是否调用云签注册企业接口");
        logger.info("command.getBusinessType().intValue(): " + corporationEntity.getBusinessType().intValue());
        logger.info("corporationEntity.getRegisteredInYunSign(): " + corporationEntity.getRegisteredInYunSign());

        if ((corporationEntity.getBusinessType().intValue() & CorpConstants.CORP_BUSINESS_ECONTRACT) > 0
                && (corporationEntity.getRegisteredInYunSign() == null
                || !corporationEntity.getRegisteredInYunSign())) {
            logger.info("如果选择了电子合同企业, 调用云签注册企业的接口");

            String jsonResult = eContractCorpService.userRegist(
                    corporationEntity.getId(),
                    EContractCorpService.userType.corp.ordinal() + 1,
                    EContractCorpService.userLeave.admin.ordinal()
            );
            Map<String, String> resultMap = SysUtils.jsonToMap(jsonResult);
            if (!"1000".equals(resultMap.get("code"))) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "云签错误:" + resultMap.get("msg"));
            }
            corporationEntity.setRegisteredInYunSign(true);
            logger.info("云签注册成功");
        }
        corporationDao.update(corporationEntity);
    }
}
