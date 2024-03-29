
$(function() {
	// Ajaxを実行
	execAjaxHeaderCategory('#headerPageItems-1', '#viewLevel');
});

function execAjaxHeaderCategory(appendId, viewLevelId){

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
	.done(function(data){ setHeaderHtml(data.array, appendId, cid,); })
	.fail(function(data){ setHeaderHtml(data.array, appendId, cid); })
	;
}

function setHeaderHtml(resData, appendId, curCid) {

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

			appendHtml = 
			'<li data-hierarchy="1"><span class="c-textlink" data-cid="' + list[i].categoryid + '">'
			+ list[i].categoryname
			+ '</span></li>';
				
			$(appendId).append(appendHtml);
		}

		var categoryId = list[i].categoryid;
		if (categoryId.includes('-all')) {
			categoryId = categoryId.slice(0, categoryId.length - 4);
		}

		//　第二階層
		if (list[i].categorylevel == "2") {

				// 直前のループが第一階層の時
				if (list[i - 1].categorylevel == "1") {

					// 配列の最後のであれば（リンクあり）
					if (list[i + 1] == null) {

						appendHtml =
							'<li data-hierarchy="2" data-cidparent="' + list[i].cidParent + '">'
							+ '<a class="c-textlink" href = "/goods/list?cid=' + categoryId + '"' + selectedHtml + 'data-cid="' + list[i].categoryid + '">'
							+ list[i].categoryname
							+ '</a></li>';
						
							$(appendId).append(appendHtml);

					// 直後のループが第三階層の時（リンクなし）
					} else if (list[i + 1] !== undefined && list[i + 1].categorylevel == "3") {

						appendHtml =
								'<li data-hierarchy="2" data-cidparent="' + list[i].cidParent + '">'
								+ '<span class="c-textlink" data-cid="' + list[i].categoryid + '">'
								+ list[i].categoryname
								+ '</span></li>';
							
							$(appendId).append(appendHtml);

						// 直後のループが第一 or 二階層の時（リンクあり）
						} else {
							appendHtml =
								'<li data-hierarchy="2" data-cidparent="' + list[i].cidParent + '">'
								+ '<a class="c-textlink" href = "/goods/list?cid=' + categoryId + '"' + selectedHtml + 'data-cid="' + list[i].categoryid + '">'
								+ list[i].categoryname
								+ '</a></li>';
							
								$(appendId).append(appendHtml);
						}

				}

				// 直前のループが第二階層の時
				else {

					// 直後のループが第三階層の時（リンクなし）
					if (list[i + 1] !== undefined && list[i + 1].categorylevel == "3") {

						appendHtml =
							'<li data-hierarchy="2" data-cidparent="' + list[i].cidParent + '">'
							+ '<span class="c-textlink" data-cid="' + list[i].categoryid + '">'
							+ list[i].categoryname
							+ '</span></li>';
							
							$(appendId).append(appendHtml);

					} else {
						appendHtml =
						'<li data-hierarchy="2" data-cidparent="' + list[i].cidParent + '">'
						+ '<a class="c-textlink" href = "/goods/list?cid=' + categoryId + '"' + selectedHtml + 'data-cid="' + list[i].categoryid + '">'
						+ list[i].categoryname
						+ '</a></li>';
						
						$(appendId).append(appendHtml);
					}
					
				}


		}

		//　第三階層
		if (list[i].categorylevel == "3") {

			appendHtml = 
				'<li data-hierarchy="3" data-cidparent="' + list[i].cidParent + '">'
				+ '<a class="c-textlink" href = "/goods/list?cid=' + categoryId + '"' + selectedHtml + '">'
				+ list[i].categoryname
				+ '</a></li>';
			$(appendId).append(appendHtml);
		}
	}

	// ---------------------------
	// 階層ごとにグループ化
	// ---------------------------
	let level1 = $('.l-headermenu__contents [data-hierarchy="1"]'); 	// 第一階層のliを取得
	let level1Target = level1.find('span');														// 第一階層のspanタグを取得（data属性取得するため）
	let level1Count = level1Target.length;														// 第一階層の数
	let level1Array = [];																							// 第一階層用の配列の準備

	let level2Target = $('.l-headermenu__contents [data-hierarchy="2"]').find('span');
	let level2Count = level2Target.length;
	let level2Array = [];

	// 第一階層
	level1.wrapAll('<ul class="l-headermenu__category l-headermenu__category--level1"></ul>');
	$('.l-headermenu__category--level1').wrapAll('<div class="l-headermenu__category-wrap l-headermenu__category-wrap--level1" data-simplebar data-simplebar-auto-hide="false">');

	// 第二階層
	for (let i = 0; i < level1Count; i++) { 
		level1Array.push($(level1Target[i]).data('cid'));
		let level2 = $('.l-headermenu__contents li[data-hierarchy="2"][data-cidparent="' + level1Array[i] + '"]');
		level2.wrapAll('<ul class="l-headermenu__category l-headermenu__category--level2"' + 'data-cid="' + level1Array[i] + '"></ul>');
	}
	$('.l-headermenu__category--level2').wrapAll('<div class="l-headermenu__category-wrap l-headermenu__category-wrap--level2" data-simplebar data-simplebar-auto-hide="false">');

	// 第三階層
	for (let i = 0; i < level2Count; i++) { 
		level2Array.push($(level2Target[i]).data('cid'));

		let level3 = $('.l-headermenu__contents li[data-hierarchy="3"][data-cidparent="' + level2Array[i] + '"]');
		level3.wrapAll('<ul class="l-headermenu__category l-headermenu__category--level3"' + 'data-cid="' + level2Array[i] + '"></ul>');
	}
	$('.l-headermenu__category--level3').wrapAll('<div class="l-headermenu__category-wrap l-headermenu__category-wrap--level3"  data-simplebar data-simplebar-auto-hide="false">');



	// ---------------------------
	// 多階層メニューの動作
	// ---------------------------
	$('.l-headermenu').on({
		
		'mouseover': function () {
			$(this).find('.l-headermenu__category-wrap--level1').addClass('is-active');
    },
    'mouseleave': function () {
      $(this).find('.l-headermenu__category-wrap').removeClass('is-active');
    }

	})
	
	// 第一階層表示
	$('.l-headermenu__category--level1 span').on('mouseenter', function () { 
		$('.l-headermenu__category-wrap--level3').removeClass('is-active');
	})
		
  // 第二階層表示
	$('.l-headermenu__category--level1 span').on('mouseenter', function () { 
		
		$('.l-headermenu__category-wrap--level2').addClass('is-active');

		// this以外にアクティブ要素がないか判定
		let cat = $(this).data('cid');

		$('.l-headermenu__category--level2').each(function () { 
			if ($(this).data('cid') === cat) {
				$(this).addClass('is-active');
			} else { 
				$(this).removeClass('is-active');
			}
		})
	})

	// 第三階層表示
	$('.l-headermenu__category--level2 span').on('mouseenter', function () { 
		
		// this以外にアクティブ要素がないか判定
		let cat = $(this).data('cid');

		$('.l-headermenu__category--level3').each(function () {
			if ($(this).data('cid') === cat) {
				$('.l-headermenu__category-wrap--level3').addClass('is-active');
				$(this).addClass('is-active');
			} else {
				$(this).removeClass('is-active');
			}
		})
	})
	
	$('.l-headermenu__category--level2 a').on('mouseenter', function () { 
		$('.l-headermenu__category-wrap--level3').removeClass('is-active');
	})
}