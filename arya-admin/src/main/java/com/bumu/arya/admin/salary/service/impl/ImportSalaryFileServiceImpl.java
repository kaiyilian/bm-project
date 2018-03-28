/* @author CuiMengxin
 * @date 2015/11/25
 */
package com.bumu.arya.admin.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.admin.salary.result.SalaryImportResult;
import com.bumu.arya.admin.salary.result.SalaryTemplateUrlResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.salary.service.ImportSalaryFileService;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.service.ReadFileResponseService;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImportSalaryFileServiceImpl extends BaseBumuService implements ImportSalaryFileService {

    public static final String salaryTemplateURL = "admin/salary/import/template/file";

    public static final int salaryTemplateReadLength = 200 * 1024;//每次读取200k

    @Autowired
    ReadFileResponseService readFileResponseService;

    @Autowired
    private AryaAdminConfigService aryaAdminConfigService;

    @Override
    public List<SalaryImportResult.SalaryOutputBean> readSalaryExcel(InputStream inputStream) throws IOException {
        List<SalaryImportResult.SalaryOutputBean> excelDatas = new ArrayList<>();
        POIFSFileSystem poiFs = new POIFSFileSystem(inputStream);
        HSSFWorkbook wb = new HSSFWorkbook(poiFs);
        String sheetNAme = wb.getSheetName(0);
        HSSFSheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();

        for (int i = 1; i <= rowNum; i++) {
            SalaryImportResult.SalaryOutputBean inputBean = new SalaryImportResult.SalaryOutputBean();

            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setRealName(row.getCell(0).getStringCellValue());//姓名

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setCardId(row.getCell(1).getStringCellValue());//身份证

            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setPhone(row.getCell(2).getStringCellValue());//手机号码

            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setMonth(row.getCell(3).getStringCellValue());//月份

            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setBeanSalary(row.getCell(4).getStringCellValue());//基本工资

            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setWorkdayHours(row.getCell(5).getStringCellValue());//工作日出勤时间(小时)

            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setWorkdayOvertimeSalary(row.getCell(6).getStringCellValue());//平时加班工资

            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setOvertimeHours(row.getCell(7).getStringCellValue());//平时加班工作时间(小时)

            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setRestDayOvertimeSalary(row.getCell(8).getStringCellValue());//休息日加班工资

            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setRestDayOvertimeHours(row.getCell(9).getStringCellValue());//休息日工作时间(小时)

            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setLegalHolidayOvertimeSalary(row.getCell(10).getStringCellValue());//国假加班工资

            row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setLegalHolidayOvertimeHours(row.getCell(11).getStringCellValue());//国假加班时间(小时)

            row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setPerformanceBonus(row.getCell(12).getStringCellValue());//绩效奖金

            row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setSubsidy(row.getCell(13).getStringCellValue());//津贴（餐补/车补/住房）

            row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setCasualLeaveCut(row.getCell(14).getStringCellValue());//事假扣款

            row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setCasualLeaveDays(row.getCell(15).getStringCellValue());//事假天数

            row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setSickLeaveCut(row.getCell(16).getStringCellValue());//病假扣款

            row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setSickLeaveDays(row.getCell(17).getStringCellValue());//病假天数

            row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setOtherCut(row.getCell(18).getStringCellValue());//其他扣款

            row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setRepayment(row.getCell(19).getStringCellValue());//补款

            row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setSoinPersonal(row.getCell(20).getStringCellValue());//社保（个人）

            row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setFundPersonal(row.getCell(21).getStringCellValue());//公积金（个人）

            row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setTaxableSalary(row.getCell(22).getStringCellValue());//应税工资

            row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setPersonalTax(row.getCell(23).getStringCellValue());//个税（个人）

            row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setGrossSalary(row.getCell(24).getStringCellValue());//应发工资

            row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
            inputBean.setNetSalary(row.getCell(25).getStringCellValue());//实发工资

            excelDatas.add(inputBean);
        }
        return excelDatas;
    }

    /**
     * 薪资check
     *
     * @param list
     * @return
     */
    @Override
    public void checkInsert(List<SalaryImportResult.SalaryOutputBean> list, List<SalaryImportResult.SalaryErrorMsgBean> msgList) {
        if (list != null && list.size() > 0) {
            Set<String> cardSet = new HashSet<>();
            Set<String> phoneSet = new HashSet<>();

            for (int i = 0; i < list.size(); i++) {
                SalaryImportResult.SalaryOutputBean inputBean = list.get(i);
                cardSet.add(inputBean.getCardId());
                phoneSet.add(inputBean.getPhone());

                if (StringUtil.isEmpty(inputBean.getRealName())) {
                    putMsgList(msgList, (i + 1) + "", "姓名不能为空");
                }
                if (StringUtil.isEmpty(inputBean.getCardId())) {
                    putMsgList(msgList, (i + 1) + "", "身份证不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getCardId()) && !StringUtil.isCardId(inputBean.getCardId())) {
                    putMsgList(msgList, (i + 1) + "", "身份证格式不正确");
                }
                if (StringUtil.isEmpty(inputBean.getPhone())) {
                    putMsgList(msgList, (i + 1) + "", "手机号不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getPhone()) && !StringUtil.isMobileNumber(inputBean.getPhone())) {
                    putMsgList(msgList, (i + 1) + "", "手机号格式不正确");
                }
                if (StringUtil.isEmpty(inputBean.getMonth())) {
                    putMsgList(msgList, (i + 1) + "", "月份不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getMonth()) && DateTimeUtils.checkMonth(inputBean.getMonth()) == null) {
                    putMsgList(msgList, (i + 1) + "", "月份格式不正确");
                }
                if (StringUtil.isEmpty(inputBean.getBeanSalary())) {
                    putMsgList(msgList, (i + 1) + "", "基本工资不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getBeanSalary()) && !StringUtil.checkNum(inputBean.getBeanSalary())) {
                    putMsgList(msgList, (i + 1) + "", "基本薪资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getWorkdayHours()) && !StringUtil.checkNum(inputBean.getWorkdayHours())) {
                    putMsgList(msgList, (i + 1) + "", "工作日出勤时间格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getWorkdayOvertimeSalary()) && !StringUtil.checkNum(inputBean.getWorkdayOvertimeSalary())) {
                    putMsgList(msgList, (i + 1) + "", "平时加班工资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getOvertimeHours()) && !StringUtil.checkNum(inputBean.getOvertimeHours())) {
                    putMsgList(msgList, (i + 1) + "", "平时加班工作时间格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getRestDayOvertimeSalary()) && !StringUtil.checkNum(inputBean.getRestDayOvertimeSalary())) {
                    putMsgList(msgList, (i + 1) + "", "休息日加班工资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getRestDayOvertimeHours()) && !StringUtil.checkNum(inputBean.getRestDayOvertimeHours())) {
                    putMsgList(msgList, (i + 1) + "", "休息日工作时间格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getLegalHolidayOvertimeSalary()) && !StringUtil.checkNum(inputBean.getLegalHolidayOvertimeSalary())) {
                    putMsgList(msgList, (i + 1) + "", "国假加班工资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getLegalHolidayOvertimeHours()) && !StringUtil.checkNum(inputBean.getLegalHolidayOvertimeHours())) {
                    putMsgList(msgList, (i + 1) + "", "国假加班时间格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getPerformanceBonus()) && !StringUtil.checkNum(inputBean.getPerformanceBonus())) {
                    putMsgList(msgList, (i + 1) + "", "绩效奖金格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getSubsidy()) && !StringUtil.checkNum(inputBean.getSubsidy())) {
                    putMsgList(msgList, (i + 1) + "", "津贴（餐补/车补/住房）格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getCasualLeaveCut()) && !StringUtil.checkNum(inputBean.getCasualLeaveCut())) {
                    putMsgList(msgList, (i + 1) + "", "事假扣款格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getCasualLeaveDays()) && !StringUtil.checkNum(inputBean.getCasualLeaveDays())) {
                    putMsgList(msgList, (i + 1) + "", "事假天数格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getSickLeaveCut()) && !StringUtil.checkNum(inputBean.getSickLeaveCut())) {
                    putMsgList(msgList, (i + 1) + "", "病假扣款格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getSickLeaveDays()) && !StringUtil.checkNum(inputBean.getSickLeaveDays())) {
                    putMsgList(msgList, (i + 1) + "", "病假天数格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getOtherCut()) && !StringUtil.checkNum(inputBean.getOtherCut())) {
                    putMsgList(msgList, (i + 1) + "", "其他扣款格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getRepayment()) && !StringUtil.checkNum(inputBean.getRepayment())) {
                    putMsgList(msgList, (i + 1) + "", "补款格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getSoinPersonal()) && !StringUtil.checkNum(inputBean.getSoinPersonal())) {
                    putMsgList(msgList, (i + 1) + "", "社保（个人）格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getFundPersonal()) && !StringUtil.checkNum(inputBean.getFundPersonal())) {
                    putMsgList(msgList, (i + 1) + "", "公积金（个人）格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getTaxableSalary()) && !StringUtil.checkNum(inputBean.getTaxableSalary())) {
                    putMsgList(msgList, (i + 1) + "", "应税工资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getPersonalTax()) && !StringUtil.checkNum(inputBean.getPersonalTax())) {
                    putMsgList(msgList, (i + 1) + "", "个税（个人）格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getGrossSalary()) && !StringUtil.checkNum(inputBean.getGrossSalary())) {
                    putMsgList(msgList, (i + 1) + "", "应发工资格式不正确");
                }
                if (!StringUtil.isEmpty(inputBean.getNetSalary()) && !StringUtil.checkNum(inputBean.getNetSalary())) {
                    putMsgList(msgList, (i + 1) + "", "实发工资格式不正确");
                }
            }
            if (msgList.size() == 0) {
                if (list.size() > cardSet.size() || list.size() > phoneSet.size()) {
                    putMsgList(msgList, "", "文件中有重复数据（身份证或手机号）");
                }
            }
        } else {
            putMsgList(msgList, "", "薪资记录为空");
        }
    }

    @Override
    public void putMsgList(List<SalaryImportResult.SalaryErrorMsgBean> msgList, String columnNo, String msg) {
        SalaryImportResult.SalaryErrorMsgBean bean = new SalaryImportResult.SalaryErrorMsgBean();
        bean.setColumnNo(columnNo);
        bean.setMsg(msg);
        msgList.add(bean);
    }

    @Override
    public SalaryTemplateUrlResult downloadTemplate() throws AryaServiceException{
        SalaryTemplateUrlResult result = new SalaryTemplateUrlResult();
        result.setUrl(salaryTemplateURL);
        return result;
    }

    @Override
    public void downloadFile(HttpServletResponse response)throws AryaServiceException{
        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=\"" +
                    SysUtils.parseEncoding("薪资导入模板.xls", "UTF-8") + "\"");
            String templatePath = aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SALARY_TEMPLATE_IMPORT;
            readFileResponseService.readFileToResponse(templatePath, salaryTemplateReadLength, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
