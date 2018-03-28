package com.bumu.bran.admin.employee.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.bran.admin.employee.controller.command.SelectModelResult;
import com.bumu.bran.admin.employee.controller.command.WorkSnPrefixCommand;
import com.bumu.bran.admin.employee.service.WorkSnPrefixService;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.employee.command.WorkSnPrefixQueryCommand;
import com.bumu.bran.employee.helper.EmpCommonHelper;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.employee.model.dao.mybatis.EmployeeMybatisDao;
import com.bumu.bran.employee.result.WorkSnPrefixResult;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.bran.model.entity.WorkSnPrefixEntity;
import com.bumu.common.util.ListUtils;
import com.bumu.common.util.TxVersionUtil;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.worksn_prefix.command.WorkSnFormatCommand;
import com.bumu.worksn_prefix.model.dao.WorkSnPrefixDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * majun
 */
@Service
public class WorkSnPrefixServiceImp implements WorkSnPrefixService {

    private Logger logger = LoggerFactory.getLogger(WorkSnPrefixServiceImp.class);

    @Resource
    private WorkSnPrefixDao workSnPrefixDao;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private LeaveEmployeeDao leaveEmployeeDao;

    @Resource
    private BranOpLogDao branOpLogDao;

    @Resource
    private EmployeeMybatisDao employeeMybatisDao;

    @Autowired
    private EmpCommonHelper empCommonHelper;

    @Override
    public WorkSnPrefixResult add(WorkSnPrefixCommand command) throws Exception {
        WorkSnPrefixResult result = new WorkSnPrefixResult();
        if (StringUtils.isBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        // 工号前缀8位
        if (command.getName().length() > 8) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }

        // 判断前缀是否包含中文
        if (ValidateUtils.vefityIsChinese(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PERFIX_CHINESE);
        }

        // 判断前缀名有没有重复
        if (workSnPrefixDao.findByNameOnCorp(command.getName(), command.getBranCorpId()) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PREFIX_EXISTED);
        }

        StringBuilder sb = new StringBuilder();


        WorkSnPrefixEntity prefix = new WorkSnPrefixEntity();
        prefix.setId(Utils.makeUUID());
        prefix.setBranCorpId(command.getBranCorpId());
        prefix.setPrefixName(command.getName());
        prefix.setCreateUser(command.getUserId());
        prefix.setCreateTime(System.currentTimeMillis());
        prefix.setDigit(command.getDigit());

        workSnPrefixDao.create(prefix);
        sb.append("新增了工号前缀: " + prefix.getPrefixName());

        result.setId(prefix.getId());
        result.setName(prefix.getPrefixName());
        result.setVersion(prefix.getTxVersion());
        result.setLatestSn(prefix.getLatestSn());


        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_SN_PREFIX,
                BranOpLogEntity.OP_TYPE_ADD, command.getUserId(), sysLogExtInfo);
        return result;
    }

    @Override
    public WorkSnPrefixResult update(WorkSnPrefixCommand command) throws Exception {
        WorkSnPrefixResult result = new WorkSnPrefixResult();

        Assert.notBlank(command.getId(), "工号前缀ID必填");

        if (StringUtils.isBlank(command.getName())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        if (command.getName().length() > 8) {
            throw new AryaServiceException(ErrorCode.CODE_TEXT_TOO_LONG);
        }

        // 根据id查询前缀
        WorkSnPrefixEntity prefix = workSnPrefixDao.findById(command.getId());

        if (prefix == null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "根据ID没有查询到工号前缀,可能已经被删除");
        }

        // 判断version
        TxVersionUtil.compireVersion(prefix.getTxVersion(), command.getVersion());

        if(!command.getName().trim().equals(prefix.getPrefixName().trim())){
            WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findByNameOnCorp(command.getName().trim(), command.getBranCorpId());
            if(workSnPrefixEntity != null){
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "工号重复");
            }
        }

        // 更新时,检查名字是否重复
        List<WorkSnPrefixEntity> list = workSnPrefixDao.findByNameOnCorpAndUpdate(prefix.getId(), command.getName(),
                prefix.getBranCorpId());

        // 判断工号是否被占用
        if (employeeDao.isWorkSnBeenUsed(prefix.getPrefixName(), command.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PREFIX_ERROR);
        }

        if (!ListUtils.checkNullOrEmpty(list) && command.getDigit() < prefix.getDigit()) {

            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "该工号类型已被使用过,工号位数只可增加不可降低!");
        }

        StringBuilder sb = new StringBuilder();

        prefix.setPrefixName(command.getName());
        prefix.setUpdateUser(command.getUserId());
        prefix.setUpdateTime(System.currentTimeMillis());
        prefix.setTxVersion(prefix.getTxVersion() + 1);
        prefix.setDigit(command.getDigit());

        workSnPrefixDao.update(prefix);

        sb.append("修改了工号前缀: " + prefix.getPrefixName());

        result.setId(prefix.getId());
        result.setName(prefix.getPrefixName());
        result.setVersion(prefix.getTxVersion());
        result.setLatestSn(prefix.getLatestSn());

        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_SN_PREFIX,
                BranOpLogEntity.OP_TYPE_UPDATE, command.getUserId(), sysLogExtInfo);

        return result;
    }

    @Override
    public void delete(IdVersionsCommand command) throws Exception {
        if (ListUtils.checkNullOrEmpty(command.getIds())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "至少选择一项删除");
        }

        List<WorkSnPrefixEntity> prefixs = workSnPrefixDao.findByIds(command.getIds());

        if (ListUtils.checkNullOrEmpty(prefixs)) {
            logger.warn("prefixs is null or empty");
            return;
        }
        Map<String, Long> map = command.getMap();
        StringBuilder sb = new StringBuilder();
        sb.append("删除了工号前缀: ");
        for (WorkSnPrefixEntity prefix : prefixs) {
            // 乐观锁
            TxVersionUtil.compireVersion(prefix.getTxVersion(), map.get(prefix.getId()));
            // 判断工号是否被占用
            if (employeeDao.isWorkSnBeenUsed(prefix.getPrefixName(), command.getBranCorpId())) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PREFIX_ERROR);
            }

            sb.append(" " + prefix.getPrefixName() + " , ");
            prefix.setIsDelete(1);
            prefix.setUpdateUser(command.getUserId());
            prefix.setUpdateTime(System.currentTimeMillis());
        }

        workSnPrefixDao.update(prefixs);


        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_SN_PREFIX,
                BranOpLogEntity.OP_TYPE_DELETE, command.getUserId(), sysLogExtInfo);

    }

    @Override
    public SelectModelResult<WorkSnPrefixResult> get(WorkSnPrefixCommand workSnPrefixCommand) throws Exception {
        SelectModelResult<WorkSnPrefixResult> result = new SelectModelResult<>();
        List<WorkSnPrefixResult> subList = employeeMybatisDao.findAvailableWorkSn(workSnPrefixCommand.getBranCorpId());
        result.setModels(subList);
        return result;
    }

    @Override
    public WorkSnPrefixResult getId(WorkSnPrefixCommand workSnPrefixCommand) throws Exception {
        WorkSnPrefixResult workSnPrefixResult = new WorkSnPrefixResult();

        if ("empty".equals(workSnPrefixCommand.getId())) {
            workSnPrefixResult.setBeginWorkSn(workSnPrefixCommand.getBeginWorkSn());
            workSnPrefixResult.setId("empty");

            if (StringUtils.isNotBlank(workSnPrefixCommand.getBeginWorkSn())) {

                WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                workSnFormatCommand.setWorkSnPrefixId(null);
                workSnFormatCommand.setWorkSnPrefixName("empty");
                workSnFormatCommand.setWorkSn(workSnPrefixCommand.getBeginWorkSn());
                workSnFormatCommand.setId(null);
                workSnPrefixCommand.setBeginWorkSn(empCommonHelper.getFormatWorkSn(workSnFormatCommand));

                WorkSnPrefixQueryCommand workSnPrefixQueryCommand = new WorkSnPrefixQueryCommand();
                workSnPrefixQueryCommand.setWorkSn(workSnPrefixCommand.getBeginWorkSn());
                workSnPrefixQueryCommand.setEmpId(workSnPrefixCommand.getEmpId());
                workSnPrefixQueryCommand.setBranCorpId(workSnPrefixCommand.getBranCorpId());
                workSnPrefixQueryCommand.setWorkSnPrefixName("empty");
                if (employeeDao.isWorkSnsBeenUsed(workSnPrefixQueryCommand)) {
                    throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
                }

                workSnPrefixResult.setBeginWorkSn(workSnPrefixCommand.getBeginWorkSn());
            }
            return workSnPrefixResult;
        }

        Assert.notBlank(workSnPrefixCommand.getBeginWorkSn(), "起始工号必填");

        // 判断分配数量
        if (workSnPrefixCommand.getCount() > 20) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PERFIX_ILLEGAL);
        }
        WorkSnPrefixEntity workSnPrefixEntity = null;
        // 如果工号ID为空或者为NULL
        if (StringUtils.isBlank(workSnPrefixCommand.getId())) {
            return workSnPrefixResult;
        }

        workSnPrefixEntity = workSnPrefixDao.findById(workSnPrefixCommand.getId());

        if ("empty".equals(workSnPrefixCommand.getId())) {
            workSnPrefixResult.setId(workSnPrefixCommand.getId());
        } else {

            if (workSnPrefixEntity == null) {
                workSnPrefixResult.setDigit(8);
                return workSnPrefixResult;
            }
        }

        if (workSnPrefixEntity == null) {
            workSnPrefixResult.setBeginWorkSn(null);
            workSnPrefixResult.setEndWorkSn(null);
        } else {
            if (workSnPrefixCommand.getBeginWorkSn().length() > workSnPrefixEntity.getDigit()) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "起始工号不能大于设置的最大数" +
                        StringUtils.leftPad("", workSnPrefixEntity.getDigit(), "9") + " , 如果已经超过最大位数请联系管理员在工号设置中修改");
            }

        }

        List<String> list = generateWorkSn(workSnPrefixCommand.getId(), workSnPrefixCommand.getBeginWorkSn(),
                workSnPrefixCommand.getCount(), workSnPrefixCommand.getBranCorpId(), workSnPrefixCommand.getEmpId());

        if (!list.isEmpty()) {
            workSnPrefixResult.setBeginWorkSn(list.get(0));
            workSnPrefixResult.setEndWorkSn(list.get(list.size() - 1));
        }

        if (workSnPrefixEntity != null) {
            workSnPrefixResult.setId(workSnPrefixEntity.getId());
            workSnPrefixResult.setVersion(workSnPrefixEntity.getTxVersion());
            workSnPrefixResult.setDigit(workSnPrefixEntity.getDigit());
        }
        return workSnPrefixResult;
    }

    @Override
    public List<String> generateWorkSn(String workSnPrifexId, String startWorkSn, int size, String branCorpId,
                                       String empId) throws Exception {
        if (StringUtils.isNotBlank(startWorkSn)) {
            startWorkSn = startWorkSn.trim();
        }

        List<String> result = new ArrayList<>();
        if (size == 0) {
            return result;
        }

        // 检查工号的长度
        if (StringUtils.isNotBlank(startWorkSn) && startWorkSn.length() > Constants.WORK_SN_MAX_LENGTH) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_LENGTH_ERROR);
        }
        WorkSnPrefixEntity workSnPrefixEntity = null;
        WorkSnPrefixQueryCommand workSnPrefixQueryCommand = new WorkSnPrefixQueryCommand();
        List<String> workSns = new ArrayList<>();

        if (StringUtils.isBlank(workSnPrifexId) || "empty".equals(workSnPrifexId)) {
            if (StringUtils.isBlank(startWorkSn)) {


            } else {
                // 检查工号格式 必须是数字
                if (!SysUtils.checkIsNum(startWorkSn)) {
                    throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PERFIX_MUST_NUM);
                }
                long start = Long.valueOf(startWorkSn);
                for (int i = 0; i < size; i++) {
                    long r = start + i;
                    if (r > 99999999) {
                        throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
                    }
                    result.add(StringUtils.leftPad(start + i + "", 8, "0"));
                    workSns.add(start + i + "");
                }
            }
        } else {
            workSnPrefixEntity = workSnPrefixDao.findById(workSnPrifexId);

            // 如果起始工号为空,使用默认工号
            if (StringUtils.isBlank(startWorkSn)) {
                // 获得最大的工号
                String maxWorkSn = (workSnPrefixEntity.getLatestSn() == 0 ? 1 : workSnPrefixEntity.getLatestSn()) + "";
                logger.debug("maxWorkSn: " + maxWorkSn);
                result.add(StringUtils.leftPad(maxWorkSn, workSnPrefixEntity.getDigit(), "0"));

//				workSns.add(maxWorkSn);
            } else {
                // 检查工号格式 必须是数字
                if (!SysUtils.checkIsNum(startWorkSn)) {
                    throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PERFIX_MUST_NUM);
                }
                long start = Long.valueOf(startWorkSn);
                for (int i = 0; i < size; i++) {
                    long r = start + i;
                    if (r > 99999999) {
                        throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
                    }
                    result.add(StringUtils.leftPad(start + i + "", workSnPrefixEntity.getDigit(), "0"));

                }
            }


            for (String s : result) {
                workSns.add(workSnPrefixEntity.getPrefixName() + s);
            }

        }

        if (workSnPrefixEntity != null) {
            workSnPrefixQueryCommand.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
        }

        // 如果只有一条工号,并且是花名册修改
        if (workSns.size() == 1 && StringUtils.isNotBlank(empId)) {
            workSnPrefixQueryCommand.setWorkSn(workSns.get(0));

            workSnPrefixQueryCommand.setEmpId(empId);
            workSnPrefixQueryCommand.setBranCorpId(branCorpId);
            if (employeeDao.isWorkSnsBeenUsed(workSnPrefixQueryCommand)) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
            }
        } else {
            if (!workSns.isEmpty()) {
                // 正式员工是否被使用
                logger.info("workSns: " + workSns);

                if (employeeDao.isWorkSnsBeenUsed(workSns, branCorpId, workSnPrefixQueryCommand.getWorkSnPrefixName())) {
                    throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
                }
                // 离职员工是否被使用
                // if (leaveEmployeeDao.isWorkSnsBeenUsed(workSns, branCorpId)) {
                //    throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
                // }
            }

        }


        return result;
    }
}
