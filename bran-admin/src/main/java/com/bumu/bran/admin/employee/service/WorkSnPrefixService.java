package com.bumu.bran.admin.employee.service;

import com.bumu.bran.admin.employee.controller.command.SelectModelResult;
import com.bumu.bran.admin.employee.controller.command.WorkSnPrefixCommand;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.employee.result.WorkSnPrefixResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * majun
 */
@Transactional
public interface WorkSnPrefixService {

	WorkSnPrefixResult add(WorkSnPrefixCommand command) throws Exception;

	WorkSnPrefixResult update(WorkSnPrefixCommand command) throws Exception;

	void delete(IdVersionsCommand command) throws Exception;

	SelectModelResult<WorkSnPrefixResult> get(WorkSnPrefixCommand workSnPrefixCommand) throws Exception;

	WorkSnPrefixResult getId(WorkSnPrefixCommand workSnPrefixCommand) throws Exception;

	/**
	 * 批量生成工号
	 *
	 * @param startWorkSn
	 * @param size
	 * @param branCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	List<String> generateWorkSn(String workSnPrifexId, String startWorkSn, int size, String branCorpId, String empId)
			throws Exception;


}
