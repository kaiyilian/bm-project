/* @author CuiMengxin
 * @date 2015/11/25
 */
package com.bumu.arya.admin.soin.service;


import com.bumu.arya.admin.soin.result.SoinImportResult;

import java.io.InputStream;
import java.util.List;

public interface ReadSoinFileService {
	List<SoinImportResult.SoinOutputBean> readSalaryExcel(InputStream inputStream);

	void checkInsert(List<SoinImportResult.SoinOutputBean> list, List<SoinImportResult.SoinErrorMsgBean> errorMsgs);

	void putMsgList(List<SoinImportResult.SoinErrorMsgBean> list, String columnNo, String msg);
}
