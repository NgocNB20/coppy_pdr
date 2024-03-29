// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsPriceInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 商品情報更新ロジック実装クラス
 *
 * @author s.kume
 */
@Component
public class GoodsPriceInfoUpdateLogicImpl extends AbstractShopLogic implements GoodsPriceInfoUpdateLogic {

    /** 商品Dao */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsPriceInfoUpdateLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 商品情報更新<br/>
     *
     * @param goodsEntity 商品エンティティ
     * @return 更新結果
     */
    @Override
    public int execute(GoodsEntity goodsEntity) {

        // パラメータチェック
        // 商品エンティティが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsEntity", goodsEntity);

        // 更新日をセット
        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        goodsEntity.setUpdateTime(currentTime);

        // 商品情報更新
        if (goodsDao.update(goodsEntity) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
// PDR Migrate Customization to here
