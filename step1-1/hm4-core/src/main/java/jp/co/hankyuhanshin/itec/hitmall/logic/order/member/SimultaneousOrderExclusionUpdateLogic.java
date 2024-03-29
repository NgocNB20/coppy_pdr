/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.member;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;

/**
 * 同時注文排他情報更新<br/>
 */
public interface SimultaneousOrderExclusionUpdateLogic {

    /**
     * 同時注文排他情報更新<br/>
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他情報エンティティ
     * @return 更新件数
     */
    int execute(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity);

}
