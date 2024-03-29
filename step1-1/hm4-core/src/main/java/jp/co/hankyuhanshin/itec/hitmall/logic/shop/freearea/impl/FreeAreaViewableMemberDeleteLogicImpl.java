/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * フリーエリア表示対象会員削除ロジック<br/>
 */
@Component
public class FreeAreaViewableMemberDeleteLogicImpl extends AbstractShopLogic
                implements FreeAreaViewableMemberDeleteLogic {

    /**
     * フリーエリア表示対象会員Dao
     */
    private final FreeAreaViewableMemberDao freeAreaViewableMemberDao;

    @Autowired
    public FreeAreaViewableMemberDeleteLogicImpl(FreeAreaViewableMemberDao freeAreaViewableMemberDao) {
        this.freeAreaViewableMemberDao = freeAreaViewableMemberDao;
    }

    /**
     * フリーエリア表示対象会員削除
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return 処理件数
     */
    @Override
    public int execute(Integer freeAreaSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("freeAreaSeq", freeAreaSeq);

        // 削除処理
        return freeAreaViewableMemberDao.deleteByFreeAreaSeq(freeAreaSeq);
    }
}
