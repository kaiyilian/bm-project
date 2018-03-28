package com.bumu.arya.admin.soin.service.impl;

import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.soin.service.SoinPersonService;
import com.bumu.arya.soin.constant.SoinPersonHukouType;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.soin.model.dao.InsurancePersonDao;
import com.bumu.arya.soin.model.entity.AryaSoinPersonEntity;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.response.SoinPersonList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2015/12/25
 */
@Service
public class SoinPersonServiceImpl implements SoinPersonService {

	@Autowired
	InsurancePersonDao insurancePersonDao;

	@Autowired
	DistrictDao districtDao;

	@Autowired
	AryaAdminConfigService configService;

	@Override
	public JQDatatablesPaginationResult getSoinPersonList(int draw, int page, int pageSize, String personStatusCodesStr) {
		//因为参保人审核状态是1,2,3,4，所以不能做位与查询，前端只能直接将code传递过来
		JQDatatablesPaginationResult result = null;
		try {
			result = new JQDatatablesPaginationResult();
			SoinPersonList personList = new SoinPersonList();
			List<AryaSoinPersonEntity> personEntities = null;
			long totalCount = insurancePersonDao.findOnlineSoinPersonsCount();//参保人总人数
			long filterCount = 0;//过滤后的人数
			if (StringUtils.isAnyBlank(personStatusCodesStr)) {
				personEntities = insurancePersonDao.findOnlineSoinPersonsByPagination(page, pageSize);//非过滤查询
				filterCount = totalCount;
			} else {
				//按参保人状态过滤查询
				String[] statusCodeStrs = personStatusCodesStr.split(":");
				ArrayList<Integer> statusCodes = new ArrayList<>();
				for (String statusCodeStr : statusCodeStrs) {
					statusCodes.add(Integer.parseInt(statusCodeStr));
				}
				personEntities = insurancePersonDao.findOnlineSoinPersonsPagInStatus(page, pageSize, statusCodes);
				filterCount = insurancePersonDao.findOnlineSoinInStatusCount(statusCodes);
			}

			//如果没查询到接口直接返回空结果集
			if (personEntities == null || personEntities.size() == 0) {
				result.setDraw(draw);
				result.setRecordsTotal((int) totalCount);
				result.setRecordsFiltered(0);
				result.setData(personList);
				return result;
			}

			Collection<String> districtIds = new ArrayList<>();
			//收集地区ID
			for (AryaSoinPersonEntity person : personEntities) {
				if (StringUtils.isNotBlank(person.getHukou())) {
					String[] districtIdStrs = person.getHukou().split(":");
					for (String districtId : districtIdStrs) {
						if (!districtIds.contains(districtId)) {
							districtIds.add(districtId);
						}
					}
				}
			}

			//查出参保人所有涉及到的地区
			List<DistrictEntity> districtEntities = null;
			if (districtIds != null && !districtIds.isEmpty()) {
				districtEntities = districtDao.findDistrictList(districtIds);
			}

			int i = 0;
			while (i < personEntities.size()) {
				AryaSoinPersonEntity entity = personEntities.get(i);
				String hukouCHN = "N/A";
				if (districtEntities != null && !districtEntities.isEmpty() && StringUtils.isNotBlank(entity.getHukou())) {
					String[] hukouIdStrs = entity.getHukou().split(":");
					String[] hukouChinese = new String[hukouIdStrs.length];
					//查出每个参保人的地名
					for (int j = 0; j < hukouChinese.length; j++) {
						for (DistrictEntity districtEntity : districtEntities) {
							if (hukouIdStrs[j].equals(districtEntity.getId())) {
								hukouChinese[j] = districtEntity.getDistrictName();
								break;
							}
						}
					}

					//合并字符串
					hukouCHN = StringUtils.join(hukouChinese, "-");
				}

				SoinPersonList.SocialInsurancePerson person = new SoinPersonList.SocialInsurancePerson();
				person.setInsuredPersionId(entity.getId());
				person.setName(entity.getInsurancePersonName());
				person.setPhoneNo(entity.getPhoneNo());
				person.setIdcardNo(entity.getIdcardNo());
				person.setHukou(hukouCHN);
				person.setHukouTypeName(SoinPersonHukouType.getHuKouTypeName(entity.getHukouType()));
				person.setVerifyStatus(entity.getVerifyStatus());
				person.setOrderCount(entity.getOrderCount());
				person.setCreateTime(entity.getCreateTime());
				personList.add(person);
				i++;
			}

			result.setDraw(draw);
			result.setRecordsTotal((int) totalCount);
			result.setRecordsFiltered((int) filterCount);
			result.setData(personList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
