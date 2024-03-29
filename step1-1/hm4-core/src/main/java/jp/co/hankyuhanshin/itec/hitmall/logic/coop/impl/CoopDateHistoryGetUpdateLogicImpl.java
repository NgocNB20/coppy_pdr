// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.coop.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.coop.CoopDateHistoryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.coop.CoopDateHistoryGetUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.logic.AbstractLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 前回基幹連携日時取得・更新ロジック実装クラス
 *
 * @author s.kume(NSK)
 */
@Component
public class CoopDateHistoryGetUpdateLogicImpl extends AbstractLogic implements CoopDateHistoryGetUpdateLogic {

    /** 基幹連携日時履歴Dao */
    private final CoopDateHistoryDao coopDateHistoryDao;

    @Autowired
    public CoopDateHistoryGetUpdateLogicImpl(CoopDateHistoryDao coopDateHistoryDao) {
        this.coopDateHistoryDao = coopDateHistoryDao;
    }

    /**
     * 基幹連携IDをキーに前回連携日時情報を取得
     *
     * @param coopId 基幹連携ID
     * @return 前回連携日時情報
     */
    @Override
    public CoopDateHistoryEntity execute(String coopId) {

        return coopDateHistoryDao.getEntity(coopId);
    }

    /**
     * 前回連携日時情報更新
     *
     * @param coopDateHistoryEntity 基幹連携日時情報エンティティ
     */
    @Override
    public void execute(CoopDateHistoryEntity coopDateHistoryEntity) {
        coopDateHistoryDao.update(coopDateHistoryEntity);
    }
}
// PDR Migrate Customization to here
