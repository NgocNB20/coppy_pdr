// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.coop;

import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 前回基幹連携日時更新ロジック
 *
 * @author s.kume(NSK)
 */
public interface CoopDateHistoryGetUpdateLogic {

    /**
     * 前回連携日時情報取得
     *
     * @param coopId 基幹連携ID
     * @return 前回連携日時情報エンティティ
     */
    CoopDateHistoryEntity execute(String coopId);

    /**
     * 前回連携日時更新
     *
     * @param coopDateHistoryPdrEntity 基幹連携日時情報エンティティ
     */
    void execute(CoopDateHistoryEntity coopDateHistoryPdrEntity);
}
// PDR Migrate Customization to here
