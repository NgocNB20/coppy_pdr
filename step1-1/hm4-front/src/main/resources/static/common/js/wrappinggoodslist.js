/**
 * ラッピング商品一覧画像の切り替え設定
 */
var wrappinggoodslist = {
	/* 処理対象の商品のルートエレメントのセレクター指定 */
	rootGoods : "tr[id='rootGoods']",
	rootWrapping : "td[id='wrappingUnit']",
	/* 商品画像のエレメントID */
	imageId : "wrappingGoodsGroupImage",
	/* 商品画像の初期表示画像のエレメントID */
	defaultImageId : "wrappingGoodsGroupImageSrc",
	/* 規格画像コードのエレメントID */
	unitImageCodeId: "unitImageCodeForJs",
	/* 規格画像のエレメントID */
	unitImageDataId : "goodsUnitImageForJs",
	/* ラッピング商品の追加ボタンのエレメントID */
	targetBtnId : "doWrappingCartAdd",
	/* 規格1選択リストのエレメントID */
	unitSelect1Id : "wrappingUnitSelect1",
	/* 規格2選択リストのエレメントID */
	unitSelect2Id : "wrappingUnitSelect2",
	/* オプション入力のエレメントID */
	optionId : "wrappingOptionValue"
}

var goodsUnitSearch = new Goodsunitsearch();
var wrappingClearFlag = true;

$(function(){

	// ロード時の画像切り替えとチェンジイベント登録
	$("select[id^=" + wrappinggoodslist.unitSelect1Id + "]").each(function(){
		var rootGoods = $(this).parents(wrappinggoodslist.rootGoods);
		changeGoodsImageFuncMain(rootGoods);
		$(this).change(function() {
			callGoodsUnit2SearchFunc(this, true);
		});
	});

	// ラッピング商品の追加ボタンのクリックイベント登録
	$("input[id^=" + wrappinggoodslist.targetBtnId + "]").each(function() {
		$(this).click(function() {
			setWgcdFromWrappingCartAdd(this);
		});
	});

	// ラッピング商品の追加ボタン以外の場合、ラッピング商品の入力情報を全てクリアする
	$("#indexForm").submit(function() {
		if (wrappingClearFlag) {
			$("select[id^=" + wrappinggoodslist.unitSelect1Id + "]").each(function() {
				$(this).val("");
			});
			$("select[id^=" + wrappinggoodslist.unitSelect2Id + "]").each(function() {
				$(this).val("");
			});
			$("input[id^=" + wrappinggoodslist.optionId + "]").each(function() {
				$(this).val("");
			});
		}
	});

	// 規格２の存在するラッピング商品が存在する場合、初期設定を行う
	$("select[id^=" + wrappinggoodslist.unitSelect2Id + "]").each(function() {
		var thisParent = $(this).parents(wrappinggoodslist.rootWrapping).get(0);
		var wrappingUnitSelect1 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect1Id + "]").get(0);
		var wgcd = $(thisParent).find("input[id^='wgcd']").get(0);
		if (wrappingUnitSelect1.value != null && wrappingUnitSelect1.value != '') {
			// 規格１が選択済みの場合、規格２プルダウンを設定する
			this.value = wgcd.value;
			callGoodsUnit2SearchFunc(wrappingUnitSelect1, false);
		}
	});

});

/**
 * 選択した規格の規格画像に切り替える
 *
 * @param unitSelectElement 処理対象の規格プルダウン
 */
function changeGoodsImageFunc(unitSelectElement) {
	var rootGoods = $(unitSelectElement).parents(wrappinggoodslist.rootGoods);
	changeGoodsImageFuncMain(rootGoods);
}

/**
 * 選択した規格の規格画像に切り替える
 *
 * @param rootGoods 処理対象の商品のルートエレメント
 */
function changeGoodsImageFuncMain(rootGoods) {
	// 規格プルダウンを取得
	var unitSelect= $(rootGoods).find("select[id^=" + wrappinggoodslist.unitSelect1Id + "]");
	// 規格の選択値を取得
	var goodsCode = $(unitSelect).children(':selected').val();
	// 設定先の画像を取得
	var targetImg = $(rootGoods).find("img[id^=" + wrappinggoodslist.imageId + "]");
	// 規格の選択値から規格画像コードを取得
	var unitImageCode = getUnitImageCode(rootGoods, goodsCode);
	// 規格画像コードから規格画像を取得し、画像を切り替える
	var unitImageSrc = getUnitImageSrc(rootGoods, unitImageCode);
	$(targetImg).attr("src", unitImageSrc);
}

/**
 * 商品番号をキーに規格画像コードを取得
 *
 * @param rootGoods 処理対象の商品のルートエレメント
 * @param goodsCode 商品番号
 * @return 規格画像コード
 */
function getUnitImageCode(rootGoods, goodsCode) {
	var unitImageCode;
	if (!goodsCode) {
		return unitImageCode;
	}

	// 規格画像コードの一覧を取得
	var target = $(rootGoods).find("span[id^=" + wrappinggoodslist.unitImageCodeId + "]");
	var value = $(target).html();
	value = value.split(",");

	// 商品番号に該当する規格画像コードを取得
	for (var i = 0; i < value.length - 1; i += 2) {
		if (goodsCode == value[i]) {
			unitImageCode = value[i + 1];
			break;
		}
	}

	return unitImageCode;
}

/**
 * 規格画像コードをキーに規格画像のファイルパスを取得
 *
 * @param rootGoods 処理対象の商品のルートエレメント
 * @param imageCode 規格画像コード
 * @return 規格画像のファイルパス
 */
function getUnitImageSrc(rootGoods, imageCode) {
	var unitImageSrc = $(rootGoods).find("span[id^=" + wrappinggoodslist.defaultImageId + "]").html();
	if (!imageCode) {
		return unitImageSrc;
	}

	// 規格画像の一覧を取得
	var target = $(rootGoods).find("span[id^=" + wrappinggoodslist.unitImageDataId + "]");
	var unitImageSrc;

	// 規格画像コードに該当する規格画像のファイルパスを取得
	$(target).each(function(){
		var value = $(this).html();
		value = value.split(",");
		if (imageCode == value[0]) {
			unitImageSrc = value[3];
			return false;
		}
	});

	return unitImageSrc;
}

function callGoodsUnit2SearchFunc(wrappingUnitSelect1, isUnit1Select) {
	var thisParent = $(wrappingUnitSelect1).parents(wrappinggoodslist.rootWrapping).get(0);
	var wgcd = $(thisParent).find("input[id^='wgcd']").get(0);
	var wrappingUnitSelect2 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect2Id + "]").get(0);

	if (isUnit1Select) {
		// 規格１を再選択した場合、gcdを初期化する
		wgcd.value = "";
	}
	if (wrappingUnitSelect2 == null) {
		// 規格２が存在しない場合、wgcdに規格１の値を設定する
		wgcd.value = wrappingUnitSelect1.value;
	}

	// 規格２を取得し、洗い替える
	goodsUnitSearch.goodsUnitSearchUnit2Return(
		$(thisParent).find("input[id^='wggcd']").get(0),
		wgcd,
		wrappingUnitSelect1,
		wrappingUnitSelect2,
		''
	);
}

/**
 * ラッピング商品の追加ボタン押下時の処理
 * 対象のラッピング商品以外の項目はクリアする
 */
function setWgcdFromWrappingCartAdd(cartAddBtn) {
	wrappingClearFlag = false;
	var thisParent = $(cartAddBtn).parents(wrappinggoodslist.rootGoods).get(0);
	var wgcd = $(thisParent).find("input[id^='wgcd']").get(0);
	var wrappingUnitSelect1 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect1Id + "]").get(0);
	var wrappingUnitSelect2 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect2Id + "]").get(0);
	var wrappingUnitSelect1Val = "";
	var wrappingUnitSelect2Val = "";
	if (wrappingUnitSelect1 != null) {
		wrappingUnitSelect1Val = wrappingUnitSelect1.value;
	}
	if (wrappingUnitSelect2 != null) {
		wrappingUnitSelect2Val = wrappingUnitSelect2.value;
	}
	var wrappingOption = $(thisParent).find("input[id^=" + wrappinggoodslist.optionId + "]").get(0);
	var wrappingOptionVal = "";
	if (wrappingOption != null) {
		wrappingOptionVal = wrappingOption.value;
	}

	// 選択規格のみを残すため、選択規格を初期化する
	$("input[id^='wgcd']").each(function(i) {
		var thisParent = $(this).parents(wrappinggoodslist.rootWrapping).get(0);
		var wrappingUnitSelect1 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect1Id + "]").get(0);
		var wrappingUnitSelect2 = $(thisParent).find("select[id^=" + wrappinggoodslist.unitSelect2Id + "]").get(0);
		if (wrappingUnitSelect1 != null) {
			wrappingUnitSelect1.value = "";
		}
		if (wrappingUnitSelect2 != null) {
			wrappingUnitSelect2.value = "";
		}
		var wrappingOption = $(thisParent).parents(wrappinggoodslist.rootGoods).find("input[id^=" + wrappinggoodslist.optionId + "]").get(0);
		if (wrappingOption != null) {
			wrappingOption.value = "";
		}
	});

	// 選択規格を設定する
	if (wrappingUnitSelect1 != null) {
		wrappingUnitSelect1.value = wrappingUnitSelect1Val;
		if (wrappingUnitSelect2 == null) {
			// 規格２が存在しない場合、wgcdに規格１の値を設定する
			wgcd.value = wrappingUnitSelect1Val;
		} else {
			// 規格２が存在する場合、wgcdに規格２の値を設定する
			wgcd.value = wrappingUnitSelect2Val;
		}
	}
	if (wrappingUnitSelect2 != null) {
		wrappingUnitSelect2.value = wrappingUnitSelect2Val;
	}
	if (wrappingOption != null) {
		wrappingOption.value = wrappingOptionVal
	}
}