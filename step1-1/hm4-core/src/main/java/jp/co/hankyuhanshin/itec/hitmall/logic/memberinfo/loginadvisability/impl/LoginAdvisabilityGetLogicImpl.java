// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.loginadvisability.LoginAdvisabilityDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PDR#023 顧客番号でのログイン<br/>
 *
 * <pre>
 * 会員情報取得ロジック
 *
 * 会員メールアドレス 又は 顧客番号で会員情報を取得するメソッドを追加
 * 会員ID 又は 顧客番号で会員情報を取得するメソッドを追加
 * 電話番号から会員情報を取得するメソッドを追加
 * 電話番号、顧客番号から会員情報を取得するメソッドを追加
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */
@Component
public class LoginAdvisabilityGetLogicImpl extends AbstractShopLogic implements LoginAdvisabilityGetLogic {

    /**
     * LoginAdvisabilityPdrDao<br/>
     */
    private final LoginAdvisabilityDao loginAdvisabilityDao;

    @Autowired
    public LoginAdvisabilityGetLogicImpl(LoginAdvisabilityDao loginAdvisabilityDao) {
        this.loginAdvisabilityDao = loginAdvisabilityDao;
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return ログイン可否判定エンティティ
     */
    @Override
    public LoginAdvisabilityEntity getLoginAdvisabilityPdrEntityByMemberInfo(MemberInfoEntity memberInfoEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoStatus", memberInfoEntity.getMemberInfoStatus());
        ArgumentCheckUtil.assertNotNull("approveStatus", memberInfoEntity.getApproveStatus());
        ArgumentCheckUtil.assertNotNull("onlineLoginAdvisability", memberInfoEntity.getOnlineLoginAdvisability());
        ArgumentCheckUtil.assertNotNull("memberListType", memberInfoEntity.getMemberListType());
        ArgumentCheckUtil.assertNotNull("accountingType", memberInfoEntity.getAccountingType());

        return loginAdvisabilityDao.getEntityByLoginAdvisability(
                        memberInfoEntity.getMemberInfoStatus().getValue(),
                        memberInfoEntity.getApproveStatus().getValue(),
                        memberInfoEntity.getOnlineLoginAdvisability().getValue(),
                        memberInfoEntity.getMemberListType().getValue(), memberInfoEntity.getAccountingType().getValue()
                                                                );
    }
}
// PDR Migrate Customization to here
