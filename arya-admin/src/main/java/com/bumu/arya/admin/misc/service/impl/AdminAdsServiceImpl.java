package com.bumu.arya.admin.misc.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateAdsCommand;
import com.bumu.arya.admin.misc.result.CreateAdsResult;
import com.bumu.arya.admin.misc.result.AdsListResult;
import com.bumu.arya.admin.misc.service.AdminAdsService;
import com.bumu.arya.admin.misc.service.FileService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaAdsDao;
import com.bumu.arya.model.entity.AryaAdsEntity;
import com.bumu.bran.common.Constants;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.common.service.CommonDistrictService;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bumu.arya.common.OperateConstants.*;
import static com.bumu.arya.common.Constants.CHN_ID;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author CuiMengxin
 * @date 2016/6/13
 */
@Service
public class AdminAdsServiceImpl extends BaseBumuService implements AdminAdsService {
    Logger log = LoggerFactory.getLogger(AdminAdsServiceImpl.class);

    @Autowired
    AryaAdsDao adsDao;

    @Autowired
    CommonDistrictService commonDistrictService;

    @Autowired
    FileService fileService;

    @Autowired
    OpLogService opLogService;

    static String GET_ADS_PIC_ROUTE = "admin/operation/ads/manage/pic?file_name=";

    static final int ADS_MOVE_UP = 1;

    static final int ADS_MOVE_DOWN = 2;

    @Override
    public AdsListResult getAdsPagnationList(int page, int pageSize) throws AryaServiceException {
        AdsListResult results = new AdsListResult();
        List<AdsListResult.AdsResult> adsResults = new ArrayList<>();
        results.setAds(adsResults);
        results.setPages((int) Math.ceil(adsDao.findAllAdsCount() / new Double(pageSize)));
        List<AryaAdsEntity> adsEntities = adsDao.findPagnationAdsList(page, pageSize);
        //收集地区id
        List<String> distrcitIds = new ArrayList<>();
        for (AryaAdsEntity adsEntity : adsEntities) {
            if (isAnyBlank(adsEntity.getDistrictId())) {
                continue;
            }
            for (String distrctId : adsEntity.getDistrictId().split(":")) {
                if (!distrcitIds.contains(distrctId)) {
                    distrcitIds.add(distrctId);
                }
            }
        }
        //查询地区
        Map districtId2NameMap = commonDistrictService.generateDistrictIds2NameMap(distrcitIds);
        List tempDistcitNames = new ArrayList<>();
        for (AryaAdsEntity adsEntity : adsEntities) {
            AdsListResult.AdsResult result = new AdsListResult.AdsResult();
            result.setId(adsEntity.getId());
            if (adsEntity.getIsActive() == null) {
                result.setActive(com.bumu.bran.common.Constants.FALSE);
            } else {
                result.setActive(adsEntity.getIsActive());
            }
            if (adsEntity.getClientType() != null) {
                result.setDeviceType(adsEntity.getClientType());
            }
            if (isNotBlank(adsEntity.getDistrictId())) {
                tempDistcitNames.clear();
                for (String distrctId : adsEntity.getDistrictId().split(":")) {
                    tempDistcitNames.add(districtId2NameMap.get(distrctId));
                }
                result.setDistrcit(join(tempDistcitNames, ","));
            }
            result.setHint(adsEntity.getHint());
            result.setJumpUrl(adsEntity.getJumpUrl());
            result.setPicUrl(GET_ADS_PIC_ROUTE + adsEntity.getFileName());
            result.setFileName(adsEntity.getFileName());
            result.setJumpType(adsEntity.getJumpType() == null ? 0 : adsEntity.getJumpType());
            result.setMinVersion(adsEntity.getMinVersionCode());
            result.setMaxVersion(adsEntity.getMaxVersionCode());
            adsResults.add(result);
        }
        return results;
    }

    @Override
    public String saveAdsPic(MultipartFile file) throws AryaServiceException {
        return fileService.saveAdsPic(file);
    }

    @Override
    public CreateAdsResult createAds(CreateOrUpdateAdsCommand command) throws AryaServiceException {
        if (command.getFileName() == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_CREATE_ADS_PIC_EMPTY);
        }

        if (StringUtils.isNotBlank(command.getHint()) && command.getHint().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }

        if (StringUtils.isNotBlank(command.getJumpUrl()) && command.getJumpUrl().length() > 512) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }

        AryaAdsEntity adsEntity = new AryaAdsEntity();
        adsEntity.setFileName(command.getFileName());
        adsEntity.setId(Utils.makeUUID());
        adsEntity.setCreateTime(System.currentTimeMillis());
        adsEntity.setJumpUrl(command.getJumpUrl());
        adsEntity.setJumpType(command.getJumpType());
        if (command.getDeviceType() == null) {
            adsEntity.setClientType(255);
        } else {
            adsEntity.setClientType(command.getDeviceType());
        }
        adsEntity.setDistrictId(CHN_ID);//默认全国
        adsEntity.setIsActive(command.getActive());
        adsEntity.setHint(command.getHint());
        adsEntity.setMinVersionCode(command.getMinVersion());
        adsEntity.setMaxVersionCode(command.getMaxVersion());
        StringBuffer logStr = new StringBuffer("【广告管理】新增广告,id:" + adsEntity.getId());
        try {
            int sortIndex = 0;
            if (command.getActive() != null && command.getActive() == com.bumu.bran.common.Constants.TRUE) {
                //放在启用的最后一个
                AryaAdsEntity lastActiveAdsEntity = adsDao.findLastActiveAds();
                if (lastActiveAdsEntity != null) {
                    sortIndex = lastActiveAdsEntity.getSortIdx() + 1000;
                } else {
                    sortIndex = 1;
                }
            } else {
                //放在禁用的最后一个
                AryaAdsEntity lastNoActiveAdsEntity = adsDao.findLastNoActiveAds();
                if (lastNoActiveAdsEntity != null) {
                    sortIndex = lastNoActiveAdsEntity.getSortIdx() + 1000;
                } else {
                    sortIndex = 10000;
                }
            }
            adsEntity.setSortIdx(sortIndex);
            //检查排序号码是否被使用，如果已使用则将这个号码及之后的都整体向后移动1000
            if (adsDao.findAdsBySortIndex(sortIndex) != null) {
                adsDao.retrusionAllAdsSortAfterIndex(sortIndex);
            }
            adsDao.create(adsEntity);
            opLogService.successLog(ADS_CREATE, logStr, log);
        } catch (Exception e) {
            opLogService.failedLog(ADS_CREATE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_CREATE_ADS_FAILED);
        }
        CreateAdsResult result = new CreateAdsResult();
        result.setId(adsEntity.getId());
        return result;
    }

    @Override
    public void updateAds(CreateOrUpdateAdsCommand command) throws AryaServiceException {
        AryaAdsEntity adsEntity = adsDao.find(command.getId());
        if (adsEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ADS_NOT_FUND);
        }
        StringBuffer logStr = new StringBuffer("【广告管理】广告id:" + adsEntity.getId() + "。");
        //如果只是移位
        if (command.getDirection() != null) {
            int sortIndex = 0;
            if (command.getDirection() == ADS_MOVE_UP) {
                List<AryaAdsEntity> adsEntities = adsDao.findTwoAdsBeforeIndex(adsEntity.getSortIdx());
                if (adsEntities.size() == 0) {
                    //如果该广告之上没有广告
                    return;
                }
                if (adsEntities.size() == 2) {
                    //如果该广告之上有两个广告
                    sortIndex = (adsEntities.get(0).getSortIdx() + adsEntities.get(1).getSortIdx() + 1) / 2;
                } else if (adsEntities.size() == 1) {
                    //如果该广告之上只有一个广告
                    if (adsEntities.get(0).getSortIdx() <= 1) {
                        sortIndex = 1;
                    } else {
                        sortIndex = adsEntities.get(0).getSortIdx() / 2;
                    }
                }
                logStr.append("向上移动。");
            } else if (command.getDirection() == ADS_MOVE_DOWN) {
                List<AryaAdsEntity> adsEntities = adsDao.findTwoAdsAfterIndex(adsEntity.getSortIdx());
                if (adsEntities.size() == 0) {
                    //如果该广告之后没有广告
                    return;
                }
                if (adsEntities.size() == 2) {
                    //如果该广告之后有两个广告
                    sortIndex = (adsEntities.get(0).getSortIdx() + adsEntities.get(1).getSortIdx() + 1) / 2;
                } else if (adsEntities.size() == 1) {
                    //如果该广告之后只有一个广告
                    sortIndex = adsEntities.get(0).getSortIdx() + 1000;
                }
                logStr.append("向下移动。");
            }

            if (adsDao.findAdsBySortIndex(sortIndex) != null) {
                //如果排序号被占用，该号码及之后的广告整体后移
                adsDao.retrusionAllAdsSortAfterIndex(sortIndex);
            }
            adsEntity.setSortIdx(sortIndex);
            try {
                adsDao.update(adsEntity);
                opLogService.successLog(ADS_UPDATE, logStr, log);
            } catch (Exception e) {
                elog.error(e.getMessage(), e);
                opLogService.failedLog(ADS_UPDATE, logStr, log);
                throw new AryaServiceException(ErrorCode.CODE_SYS_ADS_MOVE_FAILED);
            }
            return;
        }


        //修改其他内容
        if (StringUtils.isNotBlank(command.getHint()) && command.getHint().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }

        if (StringUtils.isNotBlank(command.getJumpUrl()) && command.getJumpUrl().length() > 512) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }
        if (command.getFileName() != null) {
            adsEntity.setFileName(command.getFileName());
        }
        adsEntity.setJumpUrl(command.getJumpUrl());
        adsEntity.setJumpType(command.getJumpType());
        if (command.getDeviceType() == null) {
            adsEntity.setClientType(255);
        } else {
            adsEntity.setClientType(command.getDeviceType());
        }
        //修改启用状态
        if (!Objects.equals(adsEntity.getIsActive(), command.getActive())) {
            int sortIndex = 0;
            if (command.getActive() != null && command.getActive() == Constants.TRUE) {
                //启用广告
                //放在启用的最后一个
                AryaAdsEntity lastActiveAdsEntity = adsDao.findLastActiveAds();
                if (lastActiveAdsEntity != null) {
                    sortIndex = lastActiveAdsEntity.getSortIdx() + 1000;
                } else {
                    sortIndex = 1;
                }
                logStr.append("启用广告。");
            } else if (command.getActive() != null && command.getActive() == Constants.FALSE) {
                //禁用广告
                //放在禁用的最后一个
                AryaAdsEntity lastNoActiveAdsEntity = adsDao.findLastNoActiveAds();
                if (lastNoActiveAdsEntity != null) {
                    sortIndex = lastNoActiveAdsEntity.getSortIdx() + 1000;
                } else {
                    sortIndex = 10000;
                }
                logStr.append("禁用广告。");
            }
            if (adsDao.findAdsBySortIndex(sortIndex) != null) {
                //如果排序号被占用，改号码及之后的广告整体后移
                adsDao.retrusionAllAdsSortAfterIndex(sortIndex);
            }
            adsEntity.setSortIdx(sortIndex);
        }
        adsEntity.setIsActive(command.getActive());
        adsEntity.setHint(command.getHint());
        adsEntity.setMinVersionCode(command.getMinVersion());
        adsEntity.setMaxVersionCode(command.getMaxVersion());
        try {
            adsDao.update(adsEntity);
            opLogService.successLog(ADS_UPDATE, logStr, log);
        } catch (Exception e) {
            opLogService.failedLog(ADS_UPDATE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ADS_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteAds(String id) throws AryaServiceException {
        StringBuffer logStr = new StringBuffer("【广告管理】删除广告,id:" + id);
        try {
            adsDao.delete(id);
            opLogService.successLog(ADS_DELETE, logStr, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(ADS_DELETE, logStr, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
        }
    }

}
