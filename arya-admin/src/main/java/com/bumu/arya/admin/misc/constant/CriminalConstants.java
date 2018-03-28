package com.bumu.arya.admin.misc.constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liangjun on 2017/3/9.
 */
public class CriminalConstants {

    public static final String NAME = "姓名";

    public static final String IDCARDNO = "身份证";

    public static final String[] CRIMINAL_RECORD_FILE_ROW_NAMES = {NAME,IDCARDNO};
    public static final List<String> ROW_NAME_LIST = Arrays.asList(CRIMINAL_RECORD_FILE_ROW_NAMES);

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
