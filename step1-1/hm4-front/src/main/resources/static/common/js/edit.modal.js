var skipSubmit;
var stopSubmit;
var replaceSubmit;
var cardselectRadio;

$(function () {
    // 「1回休む」ダイアログの初期設定
    $("#skipMessage").dialog({
        title: false,
        bgiframe: true,
        autoOpen: false,
        dialogClass: 'TitleDialog',
        height: 'auto',
        width: 400,
        modal: true,
        draggable: true,
        closeText: '',
        close: false,
        resizable: false,
        position: ['center', 50]
    });

    // 「定期便をやめる」ダイアログの初期設定
    $("#stopMessage").dialog({
        title: false,
        bgiframe: true,
        autoOpen: false,
        dialogClass: 'TitleDialog',
        height: 'auto',
        width: 400,
        modal: true,
        draggable: true,
        closeText: '',
        close: false,
        resizable: false,
        position: ['center', 50]
    });

    // 「後継品に切り替える」ダイアログの初期設定
    $("#replaceMessage").dialog({
        title: false,
        bgiframe: true,
        autoOpen: false,
        dialogClass: 'TitleDialog',
        height: 'auto',
        width: 400,
        modal: true,
        draggable: true,
        closeText: '',
        close: false,
        resizable: false,
        position: ['center', 50]
    });

    // カード選択時ダイアログの初期設定
    $("#cardselectMessage").dialog({
        title: false,
        bgiframe: true,
        autoOpen: false,
        dialogClass: 'TitleDialog',
        height: 'auto',
        width: 400,
        modal: true,
        draggable: true,
        closeText: '',
        close: false,
        resizable: false,
        position: ['center', 50]
    });

    // 「1回休む」押下時、確認ダイアログを表示
    // teeda側でsuffixが付与されないため、複数idを考慮する必要あり
    $('[id^=skipConfirm]').click(function () {
        $('#skipMessage').dialog("open");
        skipSubmit = $(this).nextAll("input[id^='doOnceSkip']");
        return false;
    });
    $('#skipYes').click(function () {
        skipSubmit.click();
    });

    // 「定期便をやめる」押下時、確認ダイアログを表示
    // teeda側でsuffixが付与されないため、複数idを考慮する必要あり
    $('[id^=stopConfirm]').click(function () {
        $('#stopMessage').dialog("open");
        stopSubmit = $(this).nextAll("input[id^='doOnceStop']");
        return false;
    });
    $('#stopYes').click(function () {
        stopSubmit.click();
    });

    // 「後継品に切り替える」押下時、確認ダイアログを表示
    // teeda側でsuffixが付与されないため、複数idを考慮する必要あり
    $('[id^=replaceConfirm]').click(function () {
        $('#replaceMessage').dialog("open");
        replaceSubmit = $(this).nextAll("input[id^='doOnceReplace']");
        return false;
    });
    $('#replaceYes').click(function () {
        replaceSubmit.click();
    });

    var checked = $('input[name="indexForm:registCardSelect"]:checked');
    // カードラジオボタン押下時、確認ダイアログを表示
    $('[id^=registCardSelect]').click(function () {
        $('#cardselectMessage').dialog("open");
        cardselectRadio = $(this);
        cardselectRadio.prop('checked', false);
        return false;
    });
    $('#cardselectYes').click(function () {
        cardselectRadio.prop('checked', true);
        $('#doOnceCardSelect').click();
    });
    $('#cardselectNo').click(function () {
        checked.prop('checked', true);
    });

});