/* -----------------------------------------
  datepicker
------------------------------------------*/

$(function () {

  let possibleReserveFromDay = $("#possibleReserveFromDay").val();
  let possibleReserveToDay = $("#possibleReserveToDay").val()
  let calendarNotSelectDateList = $("#calendarNotSelectDateList").val()

  let off = []
  if (calendarNotSelectDateList) {
    // [yyyy/MM/dd, yyyy/MM/dd, yyyy/MM/dd, ・・・]型で格納されているので、その前提で処理
    let editCalendarNotSelectDateList = calendarNotSelectDateList.replace("[", "").replace("]", "");
    if (editCalendarNotSelectDateList) {
      off = editCalendarNotSelectDateList.split(',').map(date => {
        let dateTrim = date.trim();
        return dateTrim.slice(0, 4) + dateTrim.slice(5, 7) + dateTrim.slice(8, 10);
      });
    }
  }

  $(document).ready(function ($) {
    $(".js-datepicker").datepicker({
      dateFormat: "yy/mm/dd",
      minDate: new Date(possibleReserveFromDay),
      maxDate: new Date(possibleReserveToDay),
      beforeShowDay: function (date) {
        //日曜(0)または土曜(6)または祝日のとき
        if (off.indexOf(formatDay(date)) !== -1) {
          return [false, "ui-state-disabled"];
        }
        //祝日かどうか
        let holiday = JapaneseHolidays.isHoliday(date);
        if (date.getDay() === 0) {
          // 日曜日の場合
          return [true, "custom-calendar-sunday", ""];
        } else if (date.getDay() === 6) {
          // 土曜日の場合
          return [true, "custom-calendar-saturday", ""];
        } else if (holiday) {
          return [true, "custom-calendar-holiday", ""];
        }
        // 平日の場合
        return [true, "custom-calendar-weekday", ""];
      },
      changeMonth: true,
      changeYear: true,
      showOtherMonths: true,
      selectOtherMonths: true,

      // 追加
      showButtonPanel: true,
      gotoCurrent: true
    });
    $.datepicker.regional['ja'] = {
      currentText: '今日',
      closeText: '閉じる',
      monthNames: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
      monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
      dayNames: ['日曜日', '月曜日', '火曜日', '水曜日', '木曜日', '金曜日', '土曜日'],
      dayNamesShort: ['日', '月', '火', '水', '木', '金', '土'],
      dayNamesMin: ['日', '月', '火', '水', '木', '金', '土'],
      weekHeader: '週',
      dateFormat: 'yy/mm/dd',
      firstDay: 0,
      isRTL: false,
      showMonthAfterYear: true,
      yearSuffix: '年'
    };
    $.datepicker.setDefaults($.datepicker.regional['ja']);

    //テキスト編集した際のアラート
    $(".js-datepicker").on("change", function () {
      //内容を取得
      let val = $(this).val();
      //整形
      let date = new Date(val);
      let idSplit = $(this).attr("id").split('-');
      let indexNotice = idSplit[1]
      let noticeDiv = $("#noticeHoliday-" + indexNotice)

      if (!isValidDateFormat(val)) {
        noticeDiv.addClass("display-none");
      } else {
        //祝日かどうか
        let holiday = JapaneseHolidays.isHoliday(date);
        if (holiday) {
          noticeDiv.removeClass("display-none");
        } else {
          noticeDiv.addClass("display-none");
        }
      }
    });
  });

  $.datepicker._gotoToday = function (id) {
    $(id).datepicker('setDate', new Date()).datepicker('hide').blur().change();
  };

  //MMDDで返す関数定義
  function formatDay(dt) {
    let y = ('0' + (dt.getFullYear())).slice(-4);
    let m = ('0' + (dt.getMonth() + 1)).slice(-2);
    let d = ('0' + dt.getDate()).slice(-2);
    return (y + m + d);
  }

  // yyyy/MM/dd
  function isValidDateFormat(dateString) {
    var regex = /^\d{4}\/\d{2}\/\d{2}$/;
    return regex.test(dateString);
  }
});
