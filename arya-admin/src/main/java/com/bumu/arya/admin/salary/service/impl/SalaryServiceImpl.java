package com.bumu.arya.admin.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.salary.result.SalaryImportResult;
import com.bumu.arya.admin.salary.result.SalaryImportResult.SalaryErrorMsgBean;
import com.bumu.arya.admin.salary.result.SalaryImportResult.SalaryOutputBean;
import com.bumu.arya.admin.salary.service.SalaryCalculateService;
import com.bumu.arya.admin.salary.service.SalaryService;
import com.bumu.arya.common.Constants;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.arya.model.*;
import com.bumu.arya.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.bumu.SysUtils.turnStringToBigDecimal;

/**
 * Created by bumu-zhz on 2015/11/6.
 */
@Service
public class SalaryServiceImpl extends BaseBumuService implements SalaryService {
    Logger log = LoggerFactory.getLogger(SalaryServiceImpl.class);


    /**
     * 用户Dao
     */
    @Autowired
    private AryaUserDao aryaUserDao;

    /**
     * 公司Dao
     */
    @Autowired
    private CorporationDao corporationDao;

    /**
     * 薪资Dao
     */
    @Autowired
    private AryaSalaryDao aryaSalaryDao;

    @Autowired
    SalaryCalculateService salaryCalculateService;

    @Override
    public List<AryaSalaryEntity> select() {
        return null;
    }

    /**
     * 创建薪资记录，如果用户不存在时就创建新的用户
     *
     * @param list
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<SalaryErrorMsgBean> insert(String companyId, List<SalaryOutputBean> list) {
        List<SalaryErrorMsgBean> errorMsgBeans = new ArrayList<>();

        CorporationEntity corporationEntity = corporationDao.findCorporationById(companyId);
        if (corporationEntity == null) {
            SalaryErrorMsgBean bean = new SalaryErrorMsgBean();
            bean.setMsg("公司不存在");
            errorMsgBeans.add(bean);
        } else {
            if (list != null && list.size() > 0) {
                checkInsert(list, errorMsgBeans);//导入数据check
                if (errorMsgBeans.size() == 0) {
                    //更新公司信息  是否不能删除，0为否，1为是
                    if (corporationEntity.getMandatory() != 1) {
                        corporationEntity.setMandatory(1);
                        corporationDao.createOrUpdate(corporationEntity);
                    }

                    List<AryaSalaryEntity> salaryList = new ArrayList<>();
                    for (SalaryOutputBean bean : list) {
                        //获取记录所属用户，如果不存在就创建新的用户
                        AryaUserEntity userEntity = salaryOwnUser(bean, corporationEntity);

                        Date date = DateTimeUtils.checkMonth(bean.getMonth());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int year = calendar.get(Calendar.YEAR);//年份
                        int month = calendar.get(Calendar.MONTH) + 1;//月份

                        AryaSalaryEntity salaryEntity = new AryaSalaryEntity();
                        // 生成薪资id
                        String newPlayerId = Utils.makeUUID();
                        salaryEntity.setId(newPlayerId);

                        salaryEntity.setUserId(userEntity.getId());//用户ID
                        salaryEntity.setYear(year);//年份
                        salaryEntity.setMonth(month);//月份
                        salaryEntity.setWage(turnStringToBigDecimal(bean.getBeanSalary()));//基本工资
                        salaryEntity.setGrossSalary(turnStringToBigDecimal(bean.getGrossSalary()));//应发工资
                        salaryEntity.setNetSalary(turnStringToBigDecimal(bean.getNetSalary()));//实发工资
                        salaryEntity.setRestDayOvertimeHours(bean.getRestDayOvertimeHours() == null ? 0 : Integer.parseInt(bean.getRestDayOvertimeHours()));//休息日加班时长
                        salaryEntity.setLegalHolidayOvertimeHours(bean.getLegalHolidayOvertimeHours() == null ? 0 : Integer.parseInt(bean.getLegalHolidayOvertimeHours()));//法定节假日加班时长
                        salaryEntity.setTotalWorkdayHours(bean.getWorkdayHours() == null ? 0 : Integer.parseInt(bean.getWorkdayHours()));//工作日总出勤小时数
                        salaryEntity.setTotalOvertimeHours(bean.getOvertimeHours() == null ? 0 : Integer.parseInt(bean.getOvertimeHours()));//加班小时数
                        salaryEntity.setWorkdayOvertimeSalary(turnStringToBigDecimal(bean.getWorkdayOvertimeSalary()));//工作日加班工资
                        salaryEntity.setRestdayOvertimeSalary(turnStringToBigDecimal(bean.getRestDayOvertimeSalary()));//休息日加班工资
                        salaryEntity.setLegalHolidayOvertimeSalary(turnStringToBigDecimal(bean.getLegalHolidayOvertimeSalary()));//法定节假日加班工资
                        salaryEntity.setPerformanceBonus(turnStringToBigDecimal(bean.getPerformanceBonus()));//绩效奖金
                        salaryEntity.setSubsidy(turnStringToBigDecimal(bean.getSubsidy()));//津贴
                        salaryEntity.setCasualLeaveCut(turnStringToBigDecimal(bean.getCasualLeaveCut()));//事假扣款
                        salaryEntity.setCasualLeaveDays(bean.getCasualLeaveDays() == null ? 0 : Integer.parseInt(bean.getCasualLeaveDays()));//事假天数
                        salaryEntity.setSickLeaveCut(turnStringToBigDecimal(bean.getSickLeaveCut()));//病假扣款
                        salaryEntity.setSickLeaveDays(bean.getSickLeaveDays() == null ? 0 : Integer.parseInt(bean.getSickLeaveDays()));//病假天数
                        salaryEntity.setOtherCut(turnStringToBigDecimal(bean.getOtherCut()));//其他扣款
                        salaryEntity.setSoinPersonal(turnStringToBigDecimal(bean.getSoinPersonal()));//个人社保
                        salaryEntity.setHouseFundPersonal(turnStringToBigDecimal(bean.getFundPersonal()));//个人公积金
                        salaryEntity.setTaxableSalary(turnStringToBigDecimal(bean.getTaxableSalary()));//应税工资
                        salaryEntity.setPersonalTax(turnStringToBigDecimal(bean.getPersonalTax()));//个税
                        salaryEntity.setRepayment(turnStringToBigDecimal(bean.getRepayment()));//补款
                        salaryEntity.setDelete(false);//是否删除
                        salaryEntity.setCreateTime(System.currentTimeMillis());//创建时间

                        salaryList.add(salaryEntity);
                    }
                    aryaSalaryDao.create(salaryList);
                }
            }
        }
        return errorMsgBeans;
    }

    /**
     * 导入check，判断用户是否已经导入
     *
     * @param list
     * @param errorMsgBeans
     */
    private void checkInsert(List<SalaryImportResult.SalaryOutputBean> list, List<SalaryImportResult.SalaryErrorMsgBean> errorMsgBeans) {
        if (list != null && list.size() > 0 && errorMsgBeans != null) {
            for (SalaryImportResult.SalaryOutputBean bean : list) {
                List<AryaUserEntity> userEntities = aryaUserDao.selectByCard(bean.getCardId(), bean.getRealName());
                if (userEntities != null && userEntities.size() > 0) {
                    AryaUserEntity userEntity = userEntities.get(0);

                    Date date = DateTimeUtils.checkMonth(bean.getMonth());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);//年份
                    int month = calendar.get(Calendar.MONTH) + 1;//月份

                    List<AryaSalaryEntity> salaryEntityList = aryaSalaryDao.findSalarysByUserMonth(userEntity.getId(), year, month);
                    if (salaryEntityList != null && salaryEntityList.size() > 0) {
                        SalaryImportResult.SalaryErrorMsgBean errorMsgBean = new SalaryImportResult.SalaryErrorMsgBean();
                        errorMsgBean.setMsg(userEntity.getRealName() + "的薪资信息已经导入过系统");

                        errorMsgBeans.add(errorMsgBean);
                    }
                }
            }
        }
    }


    /**
     * 获取记录所属用户，如果不存在就创建新的用户
     *
     * @param bean
     * @return
     */
    private AryaUserEntity salaryOwnUser(SalaryImportResult.SalaryOutputBean bean, CorporationEntity corporationEntity) {
        List<AryaUserEntity> userEntities = aryaUserDao.selectByCard(bean.getCardId(), bean.getRealName());
        AryaUserEntity userEntity = null;
        if (userEntities != null && userEntities.size() > 0) {
            userEntity = userEntities.get(0);
        }
        if (userEntity == null) {
            userEntity = new AryaUserEntity();
            // 先生成用户 GUID 给交易系统
            String newPlayerId = Utils.makeUUID();
            userEntity.setId(newPlayerId);//主键ID
            userEntity.setCorporationId(corporationEntity.getId());//公司ID
            userEntity.setNickName("招才进宝");//昵称，可选
            userEntity.setPwd(SysUtils.encryptPassword("123456"));//密码，MD5加密
            userEntity.setPhoneNo(bean.getPhone());//手机号码，唯一
            userEntity.setRealName(bean.getRealName());//真实姓名
            userEntity.setGender("1");//性别，1：男，2：女
            userEntity.setCreateTime(System.currentTimeMillis());//创建时间
            userEntity.setIdcardNo(bean.getCardId());//用户身份证号码，１５位或者１８位都可以
            userEntity.setCreateType(Constants.CREATE_TYPE_IMPORT);//创建类型，1表示自行注册，2表示批量导入

            aryaUserDao.create(userEntity);
        }
        return userEntity;

    }

}
