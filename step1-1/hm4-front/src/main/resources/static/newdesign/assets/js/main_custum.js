'use strict'

// ---------------------
//  define
// ---------------------

// import EL from '/assets/js/constant/elements'
let EL = {
  HTML: document.getElementsByTagName('html')[0],
  BODY: document.getElementsByTagName('body')[0],
  HEADER: document.getElementsByTagName('header')[0],
  MAIN: document.getElementsByTagName('main')[0],
  FOOTER: document.getElementsByTagName('footer')[0],
  NAV: document.getElementsByTagName('nav')[0],
  STICKY: document.querySelectorAll('.sticky')
}

// '/assets/js/constant/define'
const DEFINE = { 
  BREAKPOINT: 899,
  SCROLL_OFFSET_LG: -50,
  SCROLL_OFFSET_SM: -80,
  SCROLL_DURATION_LG: 900,
  SCROLL_DURATION_SM: 1200,
  SCROLL_EASING_LG: 'easeInOutQuart',
  SCROLL_EASING_SM: 'easeInOutQuart'
}

// ---------------------
// helper
// ---------------------

// import hmb from './helper/hmb'
  /**
   * ハンバーガーメニューの処理を提供します
   */
  function hmb() {
    const func = {
      isActive: false,
      deviceType: getDeviceType(),

      HMB: document.querySelector('.l-hamburger'),
      HMBBG: document.querySelector('.l-hamburger__back'),
      HMBCLOSE: document.querySelector('.l-hamburger__close'),
      HMBCLOSE2: document.querySelector('.l-hamburger__close2'),

      /**
       * init
       */
      init: () => {
        if (func.HMB) {
          func.HMB.addEventListener('click', func.switchShowHide, false)
          func.HMBCLOSE.addEventListener('click', func.switchShowHide, false)
          func.HMBCLOSE2.addEventListener('click', func.switchShowHide, false)
          window.addEventListener('resize', func.resize, false)
        }
      },

      /**
       * show
       */
      show: () => {
        if (EL.HTML.classList.contains('is-search-active')) EL.HTML.classList.remove('is-search-active')
        func.isActive = true
        EL.NAV.style.visibility = ''
        EL.HTML.classList.add('is-nav-active')
      },

      /**
       * hide
       */
      hide: () => {
        func.isActive = false
        EL.HTML.classList.remove('is-nav-active')
      },

      /**
       * switchShowHide
       */
      switchShowHide: () => {
        func.isActive ? func.hide() : func.show()
      },

      /**
       * resize
       */
      resize: () => {
        if (func.deviceType !== getDeviceType()) {
          func.deviceType = getDeviceType()
          func.hide()

          if (func.deviceType === 'lg') {
            EL.NAV.style.visibility = ''
          }
        }
      }

    }

    func.init()
  }

// import search from './helper/search'
  function search() {
    const func = {
      isActive: false,
      deviceType: getDeviceType(),

      SCH: document.querySelector('.js-search-btn'),
      SCHBG: document.querySelector('.l-header-search div'),

      /**
       * init
       */
      init: () => {
        if (func.SCH) {
          func.SCH.addEventListener('click', func.switchShowHide, false)
          func.SCHBG.addEventListener('click', func.switchShowHide, false)
          window.addEventListener('resize', func.resize, false)
        }
      },

      /**
       * show
       */
      show: () => {
        func.isActive = true
        EL.NAV.style.visibility = ''
        EL.HTML.classList.add('is-search-active')
      },

      /**
       * hide
       */
      hide: () => {
        func.isActive = false
        EL.HTML.classList.remove('is-search-active')
      },

      /**
       * switchShowHide
       */
      switchShowHide: () => {
        func.isActive ? func.hide() : func.show()
      },

      /**
       * resize
       */
      resize: () => {
        if (func.deviceType !== getDeviceType()) {
          func.deviceType = getDeviceType()
          func.hide()

          if (func.deviceType === 'lg') {
            EL.NAV.style.visibility = ''
          }
        }
      }

    }

    func.init()
  };

// import smSearch from './helper/sm-search'
  /**
   * スマホ用検索の処理を提供します
   */
  function smSearch() { 
      const func = {
        isActive: false,
        deviceType: getDeviceType(),
    
        HMB: document.querySelector('.js-search-open'),
        CLOSE: document.querySelector('.js-search-close'),
        HMBBG: document.querySelector('.c-search__back'),
    
        /**
         * init
         */
        init: () => {
          if (func.HMB) {
            func.HMB.addEventListener('click', func.switchShowHide, false)
            func.CLOSE.addEventListener('click', func.switchShowHide, false)
            func.HMBBG.addEventListener('click', func.switchShowHide, false)
            window.addEventListener('resize', func.resize, false)
          }
        },
    
        /**
         * show
         */
        show: () => {
          func.isActive = true
          EL.HTML.classList.add('is-c-search-active')
        },
    
        /**
         * hide
         */
        hide: () => {
          func.isActive = false
          EL.HTML.classList.remove('is-c-search-active')
        },
    
        /**
         * switchShowHide
         */
        switchShowHide: () => {
          func.isActive ? func.hide() : func.show()
        },
    
        /**
         * resize
         */
        resize: () => {
          if (func.deviceType !== getDeviceType()) {
            func.deviceType = getDeviceType()
            func.hide()
          }
        }
    
      }
    
      func.init()
  };


// import uaDataset from './helper/uaDataset'   // UA判定（プラグイン利用）
  /**
   * UA情報を<html>タグにdatasetとして追加します
   * 文字列にスペースが付く場合はハイフンで繋がれます
   */
  function uaDataset() { 
    const ua = UAParser()
    const uaString = {
      browserName: ua.browser.name.toLowerCase().replace(' ', '-'),
      browserVersion: ua.browser.major,
      browserEngine: ua.engine.name.toLowerCase().replace(' ', '-'),
      osName: ua.os.name.toLowerCase().replace(' ', '-'),
      type: (typeof ua.device.type !== 'undefined') ? ua.device.type.toLowerCase().replace(' ', '-') : 'laptop'
    }
    EL.HTML.dataset.browser = uaString.browserName
    EL.HTML.dataset.browserversion = uaString.browserVersion
    EL.HTML.dataset.browserengine = uaString.browserEngine
    EL.HTML.dataset.os = uaString.osName
    EL.HTML.dataset.type = uaString.type
  }


//import getDocumentH from '/assets/js/helper/getDocumentHeight'
  /**
   * documentの高さを取得します
   * @return document height
   */
  function getDocumentH() { 
    let documentH = document.body.clientHeight - window.innerHeight

    if (getDeviceType() === 'sm') {
      //documentH = documentH - EL.FOOTER.clientHeight + 0
    }
    return documentH;
  }

// import isTouchSupport from './helper/isTouchSupport'
  /**
   * タッチサポート判定を行います
   */
  function isTouchSupport() { 
    const isTouchSupport = (window.ontouchstart === null)
    EL.HTML.dataset.touchsupport = isTouchSupport;
  }


// import getClassName from './helper/getClassName'
  /**
   * 特定の要素のクラスを取得して文字列で返します
   * @return string
   */
  function getClassName(target)  {
    const className = target.classList[0];
    return className;
  }

// import getDeviceType from './helper/getDeviceType'
  /**
   * breakpointとウインドウサイズを比較してlgかsmか返します
   * @return string 'lg' or 'sm'
   */
  function getDeviceType() { 
    const windowWidth = window.innerWidth;
    const deviceType = windowWidth > DEFINE.BREAKPOINT ? 'lg' : 'sm';
    return deviceType;
  }


// import relationInputs from './helper/relationInputs'
  /**
   * フォームの入力項目の連動を制御
   */
  function relationInputs() {
    const func = {

      el: document.querySelectorAll('[data-relation-inputs="el"]'),
      childs: document.querySelectorAll('[data-relation-inputs="child"]'),

      /**
       * init
       */
      init: () => {
        if (!func.el) return
        func.disableChild()
        func.el.forEach(el => {
          const parent = el.querySelector('[data-relation-inputs="parent"]')
          const child = el.querySelector('[data-relation-inputs="child"]')

          if (!parent || !child) return
          parent.addEventListener('change', e => {
            func.resetChild(child)
          }, false)
        })
      },

      /**
       * resetChild
       */
      resetChild: (child) => {
        func.childs.forEach(el => {
          const select = el.querySelectorAll('select')
          const input = el.querySelectorAll('input')
          const selectDiv = el.querySelectorAll('.c-selectbox')
          if (el === child) {
            select.forEach(el => {
              el.disabled = false
            })
            input.forEach(el => {
              el.disabled = false
            })
            selectDiv.forEach(el => {
              el.classList.remove('disable')
            })
          } else {
            select.forEach(el => {
              el.disabled = true
              el.selectedIndex = 0
            })
            input.forEach(el => {
              el.disabled = true
              el.checked = false
              el.value = ''
            })
            selectDiv.forEach(el => {
              func.resetSelectBox(el)
            })
            selectDiv.forEach(el => {
              el.classList.add('disable')
              el.classList.remove('is-open')
            })
          }
        })
      },

      /**
       * disableChild
       */
      // LS元ソース
      // disableChild: () => {
      //   func.childs.forEach(el => {
      //     el.querySelectorAll('select').forEach(el => {
      //       el.disabled = true
      //     })
      //     el.querySelectorAll('input').forEach(el => {
      //       el.disabled = true
      //     })
      //     el.querySelectorAll('.c-selectbox').forEach(el => {
      //       el.classList.add('disable')
      //     })
      //   })
      // },
      disableChild: function disableChild() {
        func.el.forEach(function (el) {
          var parent = el.querySelector('[data-relation-inputs="parent"]');
          var child = el.querySelector('[data-relation-inputs="child"]');
          if (!parent || !child) return;

          if (!parent.checked) {
            child.querySelectorAll('select').forEach(function (el) {
              el.disabled = true;
            });
            child.querySelectorAll('input').forEach(function (el) {
              el.disabled = true;
            });
            child.querySelectorAll('.c-selectbox').forEach(function (el) {
              el.classList.add('disable');
            });
          }
        });
      },


      /**
       * resetSelectbox
       */
      resetSelectBox: (el) => {
        if (el.querySelector('.c-selectbox__list li.is-select')) el.querySelector('.c-selectbox__list li.is-select').classList.remove('is-select')

        const def = el.querySelector('.c-selectbox__list li')
        def.classList.add('is-select')

        el.querySelector('.c-selectbox--text').innerHTML = def.querySelector('.c-selectbox--text').innerHTML
      }
    }

    func.init()
  }

// import favorite from './helper/favorite'
  /**
   * お気に入りボタンの処理を提供します
   */
  function favorite() { 
    const func = {
      FAVBTN: document.querySelectorAll('.js-btn-favorite'),

      /**
       * init
       */
      init: () => {
        func.FAVBTN.forEach(btn => {
          btn.addEventListener('click', (e) => {
            e.preventDefault()
            func.switchShowHide(btn)
          })
        })
      },

      /**
       * show
       */
      show: (btn) => {
        btn.classList.add('is-active')
      },

      /**
       * hide
       */
      hide: (btn) => {
        btn.classList.remove('is-active')
      },

      /**
       * switchShowHide
       */
      switchShowHide: (btn) => {
        btn.classList.contains('is-active') ? func.hide(btn) : func.show(btn)
      }

    }

    func.init()
  }

// import password from './helper/password'
  /**
   * パスワードの処理を提供します
   */
  function password() {
    const func = {
      INPUT: document.querySelectorAll('.js-password-input'),
      BUTTON: document.querySelectorAll('.js-password-btn'),

      /**
       * init
       */
      init: () => {
        func.BUTTON.forEach(btn => {
          btn.addEventListener('click', () => {
            func.switchShowHide(btn)
          })
        })
      },

      /**
       * show
       */
      show: (target) => {
        target.previousElementSibling.type = 'text'
        target.classList.add('is-show')
      },

      /**
       * hide
       */
      hide: (target) => {
        target.previousElementSibling.type = 'password'
        target.classList.remove('is-show')
      },

      /**
       * switchShowHide
       */
      switchShowHide: (target) => {
        target.classList.contains('is-show') ? func.hide(target) : func.show(target)
      }
    }

    func.init()
  }



// ---------------------
// page scripts
// ---------------------

const breakW = 600;

// import pageNameTop from './page/top'
function pageNameTop() { 
    
    //mv
    const mvSwiper = new Swiper('.p-top__mv', {
      slidesPerView: 'auto',
      centeredSlides: true,
      effect: 'slide',
      loop: true,
      speed: 500,
      autoplay: {
        delay: 5000
      },
      pagination: {
        el: '.top__mv-pagination',
        clickable: true
      },
      navigation: {
        nextEl: '.top__mv-next',
        prevEl: '.top__mv-prev'
      },
      spaceBetween: 5,
      
      breakpoints: {
        // 600px以上の場合
        600: {
          spaceBetween: 10,
        }
      },
    })

    //banner
    if (window.innerWidth >= 600) {
      const bnrSwiper = new Swiper('.p-top__banner-swiper', {
        slidesPerView: 'auto',
        effect: 'slide',
        loop: false,
        speed: 500,

        navigation: {
          nextEl: '.top__banner-next',
          prevEl: '.top__banner-prev'
        }
      })
    }

    // sellwell
    const sellwellSwiper = new Swiper('.p-top__sellswell .js-product-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,

      breakpoints: {
        // 600px以上の場合
        600: {
          // navigationを外に出している場合は固有のクラス名を指定する。（他のスライダーに干渉してしまう為）
          navigation: {
            nextEl: '.js-product-swiper-next',
            prevEl: '.js-product-swiper-prev'
          },
        }
      },
    })

    // special
    const special1Swiper = new Swiper('.p-top__special .js-product-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,

      breakpoints: {
        // 600px以上の場合
        600: {
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
        }
      },
    })

    // topics
    const topicsSwiper = new Swiper('.p-top__topics-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,

      breakpoints: {
        // 600px以上の場合
        600: {
          navigation: {
            nextEl: '.top__topics-next',
            prevEl: '.top__topics-prev',
          },
        }
      },
    })
   
    // aside
    if (window.innerWidth >= 600) {
      const asideSwiper = new Swiper('.p-top__aside-swiper', {
        slidesPerView: 'auto',
        effect: 'slide',
        loop: false,
        speed: 500,

        breakpoints: {
          // 600px以上の場合
          600: {
            navigation: {
              nextEl: '.top__aside-next',
              prevEl: '.top__aside-prev',
            },
          }
        },
      })
    }

  }

// import pageNameProductDetail from './page/product-detail'
function pageNameProductDetail() {
    
    // viewed
    const viewedSwiper = new Swiper('.p-product-detail__viewed .js-product-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,

      breakpoints: {
        // 600px以上の場合
        600: {
          // navigationを外に出している場合は固有のクラス名を指定する。（他のスライダーに干渉してしまう為）
          navigation: {
            nextEl: '.js-product-swiper-next',
            prevEl: '.js-product-swiper-prev'
          },
        }
      },
    })
  
    // favorite & sellswell & related
    const goodsdetailSwiper = new Swiper('.p-product-detail__common-swiper .js-product-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,

      breakpoints: {
        // 600px以上の場合
        600: {
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
        }
      },
    })
  
    // relation
    const rankItems = document.querySelectorAll('.c-product--relation')
    const rankSwiper = Array(document.querySelectorAll('.c-product--relation').length)
    rankItems.forEach(function (elem, index) {
      if (window.innerWidth <= 768.9) {
        rankSwiper[index] = new Swiper(elem, {
          slidesPerView: 'auto',
          effect: 'slide',
          speed: 500,
          pagination: {
            el: elem.querySelector('.swiper-pagination'),
            clickable: true
          }
        })
      }
    })
    window.addEventListener('resize',
      () => {
      rankItems.forEach(function (elem, index) {
        if (window.innerWidth <= 768.9) {
          if (rankSwiper[index]) {

          } else {
            rankSwiper[index] = new Swiper(elem, {
              slidesPerView: 'auto',
              effect: 'slide',
              speed: 500,
              pagination: {
                el: elem.querySelector('.swiper-pagination'),
                clickable: true
              }
            })
          }
        } else {
          if (rankSwiper[index]) {
            rankSwiper[index].destroy()
            rankSwiper[index] = undefined
          }
        }
      })
    }),
    false;
  
  }

// import pageNameCart from './page/cart'
  function pageNameCart() {


  }

// import pageNameSearch from './page/search'
function pageNameSearch() { 

  const searchSwiper = new Swiper('.p-search__unisearch-swiper', {
    slidesPerView: 'auto',
    effect: 'slide',
    loop: false,
    speed: 500,

    breakpoints: {
      // 600px以上の場合
      600: {
        // navigationを外に出している場合は固有のクラス名を指定する。（他のスライダーに干渉してしまう為）
        navigation: {
          nextEl: '.js-search-swiper-next',
          prevEl: '.js-search-swiper-prev'
        },
      }
    },
  })

}

// mypage
function pageNameMypage() { 
  // mypage top
  let elements = document.getElementsByClassName('p-mypage-top');

  if (elements.length) {
    const special1Swiper = new Swiper('.p-mypage-top__slider .js-product-swiper', {
      slidesPerView: 'auto',
      effect: 'slide',
      loop: false,
      speed: 500,
  
      breakpoints: {
        // 600px以上の場合
        600: {
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
          },
        }
      },
    })
  }
}

// require('svgxuse')

// ---------------------
// getDeviceType
// ---------------------
  let deviceType = getDeviceType()


// ---------------------
// getDocumentH
// ---------------------

  let documentH = getDocumentH()

/**
 * getScrollPos
 */
const getScrollPos = function getScrollPos() {
  const y = window.pageYOffset;

  // add class is-scroll
  if (y > 1) {
    if (!EL.HTML.classList.contains('is-scroll')) {
      EL.HTML.classList.add('is-scroll')
    }
  } else {
    EL.HTML.classList.remove('is-scroll')
  }

  // add class is-footer
  if (documentH <= y) {
    if (!EL.HTML.classList.contains('is-footer')) {
      EL.HTML.classList.add('is-footer')
    }
  } else {
    EL.HTML.classList.remove('is-footer')
  }
}

/**
 * first
 */
const first = function first() { // 【メモ】コメント表示(07/18)
  // set ua dataset
  uaDataset()

  // set touch support dataset
  isTouchSupport();

  // hmb
  hmb();

  // search modal
  search();
  smSearch();

  //   // selectbox
  //   selectbox()

  // favorite
  favorite();

  // password
  password();

  // sweetScroll
  const sweetScroll = new SweetScroll({
  });

}

/**
 * init
 */
const init = function init() {
  // get body className
  const className = getClassName(EL.BODY);

  // getScrollPos
  getScrollPos();

  // top
  if (className.endsWith('top')) {
    pageNameTop();
  }

  // product-detail
  if (className.endsWith('product-detail')) {
    pageNameProductDetail();
  }

  // cart
  if (className.endsWith('cart')) {
    pageNameCart();
  }

  //form
  relationInputs();

  // search
  if (className.endsWith('search')) {
    pageNameSearch()
  }

  // mypage
  if (className.endsWith('mypage')) {
    pageNameMypage()
  }

}

/**
 * DOMCONTENTLOADED
 */
window.addEventListener('DOMContentLoaded', first);

/**
 * LOAD
 */
window.addEventListener('load', init);

/**
 * SCROLL
 */
window.addEventListener('scroll', getScrollPos, false);

