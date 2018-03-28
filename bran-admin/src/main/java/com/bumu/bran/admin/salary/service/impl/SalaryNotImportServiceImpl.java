package com.bumu.bran.admin.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.salary.service.SalaryNotImportService;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.admin.system.command.IdVersion;
import com.bumu.bran.esalary.command.SalaryNotImportCommand;
import com.bumu.bran.esalary.model.dao.SalaryNotImportDao;
import com.bumu.bran.esalary.model.entity.SalaryNotImportEntity;
import com.bumu.bran.esalary.result.SalaryNotImportResult;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.common.result.Pager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/11/22
 */
@Service
public class SalaryNotImportServiceImpl implements SalaryNotImportService {

	@Autowired
	private SalaryNotImportDao salaryNotImportDao;

	@Autowired
	private ExcelExportHelper excelExportHelper;

	@Autowired
	private BranAdminConfigService branAdminConfigService;

	@Override
	public Map<String, Object> get(SalaryNotImportCommand salaryCommand) {
		Session session = SecurityUtils.getSubject().getSession();
		String currentUserId = session.getAttribute("user_id").toString();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		salaryCommand.setBranCorpId(branCorpId);
		salaryCommand.setUserId(currentUserId);
		Pager<SalaryNotImportEntity> pager = salaryNotImportDao.findByKeyWordPagination(salaryCommand);
		pager.getResult();
		pager.getPage();

		Map<String, Object> result = new HashMap<>();
		result.put("total_page", pager.getPage());
		result.put("total_rows", pager.getRowCount());
		result.put("rows", pager.getResult());
		return result;
	}

	@Override
	public void delete(CorpModel command) {
		for (IdVersion id : command.getIds()) {
			salaryNotImportDao.delete(id.getId());
		}
	}

	@Override
	public void export(SalaryNotImportCommand salaryNotImportCommand, HttpServletResponse response) {
		Session session = SecurityUtils.getSubject().getSession();
		String currentUserId = session.getAttribute("user_id").toString();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		salaryNotImportCommand.setBranCorpId(branCorpId);
		salaryNotImportCommand.setUserId(currentUserId);
		List<SalaryNotImportEntity> entities = salaryNotImportDao.findByKeyWord(salaryNotImportCommand).list();
		List<SalaryNotImportResult> list = new ArrayList<>();
		if (entities != null) {
			for (SalaryNotImportEntity entity : entities) {
				SalaryNotImportResult result = new SalaryNotImportResult();
				result.setName(entity.getName());
				result.setIdCardNo(entity.getIdCardNo());
				result.setPhoneNo(entity.getPhoneNo());
				result.setYear(entity.getYear());
				result.setMonth(entity.getMonth());
				if (entity.getImportTime() != null) {
					result.setImportTimeStr(SysUtils.getDateStringFormTimestamp(entity.getImportTime(),
							SysUtils.DATE_FORMAT_SECOND));
				}
				result.setFailMsg(entity.getFailMsg());
				list.add(result);
			}

		}

		Map<String, Object> params = new HashMap<>();
		params.put("list", list);
		excelExportHelper.export(
				branAdminConfigService.getExcelTemplateLocation() + BranAdminConfigService.SALARY_NOT_IMPORT_EXCEL_TEMPLATE,
				"未成功导入的薪资列表",
				params,
				response
		);
	}
}
