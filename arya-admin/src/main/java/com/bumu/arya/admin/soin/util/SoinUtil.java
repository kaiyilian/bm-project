package com.bumu.arya.admin.soin.util;

import com.bumu.exception.AryaServiceException;

import java.math.BigDecimal;

import static com.bumu.arya.exception.ErrorCode.*;

/**
 * Created by user on 2016/12/5.
 */
public class SoinUtil {

	/**
	 * 验证险种比例
	 *
	 * @param ruleName
	 * @param percentStr
	 * @param isCorp
	 */
	public static void validateRulePercent(String ruleName, String percentStr, boolean isCorp) {
		double percent = Double.parseDouble(percentStr);
		if (percent < 0 || percent > 100) {
			throw new AryaServiceException(CODE_SOIN_RULE_PERCENT_ILLEGAL, ruleName + (isCorp ? "公司" : "个人") + getErrorMessage(CODE_SOIN_RULE_PERCENT_ILLEGAL));
		}
	}

	/**
	 * 验证险种固定费用
	 *
	 * @param ruleName
	 * @param extra
	 * @param isCorp
	 */
	public static BigDecimal validateRuleExtra(String ruleName, BigDecimal extra, boolean isCorp) {
		if (extra != null) {
			if (extra.compareTo(BigDecimal.ZERO) < 0) {
				throw new AryaServiceException(CODE_SOIN_RULE_EXTRA_ILLEGAL, ruleName + (isCorp ? "公司" : "个人") + getErrorMessage(CODE_SOIN_RULE_EXTRA_ILLEGAL));
			}
		}
		return extra;
	}

	/**
	 * 验证险种基数
	 *
	 * @param ruleName
	 * @param base
	 * @return
	 */
	public static BigDecimal validateRuleBae(String ruleName, BigDecimal base) {
		if (base != null) {
			if (base.compareTo(BigDecimal.ZERO) < 0) {
				throw new AryaServiceException(CODE_SOIN_RULE_BASE_ILLEGAL, ruleName + getErrorMessage(CODE_SOIN_RULE_BASE_ILLEGAL));
			}
		}
		return base;
	}

	/**
	 * 判断年月是否是指定年月的上个月
	 *
	 * @param year
	 * @param month
	 * @param compareYear
	 * @param compareMonth
	 * @return
	 */
	public static boolean islastYearMonth(int year, int month, int compareYear, int compareMonth) {
		if (year == compareYear) {
			if ((compareMonth - month) == 1) {
				return true;
			}
		} else if ((compareYear - year) == 1) {
			if ((compareMonth + 12 - month) == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算月数
	 *
	 * @param startYear
	 * @param startMonth
	 * @param endYear
	 * @param endMonth
	 * @return
	 */
	public static int calculateMonths(int startYear, int startMonth, int endYear, int endMonth) {
		if (startYear > endYear) {
			return -1;
		}
		if (startYear == endYear && startMonth > endMonth) {
			return -1;
		}
		if (startYear == endYear) {
			return endMonth - startMonth + 1;
		}

		if (startYear < endYear) {
			return (endYear - startYear) * 12 + endMonth - startMonth + 1;
		}
		return -1;
	}
}
