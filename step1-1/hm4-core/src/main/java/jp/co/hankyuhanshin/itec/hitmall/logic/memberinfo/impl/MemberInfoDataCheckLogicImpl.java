/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会員情報データチェック実装<br/>
 *
 * @author natume
 * @author Iwama (Itec) 2010/08/25 チケット #2132 対応
 * @version $Revision: 1.4 $
 */
@Component
public class MemberInfoDataCheckLogicImpl extends AbstractShopLogic implements MemberInfoDataCheckLogic {

    /**
     * MemberInfoDao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    @Autowired
    public MemberInfoDataCheckLogicImpl(MemberInfoDao memberInfoDao) {
        this.memberInfoDao = memberInfoDao;
    }

    /**
     * 会員情報データチェック処理<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    @Override
    public void execute(MemberInfoEntity memberInfoEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);

        // ユニークIDで会員情報取得
        MemberInfoEntity memberInfoEntityDb =
                        memberInfoDao.getEntityByMemberInfoUniqueId(memberInfoEntity.getMemberInfoUniqueId());

        // ID重複エラーチェック
        if (memberInfoEntity.getMemberInfoSeq() == null) {

            // 登録の場合 DBの会員情報が取得できた場合エラー
            if (memberInfoEntityDb != null) {
                addErrorMessage(MSGCD_MEMBERINFO_ID_MULTI);
            }

        } else {
            // PDR Migrate Customization from here
            // PKGでは「退会していない会員に対してのみチェックする」とあるがPDRではEC会員新規登録時、会員ステータスが「1:退会」状態で登録される。
            // 管理画面での更新を考慮し、会員状態をチェックしないこととする

            // 更新の場合 会員SEQが異なる場合エラー
            if (memberInfoEntityDb != null && !memberInfoEntity.getMemberInfoSeq()
                                                               .equals(memberInfoEntityDb.getMemberInfoSeq())) {
                addErrorMessage(MSGCD_MEMBERINFO_ID_MULTI);
            }
            // PDR Migrate Customization to here
        }

        // 例外出力
        if (hasErrorList()) {
            throwMessage();
        }

    }

    /**
     * 会員情報データチェック処理<br/>
     * 暫定会員情報も取得し、該当メールアドレスの入会中会員データが存在するかチェック
     *
     * @param memberInfoMail メールアドレス
     */
    @Override
    public void execute(String memberInfoMail, HTypeMemberInfoStatus memberinfoStatus) {

        // メールアドレスと会員ステータスで会員情報取得
        MemberInfoEntity memberInfoEntityByMail = memberInfoDao.getEntityByMailStatus(memberInfoMail, memberinfoStatus);

        // DBの会員情報が取得できた場合エラー
        if (memberInfoEntityByMail != null) {
            addErrorMessage(MSGCD_MEMBERINFO_ID_MULTI);
        }

        // 例外出力
        if (hasErrorList()) {
            throwMessage();
        }
    }
}
