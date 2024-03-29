// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.loginadvisability.LoginAdvisabilityDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * <pre>
 * ログイン不可の会員情報取得ロジック
 *
 *  * </pre>
 *
 * @author st75001
 * @version $Revision:$
 */
@Component
public class LoginAdvisabilityGetCanNotLoginMemberLogicImpl extends AbstractShopLogic implements LoginAdvisabilityGetCanNotLoginMemberLogic {

    /**
     * LoginAdvisabilityPdrDao<br/>
     */
    private final LoginAdvisabilityDao loginAdvisabilityDao;

    @Autowired
    public LoginAdvisabilityGetCanNotLoginMemberLogicImpl(LoginAdvisabilityDao loginAdvisabilityDao) {
        this.loginAdvisabilityDao = loginAdvisabilityDao;
    }

    /**
     * ログイン不可の会員情報を取得する<br />
     *
     * @param customerNoList 顧客番号リスト
     * @return 顧客番号リスト
     */
    @Override
    public List<Integer> getLoginAdvisabilityGetCanNotLoginMemberLogic(List<Integer> customerNoList) {

        return loginAdvisabilityDao.getCanNotLoginMember(customerNoList);

    }

    /**
     * ログイン不可の会員情報を取得する（会員SEQ）<br />
     *
     * @param memberInfoSeqList 会員SEQリスト
     * @return 会員SEQリスト
     */
    @Override
    public List<Integer> getLoginAdvisabilityGetCanNotLoginMemberInfoSeqLogic(List<Integer> memberInfoSeqList) {

        return loginAdvisabilityDao.getCanNotLoginMemberInfoSeq(memberInfoSeqList);

    }
}
// PDR Migrate Customization to here
