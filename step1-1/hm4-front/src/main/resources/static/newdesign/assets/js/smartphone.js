function SwitchPcView(){
        var cookiepath = pkg_common.getAppComplementUrl() + "/";
        document.cookie = "smart_view=false;path=" + cookiepath + ";max-age=7776000";
        document.location.href= document.location.href.replace(/#.*$/, "");
        return false;
}

function SwitchSmartView(){
    var cookiepath = pkg_common.getAppComplementUrl() + "/";
        document.cookie = "smart_view=;path=" + cookiepath + ";max-age=0";
        document.location.href = document.location.href.replace(/#.*$/, "");
        return false;
}

<!-- Script for goods information -->
function toggleInfo(obj) {
	var ddNode = obj.parentNode.childNodes[1];
	var dtNode = obj.parentNode.childNodes[0];
	if (ddNode.style.display == "block") {
		dtNode.className = "note-closed";
		ddNode.style.display = "none";
	} else {
		dtNode.className = "note-opened";
		ddNode.style.display = "block";
	}
}

<!-- Script for receiverList -->
function toggleInfoReceiver(obj) {
	var buttonNode = obj.parentNode.childNodes[0];
	if ($("#receiverArea").css('display') == 'block') {
		buttonNode.value = "開く";
		$("#receiverArea").css('display','none'); 
	} else {
		buttonNode.value = "閉じる";
		$("#receiverArea").css('display','block'); 
	}
}

<!-- iPhone用 iOS3,4,5 ラベルクリック -->
$(function(){
	$('label').click(function(){
	});

	/* hmenu */
	$(".hdMenu > p").click(
		function () {
			if($(this).hasClass('open')) {
				$(this).removeClass('open');
				$(this).next('.hmenu').hide();
				$('.hmenuInner').slideUp();
			} else {
				$(this).addClass('open');
				$(this).next('.hmenu').show();
				$('.hmenuInner').slideDown();
			}
		}
	);
	$(".hmClose a").click(
		function () {
			$('.hdMenu > p').removeClass('open');
			$('.hmenu').hide();
			$('.hmenuInner').slideUp();
			return false;
		}
	);

	/* hmenu */
	$(".menu01 a").click(
		function () {
			if($(this).hasClass('open')) {
				$(this).removeClass('open');
				$('.hcCategory').hide();
			} else {
				$(this).addClass('open');
				$('.hcCategory').show();
			}
			return false;
		}
	);

	/* !pageTop
	---------------------------------------------------------- */
	$(".pagetop").hide();
	$(window).scroll(function () {
		if ($(this).scrollTop() > 100) {
			$('.pagetop').slideDown("fast");
		} else {
			$('.pagetop').slideUp("fast");
		}
	});

});

