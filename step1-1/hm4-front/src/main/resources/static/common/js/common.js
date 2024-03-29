/*-----------------------------------------------------------
index:
	Rollover Image
	Header Search Parts
	Horizon Scroll View
	Window Onload
-----------------------------------------------------------*/

/* Rollover Image
-----------------------------------------------------------*/
function initRollovers() {
	if (!document.getElementById) return

	var aPreLoad = new Array();
	var sTempSrc;
	var aImages = document.getElementsByTagName('img');
	var iImages = document.getElementsByTagName('input');

	for (var i = 0; i < aImages.length; i++) {
		if (aImages[i].className == 'over') {
			var src = aImages[i].getAttribute('src');
			var ftype = src.substring(src.lastIndexOf('.'), src.length);
			var hsrc = src.replace(ftype, '_o'+ftype);

			aImages[i].setAttribute('hsrc', hsrc);

			aPreLoad[i] = new Image();
			aPreLoad[i].src = hsrc;

			aImages[i].onmouseover = function() {
				sTempSrc = this.getAttribute('src');
				this.setAttribute('src', this.getAttribute('hsrc'));
			}

			aImages[i].onmouseout = function() {
				if (!sTempSrc) sTempSrc = this.getAttribute('src').replace('_o'+ftype, ftype);
				this.setAttribute('src', sTempSrc);
			}
		}
	}

	for (var i = 0; i < iImages.length; i++) {
		if (iImages[i].className == 'over') {
			var src = iImages[i].getAttribute('src');
			var ftype = src.substring(src.lastIndexOf('.'), src.length);
			var hsrc = src.replace(ftype, '_o'+ftype);

			iImages[i].setAttribute('hsrc', hsrc);

			aPreLoad[i] = new Image();
			aPreLoad[i].src = hsrc;

			iImages[i].onmouseover = function() {
				sTempSrc = this.getAttribute('src');
				this.setAttribute('src', this.getAttribute('hsrc'));
			}

			iImages[i].onmouseout = function() {
				if (!sTempSrc) sTempSrc = this.getAttribute('src').replace('_o'+ftype, ftype);
				this.setAttribute('src', sTempSrc);
			}
		}
	}

}

/* Horizon Scroll View
-----------------------------------------------------------*/
function sv_initHScrollView( range ) {

	var sv_tx = [];
	var sv_max = [];
	var sv_range = [];

	var sv_view = [];
	var sv_element = [];

	var n = 0;
	var n2 = 0;

	while( 1 ) {

		//window.alert( n );

		if( $("#sv_scrollView"+sv_zero( n )).html() == null ) {
			if ( n == 0 ) {
				n = 1;
				if (range[n2] == 0) {
					n++;
					n2++;
					continue;
				}
				if( $("#sv_scrollView"+sv_zero( n )).html() == null ) break;
			} else {
				if (range[n2] == 0) {
					n++;
					n2++;
					continue;
				}
				break;
			}
		}

		sv_view[n] = $("#sv_scrollView"+sv_zero( n ));
		sv_element[n] = $("#sv_scrollElement"+sv_zero( n ));

		sv_view[n].css("position","relative");
		sv_element[n].css("position", "relative");

		sv_tx[n] = 0;
		sv_max[n] = sv_element[n].width() - sv_view[n].width();
		sv_range[n] = range[n2];

		if ( sv_max[n] > 0 ) {
			$("#sv_nextButton"+sv_zero( n )).click( function(e) {
				var i = e.target.id.split("sv_nextButton")[1];
				if ( sv_tx[i] > -sv_max[i] ) {
					sv_tx[i] -= sv_range[i];
					sv_element[i].stop();
					sv_element[i].animate({ left:sv_tx[i] }, 600, "easeOutQuart" );
				}
			});

			$("#sv_prevButton"+sv_zero( n )).click( function(e) {
				var i = e.target.id.split("sv_prevButton")[1];
				if ( sv_tx[i] < 0 ) {
					sv_tx[i] += sv_range[i];
					sv_element[i].stop();
					sv_element[i].animate({ left:sv_tx[i] }, 600, "easeOutQuart" );
				}
			});
		}
		n++;
		n2++;
	}
}

function sv_zero( n ) {
	return ( n == 0 ) ? "" : n;
}

function hcHeight(){
		var hc_a = $(".horizonScroll").innerHeight();
		$(".horizonScroll .back").height(hc_a);
		$(".horizonScroll .next").height(hc_a);
		if (hc_a < 50) {
			$(".horizonScroll .back").css("display","none");
			$(".horizonScroll .next").css("display","none");
		}
};

/*
 * カード情報削除確認ダイアログ
 */
function cardDeleteConfirm(){
    return confirm("削除してもよろしいですか？");
}

function initElements(executeInitRoll = true) {

	if (executeInitRoll) {
		initRollovers();
	}

	// 検索フォーム
	//$("#d_lookup input#search").focus(function() {
	//	if($(this).val() == $(this).attr('defaultValue'))
	//	$(this).val('');
	//}).blur(function() {
	//	if(jQuery.trim($(this).val()) == "") {
	//		$(this).val($(this).attr('defaultValue'));
	//	}
	//});

	// トップページの横スクロールを設定
	var top_page = [];
	
	// トップページ[購入した商品]
	if ($("#d_grand_top #d_buyItemList").length > 0) {
		top_page.push(166);
	} else {
		top_page.push(0);
	}
	
	// トップページ[新着アイテム]
	if ($("#d_grand_top #d_newitem").length > 0) {
		top_page.push(195);
	} else {
		top_page.push(0);
	}

	// トップページ[おすすめ商品]
	if ($("#d_grand_top #d_recommend").length > 0) {
		top_page.push(195);
	} else {
		top_page.push(0);
	}

	// トップページの場合のみ設定
	if ($("#d_grand_top").length > 0) {
		sv_initHScrollView(top_page);
		hcHeight();
	}

	// 商品詳細あしあと
	if ($("#d_goods_detail #d_foot_mark").length > 0) {
		sv_initHScrollView([170]);
		hcHeight();
	}

	// カートあしあと
	if ($("#d_cart #d_foot_mark").length > 0) {
		sv_initHScrollView([170]);
		hcHeight();
	}

	// 定期便検索の横スクロールを設定
    var subscription_search_page = [];

    // 定期便検索[購入した商品]
    if ($("#d_member #d_buyItemList").length > 0) {
        subscription_search_page.push(166);
    } else {
        subscription_search_page.push(0);
    }

    // 定期便検索[PDRからのおすすめ]
    if ($("#d_member #d_RecomendedItemList").length > 0) {
        subscription_search_page.push(166);
    } else {
        subscription_search_page.push(0);
    }

    // 定期便検索の場合のみ設定
    if ($("#d_member #d_buyItemList").length > 0) {
        sv_initHScrollView(subscription_search_page);
        hcHeight();
    }

        // 定期便検索の場合のみ設定
    if ($("#d_member #d_RecomendedItemList").length > 0) {
        sv_initHScrollView(subscription_search_page);
        hcHeight();
    }

	/* !pageTop
	---------------------------------------------------------- */
	$(".pagetop").hide();
	$(window).scroll(function () {
		if ($(this).scrollTop() > 100) {
			$('.pagetop').slideDown("fast");
		} else {
			$('.pagetop').slideUp("fast");
		}
	});

	/* mypagemenu */
	if ($("#d_mn").length > 0) {
		$("#d_mn").find("."+$(".ptTitle").attr("id")+"").addClass("active");
	}
}

/* Window Onload
-----------------------------------------------------------*/
$(function(){
	initElements();

});
jQuery(function($){
/* 二重サブミットを防止する処理
-----------------------------------------------------------*/
	$('form').on('submit', function(e){
		var $form = $(this);
		if ($form.data('submitted') === true) {
			e.preventDefault();
		} else {
			$form.data('submitted', true);
		}
		setTimeout(function () {
			$form.data('submitted', false);
		}, 1500);
	});
});

