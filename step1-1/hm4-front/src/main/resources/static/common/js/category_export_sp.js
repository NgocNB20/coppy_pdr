/* Window Onload
-----------------------------------------------------------*/
$(function(){

	$.getJSON('/categorydatalistServlet?sl=1&el=3' , function(data) {
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
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="sml1">' + data.array[i].categoryname + '</a>';
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
					smhtml += '<ul class="opm">';
				}
				sm2_Flag = 1;
				sm3_Flag = 0;
				sm4_Flag = 0;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="sml2">' + data.array[i].categoryname + '</a>';
			}else if(data.array[i].categorylevel == 3) {
				if (sm4_Flag == 1) {
					smhtml += '</li></ul>';
				}
				if (sm3_Flag == 1) {
					smhtml += '</li>';
				} else {
					smhtml += '<ul class="opm">';
				}
				sm3_Flag = 1;
				sm4_Flag = 0;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '" class="sml3">' + data.array[i].categoryname + '</a>';
			}else if(data.array[i].categorylevel == 4) {
				if (sm4_Flag == 1) {
					smhtml += '</li>';
				} else {
					smhtml += '<ul class="opm sml4">';
				}
				sm4_Flag = 1;
				smhtml += '<li><a href="/goods/list.html?cid=' + data.array[i].categoryid + '">' + data.array[i].categoryname + '</a>';
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

		// �������j���[�̒ǉ�
		$(".categoryListInner").prepend(smhtml);

		// �W�J�ł���悤�Ƀ\�[�X�ύX
		$('.opm').prev('a').replaceWith(function() {
			$(this).replaceWith('<span class="'+$(this).attr('class')+'">'+$(this).text()+'</span>')
		});

		// ���j���[�W�J����
		$("span.sml1").click(
			function () {
				if($(this).hasClass('open')) {
					$(this).removeClass('open');
					$(this).next('ul').hide();
				} else {
					$(this).addClass('open');
					$(this).next('ul').show();
				}
			}
		);
		$("span.sml2").click(
			function () {
				if($(this).hasClass('open')) {
					$(this).removeClass('open');
					$(this).next('ul').hide();
				} else {
					$(this).addClass('open');
					$(this).next('ul').show();
				}
			}
		);
		$("span.sml3").click(
			function () {
				if($(this).hasClass('open')) {
					$(this).removeClass('open');
					$(this).next('ul').hide();
				} else {
					$(this).addClass('open');
					$(this).next('ul').show();
				}
			}
		);

	});

});
