package com.bumu.arya.admin.salary.service;

import com.bumu.arya.admin.salary.controller.command.CreateSalaryCalculateRuleCommand;
import com.bumu.arya.admin.salary.controller.command.QueryCalculateSalary2ModelsCommand;
import com.bumu.arya.admin.salary.controller.command.UpdateSalaryCalculateRuleCommand;
import com.bumu.arya.admin.salary.controller.command.UpdateSalaryUserInfoCommand;
import com.bumu.arya.admin.salary.model.CorpSalaryStatisticsStructure;
import com.bumu.arya.admin.salary.model.SalaryCalculateRuleGearModel;
import com.bumu.arya.admin.salary.model.SalaryCalculateRuleModel;
import com.bumu.arya.admin.salary.result.*;
import com.bumu.arya.model.entity.AryaSalaryEntity;
import com.bumu.arya.model.entity.AryaSalaryWeekEntity;
import com.bumu.arya.admin.salary.model.entity.SalaryRuleEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.admin.salary.model.SalaryCalculateModel;
import com.bumu.arya.admin.salary.model.SalaryModel;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/6/2
 */
@Transactional
public interface SalaryCalculateService {

    /**
     * 计算薪资
     *
     * @param groupCorpId
     * @param departmentId
     * @param year
     * @param month
     * @param week
     * @param fileName
     * @return
     * @throws AryaServiceException
     */
    @Transactional(readOnly = true)
    SalaryCalculateListResult calculateSalary(String groupCorpId, String departmentId, int year, int month, Integer week, String fileName, Integer page, Integer pageSize) throws AryaServiceException;


    /**
     * 计算薪资结果为模型
     *
     * @param taxableSalary
     * @return
     */
    SalaryCalculateModel calculateSalaryToModel(BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel);

    /**
     * 导入薪资
     *
     * @param userId
     * @param groupId
     * @param year
     * @param month
     * @param week
     * @param fileName
     * @return
     * @throws AryaServiceException
     */
    SalaryCalculateImportResult importCalculateSalary(String userId, String groupId, String departmentId, int year, int month, Integer week, String fileName) throws AryaServiceException;


    /**
     * 查询薪资
     *
     * @param groupId
     * @param year
     * @param month
     * @param week
     * @return
     * @throws AryaServiceException
     */
    SalaryCalculateListResult queryCalculateSalary(String groupId, String departmentId, int year, int month, Integer week, String keyWord, Integer page, Integer pageSize) throws AryaServiceException;

    /**
     * 查询薪资成SalaryModel
     *
     *
     * @param queryCalculateSalary2ModelsCommand@return
     * @throws AryaServiceException
     */
    List<SalaryModel> queryCalculateSalary2Models(QueryCalculateSalary2ModelsCommand queryCalculateSalary2ModelsCommand) throws AryaServiceException;

    /**
     * 导出薪资成Excel
     *
     * @param groupId
     * @param year
     * @param month
     * @param week
     * @param response
     */
    void exportSalary(String userId, String groupId, String departmentId, int year, int month, Integer week, HttpServletResponse response);

    /**
     * 导出集团的统计Excel
     *
     * @param userId
     * @param groupId
     * @param departmentId
     * @param year
     * @param month
     * @param week
     * @param response
     */
    void exportStatistics(String userId, String groupId, String departmentId, int year, int month, Integer week, HttpServletResponse response);

    /**
     * 合并计算记录与数据库的记录
     *
     * @param salaryModels
     * @param departmentId
     * @param groupId
     * @param year
     * @param month
     * @param week
     * @return
     */
    List<SalaryModel> mergerSalaryModelsAndDBRecords(List<SalaryModel> salaryModels, String groupId, String departmentId, int year, int month, Integer week);

    /**
     * 统计公司薪资
     *
     * @param salaryModels
     * @return
     */
    SalaryCalculateStatisticsResultList countCorpSalary(List<SalaryModel> salaryModels);

    /**
     * 生成公司薪资统计数据
     *
     * @param salaryModels
     * @return
     */
    Collection<CorpSalaryStatisticsStructure> generateCorpSalaryStatisticsStructures(List<SalaryModel> salaryModels);

    /**
     * 获取薪资计算规则
     *
     * @param groupId
     * @param departmentId
     * @return
     * @throws AryaServiceException
     */
    SalaryCalculateRuleResult getSalaryCalculateRule(String groupId, String departmentId, int ruleType) throws AryaServiceException;

    /**
     * 根据公司或部分获取计算规则类型
     *
     * @param groupId
     * @param departmentId
     * @return
     * @throws AryaServiceException
     */
    SalaryCalculateRuleTypeResult getSalaryCalculateRuleType(String groupId, String departmentId) throws AryaServiceException;

    /**
     * 获取计算规则实体中的计算规则
     *
     * @param ruleEntity
     * @return
     * @throws AryaServiceException
     */
    SalaryCalculateRuleModel getSalaryCalculateRuleModel(SalaryRuleEntity ruleEntity) throws AryaServiceException;

    /**
     * 新增集团的薪资计算规则
     *
     * @param command
     */
    SalaryCalculateRuleResult addGroupSalaryCalculateRule(CreateSalaryCalculateRuleCommand command);

    /**
     * 新增部门的薪资计算规则
     *
     * @param command
     */
    SalaryCalculateRuleResult addDepartmentSalaryCalculateRule(CreateSalaryCalculateRuleCommand command);

    /**
     * 更新计算规则
     *
     * @param command
     */
    SalaryCalculateRuleResult updateSalaryCalculateRule(UpdateSalaryCalculateRuleCommand command);

    /**
     * 删除计算规则
     *
     * @param command
     */
    void deleteSalaryCalculateRule(UpdateSalaryCalculateRuleCommand command);

    /**
     * 获取倒叙排序好的计算规则
     *
     * @param taxGears
     * @return
     */
    List<SalaryCalculateRuleGearModel> getDescTaxGears(List<SalaryCalculateRuleGearModel> taxGears);

    /**
     * 获取倒叙排序好的计算规则返回
     *
     * @param taxGears
     * @return
     */
    List<SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult> getDescTaxGearsResult(List<SalaryCalculateRuleGearModel> taxGears);

    /**
     * 删除薪资记录
     *
     * @param deleteIds
     */
    void deleteCalculatedSalary(List<String> deleteIds, Integer ruleType);

    /**
     * 更新删除的周薪之后的周薪和当月的月薪
     *
     * @param deleteIds
     * @param afterWeek
     * @param salaryCalculateModel
     */
    void updateAfterDeleteWeekSalariesAndMonthSalary(List<String> deleteIds, Integer ruleType, int afterWeek, SalaryCalculateRuleModel salaryCalculateModel);

    /**
     * 重新计算某周之后的周薪
     *
     * @param salaryWeekEntities
     * @param afterWeek
     * @param calculateRuleModel
     * @return
     */
    void reCalculateWeekSalariesAfterWeek(Integer ruleType, List<AryaSalaryWeekEntity> salaryWeekEntities, int afterWeek, SalaryCalculateRuleModel calculateRuleModel);

    /**
     * 重新计算月薪
     *
     * @param salaryEntity
     * @param salaryWeekEntities
     */
    void reCalculateMonthSalary(AryaSalaryEntity salaryEntity, List<AryaSalaryWeekEntity> salaryWeekEntities, SalaryCalculateRuleModel calculateRuleModel);

    /**
     * 根据之前的周薪计算周薪
     *
     * @param forwardWeekSalaryEntities
     * @param taxableSalary
     * @param ruleModel
     * @return
     */
    SalaryCalculateModel calculateWeekSalaryBaseOnForwardWeekSalaries(List<AryaSalaryWeekEntity> forwardWeekSalaryEntities, BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel);

    /**
     * 将薪资计算规则command转成model
     *
     * @param commandGears
     * @return
     * @throws AryaServiceException
     */
    List<SalaryCalculateRuleGearModel> turnSalaryCalculateRuleGearCommandToModel(List<CreateSalaryCalculateRuleCommand.SalaryCalculateRuleTaxGear> commandGears) throws AryaServiceException;

    /**
     * 检查计算规则的计税档是否合法
     *
     * @param taxGears
     * @throws AryaServiceException
     */
    void checkTaxGearsLegal(List<CreateSalaryCalculateRuleCommand.SalaryCalculateRuleTaxGear> taxGears) throws AryaServiceException;

    /**
     * 修改薪资所属用户的个人资料
     *
     * @param command
     * @throws AryaServiceException
     */
    void updateSalaryUserInfo(UpdateSalaryUserInfoCommand command) throws AryaServiceException;

    /**
     * 生成薪资批次号
     *
     * @return
     * @throws AryaServiceException
     */
    Integer generateSalaryBatchNo() throws AryaServiceException;

    /**
     * 获取集团或部门的某月所有薪资批次列表
     *
     * @param groupOrDepartmentId
     * @param year
     * @param month
     * @return
     */
    List getSalaryBatchNoList(String groupOrDepartmentId, Integer year, Integer month) throws AryaServiceException;
}
