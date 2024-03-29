/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.member;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;

/**
 * 同時注文排他情報取得ロジック<br/>
 *
 * @author h_hakogi
 */
public interface SimultaneousOrderExclusionGetLogic {

    /**
     * 同時注文排他情報を取得する<br />
     *
     * @param memberInfoSeq 会員SEQ
     * @return 同時注文排他情報エンティティ
     */
    SimultaneousOrderExclusionEntity execute(Integer memberInfoSeq);
}
