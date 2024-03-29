/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;

/**
 * ショップ情報更新<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface ShopUpdateService {

    /**
     * ショップなしエラー<br/>
     * <code>MSGCD_SHOP_NONE_FAIL</code>
     */
    String MSGCD_SHOP_NONE_FAIL = "SSS000701";

    /**
     * システムプロパティのショップSEQを元にショップ情報を取得する<br/>
     *
     * @param shopEntity ショップエンティティ
     * @return 更新した件数
     */
    int execute(ShopEntity shopEntity);
}
