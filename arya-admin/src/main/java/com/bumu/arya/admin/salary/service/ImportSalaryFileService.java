/* @author CuiMengxin
 * @date 2015/11/25
 */
package com.bumu.arya.admin.salary.service;


import com.bumu.arya.admin.salary.result.SalaryImportResult;
import com.bumu.arya.admin.salary.result.SalaryTemplateUrlResult;
import com.bumu.exception.AryaServiceException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Deprecated
public interface ImportSalaryFileService {

	@Deprecated
	List<SalaryImportResult.SalaryOutputBean> readSalaryExcel(InputStream inputStream) throws IOException;

	@Deprecated
	void checkInsert(List<SalaryImportResult.SalaryOutputBean> list,List<SalaryImportResult.SalaryErrorMsgBean> msgList);

	@Deprecated
	void putMsgList(List<SalaryImportResult.SalaryErrorMsgBean> msgList,String columnNo, String msg);

	@Deprecated
	SalaryTemplateUrlResult downloadTemplate() throws AryaServiceException;

	@Deprecated
	void downloadFile(HttpServletResponse response)throws AryaServiceException;
}
