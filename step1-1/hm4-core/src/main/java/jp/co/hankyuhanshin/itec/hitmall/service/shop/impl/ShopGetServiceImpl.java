/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.ShopGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ショップ情報取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Service
public class ShopGetServiceImpl extends AbstractShopService implements ShopGetService {

    /**
     * ショップ情報取得ロジック
     */
    private final ShopGetLogic shopGetLogic;

    @Autowired
    public ShopGetServiceImpl(ShopGetLogic shopGetLogic) {
        this.shopGetLogic = shopGetLogic;
    }

    /**
     * システムプロパティのショップSEQを元にショップ情報を取得する<br/>
     *
     * @return ショップエンティティ
     */
    @Override
    public ShopEntity execute() {

        // (1) 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;

        // (2) ショップ情報を取得する
        ShopEntity shopEntity = shopGetLogic.execute(shopSeq, null, null);

        if (shopEntity == null) {
            // ショップなしエラー
            throwMessage(MSGCD_SHOP_NONE_FAIL, new Object[] {shopSeq.toString()});
        }

        return shopEntity;
    }
}
