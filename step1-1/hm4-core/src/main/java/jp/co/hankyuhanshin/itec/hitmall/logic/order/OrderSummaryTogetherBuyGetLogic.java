// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import org.seasar.doma.Select;

import java.util.List;

/**
 * よく一緒に購入される商品クラスリスト取得<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface OrderSummaryTogetherBuyGetLogic {

    /**
     * よく一緒に購入される商品クラスリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品クラスリスト
     */
    @Select
    List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq);
}
// 2023-renew No21 to here