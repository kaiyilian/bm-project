package com.bumu.arya.admin.soin.model.dao;

import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.KeyedCrudDao;

import java.util.Collection;
import java.util.List;

/**
 * 社保供应商
 */
@Transactional
public interface SoinSupplierDao extends KeyedCrudDao<SoinSupplierEntity> {

	/**
	 * 根据 ID 查找社保供应商
	 */
	SoinSupplierEntity findSoinSupplier(String id);

	/**
	 * 批量查询供应商
	 *
	 * @param ids
	 * @return
	 */
	List<SoinSupplierEntity> findSoinSuppliers(List<String> ids);

	/**
	 * 查找所有社保供应商
	 */
	List<SoinSupplierEntity> findAllSoinSupplier();

	/**
	 * 批量查询供应商
	 *
	 * @param ids
	 * @return
	 */
	List<SoinSupplierEntity> findSuppliersByIds(List<String> ids);

	/**
	 * 分页查询供应商 除了指定的供应商
	 *
	 * @param ids
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<SoinSupplierEntity> findPaginationSuppliersWithoutIds(List<String> ids, int page, int pageSize);

	/**
	 * 统计出了一部分其他所有供应商的数量
	 *
	 * @param ids
	 * @return
	 */
	int coutSuppliersWithoutIds(List<String> ids);

	/**
	 * 分页查询供应商
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<SoinSupplierEntity> findSoinSupplierPagination(int page, int pageSize);

	/**
	 * 通过名称找供应商
	 *
	 * @param name
	 * @return
	 */
	SoinSupplierEntity findSoinSupplierByName(String name);

	/**
	 * 统计总供应商数量
	 *
	 * @return
	 */
	int countAllSuppliers();

	/**
	 * 批量查询供应商
	 *
	 * @param ids
	 * @return
	 */
	List<SoinSupplierEntity> findSoinSuppliers(Collection<String> ids);
}