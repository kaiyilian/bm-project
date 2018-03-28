package com.bumu.arya.admin.soin.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by CuiMengxin on 16/8/3.
 */
public class SoinOrderBillImportConstants {

	/**
	 * 字段在Excel中的顺序
	 */
	public static final String NO = "NO";
	public static final String SUBJECT = "缴纳主体";
	public static final String NAME = "姓名";
	public static final String IDCARD_NO = "身份证号码";
	public static final String PHONE_NO = "联系电话";
	public static final String SOIN_DISTRICT_PROVINCE = "参保省";
	public static final String SOIN_DISTRICT_CITY = "参保地区";
	public static final String SOIN_TYPE = "社保类型";
	public static final String SERVICE_YEAR_MONTH = "服务年月";
	public static final String PAY_YEAR_MONTH = "缴纳年月";
	public static final String SOIN_BACK_START_YEAR_MONTH = "补缴开始年月";
	public static final String SOIN_BACK_END_YEAR_MONTH = "补缴结束年月";
	public static final String WORK_STATION = "工作岗位";
	public static final String WORK_LOCATION = "员工工作地点";
	public static final String ENTRY_DATE = "入职日期";
	public static final String MODIFY = "增/减员";
	public static final String SOIN_BASE = "社保基数";
	public static final String HOUSEFUND_BASE = "公积金基数";
	public static final String HOUSEFUND_PERCENT = "公积金比例";
	public static final String SOIN_CODE = "社保编号";
	public static final String HOUSEFUND_CODE = "公积金编号";
	public static final String HUKOU_TYPE = "户口性质";
	public static final String HUKOU_DISTRICT = "户籍地址";
	public static final String COLLECTION_FEE = "服务费-收账";
	public static final String CHARGE_FEE = "服务费-出账";
	public static final String SALESMAN = "业务人员";
	public static final String MEMO = "描述";
	public static final String POSTPONE_MONTH = "顺延月";

	public static final String SOIN_ORDER_BILL_PERSONAL_CALCULATE_FILE_SHEET_TITLE = "苏州汇嘉个人社保代缴客户明细表";
	public static final String SOIN_ORDER_BILL_COMPANY_CALCULATE_FILE_SHEET_TITLE = "苏州汇嘉企业社保代缴客户明细表";
	public static final String[] SOIN_ORDER_BILL_CALCULATE_FILE_ROW_NAMES = {NO, SUBJECT, NAME, IDCARD_NO, PHONE_NO, SOIN_DISTRICT_PROVINCE, SOIN_DISTRICT_CITY, SOIN_TYPE, SERVICE_YEAR_MONTH, PAY_YEAR_MONTH, SOIN_BACK_START_YEAR_MONTH, SOIN_BACK_END_YEAR_MONTH, WORK_STATION, WORK_LOCATION, ENTRY_DATE, MODIFY, SOIN_BASE, HOUSEFUND_BASE, HOUSEFUND_PERCENT, SOIN_CODE, HOUSEFUND_CODE, HUKOU_TYPE, HUKOU_DISTRICT, COLLECTION_FEE, CHARGE_FEE, SALESMAN, MEMO, POSTPONE_MONTH};
	public static final List<String> ROW_NAME_LIST = Arrays.asList(SOIN_ORDER_BILL_CALCULATE_FILE_ROW_NAMES);

	/**
	 * 根据表头名称获取列编号
	 *
	 * @param Title
	 * @return
	 */
	public static int getColumnNo(String Title) {
		return ROW_NAME_LIST.indexOf(Title);
	}
}