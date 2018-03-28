package com.bumu.arya.admin.soin.model.dao;

import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author majun
 * @date 2017/3/3
 */
@MapperScan
public interface SoinOrderMybatisTTDao {

	AryaSoinOrderEntity findById(@Param("id") String id);
}
