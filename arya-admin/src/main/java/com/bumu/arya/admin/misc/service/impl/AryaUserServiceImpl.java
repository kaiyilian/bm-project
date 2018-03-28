package com.bumu.arya.admin.misc.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.misc.controller.command.AryaUserListCommand;
import com.bumu.arya.admin.misc.result.AryaUserInfoListResult;
import com.bumu.arya.admin.misc.result.AryaUserInfoResult;
import com.bumu.arya.admin.misc.result.UserEmpInfo;
import com.bumu.arya.admin.misc.result.UserInfoResult;
import com.bumu.arya.admin.misc.service.AryaUserService;
import com.bumu.arya.admin.devops.model.dao.ApiLogRepository;
import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.user.service.impl.BaseAryaUserServiceImpl;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.bran.service.BranConfigService;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.command.EmpQueryCommonCommand;
import com.bumu.employee.model.dao.impl.EmployeeCommonDao;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.payroll.model.dao.PayrollRepository;
import com.bumu.esalary.model.dao.mybatis.ESalaryMybatisDao;
import com.bumu.payroll.model.entity.PayrollDocument;
import com.bumu.payroll.model.entity.PayrollDetail;
import com.bumu.payroll.result.UserPayrollResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.prospective.helper.ProspectiveCommonHelper;
import com.bumu.prospective.model.dao.ProspectiveEmployeeCommonDao;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bumu.bran.common.Constants.HR_CREATE;
import static com.bumu.prospective.constant.ProspectiveConstant.MEMO;

/**
 * Created by CuiMengxin on 2016/10/17.
 */
@Service
public class AryaUserServiceImpl extends BaseAryaUserServiceImpl implements AryaUserService {

    private static Logger logger = LoggerFactory.getLogger(AryaUserServiceImpl.class);

    @Autowired
    private ESalaryMybatisDao eSalaryMybatisDao;

    @Autowired
    private BranUserDao branUserDao;

    @Resource(name = "prospectiveEmployeeCommonDaoImpl")
    private ProspectiveEmployeeCommonDao prospectiveEmployeeDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Resource(name = "employeeCommonDaoImpl")
    private EmployeeCommonDao employeeCommonDao;

    @Autowired
    private ApiLogRepository apiLogRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ProspectiveCommonHelper prospectiveCommonHelper;

    @Autowired
    private BranConfigService configService;

    @Override
    public AryaUserInfoResult getUserInfo(String keyword) throws AryaServiceException {

        AryaUserEntity userEntity = null;
        if (IdcardValidator.isValidatedAllIdcard(keyword)) {
            userEntity = userDao.findUserByIdcardNo(keyword);
        } else if (SysUtils.checkPhoneNo(keyword)) {
            userEntity = userDao.findUserByPhoneNo(keyword);
        } else {
            userEntity = userDao.findUserByRealName(keyword);
        }
        if (userEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_USER_NOT_EXIST);
        }
        AryaUserInfoResult userInfoResult = new AryaUserInfoResult();
        userInfoResult.setId(userEntity.getId());
        userInfoResult.setName(userEntity.getRealName());
        userInfoResult.setIdcardNo(userEntity.getIdcardNo());
        userInfoResult.setNickName(userEntity.getNickName());
        if (StringUtils.isNotBlank(userEntity.getCorporationId())) {
            CorporationEntity corporationEntity = corporationDao.findCorporationById(userEntity.getCorporationId());
            if (corporationEntity != null) {
                userInfoResult.setCorp(corporationEntity.getName());
            }
        }
        return userInfoResult;
    }

    @Override
    public AryaUserInfoListResult getUserList(AryaUserListCommand command) throws AryaServiceException {
        AryaUserInfoListResult result = new AryaUserInfoListResult();
        List<AryaUserInfoResult> list = new ArrayList<>();
        result.setUsers(list);
        Pager<AryaUserEntity> pager = userDao.findUsers(command.getPage(), command.getPage_size(), command.getKeyword());
        if (pager.getResult().size() == 0) {
            return result;
        }
        for (AryaUserEntity userEntity : pager.getResult()) {
            AryaUserInfoResult userInfoResult = new AryaUserInfoResult();
            userInfoResult.setId(userEntity.getId());
            userInfoResult.setName(userEntity.getRealName());
            userInfoResult.setIdcardNo(userEntity.getIdcardNo());
            userInfoResult.setNickName(userEntity.getNickName());
//            userInfoResult.setSex();
            if (StringUtils.isNotBlank(userEntity.getCorporationId())) {
                CorporationEntity corporationEntity = corporationDao.findCorporationById(userEntity.getCorporationId());
                if (corporationEntity != null) {
                    userInfoResult.setCorp(corporationEntity.getName());
                    userInfoResult.setShortName(corporationEntity.getShortName());
                }
            }
            userInfoResult.setCreateTime(userEntity.getCreateTime());
            userInfoResult.setLastLoginTime(userEntity.getLastLoginTime());
            userInfoResult.setPhoneNo(userEntity.getPhoneNo());
            userInfoResult.setUsePhoneNo(userEntity.getUsedPhoneNo());
            if (userEntity.getBalance() == null) {
                userInfoResult.setBalance(BigDecimal.valueOf(0));
            } else {
                userInfoResult.setBalance(userEntity.getBalance());
            }
            list.add(userInfoResult);
        }
        result.setTotalRows(pager.getRowCount());
        result.setPages(Utils.calculatePages(pager.getRowCount(), command.getPage_size()));
        return result;
    }

    @Override
    public boolean filterUserTag(List<Integer> tags, int userTag) {
        return false;
    }

    @Override
    public UserInfoResult getUserAppInfo(String tel) {
        UserInfoResult userInfoResult = new UserInfoResult();
        AryaUserEntity aryaUserEntity = userDao.findUserByPhoneNo(tel);
        if (aryaUserEntity == null) {
            return userInfoResult;
        }
        userInfoResult.convert(aryaUserEntity);
        ApiLogDocument apiLogDocument = apiLogRepository.findByUserIdLastAccess(aryaUserEntity.getId());
        if (apiLogDocument != null) {
            Map<String, Object> map = apiLogDocument.getContextMap();
            if (map != null) {
                userInfoResult.setAppVersion((String) map.get("app_version"));
            }
            logger.debug("appVersion: " + userInfoResult.getAppVersion());
            userInfoResult.setLastAccessTime(apiLogDocument.getMillis());
        }
        return userInfoResult;
    }

    @Override
    public List<UserPayrollResult> getUserPayrollInfo(String tel) {
        List<UserPayrollResult> payrollResults = eSalaryMybatisDao.getUserPayrollInfo(tel);
        if (!ListUtils.checkNullOrEmpty(payrollResults)) {
            for (UserPayrollResult payrollResult : payrollResults) {
                PayrollDocument payrollDocument = payrollRepository.findByUserInfo(payrollResult.getUserId(), payrollResult.getId());
                if (payrollDocument != null) {
//                    logger.info("branMongoSalaryInfoEntity id: " + branMongoSalaryInfoEntity.getId());
                    List<PayrollDetail> other = payrollDocument.getOther();
                    if (!ListUtils.checkNullOrEmpty(other)) {
                        other
                                .parallelStream()
                                .filter(one -> "idCardNo".equals(one.getColumn()))
                                .findFirst()
                                .ifPresent(col -> {
                                    logger.debug("idCardNo: " + col.getValue());
                                    payrollResult.setIdCardNo(col.getValue());
                                });
                    }
                }
            }
        }
        return payrollResults;
    }

    @Override
    public UserEmpInfo getUserEmpInfo(String tel) {
        UserEmpInfo userEmpInfo = new UserEmpInfo();
        UserEmpInfo.App app = new UserEmpInfo.App();

        logger.debug("query app user ... ");
        AryaUserEntity aryaUserEntity = userDao.findUserByPhoneNo(tel);
        if (aryaUserEntity != null) {
            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity != null) {
                app.setExpireTime(branUserEntity.getExpireTime());
                app.setRealName(aryaUserEntity.getRealName());
                app.setIdCardNo(aryaUserEntity.getIdcardNo());
                app.setNation(branUserEntity.getNation());
                app.setSex(branUserEntity.getSex());
                userEmpInfo.setApp(app);
            }
        }

        logger.debug("query prospective user ... ");

        List<UserEmpInfo.Prospective> prospectiveResult = new ArrayList<>();

        List<ProspectiveEmployeeEntity> prospectives = prospectiveEmployeeDao.findProspectiveEmployeeByPhoneNo(tel);

        if (!ListUtils.checkNullOrEmpty(prospectives)) {

            for (ProspectiveEmployeeEntity p : prospectives) {
                BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(p.getBranCorpId());
                if (branCorporationEntity == null) {
                    continue;
                }
                UserEmpInfo.Prospective prospective = new UserEmpInfo.Prospective();
                prospective.setRealName(p.getFullName());
                prospective.setAcceptOffer(p.getAcceptOffer());
                prospective.setCheckinCode(branCorporationEntity.getCheckinCode());
                prospective.setCheckinComplete(p.getCheckinComplete());

                if (p.getCreateType() != null && p.getCreateType() == HR_CREATE && prospective.getCheckinComplete() == 1) {
                    prospective.setAcceptToEmp(1);
                }

                prospective.setCorpName(branCorporationEntity.getCorpName());
                BranUserEntity branUserEntity = branUserDao.findBranUserByPhoneNoAndCorpId(p.getPhoneNo(), p.getBranCorpId());
                if (branUserEntity != null) {
                    if (branUserEntity.getFaceMatch() != null && branUserEntity.getFaceMatch() == 1) {
                        prospective.setMemo(MEMO);
                    }
                }
                prospective.setPhoneNo(p.getPhoneNo());
                prospective.setProfileProgress(prospectiveCommonHelper.getProgressStr(p.getPhoneNo(), configService.getRequireProfileTotal()));
                prospective.setCheckinTime(p.getCheckinTime());
                prospectiveResult.add(prospective);
                userEmpInfo.setProspectives(prospectiveResult);
            }
        }

        logger.debug("query emp user ... ");

        EmpQueryCommonCommand empQueryCommonCommand = new EmpQueryCommonCommand();
        empQueryCommonCommand.setRegisterAccount(tel);
        empQueryCommonCommand.setIsBinding(null);
        List<EmployeeEntity> employeeEntities = employeeCommonDao.findEmployeeByQueryCommand(empQueryCommonCommand);

        List<UserEmpInfo.Emp> emps = new ArrayList<>();

        if (!ListUtils.checkNullOrEmpty(employeeEntities)) {

            for (EmployeeEntity employeeEntity : employeeEntities) {
                BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(employeeEntity.getBranCorpId());
                UserEmpInfo.Emp emp = new UserEmpInfo.Emp();
                if (branCorporationEntity != null) {
                    emp.setCheckinCode(branCorporationEntity.getCheckinCode());
                    emp.setCorpName(branCorporationEntity.getCorpName());
                    emp.setRealName(employeeEntity.getRealName());
                    emp.setPhoneNo(employeeEntity.getRegisterAccount());
                    emp.setWorkAttendanceAddState(employeeEntity.getWorkAttendanceAddState().ordinal());
                    emp.setWorkAttendanceNo(employeeEntity.getWorkAttendanceNo());
                    emp.setIsBinding(employeeEntity.getIsBinding());
                    emps.add(emp);
                }
            }
        }
        userEmpInfo.setEmps(emps);
        return userEmpInfo;
    }

    @Override
    public List<EmployeeEntity> getEmployeeUserById(String corpId, String id) {
        return employeeCommonDao.getEmployeeUserById(corpId,id);
    }
}
