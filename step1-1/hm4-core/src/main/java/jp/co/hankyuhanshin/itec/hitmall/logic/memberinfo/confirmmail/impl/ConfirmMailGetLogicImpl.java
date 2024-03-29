/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.confirmmail.ConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 確認メール情報取得<br/>
 *
 * @author natume
 */
@Component
public class ConfirmMailGetLogicImpl extends AbstractShopLogic implements ConfirmMailGetLogic {

    /**
     * ConfirmMailDao<br/>
     */
    private final ConfirmMailDao confirmMailDao;

    @Autowired
    public ConfirmMailGetLogicImpl(ConfirmMailDao confirmMailDao) {
        this.confirmMailDao = confirmMailDao;
    }

    /**
     * 確認メール情報取得処理<br/>
     *
     * @param password メールパスワード
     * @return 確認メールエンティティ
     */
    @Override
    public ConfirmMailEntity execute(String password) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("password", password);

        // 有効な確認メール情報取得
        return confirmMailDao.getEntityByPassword(password);
    }

    /**
     * 確認メール情報取得処理<br/>
     *
     * @param password        メールパスワード
     * @param confirmMailType 確認メール種別
     * @return 確認メールエンティティ
     */
    @Override
    public ConfirmMailEntity execute(String password, HTypeConfirmMailType confirmMailType) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("password", password);
        ArgumentCheckUtil.assertNotNull("confirmMailType", confirmMailType);

        // 有効な確認メール情報取得
        return confirmMailDao.getEntityByType(password, confirmMailType);
    }

}
