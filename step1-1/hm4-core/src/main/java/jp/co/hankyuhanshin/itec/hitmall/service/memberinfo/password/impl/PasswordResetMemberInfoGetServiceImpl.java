/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.PasswordResetMemberInfoGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * パスワード再設定メール送信サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
@Service
public class PasswordResetMemberInfoGetServiceImpl extends AbstractShopService
                implements PasswordResetMemberInfoGetService {

    /**
     * 確認メール情報取得ロジック<br/>
     */
    private final ConfirmMailGetLogic confirmMailGetLogic;

    /**
     * 会員情報取得ロジック<br/>
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    @Autowired
    public PasswordResetMemberInfoGetServiceImpl(ConfirmMailGetLogic confirmMailGetLogic,
                                                 MemberInfoGetLogic memberInfoGetLogic) {
        this.confirmMailGetLogic = confirmMailGetLogic;
        this.memberInfoGetLogic = memberInfoGetLogic;
    }

    /**
     * パスワード再設定会員情報取得処理<br/>
     *
     * @param password パスワード
     * @return 会員エンティティ
     */
    @Override
    public MemberInfoEntity execute(String password) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("password", password);

        // 確認メール情報取得
        ConfirmMailEntity confirmMailEntity =
                        confirmMailGetLogic.execute(password, HTypeConfirmMailType.PASSWORD_REISSUE);
        if (confirmMailEntity == null) {
            throwMessage(MSGCD_CONFIRMMAILENTITYDTO_NULL);
        }

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(confirmMailEntity.getMemberInfoSeq(),
                                                                       HTypeMemberInfoStatus.ADMISSION
                                                                      );
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFOENTITYDTO_NULL);
        }
        return memberInfoEntity;
    }
}
