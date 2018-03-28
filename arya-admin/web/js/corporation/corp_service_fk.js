/**
 * Created by CuiMengxin on 2017/5/3.
 */

var $corp_service_fk_container = $(".corp_service_fk_container");
var $fk_info_container = $corp_service_fk_container.find(".fk_info_container");

var corp_service_fk = {

    node: "",
    organize_type: "",//组织类型:1集团，2子公司，3通用部门，4一级公司
    organize_id: "",//组织id
    organize_pid: "",//组织pid
    btn_value: "0",//

    //初始化
    init: function () {

        corp_service_fk.clearFkInfo();	//清空 内容

    },

    //树形菜单 - 点击事件
    groupTreeOnclick: function (node) {
        //console.log(JSON.stringify(node));

        loadingInit();//加载中 弹框显示

        corp_service_fk.node = node;
        corp_service_fk.organize_id = node.id;//组织id
        corp_service_fk.organize_pid = node.pid ? node.pid : 0;//父级id
        corp_service_fk.organize_type = node.type;//组织类型:1集团，2子公司，3通用部门，4一级公司

        corp_service_fk.fkInfo();//查询公司 福库信息

    },

    //清空 内容
    clearFkInfo: function () {
        $fk_info_container.find(".editable").attr("disabled", "disabled");//禁用所有 点击
        $fk_info_container.find(".editable").val("");

        //隐藏所有按钮
        $fk_info_container.find(".btn_operate").find(".btn").hide();
    },

    //福库 信息获取
    fkInfo: function () {
        corp_service_fk.clearFkInfo();//清空 内容

        var obj = {
            corp_id: corp_service_fk.organize_id
        };
        var url = urlGroup.corp_service_fk_detail + "?" + jsonParseParam(obj);

        aryaGetRequest(
            url,
            function (data) {
                //alert(JSON.stringify(data));
                //console.info("获取日志：");
                //console.log(data);

                if (data.code == RESPONSE_OK_CODE) {

                    var $item = data.result;
                    var name = $item.welfare_corp_name ? $item.welfare_corp_name : "";//

                    $fk_info_container.find(".welfare_corp_name").val(name);

                    corp_service_fk.fkInfoInit();//福库- 详情 初始化

                }
                else {
                    toastr.info(data.msg);
                }
            },
            function () {
                toastr.error("系统错误，请联系管理员！");
            }
        );

    },
    //福库 - 详情 初始化
    fkInfoInit: function () {

        var $btn_operate = $fk_info_container.find(".btn_operate");
        corp_service_fk.btn_value = $btn_operate.find(".btn_modify").attr("data-value");

        corp_service_fk.BtnOperateInit();//底部按钮 初始化
    },

    //福库 信息编辑
    fkInfoModify: function () {
        $fk_info_container.find(".editable").removeAttr("disabled");//

        //赋值企业id
        $fk_info_container.find(".corp_id").val(corp_service_fk.organize_id);
        $fk_info_container.find(".corp_parent_id").val(corp_service_fk.organize_pid);

        var $btn_operate = $fk_info_container.find(".btn_operate");
        //保存
        var value_1 = $btn_operate.find(".btn_save").attr("data-value");
        //取消
        var value_2 = $btn_operate.find(".btn_cancel").attr("data-value");

        //底部 按钮
        corp_service_fk.btn_value = value_1 | value_2;

        corp_service_fk.BtnOperateInit();//底部按钮 初始化

    },
    //福库 信息 - 取消编辑
    fkInfoCancelByModify: function () {
        corp_service_fk.fkInfo();//重新获取 福库 信息
    },
    //福库 - 信息保存
    fkInfoSave: function () {

        loadingInit();//加载中 弹框显示

        $fk_info_container.ajaxSubmit({
            url: urlGroup.corp_service_fk_save,
            type: 'post',
            dataType: 'json',
            data: $fk_info_container.fieldSerialize(),
            beforeSend: function () {
            },
            uploadProgress: function (event, position, total, percentComplete) {
                //var percentVal = percentComplete + '%';
            },
            success: function (data) {
                //alert(JSON.stringify(data));

                if (data.code == RESPONSE_OK_CODE) {
                    toastr.success("保存成功！");

                    corp_service_fk.fkInfo();//重新获取 信息

                }
                else {
                    loadingRemove();//加载中 - 移除logo

                    toastr.error("操作失败：" + data.msg);
                }

            },
            complete: function (xhr) {
            }
        });

    },

    //底部按钮 初始化
    BtnOperateInit: function () {

        $fk_info_container.find(".btn_operate").find(".btn").hide();

        $fk_info_container.find(".btn_operate").find(".btn").each(function () {
            var $this = $(this);

            var value = $this.attr("data-value");
            if (value & corp_service_fk.btn_value) {
                $this.show();
            }

        });

    }

};

$(document).ready(function () {

    var treeId = ".corp_service_fk_container .aryaZtreeContainer .ztree";//树结构 class
    var searchId = ".corp_service_fk_container .aryaZtreeContainer .ztree_search";//查询框 id
    var url = urlGroup.corp_service_tree1_url + "?business_type=" + corp_service_type.fk;//获取树结构 url
    var treeClickFunc = corp_service_fk.groupTreeOnclick;//树结构 click事件
    var clearInfoByTreeClickFunc = corp_service_fk.clearFkInfo;	//清空 内容

    organizationTree.init(treeId, searchId, url, treeClickFunc, clearInfoByTreeClickFunc);//初始化 树结构

    corp_service_fk.init();

});
