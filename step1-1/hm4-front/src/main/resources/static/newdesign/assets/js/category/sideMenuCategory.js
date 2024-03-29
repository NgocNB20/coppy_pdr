$(function() {
	// Ajaxを実行
	execAjaxCategory('.sidemenuPageItems-1', '#viewLevel');
});

function execAjaxCategory(appendId, viewLevelId){

	var viewLevel = $(viewLevelId).val();
	var cid = (new URL(document.location)).searchParams.get('cid');
	if(!cid){
		cid = "";
	}


	// Ajax通信を行う
	$.ajax({
		 type			:	"GET"
		,url			:	"/categorydatalistServlet?sl=1&el=3"
		,data		 	:	{"viewLevel":viewLevel, "cid":cid}
		,dataType 	:	"json"
		,timeout		:	30000
	})
	.done(function(data){ setSideMenuHtml(data.array, appendId, cid); })
	.fail(function(data){ setSideMenuHtml(data.array, appendId, cid); })
	;
}

function setSideMenuHtml(resData, appendId, curCid) {

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
	$('.l-category').each(function () {
		for (var i = 0, size = list.length; i < size; i++) {

			var appendHtml = '';
			var selectedHtml = '';
			var selectedHtmlNoClass = '';
			var categoryWrap = $(this).parent('.l-category-wrap');

			if (list[i].categoryid == curCid) {
				selectedHtml = ' class="is-current" ';
				selectedHtmlNoClass = ' is-current ';
			}

			var categoryId = list[i].categoryid;
			if (categoryId.includes('-all')) {
				categoryId = categoryId.slice(0, categoryId.length - 4);
			}

			//　第一階層
			if (list[i].categorylevel == "1") {

				appendHtml = '<li data-hierarchy="1" id="' + list[i].categoryid + '">'
					+ '<div class="l-category__button">'
					+ '<span class="l-category__button-accordion js-accordion-button">'
					+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
					+ '</span></div></li>';

				$(categoryWrap).find(appendId).append(appendHtml);
			}

			//　第二階層
			if (list[i].categorylevel == "2") {

				// 直前のループが第一階層の時
				if (list[i - 1].categorylevel == "1") {

					// 配列の最後のであれば（リンクあり）
					if (list[i + 1] == null) {
						appendHtml =
							'<div class="l-category__block js-accordion-block">'
							+ '<ul class="l-category__level2">'
							+ '<li data-hierarchy="2" id = ' + list[i].categoryid + ' > '
							+ '<div class="l-category__button-anchor' + selectedHtmlNoClass + '">'
							+ '<a href="/goods/list?cid=' + categoryId + '"' + '>'
							+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
							+ '</a></div></li></></div>';
							$(categoryWrap).find('#' + list[i].cidParent).append(appendHtml);

						// 直後のループが第三階層の時（リンクなし）
					} else if (list[i + 1] !== undefined && list[i + 1].categorylevel == "3") {

						appendHtml =
							'<div class="l-category__block js-accordion-block">'
							+ '<ul class="l-category__level2">'
							+ '<li data-hierarchy="2" id = ' + list[i].categoryid + ' > '
							+ '<div class="l-category__button"><span class="l-category__button-accordion js-accordion-button">'
							+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
							+ '</span></div></li></ul></div>';
							$(categoryWrap).find('#' + list[i].cidParent).append(appendHtml);

						// 直後のループが第一 or 二階層の時（リンクあり）
					} else {
						appendHtml =
							'<div class="l-category__block js-accordion-block">'
							+ '<ul class="l-category__level2">'
							+ '<li data-hierarchy="2" id = ' + list[i].categoryid + ' > '
							+ '<div class="l-category__button-anchor' + selectedHtmlNoClass + '">'
							+ '<a href="/goods/list?cid=' + categoryId + '"' + '>'
							+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
							+ '</a></div></li></ul></div>';
							$(categoryWrap).find('#' + list[i].cidParent).append(appendHtml);

					}

				}

				// 直前のループが第二階層の時
				else {

					// 直後のループが第三階層の時（リンクなし）
					if (list[i + 1] !== undefined && list[i + 1].categorylevel == "3") {

						appendHtml =
							'<li data-hierarchy="2" id=' + list[i].categoryid + '>'
							+ '<div class="l-category__button"><span class="l-category__button-accordion js-accordion-button">'
							+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
							+ '</span></div></li>';
						$(categoryWrap).find('#' + list[i].cidParent).find('.l-category__level2').append(appendHtml);

					} else {
						// 直後のループが第一 or 二階層の時（リンクあり）

						appendHtml =
							'<li data-hierarchy="2" id=' + list[i].categoryid + '>'
							+ '<div class="l-category__button-anchor' + selectedHtmlNoClass + '">'
							+ '<a href="/goods/list?cid=' + categoryId + '"' + '>'
							+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
							+ '</a></div></li>';
							$(categoryWrap).find('#' + list[i].cidParent).find('.l-category__level2').append(appendHtml);

					}
				}
			}

			//　第三階層
			if (list[i].categorylevel == "3") {

				// 直前のループが第二階層の時（js-accordion-blockを作る）
				if (list[i - 1].categorylevel == "2") {

					appendHtml =
						'<div class="l-category__block js-accordion-block">'
						+ '<div class="l-category__level3"><ul>'
						+ '<li data-hierarchy="3" id=' + list[i].cidParent + ' > '
						+ '<a class="l-category__button-anchor ' + selectedHtmlNoClass + '"' + ' href="/goods/list?cid=' + categoryId + '"' + '>'
						+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
						+ '</a></li></ul></div></div>';
					$(categoryWrap).find('#' + list[i - 1].categoryid).append(appendHtml)

				}

				// 直前のループが第三階層の時
				else {

					appendHtml =
						'<li data-hierarchy="3" id=' + list[i].cidParent + '>'
						+ '<a class="l-category__button-anchor ' + selectedHtmlNoClass + '"' + ' href="/goods/list?cid=' + categoryId + '"' + '>'
						+ '<p class="c-textlink">' + list[i].categoryname + '</p>'
						+ '</a></li>';
					$(categoryWrap).find('#' + list[i - 1].cidParent).find('ul').append(appendHtml);

				}
			}
		}
	})


	// アコーディオンの処理を行います

	function accordion_lgSide() {
		
		document.querySelectorAll('.l-category .js-accordion-button').forEach(btn => {
			btn.addEventListener('click', () => {
				btn.closest('.l-nav-slide--btn,.l-category__button').classList.contains('is-opened') ? accordionHide(btn) : accordionShow(btn)
			})
		})

		const getHeight = target => {
			return target.clientHeight
		}

		function accordionShow(btn) {
			const btnParent = btn.closest('.l-nav-slide--btn , .l-category__button')
			const panel = btnParent.nextElementSibling
			btnParent.classList.add('is-opened')
			panel.classList.add('is-opened')
			if (btnParent.closest('li').dataset.hierarchy > 1) {
				const addHeight = getHeight(panel.firstElementChild)
				for (let i = 1; i < btnParent.closest('li').dataset.hierarchy; ++i) {
					const parentPanel = btnParent.closest('[data-hierarchy="' + i + '"]').querySelector('.js-accordion-block')
					parentPanel.style.height = Number(parentPanel.style.height.replace('px', '')) + addHeight + 'px'
				}
			}
			panel.style.height = getHeight(panel.firstElementChild)  + 'px'
		}
		function accordionHide(btn) {
			const btnParent = btn.closest('.l-nav-slide--btn , .l-category__button')
			const panel = btnParent.nextElementSibling
			btnParent.classList.remove('is-opened')
			panel.classList.remove('is-opened')
			if (btnParent.closest('li').dataset.hierarchy > 1) {
				const minusHeight = getHeight(panel.firstElementChild)
				for (let i = 1; i < btnParent.closest('li').dataset.hierarchy; ++i) {
					const parentPanel = btnParent.closest('[data-hierarchy="' + i + '"]').querySelector('.js-accordion-block')
					parentPanel.style.height = Number(parentPanel.style.height.replace('px', '')) - minusHeight + 'px'
				}
			}
			panel.style.height = 0
		}
	}
	accordion_lgSide();

	
 // 子階層が「すべて見る」だけの場合、第一階層をリンクにする	
 //  ------------------------------------------------------
 	$('.l-category li[data-hierarchy=1]').each(function () {
			// 子階層が「すべて見る」だけの場合
			if ($(this).find('.l-category__button-anchor').length === 1) {
				
				// 第2階層を削除
				$(this).find('.l-category__block').remove();

				// 第1階層をリンクに変更
				let el = $(this).find('.l-category__button-accordion');
				let elText = $(this).find('.l-category__button-accordion').text();
				let elLink = $(this).attr('id');
				$(el).replaceWith('<a href="/goods/list?cid=' + elLink + '" class="l-category__button-anchor"><p class="c-textlink">' + elText + '</p></a>');
			}
		})


 	// -------------------------------------------
	// 【デザイン確認用・静的時のみ】静的コーディング確認用 カテゴリー展開
 	// -------------------------------------------

	// カレント：第二階層
	var url2lv = location.href
	if ( url2lv == "https://dev-pdr.hit-mall.jp/ec/shop/sttc/templates/goods/list_thum.html") { 
		let categoryTarget = $('.l-side.l-lg').find("p:contains('歯科関連用品')");
		categoryTarget.parents('.l-category__button').addClass('is-opened');
		categoryTarget.parents('.l-category__button').next('.l-category__block').addClass('is-opened');
		categoryTarget.parents('.l-category__button').next('.l-category__block').css('height','1329px');
	}

	//カレント： 第三階層
	var url3lv = location.href
	if ( url3lv == "https://dev-pdr.hit-mall.jp/ec/shop/sttc/templates/goods/list_list.html") { 
		let categoryTarget = $('.l-side.l-lg').find("p:contains('歯科関連用品')");
		categoryTarget.parents('.l-category__button').addClass('is-opened');
		categoryTarget.parents('.l-category__button').next('.l-category__block').addClass('is-opened');
		categoryTarget.parents('.l-category__button').next('.l-category__block').css('height', '1545px');
		
		let categoryTaget3lv = $('.l-side.l-lg .l-category__level2').find("p:contains('グローブ')");
		categoryTaget3lv.parents('.l-category__button').addClass('is-opened');
		categoryTaget3lv.parents('.l-category__button').next('.l-category__block').addClass('is-opened');
		categoryTaget3lv.parents('.l-category__button').next('.l-category__block').css('height', '216px');
	}
 	// ここまで -------------------------------------------


}


