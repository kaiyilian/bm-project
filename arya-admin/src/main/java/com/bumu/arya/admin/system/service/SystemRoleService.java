package com.bumu.arya.admin.system.service;

import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.model.entity.RoleEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 系统角色相关的业务处理
 * Created by allen on 15/11/17.
 */
@Transactional
public interface SystemRoleService {

    /**
     * @return
     */
    @Transactional(readOnly = true)
    List<RoleEntity> findSysRoleList();

    /**
     * 查询角色列表（附带所有用户）
     *
     * @return
     */
    @Transactional(readOnly = true)
    List<RoleEntity> findSysRoleListWithUser();

    /**
     * 查询角色列表（附带所有用户和所有权限）
     *
     * @return
     */
    @Transactional(readOnly = true)
    List<RoleEntity> findSysRoleListWithUserPermission();

	/**
	 * 分页查询角色列表（附带所有用户和所有权限）
	 * @param len
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true)
	List<RoleEntity> findSysRoleListWithUserPermission(int len, int page);

    /**
     * 查询系统角色的所有有效权限
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    Set<PermissionEntity> findSysRolePermission(String roleId);

    /**
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    Set<SysUserEntity> findSysRoleUser(String roleId);


//    /**
//     * 初始化系统角色（包含它对应的默认角色和默认用户）
//     * @param roles
//     */
//    void initSysRoles(Collection<RoleEntity> roles);

    /**
     * 将系统用户添加至角色中（即和角色建立关联）
     *
     * @param roleId
     * @param sysUserId
     * @throws AryaServiceException
     */
    void addSysUserToRole(String roleId, String sysUserId) throws AryaServiceException;

    /**
     * 将系统用户从角色中移除（接触关联）
     *
     * @param roleId
     * @param sysUserId
     * @throws AryaServiceException
     */
    void removeSysUserFromRole(String roleId, String sysUserId) throws AryaServiceException;

    /**
     * @param roleId
     * @param permId
     * @throws AryaServiceException
     */
    void addSysPermissionToRole(String roleId, String permId) throws AryaServiceException;

    /**
     * @param roleId
     * @param permId
     * @throws AryaServiceException
     */
    void removePermissionFromRole(String roleId, String permId) throws AryaServiceException;

    /**
     * 判断用户是否是业务员角色
     *
     * @param userId
     * @return
     * @throws AryaServiceException
     */
    boolean isSalesmanRole(String userId) throws AryaServiceException;

    /**
     * 判断用户是否是业务员角色
     *
     * @param sysUserEntity
     * @return
     * @throws AryaServiceException
     */
    boolean isSalesmanRole(SysUserEntity sysUserEntity) throws AryaServiceException;

    /**
     * 创建角色
     * @return
     * @throws AryaServiceException
     */
    void createOrUpdateRole(String roleId, String roleName, String roleDesc) throws AryaServiceException;

    /**
     * 安全的删除角色
     * @param roleId
     */
    void deleteRoleSafely(String roleId);

    /**
     * 记录系统角色操作的日志
     * @param opType
     * @param roleEntity
     * @param userEntity
     * @param permissionEntity
     * @param msg
     * @return 日志ID
     */
    String logRoleOps(int opType, RoleEntity roleEntity, SysUserEntity userEntity, PermissionEntity permissionEntity, String msg);
}
