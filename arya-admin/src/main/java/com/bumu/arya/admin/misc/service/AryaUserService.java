package com.bumu.arya.admin.misc.service;

import com.bumu.arya.admin.misc.controller.command.AryaUserListCommand;
import com.bumu.arya.admin.misc.result.AryaUserInfoListResult;
import com.bumu.arya.admin.misc.result.AryaUserInfoResult;
import com.bumu.arya.admin.misc.result.UserEmpInfo;
import com.bumu.arya.admin.misc.result.UserInfoResult;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.user.service.BaseAryaUserService;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.payroll.result.UserPayrollResult;
import com.bumu.exception.AryaServiceException;

import java.util.List;
import java.util.Set;

/**
 * Created by CuiMengxin on 2016/10/17.
 */
public interface AryaUserService extends BaseAryaUserService {

	/**
	 * 获取用户详细信息
	 *
	 * @param keyword
	 * @return
	 * @throws AryaServiceException
	 */
	AryaUserInfoResult getUserInfo(String keyword) throws AryaServiceException;

	/**
	 * 获取用户列表
	 *
	 * @param command
	 * @return
	 * @throws AryaServiceException
	 */
	AryaUserInfoListResult getUserList(AryaUserListCommand command) throws AryaServiceException;

	/**
	 * 通过aryaCorpId查询用户(包括aryaCorp关联的branCorp用户)
	 *
	 * @param aryaCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	List<AryaUserEntity> getUsersByAryaCorpId(String aryaCorpId) throws AryaServiceException;

	/**
	 * 通过branCorpId查询用户
	 *
	 * @param branCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	List<AryaUserEntity> getUsersByBranCorpId(String branCorpId) throws AryaServiceException;


	/**
	 * 通过aryaCorpIds查询用户(包括aryaCorp关联的branCorp用户)
	 *
	 * @param aryaCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	Set<AryaUserEntity> getUsersByAryaCorpIds(List<String> aryaCorpId) throws AryaServiceException;


	/**
	 * 判断用户是否有某些标签
	 *
	 * @param tags
	 * @param userTag
	 * @return
	 */
	boolean filterUserTag(List<Integer> tags, int userTag);

	UserInfoResult getUserAppInfo(String tel);

	List<UserPayrollResult> getUserPayrollInfo(String tel);

	UserEmpInfo getUserEmpInfo(String tel);

	List<EmployeeEntity> getEmployeeUserById(String corpId, String id);
}
