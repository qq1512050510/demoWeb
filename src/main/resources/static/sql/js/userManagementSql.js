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
			console.log(123132);
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
	console.log(dataReturn);
	return dataReturn;
}

$(function() {
	// 根据窗口调整表格高度
	$(window).resize(function() {
			$('#mytab').bootstrapTable('resetView', {
				height: tableHeight()
			})
		});
		// 生成用户数据
	var t = $("#mytab").bootstrapTable({
		url: '/demoWeb/sql/getSqlByPagefilterAndImport/0/5/notImport',
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
				title: 'sql编号',
				field: 'sqlid',
				sortable: true,
				align: 'center',

			}, {
				title: 'sql内容',
				sortable: true,
				field: 'sqlcontent',
				align: 'center',
				formatter: function(value, row, index) { // 自定义显示可以写标签哦~
					return row.sqlcontent.substr(0,100)+"...";
				}
				
			}, {
				title: '描述信息',
				field: 'sqldetails',
				align: 'center'
			},
			{
				title: '类型Id',
				field: 'tSortSortid',
				align: 'center',

			}, {
				title: 'sqlSort',
				field: 'sqlsort',
				align: 'center',

			}, {
				title: '操作',
				field: 'userId',
				align: 'center',
				formatter: function(value, row, index) { // 自定义显示可以写标签哦~
					return '<a href="#" mce_href="#" onclick="edit(\'' + row.sqlid + '\')">操作</a> ';
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
		console.log(row);
		console.log(element);
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
	function menusAction()
	{
		var dataArr = $('#mytab .selected');
		if(dataArr.length == 1) {
			$('#btn_stastic').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_edit').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_edit').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_edit').css('display', 'none');
			}, 400);
		}
		if(dataArr.length >= 1) {
			$('#btn_stastic').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
			$('#btn_delete').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');

		} else {
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_delete').css('display', 'none');
			}, 400);
			$('#btn_stastic').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_stastic').css('display', 'none');
			}, 400);

		}
	}
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
		
		});
	
	
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
	/*	// 增加页面表单验证
		// Validate the form manually
	$('#add_saveBtn').click(function() {
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
			var _info = $('#addForm').serialize();
			
				$.ajax({
					type : "post",
					dataType : "json",
					url : "/demoWeb/sql/addSqlSelective",
					data : _info,
					success : function(result) {
						$('.addBody').addClass('animated slideOutLeft');
						setTimeout(function() {
							$('.addBody').removeClass('animated slideOutLeft').css('display', 'none');
						}, 500)
						$('.tableBody').css('display', 'block').addClass('animated slideInRight');
						search();
					},
					error : function(jqXHR, textStatus) {
						//jqAjaxError(jqXHR, textStatus);
						alert('error!');
					}
				});
			
			
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
			$('#editForm').data('bootstrapValidator').resetForm(true);
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
			url : "/demoWeb/sql/editSqlSelective",
			data : _info,
			success : function(result) {
				
				$('.changeBody').addClass('animated slideOutLeft');
				setTimeout(function() {
					$('.changeBody').removeClass('animated slideOutLeft').css('display', 'none');
				}, 500)
				$('.tableBody').css('display', 'block').addClass('animated slideInRight');
				search();
			},
			error : function(jqXHR, textStatus) {
				//jqAjaxError(jqXHR, textStatus);
				alert('error!');
			}
		});
		
		});
		// 删除事件按钮
	$('#btn_delete').click(function() {
		var dataArr = $('#mytab').bootstrapTable('getSelections');
		$('.popup_de .show_msg').text('确定要删除该SQL吗?');
		$('.popup_de').addClass('bbox');
		$('.popup_de .btn_submit').one('click', function() {
			var ID = [];
			for(var i = 0; i < dataArr.length; i++) {
				
				ID[i] = dataArr[i].sqlid;
				//alert(dataArr[i].conid);
				console.log(dataArr[i]);
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
		    $.post("/demoWeb/sql/deleteSqlByIds", {conIds:ID.join(',')}, function(result) {
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
		});
		});
		// 弹出框取消按钮事件
		　　 $('.popup_de .btn_cancel').click(function() {
			$('.popup_de').removeClass('bbox');　　
		})
		// 弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');　　
	})
})
function edit(sqlid)
{
	let dbSourceId = $('#dbSourceId').val();
	if(dbSourceId=="")
		{
		alert('请输入数据库ID！');
		return false;
		}
	$('.popup_de .show_msg').text('查询id为:'+dbSourceId+'生成相应的sql');
	$('.popup_de').addClass('bbox');
	$('.popup_de .btn_submit').one('click', function() {
	$.ajax({
		type : "post",
		dataType : "json",
		//url : "/demoWeb/sql/executeSql",
		url : "/demoWeb/sql/exportSql",
		data : {
			sqlid : sqlid,
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
	});
	
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
function tableHeight() {
	return $(window).height() - 140;
}