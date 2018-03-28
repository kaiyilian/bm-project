package com.bumu.bran.admin.prospective.helper;

import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.prospective.handler.Checkable;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author majun
 * @date 2018/1/24
 * @email 351264830@qq.com
 */
public enum ProspectiveEntryStatus {

    unCodeScanning("未扫码",
            prospectiveEmployeeEntity -> prospectiveEmployeeEntity == null || prospectiveEmployeeEntity.getAcceptOffer() == 0,
            "请通知员工扫码，开始入职"
    ),

    writingInfo("填写资料中",
            prospectiveEmployeeEntity ->
                    prospectiveEmployeeEntity != null
                            && prospectiveEmployeeEntity.getAcceptOffer() == 1
                            && prospectiveEmployeeEntity.getCheckinComplete() == 0,
            "员工尚未提交资料审核，请稍等"
    ),

    completedInfo("填写资料完成", null, "请通知员工点击下一步，完成资料提交"),

    hrReview("待审核",
            prospectiveEmployeeEntity -> prospectiveEmployeeEntity != null &&
                    prospectiveEmployeeEntity.getAcceptOffer() == 1
                    && prospectiveEmployeeEntity.getCheckinComplete() == 1,
            "请求接口:/admin/employee/prospective/manage/check,根据返回结果动态显示"
    );

    private String desc;
    private Checkable<ProspectiveEmployeeEntity> checkable;
    private String nextStep;

    ProspectiveEntryStatus(String desc, Checkable<ProspectiveEmployeeEntity> checkable, String nextStep) {
        this.desc = desc;
        this.checkable = checkable;
        this.nextStep = nextStep;
    }

    public String getDesc() {
        return desc;
    }

    public String getNextStep() {
        return nextStep;
    }

    @Component
    public static class ProspectiveEntryStatusProcess {

        private static Logger logger = LoggerFactory.getLogger(ProspectiveEntryStatus.class);

        @Autowired
        private ProspectiveHelper prospectiveHelper;

        @Autowired
        private AryaUserDao aryaUserDao;

        @Autowired
        private BranUserDao branUserDao;


        public int getEntryStatus(ProspectiveEmployeeEntity prospectiveEmployeeEntity) {

            int entryStatus = unCodeScanning.ordinal();

            if (prospectiveEmployeeEntity == null) {
                logger.debug("prospectiveEmployeeEntity is null: " + entryStatus);
                return entryStatus;
            }

            logger.info("参数: getAcceptOffer:" + prospectiveEmployeeEntity.getAcceptOffer() + " , getCheckinComplete: "
                    + prospectiveEmployeeEntity.getCheckinComplete());

            if (unCodeScanning.checkable.check(prospectiveEmployeeEntity)) {
                return unCodeScanning.ordinal();
            }

            logger.debug("判断资料是否填写完整: ");
            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(prospectiveEmployeeEntity.getPhoneNo());

            if (aryaUserEntity == null) {
                logger.debug("aryaUserEntity is null");
                return writingInfo.ordinal();
            }

            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity == null) {
                logger.debug("branUserEntity is null");
                return writingInfo.ordinal();
            }

            if (prospectiveHelper.checkCompletedInfo(aryaUserEntity, branUserEntity)
                    && prospectiveEmployeeEntity.getCheckinComplete() == 0) {
                return completedInfo.ordinal();
            }

            if (writingInfo.checkable.check(prospectiveEmployeeEntity)) {
                return writingInfo.ordinal();
            }

            if (hrReview.checkable.check(prospectiveEmployeeEntity)) {
                return hrReview.ordinal();
            }
            return entryStatus;
        }
    }


}
