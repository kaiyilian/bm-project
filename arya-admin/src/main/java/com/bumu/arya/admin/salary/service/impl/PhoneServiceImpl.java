package com.bumu.arya.admin.salary.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.salary.service.PhoneService;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.common.util.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author CuiMengxin
 * @date 2016/4/13
 */
@Service
public class PhoneServiceImpl implements PhoneService {

	static final String SYSTEM_PHONE_NUMBER = "systemPhoneNumber";

	static final String SYSTEM_PHPNE_NUMBER_FIRST = "20000000000";

	@Autowired
	AryaAdminConfigService configService;

	private Jedis jedis;

	@Autowired

	AryaUserDao userDao;

	@PostConstruct
	public void init() {
		jedis = new Jedis(configService.getRedisHost(), configService.getRedisPort() == 0 ? 6379 : configService.getRedisPort());
	}

	@Override
	public String getNewSystemPhoneNumber() {
		byte[] bytes = jedis.get(SYSTEM_PHONE_NUMBER.getBytes());
		if (bytes == null) {
			BigDecimal initSystemPhoneNumber = new BigDecimal(SYSTEM_PHPNE_NUMBER_FIRST);
			jedis.set(SYSTEM_PHONE_NUMBER.getBytes(), initSystemPhoneNumber.toString().getBytes());
			bytes = SYSTEM_PHPNE_NUMBER_FIRST.getBytes();
		}
		BigDecimal newSystemPhoneNumber = new BigDecimal(new String(bytes)).add(new BigDecimal("1"));
		jedis.set(SYSTEM_PHONE_NUMBER.getBytes(), newSystemPhoneNumber.toString().getBytes());
		return newSystemPhoneNumber.toString();
	}

	@Override
	public Boolean isPhoneUsedByOtherUser(String phoneNo, String idcardNo) {
		if (StringUtils.isAnyBlank(phoneNo)) {
			return true;
		}
		AryaUserEntity userEntity = userDao.findUserByPhoneNo(phoneNo);
		return userEntity != null && !ValidateUtils.isIdCardEquals(userEntity.getIdcardNo(), idcardNo);
	}
}
