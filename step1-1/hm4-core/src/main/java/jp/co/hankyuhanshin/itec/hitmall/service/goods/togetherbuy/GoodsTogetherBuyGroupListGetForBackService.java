// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;

/**
 * よく一緒に購入される商品リスト取得（バック用）<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface GoodsTogetherBuyGroupListGetForBackService {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品エンティティリスト
     */
    List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq);
}
// 2023-renew No21 to here