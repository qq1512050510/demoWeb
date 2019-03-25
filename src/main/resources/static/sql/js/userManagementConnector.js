
//load Data
function loadDataByUrl(url) {
	var returnData = new Object();
	$.ajax({
		url: url,
		type: 'post',
		data: {
			data: "",
		},
		async: false,
		dataType: 'json',
		success: function(data) {
			returnData.rows = data.list;
			returnData.total = data.total;
		},
		error: function() {
			returnData.suc = false;

		}
	});
	return returnData;

}

function formatData(url) {
	var dataReturn = loadDataByUrl(url);
	return dataReturn;
}

function initTableByUrl(urlInitTable)
{
	// 根据窗口调整表格高度
	$(window).resize(function() {
			$('#mytab').bootstrapTable('resetView', {
				height: tableHeight()
			})
		});
	// 生成用户数据
	var t = $("#mytab").bootstrapTable({
		url: urlInitTable,
		dataType: "json",
		//data: formatData("/demoWeb/sql/getUserByPagefilter/0/4"),
		method: 'get',
		striped: true, // 设置为 true 会有隔行变色效果
		undefinedText: "空", // 当数据为 undefined 时显示的字符
		pagination: true, // 分页
		// paginationLoop:true,//设置为 true 启用分页条无限循环的功能。
		showToggle: "true", // 是否显示 切换试图（table/card）按钮
		showColumns: "true", // 是否显示 内容列下拉框
		pageNumber: 1, // 如果设置了分页，首页页码
		// showPaginationSwitch:true,//是否显示 数据条数选择框
		pageSize: 5, // 如果设置了分页，页面数据条数
		pageList: [5, 10, 20, 40], // 如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
		paginationPreText: '‹', // 指定分页条中上一页按钮的图标或文字,这里是<
		paginationNextText: '›', // 指定分页条中下一页按钮的图标或文字,这里是>
		// singleSelect: false,//设置True 将禁止多选
		search: true, // 显示搜索框
		//detailView:true,
		data_local: "zh-US", // 表格汉化
		sidePagination: "server", // 服务端处理分页
		queryParams: function(params) { // 自定义参数，这里的参数是传给后台的，我这是是分页用的
			return { // 这里的params是table提供的
				cp: params.offset, // 从数据库第几条记录开始
				ps: params.limit // 找多少条
			};
		},
		idField: "userId", // 指定主键列
		columns: [{
				title: '全选',
				field: 'select',
				checkbox: true,
				width: 25,
				align: 'center',
				valign: 'middle'
			}, {
				title: '数据源编号',
				field: 'conid',
				sortable: true,
				align: 'center',

			}, {
				title: '数据库连接URL',
				/*sortable: true,*/
				field: 'conurl',
				align: 'center',
				formatter: function(value, row, index) { // 自定义显示可以写标签哦~
					return row.conurl.substr(0,40)+"...";
				}
			}, {
				title: '数据库类型',
				field: 'consqltype',
				align: 'center'
			},
			{
				title: '驱动名称',
				field: 'condrivername',
				align: 'center',

			},{
				title: '用户名',
				field: 'conusername',
				align: 'center',

			}, {
				title: '密码',
				field: 'conpassword',
				align: 'center',

			},  {
				title: '导入sqlsortId',
				field: 'sortids',
				align: 'center',

			}, {
				title: '操作',
				field: 'userId',
				align: 'center',
				formatter: function(value, row, index) { // 自定义显示可以写标签哦~
					return '<a href="#" mce_href="#" onclick="edit(\'' + row.conid + '\')">操作</a> ';
				}
			}

		]
	});	
	var roleArr = [];

	function operateFormatter(value, row, index) {
		if(value == 2) {
			return '<i class="fa fa-lock" style="color:red"></i>'
		} else if(value == 1) {
			return '<i class="fa fa-unlock" style="color:green"></i>'
		} else {
			return '数据错误'
		}
	}

	// 请求服务数据时所传参数
	function queryParams(params) {
		return {
			pageSize: params.limit,
			pageIndex: params.pageNumber,
			Name: $('#search_name').val(),
			Tel: $('#search_tel').val()
		}
	}
	// 查询按钮事件
	$('#search_btn').click(function() {
		$('#mytab').bootstrapTable('refresh', {
			url: '../index.php/admin/index/userManagement'
		});
	})

	// 增加按钮事件
	$('#btn_add').click(function() {
		$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
		}, 500)
		$('.addBody').css('display', 'block');
		$('.addBody').addClass('animated slideInRight');
	});

	$('.bootstrap-table').on('click-row.bs.table', function(e, row, element) {
		// $(element).css({"color":"blue","font-size":"16px;"});
		/*
		 * if($(element[0].cells[0].children).is(':checked')) {
		 * $(element[0].cells[0].children).prop("checked",false); }else {
		 * $(element[0].cells[0].children).prop("checked",true); }
		 */
		$(element[0].cells[0].children).click();
		
	});
	// 删除按钮与修改按钮的出现与消失

	$('.bootstrap-table').change(function() {
		menusAction();
	});
	
	// 修改按钮事件
	$('#btn_edit').click(function() {
		
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			loadDataById(dataArr[0]);
		
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');

			
		})
	
	
	// 统计按钮
	$('#btn_stastic').click(function() {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
			}, 500)
			$('.changeBody_stastic').css('display', 'block');
			$('.changeBody_stastic').addClass('animated slideInRight');
			$('#edit_ID').val(dataArr[0].ID);
			$('#edit_LoginName').val(dataArr[0].LoginName);
			$('#edit_Name').val(dataArr[0].Name);
			$('#edit_Tel').val(dataArr[0].Tel);
			$('#edit_Email').val(dataArr[0].Email);
			if(dataArr[0].Attribute == 1) {
				$("#staticForm input[name=Attribute]:eq(0)").prop("checked", true);
				$("#staticForm input[name=Attribute]:eq(1)").prop("checked", false);
			} else if(dataArr[0].Attribute == 2) {
				$("#staticForm input[name=Attribute]:eq(1)").prop("checked", true);
				$("#staticForm input[name=Attribute]:eq(0)").prop("checked", false);
			}
			// 先清空角色复选框
			$('#staticForm .edit input').prop('checked', false);
			// 获取用户角色
			$.post('../index.php/admin/Index/getUserById', {
					ID: dataArr[0].ID
				},
				function(data) {
					var roleIDArr = data.res.user.RoleID;
					// 将对应用户的角色列表显示到对应的修改页
					for(var i = 0; i < roleIDArr.length; i++) {
						for(var j = 0; j < $('#staticForm .edit input').length; j++) {
							if(roleIDArr[i] == $('#staticForm .edit input:eq(' + j + ')').val()) {
								$('#staticForm .edit input:eq(' + j + ')').prop('checked', true);
							}
						}
					}
				}
			);
		});
		/*
		 * 用户管理增加用户页面所有事件
		 */
		// 增加页面表单验证
		// Validate the form manually
	/*$('#add_saveBtn').click(function() {
		// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if($("#addForm").data('bootstrapValidator').isValid()) {
			var _info = $('#addForm').serialize();
			$.post(
				"../index.php/admin/index/insertUser",
				$('#addForm').serialize(),
				function(data) {
					// 后台返回添加成功
					if(data.suc == true) {
						$('.addBody').addClass('animated slideOutLeft');
						setTimeout(function() {
							$('.addBody').removeClass('animated slideOutLeft').css('display', 'none');
						}, 500);
						$('.tableBody').css('display', 'block').addClass('animated slideInRight');
						$('#mytab').bootstrapTable('refresh', {
							url: '../index.php/admin/index/userManagement'
						});
						$('#addForm').data('bootstrapValidator').resetForm(true);
						// 隐藏修改与删除按钮
						$('#btn_delete').css('display', 'none');
						$('#btn_edit').css('display', 'none');
					}
					// 否则
					else {}
				}
			)
		}
	});*/
	$('#add_saveBtn').click(function() {
		/*// 点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		// 如果表单验证正确，则请求后台添加用户
		if($("#addForm").data('bootstrapValidator').isValid()) {*/
			var _info = $('#addForm').serialize();
			
				$.ajax({
					type : "post",
					dataType : "json",
					url : "/demoWeb/database/addDatabaseSelective",
					data : _info,
					success : function(result) {
						//displayTableBody("#addForm",".addBody", ".tableBody");
						
					/*	$('#btn_delete').css('display', 'none');
						$('#btn_edit').css('display', 'none');*/
						$('.addBody').addClass('animated slideOutLeft');
						setTimeout(function() {
							$('.addBody').removeClass('animated slideOutLeft').css('display', 'none');
						}, 500)
						$('.tableBody').css('display', 'block').addClass('animated slideInRight');
						search();
						
					},
					error : function(jqXHR, textStatus) {
						jqAjaxError(jqXHR, textStatus);
					}
				});
			
			
		//}
	});
	// 增加页面返回按钮事件
	$('#add_backBtn').click(function() {
		$('.addBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.addBody').removeClass('animated slideOutLeft').css('display', 'none');
		}, 500)
		$('.tableBody').css('display', 'block').addClass('animated slideInRight');
		$('#addForm').data('bootstrapValidator').resetForm(true);
	});
	/*
	 * 用户管理修改用户页面所有事件
	 */
	// 修改页面回退按钮事件
	$('#edit_backBtn').click(function() {
			$('.changeBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.changeBody').removeClass('animated slideOutLeft').css('display', 'none');
			}, 500)
			$('.tableBody').css('display', 'block').addClass('animated slideInRight');
		})
	$('#stastic_backBtn').click(function() {
			$('.changeBody_stastic').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.changeBody_stastic').removeClass('animated slideOutLeft').css('display', 'none');
			}, 500)
			$('.tableBody').css('display', 'block').addClass('animated slideInRight');
			$('#editForm').data('bootstrapValidator').resetForm(true);
		})
		// 修改页面保存按钮事件
	$('#edit_saveBtn').click(function() {
		var _info = $('#editForm').serialize();
		$.ajax({
			type : "post",
			dataType : "json",
			url : "/demoWeb/database/editDatabaseSelective",
			data : _info,
			success : function(result) {
				//displayTableBody("#editForm",".addBody", ".tableBody");
				//search();
				
				$('.changeBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.changeBody').removeClass('animated slideOutLeft').css('display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass('animated slideInRight');
				search();
			},
			error : function(jqXHR, textStatus) {
				jqAjaxError(jqXHR, textStatus);
			}
		});
		})
		// 删除事件按钮
	$('#btn_delete').click(function() {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除该连接吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				var ID = [];
				for(var i = 0; i < dataArr.length; i++) {
					
					ID[i] = dataArr[i].conid;
					//alert(dataArr[i].conid);
				}
				/*$.post("../index.php/admin/index/deleteUserById", {
						ID: ID
					},
					function(data) {
						if(data.suc == true) {
							$('.popup_de .show_msg').text('删除成功！');
							$('.popup_de .btn_cancel').css('display', 'none');
							$('.popup_de').addClass('bbox');
							$('.popup_de .btn_submit').one('click', function() {
								$('.popup_de').removeClass('bbox');
							})
							$('#mytab').bootstrapTable('refresh', {
								url: '../index.php/admin/index/userManagement'
							});
						} else {}
					});*/
			    $.ajaxSettings.async = false;
			    $.post("/demoWeb/database/deleteDbByIds", {conIds:ID.join(',')}, function(result) {
			    	if(result.state=="success")
			    		{
			    		$('.popup_de .show_msg').text('删除成功！');
			    		$('.popup_de .btn_cancel').css('display', 'none');
			    		$('.popup_de').addClass('bbox');
			    		$('.popup_de .btn_submit').one('click', function() {
			    			$('.popup_de').removeClass('bbox');
			    		})
			    		search();
			    		}
			    	else
			    		{
			    		$('.popup_de .show_msg').text('删除异常！');
			    		}
			    },"json");
			    $.ajaxSettings.async = true;
			})
		})
		// 弹出框取消按钮事件
		　　 $('.popup_de .btn_cancel').click(function() {
			$('.popup_de').removeClass('bbox');　　
		})
		// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');　　
	})

}
//刷新列表
function search() {
	$('#mytab').bootstrapTable('refreshOptions', {
        pageNumber: 1
    });
	// 行单击事件
	rowClickEvent();
	menuActionInit();
	$('.bootstrap-table').change(function() {
		menusAction();
	});
}


function edit(dbSourceId)
{
	
		$('#executeButton').attr('onclick','executeSql("'+dbSourceId+'")');
		var sqlsortId = prompt("请选择导入sqlsortId", ""); //将输入的内容赋给变量 name ，

		//这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值

		if(sqlsortId===null) //如果返回的有内容

		{
			return false;
			
			
		} else {
			if(sqlsortId === "")
				{
				alert("空值");
				return;
				}
			//alert("欢迎您：" + sqlsortId);
			addSqlDetailBySorid(sqlsortId);
			//let promise = new Promise();
			$('#myModal').modal('toggle');
			return ;
			
		}

	$.ajax({
		type : "post",
		dataType : "json",
		url : "/demoWeb/sql/executeSqlBysortId",
		//url : "/demoWeb/sql/exportSql",
		data : {
			sortId : sqlsortId,
			dbSourceId : dbSourceId
		},
		success : function(result) {
			if(result.state=="success")
    		{
    		$('.popup_de .show_msg').text('导入sqlId为：'+result.insertSqlId);
    		$('.popup_de .btn_cancel').css('display', 'none');
    		$('.popup_de').addClass('bbox');
    		$('.popup_de .btn_submit').one('click', function() {
    			$('.popup_de').removeClass('bbox');
    		})
    		search();
    		}
    	else
    		{
    		$('.popup_de .show_msg').text('导入sql异常！');
    		}
		},
		error : function(jqXHR, textStatus) {
			////jqAjaxError(jqXHR, textStatus);
			alert('error!');
		}
	});

	
}
function addSqlDetailBySorid(sqlSortId,sqlRowsIds,displayState)
{
	sqlRowsIds = sqlRowsIds||["sqlRows"];
	displayState = displayState||"none";
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/demoWeb/sql/selectSqlTableBySortId",
		data : {
			sortId : sqlSortId
		},
		success : function(result) {
			for(let i =0 ;i<sqlRowsIds.length;i++)
				{
				let sqlRowsId = sqlRowsIds[i];
				$('#'+sqlRowsId).empty();
				let rowDivStringOne = '<div class="well" id="div_';
				let rowDivStringId = '" style="display:'+displayState+'"><i  class="fa fa-circle-o" style="display:none"></i><i class="fa fa-check-circle-o" style="display:none"></i><div id="" class="btn btn-success sqlRowsC" data-content="';
				let rowDivStringDetails = '" style="background-color: #1ab394;border-color: #1ab394;" data-original-title="';
				let rowDivStringDetailsHead = '">';
				let rowDivStringContent = '</div></div>';
				rowList  = result.rows;
				for(let i = 0 ;i<result.rows.length;i++)
				{
					
					let sqlRowFor = result.rows[i];
					let sqlRowsDivAll = rowDivStringOne;
					sqlRowsDivAll += (sqlRowFor.sqlorder + rowDivStringId);
					sqlRowsDivAll += (sqlRowFor.sqlcontent + rowDivStringDetails);
					sqlRowsDivAll += (sqlRowFor.sqlorder + '执行情况'+rowDivStringDetailsHead);
					sqlRowsDivAll += (sqlRowFor.sqlcontent.substr(0,50) + '...'+rowDivStringContent);
					$('#'+sqlRowsId).append(sqlRowsDivAll);
					
				}
				$(".sqlRowsC").popover({trigger: 'hover'});
				}
			
		},
		error : function(jqXHR, textStatus) {
			alert('error!');
		}
	});
}

function addSqlDetailBySoridAll(sqlSortId,sqlRowsIds,displayStates)
{
	sqlRowsIds = sqlRowsIds||["sqlRows"];
	displayStates = displayStates||"none";
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/demoWeb/sql/selectSqlTableBySortId",
		data : {
			sortId : sqlSortId
		},
		success : function(result) {
			if(result.rows.length==0)
				alert(sqlSortId+"已经失效");
			for(let i =0 ;i<sqlRowsIds.length;i++)
				{
				let sqlRowsId = sqlRowsIds[i];
				let displayState = displayStates[i];
				$('#'+sqlRowsId).empty();
				let rowDivStringOne = '<div class="well" id="div_'+sqlRowsId;
				let rowDivStringId = '" style="display:'+displayState+'"><i  class="fa fa-circle-o" style="display:none"></i><i class="fa fa-check-circle-o" style="display:none"></i><div id="" class="btn btn-success sqlRowsC" data-content="';
				let rowDivStringDetails = '" style="background-color: #1ab394;border-color: #1ab394;" data-original-title="';
				let rowDivStringDetailsHead = '">';
				let rowDivStringContent = '</div></div>';
				rowListAll  = result.rows;
				for(let i = 0 ;i<result.rows.length;i++)
				{
					
					let sqlRowFor = result.rows[i];
					let sqlRowsDivAll = rowDivStringOne;
					sqlRowsDivAll += (sqlRowFor.sqlorder + rowDivStringId);
					sqlRowsDivAll += (sqlRowFor.sqlcontent + rowDivStringDetails);
					sqlRowsDivAll += (sqlRowFor.sqlorder + '执行情况'+rowDivStringDetailsHead);
					sqlRowsDivAll += (sqlRowFor.sqlcontent.substr(0,50) + '...'+rowDivStringContent);
					$('#'+sqlRowsId).append(sqlRowsDivAll);
					
				}
				$(".sqlRowsC").popover({trigger: 'hover'});
				}
			
		},
		error : function(jqXHR, textStatus) {
			alert('error!');
		}
	});
}
var rowList = [];
var rowListAll = [];
function findOk(iCurrent){
	if($(iCurrent).attr('class').indexOf('fa-check-circle-o')!=-1)
		return iCurrent;
}
function findNot(iCurrent)
{
	if($(iCurrent).attr('class').indexOf('fa-circle-o')!=-1)
		return iCurrent;
}
function sateChangeByexecuteState(currentOk,currentNot,executeState)
{
	
	if(executeState)
		{
		$(currentOk).css('display','');
		$(currentNot).css('display','none');
		}
	else
		{
		$(currentOk).css('display','none');
		$(currentNot).css('display','');
		}
		
}

function importSql(sqlRowOne,dbSourceId,divId)
{
	
	divId = divId||"";
	$.ajax({
		type : "post",
		dataType : "json",
		async : false,
		url : "/demoWeb/sql/executeSqlBySqlIdAndDBSourceId",
		data : {
			sqlId : sqlRowOne.sqlid,
			dbSourceId : dbSourceId
		},
		success : function(result) {
			currentI = currentI + 1;
			//$('html,body').animate({scrollTop: $('#div_'+sqlRowOne.sqlorder).offset().top});
			//$('#sqlRows').animate({scrollTop: $('#div_'+sqlRowOne.sqlorder).offset().top},{duration:500,easing:"swing"});
			//scrollDiv(sqlRows,'div_'+sqlRowOne.sqlorder);
			$('#div_'+divId+sqlRowOne.sqlorder).css("display","");
			if(divId!="")
			{
				$('#sqlRowsFrom').animate({scrollTop: $('#sqlRowsFrom')[0].scrollHeight});
			}
			if(result.state=="success")
				{
				let currentSateList = $('#div_'+divId+sqlRowOne.sqlorder).find('i').toArray();
				let currentOk = currentSateList.find(findOk);
				let currentNot = currentSateList.find(findNot);
				sateChangeByexecuteState(currentOk,currentNot,true);
				uploadedSizeInt  = uploadedSizeInt+1;;
				
				}
			else
				{
				
				let currentSateList = $('#div_'+divId+sqlRowOne.sqlorder).find('i').toArray();
				let currentOk = currentSateList.find(findOk);
				let currentNot = currentSateList.find(findNot);
				sateChangeByexecuteState(currentOk,currentNot,false);
				}
			
		},
		error : function(jqXHR, textStatus) {
			currentI = currentI + 1;
			$('#sqlRows').animate({scrollTop: $('#div_'+sqlRowOne.sqlorder).offset().top});
			
			//scrollDiv(sqlRows,'div_'+sqlRowOne.sqlorder);
			//$('html,body').animate({scrollTop: $('#div_'+sqlRowOne.sqlorder).offset().top},{duration:500,easing:"swing"});
			let currentSateList = $('#div_'+sqlRowOne.sqlorder).find('i').toArray();
			let currentOk = currentSateList.find(findOk);
			let currentNot = currentSateList.find(findNot);
			sateChangeByexecuteState(currentOk,currentNot,false);
			alert('error!');
		}
	});
	$('#uploadStateId'+divId).css('width',currentI/totalSizeInt*100+'%');
	$('#uploadSucStateId'+divId).css('width',uploadedSizeInt/totalSizeInt*100+'%');
	if(currentI == totalSizeInt)
		{
		setTimeout(function(){alert("执行"+currentI+"条，成功"+uploadedSizeInt+"条");},500);
		}
}
function scrollDiv(containerId,divId)
{
	var container = $('#'+containerId),
    scrollTo = $('#'+divId);

container.scrollTop(scrollTo.offset().top - container.offset().top + container.scrollTop());
// Or you can animate the scrolling:
container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop()});
}
function executeSql(dbId)
{	
	$('#uploadStateId').css('width',0+'%');
	$('#uploadSucStateId').css('width',0+'%');
	let executeAccount = 0;
	let executeSuAccount = 0;
	totalSizeInt = rowList.length;
	currentI = 0;
	uploadedSizeInt = 0;
	for(let i = 0 ;i<rowList.length;i++)
		{
		
		
		importSql(rowList[i],dbId);
		/*console.log(execuetState+"----");
		if(execuetState)
			executeSuAccount++;
		executeAccount++;
		let uploadedSizeInt = Number(executeSuAccount);
		let currentI  = Number(i);
		let totalSizeInt = Number(rowList.length);
		$('#uploadStateId').css('width',currentI/totalSizeInt*100+'%');
		$('#uploadSucStateId').css('width',uploadedSizeInt/totalSizeInt*100+'%');*/
		
		
		/*let currentSateList = $('#div_'+rowList[i].sqlorder).find('i').toArray();
		let currentOk = currentSateList.find(findOk);
		let currentNot = currentSateList.find(findNot);
		
		sateChangeByexecuteState(currentOk,currentNot,true);
		let uploadedSizeInt = Number(25);
		let totalSizeInt = Number(100);
		$('#uploadStateId').css('width',uploadedSizeInt/totalSizeInt*100+'%');*/
		}
	
}
function executeSqlAll()
{
	
	$('#uploadStateIdsqlRowsTo').css('width',0+'%');
	$('#uploadSucStateIdsqlRowsTo').css('width',0+'%');
	let executeAccount = 0;
	let executeSuAccount = 0;
	totalSizeInt = rowListAll.length;
	currentI = 0;
	uploadedSizeInt = 0;
	console.log(rowListAll);
	let dbIdAll = $('#tragetSelect').val();
	if(dbIdAll=="")
		{
		alert("请选择目标数据库");
		return;
		}
	if(rowListAll.length==0)
		{
		alert("请选择模板");
		}
	for(let i = 0 ;i<rowListAll.length;i++)
		{
			importSql(rowListAll[i],dbIdAll,"sqlRowsTo");
		}
	
}

function tableHeight() {
	return $(window).height() - 140;
}