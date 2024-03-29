// 2023-renew No14 from here
// 行追加
function doAddNewLine() {
    let rowIsVisibleList = $("#reserveTableInfo tr.display-none")
    if (!rowIsVisibleList || rowIsVisibleList.size() === 0) {
        alert("お届け日は最大50行までにしてください。")
    }
    let firstRowIsVisible = $("#reserveTableInfo tr.display-none").first();
    firstRowIsVisible.removeClass("display-none")
}

// 入力内容削除
function clearInputReserve(index) {

    let date = $("#inputReserveDeliveryDate-" + index);
    let count = $("#inputGoodsCount-" + index);
    let notice = $("#noticeHoliday-" + index);

    date.val('')
    count.val('1')
    notice.addClass('display-none');

}

// 数量追加
function increaseGcnt(idInput) {
    let counterInput = document.getElementById(idInput);
    let value = parseInt(counterInput.value);

    if (!isNaN(counterInput.value) && Number.isInteger(value) && value >= 0 && value < 999) {
        let currentValue = parseInt(counterInput.value);
        counterInput.value = currentValue + 1;
    }
}

// 数量削減
function decreaseGcnt(idInput) {
    let counterInput = document.getElementById(idInput);
    let value = parseInt(counterInput.value);

    if (!isNaN(counterInput.value) && Number.isInteger(value) && value > 1) {
        let currentValue = parseInt(counterInput.value);
        counterInput.value = currentValue - 1;
    }
}

// 2023-renew No14 to here
