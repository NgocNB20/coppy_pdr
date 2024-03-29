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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アイコン登録Logic
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsInformationIconRegistLogicImpl extends AbstractShopLogic implements GoodsInformationIconRegistLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsInformationIconRegistLogicImpl.class);

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconRegistLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * アイコンエンティティ登録処理
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
        goodsInformationIconEntity.setRegistTime(currentTime);
        goodsInformationIconEntity.setUpdateTime(currentTime);

        int iconSeq = goodsInformationIconDao.getIconSeqNextVal();
        goodsInformationIconEntity.setIconSeq(iconSeq);

        int count = 0;
        try {
            count = goodsInformationIconDao.insertAddOrderDisplay(goodsInformationIconEntity);
        } catch (DataAccessException e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_GOODSINFORMATIONICON_INSERT_FAIL);
        }

        return count;
    }

    /**
     * パラメータチェック
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     */
    protected void checkParam(GoodsInformationIconEntity goodsInformationIconEntity) {

        ArgumentCheckUtil.assertNotNull("goodsInformationIconEntity", goodsInformationIconEntity);

        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", goodsInformationIconEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("iconName", goodsInformationIconEntity.getIconName());

    }
}
