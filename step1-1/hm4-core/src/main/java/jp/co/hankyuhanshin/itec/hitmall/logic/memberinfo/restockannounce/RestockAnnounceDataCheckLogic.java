// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;

/**
 * 入荷お知らデータチェックロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceDataCheckLogic {

    /**
     * 入荷お知ら最大登録件数を超えた場合<br/>
     * <code>MSGCD_RESTOCK_ANNOUNCE_GOODS_MAX_COUNT_FAIL</code>
     */
    public static final String MSGCD_RESTOCK_ANNOUNCE_GOODS_MAX_COUNT_FAIL = "LMF000402";

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     */
    void execute(RestockAnnounceEntity restockAnnounceEntity);

}
// 2023-renew No65 to here
