/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アイコン更新Logic
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsInformationIconUpdateLogicImpl extends AbstractShopLogic implements GoodsInformationIconUpdateLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconUpdateLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * アイコン更新処理
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    @Override
    public int execute(GoodsInformationIconEntity goodsInformationIconEntity) {
        // パラメータチェック
        this.checkParam(goodsInformationIconEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 日時セット
        Timestamp currentTime = dateUtility.getCurrentTime();
        goodsInformationIconEntity.setUpdateTime(currentTime);

        return goodsInformationIconDao.update(goodsInformationIconEntity);
    }

    /**
     * パラメータチェック
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     */
    protected void checkParam(GoodsInformationIconEntity goodsInformationIconEntity) {

        ArgumentCheckUtil.assertNotNull("goodsInformationIconEntity", goodsInformationIconEntity);
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", goodsInformationIconEntity.getIconSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", goodsInformationIconEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("iconName", goodsInformationIconEntity.getIconName());
    }

}
