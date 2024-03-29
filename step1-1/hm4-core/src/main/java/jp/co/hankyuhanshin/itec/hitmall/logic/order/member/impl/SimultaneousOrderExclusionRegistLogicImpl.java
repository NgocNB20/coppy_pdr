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
import jp.co.hankyuhanshin.itec.hitmall.logic.order.member.SimultaneousOrderExclusionRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 同時注文排他登録<br/>
 *
 * @author h_hakogi
 */
@Component
public class SimultaneousOrderExclusionRegistLogicImpl extends AbstractShopLogic
                implements SimultaneousOrderExclusionRegistLogic {

    /**
     * 同時注文排他Dao
     */
    private final SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    /**
     * 日付関連ユーティリティ
     */
    private final DateUtility dateUtility;

    @Autowired
    public SimultaneousOrderExclusionRegistLogicImpl(SimultaneousOrderExclusionDao simultaneousOrderExclusionDao,
                                                     DateUtility dateUtility) {
        this.simultaneousOrderExclusionDao = simultaneousOrderExclusionDao;
        this.dateUtility = dateUtility;
    }

    /**
     * 同時注文排他情報登録処理<br/>
     *
     * @param simultaneousOrderExclusionEntity 同時注文排他エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("simultaneousOrderExclusionEntity", simultaneousOrderExclusionEntity);

        // 日時セット
        Timestamp currentTime = dateUtility.getCurrentTime();
        simultaneousOrderExclusionEntity.setRegistTime(currentTime);
        simultaneousOrderExclusionEntity.setUpdateTime(currentTime);

        // 登録
        return simultaneousOrderExclusionDao.insert(simultaneousOrderExclusionEntity);
    }

}
