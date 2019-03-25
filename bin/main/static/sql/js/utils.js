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
function menuActionInit()
{
	$('#btn_edit').addClass('fadeOutRight');
	setTimeout(function() {
		$('#btn_edit').css('display', 'none');
	}, 400);
	$('#btn_delete').addClass('fadeOutRight');
	setTimeout(function() {
		$('#btn_delete').css('display', 'none');
	}, 400);
	$('#btn_stastic').addClass('fadeOutRight');
	setTimeout(function() {
		$('#btn_stastic').css('display', 'none');
	}, 400);
}

/**
 * 行单击事件
 * 
 * @param 
 * @param 
 * @returns
 */
function rowClickEvent(){
	$('.bootstrap-table').on('click-row.bs.table', function(e, row, element) {
		$(element[0].cells[0].children).click();
	});
}


/**
 * 隐藏表单，显示列表
 * 
 * @param hiddenForm
 *            需要隐藏的div中的form的id
 * @param hiddenDivClass
 *            需要隐藏的dic的class
 * @param displayDivClass
 *            需要显示的div的class
 * @returns
 */
function displayTableBody(hiddenForm, hiddenDivClass, displayDivClass) {
    $(hiddenForm)[0].reset();
    $(hiddenForm).bootstrapValidator('resetForm', false);  
	$(hiddenDivClass).addClass('slideOutLeft');
	setTimeout(function() {
		$(hiddenDivClass).removeClass('slideOutLeft').css('display', 'none');
	}, 50);
	$(displayDivClass).css('display', 'block').addClass('slideInRight');
}
/*
 * 加载form表单数据，支持前缀
 */
function loadDataByPrefix(jsonStr, prefix) {
      var key, value, tagName, type, arr;
      for (x in jsonStr) {
          key = x;
          value = jsonStr[x];
          try
          {
              $("[id='" + prefix + key + "']").each(function () {
                  tagName = $(this)[0].tagName;
                  type = $(this).attr('type');
                  if (tagName == 'INPUT') {
                      if (type == 'radio') {
                          $(this).attr('checked', $(this).val() == value);
                      } else if (type == 'checkbox') {
                          arr = value.split(',');
                          for (var i = 0; i < arr.length; i++) {
                              if ($(this).val() == arr[i]) {
                                  $(this).attr('checked', true);
                                  break;
                              }
                          }
                      } else {
                          $(this).val(value);
                      }
                  } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                      $(this).val(value);
                  }
                  else if(tagName == 'SPAN') {
                      $(this).html(value);
                  }
              });
          }
          catch(e){}
      }
}

/*
 * 加载form表单数据，支持前缀
 */
function loadDataById(jsonStr) {
	
      var key, value, tagName, type, arr;
      for (x in jsonStr) {
          key = x;
          value = jsonStr[x];
          try
          {
              $("[id='" + key + "']").each(function () {
                  tagName = $(this)[0].tagName;
                  type = $(this).attr('type');
                  if (tagName == 'INPUT') {
                      if (type == 'radio') {
                          $(this).attr('checked', $(this).val() == value);
                      } else if (type == 'checkbox') {
                          arr = value.split(',');
                          for (var i = 0; i < arr.length; i++) {
                              if ($(this).val() == arr[i]) {
                                  $(this).attr('checked', true);
                                  break;
                              }
                          }
                      } else {
                          $(this).val(value);
                      }
                  } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                      $(this).val(value);
                  }
                  else if(tagName == 'SPAN') {
                      $(this).html(value);
                  }
              });
          }
          catch(e){}
      }
}