package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.controller.command.CreateOrUpdateSoinSupplierCommand;
import com.bumu.arya.admin.soin.controller.command.IdListCommand;
import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.soin.result.SoinSupplierListResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.common.result.ZtreeDistrictListResult;

/**
 * Created by CuiMengxin on 16/8/2.
 */
public interface SoinSupplierService {

    /**
     * 获取已开通的社保地区
     *
     * @return
     * @throws AryaServiceException
     */
    ZtreeDistrictListResult getAllSoinDistricts() throws AryaServiceException;


    /**
     * 获取所有的供应商
     *
     * @return
     * @throws AryaServiceException
     */
    SoinSupplierListResult getAllSoinSuppliers(int page, int pageSize) throws AryaServiceException;

    /**
     * 新增供应商
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    SimpleResult createSoinSupplier(CreateOrUpdateSoinSupplierCommand command) throws AryaServiceException;

    /**
     * 更新供应商
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    void updateSoinSupplier(CreateOrUpdateSoinSupplierCommand command) throws AryaServiceException;

    /**
     * 删除供应商
     *
     * @param command
     * @throws AryaServiceException
     */
    void deleteSoinSupplier(IdListCommand command) throws AryaServiceException;

    /**
     * 获取某地区的所有供应商
     *
     * @param districtId
     * @return
     * @throws AryaServiceException
     */
    SoinSupplierListResult querySoinDistrictAllSuppliers(String districtId) throws AryaServiceException;

    /**
     * 获取某地区的所有尚未添加的供应商
     *
     * @param districtId
     * @param page
     * @param pageSize
     * @return
     * @throws AryaServiceException
     */
    SoinSupplierListResult querySoinDistrictAllUnusedSuppliers(String districtId, int page, int pageSize) throws AryaServiceException;

    /**
     * 为某地区添加供应商
     *
     * @param districtId
     * @param supplierId
     * @throws AryaServiceException
     */
    void addSupplierToSoinDistrict(String districtId, String supplierId) throws AryaServiceException;

    /**
     * 为某地区移除供应商
     * @param districtId
     * @param supplierId
     * @throws AryaServiceException
     */
    void removeSupplierToSoinDistrict(String districtId, String supplierId) throws AryaServiceException;

    /**
     * 为某地区设置首选供应商
     * @param districtId
     * @param supplierId
     * @throws AryaServiceException
     */
    void setSetSoinDistrctPrimarySupplier(String districtId,String supplierId) throws AryaServiceException;
}
