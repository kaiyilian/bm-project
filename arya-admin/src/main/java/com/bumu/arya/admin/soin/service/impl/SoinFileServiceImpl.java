package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.soin.util.SoinUtil;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.impl.BaseExcelFileServiceImpl;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.admin.soin.model.OrderBillImportMessage;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillExcelReadModel;
import com.bumu.arya.admin.soin.result.SoinOrderBillExcelFileReadResult;
import com.bumu.arya.admin.soin.service.SoinFileService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bumu.arya.admin.soin.constant.SoinConstants.NUMBER_INVALID_FORMAT;
import static com.bumu.arya.admin.soin.constant.SoinOrderBillImportConstants.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

@Service
public class SoinFileServiceImpl extends BaseExcelFileServiceImpl implements SoinFileService {

    Logger log = LoggerFactory.getLogger(SoinFileServiceImpl.class);

    @Autowired
    SysUserService sysUserService;

    @Autowired
    AryaAdminConfigService configService;

    @Override
    public String saveOrderCalculateExcelFile(MultipartFile file) throws AryaServiceException {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        Date date = new Date();
        String fileName = file.getOriginalFilename().split(".xls")[0] + "-" + dateFormater.format(date) + ".xls";
        String dirPath = configService.getSoinOrderBillCalculateExcelUploadPath() + currentUser.getId() + File.separator;
        String filePath = dirPath + fileName;
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_IMPORT_FILE_SAVE_FAILED);
        }
        return fileName;
    }



    @Override
    public SoinOrderBillExcelFileReadResult readSoinOrderBillCalculateExcelFile(String fileName) throws AryaServiceException {
        SoinOrderBillExcelFileReadResult readResult = new SoinOrderBillExcelFileReadResult();
        List<SoinOrderBillExcelReadModel> soinOrderBillExcelReadModels = new ArrayList<>();
        OrderBillImportMessage importMessage = new OrderBillImportMessage();
        readResult.setModels(soinOrderBillExcelReadModels);
        readResult.setMessages(importMessage);
        try {
            SysUserModel currentUser = sysUserService.getCurrentSysUser();
            //读文件
            FileInputStream excelFileInputStream = new FileInputStream(configService.getSoinOrderBillCalculateExcelUploadPath() + currentUser.getId() + File.separator + fileName);
            //得到Excel工作簿对象
            XSSFWorkbook wb = new XSSFWorkbook(excelFileInputStream);
            //遍历Excel工作表对象
            Sheet sheet = wb.getSheetAt(0);
            //遍历Excel工作表的行
            for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                //判断该sheet是否为导入模板，不是则进行下一个sheet
                if (j == 0) {
                    Cell cell = row.getCell(0);
                    if (cell == null) {
                        importMessage.appendErrorMsg(j, "模板不正确,工作簿标题为空。");
                        break;
                    }
                    String title = cell.getStringCellValue();
                    if (!title.trim().startsWith(SOIN_ORDER_BILL_COMPANY_CALCULATE_FILE_SHEET_TITLE) && !title.trim().startsWith(SOIN_ORDER_BILL_PERSONAL_CALCULATE_FILE_SHEET_TITLE)) {
                        importMessage.appendErrorMsg(j, "模板不正确,标题不正确。");
                        break;
                    }
                    continue;
                }
                if (j == 1) {
                    if (row.getPhysicalNumberOfCells() != SOIN_ORDER_BILL_CALCULATE_FILE_ROW_NAMES.length && row.getPhysicalNumberOfCells() != SOIN_ORDER_BILL_CALCULATE_FILE_ROW_NAMES.length - 1) {
                        importMessage.appendErrorMsg(j, "模板不正确,表头不正确。");
                        break;
                    }
                    continue;
                }
                //判断第一个cell内容是否为编号
                Cell noCell = row.getCell(getColumnNo(NO));
                if (noCell == null) {
                    break;
                }
                SoinOrderBillExcelReadModel billReadModel = new SoinOrderBillExcelReadModel();
                try {
                    Integer.parseInt(row.getCell(getColumnNo(NO)).getStringCellValue());
                } catch (Exception e) {
                    log.info("【社保计算导入】读取excel内记录的编号出错,应该是到了文件末尾。");
                    break;
                }
                try {
                    billReadModel.setNo(j + 1);
                    billReadModel.setSubject(readCellStringValue(row.getCell(getColumnNo(SUBJECT))));
                    billReadModel.setName(readCellStringValue(row.getCell(getColumnNo(NAME))));
                    billReadModel.setIdcardNo(readCellStringValue(row.getCell(getColumnNo(IDCARD_NO))));
                    billReadModel.setPhoneNo(readCellStringValue(row.getCell(getColumnNo(PHONE_NO))));
                    billReadModel.setServiceYearMonth(readCellStringValue(row.getCell(getColumnNo(SERVICE_YEAR_MONTH))));
                    String provinceName = readCellStringValue(row.getCell(getColumnNo(SOIN_DISTRICT_PROVINCE)));
                    String cityName = readCellStringValue(row.getCell(getColumnNo(SOIN_DISTRICT_CITY)));
                    List<String> soinDistrictList = new ArrayList<>();
                    if (isNotBlank(provinceName)) {
                        soinDistrictList.add(provinceName);
                    }
                    if (isNotBlank(cityName)) {
                        soinDistrictList.add(cityName);
                    }
                    billReadModel.setSoinDistrict(join(soinDistrictList, "-"));//soinDistrict是“省-市县”
                    billReadModel.setSoinType(readCellStringValue(row.getCell(getColumnNo(SOIN_TYPE))));
                    billReadModel.setPayYearMonth(readCellStringValue(row.getCell(getColumnNo(PAY_YEAR_MONTH))));
                    billReadModel.setBackStartYearMonth(readCellStringValue(row.getCell(getColumnNo(SOIN_BACK_START_YEAR_MONTH))));//补缴开始年月
                    billReadModel.setBackEndYearMonth(readCellStringValue(row.getCell(getColumnNo(SOIN_BACK_END_YEAR_MONTH))));//补缴结束年月
                    billReadModel.setModify(readCellStringValue(row.getCell(getColumnNo(MODIFY))));
                    billReadModel.setSoinBase(readCellBigDecimalValue(row.getCell(getColumnNo(SOIN_BASE))));
                    billReadModel.setHousefundBase(readHouseFundCellBigDecimalValue(row.getCell(getColumnNo(HOUSEFUND_BASE))));
                    billReadModel.setHousefundPercent(readCellBigDecimalValue(row.getCell(getColumnNo(HOUSEFUND_PERCENT))));
                    billReadModel.setSoinCode(readCellStringValue(row.getCell(getColumnNo(SOIN_CODE))));
                    billReadModel.setHouseFundCode(readCellStringValue(row.getCell(getColumnNo(HOUSEFUND_CODE))));
                    billReadModel.setHukouType(readCellStringValue(row.getCell(getColumnNo(HUKOU_TYPE))));
                    billReadModel.setHukouDistrict(readCellStringValue(row.getCell(getColumnNo(HUKOU_DISTRICT))));
                    billReadModel.setCollectionServiceFee(readCellBigDecimalValue(row.getCell(getColumnNo(COLLECTION_FEE))));
                    billReadModel.setChargeServiceFee(readCellBigDecimalValue(row.getCell(getColumnNo(CHARGE_FEE))));
                    billReadModel.setSalesman(readCellStringValue(row.getCell(getColumnNo(SALESMAN))));
                    if (row.getPhysicalNumberOfCells() == SOIN_ORDER_BILL_CALCULATE_FILE_ROW_NAMES.length) {
                        billReadModel.setPostponeMonth(readCellStringValue(row.getCell(getColumnNo(POSTPONE_MONTH))));
                        billReadModel.setTemplateType("personal");
                    } else {
                        billReadModel.setTemplateType("company");
                    }
                    if (isNotBlank(billReadModel.getSubject())) {

                        soinOrderBillExcelReadModels.add(billReadModel);
                    }
                } catch (Exception e) {
                    importMessage.appendErrorMsg(j, "读取第" + (j - 1) + "记录出错,请检查。");
                    elog.error("【对账单导入模板读取" + (j - 1) + "行】", e);
                }
            }
        } catch (Exception e) {
            importMessage.appendErrorMsg(-1, "读取文件内容出错,请检查。");
            elog.error("【对账单导入模板读取错误】", e);
        }
        return readResult;
    }


    /**
     * 读取BigDecimal内容的Cell
     *
     * @param cell
     * @return
     */
    private BigDecimal readCellBigDecimalValue(Cell cell) {
        if (cell == null) {
            return new BigDecimal("0").setScale(2);
        }
        try {
            return new BigDecimal(Double.toString(cell.getNumericCellValue())).setScale(2, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            String value = cell.getStringCellValue();
            if (StringUtils.isAnyBlank(value)) {
                return new BigDecimal("0").setScale(2);
            }
            try {
                return new BigDecimal(value).setScale(2, BigDecimal.ROUND_DOWN);
            } catch (Exception e2) {
                return NUMBER_INVALID_FORMAT;
            }
        }
    }

    /**
     * 读取公积金BigDecimal内容的Cell
     *
     * @param cell
     * @return
     */
    private BigDecimal readHouseFundCellBigDecimalValue(Cell cell) {
        if (cell == null) {
            return new BigDecimal("0").setScale(2);
        }
        try {
            String value = cell.getStringCellValue();
            if (StringUtils.isAnyBlank(value)) {
                return new BigDecimal("0").setScale(2);
            }
            BigDecimal value2 = new BigDecimal(value).setScale(2, BigDecimal.ROUND_DOWN);
            if (value2.compareTo(SoinUtil.ZERO) == 0) {
                return null;
            }
            return value2;
        } catch (Exception e) {
            try {
                BigDecimal value = new BigDecimal(Double.toString(cell.getNumericCellValue())).setScale(2, BigDecimal.ROUND_DOWN);
                if (value.compareTo(SoinUtil.ZERO) == 0) {
                    return null;
                }
                return value;
            } catch (Exception e2) {
                return NUMBER_INVALID_FORMAT;
            }
        }
    }
}
