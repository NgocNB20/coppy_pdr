/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoMailUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会員メールアドレス変更サービス(本登録)実装<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoMailUpdateServiceImpl extends AbstractShopService implements MemberInfoMailUpdateService {

    /**
     * 会員データチェックロジック<br/>
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    /**
     * 会員情報更新ロジック<br/>
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * 確認メール削除ロジック
     */
    private final ConfirmMailDeleteLogic confirmMailDeleteLogic;

    @Autowired
    public MemberInfoMailUpdateServiceImpl(MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                           MemberInfoUpdateLogic memberInfoUpdateLogic,
                                           ConfirmMailDeleteLogic confirmMailDeleteLogic) {
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.confirmMailDeleteLogic = confirmMailDeleteLogic;
    }

    /**
     * 会員メールアドレス変更処理<br/>
     *
     * @param memberInfoEntity    会員エンティティ
     * @param confirmMailPassword 確認メールパスワード
     */
    @Override
    public void execute(MemberInfoEntity memberInfoEntity, String confirmMailPassword) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
        ArgumentCheckUtil.assertNotNull("confirmMailPassword", confirmMailPassword);

        // 会員データチェック
        memberInfoDataCheckLogic.execute(memberInfoEntity.getMemberInfoMail(), HTypeMemberInfoStatus.ADMISSION);

        // 会員情報更新
        int result = memberInfoUpdateLogic.execute(memberInfoEntity);
        if (result == 0) {
            throwMessage(MSGCD_MEMBERINFO_UPDATE_ERROR);
        }

        int resultDelete = 0;
        // 確認メール削除ロジック実行
        resultDelete = confirmMailDeleteLogic.execute(confirmMailPassword);
        if (resultDelete == 0) {
            // 2023-renew No0 from here
            throwMessage(MSGCD_CONFIRMMAIL_DELETE_ERROR);
            // 2023-renew No0 to here
        }
    }

}

