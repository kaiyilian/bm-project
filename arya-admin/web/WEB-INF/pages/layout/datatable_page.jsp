<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/3/3
  Time: 13:12
  Desc:dataTable 通用
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<%=contextPath%>/js/datatables/css/dataTable.css" rel="stylesheet">
<link href="<%=contextPath%>/js/datatables/css/dataTables.bootstrap.css" rel="stylesheet">

<script src="<%=contextPath%>/js/datatables/js/jquery.dataTables.min.js"></script>
<script src="<%=contextPath%>/js/datatables/js/dataTables.bootstrap.min.js"></script>

<script>

	/**
	 * 表格点击事件
	 * @param tableSelector 表格选择器
	 */
	function tableClick(tableSelector, DataTable, func) {
		//点击事件
		$(tableSelector).click(function () {
			var row = DataTable.row('.selected');
			console.log(row);
			if (row.data()) {
				getSelectedRowData(DataTable, "", function (rowData) {
					if (rowData) {
						if (func) {
							func(rowData);
						}
					}
				});
			}
		});
	}

	/**
	 * 表格单选开启
	 * @param tableId 表格名称
	 */
	function enableSingleSelection(tableId) {
		// 表格单选处理
		$('#' + tableId + ' tbody').on('click', 'tr', function () {
			console.log($(this));
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			}
			else {
				$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}

		});
	}

	function disableSingleSelection(tableId) {
		$('#' + tableId + ' tbody').off('click');
	}

	/**
	 * 通用的获取当前选中行数据的方法，如果没有选中则提示错误
	 * @param table JQuery表格对象
	 * @param actionLabel 动作名称，用于显示
	 * @param fnCallback 获取成功后的回调
	 */
	function getSelectedRowData(table, actionLabel, fnCallback) {
		var row = table.row('.selected');
		if (row) {
			var rowData = row.data();
			if (rowData) {
				fnCallback(rowData);
				return;
			}
		}
		swal({
			title: '请选中需要' + actionLabel + '的对象',
			text: '',
			type: "info"
		});
	}

	function removeSelectedRow(table) {
		var row = table.row('.selected');
		row.remove();
	}

	$.fn.dataTableExt.oApi.fnStandingRedraw = function (oSettings) {
		//redraw to account for filtering and sorting
		// concept here is that (for client side) there is a row got inserted at the end (for an add)
		// or when a record was modified it could be in the middle of the table
		// that is probably not supposed to be there - due to filtering / sorting
		// so we need to re process filtering and sorting
		// BUT - if it is server side - then this should be handled by the server - so skip this step
		if (oSettings.oFeatures.bServerSide === false) {
			var before = oSettings._iDisplayStart;
			oSettings.oApi._fnReDraw(oSettings);
			//iDisplayStart has been reset to zero - so lets change it back
			oSettings._iDisplayStart = before;
			oSettings.oApi._fnCalculateEnd(oSettings);
		}

		//draw the 'current' page
		oSettings.oApi._fnDraw(oSettings);
	};

	/**
	 * 刷新DataTable表格列表数据
	 */
	function refreshList(manager) {
		manager.table.ajax.reload();
	}

	/**
	 * 刷新DataTable表格列表数据
	 */
	function refreshTableList(table) {
		table.ajax.reload();
	}

	/**
	 * 刷新datatable表格列表当前页数据
	 */
	function refreshDataTable(manager) {
		manager.dataTable.fnStandingRedraw();
	}

	/**
	 * 刷新datatable表格列表当前页数据
	 */
	function refreshTableCurrentPage(dataTable) {
		dataTable.fnStandingRedraw();
	}

	/**
	 * 创建新对象
	 * @param manager 每个页面各自的管理对象
	 */
	function create(manager) {
		$('#' + manager.DLG_ROLE_USER_LABEL_ID).text('新建');
		manager.dialog.modal('show');
		clearForm($('#' + manager.FORM_ID));
	}

	/**
	 * 编辑选中对象
	 * @param manager 每个页面各自的管理对象
	 */
	function edit(manager) {
		$('#' + manager.DLG_ROLE_USER_LABEL_ID).text('编辑');
		console.log(manager.table);
		getSelectedRowData(manager.table, '编辑', function (rowData) {
			manager.fnInitEditForm(rowData);
		});
	}

	/**
	 * 删除选中对象
	 * @param manager 每个页面各自的管理对象
	 */
	function removeIt(manager) {
		getSelectedRowData(manager.table, '删除', function (rowData) {
		});
	}

	/**
	 * 改变选中对象的状态
	 * @param manager 每个页面各自的管理对象
	 * @param actionName 动作名称
	 * @param desc 描述信息
	 * @param fnSubmit 确定后的处理函数
	 */
	function changeStatus(manager, actionName, desc, fnSubmit) {
		getSelectedRowData(manager.table, actionName, function (rowData) {
			swal({
						title: manager.fnTitleFormatter(actionName, rowData),
						text: desc,
						type: 'warning',
						showCancelButton: true,
						confirmButtonColor: CFG_CONFIRM_BTN_COLOR,
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						closeOnConfirm: false
					},
					function () {
						fnSubmit(rowData.id);
					});
		});
	}

	/**
	 * 提交改变至服务器（例如：删除，冻结，审核等改变状态的操作）
	 * @param params 提交的参数（JSON格式）
	 * @param url URL，POST方式提交
	 * @param successMsg 成功后的提示信息
	 * @param fnCallback 改变成功后的回调，参数和 SWAL 定义的回调一样
	 */
	function submitChanges(params, url, successMsg, fnCallback) {
		console.log('Access ' + url);
		console.log(params);
		ajaxSetup();
		$.ajax({
			url: url,
			method: 'POST',
			data: JSON.stringify(params),
			success: function (data, status, jqXHR) {
				console.log(data);
				if (data.code == ERR_CODE_OK) {
					swal({title: '成功!', text: successMsg, type: 'success'},
							fnCallback());
				}
				else {
					swal(data.code, data.msg, 'error');
				}
			}
		});
	}

	function updateDataTableSelectAllCtrl(table) {
		var $table = table.table().node();
		var $chkbox_all = $('tbody input[type="checkbox"]', $table);
		var $chkbox_checked = $('tbody input[type="checkbox"]:checked', $table);
		var chkbox_select_all = $('thead input[name="select_all"]', $table).get(0);

		// If none of the checkboxes are checked
		if ($chkbox_checked.length === 0) {
			chkbox_select_all.checked = false;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = false;
			}

			// If all of the checkboxes are checked
		}
		else if ($chkbox_checked.length === $chkbox_all.length) {
			chkbox_select_all.checked = true;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = false;
			}

			// If some of the checkboxes are checked
		}
		else {
			chkbox_select_all.checked = true;
			if ('indeterminate' in chkbox_select_all) {
				chkbox_select_all.indeterminate = true;
			}
		}
	}
</script>
