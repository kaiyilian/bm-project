package com.bumu.arya.admin.soin.service;

/**
 * @author CuiMengxin
 * @date 2016/1/28
 */
public interface SoinPersonPushService {

	/**
	 * 推送参保人审核状态变更
	 * @param personName
	 * @param userId
	 * @return
	 */
	String pushPersonStatusChange(String bizId, String personName,String status, String userId);
}
