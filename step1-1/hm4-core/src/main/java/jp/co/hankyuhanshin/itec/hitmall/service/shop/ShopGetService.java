/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;

/**
 * ショップ情報取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface ShopGetService {
    // SSS0006

    /**
     * ショップなしエラー<br/>
     * <code>MSGCD_SHOP_NONE_FAIL</code>
     */
    String MSGCD_SHOP_NONE_FAIL = "SSS000601";

    /**
     * システムプロパティのショップSEQを元にショップ情報を取得する<br/>
     *
     * @return ショップエンティティ
     */
    ShopEntity execute();
}
