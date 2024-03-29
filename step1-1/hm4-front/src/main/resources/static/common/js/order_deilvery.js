$(function(){
    var codeArray = {};
	var timeZoneYamato = new Array();
	var timeZoneJapanPost = new Array();

	var specification = false;
	// API連携されたお届け日毎のお届け時間帯コードを配列に設定
	if ($("#receiverTimeZoneCodes").length > 0) {
		var codeStr = $("#receiverTimeZoneCodes").text();
		if (codeStr.length > 0) {
			// 元データ形式　yyyyMMdd:1|2_yyyyMMdd:1|2|3
			var receives = codeStr.split("_");
			for (i = 0; i < receives.length; i++) {
				var receive = receives[i].split(":");
				codeArray[receive[0]] = receive[1].split("|");
				specification = true;
			}
		}
	}
	// 配送がヤマト／自動設定の場合
	if (specification && $("#receiverTimeZoneYamato").length > 0) {
		initReceiverTimeZone("#receiverTimeZoneYamato", timeZoneYamato);
	}
	// 配送が日本郵政の場合
	if (specification && $("#receiverTimeZoneJapanPost").length > 0) {
		initReceiverTimeZone("#receiverTimeZoneJapanPost", timeZoneJapanPost);
	}
	
	function initReceiverTimeZone(receiverTimeZone, timeZoneArray) {
		// プルダウンの内容を退避
		$(receiverTimeZone).children("option").each(function() {
			timeZoneArray.push([$(this).prop("value"), $(this).text()]);
		});

		$(document).on("change","#deliveryDate", function() {
			// お届け日のchangeイベント発火時に、お届け時間帯プルダウンを再設定
			chageReceiverTimeZone(receiverTimeZone, timeZoneArray, null)
		});
		// お届け時間帯プルダウンを設定（初期表示）
		chageReceiverTimeZone(receiverTimeZone, timeZoneArray, $(receiverTimeZone).prop("value"));
	}

	/*
	 * お届け日に紐づくお届け時間帯をプルダウンに設定する。
	 * @param displayPullDown 画面表示用お届け時間帯プルダウン
	 * @param companyTimeZoneArray 配送会社のお届け時間帯配列
	 * @param selectValue 初期選択値
	 */
	function chageReceiverTimeZone(displayPullDown, companyTimeZoneArray, selectValue) {
		// お届け日を取得
		var selectDeliverDate = $("#deliveryDate").prop("value");
		if (selectDeliverDate == null) {
			// 未選択の場合、先頭行を取得
			selectDeliverDate = $("#deliveryDate").children("option")[0].value;
		}

		// お届け日に紐づくお届け時間帯を取得
		var timeZoneCodes = codeArray[selectDeliverDate];

		// お届け時間帯プルダウンをクリア
		$(displayPullDown).children().remove();

		var i = 0;
		$.each(companyTimeZoneArray, function(index, elemet) {
			if (timeZoneCodes != null && $.inArray(elemet[0], timeZoneCodes) > -1) {
				// お届け時間帯プルダウンの再設定
				$(displayPullDown).append($("<option>").attr({ value: elemet[0]}).text(elemet[1]));
				
				// 初期選択
				if (selectValue != null && selectValue == elemet[0]) {
					$(displayPullDown).val(selectValue);
				}
				i++;
			}
		});
		if (i == 0) {
			$(displayPullDown).append($("<option>").attr({ value: ""}).text(""));
		}
	}

    // お届け希望日設定
    $('[id^="deliveryDateLabel"]').each(function (){
        $(this).text($('#deliveryDate option:selected').text());
    });

    // お届け予定日チェンジイベント
    $('#deliveryDate').change(function () {
        $('[id^="deliveryDateLabel"]').each(function (){
            $(this).text($('#deliveryDate option:selected').text());
        });
    });

});

