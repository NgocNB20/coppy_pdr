/* Window Onload
-----------------------------------------------------------*/
$(function(){

	$.getJSON('/categorydatalistServlet?sl=1&el=4' , function(data) {
		var smlen = data.array.length;
		// console.log(data);
		var smhtml = '';
		var sm1_Flag = 0;
		var sm2_Flag = 0;
		var sm3_Flag = 0;
		var sm4_Flag = 0;

		for (var i = 0; i < smlen; i++) {
			// console.log("bb: " + i + " = " + data.array[i].categorylevel + data.array[i].categoryname);
			if (data.array[i].categorylevel == 1) {
				if (sm4_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm3_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm2_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm1_Flag == 1) {
					smhtml += '</li>';
				}
				sm1_Flag = 1;
				sm2_Flag = 0;
				sm3_Flag = 0;
				sm4_Flag = 0;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="sml1" id="' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a>';
			}else if(data.array[i].categorylevel == 2) {
				if (sm4_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm3_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm2_Flag == 1) {
					smhtml += '</li>';
				} else {
					smhtml += '<ul>';
				}
				sm2_Flag = 1;
				sm3_Flag = 0;
				sm4_Flag = 0;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="sml2" id="' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a>';
			}else if(data.array[i].categorylevel == 3) {
				if (sm4_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm3_Flag == 1) {
					smhtml += '</li>';
				} else {
					smhtml += '<ul class="sml3">';
				}
				sm3_Flag = 1;
				sm4_Flag = 0;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" id="' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a>';
			}else if(data.array[i].categorylevel == 4) {
				if (sm4_Flag == 1) {
					smhtml += '</li>';
				} else {
					smhtml += '<ul class="sml4">';
				}
				sm4_Flag = 1;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" id="' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a>';
			}else{

			}
		}

		if (sm4_Flag == 1) {
			smhtml += '</li></ul></li></ul></li></ul></li>';
		}else if(sm3_Flag == 1){
			smhtml += '</li></ul></li></ul></li>';
		}else if(sm2_Flag == 1){
			smhtml += '</li></ul></li>';
		}else if(sm1_Flag == 1){
			smhtml += '</li>';
		}

		// 生成メニューの追加
		$(".categoryListInner").prepend(smhtml);
		// ホバー外れた場合に残る.sml3をhide
		$(".categoryListInner").hover(
			function(){},
			function(){$(".sml3").hide();}
		);
		// メニュー展開処理
		$(".sml2").hover(
			function () {
				$(".sml2").next(".sml3").hide();
				$(this).next(".sml3").show();
			}
		);
		$(".sml3").hover(
			function () {},
			function () {
				$(this).hide();
			}
		);
		
		// カテゴリメニュー展開処理
		var arg = new Object;
		var pair=location.search.substring(1).split('&');
		for(var i=0;pair[i];i++) {
			var kv = pair[i].split('=');
			arg[kv[0]]=kv[1];
		}

		if (arg.cid != null) {
			
			var ulFlag = $("#"+arg.cid).attr("class");
			
			if ( ulFlag == 'sml1' ) {
				$("#"+arg.cid).next("ul").show();
			} else if ( ulFlag == 'sml2' ) {
				$("#"+arg.cid).parent("li").parent("ul").show();
			} else {
				$("#"+arg.cid).parent("li").parent("ul").parent("li").parent("ul").show();
			}
			
		}
		

	});

});
