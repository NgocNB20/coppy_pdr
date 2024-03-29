/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoSeqListGetForPointInvalidationNotificationLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * 【ポイント部品】<br/>
 * ポイント有効期限告知対象の会員SEQリスト取得ロジックの実装クラス。
 *
 * @author k.kizaki
 */
@Component
public class MemberInfoSeqListGetForPointInvalidationNotificationLogicImpl extends AbstractShopLogic
                implements MemberInfoSeqListGetForPointInvalidationNotificationLogic {

    /**
     * 会員情報DAO
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoSeqListGetForPointInvalidationNotificationLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * ポイント有効期限告知対象の会員SEQリストを取得する。
     * <pre>
     * 会員状態 == 入会 かつ
     * 《[ポイントの有効期限日]の最大値  = 指定日》
     * 保有ポイント数（合計ポイント数の和）が1以上となる会員が対象
     * </pre>
     *
     * @param checkeDate 指定日
     * @return 会員SEQリスト
     */
    @Override
    public List<Integer> execute(Timestamp checkeDate) {
        ArgumentCheckUtil.assertNotNull("checkeDate", checkeDate);
        return memberInfoDao.getSeqListForPointInvalidation(checkeDate, true);
    }

}
