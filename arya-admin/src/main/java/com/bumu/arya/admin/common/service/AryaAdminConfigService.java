package com.bumu.arya.admin.common.service;

import com.bumu.arya.common.service.AryaConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by allen on 16/2/17.
 */
@Service
//@Qualifier(value = "aryaAdminConfigService")
public class AryaAdminConfigService extends AryaConfigService {

	static final String SALARY_LOCATION = "salary/";//薪资目录

	static final String SOIN_LOCATION = "soin/";//用户社保信息目录

	static final String SALARY_CALCULATE_EXCEL_UPLOAD_LOCATION = "salary_calculate_import/";//薪资计算文件上传目录

	static final String SALARY_CALCULATE_EXCEL_EXPORT_LOCATION = "salary_calculate_export/";//薪资计算文件导出copy目录

	static final String SOIN_ORDER_BILL_CALCULATE_EXCEL_UPLOAD_LOCATION = "soin_order_calculate_import/";//订单对账单计算文件上传目录

	static final String CRIMINAL_RECORD_EXCEL_UPLOAD_LOCATION = "criminal_upload/";//犯罪记录批量查询文件上传目录

	public static final String CRIMINAL_RECORD_EXCEL_EXPORT_LOCATION = "criminal_upload/Criminal_export_files";//犯罪记录批量查询excel下载文件缓存目录

	public static final String SOIN_ORDER_BILL_EXPORT = "orderBillExportTemplate.xls";//对账单模板

	public static final String SOIN_ORDER_COMPANY_BILL_IMPORT = "companyOrderImport.xlsx";//公司对账单导入模板

	public static final String SOIN_ORDER_PERSONAL_BILL_IMPORT = "personalOrderImport.xlsx";//个人对账单导入模板

	public static final String EMPLOYEE_IN_OR_DECREASE_EXPORT = "employeeMangerTemplate.xls";//增减员导出模板

	public static final String SOIN_ORDER_BILL_IMPORT = "orderImport.xlsx";//对账单导入模板

	public static final String WELFARE_ORDER_LIST_EXPORT = "welfareOrders.xls";//福库订单模板

	public static final String GOODS_RECEIPT_LIST_EXPORT = "goodsReceipt.xls"; //福库货物签收单模板

	public static final String SOIN_BASE_EXPORT = "soinBaseExportTemplate.xls";//社保规则详情模板

	public static final String CRIMINAL_RECORD_EXPORT = "crimanalRecortExport.xlsx";//犯罪记录查询导出模板

	public static final String CRIMINAL_RECORD_IMPORT = "crimanalRecortImport.xlsx";//犯罪记录查询导出模板

	public static final String SALARY_TEMPLATE_IMPORT = "SalaryCalculateTemplate.xls";//薪资计算模板

	public static final String ENROLL_RECORD_TEMPLATE_IMPORT = "EnrollRecordTemplate.xls";//报名记录导出模板

	public static final String ACTIVITY_WOMENS_DAY_IMPORT = "WomensDayTemplate.xls";//报名记录导出模板

	public static final String WALLET_USER_IMPORT = "WalletUserTemplate.xls";

	public static final String WALLET_PAYS_IMPORT = "WalletPaysTemplate.xls";//钱包发薪导入模板

	public static final String WALLET_USER_CHANGE_IMPORT = "WalletUserChangeTemplate.xls";//钱包充值提现记录模板

	public static final String WALLET_PAY_SALARY_IMPORT = "WalletPaySalarTemplate.xls";//钱包发薪明细记录模板
//	@PostConstruct
//	public void init()  {
//		super.init();
//	}

    @Value("${arya.admin.payroll.exclusion.corpids}")
    String payrollExclusionCorpIds;

    public String getPayrollExclusionCorpIds() {
        return payrollExclusionCorpIds;
    }

    public void setPayrollExclusionCorpIds(String payrollExclusionCorpIds) {
        this.payrollExclusionCorpIds = payrollExclusionCorpIds;
    }

    /**
	 * @return
	 */
	public String getSalaryUploadPath() {
		return getUploadPath(SALARY_LOCATION);
	}

	/**
	 * @return
	 */
	public String getSoinUploadPath() {
		return getUploadPath(SOIN_LOCATION);
	}

	/**
	 * 获取薪资计算Excel文件的上传存放路径
	 *
	 * @return
	 */
	public String getSalaryCalculateExcelUploadPath() {
		return getUploadPath(SALARY_CALCULATE_EXCEL_UPLOAD_LOCATION);
	}

	/**
	 * 获取薪资计算Excel文件的导出copy存放路径
	 *
	 * @return
	 */
	public String getSalaryCalculateExcelExportPath() {
		return getExportPath(SALARY_CALCULATE_EXCEL_EXPORT_LOCATION);
	}


	/**
	 * 订单对账单Excel文件的上传存放路径
	 *
	 * @return
	 */
	public String getSoinOrderBillCalculateExcelUploadPath() {
		return getUploadPath(SOIN_ORDER_BILL_CALCULATE_EXCEL_UPLOAD_LOCATION);
	}


	public String getCriminalRecordExcelUploadPath() {
		return getUploadPath(CRIMINAL_RECORD_EXCEL_UPLOAD_LOCATION);
	}

	public String getCriminalRecordExcelExportPath() {
		return getUploadPath(CRIMINAL_RECORD_EXCEL_EXPORT_LOCATION);
	}

	public String getSalesmanRoleId() {
		return getConfigByKey("arya.admin.salesman.role.id");
	}


	public int getWelfareCouponWidth() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.width"));
	}


	public int getWelfareCouponHeight() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.height"));
	}


	public int getWelfareCouponQrcodeX() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.qr_code_x"));
	}


	public int getWelfareCouponQrcodeY() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.qr_code_y"));
	}


	public int getWelfareCouponQrcodeWidth() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.qr_code_width"));
	}


	public int getWelfareCouponQrcodeHeight() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.qr_code_height"));
	}


	public int getWelfareCouponNumX() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.num.x"));
	}


	public int getWelfareCouponNumY() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.num.y"));
	}


	public int getWelfareCouponNumFontSize() {
		return Integer.parseInt(getConfigByKey("arya.admin.welfare.coupon.num.font.size"));
	}


}
