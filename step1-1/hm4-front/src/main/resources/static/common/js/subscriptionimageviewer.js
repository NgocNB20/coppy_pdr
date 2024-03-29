/**
 * 規格画像の切り替え設定
 */
var goodsImageViewer = {
	/* 規格1選択リストのエレメントID */
	unitSelect1Id : "unitSelect1",
    /* 規格2選択リストのエレメントID */
    unitSelect2Id : "unitSelect2",
	/* 静的ファイル補完URL */
	sUrl : "",
	/* 静的ファイル補完URL(g_images) */
	gImagePath : "",
	/* 静的ファイル補完URL(images/goods) */
	goodsImagePath : "",
	/* 画像なしの場合に表示する画像 */
	sNoImage : "/noimage_pds.gif",
	mNoImage : "/noimage_pdm.gif",
	/* admin PCプレビュー機能での利用か？ */
	isPreviewPc : false
}

$(function(){

	// 静的ファイルの作成
	goodsImageViewer.sUrl = $("#staticComplementUrl").val();
	goodsImageViewer.gImagePath = $("#goodsImagePath").val();
	if (goodsImageViewer.isPreviewPc) {
		goodsImageViewer.goodsImagePath = goodsImageViewer.sUrl += "/images/preview/pc"
	} else {
		goodsImageViewer.goodsImagePath = goodsImageViewer.sUrl += "/images/goods";
	}

	changeGoodsImageFunc();

});

//---------------------------------------------
//規格プルダウン変更アクション
//---------------------------------------------
function changeGoodsImageFunc() {

    var target1 = $('select[id=' + goodsImageViewer.unitSelect1Id +']');
    var target2 = $('select[id=' + goodsImageViewer.unitSelect2Id +']');

    var selected;
    
    // 規格1プルダウンがある場合
    if(target1.length){
        // 規格2プルダウンがある場合
	    if(target2.length){
	        selected = target2.val();
	    }else{
            selected = target1.val();
	    }
    }else{
        // ラベル表示の場合
        selected = $('input[id^=goodsCode]').val();
    }

	// 規格プルダウンの選択状態に応じて画像を設定
	if (!selected) {
	    setImageForGroup();
	} else {
	    setImageForUnit(selected);
	}
	
	// 商品情報を設定
	if(target1.length){
	    setGoodsInfo(selected);
	}
}

function setImageForGroup(){

    var goodsGroupImagePath = $("#goodsGroupImagePath").text().split(",");

    setImage(goodsGroupImagePath[0]);
}

function setImageForUnit(selected){

    // 商品番号に該当する規格画像コードを取得
    var value = $("#unitImageCodeForJs").text().split(",");
    var unitImageCode;
    for (var i = 0; i < value.length - 1; i += 2) {
        if (selected == value[i]) {
            unitImageCode = value[i + 1];
            break;
        }
    }

    var goodsImagePath;
    if(unitImageCode != null){
    
        // 商品番号に該当する規格画像を取得
        var value = $("#goodsUnitImagePath").text().split(",");

        for (var i = 0; i < value.length - 1; i += 2) {
            if (unitImageCode == value[i]) {
                goodsImagePath = value[i + 1];
                break;
            }
        }
    }
    
    // 規格画像が取得できた場合規格画像を表示し、取得できなかった場合グループ画像を表示
    if(goodsImagePath){
        setImage(goodsImagePath);
    }else{
        setImageForGroup()
    }
}

function setGoodsInfo(selected){

    // 表示項目を初期化

   $('span[id^=goodsCode]').text('');
   $('input[id^=goodsCode]').val('');
   $('span[id^=goodsPriceNoTax]').text('');

    // ワークテーブルを参照し、商品コードが一致するレコードを探す
    var goodsInfoList = $('#goods_info_table_wk').find('tr');
    goodsInfoList.each(function(index, el){

        var workGoodsCode = $(this).find('[id^=workGoodsCode]').text();
        var workPrice = $(this).find('[id^=workPrice]').text();

        // 選択値と同じ場合、商品情報を設定
        if(selected == workGoodsCode){
           $('span[id^=goodsCode]').text(workGoodsCode);
           $('input[id^=goodsCode]').val(workGoodsCode);
           $('span[id^=goodsPriceNoTax]').text(workPrice);
        }
    });
}

function setImage(goodsImagePath) {

    // 画像がない場合、noimageを設定
    if(!goodsImagePath){
        goodsImagePath = goodsImageViewer.gImagePath + goodsImageViewer.mNoImage;
    }
    
    $("#d_photo").attr('src', goodsImagePath);
    $('input[id=goodsImage]').val(goodsImagePath);
}