package com.bumu.arya.admin.system.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.RoleDao;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.model.entity.RoleEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.system.controller.command.ChangeSysRoleCommand;
import com.bumu.arya.admin.system.controller.command.CreateEditSysRoleCommand;
import com.bumu.arya.admin.system.controller.command.SysPermissionToRoleCommand;
import com.bumu.arya.admin.system.controller.command.SysUserToRoleCommand;
import com.bumu.arya.admin.system.result.SysRoleListResult;
import com.bumu.arya.admin.system.service.SystemRoleService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.ValidationResult;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统相关接口，包括：
 * 系统用户管理
 */
@Api(tags = {"系统角色SystemRole"})
@Controller
public class SystemRoleController extends BaseController {

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SystemRoleService systemRoleService;

    private Logger log = LoggerFactory.getLogger(SystemRoleController.class);

    /**
     * 系统用户管理索引页
     *
     * @return
     */
    @ApiOperation(value = "系统用户管理索引页")
    @RequestMapping(value = "/admin/sys/role", method = RequestMethod.GET)
    public String sysRoleIndex() {
        return "system/sys_role_manager";
    }

    /**
     * 创建系统用户
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "创建系统用户")
    @RequestMapping(value = "/admin/sys/role/create_edit", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse createSysRole(@RequestBody CreateEditSysRoleCommand cmd) {

        ValidationResult validationResult = new ValidationResult();

		/* 输入验证处理 */
        if (StringUtils.isBlank(cmd.getRoleName())) {
            validationResult.addErrorCode("role_name", ErrorCode.CODE_VALID_REQUIRED);
        }

        if (validationResult.hasErrors()) {
            return new HttpResponse(ErrorCode.CODE_VALIDATION_FAIL, validationResult);
        }

        try {
            systemRoleService.createOrUpdateRole(cmd.getRid(), cmd.getRoleName(), cmd.getRoleDesc());
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        } catch (Throwable e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR, e.getMessage());
        }

        return new HttpResponse();
    }

    /**
     * 更新系统用户
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "更新系统用户")
    @RequestMapping(value = "/admin/sys/role/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateSysRole(@RequestBody CreateEditSysRoleCommand cmd) {

        log.debug(cmd.getRid());

        return new HttpResponse();
    }

    /**
     * 删除系统角色
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "删除系统角色")
    @RequestMapping(value = "/admin/sys/role/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteSysRole(@RequestBody ChangeSysRoleCommand cmd) {

        log.debug(cmd.getRid());

        try {
            systemRoleService.deleteRoleSafely(cmd.getRid());
        } catch (AryaServiceException e) {
            return new HttpResponse(e);
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(ErrorCode.CODE_SYS_ERR, e.getMessage());
        }
        return new HttpResponse();
    }

    /**
     * 冻结系统用户
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "冻结系统用户")
    @RequestMapping(value = "/admin/sys/role/freeze", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse freezeSysRole(@RequestBody ChangeSysRoleCommand cmd) {

        log.debug(cmd.getRid());
        return new HttpResponse();
    }

    /**
     * 角色列表(JQuery Datatables格式）
     *
     * @param
     * @return
     */
    @ApiOperation(value = "角色列表(JQuery Datatables格式）")
    @RequestMapping(value = "/admin/sys/role/list", method = RequestMethod.GET)
    public
    @ResponseBody
    JQDatatablesPaginationResult sysRoleList(
            @ApiParam @RequestParam Map<String, String> params) {

        Integer draw = Integer.valueOf(params.get("draw"));
        Integer start = Integer.valueOf(params.get("start"));
        Integer length = Integer.valueOf(params.get("length"));

        JQDatatablesPaginationResult result = null;

        try {

            result = new JQDatatablesPaginationResult();

            if (start == null || length == null) {
               return makeEmptyJQDatatable(draw);
            }

			List<RoleEntity> roleList;
			if (length <= 0) {
				roleList = systemRoleService.findSysRoleListWithUserPermission();
			} else {
				roleList = systemRoleService.findSysRoleListWithUserPermission(length, start / length);
			}
//            List<RoleEntity> roleList = systemRoleService.findSysRoleListWithUserPermission();

            for (RoleEntity roleEntity : roleList) {
                log.debug(String.format("角色[%s]用户数量：%d", roleEntity.getRoleName(), roleEntity.getSysUsers().size()));
            }

			if (roleList == null || roleList.isEmpty()) {
				return makeEmptyJQDatatable(draw);
			}

            result.setDraw(draw);
            result.setRecordsTotal((int) roleDao.countAll());
            result.setRecordsFiltered(result.getRecordsTotal());

            SysRoleListResult listResult = new SysRoleListResult();
            listResult.addAll(roleList);
            result.setData(listResult);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


    /**
     * 获取属于某个角色的所有用户
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取属于某个角色的所有用户")
    @ApiImplicitParams(@ApiImplicitParam(name = "rid", value = "角色ID", dataType = "String"))
    @RequestMapping(value = "/admin/sys/role/user/list", method = RequestMethod.GET)
    public
    @ResponseBody
    JQDatatablesPaginationResult sysRoleUserList(@ApiParam @RequestParam Map<String, String> params) {

        String rid = params.get("rid");

        if (StringUtils.isBlank(rid)) {
            return makeEmptyJQDatatable(0);
        }

        Set<SysUserEntity> sysUsersOfRole = systemRoleService.findSysRoleUser(rid);

        if (sysUsersOfRole == null || sysUsersOfRole.isEmpty()) {
            return makeEmptyJQDatatable(0);
        }

        JQDatatablesPaginationResult result = new JQDatatablesPaginationResult();
        result.setDraw(0);
        result.setRecordsTotal(sysUsersOfRole.size());
        result.setRecordsFiltered(sysUsersOfRole.size());
        result.setData(sysUsersOfRole);
        return result;
    }

    /**
     * 系统用户和角色建立关联
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "系统用户和角色建立关联")
    @RequestMapping(value = "/admin/sys/role/user/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addUserToRole(@RequestBody SysUserToRoleCommand cmd) {
        try {
            systemRoleService.addSysUserToRole(cmd.getRoleId(), cmd.getSysUserId());
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }

        return new HttpResponse();
    }

    /**
     * 从角色中移除用户
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "从角色中移除用户")
    @RequestMapping(value = "/admin/sys/role/user/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse removeUserFromRole(@RequestBody SysUserToRoleCommand cmd) {
        log.debug(cmd.toString());

        try {
            systemRoleService.removeSysUserFromRole(cmd.getRoleId(), cmd.getSysUserId());
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }

        return new HttpResponse();
    }

    /**
     * 角色的权限列表
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "角色的权限列表")
    @RequestMapping(value = "/admin/sys/role/permission/list")
    public
    @ResponseBody
    JQDatatablesPaginationResult sysRolePermissionList(
            @ApiParam @RequestParam Map<String, String> params) {
        String roleId = params.get("rid");

        if (StringUtils.isBlank(roleId)) {
            return makeEmptyJQDatatable(0);
        }

        // 按照code排序
        TreeSet<PermissionEntity> resultSet = new TreeSet<>(new Comparator<PermissionEntity>() {
            @Override
            public int compare(PermissionEntity o1, PermissionEntity o2) {
                return o1.getPermissionCode().compareTo(o2.getPermissionCode());
            }
        });

        try {
            Set<PermissionEntity> permissions;
            permissions = systemRoleService.findSysRolePermission(roleId);
            if (permissions == null || permissions.isEmpty()) {
                return makeEmptyJQDatatable(0);
            }
            resultSet.addAll(permissions);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return makeEmptyJQDatatable(0);
        }

        JQDatatablesPaginationResult result = new JQDatatablesPaginationResult();
        result.setDraw(0);
        result.setRecordsTotal(resultSet.size());
        result.setRecordsFiltered(resultSet.size());
        result.setData(resultSet);
        return result;
    }

    /**
     * 系统用户和角色建立关联
     *
     * @param cmd
     * @return
     */
    @ApiOperation(value = "系统用户和角色建立关联")
    @RequestMapping(value = "/admin/sys/role/permission/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addPermissionToRole(@RequestBody SysPermissionToRoleCommand cmd) {
        try {
            systemRoleService.addSysPermissionToRole(cmd.getRoleId(), cmd.getSysPermissionId());
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
        return new HttpResponse();
    }

    /**
     * 从角色中移除权限
     * @param cmd
     * @return
     */
    @ApiOperation(value = "从角色中移除权限")
    @RequestMapping(value = "/admin/sys/role/permission/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse removePermissionFromRole(@RequestBody SysPermissionToRoleCommand cmd) {
        try {
            systemRoleService.removePermissionFromRole(cmd.getRoleId(), cmd.getSysPermissionId());
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
        return new HttpResponse();
    }
}
