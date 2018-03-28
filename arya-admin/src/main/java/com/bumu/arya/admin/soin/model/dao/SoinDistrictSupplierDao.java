package com.bumu.arya.admin.soin.model.dao;

import com.bumu.arya.admin.soin.model.entity.SoinDistrictSupplierEntity;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.KeyedCrudDao;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/4.
 */
@Transactional
public interface SoinDistrictSupplierDao extends KeyedCrudDao<SoinDistrictSupplierEntity> {

    /**
     * 根据地区Id找到首选的供应商
     *
     * @param districtId
     * @return
     */
    SoinDistrictSupplierEntity findDistrictPrimarySupplier(String districtId);

    /**
     * 根据供应商id查询所有的地区关系
     *
     * @param supplierId
     * @return
     */
    List<SoinDistrictSupplierEntity> findBySupplierId(String supplierId);

    /**
     * 检查供应商是否正在被使用
     *
     * @param supplierId
     * @return
     */
    boolean isSupplierUsing(String supplierId);

    /**
     * 查询地区下的所有供应商,先按是否是首选供应商排序,然后按创建时间排序
     *
     * @param districtId
     * @return
     */
    List<SoinDistrictSupplierEntity> findDistrictAllSuppliers(String districtId);

    /**
     * 判断地区是否已有供应商
     *
     * @param districtId
     * @param supplierId
     * @return
     */
    SoinDistrictSupplierEntity isDistrctHasSupplier(String districtId, String supplierId);
}
