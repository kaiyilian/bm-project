package com.bumu.arya.admin.econtract.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.econtract.controller.command.EContractSealCommand;
import com.bumu.arya.admin.econtract.service.EContractSealService;
import com.bumu.arya.admin.misc.service.FileService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.FileUploadEnum;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.ListUtils;
import com.bumu.common.util.MultipartFileUtils;
import com.bumu.econtract.model.dao.EContractSealDao;
import com.bumu.econtract.model.entity.EContractSealEntity;
import com.bumu.econtract.result.EContractSealResult;
import com.bumu.econtract.service.EContractCorpService;
import com.bumu.exception.AryaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2017/6/1
 */
@Service
public class EContractSealServiceImpl implements EContractSealService {

    private static Logger logger = LoggerFactory.getLogger(EContractSealServiceImpl.class);

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private EContractSealDao eContractSealDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileService fileService;

    @Autowired
    private EContractCorpService eContractCorpService;

    @Override
    public List<BaseResult.IDResult> batchAdd(EContractSealCommand.EContractSealAdd command) throws Exception {

        List<BaseResult.IDResult> idResults = new ArrayList<>();

        List<EContractSealEntity> sealEntities = eContractSealDao.findByAryaCorpId(command.getAryaCorpId());
        if (sealEntities.size()+command.getSealFileNames().size()>10) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR,"一个公司最多添加10个印章");
        }

        for (String fileName : command.getSealFileNames()) {
            logger.info("调用云签上传接口");

            String resultJson = eContractCorpService.uploadSeal(command.getAryaCorpId(),
                    MultipartFileUtils.create(new File(fileService.getTempFileUrl(fileName,
                            FileUploadEnum.fileType.eContractTemplateFile.ordinal()))));

            Map<String, Object> resultMap = SysUtils.jsonToMap(resultJson);
            if (!"1000".equals(resultMap.get("code"))) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, (String) resultMap.get("msg"));
            }
            Map<String, Object> resultDataMap = (Map<String, Object>) SysUtils.jsonTo(resultMap.get("result").toString(), ArrayList.class).get(0);
            logger.info("创建电子合同印章entity: ");
            CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(command.getAryaCorpId());

            EContractSealEntity eContractSealEntity = new EContractSealEntity();
            eContractSealEntity.setId(Utils.makeUUID());
            eContractSealEntity.setAryaCorpId(command.getAryaCorpId());
            eContractSealEntity.setBranCorpId(corporationEntity.getBranCorpId());
            eContractSealEntity.setAryaCorpName(corporationEntity.getName());
            eContractSealEntity.setFileName(fileName);
            eContractSealEntity.setCreateTime(System.currentTimeMillis());
            eContractSealEntity.setCreateUser(command.getAryaCorpId());
            eContractSealEntity.setYunSignSealId(null == resultDataMap.get("sealId")
                    ? null : String.valueOf(resultDataMap.get("sealId")));
            eContractSealEntity.setYunSignSealNum(null == resultDataMap.get("sealNum")
                    ? null : String.valueOf(resultDataMap.get("sealNum")));
            eContractSealDao.persist(eContractSealEntity);


            BaseResult.IDResult idResult = new BaseResult.IDResult();
            idResult.setId(eContractSealEntity.getId());
            idResults.add(idResult);
        }

        return idResults;
    }

    @Override
    public void batchDelete(BaseCommand.BatchIds command) throws Exception {

        for (BaseCommand.IDCommand IDCommand : command.getBatch()) {
            EContractSealEntity eContractSealEntity = eContractSealDao.findByIdNotDelete(IDCommand.getId());
            eContractSealEntity.setUpdateTime(System.currentTimeMillis());
            eContractSealEntity.setIsDelete(1);
            eContractSealDao.update(eContractSealEntity);
        }
    }

    @Override
    public Pager<EContractSealResult> getPageList(String aryaCorpId, Integer page, Integer pageSize) {
        Pager<EContractSealResult> resultPager = new Pager<>();
        List<EContractSealResult> list = new ArrayList<>();
        Pager<EContractSealEntity> pager = eContractSealDao.findPaginationByAryaCorpId(aryaCorpId, page, pageSize);
        resultPager.setPageSize(pager.getPageSize());
        resultPager.setRowCount(pager.getRowCount());
        if (ListUtils.checkNullOrEmpty(pager.getResult())) {
            return resultPager;
        }

        for (EContractSealEntity entity : pager.getResult()) {
            EContractSealResult eContractSealResult = new EContractSealResult();
            eContractSealResult.setUploadTime(entity.getCreateTime());
            eContractSealResult.setUrl(fileUploadService.generateFileUrl("temp", entity.getFileName(), 3, "jpg"));
            eContractSealResult.setAryaCorpName(entity.getAryaCorpName());
            eContractSealResult.setTxVersion(entity.getTxVersion());
            eContractSealResult.setId(entity.getId());
            list.add(eContractSealResult);
        }

        resultPager.setResult(list);
        return resultPager;
    }
}
