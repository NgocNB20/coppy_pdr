/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

/**
 * キャンペーンデータチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface CampaignDataCheckLogic {

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return true データあり、false データなし
     */
    boolean execute(Integer shopSeq, String campaignCode);
}
