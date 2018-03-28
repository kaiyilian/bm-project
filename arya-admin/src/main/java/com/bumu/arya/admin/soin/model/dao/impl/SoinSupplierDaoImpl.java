package com.bumu.arya.admin.soin.model.dao.impl;

import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.soin.model.dao.SoinSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 社保供应商
 */
@Repository
public class SoinSupplierDaoImpl extends HibernateKeyedCrudDaoImpl<SoinSupplierEntity> implements SoinSupplierDao {
	/**
	 * 根据 ID 查找社保供应商
	 */
	public SoinSupplierEntity findSoinSupplier(String id) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.setMaxResults(1);
		List<SoinSupplierEntity> soinSupplierEntities = criteria.list();
		if (soinSupplierEntities.size() > 0) {
			return soinSupplierEntities.get(0);
		}
		return null;
	}

	@Override
	public List<SoinSupplierEntity> findSoinSuppliers(List<String> ids) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.in("id", ids))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE));
		return criteria.list();
	}

	/**
	 * 查找所有社保供应商
	 */
	public List<SoinSupplierEntity> findAllSoinSupplier() {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.addOrder(Order.asc("supplierName"));
		return criteria.list();
	}

	@Override
	public List<SoinSupplierEntity> findSuppliersByIds(List<String> ids) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.in("id", ids))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE));
		return criteria.list();
	}

	@Override
	public List<SoinSupplierEntity> findPaginationSuppliersWithoutIds(List<String> ids, int page, int pageSize) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.not(Restrictions.in("id", ids)))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.addOrder(Order.asc("supplierName"))
				.setFirstResult(page * pageSize)
				.setMaxResults(pageSize);
		return criteria.list();
	}

	@Override
	public int coutSuppliersWithoutIds(List<String> ids) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.not(Restrictions.in("id", ids)))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.setProjection(Projections.rowCount());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}

	@Override
	public List<SoinSupplierEntity> findSoinSupplierPagination(int page, int pageSize) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.addOrder(Order.asc("supplierName"))
				.setFirstResult(page * pageSize)
				.setMaxResults(pageSize);
		return criteria.list();
	}

	@Override
	public SoinSupplierEntity findSoinSupplierByName(String name) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.eq("supplierName", name))
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.setMaxResults(1);
		List<SoinSupplierEntity> soinSupplierEntities = criteria.list();
		if (soinSupplierEntities.size() > 0) {
			return soinSupplierEntities.get(0);
		}
		return null;
	}

	@Override
	public int countAllSuppliers() {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.eq("isDelete", CorpConstants.FALSE))
				.setMaxResults(1)
				.setProjection(Projections.rowCount());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}

	@Override
	public List<SoinSupplierEntity> findSoinSuppliers(Collection<String> ids) {
		if (ids.isEmpty()) {
			return new ArrayList<>();
		}
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SoinSupplierEntity.class)
				.add(Restrictions.in("id", ids));
		return criteria.list();
	}
}