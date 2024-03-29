/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCountGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * フリーエリア表示対象会員件数取得ロジック<br/>
 */
@Component
public class FreeAreaViewableMemberCountGetLogicImpl extends AbstractShopLogic
                implements FreeAreaViewableMemberCountGetLogic {

    /**
     * フリーエリア表示対象会員Dao
     */
    private final FreeAreaViewableMemberDao freeAreaViewableMemberDao;

    @Autowired
    public FreeAreaViewableMemberCountGetLogicImpl(FreeAreaViewableMemberDao freeAreaViewableMemberDao) {
        this.freeAreaViewableMemberDao = freeAreaViewableMemberDao;
    }

    /**
     * フリーエリア表示対象会員件数取得処理
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return 件数
     */
    @Override
    public int execute(Integer freeAreaSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("freeAreaSeq", freeAreaSeq);

        // 件数取得
        return freeAreaViewableMemberDao.getCountByFreeAreaSeq(freeAreaSeq);
    }

}
