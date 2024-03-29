/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoAccessUidClearLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 端末識別番号クリアロジック<br/>
 * ※携帯端末で利用
 *
 * @version $Revision: 1.1 $
 */
@Component
public class MemberInfoAccessUidClearLogicImpl extends AbstractShopLogic implements MemberInfoAccessUidClearLogic {

    /**
     * 会員Dao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoAccessUidClearLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 端末識別番号のクリアを行う<br/>
     *
     * @param accessUid 携帯識別番号
     * @return 更新件数
     */
    @Override
    public int execute(String accessUid) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("accessUid", accessUid);

        // 端末識別番号クリア
        return memberInfoDao.updateAccessUidClear(accessUid);
    }

}
