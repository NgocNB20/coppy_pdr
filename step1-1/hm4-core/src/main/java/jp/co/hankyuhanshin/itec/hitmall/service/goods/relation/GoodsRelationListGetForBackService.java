/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.relation;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;

import java.util.List;

/**
 * 関連商品リスト取得（バック用）<br/>
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
public interface GoodsRelationListGetForBackService {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 関連商品エンティティリスト
     */
    List<GoodsRelationEntity> execute(Integer goodsGroupSeq);
}
