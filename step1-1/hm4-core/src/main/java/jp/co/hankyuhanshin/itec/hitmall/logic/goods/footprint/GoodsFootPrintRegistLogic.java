/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;

/**
 * あしあと商品情報登録<br/>
 * あしあと商品情報を登録する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsFootPrintRegistLogic {

    // LGT0001

    /**
     * あしあと商品情報登録<br/>
     * あしあと商品情報を登録する。<br/>
     *
     * @param footprintEntity あしあと情報エンティティ
     * @return 登録・更新件数
     */
    int execute(FootprintEntity footprintEntity);

}
