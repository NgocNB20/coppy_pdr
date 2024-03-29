/**
 * Suggestメイン処理
 */
$(function () {
    /** 定数 */
    let inputText = '';
    let historyGoods = [];
    let suggestKeywordGoods = [];
    let suggestKeywordContents = [];
    let suggestKeyword = [];
    let suggestCategory = [];
    let suggestModelNumber = [];
    let suggestProductName = [];
    let suggestCampaign = [];

    let historyGoodsParams = {
        URL: $('#uniSuggestGoodsSrc').val(),
        data: {
            kw: '',
            rows: 3,
            wt: 'jsonp',
            history: 'on',
            loginid: ($('#cryptUserId').val() == '') ? undefined : $('#cryptUserId').val(),
            uid: (document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*')) == null) ? $('#accessUid').val() : document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*'))[1].split('_')[0]
        }
    };
    let suggestGoodsParams = {
        URL: $('#uniSuggestGoodsSrc').val(),
        data: {
            kw: '',
            rows: 12,
            dr_category: 2,
            dr_modelnumber: 2,
            dr_productname: 3,
            wt: 'jsonp',
            history: 'off',
            loginid: ($('#cryptUserId').val() == '') ? undefined : $('#cryptUserId').val(),
            uid: (document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*')) == null) ? $('#accessUid').val() : document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*'))[1].split('_')[0]
        }
    };
    let suggestContentsParams = {
        URL: $('#uniSuggestContentsSrc').val(),
        data: {
            kw: '',
            rows: 7,
            dr_campaign: 2,
            wt: 'jsonp',
            history: 'off',
            loginid: ($('#cryptUserId').val() == '') ? undefined : $('#cryptUserId').val(),
            uid: (document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*')) == null) ? $('#accessUid').val() : document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*'))[1].split('_')[0]
        }
    };

    /**
     * サジェストメイン処理
     */
    $('#search').on('keyup', function (event) {
        let newText = $(this).val();
        if (inputText == newText) {
            return;
        }
        // キーワードに変更があった場合、サジェストを実行
        inputText = newText;
        initSuggestBlock();
        if (inputText == '') {
            // 履歴サジェストを実施
            let deferredGoods = execHistoryGoods();
            $.when(deferredGoods).done(function () {
                outputHistory();
            });
            return;
        }

        let deferredGoods = execSuggestGoods(inputText);
        let deferredContents = execSuggestContents(inputText);
        $.when(deferredGoods, deferredContents).done(function () {
            outputSuggest();
        });
    });

    /**
     * サジェスト（商品）を取得する
     * @param {String} keyword 検索キーワード
     * @returns $.ajax()
     */
    function execSuggestGoods(keyword) {
        suggestGoodsParams.data.kw = keyword;

        return $.ajax({
            type: 'GET',
            url: suggestGoodsParams.URL,
            data: suggestGoodsParams.data,
            dataType: 'jsonp',
            jsonp: 'cbk',
            async: true,
            cache: false,
            timeout: 3000
        }).done(
            function (data) {
                let status = data.responseHeader.status;
                let response = data.response;
                if (status != 0) {
                    return;
                }
                // キーワードサジェスト
                suggestKeywordGoods = [];
                if (typeof (response.keyword) !== 'undefined') {
                    suggestKeywordGoods = response.keyword.docs.item;
                }
                // カテゴリサジェスト
                suggestCategory = [];
                if (typeof (response.category) !== 'undefined') {
                    suggestCategory = response.category.docs.item;
                }
                // 型番・品番サジェスト
                suggestModelNumber = [];
                if (typeof (response.modelnumber) !== 'undefined') {
                    suggestModelNumber = response.modelnumber.docs.item;
                }
                // 商品サジェスト
                suggestProductName = [];
                if (typeof (response.productname) !== 'undefined') {
                    suggestProductName = response.productname.docs.item;
                }
            }
        );
    }

    /**
     * サジェスト（コンテンツ）を取得する
     * @param {String} keyword 検索キーワード
     */
    function execSuggestContents(keyword) {
        suggestContentsParams.data.kw = keyword;

        $.ajax({
            type: 'GET',
            url: suggestContentsParams.URL,
            data: suggestContentsParams.data,
            dataType: 'jsonp',
            jsonp: 'cbk',
            async: true,
            cache: false,
            timeout: 3000
        }).done(
            function (data) {
                let status = data.responseHeader.status;
                let response = data.response;
                if (status != 0) {
                    return;
                }
                // キーワードサジェスト
                suggestKeywordContents = [];
                if (typeof (response.keyword) !== 'undefined') {
                    suggestKeywordContents = response.keyword.docs.item;
                }
                // 特集サジェスト
                suggestCampaign = [];
                if (typeof (response.campaign) !== 'undefined') {
                    suggestCampaign = response.campaign.docs.item;
                }
            }
        );
    }

    /**
     * 取得したサジェスト結果を表示する
     */
    function outputSuggest() {
        const suggestBlock = document.getElementById('suggestBlock');

        if (suggestKeywordGoods.length || suggestCategory.length || suggestModelNumber.length || suggestProductName.length || suggestKeywordContents.length || suggestCampaign.length) {
            suggestBlock.innerHTML = '';
            suggestKeyword = suggestKeywordGoods.concat(suggestKeywordContents).slice(0, 5);
            let li = document.createElement('li');

            if (suggestKeyword.length) {
                let ul = document.createElement('ul');
                ul.classList.add('suggest-list','search');
                const searchPrefix = '<span style="margin-right:5px;"><img src="/images/search/search.png" class="suggest-icon"></span>';

                suggestKeyword.forEach(suggest => {
                    let li = document.createElement('li');
                    li.classList.add('suggest-item-search');
                    li.innerHTML = `
                    ${searchPrefix}<a href="/search/index.html?keyword=${suggest.word}">${suggest.word}</a>`;
                    ul.appendChild(li);
                });
                suggestBlock.appendChild(ul);
            }
            if (suggestCategory.length) {
                let ul = document.createElement('ul');
                ul.classList.add('suggest-list','category');
                const categoryPrefix = '<span style="margin-right:5px;"><img src="/images/search/category.png" class="suggest-icon"></span>';

                suggestCategory.forEach(category => {
                    let li = document.createElement('li');
                    let cid = category.direct_category_id.substring(category.direct_category_id.lastIndexOf('>') + 1).trim();
                    li.classList.add('suggest-item-category');
                    li.innerHTML = `
                    ${categoryPrefix}<a href="/goods/list.html?cid=${cid}">${category.direct_category_name}</a>`;
                    ul.appendChild(li);
                });
                suggestBlock.appendChild(ul);
            }
            if (suggestModelNumber.length) {
                let ul = document.createElement('ul');
                ul.classList.add('suggest-list','model');

                suggestModelNumber.forEach(modelnumber => {
                    // <li>要素の生成
                    let li = document.createElement('li');
                    li.classList.add('suggest-item-model');

                    // <a>要素の生成と設定
                    let a = document.createElement('a');
                    a.classList.add('suggest-img-block');
                    a.href = `/goods/index.html?ggcd=${modelnumber.direct_group_id}`;

                    // <figure>要素の生成と設定
                    let figure = document.createElement('figure');
                    figure.style.margin = '0 15px 0 0';

                    // <img>要素の生成と設定
                    let img = document.createElement('img');
                    img.src = $('#goodsImageSrc').val() + `/${modelnumber.direct_group_id}/${modelnumber.direct_group_id}_01.jpg`;
                    img.onerror = function() {
                      this.src = $('#goodsImageSrc').val() + `/noimg.jpg`;
                      this.removeAttribute('onerror');
                    };
                    img.width = 50;
                    img.height = 50;

                    // <div>要素の生成
                    let div = document.createElement('div');

                    // 商品名の表示
                    let productName = document.createElement('p');
                    productName.style.fontSize = '14px';
                    productName.style.fontWeight = 700;
                    productName.style.marginBottom = '5px';
                    productName.textContent = modelnumber.direct_item_name;

                    // 価格情報の表示
                    let priceInfo = document.createElement('p');
                    if($('#cryptUserId').val() != ''){
                        if (modelnumber.direct_sale_min_price < modelnumber.direct_min_price || modelnumber.direct_sale_max_price < modelnumber.direct_max_price) {
                            if (modelnumber.direct_sale_min_price < modelnumber.direct_sale_max_price) {
                                priceInfo.innerHTML += `<span style="color:#ff0000;">セール：${modelnumber.direct_sale_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円～${modelnumber.direct_sale_max_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span><br />`;
                            } else {
                                priceInfo.innerHTML += `<span style="color:#ff0000;">セール：${modelnumber.direct_sale_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span><br />`;
                            }
                        }
                        if (modelnumber.direct_min_price < modelnumber.direct_max_price) {
                            priceInfo.innerHTML += `<span>通常価格：${modelnumber.direct_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円～${modelnumber.direct_max_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span>`;
                        } else {
                            priceInfo.innerHTML += `<span>通常価格：${modelnumber.direct_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span>`;
                        }
                    } else {
                        priceInfo.innerHTML += `<span style="font-weight:bold;">価格はログイン後表示</span>`;
                    }

                    // 要素の組み立て
                    figure.appendChild(img);
                    a.appendChild(figure);
                    div.appendChild(productName);
                    div.appendChild(priceInfo);
                    a.appendChild(div);
                    li.appendChild(a);
                    ul.appendChild(li);
                });
                suggestBlock.appendChild(ul);
            }
            if (suggestProductName.length) {
                let ul = document.createElement('ul');
                ul.classList.add('suggest-list','product');
                suggestProductName.forEach(productname => {
                    // <li>要素の生成
                    let li = document.createElement('li');
                    li.classList.add('suggest-item-product');

                    // <a>要素の生成と設定
                    let a = document.createElement('a');
                    a.classList.add('suggest-img-block');
                    a.href = `/goods/index.html?ggcd=${productname.direct_group_id}`;

                    // <figure>要素の生成と設定
                    let figure = document.createElement('figure');
                    figure.style.margin = '0 15px 0 0';

                    // <img>要素の生成と設定
                    let img = document.createElement('img');
                    img.src = $('#goodsImageSrc').val() + `/${productname.direct_group_id}/${productname.direct_group_id}_01.jpg`;
                    img.onerror = function() {
                      this.src = $('#goodsImageSrc').val() + `/noimg.jpg`;
                      this.removeAttribute('onerror');
                    };
                    img.width = 50;
                    img.height = 50;

                    // <div>要素の生成
                    let div = document.createElement('div');

                    // 商品名の表示
                    let productName = document.createElement('p');
                    productName.style.fontSize = '14px';
                    productName.style.fontWeight = 700;
                    productName.style.marginBottom = '5px';
                    productName.textContent = productname.direct_item_name;

                    // 価格情報の表示
                    let priceInfo = document.createElement('p');
                    if($('#cryptUserId').val() != ''){
                        if (productname.direct_sale_min_price < productname.direct_min_price || productname.direct_sale_max_price < productname.direct_max_price) {
                            if (productname.direct_sale_min_price < productname.direct_sale_max_price) {
                                priceInfo.innerHTML += `<span style="color:#ff0000;">セール：${productname.direct_sale_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円～${productname.direct_sale_max_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span><br />`;
                            } else {
                                priceInfo.innerHTML += `<span style="color:#ff0000;">セール：${productname.direct_sale_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span><br />`;
                            }
                        }
                        if (productname.direct_min_price < productname.direct_max_price) {
                            priceInfo.innerHTML += `<span>通常価格：${productname.direct_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円～${productname.direct_max_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span>`;
                        } else {
                            priceInfo.innerHTML += `<span>通常価格：${productname.direct_min_price.replace(/\B(?=(\d{3})+(?!\d))/g, ",")}円</span>`;
                        }
                    } else {
                        priceInfo.innerHTML += `<span style="font-weight:bold;">価格はログイン後表示</span>`;
                    }

                    // 要素の組み立て
                    figure.appendChild(img);
                    a.appendChild(figure);
                    div.appendChild(productName);
                    div.appendChild(priceInfo);
                    a.appendChild(div);
                    li.appendChild(a);
                    ul.appendChild(li);
                });
                suggestBlock.appendChild(ul);
            }
            if (suggestCampaign.length) {
                let ul = document.createElement('ul');
                ul.classList.add('suggest-list-campaign');
                const campaignPrefix = '<span style="margin-right:5px;"><img src="/images/search/document.png" class="suggest-icon"></span>';

                suggestCampaign.forEach(campaign => {
                    let li = document.createElement('li');
                    li.classList.add('suggest-item','campaign');
                    li.innerHTML = `
                    ${campaignPrefix}<a href="/${campaign.direct_campaign_url}">${campaign.direct_campaign_title}</a>`;
                    ul.appendChild(li);
                });
                suggestBlock.appendChild(ul);
            }
            suggestBlock.style.display = "block";
        } else {
            suggestBlock.style.display = "none";
        }
    }

    /**
     * 検索履歴メイン処理
     */
    $('#search').on('click tap', function () {
        initSuggestBlock();
        if ($(this).val() == '') {
            // キーワードなし場合のみ、履歴サジェストを実行する
            let deferredGoods = execHistoryGoods();
            $.when(deferredGoods).done(function () {
                outputHistory();
            });
            return;
        }
        // キーワードありの場合、そのほかのサジェストを実行する
        let deferredGoods = execSuggestGoods($(this).val());
        let deferredContents = execSuggestContents($(this).val());
        $.when(deferredGoods, deferredContents).done(function () {
            outputSuggest();
        });
    });

    /**
     * 検索履歴（商品）を取得する
     * @returns $.ajax()
     */
    function execHistoryGoods() {
        return $.ajax({
            type: 'GET',
            url: historyGoodsParams.URL,
            data: historyGoodsParams.data,
            dataType: 'jsonp',
            jsonp: 'cbk',
            async: true,
            cache: false,
            timeout: 3000
        }).done(
            function (data) {
                let status = data.responseHeader.status;
                let response = data.response;
                if (status != 0) {
                    return;
                }
                // 履歴サジェスト
                historyGoods = [];
                if (typeof (response.history) !== 'undefined') {
                    historyGoods = response.history.docs.item;
                }
            }
        );
    }

    /**
     * 取得した検索履歴結果を表示する
     */
    function outputHistory() {
        const suggestBlock = document.getElementById('suggestBlock');

        if (historyGoods.length) {
            suggestBlock.innerHTML = '';
            let ul = document.createElement('ul');
            ul.classList.add('suggest-list');
            const historyPrefix = '<span style="margin-right:5px;"><img src="/images/search/history.png" class="suggest-icon"></span>';
            historyGoods.forEach(history => {
                let li = document.createElement('li');
                li.classList.add("suggest-item");
                li.innerHTML = `
                ${historyPrefix}<a href="/search/index.html?keyword=${history.word}">${history.word}</a>
                <span onclick="deleteSuggestGoods('${history.word}'); return false;" class="suggest-delete">削除</span>`;
                ul.appendChild(li);
            });
            suggestBlock.appendChild(ul);
            suggestBlock.style.display = "block";
        } else {
            suggestBlock.style.display = "none";
        }

    }

    let modal = document.getElementById('gotop');
    if (modal) {
        modal.addEventListener('click', (event) => {
            if (event.target.closest('.suggest-list') === null) {
                let modal = document.getElementById('suggestBlock');
                if (modal) {
                    modal.style.display = "none";
                }
            }
        });
    }

});

/**
  * 履歴を削除する
  * @param {*} word 検索履歴
  */
function deleteSuggestGoods(word) {
    let deleteSuggestGoodsParams = {
        URL: $('#deleteHistoryGoodsSrc').val(),
        data: {
            q: word,
            wt: 'jsonp',
            loginid: ($('#cryptUserId').val() == '') ? undefined : $('#cryptUserId').val(),
            uid: (document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*')) == null) ? $('#accessUid').val() : document.cookie.match(new RegExp('__ukwlgck'+'\=([^\;]*)\;*'))[1].split('_')[0]
        }
    };

    $.ajax({
        type: 'GET',
        url: deleteSuggestGoodsParams.URL,
        data: deleteSuggestGoodsParams.data,
        dataType: 'jsonp',
        jsonp: 'cbk',
        async: true,
        cache: false,
        timeout: 3000
    }).done(
        function (data) {
            var status = data.message;
            if (status == "OK") {
                const suggestBlock = document.getElementById('suggestBlock');
                suggestBlock.style.display = "none";
            }
        }
    );
}

/**
 * サジェストブロックを初期化する
 */
function initSuggestBlock() {
    const suggestBlock = document.getElementById('suggestBlock');
    suggestBlock.innerHTML = '';
    suggestBlock.style.display = "none";
}
