/**
 * SNS連携ボタン制御
 */
var snsGoodsSelect = {
	/* 規格1選択リストのエレメントID */
	rootSelect : "div[class='items']",
	/* 規格2選択リストのエレメントID */
	unitSelect2Id : "unitSelect2"
}

$(function(){
	// 画像切替ボタン制御
	$("a[id^='goPrevImage']").each(function() {
		$(this).click(function() {
			var root = $(this).parents(snsGoodsSelect.rootSelect);
			var goodsImageCurIdx = $(root).find("span[id='goodsImageCurIdx']");
			var curIdx = parseInt($(goodsImageCurIdx).html());
			if (curIdx <= 0) {
				return false;
			}
			var imgPathItems = $(root).find("span[id^='goodsImagePath']");
			var prevPath = $(imgPathItems).eq(--curIdx).html();
			$(root).find("img[id='goodsImage']").attr("src", prevPath);
			$(goodsImageCurIdx).html(curIdx);
			return false;
		});
	});

	// 画像切替ボタン制御
	$("a[id^='goNextImage']").each(function() {
		$(this).click(function() {
			var root = $(this).parents(snsGoodsSelect.rootSelect);
			var goodsImageCurIdx = $(root).find("span[id='goodsImageCurIdx']");
			var curIdx = parseInt($(goodsImageCurIdx).html());
			var imgPathItems = $(root).find("span[id^='goodsImagePath']");
			var maxIdx = $(imgPathItems).size();
			if (curIdx + 1 >= maxIdx) {
				return false;
			}
			var nextPath = $(imgPathItems).eq(++curIdx).html();
			$(root).find("img[id='goodsImage']").attr("src", nextPath);
			$(goodsImageCurIdx).html(curIdx);
			return false;
		});
	});

	// Facebook シェアボタン制御
	$("a[id^='goFacebook']").each(function() {
		$(this).click(function() {
			var root = $(this).parents(snsGoodsSelect.rootSelect);
			var name = $(root).find("span[id^='goodsName']").html() + " を購入しました。";
			var link = $(root).find("span[id^='goodsPageUrl']").html();
			var description = $(root).find("span[id^='description']").html();
			var curIdx = parseInt($(root).find("span[id='goodsImageCurIdx']").html());
			if (!curIdx) {
				curIdx = 0;
			}
			var imgUrlItems = $(root).find("span[id^='goodsImageUrl']");
			var picture = $(imgUrlItems).eq(curIdx).html();
			fbDialog(name, link, picture, fbCaption, description);
			return false;
		});
	});

	// Twitter ツイートボタン制御
	$("a[id^='goTwitter']").each(function() {
		$(this).click(function() {
			var root = $(this).parents(snsGoodsSelect.rootSelect);
			var link = $(root).find("span[id^='goodsPageUrl']").html();
			tweetDialog(link, twitterVia);
			return false;
		});
	});
});

/**
 * Facebook ダイアログ
 */
function fbDialog(name, link, picture, caption, description){
	FB.ui({
		method : "feed",
		name : name,
		link : link,
		picture : picture,
		caption : caption,
		description : description
	});
}

/**
 * Twitter ダイアログ
 */
function tweetDialog(link, via){
	if(via) {
		link = "http://twitter.com/intent/tweet?tw_p=tweetbutton&url=" + encodeURIComponent(link) + "&via=" + via;
	} else {
		link = "http://twitter.com/intent/tweet?tw_p=tweetbutton&url=" + encodeURIComponent(link);
	}	
	
	window.open(
		link,
		"Twitter",
		"menubar=no,width=550,height=450,toolbar=no"
	);

}