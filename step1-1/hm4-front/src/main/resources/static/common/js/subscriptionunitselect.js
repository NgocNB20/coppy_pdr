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
		changeGoodsImageFunc();
	});
	
	// 規格1が選択されている場合、規格2検索を呼び出す
	var unitSelect1 = $('select[id^=' + goodsUnitSelect.unitSelect1Id+']').val();
	if(undefined != unitSelect1 && unitSelect1.length){
	    callGoodsUnit2SearchFunc($(this).get(0), true)
	}
});

function callGoodsUnit2SearchFunc(unitSelect1, isUnit1Select) {

    var gcd = $('input[id^=goodsCode]').get(0);
    var ggcd = $("#ggcd").get(0);
    var unitSelect1 = $("#" + goodsUnitSelect.unitSelect1Id).get(0);
    var unitSelect2 = $("#" + goodsUnitSelect.unitSelect2Id).get(0);
    

    // 規格２を取得し、洗い替える
    goodsUnitSearch.goodsUnitSearchUnit2Return(
        ggcd,
        gcd,
        unitSelect1,
        unitSelect2
    );
}