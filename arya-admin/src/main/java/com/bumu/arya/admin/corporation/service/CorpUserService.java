package com.bumu.arya.admin.corporation.service;

import com.bumu.arya.admin.corporation.controller.command.CorpUserPermCommand;
import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.bumu.arya.admin.corporation.result.CorpUserListResult;
import com.bumu.arya.admin.corporation.result.CorpUserPermResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by allen on 16/5/16.
 */
@Transactional
public interface CorpUserService {

    /**
     * 查询企业管理员
     *
     * @param aryaCorpId
     * @return
     * @throws AryaServiceException
     */
    CorpUserListResult getCorporationUserList(String aryaCorpId, int page, int pageSize) throws AryaServiceException;

    /**
     * 获取企业用户的所有企业访问权限
     *
     * @param corpUserId
     * @return
     */
    List<CorpUserPermResult> findCorpPermsWithCorpUser(String corpUserId);

    /**
     * 给企业用户指派企业权限
     *
     * @param corpUserId
     * @param corpPermId
     */
    @Transactional
    void assignCorpPermToCorpUser(String corpUserId, String corpPermId);

    /**
     * 给企业用户指派所有企业权限
     *
     * @param corpUserId
     */
    @Transactional
    void assignAllCorpPermToCorpUser(String corpUserId);

    /**
     * 调整用户权限
     *
     * @param corpUserId
     */
    @Transactional
    void adjustCorpPermToCorpUser(String corpUserId, List<CorpUserPermCommand> permCommands);

    /**
     * 移除企业用户企业权限
     *
     * @param corpUserId
     * @param corpPermId
     */
    void removeCorpPermFromCorpUser(String corpUserId, String corpPermId);

    /**
     * 移除企业用户所有企业权限
     *
     * @param corpUserId
     */
    void removeAllCorpPermFromCorpUser(String corpUserId);

    /**
     * 重置用户尝试登录次数
     *
     * @param corpUserId
     * @throws AryaServiceException
     */
    void restUserTryLoginTimes(String corpUserId) throws AryaServiceException;

    /**
     * 删除用户
     *
     * @param command
     * @throws AryaServiceException
     */
    void deleteCorpUser(IdsCommand command) throws AryaServiceException;
}
