/*
 * 商品規格値検索クラス
 */
var Goodsunitsearch = function() {
	// 値はgoodsUnitSearchメソッドの中でセット
	this.ggcdObj;
	this.gcdObj;
	this.unitSelect1Obj;
	this.unitSelect2Obj;
	/* 規格画像の設定を行うか？ */
	this.isChangeGoodsImage = true;
}

/*
 * 商品規格値検索クラスのメソッド
 */
Goodsunitsearch.prototype = {

	/*
	 * 規格値2検索処理
	 */
	 goodsUnitSearchUnit2Return : function(ggcdObj, gcdObj, unitSelect1Obj, unitSelect2Obj) {
		goodsUnit2FromUnit1Search(ggcdObj, gcdObj, unitSelect1Obj, unitSelect2Obj);
	}
};

function goodsUnit2FromUnit1Search(ggcdObj, gcdObj, unitSelect1Obj, unitSelect2Obj) {

	// 規格値2が存在しない商品の場合は処理終了
	if (unitSelect2Obj == null || unitSelect2Obj == undefined) {
		if (goodsUnitSearch.isChangeGoodsImage) {
			changeGoodsImageFunc(unitSelect1Obj);
		}
		return;
	}
	
	// 商品グループコードを取得
	var unitGgcd = ggcdObj.value;
	// 選択された規格値1の商品コードを取得
	var unit1Gcd = unitSelect1Obj.value;

	// 引数で渡されたobjectをインスタンス変数にセット
	setAddresInfoObjects(ggcdObj, gcdObj, unitSelect1Obj, unitSelect2Obj);

	// Ajax用の規格値2検索クラスを呼ぶ
	Kumu.Ajax.async = false;
	Kumu.Ajax.executeTeedaAjax(subscriptionUnitSearchAjax_searchGoodsUnit2, [unitGgcd, unit1Gcd], Kumu.Ajax.RESPONSE_TYPE_JSON);
}

function subscriptionUnitSearchAjax_searchGoodsUnit2(response) {
	setValue2(response);
	if (goodsUnitSearch.isChangeGoodsImage) {
		changeGoodsImageFunc(Goodsunitsearch.unitSelect1Obj);
	}
}

/* Ajax用の規格値2検索クラスの戻り値をhtmlにセットします */
function setValue2(response) {

	if (response == null || response == undefined || response == '') {
		// 戻り値がない場合、セレクトボックスを初期化
		Goodsunitsearch.unitSelect2Obj.length = 0;
		Goodsunitsearch.unitSelect2Obj.options[0] = new Option("選択してください", "");
		Goodsunitsearch.unitSelect2Obj.selectedIndex = 0;
		return;
	}
	// セレクトボックスに値をセット
	var index = 0;
	Goodsunitsearch.unitSelect2Obj.length = 0;
	
	if(Goodsunitsearch.unitSelect1Obj.length >= 3) {
		Goodsunitsearch.unitSelect2Obj.options[0] = new Option("選択してください", "");
		for (var i = 0; i < response.length; i++) {
			var optionMap = response[i];
			Goodsunitsearch.unitSelect2Obj.options[i + 1] = new Option(optionMap.label, optionMap.value);
			if (Goodsunitsearch.gcdObj != null && Goodsunitsearch.gcdObj.value == optionMap.value) {
				index = i + 1;
			}
		}
		// ページが商品コードを持っている場合、初期選択する
		Goodsunitsearch.unitSelect2Obj.selectedIndex = index;
	} else {
		for (var i = 0; i < response.length; i++) {
			var optionMap = response[i];
			Goodsunitsearch.unitSelect2Obj.options[i] = new Option(optionMap.label, optionMap.value);
			if (Goodsunitsearch.gcdObj != null && Goodsunitsearch.gcdObj.value == optionMap.value) {
				index = i;
			}
		}

		// ページが商品コードを持っている場合、初期選択する
		Goodsunitsearch.unitSelect2Obj.selectedIndex = index;
	}
}
/* 引数のobjectをインスタンス変数セット */
function setAddresInfoObjects(ggcdObj, gcdObj, unitSelect1Obj, unitSelect2Obj) {

	Goodsunitsearch.ggcdObj = ggcdObj;
	Goodsunitsearch.gcdObj = gcdObj;
	Goodsunitsearch.unitSelect1Obj = unitSelect1Obj;
	Goodsunitsearch.unitSelect2Obj = unitSelect2Obj;
}
