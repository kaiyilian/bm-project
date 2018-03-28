package com.bumu.arya.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.command.ErrLogExportCommand;
import com.bumu.arya.salary.command.SalaryExportCommand;
import com.bumu.arya.salary.common.Constants;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.dao.*;
import com.bumu.arya.salary.model.entity.*;
import com.bumu.arya.salary.result.*;
import com.bumu.arya.salary.service.SalaryBillService;
import com.bumu.arya.salary.service.SalaryCalculateService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.ReadFileResponseService;
import com.bumu.common.util.NumberUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.bumu.arya.salary.common.SalaryEnum.exportType.count;
import static com.bumu.common.service.impl.CommonFileServiceImpl.fileDateReadLength;
import static java.lang.System.out;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/21
 */
@Service
public class SalaryFileServiceImpl implements SalaryFileService {

    public static final String BUMU_SALARY_TEMPLATE_IMPORT = "SalaryCalculateTemplate.xls";//薪资自定义、普通计算模板
    public static final String HUMANPOOL_SALARY_TEMPLATE_IMPORT = "SalaryHumanPoolTemplate.xls";//蓝领模板
    public static final String ORDINARY_SALARY_TEMPLATE_IMPORT = "SalaryOrdinaryTemplate.xls";//蓝领模板
    public static final String SALARY_CALCULATE_EXCEL_UPLOAD_LOCATION = "salary_calculate_import/";//薪资计算文件上传目录
    public static final String SALARY_CUSTOMER_CONTRACT_LOCATION = "salary_customer_contract/";
    public static final String EXPORT_FILE_URL = "salary/calculate/file/retrieve";
    public static final String CONTRACT_URL = "salary/customer/contract/download";
    public static final String CONTRACT_READ = "salary/customer/contract/read";
    public static final String TEMPLATE_DOWNLOAD_URL = "salary/calculate/base/file/download/template";
    public static final String[] SALARY_ERROR_LOG_EXPORT_FILE_ROW_NAMES = {"序号","客户名称","城市","计算反馈"};
    public static final String[] SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES = {"序号", "城市", "公司", "姓名", "身份证", "手机号码", "税前薪资", "个税服务费", "税后薪资", "薪资服务费", "账号", "开户行"};
    public static final String[] SALARY_CALCULATE_COUNT_EXPORT_FILE_ROW_NAMES = {"序号", "城市", "公司", "人数", "税前薪资总额", "个税处理费总额", "税后薪资总额", "薪资服务费总额"};
    public static final String[] CUSTOMER_ACCOUNT_EXPORT_FILE_ROW_NAMES = {"序号", "到账日", "到账金额", "清单日期", "税前薪资", "个税处理费","税后薪资", "薪资服务费", "可用余额", "开票金额", "备注"};
    public static final String[] CUSTOMER_EXPORT_FILE_ROW_NAMES = {"序号", "销售部门", "销售人员", "客户名称", "客户简称", "联系人", "联系电话", "地址", "合同结束日期", "备注"};
    public static final String[] SALARY_BILL_EXPORT_FILE_ROW_NAMES = {"序号", "开票公司", "客户名称", "工资", "个税", "管理费", "开票总金额", "申请开票日期", "开票日期", "邮寄日期", "收件人", "签收日期", "签收情况", "汇款日期", "备注"};
    public static final String[] CUSTOMER_ACCOUNT_TOTAL_EXPORT_FILE_ROW_NAMES = {"序号", "客户", "到账金额", "税前金额", "个税服务费", "税后薪资", "薪资服务费", "可用余额"};
    public static final int salaryTemplateReadLength = 200 * 1024;//每次读取200k
    private static Logger logger = LoggerFactory.getLogger(SalaryFileServiceImpl.class);
    private static String BILL_APPLY_DOC_NAME = "bill_apply_doc.doc";
    private static String BILL_BACK_DOC_NAME = "bill_back_doc.doc";
    private String ARYA_SALARY_EXPORT_TEMPLATE_DIR = "arya.dir.export.template";
    private String ARYA_UPLOAD_DIR = "arya.dir.upload";
    @Autowired
    private ConfigService configService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SalaryCalculateService salaryCalculateService;

    @Autowired
    private ReadFileResponseService readFileResponseService;

    @Autowired
    private CustomerSalaryRuleDao customerSalaryRuleDao;

    @Autowired
    private SalaryErrLogDao salaryErrLogDao;

    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Autowired
    private SalaryBillService salaryBillService;

    @Autowired
    private CustomerContractDao customerContractDao;

    @Override
    public void downTemplate(String customerId, HttpServletResponse response) {
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.getOneByCustomerId(customerId);
        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=\"" +
                    SysUtils.parseEncoding("薪资导入模板.xls", "UTF-8") + "\"");
            String templatePath = configService.getConfigByKey(ARYA_SALARY_EXPORT_TEMPLATE_DIR);
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.defined || customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.standard){
                templatePath += BUMU_SALARY_TEMPLATE_IMPORT;
            }
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.humanPool) {
                templatePath += HUMANPOOL_SALARY_TEMPLATE_IMPORT;
            }
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.ordinary) {
                templatePath += ORDINARY_SALARY_TEMPLATE_IMPORT;
            }
            readFileResponseService.readFileToResponse(templatePath, salaryTemplateReadLength, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public FileUploadFileResult templateFile(String customerId) {
        FileUploadFileResult result = new FileUploadFileResult();
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.getOneByCustomerId(customerId);
        if (null != customerSalaryRuleEntity) {
            String templatePath = configService.getConfigByKey(ARYA_SALARY_EXPORT_TEMPLATE_DIR);
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.defined || customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.standard){
                templatePath += BUMU_SALARY_TEMPLATE_IMPORT;
            }
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.humanPool) {
                templatePath += HUMANPOOL_SALARY_TEMPLATE_IMPORT;
            }
            if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.ordinary) {
                templatePath += ORDINARY_SALARY_TEMPLATE_IMPORT;
            }
            File file = new File(templatePath);
            if (file.exists()) {
                result.setUrl(TEMPLATE_DOWNLOAD_URL + "?customerId=" + customerId);
            }
        } else {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED, "该客户没有设置规则");
        }
        return result;
    }

    @Override
    public FileUploadFileResult exportSalaryFile(SalaryExportCommand salaryExportCommand) throws Exception {
        //使用查询的接口 查出这些批次中的薪资数据
        SalaryCalculateListResult salaryCalculateListResult = salaryCalculateService.query(salaryExportCommand.getCondition(),
                salaryExportCommand.getCustomerId(), null, salaryExportCommand.getYear(), salaryExportCommand.getMonth(), salaryExportCommand.getWeek(), null, null);
        if (null == salaryCalculateListResult.getCalculateResults()) {
            logger.info("导出失败-->该条件下未发现有可导出的数据！");
        }
        //查询用户信息 用于文件名称拟定
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.getOneByCustomerId(salaryExportCommand.getCustomerId());
        String fileType = "xls";
        SalaryEnum.exportType exportTypeName;
        String fileId = Utils.makeUUID();
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(salaryExportCommand.getCustomerId());

        Object workbook = null;
        if (null != salaryExportCommand.getExportType() && salaryExportCommand.getExportType() == SalaryEnum.exportType.salaryList.getValue()) {
            exportTypeName = SalaryEnum.exportType.salaryList;
            if(customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.humanPool || customerSalaryRuleEntity.getRuleType()  == SalaryEnum.RuleType.ordinary) {
                workbook = generateSalaryHumanPoolCalculateExcelFile(salaryCalculateListResult.getCalculateResults(), salaryExportCommand.getCustomerId());
            } else {
                workbook = generateSalaryCalculateExcelFile(salaryCalculateListResult.getCalculateResults(), salaryExportCommand.getCustomerId());
            }
        } else if (null != salaryExportCommand.getExportType() && salaryExportCommand.getExportType() == count.getValue()){
            exportTypeName = count;
            workbook = generateSalaryCountExcelFile(salaryCalculateListResult.getSalaryCalculateCountResultList());
        } else if (null != salaryExportCommand.getExportType() && salaryExportCommand.getExportType() == SalaryEnum.exportType.billApply.getValue()) {
            exportTypeName = SalaryEnum.exportType.billApply;
            fileType = "doc";
            workbook = generateSalaryBillApplyWordFile(salaryCalculateListResult.getSalaryCalculateCountResultList(), salaryExportCommand.getCustomerId(), salaryExportCommand.getYear(), salaryExportCommand.getMonth());
            salaryBillService.saveBillApply(salaryCalculateListResult.getSalaryCalculateCountResultList(), salaryExportCommand.getCustomerId());
        } else if (null != salaryExportCommand.getExportType() && salaryExportCommand.getExportType() == SalaryEnum.exportType.billBack.getValue()) {
            exportTypeName = SalaryEnum.exportType.billBack;
            fileType = "doc";
            workbook = generateSalaryBillBackWordFile(salaryCalculateListResult.getSalaryCalculateCountResultList(),salaryExportCommand.getCustomerId());
        } else {
            logger.info("导出失败，无可绘制的导出文件");
            return null;
        }
        String fileName = fileId + "." + fileType;


        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + exportTypeName.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            if ("doc".equals(fileType)) {
                ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                ((HWPFDocument) workbook).write(ostream);
                out.write(ostream.toByteArray());
            } else if ("xls".equals(fileType)) {
                ((HSSFWorkbook) workbook).write(out);
            } else {
                logger.info("无导出文件，请检查");
            }
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + fileType
                    + "&customerId=" + salaryExportCommand.getCustomerId() + "&exportType=" +exportTypeName);
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【薪资计算】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    private HSSFWorkbook generateSalaryHumanPoolCalculateExcelFile(List<SalaryCalculateListResult.SalaryCalculateResult> salaryCalculateResultList, String customerId) throws AryaServiceException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出");
        // 产生表格标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFRow rowm = sheet.createRow(1);
        Map<String, Integer> title = new HashMap<>();
        //文本格式
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        // 标题格式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        if (CollectionUtils.isNotEmpty(salaryCalculateResultList)) {
            SalaryCalculateListResult.SalaryCalculateResult firstData = salaryCalculateResultList.get(0);
            List<Map<String, String>> excelInfoList = firstData.getExcelInfo();

            CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);

            Integer repeatNum = 0;
            for (int i = 0; i < excelInfoList.size(); i++) {
                if (null != title.get(excelInfoList.get(i).get("title")) && title.get(excelInfoList.get(i).get("title")) > 0) {
                    repeatNum++;
                    continue;
                }
                HSSFCell cell = rowm.createCell(i - repeatNum);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(excelInfoList.get(i).get("title"));
                title.put(excelInfoList.get(i).get("title"), i - repeatNum);
            }

            //添加标题行
            HSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(titleCellStyle);
            titleCell.setCellValue(org.apache.tools.ant.util.DateUtils.format(new Date(), "MMdd") + (null != customerEntity ? customerEntity.getCustomerName() : ""));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelInfoList.size() - repeatNum));

            for (int i = 0; i < salaryCalculateResultList.size(); i++) {
                HSSFRow row = sheet.createRow(i + 2);
                SalaryCalculateListResult.SalaryCalculateResult data = salaryCalculateResultList.get(i);
                List<Map<String, String>> excelInfoTempList = data.getExcelInfo();
                for (Map<String, String> map : excelInfoTempList) {
                    if (null != title.get(map.get("title"))) {
                        createStringCell(row, title.get(map.get("title")), map.get("value"), stringCellStyle);
                    }
                }
            }
        }
        return workbook;
    }

    private HSSFWorkbook generateSalaryCalculateExcelFile(List<SalaryCalculateListResult.SalaryCalculateResult> salaryCalculateResultList, String customerId) throws AryaServiceException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出");

        //数字格式
        HSSFCellStyle moneycellStyle = workbook.createCellStyle();
        moneycellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cellBorderStyle(moneycellStyle);
        //文本格式
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        // 标题格式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(1);
        for (int i = 0; i < SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowm.createCell(i);
            cell.setCellValue(SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }

        //添加标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        titleCell.setCellValue(org.apache.tools.ant.util.DateUtils.format(new Date(), "MMdd") + (null != customerEntity ? customerEntity.getCustomerName() : ""));
        titleCell.setCellStyle(titleCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) SALARY_CALCULATE_EXPORT_FILE_ROW_NAMES.length - 1));
        BigDecimal totalTaxableSlary = new BigDecimal(0);
        BigDecimal totalPersonalTax = new BigDecimal(0);
        BigDecimal totalNetSalary = new BigDecimal(0);
        BigDecimal totalBrokerage = new BigDecimal(0);
        for (int i = 0; i < salaryCalculateResultList.size(); i++) {
            SalaryCalculateListResult.SalaryCalculateResult salaryResult = salaryCalculateResultList.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 2);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, salaryResult.getDistrictName(), stringCellStyle);
            createStringCell(row, 2, salaryResult.getCorpName(), stringCellStyle);
            createStringCell(row, 3, salaryResult.getName(), stringCellStyle);
            createStringCell(row, 4, salaryResult.getIdcardNo(), stringCellStyle);
            createStringCell(row, 5, salaryResult.getPhone(), stringCellStyle);

            createMoneyCell(row, 6, new BigDecimal(salaryResult.getTaxableSalary()), moneycellStyle);
            createMoneyCell(row, 7, new BigDecimal(salaryResult.getPersonalTax()), moneycellStyle);
            createMoneyCell(row, 8, new BigDecimal(salaryResult.getNetSalary()), moneycellStyle);
            createMoneyCell(row, 9, new BigDecimal(salaryResult.getBrokerage()), moneycellStyle);

            totalTaxableSlary = totalTaxableSlary.add(new BigDecimal(salaryResult.getTaxableSalary()));
            totalPersonalTax = totalPersonalTax.add(new BigDecimal(salaryResult.getPersonalTax()));
            totalNetSalary = totalNetSalary.add(new BigDecimal(salaryResult.getNetSalary()));
            totalBrokerage = totalBrokerage.add(new BigDecimal(salaryResult.getBrokerage()));

            createStringCell(row, 10, salaryResult.getBankAccount(), stringCellStyle);
            createStringCell(row, 11, salaryResult.getBankName(), stringCellStyle);
        }

        HSSFRow countRow = sheet.createRow(salaryCalculateResultList.size() + 2);
        createStringCell(countRow, 0, "合计", titleCellStyle);
        createStringCell(countRow, 1, "", stringCellStyle);
        createStringCell(countRow, 2, "", stringCellStyle);
        createStringCell(countRow, 3, "", stringCellStyle);
        createStringCell(countRow, 4, "", stringCellStyle);
        createStringCell(countRow, 5, "", stringCellStyle);

        sheet.addMergedRegion(new CellRangeAddress(salaryCalculateResultList.size() + 2, salaryCalculateResultList.size() + 2, 0, 5));
        createStringCell(countRow, 6, totalTaxableSlary.toString(), stringCellStyle);
        createStringCell(countRow, 7, totalPersonalTax.toString(), stringCellStyle);
        createStringCell(countRow, 8, totalNetSalary.toString(), stringCellStyle);
        createStringCell(countRow, 9, totalBrokerage.toString(), stringCellStyle);
        return workbook;
    }

    private HSSFWorkbook generateSalaryCountExcelFile(List<SalaryCalculateCountResult> salaryCalculateCountResultList) throws AryaServiceException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出");
        // 产生表格标题行
        // 标题格式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);
        HSSFRow rowm = sheet.createRow(0);
        for (int i = 0; i < SALARY_CALCULATE_COUNT_EXPORT_FILE_ROW_NAMES.length; i++) {
            createStringCell(rowm, i, String.valueOf(SALARY_CALCULATE_COUNT_EXPORT_FILE_ROW_NAMES[i]), titleCellStyle);
        }
        //数字格式
        HSSFCellStyle moneycellStyle = workbook.createCellStyle();
        moneycellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        //文本格式
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < salaryCalculateCountResultList.size(); i++) {
            SalaryCalculateCountResult salaryCountResult = salaryCalculateCountResultList.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);

            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, salaryCountResult.getDistrictName(), stringCellStyle);
            createStringCell(row, 2, salaryCountResult.getCorpName(), stringCellStyle);
            createStringCell(row, 3, String.valueOf(salaryCountResult.getStaffCount()), stringCellStyle);

            createMoneyCell(row, 4, salaryCountResult.getTaxableSalaryTotal(), moneycellStyle);
            createMoneyCell(row, 5, salaryCountResult.getPersonalTaxTotal(), moneycellStyle);
            createMoneyCell(row, 6, salaryCountResult.getNetSalaryTotal(), moneycellStyle);
            createMoneyCell(row, 7, salaryCountResult.getBrokerageTotal(), moneycellStyle);
        }
        return workbook;
    }

    private HWPFDocument  generateSalaryBillApplyWordFile(List<SalaryCalculateCountResult> salaryCalculateCountResultList, String customerId, Integer year, Integer month) {
        HWPFDocument word;
        if (CollectionUtils.isEmpty(salaryCalculateCountResultList) || StringUtils.isAnyBlank(customerId)) {
            logger.info("绘制开票申请单条件不够,无统计结果或客户ID");
            return null;
        }
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
        if (null == customerEntity) {
            logger.info("绘制开票申请单失败，无法定位客户对象");
            return null;
        }
        Map<String, String> params = new HashMap();
        params.put("$customerName$", customerEntity.getCustomerName());
        params.put("$corpName$", Constants.SALARY_CORP_NAME);
        params.put("$nowDate$", DateFormatUtils.format(new Date(), "yyyy/MM/dd"));
        params.put("$billProject$", customerEntity.getBillProjectOne().getName());
        params.put("$countDate$", year + "-" + month);
        params.put("$staffCount$", String.valueOf(salaryCalculateCountResultList.get(0).getStaffCount()));
        SalaryCalculateCountResult total = salaryCalculateCountResultList.get(salaryCalculateCountResultList.size() - 1);
        if (customerEntity.getBillType() == SalaryEnum.BillType.fullFare || customerEntity.getBillType() == SalaryEnum.BillType.fullSheet){
            params.put("$billProjectTwo$", "");
            params.put("$countDateTwo$", "");
            params.put("$staffCountTwo$", "");
            params.put("$moneyTwo$", "");
            params.put("$money$", total.getTaxableSalaryTotal().add(
                    customerSalaryRuleEntity.getCostBearing() == SalaryEnum.CostBearing.company
                            ? total.getBrokerageTotal() : new BigDecimal(0))
                    .toString());
        }
        if (customerEntity.getBillType() == SalaryEnum.BillType.balanceFare || customerEntity.getBillType() == SalaryEnum.BillType.balanceSheet){
            params.put("$billProjectTwo$", customerEntity.getBillProjectTwo().getName());
            params.put("$countDateTwo$", year + "-" + month);
            params.put("$staffCountTwo$", String.valueOf(salaryCalculateCountResultList.get(0).getStaffCount()));
            params.put("$moneyTwo$", total.getPersonalTaxTotal().add(total.getBrokerageTotal()).toString());
            params.put("$money$", total.getNetSalaryTotal().toString());
        }
        params.put("$totalMoney$", new BigDecimal(StringUtils.isAnyBlank(params.get("$money$")) ? "0" : params.get("$money$"))
                .add(new BigDecimal(StringUtils.isAnyBlank(params.get("$moneyTwo$")) ? "0" : params.get("$moneyTwo$"))).toString());
        params.put("$totalMoneyUpper$", NumberUtils.number2CNMontrayUnit(new BigDecimal(StringUtils.isAnyBlank(params.get("$totalMoney$")) ? "0" : params.get("$totalMoney$"))));
        params.put("$fareType$", checkBillType(customerEntity.getBillType()));

        try {
            InputStream is = new FileInputStream(configService.getConfigByKey(ARYA_SALARY_EXPORT_TEMPLATE_DIR) + BILL_APPLY_DOC_NAME);
            word = new HWPFDocument(is);
            Range range = word.getRange();
            for(Map.Entry<String, String> entry : params.entrySet()){
                range.replaceText(entry.getKey(), entry.getValue());
            }
            //this.replaceInPara(word, params);
        } catch (Exception e) {
            logger.info("导出开票申请单失败，绘制申请单异常");
            logger.info(e.getMessage());
            return null;
        }
        return word;
    }

    private HWPFDocument generateSalaryBillBackWordFile(List<SalaryCalculateCountResult> salaryCalculateCountResultList, String customerId) {
        HWPFDocument word;
        if (CollectionUtils.isEmpty(salaryCalculateCountResultList) || StringUtils.isAnyBlank(customerId)) {
            logger.info("绘制发票回执单条件不够,无统计结果或客户ID");
            return null;
        }
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        if (null == customerEntity) {
            logger.info("绘制发票回执单失败，无法定位客户对象");
            return null;
        }
        Map<String, String> params = new HashMap();
        params.put("$customerName$", customerEntity.getCustomerName());
        params.put("$corpName$", salaryCalculateCountResultList.get(0).getCorpName());
        Calendar calendar = Calendar.getInstance();
        params.put("$year$", String.valueOf(calendar.get(Calendar.YEAR)));
        params.put("$month$", String.valueOf(calendar.get(Calendar.MONTH) + 1));
        params.put("$day$", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        SalaryCalculateCountResult total = salaryCalculateCountResultList.get(salaryCalculateCountResultList.size() - 1);
        params.put("$totalMoney$", total.getTaxableSalaryTotal().add(total.getBrokerageTotal()).toString());
        params.put("$fareType$", checkBillType(customerEntity.getBillType()));

        try {
            InputStream is = new FileInputStream(configService.getConfigByKey(ARYA_SALARY_EXPORT_TEMPLATE_DIR) + BILL_BACK_DOC_NAME);
            word = new HWPFDocument(is);
            Range range = word.getRange();
            for(Map.Entry<String, String> entry : params.entrySet()){
                range.replaceText(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.info("导出发票申请单失败，绘制申请单异常");
            logger.info(e.getMessage());
            return null;
        }
        return word;
    }

    private String checkBillType(SalaryEnum.BillType fareType){
        String fareTypeStr = "";
        if (fareType.equals(SalaryEnum.BillType.fullFare)) {
            fareTypeStr = "全额纳税增值税专用发票";
        } else if (fareType.equals(SalaryEnum.BillType.fullSheet)) {
            fareTypeStr = "全额纳税增值税普通发票";
        } else if (fareType.equals(SalaryEnum.BillType.balanceFare)) {
            fareTypeStr = "差额纳税增值税专用发票";
        } else if (fareType.equals(SalaryEnum.BillType.balanceSheet)) {
            fareTypeStr = "差额纳税增值税普通发票";
        }
        return fareTypeStr;
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
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                out.println("------>>>>>>>>>" + runText);
                if ('$' == runText.charAt(0)&&'{' == runText.charAt(1)) {
                    start = i;
                }
                if ((start != -1)) {
                    str += runText;
                }
                if ('}' == runText.charAt(runText.length() - 1)) {
                    if (start != -1) {
                        end = i;
                        break;
                    }
                }
            }
            out.println("start--->"+start);
            out.println("end--->"+end);

            out.println("str---->>>" + str);

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
                out.println("remove i="+i);
            }

            for (String key : params.keySet()) {
                if (str.equals(key)) {
                    para.createRun().setText((String) params.get(key));
                    break;
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }
    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    @Override
    public void saveSalaryCalculateExcelFile(String fileName, MultipartFile file) throws AryaServiceException {
        String filePath = configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CALCULATE_EXCEL_UPLOAD_LOCATION + fileName + ".xls";
        File saveFile = new File(filePath);

        try {
            saveFile.mkdirs();
            saveFile.createNewFile();
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FILE_SAVE_FAILD);
        }
    }

    @Override
    public ContractUploadResult uploadCustomerContract(MultipartFile file) {
        ContractUploadResult result = new ContractUploadResult();
        String fileId = Utils.makeUUID();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String filePath = configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + fileId + "." + suffix;
        File saveFile = new File(filePath);

        try {
            saveFile.mkdirs();
            saveFile.createNewFile();
            file.transferTo(new File(filePath));// 转存文件
            result.setId(fileId);
            result.setUrl(CONTRACT_URL + "?fileId=" + fileId + "&suffix=" + suffix);
            result.setDir(CONTRACT_READ + "?fileId=" + fileId + "&suffix=" + suffix);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FILE_SAVE_FAILD);
        }
        return result;
    }

    @Override
    public List<ContractUploadResult> uploadCustomerContracts(MultipartFile[] files, String customerId) {
        List<ContractUploadResult> uploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            ContractUploadResult result = new ContractUploadResult();
            String fileId = Utils.makeUUID();
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String filePath = configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + customerId + "/" + fileId + "." + suffix;
            File saveFile = new File(filePath);

            try {
                saveFile.mkdirs();
                saveFile.createNewFile();
                file.transferTo(new File(filePath));// 转存文件
                result.setName(fileId + "." + suffix);
                result.setId(fileId);
                result.setUrl(filePath);
                result.setDir(CONTRACT_READ + "?fileId=" + fileId + "&suffix=" + suffix + "&customerId=" + customerId);
                uploadResults.add(result);
            } catch (Exception e) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FILE_SAVE_FAILD);
            }
        }
        return uploadResults;
    }

    /**
     * 将客户的合同压缩成一个压缩包
     * @param customerId
     * @return
     */
    @Override
    public void customerContractsZip(String customerId) throws Exception {
        //判断客户的压缩文件是否存在
        File customerZip = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + customerId + "/" + customerId + ".zip");
        if (customerZip.exists()) {
            customerZip.delete();
        }
        //判断客户是否有合同数据
        List<CustomerContractEntity> customerContractEntityList = customerContractDao.findListByCustomerId(customerId);
        if (CollectionUtils.isEmpty(customerContractEntityList)) {
            return;
        }
        new File(customerZip.getParent()).mkdirs();
        ZipOutputStream zout;
        //创建新压缩文件
        customerZip.createNewFile();
        zout = new ZipOutputStream(new FileOutputStream(customerZip));

        int length;
        byte[] buf = new byte[1024];
        for (CustomerContractEntity customerContractEntity : customerContractEntityList) {
            File tempFile = new File(customerContractEntity.getFileUrl());
            if (!tempFile.exists()) {
                continue;
            }
            FileInputStream fis = new FileInputStream(tempFile);
            zout.putNextEntry(new ZipEntry(customerContractEntity.getFileName()));
            while ((length = fis.read(buf)) > 0) {
                zout.write(buf);
            }
            zout.closeEntry();
            fis.close();
        }
        zout.flush();
        zout.close();
    }

    @Override
    public FileUploadFileResult downloadContractUrl(String customerId) {
        if (StringUtils.isAnyBlank(customerId)){
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "客户ID不能为空");
        }
        File downloadFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + customerId + "/" + customerId + ".zip");
        if (!downloadFile.exists()) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "无可下载的合同");
        }
        FileUploadFileResult result = new FileUploadFileResult();
        result.setUrl(CONTRACT_URL + "?customerId=" + customerId);
        return result;
    }

    @Override
    public void downloadCustomerContract(String customerId, HttpServletResponse response) {
        response.setContentType("APPLICATION/OCTET-STREAM");
        String headStr = "attachment;filename=\"" + SysUtils.parseGBK("客户合同.zip") + "\"";
        response.setHeader("Content-Disposition", headStr);
        readFileResponseService.readFileToResponse(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + customerId + "/" + customerId + ".zip"
                ,fileDateReadLength, response);
    }

    @Override
    public void readCustomerContract(String fileId, String suffix, String customerId, HttpServletResponse response) {
        if (suffix.equals("html")) {
            response.setContentType("text/html");
        } else {
            response.setContentType("image/jpeg");
        }
        readFileResponseService.readFileToResponse(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CUSTOMER_CONTRACT_LOCATION + customerId + "/" + fileId + "." + suffix
                , fileDateReadLength, response);
    }

    @Override
    public File readSalaryCalculateExcelFile(String fileName) throws Exception {
        return new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SALARY_CALCULATE_EXCEL_UPLOAD_LOCATION + fileName + ".xls");
    }

    @Override
    public void readSalaryFile(String fileId, String type, SalaryEnum.exportType exportType, String customerId, HttpServletResponse response) {
        String customerName = "";
        if (StringUtils.isNotBlank(customerId)) {
            CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
            customerName = customerEntity.getCustomerName();
        }
        String fileName = DateUtils.formatDate(new Date(), "yyyyMMdd") + "-" + customerName + "-" + exportType.getName();
        response.setContentType("APPLICATION/OCTET-STREAM");
        String headStr = "attachment;filename=\"" + SysUtils.parseGBK(fileName + "." + type) + "\"";
        response.setHeader("Content-Disposition", headStr);
        readFileResponseService.readFileToResponse(configService.getConfigByKey(ARYA_UPLOAD_DIR) + exportType.getPath() + fileId + "." + type
                ,fileDateReadLength, response);
    }

    /**
     * 导出错误日志
     * @param errLogExportCommand
     * @return
     */
    @Override
    public FileUploadFileResult exportErrLog(ErrLogExportCommand errLogExportCommand) {
        List<SalaryErrLogEntity> list = salaryErrLogDao.getList(errLogExportCommand.getCondition());
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出错误日志");
        // 产生表格标题行
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);
        for (int i = 0; i < SALARY_ERROR_LOG_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(SALARY_ERROR_LOG_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }
        //文本格式
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < list.size(); i++) {
            SalaryErrLogEntity salaryErrLogEntity = list.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, salaryErrLogEntity.getCustomerName(), stringCellStyle);
            createStringCell(row, 2, salaryErrLogEntity.getDistrictName(), stringCellStyle);
            createStringCell(row, 3, salaryErrLogEntity.getLogInfo(), stringCellStyle);
        }
        String fileId = Utils.makeUUID();
        String suffix = "xls";
        String fileName = fileId + "." + suffix;
        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SalaryEnum.exportType.errLog.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            ((HSSFWorkbook) workbook).write(out);
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + suffix  + "&exportType=errLog");
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【错误日志】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public FileUploadFileResult exportAccount(String customerId, String yearMonth) throws Exception {
        List<CustomerAccountEntity> list = customerAccountDao.getList(customerId, yearMonth);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出台账记录");
        // 产生表格标题行
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        for (int i = 0; i < CUSTOMER_ACCOUNT_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(CUSTOMER_ACCOUNT_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }
        //文本格式
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < list.size(); i++) {
            CustomerAccountEntity customerAccountEntity = list.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, null == customerAccountEntity.getTransAccountDate() ? "" : DateUtils.formatDate(new Date(customerAccountEntity.getTransAccountDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 2, null == customerAccountEntity.getTransAccountAmount() ? "" : customerAccountEntity.getTransAccountAmount(), stringCellStyle);
            createStringCell(row, 3, null == customerAccountEntity.getDealDate() ? "" : DateUtils.formatDate(new Date(customerAccountEntity.getDealDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 4, null == customerAccountEntity.getSalaryBeforeTax() ? "" : customerAccountEntity.getSalaryBeforeTax(), stringCellStyle);
            createStringCell(row, 5, null == customerAccountEntity.getPersonalTaxFee() ? "" : customerAccountEntity.getPersonalTaxFee(), stringCellStyle);
            createStringCell(row, 6, null == customerAccountEntity.getSalaryAfterTax() ? "" : customerAccountEntity.getSalaryAfterTax(), stringCellStyle);
            createStringCell(row, 7, null == customerAccountEntity.getSalaryFee() ? "" : customerAccountEntity.getSalaryFee(), stringCellStyle);
            createStringCell(row, 8, null == customerAccountEntity.getRemainAccount() ? "" : customerAccountEntity.getRemainAccount(), stringCellStyle);
            createStringCell(row, 9, null == customerAccountEntity.getBillAmount() ? "" : customerAccountEntity.getBillAmount(), stringCellStyle);
            createStringCell(row, 10, null == customerAccountEntity.getRemark() ? "" : customerAccountEntity.getRemark(), stringCellStyle);
        }
        String fileId = Utils.makeUUID();
        String suffix = "xls";
        String fileName = fileId + "." + suffix;
        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SalaryEnum.exportType.customerAccount.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            ((HSSFWorkbook) workbook).write(out);
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + suffix + "&customerId=" + customerId + "&exportType=customerAccount");
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【错误日志】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public FileUploadFileResult exportCustomer() throws Exception {
        List<CustomerEntity> list = customerDao.getList(null);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出客户信息");
        // 产生表格标题行
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        for (int i = 0; i < CUSTOMER_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(CUSTOMER_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }
        //文本格式
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < list.size(); i++) {
            CustomerEntity customerEntity = list.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, null == customerEntity.getSalesDepartment() ? "" : customerEntity.getSalesDepartment(), stringCellStyle);
            createStringCell(row, 2, null == customerEntity.getSalesMan() ? "" : customerEntity.getSalesMan(), stringCellStyle);
            createStringCell(row, 3, null == customerEntity.getCustomerName() ? "" : customerEntity.getCustomerName(), stringCellStyle);
            createStringCell(row, 3, null == customerEntity.getShortName() ? "" : customerEntity.getShortName(), stringCellStyle);
            createStringCell(row, 4, null == customerEntity.getLinkMan() ? "" : customerEntity.getLinkMan(), stringCellStyle);
            createStringCell(row, 5, null == customerEntity.getTelphone() ? "" : customerEntity.getTelphone(), stringCellStyle);
            createStringCell(row, 6, null == customerEntity.getAddress() ? "" : customerEntity.getAddress(), stringCellStyle);
            createStringCell(row, 7, null == customerEntity.getContractDateEnd() ? "" : DateUtils.formatDate(new Date(customerEntity.getContractDateEnd()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 8, null == customerEntity.getRemark() ? "" : customerEntity.getRemark(), stringCellStyle);
        }
        String fileId = Utils.makeUUID();
        String suffix = "xls";
        String fileName = fileId + "." + suffix;
        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SalaryEnum.exportType.customer.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            ((HSSFWorkbook) workbook).write(out);
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + suffix + "&exportType=customer");
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【错误日志】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public FileUploadFileResult exportSalaryBill() throws Exception {
        List<SalaryBillEntity> salaryBillEntityList = salaryBillService.getAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出客户信息");
        // 产生表格标题行
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        for (int i = 0; i < SALARY_BILL_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(SALARY_BILL_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }
        //文本格式
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < salaryBillEntityList.size(); i++) {
            SalaryBillEntity salaryBillEntity = salaryBillEntityList.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, null == salaryBillEntity.getCorpName() ? "" : salaryBillEntity.getCorpName(), stringCellStyle);
            createStringCell(row, 2, null == salaryBillEntity.getCustomerName() ? "" : salaryBillEntity.getCustomerName(), stringCellStyle);
            createStringCell(row, 3, null == salaryBillEntity.getNetSalary() ? "" : salaryBillEntity.getNetSalary(), stringCellStyle);
            createStringCell(row, 4, null == salaryBillEntity.getPersonalTax() ? "" : salaryBillEntity.getPersonalTax(), stringCellStyle);
            createStringCell(row, 5, null == salaryBillEntity.getManagerFee() ? "" : salaryBillEntity.getManagerFee(), stringCellStyle);
            createStringCell(row, 6, null == salaryBillEntity.getTotalMoney() ? "" : salaryBillEntity.getTotalMoney(), stringCellStyle);
            createStringCell(row, 7, null == salaryBillEntity.getBillApplyDate() ? "" : DateUtils.formatDate(new Date(salaryBillEntity.getBillApplyDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 8, null == salaryBillEntity.getBillDate() ? "" : DateUtils.formatDate(new Date(salaryBillEntity.getBillDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 9, null == salaryBillEntity.getMailDate() ? "" : DateUtils.formatDate(new Date(salaryBillEntity.getMailDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 10, null == salaryBillEntity.getReceiver() ? "" : salaryBillEntity.getReceiver(), stringCellStyle);
            createStringCell(row, 11, null == salaryBillEntity.getReceiveDate() ? "" : DateUtils.formatDate(new Date(salaryBillEntity.getReceiveDate()), "yyyy-MM-dd HH:mm:ss"), stringCellStyle);
            createStringCell(row, 12, null == salaryBillEntity.getReceiveInfo() ? "" : salaryBillEntity.getReceiveInfo(), stringCellStyle);
            createStringCell(row, 13, null == salaryBillEntity.getBackInfo() ? "" : salaryBillEntity.getBackInfo(), stringCellStyle);
            createStringCell(row, 14, null == salaryBillEntity.getRemark() ? "" : salaryBillEntity.getRemark(), stringCellStyle);
        }
        String fileId = Utils.makeUUID();
        String suffix = "xls";
        String fileName = fileId + "." + suffix;
        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SalaryEnum.exportType.salaryBill.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            ((HSSFWorkbook) workbook).write(out);
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + suffix + "&exportType=salaryBill");
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【错误日志】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public FileUploadFileResult exportCustomerAccountTotal(CustomerAccountTotalResult customerAccountTotalResult) throws Exception {
        List<CustomerAccountTotalQueryResult> customerAccountTotalQueryResultList = customerAccountTotalResult.getResultList();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("导出客户台账汇总");
        // 产生表格标题行
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();
        cellBorderStyle(stringCellStyle);
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        cellBorderStyle(titleCellStyle);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleCellStyle.setFont(font);

        for (int i = 0; i < CUSTOMER_ACCOUNT_TOTAL_EXPORT_FILE_ROW_NAMES.length; i++) {
            HSSFCell cell = rowTitle.createCell(i);
            cell.setCellValue(CUSTOMER_ACCOUNT_TOTAL_EXPORT_FILE_ROW_NAMES[i]);
            cell.setCellStyle(titleCellStyle);
        }
        //文本格式
        HSSFDataFormat stringCellDataFormat = workbook.createDataFormat();
        stringCellStyle.setDataFormat(stringCellDataFormat.getFormat("@"));
        for (int i = 0; i < customerAccountTotalQueryResultList.size(); i++) {
            CustomerAccountTotalQueryResult customerAccountTotalResult1 = customerAccountTotalQueryResultList.get(i);
            //创建行
            HSSFRow row = sheet.createRow(i + 1);
            createStringCell(row, 0, String.valueOf(i + 1), stringCellStyle);
            createStringCell(row, 1, null == customerAccountTotalResult1.getCustomerName() ? "" : customerAccountTotalResult1.getCustomerName(), stringCellStyle);
            createStringCell(row, 2, null == customerAccountTotalResult1.getTransAccountAmountTotal() ? "" : customerAccountTotalResult1.getTransAccountAmountTotal(), stringCellStyle);
            createStringCell(row, 3, null == customerAccountTotalResult1.getSalaryBeforeTaxTotal() ? "" : customerAccountTotalResult1.getSalaryBeforeTaxTotal(), stringCellStyle);
            createStringCell(row, 4, null == customerAccountTotalResult1.getPersonalTaxFeeTotal() ? "" : customerAccountTotalResult1.getPersonalTaxFeeTotal(), stringCellStyle);
            createStringCell(row, 5, null == customerAccountTotalResult1.getSalaryAfterTaxTotal() ? "" : customerAccountTotalResult1.getSalaryAfterTaxTotal(), stringCellStyle);
            createStringCell(row, 6, null == customerAccountTotalResult1.getSalaryFeeTotal() ? "" : customerAccountTotalResult1.getSalaryFeeTotal(), stringCellStyle);
            createStringCell(row, 7, null == customerAccountTotalResult1.getRemainAccount() ? "" : customerAccountTotalResult1.getRemainAccount(), stringCellStyle);
        }
        //总计
        HSSFRow countRow = sheet.createRow(customerAccountTotalQueryResultList.size() + 1);
        createStringCell(countRow, 0, "合计", stringCellStyle);
        createStringCell(countRow, 1, "", stringCellStyle);

        CustomerAccountTotalQueryResult resultCount = customerAccountTotalResult.getResultCount();
        sheet.addMergedRegion(new CellRangeAddress(customerAccountTotalQueryResultList.size() + 2, customerAccountTotalQueryResultList.size() + 2, 0, 1));
        createStringCell(countRow, 2, resultCount.getTransAccountAmountTotal(), stringCellStyle);
        createStringCell(countRow, 3, resultCount.getSalaryBeforeTaxTotal(), stringCellStyle);
        createStringCell(countRow, 4, resultCount.getPersonalTaxFeeTotal(), stringCellStyle);
        createStringCell(countRow, 5, resultCount.getSalaryAfterTaxTotal(), stringCellStyle);
        createStringCell(countRow, 6, resultCount.getSalaryFeeTotal(), stringCellStyle);
        createStringCell(countRow, 7, "-", stringCellStyle);

        String fileId = Utils.makeUUID();
        String suffix = "xls";
        String fileName = fileId + "." + suffix;
        //保留至本地
        File exportFile = new File(configService.getConfigByKey(ARYA_UPLOAD_DIR) + SalaryEnum.exportType.accountTotal.getPath() + fileName);
        try {
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            OutputStream out = new FileOutputStream(exportFile);
            ((HSSFWorkbook) workbook).write(out);
            out.flush();
            out.close();
            FileUploadFileResult fileUploadFileResult = new FileUploadFileResult();
            fileUploadFileResult.setUrl(EXPORT_FILE_URL + "?fileId=" + fileId + "&type=" + suffix + "&exportType=accountTotal");
            return  fileUploadFileResult;
        } catch (Exception e) {
            logger.info("【错误日志】导出文件失败。" + fileName + "。");
            logger.info(e.getMessage());
        }
        return null;
    }

    private void cellBorderStyle(HSSFCellStyle cellStyle) {
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    }
}