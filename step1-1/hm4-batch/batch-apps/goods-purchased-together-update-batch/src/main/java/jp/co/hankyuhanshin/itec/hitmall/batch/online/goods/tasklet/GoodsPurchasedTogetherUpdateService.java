// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupRegistLogic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 一緒によく購入される商品更新サービス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
public class GoodsPurchasedTogetherUpdateService {

    /**
     * 一緒によく購入される商品削除
     */
    private final GoodsTogetherBuyGroupDeleteLogic goodsTogetherBuyGroupDeleteLogic;

    /**
     * よく一緒に購入される商品登録
     */
    private final GoodsTogetherBuyGroupRegistLogic goodsTogetherBuyGroupRegistLogic;

    /**
     * コンストラクター
     *
     * @param goodsTogetherBuyGroupDeleteLogic    一緒によく購入される商品更新削除
     * @param goodsTogetherBuyGroupRegistLogic    よく一緒に購入される商品登録
     */
    public GoodsPurchasedTogetherUpdateService(GoodsTogetherBuyGroupDeleteLogic goodsTogetherBuyGroupDeleteLogic,
                                               GoodsTogetherBuyGroupRegistLogic goodsTogetherBuyGroupRegistLogic) {
        this.goodsTogetherBuyGroupDeleteLogic = goodsTogetherBuyGroupDeleteLogic;
        this.goodsTogetherBuyGroupRegistLogic = goodsTogetherBuyGroupRegistLogic;
    }

    /**
     * 一緒によく購入される商品削除
     *
     * @param registMethod 登録方法
     */
    @Transactional(rollbackFor = Exception.class)
    public void goodsTogetherBuyGroupDelete(String registMethod) {
        goodsTogetherBuyGroupDeleteLogic.execute(registMethod);
    }

    /**
     * 一緒によく購入される商品更新
     *
     * @param goodsTogetherBuyGroupEntity よく一緒に購入される商品クラス
     */
    @Transactional(rollbackFor = Exception.class)
    public void goodsTogetherBuyGroupRegist(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity) {
        goodsTogetherBuyGroupRegistLogic.execute(goodsTogetherBuyGroupEntity);
    }

}
// 2023-renew No21 to here