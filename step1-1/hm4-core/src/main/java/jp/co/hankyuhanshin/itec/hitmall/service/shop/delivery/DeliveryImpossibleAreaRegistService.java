/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;

/**
 * 配送不可能エリア登録Serviceインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface DeliveryImpossibleAreaRegistService {

    /**
     * 配送特別料金エリア登録処理を行います
     *
     * @param entity DeliverySpecialChargeAreaEntity ME!
     * @return int 処理結果
     */
    int execute(DeliveryImpossibleAreaEntity entity);

}
