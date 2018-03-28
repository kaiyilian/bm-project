package com.bumu.arya.admin.soin.model.dao;

import com.bumu.arya.admin.soin.model.entity.SoinImportBatchEntity;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.KeyedCrudDao;

import java.util.List;

/**
 * 订单导入批次表
 */
@Transactional
public interface SoinImportBatchDao extends KeyedCrudDao<SoinImportBatchEntity> {

    /**
     * 根据 ID 查找订单导入批次表
     */
    SoinImportBatchEntity findSoinImportBatch(String id);


    /**
     * 查询所有做过导入操作的用户id
     * @return
     */
    List findAllSoinImportBatchGroupByUser();

    /**
     * 根据导入者和批次号查询
     *
     * @param operatorId
     * @param batchNo
     * @return
     */
    SoinImportBatchEntity findSoinImportBatch(String operatorId, String batchNo);

    /**
     * 查询业务员的所有导入批次
     *
     * @param salesmanId
     * @return
     */
    List<SoinImportBatchEntity> findSalesmanAllSoinImportBatch(String salesmanId);
}