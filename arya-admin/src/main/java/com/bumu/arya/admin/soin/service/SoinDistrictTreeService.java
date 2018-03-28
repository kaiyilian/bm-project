package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.result.DistrictTree;
import com.bumu.arya.admin.soin.result.DistrictTreeV2Result;
import com.bumu.arya.soin.model.entity.AryaSoinDistrictEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CuiMengxin on 2015/11/4.
 */
@Transactional

public interface SoinDistrictTreeService {
	/**
	 * 构造地区树
	 *
	 * @param districtEntities
	 * @return
	 */
	DistrictTree buildDistrictTree(List<AryaSoinDistrictEntity> districtEntities);

	/**
	 * 构造地区树
	 *
	 * @param districtEntities
	 * @return
	 */
	DistrictTreeV2Result buildDistrictTreeV2(List<AryaSoinDistrictEntity> districtEntities);
}
