/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 会員情報リスト取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
@Component
public class MemberInfoListGetLogicImpl extends AbstractShopLogic implements MemberInfoListGetLogic {

    /**
     * 会員Dao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoListGetLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 会員情報リストを取得する<br />
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員情報エンティティリスト
     */
    @Override
    public List<MemberInfoEntity> execute(List<Integer> memberInfoSeqList) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("memberInfoSeqList", memberInfoSeqList);

        // 取得
        return memberInfoDao.getEntityListByMemberInfoSeqList(memberInfoSeqList);
    }
}
