/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodEntityListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodEntityListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 決済方法リスト取得サービス実装クラス
 *
 * @author nakamura
 * @version $Revision: 1.3 $
 */
@Service
public class SettlementMethodEntityListGetServiceImpl extends AbstractShopService
                implements SettlementMethodEntityListGetService {

    /**
     * 決済方法リスト取得ロジック
     */
    private final SettlementMethodEntityListGetLogic settlementMethodEntityListGetLogic;

    @Autowired
    public SettlementMethodEntityListGetServiceImpl(SettlementMethodEntityListGetLogic settlementMethodEntityListGetLogic) {
        this.settlementMethodEntityListGetLogic = settlementMethodEntityListGetLogic;
    }

    /**
     * 実行メソッド
     *
     * @return 決済方法エンティティ全件分のリスト（ソート：表示昇順）
     */
    @Override
    public List<SettlementMethodEntity> execute() {

        // ショップSEQを取得
        Integer shopSeq = 1001;

        // パラメータチェック
        this.checkParameter(shopSeq);

        // 決済方法リスト取得ロジック実行
        return settlementMethodEntityListGetLogic.execute(shopSeq);
    }

    /**
     * パラメータのチェックを行います<br/>
     *
     * @param shopSeq ショップSeq
     */
    protected void checkParameter(Integer shopSeq) {

        // ショップSEQ
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", 1001);

    }

}
