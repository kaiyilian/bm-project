package com.bumu.arya.admin.misc.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.bumu.arya.admin.corporation.result.OrganizationTreeResult;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateNotificationCommand;
import com.bumu.arya.admin.misc.result.*;
import com.bumu.arya.admin.misc.result.NotificationListResult.NotificationResult;
import com.bumu.arya.admin.misc.service.AdminNotificationService;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.common.constant.NotificationConstants;
import com.bumu.arya.common.model.FilterTag;
import com.bumu.arya.common.service.NotificationScheduleService;
import com.bumu.arya.common.service.impl.NotificationServiceImpl;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.NotificationDao;
import com.bumu.arya.model.TagCategoryDao;
import com.bumu.arya.model.entity.*;
import com.bumu.arya.response.SimpleIdNameResult;
import com.bumu.arya.schedule.NotificationTaskJob;
import com.bumu.common.model.PushFilter;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bumu.arya.common.constant.NotificationConstants.NOTIFICATION_TYPE_BROADCAST;
import static com.bumu.arya.common.constant.NotificationConstants.NOTIFICATION_TYPE_GROUPCAST;

/**
 * Created by CuiMengxin on 2016/9/29.
 */
@Service
public class AdminNotificationServiceImpl extends NotificationServiceImpl implements AdminNotificationService {

    @Autowired
    NotificationDao notificationDao;

    @Autowired
    OpLogService opLogService;

    @Autowired
    SysUserService sysUserService;
//
//	@Autowired
//	PushService pushService;

    @Autowired
    NotificationScheduleService scheduleService;

    @Autowired
    CorporationService corporationService;

    @Autowired
    TagCategoryDao tagCategoryDao;

    Logger log = LoggerFactory.getLogger(AdminNotificationService.class);


    @Override
    public NotificationListResult getNotificationList(int page, int pageSize) throws AryaServiceException {
        NotificationListResult ret = new NotificationListResult();
        Pager<NotificationEntity> pager = notificationDao.findPagination(page, pageSize);
        long count = notificationDao.countByParam("isDelete", 0);
        ret.setTotalRows((int) count);
        ret.setPages(Utils.calculatePages(pager.getRowCount(), pager.getPageSize()));
        List<NotificationResult> list = new ArrayList<>();
        ret.setNotifications(list);
        for (NotificationEntity entity : pager.getResult()) {
            NotificationResult result = new NotificationResult();
            result.setId(entity.getId());
            result.setTitle(entity.getTitle());
            result.setContent(entity.getContent());
            if (entity.getSetSendTime() == null) {
                result.setSendTime(entity.getSendTime());
                result.setCanEdit(CorpConstants.FALSE);
            } else {
                result.setSendTime(entity.getSetSendTime());
                if (entity.getSetSendTime() > System.currentTimeMillis()) {
                    result.setCanEdit(CorpConstants.TRUE);
                } else {
                    result.setCanEdit(CorpConstants.FALSE);
                }
            }
            result.setDisplayType(entity.getDisplayType());
            result.setStatus(entity.getSendStatus());
            result.setStatusStr(NotificationConstants.getNotificationSendStatusName(entity.getSendStatus()));
            list.add(result);
        }
        return ret;
    }

    @Override
    public NotificationDetailResult getNotificationDetail(String id) throws AryaServiceException {
        NotificationEntity entity = notificationDao.findThrow(id);
        NotificationDetailResult ret = new NotificationDetailResult();
        ret.setId(entity.getId());
        ret.setTitle(entity.getTitle());
        ret.setContent(entity.getContent());
        ret.setStatus(entity.getSendStatus());
        ret.setDisplayType(entity.getDisplayType());
        ret.setJumpType(entity.getJumpType());
        ret.setJumpUrl(entity.getJumpUrl());
        ret.setPushCount(0l);
        if (entity.getSetSendTime() == null) {
            ret.setIsTiming(CorpConstants.FALSE);
            ret.setCanEdit(CorpConstants.FALSE);
        } else {
            ret.setIsTiming(CorpConstants.TRUE);
            ret.setSetSendTime(entity.getSetSendTime());
            if (entity.getSetSendTime() > System.currentTimeMillis()) {
                ret.setCanEdit(CorpConstants.TRUE);
            } else {
                ret.setCanEdit(CorpConstants.FALSE);
            }
        }
        //找出标签
        ret.setFilterTags(getNotifiedFilterTags(entity));
        return ret;
    }

    @Override
    public void delete(IdsCommand command) throws AryaServiceException {
        List<String> ids = new ArrayList<>();
        for (IdsCommand.IdCommand idCommand : command.getIds()) {
            ids.add(idCommand.getId());
        }
        if (ids.size() == 0) {
            return;
        }
        List<NotificationEntity> notificationEntities = notificationDao.findList(ids);
        StringBuffer logMsg = new StringBuffer("【推送管理】删除推送,ids:" + StringUtils.join(ids, ","));
        for (NotificationEntity notificationEntity : notificationEntities) {
            notificationEntity.setIsDelete(CorpConstants.TRUE);
            if (notificationEntity.getSetSendTime() != null) {
                //删除
                scheduleService.deleteBroadCastNotification(notificationEntity.getId());
            }
        }

        try {
            notificationDao.update(notificationEntities);
            opLogService.successLog(OperateConstants.NOTIFICATION_DELETE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.NOTIFICATION_DELETE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_NOTIFICATION_DELETE_FAILED);
        }
    }

    @Override
    public NotificationJumpTypeList getAllJumpTypeList() throws AryaServiceException {
        NotificationJumpTypeList resultList = new NotificationJumpTypeList();
        List<SimpleIdNameResult> list = new ArrayList<>();
        resultList.setTypes(list);
        for (Integer key : NotificationConstants.jumpTypeMap.keySet()) {
            SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
            simpleIdNameResult.setId(key.toString());
            simpleIdNameResult.setName(NotificationConstants.jumpTypeMap.get(key));
            list.add(simpleIdNameResult);
        }
        return resultList;
    }

    @Override
    public String create(CreateOrUpdateNotificationCommand command) throws Exception {
        PushFilter pushFilter = new PushFilter();
        PushFilter.WhereAnd whereAnd = new PushFilter.WhereAnd();

        List<PushFilter.WhereOr> and = new ArrayList<>();

        PushFilter.WhereOr whereOr = new PushFilter.WhereOr();
        List<Map<String, String>> tags = new ArrayList<>();

//        Map<String, String> map = new HashedMap();
//        map.put("tag","v3.0");
//        tags.add(map);

        log.info("command.getTags(): " + command.getTags().toString());

        if (ListUtils.checkNullOrEmpty(command.getTags())) {
            command.setFilter(null);
        } else {
            tags = command.getTags();
            whereOr.setOr(tags);
            and.add(whereOr);
            whereAnd.setAnd(and);
            pushFilter.setWhere(whereAnd);
            command.setFilter(pushFilter);
        }


        NotificationEntity entity = new NotificationEntity();
        entity.setId(Utils.makeUUID());
        entity.setIsDelete(CorpConstants.FALSE);
        entity.setCreateTime(System.currentTimeMillis());
        entity.setJumpUrl(command.getJumpUrl());
        if (StringUtils.isNotBlank(command.getJumpUrl())) {
            log.info("推送的URL为:" + command.getJumpUrl());
        }
        entity.setContent(command.getContent());
        entity.setJumpType(command.getJumpType());
        entity.setDisplayType(command.getDisplayType());
        entity.setTitle(command.getTitle());
        entity.setDeviceType(255);
        entity.setPushCount(getNotificationUsers(command.getFilterTags()).getCount());
        if (command.getFilterTags() != null && command.getFilterTags().size() > 0) {
            entity.setNotificationType(NOTIFICATION_TYPE_GROUPCAST);
        } else {
            entity.setNotificationType(NOTIFICATION_TYPE_BROADCAST);
        }
        entity.setSetSendTime(command.getSendTime());
        entity.setSendStatus(NotificationConstants.SEND_WAITING);
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        entity.setSender(currentUser.getId());
        StringBuffer logMsg = new StringBuffer("【推送管理】新增推送");
        //整理标签
        if (entity.getNotificationType() != NOTIFICATION_TYPE_BROADCAST) {
            if (command.getFilterTags() != null) {
                createNotifyTags(entity, command.getFilterTags());
            }
        }

        //判断是否立即发送
        if (command.getSendTime() == null) {
            logMsg.append(",立即推送:").append(entity.getContent())
                    .append(",推送人数:").append(entity.getPushCount()).append("人")
                    .append(",显示类型").append(entity.getDisplayType());
            //立即发送
            if (super.notifyNow(entity.getTitle(), entity.getContent(), entity.getJumpType(), entity.getJumpUrl(), entity.getDisplayType(), getNotificationUsers(command.getFilterTags()), command.getFilter()).contains("SUCCESS")) {
                entity.setSendStatus(NotificationConstants.SEND_SUCCESS);
            } else {
                entity.setSendStatus(NotificationConstants.SEND_FAILED);
            }
            entity.setSendTime(System.currentTimeMillis());
        } else {
            //定时发送
            scheduleService.scheduleBroadCastNotification(command.getFilter(), entity, NotificationTaskJob.class);
            logMsg.append(",定时:")
                    .append(SysUtils.getDateSecondStringFormTimestamp(command.getSendTime()))
                    .append(",新增:").append(entity.getContent());
        }
        try {
            notificationDao.create(entity);
            opLogService.successLog(OperateConstants.NOTIFICATION_CREATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.NOTIFICATION_CREATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_NOTIFICATION_CREATE_FAILED);
        }
        return entity.getId();
    }

    /**
     * 给通知实体添加标签
     *
     * @param notificationEntity
     * @param filterTags
     */
    private void createNotifyTags(NotificationEntity notificationEntity, List<FilterTag> filterTags) {
        Set<NotifyTagCategoryEntity> notifyTagCategoryEntities = new HashSet<>();
        for (FilterTag filterTag : filterTags) {
            NotifyTagCategoryEntity corpNotifyTagCategoryEntity = new NotifyTagCategoryEntity();
            corpNotifyTagCategoryEntity.setId(Utils.makeUUID());
            if (filterTag.getCategoryId().contains(CORP_NOTIFICATION_TAG_CATEGORY_ID_PREFIX)) {
                //如果是公司分类特殊处理
                corpNotifyTagCategoryEntity.setType(NotificationConstants.NOTIFICATION_CATEGORY_TYPE_CORP);
                corpNotifyTagCategoryEntity.setCategoryId(filterTag.getCategoryId().replace(CORP_NOTIFICATION_TAG_CATEGORY_ID_PREFIX, ""));
            } else {
                corpNotifyTagCategoryEntity.setType(NotificationConstants.NOTIFICATION_CATEGORY_TYPE_USER);
                corpNotifyTagCategoryEntity.setCategoryId(filterTag.getCategoryId());
            }

            Set<NotifyTagEntity> notifyTagEntities = new HashSet<>();
            for (String corpTag : filterTag.getTags()) {
                NotifyTagEntity notifyTagEntity = new NotifyTagEntity();
                notifyTagEntity.setId(Utils.makeUUID());
                notifyTagEntity.setTagId(corpTag);
                notifyTagEntities.add(notifyTagEntity);
            }
            corpNotifyTagCategoryEntity.setNotifyTagEntities(notifyTagEntities);
            notifyTagCategoryEntities.add(corpNotifyTagCategoryEntity);
        }

        notificationEntity.setNotifyTagCategoryEntities(notifyTagCategoryEntities);
    }

    @Override
    public String update(CreateOrUpdateNotificationCommand command) throws AryaServiceException {

        PushFilter pushFilter = new PushFilter();
        PushFilter.WhereAnd whereAnd = new PushFilter.WhereAnd();

        List<PushFilter.WhereOr> and = new ArrayList<>();

        PushFilter.WhereOr whereOr = new PushFilter.WhereOr();
        List<Map<String, String>> tags = new ArrayList<>();

//        Map<String, String> map = new HashedMap();
//        map.put("tag","v3.0");
//        tags.add(map);

        log.info("command.getTags(): " + command.getTags().toString());

        if (ListUtils.checkNullOrEmpty(command.getTags())) {
            command.setFilter(null);
        } else {
            tags = command.getTags();
            whereOr.setOr(tags);
            and.add(whereOr);
            whereAnd.setAnd(and);
            pushFilter.setWhere(whereAnd);
            command.setFilter(pushFilter);
        }


        NotificationEntity entity = notificationDao.findThrow(command.getId());
        if (entity.getSetSendTime() == null) {
            throw new AryaServiceException(ErrorCode.CODE_NOTIFICATION_CAN_NOT_UPDATE);
        }
        entity.setUpdateTime(System.currentTimeMillis());
        entity.setTitle(command.getTitle());
        entity.setContent(command.getContent());
        entity.setSetSendTime(command.getSendTime());
        entity.setDisplayType(command.getDisplayType());
        entity.setJumpType(command.getJumpType());
        entity.setJumpUrl(command.getJumpUrl());
        if (StringUtils.isNotBlank(command.getJumpUrl())) {
            log.info("推送的URL为:" + command.getJumpUrl());
        }
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        entity.setSender(currentUser.getId());
        StringBuffer logMsg = new StringBuffer("【推送管理】修改推送的id:" + entity.getId());
        entity.setPushCount(getNotificationUsers(command.getFilterTags()).getCount());
        //整理标签
        if (entity.getNotificationType() != NOTIFICATION_TYPE_BROADCAST || (command.getFilterTags() != null && command.getFilterTags().size() > 0)) {
            //先清空
            entity.setNotifyTagCategoryEntities(new HashSet<>());
            if (command.getFilterTags() == null || command.getFilterTags().size() == 0) {
                entity.setNotificationType(NOTIFICATION_TYPE_BROADCAST);
            } else {
                //如果有标签,则添加。
                createNotifyTags(entity, command.getFilterTags());
            }
        }

        //打日志
        if (!entity.getContent().equals(command.getContent())) {
            logMsg.append("原内容:" + entity.getContent() + ",改为:" + command.getContent());
        }
        logMsg.append(",推送人数:" + entity.getPushCount() + "人");
        if (entity.getSetSendTime() != null && !entity.getSetSendTime().equals(command.getSendTime())) {
            if (command.getSendTime() == null) {
                logMsg.append("原定时:" + SysUtils.getDateSecondStringFormTimestamp(entity.getSetSendTime()) + ",改为立即发送。");
            } else {
                logMsg.append("原定时:" + SysUtils.getDateSecondStringFormTimestamp(entity.getSetSendTime()) + ",改为:" + SysUtils.getDateSecondStringFormTimestamp(command.getSendTime()) + "。");
            }
        }

        if (command.getSendTime() == null) {
            //立即发送
            if (notifyNow(entity.getTitle(), entity.getContent(), entity.getJumpType(), entity.getJumpUrl(), entity.getDisplayType(), getNotificationUsers(command.getFilterTags()), command.getFilter()).contains("SUCCESS")) {
                entity.setSendStatus(NotificationConstants.SEND_SUCCESS);
            } else {
                entity.setSendStatus(NotificationConstants.SEND_FAILED);
            }
        } else {
            //修改定时发送
            scheduleService.reScheduleBroadCastNotification(command.getFilter(), entity.getId(), entity.getTitle(), entity.getContent(), entity.getJumpType(), entity.getJumpUrl(), entity.getSetSendTime(), NotificationTaskJob.class, entity.getDisplayType());
        }
        try {
            notificationDao.update(entity);
            opLogService.successLog(OperateConstants.NOTIFICATION_UPDATE, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.NOTIFICATION_UPDATE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_NOTIFICATION_UPDATE_FAILED);
        }
        return entity.getId();
    }
//
//	public String notifyNow(String title, String content, int jumpType, String jumpUrl, NotificationUsersModel usersModel) {
//		PushMessage pushMessage = new PushMessage();
//		pushMessage.setTitle(title);
//		pushMessage.setContent(content);
////        pushMessage.setIcon(icon);
//		pushMessage.setType(jumpType);
//		pushMessage.setUrl(jumpUrl);
//		String pushResult = "";
//		if (usersModel.getType() == NOTIFICATION_TYPE_BROADCAST) {
//			//广播
//			PushTo.PushToAll toAll = new PushTo.PushToAll(Utils.makeUUID());
//			pushResult = pushService.push(pushMessage, toAll);
//
//		} else if (usersModel.getType() == NOTIFICATION_TYPE_GROUPCAST) {
//			//ios组播
//			PushManyNoticeThread pushIosManyNoticeThread = new PushManyNoticeThread(pushService, pushMessage, new ArrayList<>(usersModel.getIosUserIds()), CLINET_IOS, "system");
//			pushIosManyNoticeThread.run();
//
//			//android组播
//			PushManyNoticeThread pushAndroidManyNoticeThread = new PushManyNoticeThread(pushService, pushMessage, new ArrayList<>(usersModel.getAndroidUserIds()), CLINET_ANDROID, "system");
//			pushAndroidManyNoticeThread.run();
//		}
//
//		log.info(pushResult);
//		return pushResult;
//
//	}

//	@Override
//	public void addNewBroadcastSchedule(NotificationEntity notificationEntity) {
//		scheduleService.scheduleBroadCastNotification(notificationEntity.getId(), notificationEntity.getTitle(), notificationEntity.getContent(), notificationEntity.getJumpType(), notificationEntity.getJumpUrl(), notificationEntity.getSetSendTime(), NotificationTaskJob.class);
//	}

    @Override
    public GetAllTagsResult getAllTags() throws AryaServiceException {
        GetAllTagsResult result = new GetAllTagsResult();
        List<TagCategoryResult> tagCategoryResultList = new ArrayList<>();
        result.setCorpTags(getAllCorpTags());
        result.setFilterTags(tagCategoryResultList);

        //查出所有标签
        List<TagCategoryEntity> tagCategoryEntities = tagCategoryDao.findAllTagCategory();
        for (TagCategoryEntity tagCategoryEntity : tagCategoryEntities) {
            TagCategoryResult tagCategoryResult = new TagCategoryResult();
            tagCategoryResult.setIsMultiSelect(tagCategoryEntity.getIsMultiSelect());
            tagCategoryResult.setCategoryName(tagCategoryEntity.getName());
            tagCategoryResult.setCategoryId(tagCategoryEntity.getId());
            List<SimpleIdNameResult> tags = new ArrayList<>();
            tagCategoryResult.setTags(tags);
            for (TagEntity tagEntity : tagCategoryEntity.getTagEntities()) {
                SimpleIdNameResult tag = new SimpleIdNameResult();
                tag.setId(tagEntity.getId());
                tag.setName(tagEntity.getName());
                tags.add(tag);
            }
            tagCategoryResultList.add(tagCategoryResult);
        }
        return result;
    }

    @Override
    public TagCategoryResult getAllCorpTags() throws AryaServiceException {
        //特殊处理公司标签
        TagCategoryResult result = new TagCategoryResult();
        result.setCategoryId(CORP_NOTIFICATION_TAG_CATEGORY_ID_PREFIX + "004c5dab9d514bdebd83d3a762a4f7a1");
        result.setCategoryName("项目");
        result.setIsMultiSelect(CorpConstants.TRUE);
        OrganizationTreeResult treeResult = corporationService.generateOrganizationTree();
        List<SimpleIdNameResult> corpTags = new ArrayList<>();
        result.setTags(corpTags);
        for (OrganizationTreeResult.OrganizationResult corp : treeResult.getTree()) {
            SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
            simpleIdNameResult.setId(corp.getId());
            simpleIdNameResult.setName(corp.getName());
            corpTags.add(simpleIdNameResult);
        }
        return result;
    }

}
