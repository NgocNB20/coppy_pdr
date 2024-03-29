/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.conveni.ConvenienceDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * コンビニ名称取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
@Component
public class ConvenienceLogicImpl extends AbstractShopLogic implements ConvenienceLogic {

    /**
     * コンビニ名称Dao<br/>
     */
    private final ConvenienceDao convenienceDao;

    @Autowired
    public ConvenienceLogicImpl(ConvenienceDao convenienceDao) {
        this.convenienceDao = convenienceDao;
    }

    /**
     * コンビニ名称取得処理<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param conveniCode 選択コンビニコード
     * @return コンビニ名称エンティティ
     */
    @Override
    public ConvenienceEntity execute(Integer shopSeq, String conveniCode) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("conveniCode", conveniCode);

        // コンビニ名称取得
        return convenienceDao.getEntityByConveniCode(shopSeq, conveniCode);
    }

    /**
     * コンビニ名称リスト取得処理<br/>
     *
     * @return コンビニ名称エンティティリスト
     */
    @Override
    public List<ConvenienceEntity> getConvenienceList() {

        // コンビニ名称リスト取得
        return convenienceDao.getConvenienceList(1001, false);
    }

}
