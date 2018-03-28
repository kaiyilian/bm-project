package com.bumu.arya.admin.salary.service;

/**
 * @author CuiMengxin
 * @date 2016/4/13
 */
public interface PhoneService {

	/**
	 * 获取系统手机号码
	 *
	 * @return
	 */
	String getNewSystemPhoneNumber();

	/**
	 * 手机号码是否被他人占用
	 * @param phoneNo
	 * @return
	 */
	Boolean isPhoneUsedByOtherUser(String phoneNo, String idcardNo);
}
