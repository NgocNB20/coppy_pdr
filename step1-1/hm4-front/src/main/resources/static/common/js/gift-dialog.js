/* ======================================================================
 のし選択
====================================================================== */

$( document ).ready( function() {
	$( "#mizuhiki" ).hide();
	$( ".mizuhiki" ).hide();
	$( "#noshi_list li" ).hide();
});

$(function(){
	// のし選択
	var handler03 ='';
    var handler03 = $('#noshi_list label');
    handler03.click(function(){
		$('#noshi_list label').removeClass("selected");
		$( ".mizuhiki" ).hide();
		$(this).andSelf().addClass("selected");
        $(this).next().css('display','block');
		$('#mizuhiki').show();
    });
});


// のしのかけ方が選択されている場合
$(function(){
  if ($("input:radio[name='giftWrappingType']:checked").val()) {
    $('#mizuhiki').show();
}
});

// のしダイアログ
$(function() {
	$(":button[id='selectGift']").click(function() {
		var giftReceiverIndex = $(this).attr("rindex");
		var giftGoodsIndex = $(this).attr("index");
		$("#giftReceiverIndex").val(giftReceiverIndex);
		$("#giftGoodsIndex").val(giftGoodsIndex);
		$("#doInputGift").click();
		return false;
	});

	$(":image[id='selectGift']").click(function() {
		var giftReceiverIndex = $(this).attr("rindex");
		var giftGoodsIndex = $(this).attr("index");
		$("#giftReceiverIndex").val(giftReceiverIndex);
		$("#giftGoodsIndex").val(giftGoodsIndex);
		$("#doInputGift").click();
		return false;
	});

	$("#cancelGift").click(function() {
		$("#giftReceiverIndex").val("");
		$("#giftGoodsIndex").val("");
		dialogClose();
	});

	$("#setGift").click(function() {
		if (!validateGift()) return;

		$(":hidden[id='giftWrappingGroupSeq']").val($(":radio[name='giftWrappingGroupSeq']:checked").val());
		$(":hidden[id='giftWrappingSeq']").val($(":radio[name='dispGiftWrappingSeq']:checked").val());
		$(":hidden[id='giftWrappingType']").val($(":radio[name='dispGiftWrappingType']:checked").val());
		$(":hidden[id='inscription']").val($(":text[id='inscription']").val());
		$(":hidden[id='giftWrappingNamePrint']").val($(":text[id='giftWrappingNamePrint']").val());


		$(":radio[name='giftWrappingGroupSeq']").prop("disabled", true);
		$(":radio[name='dispGiftWrappingSeq']").prop("disabled", true);
		$(":radio[name='dispGiftWrappingType']").prop("disabled", true);
		$(":text[id='inscription']").prop("disabled", true);
		$(":text[id='giftWrappingNamePrint']").prop("disabled", true);

		dialogDestroy();
		$("#doSetGift").click();
	});

	$(":radio[name='giftWrappingGroupSeq'][value='" + $(":hidden[id='giftWrappingGroupSeq']").val() + "']").attr("checked", true);
	$(":radio[name='dispGiftWrappingSeq'][value='" + $(":hidden[id='giftWrappingSeq']").val() + "']").attr("checked", true);
	$(":radio[name='dispGiftWrappingType'][value='" + $(":hidden[id='giftWrappingType']").val() + "']").attr("checked", true);
	$(":text[id='inscription']").val($(":hidden[id='inscription']").val());
	$(":text[id='giftWrappingNamePrint']").val($(":hidden[id='giftWrappingNamePrint']").val());

	if ($(":hidden[id='inscription']").hasClass("onTeedaError")) $(":text[id='inscription']").addClass("onTeedaError");
	if ($(":hidden[id='giftWrappingNamePrint']").hasClass("onTeedaError")) $(":text[id='giftWrappingNamePrint']").addClass("onTeedaError");

	var giftSeq = $(":radio[name='dispGiftWrappingSeq']:checked");
	if (giftSeq.length > 0) {
		$('#noshi_list label').removeClass("selected");
		$( ".mizuhiki" ).hide();
		giftSeq.parent().andSelf().addClass("selected");
	    giftSeq.parent().next().css('display','block');
		$('#mizuhiki').show();
	}
});

function changeGiftWrappingGroupSeq(noInitFlag) {
	var giftWrappingGroupSeq = $(":radio[name='giftWrappingGroupSeq']:checked");
	if (giftWrappingGroupSeq.length != 1) return;

	if (giftWrappingGroupSeq.val() == "-") {
		$("li[id='giftWrappingItem']").show();
	} else {
		if (!noInitFlag) $(":radio[name='dispGiftWrappingSeq']:checked").attr("checked", false);
		$("li[id='giftWrappingItem']").hide();
		$("li[id='giftWrappingItem'][giftWrappingGroupSeq='" + giftWrappingGroupSeq.val() + "']").show();

		if (!noInitFlag) $(":text[id='inscription']").val("");
		$(":text[id='inscription']").css("background-color", "#e7ece8");
		$(":text[id='inscription']").attr("readonly", true);
	}
}

function changeGiftWrappingSeq(noInitFlag) {
	var giftWrappingSeq = $(":radio[name='dispGiftWrappingSeq']:checked");
	if (giftWrappingSeq.length != 1) {
		if (!noInitFlag) $(":text[id='inscription']").val("");
		$(":text[id='inscription']").css("background-color", "#e7ece8");
		$(":text[id='inscription']").attr("readonly", true);
		return;
	}

	var giftWrappingItem = giftWrappingSeq;
	while (giftWrappingItem != null && giftWrappingItem.attr("namePrintFlag") == null) giftWrappingItem = giftWrappingItem.parent();
	if (giftWrappingItem == null) return;

	if (giftWrappingItem.attr("namePrintFlag") == 0) {
		if (!noInitFlag) $(":text[id='inscription']").val(giftWrappingItem.attr("giftWrappingName"));
		$(":text[id='inscription']").css("background-color", "#e7ece8");
		$(":text[id='inscription']").attr("readonly", true);
	} else {
		if (!noInitFlag) $(":text[id='inscription']").val("");
		$(":text[id='inscription']").css("background-color", "");
		$(":text[id='inscription']").attr("readonly", false);
	}
}

function validateGift() {
	var errmsg = "", value;

	// のし
	var value = $(":radio[name='dispGiftWrappingSeq']:checked");
	if (value.length == 0) errmsg += "のし を選択してください。<br />";

	// のしのかけ方
	// 個別のしの場合のみ入力チェック[理由]一括のしの場合はのしのかけ方が固定のため
	if($("#giftWrappingType").html() == "false") {
		value = $(":radio[name='dispGiftWrappingType']:checked");
		if (value.length == 0) errmsg += "のしのかけ方 を選択してください。<br />";
	}

	// 表書き
	var omotegakiError = false;
	value = $(":text[id='inscription']").val();
	if (value == "") {errmsg += "表書き を入力してください。<br />"; omotegakiError=true;}
	else if (value.length > 100) {errmsg += "表書きは全角100文字以内でご入力ください。"; omotegakiError=true;}
	else if (!validateSpecialCharacter(value)) {errmsg += "表書き に正しい値を入力してください。<br />"; omotegakiError=true;}
	else if (!validateBothSideSpace(value)) {errmsg += "表書きの両端の半角スペース、全角スペースを削除してください。<br />"; omotegakiError=true;}
	// 表書きで入力に誤りがある場合、テキストボックスの色を変える
	if (omotegakiError) $(":text[id='inscription']").addClass("onTeedaError");
	// ない場合、テキストボックスの色を元に戻す
	else $(":text[id='inscription']").removeClass("onTeedaError");

	// お名前
	var onamaeError = false;
	value = $(":text[id='giftWrappingNamePrint']").val();
	value = value.split("\/").join("／");
	if (value != "") {
		if (value.length > 32) {errmsg += "お名前は全角32文字以内でご入力ください。"; onamaeError=true;}
		else if (!validateSpecialCharacterForOnamaeError(value)) {errmsg += "お名前 に正しい値を入力してください。<br />"; onamaeError=true;}
		else if (!validateBothSideSpace(value)) {errmsg += "お名前の両端の半角スペース、全角スペースを削除してください。<br />"; onamaeError=true;}
	}

	// お名前で入力に誤りがある場合、テキストボックスの色を変える
	if (onamaeError) $(":text[id='giftWrappingNamePrint']").addClass("onTeedaError");
	// ない場合、テキストボックスの色を元に戻す
	else $(":text[id='giftWrappingNamePrint']").removeClass("onTeedaError")
	                                  .val(value);

	// 入力に誤りがある場合、エラーメッセージを設定しダイアログ画面の先頭に移動
	if (errmsg != "") {
		$('#jsGiftError').css("display","block");
		$("#jsGiftErrorMessages").html(errmsg);
		var position = $("#giftDialog").offset().top;
		$("html,body").animate({
		    scrollTop : position
		}, {
		    queue : false
		});

		return false;
	}

	return true;
}

/**
 * のしのお名前用バリデーションチェック
 * @param str
 * @param allow
 * @returns
 */
function validateSpecialCharacterForOnamaeError(str, allow) {
	var re;
	if (allow) {
		re = new RegExp("[\\x00-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f-\\x9f]");
	} else {
		re = new RegExp("[\\x00-\\x1f\\x7f-\\x9f!\"#$%&'()*+,-.:;<=>?@\\[\\\\\\]\\^_`{|}~]");
	}
	return !str.match(re);
}

function validateSpecialCharacter(str, allow) {
	var re;
	if (allow) {
		re = new RegExp("[\\x00-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f-\\x9f]");
	} else {
		re = new RegExp("[\\x00-\\x1f\\x7f-\\x9f!\"#$%&'()*+,-.\\/:;<=>?@\\[\\\\\\]\\^_`{|}~]");
	}
	return !str.match(re);
}

function validateBothSideSpace(str) {
	return !str.match(/^[\s　]+|[\s　]+$/);
}

/* ======================================================================
 メッセージカード
====================================================================== */
jQuery(function($){
	$( 'input[name^="goodssettingForm:receiverItems"]:radio' ).each( function() {
		$(this).change( function() {
			changeMessageCard(this)
		});
		changeMessageCardLoad(this)
	});
});

function changeMessageCardLoad(element) {
	if ($(element).val() == '0' && $(element).prop("checked")) {
		$(element).parent().parent().find('#messageCardBodyArea').css('visibility','hidden'); 
		$(element).parent().parent().find('#messageCardBodyArea').css('height','0px'); 
	} else if ($(element).val() == '1' && $(element).prop("checked")) {
		$(element).parent().parent().find('#messageCardBodyArea').css('visibility','visible'); 
		$(element).parent().parent().find('#messageCardBodyArea').css('height','auto'); 
	}
}

function changeMessageCard(element) {
	if ($(element).val() == '0') {
		$(element).parent().parent().find('#messageCardBodyArea').css('visibility','hidden'); 
		$(element).parent().parent().find('#messageCardBodyArea').css('height','0px'); 
	} else {
		$(element).parent().parent().find('#messageCardBodyArea').css('visibility','visible'); 
		$(element).parent().parent().find('#messageCardBodyArea').css('height','auto'); 
	}
}