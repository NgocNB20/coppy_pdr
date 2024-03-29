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
 * 入荷お知ら情報登録ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceRegistLogic {

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     * @return 登録件数
     */
    int execute(RestockAnnounceEntity restockAnnounceEntity);
}
// 2023-renew No65 to here