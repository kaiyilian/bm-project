package com.bumu.arya.admin.devops.service.impl;

import com.bumu.arya.admin.devops.model.dao.ApiLogRepository;
import com.bumu.arya.admin.devops.model.entity.ApiLogDocument;
import com.bumu.arya.admin.devops.result.UserActivityResult;
import com.bumu.arya.admin.devops.result.UserActivityResult.UserActivity;
import com.bumu.arya.admin.devops.service.UserActivityService;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.employee.constant.EmployeeConstants;
import com.bumu.employee.model.dao.impl.EmployeeCommonDao;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.payroll.model.dao.PayrollRepository;
import com.bumu.payroll.model.dao.PayrollInfoDao;
import com.bumu.payroll.model.entity.PayrollEmpUserEntity;
import com.bumu.payroll.model.entity.PayrollInfoEntity;
import com.bumu.payroll.model.dao.BranSalaryEmpUserDao;
import com.bumu.payroll.model.entity.PayrollDocument;
import com.github.swiftech.swifttime.Time;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Allen 2018-02-27
 **/
@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    ApiLogRepository apiLogRepository;

    @Autowired
    PayrollInfoDao payrollInfoDao;

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    BranSalaryEmpUserDao branSalaryEmpUserDao;

    @Autowired
    AryaUserDao aryaUserDao;

    @Autowired
    BranCorporationDao branCorporationDao;

    @Autowired
    EmployeeCommonDao employeeCommonDao;

    Time start;
    Time end;

    @PostConstruct
    public void init() {
        start = new Time(2018, 1, 1).truncateAtDate();
        end = new Time(2018, 2, 28).truncateAtDate();
    }

    @Override
    public List<ApiLogDocument> findLastVisit() {
        List<ApiLogDocument> lastVisitBetween = apiLogRepository.findLastVisitBetween(start, end);
        return lastVisitBetween;
    }

    @Override
    public UserActivityResult getActivitis() {
        UserActivityResult ret = new UserActivityResult();

        List<ApiLogDocument> lastVisitBetween = apiLogRepository.findLastVisitBetween(start, end);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("YYYY-MM-dd HH:mm:ss");

        System.out.println("查询得到：" + lastVisitBetween.size());
        for (ApiLogDocument apiLog : lastVisitBetween) {
            UserActivity ua = new UserActivity();
            String userId = (String) apiLog.getContextMap().get("user_id");
            Time lastVisitTime = new Time(apiLog.getMillis());

            if (StringUtils.isBlank(userId)) {
                continue;
            }

            // 通过用户ID查询手机号码，身份证号码、注册时间
            AryaUserEntity aryaUser = aryaUserDao.find(userId);
            String phoneNo = null;
            String idcardNo = null;
            Long regTime = null;
            if (aryaUser != null) {
                phoneNo = aryaUser.getPhoneNo();
                idcardNo = aryaUser.getIdcardNo();
                regTime = aryaUser.getCreateTime();
            }
            else {
                System.out.printf("没有找到 %s 对应的用户信息%n", userId);
                continue;
            }

            System.out.println("查询手机号码：" + phoneNo);
            // 通过手机号码查询 BRAN_ESALARY_EMP_USER 的ID和姓名
            List<PayrollEmpUserEntity> userList = branSalaryEmpUserDao.findByPhoneNo(phoneNo);
            Collections.sort(userList, new Comparator<PayrollEmpUserEntity>() {
                @Override
                public int compare(PayrollEmpUserEntity o1, PayrollEmpUserEntity o2) {
                    return o2.getCreateTime().compareTo(o1.getCreateTime());
                }
            });
            // 最近一次导入工资单
            PayrollEmpUserEntity empUser = null;
            String realName = null;
            String empUserId = null;
            String batchId = null;
            Long lastBatchTime = null;
            String payrollCorpName = null;
            String sex = null;
            String empCorpName = null;
            if (userList == null || userList.isEmpty()) {
                System.out.printf("没有找到手机号码 %s 对应的工资单用户信息%n", phoneNo);
            }
            else {
                empUser = userList.get(0);
                realName = empUser.getName();
                empUserId = empUser.getId();

                // 通过 BRAN_ESALARY_EMP_USER 的 ID 查询 mongo 中 SALARY_INFO 工资单批次id
                List<PayrollDocument> payrollList = payrollRepository.findByEmpUserId(empUserId);
                if (payrollList == null || payrollList.isEmpty()) {
                    System.out.printf("没有找到 emp user id %s 对应的工资单信息%n", empUserId);
                }
                else {
                    PayrollDocument latestPayroll = payrollList.get(0);
                    batchId = latestPayroll.getBranSalaryInfoId();

                    // 通过批次ID查询到发薪批次，算出最新一次工资单的时间
                    PayrollInfoEntity payrollBatch = payrollInfoDao.find(batchId);
                    lastBatchTime = payrollBatch.getCreateTime();

                    // 通过批次信息查询到关联的公司名称
                    String branCorpId = payrollBatch.getBranCorpId();
                    if (!StringUtils.isBlank(branCorpId)) {
                        BranCorporationEntity payrollCorp = branCorporationDao.find(branCorpId);
                        if (payrollCorp != null) {
                            payrollCorpName = payrollCorp.getCorpName();
                        }
                    }

                    // 通过手机号码查询花名册中的性别、所属公司名称。
                    EmployeeEntity employee = employeeCommonDao.findEmployeeByPhoneAndBranCorpId(phoneNo, branCorpId);
                    if (employee == null) {
                        System.out.printf("没有查到花名册信息(phone: %s, bran_corp_id: %s)%n", phoneNo, branCorpId);
                    }
                    else {
                        sex = EmployeeConstants.getSex(employee.getSex());
                        BranCorporationEntity empCorp = branCorporationDao.find(employee.getBranCorpId());
                        if (empCorp == null) {
                            System.out.println("没有查询到企业信息：" + employee.getBranCorpId());
                        }
                        else {
                            empCorpName = empCorp.getCorpName();
                        }
                    }
                }
            }

            ua.setCorpName(empCorpName);
            ua.setIdcardNo(idcardNo);
            ua.setPhoneNo(phoneNo);
            ua.setRealName(realName);
            ua.setLastPayrollCorpName(payrollCorpName);
            ua.setCorpName(empCorpName);
            ua.setLastVisitTime(simpleDateFormat.format(lastVisitTime));
            ua.setLastPayrollTime(simpleDateFormat.format(new Time(lastBatchTime)));
            ua.setSex(sex);
            ua.setRegTime(simpleDateFormat.format(new Time(regTime)));

            ua.setUserId(userId);
            ret.getUserActivities().add(ua);
        }
        return ret;
    }

    @Override
    public UserActivityResult getPayrollActivities() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("YYYY-MM-dd HH:mm:ss");

        // 手机号码->时间 ， 存储最近的时间的
        Map<String, UserActivity> userCache = new HashMap<>();

        UserActivityResult ret = new UserActivityResult();

        List<PayrollInfoEntity> payrollBatch = payrollInfoDao.findByTime(start, end);

        for (PayrollInfoEntity batch : payrollBatch) {
            Date sendTime = batch.getSendTime();
            String branCorpId = batch.getBranCorpId();
            List<PayrollDocument> byInfoId = payrollRepository.findByInfoId(batch.getId());


            BranCorporationEntity branCorp = branCorporationDao.find(branCorpId);
            String corpName = null;
            if (branCorp != null) {
                corpName = branCorp.getCorpName();
            }

            for (PayrollDocument payrollDocument : byInfoId) {

                String realName = null;
                String phoneNo = null;
                String empUserId = payrollDocument.getBranSalaryEmpUserId();
                PayrollEmpUserEntity empUser = branSalaryEmpUserDao.find(empUserId);

                if (empUser == null) {
                    System.out.println("没有找到工资单用户信息: " + empUserId);
                    continue;
                }
                else {
                    realName = empUser.getName();
                    phoneNo = empUser.getPhone();
                }
                Long createTime = empUser.getCreateTime();

                UserActivity userActivity = userCache.get(phoneNo);
                if (userActivity == null) {
                    UserActivity ua = new UserActivity();
                    ua.setRealName(realName);
                    ua.setCorpName(corpName);
                    ua.setPhoneNo(phoneNo);
                    ua.setLastPayrollTime(simpleDateFormat.format(createTime));
                    ua.setLastPayrollTimeMillis(createTime);
                    userCache.put(phoneNo, ua);
                }
                else {
                    if (createTime > userActivity.getLastPayrollTimeMillis()) {
                        userActivity.setRealName(realName);
                        userActivity.setCorpName(corpName);
                        userActivity.setPhoneNo(phoneNo);
                        userActivity.setLastPayrollTime(simpleDateFormat.format(createTime));
                        userActivity.setLastPayrollTimeMillis(createTime);
                    }
                }

            }
        }

        for (String k : userCache.keySet()) {
            ret.getUserActivities().add(userCache.get(k));
        }

        return ret;
    }
}
