/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaGetForBackService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * フリーエリア取得(管理機能用)
 *
 * @author shibuya
 * @version $Revision: 1.2 $
 */
@Service
public class FreeAreaGetForBackServiceImpl extends AbstractShopService implements FreeAreaGetForBackService {

    /**
     * フリーエリア取得ロジック
     */
    private final FreeAreaGetLogic freeAreaGetLogic;

    @Autowired
    public FreeAreaGetForBackServiceImpl(FreeAreaGetLogic freeAreaGetLogic) {
        this.freeAreaGetLogic = freeAreaGetLogic;
    }

    /**
     * フリーエリア取得(管理機能用)
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(Integer freeAreaSeq) {

        // パラメータチェック
        this.checkParam(freeAreaSeq);

        // 共通情報の取得
        Integer shopSeq = 1001;

        // 取得処理
        FreeAreaEntity freeAreaEntity = freeAreaGetLogic.execute(shopSeq, freeAreaSeq);
        if (freeAreaEntity == null) {
            throwMessage(MSGCD_FREEAREA_GET_FAIL);
        }

        return freeAreaEntity;
    }

    /**
     * パラメータチェック
     *
     * @param freeAreaSeq フリーエリアSEQ
     */
    protected void checkParam(Integer freeAreaSeq) {
        // 検索条件
        ArgumentCheckUtil.assertGreaterThanZero("freeAreaSeq", freeAreaSeq);

    }
}
