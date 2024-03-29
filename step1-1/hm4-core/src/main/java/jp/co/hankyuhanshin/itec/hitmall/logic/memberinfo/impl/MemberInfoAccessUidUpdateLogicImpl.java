/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoAccessUidUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 端末識別番号更新ロジック<br/>
 * ※携帯端末で利用
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
@Component
public class MemberInfoAccessUidUpdateLogicImpl extends AbstractShopLogic implements MemberInfoAccessUidUpdateLogic {

    /**
     * 会員Dao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoAccessUidUpdateLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 端末識別番号の更新処理を行う<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     携帯識別番号
     * @return 更新件数
     */
    @Override
    public int execute(Integer memberInfoSeq, String accessUid) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 端末識別番号更新 null更新許可
        return memberInfoDao.updateAccessUid(memberInfoSeq, accessUid);
    }
}
