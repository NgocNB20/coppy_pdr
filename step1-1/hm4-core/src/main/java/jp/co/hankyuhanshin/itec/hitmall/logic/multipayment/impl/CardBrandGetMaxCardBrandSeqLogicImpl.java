/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.CardBrandDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MAXカードブランドSEQ取得Logicクラス<br/>
 *
 * @author ito
 */
@Component
public class CardBrandGetMaxCardBrandSeqLogicImpl extends AbstractShopLogic
                implements CardBrandGetMaxCardBrandSeqLogic {

    /**
     * カードブランドDao
     */
    private final CardBrandDao cardBrandDao;

    @Autowired
    public CardBrandGetMaxCardBrandSeqLogicImpl(CardBrandDao cardBrandDao) {
        this.cardBrandDao = cardBrandDao;
    }

    /**
     * MAXカードブランドSEQを取得<br/>
     *
     * @return MAXカードブランドSEQ
     */
    @Override
    public int execute() {
        return cardBrandDao.getMaxCardBrandSeq();
    }

}
