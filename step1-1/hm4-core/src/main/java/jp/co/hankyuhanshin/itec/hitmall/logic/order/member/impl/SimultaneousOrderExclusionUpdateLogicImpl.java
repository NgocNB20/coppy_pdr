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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 同時注文排他情報更新<br/>
 *
 * @author h_hakogi
 */
@Component
public class SimultaneousOrderExclusionUpdateLogicImpl extends AbstractShopLogic
                implements SimultaneousOrderExclusionUpdateLogic {

    /**
     * 同時注文排他Dao
     */
    private final SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    /**
     * 日付関連ユーティリティ
     */
    private final DateUtility dateUtility;

    @Autowired
    public SimultaneousOrderExclusionUpdateLogicImpl(SimultaneousOrderExclusionDao simultaneousOrderExclusionDao,
                                                     DateUtility dateUtility) {
        this.simultaneousOrderExclusionDao = simultaneousOrderExclusionDao;
        this.dateUtility = dateUtility;
    }

    /**
     * 同時注文排他情報更新処理<br/>
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("simultaneousOrderExclusionEntity", simultaneousOrderExclusionEntity);
        // 日時セット
        simultaneousOrderExclusionEntity.setUpdateTime(dateUtility.getCurrentTime());
        // 更新
        return simultaneousOrderExclusionDao.update(simultaneousOrderExclusionEntity);
    }

}
