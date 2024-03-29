/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaViewableMemberDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaViewableMemberEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * フリーエリア表示対象会員登録ロジック<br/>
 */
@Component
public class FreeAreaViewableMemberRegistLogicImpl extends AbstractShopLogic
                implements FreeAreaViewableMemberRegistLogic {

    /**
     * フリーエリア表示対象会員Dao
     */
    private final FreeAreaViewableMemberDao freeAreaViewableMemberDao;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    @Autowired
    public FreeAreaViewableMemberRegistLogicImpl(DateUtility dateUtility,
                                                 FreeAreaViewableMemberDao freeAreaViewableMemberDao) {
        this.dateUtility = dateUtility;
        this.freeAreaViewableMemberDao = freeAreaViewableMemberDao;
    }

    /**
     * フリーエリア表示対象会員情報登録処理<br/>
     *
     * @param freeAreaSeq       フリーエリアSEQ
     * @param memberInfoSeqList 対象会員リスト
     * @return 処理件数
     */
    @Override
    public int execute(Integer freeAreaSeq, List<Integer> memberInfoSeqList) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("freeAreaSeq", freeAreaSeq);
        ArgumentCheckUtil.assertNotNull("memberInfoSeqList", memberInfoSeqList);

        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // フリーエリア表示対象会員情報登録
        for (Integer memberInfoSeq : memberInfoSeqList) {
            FreeAreaViewableMemberEntity entity = ApplicationContextUtility.getBean(FreeAreaViewableMemberEntity.class);

            // フリーエリアSEQを設定
            entity.setFreeAreaSeq(freeAreaSeq);
            // 会員SEQを設定
            entity.setMemberInfoSeq(memberInfoSeq);
            // ショップSEQを設定
            entity.setShopSeq(shopSeq);

            entity.setRegistTime(currentTime);
            entity.setUpdateTime(currentTime);

            freeAreaViewableMemberDao.insert(entity);
        }
        return 0;
    }

}
