package com.bumu.arya.admin.corporation.service.impl;

import com.bumu.SysUtils;
import com.bumu.approval.helper.ApprovalHelper;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.controller.command.*;
import com.bumu.arya.admin.corporation.model.CorpModel;
import com.bumu.arya.admin.corporation.result.*;
import com.bumu.arya.admin.corporation.service.AryaAdminFileService;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.common.model.DistrictCombination;
import com.bumu.arya.common.service.CommonDistrictService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.*;
import com.bumu.arya.model.entity.AryaDepartmentEntity;
import com.bumu.arya.model.entity.CorpBusinessDetailEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.model.dao.BranCorpImageDao;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorpImageEntity;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.admin.service.BranCorpUserCommonService;
import com.bumu.common.model.PagingModel;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.common.service.CommonFileService;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.ListUtils;
import com.bumu.econtract.service.EContractCorpService;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.bumu.arya.common.OperateConstants.*;

/**
 * 公司接口
 * Created by bumu-zhz on 2015/11/11.
 */
@Service
public class CorporationServiceImpl extends BaseBumuService implements CorporationService {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Logger log = LoggerFactory.getLogger(CorporationServiceImpl.class);
    @Autowired
    BranCorporationDao branCorporationDao;

    @Autowired
    CommonDistrictService commonDistrictService;
    //    @Autowired
//    AryaAdminConfigService aryaAdminConfigService;
    @Autowired
    CommonFileService fileService;
    @Autowired
    BranCorpImageDao branCorpImageDao;
    @Autowired
    AryaAdminFileService adminFileService;
    @Autowired
    AryaDepartmentDao aryaDepartmentDao;
    @Autowired
    AryaSalaryDao monthSalaryDao;
    @Autowired
    AryaSalaryWeekDao weekSalaryDao;
    @Autowired
    BranCorpUserDao branCorpUserDao;
    @Autowired
    BranCorpUserCommonService branCorpUserCommonService;
    @Autowired
    OpLogService opLogService;
    @Autowired
    CorpBusinessDetailDao corpBusinessDetailDao;
    @Autowired
    private CorporationDao corporationDao;
    @Autowired
    private EContractCorpService eContractCorpService;
    @Autowired
    private ApprovalHelper approvalHelper;


    /**
     * 查询所有公司
     *
     * @return
     */
    @Override
    public List<CorporationEntity> retrieveCorporation() throws AryaServiceException {
        List<CorporationEntity> corporationEntities = null;
        try {
            corporationEntities = corporationDao.findAll();
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_QUERY_FAILED);//公司查询失败
        }
        return corporationEntities;
    }

    @Override
    public CorpListResult getCorpGroupList() throws AryaServiceException {
        CorpListResult result = new CorpListResult();
        List<CorporationEntity> corporationEntities = null;
        try {
            corporationEntities = corporationDao.findAllCorpGroupAscByName();
            for (CorporationEntity entity : corporationEntities) {
                CorpListResult.CorpInfo corpInfo = new CorpListResult.CorpInfo();
                corpInfo.setId(entity.getId());
                corpInfo.setName(entity.getName());
                result.add(corpInfo);
            }
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_QUERY_FAILED);//公司查询失败
        }
        return result;
    }

    @Override
    public JQDatatablesPaginationResult getSubsidiaryList(String corpName, String corpShortName, String contactPerson, int page, int pageSize) throws AryaServiceException {
        JQDatatablesPaginationResult jqDatatablesPaginationResult = new JQDatatablesPaginationResult();
        CorpListResult result = new CorpListResult();
        List<CorporationEntity> corporationEntities;
        try {
            Criteria criteria = corporationDao.getSubsidiaryPagnationQueryCriteria(corpName, corpShortName, contactPerson);
            PagingModel pagingModel = new PagingModel();
            pagingModel.setData(corporationDao.findSubsidiaryFilterPagnationDescByCreateTime(criteria, page, pageSize));
            pagingModel.setRecordsFiltered(corporationDao.findSubsidiaryFilterTotalCount(criteria));
            pagingModel.setRecordsTotal(corporationDao.countAllSubsidiary());
            if (pagingModel.getData().size() == 0) {
                jqDatatablesPaginationResult.setData(result);
                jqDatatablesPaginationResult.setRecordsTotal(0);
                jqDatatablesPaginationResult.setRecordsFiltered(0);
                return jqDatatablesPaginationResult;
            }
            List<String> districtIds = new ArrayList<>();
            corporationEntities = pagingModel.getData();
            for (CorporationEntity entity : corporationEntities) {
                if (StringUtils.isAnyBlank(entity.getDistrictId())) {
                    continue;
                }
                String[] ids = entity.getDistrictId().split(":");
                for (String id : ids) {
                    if (!districtIds.contains(id)) {
                        districtIds.add(id);
                    }
                }
            }

            Map<String, String> districtId2NameMap = commonDistrictService.generateDistrictIds2NameMap(districtIds);

            for (CorporationEntity entity : corporationEntities) {
                CorpListResult.CorpInfo corpInfo = new CorpListResult.CorpInfo();
                corpInfo.setId(entity.getId());
                if (StringUtils.isNotBlank(entity.getDistrictId())) {
                    corpInfo.setDistrict(commonDistrictService.transDistrictId2Name(entity.getDistrictId(), districtId2NameMap));
                }
                corpInfo.setName(entity.getName());
                corpInfo.setShortName(entity.getShortName());
                corpInfo.setContactName(entity.getContactName());
                corpInfo.setContactPhone(entity.getTelephone());
                corpInfo.setMail(entity.getEmail());
                corpInfo.setCreateTime(dateFormat.format(entity.getCreateTime()));
//				corpInfo.setStatus(entity.getStatus());
                result.add(corpInfo);
            }
            jqDatatablesPaginationResult.setData(result);
            jqDatatablesPaginationResult.setRecordsTotal((int) pagingModel.getRecordsTotal());
            jqDatatablesPaginationResult.setRecordsFiltered(pagingModel.getRecordsFiltered());
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_QUERY_FAILED);//公司查询失败
        }
        return jqDatatablesPaginationResult;
    }

    @Override
    public CorpListResult.CorpInfo getCorpDetail(String corpId) {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        CorpListResult.CorpInfo corpInfo = new CorpListResult.CorpInfo();
        corpInfo.setId(corporationEntity.getId());
        if (!StringUtils.isAnyBlank(corporationEntity.getDistrictId())) {
            corpInfo.setDistrict(commonDistrictService.getDistrictCombination(corporationEntity.getDistrictId().split(":")).getNameCombinationStr());
            corpInfo.setDistrictId(corporationEntity.getDistrictId());
        }
        corpInfo.setName(corporationEntity.getName());
        corpInfo.setShortName(corporationEntity.getShortName());
        corpInfo.setContactName(corporationEntity.getContactName());
        corpInfo.setContactPhone(corporationEntity.getTelephone());
        corpInfo.setMail(corporationEntity.getEmail());
        corpInfo.setCreateTime(dateFormat.format(corporationEntity.getCreateTime()));
        corpInfo.setDesc(corporationEntity.getDesc());
        corpInfo.setRequirement(Integer.toString(corporationEntity.getRequirement()));
        corpInfo.setWelfareCorpName(corporationEntity.getWelfareName());
        if (corporationEntity.getIsHumanPoolProject() == null) {
            corpInfo.setIsHumanPoolProject(CorpConstants.FALSE);
        } else {
            corpInfo.setIsHumanPoolProject(corporationEntity.getIsHumanPoolProject());
        }

        if (corporationEntity.getBusinessType() == null) {
            corpInfo.setBusinessType(0);
        } else {
            corpInfo.setBusinessType(corporationEntity.getBusinessType());
        }

        if (!StringUtils.isAnyBlank(corporationEntity.getStatus())) {
            corpInfo.setStatus(Integer.parseInt(corporationEntity.getStatus()));
        }

        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            corpInfo.setCheckinCode(generateCheckinCode());
            corpInfo.setCheckinCodeTemporary(true);
            return corpInfo;
        }
        BranCorporationEntity branCorporationEntity = branCorporationDao.find(corporationEntity.getBranCorpId());
        if (branCorporationEntity == null) {
            corpInfo.setCheckinCode(generateCheckinCode());
            corpInfo.setCheckinCodeTemporary(true);
            return corpInfo;
        }
        corpInfo.setBranCorpId(branCorporationEntity.getId());
        corpInfo.setCheckinCode(branCorporationEntity.getCheckinCode());
        corpInfo.setLongitude(branCorporationEntity.getLongitude());
        corpInfo.setLatitude(branCorporationEntity.getLatitude());
        corpInfo.setAddress(branCorporationEntity.getAddress());
        corpInfo.setLicenseCode(branCorporationEntity.getCorpLicenseCode());
        corpInfo.setIsGroup(corporationEntity.getIsGroup());
        corpInfo.setEnterpriseNature(branCorporationEntity.getCorpType());


        //logo照片URL
        if (!StringUtils.isAnyBlank(corporationEntity.getCorpLogo())) {
            corpInfo.setLogoUrl(CORP_LOGO_IMAGE_MAP + corporationEntity.getCorpLogo() + "&corp_id=" + branCorporationEntity.getId());
        }
        //营业执照副本照片
        if (!StringUtils.isAnyBlank(branCorporationEntity.getCorpLicenseFileName())) {
            corpInfo.setLicenseUrl(CORP_LICENSE_IMAGE_MAP + branCorporationEntity.getCorpLicenseFileName() + "&corp_id=" + branCorporationEntity.getId());
        }
        BranCorpImageEntity corpImageEntity = branCorpImageDao.findBranCorpFirstImageByBranCorpId(branCorporationEntity.getId());
        if (corpImageEntity != null) {
            corpInfo.setCorpImageUrl(CORP_IMAGE_MAP + corpImageEntity.getFileName() + "&bran_corp_id=" + branCorporationEntity.getId());
        }

        return corpInfo;
    }

    @Override
    public GetGroupOrCorporationDetailResult getCorpDetailInfo(String corpId) {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        GetGroupOrCorporationDetailResult corpInfo = new GetGroupOrCorporationDetailResult();
        corpInfo.setId(corporationEntity.getId());
        corpInfo.setName(corporationEntity.getName());
        corpInfo.setShortName(corporationEntity.getShortName());
        corpInfo.setContactName(corporationEntity.getContactName());
        corpInfo.setContactPhone(corporationEntity.getTelephone());
        if (corporationEntity.getIsHumanPoolProject() == null) {
            corpInfo.setIsHumanPoolProject(CorpConstants.FALSE);
        } else {
            corpInfo.setIsHumanPoolProject(corporationEntity.getIsHumanPoolProject());
        }
        if (corporationEntity.getBusinessType() == null) {
            corpInfo.setBusinessType(0);
        } else {
            corpInfo.setBusinessType(corporationEntity.getBusinessType());
        }
        corpInfo.setIsGroup(corporationEntity.getIsGroup());
        return corpInfo;
    }

    @Override
    public GetCorporationEntryDetailResult getCorpEntryDetail(String corpId) {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        GetCorporationEntryDetailResult corpInfo = new GetCorporationEntryDetailResult();
        corpInfo.setId(corporationEntity.getId());
        if (!StringUtils.isAnyBlank(corporationEntity.getDistrictId())) {
            corpInfo.setDistrict(commonDistrictService.getDistrictCombination(corporationEntity.getDistrictId().split(":")).getNameCombinationStr());
            corpInfo.setDistrictId(corporationEntity.getDistrictId());
        }
        corpInfo.setMail(corporationEntity.getEmail());
        corpInfo.setCreateTime(dateFormat.format(corporationEntity.getCreateTime()));
        corpInfo.setDesc(corporationEntity.getDesc());

        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            corpInfo.setCheckinCode(generateCheckinCode());
            return corpInfo;
        }
        BranCorporationEntity branCorporationEntity = branCorporationDao.find(corporationEntity.getBranCorpId());
        if (branCorporationEntity == null) {
            corpInfo.setCheckinCode(generateCheckinCode());
            return corpInfo;
        }
        corpInfo.setCheckinCode(branCorporationEntity.getCheckinCode());
        corpInfo.setLongitude(branCorporationEntity.getLongitude());
        corpInfo.setLatitude(branCorporationEntity.getLatitude());
        corpInfo.setAddress(branCorporationEntity.getAddress());
        corpInfo.setLicenseCode(branCorporationEntity.getCorpLicenseCode());
        corpInfo.setEnterpriseNature(branCorporationEntity.getCorpType());
        corpInfo.setSalarySmsHours(null == branCorporationEntity.getSalarySmsHours() ? 48 : branCorporationEntity.getSalarySmsHours());

        //logo照片URL
        if (!StringUtils.isAnyBlank(corporationEntity.getCorpLogo())) {
            corpInfo.setLogoUrl(CORP_LOGO_IMAGE_MAP + corporationEntity.getCorpLogo() + "&corp_id=" + branCorporationEntity.getId());
        }
        //营业执照副本照片
        if (!StringUtils.isAnyBlank(branCorporationEntity.getCorpLicenseFileName())) {
            corpInfo.setLicenseUrl(CORP_LICENSE_IMAGE_MAP + branCorporationEntity.getCorpLicenseFileName() + "&corp_id=" + branCorporationEntity.getId());
        }
        BranCorpImageEntity corpImageEntity = branCorpImageDao.findBranCorpFirstImageByBranCorpId(branCorporationEntity.getId());
        if (corpImageEntity != null) {
            corpInfo.setCorpImageUrl(CORP_IMAGE_MAP + corpImageEntity.getFileName() + "&bran_corp_id=" + branCorporationEntity.getId());
        }
        return corpInfo;
    }

    @Override
    public GetCorporationWelfareDetailResult getCorpWelfareDetail(String corpId) {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        GetCorporationWelfareDetailResult corpInfo = new GetCorporationWelfareDetailResult();
        corpInfo.setId(corporationEntity.getId());
        corpInfo.setWelfareCorpName(corporationEntity.getWelfareName());

        return corpInfo;
    }

    @Override
    public String generateCheckinCode() {
        String codeStr = "";
        Random random = new Random();
        do {
            for (int i = 0; i < 6; i++) {
                int code = random.nextInt(10);
                codeStr += String.valueOf(code);
            }
        } while (branCorporationDao.isCheckinCodeExist(codeStr));
        return codeStr;
    }


    @Override
    public Map<String, CorporationEntity> getGroupAndSubCorpMap(String groupId) {
        Map<String, CorporationEntity> corpId2CorpEntityMap = new HashMap<>();
        CorporationEntity corporationEntity = corporationDao.find(groupId);
        if (corporationEntity == null) {

            return corpId2CorpEntityMap;
        }
        corpId2CorpEntityMap.put(corporationEntity.getId(), corporationEntity);
        //如果不是集团
        if (corporationEntity.getIsGroup() != 1) {
            return corpId2CorpEntityMap;
        }

        List<CorporationEntity> corporationEntities = corporationDao.findCorpsFromGroup(corporationEntity.getId());
        for (CorporationEntity entity : corporationEntities) {
            corpId2CorpEntityMap.put(entity.getId(), entity);
        }
        return corpId2CorpEntityMap;
    }

    @Override
    public Map<String, String> getGroupDepartmentsMap(String groupId) {
        Map<String, String> departmentNameMap = new HashMap<>();
        List<AryaDepartmentEntity> departmentEntities = aryaDepartmentDao.findDepartmentsByGroupId(groupId);
        for (AryaDepartmentEntity departmentEntity : departmentEntities) {
            departmentNameMap.put(departmentEntity.getId(), departmentEntity.getName());
        }
        return departmentNameMap;
    }

    @Override
    public CreateUpdateCorpResult updateCorpInfo(CorpModel model) {
        CorporationEntity corporationEntity = corporationDao.find(model.getCorpId());
        List<CorporationEntity> subCorps = null;
        //公司不存在
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }

        corporationEntity.setName(model.getCorpName());
//		corporationEntity.setStatus(model.getStatus());
        corporationEntity.setFax(model.getFax());
        corporationEntity.setShortName(model.getCorpShortName());
        corporationEntity.setDistrictId(model.getDistrictId());
        corporationEntity.setContactName(model.getContactName());
        corporationEntity.setTelephone(model.getContactPhone());
        corporationEntity.setEmail(model.getContactMail());
        corporationEntity.setWelfareName(model.getWelfareCorpName());
        corporationEntity.setDesc(model.getDesc());
        if (corporationEntity.getIsGroup() == CorpConstants.FALSE) {
            corporationEntity.setIsGroup(model.getIsGroup());
        } else {
            corporationEntity.setIsHumanPoolProject(model.getIsHumanPoolProject());
            //更新子公司是否汇思项目状态
            subCorps = corporationDao.findAllSubCorps(model.getCorpId());
            if (subCorps != null && !subCorps.isEmpty()) {
                for (CorporationEntity subCorp : subCorps) {
                    subCorp.setIsHumanPoolProject(model.getIsHumanPoolProject());
                }
            }
        }
        corporationEntity.setBusinessType(model.getBusinessType());
        BranCorporationEntity branCorporationEntity;
        //如果不存在公司详情则新建
        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            branCorporationEntity = createBranCorp(model);
            corporationEntity.setBranCorpId(branCorporationEntity.getId());
            //转存公司logo
            if (model.getCorpLogoFile() != null) {
                String logoFileName = Utils.makeUUID();
                fileService.saveCorpLogoImage(logoFileName, branCorporationEntity.getId(), model.getCorpLogoFile());
                corporationEntity.setCorpLogo(logoFileName);
            }
        } else {
            branCorporationEntity = updateBranCorp(corporationEntity.getBranCorpId(), model);
            //转存公司logo
            if (model.getCorpLogoFile() != null) {
                String logoFileName = Utils.makeUUID();
                fileService.saveCorpLogoImage(logoFileName, branCorporationEntity.getId(), model.getCorpLogoFile());
                corporationEntity.setCorpLogo(logoFileName);
            }
        }
        if (model.getCorpImageFile() != null) {
            //上传公司图片
            uploadCorpImage(branCorporationEntity.getId(), model.getCorpImageFile(), model.getOperateUserId());
        }
        StringBuffer logMsg = new StringBuffer("【企业信息管理】用户:" + model.getOperateUserId() + ",更新公司:" + model.getCorpName() + ",id:" + corporationEntity.getId());
        try {
            corporationDao.update(corporationEntity);
            if (subCorps != null && !subCorps.isEmpty()) {
                corporationDao.update(subCorps);
            }
            branCorporationDao.createOrUpdate(branCorporationEntity);
            opLogService.successLog(OperateConstants.CORP_UPDATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.CORP_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_INFO_UPDATE_FAILED);
        }
        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.FALSE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }
        return result;
    }

    @Override
    public CreateUpdateCorpResult updateCorporationInfo(CreateOrUpdateCorporationCommand command) throws Exception {
        CorporationEntity corporationEntity = corporationDao.find(command.getCorpId());
        List<CorporationEntity> subCorps = null;
        //公司不存在
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(corporationEntity.getBranCorpId());
        String name = command.getName();
        if (branCorporationEntity != null) {
            if (name != null && !name.equals(branCorporationEntity.getCorpName())) {
                branCorporationEntity.setCorpName(name);
                branCorporationDao.update(branCorporationEntity);
            }
        }

        corporationEntity.setName(name);
        corporationEntity.setShortName(command.getShortName());
        corporationEntity.setContactName(command.getContactName());
        corporationEntity.setTelephone(command.getContactPhone());
        if (corporationEntity.getIsGroup() == CorpConstants.FALSE) {
            corporationEntity.setIsGroup(command.getIsGroup());
        } else {
            corporationEntity.setIsHumanPoolProject(command.getIsHumanPoolProject());
            //更新子公司是否汇思项目状态
            subCorps = corporationDao.findAllSubCorps(command.getCorpId());
            if (subCorps != null && !subCorps.isEmpty()) {
                for (CorporationEntity subCorp : subCorps) {
                    subCorp.setIsHumanPoolProject(command.getIsHumanPoolProject());
                }
            }
        }
        corporationEntity.setBusinessType(command.getBusinessType());
        Session session = SecurityUtils.getSubject().getSession();
        String userId = session.getAttribute("user_id").toString();

        //如果取消考勤业务则同时删除考勤业务详情表中的数据
        List<CorpBusinessDetailEntity> corpBusinessDetailEntities = null;
        if ((command.getBusinessType() & CorpConstants.CORP_BUSINESS_ATTENDANCE) <= 0) {
            corpBusinessDetailEntities = corpBusinessDetailDao.findByCorpIdAndType(command.getCorpId(), CorpConstants.CORP_BUSINESS_ATTENDANCE);
        }
        //如果选择了考勤业务，则生成一条数据
        CorpBusinessDetailEntity corpBusinessDetailEntity = null;
        if ((command.getBusinessType().intValue() & CorpConstants.CORP_BUSINESS_ATTENDANCE) > 0) {
            corpBusinessDetailEntity = creatCorpBusinessDetail(corporationEntity.getId(), CorpConstants.CORP_BUSINESS_ATTENDANCE);
        }

        StringBuffer logMsg = new StringBuffer("【企业信息管理】用户:" + userId + ",更新公司:" + name + ",id:" + corporationEntity.getId());
        try {
            corporationDao.update(corporationEntity);
            if (subCorps != null && !subCorps.isEmpty()) {
                corporationDao.update(subCorps);
            }
            if (corpBusinessDetailEntity != null) {
                corpBusinessDetailDao.create(corpBusinessDetailEntity);
            }
            if (!ListUtils.checkNullOrEmpty(corpBusinessDetailEntities)) {
                corpBusinessDetailDao.delete(corpBusinessDetailEntities);
            }
            opLogService.successLog(OperateConstants.CORP_UPDATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.CORP_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_INFO_UPDATE_FAILED);
        }
        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.FALSE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }
        return result;
    }

    @Override
    public BranCorporationEntity createBranCorp(CorpModel model) throws AryaServiceException {
        BranCorporationEntity branCorporationEntity = new BranCorporationEntity();
        branCorporationEntity.setId(Utils.makeUUID());
        branCorporationEntity.setCreateTime(System.currentTimeMillis());
        branCorporationEntity.setCheckinCode(generateCheckinCode());
        branCorporationEntity.setCreateUser(model.getOperateUserId());
        branCorporationEntity.setUpdateUser(model.getOperateUserId());
        branCorporationEntity.setUpdateTime(branCorporationEntity.getCreateTime());
        branCorporationEntity.setIsDelete(com.bumu.bran.common.Constants.FALSE);
        setBranCorpDetail(branCorporationEntity, model);
        return branCorporationEntity;
    }

    @Override
    public BranCorporationEntity updateBranCorp(String branCorpId, CorpModel model) throws AryaServiceException {
        BranCorporationEntity branCorporationEntity = branCorporationDao.find(branCorpId);
        if (branCorporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_BRAN_CORP_NOT_EXIST);
        }
        setBranCorpDetail(branCorporationEntity, model);
        branCorporationEntity.setUpdateUser(model.getOperateUserId());
        branCorporationEntity.setUpdateTime(System.currentTimeMillis());
        return branCorporationEntity;
    }

    @Override
    public CorpBusinessDetailEntity creatCorpBusinessDetail(String corpId, int business) throws AryaServiceException {
        CorpBusinessDetailEntity corpBusinessDetailEntity = new CorpBusinessDetailEntity();
        corpBusinessDetailEntity.setId(Utils.makeUUID());
        corpBusinessDetailEntity.setCorpId(corpId);
        corpBusinessDetailEntity.setCorpBusiness(business);
        corpBusinessDetailEntity.setCorpBusinessType(0);
        corpBusinessDetailEntity.setCreateTime(System.currentTimeMillis());
        return corpBusinessDetailEntity;
    }

    /**
     * 为新增和更新branCorp赋值
     *
     * @param branCorporationEntity
     * @param model
     */
    private void setBranCorpDetail(BranCorporationEntity branCorporationEntity, CorpModel model) {
        branCorporationEntity.setAddress(model.getAddress());
        if (model.getCorpType() != null) {
            branCorporationEntity.setCorpType(model.getCorpType());
        }
        branCorporationEntity.setCorpName(model.getCorpName());
        branCorporationEntity.setLongitude(model.getLongitude());
        branCorporationEntity.setLatitude(model.getLatitude());
        branCorporationEntity.setSalarySmsHours(model.getSalarySmsHours());
        if (StringUtils.isNotBlank(model.getDistrictId())) {
            DistrictCombination districtIdCombination = commonDistrictService.splitDistrictCombinationToIds(model.getDistrictId());
            branCorporationEntity.setProvinceCode(districtIdCombination.getProvinceId());
            branCorporationEntity.setCityCode(districtIdCombination.getCityId());
            branCorporationEntity.setCountyCode(districtIdCombination.getCountyId());

            String[] districtIds = model.getDistrictId().split(":");
            DistrictCombination districtNameCombination = commonDistrictService.getDistrictCombination(districtIds[districtIds.length - 1]);
            branCorporationEntity.setProvinceName(districtNameCombination.getProvinceName());
            branCorporationEntity.setCityName(districtNameCombination.getCityName());
            branCorporationEntity.setCountyName(districtNameCombination.getCountyName());
        }
    }

    @Override
    public CreateUpdateCorpResult createNewCorp(CorpModel model) {
        if (StringUtils.isAnyBlank(model.getCorpName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_NAME_EMPTY);
        }

        CorporationEntity corporationEntity = new CorporationEntity();
        //如果有父公司,查询父公司的业务,如果有则继承
        if (StringUtils.isNotBlank(model.getParentId())) {
            CorporationEntity parentCorp = corporationDao.findCorporationByIdThrow(model.getParentId());
            if (parentCorp == null) {
                throw new AryaServiceException(ErrorCode.CODE_CORPORATION_PARENT_NOT_EXIST);
            }
            corporationEntity.setBusinessType(parentCorp.getBusinessType());
            corporationEntity.setIsHumanPoolProject(parentCorp.getIsHumanPoolProject());
        } else {
            corporationEntity.setIsHumanPoolProject(model.getIsHumanPoolProject());
        }
        BranCorporationEntity branCorporationEntity = createBranCorp(model);

        corporationEntity.setId(Utils.makeUUID());
        corporationEntity.setParentId(model.getParentId());
        //转存公司logo
        if (model.getCorpLogoFile() != null) {
            String logoFileName = Utils.makeUUID();
            fileService.saveCorpLogoImage(logoFileName, branCorporationEntity.getId(), model.getCorpLogoFile());
            corporationEntity.setCorpLogo(logoFileName);
        }
        corporationEntity.setCreateTime(System.currentTimeMillis());
        corporationEntity.setIsDelete(false);
        corporationEntity.setIsGroup(model.getIsGroup());
        corporationEntity.setName(model.getCorpName());
        corporationEntity.setShortName(model.getCorpShortName());
        corporationEntity.setDistrictId(model.getDistrictId());
        corporationEntity.setContactName(model.getContactName());
        corporationEntity.setTelephone(model.getContactPhone());
        corporationEntity.setEmail(model.getContactMail());
        corporationEntity.setBusinessType(model.getBusinessType());
        corporationEntity.setWelfareName(model.getWelfareCorpName());
        corporationEntity.setDesc(model.getDesc());
        if (model.getCorpImageFile() != null) {
            uploadCorpImage(branCorporationEntity.getId(), model.getCorpImageFile(), model.getOperateUserId());
        }
        corporationEntity.setBranCorpId(branCorporationEntity.getId());


        StringBuffer logStr = new StringBuffer("【企业信息管理】系统用户:" + model.getOperateUserId() + "新增" + model.getCorpName());
        try {
            corporationDao.create(corporationEntity);
            branCorporationDao.create(branCorporationEntity);
            opLogService.successLog(CORP_CREATE, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.successLog(CORP_CREATE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CREATE_FAILED);
        }

        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.TRUE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }

        return result;
    }

    @Override
    public CreateUpdateCorpResult updateCorpEntryInfo(CorpModel model) {
        CorporationEntity corporationEntity = corporationDao.find(model.getCorpId());
        //公司不存在
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        model.setCorpName(corporationEntity.getName());
        corporationEntity.setDistrictId(model.getDistrictId());
        corporationEntity.setEmail(model.getContactMail());
        corporationEntity.setDesc(model.getDesc());

        Session session = SecurityUtils.getSubject().getSession();
        String userId = session.getAttribute("user_id").toString();
        model.setOperateUserId(userId);

        BranCorporationEntity branCorporationEntity = updateBranCorp(corporationEntity.getBranCorpId(), model);
        //转存公司logo
        if (model.getCorpLogoFile() != null) {
            String logoFileName = Utils.makeUUID();
            fileService.saveCorpLogoImage(logoFileName, branCorporationEntity.getId(), model.getCorpLogoFile());
            corporationEntity.setCorpLogo(logoFileName);
        }
        if (model.getCorpImageFile() != null) {
            //上传公司图片
            uploadCorpImage(branCorporationEntity.getId(), model.getCorpImageFile(), model.getOperateUserId());
        }
        StringBuffer logMsg = new StringBuffer("【企业信息管理】用户:" + model.getOperateUserId() + ",更新公司:" + model.getCorpName() + "一键入职业务" + ",id:" + corporationEntity.getId());
        try {
            corporationDao.update(corporationEntity);
            branCorporationDao.createOrUpdate(branCorporationEntity);
            opLogService.successLog(OperateConstants.CORP_UPDATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.CORP_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_INFO_UPDATE_FAILED);
        }
        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.FALSE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }

        return result;
    }

    @Override
    public CreateUpdateCorpResult updateCorpWelfareInfo(String corpId, String parentId, String welfareCorpName) {
        CorporationEntity corporationEntity = corporationDao.find(corpId);
        //公司不存在
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        corporationEntity.setWelfareName(welfareCorpName);
        Session session = SecurityUtils.getSubject().getSession();
        String userId = session.getAttribute("user_id").toString();
        corporationEntity.setUpdateTime(System.currentTimeMillis());
        StringBuffer logMsg = new StringBuffer("【企业信息管理】用户:" + userId + ",更新公司:" + corporationEntity.getName() + "一键入职业务" + ",id:" + corporationEntity.getId());
        try {
            corporationDao.update(corporationEntity);
            opLogService.successLog(OperateConstants.CORP_UPDATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.CORP_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_INFO_UPDATE_FAILED);
        }
        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.FALSE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }

        return result;
    }

    @Override
    public CreateUpdateCorpResult createNewCorporation(CreateOrUpdateCorporationCommand command) {
        if (StringUtils.isAnyBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_NAME_EMPTY);
        }
        CorporationEntity corporationEntity = new CorporationEntity();
        //如果有父公司,查询父公司的业务,如果有则继承
        if (StringUtils.isNotBlank(command.getParentId())) {
            CorporationEntity parentCorp = corporationDao.findCorporationByIdThrow(command.getParentId());
            if (parentCorp == null) {
                throw new AryaServiceException(ErrorCode.CODE_CORPORATION_PARENT_NOT_EXIST);
            }
            corporationEntity.setBusinessType(parentCorp.getBusinessType());
            corporationEntity.setIsHumanPoolProject(parentCorp.getIsHumanPoolProject());
        } else {
            corporationEntity.setIsHumanPoolProject(command.getIsHumanPoolProject());
        }
        BranCorporationEntity branCorporationEntity = new BranCorporationEntity();
        branCorporationEntity.setId(Utils.makeUUID());
        branCorporationEntity.setCreateTime(System.currentTimeMillis());

        corporationEntity.setId(Utils.makeUUID());
        corporationEntity.setParentId(command.getParentId());
        corporationEntity.setCreateTime(System.currentTimeMillis());
        corporationEntity.setIsDelete(false);
        corporationEntity.setIsGroup(command.getIsGroup());
        corporationEntity.setName(command.getName());
        corporationEntity.setShortName(command.getShortName());
        corporationEntity.setContactName(command.getContactName());
        corporationEntity.setTelephone(command.getContactPhone());
        corporationEntity.setBusinessType(command.getBusinessType());
        corporationEntity.setBranCorpId(branCorporationEntity.getId());

        Session session = SecurityUtils.getSubject().getSession();
        String userId = session.getAttribute("user_id").toString();
        branCorporationEntity.setCreateUser(userId);
        branCorporationEntity.setIsDelete(com.bumu.bran.common.Constants.FALSE);
        branCorporationEntity.setCheckinCode(generateCheckinCode());
        branCorporationEntity.setCorpName(command.getName());

        CorpBusinessDetailEntity corpBusinessDetailEntity = null;
        if ((command.getBusinessType().intValue() & CorpConstants.CORP_BUSINESS_ATTENDANCE) > 0) {
            corpBusinessDetailEntity = creatCorpBusinessDetail(corporationEntity.getId(), CorpConstants.CORP_BUSINESS_ATTENDANCE);
        }
        StringBuffer logStr = new StringBuffer("【企业信息管理】系统用户:" + userId + "新增" + command.getName());
        try {
            corporationDao.create(corporationEntity);
            branCorporationDao.create(branCorporationEntity);
            if (corpBusinessDetailEntity != null) {
                corpBusinessDetailDao.create(corpBusinessDetailEntity);
            }
            opLogService.successLog(CORP_CREATE, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.successLog(CORP_CREATE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CREATE_FAILED);
        }

        CreateUpdateCorpResult result = new CreateUpdateCorpResult();
        result.setCorpId(corporationEntity.getId());
        result.setName(corporationEntity.getName());
        result.setParentId(corporationEntity.getParentId());
        result.setIsNew(CorpConstants.TRUE);
        if (corporationEntity.getIsGroup() == CorpConstants.TRUE) {
            result.setType(CorpConstants.CORP_GROUP);
        } else {
            if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
                result.setType(CorpConstants.CORP_SUB_CORP);
            } else {
                result.setType(CorpConstants.CORP_FIRST_LEVEL_CORP);
            }
        }

        approvalHelper.createAfter(branCorporationEntity.getId());

        return result;
    }

    @Override
    public void deleteCorp(String corpId, String operator) {
        CorporationEntity corporationEntity = corporationDao.find(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        if (isCorpInUse(corpId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_IS_IN_USE_CANT_DELETE);
        }
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(corporationEntity.getBranCorpId());
        StringBuffer logStr = new StringBuffer("【企业信息管理】用户:" + operator + ",删除公司:" + corporationEntity.getName() + ",id:" + corporationEntity.getId());
        try {
            corporationEntity.setIsDelete(true);
            corporationDao.update(corporationEntity);
            if (branCorporationEntity != null) {
                branCorporationEntity.setIsDelete(com.bumu.bran.common.Constants.TRUE);
                branCorporationDao.update(branCorporationEntity);
            }
            opLogService.successLog(CORP_DELETE, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(CORP_DELETE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_DELETE_FAILED);
        }
    }

    @Override
    public boolean isCorpInUse(String aryaCorpId) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(aryaCorpId);
        List<String> corpIds = new ArrayList<>();
        corpIds.add(corporationEntity.getId());
        if (corporationEntity.getIsGroup() == com.bumu.bran.common.Constants.TRUE) {
            List<String> subCorpIds = corporationDao.findAllSubCorpIds(corporationEntity.getId());
            if (subCorpIds.size() > 0) {
                return true;
            }
        }

        if (monthSalaryDao.isCorpInUse(corpIds) == true) {
            return true;
        }

        List<String> branCorpIds = corporationDao.findBranCorpIdsByAryaCorpIds(corpIds);
        if (branCorpIds.size() == 0) {
            return false;
        }
        return branCorpUserDao.isBranCorpsInUse(branCorpIds);

    }

    @Override
    public void uploadCorpImage(String branCorpId, MultipartFile file, String operator) throws AryaServiceException {
        BranCorpImageEntity imageEntity = new BranCorpImageEntity();
        imageEntity.setId(Utils.makeUUID());
        imageEntity.setBranCorpId(branCorpId);
        imageEntity.setFileName(imageEntity.getId());
        imageEntity.setCreateTime(System.currentTimeMillis());
        imageEntity.setCreateUser(operator);
        imageEntity.setUpdateTime(System.currentTimeMillis());
        imageEntity.setUpdateUser(operator);

        fileService.saveCorpOtherImage(imageEntity.getFileName(), branCorpId, file);
        try {
            branCorpImageDao.deleteBranCorpAllImage(branCorpId);
            branCorpImageDao.create(imageEntity);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_CORP_IMAGE_UPLOAD_FAILED);
        }
    }

    @Override
    public CorpImageListResult getCorpImageList(String aryaCorpId) {
        CorpImageListResult results = new CorpImageListResult();
        String branCorpId = corporationDao.findBranCorpIdByAryaCorpIdThrow(aryaCorpId);
        List<BranCorpImageEntity> imageEntities = branCorpImageDao.findBranCorpImagesByBranCorpId(branCorpId);
        for (BranCorpImageEntity corpImageEntity : imageEntities) {
            CorpImageListResult.CorpImageResult result = new CorpImageListResult.CorpImageResult();
            result.setId(corpImageEntity.getId());
            result.setUrl(adminFileService.generateCorpImageUrl(corpImageEntity.getId(), branCorpId));
            results.add(result);
        }
        return results;
    }

    @Override
    public void deleteCorpImageById(String imageId) throws AryaServiceException {
        branCorpImageDao.deleteBranCorpImageById(imageId);
    }

    @Override
    public AryaDepartmentDetailResult createAryaDepartment(CreateAryaDepartmentCommand command) throws AryaServiceException {
        CorporationEntity groupEntity = corporationDao.findCorporationByIdThrow(command.getGroupId());
        if (groupEntity.getIsGroup() == com.bumu.bran.common.Constants.FALSE) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ONLY_GROUP_ADD_DEPARTMENT);
        }
        if (StringUtils.isAnyBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_DEPATMENT_NAME_EMPTY);
        }
        command.setName(command.getName().trim());

        if (aryaDepartmentDao.isExistSameDepartment(command.getGroupId(), command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_ALREADY_EXIST);
        }
        AryaDepartmentEntity departmentEntity = new AryaDepartmentEntity();
        departmentEntity.setId(Utils.makeUUID());
        departmentEntity.setName(command.getName());
        departmentEntity.setCorpId(command.getGroupId());
        departmentEntity.setIsDelete(com.bumu.bran.common.Constants.FALSE);
        departmentEntity.setCreateTime(System.currentTimeMillis());
        departmentEntity.setUpdateTime(departmentEntity.getCreateTime());
        StringBuffer logStr = new StringBuffer("【企业信息管理】新增通用部门:" + departmentEntity.getName() + ",id:" + departmentEntity.getId());
        try {
            aryaDepartmentDao.create(departmentEntity);
            opLogService.successLog(CORP_CREATE_DEPARMENT, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(CORP_CREATE_DEPARMENT, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_ADD_FAILED);
        }
        AryaDepartmentDetailResult result = new AryaDepartmentDetailResult();
        result.setDepartmentId(departmentEntity.getId());
        result.setName(departmentEntity.getName());
        result.setCreateTime(departmentEntity.getCreateTime());
        return result;
    }

    @Override
    public AryaDepartmentDetailResult getAryaDepartmentDetail(String departmentId) throws AryaServiceException {
        AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
        if (aryaDepartmentEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_NOT_FOUND);
        }
        AryaDepartmentDetailResult detailResult = new AryaDepartmentDetailResult();
        detailResult.setDepartmentId(aryaDepartmentEntity.getId());
        detailResult.setName(aryaDepartmentEntity.getName());
        detailResult.setCreateTime(aryaDepartmentEntity.getCreateTime());
        detailResult.setUpdateTime(aryaDepartmentEntity.getUpdateTime());
        return detailResult;
    }

    @Override
    public void updateDepartment(UpdateAryaDepartmentCommand command) {
        AryaDepartmentEntity departmentEntity = aryaDepartmentDao.findDepartmentById(command.getDepartmentId());
        if (departmentEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_NOT_FOUND);
        }
        if (StringUtils.isAnyBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_DEPATMENT_NAME_EMPTY);
        }
        command.setName(command.getName().trim());
        if (aryaDepartmentDao.isExistSameDepartment(departmentEntity.getCorpId(), command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_ALREADY_EXIST);
        }
        departmentEntity.setName(command.getName());
        departmentEntity.setUpdateTime(System.currentTimeMillis());
        StringBuffer logStr = new StringBuffer("【企业信息管理】修改通用部门:" + departmentEntity.getName() + ",id:" + departmentEntity.getId());
        try {
            aryaDepartmentDao.update(departmentEntity);
            monthSalaryDao.updateDeparmentName(departmentEntity.getId(), command.getName());
            weekSalaryDao.updateDeparmentName(departmentEntity.getId(), command.getName());
            opLogService.successLog(CORP_UPDATE_DEPARMENT, logStr, log);
        } catch (Exception e) {
            opLogService.failedLog(CORP_UPDATE_DEPARMENT, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORP_UPDATE_DEPARTMENT_FAILED);
        }

    }

    @Override
    public void deleteAryaDepartment(String departmentId) throws AryaServiceException {
        StringBuffer logStr = new StringBuffer("【企业信息管理】删除通用部门id:" + departmentId);
        if (monthSalaryDao.isDepartmentInUse(departmentId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_CAN_NOT_DELETE);
        }
        try {
            aryaDepartmentDao.deleteDepartment(departmentId);
            opLogService.successLog(CORP_UPDATE_DEPARMENT, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(CORP_UPDATE_DEPARMENT, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_DELETE_FAILED);
        }
    }

    @Override
    public OrganizationTreeResult generateSalaryOrganizationTree() {
        List<CorporationEntity> groups = corporationDao.findGroupsByBusinessType(CorpConstants.CORP_BUSINESS_SALARY);
        OrganizationTreeResult treeResult = new OrganizationTreeResult();
        if (groups.size() == 0) {
            return treeResult;
        }
        List<String> groupIds = new ArrayList<>();
        List<OrganizationTreeResult.OrganizationResult> results = new ArrayList<>();
        for (CorporationEntity corporationEntity : groups) {
            groupIds.add(corporationEntity.getId());
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            organizationResult.setId(corporationEntity.getId());
            organizationResult.setName(corporationEntity.getName());
            organizationResult.setType(CorpConstants.CORP_TYPE_GROUP);//集团
            results.add(organizationResult);
        }
        treeResult.setTree(results);
        if (groupIds.size() == 0) {
            return treeResult;
        }
        results.addAll(getGroupsAllAryaDepartmentsTreeResult(groupIds));
        return treeResult;
    }

    @Override
    public OrganizationTreeResult generateOrganizationTree() {
        List<CorporationEntity> corporationEntities = corporationDao.findAllNoParentCorpByBusinessType();
        OrganizationTreeResult treeResult = new OrganizationTreeResult();
        if (corporationEntities.size() == 0) {
            return treeResult;
        }
        List<String> groupIds = new ArrayList<>();
        List<OrganizationTreeResult.OrganizationResult> results = new ArrayList<>();
        for (CorporationEntity corporationEntity : corporationEntities) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            if (corporationEntity.getIsGroup() == com.bumu.bran.common.Constants.TRUE) {
                groupIds.add(corporationEntity.getId());
                organizationResult.setType(CorpConstants.CORP_TYPE_GROUP);//集团
            } else {
                organizationResult.setType(CorpConstants.CORP_TYPE_TOP_LEAVER_CORP);//一级公司
            }
            organizationResult.setId(corporationEntity.getId());
            organizationResult.setName(corporationEntity.getName());
            results.add(organizationResult);
        }
        treeResult.setTree(results);
        if (groupIds.size() == 0) {
            //如果没有集团，则不用往下组合通用部门和子公司
            return treeResult;
        }
        //获取所有部门
        results.addAll(getGroupsAllAryaDepartmentsTreeResult(groupIds));
        //获取所有子公司
        results.addAll(getGroupsAllSubCorpsTreeResult(groupIds));
        return treeResult;
    }

    @Override
    public OrganizationTreeResult generateEntryOrganizationTree() {
        List<CorporationEntity> corps = corporationDao.findNoParentCorpsByBusinessType(CorpConstants.CORP_BUSINESS_RUZHI);
        OrganizationTreeResult treeResult = new OrganizationTreeResult();
        if (corps.size() == 0) {
            return treeResult;
        }
        List<String> groupIds = new ArrayList<>();
        List<OrganizationTreeResult.OrganizationResult> results = new ArrayList<>();
        //构建集团和一级公司
        for (CorporationEntity corporationEntity : corps) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            if (corporationEntity.getIsGroup() == com.bumu.bran.common.Constants.TRUE) {
                groupIds.add(corporationEntity.getId());
                organizationResult.setType(CorpConstants.CORP_TYPE_GROUP);//集团
            } else {
                organizationResult.setType(CorpConstants.CORP_TYPE_TOP_LEAVER_CORP);//一级公司
            }
            organizationResult.setId(corporationEntity.getId());
            organizationResult.setName(corporationEntity.getName());
            results.add(organizationResult);
        }
        treeResult.setTree(results);
        if (groupIds.size() == 0) {
            return treeResult;
        }

        //构建子公司
        List<CorporationEntity> subCorps = corporationDao.findGroupsAllSubCorps(groupIds);
        for (CorporationEntity subCorp : subCorps) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            organizationResult.setType(CorpConstants.CORP_TYPE_SUBCORP);//子公司
            organizationResult.setParentId(subCorp.getParentId());
            organizationResult.setId(subCorp.getId());
            organizationResult.setName(subCorp.getName());
            results.add(organizationResult);
        }
        return treeResult;
    }

    @Override
    public OrganizationTreeResult generateEntryAndWelfareOrganizationTree(int businessType) {
        List<CorporationEntity> corporationEntities = corporationDao.findNoParentCorpsByBusinessType(businessType);
        OrganizationTreeResult treeResult = new OrganizationTreeResult();
        if (corporationEntities.size() == 0) {
            return treeResult;
        }
        List<String> groupIds = new ArrayList<>();
        List<OrganizationTreeResult.OrganizationResult> results = new ArrayList<>();
        for (CorporationEntity corporationEntity : corporationEntities) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            if (corporationEntity.getIsGroup() == com.bumu.bran.common.Constants.TRUE) {
                groupIds.add(corporationEntity.getId());
                organizationResult.setType(CorpConstants.CORP_TYPE_GROUP);//集团
            } else {
                organizationResult.setType(CorpConstants.CORP_TYPE_TOP_LEAVER_CORP);//一级公司
            }
            organizationResult.setId(corporationEntity.getId());
            organizationResult.setName(corporationEntity.getName());
            results.add(organizationResult);
        }
        treeResult.setTree(results);
        if (groupIds.size() == 0) {
            //如果没有集团，则不用往下组合通用部门和子公司
            return treeResult;
        }
        if (businessType != CorpConstants.CORP_BUSINESS_ATTENDANCE) {
            //获取所有子公司
            results.addAll(getGroupsAllSubCorpsTreeResult(groupIds));
        }
        return treeResult;
    }

    @Override
    public List<OrganizationTreeResult.OrganizationResult> getGroupsAllAryaDepartmentsTreeResult(List<String> groupIds) {
        List<OrganizationTreeResult.OrganizationResult> treeResult = new ArrayList<>();
        //查询通用部门
        List<AryaDepartmentEntity> aryaDepartmentEntities = aryaDepartmentDao.findDepartmentsByGroupIds(groupIds);
        if (aryaDepartmentEntities.size() == 0) {
            return treeResult;
        }
        for (AryaDepartmentEntity aryaDepartmentEntity : aryaDepartmentEntities) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            organizationResult.setId(aryaDepartmentEntity.getId());
            organizationResult.setName(aryaDepartmentEntity.getName());
            organizationResult.setParentId(aryaDepartmentEntity.getCorpId());
            organizationResult.setType(CorpConstants.CORP_TYPE_ARYADEPARTMENT);
            treeResult.add(organizationResult);
        }
        return treeResult;
    }

    @Override
    public List<OrganizationTreeResult.OrganizationResult> getGroupsAllSubCorpsTreeResult(List<String> groupIds) {
        List<OrganizationTreeResult.OrganizationResult> treeResult = new ArrayList<>();
        List<CorporationEntity> subCorpEntities = corporationDao.findGroupsAllSubCorps(groupIds);
        if (subCorpEntities.size() == 0) {
            return treeResult;
        }
        for (CorporationEntity corporationEntity : subCorpEntities) {
            OrganizationTreeResult.OrganizationResult organizationResult = new OrganizationTreeResult.OrganizationResult();
            organizationResult.setId(corporationEntity.getId());
            organizationResult.setName(corporationEntity.getName());
            organizationResult.setParentId(corporationEntity.getParentId());
            organizationResult.setType(CorpConstants.CORP_TYPE_SUBCORP);
            treeResult.add(organizationResult);
        }
        return treeResult;
    }

    @Override
    public CorpAdminListResult getCorpAdminList(String corpId) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(corpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_CORPORATION_NOT_EXIST);
        }
        List<BranCorpUserEntity> userEntities = branCorpUserDao.findCorpAdminsBybranCorpId(corporationEntity.getBranCorpId());
        CorpAdminListResult result = new CorpAdminListResult();
        if (userEntities.size() == 0) {
            return result;
        }
        List<CorpAdminListResult.CorpAdminResult> adminResults = new ArrayList<>();
        for (BranCorpUserEntity userEntity : userEntities) {
            CorpAdminListResult.CorpAdminResult adminResult = new CorpAdminListResult.CorpAdminResult();
//            adminResult.setTryLoginTimesToday(branCorpUserCommonService.getCorpUserTryLoginTimes(userEntity.getId()));
            adminResult.setId(userEntity.getId());
            adminResult.setAccount(userEntity.getLoginName());
            adminResult.setLastLoginTime(userEntity.getLastLoginTime());
            adminResult.setLastLoginIp(userEntity.getLastLoginIp());
            adminResult.setNickName(userEntity.getNickName());
            adminResult.setCreateTime(userEntity.getCreateTime());
            adminResults.add(adminResult);
        }
        result.setAdmins(adminResults);
        return result;
    }

    @Override
    public void addCorpAdmin(CreateUpdateCorpAdminCommand command, String operator) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationById(command.getCorpId());
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_CORPORATION_NOT_EXIST);
        }
        //检查账号是否被使用
        if (StringUtils.isAnyBlank(command.getAccount())) {
            throw new AryaServiceException(ErrorCode.CODE_USER_ACCOUNT_EMPTY);
        }

        BranCorpUserEntity existUser = branCorpUserDao.findBranCorpUserByAccountWithDeleted(command.getAccount());
        if (existUser != null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_USER_ACCOUNT_EXIST);
        }

        if (StringUtils.isAnyBlank(command.getPassword())) {
            throw new AryaServiceException(ErrorCode.CODE_USER_PASSWORD_EMPTY);
        }
        BranCorpUserEntity corpUserEntity = new BranCorpUserEntity();
        corpUserEntity.setId(Utils.makeUUID());
        corpUserEntity.setCreateTime(System.currentTimeMillis());
        corpUserEntity.setBranCorpId(corporationEntity.getBranCorpId());
        corpUserEntity.setNickName(command.getNickName());
        corpUserEntity.setLoginName(command.getAccount());
        corpUserEntity.setLoginPwd(SysUtils.encryptPassword(command.getPassword()));
        corpUserEntity.setIsAdmin(com.bumu.bran.common.Constants.TRUE);
        corpUserEntity.setIsDelete(com.bumu.bran.common.Constants.FALSE);
        corpUserEntity.setUpdateTime(corpUserEntity.getCreateTime());
        corpUserEntity.setCreateUser(operator);
        corpUserEntity.setUpdateUser(operator);
        corpUserEntity.setEmail(command.getEmail());
        StringBuffer logStr = new StringBuffer("【入职管理】公司id:" + corporationEntity.getId() + ",新增企业管理员id:" + corpUserEntity.getId());
        try {
            branCorpUserDao.create(corpUserEntity);
            opLogService.successLog(ENTRY_ADD_CORP_ADMIN, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(ENTRY_ADD_CORP_ADMIN, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_USER_CREATE_CORP_USER_FAILED);
        }
    }

    @Override
    public void updateCorpAdmin(CreateUpdateCorpAdminCommand command, String operator) throws AryaServiceException {
        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(command.getId());
        if (corpUserEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
        }

        StringBuffer logStr = new StringBuffer("【入职管理】修改企业管理员信息,id:" + corpUserEntity.getId());
        if (StringUtils.isNoneBlank(command.getEmail())) {
            corpUserEntity.setEmail(command.getEmail());
            logStr.append("修改邮箱:" + command.getEmail());
        }

        if (StringUtils.isNoneBlank(command.getPassword())) {
            corpUserEntity.setLoginPwd(SysUtils.encryptPassword(command.getPassword()));
            logStr.append("修改密码:" + command.getPassword());
        }

        if (StringUtils.isNoneBlank(command.getNickName())) {
            corpUserEntity.setNickName(command.getNickName());
            logStr.append("修改昵称:" + command.getNickName());
        }

        corpUserEntity.setUpdateTime(System.currentTimeMillis());

        try {
            branCorpUserDao.update(corpUserEntity);
            opLogService.successLog(ENTRY_UPDATE_CORP_ADMIN, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(ENTRY_UPDATE_CORP_ADMIN, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_UPDATE_FAILED);
        }
    }

    @Override
    public GetCorpCheckInCodeAndQRCodeResult getCorpCheckInCodeAndQRCode(String corpId) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(corpId);
        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_CORPORATION_NOT_EXIST);
        }
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(corporationEntity.getBranCorpId());
        if (branCorporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_CORPORATION_NOT_EXIST);
        }
        GetCorpCheckInCodeAndQRCodeResult result = new GetCorpCheckInCodeAndQRCodeResult();
        result.setCode(branCorporationEntity.getCheckinCode());
        result.setIsEffective(com.bumu.bran.common.Constants.TRUE);
        result.setQrcodeUrl("admin/entry/corporation/qrcode?code=" + branCorporationEntity.getCheckinCode());
        return result;
    }

    @Override
    public void upgradeCorpToGroup(String corpId) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(corpId);
        if (corporationEntity.getIsGroup() == com.bumu.bran.common.Constants.TRUE) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_IS_GROUP_ALREADY);
        }
        if (StringUtils.isNotBlank(corporationEntity.getParentId())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_IS_SUBCORP_CANT_UPGRADE);
        }
        corporationEntity.setIsGroup(com.bumu.bran.common.Constants.TRUE);
        try {
            corporationDao.update(corporationEntity);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_UPGRADE_FAILED);
        }
    }

    @Override
    public void checkCorpCreateOrUpdateCommand(CorpModel corpModel) throws AryaServiceException {
        if (StringUtils.isNotBlank(corpModel.getCorpName()) && corpModel.getCorpName().length() > 128) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NAME_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getCorpShortName()) && corpModel.getCorpShortName().length() > 16) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_SHORT_NAME_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getContactName()) && corpModel.getContactName().length() > 16) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CONTACT_NAME_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getContactPhone()) && corpModel.getContactPhone().length() > 16) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CONTACT_PHONE_TOO_LONG);
        }
        if (StringUtils.isNotBlank(corpModel.getContactPhone()) && !StringUtils.isNumeric(corpModel.getContactPhone())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CONTACT_PHONE_WRONG);
        }

        if (StringUtils.isNotBlank(corpModel.getContactMail()) && !SysUtils.checkEmail(corpModel.getContactMail())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_CONTACT_MAIL_WRONG);
        }

        if (StringUtils.isNotBlank(corpModel.getAddress()) && corpModel.getAddress().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ADDRESS_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getLongitude()) && corpModel.getLongitude().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_LONGITUDE_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getLongitude()) && !StringUtils.isNumeric(corpModel.getLongitude())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_LONGITUDE_WRONG);
        }

        if (StringUtils.isNotBlank(corpModel.getLatitude()) && corpModel.getLatitude().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_LATITUDE_TOO_LONG);
        }

        if (StringUtils.isNotBlank(corpModel.getLatitude()) && !StringUtils.isNumeric(corpModel.getLatitude())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_LATITUDE_WRONG);
        }

        if (StringUtils.isNotBlank(corpModel.getDesc()) && corpModel.getDesc().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_DESC_TOO_LONG);
        }

        //检查公司重名
        CorporationEntity sameNameCorpEntity;
        if (StringUtils.isAnyBlank(corpModel.getCorpName())) {
            sameNameCorpEntity = null;
        } else {
            sameNameCorpEntity = corporationDao.findCorpByName(corpModel.getCorpName());
        }
        if (sameNameCorpEntity != null) {
            if (StringUtils.isNotBlank(corpModel.getCorpId()) && sameNameCorpEntity.getId().equals(corpModel.getCorpId())) {
                return;
            }
            if ((StringUtils.isAnyBlank(sameNameCorpEntity.getParentId()) && StringUtils.isAnyBlank(corpModel.getParentId())) || (sameNameCorpEntity.getParentId()).equals(corpModel.getParentId())) {
                throw new AryaServiceException(ErrorCode.CODE_CORPORATION_EXIST_SAME_NAME_CORP);
            }
        }
    }

    @Override
    public CorpAttendanceClockTypeResult getAttendanceClockWay(String corpId) throws AryaServiceException {
        CorpAttendanceClockTypeResult result = new CorpAttendanceClockTypeResult();
        List<CorpAttendanceClockTypeResult.ClockType> clockTypeResult = new ArrayList<>();
        if (StringUtils.isAnyBlank(corpId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
        }
        List<CorpBusinessDetailEntity> corpBusinessDetailEntities = corpBusinessDetailDao.findByCorpIdAndType(corpId, CorpConstants.CORP_BUSINESS_ATTENDANCE);
        if (ListUtils.checkNullOrEmpty(corpBusinessDetailEntities)) {
            throw new AryaServiceException(ErrorCode.CODE_CORA_NOT_OPEAN_ATTENDANCE_BUSINESS);
        }
        for (CorpBusinessDetailEntity corpBusinessDetailEntity : corpBusinessDetailEntities) {
            CorpAttendanceClockTypeResult.ClockType clockType = new CorpAttendanceClockTypeResult.ClockType();
            clockType.setType(corpBusinessDetailEntity.getCorpBusinessType());
            clockTypeResult.add(clockType);
        }
        result.setCorpId(corpId);
        result.setClockTypes(clockTypeResult);
        return result;
    }

    @Override
    public void updateAttendanceClockWay(CorpAttendanceClockTypeCommand command) throws AryaServiceException {
        if (StringUtils.isAnyBlank(command.getCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
        }
        if (ListUtils.checkNullOrEmpty(command.getClockTypes())) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ATTENDANCE_CLOCK_TYPE_IS_NULL);
        }
//        if (command.getClockTypes().size() > 1) {
//            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ATTENDANCE_CLOCK_TYPE_ONLY_ONE);
//        }
        List<CorpBusinessDetailEntity> corpBusinessDetailEntities = corpBusinessDetailDao.findByCorpIdAndType(command.getCorpId(), CorpConstants.CORP_BUSINESS_ATTENDANCE);
        try {
            if (!ListUtils.checkNullOrEmpty(corpBusinessDetailEntities)) {
                corpBusinessDetailDao.delete(corpBusinessDetailEntities);
            }
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ATTENDANCE_CLOCK_UPDATE_FILED);
        }
        List<CorpBusinessDetailEntity> businessDetailEntities = new ArrayList<>();
        for (CorpAttendanceClockTypeCommand.ClockType clockType : command.getClockTypes()) {
            CorpBusinessDetailEntity corpBusinessDetailEntity = creatCorpBusinessDetail(command.getCorpId(), CorpConstants.CORP_BUSINESS_ATTENDANCE);
            corpBusinessDetailEntity.setCorpBusinessType(clockType.getType());
            businessDetailEntities.add(corpBusinessDetailEntity);
        }
        try {
            if (!ListUtils.checkNullOrEmpty(businessDetailEntities)) {
                corpBusinessDetailDao.create(businessDetailEntities);
            }
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ATTENDANCE_CLOCK_UPDATE_FILED);
        }
    }

    @Override
    public List<CorporationEntity> findNoParentCorpsByBusinessType(int corpBusinessWalletpays) {
        return corporationDao.findNoParentCorpsByBusinessType(corpBusinessWalletpays);
    }

    @Override
    public CorporationEntity findByBranId(String branId) {
        return corporationDao.findByBranId(branId);
    }
}
