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
      			myChart.setOption(option,true);
}

function dataFormatter(obj) {
    var pList = ['三亚站','北极站','密云站','海南站'];
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
    2011:[16251.93,11307.28,24515.76,11237.55],
    2010:[14113.58,9224.46,20394.26,9200.86],
    2009:[12153.03,7521.85,17235.48,7358.31],
    2008:[11115,6719.01,16011.97,7315.4],
    2007:[9846.81,5252.76,13607.32,6024.4],
    2006:[8117.78,4462.74,11467.6,4878.61],
    2005:[6969.52,3905.64,10012.11,4230.53]});

dataMap.dataPI = dataFormatter({
    //max : 4000,
    2011:[136.27,159.72,2905.73,641.42],
    2010:[124.36,145.58,2562.81,554.48],
    2009:[118.29,128.85,2207.34,477.59],
    2008:[112.83,122.58,2034.59,313.58],
    2007:[101.26,110.19,1804.72,311.97],
    2006:[88.8,103.35,1461.81,276.77],
    2005:[88.68,112.38,1400,262.42]});

dataMap.dataSI = dataFormatter({
    //max : 26600,
    2011:[3752.48,5928.32,13126.86,6635.26],
    2010:[3388.38,4840.23,10707.68,5234,63],
    2009:[2855.55,3987.84,8959.83,3993.8],
    2008:[2626.41,3709.78,8701.34,4242.36],
    2007:[2509.4,2892.53,7201.88,3454.49],
    2006:[2191.43,2457.08,6110.43,2755.66,2374],
    2005:[2026.51,2135.07,5271.57,2357.04]});


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
                        	console.log(params);
                            return params.name + 'GDP达到又一个高度{b}';
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
            subtext: '数据来自资源卫星'
        },
        tooltip: {
        },
        legend: {
            x: 'right',
            data: ['已生产', '未生产', '传输完成'],
            selected: {
                '传输完成': true
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
                   '三亚站','北极站','密云站','海南站'
                ],
                splitLine: {show: false}
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '生产数据量'
            }
        ],
        series: [
            {name: '传输完成', type: 'bar'},
            {name: '已生产', type: 'bar'},
            {name: '未生产', type: 'bar'},
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
            title : {text: '2005资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2005']},
                {data: dataMap.dataPI['2005']},
                {data: dataMap.dataSI['2005']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2005sum']},
                    {name: '未生产', value: dataMap.dataSI['2005sum']},
                    {name: '传输完成',value: dataMap.dataGDP['2005sum']}
                ]}
            ]
        },
        {
            title : {text: '2006资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2006']},
                {data: dataMap.dataPI['2006']},
                {data: dataMap.dataSI['2006']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2006sum']},
                    {name: '未生产', value: dataMap.dataSI['2006sum']},
                    {name: '传输完成', value: dataMap.dataGDP['2006sum']}
                ]}
            ]
        },
        {
            title : {text: '2007资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2007']},
                {data: dataMap.dataPI['2007']},
                {data: dataMap.dataSI['2007']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2007sum']},
                    {name: '未生产', value: dataMap.dataSI['2007sum']},
                    {name: '传输完成',value: dataMap.dataGDP['2007sum']}
                ]}
            ]
        },
        {
            title : {text: '2008资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2008']},
                {data: dataMap.dataPI['2008']},
                {data: dataMap.dataSI['2008']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2008sum']},
                    {name: '未生产', value: dataMap.dataSI['2008sum']},
                    {name: '传输完成', value: dataMap.dataGDP['2008sum']}
                ]}
            ]
        },
        {
            title : {text: '2009资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2009']},
                {data: dataMap.dataPI['2009']},
                {data: dataMap.dataSI['2009']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2009sum']},
                    {name: '未生产', value: dataMap.dataSI['2009sum']},
                    {name: '传输完成', value: dataMap.dataGDP['2009sum']}
                ]}
            ]
        },
        {
            title : {text: '2010资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2010']},
                {data: dataMap.dataPI['2010']},
                {data: dataMap.dataSI['2010']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2010sum']},
                    {name: '未生产', value: dataMap.dataSI['2010sum']},
                    {name: '传输完成', value: dataMap.dataGDP['2010sum']}
                ]}
            ]
        },
        {
            title : {text: '2011资源卫星数据生产情况'},
            series : [
                {data: dataMap.dataGDP['2011']},
                {data: dataMap.dataPI['2011']},
                {data: dataMap.dataSI['2011']},
                {data: [
                    {name: '已生产', value: dataMap.dataPI['2011sum']},
                    {name: '未生产', value: dataMap.dataSI['2011sum']},
                    {name: '传输完成',value: dataMap.dataGDP['2011sum']}
                ]}
            ]
        }
    ]
};
 var myChart = echarts.init(document.getElementById('main'));
				
				 // 使用刚指定的配置项和数据显示图表。
     myChart.setOption(option,true);
}
function tableHeight() {
	return $(window).height() - 140;
}