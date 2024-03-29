/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

/**
 * マルチペイショップロジック<br/>
 *
 * @author is40701
 */
public interface MulPayShopLogic {

    /**
     * マルペイショップID取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return String マルペイショップID
     */
    String getMulPayShopId(Integer shopSeq);
}
