package com.bumu.bran.admin.user_permission.service;

import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.admin.user_permission.result.CorpModelResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Transactional
public interface UserPermissionService {

	Map<String, Long> addPermissionUser(CorpModel command) throws Exception;

	Map<String, Long>  updatePermissionUser(CorpModel command) throws Exception;

	void deletePermissionUser(CorpModel command) throws Exception;

	CorpModelResult getPermissionUser(CorpModel command);
}
