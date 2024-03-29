/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

import java.util.List;

/**
 * アイコン表示順更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconListOrderDisplayUpdateLogic {

    /* メッセージ LSM0003 */
    /**
     * 商品インフォメーションアイコン更新失敗エラー<br/>
     * <code>MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL</code>
     */
    public static final String MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL = "LSM000301";

    /**
     * リスト分、商品インフォメーションアイコンの表示順を指定値で更新します。<br />
     * 更新項目
     * <ul>
     *  <li>表示順</li>
     *  <li>更新日付</li>
     * </ul>
     *
     * @param iconEntityList アイコンエンティティリスト
     * @return 処理件数
     */
    int execute(List<GoodsInformationIconEntity> iconEntityList);
}
