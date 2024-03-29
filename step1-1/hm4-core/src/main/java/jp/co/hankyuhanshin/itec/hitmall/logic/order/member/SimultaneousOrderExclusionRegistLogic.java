/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.member;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;

/**
 * 同時注文排他情報登録<br/>
 *
 * @author h_hakogi
 */
public interface SimultaneousOrderExclusionRegistLogic {

    /**
     * 同時注文排他情報登録処理<br/>
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他情報エンティティ
     * @return 登録件数
     */
    int execute(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity);
}
