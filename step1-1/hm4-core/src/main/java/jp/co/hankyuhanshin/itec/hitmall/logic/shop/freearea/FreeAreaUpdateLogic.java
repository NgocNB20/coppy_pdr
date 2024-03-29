/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface FreeAreaUpdateLogic {

    /**
     * フリーエリア更新
     *
     * @param freeAreaEntity フリーエリアエンティティ
     * @return 処理件数
     */
    int execute(FreeAreaEntity freeAreaEntity);

}
