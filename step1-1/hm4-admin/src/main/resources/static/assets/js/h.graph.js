/**
 * グラフ表示
 */
(function($){

	/**
	 * 円グラフ
	 */
	$.fn.pieGraphRenderer = function(){
		var targetId = $(this).attr("id");
		var targetData = $(this).html();
		$(this).html("");
		targetData = $.parseJSON(targetData);

		var graphData = [];
		var isExistData = false;
		$.each(targetData, function(){
			if (this.graphDataLabel.length > 30) {
				// 凡例の文字が長いと崩れる為、30文字でカットする
				this.graphDataLabel = this.graphDataLabel.substr(0, 30) + '…';
			}
			graphData.push([this.graphDataLabel, this.graphDataValue]);
			if (this.graphDataValue > 0) {
				isExistData = true;
			}
		});

		var title = '';
		if (!isExistData) {
			// グラフのデータが全て0の場合、メッセージを出力する、
			var noGraphMsg = $(this).attr("nographmsg");
			title = '<div style="color: #ff0000; font-weight: bold;">【' + noGraphMsg + '】</div>'
		}

		// 凡例の数だけ、グラフの描画エリアの高さを広げる
		$(this).height($(this).height() + graphData.length * 22);

		$.jqplot(targetId, [graphData], {
			title: {
				text: title
			},
			seriesDefaults: {
				renderer: $.jqplot.PieRenderer,
				rendererOptions: {
					padding: 10,
					sliceMargin: 4,
					dataLabels: 'value',
					showDataLabels: true,
					startAngle: -90,
					dataLabelFormatString: "%'d",
					highlightMouseOver: true,
					highlighter: true
				}
			},
			legend: {
				show: true,
				location: 's'
			},
			highlighter: {
				show: true,
				useAxesFormatters: false,
				formatString: "%s<br />%'d"
			},
		});
	};

})(jQuery);

$(function(){
	$(".pieGraph").each(function(){
		$(this).pieGraphRenderer();
	});
});