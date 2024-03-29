// 2023-renew No21 from here
var tokenTogetherBuy = $('#_csrf').attr('content');
var headerTogetherBuy = $('#_csrf_header').attr('content');
// ダイアログ表示
$(function () {
    $("#GoodsTogetherBuyGroup_item_dialog_btn").click(function () {
        $("#GoodsTogetherBuyGroup_item_dialog").dialog({
            modal: true,
            width: 1200,
            title: "よく一緒に購入されている商品の追加",
            buttons: [],
            close: function (event, ui) {
                resetFormForTogetherBuy();
            }
        });
        $("#doSearchGoodsTogetherBuyGroup").click(function () {
            doGoodsTogetherBuyGroupSearch();
        })
        doAddGoodsTogetherBuyGroupAjax();
    });
});

// 商品変更画面でよく一緒に購入されている商品追加イベント
function doAddGoodsTogetherBuyGroupAjax() {
    var form = $('form')[0]
    var formAddgoods = new FormData(form)
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/goods/registupdate/doAddGoodsTogetherBuyGroupAjax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(headerTogetherBuy, tokenTogetherBuy);
        },
        processData: false,
        contentType: false,
        dataType: "json",
        data: formAddgoods,
        timeout: 30000

    })
        .done(function (data) {
            getGoodsRegistUpdateTogetherBuyGroupSearchModel(data);
        })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                location.href = pkg_common.getAppComplementUrl() + '/login/'
            }
        })
}

// よく一緒に購入されている商品追加ダイアログ表示
function getGoodsRegistUpdateTogetherBuyGroupSearchModel(data) {
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/goods/registupdate/goodstogetherbuygroupsearch/ajax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(headerTogetherBuy, tokenTogetherBuy);
        },
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),
        timeout: 30000

    }).done(function (data) {
        setListCategoryForTogetherBuy(data);
    }).fail(function (data) {
        if (data && data.status && data.status === 403) {
            location.href = pkg_common.getAppComplementUrl() + '/login/'
        }
    })
}

// よく一緒に購入されている商品ダイアログにて検索する
function doGoodsTogetherBuyGroupSearch() {
    resetFormForTogetherBuy();
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/goods/registupdate/goodstogetherbuygroupsearch/post/ajax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(headerTogetherBuy, tokenTogetherBuy);
        },
        processData: false,
        contentType: false,
        dataType: "json",
        data: getConditionSearch(),
        timeout: 30000
    }).done(function (data) {
        setResultDataGoodsTogetherBuyGroupSearch(data);
    })
        .fail(function (data) {
            if (data.responseJSON && data.responseJSON.length > 0) {
                bindingErrorMessageForBuyTogether(data.responseJSON)
            }
        })
}

// よく一緒に購入されている商品追加し、商品変更画面リロード
function doBindingReloadToIndexForTogetherBuy(data) {
    let resForm = {};
    resForm.redirectGoodsTogetherBuyGroupEntityList = data.redirectGoodsTogetherBuyGroupEntityList
    resForm.tmpGoodsTogetherBuyGroupEntityList = data.tmpGoodsTogetherBuyGroupEntityList
    resForm.goodsGroupDto = data.goodsGroupDto
    resForm.GoodsTogetherBuyGroupEntityList = data.GoodsTogetherBuyGroupEntityList
    $.ajax({
        type: "POST",
        url: pkg_common.getAppComplementUrl() + "/goods/registupdate/loadGoodsTogetherBuyGroupAjax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(headerTogetherBuy, tokenTogetherBuy);
        },
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(resForm),
        timeout: 30000
    })
        .done(function (data) {
            location.href = pkg_common.getAppComplementUrl() + "/goods/registupdate?scroll=goodsTogetherBuyGroup"
        })
        .fail(function (data) {
            if (data && data.status && data.status === 403) {
                location.href = pkg_common.getAppComplementUrl() + '/login/'
            }
            if (data.responseJSON && data.responseJSON.length > 0) {
                bindingErrorMessageForBuyTogether(data.responseJSON)
            }
        })
}

// フォームリセット
function resetFormForTogetherBuy() {
    $("#result_search_GoodsTogetherBuyGroup").html('');
    $("#globalMesTogetherBuyDiv").html('');
    $("#globalMesTogetherBuyDiv").attr('style', 'display:none');
    $("#searchGoodsTogetherBuyGroupCodeErr").attr('style', 'display:none');
    $("#searchGoodsTogetherBuyGroupNameErr").attr('style', 'display:none');
    $("#goodsTogetherBuySearchCategoryIdErr").attr('style', 'display:none');
    $("#searchGoodsTogetherBuyGroupCodeErr").html('');
    $("#searchGoodsTogetherBuyGroupNameErr").html('');
    $("#goodsTogetherBuySearchCategoryIdErr").html('');
    $("#searchGoodsTogetherBuyGroupCode").removeClass("error")
    $("#searchGoodsTogetherBuyGroupName").removeClass("error")
    $("#goodsTogetherBuySearchCategory").removeClass("error")
    formCheckboxTogetherBuy = new FormData();
    $(".ui-dialog-buttonpane").remove();
}

// ダイアログの項目にフォーム値から設定
function getConditionSearch() {
    let form = new FormData();
    form.append("searchGoodsTogetherBuyGroupCode", $("#searchGoodsTogetherBuyGroupCode").val())
    form.append("searchGoodsTogetherBuyGroupName", $("#searchGoodsTogetherBuyGroupName").val())
    form.append("searchCategoryId", $("#goodsTogetherBuySearchCategory").val())
    return form
}

let formCheckboxTogetherBuy = new FormData();

// チェックボックス処理
function onchangeCheckboxForTogetherBuy(name, e) {
    let value = $(e).is(":checked")
    if (value) {
        formCheckboxTogetherBuy.append(name, value)
    } else {
        formCheckboxTogetherBuy.set(name, value)
    }
}

// カテゴリーを設定
function setListCategoryForTogetherBuy(data) {
    $("#goodsTogetherBuySearchCategory").html('')
    for (const [key, value] of Object.entries(data.searchCategoryIdItems)) {
        let optionVal = '<option value="' + key + '">' + value + '</option>'
        $("#goodsTogetherBuySearchCategory").append(optionVal)
    }
}

// 検索結果を設定
function setResultDataGoodsTogetherBuyGroupSearch(data) {
    if (typeof data == 'undefined' || data.length == 0) {
        let noResultItem = '<div class="l-inner_wrap" >\n' +
            '                   <div class="col wrap_flex">\n' +
            '                       <h4 class="c-pagetitle_h4 mr10">検索結果</h4>\n' +
            '                       <span class="as_end">全0件が該当しました</span>\n' +
            '                  </div>\n' +
            '               </div> '
        $("#result_search_GoodsTogetherBuyGroup").html('');
        $("#result_search_GoodsTogetherBuyGroup").append(noResultItem);
    }
    if (typeof data !== 'undefined' && data.length > 0) {
        const iconSort = '<span class="ascd" style="display: none">&nbsp;▲</span>' + '<span class="desc" style="display: none">&nbsp;▼</span>' + '</th>\n'
        let tableResultItems = ' <table id="GoodsTogetherBuyGroup_result" class="c-tbl large60 tbl_GoodsTogetherBuyGroup_item">\n' +
            '    <thead>\n' +
            '    <tr>\n' +
            '        <th class="check w60px"></th>\n' +
            '        <th class="w155px">商品管理番号' + iconSort +
            '        <th>商品名' + iconSort +
            '        <th class="w240px">公開状態' + iconSort +
            '    </tr>\n' +
            '    </thead>\n' +
            '    <tbody id="items_data_row">\n' +
            '    \n' +
            '    </tbody>\n' +
            '</table> '
        $("#result_search_GoodsTogetherBuyGroup").html('');
        $("#result_search_GoodsTogetherBuyGroup").append(tableResultItems);
        data.forEach((val, index) => {
            let groupCode = val.goodsGroupCode ? val.goodsGroupCode : ''
            let goodName = val.goodsGroupNameAdmin ? val.goodsGroupNameAdmin : ''
            let goodsStatusPC = ''
            if (val.goodsOpenStatusPC) {
                if (val.goodsOpenStatusPC === 'OPEN') {
                    goodsStatusPC = '公開中'
                } else if (val.goodsOpenStatusPC === 'NO_OPEN') {
                    goodsStatusPC = '非公開'
                } else if (val.goodsOpenStatusPC === 'DELETED') {
                    goodsStatusPC = '削除'
                }
            }
            let trData = '<tr>' +
                '<td class="check">\n' +
                '     <label class="c-form-control"><input onchange="onchangeCheckboxForTogetherBuy(name,this)" name="resultItems[' + index + '].resultCheck" value="true" type="checkbox"><i></i></label>\n' +
                '</td> ' +
                '<td class="w155px alphabetic">' + groupCode + '</td>' +
                '<td>' + goodName + '</td>' +
                '<td class="w240px">' + goodsStatusPC + '</td>' + '' +
                '</tr>'
            $("#items_data_row").append(trData)
        })
        //4
        $("#GoodsTogetherBuyGroup_item_dialog").dialog({
            buttons: [{
                text: "追加する",
                click: function () {
                    $.ajax({
                        type: "POST",
                        url: pkg_common.getAppComplementUrl() + "/goods/registupdate/goodstogetherbuygroupsearch/doSelectGoodsTogetherBuyGroupAjax",
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(headerTogetherBuy, tokenTogetherBuy);
                        },
                        processData: false,
                        contentType: false,
                        dataType: "json",
                        data: formCheckboxTogetherBuy,
                        timeout: 30000
                    })
                        .done(function (data) {
                            doBindingReloadToIndexForTogetherBuy(data)
                            $("#GoodsTogetherBuyGroup_item_dialog").dialog("close");
                        })
                        .fail(function (data) {
                            if (data && data.status && data.status === 403) {
                                location.href = pkg_common.getAppComplementUrl() + '/login/'
                            }
                            if (data.responseJSON && data.responseJSON.length > 0) {
                                bindingErrorMessageForBuyTogether(data.responseJSON)
                            }

                        })
                    formCheckboxTogetherBuy = new FormData()
                }
            }
            ]
        });
        $(document).ready(function () {
            $('#GoodsTogetherBuyGroup_result').DataTable({
                paging: false,
                searching: false,
                info: false,
                order: [[1, "asc"]],
                columnDefs: [{orderable: false, targets: 0}],
            });
        });
    }
}

// バリデータメッセージ処理
function bindingErrorMessageForBuyTogether(error) {
    $("#globalMesTogetherBuyDiv").html('')
    $("#searchGoodsTogetherBuyGroupCodeErr").html('')
    $("#searchGoodsTogetherBuyGroupNameErr").html('')
    $("#goodsTogetherBuySearchCategoryIdErr").html('')
    if (error && error.length > 0) {
        $("#globalMesTogetherBuyDiv").append("<ul></ul>")
        $("#searchGoodsTogetherBuyGroupCodeErr").append("<div id='searchGoodsTogetherBuyGroupCodeError'></div>")
        $("#searchGoodsTogetherBuyGroupCodeErr div").append("<ul></ul>")
        $("#searchGoodsTogetherBuyGroupNameErr").append("<div id='searchGoodsTogetherBuyGroupNameError'></div>")
        $("#searchGoodsTogetherBuyGroupNameErr div").append("<ul></ul>")
        $("#goodsTogetherBuySearchCategoryIdErr").append("<div id='goodsTogetherBuySearchCategoryIdError'></div>")
        $("#goodsTogetherBuySearchCategoryIdErr div").append("<ul></ul>")
        error.forEach(e => {
            if (e.field == 'globalMessage') {
                let itemErr = '<li>' + e.message + '</li>'
                $("#globalMesTogetherBuyDiv ul").append(itemErr);
                if ($("#globalMesTogetherBuyDiv ul li").length) {
                    $("#globalMesTogetherBuyDiv").attr("style", "display:block")
                }
            }
            if (e.field == 'searchGoodsTogetherBuyGroupCode') {
                let itemErr = '<li>' + e.message + '</li>'
                $("#searchGoodsTogetherBuyGroupCodeErr ul").append(itemErr);
                if ($("#searchGoodsTogetherBuyGroupCodeErr ul li").length) {
                    $("#searchGoodsTogetherBuyGroupCodeErr").attr("style", "display:block")
                }
                $("#searchGoodsTogetherBuyGroupCode").addClass("error")
            }
            if (e.field == "searchGoodsTogetherBuyGroupName") {
                let itemErr = '<li>' + e.message + '</li>'
                $("#searchGoodsTogetherBuyGroupNameErr ul").append(itemErr);
                $("#searchGoodsTogetherBuyGroupNameErr ul li").length ? $("#searchGoodsTogetherBuyGroupNameErr").attr("style", "display:block") : ''
                $("#searchGoodsTogetherBuyGroupName").addClass("error")
            }
            if (e.field == "searchCategoryId") {
                let itemErr = '<li>' + e.message + '</li>'
                $("#goodsTogetherBuySearchCategoryIdErr ul").append(itemErr);
                $("#goodsTogetherBuySearchCategoryIdErr ul li").length ? $("#goodsTogetherBuySearchCategoryIdErr").attr("style", "display:block") : ''
                $("#goodsTogetherBuySearchCategory").addClass("error")
            }
        })
    }
}
// 2023-renew No21 to here
