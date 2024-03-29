/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupPopularityDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupPopularityRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 商品グループ人気登録<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsGroupPopularityRegistLogicImpl extends AbstractShopLogic implements GoodsGroupPopularityRegistLogic {

    /**
     * 商品グループ人気DAO
     */
    private final GoodsGroupPopularityDao goodsGroupPopularityDao;

    @Autowired
    public GoodsGroupPopularityRegistLogicImpl(GoodsGroupPopularityDao goodsGroupPopularityDao) {
        this.goodsGroupPopularityDao = goodsGroupPopularityDao;
    }

    /**
     * 商品グループ人気登録<br/>
     *
     * @param goodsGroupPopularityEntity 商品グループ人気エンティティ
     * @return 登録した件数
     */
    @Override
    public int execute(GoodsGroupPopularityEntity goodsGroupPopularityEntity) {

        // (1) パラメータチェック
        // 商品グループ人気エンティティが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupPopularityEntity", goodsGroupPopularityEntity);

        // (2) 登録日・更新日をセット

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        goodsGroupPopularityEntity.setRegistTime(currentTime);
        goodsGroupPopularityEntity.setUpdateTime(currentTime);

        // (3) 商品グループ人気情報登録
        int ret = goodsGroupPopularityDao.insert(goodsGroupPopularityEntity);

        // (4) 戻り値
        return ret;
    }
}
