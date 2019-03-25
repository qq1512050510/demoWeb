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
		menulist += '<li><a href="' + n.menuUrl + '" cont="' + n.menuname + '" contId="' + n.menuid + '"><span class="icon color7"><i class="fa ' + n.icon + '"></i></span>' + n.menuname + '' + menuChildAppend;

		$.each(n.menus, function(j, o) {
			menulist += '<li><a href="' + o.menuUrl + '" cont="' + o.menuname + '" contId="' + o.menuid + '">' + o.menuname + '</a></li>';
		})
		if(isNotNull(n.menus)) {
			menulist += '</ul>'
		}
		menulist += '</li>';
		$("#leftMenu").append(menulist);
	});
}
/*menuAciton click slideToggle*/
function menuAction() {
	$('#leftMenu > li > a').click(function() {

		if($(this).attr('contid') !==$('#leftMenu>li>.active').attr('contid')) {
			$('#leftMenu>li>.active').parent().find('ul').slideToggle();
			$('#leftMenu>li>.active').removeClass('active');
		}

		if($(this).attr('class') != 'active') {
			console.log($(this));
			$(this).parent().find('ul').slideToggle();
			$(this).addClass('active');
		} else {
			$(this).parent().find('ul').slideToggle();
			$(this).removeClass('active');
		}
		return false;
	});
	//add open Tab
	menuClickOpenTab();
}
/*menu ahref click open tab action*/
function menuClickOpenTab() {
	$('#leftMenu a').bind('click', function(e) {
		e.preventDefault();
		console.log(this);
		var currAUrl = $(this).attr('href');
		var currACont = $(this).attr('cont');
		var contId = $(this).attr('contId');
		var options = {};
		if(currAUrl !== "#") {
			options.titleTab = currACont;
			options.urlTab = currAUrl;
			options.idTab = contId;
			AddTab(options);
		}
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

//add tab Js 
function AddTab(options) {
	var _options = {};
	_options = $.loadData(options);
	console.log(_options);
	var appendTabId = 'tabId' + options.idTab;
	if($('#' + appendTabId).size() != 0) {
		$('a[href="#' + appendTabId + '"]').click();
		return false;
	}
	$("#contentnavid").append('<li role="presentation" id="closetabli">' +
		'<a href="#' + appendTabId + '" aria-controls="' + appendTabId + '" role="tab" data-toggle="tab" style="height: 40px;"><span style="position: relative;float: left;">' + _options.titleTab + '</span>' +
		'<button type="button" class="close" aria-label="Close" style="position: relative;float: left;"><span aria-hidden="true" style="color:#8d7979;">&times;</span></button></a></li>');
	$("#tabContent").append('<div role="tabpanel" class="tab-pane" id="' + appendTabId + '"></div>');
	//$('a[aria-controls="'+appendTabId+'"').tab('show');
	$('a[aria-controls = "' + appendTabId + '"]').tab('show');

	//loadPageToDiv(_options.urlTab,appendTabId);
	loadpageToIframe(_options.urlTab, appendTabId);

	//resize && add action
	divContentResize();
	recordMenuTabClick();
	closeTabInitByClass('close');

}

function loadpageToIframe(url, divId) {
	$("#" + divId).append('<iframe src="' + url + '" class="iframeTab"></iframe>');
};
//div加载数据
function loadPageToDiv(url, divId) {
	$.get(url, function(data) {
		$("#" + divId).html(data); //初始化加载界面  
	});
}

$.loadData = function() {
	var _options = {};
	if(typeof arguments[0] === 'object') {
		_options = arguments[0];
	} else if(typeof arguments[0] === 'string') {
		_options = $.parseJSON(arguments[0]);
	}
	return _options;
}

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
});

// winow resize
$(window).resize(function() {
	var marginLeft = $('.content').css('margin-left');
	if(marginLeft !== "0px")
		$('.sidebar').css('display', '');
});