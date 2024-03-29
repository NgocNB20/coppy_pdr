/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア取得(管理機能用)
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
public interface FreeAreaGetForBackService {

    /* メッセージ SSF0003 */
    /**
     * フリーエリア取得失敗エラー<br/>
     * <code>MSGCD_FREEAREA_GET_FAIL</code>
     */
    String MSGCD_FREEAREA_GET_FAIL = "SSF000301";

    /**
     * フリーエリア取得(管理機能用)
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(Integer freeAreaSeq);
}
