/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 消費税情報取得Logic<br/>
 *
 * @author kumazawa
 */
public interface TaxGetLogic {

    /**
     * 現在施行中の消費税情報（標準税率）を取得する<br/>
     *
     * @return 消費税情報エンティティ
     */
    TaxEntity getCurrentStandardTaxEntity();

    /**
     * 現在施行中税率取得<br/>
     *
     * @return 現在施行中税率マップ
     */
    Map<HTypeTaxRateType, TaxRateEntity> getEffectiveTaxRateMap();

    /**
     * 消費税SEQから税率を取得<br/>
     *
     * @param taxSeq 消費税SEQ
     * @return 税率マップ
     */
    Map<HTypeTaxRateType, TaxRateEntity> getEffectiveTaxRateMapByTaxSeq(Integer taxSeq);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 消費税情報を格納したMap
     */
    Map<BigDecimal, String> getItemMapList();

}
