package com.bumu.bran.admin.config.service;

import com.bumu.bran.service.BranConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * bran-admin 的配置
 */
@Component
public class BranAdminConfigService extends BranConfigService {

    //预览目录
	public static final String PREVIEW_LOCATION = "preview/";
    //待入职员工上传目录
	public static final String PROSPECTIVE_EMPLOYEE_LOCATION = "corporation/";
    //待入职员工导出的excel
	public static final String PROSPECTIVE_EMPLOYEE_EXCEL_TEMPLATE = "ProspectiveEmployee.xls";
    //未导入成功薪资的xls
	public static final String SALARY_NOT_IMPORT_EXCEL_TEMPLATE = "未成功导入的薪资列表.xls";
    //排班视图导出的excel
	public static final String SCHEDULE_EXCEL_TEMPLATE = "排班视图导出.xls";
    //考勤汇总导出的excel
	public static final String WORK_ATTENDANCE_TEMPLATE = "work_attend_template.xls";
	public static final String WORK_ATTENDANCE_TOTLA_TEMPLATE = "work_attend_total_template.xls";
	public static final String WORK_ATTENDANCE_SETTINF_TOTAL_TEMPLATE = "考勤月度汇总表.xls";
	public static final String WORK_ATTENDANCE_GUOAN_TOTAL_TEMPLATE = "国安考勤月度汇总表.xls";
	// 审批导出
	public static final String WORK_ATTENDANCE_APPROVAL_TEMPLATE = "approval_template.xls";
	private static Logger logger = getLogger(BranAdminConfigService.class);

	public int getProfileTotal() {
		return getIntKey("bran.admin.employee.profile.total");
	}


    /**
     * bran-admin 的资源URL前缀
     * @return
     */
	public String getBranAdminResourceServer() {
	    return getConfigByKey("bran.admin.resource.server");
    }

	/**
	 * 厂车路线预览Html文件存放路径
	 *
	 * @param branCorpId
	 * @return
	 */
	public String getCorpBusHtmlPreviewPath(String branCorpId) {
		return getUploadPath(CORP_PROFILE_LOCATION + branCorpId + "/" + CORP_BUS_LOCATION + PREVIEW_LOCATION);
	}

	/**
	 * 获取企业员工照片的存放路径
	 *
	 * @param branId
	 * @param userId
	 * @return
	 */
	public String getCorpPhotoPath(String branId, String userId) {

		return getCorpUploadPath() + branId + File.separator + "emp" + File.separator + userId;
	}

	/**
	 * 员工手册预览Html文件存放路径
	 *
	 * @param branCorpId
	 * @return
	 */
	public String getCorpHandBookHtmlPreviewPath(String branCorpId) {
		return getUploadPath(CORP_PROFILE_LOCATION + branCorpId + "/" + CORP_EMPLOYEE_HAND_BOOK_LOCATION + PREVIEW_LOCATION);
	}

	/**
	 * 员工手册Html文件存放路径
	 *
	 * @return
	 */
	public String getCorpHandBookHtmlConvertPath(String branCorpId) {
		return getUploadPath(CORP_PROFILE_LOCATION + branCorpId + "/" + CORP_EMPLOYEE_HAND_BOOK_LOCATION);
	}

	/**
	 * 待入职员工导入的路径
	 *
	 * @return
	 */
	public String getEmpExcelImportPath(String branCorpId, String fileId, String suffixName) {
		logger.debug("待入职员工导入的路径: " +
				getUploadPath(PROSPECTIVE_EMPLOYEE_LOCATION + branCorpId + "/" + fileId + suffixName));
		return getUploadPath(PROSPECTIVE_EMPLOYEE_LOCATION + branCorpId + "/" + fileId + suffixName);
	}

    public String makeExamUrlForAdmin(String examId, String interfaceName) {
        if (StringUtils.isBlank(examId) || StringUtils.isBlank(interfaceName)) {
            return null;
        }
        return getBranAdminResourceServer() + interfaceName + "?exam_id=" + examId;
    }


	public String getExcelTemplateLocation() {
		return getConfigByKey("bran.dir.excel.template");
	}

	public int getNotificationMinute() {
		return getIntKey("bran.admin.notification.minute");
	}

	public String getProspectiveSMS_Msg() {
		return getConfigByKey("bran.admin.prospective.sms.msg");
	}

	public int getModifyCount() {
		return getIntKey("bran.admin.modify.count");
	}

	public int getBadRecordCount() {
		return getIntKey("bran.admin.bad.record.count");
	}
}
