package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.soin.controller.command.CreateOrUpdateSoinSupplierCommand;
import com.bumu.arya.admin.soin.controller.command.IdListCommand;
import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.soin.result.SoinSupplierListResult;
import com.bumu.arya.admin.soin.model.dao.SoinDistrictSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinDistrictSupplierEntity;
import com.bumu.arya.admin.soin.model.dao.SoinSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.soin.service.SoinSupplierService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.soin.model.dao.AryaSoinDistrictDao;
import com.bumu.arya.soin.model.entity.AryaSoinDistrictEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.common.result.ZtreeDistrictListResult;
import com.bumu.arya.common.service.CommonDistrictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bumu.arya.common.OperateConstants.*;

/**
 * Created by CuiMengxin on 16/8/2.
 */
@Service
public class SoinSupplierServiceImpl implements SoinSupplierService {

    Logger log = LoggerFactory.getLogger(SoinOrderBillServiceImpl.class);

    @Autowired
    OpLogService opLogService;

    @Autowired
    CommonDistrictService commonDistrictService;

    @Autowired
    SoinSupplierDao soinSupplierDao;

    @Autowired
    SoinDistrictSupplierDao soinDistrictSupplierDao;

    @Autowired
    AryaSoinDistrictDao soinDistrictDao;

    @Override
    public ZtreeDistrictListResult getAllSoinDistricts() throws AryaServiceException {
        return commonDistrictService.getAllSoinDistricts();
    }

    @Override
    public SoinSupplierListResult getAllSoinSuppliers(int page, int pageSize) throws AryaServiceException {
        SoinSupplierListResult listResult = new SoinSupplierListResult();
        List<SoinSupplierEntity> soinSupplierEntities = soinSupplierDao.findSoinSupplierPagination(page, pageSize);
        for (SoinSupplierEntity soinSupplierEntity : soinSupplierEntities) {
            SoinSupplierListResult.SoinSupplierResult supplierResult = new SoinSupplierListResult.SoinSupplierResult();
            supplierResult.setId(soinSupplierEntity.getId());
            supplierResult.setName(soinSupplierEntity.getSupplierName());
            supplierResult.setFee(soinSupplierEntity.getSoinFee());
            listResult.getSuppliers().add(supplierResult);

            List<SoinDistrictSupplierEntity> soinDistrictSupplierEntities = soinDistrictSupplierDao.findBySupplierId(soinSupplierEntity.getId());
            if (soinDistrictSupplierEntities == null) {
                continue;
            }
            supplierResult.setDistrictCount(soinDistrictSupplierEntities.size());
            for (SoinDistrictSupplierEntity districtSupplierEntity : soinDistrictSupplierEntities) {
                SoinSupplierListResult.DistrictResult districtResult = new SoinSupplierListResult.DistrictResult();
                districtResult.setId(districtSupplierEntity.getDistrictId());
                districtResult.setName(commonDistrictService.getDistrictCombination(districtSupplierEntity.getDistrictId()).getNameCombinationStr());
                supplierResult.getDistricts().add(districtResult);
            }
        }
        int totalRowCount = soinSupplierDao.countAllSuppliers();
        listResult.setPages(Utils.calculatePages(totalRowCount, pageSize));
        return listResult;
    }

    @Override
    public SimpleResult createSoinSupplier(CreateOrUpdateSoinSupplierCommand command) throws AryaServiceException {
        if (StringUtils.isAnyBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NAME_EMPTY);
        }
        SoinSupplierEntity supplierEntity = soinSupplierDao.findSoinSupplierByName(command.getName());
        if (supplierEntity != null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NAME_IS_EXIST);
        }
        SoinSupplierEntity newSupplier = new SoinSupplierEntity();
        newSupplier.setId(Utils.makeUUID());
        newSupplier.setIsDelete(CorpConstants.FALSE);
        newSupplier.setSoinFee(command.getFee());
        newSupplier.setSupplierName(command.getName());
        newSupplier.setCreateTime(System.currentTimeMillis());
        StringBuffer logMsg = new StringBuffer("【供应商管理】新增供应商id:" + newSupplier.getId() + ",名称:" + newSupplier.getSupplierName());
        try {
            soinSupplierDao.create(newSupplier);
            opLogService.successLog(SOIN_SUPPLIER_CREATE, logMsg, log);
        } catch (Exception e) {
            e.printStackTrace();
            opLogService.failedLog(SOIN_SUPPLIER_CREATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_CREATE_FAILED);
        }
        SimpleResult result = new SimpleResult();
        result.setId(newSupplier.getId());
        result.setName(newSupplier.getSupplierName());

        return result;
    }

    @Override
    public void updateSoinSupplier(CreateOrUpdateSoinSupplierCommand command) throws AryaServiceException {
        if (StringUtils.isAnyBlank(command.getId())) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_ID_EMPTY);
        }
        SoinSupplierEntity supplierEntity = soinSupplierDao.findSoinSupplier(command.getId().trim());
        if (supplierEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NOT_FOUND);
        }
        StringBuffer logMsg = new StringBuffer("【供应商管理】更新供应商id:" + supplierEntity.getId() + ",名称:" + supplierEntity.getSupplierName());
        if (StringUtils.isNotBlank(command.getName())) {
            SoinSupplierEntity otherSupplier = soinSupplierDao.findSoinSupplierByName(command.getName().trim());
            if (otherSupplier != null && !otherSupplier.getId().equals(command.getId().trim())) {
                throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NAME_IS_EXIST);
            }
            supplierEntity.setSupplierName(command.getName());
            logMsg.append(",名称修改为:" + command.getName());
        }
        logMsg.append(",管理费从" + supplierEntity.getSoinFee() + "修改为:" + command.getFee());
        supplierEntity.setSoinFee(command.getFee());

        try {
            soinSupplierDao.update(supplierEntity);
            opLogService.successLog(SOIN_SUPPLIER_UPDATE, logMsg, log);
        } catch (Exception e) {
            e.printStackTrace();
            opLogService.failedLog(SOIN_SUPPLIER_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteSoinSupplier(IdListCommand command) throws AryaServiceException {
        if (command.getIds().size() == 0) {
            return;
        }
        List<String> ids = new ArrayList<>();
        for (IdListCommand.IdCommand deleteCommand : command.getIds()) {
            ids.add(deleteCommand.getId());
        }
        List<SoinSupplierEntity> supplierEntities = soinSupplierDao.findSoinSuppliers(ids);
        if (supplierEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NOT_FOUND);
        }
        //检查供应商是否被使用
        for (SoinSupplierEntity supplierEntity : supplierEntities) {
            if (soinDistrictSupplierDao.isSupplierUsing(supplierEntity.getId())) {
                throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_CANT_DELETE);
            }
            supplierEntity.setIsDelete(CorpConstants.TRUE);
        }


        StringBuffer logMsg = new StringBuffer("【供应商管理】删除供应商id:" + StringUtils.join(ids, ","));
        try {
            soinSupplierDao.update(supplierEntities);
            opLogService.successLog(SOIN_SUPPLIER_DELETE, logMsg, log);
        } catch (Exception e) {
            e.printStackTrace();
            opLogService.failedLog(SOIN_SUPPLIER_DELETE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_DELETE_FAILED);
        }
    }

    @Override
    public SoinSupplierListResult querySoinDistrictAllSuppliers(String districtId) throws AryaServiceException {
        SoinSupplierListResult listResult = new SoinSupplierListResult();
        List<SoinDistrictSupplierEntity> districtSupplierEntities = soinDistrictSupplierDao.findDistrictAllSuppliers(districtId);
        List<String> suppliersId = new ArrayList<>();
        for (SoinDistrictSupplierEntity soinDistrictSupplierEntity : districtSupplierEntities) {
            SoinSupplierListResult.SoinSupplierResult soinSupplierResult = new SoinSupplierListResult.SoinSupplierResult();
            soinSupplierResult.setId(soinDistrictSupplierEntity.getSupplierId());
            soinSupplierResult.setIsDefault(soinDistrictSupplierEntity.getIsDefault());
            listResult.getSuppliers().add(soinSupplierResult);
            suppliersId.add(soinDistrictSupplierEntity.getSupplierId());
        }
        if (suppliersId.size() == 0) {
            return listResult;
        }

        List<SoinSupplierEntity> soinSupplierEntities = soinSupplierDao.findSuppliersByIds(suppliersId);
        for (SoinSupplierEntity supplierEntity : soinSupplierEntities) {
            for (SoinSupplierListResult.SoinSupplierResult soinSupplierResult : listResult.getSuppliers()) {
                if (supplierEntity.getId().equals(soinSupplierResult.getId())) {
                    soinSupplierResult.setName(supplierEntity.getSupplierName());
                    soinSupplierResult.setFee(supplierEntity.getSoinFee());
                    break;
                }
            }
        }
        return listResult;
    }

    @Override
    public SoinSupplierListResult querySoinDistrictAllUnusedSuppliers(String districtId, int page, int pageSize) throws AryaServiceException {
        SoinSupplierListResult listResult = new SoinSupplierListResult();
        List<SoinDistrictSupplierEntity> districtSupplierEntities = soinDistrictSupplierDao.findDistrictAllSuppliers(districtId);
        List<String> suppliersId = new ArrayList<>();
        for (SoinDistrictSupplierEntity soinDistrictSupplierEntity : districtSupplierEntities) {
            suppliersId.add(soinDistrictSupplierEntity.getId());
        }
        List<SoinSupplierEntity> soinSupplierEntities;
        int count = 0;
        if (suppliersId.size() == 0) {
            soinSupplierEntities = soinSupplierDao.findSoinSupplierPagination(page, pageSize);
            count = soinSupplierDao.countAllSuppliers();
        } else {
            soinSupplierEntities = soinSupplierDao.findPaginationSuppliersWithoutIds(suppliersId, page, pageSize);
            count = soinSupplierDao.coutSuppliersWithoutIds(suppliersId);
        }

        for (SoinSupplierEntity supplierEntity : soinSupplierEntities) {
            SoinSupplierListResult.SoinSupplierResult soinSupplierResult = new SoinSupplierListResult.SoinSupplierResult();
            soinSupplierResult.setId(supplierEntity.getId());
            soinSupplierResult.setIsDefault(CorpConstants.FALSE);
            soinSupplierResult.setFee(supplierEntity.getSoinFee());
            soinSupplierResult.setName(supplierEntity.getSupplierName());
            listResult.getSuppliers().add(soinSupplierResult);
        }
        listResult.setPages(Utils.calculatePages(count, pageSize));
        return listResult;
    }

    @Override
    public void addSupplierToSoinDistrict(String districtId, String supplierId) throws AryaServiceException {
        AryaSoinDistrictEntity soinDistrictEntity = soinDistrictDao.findDistrictById(districtId);
        if (soinDistrictEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_NOT_SOIN_DISTRICT);
        }
        if (soinDistrictSupplierDao.isDistrctHasSupplier(districtId, supplierId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_HAS_SAME_SUPPLIER_ALREADY);
        }

        SoinDistrictSupplierEntity soinDistrictSupplierEntity = new SoinDistrictSupplierEntity();
        soinDistrictSupplierEntity.setId(Utils.makeUUID());
        soinDistrictSupplierEntity.setIsDelete(CorpConstants.FALSE);
        soinDistrictSupplierEntity.setCreateTime(System.currentTimeMillis());
        soinDistrictSupplierEntity.setDistrictId(districtId);
        soinDistrictSupplierEntity.setSupplierId(supplierId);
        soinDistrictSupplierEntity.setIsDefault(CorpConstants.FALSE);

        StringBuffer logMsg = new StringBuffer("【地区供应商管理】地区id:" + districtId + ",添加供应商id" + supplierId);
        SoinDistrictSupplierEntity districtDefaultSupplier = soinDistrictSupplierDao.findDistrictPrimarySupplier(districtId);
        if (districtDefaultSupplier == null) {
            soinDistrictSupplierEntity.setIsDefault(CorpConstants.TRUE);//如果该地区没有首选供应商则自动变成首选
            logMsg.append("该地区尚未添加供应商,已自动将该供应商置为首选供应商。");
        }
        try {
            soinDistrictSupplierDao.create(soinDistrictSupplierEntity);
            opLogService.successLog(SOIN_DISTRICT_ADD_SUPPLIER, logMsg, log);
        } catch (Exception e) {
            e.printStackTrace();
            opLogService.successLog(SOIN_DISTRICT_ADD_SUPPLIER, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_ADD_SUPPLIER_FAILED);
        }
    }

    @Override
    public void removeSupplierToSoinDistrict(String districtId, String supplierId) throws AryaServiceException {
        SoinDistrictSupplierEntity districtSupplierEntity = soinDistrictSupplierDao.isDistrctHasSupplier(districtId, supplierId);
        if (districtSupplierEntity != null) {
            StringBuffer logMsg = new StringBuffer("【地区供应商管理】地区id:" + districtId + ",移除供应商id:" + supplierId);
            try {
                soinDistrictSupplierDao.delete(districtSupplierEntity);
                soidDistrictSetPrimarySupplier(districtId);
                opLogService.successLog(SOIN_DISTRICT_REMOVE_SUPPLIER, logMsg, log);
            } catch (Exception e) {
                e.printStackTrace();
                opLogService.failedLog(SOIN_DISTRICT_REMOVE_SUPPLIER, logMsg, log);
                throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_REMOVE_SUPPLIER_FAILED);
            }
        }
    }

    @Override
    public void setSetSoinDistrctPrimarySupplier(String districtId, String supplierId) throws AryaServiceException {
        AryaSoinDistrictEntity soinDistrictEntity = soinDistrictDao.findDistrictById(districtId);
        if (soinDistrictEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_NOT_SOIN_DISTRICT);
        }
        SoinDistrictSupplierEntity districtSupplierEntity = soinDistrictSupplierDao.isDistrctHasSupplier(districtId, supplierId);
        if (districtSupplierEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DISTRICT_DOESNOT_HAS_THE_SUPPLIER);//供应商尚未被添加到该地区
        }
        SoinDistrictSupplierEntity primarySupplier = soinDistrictSupplierDao.findDistrictPrimarySupplier(districtId);
        if (!primarySupplier.getSupplierId().equals(districtSupplierEntity.getSupplierId())) {
            //如果不是首选供应商
            primarySupplier.setIsDefault(CorpConstants.FALSE);
            districtSupplierEntity.setIsDefault(CorpConstants.TRUE);
            primarySupplier.setUpdateTime(System.currentTimeMillis());
            districtSupplierEntity.setUpdateTime(System.currentTimeMillis());
            StringBuffer logMsg = new StringBuffer("【地区供应商管理】设置地区:" + districtId + "的供应商从:" + primarySupplier.getSupplierId() + "为:" + districtSupplierEntity.getSupplierId());
            try {
                soinDistrictSupplierDao.update(primarySupplier);
                soinDistrictSupplierDao.update(districtSupplierEntity);
                opLogService.successLog(SOIN_DISTRICT_SET_PRIMARY_SUPPLIER, logMsg, log);
            } catch (Exception e) {
                e.printStackTrace();
                opLogService.failedLog(SOIN_DISTRICT_SET_PRIMARY_SUPPLIER, logMsg, log);
                throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_SET_PRIMARY_FAILED);
            }
        }
    }

    /**
     * 自动重置首选供应商
     *
     * @param districtId
     */
    public void soidDistrictSetPrimarySupplier(String districtId) {
        List<SoinDistrictSupplierEntity> districtSupplierEntities = soinDistrictSupplierDao.findDistrictAllSuppliers(districtId);
        if (districtSupplierEntities.size() == 0) {
            return;
        }
        List<SoinDistrictSupplierEntity> changedList = new ArrayList<>();
        for (int i = 0; i < districtSupplierEntities.size(); i++) {
            SoinDistrictSupplierEntity districtSupplierEntity = districtSupplierEntities.get(i);
            if (i == 0) {
                if (districtSupplierEntity.getIsDefault() == CorpConstants.FALSE) {
                    districtSupplierEntity.setIsDefault(CorpConstants.TRUE);
                    changedList.add(districtSupplierEntity);
                }
            } else {
                if (districtSupplierEntity.getIsDefault() == CorpConstants.TRUE) {
                    districtSupplierEntity.setIsDefault(CorpConstants.FALSE);
                    changedList.add(districtSupplierEntity);
                }
            }
        }

        try {
            if (changedList.size() > 0) {
                soinDistrictSupplierDao.update(changedList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
