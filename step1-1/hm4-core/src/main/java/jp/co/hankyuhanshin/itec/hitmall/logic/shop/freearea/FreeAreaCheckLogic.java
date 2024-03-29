/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリアデータチェック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface FreeAreaCheckLogic {

    /* メッセージ LSF0005 */
    /**
     * フリーエリア重複エラー<br/>
     * <code>MSGCD_DUPLICATE_FREEAREA</code>
     */
    public static final String MSGCD_DUPLICATE_FREEAREA = "LSF000501";

    /**
     * フリーエリアデータチェック
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    void execute(FreeAreaEntity freeAreaEntity);

}
