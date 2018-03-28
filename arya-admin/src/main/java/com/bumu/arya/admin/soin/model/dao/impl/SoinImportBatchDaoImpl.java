package com.bumu.arya.admin.soin.model.dao.impl;

import com.bumu.arya.admin.soin.model.dao.SoinImportBatchDao;
import com.bumu.arya.admin.soin.model.entity.SoinImportBatchEntity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

import java.util.List;

/**
 * 订单导入批次表
 */
@Repository
public class SoinImportBatchDaoImpl extends HibernateKeyedCrudDaoImpl<SoinImportBatchEntity> implements SoinImportBatchDao {
    /**
     * 根据 ID 查找订单导入批次表
     */
    public SoinImportBatchEntity findSoinImportBatch(String id) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinImportBatchEntity.class)
                .add(Restrictions.eq("id", id));
        List<SoinImportBatchEntity> batchEntities = criteria.list();
        if (batchEntities.size() > 0) {
            return batchEntities.get(0);
        }
        return null;
    }

    @Override
    public List findAllSoinImportBatchGroupByUser() {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        String sql = String.format("SELECT %s FROM %s GROUP BY %s",
                SoinImportBatchEntity.COL_NAME_OPERATOR_ID,
                SoinImportBatchEntity.TABLE_NAME_ARYA_SOIN_IMPORT_BATCH,
                SoinImportBatchEntity.COL_NAME_OPERATOR_ID);
        SQLQuery sqlQuery = currentSession.createSQLQuery(sql);
        return sqlQuery.list();
    }


    @Override
    public SoinImportBatchEntity findSoinImportBatch(String operatorId, String batchNo) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinImportBatchEntity.class)
                .add(Restrictions.eq("operatorId", operatorId))
                .add(Restrictions.eq("batchNo", batchNo));
        List<SoinImportBatchEntity> batchEntities = criteria.list();
        if (batchEntities.size() > 0) {
            return batchEntities.get(0);
        }
        return null;
    }

    @Override
    public List<SoinImportBatchEntity> findSalesmanAllSoinImportBatch(String salesmanId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinImportBatchEntity.class)
                .add(Restrictions.eq("operatorId", salesmanId))
                .addOrder(Order.desc("createTime"));
        return criteria.list();
    }
}