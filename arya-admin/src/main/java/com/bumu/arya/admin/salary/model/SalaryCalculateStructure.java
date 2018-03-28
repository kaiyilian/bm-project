package com.bumu.arya.admin.salary.model;

import com.bumu.arya.admin.salary.result.SalaryCalculateListResult;
import com.bumu.arya.model.entity.*;

import java.util.*;

/**
 * @author CuiMengxin
 * @date 2016/4/5
 */
public class SalaryCalculateStructure {

	public String groupId;//集团公司id

	public SalaryCalculateRuleModel ruleModel;

	public Map<String, String> existUserId2existCorpIdMap;//key是用户id,vlaue是公司id
	//地区实体Map，key是地区名称
	public Map<String, DistrictEntity> districtEntitiesMap;
	//已存在的通过身份证号码查询到的用户实体Map，key是身份证号码
	public Map<String, AryaUserEntity> existUserEntitiesFindByIdcardMap;
	//已存在的通过手机号码查询到的用户实体Map，key是手机号码
	public Map<String, AryaUserEntity> existUserEntitiesFindByPhoneMap;
	//需要更新的用户实体Map，key是身份证号码
	public Map<String, AryaUserEntity> updateUserEntityMap;
	//已存在公司实体Map，key是公司名称q
	public Map<String, CorporationEntity> existCorporationEntitiesMap;
	//公司实体Map，key是公司名称
	public Map<String, String> corporationName2IdMap;
	//已存在月薪和周薪薪资Map,key是用户id，value是薪资实体
	public Map<String, AryaSalaryEntity> existMonthSalaryEntitiesMap;
	public Map<String, AryaSalaryWeekEntity> existWeekSalaryEntitiesMap;
	//已存在月薪薪资
	public List<AryaSalaryEntity> existMonthSalaryEntities;
	//已存在周薪薪资
	public List<AryaSalaryWeekEntity> existWeekSalaryEntities;
	//已存在本月其他周周薪薪资
	public List<AryaSalaryWeekEntity> existOtherWeekSalaryEntities;
	//已存在本月其他周周薪薪资map,key是用户id，value是UserWeekSalariesInOneMonth
	public Map<String, UserWeekSalariesInOneMonth> existOtherWeekSalaryEntitiesInOneMonthMap;
	//收集用户身份证号码
	public Collection<String> userIdcardNos;
	//收集用户重复的身份证号码
	public Collection<String> userDuplicateIdcardNos;
	//收集用户手机号码
	public Collection<String> userPhonesNos;
	//收集用户重复的手机号码
	public Collection<String> userDuplicatePhonesNos;
	//收集地区名称
	public Collection<String> districtNames;
	//收集公司名称
	public Collection<String> corpNames;
	//收集已存在的用户id
	public Collection<String> existUserIds;
	//收集新公司名称
	public Collection<String> newCorpNames;
	//收集新用户身份证号码
	public Collection<String> newUserIdcards;
	//新用户
	public List<AryaUserEntity> newUserEntities;
	//新公司
	public List<CorporationEntity> newCorpEntities;
	//新增月薪
	public List<AryaSalaryEntity> newMonthSalaryEntities;
	//新增周薪
	public List<AryaSalaryWeekEntity> newWeekSalaryEntities;
	//需更新的月薪
	public List<AryaSalaryEntity> updateMonthSalaryEntities;
	//需更新的周薪
	public List<AryaSalaryWeekEntity> updateWeekSalaryEntities;
	//薪资模型
	public List<SalaryModel> salaryModels;
	//忽略的薪资模型
	public List<SalaryModel> ignoredSalaryModels;
	//有问题的薪资模型
	public List<SalaryModel> wrongSalaryModels;
	//计算结果
	public ArrayList<SalaryCalculateListResult.SalaryCalculateResult> listResult;

	public List<String> log;

	public SalaryCalculateStructure() {
		existUserId2existCorpIdMap = new HashMap<>();
		districtEntitiesMap = new HashMap<>();
		existUserEntitiesFindByIdcardMap = new HashMap<>();
		existUserEntitiesFindByPhoneMap = new HashMap<>();
		existCorporationEntitiesMap = new HashMap<>();
		existMonthSalaryEntitiesMap = new HashMap<>();
		existWeekSalaryEntitiesMap = new HashMap<>();
		corporationName2IdMap = new HashMap<>();
		userIdcardNos = new ArrayList<>();
		userDuplicateIdcardNos = new ArrayList<>();
		userPhonesNos = new ArrayList<>();
		userDuplicatePhonesNos = new ArrayList<>();
		districtNames = new ArrayList<>();
		corpNames = new ArrayList<>();
		existUserIds = new ArrayList<>();
		ignoredSalaryModels = new ArrayList<>();
		wrongSalaryModels = new ArrayList<>();
		newCorpNames = new ArrayList<>();
		newUserIdcards = new ArrayList<>();
		log = new ArrayList<>();
	}

	/**
	 * 获取需要更新的用户实体集
	 *
	 * @return
	 */
	public List<AryaUserEntity> getUpdateUserEntities() {
		if (updateUserEntityMap == null) {
			return null;
		}
		List<AryaUserEntity> updateUsers = new ArrayList<>();
		for (AryaUserEntity userEntity : updateUserEntityMap.values()) {
			updateUsers.add(userEntity);
		}
		return updateUsers;
	}

	public SalaryCalculateRuleModel getRuleModel() {
		return ruleModel;
	}

	public void setRuleModel(SalaryCalculateRuleModel ruleModel) {
		this.ruleModel = ruleModel;
	}

	public List<SalaryModel> getWrongSalaryModels() {
		return wrongSalaryModels;
	}

	public void setWrongSalaryModels(List<SalaryModel> wrongSalaryModels) {
		this.wrongSalaryModels = wrongSalaryModels;
	}

	public Map<String, DistrictEntity> getDistrictEntitiesMap() {
		return districtEntitiesMap;
	}

	public List<SalaryModel> getIgnoredSalaryModels() {
		return ignoredSalaryModels;
	}

	public void setIgnoredSalaryModels(List<SalaryModel> ignoredSalaryModels) {
		this.ignoredSalaryModels = ignoredSalaryModels;
	}

	public void setDistrictEntitiesMap(Map<String, DistrictEntity> districtEntitiesMap) {
		this.districtEntitiesMap = districtEntitiesMap;
	}

	public Map<String, AryaUserEntity> getExistUserEntitiesFindByIdcardMap() {
		return existUserEntitiesFindByIdcardMap;
	}

	public void setExistUserEntitiesFindByIdcardMap(Map<String, AryaUserEntity> existUserEntitiesFindByIdcardMap) {
		this.existUserEntitiesFindByIdcardMap = existUserEntitiesFindByIdcardMap;
	}

	public Map<String, CorporationEntity> getExistCorporationEntitiesMap() {
		return existCorporationEntitiesMap;
	}

	public void setExistCorporationEntitiesMap(Map<String, CorporationEntity> existCorporationEntitiesMap) {
		this.existCorporationEntitiesMap = existCorporationEntitiesMap;
	}

	public List<AryaSalaryEntity> getExistMonthSalaryEntities() {
		return existMonthSalaryEntities;
	}

	public void setExistMonthSalaryEntities(List<AryaSalaryEntity> existMonthSalaryEntities) {
		this.existMonthSalaryEntities = existMonthSalaryEntities;
	}

	public Collection<String> getUserIdcardNos() {
		return userIdcardNos;
	}

	public void setUserIdcardNos(Collection<String> userIdcardNos) {
		this.userIdcardNos = userIdcardNos;
	}

	public Collection<String> getDistrictNames() {
		return districtNames;
	}

	public void setDistrictNames(Collection<String> districtNames) {
		this.districtNames = districtNames;
	}

	public Collection<String> getCorpNames() {
		return corpNames;
	}

	public void setCorpNames(Collection<String> corpNames) {
		this.corpNames = corpNames;
	}

	public List<AryaUserEntity> getNewUserEntities() {
		return newUserEntities;
	}

	public void setNewUserEntities(List<AryaUserEntity> newUserEntities) {
		this.newUserEntities = newUserEntities;
	}

	public List<CorporationEntity> getNewCorpEntities() {
		return newCorpEntities;
	}

	public void setNewCorpEntities(List<CorporationEntity> newCorpEntities) {
		this.newCorpEntities = newCorpEntities;
	}

	public List<AryaSalaryEntity> getNewMonthSalaryEntities() {
		return newMonthSalaryEntities;
	}

	public void setNewMonthSalaryEntities(List<AryaSalaryEntity> newMonthSalaryEntities) {
		this.newMonthSalaryEntities = newMonthSalaryEntities;
	}

	public ArrayList<SalaryCalculateListResult.SalaryCalculateResult> getListResult() {
		return listResult;
	}

	public void setListResult(ArrayList<SalaryCalculateListResult.SalaryCalculateResult> listResult) {
		this.listResult = listResult;
	}

}
