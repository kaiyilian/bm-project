package com.bumu.arya.admin.salary.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.impl.BaseExcelFileServiceImpl;
import com.bumu.arya.admin.salary.model.CorpSalaryStatisticsStructure;
import com.bumu.arya.admin.salary.model.SalaryCalculateExcelFileReadResult;
import com.bumu.arya.admin.salary.service.SalaryFileService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.admin.salary.model.SalaryModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SalaryFileServiceImpl extends BaseExcelFileServiceImpl implements SalaryFileService {


    String[] SALARY_CALCULATE_FILE_ROW_NAMES = {"地区", "公司", "姓名", "身份证", "手机号码", "税前薪资", "账号"};
    String[] SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES = {"地区", "公司", "部门", "姓名", "身份证", "手机号码", "税前薪资", "个税处理费", "个税服务费", "税后薪资", "薪资服务费", "账号"};
    String[] SALARY_CALCULATE_STATISTICS_EXPORT_FILE_ROW_NAMES = {"地区", "公司", "部门", "人数", "税前薪资总额", "个税处理费总额", "个税服务费总额", "税后薪资总额", "薪资服务费总额"};


    @Autowired
    AryaAdminConfigService configService;

    @Override
    public void saveSalaryCalculateExcelFile(String fileName, MultipartFile file) throws AryaServiceException {
        String filePath = configService.getSalaryCalculateExcelUploadPath() + fileName + ".xls";
        try {
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FILE_SAVE_FAILD);
        }
    }

    @Override
    public SalaryCalculateExcelFileReadResult readSalaryCalculateExcelFile(String fileName) throws AryaServiceException {
        SalaryCalculateExcelFileReadResult readResult = new SalaryCalculateExcelFileReadResult();
        List<SalaryModel> models = new ArrayList<>();
        readResult.setModels(models);
        List<String> logs = new ArrayList<>();
        readResult.setLog(logs);
        try {
            logs.add("开始读取文件。");
            //读文件
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(configService.getSalaryCalculateExcelUploadPath() + fileName + ".xls"));
            //得到Excel工作簿对象
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            //遍历Excel工作表对象
            logs.add("开始遍历工作表。");
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                HSSFSheet sheet = wb.getSheetAt(i);
                //遍历Excel工作表的行
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    //判断该sheet是否为导入模板，不是则进行下一个sheet
                    if (j == 0) {
                        if (row.getPhysicalNumberOfCells() != SALARY_CALCULATE_FILE_ROW_NAMES.length) {
                            logs.add("第" + (i + 1) + "工作表列数不正确。");
                            break;
                        }
                        if (!isCellEqualString(row, SALARY_CALCULATE_FILE_ROW_NAMES)) {
                            logs.add("第" + (i + 1) + "工作表表头与模板表表头不对应。");
                            break;
                        }
                        continue;//跳过表头
                    }
                    SalaryModel model = new SalaryModel();
                    model.setDistrict(readCellStringValue(row.getCell(0)));
                    model.setCorp(readCellStringValue(row.getCell(1)));
                    model.setName(readCellStringValue(row.getCell(2)));
                    if (model.getDistrict() != null || model.getCorp() != null || model.getName() != null) {
                        models.add(model);
                    } else {
                        continue;
                    }
                    if (row.getCell(3) != null) {
                        if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            DecimalFormat format = new DecimalFormat("#");
                            model.setIdcardNo(format.format(row.getCell(3).getNumericCellValue()).toUpperCase());
                        } else {
                            model.setIdcardNo(readCellStringValue(row.getCell(3)).toUpperCase());
                        }
                    } else {
                        model.setIdcardNo("N/A");
                        logs.add(model.getName() + "的身份证号码为空。");
                    }
                    if (row.getCell(4) != null) {
                        if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            DecimalFormat format = new DecimalFormat("#");
                            model.setPhoneNo(format.format(row.getCell(4).getNumericCellValue()));
                        } else {
                            model.setPhoneNo(readCellStringValue(row.getCell(4)));
                        }
                    }
                    if (row.getCell(5) != null) {
                        if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            model.setTaxableSalary(new BigDecimal(Double.toString(row.getCell(5).getNumericCellValue())).setScale(2, BigDecimal.ROUND_DOWN));
                        } else {
                            model.setTaxableSalary(new BigDecimal(Double.parseDouble(row.getCell(5).getStringCellValue())).setScale(2, BigDecimal.ROUND_DOWN));
                        }
                    } else {
                        model.setTaxableSalary(null);
                    }

                    model.setBankAccount(readCellStringValue(row.getCell(6)));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logs.add("读取文件出错。");
            logs.add(e.toString());
            return readResult;
        }
        logs.add("读取文件结束-------------------------------------------------------------------");
        return readResult;
    }

    @Override
    public HSSFWorkbook generateSalaryCalculateExcelFile(List<SalaryModel> salaryModels) throws AryaServiceException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出");
        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        for (int i = 0; i < SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowm.createCell(i);
            cell.setCellValue(SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES[i]);
        }
        //数字格式
        HSSFCellStyle moneycellStyle = workbook.createCellStyle();
        moneycellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        //文本格式
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < salaryModels.size(); i++) {
            SalaryModel salaryModel = salaryModels.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell districtCell = row.createCell(0);
            districtCell.setCellValue(salaryModel.getDistrict());
            HSSFCell corpCell = row.createCell(1);
            corpCell.setCellValue(salaryModel.getCorp());

            HSSFCell departmentCell = row.createCell(2);
            departmentCell.setCellValue(salaryModel.getDepartmentName());

            HSSFCell nameCell = row.createCell(3);
            nameCell.setCellValue(salaryModel.getName());

            createStringCell(row, 4, salaryModel.getIdcardNo(), stringCellStyle);
            createStringCell(row, 5, salaryModel.getPhoneNo(), stringCellStyle);

            createMoneyCell(row, 6, salaryModel.getTaxableSalary(), moneycellStyle);
            createMoneyCell(row, 7, salaryModel.getPersonalTax(), moneycellStyle);
            createMoneyCell(row, 8, salaryModel.getServiceCharge(), moneycellStyle);
            createMoneyCell(row, 9, salaryModel.getNetSalary(), moneycellStyle);
            createMoneyCell(row, 10, salaryModel.getBrokerage(), moneycellStyle);

            createStringCell(row, 11, salaryModel.getBankAccount(), stringCellStyle);
        }
        return workbook;
    }



    @Override
    public HSSFWorkbook generateSalaryCalculateStatisticsExcelFile(Collection<CorpSalaryStatisticsStructure> salaryStatisticsStructures) throws AryaServiceException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("统计导出");
        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        for (int i = 0; i < SALARY_CALCULATE_STATISTICS_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowm.createCell(i);
            cell.setCellValue(SALARY_CALCULATE_STATISTICS_EXPORT_FILE_ROW_NAMES[i]);
        }
        //数字格式
        HSSFCellStyle moneycellStyle = workbook.createCellStyle();
        moneycellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        int i = 0;
        for (CorpSalaryStatisticsStructure statisticsStructure : salaryStatisticsStructures) {
            //创建行
            HSSFRow row = sheet.createRow(++i);
            HSSFCell districtCell = row.createCell(0);
            districtCell.setCellValue(statisticsStructure.getDistrictName());

            HSSFCell corpCell = row.createCell(1);
            corpCell.setCellValue(statisticsStructure.getCorpName());

            HSSFCell departmentCell = row.createCell(2);
            departmentCell.setCellValue(statisticsStructure.getDepartmentName());

            HSSFCell nameCell = row.createCell(3);
            nameCell.setCellValue(statisticsStructure.getStaffCount());

            createMoneyCell(row, 4, statisticsStructure.getTaxableSalaryTotal(), moneycellStyle);
            createMoneyCell(row, 5, statisticsStructure.getPersonalTaxTotal(), moneycellStyle);
            createMoneyCell(row, 6, statisticsStructure.getServiceChargeTotal(), moneycellStyle);
            createMoneyCell(row, 7, statisticsStructure.getNetSalaryTotal(), moneycellStyle);
            createMoneyCell(row, 8, statisticsStructure.getBrokerageTotal(), moneycellStyle);
        }
        return workbook;
    }

    @Override
    public void copySalaryCalculateExportExcelFile(HSSFWorkbook workbook, String fileName) {
        try {
            OutputStream copyOut = new FileOutputStream(configService.getSalaryCalculateExcelExportPath() + File.separatorChar + fileName);
            workbook.write(copyOut);
            copyOut.flush();
            copyOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HSSFCell createStringCell(HSSFRow row, int index, String text, HSSFCellStyle stringCellStyle) {
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(stringCellStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(text);
        return cell;
    }

    private HSSFCell createMoneyCell(HSSFRow row, int index, BigDecimal money, HSSFCellStyle cellStyle) {
        HSSFCell cell = row.createCell(index);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellValue(money.doubleValue());
        cell.setCellStyle(cellStyle);
        return cell;
    }


    /**
     * 判断单元格内容是否为指定文字
     *
     * @param row
     * @param strs
     * @return
     */
    Boolean isCellEqualString(Row row, String[] strs) {
        if (row.getPhysicalNumberOfCells() != strs.length) {
            return false;
        }
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].equals(row.getCell(i).getStringCellValue().replace(" ", ""))) {
                return false;
            }
        }
        return true;
    }


}
