/* Window Onload
-----------------------------------------------------------*/
$(function(){

	$.getJSON('/categorydatalistServlet?sl=1&el=2' , function(data) {
		var len = data.array.length;
		// console.log(data);
		var hchtml = '';
		var firstFlag = 0;
		
		for (var i = 0; i < len; i++) {
			// console.log("bb: " + i + " = " + data.array[i].categorylevel + data.array[i].categoryname);
			if (data.array[i].categorylevel == 1) {
				if (firstFlag == 1) {
					hchtml += '</ul></div></li>';
				}
				firstFlag = 1;
				hchtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="hcl1">' + data.array[i].categoryname + '</a><div class="hcl2"><ul>';
			}else if(data.array[i].categorylevel == 2) {
				hchtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a></li>';
			}else{
			
			}
		}
		
		hchtml += '</ul></div></li><li><a href="/cart/catalog.html" class="hcl1">クイックオーダー</a></li><!--<li><a href="/member/subscription/confirm.html" class="hcl1">定期便</a></li>-->';
		
		// 生成メニューの追加
		$(".hcMenuInner").prepend(hchtml);
		
		// 第2カテゴリが0の場合
		$(".hcl2").each(function(i, elem) {
			//console.log(i + ': ' + $(elem).text());
			if ($(elem).text() == "") {
				$(elem).remove();
			}
		});
		
		// メニュー展開処理
		$(".hcl1").hover(
			function () {
				$(".hcl1").next("div").hide();
				$(this).next("div").show();
			}
		);
		$(".hcl2").hover(
			function () {},
			function () {
				$(this).hide();
			}
		);

	});
});
