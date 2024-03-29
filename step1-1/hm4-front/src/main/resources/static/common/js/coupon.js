// 2023-renew No24 from here
/** クーポン関連JS */

/* Cookieキー接頭辞 */
const COOKIE_KEY_PREFIX = 'couponCode';
/* Cookie値区切り */
const COOKIE_VALUE_SEPARATOR = '/';
/* Cookieクォーテーション */
const QUOTATION = '"';

/**
 * Cookie保存処理
 */
function saveCookie(couponCode) {
    // クーポンコードが空の場合、処理しない
    if (!couponCode) {
        return;
    }

    // ユーザーID
    var userId = document.getElementById("loginUserId").value;

    // Cookie有効期限（24時間）
    var expires = new Date(); // 現在日時
    expires.setDate(expires.getDate() + 1); // 翌日
    expires.setHours(expires.getHours() + 9); // 9時間後（日本を基準とし、グリニッジ標準時との時差）
    expires = expires.toGMTString(); // Cookie用にグリニッジ標準時に変換

    // Cookie登録連番
    var couponCodeCnt = 1;
    // Cookie登録連番配列
    var cookieArray = new Array();
    // 保存されている全Cookie配列
    allCookies = document.cookie.split('; ');
    // 別会員フラグ
    var anotherFlg = false;

    for (var i = 0; i < allCookies.length; i++) {
        // クーポンの存在チェック
        if (allCookies[i].includes(COOKIE_KEY_PREFIX)) {
            // 連番を取得
            var cookieNameNm = allCookies[i]
                .split('=')
                .find(row => row.startsWith(COOKIE_KEY_PREFIX))
                .split('-')[1];
            // 同一会員のクーポン存在チェック
            if (allCookies[i].includes(userId)) {
                // 連番をそのまま設定
                couponCodeCnt = cookieNameNm;
                break;
            } else {
                anotherFlg = true;
                cookieArray.push(cookieNameNm);
            }
        }
    }

    // 別会員が同ブラウザでCookie登録連番を使用している場合
    if (anotherFlg) {
        if (cookieArray.length) {
            couponCodeCnt = (Math.max.apply(null, cookieArray)) + 1;
        }
    }

    // Cookieキーの作成（定数 + 「-」 + 連番）
    var cookieName = COOKIE_KEY_PREFIX + "-" + couponCodeCnt + "=";

    // クーポンを「利用しない」場合の値を取得
    var defaultCouponRadioValue = $("#defaultCouponRadio").length ? document.getElementById("defaultCouponRadio").value : "";
    if (couponCode === defaultCouponRadioValue) {
        // Cookie削除
        document.cookie = cookieName + QUOTATION + userId + COOKIE_VALUE_SEPARATOR + couponCode + QUOTATION + "; path=/; max-age=0;";
    } else {
        // Cookie登録
        document.cookie = cookieName + QUOTATION + userId + COOKIE_VALUE_SEPARATOR + couponCode + QUOTATION + "; path=/; expires=" + expires + ";";
    }
}

/**
 * クーポンラジオボタン選択時の処理
 */
function selectCouponRadio(couponCode) {
    // Cookie登録処理
    saveCookie(couponCode);

    // ajax処理
    $.ajax({
        type     : "GET"
        ,url      : pkg_common.getAppComplementUrl() + "/cart/doSelectCoupon"
        ,timeout  : 30000
    })
        .done(function (data) {
            var couponMessageDiv = $("#couponMessageDiv")
            if (couponMessageDiv && couponMessageDiv.length) {
                couponMessageDiv.remove()
            }
            var couponMessageErrorDiv = $("#couponMessageErrorDiv")
            if (couponMessageErrorDiv && couponMessageErrorDiv.length) {
                couponMessageErrorDiv.remove()
            }
            if (data) {
                var newLi = "<div id='couponMessageDiv' class='p-cart__coupon-reception c-reception c-reception--line'><ul><li>" + data + "</li></ul></div>"
                $("#couponMessage").append(newLi);
            }
        });
}
// 2023-renew No24 to here
