/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア登録サービス
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface FreeAreaRegistService {

    /* メッセージ SSF0004 */
    /**
     * フリーエリア登録失敗エラー<br/>
     * <code>MSGCD_FREEAREA_REGIST_FAIL</code>
     */
    String MSGCD_FREEAREA_REGIST_FAIL = "SSF000401";

    /**
     * フリーエリア登録
     *
     * @param freeAreaEntity フリーエリアエンティティ
     * @return 処理件数
     */
    int execute(FreeAreaEntity freeAreaEntity);
}
