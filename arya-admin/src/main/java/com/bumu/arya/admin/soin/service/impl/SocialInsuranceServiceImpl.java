package com.bumu.arya.admin.soin.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.soin.result.SoinImportResult.SoinErrorMsgBean;
import com.bumu.arya.admin.soin.result.SoinImportResult.SoinOutputBean;
import com.bumu.arya.admin.soin.service.SocialInsuranceService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.soin.model.dao.AryaSoinDao;
import com.bumu.arya.soin.model.dao.AryaSoinTypeDao;
import com.bumu.arya.soin.model.entity.AryaSoinEntity;
import com.bumu.arya.soin.model.entity.SoinTypeEntity;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.StringUtil;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.bumu.SysUtils.turnStringToBigDecimal;

/**
 * Created by bumu-zhz on 2015/11/12.
 */
@Service
public class SocialInsuranceServiceImpl extends BaseBumuService implements SocialInsuranceService {


	/**
	 * 依赖注入用户Dao
	 */
	@Autowired
	private AryaUserDao aryaUserDao;

	/**
	 * 依赖注入公司Dao
	 */
	@Autowired
	private CorporationDao corporationDao;

	/**
	 * 依赖注入社保Dao
	 */
	@Autowired
	private AryaSoinDao aryaSoinDao;

	/**
	 * 依赖注入社保类型Dao
	 */
	@Autowired
	private AryaSoinTypeDao aryaSoinTypeDao;

	/**
	 * 依赖注入地区Dao
	 */
	@Autowired
	private DistrictDao districtDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SoinErrorMsgBean> insert(String companyId, String cityId, String soinTypeId, List<SoinOutputBean> list) throws AryaServiceException {
		List<SoinErrorMsgBean> errorMsgBeans = new ArrayList<>();
		List<String> districtIdList = new ArrayList<>();
		String districtId;//存放地区串
		CorporationEntity corporationEntity = corporationDao.findCorporationById(companyId);
		DistrictEntity districtEntity = districtDao.find(cityId);
		SoinTypeEntity soinTypeEntity = aryaSoinTypeDao.findNotDeleteSoinTypeById(soinTypeId);
		//公司城市社保类型check
		checkId(corporationEntity, districtEntity, soinTypeEntity, errorMsgBeans);

		//拼接地区id
		while (!(Constants.CHN_ID.equals(districtEntity.getId()))) {//将父地区都找出来
			districtIdList.add(districtEntity.getId());
			districtEntity = districtDao.find(districtEntity.getParentId());
		}


		Collections.reverse(districtIdList); // 反转
		districtId = StringUtils.join(districtIdList, ":"); // 拼接

		if (errorMsgBeans.size() == 0) {
			if (list != null && list.size() > 0) {
				checkInsert(list, errorMsgBeans);//导入数据check
				if (errorMsgBeans.size() == 0) {
					//更新公司信息  是否不能删除，0为否，1为是
					if (corporationEntity.getMandatory() != 1) {
						corporationEntity.setMandatory(1);
						corporationDao.createOrUpdate(corporationEntity);
					}

					List<AryaSoinEntity> soinEntities = new ArrayList<>();
					for (SoinOutputBean outputBean : list) {
						//获取记录所属用户，如果不存在就创建新的用户
						AryaUserEntity userEntity = salaryOwnUser(outputBean, corporationEntity);

						Date date = DateTimeUtils.checkMonth(outputBean.getMonth());
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						int year = calendar.get(Calendar.YEAR);//年份
						int month = calendar.get(Calendar.MONTH) + 1;//月份

						AryaSoinEntity soinEntity = new AryaSoinEntity();
						// 生成薪资id
						String newPlayerId = Utils.makeUUID();
						soinEntity.setId(newPlayerId);
						soinEntity.setUserId(userEntity.getId());//用户ID
						soinEntity.setIdcardNo(outputBean.getCardId());//身份证号码
						soinEntity.setPhoneNo(outputBean.getPhone());//手机号码
						soinEntity.setAreaId(districtId);//城市区域
						soinEntity.setCompanyId(corporationEntity.getId());//公司
						soinEntity.setSoinTypeId(soinTypeEntity.getId());//社保类型
						soinEntity.setYear(year);//年份
						soinEntity.setMonth(month);//月份
						soinEntity.setSoinBase(turnStringToBigDecimal(outputBean.getSoinBase()));//社保缴纳基数
						soinEntity.setHouseFundBase(turnStringToBigDecimal(outputBean.getHouseFundBase()));//公积金缴纳基数
						soinEntity.setTotalPayment(turnStringToBigDecimal(outputBean.getTotalPayment()));//总金额
						soinEntity.setPersonalPregnancy(turnStringToBigDecimal(outputBean.getPersonalPregnancy()));//个人-生育
						soinEntity.setPersonalPension(turnStringToBigDecimal(outputBean.getPersonalPension()));//个人-养老
						soinEntity.setPersonalMedical(turnStringToBigDecimal(outputBean.getPersonalMedical()));//个人-医疗
						soinEntity.setPersonalUnemployment(turnStringToBigDecimal(outputBean.getPersonalUnemployment()));//个人-失业
						soinEntity.setPersonalInjury(turnStringToBigDecimal(outputBean.getPersonalInjury()));//个人-工伤
						soinEntity.setPersonalHouseFund(turnStringToBigDecimal(outputBean.getPersonalHouseFund()));//个人-住房
						soinEntity.setPersonalTotal(addString(outputBean.getPersonalPregnancy(), outputBean.getPersonalPension(), outputBean.getPersonalMedical(),
								outputBean.getPersonalUnemployment(), outputBean.getPersonalInjury(), outputBean.getPersonalHouseFund()));//个人小计
						soinEntity.setCompanyPregnancy(turnStringToBigDecimal(outputBean.getCompanyPregnancy()));//企业-生育
						soinEntity.setCompanyPension(turnStringToBigDecimal(outputBean.getCompanyPension()));//企业-养老
						soinEntity.setCompanyMedical(turnStringToBigDecimal(outputBean.getCompanyMedical()));//企业-医疗
						soinEntity.setCompanyUnemployment(turnStringToBigDecimal(outputBean.getCompanyUnemployment()));//企业-失业
						soinEntity.setCompanyInjury(turnStringToBigDecimal(outputBean.getCompanyInjury()));//企业-工伤
						soinEntity.setCompanyHouseFund(turnStringToBigDecimal(outputBean.getCompanyHouseFund()));//企业-住房
						soinEntity.setCompanyTotal(addString(outputBean.getCompanyPregnancy(), outputBean.getCompanyPension(), outputBean.getCompanyMedical(),
								outputBean.getCompanyUnemployment(), outputBean.getCompanyInjury(), outputBean.getCompanyHouseFund()));//企业小计
						soinEntity.setCompanyDisability(turnStringToBigDecimal(outputBean.getDisability()));//残保
						//其他缴纳部分
						soinEntity.setIsPayed(1);//已缴纳
						soinEntity.setFees(turnStringToBigDecimal(outputBean.getFees()));//管理费
						soinEntities.add(soinEntity);
					}
					aryaSoinDao.create(soinEntities);
				}
			}
		}
		return errorMsgBeans;
	}

	/**
	 * excel社保数据业务check
	 *
	 * @param soinOutputBeans
	 * @param errorMsgs
	 */
	private void checkInsert(List<SoinOutputBean> soinOutputBeans, List<SoinErrorMsgBean> errorMsgs) {
		if (soinOutputBeans != null && soinOutputBeans.size() > 0 && errorMsgs != null) {
			for (SoinOutputBean outputBean : soinOutputBeans) {
//				List<AryaUserEntity> userEntities = aryaUserDao.selectByCard(outputBean.getCardId(), outputBean.getRealName());
				List<AryaUserEntity> userEntities = aryaUserDao.selectByPhone(outputBean.getPhone());
				if (userEntities != null && userEntities.size() > 0) {
					AryaUserEntity userEntity = userEntities.get(0);
					Date date = DateTimeUtils.checkMonth(outputBean.getMonth());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);//年份
					int month = calendar.get(Calendar.MONTH) + 1;//月份

					List<AryaSoinEntity> soinEntityList = aryaSoinDao.findSoinsByUserMonth(userEntity.getId(), year, month);
					if (soinEntityList != null && soinEntityList.size() > 0) {
						SoinErrorMsgBean bean = new SoinErrorMsgBean();
						bean.setMsg(userEntity.getRealName() + "的社保信息已经导入过系统");
						errorMsgs.add(bean);
					}
				}
			}
		}
	}

	/**
	 * 公司城市社保类型check
	 *
	 * @param corporationEntity
	 * @param districtEntity
	 * @param soinTypeEntity
	 * @param list
	 */
	private void checkId(CorporationEntity corporationEntity, DistrictEntity districtEntity, SoinTypeEntity soinTypeEntity, List<SoinErrorMsgBean> list) {
		if (corporationEntity == null) {
			SoinErrorMsgBean bean = new SoinErrorMsgBean();
			bean.setMsg("公司不存在");
			list.add(bean);
		}
		if (districtEntity == null) {
			SoinErrorMsgBean bean = new SoinErrorMsgBean();
			bean.setMsg("城市不存在");
			list.add(bean);
		}
		if (soinTypeEntity == null) {
			SoinErrorMsgBean bean = new SoinErrorMsgBean();
			bean.setMsg("社保类型不存在");
			list.add(bean);
		}

		if (districtEntity != null && soinTypeEntity != null) {
			List<SoinTypeEntity> soinTypeEntities = aryaSoinTypeDao.findNotDeleteSoinTypeByDistrict(districtEntity.getId());
			boolean haveFlag = false;
			if (soinTypeEntities != null && soinTypeEntities.size() > 0) {
				for (SoinTypeEntity entity : soinTypeEntities) {
					if (soinTypeEntity.getId().equals(entity.getId())) {
						haveFlag = true;
						break;
					}
				}
			}
			if (!haveFlag) {
				SoinErrorMsgBean bean = new SoinErrorMsgBean();
				bean.setMsg("选择城市与社保类型不匹配");
				list.add(bean);
			}
		}
	}

	/**
	 * 获取记录所属用户，如果不存在就创建新的用户
	 *
	 * @param bean
	 * @return
	 */
	private AryaUserEntity salaryOwnUser(SoinOutputBean bean, CorporationEntity corporationEntity) {
//		List<AryaUserEntity> userEntities = aryaUserDao.selectByCard(bean.getCardId(), bean.getRealName());
		List<AryaUserEntity> userEntities = aryaUserDao.selectByPhone(bean.getPhone());
		AryaUserEntity userEntity = null;
		if (userEntities != null && userEntities.size() > 0) {
			userEntity = userEntities.get(0);
		}
		if (userEntity == null) {
			userEntity = new AryaUserEntity();
			// 先生成用户 GUID 给交易系统
			String newPlayerId = Utils.makeUUID();
			userEntity.setId(newPlayerId);//主键ID
			userEntity.setCorporationId(corporationEntity.getId());//公司ID
			userEntity.setNickName("招才进宝");//昵称，可选
			userEntity.setPwd(SysUtils.encryptPassword("123456"));//密码，MD5加密
			userEntity.setPhoneNo(bean.getPhone());//手机号码，唯一
			userEntity.setRealName(bean.getRealName());//真实姓名
			userEntity.setGender("1");//性别，1：男，2：女
			userEntity.setCreateTime(System.currentTimeMillis());//创建时间
			userEntity.setIdcardNo(bean.getCardId());//用户身份证号码，１５位或者１８位都可以
			userEntity.setCreateType("2");//创建类型，1表示自行注册，2表示批量导入
			aryaUserDao.create(userEntity);
		} else {
			if (StringUtils.isAnyBlank(userEntity.getRealName())) {
				userEntity.setRealName(bean.getRealName());
				aryaUserDao.update(userEntity);
			}
		}
		return userEntity;
	}

	private BigDecimal addString(String... strs) {
		BigDecimal bigDecimal = new BigDecimal(0);
		for (String str : strs) {
			if (!StringUtil.isEmpty(str)) {
				bigDecimal = bigDecimal.add(new BigDecimal(str));
			}
		}
		return bigDecimal.setScale(2, BigDecimal.ROUND_UP);
	}
}
