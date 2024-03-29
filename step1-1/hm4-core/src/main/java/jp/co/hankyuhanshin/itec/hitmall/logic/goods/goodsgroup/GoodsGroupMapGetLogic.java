/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;
import java.util.Map;

/**
 * 商品グループマップ取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface GoodsGroupMapGetLogic {

    // LGP0002

    /**
     * 商品グループマップ取得<br/>
     * 商品グループDTOを保持するマップを取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 商品グループMAP
     */
    Map<Integer, GoodsGroupDto> execute(List<Integer> goodsGroupSeqList);
}
