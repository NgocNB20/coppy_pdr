/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * フリーエリア情報取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
@Component
public class FreeAreaGetLogicImpl extends AbstractShopLogic implements FreeAreaGetLogic {

    /**
     * フリーエリアDao<br/>
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaGetLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaKey フリーエリアキー
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(Integer shopSeq, String freeAreaKey) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaKey);

        // フリーエリア情報取得
        return freeAreaDao.getFreeAreaByKey(shopSeq, freeAreaKey);
    }

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(Integer shopSeq, Integer freeAreaSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("freeAreaSeq", freeAreaSeq);

        // フリーエリア情報取得
        return freeAreaDao.getFreeAreaByShopSeq(shopSeq, freeAreaSeq);
    }

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param freeAreaKey   フリーエリアキー
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(Integer shopSeq, String freeAreaKey, Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaKey);

        // フリーエリア情報取得
        return freeAreaDao.getFreeAreaByKeyAndMember(shopSeq, freeAreaKey, memberInfoSeq);
    }
}
