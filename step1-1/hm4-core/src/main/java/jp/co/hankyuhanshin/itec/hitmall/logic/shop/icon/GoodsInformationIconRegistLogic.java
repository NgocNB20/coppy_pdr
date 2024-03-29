/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * アイコン登録Logic
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconRegistLogic {

    /**
     * 商品インフォメーションアイコン登録失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_INSERT_FAIL</code>
     */
    public static final String MSGCD_GOODSINFORMATIONICON_INSERT_FAIL = "LSM000401";

    /**
     * アイコン登録
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    int execute(GoodsInformationIconEntity goodsInformationIconEntity);

}
