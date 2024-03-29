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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconListOrderDisplayUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * アイコン表示順更新
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsInformationIconListOrderDisplayUpdateLogicImpl extends AbstractShopLogic
                implements GoodsInformationIconListOrderDisplayUpdateLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconListOrderDisplayUpdateLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * リスト分、商品インフォメーションアイコンの表示順を指定値で変更します。
     *
     * @param iconEntityList アイコンエンティティリスト
     * @return 処理件数
     */
    @Override
    public int execute(List<GoodsInformationIconEntity> iconEntityList) {

        // パラメータチェック
        this.checkParam(iconEntityList);

        // テーブルロック
        goodsInformationIconDao.updateLockTableShareModeNowait();

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // リスト分更新
        int retCnt = 0;
        for (GoodsInformationIconEntity iconEntity : iconEntityList) {
            Integer iconSeq = iconEntity.getIconSeq();
            Integer shopSeq = iconEntity.getShopSeq();
            Integer orderDisplay = iconEntity.getOrderDisplay();
            Timestamp updateTime = dateUtility.getCurrentTime();

            int result = goodsInformationIconDao.updateOrderDisplay(iconSeq, shopSeq, orderDisplay, updateTime);
            // 0件更新時
            if (result == 0) {
                // エラーとして処理中断
                throwMessage(MSGCD_GOODSINFORMATIONICON_UPDATE_FAIL);
            }

            retCnt = retCnt + result;
        }

        return retCnt;
    }

    /**
     * パラメータチェック
     *
     * @param iconEntityList アイコンエンティティリスト
     */
    protected void checkParam(List<GoodsInformationIconEntity> iconEntityList) {

        ArgumentCheckUtil.assertNotNull("iconEntityList", iconEntityList);
        for (GoodsInformationIconEntity iconEntity : iconEntityList) {
            ArgumentCheckUtil.assertGreaterThanZero("iconSeq", iconEntity.getIconSeq());
            ArgumentCheckUtil.assertGreaterThanZero("shopSeq", iconEntity.getShopSeq());
            ArgumentCheckUtil.assertNotEmpty("iconName", iconEntity.getIconName());
            ArgumentCheckUtil.assertNotNull("orderDisplay", iconEntity.getOrderDisplay());

        }
    }
}
