/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア更新サービス
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface FreeAreaUpdateService {

    /* メッセージ SSF0005 */
    /**
     * フリーエリア更新失敗エラー<br/>
     * <code>MSGCD_FREEAREA_UPDATE_FAIL</code>
     */
    String MSGCD_FREEAREA_UPDATE_FAIL = "SSF000501";

    /**
     * フリーエリア更新
     *
     * @param freeAreaEntity フリーエリアエンティティ
     * @return 処理件数
     */
    int execute(FreeAreaEntity freeAreaEntity);
}
