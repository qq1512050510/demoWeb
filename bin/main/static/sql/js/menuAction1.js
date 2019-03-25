/*
        作者：765819328@qq.com
        时间：2018-04-10
        描述：Menu Construct js
*/
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
			returnData = data;
			returnData.success = "yes";
		},
		error: function() {
			returnData.success = "no";

		}
	});
	return returnData;

}
//Menu
function constructMenu(menuData) {
	console.log(menuData);
	$.each(menuData.menus, function(i, n) {
		var menulist = '';
		var menuChildAppend = '';
		if(isNotNull(n.alarmCount) && n.alarmCount > 0) {
			menuChildAppend += '<span class="label label-default">' + n.alarmCount + '</span>';
		}
		if(isNotNull(n.menus)) {
			menuChildAppend += '<span class="caret"></span></a><ul>'
		} else {
			menuChildAppend += '</a>';
		}
		menulist += '<li><a href="#"><span class="icon color7"><i class="fa ' + n.icon + '"></i></span>' + n.menuname + '' + menuChildAppend;

		$.each(n.menus, function(j, o) {
			menulist += '<li><a href="' + o.menuUrl + '">' + o.menuname + '</a></li>';
		})
		if(isNotNull(n.menus)) {
			menulist += '</ul>'
		}
		menulist += '</li>';
		console.log(menulist);
		$("#leftMenu").append(menulist);
	});
}
/*menuAciton click slideToggle*/
function menuAction() {
	$('.nav > li > a').click(function() {
		if($(this).attr('class') != 'active') {
			console.log($(this));
			$(this).parent().find('ul').slideToggle();
			$(this).addClass('active');
		} else {
			$(this).parent().find('ul').slideToggle();
			$(this).removeClass('active');
		}
	});
	//add open Tab
	menuClickOpenTab();
}
/*menu ahref click open tab action*/
function menuClickOpenTab() {
	$('#leftMenu a').bind('click', function() {
		console.log(this);
		var currAUrl = $(this).attr('href');
		console.log(currAUrl);
		alert(currAUrl);
		return false;
	});
	//add content adjust

}



//true:not null |||||false: null
function isNotNull(dataJud) {
	if(dataJud !== undefined && dataJud.length !== 0)
		return true;
	else
		return false;
}

//before
$(function() {
	var menuLoadUrl = workDir + "data/_menu.json";
	var menuData = "";
	menuData = loadDataByUrl(menuLoadUrl);
	if(menuData.success === "yes") {
		constructMenu(menuData);
	} else {
		alert("no menu data");
	}
	//
	layoutAjust();
})

//after
$(document).ready(function() {
	menuAction();
	contentTabInit();
});

// winow resize
$(window).resize(function() {

});

////////////css adjust main
function layoutAjust() {
	contAjust("500px");
}

///////////css ajust fucntion
function menuAjust() {
	$('.sidebar ').css('height', $('.content').css('height'));
}

function contAjustByMenu() {
	$('.content ').css('height', $('.sidebar').css('height'));
}

function contAjust(minHeight) {
	$('.content').css('min-height', minHeight);
}

function contentTabInit() {
	var activeTab = "tab1"; //当前的tab页。默认为第一个tab页。
	var previousTab; //上一个打开的tab页。默认为空。

	//实现事件响应函数，当tab页被显示时会触发
	$('a[data-toggle = "tab"]').on('shown.bs.tab', function(e) {
		//获取当前被显示的tab页标签的aria-controls属性值
		activeTab = $(e.target).attr("aria-controls");
		//获取前一个被显示的tab页标签的aria-controls属性值
		previousTab = $(e.relatedTarget).attr("aria-controls");
	});

	//点击带关闭按钮tab页标签上的x后的响应事件
	$("#closetabbtn").click(function(e) {
		$(this).parent().parent().css("display", "none"); //隐藏tab头，调用remove方法就是删除了
		$("#closetab").css("display", "none"); //隐藏tab正文信息，调用remove方法就是删除了
		if(activeTab == "closetab") { //判断当前tab页是否是带关闭按钮的tab页，如果是，则显示上次打开的tab页
			$('#contentnavid a[href = "#' + previousTab + '"]').tab('show'); //显示tab页
		}
		return false; //一定要return false，阻止事件往上冒泡
	});

}