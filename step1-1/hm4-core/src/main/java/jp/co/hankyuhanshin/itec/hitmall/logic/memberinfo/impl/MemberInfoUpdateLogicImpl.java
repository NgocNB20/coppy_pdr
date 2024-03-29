/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 会員情報更新
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class MemberInfoUpdateLogicImpl extends AbstractShopLogic implements MemberInfoUpdateLogic {

    /**
     * MemberInfoDao
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoUpdateLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 会員情報更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoEntity memberInfoEntity) {
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        return execute(memberInfoEntity, dateUtility.getCurrentTime());
    }

    /**
     * 会員情報更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @param currentTime      現在日時
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoEntity memberInfoEntity, Timestamp currentTime) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
        // 日時セット
        memberInfoEntity.setUpdateTime(currentTime);
        // 更新
        return memberInfoDao.update(memberInfoEntity);
    }

    /**
     * 会員ログイン情報更新処理
     *
     * @param memberInfoSeq 会員SEQ
     * @param userAgent     ユーザーエージェント
     * @return 更新件数
     */
    @Override
    public int execute(Integer memberInfoSeq, String userAgent, HTypeDeviceType deviceType) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("userAgent", userAgent);

        // 更新
        return memberInfoDao.updateLogin(memberInfoSeq, userAgent, deviceType);
    }

    /**
     * ログイン失敗回数を0にする
     * アカウントロック日時をnullにする
     * ログイン成功時、パスワードリセット時に呼び出される想定
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    @Override
    public int resetLoginFailureCount(Integer memberInfoSeq) {
        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);
        return memberInfoDao.updateLoginFailureCountReset(memberInfoSeq);
    }

    /**
     * ログイン失敗回数をインクリメントする
     * accountLockTime が渡されていれば、アカウントロック日時を更新する
     *
     * @param memberInfoSeq 会員SEQ
     * @param accountLockTime アカウントロック日時
     * @return 更新件数
     */
    @Override
    public int updateLoginFailureCount(Integer memberInfoSeq, Timestamp accountLockTime) {
        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);
        return memberInfoDao.updateLoginFailureCount(memberInfoSeq, accountLockTime);
    }

    /**
     * ログイン失敗回数をインクリメントする
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    @Override
    public int updateLoginFailureCount(Integer memberInfoSeq) {
        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);
        return updateLoginFailureCount(memberInfoSeq, null);
    }
}
