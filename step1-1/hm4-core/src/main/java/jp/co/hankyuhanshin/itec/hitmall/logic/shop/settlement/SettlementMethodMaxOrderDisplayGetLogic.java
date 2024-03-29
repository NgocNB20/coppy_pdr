/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

/**
 * 最大表示順取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodMaxOrderDisplayGetLogic {

    /**
     * ロジック実行
     *
     * @param shopSeq ショップSEQ
     * @return 表示順の最大値
     */
    Integer execute(Integer shopSeq);
}
