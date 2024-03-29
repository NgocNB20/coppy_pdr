// 検索画面からプレビュー画面を開く
function openPreviewPcBySearch(self) {
    return preview($(self).children("a[id^='goPreview']").attr("href"), 0.9);
}

// 登録・更新画面からプレビュー画面を開く
function openPreviewByConfirm() {
    return preview('/admin/freearea/registupdate/preview/?previewScreen=confirm', 0.9);
}

// PCプレビュー画面オープン
function preview(url, width, height) {
    if (width == null) {
        width = window.screen.availWidth * 0.8;
    } else {
        width = window.screen.availWidth * width;
    }

    if (height == null) {
        height = window.screen.availHeight * 0.8;
    } else {
        height = window.screen.availHeight * height;
    }

    if (url.indexOf("?") != -1) {
        url = url + "&newwindow=true";
    } else {
        url = url + "?newwindow=true";
    }

    // 
    var subleft = (window.screen.availWidth - width) / 2;
    var subtop = (window.screen.availHeight - height) / 2;

    window.open(url, "_blank", "width=" + width + ", height=" + height + ", resizable=yes, scrollbars=yes, top=" + subtop + ", left=" + subleft);

    return false;
}
