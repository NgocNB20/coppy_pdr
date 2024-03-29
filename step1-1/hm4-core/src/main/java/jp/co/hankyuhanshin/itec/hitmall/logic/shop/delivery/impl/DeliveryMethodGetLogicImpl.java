/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送方法取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class DeliveryMethodGetLogicImpl extends AbstractShopLogic implements DeliveryMethodGetLogic {

    /**
     * 配送方法分類リスト用valueカラム名
     */
    protected static final String VALUE_COLNAME = "deliverymethodseq";
    /**
     * 配送方法分類リスト用ラベルカラム名
     */
    protected static final String LABEL_COLNAME = "deliverymethodname";

    /**
     * 配送方法Dao
     */
    private final DeliveryMethodDao deliveryMethodDao;

    @Autowired
    public DeliveryMethodGetLogicImpl(DeliveryMethodDao deliveryMethodDao) {
        this.deliveryMethodDao = deliveryMethodDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法エンティティ
     */
    @Override
    public DeliveryMethodEntity execute(Integer deliveryMethodSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);

        // 配送方法の検索
        return deliveryMethodDao.getEntity(deliveryMethodSeq);
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryMethodName 配送方法名
     * @param shopSeq            ショップSEQ
     * @return 配送方法エンティティ
     */
    @Override
    public DeliveryMethodEntity execute(String deliveryMethodName, Integer shopSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodName);
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        // 配送方法の検索
        return deliveryMethodDao.getDeliveryMethodByName(deliveryMethodName, shopSeq);
    }

    /**
     * 配送方法分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return 配送方法せ分類エンティティリスト
     */
    @Override
    public Map<String, String> getItemMapList(Integer shopSeq) {

        // 取得
        List<Map<String, Object>> deliveryMapList = deliveryMethodDao.getItemMapList(shopSeq);

        Map<String, String> map = new LinkedHashMap<String, String>();
        if (map != null) {
            for (Map<String, ?> deliveryMap : deliveryMapList) {
                map.put(deliveryMap.get(VALUE_COLNAME).toString(), deliveryMap.get(LABEL_COLNAME).toString());
            }
        }

        return map;
    }
}
