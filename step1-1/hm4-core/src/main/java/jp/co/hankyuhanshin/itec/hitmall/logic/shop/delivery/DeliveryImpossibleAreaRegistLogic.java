/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;

/**
 * 配送不可能エリア登録Logicインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliveryImpossibleAreaRegistLogic {

    /**
     * 配送不可能エリア登録処理を行います
     *
     * @param entity DeliveryImpossibleAreaEntity
     * @return int 処理結果
     */
    int execute(DeliveryImpossibleAreaEntity entity);

}
