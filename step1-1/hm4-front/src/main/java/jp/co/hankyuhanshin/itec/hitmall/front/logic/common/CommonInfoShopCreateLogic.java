/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.common;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoShop;

/**
 * ショップ情報作成ロジック(共通情報)<br/>
 *
 * @author natume
 * @author sakai
 * @version $Revision: 1.2 $
 */
public interface CommonInfoShopCreateLogic {

    /**
     * ショップ情報作成処理<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイトタイプ
     * @param openStatus 公開状態
     * @return ショップ情報(共通情報)
     */
    // CommonInfoShop execute(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus);

    /**
     * ショップ情報作成処理<br/>
     *
     * @param shopSeq ショップSEQ
     * @return ショップ情報(共通情報)
     */
    CommonInfoShop execute(Integer shopSeq);
}
