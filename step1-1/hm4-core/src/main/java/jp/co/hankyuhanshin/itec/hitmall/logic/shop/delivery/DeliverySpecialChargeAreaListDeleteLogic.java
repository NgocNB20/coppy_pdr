/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;

import java.util.List;

/**
 * 配送特別料金エリア削除Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliverySpecialChargeAreaListDeleteLogic {
    /**
     * 配送特別料金エリア情報を削除します
     *
     * @param entityList List&lt;DeliverySpecialChargeAreaEntity&gt;
     * @return int 処理結果
     */
    int execute(List<DeliverySpecialChargeAreaEntity> entityList);
}
