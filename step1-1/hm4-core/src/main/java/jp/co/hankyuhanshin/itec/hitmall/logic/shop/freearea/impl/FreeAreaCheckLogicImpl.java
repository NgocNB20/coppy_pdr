/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * フリーエリアデータチェック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class FreeAreaCheckLogicImpl extends AbstractShopLogic implements FreeAreaCheckLogic {

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaCheckLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリアデータチェック<br />
     * 登録/更新前のデータチェックを行います。<br />
     * <ul>
     *  <li>重複チェック</li>
     * </ul>
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    @Override
    public void execute(FreeAreaEntity freeAreaEntity) {

        // パラメータチェック
        this.checkParam(freeAreaEntity);

        // 重複チェック
        this.checkDuplicate(freeAreaEntity);

        return;
    }

    /**
     * パラメータチェック
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    protected void checkParam(FreeAreaEntity freeAreaEntity) {

        ArgumentCheckUtil.assertNotNull("FreeAreaEntity", freeAreaEntity);
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", freeAreaEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaEntity.getFreeAreaKey());
        ArgumentCheckUtil.assertNotNull("openStartTime", freeAreaEntity.getOpenStartTime());
    }

    /**
     * 重複チェック
     * (キー項目が同一のフリーエリアが既に存在しないかをチェック)
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    protected void checkDuplicate(FreeAreaEntity freeAreaEntity) {
        // フリーエリア重複チェック
        List<FreeAreaEntity> list = freeAreaDao.getSameKeyOpenStartTimeEntityList(freeAreaEntity);
        // 結果取得時
        if (list.size() > 0) {
            throwMessage(MSGCD_DUPLICATE_FREEAREA);
        }
    }

}
