/**
 * Created by CuiMengxin on 2016/8/2.
 * url集合
 */

//var $host = "http://192.168.13.248:9015/arya-admin/";//测试服务器
var $host = "";//本地

var urlGroup = {

    //公用
    file_upload: $host + "admin/common/file/upload",
    //客户列表 - 左侧树结构 GET
    customer_list_tree_url: $host + "salary/customer",

    //

    /**
     * 登录页
     * */

    //登录url
    // login_url: $host + "signin",
    //获取验证码
    // captcha_url: $host + "captcha/user",
    //退出登录
    login_out_url: $host + "signout",


    /**
     *  立项申请
     */

    //立项申请 - 页面
    project_apply_page: $host + "salary/project/apply/index",
    //公司列表 GET
    project_apply_corp_list: $host + "salary/project",
    //升级为正式客户 PATCH
    project_apply_corp_upgrade: $host + "salary/project/toCustomer",
    //查看立项申请详情 GET
    project_apply_corp_detail: $host + "salary/project/view",


    //公司详情 - 页面
    project_apply_corp_detail_page: $host + "salary/project/apply/detail/index",
    //跟进记录 列表   GET
    project_apply_operate_record_list: $host + "salary/project/customerFollows",
    //跟进记录 新增   POST
    project_apply_operate_record_add: $host + "salary/project/addFollowRecord",
    //更新 立项申请   PUT
    project_apply_update: $host + "salary/project",

    //新建公司 - 页面
    project_apply_corp_create_page: $host + "salary/project/apply/create/index",
    //立项申请 - 新增 POST
    project_apply_add: $host + "salary/project",


    /**
     *  薪资管理
     */

    //薪资计算

    //页面
    salary_calc_page: $host + "salary/calculate/base/index",
    //薪资导入 - 左侧树结构
    salary_calc_tree_url: $host + "salary/calculate/base/customer",

    //薪资 - 导出   POST
    //导出分类（1.导出薪资数据 2.导出统计结果 3.导出开票申请 4.导出发票回执单）
    salary_export: $host + "salary/calculate/file/export",

    //预览 - 上传文件计算，获取计算结果 POST
    salary_result_get_by_upload: $host + "salary/calculate/base/preview",
    //薪资导入 POST
    salary_import: $host + "salary/calculate/base/importSalary",
    //模板下载 GET
    salary_template_down: $host + "salary/calculate/base/file/download/template/url",


    //根据年月 获取批次列表 GET
    salary_calc_batch_list: $host + "salary/calculate/base/weeks",
    //薪资查询  GET
    salary_query: $host + "salary/calculate/base/query",


    //用户信息修改 POST
    salary_user_info_modify: $host + "salary/calculate/base/user/update",
    //薪资计算结果 删除 POST
    salary_calc_result_del: $host + "salary/calculate/delete",

    //确认扣款 POST
    salary_deduct_sure: $host + "salary/calculate/deduct",


    /**
     *  客户管理
     */

    //客户管理 - 页面
    customer_manage_page: $host + "salary/customer/index",

    //客户管理 - 客户信息 GET
    customer_detail: $host + "salary/customer/view",
    //客户管理 - 薪资规则 GET
    rule_in_customer_manage: $host + "salary/customer/rule/get",

    //上传 合同 POST
    upload_contract: $host + "salary/customer/contract/upload",
    //合同图片 列表 GET
    contract_img_list: $host + "salary/customer/contract/list",
    //合同图片 删除 POST
    contract_img_del: $host + "salary/customer/contract/delete",
    //合同图片 下载 GET
    contract_down: $host + "salary/customer/contract/download/url",


    //充值
    customer_recharge: $host + "salary/account/recharge",
    //薪资规则 保存 POST
    rule_save_in_customer_manage: $host + "salary/rule/save",

    //客户详情 - 页面
    customer_detail_page: $host + "salary/customer/detail/index",
    //更新 客户 PUT
    customer_detail_update: $host + "salary/customer",
    //客户管理 - 跟进记录 GET
    record_list_in_customer_manage: $host + "salary/customer/customerFollows",
    //新增跟进记录 POST
    record_add_in_customer_manage: $host + "salary/customer/addFollowRecord",

    //客户资料

    //页面
    customer_info_page: $host + "salary/customerInfo/page/index",
    //客户资料 - 列表 GET
    customer_info_list: $host + "salary/customerInfo/page",
    //客户资料 - 更新 POST
    customer_info_update: $host + "salary/customerInfo/update",
    //客户资料 - 导出 POST
    customer_info_down: $host + "salary/customerInfo/export",

    /**
     *  台账管理
     */

    ledger_manage_page: $host + "salary/customer/account/index",
    //台账列表 GET
    ledger_list: $host + "salary/customer/account/page",
    //更新台账 POST
    ledger_update: $host + "salary/customer/account/update",
    //台账 下载 POST
    ledger_down: $host + "salary/customer/account/export",

    /**
     *  台账汇总
     */

    ledger_summary_page: $host + "salary/customer/account/total/index",
    //台账汇总列表 GET
    ledger_summary_list: $host + "salary/customer/account/total",
    //台账汇总列表 导出 POST
    ledger_summary_export: $host + "salary/customer/account/total/export",

    /**
     *  薪资操作反馈记录
     */

    //页面
    salary_operate_record_page: $host + "salary/calculate/errLog/index",
    //列表 GET
    salary_operate_record_list: $host + "salary/calculate/errLog/list",
    //删除 POST
    salary_operate_record_del: $host + "salary/calculate/errLog/delete",
    //保存 POST
    salary_operate_record_update: $host + "salary/calculate/errLog/update",
    //导出 POST
    salary_operate_record_export: $host + "salary/calculate/errLog/export",

    /**
     *  开票记录
     */

    //页面
    bill_record_page: $host + "salary/bill/index",
    //列表 GET
    bill_record_list: $host + "salary/bill/pager",
    //保存 POST
    bill_record_save: $host + "salary/bill/update",
    //导出 POST
    bill_record_export: $host + "salary/bill/export",
    //删除 POST
    bill_record_del: $host + "salary/bill/delete",

};
