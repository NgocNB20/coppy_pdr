/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodPriceCommissionDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 決済方法金額別手数料更新ロジック<br/>
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class SettlementMethodPriceCommissionUpdateLogicImpl extends AbstractShopLogic
                implements SettlementMethodPriceCommissionUpdateLogic {

    /**
     * 決済方法金額別手数料登録DAO
     */
    private final SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao;

    @Autowired
    public SettlementMethodPriceCommissionUpdateLogicImpl(SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao) {
        this.settlementMethodPriceCommissionDao = settlementMethodPriceCommissionDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodPriceCommissionEntity 決済方法金額別手数料エンティティ
     * @return 処理件数
     */
    @Override
    public int execute(SettlementMethodPriceCommissionEntity settlementMethodPriceCommissionEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("settlementMethodPriceCommissionEntity", settlementMethodPriceCommissionEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp currentTime = dateUtility.getCurrentTime();
        settlementMethodPriceCommissionEntity.setUpdateTime(currentTime);

        return settlementMethodPriceCommissionDao.update(settlementMethodPriceCommissionEntity);
    }

}
