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
 * 入荷お知ら更新ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceUpdateLogic {

    /**
     * 入荷お知ら更新処理<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     * @return 更新処理
     */
    int execute(RestockAnnounceEntity restockAnnounceEntity);
}
// 2023-renew No65 to here