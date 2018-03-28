package com.bumu.arya.admin.common.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * Created by user on 2016/11/30.
 */
public class POIExcelUtil {

	public static Row getRow(Sheet sheet, int row) {
		if (sheet.getRow(row) == null) {
			sheet.createRow(row);
		}
		return sheet.getRow(row);
	}

	/**
	 * 创建名称
	 *
	 * @param wb
	 * @param name
	 * @param expression
	 * @return
	 */
	public static Name createName(Workbook wb, String name, String expression) {
		Name refer = wb.createName();
		refer.setRefersToFormula(expression);
		refer.setNameName(name);
		return refer;
	}

	/**
	 * 设置数据有效性（通过名称管理器级联相关）
	 *
	 * @param name
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 */
	public static void setDataValidation(Sheet sheet, String name, int firstRow, int endRow, int firstCol, int endCol) {

		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
		//加载下拉列表内容
		XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) dvHelper
				.createFormulaListConstraint(name);
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
		// 数据有效性对象
		XSSFDataValidation data_validation = (XSSFDataValidation) dvHelper.createValidation(constraint, regions);
		sheet.addValidationData(data_validation);
	}


	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 *
	 * @param sheet    要设置的sheet.
	 * @param textlist 下拉框显示的内容
	 * @param firstRow 开始行
	 * @param endRow   结束行
	 * @param firstCol 开始列
	 * @param endCol   结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
											  String[] textlist, int firstRow, int endRow, int firstCol,
											  int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/**
	 * 设置单元格上提示
	 *
	 * @param sheet         要设置的sheet.
	 * @param promptTitle   标题
	 * @param promptContent 内容
	 * @param firstRow      开始行
	 * @param endRow        结束行
	 * @param firstCol      开始列
	 * @param endCol        结束列
	 * @return 设置好的sheet.
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
										  String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("BB1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}
}
