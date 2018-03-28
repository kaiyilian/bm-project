package com.bumu.bran.admin.work_shift_type.service;

import com.bumu.bran.workshift.command.WorkShiftTypeCommand;
import com.bumu.bran.workshift.result.BranWorkShiftTypeResult;
import com.bumu.common.result.ModelResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author majun
 * @date 2016/12/19
 */
@Transactional
public interface BranWorkShiftTypeService {
	void add(WorkShiftTypeCommand workShiftTypeCommand) throws Exception;

	void delete(WorkShiftTypeCommand workShiftTypeCommand);

	ModelResult update(WorkShiftTypeCommand workShiftTypeCommand);

	Map<String, Object> get(WorkShiftTypeCommand workShiftTypeCommand) throws Exception;

	BranWorkShiftTypeResult getOneById(WorkShiftTypeCommand workShiftTypeCommand) throws Exception;

	Map<String, Object> getDefault(WorkShiftTypeCommand workShiftTypeCommand) throws Exception;
}
