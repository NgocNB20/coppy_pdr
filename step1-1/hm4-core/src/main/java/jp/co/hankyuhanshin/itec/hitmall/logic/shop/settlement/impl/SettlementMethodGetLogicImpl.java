/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 決済方法取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class SettlementMethodGetLogicImpl extends AbstractShopLogic implements SettlementMethodGetLogic {

    /**
     * 決済方法分類リスト用valueカラム名
     */
    protected static final String VALUE_COLNAME = "settlementmethodseq";
    /**
     * 決済方法分類リスト用ラベルカラム名
     */
    protected static final String LABEL_COLNAME = "settlementmethodname";

    /**
     * 決済方法Dao
     */
    private final SettlementMethodDao settlementMethodDao;

    @Autowired
    public SettlementMethodGetLogicImpl(SettlementMethodDao settlementMethodDao) {
        this.settlementMethodDao = settlementMethodDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法エンティティ
     */
    @Override
    public SettlementMethodEntity execute(Integer settlementMethodSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("settlementMethodSeq", settlementMethodSeq);

        // 決済方法の検索
        return settlementMethodDao.getEntity(settlementMethodSeq);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @param shopSeq             ショップSEQ
     * @return 決済方法エンティティ
     */
    @Override
    public SettlementMethodEntity execute(Integer settlementMethodSeq, Integer shopSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("settlementMethodSeq", settlementMethodSeq);
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        // 決済方法の検索
        return settlementMethodDao.getSettlementMethodEntity(settlementMethodSeq, shopSeq);
    }

    /**
     * Get settlement method entity by method type.<br/>
     *
     * @param settlementMethodType 決済方法種別
     * @param openStatus           open status settlement.
     * @return 決済方法エンティティ
     */
    @Override
    public SettlementMethodEntity execute(HTypeSettlementMethodType settlementMethodType, HTypeOpenStatus openStatus) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("settlementMethodType", settlementMethodType);

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("openStatus", openStatus);
        // ショップSEQ
        Integer shopSeq = 1001;

        // 決済方法の検索
        return settlementMethodDao.getEntityBySettlementMethodTypeAndOpenStatus(
                        shopSeq, settlementMethodType, openStatus);
    }

    /**
     * 決済方法分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return 決済方法分類エンティティリスト
     */
    @Override
    public Map<String, String> getItemMapList(Integer shopSeq) {

        // 取得
        List<Map<String, Object>> settlementMapList = settlementMethodDao.getItemMapList(shopSeq);

        Map<String, String> map = new LinkedHashMap<String, String>();
        if (map != null) {
            for (Map<String, ?> settlementMap : settlementMapList) {
                map.put(settlementMap.get(VALUE_COLNAME).toString(), settlementMap.get(LABEL_COLNAME).toString());
            }
        }

        return map;
    }

}
