/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 会員情報更新サービス実装クラス
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
@Service
public class MemberInfoUpdateServiceImpl extends AbstractShopService implements MemberInfoUpdateService {

    /**
     * 会員情報更新ロジック
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    @Autowired
    public MemberInfoUpdateServiceImpl(MemberInfoUpdateLogic memberInfoUpdateLogic) {
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
    }

    /**
     * 会員情報更新処理
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoEntity memberInfoEntity) {
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        return execute(memberInfoEntity, dateUtility.getCurrentTime());
    }

    /**
     * 更新日時指定付き会員情報更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @param currentTime      現在日時
     * @return 更新件数
     */
    @Override
    public int execute(MemberInfoEntity memberInfoEntity, Timestamp currentTime) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);

        // 会員情報更新
        int result = memberInfoUpdateLogic.execute(memberInfoEntity, currentTime);
        if (result == 0) {
            throwMessage(MSGCD_MEMBERINFO_UPDATE_FAIL);
        }
        return result;
    }

}
