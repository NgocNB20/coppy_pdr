/**
 * 規格プルダウン制御
 */
var goodsUnitSelect = {
	/* 規格1選択リストのエレメントID */
	unitSelect1Id : "unitSelect1",
	/* 規格2選択リストのエレメントID */
	unitSelect2Id : "unitSelect2"
}

var goodsUnitSearch = new Goodsunitsearch();

$(function(){
	// 規格1のチェンジイベント登録
	$("#" + goodsUnitSelect.unitSelect1Id).change(function() {
		callGoodsUnit2SearchFunc($(this).get(0), true);
	});

	// 規格2のチェンジイベント登録
	$("#" + goodsUnitSelect.unitSelect2Id).change(function() {
	
		if (goodsUnitSearch.isChangeGoodsImage) {
			changeGoodsImageFunc();
		}
	
		$("#gcd").val($(this).val());
		//商品在庫詳細表示更新
		changeStockDetail();
	});

	var unitSelect1 = $("#" + goodsUnitSelect.unitSelect1Id).get(0);
	var unitSelect2 = $("#" + goodsUnitSelect.unitSelect2Id).get(0);
	// 規格１が選択済みかつ規格２が存在する場合、初期設定を行う（マイリスト、ブラウザバックなど）
	if (unitSelect2 != null && unitSelect1 != null && unitSelect1.value != null && unitSelect1.value != '') {
		callGoodsUnit2SearchFunc(unitSelect1, false);
	}
	
	// 初回表示に在庫表示を表示する
	var gcd = $("#gcd").get(0);
	if (unitSelect2 == null ) {
		// 規格２が存在しない場合、gcdに規格１の値を設定する
		if(unitSelect1 != null) {
			gcd.value = unitSelect1.value;
		}
		
		changeStockDetail();
	}
	setFavoriteButton();
});

function callGoodsUnit2SearchFunc(unitSelect1, isUnit1Select) {
	var gcd = $("#gcd").get(0);
	var unitSelect1 = $("#" + goodsUnitSelect.unitSelect1Id).get(0);
	var unitSelect2 = $("#" + goodsUnitSelect.unitSelect2Id).get(0);


	if (isUnit1Select) {
		
		// 規格１を再選択した場合、gcdを初期化する
		gcd.value = "";
		
		// 規格１でデフォルト値（選択して下さい）が選択された場合は
		// 「選択した商品の価格と在庫」の項目ごと非表示にする
		if(unitSelect1.value == "") {
			changeStockDetail();
		}
	}

	
	if (unitSelect2 == null ) {
		// 規格２が存在しない場合、gcdに規格１の値を設定する
		gcd.value = unitSelect1.value;
		changeStockDetail();
		setFavoriteButton();
	}

	// 規格２を取得し、洗い替える
	goodsUnitSearch.goodsUnitSearchUnit2Return(
		$("#ggcd").get(0),
		gcd,
		unitSelect1,
		unitSelect2
	);
}

function setFavoriteButton() {
	if ($("#favoriteGoodsCodeList").length) {
		if ($("#favoriteGoodsCodeList").val() != "") {
			if ($("#gcd").val() != "") {
				var array = $("#favoriteGoodsCodeList").val().split(",");
				$("#favoriteBtn").attr("style", "display:inline-flex");
				$("#favoriteRegistered").attr("style", "display:none");
				for(var i = 0; i < array.length; i++) {
					if ($("#gcd").val() == array[i]) {
						$("#favoriteBtn").attr("style", "display:none");
						$("#favoriteRegistered").attr("style", "display:inline-flex");
					}
				}
			}
		}
	}
}