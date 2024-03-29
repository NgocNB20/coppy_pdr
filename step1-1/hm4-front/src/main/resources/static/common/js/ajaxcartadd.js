/** サーバサイドのPageコンポーネント名 */
var PAGE_COMPONENT;

/** サーバサイドのActionコンポーネント名 */
var ACTION_COMPONENT;

/** カート追加メソッド */
var ACTION_METHOD_ADD_CART;

/** カート追加メソッド（セット商品用）*/
var ACTION_METHOD_ADD_CART_SETGOODS;

/** タイムアウト（秒） */
var TIME_OUT;

/** セット商品フラグ セット商品 */
var SETGOODS_FLAG_ON;

/** セット商品フラグ セット商品以外 */
var SETGOODS_FLAG_OFF;

/** カート投入数量 */
var ADD_CART_GOODS_COUNT;

/**
 * Ajax処理URL
 * cookieがoffの場合、URLにjsessionidを付与する必要がある。
 * jessesionidは、html内にダミーリンクを用意することで、teedaに付与させる。
 */
var AJAX_PROCESS_URL;

/**
 * このJavaScriptをインポートしている画面のパス
 * location.pathnameから、コンテキストルートを取得するときに利用する
 */
var THIS_SCRIPT_IMPORT_PAGE_PATH;

/** タイムアウト発生時のリダイレクト先URL */
var TIME_OUT_PAGE_URL;

/** サーバサイドでのエラー発生時および、JavaScriptエラー発生時のリダイレクト先URL */
var SYSTEM_ERROR_PAGE_URL;


/** クリックイベントを無効にするメソッド */
function preventOnClick(e) {
	(e.preventDefault) ? e.preventDefault():e.returnValue=false;
}

/** クリックを禁止するメソッド */
function prohibitClick(){
	if($("#ajaxCartAdd").size() > 0) {
		$("#ajaxCartAdd").attr('onclick', '(event.preventDefault) ? event.preventDefault():event.returnValue=false;');
	}
	if($("[id^='goAjaxCart']").size() > 0) {
		$("[id^='goAjaxCart']").attr('onclick', '(event.preventDefault) ? event.preventDefault():event.returnValue=false;');
	}
}

/** クリック許可メソッド */
function allowClick(){
	if($("#ajaxCartAdd").size() > 0) {
		$("#ajaxCartAdd").attr('onclick', 'ajaxCartAdd();');
	}
	if($("[id^='goAjaxCart']").size() > 0) {
		$("[id^='goAjaxCart']").attr('onclick', 'ajaxCartAddFavorite(this); return false;');
	}
}

/** クリック許可メソッド（セット商品用） */
function allowClickSetGoods(){
	$("#ajaxCartAddSetGoods").attr('onclick', 'ajaxCartAddSetGoods();');
}

/** タイムアウト発生時のコールバックメソッド */
function onTimeOut() {
	// エラー画面へリダイレクト
	location.href = TIME_OUT_PAGE_URL;
}

/**
 * エラー発生時のコールバックメソッド
 * JavaScriptエラー、通信エラー時にコールバックされる
 */
function onFailure() {
	// エラー画面へリダイレクト
	location.href = TIME_OUT_PAGE_URL;
}

/**
 * カートへ追加するボタンが押された際の初期処理
 */
function initialProcessing(){
	// 複数回カートに追加されるのを防止するため、クリックを禁止
	prohibitClick();

	$('.add-bag-wrap').unbind();

	// エラーメッセージを一旦削除
	if ($("#errorMessages").children().size() > 0) {
		$("#errorMessages").children().remove();
	}

	// サーバサイドでバリデータエラーが発生している場合はメッセージを非表示にする
	if($(".inputError").size() > 0) {
		$(".inputError").hide();
	}
}

/**
 * エラーメッセージをhtmlに出力するメソッド
 */
function printErrorMessages(errorMessage){
	if(errorMessage.length){
		var html = "<div class=\"inputError\"><ul id=\"allMessages\">";
		for(var i = 0; i < errorMessage.length; i++){
			html += "<li>" + errorMessage[i].message + "</li>\n";
		}
		html += "</ul>\n</div>";
		$("#errorMessages").html(html);
		// 2023-renew No65 from here
		$(".c-field-error").css("display","block");
		// 2023-renew No65 to here
		$("html,body").animate({scrollTop:$('#errorMessages').offset().top});
	}
}

/**
 * エラーメッセージをhtmlに出力するメソッド
 */
function printErrorMessages(errorMessage, id){
	if(errorMessage.length) {
		var html = "<div class=\"c-field-error c-field-error--top\">";
		for(var i = 0; i < errorMessage.length; i++){
			html += errorMessage[i].message + "</div>";
		}
		$('#goodsCode-' + id + '-error').html(html);
	}
}

/**
 * 実行するサーバサイドコンポーネントの設定
 */
function setServerSideComponent(requestParam, actionMethod){
	// サーバサイドのPageコンポーネント
	requestParam.pageComponent = PAGE_COMPONENT;
	// サーバサイドのActionコンポーネント
	requestParam.actionComponent = ACTION_COMPONENT;
	// Actionコンポーネントのメソッド名
	requestParam.actionMethod = actionMethod;
}

/**
 * Ajax通信設定
 */
function setAjax(ajax, requestParam){
	// リクエストパラメータ
	ajax.params = requestParam;
	// URL
	ajax.url = AJAX_PROCESS_URL;
	// タイムアウト
	ajax.timeout = TIME_OUT;
	// レスポンスはJSON形式で受け取る
	// 明示的にレスポンス形式を指定しないと、TEXT形式となってしまう。
	ajax.responseType = Kumu.Ajax.RESPONSE_TYPE_JSON;
}

/**
 * コールバック関数設定
 */
function setCallBackMethod(ajax, update){
	// 通信成功
	ajax.doAction = update;
	// タイムアウト
	ajax.onTimeout = onTimeOut;
	// 通信失敗
	ajax.onFailure = onFailure;
	// JavaScriptエラー
	ajax.onException = onFailure;
	// 実行
	Kumu.Ajax.executeAjax(ajax);
}


/**
 * カート追加通信のコールバックメソッド
 */
function updateCartView(response) {
	showResult(response);

	// クリック許可
	allowClick();
}

/**
 * カート追加通信のコールバックメソッド（セット商品用）
 */
function updateCartViewSetGoods(response) {
	showResult(response);

	// クリック許可
	allowClickSetGoods();
}

/**
 * カート追加結果を表示
 */
function showResult(response){
	// セッション切れの場合は再読込する
	if(!response["cart"]["okSession"]){
		location.href = location.href;
	}
	// カート追加結果の表示
	if (response["cart"]["result"]) {
		// 追加成功
		// 表示項目（商品番号、商品名、規格タイトル1、規格値1、規格タイトル2、規格値2、カート投入数量）
		$("#cartMessageGoodsCode").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["goodsCode"]);
		$("#cartMessageGoodsName").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["goodsGroupName"]);
		$("#cartMessageUnitTitle1").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitTitle1"]);
		$("#cartMessageUnitValue1").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitValue1"]);
		$("#cartMessageUnitTitle2").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitTitle2"]);
		$("#cartMessageUnitValue2").html(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitValue2"]);
		$("#cartMessageGoodsCount").html(ADD_CART_GOODS_COUNT);
		// 規格タイトル1が存在する場合は表示させる、存在しない場合は表示させない
		if (response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitTitle1"] != null) {
			$("#isCartMessageUnitTitle1").css('display', 'block');
		} else {
			$("#isCartMessageUnitTitle1").css('display', 'none');
		}
		// 規格タイトル2が存在する場合は表示させる、存在しない場合は表示させない
		if (response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["unitTitle2"] != null) {
			$("#isCartMessageUnitTitle2").css('display', 'block');
		} else {
			$("#isCartMessageUnitTitle2").css('display', 'none');
		}

        // UKタグログ連携
        if (typeof(_ukwq) !== 'undefined') {
            // 初期化
            _ukwq = [];
            _ukt = document.createElement('script'); _ukt.type = 'text/javascript'; _ukt.async = true;
            _ukt.src = '//' + _ukwhost + '/taglog/ukwlg.js';
            _uks = document.getElementsByTagName('script')[0]; _uks.parentNode.insertBefore(_ukt, _uks);
            // 共通部
            _ukwq.push(['_setClient', _goodsClient]);
            if (_cryptoLoginId != null) { _ukwq.push(['_setLoginID', _cryptoLoginId]); }
            _ukwq.push(['_setReqURL', _reqUrl]);
            _ukwq.push(['_setRefURL', _refUrl]);
            _ukwq.push(['_setDisplay', _display]);
            // カートイン
            var cartInGoods = response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"];
            _ukwq.push(['_setCartIn', cartInGoods["goodsGroupCode"] + ":" + ADD_CART_GOODS_COUNT + ":" + cartInGoods["goodsPrice"]]);
            _ukwq.push(['_sendCartLog']);
        }

		//ダイアログオープン
		$("#cartMessage").dialog("open");

        $("#modal-cart").fadeIn();
        $('body').addClass('is-modal-opened');
	} else {
		// 追加失敗
		if (response["cart"]["validatorErrorList"].length) {
			// バリデータエラー
			printErrorMessages(response["cart"]["validatorErrorList"]);
		} else if (response["cart"]["errorList"].length){
			// カート追加エラー errorList
			printErrorMessages(response["cart"]["errorList"]);
		}
	}
	// ヘッダーの商品点数を変更
	if (null != response["cart"]["cartInfo"]) {
		if ($("#sumCount").size() < 1) {
			$(".cart_item").html("<span id=\"sumCount\" ></span>");
		}
		var sumCount = parseInt($("#sumCount").text());
		var addCartGoodsCount = parseInt(ADD_CART_GOODS_COUNT);
		var sumCartGoodsCount = (sumCount + addCartGoodsCount).toString();
		$("#sumCount").html(sumCartGoodsCount);
	}
	// ヘッダーの商品合計金額を変更
	if (null != response["cart"]["cartInfo"]) {
		if ($("#sumPrice").size() < 1) {
			$(".cart_price").html("&yen;" + "<span id=\"sumPrice\" ></span>");
		}
		var sumPrice = $("#sumPrice").text();
		sumPrice = parseInt(sumPrice.replace(/,/g, ""));
		var addCartGoodsPrice = parseInt(response["cart"]["cartInfo"]["cartGoodsDtoList"]["0"]["goodsDetailsDto"]["goodsPrice"]);
		var addCartGoodsCount = parseInt(ADD_CART_GOODS_COUNT);
		var sumCartGoodsPrice = sumPrice + (addCartGoodsPrice * addCartGoodsCount);
		sumCartGoodsPrice = sumCartGoodsPrice.toString();
		sumCartGoodsPrice = sumCartGoodsPrice.replace(/(\d)(?=(\d\d\d)+$)/g, '$1,');
		$("#sumPrice").html(sumCartGoodsPrice);
	}
}

/**
 * onload処理
 */
$(function(){
	PAGE_COMPONENT = "front_goods_ajaxCartAddPage";

	ACTION_COMPONENT = "front_goods_ajaxCartAddAction";

	ACTION_METHOD_ADD_CART = "/ajaxCartAdd";

	ACTION_METHOD_ADD_CART_SETGOODS = "ajaxCartAddSetGoods";

	TIME_OUT = 30000;

	AJAX_PROCESS_URL = $("#goAjaxProcessUrl").attr("href");

	THIS_SCRIPT_IMPORT_PAGE_PATH = 'goods/index.html';

	TIME_OUT_PAGE_URL = location.pathname.replace(THIS_SCRIPT_IMPORT_PAGE_PATH, 'error.html');

	SYSTEM_ERROR_PAGE_URL = location.pathname.replace(THIS_SCRIPT_IMPORT_PAGE_PATH, 'error.html');

	// ロード完了前にカート追加ボタンを押下した場合、Ajaxリクエストが出来ず、ボタンが非活性のままになるケース対応
	allowClick();
	allowClickSetGoods();
});

/**
 * カートへ追加
 */
function ajaxCartAdd(){
	// 初期処理
	initialProcessing();

	// ログイン判定（ゲストはログイン画面へ）
	if ($("#logIn").val() != undefined && $("#doLogout-h").is(":visible") == false) {
		// クリックを許可
		allowClick();

		$("#go-Login-h").children("img").click();
		return;
	} else if ($(".hlogin").val() != undefined && $("#goLogout-h").is(":visible") == false) {
		// クリックを許可
		allowClick();

		$("#goLoginPage-h")[0].click();
		return;
	}

	// JavaScript内でチェックした際のエラーメッセージを格納する変数
	var list = [];

	// 入力チェック
	// 規格1が未選択の場合は入力エラーとする
	if($("#unitSelect1").size() > 0) {
		if(!$("#unitSelect1").val()) {
			var errObj = {};
			errObj["message"] = $("#unitSelect1").attr("title") + " を選択してください。";
			list[list.length] = errObj;
		}
	}
	// 規格2が未選択の場合は入力エラーとする
	if($("#unitSelect2").size() > 0) {
		if(!$("#unitSelect2").val()) {
			var errObj = {};
			errObj["message"] = $("#unitSelect2").attr("title") + " を選択してください。";
			list[list.length] = errObj;
		}
	}
	// 配送サイクルが未選択の場合は入力エラーとする
	if($("#purchaseInterval").size() > 0) {
		if(!$("#purchaseInterval").val()) {
			var errObj = {};
			errObj["message"] = $("#purchaseInterval").attr("title") + " を選択してください。";
			list[list.length] = errObj;
		}
	}
	// 数量が未入力の場合は入力エラーとする
	if($("#gcnt").size() > 0) {
		if(!$("#gcnt").val()) {
			var errObj = {};
			errObj["message"] = $("#gcnt").attr("title") + " を入力してください。";
			list[list.length] = errObj;
		} else if($("#gcnt").val().match(/^[^0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = $("#gcnt").attr("title") + " の値は、数値で入力してください。";
			list[list.length] = errObj;
		} else if($("#gcnt").val().match(/^[-][0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = $("#gcnt").attr("title") + " は、0以上の値を入力してください。";
			list[list.length] = errObj;
		} else if($("#gcnt").val().match(/^[-]?[0-9]+\.[0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = $("#gcnt").attr("title") + " は整数で入力してください。";
			list[list.length] = errObj;
		} else if($("#gcnt").val() < 1 || $("#gcnt").val() > 999) {
			var errObj = {};
			errObj["message"] = $("#gcnt").attr("title") + " は 1 以上、999 以下で入力してください。";
			list[list.length] = errObj;
		}
	}
	/* 処理無し
	// 在庫数チェック
	if(!list.length){
		// ここまででエラーがない場合 -> 規格も数量もきちんと入力されている場合
		// 在庫数チェックを行う
		var gcd = $("#gcd").val();
		var stock = parseInt($("." + gcd + "[id^='salesPossibleStock']").val());
		if (stock === 0) {
			// 在庫数が0の場合エラー
			var errObj = {};
			errObj["message"] = "申し訳ございませんが、現在ご指定の商品は在庫切れです。";
			list[list.length] = errObj;
		}else if(parseInt($("#gcnt").val()) > stock) {
			// 在庫不足の場合エラー
			var errObj = {};
			errObj["message"] = "申し訳ございませんが、現在ご指定の商品は在庫が不足しています。" + stock + "個以内で注文してください。";
			list[list.length] = errObj;
		}
	}
	*/

	if(list.length){
		// エラーメッセージをhtml出力
		printErrorMessages(list);

		// クリックを許可
		allowClick();

		return;
	}



	// // グローバル変数に設定
	 ADD_CART_GOODS_COUNT = $("#gcnt").val();

	var gcd = $("#gcd").val();
	var gcnt = $("#gcnt").val();
	// Ajax通信設定
	callAjaxCartAdd(gcd, gcnt);

}

/**
 * ajax実行
 */
function callAjaxCartAdd(gcd, gcnt){
	// Ajax通信を行う
	$.ajax({
		type			:	"GET"
		,url			:	ACTION_METHOD_ADD_CART
		,dataType	:	"json"
		,data			:	{ "gcd":gcd , "gcnt":gcnt}
		,timeout		:	30000
	})
		.done(function(response){updateCartView(response);})
		.fail(function(response){ updateCartView(response)})
	;
}

/**
 * カートへ追加（マイリスト用）
 */
function ajaxCartAddFavorite(req){
	// 初期処理
	initialProcessing();

	// ログイン判定（ゲストはログイン画面へ）共通化
	if ($("#logIn").val() != undefined && $("#doLogout-h").is(":visible") == false) {
		// クリックを許可
		allowClick();

		$("#go-Login-h").children("img").click();
		return;
	} else if ($(".hlogin").val() != undefined && $("#goLogout-h").is(":visible") == false) {
		// クリックを許可
		allowClick();

		$("#goLoginPage-h")[0].click();
		return;
	}

	// JavaScript内でチェックした際のエラーメッセージを格納する変数
	var list = [];

	// パラメータを取得
	var reqIdArray = req.id.split("-");
	var reqParams = req.getAttribute("href").split("?");
	var goodsParams = reqParams[1].split("&");
	var paramArray = [];
	for (var i = 0; i < goodsParams.length; i++) {
		var paramItem = goodsParams[i].split('=');
		paramArray[paramItem[0]] = paramItem[1];
	}
	var gcd = paramArray.gcd;
	var gcnt = paramArray.gcnt;

	// パラメータから数量が取得できない場合（数量入力が可能な場合）
	// 採番されたハイフンNoを利用して、数量を取得する。取得後、チェック処理を行う。
	if (gcnt == "") {
		var gcnt = $('#gcnt-' + reqIdArray[1]).val();
		// 数量が未入力の場合は入力エラーとする
		if (gcnt == ""|| gcnt == undefined){
			var errObj = {};
			errObj["message"] = "注文数 を入力してください。";
			list[list.length] = errObj;
		} else if (gcnt.match(/^[^0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = "注文数 の値は、数値で入力してください。";
			list[list.length] = errObj;
		} else if (gcnt.match(/^[-][0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = "注文数 は、0以上の値を入力してください。";
			list[list.length] = errObj;
		} else if (gcnt.match(/^[-]?[0-9]+\.[0-9]+$/g)) {
			var errObj = {};
			errObj["message"] = "注文数 は整数で入力してください。";
			list[list.length] = errObj;
		} else if (gcnt < 1 || gcnt > 999) {
			var errObj = {};
			errObj["message"] = "注文数 は 1 以上、999 以下で入力してください。";
			list[list.length] = errObj;
		}

		if (list.length) {
      $(req).attr('active-fadein', 'false');
      if ($(req).attr('field-validate')) {
        // エラーメッセージをhtml出力
        printErrorMessages(list, reqIdArray[1]);
      } else {
        // エラーメッセージをhtml出力
        printErrorMessages(list);
      }
			// クリックを許可
			allowClick();

			return;
		} else {
      $(req).attr('active-fadein', 'true');
		}
	}

	ADD_CART_GOODS_COUNT = gcnt;

	callAjaxCartAdd(gcd, gcnt);
}

/**
 * カートへ追加（セット商品用）
 */
function ajaxCartAddSetGoods(){
	// 初期処理
	initialProcessing();

	// JavaScript内でチェックした際のエラーメッセージを格納する変数
	var list = [];

	// 親商品のパラメータチェック
	if ($("#ggcd").val() ===  "" && $("#gcd").val() === "") {
		// 商品グループコード、商品コードの両方がnullか空文字の場合エラー
		var errObj = {};
		errObj["message"] = "申し訳ございませんが、現在ご指定の商品は取り扱っておりません。";
		list[list.length] = errObj;
	}

	// 送信する子商品情報を作成する
	// 子商品の情報を格納する配列
	var sendSetArray = []
	$("[id^='bundleGcnt-']").each(function(){
		var idx = $(this).attr("id").replace("bundleGcnt-","");
		var child_gcnt = $(this).val();
		var child_gcd = $("#bundleGcd-" + idx).val();
		var child_bundleGoodsSeq = $("#bundleGoodsSeq-" + idx).val();

		if (child_bundleGoodsSeq ===  "" || child_gcd ===  "" || child_gcnt === ""){
			// 商品SEQ、商品コード、商品数量のいずれか一つでもnullか空文字だった場合はエラー
			var errObj = {};
			errObj["message"] = "申し訳ございませんが、現在ご指定の商品は取り扱っておりません。";
			list[list.length] = errObj;
		}
		// 値を半角スペースで区切って連結して格納
		sendSetArray.push(child_bundleGoodsSeq + " " + child_gcd + " " + child_gcnt);
	});
	// 全ての子商品の値を"/"で区切って連結
	var sendStringSetGoods = sendSetArray.join("/");

	if(list.length){
		// エラーメッセージをhtml出力
		printErrorMessages(list);

		// クリックを許可
		allowClickSetGoods();

		return;
	}

	// 要求されたページを取得する
	var requestParam = {};

	// リクエストパラメータの作成
	// 文字化け回避のためURLエンコード
	requestParam.gcd = encodeURI($("#gcd").val());
	requestParam.ggcd = encodeURI($("#ggcd").val());
	requestParam.gcnt = encodeURI("1");
	requestParam.saleId = encodeURI($("#saleId").val());
	requestParam.childGoodsInfo = encodeURI(sendStringSetGoods);
	// 実行するサーバサイドコンポーネントの設定
	setServerSideComponent(requestParam, ACTION_METHOD_ADD_CART_SETGOODS);

	var ajax = Kumu.Ajax.getS2AjaxComponent();
	// Ajax通信設定
	setAjax(ajax, requestParam);
	// コールバック関数設定
	setCallBackMethod(ajax, updateCartViewSetGoods);
}
