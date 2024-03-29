/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループマップ取得(商品グループコード)<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupMapGetByCodeLogic {
    // LGP0021

    /**
     * 商品グループマップ取得<br/>
     * 商品グループエンティティを保持するマップを取得する。<br/>
     *
     * @param shopSeq            ショップSEQ
     * @param goodsGroupCodeList 商品グループコードリスト
     * @return 商品グループエンティティMAP
     */
    Map<String, GoodsGroupEntity> execute(Integer shopSeq, List<String> goodsGroupCodeList);
}
