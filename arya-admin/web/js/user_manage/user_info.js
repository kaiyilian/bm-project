var $user_info_container;

//用户信息查询
var user_info = {
    phone: null,//用户手机号
    is_query: false,//是否 手机号查询

    //初始化
    init: function () {

        // $('a[href="#personal_info"]').tab('show'); //默认显示

        $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {

            if (!user_info.is_query) {
                toastr.warning("请先输入手机号查询！");
                e.preventDefault();
            }

        });

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {

            var href = $(this).attr("data-href");
            if (href === "personal_info") {
                personal_info.init();//初始化
            }

            if (href === "e_salary_info") {
                e_salary.init();//初始化
            }

            if (href === "entry_info") {
                entry_info.init();//初始化
            }

            if (href === "wallet_info") {
                wallet_info.init();//初始化
            }

        });

    },

    //查询
    btnSearchClick: function () {
        user_info.phone = $user_info_container.find(".search_container .searchCondition").val();

        //如果手机号格式不对
        if (!user_info.phone) {
            toastr.warning("请输入手机号！");
            return;
        }
        if (!phone_reg.test(user_info.phone)) {
            toastr.warning("手机号格式不对！");
            return;
        }

        // personal_info.init();//初始化
        user_info.is_query = true;
        $('a[href="#personal_info"]').tab('show'); //默认显示

    }

};

//个人信息
var personal_info = {

    init: function () {
        console.log("获取个人信息：" + new Date().getTime());

        var $personal_info = $user_info_container.find("#personal_info");

        var obj = {
            tel: user_info.phone
        };
        var url = urlGroup.user_manage.user_info.personal + "?" + jsonParseParam(obj);
        aryaGetRequest(
            url,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {

                    var phoneNo = "";// (string, optional): 当前手机号码 ,
                    var usedPhoneNo = "";//历史手机号码
                    var realName = "";//姓名
                    var idcardNo = "";//身份证
                    var lastLoginTime = "";//最后登录时间
                    var lastClientType = "";//最后登录设备类型
                    var appVersion = "";//app版本
                    var lastAccessTime = "";//最后访问时间

                    if (res.result) {

                        var $item = res.result;
                        phoneNo = $item.phoneNo ? $item.phoneNo : "";//
                        usedPhoneNo = $item.usedPhoneNo ? $item.usedPhoneNo : "";//
                        realName = $item.realName ? $item.realName : "";//
                        idcardNo = $item.idcardNo ? $item.idcardNo : "";//
                        lastLoginTime = $item.lastLoginTime ? $item.lastLoginTime : "";//
                        lastLoginTime = timeInit(lastLoginTime);
                        lastClientType = $item.lastClientType ? $item.lastClientType : 0;//
                        appVersion = $item.appVersion ? $item.appVersion : "";//app版本
                        lastAccessTime = $item.lastAccessTime ? $item.lastAccessTime : "";//
                        lastAccessTime = timeInit(lastAccessTime);

                        switch (lastClientType) {
                            case 1:
                                lastClientType = "安卓";
                                break;
                            case 2:
                                lastClientType = "IOS";
                                break;
                            default:
                                lastClientType = "";
                                break;
                        }

                    }

                    $personal_info.find(".cur_phone_no").text(phoneNo);
                    $personal_info.find(".history_phone_no").text(usedPhoneNo);
                    $personal_info.find(".user_name").text(realName);
                    $personal_info.find(".user_idCard").text(idcardNo);
                    $personal_info.find(".last_login_time").text(lastLoginTime);
                    $personal_info.find(".last_login_device_type").text(lastClientType);
                    $personal_info.find(".app_version").text(appVersion);
                    $personal_info.find(".last_query_time").text(lastAccessTime);

                }
                else {
                    toastr.warning(res.msg);
                }

            },
            function (error) {
            }
        );

        // user_info.is_query = true;
        // $('a[href="#personal_info"]').tab('show'); //默认显示

    }
};

//电子工资单
var e_salary = {

    init: function () {
        var $tb = $user_info_container.find("#tb_e_salary");

        var columns = [];
        columns.push({
            field: 'salaryTime',
            title: '发薪时间',
            align: "center",
            class: "salaryTime",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div>" + timeInit1(value) + "</div>";
                }
                else {
                    html = "<div></div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'name',
            title: '姓名',
            align: "center",
            class: "name",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'phone',
            title: '手机号码',
            align: "center",
            class: "phone",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'idCard',
            title: '身份证',
            align: "center",
            class: "idCard",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'year',
            title: '年',
            align: "center",
            class: "year",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'month',
            title: '月',
            align: "center",
            class: "month",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'salaryName',
            title: '工资名称',
            align: "center",
            class: "salaryName",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'salaryCompany',
            title: '发薪公司',
            align: "center",
            class: "salaryCompany",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });
        columns.push({
            field: 'payrollCompany',
            title: '代发公司',
            align: "center",
            class: "payrollCompany",
            formatter: function (value, row, index) {

                var html = "";
                if (value) {
                    html = "<div title='" + value + "'>" + value + "</div>";
                }

                return html;

            }
        });

        $tb.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            // data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            height: 600,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: columns,

            sidePagination: "server",           //分页方式：client 客户端分页，server 服务端分页（*）
            method: "get",
            contentType: "application/x-www-form-urlencoded",
            url: urlGroup.user_manage.user_info.e_salary,
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数

                return {
                    tel: user_info.phone
                };

            },
            onLoadSuccess: function () {  //加载成功时执行

                // toastr.success("成功！");

            },
            onLoadError: function () {  //加载失败时执行
                // layer.msg("加载数据失败", {time: 1500, icon: 2});
            },
            responseHandler: function (res) {

                setTimeout(function () {
                    loadingRemove();
                }, 500);

                var tb_data = [];
                var total_rows = 0;//总条数

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result && res.result.length > 0) {
                        total_rows = res.result.length;//总条数

                        if (res.result) {
                            $.each(res.result, function (i, item) {

                                var salaryTime = item.sendTime ? item.sendTime : "";//
                                var name = item.realName ? item.realName : "";//
                                var phone = item.phoneNo ? item.phoneNo : "";//
                                var idCard = item.idCardNo ? item.idCardNo : "";//
                                var year = item.infoYear ? item.infoYear : "";//
                                var month = item.infoMonth ? item.infoMonth : "";//
                                var salaryName = item.salaryName ? item.salaryName : "";//
                                var salaryCompany = item.corpName ? item.corpName : "";//
                                var payrollCompany = item.childCorpName ? item.childCorpName : "";//

                                var obj = {

                                    salaryTime: salaryTime,
                                    name: name,
                                    phone: phone,
                                    idCard: idCard,
                                    year: year,
                                    month: month,
                                    salaryName: salaryName,
                                    salaryCompany: salaryCompany,
                                    payrollCompany: payrollCompany

                                };
                                tb_data.push(obj);

                            });
                        }

                    }

                }
                else {
                    toastr.warning(res.msg);
                }

                return {
                    total: total_rows,
                    rows: tb_data
                };

            }

        });
    }

};

//入职信息
var entry_info = {

    init: function () {

        var obj = {
            tel: user_info.phone
        };
        var url = urlGroup.user_manage.user_info.entry + "?" + jsonParseParam(obj);
        aryaGetRequest(
            url,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        var $res = res.result;
                        var app_info = $res.app ? $res.app : null;//
                        var emp_prospective = $res.prospectives ? $res.prospectives : [];//
                        var emp_roster = $res.emps ? $res.emps : [];//

                        entry_info.initAppEntryInfo(app_info);
                        entry_info.initEmpProspective(emp_prospective);
                        entry_info.initEmpRoster(emp_roster);

                    }

                }
                else {
                    toastr.warning(res.msg);
                }

            },
            function (error) {
            }
        );

    },

    //初始化 app入职信息
    initAppEntryInfo: function (app_info) {

        var $app_entry = $user_info_container.find("#entry_info").find(".app_entry");

        var realName = "";//
        var sex = "";//
        var validity = "";//
        var idCardNo = "";//
        var nation = "";//

        if (app_info) {
            realName = app_info.realName ? app_info.realName : "";
            sex = app_info.sex ? app_info.sex : "";
            switch (sex) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                default:
                    sex = "";
                    break;
            }
            validity = app_info.expireTime ? app_info.expireTime : "";
            idCardNo = app_info.idCardNo ? app_info.idCardNo : "";
            nation = app_info.nation ? app_info.nation : "";
        }

        $app_entry.find(".name").text(realName);
        $app_entry.find(".sex").text(sex);
        $app_entry.find(".validity").text(validity);
        $app_entry.find(".idCard").text(idCardNo);
        $app_entry.find(".nation").text(nation);

    },

    //初始化 待入职列表
    initEmpProspective: function (emp_prospectives) {

        var data = [];
        $.each(emp_prospectives, function (i, $item) {

            var corpName = $item.corpName ? $item.corpName : "";//
            var checkinCode = $item.checkinCode ? $item.checkinCode : "";//
            var realName = $item.realName ? $item.realName : "";//
            var phoneNo = $item.phoneNo ? $item.phoneNo : "";//
            var checkinTime = $item.checkinTime ? $item.checkinTime : "";//
            var profileProgress = $item.profileProgress ? $item.profileProgress : "";//
            var memo = $item.memo ? $item.memo : "";//
            var acceptToEmp = $item.acceptToEmp ? $item.acceptToEmp : "";//
            var acceptOffer = $item.acceptOffer ? $item.acceptOffer : "";//
            var checkinComplete = $item.checkinComplete ? $item.checkinComplete : "";//

            data.push({
                corpName: corpName,
                corpCode: checkinCode,
                name: realName,
                phone: phoneNo,
                checkInTime: checkinTime,
                dataProcess: profileProgress,
                remark: memo,
                entryAgree: acceptToEmp,
                codeConfirm: acceptOffer,
                subExamine: checkinComplete
            });

        });

        var $tb = $user_info_container.find("#tb_emp_prospective");

        $tb.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,                     //是否显示搜索框功能
            editable: true,//开启编辑模式

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 600,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [
                {
                    field: 'corpName',
                    title: '企业名称',
                    align: "center",
                    class: "corpName",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'corpCode',
                    title: '企业码',
                    align: "center",
                    class: "corpCode",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'name',
                    title: '姓名',
                    align: "center",
                    class: "name",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'phone',
                    title: '手机号码',
                    align: "center",
                    class: "phone",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'checkInTime',
                    title: '入职时间',
                    align: "center",
                    class: "checkInTime",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + timeInit(value) + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'dataProcess',
                    title: '资料进度',
                    align: "center",
                    class: "dataProcess",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'remark',
                    title: '备注',
                    align: "center",
                    class: "remark",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'entryAgree',
                    title: '同意入职',
                    align: "center",
                    class: "entryAgree",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>能</div>";
                        }
                        else {
                            html = "<div>不能</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'codeConfirm',
                    title: '扫码确认',
                    align: "center",
                    class: "codeConfirm",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>是</div>";
                        }
                        else {
                            html = "<div>否</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'subExamine',
                    title: '提交审核',
                    align: "center",
                    class: "subExamine",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>是</div>";
                        }
                        else {
                            html = "<div>否</div>";
                        }

                        return html;

                    }
                }
            ]

        });

    },

    //初始化 花名册列表
    initEmpRoster: function (emp_roster) {

        var data = [];
        $.each(emp_roster, function (i, $item) {

            var corpName = $item.corpName ? $item.corpName : "";//
            var checkinCode = $item.checkinCode ? $item.checkinCode : "";//
            var realName = $item.realName ? $item.realName : "";//
            var phoneNo = $item.phoneNo ? $item.phoneNo : "";//
            var workAttendanceNo = $item.workAttendanceNo ? $item.workAttendanceNo : "";//
            var workAttendanceAddState = $item.workAttendanceAddState ? $item.workAttendanceAddState : 0;//

            data.push({
                corpName: corpName,
                corpCode: checkinCode,
                name: realName,
                phone: phoneNo,
                attendNo: workAttendanceNo,
                attendStatus: workAttendanceAddState
            });

        });

        var $tb = $user_info_container.find("#tb_emp_roster");

        $tb.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,  //是否显示搜索框功能
            editable: true,//开启编辑模式

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: true,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 600,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [
                {
                    field: 'corpName',
                    title: '企业名称',
                    align: "center",
                    class: "corpName",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'corpCode',
                    title: '企业码',
                    align: "center",
                    class: "corpCode",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'name',
                    title: '姓名',
                    align: "center",
                    class: "name",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'phone',
                    title: '手机号码',
                    align: "center",
                    class: "phone",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'attendNo',
                    title: '打卡号',
                    align: "center",
                    class: "attendNo",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'attendStatus',
                    title: '打卡状态',
                    align: "center",
                    class: "attendStatus",
                    formatter: function (value, row, index) {

                        var val = "";
                        switch (value) {
                            case 1:
                                val = "录入失败";
                                break;
                            case 2:
                                val = "未录入";
                                break;
                            case 3:
                                val = "已录入";
                                break;
                            default:
                                val = "录入等待";
                                break;
                        }

                        return "<div>" + val + "</div>";

                    }
                }
            ]

        });

    }

};

//钱包信息
var wallet_info = {

    init: function () {

        var obj = {
            tel: user_info.phone
        };
        var url = urlGroup.user_manage.user_info.wallet + "?" + jsonParseParam(obj);
        aryaGetRequest(
            url,
            function (res) {

                if (res.code === RESPONSE_OK_CODE) {

                    if (res.result) {
                        var $res = res.result;
                        var obj = $res.userInfo ? $res.userInfo : null;//
                        var arr = $res.bankCardInfoList ? $res.bankCardInfoList : [];//

                        wallet_info.initWalletInfo(obj);
                        wallet_info.initBankList(arr);

                    }

                }
                else {
                    toastr.warning(res.msg);
                }

            },
            function (error) {
            }
        );

    },

    //初始化 钱包信息
    initWalletInfo: function (obj) {

        var $wallet_info = $user_info_container.find("#wallet_info").find(".wallet_info");

        var account = "";//
        var name = "";//
        var register_phone = "";//
        var idCard = "";//

        if (obj) {
            account = obj.walletUserId ? obj.walletUserId : "";
            name = obj.validUserName ? obj.validUserName : "";
            register_phone = obj.phone ? obj.phone : "";
            idCard = obj.validCardNo ? obj.validCardNo : "";
        }

        $wallet_info.find(".account").text(account);
        $wallet_info.find(".name").text(name);
        $wallet_info.find(".register_phone").text(register_phone);
        $wallet_info.find(".idCard").text(idCard);

    },

    //初始化 银行卡列表
    initBankList: function (arr) {

        var data = [];
        $.each(arr, function (i, $item) {

            var bank_name = $item.bankName ? $item.bankName : "";//
            var bank_card_type = $item.bankType ? $item.bankType : "";//
            var bank_card = $item.bankCardNo ? $item.bankCardNo : "";//
            var bind_time = $item.bindTime ? $item.bindTime : "";//

            data.push({
                bank_name: bank_name,
                bank_card_type: bank_card_type,
                bank_card: bank_card,
                bind_time: bind_time
            });

        });

        var $tb = $user_info_container.find("#tb_wallet_bank");

        $tb.bootstrapTable("destroy");//表格摧毁

        //表格的初始化
        $tb.bootstrapTable({

            undefinedText: "",                   //当数据为 undefined 时显示的字符
            striped: false,                      //是否显示行间隔色
            cache: false,                        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            // search: true,                     //是否显示搜索框功能
            editable: true,//开启编辑模式

            data: data,                         //直接从本地数据初始化表格
            uniqueId: "id",

            //分页
            pagination: false,                   //是否显示分页（*）
            onlyPagination: true,               //只显示分页 页码
            sidePagination: "client",           //分页方式：client 客户端分页，server 服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 15],        //可供选择的每页的行数（*）

            //排序
            // sortable: true,                     //所有列的排序 是否开启
            sortOrder: "desc",                   //排序方式

            width: "100%",
            // height: 600,
            // selectItemName: 'parentItem',       //tbody中 radio or checkbox 的字段名（name='parentItem'）

            paginationPreText: "上一页",               //指定分页条中上一页按钮的图标或文字
            paginationNextText: "下一页",             //指定分页条中下一页按钮的图标或文字

            // detailView: true, //是否显示详情折叠

            rowStyle: function (row, index) {
                return {
                    classes: 'item'
                }
            },
            columns: [

                {
                    field: 'no',
                    title: '序号',
                    align: "center",
                    class: "no",
                    formatter: function (value, row, index) {

                        return "<div>" + (index + 1) + "</div>";

                    }
                },
                {
                    field: 'bank_name',
                    title: '银行名称',
                    align: "center",
                    class: "bank_name",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'bank_card_type',
                    title: '银行卡类型',
                    align: "center",
                    class: "bank_card_type",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'bank_card',
                    title: '银行卡号码',
                    align: "center",
                    class: "bank_card",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div title='" + value + "'>" + value + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                },
                {
                    field: 'bind_time',
                    title: '绑定时间',
                    align: "center",
                    class: "bind_time",
                    formatter: function (value, row, index) {

                        var html = "";
                        if (value) {
                            html = "<div>" + timeInit1(value) + "</div>";
                        }
                        else {
                            html = "<div></div>";
                        }

                        return html;

                    }
                }

            ]

        });

    },

};

$(function () {
    $user_info_container = $(".user_info_container");
    user_info.init();
});


var a = function () {

    var a = 11111;
    var ary = [12, 23, 24, 42, 1];
    var res = ary.map(function (item, index, input) {

        var a = 222222;

        function m() {

            console.log(this.a);

        }

        m();

        return item * 10;

    });
    console.log(res);//-->[120,230,240,420,10];
    console.log(ary);//-->[12,23,24,42,1]；
    //11111 5次

    var a = 11111;
    var ary = [12, 23, 24, 42, 1];
    for (var i = 0; i < ary.length; i++) {

        var a = 2222;
        ary[i] = ary[i] * 10;
        console.log(this.a)

    }
    console.log(ary);//-->[120,230,240,420,10];
    //2222 5次


    var obj = {
        name: "张三",
        age: 13
    };
    var m = $.map(obj, function (v, k) {

        // console.log(k + "----" + v)
        return v + "---"

    });
    console.log(m);
    console.log(obj);




}
