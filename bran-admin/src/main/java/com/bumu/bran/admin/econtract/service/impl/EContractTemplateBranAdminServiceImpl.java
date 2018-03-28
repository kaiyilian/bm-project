package com.bumu.bran.admin.econtract.service.impl;

import com.bumu.common.util.ListUtils;
import com.bumu.common.constant.UploadFileTypes;
import com.bumu.bran.admin.econtract.result.EContractLoopsKeyValueResult;
import com.bumu.bran.admin.econtract.service.EContractTemplateBranAdminService;
import com.bumu.econtract.model.entity.EContractTemplateEntity;
import com.bumu.econtract.model.entity.EContractTemplateLoopsEntity;
import com.bumu.econtract.constant.EContractEnum;
import com.bumu.common.SessionInfo;
import com.bumu.econtract.model.dao.EContractTemplateDao;
import com.bumu.econtract.model.dao.EContractTemplateLoopsDao;
import com.bumu.econtract.result.EContractTemplateResult;
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
public class EContractTemplateBranAdminServiceImpl implements EContractTemplateBranAdminService {

    private static Logger logger = LoggerFactory.getLogger(EContractTemplateBranAdminServiceImpl.class);

    @Autowired
    private EContractTemplateDao eContractTemplateDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EContractTemplateLoopsDao eContractTemplateLoopsDao;

    @Override
    public List<EContractTemplateResult> getList(SessionInfo sessionInfo) {
        logger.info("查询模板: " + sessionInfo.getCorpId());
        List<EContractTemplateResult> eContractTemplateResults = new ArrayList<>();
        List<EContractTemplateEntity> entities = eContractTemplateDao.findByBranCorpId(sessionInfo.getCorpId());
        if (!ListUtils.checkNullOrEmpty(entities)) {
            entities.forEach(eContractEntity -> {
                logger.info("查询单个模板: " + eContractEntity.getId());
                EContractTemplateResult result = new EContractTemplateResult();
                result.convert(eContractEntity);
                result.setUrl(
                        fileUploadService.generateFileUrl(
                                "temp",
                                eContractEntity.getFileName(),
                                UploadFileTypes.E_CONTRACT_TEMPLATE_FILE,
                                "html"
                        )
                );
                eContractTemplateResults.add(result);
            });
        }
        return eContractTemplateResults;
    }

    @Override
    public List<EContractLoopsKeyValueResult> getLoops(String id, SessionInfo sessionInfo) {
        List<EContractLoopsKeyValueResult> loopsKeyValueResults = new ArrayList<>();

        List<EContractTemplateLoopsEntity> loopsEntities = eContractTemplateLoopsDao.findByTemplateId(id, EContractEnum.WriterType.first);
        if (ListUtils.checkNullOrEmpty(loopsEntities)) {
            return loopsKeyValueResults;
        }
        for (EContractTemplateLoopsEntity eContractTemplateLoopsEntity : loopsEntities) {
            EContractLoopsKeyValueResult eContractLoopsKeyValueResult = new EContractLoopsKeyValueResult();
            eContractLoopsKeyValueResult.setWriterType(eContractTemplateLoopsEntity.getWriterType().ordinal());
            eContractLoopsKeyValueResult.setKeyParam(eContractTemplateLoopsEntity.getLoopParam());
            eContractLoopsKeyValueResult.setKey(eContractTemplateLoopsEntity.getLoopName());
            eContractLoopsKeyValueResult.setId(eContractTemplateLoopsEntity.getId());
            loopsKeyValueResults.add(eContractLoopsKeyValueResult);
        }
        loopsKeyValueResults.sort(new EContractLoopsKeyValueResult()::compare);
        return loopsKeyValueResults;
    }
}
