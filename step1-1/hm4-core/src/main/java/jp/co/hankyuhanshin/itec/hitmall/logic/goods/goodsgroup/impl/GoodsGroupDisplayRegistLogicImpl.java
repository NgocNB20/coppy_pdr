/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 商品グループ表示登録<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsGroupDisplayRegistLogicImpl extends AbstractShopLogic implements GoodsGroupDisplayRegistLogic {

    /**
     * 商品グループ表示DAO
     */
    private final GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Autowired
    public GoodsGroupDisplayRegistLogicImpl(GoodsGroupDisplayDao goodsGroupDisplayDao) {
        this.goodsGroupDisplayDao = goodsGroupDisplayDao;
    }

    /**
     * 商品グループ表示登録<br/>
     *
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @return 登録した件数
     */
    @Override
    public int execute(GoodsGroupDisplayEntity goodsGroupDisplayEntity) {

        // (1) パラメータチェック
        // 商品グループ表示エンティティが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupDisplayEntity", goodsGroupDisplayEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // (2) 登録日・更新日をセット
        // 登録・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        goodsGroupDisplayEntity.setRegistTime(currentTime);
        goodsGroupDisplayEntity.setUpdateTime(currentTime);

        // (3) 商品グループ表示情報登録
        int ret = goodsGroupDisplayDao.insert(goodsGroupDisplayEntity);

        // (4) 戻り値
        return ret;
    }
}
