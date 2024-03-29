
// PCプレビュー画面オープン
function openPreviewPc(self) {
    return preview($(self).children("a[id^='goPreview']").attr("href"), 0.9);
}

// 登録・更新画面からプレビュー画面を開く
function openPreviewNewByConfirm() {
    return preview('/news/registupdate/preview/?fromView=confirm', 0.9);
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

    // ディスプレイの中央に表示
    var subleft = (window.screen.availWidth - width) / 2;
    var subtop = (window.screen.availHeight - height) / 2;

    window.open(pkg_common.getComplementedAppPath(url), "_blank", "width=" + width + ", height=" + height + ", resizable=yes, scrollbars=yes, top=" + subtop + ", left=" + subleft);

    return false;
}