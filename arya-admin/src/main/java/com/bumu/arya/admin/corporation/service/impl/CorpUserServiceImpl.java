package com.bumu.arya.admin.corporation.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.controller.command.CorpUserPermCommand;
import com.bumu.arya.admin.corporation.controller.command.IdsCommand;
import com.bumu.arya.admin.corporation.model.dao.mybatis.CorpUserPermMybatisDao;
import com.bumu.arya.admin.corporation.result.CorpAdminListResult;
import com.bumu.arya.admin.corporation.result.CorpUserListResult;
import com.bumu.arya.admin.corporation.result.CorpUserPermResult;
import com.bumu.arya.admin.corporation.service.CorpUserService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.admin.model.CorpPermissionDao;
import com.bumu.bran.admin.model.entity.CorpPermissionEntity;
import com.bumu.bran.admin.model.entity.CorpRoleEntity;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.admin.service.BranCorpUserCommonService;
import com.bumu.common.service.RedisService;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bumu.SysUtils.DATE_FORMAT_SECOND;
import static com.bumu.arya.common.OperateConstants.*;

/**
 * 企业用户
 * Created by allen on 16/5/16.
 */
@Service
public class CorpUserServiceImpl extends BaseBumuService implements CorpUserService {

    @Autowired
    CorporationDao corporationDao;
    @Autowired
    BranCorpUserCommonService branCorpUserCommonService;
    @Autowired
    AryaAdminConfigService aryaAdminConfigService;
    @Autowired
    OpLogService opLogService;
    @Autowired
    RedisService redisService;
    private Logger logger = LoggerFactory.getLogger(CorpUserServiceImpl.class);
    @Autowired
    private BranCorpUserDao branCorpUserDao;
    @Autowired
    private CorpPermissionDao corpPermissionDao;
    @Autowired
    private CorpUserPermMybatisDao corpUserPermMybatisDao;

//    private Jedis jedis;

//    @PostConstruct
//    public void init() {
//        jedis = new Jedis(aryaAdminConfigService.getRedisHost(), aryaAdminConfigService.getRedisPort() == 0 ? 6379 : aryaAdminConfigService.getRedisPort());
//    }

    @Override
    public CorpUserListResult getCorporationUserList(String aryaCorpId, int page, int pageSize) throws AryaServiceException {
        CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(aryaCorpId);
        if (StringUtils.isAnyBlank(corporationEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_CORPORATION_NOT_EXIST);
        }

        CorpUserListResult result = new CorpUserListResult();
        try {
            List<BranCorpUserEntity> allCorpUsers = branCorpUserDao.findCorpAdminsBybranCorpId(corporationEntity.getBranCorpId());
            List<CorpAdminListResult.CorpAdminResult> adminResults = new ArrayList<>();
            for (BranCorpUserEntity userEntity : allCorpUsers) {
                CorpAdminListResult.CorpAdminResult adminResult = new CorpAdminListResult.CorpAdminResult();
                adminResult.setTryLoginTimesToday(branCorpUserCommonService.getCorpUserTryLoginTimes(userEntity.getId()));
                adminResult.setId(userEntity.getId());
                adminResult.setAccount(userEntity.getLoginName());
                adminResult.setLastLoginTime(userEntity.getLastLoginTime());
                adminResult.setLastLoginIp(userEntity.getLastLoginIp());
                adminResult.setNickName(userEntity.getNickName());
                adminResult.setCreateTime(userEntity.getCreateTime());
                adminResult.setEmail(userEntity.getEmail());
                adminResults.add(adminResult);
            }
            result.setPages(Utils.calculatePages(allCorpUsers.size(), pageSize));
            result.setUsers(adminResults);
        } catch (DataAccessException e) {
            e.printStackTrace();
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
        }

        return result;
    }

    @Override
    public List<CorpUserPermResult> findCorpPermsWithCorpUser(String corpUserId) {
        Assert.notBlank(corpUserId, "企业用户必填");
        List<CorpUserPermResult> list = corpUserPermMybatisDao.findCorpPermsWithCorpUser(corpUserId);
        return filter(list);
    }

    @Override
    public void assignCorpPermToCorpUser(String corpUserId, String corpPermId) {

        if (StringUtils.isAnyBlank(corpUserId, corpPermId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(corpUserId);

        CorpPermissionEntity corpPermEntity = corpPermissionDao.findCorpPerm(corpPermId);

        if (corpUserEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
        }

        if (corpPermEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PERM_NOT_EXIST);
        }

        // 确保存在角色
        if (corpUserEntity.getCorpRoles() == null) {
            corpUserEntity.setCorpRoles(new HashSet<>());
        }
        CorpRoleEntity corpUserRole = null;
        // 找到默认名字的那个角色
        for (CorpRoleEntity corpRoleEntity : corpUserEntity.getCorpRoles()) {
            if (CorpConstants.DEFAULT_ROLE_NAME.equals(corpRoleEntity.getRoleName())) {
                corpUserRole = corpRoleEntity;
                break;
            }
        }

        if (corpUserRole == null) {
            // 创建默认角色
            corpUserRole = new CorpRoleEntity();
            corpUserRole.setId(Utils.makeUUID());
            corpUserRole.setRoleName(CorpConstants.DEFAULT_ROLE_NAME);
            corpUserRole.setRoleDesc(CorpConstants.DEFAULT_ROLE_DESC);
        }
        StringBuffer logStr = new StringBuffer("【入职管理】增加企业用户的权限,id:" + corpUserId);
        // 此角色不包含指定权限
        if (corpUserRole.getCorpPermissions() == null) {
            // 新建权限集
            Set<CorpPermissionEntity> perms = new HashSet<>();
            perms.add(corpPermEntity);
            corpUserRole.setCorpPermissions(perms);
            logStr.append("," + corpPermEntity.getId());
        } else {
            if (!corpUserRole.getCorpPermissions().contains(corpPermEntity)) {
                corpUserRole.getCorpPermissions().add(corpPermEntity);
            } else {
                throw new AryaServiceException(ErrorCode.CODE_CORP_USER_ALREADY_HAS_PERM);
            }
        }

        corpUserEntity.getCorpRoles().add(corpUserRole);

        branCorpUserDao.update(corpUserEntity);
        opLogService.successLog(ENTRY_CORP_ADMIN_ADD_PERMS, logStr, logger);
    }

    @Override
    public void assignAllCorpPermToCorpUser(String corpUserId) {
        if (StringUtils.isAnyBlank(corpUserId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(corpUserId);

        List<CorpPermissionEntity> allPerms = corpPermissionDao.findAllCorpPerms();

        if (corpUserEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
        }

        if (allPerms == null || allPerms.isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PERM_NOT_EXIST);
        }

        logger.debug("检查角色是否存在");

        // 确保存在角色
        if (corpUserEntity.getCorpRoles() == null) {
            corpUserEntity.setCorpRoles(new HashSet<>());
        }
        CorpRoleEntity corpUserRole = null;
        // 找到默认名字的那个角色
        for (CorpRoleEntity corpRoleEntity : corpUserEntity.getCorpRoles()) {
            if (CorpConstants.DEFAULT_ROLE_NAME.equals(corpRoleEntity.getRoleName())) {
                corpUserRole = corpRoleEntity;
                break;
            }
        }

        if (corpUserRole == null) {
            // 创建默认角色
            corpUserRole = new CorpRoleEntity();
            corpUserRole.setId(Utils.makeUUID());
            corpUserRole.setRoleName(CorpConstants.DEFAULT_ROLE_NAME);
            corpUserRole.setRoleDesc(CorpConstants.DEFAULT_ROLE_DESC);
        }

        logger.debug(String.format("给角色 %s 分配所有权限", corpUserRole.getRoleName()));

        // 此角色不包含指定权限, 新建权限集
        if (corpUserRole.getCorpPermissions() == null) {
            corpUserRole.setCorpPermissions(new HashSet<>());
        }

        for (CorpPermissionEntity perm : allPerms) {
            corpUserRole.getCorpPermissions().add(perm);
        }

        corpUserEntity.getCorpRoles().add(corpUserRole);

        logger.debug("保存角色权限");

        branCorpUserDao.update(corpUserEntity);
        StringBuffer logStr = new StringBuffer("【入职管理】增加企业用户所有的权限,id:" + corpUserId);
        opLogService.successLog(ENTRY_CORP_ADMIN_ADD_ALL_PERMS, logStr, logger);
    }

    @Override
    public void adjustCorpPermToCorpUser(String corpUserId, List<CorpUserPermCommand> permCommands) {

        Assert.notBlank(corpUserId, "用户id不能为空");
        if (ListUtils.checkNullOrEmpty(permCommands)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "权限必填");
        }

        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(corpUserId);

        Assert.notNull(corpUserEntity, "没有查询到企业用户");


        // 确保存在角色
        if (corpUserEntity.getCorpRoles() == null) {
            corpUserEntity.setCorpRoles(new HashSet<>());
        }
        CorpRoleEntity corpUserRole = null;
        // 找到默认名字的那个角色
        for (CorpRoleEntity corpRoleEntity : corpUserEntity.getCorpRoles()) {
            if (CorpConstants.DEFAULT_ROLE_NAME.equals(corpRoleEntity.getRoleName())) {
                corpUserRole = corpRoleEntity;
                break;
            }
        }

        if (corpUserRole == null) {
            // 创建默认角色
            corpUserRole = new CorpRoleEntity();
            corpUserRole.setId(Utils.makeUUID());
            corpUserRole.setRoleName(CorpConstants.DEFAULT_ROLE_NAME);
            corpUserRole.setRoleDesc(CorpConstants.DEFAULT_ROLE_DESC);
            corpUserEntity.getCorpRoles().add(corpUserRole);
        }

        // 新建权限集
        Set<CorpPermissionEntity> perms = new HashSet<>();
        corpUserRole.setCorpPermissions(perms);
        perms.clear();

        StringBuffer logStr = new StringBuffer("【入职管理】企业用户权限管理, ");
        logStr.append("创建人id: " + corpUserId);
        logStr.append("创建时间: " + SysUtils.getDateStringFormTimestamp(System.currentTimeMillis(), DATE_FORMAT_SECOND));
        for (CorpUserPermCommand one : permCommands) {
            if (one.getSelected() == 1) {
                CorpPermissionEntity corpPermissionEntity = corpPermissionDao.findCorpPerm(one.getId());
                Assert.notNull(corpPermissionEntity, "没有查询到权限id: " + corpPermissionEntity.getId());
                logStr.append("添加权限 id: " + corpPermissionEntity.getId());
                logStr.append(" , ");
                logStr.append("name: " + corpPermissionEntity.getPermissionName());
                logStr.append(" , ");
                logStr.append("code: " + corpPermissionEntity.getPermissionCode());
                perms.add(corpPermissionEntity);
            }
        }

        opLogService.successLog(ENTRY_CORP_ADMIN_ADJUST_PERMS, logStr, logger);
        branCorpUserDao.update(corpUserEntity);
    }

    @Override
    public void removeCorpPermFromCorpUser(String corpUserId, String corpPermId) {

        if (StringUtils.isAnyBlank(corpUserId, corpPermId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(corpUserId);

        if (corpUserEntity.getCorpRoles() == null || corpUserEntity.getCorpRoles().isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_HAS_NO_ROLE);
        }

        CorpRoleEntity corpRoleEntity = (CorpRoleEntity) corpUserEntity.getCorpRoles().toArray()[0];

        if (corpRoleEntity == null || corpRoleEntity.getCorpPermissions() == null || corpRoleEntity.getCorpPermissions().isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_HAS_NO_PERM);
        }
        StringBuffer logStr = new StringBuffer("【入职管理】移除企业用户的权限,id:" + corpUserId);
        for (CorpPermissionEntity corpPerm : corpRoleEntity.getCorpPermissions()) {
            if (corpPermId.equals(corpPerm.getId())) {
                corpRoleEntity.getCorpPermissions().remove(corpPerm);
                logStr.append("," + corpPerm.getId());
                break;
            }
        }

        branCorpUserDao.update(corpUserEntity);

        opLogService.successLog(ENTRY_CORP_ADMIN_REMOVE_PERMS, logStr, logger);
    }

    @Override
    public void removeAllCorpPermFromCorpUser(String corpUserId) {
        if (StringUtils.isAnyBlank(corpUserId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        BranCorpUserEntity corpUserEntity = branCorpUserDao.find(corpUserId);

        if (corpUserEntity.getCorpRoles() == null || corpUserEntity.getCorpRoles().isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_HAS_NO_ROLE);
        }

        CorpRoleEntity corpRoleEntity = (CorpRoleEntity) corpUserEntity.getCorpRoles().toArray()[0];

        if (corpRoleEntity == null || corpRoleEntity.getCorpPermissions() == null || corpRoleEntity.getCorpPermissions().isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_HAS_NO_PERM);
        }

        corpRoleEntity.setCorpPermissions(null);

        branCorpUserDao.update(corpUserEntity);
        StringBuffer logStr = new StringBuffer("【入职管理】移除企业用户所有的权限,id:" + corpUserId);
        opLogService.successLog(ENTRY_CORP_ADMIN_REMOVE_ALL_PERMS, logStr, logger);
    }

    @Override
    public void restUserTryLoginTimes(String corpUserId) throws AryaServiceException {
        branCorpUserCommonService.clearCorpUserTryLoginTimes(corpUserId);
        StringBuffer logStr = new StringBuffer("【入职管理】清空企业用户,id:" + corpUserId + "的尝试登录次数。");
        opLogService.successLog(ENTRY_CORP_ADMIN_CLEAR_TRY_LOGIN_TIMES, logStr, logger);
    }

    @Override
    public void deleteCorpUser(IdsCommand command) throws AryaServiceException {
        if (command.getIdList().size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_CHOOSE);
        }
        List<BranCorpUserEntity> corpUserEntities = branCorpUserDao.findBranCorpUsersByIds(command.getIdList());
        StringBuffer logStr = new StringBuffer("【企业用户管理】删除用户:" + StringUtils.join(command.getIdList(), ",") + "。");
        if (corpUserEntities == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_CHOOSE);
        }
        for (BranCorpUserEntity corpUserEntity : corpUserEntities) {
            corpUserEntity.setIsDelete(CorpConstants.TRUE);
        }

        try {
            branCorpUserDao.update(corpUserEntities);
            opLogService.successLog(ENTRY_CORP_ADMIN_DELETE, logStr, logger);
        } catch (Exception e) {
            opLogService.failedLog(ENTRY_CORP_ADMIN_DELETE, logStr, logger);
            throw new AryaServiceException(ErrorCode.CODE_CORP_USER_DELETE_FAILED);
        }
    }

    public List<CorpUserPermResult> filter(List<CorpUserPermResult> list) {

        List<CorpUserPermResult> results = new ArrayList<>();

        Queue<CorpUserPermResult> parent = new ArrayDeque<>();
        Queue<CorpUserPermResult> children = new ArrayDeque<>();

        String reg = ":[a-zA-Z0-9-_*]+";
        String rootReg = "[a-zA-Z0-9-_]+\\:\\*";

        logger.debug("一共" + list.size() + "个节点");
        logger.debug("筛选第一层节点");
        logger.debug("正则:" + rootReg);
        for (CorpUserPermResult one : list) {
            if (StringUtils.isBlank(one.getPermissionCode())) {
                continue;
            }
            if (SysUtils.patternCheck(rootReg, one.getPermissionCode())) {
                one.setParentId(null);
                one.setReg(one.getPermissionCode().split(":")[0] + reg);
                parent.offer(one);
            } else {
                children.offer(one);
            }
        }

        list = null;

        if (ListUtils.checkNullOrEmpty(parent)) {
            logger.debug("没有第一层节点");
            return results;
        }
        logger.debug("筛选完毕");
        logger.debug("第一层节点count: " + parent.size());
        logger.debug("其他节点count: " + children.size());

        // 使用队列来遍历
        while (!parent.isEmpty()) {
            CorpUserPermResult result = parent.poll();
            results.add(result);
            Iterator<CorpUserPermResult> iter = children.iterator();
            logger.trace("code: " + result.getPermissionCode());
            logger.trace("正则: " + setReg(result.getReg() + reg));
            while (iter.hasNext()) {
                CorpUserPermResult s = iter.next();
                if (SysUtils.patternCheck(setReg(result.getReg() + reg), s.getPermissionCode())) {
                    logger.trace("筛选到: " + s.getPermissionCode());
                    s.setParentId(result.getId());
                    s.setReg(s.getPermissionCode() + reg);
                    iter.remove();
                    parent.offer(s);
                }
            }
        }

        logger.debug("result: " + results.size());
        results.forEach(r ->
                logger.trace("r.id: " + r.getId() + " r.parentId: " + r.getParentId() + " r.code: " + r.getPermissionCode())
        );
        logger.debug("noUsedNode: " + children.size());
        children.forEach(r ->
                logger.trace("r.id: " + r.getId() + " r.parentId: " + r.getParentId() + " r.code: " + r.getPermissionCode())
        );

        return results;
    }

    private String setReg(String reg) {
        return "^" + reg + "$";
    }
}
