package com.bumu.arya.admin.econtract.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.econtract.controller.command.EContractTemplateCommand;
import com.bumu.arya.admin.econtract.service.EContractTemplateService;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.constant.UploadFileTypes;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.ListUtils;
import com.bumu.econtract.constant.EContractEnum;
import com.bumu.econtract.model.EContractTemplateLoopsAdd;
import com.bumu.econtract.model.EContractTemplateLoopsUpdate;
import com.bumu.econtract.model.dao.EContractTemplateDao;
import com.bumu.econtract.model.dao.EContractTemplateLoopsDao;
import com.bumu.econtract.model.entity.EContractTemplateEntity;
import com.bumu.econtract.model.entity.EContractTemplateLoopsEntity;
import com.bumu.econtract.result.EContractTemplateDetailResult;
import com.bumu.econtract.result.EContractTemplateResult;
import com.bumu.exception.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bumu.arya.common.Constants.TRUE;

/**
 * @author majun
 * @date 2017/6/1
 */
@Service
public class EContractTemplateServiceImpl implements EContractTemplateService {

    private static Logger logger = LoggerFactory.getLogger(EContractTemplateServiceImpl.class);

    @Autowired
    private EContractTemplateDao eContractTemplateDao;

    @Autowired
    private EContractTemplateLoopsDao eContractTemplateLoopsDao;

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public BaseResult.IDResult add(EContractTemplateCommand.EContractTemplateAdd command, SessionInfo sessionInfo) {

        logger.info("查询公司: " + sessionInfo.getCorpId());
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(sessionInfo.getCorpId());
        logger.info("根据aryaCorp查询branCorp: " + sessionInfo.getCorpId());
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpByIdThrow(corporationEntity.getBranCorpId());

        EContractTemplateEntity eContractTemplateEntity = new EContractTemplateEntity();
        logger.info("创建eContractTemplate");
        /*command.convert(
                eContractTemplateEntity,
                entity -> {
                    entity.setBranCorpName(branCorporationEntity.getCorpName());
                    entity.setBranCorpId(branCorporationEntity.getId());
                    command.begin(eContractTemplateEntity, sessionInfo);
                },
                eContractTemplateDao::persist
        );*/
        eContractTemplateEntity.setId(Utils.makeUUID());
        eContractTemplateEntity.setCreateTime(System.currentTimeMillis());
        eContractTemplateEntity.setCreateUser(sessionInfo.getUserId());
        eContractTemplateEntity.setAryaCorpId(command.getAryaCorpId());
        eContractTemplateEntity.setBranCorpId(branCorporationEntity.getId());
        eContractTemplateEntity.setBranCorpName(branCorporationEntity.getCorpName());
        eContractTemplateEntity.setContractType(EContractEnum.ContractType.values()[command.getContractType()]);
        eContractTemplateEntity.setFileName(command.getUploadFileId());
        eContractTemplateEntity.setYunSignTemplateId(command.getYunSignTemplateId());
        eContractTemplateDao.createOrUpdate(eContractTemplateEntity);

        /*command.getLoops().forEach(
                one -> {
                    logger.info("创建loops");
                    EContractTemplateLoopsEntity eContractTemplateLoopsEntity = new EContractTemplateLoopsEntity();
                    one.convert(
                            eContractTemplateLoopsEntity,
                            entity -> {
                                one.begin(eContractTemplateLoopsEntity, sessionInfo);
                                eContractTemplateLoopsEntity.seteContractTemplateId(eContractTemplateEntity.getId());
                            },
                            eContractTemplateLoopsDao::persist
                    );
                }
        );*/
        List<EContractTemplateLoopsAdd> list = command.getLoops();
        for(EContractTemplateLoopsAdd add:list){
            EContractTemplateLoopsEntity eContractTemplateLoopsEntity = new EContractTemplateLoopsEntity();
            eContractTemplateLoopsEntity.setId(Utils.makeUUID());
            eContractTemplateLoopsEntity.seteContractTemplateId(eContractTemplateEntity.getId());
            eContractTemplateLoopsEntity.setLoopName(add.getLoopName());
            eContractTemplateLoopsEntity.setLoopParam(add.getLoopParam());
            eContractTemplateLoopsEntity.setWriterType(EContractEnum.WriterType.values()[add.getWriterType()]);
            eContractTemplateLoopsDao.create(eContractTemplateLoopsEntity);
        }


        BaseResult.IDResult idResult = new BaseResult.IDResult();
        idResult.setId(eContractTemplateEntity.getId());
        return idResult;
    }

    @Override
    public void update(EContractTemplateCommand.EContractTemplateUpdate command, SessionInfo sessionInfo) {
        logger.info("查询合同模板");
        EContractTemplateEntity eContractTemplateEntity = eContractTemplateDao.findByIdNotDelete(command.getId());
        Assert.notNull(eContractTemplateEntity, "没有查询到合同模板");
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(sessionInfo.getCorpId());
        logger.info("根据aryaCorp查询branCorp: " + sessionInfo.getCorpId());
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpByIdThrow(corporationEntity.getBranCorpId());
        logger.info("更新合同模板");
        /*command.convert(
                eContractTemplateEntity,
                entity -> command.begin(eContractTemplateEntity, sessionInfo),
                entity -> {
                    entity.setBranCorpId(corporationEntity.getBranCorpId());
                    entity.setBranCorpName(branCorporationEntity.getCorpName());
                    eContractTemplateDao.update(entity);
                }
        );*/

        eContractTemplateEntity.setCreateTime(System.currentTimeMillis());
        eContractTemplateEntity.setCreateUser(sessionInfo.getUserId());
        eContractTemplateEntity.setAryaCorpId(command.getAryaCorpId());
        eContractTemplateEntity.setBranCorpId(branCorporationEntity.getId());
        eContractTemplateEntity.setBranCorpName(branCorporationEntity.getCorpName());
        eContractTemplateEntity.setContractType(EContractEnum.ContractType.values()[command.getContractType()]);
        eContractTemplateEntity.setFileName(command.getUploadFileId());
        eContractTemplateEntity.setYunSignTemplateId(command.getYunSignTemplateId());
        eContractTemplateDao.createOrUpdate(eContractTemplateEntity);

        logger.info("查询loops模板: " + eContractTemplateEntity.getId());
        List<EContractTemplateLoopsEntity> loopsEntities = eContractTemplateLoopsDao.findByTemplateId(eContractTemplateEntity.getId());
        if (!ListUtils.checkNullOrEmpty(loopsEntities)) {
            logger.info("删除旧的loops: ");
            eContractTemplateLoopsDao.delete(loopsEntities);
        }

        /*command.getLoops().forEach(
                one -> {
                    logger.info("创建loops");
                    EContractTemplateLoopsEntity eContractTemplateLoopsEntity = new EContractTemplateLoopsEntity();
                    one.convert(
                            eContractTemplateLoopsEntity,
                            entity -> {
                                one.begin(eContractTemplateLoopsEntity, sessionInfo);
                                eContractTemplateLoopsEntity.seteContractTemplateId(eContractTemplateEntity.getId());
                            },
                            eContractTemplateLoopsDao::persist
                    );
                }
        );*/
        List<EContractTemplateLoopsAdd> list = command.getLoops();
        for(EContractTemplateLoopsAdd add:list){
            EContractTemplateLoopsEntity eContractTemplateLoopsEntity = new EContractTemplateLoopsEntity();
            eContractTemplateLoopsEntity.setId(Utils.makeUUID());
            eContractTemplateLoopsEntity.seteContractTemplateId(eContractTemplateEntity.getId());
            eContractTemplateLoopsEntity.setLoopName(add.getLoopName());
            eContractTemplateLoopsEntity.setLoopParam(add.getLoopParam());
            eContractTemplateLoopsEntity.setWriterType(EContractEnum.WriterType.values()[add.getWriterType()]);
            eContractTemplateLoopsDao.create(eContractTemplateLoopsEntity);
        }
    }

    @Override
    public EContractTemplateDetailResult detail(String contractTemplateId) {
        EContractTemplateDetailResult eContractTemplateDetailResult = new EContractTemplateDetailResult();
        List<EContractTemplateLoopsUpdate> loopsResult = new ArrayList<>();
        logger.info("查询模板: " + contractTemplateId);
        EContractTemplateEntity eContractTemplateEntity = eContractTemplateDao.findByIdNotDelete(contractTemplateId);
        Assert.notNull(eContractTemplateEntity, "没有查询到模板: " + contractTemplateId);
        eContractTemplateDetailResult.convert(eContractTemplateEntity);
        logger.info("查询loops项");
        List<EContractTemplateLoopsEntity> loopsEntities = eContractTemplateLoopsDao.findByTemplateId(contractTemplateId);
        if (!ListUtils.checkNullOrEmpty(loopsEntities)) {
            loopsEntities.forEach(
                    entity -> {
                        EContractTemplateLoopsUpdate loopResult = new EContractTemplateLoopsUpdate();
                        loopResult.convert(entity);
                        loopsResult.add(loopResult);
                    }
            );
        }
        loopsResult.sort(new EContractTemplateLoopsUpdate()::compare);
        eContractTemplateDetailResult.setLoops(loopsResult);
        logger.info("查询url");
        eContractTemplateDetailResult.setUrl(
                fileUploadService.generateFileUrl(
                        "temp",
                        eContractTemplateEntity.getFileName(),
                        UploadFileTypes.E_CONTRACT_TEMPLATE_FILE,
                        "html"
                )
        );
        return eContractTemplateDetailResult;
    }

    @Override
    public Pager<EContractTemplateResult> getPageList(String aryaCorpId, Integer page, Integer pageSize) {
        Pager<EContractTemplateResult> pagerResult = new Pager<>();
        List<EContractTemplateResult> eContractTemplateResults = new ArrayList<>();
        logger.info("分页查询模板");
        Pager<EContractTemplateEntity> pagerEntity = eContractTemplateDao.findPaginationByAryaCorpId(aryaCorpId, page, pageSize);
        List<EContractTemplateEntity> eContractTemplateEntities = pagerEntity.getResult();
        if (!ListUtils.checkNullOrEmpty(eContractTemplateEntities)) {
            eContractTemplateEntities.forEach(eContractEntity -> {
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
        pagerResult.setResult(eContractTemplateResults);
        pagerResult.setRowCount(pagerEntity.getRowCount());
        pagerResult.setPageSize(pagerEntity.getPageSize());
        return pagerResult;
    }

    @Override
    public void batchDelete(BaseCommand.BatchIds command) {
        if (ListUtils.checkNullOrEmpty(command.getBatch())) {
            return;
        }
        command.getBatch().forEach(delete -> {
            logger.info("查询填写项: " + delete.getId());
            EContractTemplateEntity eContractTemplateEntity = eContractTemplateDao.findByIdNotDelete(delete.getId());
            if (Objects.nonNull(eContractTemplateEntity)) {
                logger.info("假删除模板");
                eContractTemplateEntity.setIsDelete(TRUE);
                eContractTemplateDao.update(eContractTemplateEntity);
            }
        });
    }
}
