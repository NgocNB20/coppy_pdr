/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * アイコン登録
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface GoodsInformationIconRegistService {

    /**
     * 商品インフォメーションアイコン登録失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_REGIST_FAIL</code>
     */
    String MSGCD_GOODSINFORMATIONICON_REGIST_FAIL = "SSM000301";

    /**
     * アイコン登録
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理結果マップ
     */
    int execute(GoodsInformationIconEntity iconEntity);
}
