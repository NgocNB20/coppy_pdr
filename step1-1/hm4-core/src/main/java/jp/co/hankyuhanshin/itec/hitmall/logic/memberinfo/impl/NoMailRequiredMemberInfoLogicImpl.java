/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * メール不要の会員情報取得ロジック
 *
 * @author st75001
 */
@Component
public class NoMailRequiredMemberInfoLogicImpl extends AbstractShopLogic implements NoMailRequiredMemberInfoLogic {

    /**
     * MemberInfoDao
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public NoMailRequiredMemberInfoLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * メール不要の会員情報を取得する<br/>
     *
     * @param customerNoList 顧客番号リスト
     * @return 登録件数
     */
    @Override
    public List<Integer> getNoMailRequiredMemberInfoLogic(List<Integer> customerNoList) {
        return memberInfoDao.getNoMailRequiredMemberInfo(customerNoList);
    }

    /**
     * メール不要の会員情報を取得する(会員SEQ)<br/>
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 登録件数
     */
    @Override
    public List<Integer> getNoMailRequiredMemberInfoSeqLogic(List<Integer> memberInfoSeqList){
        return memberInfoDao.getNoMailRequiredMemberInfoSeq(memberInfoSeqList);

    }
}
