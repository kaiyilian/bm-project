/**
 * 角色管理类
 * @constructor
 */
function RoleManager() {
    // 文档中的ID定义，注意不能与其他js冲突
    this.DLG_ID = 'dlg_sys_role';
    this.DLG_ROLE_USER_LABEL_ID = 'dlg_sys_role_label';
    this.DLG_ADD_USER_ID = 'dlg_sys_role_user';
    this.DLG_ROLE_LABEL_ID = 'dlg_sys_role_user_label';
    this.DLG_ASSIGN_PERMISSION_ID = 'dlg_sys_role_permission';

    this.TB_ID = 'sys_role_list';
    this.TB_USERS_ID = 'sys_role_user_list';
    this.TB_USER_OTHERS_ID = 'sys_role_user_list_others';
    this.TB_PERMISSIONS_ID = 'sys_role_permission_list';
    this.TB_PERMISSIONS_OTHER_ID = 'sys_role_permission_list_others';
    this.FORM_ID = 'sys_role_form';
    this.ENTITY_ID = 'rid';

    // URL定义
    ////this.LIST_URL = 'admin/sys/role/list';
    //this.CREATE_URL = 'admin/sys/role/create_edit';
    //this.UPDATE_URL = 'admin/sys/role/create_edit';
    //this.REMOVE_URL = 'admin/sys/role/delete';
    ////this.FREEZE_URL = 'admin/sys/role/freeze';
    //this.ROLE_USER_LIST_URL = 'admin/sys/role/user/list';//角色 已添加的用户列表
    //this.USER_LIST_URL = 'admin/sys/user/list';//角色  所有可用用户列表
    //this.ROLE_ADD_USER_URL = 'admin/sys/role/user/add';
    //this.ROLE_REMOVE_USER_URL = 'admin/sys/role/user/remove';
    //this.ROLE_PERMISSION_LIST_URL = 'admin/sys/role/permission/list';
    //this.ROLE_ADD_PERMISSION_URL = 'admin/sys/role/permission/add';
    //this.ROLE_REMOVE_PERMISSION_URL = 'admin/sys/role/permission/remove';
    //
    //this.PERMISSION_LIST_URL = 'admin/sys/permission/list';

    // 全局DOM对象
    this.table = undefined; // JQuery Datatables
    this.tableUsers = undefined;
    this.tableOtherUsers = undefined;
    this.tableOwnPermissions = undefined;
    this.tableOtherPermissions = undefined;
    this.dialog = undefined; // 新增编辑角色对话框（JQuery Modal Dialog）
    this.dialogAddUser = undefined; // 添加用户对话框（JQuery Modal Dialog）
}

/**
 * 初始化编辑表单
 * @param rowData 要编辑的数据
 */
RoleManager.prototype.fnInitEditForm = function (rowData) {
    console.log(rowData);
    $('#rid').val(rowData.id);
    $('#role_name').val(rowData.roleName);
    $('#role_desc').val(rowData.roleDesc);
    $('#label_err_msg').css('display', 'none');
    this.dialog.modal('show');
};

/**
 * 生成状态变化相关的对话框标题
 * @param actionName 动作名称
 * @param rowData
 * @returns {*}
 */
RoleManager.prototype.fnTitleFormatter = function (actionName, rowData) {
    if (rowData) {
        return '确定要' + actionName + rowData.roleName + '吗?'
    }
    else {
        return '';
    }
};

/**
 * 提交创建新对象或者更新对象的表单
 */
RoleManager.prototype.submitCreateEditForm = function () {
    loadingInit();

    var rid = $('#' + this.ENTITY_ID).val();
    var roleName = $('#role_name').val();
    var roleDesc = $('#role_desc').val();

    var params = {};
    var url;

    if (rid) {
        console.log('编辑');
        url = urlGroup.sys_role_add_or_modify;
        params.rid = rid;
    }
    else {
        console.log('新建');
        url = urlGroup.sys_role_add_or_modify;
    }

    params.role_name = roleName;
    params.role_desc = roleDesc;

    //console.log(JSON.stringify(params));

    var table = this.table;
    var dialog = this.dialog;

    ajaxSetup();
    $.ajax({
        url: url,
        method: 'POST',
        data: JSON.stringify(params),
        success: function (data, status, jqXHR) {
            console.log(data);
            loadingRemove();

            if (data.code == ERR_CODE_OK) {
                dialog.modal('hide');
                setTimeout(function () {
                    swal({title: '成功!', text: '已经成功保存.', type: 'success'}, function () {
                        table.ajax.reload();
                    });
                }, 1000);

            }
            else if (data.code == ERR_CODE_VALIDATION) {
                console.log("验证错误：");
                console.log(data.result);
                var errs = '\r';
                for (var i = 0; i < data.result.length; i++) {
                    var e = data.result[i];
                    console.log(e);
                    console.log(e.key + ' - ' + e.msg);
                    errs += '\r' + e.msg;
                }
                $('#label_err_msg').css('display', 'block');
                $('#label_err_msg').html(errs);
                // swal(data.code, data.result, 'error');
            }
            else {
                swal(data.code, data.msg, 'error');
            }
        }
    });
};

/**
 * 提交删除系统角色
 * @param rid
 */
RoleManager.prototype.submitDeleteSysRole = function (rid) {
    var params = {
        rid: rid
    };
    var table = this.table;
    submitChanges(params, urlGroup.sys_role_del, '角色已被删除.', function () {
        console.log(table.row('.selected').data);
        table.row('.selected').remove();
    });
};

// 此处命名注意要避免和其他js冲突
var roleManager = new RoleManager();

$(document).ready(function () {

    roleManager.dialog = $('#' + roleManager.DLG_ID);

    roleManager.dialogAddUser = $('#' + roleManager.DLG_ADD_USER_ID);
    roleManager.dialogAssignPermission = $('#' + roleManager.DLG_ASSIGN_PERMISSION_ID);

    // 角色主列表初始化
    roleManager.table = $('#' + roleManager.TB_ID).DataTable({
        'processing': true,
        'serverSide': true,
        'showRowNumber': true,
        'bStateSave': true,
        'searching': false,
        'ordering': false,
        "language": {
            "url": DATATABLES_CHINESE_LANGUAGE
        },
        'ajax': {
            'url': urlGroup.sys_role_list
        },
        //'rowId':'id',
        'aoColumns': [
            {"sTitle": "角色名称", "mData": 'roleName'},
            {"sTitle": "角色描述", "mData": 'roleDesc'},
            {"sTitle": "用户数量", "mData": 'sysUserCount'},
            {"sTitle": "权限数量", "mData": 'sysPermissionCount'},
            {
                "sTitle": "创建时间", mData: 'createTime',
                "mRender": formatUnixTime
            }
        ],
        'rowCallback': function (row, data) {
            //console.log(data);
        }
    });

    enableSingleSelection(roleManager.TB_ID);

});

/**
 * 处理用户角色分配
 * @param manager
 */
function assignSysUserRole(manager) {
    getSelectedRowData(manager.table, '用户角色', function (rowData) {

        // 保存选择的角色ID以备后用
        roleManager.dialogAddUser.attr('selected_role_id', rowData.id);
        roleManager.dialogAddUser.attr('selected_role_name', rowData.roleName);

        // 已关联角色的用户列表
        if (roleManager.tableUsers) {
            roleManager.tableUsers.destroy();
        }
        roleManager.tableUsers = $('#' + roleManager.TB_USERS_ID).DataTable({
            'processing': true,
            'serverSide': true,
            'searching': false,
            'lengthChange': false,
            'paging': false,
            "language": {
                "url": DATATABLES_CHINESE_LANGUAGE
            },
            'ajax': {
                'url': urlGroup.sys_role_user_added,
                'data': function (p) {
                    p.rid = rowData.id
                }
            },
            'aoColumns': [
                {
                    "sTitle": "登录名",
                    "mData": "loginName"
                },
                {
                    "sTitle": "真实姓名",
                    "mData": 'realName'
                },
                {
                    "sTitle": "状态",
                    "mData": 'status',
                    "mRender": formatSysUserStatus
                }
            ],
            'rowCallback': function (row, data) {
                //console.log(data);
            }
        });

        //可选的用户列表
        if (roleManager.tableOtherUsers) {
            roleManager.tableOtherUsers.destroy();
        }
        roleManager.tableOtherUsers = $('#' + roleManager.TB_USER_OTHERS_ID).DataTable({
            'processing': true,
            'serverSide': true,
            'showRowNumber': true,
            'bStateSave': true,
            'searching': false,
            'ordering': false,
            "language": {
                "url": DATATABLES_CHINESE_LANGUAGE
            },
            'ajax': {
                'url': urlGroup.sys_role_user_all,
                'data': function (p) {
                    p.status = 1;
                }
            },
            'columns': [
                {data: 'realName'},
                {data: 'loginName'}
            ],
            'rowCallback': function (row, data) {
                //console.log(data);
            }
        });

        enableSingleSelection(roleManager.TB_USERS_ID);
        enableSingleSelection(roleManager.TB_USER_OTHERS_ID);

        //var roleName = roleManager.dialogAddUser.attr('selected_role_name');
        $('#' + manager.DLG_ROLE_LABEL_ID).text(rowData.roleName);
        manager.dialogAddUser.modal('show');
        manager.dialogAddUser.on('hidden.bs.modal', function (event) {
            console.log(event);
            disableSingleSelection(roleManager.TB_USERS_ID);
            disableSingleSelection(roleManager.TB_USER_OTHERS_ID);
        });
    });
}

/**
 * 分配权限
 * @param manager
 */
function sysRolePermission(manager) {
    getSelectedRowData(manager.table, '角色权限', function (rowData) {
        // 保存选择的角色ID以备后用
        var roleId = rowData.id;
        roleManager.dialogAssignPermission.attr('selected_role_id', roleId);
        roleManager.dialogAssignPermission.attr('selected_role_name', rowData.roleName);

        // 已关联角色的权限列表
        if (roleManager.tableOwnPermissions) {
            roleManager.tableOwnPermissions.destroy();
        }
        roleManager.tableOwnPermissions = $('#' + roleManager.TB_PERMISSIONS_ID).DataTable({
            'processing': true,
            'serverSide': true,
            'paging': false,
            'showRowNumber': false,
            'searching': false,
            'lengthChange': false,
            "language": {
                "url": DATATABLES_CHINESE_LANGUAGE
            },
            'ajax': {
                'url': urlGroup.sys_role_permission_added,
                'data': function (p) {
                    p.rid = roleId
                }
            },
            //'rowId':'id',
            'columns': [
                //{data: 'id'},
                {data: 'permissionCode'},
                {data: 'desc'}
            ],
            'rowCallback': function (row, data) {
                //console.log(data);
            },
            'initComplete': function () {
                console.log('initComplete');
                initOtherPermissionTable(manager, roleId);
            }
        });

        enableSingleSelection(roleManager.TB_PERMISSIONS_ID);

        //var roleName = roleManager.dialogAddUser.attr('selected_role_name');
        $('#' + manager.DLG_ROLE_PERM_LABEL_ID).text(rowData.roleName);
        manager.dialogAssignPermission.modal('show');
        manager.dialogAssignPermission.on('hidden.bs.modal', function (event) {
            console.log(event);
            disableSingleSelection(roleManager.TB_PERMISSIONS_ID);
            disableSingleSelection(roleManager.TB_PERMISSIONS_OTHER_ID);
        });
    });
}

function initOtherPermissionTable(manager, roleId) {

    //可选的权限列表
    if (manager.tableOtherPermissions) {
        manager.tableOtherPermissions.destroy();
    }
    manager.tableOtherPermissions = $('#' + manager.TB_PERMISSIONS_OTHER_ID).DataTable({
        'processing': true,
        'serverSide': true,
        'paging': false,
        'showRowNumber': false,
        'bStateSave': true,
        'searching': false,
        'ordering': false,
        "language": {
            "url": DATATABLES_CHINESE_LANGUAGE
        },
        'ajax': {
            'url': urlGroup.sys_role_permission_all,
            'data': function (p) {
                p.role_id = roleId;
            }
        },
        'columns': [
            {data: 'permissionCode'},
            {data: 'desc'}
        ],
        'rowCallback': function (row, data) {
            //console.log(data);
        }
    });
    enableSingleSelection(manager.TB_PERMISSIONS_OTHER_ID);
}

/**
 * 添加选择的用户至角色所属用户列表中
 */
function addSysUserToRole() {
    var roleName = roleManager.dialogAddUser.attr('selected_role_name');
    var label = '给角色' + roleName + '添加用户';
    getSelectedRowData(roleManager.tableOtherUsers, label, function (rowData) {
        console.log(rowData);
        var params = {
            role_id: roleManager.dialogAddUser.attr('selected_role_id'),
            sys_user_id: rowData.id
        };

        ajaxSetup();
        $.ajax({
            url: urlGroup.sys_role_user_add,
            method: 'POST',
            data: JSON.stringify(params),
            success: function (data, status, jqXHR) {
                console.log(data);
                if (data.code == ERR_CODE_OK) {
                    setTimeout(function () {
                        roleManager.tableUsers.ajax.reload();
                        roleManager.tableOtherUsers.ajax.reload();
                    }, 1000);
                }
                else if (data.code == ERR_CODE_VALIDATION) {
                    swal(data.code, data.result, 'error');
                }
                else {
                    swal(data.code, data.msg, 'error');
                }
            }
        });
    });
}

/**
 * 从角色所属列表移除用户
 */
function removeSysUserToRole() {
    var roleName = roleManager.dialogAddUser.attr('selected_role_name');
    var label = '给角色' + roleName + '移除用户';
    getSelectedRowData(roleManager.tableUsers, label, function (rowData) {
        console.log(rowData);
        var params = {
            role_id: roleManager.dialogAddUser.attr('selected_role_id'),
            sys_user_id: rowData.id
        };

        ajaxSetup();
        $.ajax({
            url: urlGroup.sys_role_user_del,
            method: 'POST',
            data: JSON.stringify(params),
            success: function (data, status, jqXHR) {
                console.log(data);
                if (data.code == ERR_CODE_OK) {
                    //setTimeout(function () {
                    roleManager.tableUsers.ajax.reload();
                    roleManager.tableOtherUsers.ajax.reload();
                    //}, 1000);
                }
                else if (data.code == ERR_CODE_VALIDATION) {
                    swal(data.code, data.result, 'error');
                }
                else {
                    swal(data.code, data.msg, 'error');
                }
            }
        });
    });
}

/**
 * 添加选择的权限至角色所属权限列表中
 */
function addSysPermissionToRole() {

    var roleName = roleManager.dialogAssignPermission.attr('selected_role_name');
    var label = '给角色' + roleName + '添加权限';
    getSelectedRowData(roleManager.tableOtherPermissions, label, function (rowData) {
        console.log(rowData);
        var params = {
            role_id: roleManager.dialogAssignPermission.attr('selected_role_id'),
            sys_permission_id: rowData.id
        };

        ajaxSetup();
        $.ajax({
            url: urlGroup.sys_role_permission_add,
            method: 'POST',
            data: JSON.stringify(params),
            success: function (data, status, jqXHR) {
                console.log(data);
                if (data.code == ERR_CODE_OK) {
                    setTimeout(function () {
                        roleManager.tableOwnPermissions.ajax.reload();
                        roleManager.tableOtherPermissions.ajax.reload();
                    }, 1000);
                }
                else if (data.code == ERR_CODE_VALIDATION) {
                    swal(data.code, data.result, 'error');
                }
                else {
                    swal(data.code, data.msg, 'error');
                }
            }
        });
    });
}

/**
 * 从角色所属列表移除权限
 */
function removeSysPermissionToRole() {
    var roleName = roleManager.dialogAssignPermission.attr('selected_role_name');
    var label = '给角色' + roleName + '移除权限';
    getSelectedRowData(roleManager.tableOwnPermissions, label, function (rowData) {
        console.log(rowData);
        var params = {
            role_id: roleManager.dialogAssignPermission.attr('selected_role_id'),
            sys_permission_id: rowData.id
        };

        ajaxSetup();
        $.ajax({
            url: urlGroup.sys_role_permission_del,
            method: 'POST',
            data: JSON.stringify(params),
            success: function (data, status, jqXHR) {
                console.log(data);
                if (data.code == ERR_CODE_OK) {
                    //setTimeout(function () {
                    roleManager.tableOwnPermissions.ajax.reload();
                    roleManager.tableOtherPermissions.ajax.reload();
                    //}, 1000);
                }
                else if (data.code == ERR_CODE_VALIDATION) {
                    swal(data.code, data.result, 'error');
                }
                else {
                    swal(data.code, data.msg, 'error');
                }
            }
        });
    });
}
