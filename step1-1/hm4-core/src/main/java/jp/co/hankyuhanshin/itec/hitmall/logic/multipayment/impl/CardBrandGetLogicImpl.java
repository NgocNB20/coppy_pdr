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
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * カードブランド情報取得Logicクラス<br/>
 *
 * @author ito
 */
@Component
public class CardBrandGetLogicImpl extends AbstractShopLogic implements CardBrandGetLogic {

    /**
     * カードブランドDao
     */
    private final CardBrandDao cardBrandDao;

    @Autowired
    public CardBrandGetLogicImpl(CardBrandDao cardBrandDao) {
        this.cardBrandDao = cardBrandDao;
    }

    /**
     * カードブランド情報を取得<br/>
     *
     * @param cardBrandCode クレジットカード会社コード
     * @return カードブランド情報エンティティ
     */
    @Override
    public CardBrandEntity execute(String cardBrandCode) {
        return cardBrandDao.getEntityByCardBrandCode(cardBrandCode);
    }

    /**
     * カードブランドリスト取得<br/>
     *
     * @param frontDisplayFlag Front表示フラグ
     * @return カードブランド情報リスト
     */
    @Override
    public List<CardBrandEntity> execute(boolean frontDisplayFlag) {
        return cardBrandDao.getCardBrandList(frontDisplayFlag);
    }

}
