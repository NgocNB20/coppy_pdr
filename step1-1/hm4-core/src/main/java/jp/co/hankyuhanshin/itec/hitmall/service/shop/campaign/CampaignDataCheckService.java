/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

/**
 * キャンペーン情報データチェック(論理削除されているものも含む)<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 *
 */
public interface CampaignDataCheckService {

    /**
     * キャンペーン情報データチェック(論理削除されているものも含む)<br/>
     *
     * @param campaignCode キャンペーンコード
     * @return チェック結果
     */
    boolean execute(String campaignCode);

}
