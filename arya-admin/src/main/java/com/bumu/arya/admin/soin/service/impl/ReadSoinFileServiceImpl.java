/* @author CuiMengxin
 * @date 2015/11/25
 */
package com.bumu.arya.admin.soin.service.impl;


import com.bumu.arya.admin.soin.result.SoinImportResult;
import com.bumu.arya.admin.soin.service.ReadSoinFileService;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReadSoinFileServiceImpl extends BaseBumuService implements ReadSoinFileService {

    @Override
    public List<SoinImportResult.SoinOutputBean> readSalaryExcel(InputStream inputStream) {
        List<SoinImportResult.SoinOutputBean> excelDatas = new ArrayList<>();//Excel导入数据信息
        try {
            POIFSFileSystem poiFs = new POIFSFileSystem(inputStream);
            HSSFWorkbook wb = new HSSFWorkbook(poiFs);
            String sheetNAme = wb.getSheetName(0);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 得到总行数
            int rowNum = sheet.getLastRowNum();

            for (int i = 1; i <= rowNum; i++) {
                SoinImportResult.SoinOutputBean outputBean = new SoinImportResult.SoinOutputBean();

                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setRealName(row.getCell(0).getStringCellValue());//姓名

                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCardId(row.getCell(1).getStringCellValue());//身份证

                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPhone(row.getCell(2).getStringCellValue());//手机号码

                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setMonth(row.getCell(5).getStringCellValue());//月份

                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setSoinBase(row.getCell(6).getStringCellValue());//社保缴纳基数

                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setHouseFundBase(row.getCell(7).getStringCellValue());//公积金缴纳基数

                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalPension(row.getCell(8).getStringCellValue());//个人-养老

                row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalUnemployment(row.getCell(9).getStringCellValue());//个人-失业

                row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalMedical(row.getCell(10).getStringCellValue());//个人-医疗

                row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalInjury(row.getCell(11).getStringCellValue());//个人-工伤

                row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalPregnancy(row.getCell(12).getStringCellValue());//个人-生育

                row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setPersonalHouseFund(row.getCell(13).getStringCellValue());//个人-住房

                row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyPension(row.getCell(15).getStringCellValue());//企业-养老

                row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyUnemployment(row.getCell(16).getStringCellValue());//企业-失业

                row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyMedical(row.getCell(17).getStringCellValue());//企业-医疗

                row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyInjury(row.getCell(18).getStringCellValue());//企业-工伤

                row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyPregnancy(row.getCell(19).getStringCellValue());//企业-生育

                row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setCompanyHouseFund(row.getCell(20).getStringCellValue());//企业-住房

                row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setDisability(row.getCell(22).getStringCellValue());//残保

                row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setFees(row.getCell(23).getStringCellValue());//管理费

                row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
                outputBean.setTotalPayment(row.getCell(24).getStringCellValue());//总金额

                excelDatas.add(outputBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelDatas;
    }

    /**
     * 薪资check
     *
     * @param list
     * @return
     */
    public void checkInsert(List<SoinImportResult.SoinOutputBean> list, List<SoinImportResult.SoinErrorMsgBean> errorMsgs) {
        if (list != null && list.size() > 0) {
            Set<String> cardSet = new HashSet<>();
            Set<String> phoneSet = new HashSet<>();

            for (int i = 0; i < list.size(); i++) {
                SoinImportResult.SoinOutputBean inputBean = list.get(i);
                cardSet.add(inputBean.getCardId());
                phoneSet.add(inputBean.getPhone());

                if (StringUtil.isEmpty(inputBean.getRealName())) {
                    putMsgList(errorMsgs, (i + 1) + "", "姓名不能为空");
                }
                if (StringUtil.isEmpty(inputBean.getCardId())) {
                    putMsgList(errorMsgs, (i + 1) + "", "身份证不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getCardId()) && !StringUtil.isCardId(inputBean.getCardId())) {
                    putMsgList(errorMsgs, (i + 1) + "", "身份证格式不正确");
                }
                if (StringUtil.isEmpty(inputBean.getPhone())) {
                    putMsgList(errorMsgs, (i + 1) + "", "手机号不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getPhone()) && !StringUtil.isMobileNumber(inputBean.getPhone())) {
                    putMsgList(errorMsgs, (i + 1) + "", "手机号格式不正确");
                }
                if (StringUtil.isEmpty(inputBean.getMonth())) {
                    putMsgList(errorMsgs, (i + 1) + "", "月份不能为空");
                }
                if (!StringUtil.isEmpty(inputBean.getMonth()) && DateTimeUtils.checkMonth(inputBean.getMonth()) == null) {
                    putMsgList(errorMsgs, (i + 1) + "", "月份格式不正确(2015.09)");
                }

                if (StringUtil.isEmpty(inputBean.getSoinBase())) {
                    putMsgList(errorMsgs, (i + 1) + "", "社保缴纳基数不能为空");
                }

                if (!StringUtil.isEmpty(inputBean.getSoinBase()) && !StringUtil.checkNum(inputBean.getSoinBase())) {
                    putMsgList(errorMsgs, (i + 1) + "", "社保缴纳基数格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getHouseFundBase()) && !StringUtil.checkNum(inputBean.getHouseFundBase())) {
                    putMsgList(errorMsgs, (i + 1) + "", "公积金缴纳基数格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalPension()) && !StringUtil.checkNum(inputBean.getPersonalPension())) {
                    putMsgList(errorMsgs, (i + 1) + "", "养老（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalUnemployment()) && !StringUtil.checkNum(inputBean.getPersonalUnemployment())) {
                    putMsgList(errorMsgs, (i + 1) + "", "失业（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalMedical()) && !StringUtil.checkNum(inputBean.getPersonalMedical())) {
                    putMsgList(errorMsgs, (i + 1) + "", "医疗（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalInjury()) && !StringUtil.checkNum(inputBean.getPersonalInjury())) {
                    putMsgList(errorMsgs, (i + 1) + "", "工伤（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalPregnancy()) && !StringUtil.checkNum(inputBean.getPersonalPregnancy())) {
                    putMsgList(errorMsgs, (i + 1) + "", "生育（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getPersonalHouseFund()) && !StringUtil.checkNum(inputBean.getPersonalHouseFund())) {
                    putMsgList(errorMsgs, (i + 1) + "", "公积金（个人）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyPension()) && !StringUtil.checkNum(inputBean.getCompanyPension())) {
                    putMsgList(errorMsgs, (i + 1) + "", "养老（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyUnemployment()) && !StringUtil.checkNum(inputBean.getCompanyUnemployment())) {
                    putMsgList(errorMsgs, (i + 1) + "", "失业（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyMedical()) && !StringUtil.checkNum(inputBean.getCompanyMedical())) {
                    putMsgList(errorMsgs, (i + 1) + "", "医疗（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyInjury()) && !StringUtil.checkNum(inputBean.getCompanyInjury())) {
                    putMsgList(errorMsgs, (i + 1) + "", "工伤（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyPregnancy()) && !StringUtil.checkNum(inputBean.getCompanyPregnancy())) {
                    putMsgList(errorMsgs, (i + 1) + "", "生育（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getCompanyHouseFund()) && !StringUtil.checkNum(inputBean.getCompanyHouseFund())) {
                    putMsgList(errorMsgs, (i + 1) + "", "公积金（企业）格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getDisability()) && !StringUtil.checkNum(inputBean.getDisability())) {
                    putMsgList(errorMsgs, (i + 1) + "", "残保格式不正确");
                }

                if (!StringUtil.isEmpty(inputBean.getFees()) && !StringUtil.checkNum(inputBean.getFees())) {
                    putMsgList(errorMsgs, (i + 1) + "", "管理费格式不正确");
                }

                if (StringUtil.isEmpty(inputBean.getTotalPayment())) {
                    putMsgList(errorMsgs, (i + 1) + "", "总计缴纳金额不能为空");
                }

                if (!StringUtil.isEmpty(inputBean.getTotalPayment()) && !StringUtil.checkNum(inputBean.getTotalPayment())) {
                    putMsgList(errorMsgs, (i + 1) + "", "总计缴纳金额格式不正确");
                }
            }

            if (errorMsgs.size() == 0) {
                if (list.size() > cardSet.size() || list.size() > phoneSet.size()) {
                    putMsgList(errorMsgs, "", "文件中有重复数据（身份证或手机号）");
                }
            }
        } else {
            putMsgList(errorMsgs, "", "薪资记录为空");
        }
    }

    public void putMsgList(List<SoinImportResult.SoinErrorMsgBean> list, String columnNo, String msg) {
        SoinImportResult.SoinErrorMsgBean bean = new SoinImportResult.SoinErrorMsgBean();
        bean.setColumnNo(columnNo);
        bean.setMsg(msg);
        list.add(bean);
    }
}
