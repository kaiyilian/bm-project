package com.bumu.bran.admin.employee_defined.helper;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.command.UserDefinedDetailsCommand;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.employee.model.dao.UserDefinedColsDao;
import com.bumu.bran.employee.model.dao.UserDefinedDetailsDao;
import com.bumu.bran.employee.model.entity.UserDefinedColsEntity;
import com.bumu.bran.employee.model.entity.UserDefinedDetailsEntity;
import com.bumu.bran.employee.result.EmployeeExportNewResult;
import com.bumu.bran.employee.result.UserDefinedDetailsResult;
import com.bumu.bran.employee.service.impl.EmpExcelExportServiceImpl;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author majun
 * @date 2016/11/30
 */
@Component
public class UserDefinedDetailsHelper {
    private Logger logger = LoggerFactory.getLogger(UserDefinedDetailsHelper.class);

    @Autowired
    private UserDefinedDetailsDao userDefinedDetailsDao;

    @Autowired
    private UserDefinedColsDao userDefinedColsDao;

    @Autowired
    private LeaveEmployeeDao leaveEmployeeDao;

    @Autowired
    private EmpExcelExportServiceImpl empExcelHandler;


    public void createUserDefinedDetails(List<UserDefinedDetailsCommand> userDefinedComponentList) {
        for (UserDefinedDetailsCommand userDefinedComponent : userDefinedComponentList) {
            UserDefinedDetailsEntity entity = new UserDefinedDetailsEntity();
            userDefinedComponent.setUserDefinedColsEntity(userDefinedColsDao.findByIdNotDelete(userDefinedComponent.getColsId()));
            entity.createBefore(userDefinedComponent);
            logger.debug("创建自定义类: " + " id " + entity.getId() + ", name " + entity.getUserDefinedColsEntity().getColName() +
                    "value: " + entity.getColValue());
            userDefinedDetailsDao.create(entity);
        }
    }

    public void updateUserDefinedDetailsForLeave(ModelCommand modelCommand, LeaveEmployeeEntity leaveEmployeeEntity) {
        Criteria criteria = userDefinedDetailsDao.findByEmpId(modelCommand);
        List<UserDefinedDetailsEntity> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return;
        }

        for (UserDefinedDetailsEntity entity : list) {
            entity.setEmployeeEntity(null);
            entity.setLeaveEmployeeEntity(leaveEmployeeEntity);
            userDefinedDetailsDao.update(entity);
        }
    }

    public List<UserDefinedDetailsResult> getUserDefinedColsForEmp(ModelCommand modelCommand) {
        return setResult(userDefinedDetailsDao.findByEmpId(modelCommand));
    }

    public List<UserDefinedDetailsResult> getUserDefinedColsForLeave(ModelCommand modelCommand) {
        return setResult(userDefinedDetailsDao.findByLeaveId(modelCommand));
    }

    private List<UserDefinedDetailsResult> setResult(Criteria criteria) {
        List<UserDefinedDetailsEntity> list = criteria.list();
        List<UserDefinedDetailsResult> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        for (UserDefinedDetailsEntity entity : list) {
            UserDefinedDetailsResult definedDetailsResult = new UserDefinedDetailsResult();
            definedDetailsResult.setDetailsId(entity.getId());
            definedDetailsResult.setColName(entity.getUserDefinedColsEntity().getColName());
            definedDetailsResult.setColValue(entity.getColValue());
            definedDetailsResult.setColsId(entity.getUserDefinedColsEntity().getId());
            definedDetailsResult.setType(entity.getUserDefinedColsEntity().getType());
            if (definedDetailsResult.getColValue() != null && definedDetailsResult.getType() != null && definedDetailsResult.getType() == 2) {
                try {
                    definedDetailsResult.setColValue(SysUtils.getDateStringFormTimestamp(Long.valueOf(entity.getColValue())));
                } catch (NumberFormatException n) {
                    logger.error("离职员工花名册自定义字段日期转换错误, detailsId: " + definedDetailsResult.getDetailsId());
                    definedDetailsResult.setColValue(null);
                }

            }
            result.add(definedDetailsResult);
        }
        return result;
    }

    public void checkUpdate(List<UserDefinedDetailsResult> userDefinedDetailsResultList, UserDefinedCommand command,
                            EmployeeEntity employeeEntity, LinkedHashMap<String, String> headersParams, EmployeeExportNewResult employeeExportResult) throws Exception {
        List<String> db = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        Criteria criteria = userDefinedColsDao.findByCorpId(command);
        List<UserDefinedColsEntity> userDefinedColsEntities = criteria.list();
        int i = 0;
        empExcelHandler.setDynamicParams(headersParams, userDefinedColsEntities, i);
        for (UserDefinedColsEntity userDefinedColsEntity : userDefinedColsEntities) {
            db.add(userDefinedColsEntity.getId());
        }
        for (UserDefinedDetailsResult userDefinedDetailsResult : userDefinedDetailsResultList) {
            cur.add(userDefinedDetailsResult.getColsId());
        }
        logger.debug("比对之前: ...");
        logger.debug("db: " + db.toString());
        logger.debug("cur: " + cur.toString());
        db.removeAll(cur);
        logger.debug("对比之后: ...");
        logger.debug("db: " + db.toString());
        List<UserDefinedColsEntity> list = new ArrayList<>();
        if (!db.isEmpty()) {
//			empExcelHandler.setDynamicParams(map, list);
            for (String id : db) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(command.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setEmployeeEntity(employeeEntity);
                UserDefinedColsEntity userDefinedColsEntity = userDefinedColsDao.findByIdNotDelete(id);
                logger.debug("对比之后的:colEntity: " + userDefinedColsEntity.getId() + " , " + userDefinedColsEntity.getColName());
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                userDefinedDetailsResult.setType(userDefinedColsEntity.getType());
                list.add(userDefinedColsEntity);
                userDefinedDetailsResultList.add(0, userDefinedDetailsResult);
            }
        }
        empExcelHandler.setDynamicData(userDefinedDetailsResultList, employeeExportResult, 0);
    }

    public void checkUpdate(List<UserDefinedDetailsResult> userDefinedDetailsResultList, UserDefinedCommand command,
                            LeaveEmployeeEntity employeeEntity, LinkedHashMap<String, String> headersParams, EmployeeExportNewResult employeeExportResult) throws Exception {
        List<String> db = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        Criteria criteria = userDefinedColsDao.findByCorpId(command);
        List<UserDefinedColsEntity> userDefinedColsEntities = criteria.list();
        int i = 0;
        empExcelHandler.setDynamicParams(headersParams, userDefinedColsEntities, i);
        for (UserDefinedColsEntity userDefinedColsEntity : userDefinedColsEntities) {
            db.add(userDefinedColsEntity.getId());
        }
        for (UserDefinedDetailsResult userDefinedDetailsResult : userDefinedDetailsResultList) {
            cur.add(userDefinedDetailsResult.getColsId());
        }
        logger.debug("比对之前: ...");
        logger.debug("db: " + db.toString());
        logger.debug("cur: " + cur.toString());
        db.removeAll(cur);
        logger.debug("对比之后: ...");
        logger.debug("db: " + db.toString());
        List<UserDefinedColsEntity> list = new ArrayList<>();
        if (!db.isEmpty()) {
//			empExcelHandler.setDynamicParams(map, list);
            for (String id : db) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(command.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setLeaveEmployeeEntity(employeeEntity);
                UserDefinedColsEntity userDefinedColsEntity = userDefinedColsDao.findByIdNotDelete(id);
                logger.debug("对比之后的:colEntity: " + userDefinedColsEntity.getId() + " , " + userDefinedColsEntity.getColName());
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                list.add(userDefinedColsEntity);
                userDefinedDetailsResultList.add(0, userDefinedDetailsResult);
            }
        }
        empExcelHandler.setDynamicData(userDefinedDetailsResultList, employeeExportResult, 0);
    }


    public void checkUpdate(List<UserDefinedDetailsResult> userDefinedDetailsResultList, UserDefinedCommand command, LeaveEmployeeEntity employeeEntity) {
        List<String> db = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        Criteria criteria = userDefinedColsDao.findByCorpId(command);
        for (UserDefinedColsEntity userDefinedColsEntity : (List<UserDefinedColsEntity>) criteria.list()) {
            db.add(userDefinedColsEntity.getId());
        }

        for (UserDefinedDetailsResult userDefinedDetailsResult : userDefinedDetailsResultList) {
            cur.add(userDefinedDetailsResult.getColsId());
        }
        logger.debug("比对之前: ...");
        logger.debug("db: " + db.toString());
        logger.debug("cur: " + cur.toString());
        db.removeAll(cur);
        logger.debug("对比之后: ...");
        logger.debug("db: " + db.toString());
        if (!db.isEmpty()) {
            for (String id : db) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(command.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setLeaveEmployeeEntity(employeeEntity);
                UserDefinedColsEntity userDefinedColsEntity = userDefinedColsDao.findByIdNotDelete(id);
                logger.debug("对比之后的:colEntity: " + userDefinedColsEntity.getId() + " , " + userDefinedColsEntity.getColName());
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                userDefinedDetailsResultList.add(userDefinedDetailsResult);
            }
        }
    }
}

