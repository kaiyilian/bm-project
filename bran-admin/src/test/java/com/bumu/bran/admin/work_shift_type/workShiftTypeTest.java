package com.bumu.bran.admin.work_shift_type;

import com.bumu.arya.model.entity.*;
import com.bumu.bran.attendance.engine.WorkAttendanceGetRecordsHelper;
import com.bumu.bran.attendance.model.dao.WorkAttendanceDao;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.model.entity.attendance.WorkAttendanceEntity;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.bran.workshift.command.WorkShiftRuleQueryCommand;
import com.bumu.bran.workshift.model.dao.*;
import com.bumu.employee.model.entity.EmployeeEntity;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/11/29
 * @email 351264830@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:common/test_common_spring.xml",
        "classpath*:ac_datasource.xml",
        "classpath:common/test_common_trx.xml",
        "classpath*:ac_mybatis.xml",
        "classpath*:ac_mongodb.xml",
        "classpath:common/test_common_schedule.xml",
        "classpath*:ac_common_sms.xml",
        "classpath:common/test_common_captcha.xml"
})
@Transactional
public class workShiftTypeTest {

    private static Logger logger = LoggerFactory.getLogger(workShiftTypeTest.class);

    @Autowired
    private BranCorporationDao branCorporationDao;
    @Autowired
    private WorkShiftDao workShiftDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;
    @Autowired
    private BranWorkShiftRuleDao branWorkShiftRuleDao;
    @Autowired
    private BranWorkShiftRelationDao branWorkShiftRelationDao;
    @Autowired
    private BranWorkShiftTypeRelationDao branWorkShiftTypeRelationDao;
    @Autowired
    private WorkAttendanceGetRecordsHelper workAttendanceGetRecordsHelper;
    @Autowired
    private BranEmpWorkShiftTypeRelationDao branEmpWorkShiftTypeRelationDao;
    @Autowired
    private WorkAttendanceDao workAttendanceDao;

    @Test
    @Rollback(false)
    public void init() {
        // 创建公司
        BranCorporationEntity branCorporationEntity = new BranCorporationEntity();
        branCorporationEntity.setId("branCorporationEntity01");
        branCorporationEntity.setCorpName("马俊的测试公司01");
        branCorporationEntity.setCheckinCode("123456");
        branCorporationDao.createOrUpdate(branCorporationEntity);
        // 创建班组
        WorkShiftEntity workShiftEntity = new WorkShiftEntity();
        workShiftEntity.setId("WorkShiftEntity01");
        workShiftEntity.setShiftName("马俊的测试班组01");
        workShiftEntity.setBranCorpId(branCorporationEntity.getId());
        workShiftDao.createOrUpdate(workShiftEntity);
        // 创建员工
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId("EmployeeEntity01");
        employeeEntity.setRealName("马俊的测试正式员工01");
        employeeEntity.setWorkShiftId(workShiftEntity.getId());
        employeeDao.createOrUpdate(employeeEntity);
        // 创建班次
        WorkShiftTypeEntity day = new WorkShiftTypeEntity();
        day.setId("WorkShiftTypeEntity01");
        day.setBranCorp(branCorporationEntity);
        day.setName("马俊的测试班次01");
        day.setShortName("马1");
        day.setWorkStartTime("08:00");
        day.setWorkEndTime("17:00");
        branWorkShiftTypeDao.createOrUpdate(day);
        WorkShiftTypeEntity mid = new WorkShiftTypeEntity();
        mid.setId("WorkShiftTypeEntityMaJunTest02");
        mid.setBranCorp(branCorporationEntity);
        mid.setName("马俊的测试班次02");
        mid.setShortName("马2");
        mid.setWorkStartTime("18:00");
        mid.setWorkEndTime("23:00");
        branWorkShiftTypeDao.createOrUpdate(mid);
        // 创建排班规律
        WorkShiftRuleEntity workShiftRuleEntity = new WorkShiftRuleEntity();
        workShiftRuleEntity.setId("workShiftRuleEntity01");
        workShiftRuleEntity.setIsPublished(1);
        workShiftRuleEntity.setName("马俊的测试规律01");
        workShiftRuleEntity.setIsCache(0);
        workShiftRuleEntity.setBranCorp(branCorporationEntity);
        workShiftRuleEntity.setCycle(2);
        workShiftRuleEntity.setExecuteTime(new DateTime(2017, 12, 01, 0, 0, 0).toDate());
        workShiftRuleEntity.setExecuteEndTime(new DateTime(2017, 12, 10, 0, 0, 0).toDate());
        workShiftRuleEntity.setIsCache(0);
        workShiftRuleEntity.setOpen(true);
        branWorkShiftRuleDao.createOrUpdate(workShiftRuleEntity);
        // 创建排班规律班组中间表
        WorkShiftRoleRelationEntity workShiftRoleRelationEntity = new WorkShiftRoleRelationEntity();
        workShiftRoleRelationEntity.setId("workShiftRoleRelationEntity01");
        workShiftRoleRelationEntity.setWorkShiftRule(workShiftRuleEntity);
        workShiftRoleRelationEntity.setWorkShift(workShiftEntity);
        workShiftRoleRelationEntity.setExecuteTime(null);
        branWorkShiftRelationDao.createOrUpdate(workShiftRoleRelationEntity);
        // 创建排班规律班次中间表
        WorkShiftTypeRoleRelationEntity workShiftTypeRoleRelationEntity = new WorkShiftTypeRoleRelationEntity();
        workShiftTypeRoleRelationEntity.setId("workShiftTypeRoleRelationEntity1");
        workShiftTypeRoleRelationEntity.setWorkShiftRule(workShiftRuleEntity);
        workShiftTypeRoleRelationEntity.setWorkShiftType(day);
        workShiftTypeRoleRelationEntity.setOrder(1);
        branWorkShiftTypeRelationDao.createOrUpdate(workShiftTypeRoleRelationEntity);

        WorkShiftTypeRoleRelationEntity workShiftTypeRoleRelationEntity02 = new WorkShiftTypeRoleRelationEntity();
        workShiftTypeRoleRelationEntity02.setId("workShiftTypeRoleRelationEntity2");
        workShiftTypeRoleRelationEntity02.setWorkShiftRule(workShiftRuleEntity);
        workShiftTypeRoleRelationEntity02.setWorkShiftType(mid);
        workShiftTypeRoleRelationEntity02.setOrder(2);
        branWorkShiftTypeRelationDao.createOrUpdate(workShiftTypeRoleRelationEntity02);
    }


    @Test
    public void getTypeNotPublished() {
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete("EmployeeEntity01");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(employeeEntity.getBranCorpId());
        workShiftRuleQueryCommand.setPublishedState(0);
        workShiftRuleQueryCommand.setQueryDate(new DateTime(2017, 12, 1, 0, 0, 0).getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());
        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        Assert.assertNull(workShiftTypeEntity);
    }

    @Test
    public void getTypePublishedFirstDay() {
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete("EmployeeEntity01");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(employeeEntity.getBranCorpId());
        workShiftRuleQueryCommand.setPublishedState(1);
        workShiftRuleQueryCommand.setQueryDate(new DateTime(2017, 12, 1, 0, 0, 0).getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());
        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        Assert.assertEquals("马1", workShiftTypeEntity.getShortName());
    }

    @Test
    public void getTypePublishedLastDay() {
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete("EmployeeEntity01");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(employeeEntity.getBranCorpId());
        workShiftRuleQueryCommand.setPublishedState(1);
        workShiftRuleQueryCommand.setQueryDate(new DateTime(2017, 12, 10, 0, 0, 0).getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());
        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        Assert.assertEquals("马2", workShiftTypeEntity.getShortName());
    }

    @Test
    @Rollback(value = false)
    public void addPersonWorkShiftType(){
        // 增加个人修改
        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete("branCorporationEntity01");

        // 创建班次
        WorkShiftTypeEntity day = new WorkShiftTypeEntity();
        day.setId("WorkShiftTypeEntityPerson01");
        day.setBranCorp(branCorporationDao.findByIdNotDelete("branCorporationEntity01"));
        day.setName("马俊的个人修改测试班次01");
        day.setShortName("个1");
        day.setWorkStartTime("08:00");
        day.setWorkEndTime("17:00");

        branWorkShiftTypeDao.persist(day);

        EmpWorkShiftTypeRelationEntity empWorkShiftTypeRelationEntity = new EmpWorkShiftTypeRelationEntity();
        empWorkShiftTypeRelationEntity.setId("empWorkShiftTypeRelationEntity01");
        empWorkShiftTypeRelationEntity.setWorkShiftTypeId("WorkShiftTypeEntityPerson01");
        empWorkShiftTypeRelationEntity.setBranCorpId(branCorporationEntity.getId());
        empWorkShiftTypeRelationEntity.setModifyDate(new DateTime(2017, 12, 10, 0, 0, 0).toDate());
        empWorkShiftTypeRelationEntity.setWorkShiftRuleId("workShiftRuleEntity01");
        empWorkShiftTypeRelationEntity.setEmpId("EmployeeEntity01");
        branEmpWorkShiftTypeRelationDao.persist(empWorkShiftTypeRelationEntity);


    }

    @Test
    @Rollback(value = false)
    public void addWorkAttendWorkShiftType(){
        // 创建班次
        WorkShiftTypeEntity day = new WorkShiftTypeEntity();
        day.setId("WorkShiftTypeEntityPerson02");
        day.setBranCorp(branCorporationDao.findByIdNotDelete("branCorporationEntity01"));
        day.setName("马俊的考勤测试班次01");
        day.setShortName("考1");
        day.setWorkStartTime("08:00");
        day.setWorkEndTime("17:00");

        branWorkShiftTypeDao.persist(day);

        WorkAttendanceEntity workAttendanceEntity = new WorkAttendanceEntity();
        workAttendanceEntity.setId("workAttendanceEntityTest01");
        workAttendanceEntity.setEmpId("EmployeeEntity01");
        workAttendanceEntity.setWorkShiftTypeId("WorkShiftTypeEntityPerson02");
        workAttendanceEntity.setAttendDay(new DateTime(2017, 12, 10, 0, 0, 0).toDate());
        workAttendanceDao.persist(workAttendanceEntity);
    }

    @Test
    public void getPersonWorkShiftType(){
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete("EmployeeEntity01");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(employeeEntity.getBranCorpId());
        workShiftRuleQueryCommand.setPublishedState(1);
        workShiftRuleQueryCommand.setQueryDate(new DateTime(2017, 12, 10, 0, 0, 0).getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());
        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        Assert.assertEquals("个1", workShiftTypeEntity.getShortName());
    }

    @Test
    public void getWorkAttendType(){
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete("EmployeeEntity01");
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(employeeEntity.getBranCorpId());
        workShiftRuleQueryCommand.setPublishedState(1);
        workShiftRuleQueryCommand.setQueryDate(new DateTime(2017, 12, 10, 0, 0, 0).getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());
        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        Assert.assertEquals("考1", workShiftTypeEntity.getShortName());
    }
}
