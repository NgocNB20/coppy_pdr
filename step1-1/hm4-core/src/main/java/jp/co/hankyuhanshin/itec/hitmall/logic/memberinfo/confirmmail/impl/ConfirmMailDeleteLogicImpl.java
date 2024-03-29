/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.confirmmail.ConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 確認メール情報削除<br/>
 *
 * @author hasegawa(itec)
 */
@Component
public class ConfirmMailDeleteLogicImpl extends AbstractShopLogic implements ConfirmMailDeleteLogic {

    /**
     * 確認メールDaoクラス
     */
    private final ConfirmMailDao confirmMailDao;

    @Autowired
    public ConfirmMailDeleteLogicImpl(ConfirmMailDao confirmMailDao) {
        this.confirmMailDao = confirmMailDao;
    }

    /**
     * 確認メール情報削除処理<br/>
     *
     * @param confirmMailPassword 確認メールパスワード
     * @return 削除件数
     */
    @Override
    public int execute(String confirmMailPassword) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("confirmmailpassword", confirmMailPassword);

        // 有効な確認メール情報取得
        return confirmMailDao.deleteByConfirmMailPassword(confirmMailPassword);
    }
}
