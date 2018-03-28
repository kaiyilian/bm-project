package com.bumu.arya.admin.corporation.constant;

public class CorpConstants {
    /**
     * 公司类型
     */
    public static final int CORP_GROUP = 1;
    public static final int CORP_FIRST_LEVEL_CORP = 4;
    public static final int CORP_SUB_CORP = 2;
    public static final int CORP_DEPARMENT = 3;

    //企业业务类型
    public static final int CORP_BUSINESS_SOIN = 1;//社保业务
    public static final int CORP_BUSINESS_SALARY = 2;//薪资代发
    public static final int CORP_BUSINESS_RUZHI = 4;//一键入职
    public static final int CORP_BUSINESS_WELFARE = 8;//福库
    public static final int CORP_BUSINESS_ATTENDANCE = 16;//考勤
    public static final int CORP_BUSINESS_ECONTRACT = 32;//电子合同
    public static final int CORP_BUSINESS_WALLETPAYS = 64;//钱包发薪

    /**
     *
     */
    public static final String DEFAULT_ROLE_NAME = "admin";
    /**
     *
     */
    public static final String DEFAULT_ROLE_DESC = "企业管理员";

    //组织类型
	public static final int CORP_TYPE_GROUP = 1;//集团
    public static final int CORP_TYPE_SUBCORP = 2;//子公司
    public static final int CORP_TYPE_ARYADEPARTMENT = 3;//通用部门
    public static final int CORP_TYPE_TOP_LEAVER_CORP = 4;//一级公司
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final String SYNC_URL ="WORK_ATTENDACE_SYNC_URL" ;
}
