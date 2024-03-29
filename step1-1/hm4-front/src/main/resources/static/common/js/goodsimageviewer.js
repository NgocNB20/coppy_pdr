/**
 * 規格画像の切り替え設定
 */
var goodsImageViewer = {
	/* 規格1選択リストのエレメントID */
	unitSelect1Id : "unitSelect1",
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
	isPreviewPc : false,
	path_resize : '/resize/p2x2'
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

	// グループ画像の設定
	var oNode = $('<div class="imgBox"></div>');
	var goodsname = $('#goodsGroupNamePC').text();
	$("span[id^='goodsGroupImageForJs']").each(function(i){
		// 画像データを取得
		var goodsData = $(this).html();
		if (!goodsData) {
			// データがなければ読み飛ばす
			return true;
		}
		
		// 画像データを取得
		var ary = createImageData(goodsData);
			// 拡大画像がある時
		node = $('<div class="thmbContainer"><div style="background-image:url(' + goodsImageViewer.path_resize +  ary[0] + ')" pc2image="'+ goodsImageViewer.path_resize + ary[0]+'">'
					+ '<a href="' + goodsImageViewer.path_resize + ary[0] + '" title="' + goodsname + '"><img src="' + goodsImageViewer.goodsImagePath + '/zoom_image_s.gif" width="82" height="82" style="display:none;" class="alphafilter" /></a></div></div>');

		//初期画像の追加とクラスの追加
		if (i == 0) {
			setMainImage(node, ary);
		}

		// グループ画像に追加
		oNode.append(node);
	});

	// 規格画像の設定
	var cNode = $('<div class="imgBox"></div>');
	var unitImageGroupNo;
	$("span[id^='goodsUnitImageForJs']").each(function(i){
		// 画像データを取得
		var goodsData = $(this).html();
		if (!goodsData) {
			// データがなければ読み飛ばす
			return true;
		}

		// 画像データを取得
		var ary = createImageData(goodsData);
		var newLine = "";


		// 画像タイトルを作成
		var imageTitle = goodsname;
		if (ary[2] != "") {
			imageTitle +=  " / " + ary[2];
		}


			// 拡大画像がある時
		node = $(newLine + '<div class="thmbContainer heightLine"><div style="background-image:url(' + goodsImageViewer.path_resize + ary[0] + ')" pc2image="'+ goodsImageViewer.path_resize + ary[0]+'" " unitimagecode="' + ary[1] + '" " unitimagename="' + ary[2] + '">'
					+ '<a href="'+ goodsImageViewer.path_resize + ary[0] + '" title="' + imageTitle + '"><img src="' + goodsImageViewer.goodsImagePath + '/zoom_image_s.gif" width="82" height="82" style="display:none;" class="alphafilter" /></a></div>' + '<span>' + ary[2] + '</span>' + '</div>');


		// 規格画像に追加
		cNode.append(node);
	});

	// 商品画像のデータが一枚も存在していない場合
	if (!oNode.has("div").length > 0 && !cNode.has("div").length > 0) {
		$("#d_photo").css({
			"background-image" : "url(" + goodsImageViewer.gImagePath + goodsImageViewer.mNoImage + ")",
			"background-position":"center",
			"background-repeat": "no-repeat"
		});
	}

	// グループ画像のサムネイル追加
	if (oNode.children().length != 0) {
		$("#other-images").append(oNode);
	}

	// 規格画像のサムネイル追加
	if (cNode.children().length != 0) {
		$("#other-images").append(cNode);
	}

	//---------------------------------------------
	// サムネイルのロールオーバーイベントの設定
	//---------------------------------------------
	$("#other-images").find(".imgBox>div").hover(function(){
		var s = $(this).css("background-image").split('"')[1];
		setSelectGoodsImage($(this));
	})

	//---------------------------------------------
	//lightBoxの初期化
	//---------------------------------------------
	$("#other-images").find("a").lightBox({
		imageBtnPrev : goodsImageViewer.goodsImagePath + "/lightbox-btn-prev.gif",
		imageBtnNext : goodsImageViewer.goodsImagePath + "/lightbox-btn-next.gif",
		imageBtnClose : goodsImageViewer.goodsImagePath + "/lightbox-btn-close.gif",
		imageBlank : goodsImageViewer.goodsImagePath + "/lightbox-blank.gif",
		imageLoading : goodsImageViewer.goodsImagePath + "/lightbox-ico-loading.gif",
		containerBorderSize : 37,
		overlayBgColor : '#000',
		overlayOpacity : 0.6,
		txtImage : '',
		txtOf : '/'
	});

	//---------------------------------------------
	// m画像のクリック処理
	//---------------------------------------------
	$("#d_photo").click(function(){
		if ($("div.select").has("a").length > 0) $("div.select a").click();
	});

	changeGoodsImageFunc();

});

/**
 * 画像データを配列に変換
 *
 * 商品グループ画像の場合
 *   {,,,小画像,中画像,大画像}
 * 規格画像の場合
 *   {規格画像コード,規格値,規格画像グループNO,小画像,中画像,大画像}
 *
 * @param goodsData カンマ区切りの画像データ
 * @return 配列の画像データ
 */
function createImageData(goodsData) {
	var ary = goodsData.split(",");

	return ary;
}

/**
 * @return 小画像か？
 */
function isSmallImage(fileName) {
	return fileName.indexOf("_pds.") >= 0
}

/**
 * @return 中画像か？
 */
function isMediumImage(fileName) {
	return fileName.indexOf("_pdm.") >= 0
}

/**
 * @return 大画像か？
 */
function isLargeImage(fileName) {
	return fileName.indexOf("_pdl.") >= 0
}

/**
 * 対象の画像データを選択状態にする
 */
function setMainImage(node, ary) {
	$("#d_photo").css({
		"background-image": "url(" + goodsImageViewer.path_resize + ary[0] + ")",
		"background-position":"center",
		"background-repeat": "no-repeat"
	});
	node.children("div").addClass("select");
	if (ary[0]) {
		node.find("img").show();
		$("#d_photo").find("img").show();
	}
	if (ary[2] == "") {
		ary[2] = "&nbsp;"
	}
	$("#unitImageName").html(ary[2]);
}

//---------------------------------------------
//規格プルダウン変更アクション
//---------------------------------------------
function changeGoodsImageFunc() {
	// 規格プルダウンを取得
	var targetId = goodsImageViewer.unitSelect1Id

	var selected = $('#'+ targetId + ' option:selected').val();

	// 規格プルダウンが未選択の場合は処理終了
	if (!selected) {
		return false;
	}

	// 商品番号に該当する規格画像コードを取得
	var value = $("#unitImageCodeForJs").html().split(",");
	var unitImageCode;
	for (var i = 0; i < value.length; i ++) {
		if (selected == value[i] ) {
			unitImageCode = value[i];
			break;
		}
	}

	var existFlag = false;
	$("#other-images").find(".imgBox>div").each(function() {
		if (unitImageCode == $(this).children("div").attr("unitimagecode")) {
			setSelectGoodsImage($(this));
			existFlag = true;
			return false;
		}
	});

	//選択した規格の画像がない場合はNoImage画像を追加する
	if (!existFlag) {
		$("#d_photo").css({
			"background-image" : "url(" + goodsImageViewer.path_resize + goodsImageViewer.gImagePath + goodsImageViewer.mNoImage + ")",
			"background-position":"center",
			"background-repeat": "no-repeat"
		});
		$("#d_photo img").hide();
		$("#other-images div.select img").hide();
		$("#other-images div.select").removeClass("select");
		// NoImage画像の場合、規格名は表示しないため、規格名を初期化して処理終了
		$("#unitImageName").html("&nbsp;");
		return false;
	}
}

function setSelectGoodsImage(selectGoodsImage){
	$("#d_photo").css({
		"background-image": "url(" + selectGoodsImage.children("div").attr("pc2image") + ")",
		"background-position":"center",
		"background-repeat": "no-repeat"
	});

	$("#other-images div.select img").hide();
	$("#other-images div.select").removeClass("select");

	var imgInfo = selectGoodsImage.children("div");
	imgInfo.addClass("select");
	if (imgInfo.attr("unitimagename")) {
		$("#unitImageName").html(imgInfo.attr("unitimagename"));
	} else {
		$("#unitImageName").html("&nbsp;");
	}

	//拡大可否
	if (selectGoodsImage.has("a").length > 0 ) {
		$("#d_photo img").show();
		selectGoodsImage.find("img").show();
	} else {
		$("#d_photo img").hide();
	}
}

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