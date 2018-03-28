package com.bumu.bran.admin.econtract.service.impl;

import com.bumu.bran.admin.econtract.service.EContractSealBranAdminService;
import com.bumu.econtract.model.entity.EContractSealEntity;
import com.bumu.common.SessionInfo;
import com.bumu.econtract.model.dao.EContractSealDao;
import com.bumu.econtract.result.EContractSealResult;
import com.bumu.common.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2017/6/20
 */
@Service
public class EContractSealBranAdminServiceImpl implements EContractSealBranAdminService {

    private static Logger logger = LoggerFactory.getLogger(EContractSealBranAdminServiceImpl.class);

    @Autowired
    private EContractSealDao eContractSealDao;

    @Autowired
    private FileUploadService fileUploadService;


    @Override
    public List<EContractSealResult> getList(SessionInfo sessionInfo) {
        List<EContractSealResult> list = new ArrayList<>();
        List<EContractSealEntity> sealEntities = eContractSealDao.findByBranCorpId(sessionInfo.getCorpId());

        for (EContractSealEntity entity : sealEntities) {
            EContractSealResult eContractSealResult = new EContractSealResult();
            eContractSealResult.setUploadTime(entity.getCreateTime());
            eContractSealResult.setUrl(fileUploadService.generateFileUrl("temp", entity.getFileName(), 3, "jpg"));
            eContractSealResult.setAryaCorpName(entity.getAryaCorpName());
            eContractSealResult.setTxVersion(entity.getTxVersion());
            eContractSealResult.setId(entity.getId());
            list.add(eContractSealResult);
        }
        return list;
    }
}
