<%--
  Created by IntelliJ IDEA.
  User: CuiMengxin
  Date: 2017/1/16
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String contextPath = request.getContextPath().toString(); %>

<%--<link href="<%=contextPath%>/css/dataTables.css" rel="stylesheet">--%>
<%--<link rel="stylesheet" type="text/css" href="http://sandbox.runjs.cn/uploads/rs/238/n8vhm36h/bootstrap.min.css">--%>
<%--<link href="<%=contextPath%>/js/datatables/css/jquery.dataTables.min.css" rel="stylesheet">--%>
<link href="<%=contextPath%>/js/datatables/css/dataTable.css" rel="stylesheet">
<link href="<%=contextPath%>/js/datatables/css/dataTables.bootstrap.css" rel="stylesheet">
<%--<link href="http://cdn.datatables.net/plug-ins/28e7751dbec/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">--%>

<script src="<%=contextPath%>/js/datatables/js/jquery.dataTables.min.js"></script>
<script src="<%=contextPath%>/js/datatables/js/dataTables.bootstrap.min.js"></script>

<table id="example" class="table table-striped table-bordered table-hover dataTable">
	<thead></thead>
	<tbody></tbody>
</table>


<script>
	var table = $('#example').DataTable({
//		"jQueryUI": true,
		"info": true,//控制是否显示表格左下角的信息
		ordering: false,//是否允许DataTables排序
		order: [[1, "asc"]],
		"paging": true,//是否开启本地分页
		"pageLength": 3,//改变初始的页面长度(每页显示的记录数)
		"pagingType": "full_numbers",//分页按钮的显示方式
		"lengthChange": true,//是否允许用户 自定义显示数量
		"lengthMenu": [	//定义在每页显示记录数的select中显示的选项
			[3, 6, 9, -1],
			[3, 6, 9, "All"],
		],
		"scrollX": false,//设置水平滚动
		"scrollY": false,//设置垂直滚动
		"searching": true,//是否允许Datatables开启本地搜索
		"serverSide": false,//是否开启服务器模式 (不开启)
		"stateSave": false,//保存状态(缓存) - 在页面重新加载的时候恢复状态（页码等内容）
		"autoWidth": true,//控制Datatables是否自适应宽度 如果定义了columns.width 则关闭
		"processing": false,//是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
		"deferRender": true,//控制Datatables的延迟渲染，可以提高初始化的速度
		//设置位置
		"dom": "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
		"<'row'<'col-sm-12'tr>>" +
		"<'row'<'col-sm-5'i><'col-sm-7'p>>",

		rowId: "id",//tr id
		"data": [

			{
				"id": "12",
				"name": "Tiger Nixon",
				"position": "System Architect",
				"salary": "$3,120",
				"start_date": "2011/04/25",
				"office": "Edinburgh",
				"extn": 5421,
				data_info: {
					"is_pay": "yes"
				}
			},

			{
				"name": "Tiger Nixon",
				"position": "System Architect",
				"salary": "$3,120",
				"start_date": "2011/04/25",
				"office": "Edinburgh",
				"extn": 5421
			},
			{
				"name": "Tiger Nixon",
				"position": "System Architect",
				"salary": "$3,120",
				"start_date": "2011/04/25",
				"office": "Edinburgh",
				"extn": 5421
			},
			{
				"name": "Tiger Nixon",
				"position": "System Architect",
				"salary": "$3,120",
				"start_date": "2011/04/25",
				"office": "Edinburgh",
				"extn": 5421
			},

			{

				"name": "Garrett Winters",
				"position": "Director",
				"salary": "5300",
				"start_date": "2011/07/25",
				"office": "Edinburgh",
				"extn": "8422"
			}

		],
		"columns": [

			{
				"data": null,
//				"data": "choose_item",
				className: "choose_item",
				"title": function () {
					var $img = $("<img>");
					$img.attr("src", "img/icon_Unchecked.png");
					$img.addClass("choose_item");

					return $img[0].outerHTML;
				},
				"render": function (data, type, row, meta) {
					var $img = $("<img>");
					$img.attr("src", "img/icon_Unchecked.png");
					$img.addClass("choose_item");
					$img.attr("onclick","dataTable_test.chooseItem(this)");
//					$img.click(function () {
//						alert(1);
//						dataTable_test.chooseItem(this);
//					});

					return $img[0].outerHTML;
				},
			},
			{
				data: null,
				"searchable": false,
				"orderable": false,
				title: "序号",
				width: "30px"
			},
			{
				"data": "name",
				"title": "第一列",
//				"contentPadding": "mmm",
				defaultContent: "111", //默认值
			},
			{
				"data": "position",
				"title": "第二列"
			},
			{
				"data": "office",
				"title": "第三列"
			},
			{
				"data": "extn",
				"title": "第四列"
			},
			{
				"data": "start_date",
				"title": "第五列"
			},
			{
				"data": "salary",
				"title": "第六列"
			},
			{
				"data": null,
				"title": "操作",
				"className": "operate",
				"render": function (data, type, row, meta) {
//					console.log(data)
//					console.log(type)
//					console.log(row);
					var $btn_modify = $("<div>");
					$btn_modify.addClass("btn");
					$btn_modify.addClass("btn-sm");
					$btn_modify.addClass("btn-primary");
					$btn_modify.text("编辑");

					var $btn_del = $("<div>");
					$btn_del.addClass("btn");
					$btn_del.addClass("btn-sm");
					$btn_del.addClass("btn-danger");
					$btn_del.text("删除");

					return $btn_modify[0].outerHTML + $btn_del[0].outerHTML;

				}
			}
		],

		"language": {
			"decimal": ",",//小数点表示字符（有些文化中用"，"表示小数点）
			"emptyTable": "暂无数据",//当表格没有数据时，表格所显示的字符串
			"info": "第 _START_ 到 _END_ 条 , 总共 _TOTAL_ 条",
			"infoEmpty": "查询结果为空",//查询结果为空  左下角显示信息 Showing 0 to 0 of 0 entries
			"infoFiltered": "(从 _MAX_ 条数据中搜索到)",//当表格搜索后，在汇总字符串上需要增加的内容
			"thousands": ",",//千位的分隔符
			"lengthMenu": "显示 _MENU_ 条",
			"loadingRecords": "Loading...",//加载数据时的提示信息 - 当 Ajax请求数据时显示
			"search": "查询:",//搜索框的提示信息
			searchPlaceholder: "请输入查询条件",//搜索框(input)的placeholder属性
			"zeroRecords": "查询结果为空",//当搜索结果为空时，显示的信息
			"paginate": {
				"first": "首页",
				"last": "尾页",
				"next": "下一页",
				"previous": "上一页"
			}
		},
//		"stripeClasses": [ 'strip1', 'strip2', 'strip3' ],//每行的className
		"createdRow": function (row, data, dataIndex) {	  //新增行 的时候触发

			console.log(row);
//			console.log(data.data_info);

			$(row).addClass("item");
			//如果 is_pay有值
			if (data.data_info && data.data_info.is_pay) {
				$("td:eq(2)", row).addClass('important')
						.attr("data-is_pay", data.data_info.is_pay);
			}

		},

	});

	//设置索引
	table.on('draw.dt', function () {
		table.column(1, {
			search: 'applied',
			order: 'applied'
		}).nodes().each(function (cell, i) {
//			console.log(cell);

			cell.innerHTML = i + 1;
		});
	}).draw();

	var dataTable_test = {

		//选中单行
		chooseItem: function (self) {
			var $item = $(self).closest(".item");

			if ($item.hasClass("active")) { //如果选中行
				$item.removeClass("active");
				$(self).find("img").attr("src", "img/icon_Unchecked.png");
			}
			else { //如果未选中
				$item.addClass("active");
				$(self).find("img").attr("src", "img/icon_checked.png");
			}

//			dataTable_test.isChooseAll();//是否 已经全部选择
		},
		//选择当前页 全部
		chooseCurAll: function () {
			var $table_container = $(dataTable_test.containerName).find(".table_container");
			var $cur = $table_container.find("thead .choose_item");//thead choose_item
			var $item = $table_container.find("tbody .item");//tbody item
			var $choose_item = $table_container.find(".choose_item");//table choose_item


			if ($cur.hasClass("active")) { //如果选中
				$cur.removeClass("active");//
				$item.removeClass("active");//tbody item移除active
				$choose_item.find("img").attr("src", "img/icon_Unchecked.png");
			}
			else { //如果未选中
				$cur.addClass("active");
				$item.addClass("active");//tbody item加上active
				$choose_item.find("img").attr("src", "img/icon_checked.png");
			}

			fk_coupon_manage.is_Choose_all_page = "0";
			//移除 选择全部的选中状态
			$(fk_coupon_manage.containerName).find(".foot .choose_item").removeClass("active")
					.find("img").attr("src", "img/icon_Unchecked.png");

		},
		//选择全部(查询条件下)
		chooseAll: function () {
			var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
			var $thead_choose_item = $table_container.find("thead .choose_item");//thead choose_item
			var $item = $table_container.find("tbody .item");//tbody item
			var $tbody_choose_item = $table_container.find("tbody .choose_item");//tbody choose_item
			var $foot_choose_item = $(fk_coupon_manage.containerName).find(".foot .choose_item");

			$thead_choose_item.removeClass("active");
			$thead_choose_item.find("img").attr("src", "img/icon_Unchecked.png");

			if ($foot_choose_item.hasClass("active")) {   //已经选中
				fk_coupon_manage.is_Choose_all_page = "0";

				$foot_choose_item.removeClass("active");
				$foot_choose_item.find("img").attr("src", "img/icon_Unchecked.png");
				$item.removeClass("active");
				$tbody_choose_item.find("img").attr("src", "img/icon_Unchecked.png")
			}
			else {
				fk_coupon_manage.is_Choose_all_page = "1";

				$foot_choose_item.addClass("active");
				$foot_choose_item.find("img").attr("src", "img/icon_checked.png");
				$item.addClass("active");
				$tbody_choose_item.find("img").attr("src", "img/icon_checked.png")
			}

		},
		//是否 已经全部选择(当前页)
		isChooseAll: function () {
			var $table_container = $(fk_coupon_manage.containerName).find(".table_container");
			var $cur = $table_container.find("thead .choose_item");//thead choose_item
			var $item = $table_container.find("tbody .item");//tbody item
			//var $choose_item = $table_container.find(".choose_item");//table choose_item


			var chooseNo = 0;//选中的个数
			for (var i = 0; i < $item.length; i++) {
				if ($item.eq(i).hasClass("active")) { //如果 是选中的
					chooseNo += 1;
				}
			}

			//没有全部选中 当前页item
			if (chooseNo == 0 || chooseNo < $item.length) {
				$cur.removeClass("active").find("img").attr("src", "img/icon_Unchecked.png");
			}
			else {
				$cur.addClass("active").find("img").attr("src", "img/icon_checked.png");
			}

			fk_coupon_manage.is_Choose_all_page = "0";//
			//移除 选择全部的选中状态
			$(fk_coupon_manage.containerName).find(".foot .choose_item").removeClass("active")
					.find("img").attr("src", "img/icon_Unchecked.png");
		},

	};

</script>
