//load Data
function loadDataByUrl(url) {
	var returnData = new Object();
	$.ajax({
		url: url,
		type: 'GET',
		data: {
			data: "",
		},
		async: false,
		dataType: 'json',
		success: function(data) {
			return data;
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


function layoutInit(url)
{
	
	//根据窗口调整表格高度
	$(window).resize(function() {
			$('#mytab').bootstrapTable('resetView', {
				height: tableHeight()
			})
		})
		//生成用户数据

	var t = $("#mytab").bootstrapTable({
		url: url,
		//data: formatData("../data/dataPageFilter.json"),
		method: 'get',
		dataType: "json",
		striped: true, //设置为 true 会有隔行变色效果  
		undefinedText: "空", //当数据为 undefined 时显示的字符  
		pagination: true, //分页  
		// paginationLoop:true,//设置为 true 启用分页条无限循环的功能。  
		showToggle: "true", //是否显示 切换试图（table/card）按钮  
		showColumns: "true", //是否显示 内容列下拉框  
		pageNumber: 1, //如果设置了分页，首页页码  
		// showPaginationSwitch:true,//是否显示 数据条数选择框  
		pageSize: 5, //如果设置了分页，页面数据条数  
		pageList: [5, 10, 20, 40], //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。  
		paginationPreText: '‹', //指定分页条中上一页按钮的图标或文字,这里是<  
		paginationNextText: '›', //指定分页条中下一页按钮的图标或文字,这里是>  
		// singleSelect: false,//设置True 将禁止多选  
		search: false, //显示搜索框  
		data_local: "zh-US", //表格汉化  
		sidePagination: "server", //服务端处理分页  
		queryParams: function(params) { //自定义参数，这里的参数是传给后台的，我这是是分页用的  
			return { //这里的params是table提供的  
				cp: params.offset, //从数据库第几条记录开始  
				ps: params.limit //找多少条  
			};
		},
		idField: "userId", //指定主键列  
		columns: [{
				title: '全选',
				field: 'select',
				checkbox: true,
				width: 25,
				align: 'center',
				valign: 'middle'
			}, {
				title: '计划号',
				field: 'planid',
				align: 'center',
				visible:false

			}, {
				title: '卫星',
				field: 'satelliteid',
				align: 'center',

			}, {
				title: '轨道数',
				field: 'pathwaycount',
				align: 'center'
			},{
				title: '传输状态',
				field: 'fileState',
				align: 'center'
			},{
				title: '文件数量',
				field: 'filecount',
				align: 'center'
			}, {
				title: '总数据量Gb',
				field: 'diskcount',
				align: 'center'
			},  {
				title: '载荷',
				field: 'receivestationid',
				align: 'center'
			}, {
				title: '传输速率Mb/S',
				field: 'transformrate',
				align: 'center'
			}

		]
	});

	/*t.on('load-success.bs.table', function(data) { //table加载成功后的监听函数  
		console.log("load success");
		$(".pull-right").css("display", "block");
	});*/
	/*	$('#mytab').bootstrapTable({
				//method: 'get',
				//contentType: "application/x-www-form-urlencoded",
				dataType: "json",
				data: formatData("../data/table_asp.json"),
				//url:"../data/table_asp.json",
				height: tableHeight(), //高度调整
				toolbar: '#toolbar',
				striped: true, //是否显示行间隔色
				dataField: "res",
				pageNumber: 1, //初始化加载第一页，默认第一页
				pagination: true, //是否分页
				queryParamsType: 'limit',
				//queryParams: queryParams,
				sidePagination: 'server',
				pageSize: 10, //单页记录数
				pageList: [5, 10, 20, 30], //分页步进值
				showRefresh: true, //刷新按钮
				showColumns: true,
				clickToSelect: true, //是否启用点击选中行
				toolbarAlign: 'right',
				buttonsAlign: 'right', //按钮对齐方式
				toolbar: '#toolbar', //指定工作栏
				columns: [{
					title: '全选',
					field: 'select',
					checkbox: true,
					width: 25,
					align: 'center',
					valign: 'middle'
				}, {
					title: 'ID',
					field: 'innerid',
					visible: false
				}, {
					title: '登录名',
					field: 'name',
					sortable: true
				}, {
					title: '姓名',
					field: 'rid',
					sortable: true
				}, {
					title: '类型',
					field: 'st_type',
				}, {
					title: '注册日期',
					field: 'dateCreated',
					sortable: true
				}, {
					title: '状态',
					field: 'sort',
					align: 'center',
					formatter: operateFormatter
				}],
				locale: 'zh-CN', //中文支持,
			})*/
	/*
	 * 用户管理首页事件
	 */
	//请求后台数据获取角色列表
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

	//请求服务数据时所传参数
	function queryParams(params) {
		return {
			pageSize: params.limit,
			pageIndex: params.pageNumber,
			Name: $('#search_name').val(),
			Tel: $('#search_tel').val()
		}
	}
	//查询按钮事件
	$('#search_btn').click(function() {
		$('#mytab').bootstrapTable('refresh', {
			url: '../index.php/admin/index/userManagement'
		});
	})

	//增加按钮事件
	$('#btn_add').click(function() {
		$('.tableBody').addClass('animated slideOutLeft');
		setTimeout(function() {
			$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
		}, 500)
		$('.addBody').css('display', 'block');
		$('.addBody').addClass('animated slideInRight');
	});

	$('.bootstrap-table').on('click-row.bs.table', function(e, row, element) {
		//$(element).css({"color":"blue","font-size":"16px;"});  
		console.log(row);
		console.log(element);
		/*if($(element[0].cells[0].children).is(':checked'))
		{
			$(element[0].cells[0].children).prop("checked",false);
		}else
		{
			$(element[0].cells[0].children).prop("checked",true);
		}*/
		$(element[0].cells[0].children).click();
		
	});
	//删除按钮与修改按钮的出现与消失

	$('.bootstrap-table').change(function() {
		menusAction();
	});
	function menusAction()
	{
		var dataArr = $('#mytab .selected');
		if(dataArr.length == 1) {
			$('#btn_edit').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');
		} else {
			$('#btn_edit').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_edit').css('display', 'none');
			}, 400);
		}
		if(dataArr.length >= 1) {
			$('#btn_delete').css('display', 'block').removeClass('fadeOutRight').addClass('animated fadeInRight');

		} else {
			$('#btn_delete').addClass('fadeOutRight');
			setTimeout(function() {
				$('#btn_delete').css('display', 'none');
			}, 400);
		}
	}
	//修改按钮事件
	$('#btn_edit').click(function() {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.tableBody').addClass('animated slideOutLeft');
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
			}, 500)
			$('.changeBody').css('display', 'block');
			$('.changeBody').addClass('animated slideInRight');
			$('#edit_ID').val(dataArr[0].ID);
			$('#edit_LoginName').val(dataArr[0].LoginName);
			$('#edit_Name').val(dataArr[0].Name);
			$('#edit_Tel').val(dataArr[0].Tel);
			$('#edit_Email').val(dataArr[0].Email);
			if(dataArr[0].Attribute == 1) {
				$("#editForm input[name=Attribute]:eq(0)").prop("checked", true);
				$("#editForm input[name=Attribute]:eq(1)").prop("checked", false);
			} else if(dataArr[0].Attribute == 2) {
				$("#editForm input[name=Attribute]:eq(1)").prop("checked", true);
				$("#editForm input[name=Attribute]:eq(0)").prop("checked", false);
			}
			//先清空角色复选框
			$('#editForm .edit input').prop('checked', false);
			//获取用户角色
			$.post('../index.php/admin/Index/getUserById', {
					ID: dataArr[0].ID
				},
				function(data) {
					var roleIDArr = data.res.user.RoleID;
					//将对应用户的角色列表显示到对应的修改页
					for(var i = 0; i < roleIDArr.length; i++) {
						for(var j = 0; j < $('#editForm .edit input').length; j++) {
							if(roleIDArr[i] == $('#editForm .edit input:eq(' + j + ')').val()) {
								$('#editForm .edit input:eq(' + j + ')').prop('checked', true);
							}
						}
					}
				}
			);
		})
	
	
	//统计按钮
	$('#btn_stastic').click(function() {
			
			$('.tableBody').addClass('animated slideOutLeft');
			
			$('.changeBody_stastic').css('display', 'block');
			$('.changeBody_stastic').addClass('animated slideInRight');
		
			//先清空角色复选框
			$('#staticForm .edit input').prop('checked', false);
			
			
			//加载统计图表
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
				
				initStaticChart();
			}, 500)
			
		});
		
			//综合统计按钮
		$('#btn_stasticAll').click(function() {
			
			$('.tableBody').addClass('animated slideOutLeft');
			
			$('.changeBody_stastic').css('display', 'block');
			$('.changeBody_stastic').addClass('animated slideInRight');
		
			//先清空角色复选框
			$('#staticForm .edit input').prop('checked', false);
			
			
			//加载统计图表
			setTimeout(function() {
				$('.tableBody').removeClass('animated slideOutLeft').css('display', 'none');
				
				initStaticAllChart();
			}, 500)
			
		});
		/*
		 * 用户管理增加用户页面所有事件
		 */
		//增加页面表单验证   
		// Validate the form manually
	$('#add_saveBtn').click(function() {
		//点击保存时触发表单验证
		$('#addForm').bootstrapValidator('validate');
		//如果表单验证正确，则请求后台添加用户
		if($("#addForm").data('bootstrapValidator').isValid()) {
			var _info = $('#addForm').serialize();
			$.post(
				"../index.php/admin/index/insertUser",
				$('#addForm').serialize(),
				function(data) {
					//后台返回添加成功
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
						//隐藏修改与删除按钮
						$('#btn_delete').css('display', 'none');
						$('#btn_edit').css('display', 'none');
					}
					//否则
					else {}
				}
			)
		}
	});
	//增加页面返回按钮事件
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
	//修改页面回退按钮事件
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
		//修改页面保存按钮事件
	$('#edit_saveBtn').click(function() {
			$('#editForm').bootstrapValidator('validate');
			if($("#editForm").data('bootstrapValidator').isValid()) {
				$.post("../index.php/admin/index/updateUserById",
					$('#editForm').serialize(),
					function(data) {
						if(data.suc == true) {
							//隐藏修改与删除按钮
							$('#btn_delete').css('display', 'none');
							$('#btn_edit').css('display', 'none');
							//回退到人员管理主页
							$('.changeBody').addClass('animated slideOutLeft');
							setTimeout(function() {
								$('.changeBody').removeClass('animated slideOutLeft').css('display', 'none');
							}, 500)
							$('.tableBody').css('display', 'block').addClass('animated slideInRight');
							//刷新人员管理主页
							$('#mytab').bootstrapTable('refresh', {
								url: '../index.php/admin/index/userManagement'
							});
							//修改页面表单重置
							$('#editForm').data('bootstrapValidator').resetForm(true);
						} else {}
					}
				)
			}
		})
		//删除事件按钮
	$('#btn_delete').click(function() {
			var dataArr = $('#mytab').bootstrapTable('getSelections');
			$('.popup_de .show_msg').text('确定要删除该用户吗?');
			$('.popup_de').addClass('bbox');
			$('.popup_de .btn_submit').one('click', function() {
				var ID = [];
				for(var i = 0; i < dataArr.length; i++) {
					ID[i] = dataArr[i].ID;
				}
				$.post("../index.php/admin/index/deleteUserById", {
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
					});
			})
		})
		//弹出框取消按钮事件
		　　 $('.popup_de .btn_cancel').click(function() {
			$('.popup_de').removeClass('bbox');　　
		})
		//弹出框关闭按钮事件
	$('.popup_de .popup_close').click(function() {
		$('.popup_de').removeClass('bbox');　　
	})
}
function initStaticChart()
{
	
	var option = {
					tooltip: {
						trigger: 'item',
						formatter: "{a} <br/>{b}: {c} ({d}%)"
					},
					legend: {
						orient: 'vertical',
						x: 'left',
						data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
					},
					series: [{
						name: '数据统计',
						type: 'pie',
						selectedMode: 'single',
						radius: [0, '30%'],

						label: {
							normal: {
								position: 'inner'
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data: [{
							value: 335,
							name: 'DC',
							selected: true
						}, {
							value: 679,
							name: 'GF5'
						}, {
							value: 1548,
							name: 'CSES'
						}]
					}, {
						name: '数据统计',
						type: 'pie',
						radius: ['40%', '55%'],
						label: {
							normal: {
								formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
								backgroundColor: '#eee',
								borderColor: '#aaa',
								borderWidth: 1,
								borderRadius: 4,
								// shadowBlur:3,
								// shadowOffsetX: 2,
								// shadowOffsetY: 2,
								// shadowColor: '#999',
								// padding: [0, 7],
								rich: {
									a: {
										color: '#999',
										lineHeight: 22,
										align: 'center'
									},
									// abg: {
									//     backgroundColor: '#333',
									//     width: '100%',
									//     align: 'right',
									//     height: 22,
									//     borderRadius: [4, 4, 0, 0]
									// },
									hr: {
										borderColor: '#aaa',
										width: '100%',
										borderWidth: 0.5,
										height: 0
									},
									b: {
										fontSize: 16,
										lineHeight: 33
									},
									per: {
										color: '#eee',
										backgroundColor: '#334455',
										padding: [2, 4],
										borderRadius: 2
									}
								}
							}
						},
						data: [{
							value: 335,
							name: '接收'
						}, {
							value: 310,
							name: '接收'
						}, {
							value: 234,
							name: '完成'
						}, {
							value: 135,
							name: '未完成'
						}, {
							value: 1048,
							name: '传输完成'
						}, {
							value: 251,
							name: '传输中断'
						}, {
							value: 147,
							name: '结束'
						}, {
							value: 102,
							name: '已完成'
						}]
					}]
				};
				//var myChart = echarts.init(document.getElementById('main'));
				var myChart = echarts.init(document.getElementById('main'), 'vintage');
				
				 // 使用刚指定的配置项和数据显示图表。
      			myChart.setOption(option);
}

function dataFormatter(obj) {
    var pList = ['北京','天津','河北','山西','内蒙古','辽宁','吉林','黑龙江','上海','江苏','浙江','安徽','福建','江西','山东'];
    var temp;
    for (var year = 2005; year <= 2011; year++) {
        var max = 0;
        var sum = 0;
        temp = obj[year];
        for (var i = 0, l = temp.length; i < l; i++) {
            max = Math.max(max, temp[i]);
            sum += temp[i];
            obj[year][i] = {
                name : pList[i],
                value : temp[i]
            }
        }
        obj[year + 'max'] = Math.floor(max / 100) * 100;
        obj[year + 'sum'] = sum;
    }
    return obj;
}
function initStaticAllChart()
{
	var dataMap = {};


dataMap.dataGDP = dataFormatter({
    //max : 60000,
    2011:[16251.93,11307.28,24515.76,11237.55,14359.88,22226.7,10568.83,12582,19195.69,49110.27,32318.85,15300.65,17560.18,11702.82,45361.85],
    2010:[14113.58,9224.46,20394.26,9200.86,11672,18457.27,8667.58,10368.6,17165.98,41425.48,27722.31,12359.33,14737.12,9451.26,39169.92],
    2009:[12153.03,7521.85,17235.48,7358.31,9740.25,15212.49,7278.75,8587,15046.45,34457.3,22990.35,10062.82,12236.53,7655.18,33896.65],
    2008:[11115,6719.01,16011.97,7315.4,8496.2,13668.58,6426.1,8314.37,14069.87,30981.98,21462.69,8851.66,10823.01,6971.05,30933.28],
    2007:[9846.81,5252.76,13607.32,6024.45,6423.18,11164.3,5284.69,7104,12494.01,26018.48,18753.73,7360.92,9248.53,5800.25,25776.91],
    2006:[8117.78,4462.74,11467.6,4878.61,4944.25,9304.52,4275.12,6211.8,10572.24,21742.05,15718.47,6112.5,7583.85,4820.53,21900.19],
    2005:[6969.52,3905.64,10012.11,4230.53,3905.03,8047.26,3620.27,5513.7,9247.66,18598.69,13417.68,5350.17,6554.69,4056.76,18366.87]});

dataMap.dataPI = dataFormatter({
    //max : 4000,
    2011:[136.27,159.72,2905.73,641.42,1306.3,1915.57,1277.44,1701.5,124.94,3064.78,1583.04,2015.31,1612.24,1391.07,3973.85],
    2010:[124.36,145.58,2562.81,554.48,1095.28,1631.08,1050.15,1302.9,114.15,2540.1,1360.56,1729.02,1363.67,1206.98,3588.28],
    2009:[118.29,128.85,2207.34,477.59,929.6,1414.9,980.57,1154.33,113.82,2261.86,1163.08,1495.45,1182.74,1098.66,3226.64],
    2008:[112.83,122.58,2034.59,313.58,907.95,1302.02,916.72,1088.94,111.8,2100.11,1095.96,1418.09,1158.17,1060.38,3002.65],
    2007:[101.26,110.19,1804.72,311.97,762.1,1133.42,783.8,915.38,101.84,1816.31,986.02,1200.18,1002.11,905.77,2509.14],
    2006:[88.8,103.35,1461.81,276.77,634.94,939.43,672.76,750.14,93.81,1545.05,925.1,1011.03,865.98,786.14,2138.9],
    2005:[88.68,112.38,1400,262.42,589.56,882.41,625.61,684.6,90.26,1461.51,892.83,966.5,827.36,727.37,1963.51]});

dataMap.dataSI = dataFormatter({
    //max : 26600,
    2011:[3752.48,5928.32,13126.86,6635.26,8037.69,12152.15,5611.48,5962.41,7927.89,25203.28,16555.58,8309.38,9069.2,6390.55,24017.11],
    2010:[3388.38,4840.23,10707.68,5234,6367.69,9976.82,4506.31,5025.15,7218.32,21753.93,14297.93,6436.62,7522.83,5122.88,21238.49],
    2009:[2855.55,3987.84,8959.83,3993.8,5114,7906.34,3541.92,4060.72,6001.78,18566.37,11908.49,4905.22,6005.3,3919.45,18901.83],
    2008:[2626.41,3709.78,8701.34,4242.36,4376.19,7158.84,3097.12,4319.75,6085.84,16993.34,11567.42,4198.93,5318.44,3554.81,17571.98],
    2007:[2509.4,2892.53,7201.88,3454.49,3193.67,5544.14,2475.45,3695.58,5571.06,14471.26,10154.25,3370.96,4476.42,2975.53,14647.53],
    2006:[2191.43,2457.08,6110.43,2755.66,2374.96,4566.83,1915.29,3365.31,4969.95,12282.89,8511.51,2711.18,3695.04,2419.74,12574.03],
    2005:[2026.51,2135.07,5271.57,2357.04,1773.21,3869.4,1580.83,2971.68,4381.2,10524.96,7164.75,2245.9,3175.92,1917.47,10478.62]});


dataMap.dataFinancial = dataFormatter({
    //max : 3200,
    2011:[2215.41,756.5,746.01,519.32,447.46,755.57,207.65,370.78,2277.4,2600.11,2730.29,503.85,862.41,357.44,1640.41],
    2010:[1863.61,572.99,615.42,448.3,346.44,639.27,190.12,304.59,1950.96,2105.92,2326.58,396.17,767.58,241.49],
    2009:[1603.63,461.2,525.67,361.64,291.1,560.2,180.83,227.54,1804.28,1596.98,1899.33,359.6,612.2,165.1,1044.9],
    2008:[1519.19,368.1,420.74,290.91,219.09,455.07,147.24,177.43,1414.21,1298.48,1653.45,313.81,497.65,130.57,880.28],
    2007:[1302.77,288.17,347.65,218.73,148.3,386.34,126.03,155.48,1209.08,1054.25,1251.43,223.85,385.84,101.34,734.9],
    2006:[982.37,186.87,284.04,169.63,108.21,303.41,100.75,74.17,825.2,653.25,906.37,166.01,243.9,79.75,524.94],
    2005:[840.2,147.4,213.47,135.07,72.52,232.85,83.63,35.03,675.12,492.4,686.32,127.05,186.12,69.55,448.36]});


var option = {
    baseOption: {
        timeline: {
            // y: 0,
            axisType: 'category',
            // realtime: false,
            // loop: false,
            autoPlay: true,
            // currentIndex: 2,
            playInterval: 1000,
            // controlStyle: {
            //     position: 'left'
            // },
            data: [
                {
                    value: '2005-01-01',
                    tooltip: {
                        formatter: '{b} GDP达到一个高度'
                    },
                    symbol: 'diamond',
                    symbolSize: 16
                },
                '2006-01-01', '2007-01-01','2008-01-01','2009-01-01','2010-01-01',
                {
                    value: '2011-01-01',
                    tooltip: {
                        formatter: function (params) {
                            return params.name + 'GDP达到又一个高度';
                        }
                    },
                    symbol: 'diamond',
                    symbolSize: 18
                },
            ],
            label: {
                formatter : function(s) {
                    return (new Date(s)).getFullYear();
                }
            }
        },
        title: {
            subtext: '数据来自国家统计局'
        },
        tooltip: {
        },
        legend: {
            x: 'right',
            data: ['第一产业', '第二产业', 'GDP'],
            selected: {
                'GDP': false, '金融': false
            }
        },
        calculable : true,
        grid: {
            top: 80,
            bottom: 100,
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    label: {
                        show: true,
                        formatter: function (params) {
                            return params.value.replace('\n', '');
                        }
                    }
                }
            }
        },
        xAxis: [
            {
                'type':'category',
                'axisLabel':{'interval':0},
                'data':[
                    '北京','\n天津','河北','\n山西','内蒙古','\n辽宁','吉林','\n黑龙江',
                    '上海','\n江苏','浙江','\n安徽','福建','\n江西','山东'
                ],
                splitLine: {show: false}
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: 'GDP（亿元）'
            }
        ],
        series: [
            {name: 'GDP', type: 'bar'},
            {name: '金融', type: 'bar'},
            {name: '第一产业', type: 'bar'},
            {name: '第二产业', type: 'bar'},
            {
                name: 'GDP占比',
                type: 'pie',
                center: ['75%', '35%'],
                radius: '28%',
                z: 100
            }
        ]
    },
    options: [
       
        
        
        {
            title : {text: '2005全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2005']},
                {data: dataMap.dataFinancial['2005']},
                {data: dataMap.dataPI['2005']},
                {data: dataMap.dataSI['2005']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2005sum']},
                    {name: '第二产业', value: dataMap.dataSI['2005sum']}
                ]}
            ]
        },
        {
            title : {text: '2006全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2006']},
                {data: dataMap.dataFinancial['2006']},
                {data: dataMap.dataPI['2006']},
                {data: dataMap.dataSI['2006']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2006sum']},
                    {name: '第二产业', value: dataMap.dataSI['2006sum']},
                ]}
            ]
        },
        {
            title : {text: '2007全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2007']},
                {data: dataMap.dataFinancial['2007']},
                {data: dataMap.dataPI['2007']},
                {data: dataMap.dataSI['2007']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2007sum']},
                    {name: '第二产业', value: dataMap.dataSI['2007sum']}
                ]}
            ]
        },
        {
            title : {text: '2008全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2008']},
                {data: dataMap.dataFinancial['2008']},
                {data: dataMap.dataPI['2008']},
                {data: dataMap.dataSI['2008']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2008sum']},
                    {name: '第二产业', value: dataMap.dataSI['2008sum']}
                ]}
            ]
        },
        {
            title : {text: '2009全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2009']},
                {data: dataMap.dataFinancial['2009']},
                {data: dataMap.dataPI['2009']},
                {data: dataMap.dataSI['2009']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2009sum']},
                    {name: '第二产业', value: dataMap.dataSI['2009sum']}
                ]}
            ]
        },
        {
            title : {text: '2010全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2010']},
                {data: dataMap.dataFinancial['2010']},
                {data: dataMap.dataPI['2010']},
                {data: dataMap.dataSI['2010']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2010sum']},
                    {name: '第二产业', value: dataMap.dataSI['2010sum']}
                ]}
            ]
        },
        {
            title : {text: '2011全国宏观经济指标'},
            series : [
                {data: dataMap.dataGDP['2011']},
                {data: dataMap.dataFinancial['2011']},
                {data: dataMap.dataPI['2011']},
                {data: dataMap.dataSI['2011']},
                {data: [
                    {name: '第一产业', value: dataMap.dataPI['2011sum']},
                    {name: '第二产业', value: dataMap.dataSI['2011sum']}
                ]}
            ]
        }
    ]
};
 var myChart = echarts.init(document.getElementById('main'));
				
				 // 使用刚指定的配置项和数据显示图表。
     myChart.setOption(option);
}
function tableHeight() {
	return $(window).height() - 140;
}