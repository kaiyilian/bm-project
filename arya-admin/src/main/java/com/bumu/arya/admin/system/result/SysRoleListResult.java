package com.bumu.arya.admin.system.result;

import com.bumu.arya.admin.model.entity.RoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by allen on 15/12/24.
 */
public class SysRoleListResult extends ArrayList<SysRoleListResult.SysRoleResult> {

	/**
	 * 将 DB 中的数据集合转换成返回结果，按照角色名排序
	 * @param entityList
	 */
	public void addAll(List<RoleEntity> entityList) {
		for (RoleEntity roleEntity : entityList) {
			try {
				this.add(new SysRoleResult(roleEntity));
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(this);
	}

	/**
	 * 包含用户数量的查询结果
	 */
	public static class SysRoleResult extends RoleEntity implements Comparable<SysRoleResult>{
		Logger log  = LoggerFactory.getLogger(SysRoleResult.class);

		public SysRoleResult(RoleEntity roleEntity) throws InvocationTargetException, IllegalAccessException {
			log.debug("处理角色：" + roleEntity.getRoleName());
			log.debug("  用户数：" + roleEntity.getSysUsers().size());
			super.setId(roleEntity.getId());
			super.setRoleName(roleEntity.getRoleName());
			super.setRoleDesc(roleEntity.getRoleDesc());
			this.sysUserCount = roleEntity.getSysUsers().size();
			this.sysPermissionCount = roleEntity.getSysPermissions().size();
		}

		int sysUserCount;

		int sysPermissionCount;

		public int getSysUserCount() {
			return sysUserCount;
		}

		public void setSysUserCount(int sysUserCount) {
			this.sysUserCount = sysUserCount;
		}

		public int getSysPermissionCount() {
			return sysPermissionCount;
		}

		public void setSysPermissionCount(int sysPermissionCount) {
			this.sysPermissionCount = sysPermissionCount;
		}

		@Override
		public int compareTo(SysRoleResult o) {
			return this.getRoleName().compareTo(o.getRoleName());
		}
	}
}
