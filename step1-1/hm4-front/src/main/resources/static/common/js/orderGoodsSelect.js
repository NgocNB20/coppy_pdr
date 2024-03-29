/*
 * 注文　商品選択画面　JS
 * goodsselect.html
 */
var ERR_MSG = {
	GOODS_NONE : "商品を選択してください。",
	GOODS_COUNT_FAIL : "数値(1～9999)を入力してください。",
	FAIL : "不正な値が入力されています。お手数ですが、画面を閉じて再度操作をお願いいたします。"
};

var goodsSelectDialogOpenFlag = false;
/*
 * 入力チェックを行う。
 */
function convertNumber (value) {
	return value.replace(/[０-９]/g,function(s){ return String.fromCharCode(s.charCodeAt(0)-0xFEE0); });
}

/*
 * 入力チェックを行う。
 */
function checkInput () {

	// 入力チェック
	var message = "";
	var errorFlg = false;
	var msgArray = new Array();
	
	// エラーメッセージ　クリア
	$("#goodsSelectErrorMsg").html(message);
	$("#goodsSelectErrorMsg").removeClass("inputError");
	$("#goodsSelectDialog [id^='select']").each(function(){
		var no = $(this).attr("id").replace(/select-/g, "");
		// 半角化してあげる
		$("#orderGoodsCount" + "-" + no).val(convertNumber($("#orderGoodsCount" + "-" + no).val()));
		$("#orderGoodsCount" + "-" + no).removeClass("errorPart");
	});	
	
	// チェックされているものを対象とする。
	var selectFlg = false;
	$("#goodsSelectDialog [id^='select']:checked").each(function(){

		selectFlg = true;

		var no = $(this).attr("id").replace(/select-/g, "");
		var seq = $("#cartSeq" + "-" + no).val();
		var cnt = $("#orderGoodsCount" + "-" + no).val();

		// cnt
		if (!cnt || !cnt.match(/^[1-9][0-9]{0,3}$/)) {
			msgArray.push(ERR_MSG.GOODS_COUNT_FAIL);
			$("#orderGoodsCount" + "-" + no).addClass("errorPart");
			errorFlg = true ;
		}

		// seq
		if (!seq || !seq.match(/^[0-9]{1,8}$/)) {
			msgArray.push(ERR_MSG.FAIL);
			errorFlg = true ;
		}
	});	

	// 1件も商品がチェックされていない
	if (!selectFlg) {
		msgArray.push(ERR_MSG.GOODS_NONE);
		errorFlg = true ;
	}

	// rno
	var rno = $("rno-goodsSelect").val()
	if (rno && !rno.match(/[0-9]{1,8}/)) {
		msgArray.push(ERR_MSG.FAIL);
		errorFlg = true ;
	}

	// エラーチェック
	if (errorFlg) {
		for (var i = 0; i < msgArray.length; i ++) {
			if (i != 0) {
				message = message + "<br />";
			}
			message = message + msgArray[i];	
		}
		$("#goodsSelectErrorMsg").html("<li>" + message + "</li>");
		$("#goodsSelectErrorMsg").addClass("inputError");
	}

	return errorFlg;
}


/*
 * 商品情報を作成する。
 */
function createGoodsInfo () {

	// cartSeq:数量/cartSeq:数量/cartSeq:数量 の形式で受け渡し
	var goodsInfo = "";
	$("[id^='select']:checked").each(function(){
		var no = $(this).attr("id").replace(/select-/g, "");
		var seq = $("#cartSeq" + "-" + no).val();
		var cnt = $("#orderGoodsCount" + "-" + no).val();

		if (goodsInfo != "") {
			goodsInfo = goodsInfo + "/" ;
		}
		goodsInfo = goodsInfo + seq + ":" + cnt;
	});
	return goodsInfo;
}

/*
 * ボタン画像の切替
 */
function changeImage (checkBox) {
	if (checkBox.is(':checked')) {

		// 選択した商品を選択状態とする
		checkBox.parent().find("#area_select").hide();
		checkBox.parent().find("#area_cancel").show();
		checkBox.parent().parent().parent().addClass("select");

		// 子商品のスタイルを親商品の選択スタイルと合わせる
		var no = checkBox.attr("id").replace(/select-/g, "");
		var bundleGoodsTypeParent = $("#bundleGoodsTypeParent" + "-" + no).val();
		if (bundleGoodsTypeParent == 'true') {
			var sameBundleSaleGoodsCode = $("#sameBundleSaleGoodsCode" + "-" + no).val();
			$("[id^='sameBundleSaleGoodsCode']").each(function(){
				if (sameBundleSaleGoodsCode == $(this).val()) {
					$(this).parent().parent().addClass("select");
				}
			});
		}
	} else {
		// 未選択した商品を選択状態とする
		checkBox.parent().find("#area_select").show();
		checkBox.parent().find("#area_cancel").hide();
		checkBox.parent().parent().parent().removeClass("select");

		// 子商品のスタイルを親商品の選択スタイルと合わせる
		var no = checkBox.attr("id").replace(/select-/g, "");
		var bundleGoodsTypeParent = $("#bundleGoodsTypeParent" + "-" + no).val();
		if (bundleGoodsTypeParent == 'true') {
			var sameBundleSaleGoodsCode = $("#sameBundleSaleGoodsCode" + "-" + no).val();
			$("[id^='sameBundleSaleGoodsCode']").each(function(){
				if (sameBundleSaleGoodsCode == $(this).val()) {
					$(this).parent().parent().removeClass("select");
				}
			});
		}
	}
}

jQuery(function($){

	$("#goodsSelectDialog").dialog({
		title		: '商品を選択する',
		bgiframe	: true,
		autoOpen	: false,
		height		: 'auto',
		width		: 'auto',
		modal		: true,
		draggable	: true,
		closeText	: '',
		close		: false,
		resizable	: false,
		position	: ['center',50],
		close       : function(event) {
			goodsSelectDialogOpenFlag = false;
			$("#doCloseGoodsSet").click();
		}
	});

	// 商品選択ボタン　クリック
	$.ajaxSetup({ cache: false }); 
	$("a[id^='goodsSelect-']").click(function(e){
		
		if (goodsSelectDialogOpenFlag) {
			return false;
		}
		goodsSelectDialogOpenFlag = true;
		$("#goodsSelectDialog").append('<div class="goodsSelectDialog"></div>');
		var rno = $(this).attr("id").replace(/goodsSelect-/g, "");
		var url = "goodsselect.html?rno=" + rno;
		
		$(".goodsSelectDialog").load(url,function(){
			$("#goodsSelectDialog").dialog("open");
			
			
			$(function(){
			
				// OKボタン
				$("#setGoodsSelect").click(function(){
					if ( checkInput() ) {
						return ;
					}
				
					// 商品情報を作成
					var goodsInfo = createGoodsInfo();
					$("#rno").val($("#rno-goodsSelect").val());
					$("#goodsInfo").val(goodsInfo);
					$('#goodsSelectDialog').dialog('destroy');
					$("#doGoodsSet").click();
				});
			
				// キャンセルボタン
				$("#cancelGoodsSelect").click(function(){
					$('#goodsSelectDialog').dialog('close');
				});
				
				// 初期設定　チェックボックスとボタン画像
				$("input[id^='select']").each(function(){
					changeImage($(this));
				});
			
				// ボタン画像(PC)　クリック
				$("input[id^='select']").parent().find("img").click(function(e){
					var checkBox = $(this).parent().parent().parent().find("input[id^='select']");
					if (checkBox.is(':checked')) {
						checkBox.prop("checked", false);
					} else {
						checkBox.prop("checked", true);
					}
					changeImage(checkBox);
				});
				
				
				// チェックボックス　クリック
				$("input[id^='select']").click(function(e){
					changeImage($(this));
				});
			});
		});
		return false;
	});
});
