/** ユニサーチ商品取得メソッド */
var ACTION_METHOD_GET_UKGOODS;

/** リクエストに設定するpageの値 */
const PARAM_PAGE = 1;

/** リクエストに設定するrowsの値 */
const PARAM_ROWS = 5;

/** レスポンスで受け取る商品情報 */
var uniSearchGoods = [];

/** エラー発生フラグ */
let failFlag = false;

/**
 * オンロード処理
 */
$(function(){

    ACTION_METHOD_GET_UKGOODS = "/ajaxUkUniSearchGoodsGet";

    THIS_SCRIPT_IMPORT_PAGE_PATH = '/index.html';

    TIME_OUT_PAGE_URL = location.pathname.replace(THIS_SCRIPT_IMPORT_PAGE_PATH, 'error.html');

    SYSTEM_ERROR_PAGE_URL = location.pathname.replace(THIS_SCRIPT_IMPORT_PAGE_PATH, 'error.html');

    $("[id^='pickupitems']").each(function() {
        var idx = $(this).attr("id").replace("pickupitems-","");
        // リクエスト設定
        var cid = $(this).attr("cid");
        if(failFlag == false){
            let goodsResponse = callAjaxUkUniSearchGoodsGet(cid);
            setGoods(idx);
        }
    });
});

/**
 * ajax実行
 */
function callAjaxUkUniSearchGoodsGet(cid){
    // Ajax通信を行う
    return $.ajax({
        type: "GET"
        ,url: ACTION_METHOD_GET_UKGOODS
        ,dataType: "json"
        ,data: { "cid":cid}
        ,async: false
        ,cache: false
        ,timeout: 30000
    })
        .done(
            function(data){
                let status = data.responseHeader.status;
                let uniResponse = data.response;
                if (status != 0) {
                    failFlag = true;
                    printErrorMessage();
                    $("[id^='top_special-']").each(function() {
                        $(this).css("display", "none");
                    });
                    return;
                }
                uniSearchGoods = [];
                if (typeof (uniResponse.docs) !== 'undefined') {
                    uniSearchGoods = uniResponse.docs;
                }
            }
        ).fail(function(data){
                failFlag = true;
                printErrorMessage();
                $("[id^='top_special-']").each(function() {
                    $(this).css("display", "none");
                });
            }
        );
}

/**
 * エラーメッセージ組み立て
 */
function printErrorMessage(){
    const ukErrorMessage = document.getElementById(`UkErrorMessage`);
    const div = document.createElement('div');

    div.classList.add('global-error-message');
    const ul = document.createElement('ul');

    const li = document.createElement('li');
    li.innerHTML = `現在、注目商品を取得することができません。申し訳ございませんが、しばらく時間をあけてから再度アクセスお願いいたします。`;

    ul.appendChild(li);
    div.appendChild(ul);
    ukErrorMessage.appendChild(div);
    ukErrorMessage.style.display = "block";
}

/**
 * レスポンス情報からHTMLを組み立てる
 */
function setGoods(idx) {
    const topSpecial = document.getElementById(`top_special-${idx}`);
    const searchBlock = document.querySelector("[sid='searchBlock-" + idx + "']");
    const imageUrl = $('#goodsImageSrc').val();
    const resizePath = $('#resizePath').val();
    const resizeX2Path = $('#resizeX2Path').val();
    const noImagePath = $('#goodsImageSrc').val() + `/noimg.jpg`;

    if (uniSearchGoods != null && uniSearchGoods.length > 0) {
        uniSearchGoods.forEach(goods => {
            var div = document.createElement('div');
            div.classList.add('swiper-slide');
            div.classList.add('c-product__item');

            var link = document.createElement('a');
            link.href = "/goods/index.html?ggcd=" + goods.group_id;

            // 商品画像設定
            var divImage = document.createElement('div');
            divImage.classList.add('c-product__item-image');
            divImage.innerHTML = `
            <img src="${resizePath}${imageUrl}/${goods.group_id}/${goods.group_id}_01.jpg"
              srcset="${resizeX2Path}${imageUrl}/${goods.group_id}/${goods.group_id}_01.jpg 2x"
              onerror="this.src='${noImagePath}'; this.srcset='${noImagePath}' + ' 2x'; this.removeAttribute('onerror');"
              height="200" width="200" decoding="async" loading="lazy">`;
            link.appendChild(divImage);

            // アイコン設定
            if(goods.item_status !== 20 || !isLogin()){
                var divIcon = document.createElement('div');
                divIcon.classList.add('c-product__item-icon');
                divIcon.innerHTML = ``;
                if(goods.reserve_flg){
                    divIcon.innerHTML += `<span class="c-product__item-icon--reserve">セールde予約</span>`;
                }
                if(goods.sale_flg){
                    divIcon.innerHTML += `<span class="c-product__item-icon--sale">SALE</span>`;
                }
                if(goods.new_flg){
                    divIcon.innerHTML += `<span class="c-product__item-icon--new">NEW</span>`;
                }
                if(goods.outlet_flg){
                    divIcon.innerHTML += `<span class="c-product__item-icon--outlet">アウトレット</span>`;
                }
                link.appendChild(divIcon);
            }

            // 商品名
            var divTitle = document.createElement('div');
            divTitle.classList.add('c-product__item-title');
            divTitle.innerHTML = `${goods.item_name}`;
            link.appendChild(divTitle);

            // ログイン時
            if(isLogin()){
                // 販売可否判定
                if(goods.item_status === 20){
                    var divNoSell = document.createElement('div');
                    divNoSell.classList.add('c-product__item-stock');
                    divNoSell.innerHTML = `販売終了しました`;
                    link.appendChild(divNoSell);
                }else{
                    // セールコメント
                    if(goods.sale_flg){
                        if(goods.sale_comment != null){
                            var divComment = document.createElement('div');
                            divComment.classList.add('c-product__item-sale-comment');
                            divComment.innerHTML = `${goods.sale_comment}`;
                            link.appendChild(divComment);
                        }
                        // セール時価格
                        var divSalePrice = document.createElement('div');
                        divSalePrice.classList.add('c-product__item-price');
                        divSalePrice.classList.add('c-product__item-price--sale');
                        divSalePrice.innerHTML = `<small>セール：</small><div class="c-product__item-price-unit">${goods.sale_min_price.toLocaleString()}<span>円`;
                        if(goods.sale_min_price < goods.sale_max_price){
                            divSalePrice.innerHTML += `～</span></div><div class="c-product__item-price-unit">${goods.sale_max_price.toLocaleString()}<span>円</span></div>`;
                        }else {
                            divSalePrice.innerHTML += `</span></div>`;
                        }
                        link.appendChild(divSalePrice);

                        // 通常価格
                        var divRegularPrice = document.createElement('div');
                        divRegularPrice.classList.add('c-product__item-regular-price');
                        divRegularPrice.innerHTML = `<small>通常価格：</small>${goods.min_price.toLocaleString()}円`;
                        if(goods.min_price < goods.max_price){
                            divRegularPrice.innerHTML += `～${goods.max_price.toLocaleString()}円`;
                        }
                        link.appendChild(divRegularPrice);
                    } else {
                        // 価格
                        var divPrice = document.createElement('div');
                        divPrice.classList.add('c-product__item-price');
                        divPrice.innerHTML = `<div class="c-product__item-price-unit">${goods.min_price.toLocaleString()}<span>円`;
                        if(goods.min_price < goods.max_price){
                            divPrice.innerHTML += `～</span></div><div class="c-product__item-price-unit">${goods.max_price.toLocaleString()}<span>円</span></div>`;
                        } else {
                            divPrice.innerHTML += `</span></div>`;
                        }
                        link.appendChild(divPrice);
                    }
                    // 在庫切れ判定
                    if(goods.item_status === 10){
                        var divNoStock = document.createElement('div');
                        divNoStock.classList.add('c-product__item-stock');
                        divNoStock.innerHTML = `在庫切れ`;
                        link.appendChild(divNoStock);
                    }
                }
            // 未ログイン時
            }else{
                var divNotLogin = document.createElement('div');
                divNotLogin.classList.add('c-product__item-notlogin');
                divNotLogin.innerHTML = `価格はログイン後表示`;
                link.appendChild(divNotLogin);
            }

            div.appendChild(link);
            searchBlock.appendChild(div);
        });
    } else {
        // レスポンスが空の場合はエリアを非表示
        topSpecial.style.display = "none";
    }
}

/**
* ログイン判定
*/
function isLogin(){
    if ($('#cryptUserId').val() == ''){
        return false;
    }
    return true;
}
