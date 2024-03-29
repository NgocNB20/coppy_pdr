/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会員情報データチェックサービス実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoDataCheckServiceImpl extends AbstractShopService implements MemberInfoDataCheckService {

    /**
     * 会員情報データチェックロジック
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    @Autowired
    public MemberInfoDataCheckServiceImpl(MemberInfoDataCheckLogic memberInfoDataCheckLogic) {
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
    }

    /**
     * サービス実行
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    @Override
    public void execute(MemberInfoEntity memberInfoEntity) {
        // パラメータチェック
        checkParameter(memberInfoEntity);

        // 会員ユニークIDの作成・セット
        Integer shopSeq = 1001;
        // ショップSEQ
        memberInfoEntity.setShopSeq(shopSeq);
        // 会員業務Helper取得
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        // ユニークID
        memberInfoEntity.setMemberInfoUniqueId(
                        memberInfoUtility.createShopUniqueId(shopSeq, memberInfoEntity.getMemberInfoMail()));

        // 会員情報データチェックロジック実行
        memberInfoDataCheckLogic.execute(memberInfoEntity);
    }

    /**
     * パラメータチェック
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    protected void checkParameter(MemberInfoEntity memberInfoEntity) {
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
    }
}
