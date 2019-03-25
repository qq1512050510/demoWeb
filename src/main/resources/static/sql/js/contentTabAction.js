var upClickTabId_A = "";
var cuClickTabId_A = "";

function closeTabInitByClass(butClass) {
	$("." + butClass).click(function(e) {
		var tabContId = $(this).parent().attr('aria-controls');
		console.log(tabContId);
		$(this).parent().parent().remove();
		console.log(tabContId);
		$('#' + tabContId).remove();
		//enhance current 
		console.log("current:" + upClickTabId_A + "-" + tabContId);
		if(tabContId === upClickTabId_A || $('a[aria-controls = "' + upClickTabId_A + '"]').size() === 0)
			$('#contentnavid a:first').tab('show');
		else
			$('a[aria-controls = "' + upClickTabId_A + '"]').tab('show');
		return false;
	});
}

//
function recordMenuTabClick() {
	$('#contentnavid>li>a').click(function() {
		if(cuClickTabId_A === "") {
			cuClickTabId_A = $(this).attr('aria-controls');
		} else if(cuClickTabId_A !== $(this).attr('aria-controls')) {
			upClickTabId_A = cuClickTabId_A;
			cuClickTabId_A = $(this).attr('aria-controls');
		}
		return true;
	});
}

//later design
var lastTab = "";

//after
$(document).ready(function() {
	//contentTabInit();
	closeTabInitByClass('close');
	recordMenuTabClick();
	divContentResize();
});

// winow resize
$(window).resize(function() {
	divContentResize();
});

function divContentResize() {
	$('div[role="tabpanel"]').height(($('.content').height() - $('#contentnavid').height()));
}

