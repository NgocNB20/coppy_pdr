/*
 * スマホサイト商品画像表示スクリプト
 * jQueryプラグイン「flickGal」を使用して表示する為のスクリプトです。
 * https://github.com/piglovesyou/flickGal
 *
 * ◆システムから渡される画像データ
 * ・規格画像 id = goodsUnitImageForJs-n
 * ・グループ画像 id = goodsGroupImageForJs-n
 * 上記のvalueに、以下の値がカンマ区切りで設定される
 *  [0]=規格画像コード　※グループ画像の場合は未設定
 *  [1]=規格１（規格画像名）　※グループ画像の場合は未設定
 *  [2]=規格画像グループNo　※グループ画像の場合は未設定
 *  [3]=小画像のパス
 *  [4]=中画像のパス
 *  [5]=大画像のパス
 *
 */

/******
 * flickGalに対応する商品画像タグをhtmlに出力します。
 *
 * return true 表示する商品画像がある場合。 false 「noimage」を表示する場合。
 *
 ******/
function doLoadGoodsImg(){

	//-------------------------------------------------------------------------//
	// 設定項目

	//画像情報を取得するid グループ画像
	inGroupImageId = "#goodsGroupImageForJs-"

	//画像情報を取得するid 規格画像
	inUnitImageId = "#goodsUnitImageForJs-"

	//画像タグを挿入するHTMLタグのidを指定する。
	outAppendImageId = "#goodsimagePort"

	//ナビタグを挿入するHTMLタグのidを指定する。
	outAppendNavId = "#goodsimageNavPort"

	// フリック操作用のidを指定する。
	navid = "item";

	gImagePath = $("#goodsImagePath").val();

	// noImageのファイルを指定する。
	noimagePath = gImagePath + "/noimage_pdm.gif";

	//-------------------------------------------------------------------------//

	// ループのインデックス ※規格画像、グループ画像で統一されている必要がある。
	imgIndex = 0;
	// 画像出力カウント
	imgCnt = 0;

	// グループ画像タグ出力
	doAppendImageTag(inGroupImageId, outAppendImageId, navid);

	// 規格画像タグ出力
	doAppendImageTag(inUnitImageId, outAppendImageId, navid);

	if(imgCnt > 0){
		// ナビタグ出力
		doAppendNavTag(imgCnt, navid, outAppendNavId);
	}else{
		// 画像無し
		$(outAppendImageId).append("<div class='item' style='margin:auto;'><img src='" + noimagePath + "'></div>");
		return false;
	}
	return true;
}

/******
 * 商品画像タグを生成し、htmlに追加する。
 *
 * inId = 商品データ取得元のid
 * outAppendId = 画像タグの出力先のid
 * navid = ナビタグで使用するid
 *
 ******/
function doAppendImageTag(inId, outAppendId, navid){

	// 商品画像タグ生成
	while (true) {
		if (goodsData = $(inId + imgIndex).html()) {
			imageSrc = "";
			// 表示対象の画像サイズのパスを取得
			var ary = goodsData.split(",");
			for (var i = 0; i < ary.length ; i++) {
				if (ary[i].indexOf("_pdm.") >= 0) {
					imageSrc = ary[i];
					break;
				}
			}

			// 表示対象の画像名を取得
			imageName = goodsData.split(",")[1];

			// 空チェック
			if(imageSrc != ""){
				// 商品画像タグ
				$(outAppendId).append("<div id='" + navid + imgIndex + "' class='item'><img alt='"+ imageName +"' src='" + imageSrc + "'><span class=''>"+ imageName +"</span></div>");
				imgCnt++;
			}

			imgIndex++;
		} else {
			break;
		}
	}
	return imgCnt;
}

/******
 * ナビタグを生成し、htmlに追加する。
 *
 * count = 画像数
 * navid = ナビタグで使用するid
 * outAppendNavId = ナビタグの出力先のid
 *
 ******/
function doAppendNavTag(count, navid, outAppendNavId){

	naviindex = 0
	$(outAppendNavId).append("<li class='prev'>&lt;&nbsp;</li>");
	while (naviindex < count) {
		$(outAppendNavId).append("<li class='circle'><a href='#" + navid + naviindex + "'>&nbsp;●&nbsp;</a></li>");
		naviindex++;
	}
	$(outAppendNavId).append("<li class='next'>&nbsp;&gt;</li>");
}

/*******
 * flickGalの呼び出し
 *
 * オプション設定 参照
 * https://github.com/piglovesyou/flickGal#options
 *
 ******/
$(function(){

	if(doLoadGoodsImg()){
		// 商品画像が存在する場合。

		// 商品画像をフリック表示する。
		$("#slideShow").flickGal({
			infinitCarousel : true,//最後の要素を表示中に「次へ」を押した時、最初に戻ります。
			lockScroll      : false
		});
		// arrowsを表示
		$("#slideShow .arrows").css("display", "block");
	}
});

/**
 * 商品在庫状況の表示制御
 */
function changeStockDetail() {

	var gcd = $("#gcd").val();
	
	$("#d_stock_info").slideUp("fast");
	
	var dataList = [];
	var data = [];
	//var stockMap = new Map(); // #347
	var tr = $("#d_stock_info_table_wk tr");
	for( var i=0,l=tr.length;i<l;i++ ){
		var cells = tr.eq(i).children();
		
		// 商品番号が一致するものを抜き出す
		if(cells.eq(0).text() == gcd) {
			data = [];
			for( var j = 0; j < cells.length; j++ ){
				data.push(cells.eq(j).text());
			}
			dataList.push(data);
		}
	}
	
	if(dataList.length == 0) {
		$("#selectItemStockInfo").slideUp("fast");
		return;
	}
	$("#selectItemStockInfo").slideDown();
	
	// 選択した商品の在庫状況に挿入するHTMLを生成
	var newHtml = new String();
	
	// ヘッダの作成
	newHtml += "<tr>";
	newHtml += "<th>商品番号</th>";
	newHtml += "<th>価格（税抜）</th>";
	if(dataList[0][2] != ""){
		if(dataList.length != 1 || dataList[0][3] != "") {
			newHtml += "<th>数量</th>";
		}
	}
	if(dataList[0][3] != "") {
		if(dataList[0][5] != "" && dataList[0][5] != null){
			newHtml += "<th>特別セール価格（税抜）</th>";
		}else{
		newHtml += "<th>セール価格（税抜）</th>";	
		}
	}
	newHtml += "<th>在庫</th>";
	newHtml += "</tr>";
	
	// ボディの作成
	for(var i = 0; i < dataList.length; i++) {
		
		newHtml += "<tr>";
		
		if(i == 0) {
			
			// 商品番号（無条件で全結合）
			newHtml += "<td rowspan=" + dataList.length + ">" + dataList[i][0]+ "</td>";
			
			// 価格（税抜）
			if(dataList[i][3] != "") {
				newHtml += "<td rowspan=" + dataList.length + ">" + dataList[i][1]+ "</td>";
			} else {
				newHtml += "<td>" + dataList[i][1]+ "</td>";
			}
		} else {
			if(dataList[i][3] == "") {
				newHtml += "<td>" + dataList[i][1]+ "</td>";
			}
		}
		
		
		// 数量
		if(dataList[0][2] != ""){
			if(dataList.length != 1 || dataList[i][3] != "") {
				newHtml += "<td>" + dataList[i][2]+ "</td>";
			}
		}
		
		// セール価格（税抜）
		if(dataList[i][3] != "") {
			newHtml += "<td><span>" + dataList[i][3]+ "</span></td>";	
		}
		
		// 在庫
		if(i == 0) {
			newHtml += "<td rowspan=" + dataList.length + ">" + dataList[i][4]+ "</td>";
		}
		newHtml += "</tr>";
	}
	
	$("#d_stock_info table").html(newHtml);
	
	$("#d_stock_info").slideDown();
}