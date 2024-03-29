/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;

/**
 * ショップ情報取得<br/>
 *
 * @author ozaki
 * @author sakai
 * @version $Revision: 1.1 $
 */
public interface ShopGetLogic {

    /**
     * ショップ情報を取得する<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイト区分
     * @param openStatus 公開状態区分
     * @return ショップ情報エンティティ
     */
    ShopEntity execute(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus);

    /**
     * ショップ情報を取得する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return ショップ情報エンティティ
     */
    ShopEntity executeUseCache(Integer shopSeq);
}
