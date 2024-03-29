
$(function() {
	// Ajaxを実行
	execAjaxFooterCategory('#footerPageItems-1', '#viewLevel');
});

function execAjaxFooterCategory(appendId, viewLevelId){

	var viewLevel = $(viewLevelId).val();
	var cid = (new URL(document.location)).searchParams.get('cid');
	if(!cid){
		cid = "";
	}
	
	var hsn = (new URL(document.location)).searchParams.get('hsn');
	if(!hsn){
		hsn = "";
	}

	// Ajax通信を行う
	$.ajax({
		 type			:	"GET"
		,url			:	"/categorydatalistServlet?sl=1&el=3"
		,data		 	:	{"viewLevel":viewLevel, "cid":cid}
		,dataType 	:	"json"
		,timeout		:	30000
	})
	.done(function(data){ setFooterMenuHtml(data.array, appendId, cid); })
	.fail(function(data){ setFooterMenuHtml(data.array, appendId, cid); })
	;
}

function setFooterMenuHtml(resData, appendId, curCid) {

	if (!resData) {
		return;
	}

	var list = resData;
	if (list == null || list.length <= 0) {
		$(appendId).css({ display: 'none' });
		$(appendId + '_hideTarget').css({ display: 'none' });
		return;
	}

	//カテゴリーツリー生成
	for (var i = 0, size = list.length; i < size; i++) {

		var appendHtml = '';
		var selectedHtml = '';

		if (list[i].categoryid == curCid) {
			selectedHtml = ' class="is-current" ';
		}

		//　第一階層
		if (list[i].categorylevel == "1") {

			appendHtml = '<div class="p-top__footercategory-level1 ' + list[i].categoryid + '">'
				+ '<a class="c-button" href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
				+ '<p>' + list[i].categoryname + '</p>'
				+ '</a></div>';

			$(appendId).append(appendHtml);
		}

		//　第二階層
		if (list[i].categorylevel == "2") {

			// セールの場合
			if ( (list[i - 1].categoryid == "c-sale") || (list[i - 1].categoryid == "c-series-22") ) {
				appendHtml =
					'<div class="p-top__footercategory-menu ' + list[i].cidParent + '"><ul class="p-top__footercategory-level2only">'
					+ '<li id=' + list[i].cidParent + '>'
					+ '<a href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
					+ '<p class="c-textlink c-textlink--hover">' + list[i].categoryname + '</p>'
					+ '</a></li></ul></div>';
				$('#footerPageItems-1').append(appendHtml);

			} else if ( (list[i - 1].cidParent == "c-sale") || (list[i - 1].cidParent == "c-series-22") ) {

				appendHtml =
					'<li class=' + list[i].cidParent + '>'
					+ '<a href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
					+ '<p class="c-textlink c-textlink--hover">' + list[i].categoryname + '</p>'
					+ '</a></li>';
				$('#footerPageItems-1').find('.' + list[i - 1].cidParent).find('ul').append(appendHtml);

			} else { 

				appendHtml =
					'<div class="p-top__footercategory-menu ' + list[i].categoryid + ' ' +  list[i].cidParent + '">'
				+ '<p class="p-top__footercategory-level2">'
				+ '<a class="c-textlink c-textlink--hover" href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
				+ list[i].categoryname
				+ '</a></p></div>';
					$('#footerPageItems-1').append(appendHtml);
			}
		}

		//　第三階層
		if (list[i].categorylevel == "3") {

			// 直前のループが第二階層の時
			if (list[i - 1].categorylevel == "2") {

				appendHtml =
					'<ul class="p-top__footercategory-level3"><li class=' + list[i].cidParent + '>'
					+ '<a href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
					+ '<p class="c-textlink c-textlink--hover">' + list[i].categoryname + '</p>'
					+ '</a></li></ul>';
					$('#footerPageItems-1').find('.' + list[i - 1].categoryid).append(appendHtml)

			}

			// 直前のループが第三階層の時
			else {

				appendHtml =
					'<li data-hierarchy="3" class=' + list[i].cidParent + '>'
					+ '<a href="/goods/list?cid=' + list[i].categoryid + '"' + selectedHtml + '>'
					+ '<p class="c-textlink c-textlink--hover">' + list[i].categoryname + '</p>'
					+ '</a></li>';
					$('#footerPageItems-1').find('.' + list[i - 1].cidParent).find('.p-top__footercategory-level3').append(appendHtml);

			}
		}
	}

	// アウトレットの位置を移動
	$('.p-top__footer .outlet').insertBefore('.p-top__footercategory-level1.catalog');

	// すべて見るを非表示に
	let linkAll_lv2 = $('.p-top__footercategory-level2 a:contains("すべて見る")');
	let linkAll_lv2only = $('.p-top__footercategory-level2only a:contains("すべて見る")');
	linkAll_lv2.parents('.p-top__footercategory-menu').hide();
	linkAll_lv2only.parents('li').hide();
	


}