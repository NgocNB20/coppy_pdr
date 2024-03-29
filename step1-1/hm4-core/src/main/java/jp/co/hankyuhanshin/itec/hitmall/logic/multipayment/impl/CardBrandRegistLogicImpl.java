/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.CardBrandDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.CardBrandEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カードブランド情報登録Logicクラス<br/>
 *
 * @author ito
 */
@Component
public class CardBrandRegistLogicImpl extends AbstractShopLogic implements CardBrandRegistLogic {

    /**
     * カードブランドDao
     */
    private final CardBrandDao cardBrandDao;

    @Autowired
    public CardBrandRegistLogicImpl(CardBrandDao cardBrandDao) {
        this.cardBrandDao = cardBrandDao;
    }

    /**
     * カードブランド情報を登録<br/>
     *
     * @param cardBrandEntity カードブランド情報エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(CardBrandEntity cardBrandEntity) {
        return cardBrandDao.insert(cardBrandEntity);
    }

}
