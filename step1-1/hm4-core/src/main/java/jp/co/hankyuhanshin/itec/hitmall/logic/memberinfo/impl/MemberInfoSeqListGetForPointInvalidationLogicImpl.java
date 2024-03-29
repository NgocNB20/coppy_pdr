/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoSeqListGetForPointInvalidationLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * 【ポイント部品】<br/>
 * 無効化条件を満たす会員SEQリスト取得ロジックの実装クラス。
 *
 * @author k.harada
 */
@Component
public class MemberInfoSeqListGetForPointInvalidationLogicImpl extends AbstractShopLogic
                implements MemberInfoSeqListGetForPointInvalidationLogic {

    /**
     * 会員情報DAO
     */
    private final MemberInfoDao memberInfoDao;

    /**
     * 日付関連ユーティリティ
     */
    private final DateUtility dateUtility;

    @Autowired
    public MemberInfoSeqListGetForPointInvalidationLogicImpl(MemberInfoDao memberInfoDao, DateUtility dateUtility) {
        this.memberInfoDao = memberInfoDao;
        this.dateUtility = dateUtility;
    }

    /**
     * 期限切れポイント無効化バッチの無効化条件を満たす会員SEQリストを取得する。
     *
     * @return 無効化条件を満たす会員SEQリスト
     */
    @Override
    public List<Integer> execute() {
        // 現在日付（yyyy-MM-dd 00:00:00.0）を取得
        Timestamp checkeDate = dateUtility.getCurrentDate();
        // 無効化条件を満たす会員SEQリストを取得
        return memberInfoDao.getSeqListForPointInvalidation(checkeDate, false);
    }

}
