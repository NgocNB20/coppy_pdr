/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 決済方法更新ロジック<br/>
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class SettlementMethodUpdateLogicImpl extends AbstractShopLogic implements SettlementMethodUpdateLogic {

    /**
     * 決済方法Dao
     */
    private final SettlementMethodDao settlementMethodDao;

    @Autowired
    public SettlementMethodUpdateLogicImpl(SettlementMethodDao settlementMethodDao) {
        this.settlementMethodDao = settlementMethodDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @return 処理件数
     */
    @Override
    public int execute(SettlementMethodEntity settlementMethodEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("settlementMethodEntity", settlementMethodEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp currentTime = dateUtility.getCurrentTime();
        settlementMethodEntity.setUpdateTime(currentTime);

        return settlementMethodDao.update(settlementMethodEntity);
    }

}
