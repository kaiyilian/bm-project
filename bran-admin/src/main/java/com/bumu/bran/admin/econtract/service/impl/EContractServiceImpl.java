package com.bumu.bran.admin.econtract.service.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaTempUserDao;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.AryaTempUserEntity;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.admin.econtract.result.EContractDetailResult;
import com.bumu.bran.admin.econtract.result.EContractInfoResult;
import com.bumu.bran.admin.econtract.result.EContractLoopsKeyValueResult;
import com.bumu.bran.admin.econtract.result.EContractSignerResult;
import com.bumu.bran.admin.econtract.service.EContractService;
import com.bumu.bran.econtract.command.EContractCommand;
import com.bumu.bran.econtract.command.EContractSetStateCommand;
import com.bumu.bran.econtract.command.EContractUpdateCommand;
import com.bumu.bran.econtract.helper.EContractCorpHelper;
import com.bumu.bran.econtract.model.dao.EContractAryaUserDao;
import com.bumu.bran.econtract.model.dao.EContractDao;
import com.bumu.bran.econtract.model.dao.EContractValuesEntityDao;
import com.bumu.bran.econtract.model.dao.impl.EContractMybatisQuery;
import com.bumu.bran.econtract.model.dao.mybatis.EContractMybatisDao;
import com.bumu.bran.econtract.result.EContractResult2;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.constant.UploadFileTypes;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.BaseFileService;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.ListUtils;
import com.bumu.common.util.MultipartFileUtils;
import com.bumu.econtract.PagerHelper;
import com.bumu.econtract.constant.EContractEnum;
import com.bumu.econtract.model.dao.*;
import com.bumu.econtract.model.entity.*;
import com.bumu.econtract.service.EContractCorpService;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bumu.SysUtils.jsonToMap;
import static com.bumu.econtract.constant.EContractEnum.ContractState.executed;
import static com.bumu.econtract.constant.EContractEnum.ContractState.voided;
import static com.bumu.econtract.constant.EContractEnum.YunSignContractState.*;

/**
 * @author majun
 * @date 2016/6/20
 */
@Service
public class EContractServiceImpl implements EContractService {

    private static Logger logger = LoggerFactory.getLogger(EContractServiceImpl.class);

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private AryaTempUserDao aryaTempUserDao;

    @Autowired
    private EContractValuesEntityDao eContractValuesEntityDao;

    @Autowired
    private EContractDao eContractDao;

    @Autowired
    private EContractTemplateDao eContractTemplateDao;

    @Resource(name = "eContractCorpHelper")
    private EContractCorpHelper eContractHandler;

    @Autowired
    private EContractMybatisDao eContractMybatisDao;

    @Autowired
    private EContractTemplateLoopsDao eContractTemplateLoopsDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EContractSealDao eContractSealDao;

    @Autowired
    private EContractAryaUserDao eContractAryaUserDao;

    @Autowired
    private EContractCorpService eContractCorpService;

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Resource
    private EContractCorpHelper eContractCorpHelper;

    @Autowired
    private EContractTemplateLoopsBindDao eContractTemplateLoopsBindDao;

    @Autowired
    private EContractTemplateBindDao eContractTemplateBindDao;

    @Autowired
    private BaseFileService baseFileService;

    @Autowired
    private EContractSealBindDao eContractSealBindDao;

    @Autowired
    private ConfigService configService;

    @Override
    public void create(EContractCommand eContractCommand, SessionInfo sessionInfo) throws Exception {
        eContractHandler.build(eContractCommand, sessionInfo);
    }

    @Override
    public void update(EContractUpdateCommand eContractCommand, SessionInfo sessionInfo) throws Exception {
        logger.info("合同ID: " + eContractCommand.getId());
        logger.info("查询临时表用户");
        logger.info("删除临时表用户");
        aryaTempUserDao.delete(aryaTempUserDao.findByEContractId(eContractCommand.getId()));
        logger.info("查询签署人: ");
        logger.info("删除签署人表用户");
        eContractAryaUserDao.delete(eContractAryaUserDao.findByEContractId(eContractCommand.getId()));
        eContractHandler.build(eContractCommand, sessionInfo);

    }

    @Override
    public Pager<EContractResult2> getPageList(EContractMybatisQuery eContractMybatisQuery, Integer page, Integer pageSize, SessionInfo sessionInfo) {
        // 暂时使用前段分页,因为涉及到数据库查询的合并(UNION)处理起来比较麻烦,后端分页以后有空再改
        return PagerHelper.getPager(eContractMybatisDao.geList(eContractMybatisQuery), page - 1, pageSize);
    }

    @Override
    public EContractDetailResult detail(String id) {
        List<EContractLoopsKeyValueResult> keyValueResults = new ArrayList<>();

        logger.info("id: " + id);
        EContractEntity eContractEntity = eContractDao.findByIdNotDelete(id);
        Assert.notNull(eContractEntity, "没有查询到电子合同");
        EContractTemplateBindEntity eContractTemplateEntity = eContractTemplateBindDao.findByIdNotDelete(eContractEntity.geteContractTemplateBindId());
        Assert.notNull(eContractTemplateEntity, "没有查询到电子合同模板");


        List<EContractTemplateLoopsBindEntity> eContractTemplateLoopsBindEntities = eContractTemplateLoopsBindDao.findByEContractId(eContractEntity.getId());

        if (!ListUtils.checkNullOrEmpty(eContractTemplateLoopsBindEntities)) {
            for (EContractTemplateLoopsBindEntity eContractTemplateLoopsBindEntity : eContractTemplateLoopsBindEntities) {
                EContractValuesEntity eContractValuesEntity = eContractValuesEntityDao.findByLoopsId(eContractTemplateLoopsBindEntity.getId());
                EContractLoopsKeyValueResult eContractLoopsKeyValueResult = new EContractLoopsKeyValueResult();
                eContractLoopsKeyValueResult.setId(eContractTemplateLoopsBindEntity.getId());
                eContractLoopsKeyValueResult.setKey(eContractTemplateLoopsBindEntity.getLoopName());
                eContractLoopsKeyValueResult.setWriterType(eContractTemplateLoopsBindEntity.getWriterType().ordinal());
                if (eContractValuesEntity != null) {
                    eContractLoopsKeyValueResult.setValue(eContractValuesEntity.getValue());
                }
                eContractLoopsKeyValueResult.setKeyParam(eContractTemplateLoopsBindEntity.getLoopParam());
                keyValueResults.add(eContractLoopsKeyValueResult);
            }
        }

        keyValueResults.sort(new EContractLoopsKeyValueResult()::compare);
        EContractLoopsKeyValueResult eContractLoopsKeyValueResult = new EContractLoopsKeyValueResult();
        eContractLoopsKeyValueResult.setId(null);
        eContractLoopsKeyValueResult.setKey("印章");
        eContractLoopsKeyValueResult.setWriterType(0);
        eContractLoopsKeyValueResult.setKeyParam("url");
        EContractSealBindEntity eContractSealBindEntity = eContractSealBindDao.findByIdNotDelete(eContractEntity.geteContractSealBindId());
        eContractLoopsKeyValueResult.setValue(fileUploadService.generateFileUrl("temp", eContractSealBindEntity.getFileName(), 3, "jpg"));
        keyValueResults.add(eContractLoopsKeyValueResult);

        EContractDetailResult eContractDetailResult = new EContractDetailResult();
        eContractDetailResult.setId(eContractEntity.getId());
        eContractDetailResult.setTxVersion(eContractEntity.getTxVersion());
        eContractDetailResult.setContractType(eContractTemplateEntity.getContractType().ordinal());
        eContractDetailResult.setContractNo(eContractEntity.getContractNo());
        eContractDetailResult.setContractState(eContractEntity.getContractState().ordinal());
        eContractDetailResult.setCreateTime(eContractEntity.getCreateTime());
        eContractDetailResult.setLoopsKeyValues(keyValueResults);

        int validDays;
        logger.info("查询签署人: " + eContractEntity.getId());
        logger.info("查询临时用户: ");
        AryaTempUserEntity aryaTempUserEntity = aryaTempUserDao.findByUniqueEContractId(eContractEntity.getId());
        if (aryaTempUserEntity == null) {
            logger.info("没有查询到临时用户, 查询正式用户");
            EContractAryaUserRelationEntity eContractAryaUserRelationEntity = eContractAryaUserDao.findUniqueByEContractId(eContractEntity.getId());
            Assert.notNull(eContractAryaUserRelationEntity, "没有查询到签署人");
            validDays = eContractAryaUserRelationEntity.getValidDays();
        } else {
            logger.info("查询到临时用户:");
            validDays = aryaTempUserEntity.getValidDays();
        }

        logger.info("validDays: " + validDays);
        eContractDetailResult.setValidDays(validDays);

        return eContractDetailResult;
    }

    @Override
    public EContractInfoResult info(String id) {
        List<EContractLoopsKeyValueResult> keyValueResults = new ArrayList<>();

        logger.info("id: " + id);
        EContractEntity eContractEntity = eContractDao.findByIdNotDelete(id);
        Assert.notNull(eContractEntity, "没有查询到电子合同");

        EContractTemplateEntity eContractTemplateEntity = eContractTemplateDao.findByIdNotDelete(eContractEntity.geteContractTemplateId());
        Assert.notNull(eContractTemplateEntity, "没有查询到电子合同模板");


        List<EContractTemplateLoopsEntity> eContractTemplateLoopsEntities = eContractTemplateLoopsDao.findByTemplateId(eContractEntity.geteContractTemplateId(),
                EContractEnum.WriterType.first);

        if (!ListUtils.checkNullOrEmpty(eContractTemplateLoopsEntities)) {
            for (EContractTemplateLoopsEntity eContractTemplateLoopsEntity : eContractTemplateLoopsEntities) {

                EContractTemplateLoopsBindEntity eContractTemplateLoopsBindEntity = eContractTemplateLoopsBindDao.findByUniqueTemplateLoopsId(
                        eContractTemplateLoopsEntity.getId(), eContractEntity.getId());
                Assert.notNull(eContractTemplateLoopsBindEntity, "没有查询到bindLoops项");

                EContractValuesEntity eContractValuesEntity = eContractValuesEntityDao.findByLoopsId(eContractTemplateLoopsBindEntity.getId());
                EContractLoopsKeyValueResult eContractLoopsKeyValueResult = new EContractLoopsKeyValueResult();
                eContractLoopsKeyValueResult.setId(eContractTemplateLoopsEntity.getId());
                eContractLoopsKeyValueResult.setKey(eContractTemplateLoopsBindEntity.getLoopName());
                eContractLoopsKeyValueResult.setWriterType(eContractTemplateLoopsBindEntity.getWriterType().ordinal());
                if (eContractValuesEntity != null) {
                    eContractLoopsKeyValueResult.setValue(eContractValuesEntity.getValue());
                }
                eContractLoopsKeyValueResult.setKeyParam(eContractTemplateLoopsBindEntity.getLoopParam());
                keyValueResults.add(eContractLoopsKeyValueResult);
            }
        }

        keyValueResults.sort(new EContractLoopsKeyValueResult()::compare);

        logger.info("查询临时用户");
        List<EContractSignerResult> signerResults = new ArrayList<>();
        List<AryaTempUserEntity> aryaTempUserEntities = aryaTempUserDao.findByEContractId(eContractEntity.getId());
        if (!ListUtils.checkNullOrEmpty(aryaTempUserEntities)) {
            for (AryaTempUserEntity aryaTempUserEntity : aryaTempUserEntities) {
                EContractSignerResult eContractSignerResult = new EContractSignerResult();
                eContractSignerResult.setId(aryaTempUserEntity.getId());
                eContractSignerResult.setTel(aryaTempUserEntity.getTel());
                eContractSignerResult.setValidDays(aryaTempUserEntity.getValidDays());
                eContractSignerResult.setName(eContractEntity.getName());
                signerResults.add(eContractSignerResult);
            }
        }

        logger.info("查询签署人");
        List<EContractAryaUserRelationEntity> aryaUserRelationEntities = eContractAryaUserDao.findByEContractId(eContractEntity.getId());
        if (!ListUtils.checkNullOrEmpty(aryaUserRelationEntities)) {
            for (EContractAryaUserRelationEntity aryaUserRelationEntity : aryaUserRelationEntities) {

                AryaUserEntity aryaUserEntity = aryaUserDao.findUserByIdThrow(aryaUserRelationEntity.getAryaUserId());

                EContractSignerResult eContractSignerResult = new EContractSignerResult();
                eContractSignerResult.setId(aryaUserRelationEntity.getId());
                eContractSignerResult.setTel(aryaUserEntity.getPhoneNo());
                eContractSignerResult.setValidDays(aryaUserRelationEntity.getValidDays());
                eContractSignerResult.setName(eContractEntity.getName());
                signerResults.add(eContractSignerResult);
            }
        }

        EContractInfoResult eContractInfoResult = new EContractInfoResult();
        eContractInfoResult.seteContractTemplateId(eContractEntity.geteContractTemplateId());

        logger.info("查询绑定的印章");
        EContractSealBindEntity eContractSealBindEntity = eContractSealBindDao.findByIdNotDelete(eContractEntity.geteContractSealBindId());
        Assert.notNull(eContractSealBindEntity, "没有查询到绑定的印章");

        EContractSealEntity eContractSealEntity = eContractSealDao.findByIdNotDelete(eContractSealBindEntity.geteContractSealId());
        if (eContractSealEntity != null) {

            eContractInfoResult.seteContractSealId(eContractSealEntity.getId());
            eContractInfoResult.seteContractSealUrl(fileUploadService.generateFileUrl(
                    "temp",
                    eContractSealEntity.getFileName(),
                    UploadFileTypes.E_CONTRACT_SEAL_IMAGES,
                    "jpg"
            ));
        }


        eContractInfoResult.setValues(keyValueResults);
        eContractInfoResult.setSigners(signerResults);

        return eContractInfoResult;
    }

    @Override
    public void send(BaseCommand.BatchIds ids, SessionInfo sessionInfo) throws Exception {

        for (BaseCommand.IDCommand IDCommand : ids.getBatch()) {
            EContractEntity eContractEntity = eContractDao.findByIdNotDelete(IDCommand.getId());
            Assert.notNull(eContractEntity, "没有查询到电子合同");
            BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpByIdThrow(eContractEntity.getBranCorpId());

            eContractEntity.setContractState(EContractEnum.ContractState.send);
            eContractEntity.setYunSignContractState(unSigned);
            eContractEntity.setUpdateTime(System.currentTimeMillis());
            eContractDao.update(eContractEntity);
            eContractHandler.send(eContractEntity,
                    EContractCorpHelper.msgTempTemplate,
                    EContractCorpHelper.msgUserTemplate);
            eContractHandler.push(eContractEntity,
                    String.format(EContractCorpHelper.unsentPush, branCorporationEntity.getCorpName()),
                    "合同签署提醒");


            int validDays;
            logger.info("查询签署人: " + eContractEntity.getId());
            logger.info("查询临时用户: ");
            AryaTempUserEntity aryaTempUserEntity = aryaTempUserDao.findByUniqueEContractId(eContractEntity.getId());
            if (aryaTempUserEntity == null) {
                logger.info("没有查询到临时用户, 查询正式用户");
                EContractAryaUserRelationEntity eContractAryaUserRelationEntity = eContractAryaUserDao.findUniqueByEContractId(eContractEntity.getId());
                Assert.notNull(eContractAryaUserRelationEntity, "没有查询到签署人");
                validDays = eContractAryaUserRelationEntity.getValidDays();
            } else {
                logger.info("查询到临时用户:");
                validDays = aryaTempUserEntity.getValidDays();
            }

            logger.info("validDays: " + validDays);

            eContractHandler.createTask(
                    eContractEntity,
                    validDays,
                    System.currentTimeMillis()
            );
        }
    }

    @Override
    public void del(BaseCommand.BatchIds ids, SessionInfo sessionInfo) {
        ids.getBatch().forEach(
                one -> {
                    EContractEntity eContractEntity = eContractDao.findByIdNotDelete(one.getId());
                    Assert.notNull(eContractEntity, "没有查询到电子合同");
                    eContractEntity.setContractState(voided);
                    eContractEntity.setIsDelete(Constants.TRUE);
                    eContractEntity.setUpdateTime(System.currentTimeMillis());
                    eContractDao.update(eContractEntity);
                }
        );
    }

    @Override
    public void setState(EContractSetStateCommand eContractSetStateCommand, SessionInfo sessionInfo) throws Exception {
        EContractEntity eContractEntity = eContractDao.findByIdNotDelete(eContractSetStateCommand.getId());
        Assert.notNull(eContractEntity, "没有查询到电子合同");

        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpByIdThrow(eContractEntity.getBranCorpId());
        // 执行状态 0: 作废  "
        CorporationEntity corporationEntity = corporationDao.findCorpByBranCorpIdThrow(eContractEntity.getBranCorpId());
        if (eContractSetStateCommand.getState() == 0) {
            logger.info("检查是否已经在云签中签署: " + eContractEntity.getYunSignContractNo());
            if (StringUtils.isBlank(eContractEntity.getYunSignContractNo())) {
                logger.info("云签电子合同id为空,本地作废");
                // 撤销成功
                eContractEntity.setContractState(voided);
                eContractEntity.setYunSignContractState(signCancel);
            } else {
                logger.info("调用云签撤销");
                if (StringUtils.isNotBlank(eContractEntity.getYunSignContractNo())) {
                    Map<String, Object> resultMapCancelContract = jsonToMap(
                            eContractCorpService.cancelContract(corporationEntity.getId(),
                                    eContractEntity.getYunSignContractNo()));
//                    Map resultData = SysUtils.jsonToMap(resultMapCancelContract.get("result").toString());
                    if (!"1000".equals(resultMapCancelContract.get("code").toString())) {
                        logger.info(eContractEntity.getId() + "撤销合同失败," + resultMapCancelContract.get("msg"));
                        throw new AryaServiceException(ErrorCode.CODE_ECONTRACT_CANCEL_CONTRACT_ERROR);
                    }
                    // 撤销成功
                    eContractEntity.setContractState(voided);
                    eContractEntity.setYunSignContractState(signCancel);
                }

            }
            // 短信
            eContractCorpHelper.send(eContractEntity, String.format(EContractCorpHelper.voidedPush, branCorporationEntity.getCorpName()));
            // 推送
            eContractCorpHelper.push(eContractEntity, String.format(EContractCorpHelper.voidedPush, branCorporationEntity.getCorpName()), "合同作废提醒");
            // 删除定时任务
            eContractHandler.delTask(eContractEntity);

        }
        // 1:审核通过
        if (eContractSetStateCommand.getState() == 1) {
            logger.info("调用平台签署");
            logger.info("查询企业签章");
            logger.info("-- AryaCorpId:" + corporationEntity.getId());
            if (StringUtils.isAnyBlank(eContractEntity.geteContractSealBindId())) {
                throw new AryaServiceException(ErrorCode.CODE_ECONTRACT_CORP_NO_SIGN_ERROR);
            }
            EContractSealBindEntity sealBindEntity = eContractSealBindDao.findByIdNotDelete(eContractEntity.geteContractSealBindId());
            Map<String, Object> resultMapSign = jsonToMap(eContractCorpService.sign(corporationEntity.getId()
                    , sealBindEntity.getYunSignSealNum(), eContractEntity.getYunSignContractNo(), EContractCorpService.E_CONTRACT_CERTTYPE));
            if (!"1000".equals(resultMapSign.get("code").toString())) {
                logger.info("平台签署失败，" + resultMapSign.get("msg") + ",合同ID：" + eContractEntity.getYunSignContractNo());
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
            }

            eContractEntity.setContractState(executed);
            eContractEntity.setYunSignContractState(signed);
            eContractEntity.seteContractSuccessTime(new Date());
            // 短信
            eContractCorpHelper.send(eContractEntity, String.format(EContractCorpHelper.executedPush, branCorporationEntity.getCorpName()));
            // 推送
            eContractCorpHelper.push(eContractEntity, String.format(EContractCorpHelper.executedPush, branCorporationEntity.getCorpName()), "合同生效提醒");
            // 删除定时任务
            eContractHandler.delTask(eContractEntity);


        }
        // 2: 审核驳回
        if (eContractSetStateCommand.getState() == 2) {
            Assert.notBlank(eContractSetStateCommand.getRejectReason(), "驳回理由必填");
            //eContractEntity.setContractState(EContractEnum.ContractState.reject);
            eContractEntity.setRejectReason(eContractSetStateCommand.getRejectReason());
            // 调用云签合同撤销接口
            if (StringUtils.isNotBlank(eContractEntity.getYunSignContractNo())) {
                Map<String, Object> resultMapCancelContract = jsonToMap(
                        eContractCorpService.cancelContract(corporationEntity.getId(),
                                eContractEntity.getYunSignContractNo()));
//                Map resultData = SysUtils.jsonToMap(resultMapCancelContract.get("result").toString());
                if (!"1000".equals(resultMapCancelContract.get("code").toString())) {
                    logger.info(eContractEntity.getId() + "撤销合同失败," + resultMapCancelContract.get("msg"));
                    throw new AryaServiceException(ErrorCode.CODE_ECONTRACT_CANCEL_CONTRACT_ERROR);
                }
                // 重置云签流水号 云签文件
                eContractEntity.setYunSignContractFileId(null);
                eContractEntity.setYunSignContractNo(null);
                eContractEntity.setContractState(EContractEnum.ContractState.send);
                eContractEntity.setYunSignContractState(unSigned);
                // 短信
                eContractCorpHelper.send(eContractEntity, String.format(EContractCorpHelper.rejectPush, eContractSetStateCommand.getRejectReason()), branCorporationEntity.getCorpName());
                // 推送
                eContractCorpHelper.push(eContractEntity, String.format(EContractCorpHelper.rejectPush, eContractSetStateCommand.getRejectReason()), "合同重签提醒");
                // 删除定时任务
                eContractHandler.delTask(eContractEntity);
                // 定时任务
                logger.info("查询签署人: " + eContractEntity.getId());
                logger.info("查询正式用户: ");
                EContractAryaUserRelationEntity eContractAryaUserRelationEntity = eContractAryaUserDao.findUniqueByEContractId(eContractEntity.getId());
                Assert.notNull(eContractAryaUserRelationEntity, "没有查询到签署人");
                int validDays = eContractAryaUserRelationEntity.getValidDays();


                logger.info("validDays: " + validDays);

                eContractHandler.createTask(
                        eContractEntity,
                        validDays,
                        System.currentTimeMillis()
                );

            }
        }
        eContractEntity.setUpdateTime(System.currentTimeMillis());
        eContractDao.update(eContractEntity);
    }

    @Override
    public String preview(String id) throws Exception {
        return eContractHandler.preview(id);
    }

    @Override
    public String download(String id, HttpServletResponse response) throws Exception {
        EContractEntity eContractEntity = eContractDao.findByIdNotDelete(id);
        Assert.notNull(eContractEntity, "没有查询到电子合同: " + id);
        EContractAryaUserRelationEntity eContractAryaUserRelationEntity = eContractAryaUserDao.findUniqueByEContractId(eContractEntity.getId());
        Assert.notNull(eContractAryaUserRelationEntity, "没有查询到电子合同对应的用户(签署人)");
        InputStream inputStream = eContractCorpService.downloadContract(eContractAryaUserRelationEntity.getAryaUserId(), eContractEntity.getYunSignContractNo(), true);
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + eContractEntity.getYunSignContractNo() + ".pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.copy(
                inputStream,
                fileOutputStream
        );
        FileUploadFileResult fileUploadFileResult = fileUploadService.uploadFile(
                MultipartFileUtils.create(file),
                0,
                "pdf",
                null, null);

        String url = fileUploadService.generateDownLoadFileUrl(
                configService.getConfigByKey("bran.admin.resource.server"),
                com.bumu.bran.common.Constants.HPPT_TYPE_PDF,
                fileUploadFileResult.getId(),
                0,
                "pdf");
        logger.info("url: " + url);
        return url;

    }
}
