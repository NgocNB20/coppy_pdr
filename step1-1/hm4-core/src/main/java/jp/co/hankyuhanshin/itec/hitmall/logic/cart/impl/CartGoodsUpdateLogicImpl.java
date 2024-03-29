/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * カート商品更新<br/>
 * カートに商品を更新する。<br/>
 *
 * @author h_hakogi
 */
@Component
public class CartGoodsUpdateLogicImpl extends AbstractShopLogic implements CartGoodsUpdateLogic {

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsUpdateLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート商品登録<br/>
     * カートに商品を登録する。<br/>
     *
     * @param cartGoodsEntity カート商品エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(CartGoodsEntity cartGoodsEntity) {

        // パラメータチェック
        // カート商品エンティティがnullでないことをチェック
        ArgumentCheckUtil.assertNotNull("cartGoodsEntity", cartGoodsEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日をセット
        // サーバの現在日時を下記項目にセットする
        // ・カート商品エンティティ．更新日時
        Timestamp now = dateUtility.getCurrentTime();
        cartGoodsEntity.setUpdateTime(now);

        // カート商品更新
        return cartGoodsDao.update(cartGoodsEntity);
    }
}
