/**
 * Created by CuiMengxin on 2016/8/2.
 * url集合
 */

//var $host = "http://192.168.13.248:9015/arya-admin/";//测试服务器
var $host = "";//本地

//所有的头部 host集合
var host_list = {

    //运营管理
    operation: {
        activity_register: $host + "admin/activity/enroll/",
        women_day_activity: $host + "admin/activity/womens/", //3.8活动
        wallet_change_record: $host + "admin/wallet/change/", //3.8活动
    },

    //运维管理
    operation_manage: {
        log: $host + "admin/online/log/",
        access_statistics: $host + "admin/stats/visit/",
        access_detail: $host + "admin/detail/visit/"
    },

    //用户管理
    user_manage: {
        app_user_manage: $host + "admin/user/",    //app用户信息管理
        user_info: $host + "admin/user/info/",                  //用户信息查询
        wallet: $host + "admin/wallet/cnt/",                  //钱包用户查询
    },

    //官网管理
    bumu_website_manage: {
        recruit_manage: $host + "admin/officialwebsite/recruit/",        //招聘管理
        news_manage: $host + "admin/officialwebsite/news/"            //新闻管理
    },

    //组件管理
    assembly_manage: {
        assembly_list: $host + "admin/assemblyManage/assembly/"        //组件列表
    }


};

var urlGroup = {

    //公用 上传
    file_upload: $host + "admin/common/file/upload",

    /**
     * 登录页
     * */

    //登录url
    login_url: $host + "signin",
    forget_pwd_url: $host + "forget_pwd",
    change_pwd_url: $host + "change_pwd",
    //获取验证码
    captcha_url: $host + "captcha/user",

    // 首页

    //页面
    index_manage_page: $host + "admin/index/manage/page",


    /**
     * 社保管理
     * */

    //公共

    //已开通 社保地区的数结构
    soin_district_tree_url: $host + "admin/soin/district/tree",
    //查询地区下所有社保类型
    soin_type_all_list: $host + "admin/soin/district/type_all",

    //参保人管理

    //页面
    soin_person_page: $host + "admin/soin/person/index",
    //参保人 列表
    soin_person_list: $host + "admin/soin/person/list",
    //参保人 详情
    soin_person_detail: $host + "admin/soin/person",
    //参保人 信息修改
    soin_person_modify: $host + "admin/soin/person/update",

    //个人社保订单处理

    //页面
    soin_order_manage_page: $host + "admin/soin/order/manage/index",
    //社保订单 列表
    soin_order_list: $host + "admin/soin/order/manage/list/handle",
    //社保订单 详情
    soin_order_detail: $host + "admin/soin/order/manage/detail",
    //社保 业务员 列表
    soin_salesman_list: $host + "admin/soin/order/manage/salesman/list",
    //社保 供应商 列表
    soin_supplier_list: $host + "admin/soin/order/manage/suppliers/list",
    //设置 业务员和供应商
    soin_salesman_and_supplier_set: $host + "admin/soin/order/manage/suppliers/list",

    //社保订单 已支付
    soin_order_payment_complete: $host + "admin/soin/order/manage/payment_complete",
    //社保订单 退款中
    soin_order_refunding: $host + "admin/soin/order/manage/refunding",
    //社保订单 已退款
    soin_order_refund_complete: $host + "admin/soin/order/manage/refund_complete",
    //社保订单 缴纳某月
    soin_order_partial_complete: $host + "admin/soin/order/manage/partial_complete",
    //社保订单 缴纳中
    soin_order_underway: $host + "admin/soin/order/manage/underway",
    //社保订单 已停缴
    soin_order_stop: $host + "admin/soin/order/manage/stop",
    //社保订单 补款
    soin_order_supply: $host + "admin/soin/order/manage/supply",
    //社保订单 已完成
    soin_order_complete: $host + "admin/soin/order/manage/complete",
    //社保订单 订单异常
    soin_order_exception: $host + "admin/soin/order/manage/exception",
    //社保订单 订单异常恢复
    soin_order_recover: $host + "admin/soin/order/manage/recover",
    //社保订单 取消订单
    soin_order_cancel: $host + "admin/soin/order/manage/cancel",
    //社保订单 查询订单剩余金额
    soin_order_residual_amount: $host + "admin/soin/order/manage/residual_amount",


    //个人社保订单查询

    //页面
    soin_order_query_page: $host + "admin/soin/order/query/index",
    //社保订单查询 列表
    soin_order_query_list: $host + "admin/soin/order/query/list",
    //查询订单详情URL
    soin_order_query_detail: $host + "admin/soin/order/query/detail",


    //社保 基础数据

    soin_base_info_page: $host + "admin/soin/base_info/index",
    //获取 未开通社保地区
    soin_not_open_district_url: $host + "admin/soin/district/na",
    //新增社保开通地区
    soin_district_add: $host + "admin/soin/district/create",
    //删除 已开通社保地区
    soin_district_del: $host + "admin/soin/district/delete",
    //向上并列 社保地区
    soin_area_Up: $host + "admin/soin/district/up",
    //取消向上并列 社保地区
    soin_area_Up_Cancel: $host + "admin/soin/district/up/cancel",
    //导出
    soin_district_export: $host + "admin/soin/district/detail/export",
    //判断用户是否有修改权限
    check_user_jurisdiction: $host + "admin/soin/base_info/change/auth",

    //获取 社保类型列表
    soin_type_list: $host + "admin/soin/district/type",
    //获取 社保类型 详情
    soin_type_detail: $host + "admin/soin/district/type/detail",
    //保存 社保类型详情
    soin_type_detail_save: $host + "admin/soin/district/type/detail/update",
    //新增 社保类型
    soin_type_del: $host + "admin/soin/district/type/delete",
    //新增 社保类型
    soin_type_add: $host + "admin/soin/district/type/create",

    //社保类型 版本 （正常）

    //获取 社保类型 版本列表（正常）
    soin_type_version_normal_list: $host + "admin/soin/district/type/versions",
    //获取 社保类型 版本详情（正常）
    soin_type_version_normal_detail: $host + "admin/soin/district/type/version/detail",
    //社保类型 版本 新增（正常）
    soin_type_version_normal_add: $host + "admin/soin/type/version/create",
    //社保类型 版本 更新（正常）
    soin_type_version_normal_save: $host + "admin/soin/type/version/detail/update",

    //社保类型 版本 （补缴）

    //获取 社保类型 版本列表（补缴）
    soin_type_version_back_list: $host + "admin/soin/district/type/back/versions",
    //获取 社保类型 版本详情（补缴）
    soin_type_version_back_detail: $host + "admin/soin/district/type/back/version/detail",
    //社保类型 版本 新增（补缴）
    soin_type_version_back_add: $host + "admin/soin/type/back/version/create",
    //社保类型 版本 更新（补缴）
    soin_type_version_back_save: $host + "admin/soin/type/back/version/detail/update",

    //社保类型 版本 删除
    soin_type_version_del: $host + "admin/soin/type/version/delete",

    /**
     * 社保对账管理
     */

    //订单计算导入

    //页面
    order_import_index: $host + "admin/soin/order/import/index",
    //订单导入 - 模板下载
    order_import_template_down_url: $host + "admin/soin/bill/manage/export/template/download",
    //订单导入 - 计算导入的订单
    order_calc_by_import_url: $host + "admin/soin/order/import/calculate",
    //订单导入 -  确认导入
    order_import_sure_url: $host + "admin/soin/order/import/confirm",
    //批次 导出
    order_export_batch: $host + "admin/soin/bill/manage/export/batch",

    //订单对账导出、订单导出、订单管理

    //页面
    order_manage_index: $host + "admin/soin/bill/manage/index",
    //订单管理 - 客户列表
    order_manage_cus_list_get_url: $host + "admin/soin/bill/manage/customer/list",
    //订单管理 - 已开通参保地区列表
    order_manage_district_list_get_url: $host + "admin/soin/bill/manage/soin_district/tree",
    //订单管理 - 供应商列表
    order_manage_supplier_list_get_url: $host + "admin/soin/bill/manage/suppliers/list",
    //订单管理 - 订单查询
    order_manage_list_get_url: $host + "admin/soin/bill/manage/query",
    //订单管理 - 增减员查询
    order_in_or_decrease_list_get_url: $host + "admin/soin/in_or_decrease/query",
    //订单管理 - 其他费用 更新
    order_manage_other_pay_update: $host + "admin/soin/bill/manage/other_payment/adjust",
    //订单管理 - 订单缴纳失败
    order_manage_order_pay_fail_url: $host + "admin/soin/bill/manage/pay_failed",
    //订单管理 - 订单缴纳失败
    order_manage_order_pay_fail_all_url: $host + "admin/soin/bill/manage/pay_failed/all",
    //订单管理 - 订单缴纳成功
    order_manage_order_pay_success_url: $host + "admin/soin/bill/manage/pay_succeed",
    //订单管理 - 所有订单缴纳成功
    order_manage_order_pay_success_all_url: $host + "admin/soin/bill/manage/pay_succeed/all",
    //订单管理 - 订单导出
    order_manage_export_url: $host + "admin/soin/bill/manage/export",
    //订单管理 - 增减员导出
    order_in_or_decrease_export_url: $host + "admin/soin/in_or_decrease/export",
    //订单管理 - 订单删除
    order_manage_del_url: $host + "admin/soin/bill/manage/delete",
    //订单 详情 - 月份列表
    order_detail_month_list: $host + "admin/soin/bill/manage/detail/list",
    //订单 详情
    order_detail_info: $host + "admin/soin/bill/manage/detail/query",
    //订单管理 - 批量修改（获取第一条数据 5个险种信息）
    order_modify_one_data: $host + "admin/order/batch/get/one/detail",
    //订单管理 - 批量修改（保存）
    order_save_by_modify: $host + "admin/order/batch/update",

    //增减员导出

    //页面
    customer_add_del_export_index: $host + "admin/customer_add_del_export/index",

    //订单批量删除

    //页面
    order_batch_del_index: $host + "admin/soin/bill/batch/manage/delete/v2/index",
    //订单列表
    order_batch_del_list: $host + "admin/soin/order/batch/manage/delete/v2/query",
    //订单 批量删除(订单按照查询条件批量删除)
    order_batch_del_by_search: $host + "admin/soin/order/batch/manage/delete/v2/bycommand",
    //订单 批量删除(按照id数组批量删除)
    order_batch_del_by_ids: $host + "admin/soin/order/batch/manage/delete/v2/byids",

    //订单批次删除	暂时不用
    //因为有订单批次顺延，删除后会有问题

    //页面
    order_batch_manage_index: $host + "admin/soin/order/batch/manage/index",
    //订单批次删除 - 业务员列表
    order_batch_manage_salesman_list_get_url: $host + "admin/soin/order/batch/manage/salesman/list",
    //订单批次删除 - 业务员批次列表
    order_batch_manage_batch_list_get_url: $host + "admin/soin/order/batch/manage/salesman/batch_query",
    //订单批次删除 - 订单批次列表查询
    order_batch_manage_order_list_get_url: $host + "admin/soin/order/batch/manage/salesman/order_list",
    //订单批次删除 - 订单批次列表删除
    order_batch_manage_del_url: $host + "admin/soin/order/batch/manage/delete",

    //订单批量顺延

    //页面
    order_batch_extend_index: $host + "admin/soin/order/bill/extend/index",
    //获取客户列表
    order_batch_extend_custom_list: $host + "admin/soin/bill/manage/customer/list",
    //订单 批量顺延 - 查询
    order_batch_extend_list: $host + "admin/soin/bill/extend/query",
    //订单 批量顺延
    order_batch_extend: $host + "admin/soin/bill/extend/exec",

    //供应商管理

    //页面
    supplier_manage_index: $host + "admin/soin/suppliers/manage/index",
    //增减员导出 - 页面
    soin_in_or_decrease_index: $host + "admin/soin/in_or_decrease/index",
    //供应商管理 - 供应商列表
    supplier_manage_list_get_url: $host + "admin/suppliers/setting/list/all",
    //供应商管理 - 新增供应商
    supplier_manage_add_url: $host + "admin/suppliers/setting/create",
    //供应商管理 - 更新供应商
    supplier_manage_update_url: $host + "admin/suppliers/setting/update",
    //供应商管理 - 删除供应商
    supplier_manage_del_url: $host + "admin/suppliers/setting/delete",
    //供应商管理 - 已开通地区列表
    supplier_manage_district_list_get_url: $host + "admin/suppliers/setting/soin/district/list",
    //供应商管理 - 获取某地区已添加的供应商列表
    supplier_manage_added_supplier_url: $host + "admin/suppliers/setting/district/list",
    //供应商管理 - 获取某地区尚未添加的供应商列表
    supplier_manage_no_add_supplier_url: $host + "admin/suppliers/setting/district/unused/list",
    //供应商管理 - 为某地区添加供应商
    supplier_manage_add_supplier_url: $host + "admin/suppliers/setting/district/add",
    //供应商管理 - 某地区移除供应商
    supplier_manage_del_supplier_url: $host + "admin/suppliers/setting/district/remove",
    //供应商管理 - 某地区设置首选供应商
    supplier_manage_first_supplier_url: $host + "admin/suppliers/setting/district/preferred",


    /**
     *  企业管理
     */

    //企业信息 - 企业列表 （集团、一级公司、二级公司、部门）
    corp_group_tree_url: $host + "admin/corporation/organization/tree",
    //企业用户 - 企业列表
    corp_user_group_tree_url: $host + "admin/entry/organization/tree",
    //企业服务 - 企业列表 （集团、一级公司、二级公司）
    corp_service_tree1_url: $host + "admin/corporation/group/organization/tree",


    //企业用户管理

    //企业用户管理 - 新

    //页面
    corp_user_manage_page: $host + "admin/corporation/corp_user_permission",
    //获取管理员列表
    corp_user_list_url: $host + "admin/corporation/user/list",
    //获取 权限 GET
    corp_user_permission: $host + "admin/corporation/permission/list",
    //保存 权限 POST
    corp_user_permission_save: $host + "admin/corporation/user/permission/adjust",
    //获取企业的入职码和二维码
    corp_check_in_code_and_qrCode: $host + "admin/entry/corporation/check_in_code",
    //保存管理员
    corp_user_add_or_modify: $host + "admin/entry/corporation/admin/create_update",
    //清空 登录次数
    clear_try_login_times: $host + "admin/corporation/user/try_login_times/rest",
    //删除 管理员
    corp_user_del: $host + "admin/corporation/user/delete",

    //企业信息管理 - 页面
    corp_manage_new_page: $host + "admin/corporation/corp_info_manager",
    //公司详情（集团、子公司或一级公司）
    corp_info_detail_new: $host + "admin/corporation/get/detail",
    //公司/集体 新增或修改
    corp_add_or_modify_new: $host + "admin/corporation/create_update",
    //集团/公司 删除
    corp_del_new: $host + "admin/corporation/delete",
    //部门信息 查询
    corp_dept_info_detail_new: $host + "admin/corporation/department/detail",
    //部门信息 编辑
    corp_dept_info_modify_new: $host + "admin/corporation/department/update",
    //部门信息 删除
    corp_dept_del_new: $host + "admin/corporation/department/delete",
    //部门信息 新增
    corp_dept_add_new: $host + "admin/corporation/department/add",

    //一键入职 - 页面
    corp_service_entry_page: $host + "admin/corporation/entry/page",
    //一键入职 - 集团或公司的一键入职详情
    corp_service_entry_detail: $host + "admin/corporation/entry/detail",
    //一键入职 - 更新
    corp_service_entry_save: $host + "admin/corporation/entry/update",

    //福库服务 - 页面
    corp_service_fk_page: $host + "admin/corporation/welfare/page",
    //福库服务 - 福库详情
    corp_service_fk_detail: $host + "admin/corporation/welfare/detail",
    //福库服务 - 更新
    corp_service_fk_save: $host + "admin/corporation/welfare/update",

    //社保服务 - 页面
    corp_service_soin_page: $host + "admin/corporation/service/soin/page",

    //薪资代发服务 - 页面
    corp_service_salary_page: $host + "admin/corporation/service/salary/page",

    //考勤服务 - 页面
    corp_service_attendance_page: $host + "admin/corporation/attendance/page",
    //考勤服务 - 查询考勤方式
    corp_service_attendance_detail: $host + "admin/corporation/attendance/clock/detail",
    //考勤服务 - 更新考勤方式
    corp_service_attendance_save: $host + "admin/corporation/attendance/clock/update",
    //考勤服务 - 设备 查询
    corp_service_attendance_machine_list: $host + "admin/work_attendance/device/manager/get/list",
    //考勤服务 - 设备 新增
    corp_service_attendance_machine_add: $host + "admin/work_attendance/device/manager/add",
    //考勤服务 - 设备 删除
    corp_service_attendance_machine_del: $host + "admin/work_attendance/device/manager/batch/delete",
    //考勤服务 - 设备 修改
    corp_service_attendance_machine_modify: $host + "admin/work_attendance/device/manager/update",
    //考勤服务 - 同步考勤
    corp_service_attendance_sync: $host + "admin/work_attendance/device/manager/attendance/sync/record",

    //电子合同服务 - 页面
    corp_service_contract_page: $host + "admin/e_contract/service/info/page",
    //电子合同服务 - 详情 GET
    corp_service_contract_detail: $host + "admin/e_contract_service/info/detail",
    //电子合同服务 - 保存 PUT
    corp_service_contract_save: "admin/e_contract_service/info/submit",

    /**
     *  电子合同 模板管理
     */

    //页面
    contract_template_manage_page: $host + "admin/e_contract/page",
    //合同模板 查询 GET
    contract_temp_list: $host + "admin/e_contract/template/manager/page/list",
    //合同模板 新增
    contract_temp_add: $host + "admin/e_contract/template/manager/add",
    //合同模板 修改
    contract_temp_modify: $host + "admin/e_contract/template/manager/update",
    //合同模板 删除
    contract_temp_del: $host + "admin/e_contract/template/manager/batch/delete",
    //合同模板 详情 GET
    contract_temp_detail: $host + "admin/e_contract/template/manager/one/detail",


    /**
     *  电子合同 印章管理
     */

    //印章模板 查询 GET
    seal_temp_list: $host + "admin/e_contract/seal/manager/page/list",
    //印章模板 新增 POST
    seal_temp_add: $host + "admin/e_contract/seal/manager/batch/add",
    //印章模板 删除 POST
    seal_temp_del: $host + "admin/e_contract/seal/manager/batch/delete",

    /**
     *  客户管理
     */

    //页面
    customer_salary_page: $host + "admin/business/page",
    //客户 - 列表 GET
    customer_list: $host + "admin/business",
    //客户 - 删除 POST
    customer_del: $host + "admin/business/del",
    //客户 - 确认处理 POST
    customer_deal_confirm: $host + "admin/business/confirm",
    //添加 批注
    customer_remark_add: $host + "admin/business/postil",

    /**
     *  电子工资单
     */

    //页面
    electronic_payroll_page: $host + "admin/esalary/page",
    //客户 - 列表 GET
    electronic_payroll_user_list: $host + "admin/esalary/manager",
    //电子工资单 发送详情
    electronic_payroll_info: $host + "admin/esalary/info",
    //电子工资单 下载
    electronic_payroll_down: $host + "admin/esalary/download",


    /**
     *  薪资管理
     */

    //薪资计算

    //页面
    salary_calc_page: $host + "admin/salary/calculate/index",
    //薪资企业列表 - 树形菜单 url
    salary_group_tree_url: $host + "admin/salary/calculate/organization/tree",
    //根据年月 获取批次列表
    salary_calc_batch_list: $host + "admin/salary/calculate/batch/list",
    //模板下载
    salary_template_down: $host + "admin/salary/import/template/download",
    //薪资查询
    salary_query: $host + "admin/salary/calculate/query",
    //薪资导入
    salary_import: $host + "admin/salary/calculate/import",
    //计算 上传文件，获取计算结果
    salary_result_get_by_upload: $host + "admin/salary/calculate/upload2Calculate",
    //薪资计算结果 删除
    salary_calc_result_del: $host + "admin/salary/calculate/delete",
    //薪资计算结果 导出
    salary_calc_result_export: $host + "admin/salary/calculate/export",
    //薪资统计结果 导出
    salary_statistics_result_export: $host + "admin/salary/calculate/statistics/export",

    //计算规则 获取
    salary_calc_rule: $host + "admin/salary/calculate/rule",
    //计算规则 编辑
    salary_calc_rule_modify: $host + "admin/salary/calculate/rule/update",
    //计算规则 新增
    salary_calc_rule_add: $host + "admin/salary/calculate/rule/add",
    //计算规则 删除
    salary_calc_rule_del: $host + "admin/salary/calculate/rule/delete",
    //用户信息修改
    salary_user_info_modify: $host + "admin/salary/calculate/user/info/update",
    //计算规则 类型 获取
    salary_rule_type: $host + "admin/salary/calculate/getRuleType",

    //钱包发薪申请

    //页面
    wallet_pay_salary_apply_page: $host + "admin/wallet/pay/audit/apply/index",

    wallet_pay_salary_apply_query: $host + "admin/wallet/pay/audit/apply/query",

    wallet_pay_salary_detail_page: $host + "admin/wallet/pay/audit/detail/index",

    wallet_pay_salary_detail_query: $host + "admin/wallet/pay/audit/detail/query",

    wallet_pay_salary_detail_approve: $host + "admin/wallet/pay/audit/detail/approve",

    wallet_pay_salary_detail_reject: $host + "admin/wallet/pay/audit/detail/reject",

    wallet_pay_salary_detail_export: $host + "admin/wallet/pay/audit/detail/export",

    // 获取页面信息
    wallet_pay_salary_preview_index: $host + "admin/wallet/pays/preview/index",

    // 所属项目
    wallet_pay_salary_preview_corp: $host + "admin/wallet/pays/corp/list",

    // 导出模板
    wallet_pay_salary_export_template: $host + "admin/wallet/pays/export/template",

    // 导入模板
    wallet_pay_salary_import_template: $host + "admin/wallet/pays/import/template",

    // 删除
    wallet_pay_salary_preview_delete: $host + "admin/wallet/pays/preview/data/delete",

    // 保存
    wallet_pay_salary_preview_save: $host + "admin/wallet/pays/preview/data/save",




    /**
     *  运营管理
     */


    //运营
    operation: {

        //活动报名
        activity_register: {
            //活动报名 页面
            index: host_list.operation.activity_register + "register/page",
            //活动名称 列表
            name_list: host_list.operation.activity_register + "name/list",
            //活动报名 记录列表  GET
            list: host_list.operation.activity_register + "record/list",
            //活动报名 记录导出  GET
            excel_export: host_list.operation.activity_register + "record/list/export"
        },

        //3.8活动
        women_day_activity: {

            //页面
            index: host_list.operation.women_day_activity + "page",
            //列表    GET
            list: host_list.operation.women_day_activity + "records",
            //编辑    GET
            modify: host_list.operation.women_day_activity + "edit",
            //导出    GET
            excel_export: host_list.operation.women_day_activity + "records/export",

        },

        //wallet_change_record
        wallet_change_record: {

            //页面
            index: host_list.operation.wallet_change_record + "page",
            //列表    GET
            list: host_list.operation.wallet_change_record + "records",
            //导出    GET
            excel_export: host_list.operation.wallet_change_record + "records/export",

        }

    },

    //广告管理

    //页面
    advert_manage_page: $host + "admin/operation/ads/manage",
    //广告列表
    advert_list: $host + "admin/operation/ads/manage/list",
    //新增或修改广告
    advert_add_or_modify: $host + "admin/operation/ads/manage/create_update",
    //广告 删除
    advert_del: $host + "admin/operation/ads/manage/delete",
    //上传图片
    advert_img_upload: $host + "admin/operation/ads/manage/pic/upload",

    //福库商品管理

    //页面
    fk_good_page: $host + "admin/operation/fk/good/manage",
    //福库商品管理 - 上传图片
    fk_good_upload_img_url: $host + "admin/welfare/goods/image/upload",
    //福库商品管理 - 获取商品列表
    fk_good_list_get: $host + "admin/welfare/goods/list",
    //福库商品管理 - 获取 分类列表
    fk_good_category_list_get: $host + "admin/welfare/category/list",
    //福库商品管理 - 获取分类的所有规格
    fk_good_category_unit_list_get: $host + "admin/welfare/category/spec/list",
    //福库商品管理 - 获取商品详情
    fk_good_detail_get: $host + "admin/welfare/goods/detail",
    //福库商品管理 - 编辑商品（新增或修改）
    fk_good_save_by_add_or_modify: $host + "admin/welfare/goods/edit",
    //福库商品管理 - 删除商品
    fk_good_del: $host + "admin/welfare/goods/delete",
    //福库商品管理 - 移动商品位置
    fk_good_direction: $host + "admin/welfare/goods/change_position",

    //福库订单管理

    //页面
    fk_order_page: $host + "admin/operation/fk/order/manage",
    //福库订单管理 - 获取 产品名称
    fk_order_product_name_list: $host + "admin/welfare/order/names",
    //福库订单管理 - 获取 公司列表
    fk_order_corp_list: $host + "admin/corps/list",
    //福库订单管理 - 订单列表
    fk_order_list: $host + "admin/welfare/order/list",
    //福库订单管理 - 导出
    fk_order_export: $host + "admin/welfare/order/list/export",
    //货物签收单导出
    fk_receipt_form_export: $host + "admin/welfare/order/list/export_delivery",
    //福库订单管理 - 订单中 商品对应的分类
    fk_order_good_category: $host + "admin/welfare/goods/category/list",
    //福库订单管理 - 订单中 订单对应的分类
    fk_order_order_category: $host + "admin/welfare/order/category/detail",
    //福库订单管理 - 商品的分类 对应的规格
    fk_order_category_unit_list_get: $host + "admin/welfare/goods/category/spec/list",
    //福库订单管理 - 订单修改后保存
    fk_order_modify_save: $host + "admin/welfare/order/category/adjust",
    //福库订单管理 - 订单删除
    fk_order_del: $host + "admin/welfare/order/delete",
    //福库订单管理 - 订单 退款
    fk_order_refund: $host + "admin/welfare/order/refund",

    //福库公告管理

    //页面
    fk_notice_page: $host + "admin/operation/fk/notice/manage",
    //福库公告管理 - 获取公告
    fk_notice_get: $host + "admin/welfare/notice",
    //福库公告管理 - 保存公告（编辑后）
    fk_notice_save_by_modify: $host + "admin/welfare/notice/update",

    //福库券管理

    //页面
    fk_coupon_manage_index: $host + "admin/welfare/coupon_def/index",
    //福库券列表
    fk_coupon_list: $host + "admin/welfare/coupon_def/list",
    //福库券 明细
    fk_coupon_detail: $host + "admin/welfare/coupon_def/detail",
    //福库券 保存
    fk_coupon_save: $host + "admin/welfare/coupon_def/save",
    //福库券 布局配置(二维码位置)
    fk_coupon_layout: $host + "admin/welfare/coupon_def/layout",
    //福库券 删除
    fk_coupon_del: $host + "admin/welfare/coupon_def/delete",
    //福库券 导出
    fk_coupon_export: $host + "admin/welfare/coupon/export",

    //推送管理

    //页面
    notification_page: $host + "admin/notification/index",
    //推送管理 - 获取消息列表
    notification_list_get: $host + "admin/notification/list",
    //推送管理 - 获取消息 详情
    notification_detail: $host + "admin/notification/detail",
    //推送管理 - 消息 删除
    notification_del: $host + "admin/notification/delete",
    //推送管理 - 消息 新增/编辑
    notification_add_or_modify: $host + "admin/notification/create_update",
    //推送管理 - 获取所有跳转类型
    notification_type_list_get: $host + "admin/notification/jump/type/list",
    //获取所有标签
    tag_get: $host + "admin/notification/filter/category/tag/list",
    //推送人数
    notification_push_user_count: $host + "admin/notification/user/count",


    //新闻管理

    //页面
    news_manage_page: $host + "admin/news/page",
    //新闻管理 - 列表获取
    news_manage_list: $host + "admin/news/list",
    //新闻管理 - 新闻新增
    news_manage_add: $host + "admin/news/add",
    //新闻管理 - 新闻修改
    news_manage_modify: $host + "admin/news/update",
    //新闻管理 - 新闻删除
    news_manage_del: $host + "admin/news/delete",

    //领红包 记录页面
    red_packet_record_page: $host + "admin/red/packet/record/page",
    //领红包 记录列表  GET
    red_packet_record_list: $host + "admin/red/envelope",
    //领红包 记录导出  GET
    red_packet_record_export: $host + "admin/red/envelope/export",

    /**
     * 系统管理
     */

    //系统用户管理

    //页面
    sys_user_manage_page: $host + "admin/sys/user",
    //用户列表
    sys_user_list: $host + "admin/sys/user/list",
    // sys_user_list: $host + "admin/sys/user/list.jqd",
    //用户 新增
    sys_user_add: $host + "admin/sys/user/create",
    //用户 修改
    sys_user_modify: $host + "admin/sys/user/update",
    //用户 删除
    sys_user_del: $host + "admin/sys/user/delete",
    //用户 冻结、解冻
    sys_user_freeze: $host + "admin/sys/user/freeze",

    //用户列表（无格式用户列表显示）
    sys_user_list_raw: $host + "admin/sys/user/list/raw",


    //系统角色管理

    //页面
    sys_role_manage_page: $host + "admin/sys/role",
    //角色列表
    sys_role_list: $host + "admin/sys/role/list",
    //角色 新增、编辑
    sys_role_add_or_modify: $host + "admin/sys/role/create_edit",
    //角色 删除
    sys_role_del: $host + "admin/sys/role/delete",
    //角色 已添加的用户列表
    sys_role_user_added: $host + "admin/sys/role/user/list",
    //角色  所有可用用户列表
    sys_role_user_all: $host + "admin/sys/user/list",
    //角色 添加用户
    sys_role_user_add: $host + "admin/sys/role/user/add",
    //角色 移除用户
    sys_role_user_del: $host + "admin/sys/role/user/remove",
    //角色 已添加的 权限列表
    sys_role_permission_added: $host + "admin/sys/role/permission/list",
    //角色  所有可用 权限列表
    sys_role_permission_all: $host + "admin/sys/permission/list",
    //角色 添加权限
    sys_role_permission_add: $host + "admin/sys/role/permission/add",
    //角色 移除权限
    sys_role_permission_del: $host + "admin/sys/role/permission/remove",


    //日志管理

    //页面
    sys_log_manage_index: $host + "admin/sys/log/index",
    //系统日志 - 获取所有操作类型
    operate_type_list: $host + "admin/sys/log/operate/type/list",
    //系统日志 - 获取日志列表
    sys_log_list: $host + "admin/sys/log/list",

    //配置管理

    //页面
    sys_config_page: $host + "admin/sys/config/index",
    //获取系统配置列表 GET
    sys_config_list_get_url: $host + "admin/sys/config/list",
    //配置管理 - 更新 系统配置 POST
    sys_config_modify: $host + "admin/sys/config/update",
    //配置管理 - 新增 系统配置 POST
    sys_config_add: $host + "admin/sys/config/create",


    /**
     *  犯罪记录查询
     * */

    //犯罪记录

    // 页面
    criminal_record_query_index: $host + "admin/criminal/page",
    //犯罪记录 导入模板
    criminal_record_template: $host + "admin/criminal/download",
    //犯罪记录 确认导入、并导出文件
    criminal_record_export: $host + "admin/criminal/confirm",
    //犯罪记录 单个查询
    criminal_record_query_one: $host + "admin/criminal/one",


    /**
     *  用户管理
     * */

    // //页面
    // app_user_manage_index: $host + "admin/user/query/index",
    // //App用户 查询
    // app_user_list: $host + "admin/user/list",

    /**
     *  用户管理
     * */
    user_manage: {

        //app 用户管理
        app_user_manage: {
            index: host_list.user_manage.app_user_manage + "query/index",
            list: host_list.user_manage.app_user_manage + "list"
        },

        //用户信息查询
        user_info: {

            //页面
            index: host_list.user_manage.user_info + "index",

            //个人信息
            personal: host_list.user_manage.user_info + "app",
            //电子工资单
            e_salary: host_list.user_manage.user_info + "payroll",
            //入职信息
            entry: host_list.user_manage.user_info + "emp",
            //钱包信息
            wallet: host_list.user_manage.user_info + "wallet"

        },

        //钱包用户查询
        wallet: {

            //页面
            index: host_list.user_manage.wallet + "page",
            //查询    GET
            list: host_list.user_manage.wallet + "user/pager",
            //导出    GET
            excel_export: host_list.user_manage.wallet + "export/url",

        }

    },

    /**
     *  运维管理
     * */
    operation_manage: {

        //日志下载
        log: {

            //页面
            index: host_list.operation_manage.log + "page",
            //项目 - 列表   GET
            project_list: host_list.operation_manage.log + "project/list",
            //列表    GET
            list: host_list.operation_manage.log + "list",
            //下载    POST
            down: host_list.operation_manage.log + "download/url"

        },

        //访问统计
        access_statistics: {

            index: host_list.operation_manage.access_statistics + "index",

            //统计 最近24h 访问量  GET
            nHour: host_list.operation_manage.access_statistics + "last_24_hours",
            //统计 最近30天 访问量  GET
            nMonth: host_list.operation_manage.access_statistics + "last_30_days",
            //统计 各个时段24小时 访问量   GET
            eHour: host_list.operation_manage.access_statistics + "hourly",
            //统计 每周各天 访问量   GET
            eWeek: host_list.operation_manage.access_statistics + "weekly",
            //统计 app各个版本 访问量   GET
            eVersion: host_list.operation_manage.access_statistics + "app_version",
            //统计 各个设备 访问量    GET
            eDevice: host_list.operation_manage.access_statistics + "device_type"

        },

        //访问明细
        access_detail: {

            index: host_list.operation_manage.access_detail + "index",

            //统计 各个url 访问量    GET
            eUrl: host_list.operation_manage.access_statistics + "path"

        }

    },

    /**
     *  官网管理
     * */
    bumu_website_manage: {

        //招聘管理
        recruit_manage: {

            //页面
            index: host_list.bumu_website_manage.recruit_manage + "index",
            //列表
            list: host_list.bumu_website_manage.recruit_manage + "list",
            //保存
            save: host_list.bumu_website_manage.recruit_manage + "save",
            //更新
            update: host_list.bumu_website_manage.recruit_manage + "update",
            //删除
            del: host_list.bumu_website_manage.recruit_manage + "delete"

        },

        //新闻管理
        news_manage: {

            //页面
            index: host_list.bumu_website_manage.news_manage + "index",
            //列表    GET
            list: host_list.bumu_website_manage.news_manage + "list",
            //保存    POST
            save: host_list.bumu_website_manage.news_manage + "save",
            //更新    POST
            update: host_list.bumu_website_manage.news_manage + "update",
            //删除    POST
            del: host_list.bumu_website_manage.news_manage + "delete"

        }

    },

    /**
     *  组件管理
     * */
    assembly_manage: {

        //组件列表
        assembly_list: {

            //页面
            index: host_list.assembly_manage.assembly_list + "index"

        }

    }

};


