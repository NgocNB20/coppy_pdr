/** ペイジェントトークン決済 */

/** ペイジェントトークン取得通信正常終了コード */
var PAYGENT_TOKEN_RESULTOK = "0000";
/** クレジットカードの表示 */
var CREDIT = "CREDIT";
/** トークンを取得済みかどうか？ */
var isGotToken = false;
/** 親要素にerrorPartをセットするか */
var needParent = true;
/** カード預かり用トークンを取得するか */
var keepToken = true;
/** セキュリティコードの必須チェックを実施するか */
var checkSecurityCode = false;
/** トークン生成判定 */
var tokenCreate = false;
/** カード預かり用トークン生成判定 */
var keepTokenCreate = false;

/**
 * 画面初期化 イベント割当、セレクトの選択肢準備、セレクトの初期値セット
 */
function initiate() {
	$(jQSelectorH.doNext).click(function() {
			return getCreditCardToken(this);	
	});
	$(jQSelectorH.origexpire1 + ":first option").clone().appendTo(jQSelectorH.expire1);
	$(jQSelectorH.origexpire2 + ":first option").clone().appendTo(jQSelectorH.expire2);
	$(jQSelectorH.expire1).val("");
	$(jQSelectorH.expire2).val("");
}

/**
 * 新規クレジットカード用トークン取得処理 needProcessの実装がページ個別に必要
 * @returns true=遷移可能
 */
function getCreditCardToken(elem) {
	// 後続処理が必要か
	if (!needProcess(elem)) {
		return true;
	}
	
	var $selected = getSelected();
	
	if(checkSecurityCode && "" == $selected.find(jQSelectorH.securityCode).val()){
		errorHandle("1601");
		return false;
	}
	
	var merchantId = $(jQSelectorH.merchantId).val();
	var paygentTokenKey = $(jQSelectorH.paygentTokenKey).val();
	var cardNo = $selected.find(jQSelectorH.cardNo).val();
	var expireYear = $selected.find(jQSelectorH.expire1).val();
	var expireMonth = $selected.find(jQSelectorH.expire2).val();
	var securityCode = $selected.find(jQSelectorH.securityCode).val();
	
	var paygentToken = new PaygentToken();

	//　預かりカードの場合はセキュリティコードトークンを使用
	if ('0' === $(jQSelectorH.isRegisterdCard).val()) {

		afterProcess();

	} else {
		tokenCreate = false;
		keepTokenCreate = false;
		// クレジットカードトークン取得
		var cardData = {
				card_number:cardNo, //クレジットカード番号
				expire_year:expireYear, //有効期限-YY
				expire_month:expireMonth/*, //有効期限-MM
				cvc:securityCode //セキュリティーコード*/
			};

		paygentToken.createToken(
			merchantId, //第１引数：マーチャントID
			paygentTokenKey, //第２引数：トークン生成鍵
			cardData,//第３引数：クレジットカード情報
			callBackGetToken //第４引数：コールバック関数(トークン取得後に実行)
		);
		
		// カード保持を行う場合はカード預かり用トークンの発行を同時に行う
		if (keepToken) {
			paygentToken.createToken(
			merchantId, //第１引数：マーチャントID
			paygentTokenKey, //第２引数：トークン生成鍵
			cardData,//第３引数：クレジットカード情報
			callBackGetKeepToken //第４引数：コールバック関数(トークン取得後に実行)
			);	
		}
		
	}
		
	return false;
}

/**
 * トークン取得コールバック
 */
function callBackGetToken(response) {

	if (response.result !== PAYGENT_TOKEN_RESULTOK) {
		errorHandle(response.result);
		return;
	}
	
	tokenCreate = true;
		
	$(jQSelectorH.token).val(response.tokenizedCardObject.token);
	// カード情報移送
	$(jQSelectorH.origcardNo).val(response.tokenizedCardObject.masked_card_number);
	
	if (!keepToken || 'true' === $(jQSelectorH.isRegisterdCard).val()) {
		afterProcess();
	} else {
		// 非同期によりトークンと預かりトークンの取得順が保証できない。
		// 保証できないので両方のトークンが取得できた場合のみ後処理を行うようにする。
		if (tokenCreate && keepTokenCreate) {
			afterProcess();
		}
	}
}

/**
 * 預かりカード用トークン取得コールバック
 */
function callBackGetKeepToken(response) {

	if (response.result !== PAYGENT_TOKEN_RESULTOK) {
		errorHandle(response.result);
		return;
	}
	
	keepTokenCreate = true;
	
	$(jQSelectorH.keepToken).val(response.tokenizedCardObject.token);
	
	// 非同期によりトークンと預かりトークンの取得順が保証できない。
	// 保証できないので両方のトークンが取得できた場合のみ後処理を行うようにする。
	if (tokenCreate && keepTokenCreate) {
		afterProcess();
	}

}

function afterProcess() {

	var $selected = getSelected();
	var expire1 = $selected.find(jQSelectorH.expire1).val();
	var expire2 = $selected.find(jQSelectorH.expire2).val();
	$(jQSelectorH.origexpire1).val(expire1);
	$(jQSelectorH.origexpire2).val(expire2);
	
	// カード情報削除
	$(jQSelectorH.cardNo).val("");
	$(jQSelectorH.expire1).val("");
	$(jQSelectorH.expire2).val("");
	$(jQSelectorH.securityCode).val("");

	isGotToken = true;
	$(jQSelectorH.doNext).click();
}

// ペイジェントエラーコードデータ構造
var errorHash = {
	"1100" : {
		message : "マーチャントID は必須です",
		selectors : [ jQSelectorH.merchantId ]
	},
	"1200" : {
		message : "トークン生成公開鍵は必須です",
		selectors : [ jQSelectorH.paygentTokenKey ]
	},
	"1201" : {
		message : "トークン生成公開鍵を正しく入力してください",
		selectors : [ jQSelectorH.paygentTokenKey ]
	},
	"1300" : {
		message : "カード番号は必須です",
		selectors : [ jQSelectorH.cardNo ]
	},
	"1301" : {
		message : "正しいカード番号を入力してください",
		selectors : [ jQSelectorH.cardNo ]
	},
	"1400" : {
		message : "有効期限は必須です",
		selectors : [ jQSelectorH.expire1, jQSelectorH.expire2 ]
	},
	"1401" : {
		message : "有効期限を正しく入力してください",
		selectors : [ jQSelectorH.expire1, jQSelectorH.expire2 ]
	},
	"1500" : {
		message : "有効期限は必須です",
		selectors : [ jQSelectorH.expire1, jQSelectorH.expire2 ]
	},
	"1501" : {
		message : "有効期限を正しく入力してください",
		selectors : [ jQSelectorH.expire1, jQSelectorH.expire2 ]
	},
	"1502" : {
		message : "有効期限が不正です",
		selectors : [ jQSelectorH.expire1, jQSelectorH.expire2 ]
	},
	"1600" : {
		message : "セキュリティコードを正しく入力してください",
		selectors : [ jQSelectorH.securityCode ]
	},
	"1601" : {
		message : "セキュリティコードは必須です",
		selectors : [ jQSelectorH.securityCode ]
	},
	"7000" : {
		message : "非対応のブラウザです",
		selectors : [ jQSelectorH.securityCode ]
	},
	"7001" : {
		message : "ペイジェントとの通信に失敗しました",
		selectors : [ jQSelectorH.securityCode ]
	},
	"8000" : {
		message : "システムメンテナンス中です",
		selectors : [ jQSelectorH.securityCode ]
	},
	"9000" : {
		message : "ペイジェント決済システム内部エラー",
		selectors : [ jQSelectorH.securityCode ]
	}
};

/**
 * エラー処理
 * @param エラーコード
 */
function errorHandle(errCode) {
	var html = $(jQSelectorH.templateError).text();
	var errorInput = "c-input--error";
	var errorSelect = "c-select--error";
	var errorPart = "errorPart";
	$("." + errorPart).removeClass(errorPart);
	$("." + errorInput).removeClass(errorInput);
	$("." + errorSelect).removeClass(errorSelect);
	if (errorHash[errCode]) {
		html = html.replace("${error}", errorHash[errCode].message
				+ "&nbsp;(エラーコード：" + errCode + ")");
		for ( var i = 0, l = errorHash[errCode].selectors.length; i < l; i++) {
			if ($(errorHash[errCode].selectors[i]).is('select')) {
				var parentSelect = $(errorHash[errCode].selectors[i]).closest('span')
				parentSelect.addClass(errorSelect);
			} else if ($(errorHash[errCode].selectors[i]).is('input')) {
				$(errorHash[errCode].selectors[i]).addClass(errorInput);
			} else {
				$(errorHash[errCode].selectors[i]).addClass(errorPart);
			}
		}
	} else {
		html = html.replace("${error}", "処理中にエラーが発生しました" + "&nbsp;(エラーコード："
				+ errCode + ")");
		if (needParent) {
			$(jQSelectorH.isCredit1).parent(jQSelectorH.isCredit4)
					.addClass(errorPart);
		}
	}
	$(jQSelectorH.jsErrorDiv).html(html);
}
