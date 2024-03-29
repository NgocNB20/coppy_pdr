/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.MemberInfoPasswordUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 会員パスワード変更サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.6 $
 */
@Service
public class MemberInfoPasswordUpdateServiceImpl extends AbstractShopService
                implements MemberInfoPasswordUpdateService {

    /**
     * 会員情報更新ロジック
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * 確認メール削除ロジック
     */
    private final ConfirmMailDeleteLogic confirmMailDeleteLogic;

    @Autowired
    public MemberInfoPasswordUpdateServiceImpl(MemberInfoUpdateLogic memberInfoUpdateLogic,
                                               ConfirmMailDeleteLogic confirmMailDeleteLogic) {
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.confirmMailDeleteLogic = confirmMailDeleteLogic;
    }

    /**
     * 会員パスワード変更処理<br/>
     * 現在のパスワードの照合処理を行う。
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @param password         現パスワード
     * @param newPassword      新パスワード
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoEntity memberInfoEntity, String password, String newPassword) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
        ArgumentCheckUtil.assertNotEmpty("password", password);
        ArgumentCheckUtil.assertNotEmpty("newPassword", newPassword);

        // 現在のパスワードの照合処理
        boolean passwordMatching = matchPassword(memberInfoEntity, password);

        int result = 0;
        // パスワード照合OK
        if (passwordMatching) {
            // 会員パスワード変更ロジック実行
            result = updateMemberInfoPassword(memberInfoEntity, newPassword);
            if (result == 0) {
                throwMessage(MSGCD_MEMBERINFOPASSWORD_UPDATE_ERROR);
            }

            // パスワード照合NG
        } else {
            throwMessage(MSGCD_MEMBERINFOPASSWORD_MATCH_ERROR);
        }

        return result;
    }

    /**
     * 会員パスワード変更処理<br/>
     *
     * @param confirmMailPassword 確認メールパスワード
     * @param memberInfoEntity    会員情報エンティティ
     * @param newPassword         新パスワード
     * @return 更新件数
     */
    @Override
    public int execute(String confirmMailPassword, MemberInfoEntity memberInfoEntity, String newPassword) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("confirmMailPassword", confirmMailPassword);
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoEntity.getMemberInfoSeq());
        ArgumentCheckUtil.assertNotEmpty("newPassword", newPassword);

        int result = 0;
        // 会員パスワード変更ロジック実行
        result = updateMemberInfoPassword(memberInfoEntity, newPassword);
        if (result == 0) {
            throwMessage(MSGCD_MEMBERINFOPASSWORD_UPDATE_ERROR);
        }

        int resultDelete = 0;
        // 確認メール削除ロジック実行
        resultDelete = confirmMailDeleteLogic.execute(confirmMailPassword);
        if (resultDelete == 0) {
            throwMessage(MSGCD_MEMBERINFOPASSWORD_UPDATE_ERROR);
        }

        return result;
    }

    /**
     * 現在のパスワードが正しいかどうかを照合します。
     *
     * @param memberInfoEntity 会員エンティティ
     * @param password         現在のパスワード
     * @return true..OK / false..NG
     */
    protected boolean matchPassword(MemberInfoEntity memberInfoEntity, String password) {

        // 入力パスワードとSpringSecurity準拠の方式で暗号化されたパスワードをAESPasswordEncoderにより比較
        PasswordEncoder passwordEncoder =
                        ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
        return passwordEncoder.matches(password, memberInfoEntity.getMemberInfoPassword());

    }

    /**
     * 会員情報のパスワード更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @param newPassword      新パスワード
     * @return 更新件数
     */
    protected int updateMemberInfoPassword(MemberInfoEntity memberInfoEntity, String newPassword) {

        // SpringSecurity準拠の方式で暗号化
        PasswordEncoder encoder = ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
        // パスワード暗号化
        String encryptedPassword = encoder.encode(newPassword);

        memberInfoEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.OFF);

        // 更新に失敗した時、パスワード設定済みのエンティティを保持しておきたくないからコピーしとこうかなっと。
        MemberInfoEntity copyMemberInfoEntity = CopyUtil.deepCopy(memberInfoEntity);
        copyMemberInfoEntity.setMemberInfoPassword(encryptedPassword);

        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);

        if (memberInfoUtility.isAvailableAccountLock() && copyMemberInfoEntity.getAccountLockTime() != null) {
            copyMemberInfoEntity.setLoginFailureCount(0);
            copyMemberInfoEntity.setAccountLockTime(null);
        }

        return memberInfoUpdateLogic.execute(copyMemberInfoEntity);
    }
}
