package com.bumu.bran.admin.approval.service.impl;

import com.bumu.approval.command.ApprovalTypeSettingCommand;
import com.bumu.approval.model.dao.ApprovalTypeDao;
import com.bumu.approval.model.entity.ApprovalTypeEntity;
import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.admin.system.result.KeyValueResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author majun
 * @date 2017/10/14
 * @email 351264830@qq.com
 */
@Service
public class ApprovalTypeSettingServiceImpl implements ApprovalTypeSettingService {

    @Autowired
    private ApprovalTypeDao approvalTypeDao;

    @Override
    public void commit(Integer approvalType, List<ApprovalTypeSettingCommand> approvalTypeSettingCommands, SessionInfo sessionInfo) {
        // 检查
        if (ListUtils.checkNullOrEmpty(approvalTypeSettingCommands)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "至少设置一项");
        }
        // 查询公司所有的设置
        List<ApprovalTypeEntity> list = approvalTypeDao.findByApprovalTypeAndBranCorpId(approvalType, sessionInfo.getCorpId());
        // 删除设置
        if (!ListUtils.checkNullOrEmpty(list)) {
            approvalTypeDao.delete(list);
        }
        // 新增假期设置
        for (ApprovalTypeSettingCommand command : approvalTypeSettingCommands) {
            ApprovalTypeEntity approvalTypeEntity = new ApprovalTypeEntity();
            approvalTypeEntity.setId(Utils.makeUUID());
            approvalTypeEntity.setBranCorpId(sessionInfo.getCorpId());
            approvalTypeEntity.setApprovalType(approvalType);
            Assert.notNull(command.getApprovalTypeDetail(), "审批明细必填");
            approvalTypeEntity.setApprovalTypeDetail(command.getApprovalTypeDetail());
            approvalTypeDao.persist(approvalTypeEntity);
        }
    }

    @Override
    public List<ApprovalTypeSettingResult> get(Integer approvalType, SessionInfo sessionInfo) {
        List<ApprovalTypeEntity> list = approvalTypeDao.findByApprovalTypeAndBranCorpId(approvalType, sessionInfo.getCorpId());
        if (ListUtils.checkNullOrEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(ApprovalTypeSettingResult::new).collect(Collectors.toList());
    }

    /**
     * 审批类型 0:请假 1:加班 2:补卡
     *
     * @return
     */
    @Override
    public List<KeyValueResult> allType() {
        List<KeyValueResult> list = new ArrayList<>();
        list.add(new KeyValueResult("0", "请假"));
        list.add(new KeyValueResult("1", "加班"));
        list.add(new KeyValueResult("2", "补卡"));
        return list;
    }

    /**
     * 0:调休 1:事假 2:年假 3:病假 4:产检假 5:工伤假 6:婚假 7:产假 8:哺乳假 9:丧假
     * 10:平日加班 11:休息期加班 12:节假日加班 13:上班补卡 14:下班补卡
     * @return
     */
    @Override
    public List<KeyValueResult> allDetailType() {
        List<KeyValueResult> list = new ArrayList<>();
        list.add(new KeyValueResult("0", "调休"));
        list.add(new KeyValueResult("1", "事假"));
        list.add(new KeyValueResult("3", "病假"));
        list.add(new KeyValueResult("4", "产检假"));
        list.add(new KeyValueResult("5", "工伤假"));
        list.add(new KeyValueResult("6", "婚假"));
        list.add(new KeyValueResult("7", "产假"));
        list.add(new KeyValueResult("8", "哺乳假"));
        list.add(new KeyValueResult("9", "丧假"));
        list.add(new KeyValueResult("10", "工作日加班"));
        list.add(new KeyValueResult("11", "休息期加班"));
        list.add(new KeyValueResult("12", "节假日加班"));
        list.add(new KeyValueResult("13", "上班补卡"));
        list.add(new KeyValueResult("14", "下班补卡"));
        return list;
    }
}
