package com.bumu.bran.admin.work_shift_rule.helper;

import com.bumu.arya.Utils;
import com.bumu.arya.model.entity.EmpWorkShiftTypeRelationEntity;
import com.bumu.arya.model.entity.WorkShiftRoleRelationEntity;
import com.bumu.arya.model.entity.WorkShiftRuleEntity;
import com.bumu.arya.model.entity.WorkShiftTypeRoleRelationEntity;
import com.bumu.bran.workshift.model.dao.BranEmpWorkShiftTypeRelationDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftRelationDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftRuleDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeRelationDao;
import com.bumu.common.util.ListUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author majun
 * @date 2017/11/27
 * @email 351264830@qq.com
 */
@Component
public class WorkShiftRuleHelper {

    @Autowired
    private BranWorkShiftRelationDao branWorkShiftRelationDao;
    @Autowired
    private BranWorkShiftTypeRelationDao branWorkShiftTypeRelationDao;
    @Autowired
    private BranWorkShiftRuleDao branWorkShiftRuleDao;
    @Autowired
    private BranEmpWorkShiftTypeRelationDao branEmpWorkShiftTypeRelationDao;


    // 班组
    public void copyWorkShift(WorkShiftRuleEntity dest, WorkShiftRuleEntity origin) throws Exception {
        // 查询

        // 班组与排班规律的中间表
        List<WorkShiftRoleRelationEntity> workShiftRoleRelationEntities = branWorkShiftRelationDao.findByWorkShiftRuleId(origin.getId());
        // 班次与排班规律的中间表
        List<WorkShiftTypeRoleRelationEntity> workShiftTypeRoleRelationEntities = branWorkShiftTypeRelationDao.findByRuleId(origin.getId());

        // 开始复制

        // 复制排班规则
        BeanUtils.copyProperties(dest, origin);
        // 复制排班规则之后
        dest.setId(Utils.makeUUID());
        dest.setIsPublished(0);
        branWorkShiftRuleDao.persist(dest);
        origin.setIsCache(1);
        branWorkShiftRuleDao.update(origin);

        // 复制班组
        if (!ListUtils.checkNullOrEmpty(workShiftRoleRelationEntities)) {
            for (WorkShiftRoleRelationEntity workShiftRule : workShiftRoleRelationEntities) {
                WorkShiftRoleRelationEntity newWorkShiftRule = new WorkShiftRoleRelationEntity();
                BeanUtils.copyProperties(newWorkShiftRule, workShiftRule);
                newWorkShiftRule.setId(Utils.makeUUID());
                newWorkShiftRule.setWorkShiftRule(dest);
                branWorkShiftRelationDao.persist(newWorkShiftRule);
            }
        }
        // 复制班次
        if (!ListUtils.checkNullOrEmpty(workShiftTypeRoleRelationEntities)) {
            for (WorkShiftTypeRoleRelationEntity workShiftTypeRule : workShiftTypeRoleRelationEntities) {
                WorkShiftTypeRoleRelationEntity newWorkShiftTypeRule = new WorkShiftTypeRoleRelationEntity();
                BeanUtils.copyProperties(newWorkShiftTypeRule, workShiftTypeRule);
                newWorkShiftTypeRule.setId(Utils.makeUUID());
                newWorkShiftTypeRule.setWorkShiftRule(dest);
                branWorkShiftTypeRelationDao.persist(newWorkShiftTypeRule);
            }
        }

    }

    // 个人
    public void copyEmp(WorkShiftRuleEntity dest, WorkShiftRuleEntity origin) throws Exception {
        // 查询个人
        List<EmpWorkShiftTypeRelationEntity> list = branEmpWorkShiftTypeRelationDao.findListByWorkShiftRuleId(origin.getId());
        // 复制个人
        if (!ListUtils.checkNullOrEmpty(list)) {
            for (EmpWorkShiftTypeRelationEntity empWorkShiftTypeRule : list) {
                EmpWorkShiftTypeRelationEntity newEmpWorkShiftTypeRule = new EmpWorkShiftTypeRelationEntity();
                BeanUtils.copyProperties(newEmpWorkShiftTypeRule, empWorkShiftTypeRule);
                newEmpWorkShiftTypeRule.setId(Utils.makeUUID());
                newEmpWorkShiftTypeRule.setWorkShiftRuleId(dest.getId());
                branEmpWorkShiftTypeRelationDao.persist(newEmpWorkShiftTypeRule);
            }
        }
    }
}
