package com.bumu.bran.admin.salary.service;

import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.esalary.command.SalaryNotImportCommand;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author majun
 * @date 2016/11/22
 */
@Transactional
public interface SalaryNotImportService {

	Map<String, Object> get(SalaryNotImportCommand salaryCommand);

	void delete(CorpModel command);

	void export(SalaryNotImportCommand salaryNotImportCommand, HttpServletResponse response);
}
