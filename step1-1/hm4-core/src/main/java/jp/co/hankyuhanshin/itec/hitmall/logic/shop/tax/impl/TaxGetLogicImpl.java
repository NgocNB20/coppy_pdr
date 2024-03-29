/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.tax.TaxDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.tax.TaxRateDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 消費税情報取得<br/>
 *
 * @author kumazawa
 */
@Component
public class TaxGetLogicImpl extends AbstractShopLogic implements TaxGetLogic {

    /**
     * 消費税情報リスト用valueカラム名
     */
    protected static final String VALUE_COLNAME = "rate";

    /**
     * 消費税情報リスト用ラベルカラム名
     */
    protected static final String LABEL_COLNAME = "taxrate";

    /**
     * 消費税Dao
     */
    private final TaxDao taxDao;

    /**
     * 税率Dao
     */
    private final TaxRateDao taxRateDao;

    @Autowired
    public TaxGetLogicImpl(TaxDao taxDao, TaxRateDao taxRateDao) {
        this.taxDao = taxDao;
        this.taxRateDao = taxRateDao;

    }

    @Override
    public TaxEntity getCurrentStandardTaxEntity() {
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp currentTime = dateUtility.getCurrentTime();
        TaxEntity taxEntity = taxDao.getCurrentTaxEntity(currentTime);

        return taxEntity;
    }

    @Override
    public Map<HTypeTaxRateType, TaxRateEntity> getEffectiveTaxRateMap() {
        // 現在施行中消費税SEQ取得
        TaxEntity effectiveTaxEntity = getCurrentStandardTaxEntity();
        return getEffectiveTaxRateMapByTaxSeq(effectiveTaxEntity.getTaxSeq());
    }

    @Override
    public Map<HTypeTaxRateType, TaxRateEntity> getEffectiveTaxRateMapByTaxSeq(Integer taxSeq) {
        // 施行中税率取得
        List<TaxRateEntity> taxRateList = taxRateDao.getEffectiveTaxRateEntity(taxSeq);

        // 施行中の税率マップ作成
        Map<HTypeTaxRateType, TaxRateEntity> taxRateMap = new HashMap<>();
        for (TaxRateEntity entity : taxRateList) {
            taxRateMap.put(entity.getRateType(), entity);
        }

        return taxRateMap;
    }

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 消費税情報を格納したMap
     */
    @Override
    public Map<BigDecimal, String> getItemMapList() {

        // 取得
        List<Map<String, Object>> deliveryMapList = taxRateDao.getItemMapList();

        Map<BigDecimal, String> map = new LinkedHashMap<BigDecimal, String>();
        if (map != null) {
            for (Map<String, ?> deliveryMap : deliveryMapList) {
                map.put(
                                new BigDecimal(deliveryMap.get(VALUE_COLNAME).toString()),
                                deliveryMap.get(LABEL_COLNAME).toString()
                       );
            }
        }

        return map;
    }

}
