/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.member.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.member.SimultaneousOrderExclusionDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同時注文排他情報取得<br/>
 *
 * @author h_hakogi
 */
@Component
public class SimultaneousOrderExclusionGetLogicImpl extends AbstractShopLogic
                implements SimultaneousOrderExclusionGetLogic {

    /**
     * 同時注文排他Dao
     */
    private final SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    @Autowired
    public SimultaneousOrderExclusionGetLogicImpl(SimultaneousOrderExclusionDao simultaneousOrderExclusionDao) {
        this.simultaneousOrderExclusionDao = simultaneousOrderExclusionDao;
    }

    /**
     * 同時注文排他情報を取得する<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return 同時注文排他情報エンティティ
     */
    @Override
    public SimultaneousOrderExclusionEntity execute(Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 同時注文排他情報取得
        return simultaneousOrderExclusionDao.getEntity(memberInfoSeq);
    }
}
