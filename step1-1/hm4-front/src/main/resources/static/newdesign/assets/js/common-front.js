$(window).on('load', function () {

  /* -----------------------------------------
  追従ヘッダー
  ------------------------------------------*/
  let headerCommon = $('.l-header__common').outerHeight();
  let freeAreaHeader1 = $('.free-area-header1').outerHeight();

  $(window).scroll(function () {
    if (!$('.free-area-header1').length) {

      if ($('html').hasClass('is-scroll')) {
        $('.l-header__common').css({'position':'fixed','box-shadow':'0 10px 25px 0 rgba(0, 0, 0, .5)'});
        $('.l-header__member').css('padding-top', headerCommon);
      } else {
        $('.l-header__common').css({'position':'relative','box-shadow':'none'});
        $('.l-header__member').css('padding-top', '0');
      }

    } else {

      let headerScroll = $(window).scrollTop();

      if (headerScroll > freeAreaHeader1) {
        $('.l-header__common').css({'position':'fixed','box-shadow':'0 3px 3px 0 rgba(0, 0, 0, .1)'});
        $('.l-header__member').css('padding-top', headerCommon);
        // $('.l-header__member').css('padding-top', '134px');
      } else {
        $('.l-header__common').css({'position':'relative','box-shadow':'none'});
        $('.l-header__member').css('padding-top', '0');
      }
    }
  });
});



$(function () {

  const headerCommon = $('.l-header__common').outerHeight();

  /* -----------------------------------------
  アコーディオン(categoryjson以外)
  ------------------------------------------*/
  $('.js-accordion-arrow').on('click', function () {
    $(this).next().slideToggle(300);
    $(this).toggleClass('is-opened', 300);
  });

  // TOPのみお知らせを開いた状態に
  $('.p-top .l-header__member-information dd').css('display', 'block');
  $('.p-top .l-header__member-information dt').addClass('is-defaultOpen is-opened');
  $('.p-top .l-header__member-information dt').on('click', function () { 
    $(this).removeClass('is-defaultOpen');
  })

  /* -----------------------------------------
  もっと見る
  ------------------------------------------*/
  // p-top__banner用
  $('.js-limit').each(function () {
    let limitNum = $(this).data('limit');
    let target = $(this).find('.swiper-wrapper .swiper-slide');
    let moreButton = $(this).siblings('.p-top__banner-more').children('.js-more');

    if (window.innerWidth < 600) {
      $(target).slice(limitNum).hide();
    }

    $(moreButton).on('click', function () {
      $(this).parents('.p-top__banner').find('.swiper-slide').slice(limitNum).slideDown('fast');
      $(this).parent('.p-top__banner-more').hide();
    })

  });

  // p-top__news用 または その他
  $('.js-limit').each(function () {
    let limitNum = $(this).data('limit');
    let target = $(this).find('.js-limit-unit');
    let moreButton = $(this).siblings('.js-more-wrap').children('.js-more');

    $(target).slice(limitNum).hide();

    if ($(moreButton).attr('custom-js-more')) {
      if (target.length <= limitNum) {
        moreButton.parent('.js-more-wrap').hide();
      }
    } else {
      if (target.length < limitNum) {
        moreButton.parent('.js-more-wrap').hide();
      }
    }

    $(moreButton).on('click', function () {
      if ($(this).attr('custom-js-more')) {
        $(this).parent('.js-more-wrap').siblings('.js-limit').find('.js-limit-unit')
            .filter(function() {
              return $(this).attr("custom-filter-choice") == "1"
                  || $(this).attr("fixed-filter-choice") === "1";
            }).slice(limitNum).slideDown('fast');
        $(this).parent('.js-more-wrap').hide();
      } else {
        $(this).parent('.js-more-wrap').siblings('.js-limit').find('.js-limit-unit').slice(limitNum).slideDown('fast');
        $(this).parent('.js-more-wrap').hide();
      }
    })

  });

  /* -----------------------------------------
  モーダル処理
  ------------------------------------------*/
  const modal_close = $(".js-modal-close");

  $('.js-modal').each(function () {
    $(this).on('click', function (e) {
      e.preventDefault();
      let activeFadein = $(this).attr('active-fadein');
      if (!activeFadein || activeFadein === 'true') {
        let target = $(this).data('target');
        let modal = document.getElementById(target);
        let scroll = $(window).scrollTop();
        $(modal).fadeIn();
        $('body').addClass('is-modal-opened');
      }
    })
  });

  modal_close.on('click', function (e) {
    e.preventDefault();
    $('.c-modal').fadeOut( function () { 
      $('body').removeClass('is-modal-opened');
    });
  });

  $('.c-modal__contents').on('click', function (e) {
    e.stopPropagation();
  });

  // 注文履歴
  $('#modal-cancel .js-modal').on('click', function () {
    if ($('#modal-complete').css('display') == 'block') { 
      $(this).parents('.c-modal').hide();
    }
  });


  /* -----------------------------------------
  ページ内アンカー
  ------------------------------------------*/
  $('.js-anchor[href^="#"]').click(function(){
    // 【メモ】キチキチにるのを防止するなら、以下の値を入れる
    var adjust = 0;
    // スクロールの速度
    var speed = 400; // ミリ秒
    // アンカーの値取得
    var href= $(this).attr("href");
    // 移動先を取得
    var target = $(href == "#" || href == "" ? 'html' : href);
    // 移動先を調整
    var position = target.offset().top - headerCommon + adjust;
    // スムーススクロール
    $('body,html').animate({scrollTop:position}, speed, 'swing');
    return false;
  });
  
  /* -----------------------------------------
  詳細 在庫表
  ------------------------------------------*/
  $('.c-sortbox__title').each(function () { 
    $(this).on('click', function () {
      $(this).siblings('.c-sortbox__contents').toggleClass('js-sort-open');
  
      var otherList = $(this).parents('.c-table__th').siblings('.c-table__th').find('.c-sortbox__contents');
      var otherList_sm = $(this).parents('.c-sortbox').siblings('.c-sortbox').find('.c-sortbox__contents');

      if (otherList.hasClass('js-sort-open')) { 
        otherList.removeClass('js-sort-open');
      }
      if (otherList_sm.hasClass('js-sort-open') ) { 
        otherList_sm.removeClass('js-sort-open');
      }

    });
  })

  $('.js-sort-close').on('click', function () { 
    $(this).parents('.c-sortbox__contents').removeClass('js-sort-open');
  })

  // $('html , body').on('click', function (e) {
  //   if (!$(e.target).closest('.c-sortbox__title').length) {
  //     $('.c-sortbox__contents').removeClass('js-sort-open');
  //   }
  // });


  /* -----------------------------------------
  数量カウンター
  ------------------------------------------*/

  $('.js-count-quantity button').on('click', function (e) {
    e.preventDefault();


    let fildName = $(this).attr('data-field');
    let type = $(this).attr('data-type');
    let $input = $(this).siblings('input[name="' + fildName + '"]');
    let carrentVal = parseInt($input.val());

    if (!isNaN(carrentVal)) {         // 入力値が数値の場合
      if (type == 'minus') {           // マイナスボタンの場合
        if (carrentVal > $input.attr('min')) {   // ∟ 入力値 > 最小値
          $input.val(carrentVal - 1).change();
        }
        if (parseInt($input.val()) == $input.attr('min')) {    // ∟ 入力値 = 最小値
          $(this).attr('disabled', true);
        }
      } else if (type == 'plus') {
        if (carrentVal < $input.attr('max')) {
          $input.val(carrentVal + 1).change();
        }
        if (parseInt($input.val()) == $input.attr('max')) {
          $(this).attr('disabled', true);
        }
      }
    } else {
      $input.val(0);
    }
  });

  $('.input_result').on('focusin', function () {
    $(this).data('oldValue', $(this).val());
  });
  $('.input_result').on('change', function () {
    let minValue = parseInt($(this).attr('min'));
    let maxValue = parseInt($(this).attr('max'));
    let valueCurrent = parseInt($(this).val());

    if (valueCurrent >= minValue) {
      $('.minus_button').removeAttr('disabled');
    } else {
      if ($(this).attr('type') === 'number') {
        $(this).val($(this).data('oldValue'));
      }
    }
    if (valueCurrent <= maxValue) {
      $('.plus_button').removeAttr('disabled');
    } else {
      if ($(this).attr('type') === 'number') {
        $(this).val($(this).data('oldValue'));
      }
    }
  });

  /* -----------------------------------------
  タブ切り替え
  ------------------------------------------*/
  $('.js-tab').on('click', function () {
    let index = $('.js-tab').index(this);

    $('.js-tab').removeClass('js-tab--active');
    $(this).addClass('js-tab--active');
    $('.js-tab-contents').removeClass('js-tab-contents--active');
    $('.js-tab-contents').eq(index).addClass('js-tab-contents--active');
  });

  /* -----------------------------------------
  ヘッダー / お知らせアイコン
  ------------------------------------------*/
  if ($('.l-header__member-information dd').find('.c-baloon--hot').length) {
    $('.l-header__member-information').find('dt svg').addClass('is-active');
  } else { 
    $('.l-header__member-information').find('dt svg').removeClass('is-active');
  }

  /* -----------------------------------------
  注文確認 / 確認チェックボックス
  ------------------------------------------*/
  $('input[type=checkbox].js-comfirmed').change(function() {
    if ($(this).prop('checked')) {
      $('input[type=checkbox].js-comfirmed').prop('checked', true);
      $('.p-order-confirm__nextstep button').prop('disabled', false);
    } else { 
      $('input[type=checkbox].js-comfirmed').prop('checked', false);
      $('.p-order-confirm__nextstep button').prop('disabled', true);
    }
  });

  /* -----------------------------------------
  注文確認 / お届け先＆お支払い チェックボックス
  ------------------------------------------*/
  $('.js-radio').each(function () {
    
    $(this).change(function () { 

      if ($(this).prop('checked')) {

        let target = $(this).data('radio');
        let receive = document.getElementById(target);
        let group = $(this).parents('.p-order-payment__select-unit');

        $.when(
          $(receive).show(),
          $('.p-order-receive__case').not(receive).hide(),

        ).done(function () { 
          // お支払いのみ
          if ($('body').hasClass('p-order-payment')) { 
            let position = group.offset().top;
            let speed = 400;
          $('body,html').animate({ scrollTop: position }, speed, 'swing');
          }
        });
      }
    })
  });
  
  /* -----------------------------------------
  一覧ページ アイコンが無い場合
  ------------------------------------------*/
  $('.c-product__item-icon').each(function () {
    
    if ( !($(this).find('span').length)) {
      $(this).css('margin', '0');
      } 
  });

});



/* -----------------------------------------
ブレイクポイント切り替えポイントで再読み込み
------------------------------------------*/
const breakPoint = 600;
let resizeFlag;

window.addEventListener('load',()=>{
  if( breakPoint < window.innerWidth){
    resizeFlag = false;
  }else{
    resizeFlag = true;
  }
  resizeWindow();
},false);

const resizeWindow = () =>{
  window.addEventListener('resize',()=>{
    if( breakPoint < window.innerWidth && resizeFlag){
      window.location.reload();
      resizeFlag = false;
    } 
    else if ( breakPoint >= window.innerWidth && !(resizeFlag)) {
      window.location.reload();
      resizeFlag = true;
    }
  },false);
}
 



