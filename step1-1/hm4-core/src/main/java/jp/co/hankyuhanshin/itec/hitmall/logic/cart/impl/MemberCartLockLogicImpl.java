/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.MemberCartLockLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * メンバーのカートをロックします<br/>
 *
 * @author is40701
 */
@Component
// PDR Migrate Customization from here
public class MemberCartLockLogicImpl extends AbstractShopLogic implements MemberCartLockLogic {

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public MemberCartLockLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * メンバーのカートをロックします<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 1 失敗 / 0 成功
     */
    public int execute(Integer memberInfoSeq) {
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        Integer shopSeq = 1001;
        cartGoodsDao.lock(shopSeq, memberInfoSeq);
        return 0;
    }

}
// PDR Migrate Customization to here
