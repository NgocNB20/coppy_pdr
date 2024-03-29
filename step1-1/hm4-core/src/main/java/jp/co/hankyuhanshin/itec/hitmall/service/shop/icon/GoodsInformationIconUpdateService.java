/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * アイコン更新
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface GoodsInformationIconUpdateService {

    /* メッセージ SSM0005 */

    /**
     * 商品インフォメーションアイコン更新失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL</code>
     */
    String MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL = "SSM000501";

    /**
     * アイコン更新
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理結果マップ
     */
    int execute(GoodsInformationIconEntity iconEntity);
}
