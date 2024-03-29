/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.param.UkApiGetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.stereotype.Component;

/**
 * ユニサーチ商品取得（Ajax）Helperクラス
 *
 * @author tk32120
 */
@Component
public class AjaxUkUniSearchGoodsGetHelper {

    /**
     * 共通情報ヘルパークラス
     */
    private CommonInfoUtility commonInfoUtility;

    public AjaxUkUniSearchGoodsGetHelper(CommonInfoUtility commonInfoUtility) {
        this.commonInfoUtility = commonInfoUtility;
    }

    /**
     * ユニサーチ（商品）リクエストDtoを設定
     *
     * @param model ユニサーチ商品取得（Ajax）Model
     * @param cid   カテゴリーID
     * @param pnum  ページ数
     * @param limit 検索結果上限
     * @return ユニサーチ（商品）リクエストDto
     */
    public UkApiGetUkUniSearchGoodsRequest createRequest(AjaxUkUniSearchGoodsGetModel model,
                                                         String cid,
                                                         String pnum,
                                                         Integer limit) {

        UkApiGetUkUniSearchGoodsRequest req = new UkApiGetUkUniSearchGoodsRequest();

        req.setCategory(cid);
        req.setKw(null);
        req.setPage(Integer.parseInt(pnum));
        req.setRows(limit);
        req.setSortType(null);

        CommonInfoUser user = model.getCommonInfo().getCommonInfoUser();
        String cryptUser = "";
        if (commonInfoUtility.isLogin(model.getCommonInfo())) {
            cryptUser = user.getCryptUserId();
        }
        req.setUser(cryptUser);

        return req;

    }
}
