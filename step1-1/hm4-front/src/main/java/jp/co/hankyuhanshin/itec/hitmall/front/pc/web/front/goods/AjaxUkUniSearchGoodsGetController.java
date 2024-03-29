/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.UkapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * ユニサーチ商品取得（Ajax）Controllerクラス
 *
 * @author tk32120
 */
@RestController
@RequestMapping("/")
public class AjaxUkUniSearchGoodsGetController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxUkUniSearchGoodsGetController.class);

    /**
     * ユニサーチ商品取得（Ajax）Helper
     */
    private final AjaxUkUniSearchGoodsGetHelper ajaxUkUniSearchGoodsGetHelper;

    /**
     * UK-API
     */
    private final UkapiApi ukApi;

    /**
     * TOP画面：１ページ当たりのデフォルト最大表示件数
     */
    public static final Integer DEFAULT_LIMIT = 5;

    /**
     * TOP画面：デフォルトページ番号
     */
    public static final String DEFAULT_PNUM = "1";

    /**
     * コンストラクタ
     *
     * @param ajaxUkUniSearchGoodsGetHelper ユニサーチ商品取得（Ajax）Helper
     * @param ukApi                         UK-API
     */
    @Autowired
    public AjaxUkUniSearchGoodsGetController(AjaxUkUniSearchGoodsGetHelper ajaxUkUniSearchGoodsGetHelper,
                                             UkapiApi ukApi) {
        this.ajaxUkUniSearchGoodsGetHelper = ajaxUkUniSearchGoodsGetHelper;
        this.ukApi = ukApi;
    }

    /**
     * フリーエリアで指定したカテゴリーIDの商品をユニサーチで取得する(Ajax通信)
     *
     * @param ajaxUkUniSearchGoodsGetModel カート追加（Ajax）Model
     * @return ユニサーチ（商品）レスポンス
     */
    @GetMapping("/ajaxUkUniSearchGoodsGet")
    @ResponseBody
    public UkApiGetUkUniSearchGoodsResponse ajaxUkUniSearchGoodsGet(AjaxUkUniSearchGoodsGetModel ajaxUkUniSearchGoodsGetModel) {

        // UKAPIリクエストDto設定
        UkApiGetUkUniSearchGoodsRequest uniSearchRequest =
                        ajaxUkUniSearchGoodsGetHelper.createRequest(ajaxUkUniSearchGoodsGetModel,
                                                                    ajaxUkUniSearchGoodsGetModel.getCid(), DEFAULT_PNUM,
                                                                    DEFAULT_LIMIT
                                                                   );
        UkApiGetUkUniSearchGoodsResponse uniSearchResponse = null;
        try {
            // ユニサーチAPI実行
            uniSearchResponse = ukApi.getUkUniSearchGoods(uniSearchRequest, null);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return uniSearchResponse;

    }
}
