package com.bumu.arya.admin.soin.service.impl;

import com.bumu.common.service.PushService;
import com.bumu.arya.admin.soin.service.SoinPersonPushService;
import com.bumu.common.model.PushMessage;
import com.bumu.common.model.PushTo;
import com.bumu.arya.common.Constants;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CuiMengxin
 * @date 2016/1/28
 */
@Service
public class SoinPersonPushServiceImpl implements SoinPersonPushService {
	Logger log = LoggerFactory.getLogger(SoinPersonPushServiceImpl.class);
	@Autowired
	AryaUserDao userDao;

	@Autowired
	PushService pushService;

	@Override
	public String pushPersonStatusChange(String bizId, String personName, String status, String userId) {
		return pushPersonStatusChangeMsg(bizId, "参保人审核", "参保人" + personName + status, PushMessage.ICON_SOIN_PERSON, PushMessage.TYPE_SOIN_PERSON, userId);
	}

	private String pushPersonStatusChangeMsg(String bzid, String title, String content, int icon, int type, String userId) {
		AryaUserEntity userEntity = userDao.findUserByIdThrow(userId);
		PushMessage pushMessage = new PushMessage();
		pushMessage.setTitle(title);
		pushMessage.setContent(content);
		pushMessage.setIcon(icon);
		pushMessage.setType(type);
		pushMessage.setUserId(userId);
		PushTo.PushToOne pushTo = new PushTo.PushToOne(bzid, userId, Constants.ClientType.getClientType(userEntity.getLastClientType()));
		String pushResult = pushService.push(pushMessage, pushTo);
		log.info(pushResult);
		return pushResult;
	}
}
