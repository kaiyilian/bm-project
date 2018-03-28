package com.bumu.bran.admin.salary.service;

import com.bumu.bran.admin.salary.result.ImportSalaryConfirmResult;
import com.bumu.bran.admin.salary.result.SalaryResult;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.esalary.command.SalaryCommand;
import com.bumu.bran.esalary.result.NewSalaryResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * majun
 */
@Transactional
public interface SalaryService {

	void download(HttpServletResponse response);

	Map<String, Object> verify(MultipartFile multipartFile, String branCorpId, String token) throws Exception;

	ImportSalaryConfirmResult confirm(SalaryCommand salaryCommand, BindingResult bindingResult) throws Exception;

	SalaryResult get(SalaryCommand salaryCommand);

	NewSalaryResult detail(String id);

	void delete(CorpModel command);

	CorpModel release(CorpModel command);

	CorpModel releaseAll(CorpModel command);
}
