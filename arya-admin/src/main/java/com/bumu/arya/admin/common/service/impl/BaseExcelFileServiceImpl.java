package com.bumu.arya.admin.common.service.impl;

import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public class BaseExcelFileServiceImpl extends BaseBumuService {


    /**
     * 读取字符串内容cell
     *
     * @param cell
     * @return
     */
    protected String readCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String value;
        try {
            value = cell.getStringCellValue();
        } catch (Exception e) {
            value = Double.toString(cell.getNumericCellValue());
        }

        if (value != null) {
            value = value.replace(" ", "");
        }

        if (StringUtils.isAnyBlank(value)) {
            return null;
        }
        return value;
    }

}
