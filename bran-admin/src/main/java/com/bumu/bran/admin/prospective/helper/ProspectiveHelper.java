package com.bumu.bran.admin.prospective.helper;

import com.bumu.SysUtils;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.corporation.result.CreateProspectiveEmployeeResult;
import com.bumu.bran.admin.push.vo.CheckinMessageSendTimeModel;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.service.CommonBranCorpService;
import com.bumu.bran.corporation.model.dao.BranCorpCheckinMsgDao;
import com.bumu.bran.corporation.model.entity.BranCorpCheckinMessageEntity;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.bran.service.ScheduleService;
import com.bumu.common.model.SendManySmsMessage;
import com.bumu.common.service.SmsSendService;
import com.bumu.prospective.command.SaveProspectiveEmployeeCommand;
import com.bumu.prospective.helper.ProspectiveCommonHelper;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProspectiveHelper extends ProspectiveCommonHelper {
    private static final Logger logger = LoggerFactory.getLogger(ProspectiveHelper.class);

    @Autowired
    private CommonBranCorpService commonBranCorpService;

    @Autowired
    private BranAdminConfigService branAdminConfigService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private LeaveEmployeeDao leaveEmployeeDao;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private BranUserDao branUserDao;

    @Autowired
    private BranCorpCheckinMsgDao corpCheckinMessageDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private SendManySmsMessage sendManySmsMessage;

    public void setOtherPropOnSave(ProspectiveEmployeeEntity entity, SaveProspectiveEmployeeCommand command) {
        entity.setPositionName(commonBranCorpService.getPositionNameById(command.getPositionId()));
        entity.setWorkLineName(commonBranCorpService.getWorkLineNameById(command.getWorkLineId()));
        entity.setWorkShiftName(commonBranCorpService.getWorkShiftNameById(command.getWorkShiftId()));
        entity.setDepartmentName(commonBranCorpService.getDepartmentNameById(command.getDepartmentId()));
    }

    // 是否已经离职过
    public void checkIsLeaving(SaveProspectiveEmployeeCommand command, CreateProspectiveEmployeeResult result) {
        String leaveEmployeeId = leaveEmployeeDao.findLeaveEmployeeByPhoneNo(command.getPhoneNo(), command.getBranCorpId());
        if (StringUtils.isNotBlank(leaveEmployeeId)) {
            result.setMsg("注意：该员工 " + command.getName() + " 的手机号码 " + command.getPhoneNo() +
                    " 曾在您公司有离职记录。");
        }
    }

    // 是否存在多次不良记录
    public void checkHasManyBadRecords(SaveProspectiveEmployeeCommand command, CreateProspectiveEmployeeResult result,
                                       ProspectiveEmployeeEntity entity) {
        AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(command.getPhoneNo());
        if (aryaUserEntity != null) {
            //找到brUser
            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId(),
                    command.getBranCorpId());
            if (branUserEntity != null) {
                if (branUserEntity.getBadRecordCount() >= branAdminConfigService.getBadRecordCount()) {
                    entity.setBadRecordExceed(Constants.TRUE);
                    result.setMsg("注意：该员工 " + command.getName() + " 已经有过多次不良记录");
                }
            }
        }
    }

    // 发送入职短消息
    public void sendProspectiveMsg(SaveProspectiveEmployeeCommand command) throws ParseException {
        CheckinMessageSendTimeModel sendTimeModel = calculateCorpCheckinMessageSendTime(command.getCheckInTime(), command.getBranCorpId());
        scheduleService.scheduleCheckinNotification(command.getBranCorpId(), command.getPhoneNo(), command.getCheckInTime(),
                sendTimeModel.getYear(), sendTimeModel.getMonth(), sendTimeModel.getDay(), sendTimeModel.getHour(), sendTimeModel.getMinute());
    }

    public void sendTipMsg(SaveProspectiveEmployeeCommand command, ProspectiveEmployeeEntity entity) throws Exception {
        List<String> phones = new ArrayList<>();
        phones.add(entity.getPhoneNo());
        // 查询公司
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(command.getBranCorpId());
        logger.info("发送短消息之前判断公司是否存在：" + command.getBranCorpId());
        if (branCorporationEntity != null) {
            logger.info("公司存在：" + branCorporationEntity.getCorpName());
            String msg = String.format(branAdminConfigService.getProspectiveSMS_Msg(), branCorporationEntity.getCorpName(),
                    branCorporationEntity.getCheckinCode());
            logger.info("branCorporationEntity.getId(): " + branCorporationEntity.getId());
            logger.info("branCorporationEntity.getCorpName(): " + branCorporationEntity.getCorpName());
            logger.info("branCorporationEntity.getCheckinCode(): " + branCorporationEntity.getCheckinCode());
            logger.info("msg: " + msg);
            logger.info("smsMessageServiceImpl to String ... " + smsSendService.toString());
            sendManySmsMessage.init(msg, phones);
            sendManySmsMessage.run();
        }
    }

    // 发送提醒短消息
    private CheckinMessageSendTimeModel calculateCorpCheckinMessageSendTime(long checkinTime, String branCorpId) throws ParseException {
        BranCorpCheckinMessageEntity corpCheckinMessageEntity = corpCheckinMessageDao.findCheckinMessageByBranCorpId(branCorpId);
        return calculateCheckinMessageSendTime(checkinTime, corpCheckinMessageEntity.getBeforeCheckinDay(), corpCheckinMessageEntity.getPostHour());
    }

    private CheckinMessageSendTimeModel calculateCheckinMessageSendTime(long checkinTime, int beforeDay, int postHour) throws ParseException {
        long sendTime = SysUtils.getOneDayStartTime(checkinTime) - beforeDay * 24 * 60 * 60 * 1000 + postHour * 60 * 60 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String[] date = dateFormat.format(new Date(sendTime)).split("-");
        CheckinMessageSendTimeModel model = new CheckinMessageSendTimeModel();
        model.setYear(Integer.parseInt(date[0]));
        model.setMonth(Integer.parseInt(date[1]));
        model.setDay(Integer.parseInt(date[2]));
        model.setHour(Integer.parseInt(date[3]));
        model.setMinute(branAdminConfigService.getNotificationMinute());
        return model;
    }
}
