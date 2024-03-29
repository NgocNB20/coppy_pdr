// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * よく一緒に購入される商品リスト取得（バック用）<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
public class GoodsTogetherBuyGroupListGetForBackServiceImpl extends AbstractShopService
                implements GoodsTogetherBuyGroupListGetForBackService {

    /**
     * よく一緒に購入される商品リスト取得（バック用）
     */
    private final GoodsTogetherBuyGroupListGetForBackLogic goodsTogetherBuyGroupListGetForBackLogic;

    @Autowired
    public GoodsTogetherBuyGroupListGetForBackServiceImpl(GoodsTogetherBuyGroupListGetForBackLogic goodsTogetherBuyGroupListGetForBackLogic) {
        this.goodsTogetherBuyGroupListGetForBackLogic = goodsTogetherBuyGroupListGetForBackLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品エンティティリスト
     */
    @Override
    public List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq) {

        // (1) パラメータチェック
        // ・商品SEQ ： null(or 0 ) の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        // (2) よく一緒に購入されている商品リスト取得（バック用）実行
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList =
                        goodsTogetherBuyGroupListGetForBackLogic.execute(goodsGroupSeq);

        // (3) 戻り値
        return goodsTogetherBuyGroupEntityList;
    }
}
// 2023-renew No21 to here