package com.bumu.arya.admin.misc.service.impl;

import com.bumu.arya.IdcardValidator;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.impl.BaseExcelFileServiceImpl;
import com.bumu.arya.admin.misc.constant.CriminalConstants;
import com.bumu.arya.admin.misc.model.ImportMessage;
import com.bumu.arya.admin.misc.model.CriminalRecordExcelModel;
import com.bumu.arya.admin.misc.result.CriminalRecordExcelReadResult;
import com.bumu.arya.admin.misc.service.CriminalFileService;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.util.StringUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bumu.arya.admin.misc.constant.CriminalConstants.CRIMINAL_RECORD_FILE_ROW_NAMES;

@Service
public class CriminalFileServiceImpl extends BaseExcelFileServiceImpl implements CriminalFileService {
    @Autowired
    AryaAdminConfigService configService;

    @Autowired
    SysUserService sysUserService;

    @Override
    public String saveCriminalRecordExcelFile(Workbook workbook, String fileName) {
        try {
            OutputStream copyOut = new FileOutputStream(configService.getCriminalRecordExcelExportPath() + File.separatorChar + fileName);
            workbook.write(copyOut);
            copyOut.flush();
            copyOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public String saveCriminalRecorExcelFile(MultipartFile file) throws AryaServiceException {
        SysUserModel currentUser = sysUserService.getCurrentSysUser();

        String dirPath = configService.getCriminalRecordExcelUploadPath() + currentUser.getId() + File.separator;

        return saveExcelFile(file, dirPath);

    }


    private String saveExcelFile(MultipartFile file, String dirPath) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String fileName = file.getOriginalFilename().split(".xls")[0] + "-" + dateFormater.format(date) + ".xls";
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
    public CriminalRecordExcelReadResult readCriminalRecordExcelFile(String fileName) throws Exception {
        CriminalRecordExcelReadResult excelReadResult = new CriminalRecordExcelReadResult();
        List<CriminalRecordExcelModel> recordExcelModels = new ArrayList<>();
        ImportMessage importMessage = new ImportMessage();
        excelReadResult.setModels(recordExcelModels);
        excelReadResult.setImportMessage(importMessage);


        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        //读文件
        FileInputStream excelFileInputStream = null;
        try {
            excelFileInputStream = new FileInputStream(configService.getCriminalRecordExcelUploadPath() + currentUser.getId() + File.separator + fileName);
            //得到Excel工作簿对象
            XSSFWorkbook wb = new XSSFWorkbook(excelFileInputStream);
            //遍历Excel工作表对象
            Sheet sheet = wb.getSheetAt(0);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            //只有表头没有内容
            if (physicalNumberOfRows <= 1) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "犯罪记录模板内容为空");
            }
            //查询的内容超过100条
            if (physicalNumberOfRows > 101) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "查询数据超过100条");
            }


            //遍历Excel工作表的行
            for (int j = 0; j < physicalNumberOfRows; j++) {
                Row row = sheet.getRow(j);
                //判断表头是否是犯罪记录查询
                if (j == 0) {
                    if (row.getPhysicalNumberOfCells() != CRIMINAL_RECORD_FILE_ROW_NAMES.length && row.getPhysicalNumberOfCells() != CRIMINAL_RECORD_FILE_ROW_NAMES.length - 1) {
                        importMessage.appendErrorMsg(j, "犯罪记录模板不正确,表头不正确。");
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "犯罪记录模板不正确,表头不正确");
                    }
                    continue;
                }

                CriminalRecordExcelModel criminalRecordData = new CriminalRecordExcelModel();

                int indexNo = j + 1;
                String name = readCellStringValue(row.getCell(CriminalConstants.getColumnNo(CriminalConstants.NAME)));
                criminalRecordData.setName(name);
                String idCardNo = readCellStringValue(row.getCell(CriminalConstants.getColumnNo(CriminalConstants.IDCARDNO)));
                criminalRecordData.setIdCardNo(idCardNo);


                String name1 = criminalRecordData.getName();
                String idCardNo1 = criminalRecordData.getIdCardNo();
                if (StringUtil.isEmpty(name1) && StringUtil.isEmpty(idCardNo1)) {
                    continue;
                }

                if (StringUtil.isEmpty(name1)) {
                    String errorName = String.format("导入摸板 第【%s】行，姓名输入为空", indexNo);
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, errorName);

                } else if (name1.length() > 8) {
                    String errorName = String.format("导入摸板 第【%s】行，姓名【%s】长度超过8个", indexNo, name1);
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, errorName);

                } else if (StringUtil.isEmpty(idCardNo1)) {
                    String errorName = String.format("导入摸板 第【%s】行，姓名为[%s]的身份证号为空", indexNo, name1);
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, errorName);

                } else if (!IdcardValidator.isValidatedAllIdcard(idCardNo1)) {//验证身份证
                    String errorName = String.format("导入摸板 第【%s】行，姓名为[%s]的身份证号书写错误", indexNo, name1);
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, errorName);
                }
                recordExcelModels.add(criminalRecordData);
            }
        } catch (AryaServiceException e) {
            throw e;
        } catch (Exception e) {
            importMessage.appendErrorMsg(-1, "读取文件内容出错,请检查。");
            elog.error("【犯罪记录查询导入模板读取错误】", e);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "犯罪记录查询导入模板读取错误");
        }

        return excelReadResult;
    }


}
