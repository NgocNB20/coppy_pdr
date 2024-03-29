/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoMailDuplicationCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会員メールアドレス重複チェック<br/>
 *
 * @author kaneda
 */
@Component
public class MemberInfoMailDuplicationCheckLogicImpl extends AbstractShopLogic
                implements MemberInfoMailDuplicationCheckLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoMailDuplicationCheckLogicImpl.class);

    /**
     * 会員情報取得サービス
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 会員データチェックロジック
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    /**
     * コンストラクタ
     *
     * @param memberInfoGetService
     * @param memberInfoDataCheckLogic
     */
    @Autowired
    public MemberInfoMailDuplicationCheckLogicImpl(MemberInfoGetService memberInfoGetService,
                                                   MemberInfoDataCheckLogic memberInfoDataCheckLogic) {
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
    }

    /**
     * メールアドレス重複チェック<br/>
     * 引数として渡されたメールアドレスが、
     * 会員マスタに登録済みでないかチェックする<br/>
     * <p>
     * 注文フローにて呼び出されることを想定<br/>
     *
     * @param orderMail ご注文主メールアドレス
     */
    @Override
    public void execute(String orderMail, Integer memberInfoSeq) {

        // 検証用の会員Entity
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        // 登録中のメールアドレスと、入力された注文主のメールアドレスが異なっている場合
        if (!orderMail.equals(memberInfoEntity.getMemberInfoMail())) {
            // 会員メールアドレス重複チェック実行
            try {
                // 会員業務Helper取得
                MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
                // 会員一意IDをセット
                memberInfoEntity.setMemberInfoUniqueId(memberInfoUtility.createShopUniqueId(1001, orderMail));

                // 会員データチェック実行
                memberInfoDataCheckLogic.execute(memberInfoEntity);

            } catch (AppLevelListException ae) {
                LOGGER.error("例外処理が発生しました", ae);
                throwMessage(MemberInfoDataCheckLogic.MSGCD_MEMBERINFO_ID_MULTI);
            }
        }

    }
}
