package com.bumu.bran.admin.work_shift_rule.service.impl;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.WorkShiftRuleEntity;
import com.bumu.arya.model.entity.WorkShiftTypeRoleRelationEntity;
import com.bumu.bran.admin.work_shift_rule.service.BranNewScheduleService;
import com.bumu.bran.workshift.command.NewWorkShiftRuleCommand;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeRelationDao;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.bran.workshift.result.WorkShiftTypeResult;
import com.bumu.bran.service.impl.BranCommonScheduleServiceImpl;
import com.bumu.common.constant.Constants;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Service
public class BranNewScheduleServiceImpl extends BranCommonScheduleServiceImpl implements BranNewScheduleService {

    private Logger logger = LoggerFactory.getLogger(BranNewScheduleServiceImpl.class);

    @Autowired
    private BranWorkShiftTypeRelationDao branWorkShiftTypeRelationDao;




    @Override
    public List<WorkShiftRuleDetailResult> getNewRuleDetails(String id) {
        List<WorkShiftRuleDetailResult> results = new ArrayList<>();
        WorkShiftRuleDetailResult shiftRuleDetailResult = null;
        List<WorkShiftTypeResult> workShiftTypes = null;
        WorkShiftTypeResult workShiftTypeResult = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //得到该公司对应的排版规律
        List<WorkShiftRuleEntity> list = branWorkShiftRuleDao.findByCorpIdAndCacheStatus(id, 0);
        if (list != null) {
            logger.info(String.format("查询到 %s 排版规律", list.size()));
        } else {
            logger.info(String.format("查询到 %s 排版规律", 0));
        }
        //查询盖排版规律下每个规律对应的班次信息
        for (WorkShiftRuleEntity workShiftRuleEntity : list) {
            shiftRuleDetailResult = new WorkShiftRuleDetailResult();
            workShiftTypes = new ArrayList<>();
            List<WorkShiftTypeRoleRelationEntity> byRuleId = branWorkShiftTypeRelationDao.findByRuleId(workShiftRuleEntity.getId());
            //组装排版规律的视图
            for (WorkShiftTypeRoleRelationEntity workShiftTypeRoleRelationEntity : byRuleId) {
                workShiftTypeResult = new WorkShiftTypeResult();
                workShiftTypeResult.setColor(workShiftTypeRoleRelationEntity.getWorkShiftType().getColor());
                workShiftTypeResult.setId(workShiftTypeRoleRelationEntity.getWorkShiftType().getId());
                workShiftTypeResult.setShortName(workShiftTypeRoleRelationEntity.getWorkShiftType().getShortName());
                workShiftTypes.add(workShiftTypeResult);
                shiftRuleDetailResult.setWorkShiftTypes(workShiftTypes);
            }
            //排版开始时间
            Date startTime = null;
            startTime = workShiftRuleEntity.getExecuteTime();
            Date endTime = workShiftRuleEntity.getExecuteEndTime();
            if (startTime != null) {

                shiftRuleDetailResult.setExecuteTime(startTime.getTime());
            }

            //老数据问题处理  开始时间 结束时间都为空 则是老数据
            if (startTime==null&&endTime==null) {
                shiftRuleDetailResult.setIsLoopAround(1);
                shiftRuleDetailResult.setExecuteEndTime(null);
            }else if(startTime!=null){
                if (workShiftRuleEntity.getLoopAround()) {
                    shiftRuleDetailResult.setIsLoopAround(1);
                    shiftRuleDetailResult.setExecuteEndTime(null);
                }else{
                    if (endTime==null) {
                        throw  new AryaServiceException(ErrorCode.CODE_SYS_ERR,"非一直循环，结束时间为空");
                    }
                    shiftRuleDetailResult.setIsLoopAround(0);
                        shiftRuleDetailResult.setExecuteEndTime(endTime.getTime());

                }

            }

            //排版规律的id
            shiftRuleDetailResult.setId(workShiftRuleEntity.getId());
            //排版的周期
            shiftRuleDetailResult.setCycle(workShiftRuleEntity.getCycle());
            //是否启用
            if (workShiftRuleEntity.getOpen() == null) {
                //如果为空 则是老数据  如果为 false 则是 关闭  如果为 true 开启
                shiftRuleDetailResult.setIsUse(1);
            } else {
                if (workShiftRuleEntity.getOpen()) {//排版规律开启
                    shiftRuleDetailResult.setIsUse(1);
                } else {//排版版规律关闭
                    shiftRuleDetailResult.setIsUse(0);
                }

            }

            //排版的名称
            String name = workShiftRuleEntity.getName();
            if (StringUtils.isBlank(name)) {
                name = "排版规律";
            }
            shiftRuleDetailResult.setName(name);
            results.add(shiftRuleDetailResult);
        }
        return results;
    }

    @Override
    public List<WorkShiftTypeResult> getNewRuleType(NewWorkShiftRuleCommand newWorkShiftRuleCommand) {

        String id = newWorkShiftRuleCommand.getRuleId();
        List<WorkShiftTypeResult> workShiftTypes = null;
        WorkShiftTypeResult workShiftTypeResult = null;


        //得到该公司对应的排版规律
        WorkShiftRuleEntity shiftRuleEntity = branWorkShiftRuleDao.findByWorkShiftIdForApp(id);

        //查询盖排版规律下每个规律对应的班次信息
        workShiftTypes = new ArrayList<>();
        List<WorkShiftTypeRoleRelationEntity> byRuleId = branWorkShiftTypeRelationDao.findByRuleId(shiftRuleEntity.getId());
        //组装排版规律的视图
        for (WorkShiftTypeRoleRelationEntity workShiftTypeRoleRelationEntity : byRuleId) {
            workShiftTypeResult = new WorkShiftTypeResult();
            workShiftTypeResult.setColor(workShiftTypeRoleRelationEntity.getWorkShiftType().getColor());
            workShiftTypeResult.setId(workShiftTypeRoleRelationEntity.getWorkShiftType().getId());
            workShiftTypeResult.setShortName(workShiftTypeRoleRelationEntity.getWorkShiftType().getShortName());
            workShiftTypes.add(workShiftTypeResult);
        }
        return workShiftTypes;
    }


    @Override
    public Boolean setUseRule(String id, int isUse) {
        logger.info("设置排版规律状态的id--->  " + id);

        try {
            WorkShiftRuleEntity byWorkShiftIdForApp = branWorkShiftRuleDao.findByWorkShiftIdForApp(id);
            if (0 == isUse) {
                byWorkShiftIdForApp.setOpen(false);
                logger.info("启用排版规律状态是--->  关闭");

            } else if (1 == isUse) {
                logger.info("启用排版规律状态是--->  开启");
                byWorkShiftIdForApp.setOpen(true);
            }
            branWorkShiftRuleDao.update(byWorkShiftIdForApp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "排版状态设置异常");
        }
    }

    @Override
    public Boolean closeRule(String id) {
        logger.info("删除排班的id是----> " + id);
        try {
            WorkShiftRuleEntity byWorkShiftIdForApp = branWorkShiftRuleDao.findByWorkShiftIdForApp(id);
            byWorkShiftIdForApp.setIsDelete(Constants.TRUE);
            branWorkShiftRuleDao.update(byWorkShiftIdForApp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "排版删除失败");
        }

    }
}
