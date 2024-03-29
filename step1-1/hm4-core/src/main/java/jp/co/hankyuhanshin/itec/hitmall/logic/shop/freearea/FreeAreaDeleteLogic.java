/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア削除
 *
 * @author nose
 */
public interface FreeAreaDeleteLogic {

    /**
     * フリーエリア削除
     *
     * @param freeAreaEntity フリーエリア情報
     * @return 処理件数
     */
    int execute(FreeAreaEntity freeAreaEntity);

}
