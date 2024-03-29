/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.top.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.top.TopInitialaizeInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.TopInitialaizeInfoClickRankingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.top.TopInitialaizeInfoOrderRankingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.top.TopInitialaizeInfoGetService;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * トップ画面初期表示用サービス実装クラス
 *
 * @author tateishi
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class TopInitialaizeInfoGetServiceImpl extends AbstractShopService implements TopInitialaizeInfoGetService {

    /**
     * 商品ランキング情報取得(受注)Logic
     */
    private final TopInitialaizeInfoOrderRankingListGetLogic topInitialaizeInfoOrderRankingListGetLogic;

    /**
     * 商品ランキング情報取得(受注)Logic
     */
    private final TopInitialaizeInfoClickRankingListGetLogic topInitialaizeInfoClickRankingListGetLogic;

    /**
     * ショップ情報取得サービス
     */
    private final ShopGetService shopInfoGetService;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    @Autowired
    public TopInitialaizeInfoGetServiceImpl(TopInitialaizeInfoOrderRankingListGetLogic topInitialaizeInfoOrderRankingListGetLogic,
                                            TopInitialaizeInfoClickRankingListGetLogic topInitialaizeInfoClickRankingListGetLogic,
                                            ShopGetService shopInfoGetService,
                                            DateUtility dateUtility) {
        this.topInitialaizeInfoOrderRankingListGetLogic = topInitialaizeInfoOrderRankingListGetLogic;
        this.topInitialaizeInfoClickRankingListGetLogic = topInitialaizeInfoClickRankingListGetLogic;
        this.shopInfoGetService = shopInfoGetService;
        this.dateUtility = dateUtility;
    }

    /**
     * トップ画面初期表示情報を取得します<br />
     *
     * @return TopInitialaizeInfoDto
     */
    @Override
    public TopInitialaizeInfoDto execute() {
        TopInitialaizeInfoDto topInitialaizeInfoDto = getComponent(TopInitialaizeInfoDto.class);
        Integer shopSeq = 1001;

        // ショップ情報取得
        topInitialaizeInfoDto.setShopEntity(shopInfoGetService.execute());
        // 商品クリックランキングリスト取得
        topInitialaizeInfoDto.setClickRankingList(topInitialaizeInfoClickRankingListGetLogic.execute(shopSeq));
        // 商品受注ランキングリスト取得
        topInitialaizeInfoDto.setOrderRankingList(topInitialaizeInfoOrderRankingListGetLogic.execute(shopSeq));
        return topInitialaizeInfoDto;
    }

}
