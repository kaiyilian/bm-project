package com.bumu.arya.admin.system.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.system.service.SystemRoleService;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 系统权限接口
 * Created by allen on 15/12/22.
 */
@Controller
public class SystemPermissionController extends BaseController {

	private Logger log = LoggerFactory.getLogger(SystemPermissionController.class);

	@Autowired
    PermissionDao permissionDao;

	@Autowired
	SystemRoleService systemRoleService;

	/**
	 * 获取所有的系统权限（按照权限码排序）
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "admin/sys/permission/list")
	public
	@ResponseBody
	JQDatatablesPaginationResult sysPermissionList(@RequestParam Map<String, Object> params) {

		String roleId = (String) params.get("role_id");

		List<PermissionEntity> allPermissions = permissionDao.findAllValidPermissions();

		if (allPermissions == null || allPermissions.isEmpty()) {
			return makeEmptyJQDatatable(0);
		}

		if (StringUtils.isNotBlank(roleId)) {
			Set<PermissionEntity> sysRolePermission = systemRoleService.findSysRolePermission(roleId);
			if (sysRolePermission != null && !sysRolePermission.isEmpty()) {
				log.debug(String.format("角色 %s 共有 %d 个权限", roleId, sysRolePermission.size()));
				// 去掉已分配的权限
				allPermissions = ListUtils.removeAll(allPermissions, sysRolePermission);
			}
		}

		Collections.sort(allPermissions, new Comparator<PermissionEntity>() {

			@Override
			public int compare(PermissionEntity o1, PermissionEntity o2) {
				return o1.getPermissionCode().compareTo(o2.getPermissionCode());
			}
		});

		JQDatatablesPaginationResult result = new JQDatatablesPaginationResult();

		try {
			result.setDraw(0);
			result.setRecordsTotal(allPermissions.size());
			result.setRecordsFiltered(allPermissions.size());
			result.setData(allPermissions);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 分页获取系统权限
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "admin/sys/permission/list_todo")
	public
	@ResponseBody
	JQDatatablesPaginationResult sysPermissionPagination(@RequestParam Map<String, String> params) {

		Integer draw = Integer.valueOf(params.get("draw"));
		Integer start = Integer.valueOf(params.get("start"));
		Integer length = Integer.valueOf(params.get("length"));

		JQDatatablesPaginationResult result = null;

		try {

			result = new JQDatatablesPaginationResult();

			if (start == null || length == null) {
				makeEmptyJQDatatable(draw);
			}

			List<PermissionEntity> permissions = permissionDao.findAllByPagination(length, start / length);

			if (permissions == null || permissions.isEmpty()) {
				makeEmptyJQDatatable(draw);
			}

			result.setDraw(draw);
			result.setRecordsTotal(permissions.size());
			result.setRecordsFiltered(permissions.size());

			result.setData(permissions);
		} catch (DataAccessException e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}
}
