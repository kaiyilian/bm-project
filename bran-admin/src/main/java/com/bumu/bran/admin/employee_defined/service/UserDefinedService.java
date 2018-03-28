package com.bumu.bran.admin.employee_defined.service;

import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.result.UserDefinedResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/11/28
 */
@Transactional
public interface UserDefinedService {

	List<UserDefinedResult> all(UserDefinedCommand userDefinedCommand) throws Exception;

	void add(UserDefinedCommand userDefinedCommand) throws Exception;

	Map<String, Object> update(UserDefinedCommand userDefinedCommand) throws Exception;

	void delete(UserDefinedCommand userDefinedCommand);
}
