/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetCutomerNoNextValLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#11 08_データ連携（顧客情報）会員情報の新規項目<br/>
 * 顧客番号取得ロジック実装クラス<br/>
 *
 * @author s.kume
 * @version $Revision:$
 */
@Component
public class MemberInfoGetCutomerNoNextValLogicImpl extends AbstractShopLogic
                implements MemberInfoGetCutomerNoNextValLogic {

    /** 会員情報カスタムDao */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoGetCutomerNoNextValLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 顧客番号を取得する<br/>
     * 顧客番号Seqを取得し、採番ルールに従って顧客番号を生成して取得<br/>
     *
     * @return 顧客番号
     */
    @Override
    public Integer execute() {

        // 顧客番号
        StringBuilder customerNo = new StringBuilder();

        // 顧客番号Seq取得
        Integer customerSeq = memberInfoDao.getCustomerSeqNextVal();
        customerNo.append(customerSeq);

        // 採番ルールに従ってチェックデジット付与
        customerNo.append(customerSeq % 7);

        return Integer.parseInt(customerNo.toString());
    }
}
// PDR Migrate Customization to here
