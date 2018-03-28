package com.bumu.arya.admin.misc.service;

import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateNotificationCommand;
import com.bumu.arya.admin.misc.result.*;
import com.bumu.arya.common.service.NotificationService;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by CuiMengxin on 2016/9/29.
 */
@Transactional
public interface AdminNotificationService extends NotificationService{

	/**
	 * 获取历史推送分页列表
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws AryaServiceException
	 */
	NotificationListResult getNotificationList(int page, int pageSize) throws AryaServiceException;

	/**
	 * 获取推送详情
	 *
	 * @param id
	 * @return
	 * @throws AryaServiceException
	 */
	NotificationDetailResult getNotificationDetail(String id) throws AryaServiceException;

	/**
	 * 删除推送
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void delete(IdsCommand command) throws AryaServiceException;

	/**
	 * 获取所有跳转类型
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	NotificationJumpTypeList getAllJumpTypeList() throws AryaServiceException;

	/**
	 * 新增
	 *
	 * @param command
	 * @return
	 * @throws AryaServiceException
	 */
	String create(CreateOrUpdateNotificationCommand command) throws AryaServiceException, Exception;

	/**
	 * 修改
	 *
	 * @param command
	 * @return
	 * @throws AryaServiceException
	 */
	String update(CreateOrUpdateNotificationCommand command) throws AryaServiceException;

//	/**
//	 * 立即推送
//	 *
//	 * @param title
//	 * @param content
//	 * @param jumpType
//	 * @return
//	 */
//	String notifyNow(String title, String content, int jumpType, String jumpUrl, NotificationUsersModel usersModel);

//	/**
//	 * 新增广播定时任务
//	 *
//	 * @param notificationEntity
//	 */
//	void addNewBroadcastSchedule(NotificationEntity notificationEntity);

	/**
	 * 获取所有标签
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	GetAllTagsResult getAllTags() throws AryaServiceException;

	/**
	 * 获取所有公司标签
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	TagCategoryResult getAllCorpTags() throws AryaServiceException;

}
