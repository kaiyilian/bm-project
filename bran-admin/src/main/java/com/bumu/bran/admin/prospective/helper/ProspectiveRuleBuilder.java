package com.bumu.bran.admin.prospective.helper;

import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.employee.command.EmpQueryCommand;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.prospective.handler.Checkable;
import com.bumu.prospective.helper.ProspectiveCommonHelper;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author majun
 * @date 2018/1/23
 * @email 351264830@qq.com
 */
@Component
public class ProspectiveRuleBuilder {

    private static Logger logger = getLogger(ProspectiveRuleBuilder.class);

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private BranUserDao branUserDao;

    @Autowired
    private BranAdminConfigService configService;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    private ProspectiveEmployeeEntity ruleEntity;

    private List<Rule<ProspectiveEmployeeEntity>> rules = new ArrayList<>();

    private StringBuilder errorStr = new StringBuilder();

    public ProspectiveRuleBuilder clear() {
        clearRules();
        clearErrorStr();
        return this;
    }

    private void clearRules() {
        rules.clear();
    }

    private void clearErrorStr() {
        errorStr.delete(0, errorStr.length());
    }

    public ProspectiveRuleBuilder addRule(Rule rule) {
        rules.add(rule);
        return this;
    }

    public ProspectiveRuleBuilder addCheckEntity(ProspectiveEmployeeEntity ruleEntity) {
        this.ruleEntity = ruleEntity;
        return this;
    }

    public String getErrorStr() {
        clearErrorStr();
        rules.forEach(r -> {
            if (StringUtils.isNotBlank(r.getCheckStr())) {
                errorStr.append(r.getCheckStr()).append(" , ");
            }
        });
        return errorStr.toString();
    }

    public ProspectiveRuleBuilder onCheckSuccess(Consumer<ProspectiveEmployeeEntity> consumer) {
        logger.debug("onCheckSuccess: " + getErrorStr());
        if (StringUtils.isBlank(getErrorStr())) {
            consumer.accept(ruleEntity);
        }
        return this;
    }

    public ProspectiveRuleBuilder onCheckFail(Consumer<ProspectiveEmployeeEntity> consumer) {
        logger.debug("onCheckFail: " + getErrorStr());
        if (StringUtils.isNotBlank(getErrorStr())) {
            consumer.accept(ruleEntity);
        }

        return this;
    }

    public ProspectiveRuleBuilder check() {
        for (Rule rule : rules) {
            rule.check(ruleEntity);
        }
        return this;
    }

    public interface Rule<T> extends Checkable<T> {
        String getCheckStr();
    }

    public class RequireFieldRule implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity proEmployeeEntity) {

            logger.debug("check RequireFieldRule: ");

            boolean flag = true;

            if (proEmployeeEntity.getCheckinTime() == null) {
                stringBuilder.append("请先填写入职时间才能同意入职, ");
                flag = false;
            }

            if (proEmployeeEntity.getPositionId() == null || StringUtils.isBlank(proEmployeeEntity.getPositionName())) {
                stringBuilder.append("请先填写职位才能同意入职, ");
                flag = false;
            }

            if (proEmployeeEntity.getWorkShiftId() == null || StringUtils.isBlank(proEmployeeEntity.getWorkShiftName())) {
                stringBuilder.append("请先填写班组才能同意入职, ");
                flag = false;
            }


            if (proEmployeeEntity.getWorkLineId() == null || StringUtils.isBlank(proEmployeeEntity.getWorkLineName())) {
                stringBuilder.append("请先填写工段才能同意入职, ");
                flag = false;
            }


            if (proEmployeeEntity.getDepartmentId() == null || StringUtils.isBlank(proEmployeeEntity.getDepartmentName())) {
                stringBuilder.append("请先填写部门才能同意入职, ");
                flag = false;
            }

            logger.debug("getCheckStr: " + getCheckStr());
            return flag;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class CheckinCompleteRule implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity proEmployeeEntity) {

            logger.debug("check CheckinCompleteRule: ");
            if (proEmployeeEntity.getAcceptOffer() == 1 && proEmployeeEntity.getCheckinComplete() == 1) {
                return true;
            }
            stringBuilder.append("存在尚未完成入职流程的待入职员工");
            logger.debug("getCheckStr: " + getCheckStr());
            return false;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class UnregisteredRule implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity proEmployeeEntity) {
            logger.debug("check UnregisteredRule: ");
            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(proEmployeeEntity.getPhoneNo());
            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }
            return true;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class AppInfoUnfilledRule implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity prospectiveEmployeeEntity) {
            logger.debug("check AppInfoUnfilledRule: ");

            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(prospectiveEmployeeEntity.getPhoneNo());
            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }
            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity == null) {
                stringBuilder.append("待入职员工在app中入职资料尚未填写完整");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }
            return true;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class CheckinDiffCorp implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity proEmployeeEntity) {
            logger.debug("check CheckinDiffCorp: ");

            if (StringUtils.isBlank(proEmployeeEntity.getBranCorpId())) {
                stringBuilder.append("待入职员工没有公司");
                return true;
            }

            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(proEmployeeEntity.getPhoneNo());

            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                return false;
            }

            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity == null) {
                stringBuilder.append("待入职员工在app中入职资料尚未填写完整");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(branUserEntity.getBranCorpId());
            if (branCorporationEntity == null) {
                stringBuilder.append("没有查询到公司: " + branUserEntity.getBranCorpId());
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            if (!proEmployeeEntity.getBranCorpId().equals(branUserEntity.getBranCorpId())) {
                stringBuilder.append("待入职员工人事关系在其他公司, 公司名: " + branCorporationEntity.getCorpName());
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            return true;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class ProspectiveProFileNotComplete implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity prospectiveEmployeeEntity) {

            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(prospectiveEmployeeEntity.getPhoneNo());

            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                return false;
            }

            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity == null) {
                stringBuilder.append("待入职员工在app中入职资料尚未填写完整");
                return false;
            }

            if (ProspectiveCommonHelper.getRequireProfileCompleteCount(aryaUserEntity, branUserEntity) != configService.getRequireProfileTotal()) {
                stringBuilder.append("存在待入职员工资料尚未填写完成");
                return false;
            }

            return true;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

    public class InfoUnMatch implements Rule<ProspectiveEmployeeEntity> {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public boolean check(ProspectiveEmployeeEntity prospectiveEmployeeEntity) {
            logger.debug("check ProspectiveProFileNotComplete: ");
            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(prospectiveEmployeeEntity.getPhoneNo());

            if (aryaUserEntity == null) {
                stringBuilder.append("待入职员工尚未注册app");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity == null) {
                stringBuilder.append("待入职员工在app中入职资料尚未填写完整");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }


            // 判断身份证号与花名是否相同
            EmpQueryCommand empQueryCommand = new EmpQueryCommand();
            empQueryCommand.setRegisterAccount(null);
            empQueryCommand.setWorkSn(null);
            empQueryCommand.setIdCardNo(aryaUserEntity.getIdcardNo().trim());
            empQueryCommand.setBranCorpId(prospectiveEmployeeEntity.getBranCorpId());

            List<EmployeeEntity> registerAccounts = employeeDao.findByQueryCommand(empQueryCommand);
            if (!ListUtils.checkNullOrEmpty(registerAccounts)) {
                stringBuilder.append("身份证不能与花名册员工的身份证相同 ");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            if (prospectiveEmployeeEntity.getFullName() == null || branUserEntity.getRealName() == null
                    || !prospectiveEmployeeEntity.getFullName().trim().equals(branUserEntity.getRealName().trim())) {
                stringBuilder.append("待入职员工姓名与资料不符 ");
                logger.debug("getCheckStr: " + getCheckStr());
                return false;
            }

            return true;
        }

        @Override
        public String getCheckStr() {
            return stringBuilder.toString();
        }
    }

}
