package com.bumu.arya.admin.soin.model.dao.impl;

import com.bumu.arya.admin.soin.model.dao.SoinDistrictSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinDistrictSupplierEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

import java.util.List;

import static com.bumu.arya.admin.corporation.constant.CorpConstants.FALSE;
import static com.bumu.arya.admin.corporation.constant.CorpConstants.TRUE;

/**
 * Created by CuiMengxin on 16/8/4.
 */
@Repository
public class SoinDistrictSupplierDaoImpl extends HibernateKeyedCrudDaoImpl<SoinDistrictSupplierEntity> implements SoinDistrictSupplierDao {
    @Override
    public SoinDistrictSupplierEntity findDistrictPrimarySupplier(String districtId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinDistrictSupplierEntity.class)
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.eq("isDefault", TRUE))
                .add(Restrictions.eq("isDelete", FALSE))
                .setMaxResults(1);
        List<SoinDistrictSupplierEntity> soinDistrictSupplierEntities = criteria.list();
        if (soinDistrictSupplierEntities.size() > 0) {
            return soinDistrictSupplierEntities.get(0);
        }
        return null;
    }

    @Override
    public List<SoinDistrictSupplierEntity> findBySupplierId(String supplierId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinDistrictSupplierEntity.class)
                .add(Restrictions.eq("supplierId", supplierId))
                .add(Restrictions.eq("isDelete", FALSE));
        return criteria.list();
    }

    @Override
    public boolean isSupplierUsing(String supplierId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinDistrictSupplierEntity.class)
                .add(Restrictions.eq("supplierId", supplierId))
                .add(Restrictions.eq("isDelete", FALSE))
                .setMaxResults(1);
        if (criteria.list().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<SoinDistrictSupplierEntity> findDistrictAllSuppliers(String districtId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinDistrictSupplierEntity.class)
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.eq("isDelete", FALSE))
                .addOrder(Order.desc("isDefault"))
                .addOrder(Order.asc("createTime"));
        return criteria.list();
    }

    @Override
    public SoinDistrictSupplierEntity isDistrctHasSupplier(String districtId, String supplierId) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(SoinDistrictSupplierEntity.class)
                .add(Restrictions.eq("supplierId", supplierId))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.eq("isDelete", FALSE))
                .setMaxResults(1);
        List<SoinDistrictSupplierEntity> soinDistrictSupplierEntities = criteria.list();
        if (soinDistrictSupplierEntities.size() > 0) {
            return soinDistrictSupplierEntities.get(0);
        }
        return null;
    }
}
