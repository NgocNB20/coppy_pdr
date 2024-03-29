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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品グループ表示取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsGroupDisplayGetLogicImpl extends AbstractShopLogic implements GoodsGroupDisplayGetLogic {

    /**
     * 商品グループ表示DAO
     */
    private final GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Autowired
    public GoodsGroupDisplayGetLogicImpl(GoodsGroupDisplayDao goodsGroupDisplayDao) {
        this.goodsGroupDisplayDao = goodsGroupDisplayDao;
    }

    /**
     * 商品グループ表示取得<br/>
     * 商品グループSEQをもとに商品グループ表示エンティティを取得する。<br/>
     *
     * @param goodsGroupSeq 商品グループSEQリスト
     * @return 商品グループ表示情報
     */
    @Override
    public GoodsGroupDisplayEntity execute(Integer goodsGroupSeq) {

        // (1) パラメータチェック
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        // (2) 商品グループ表示情報取得
        // 商品グループ表示Daoの商品グループ表示情報取得処理を実行する。
        // DAO GoodsGroupDisplayDao
        // メソッド 商品グループ表示エンティティ getEntity( （パラメータ）商品グループSEQ)
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDisplayDao.getEntity(goodsGroupSeq);

        // (3) 戻り値
        // 取得した商品グループ表示エンティティを返す。
        return goodsGroupDisplayEntity;
    }

    @Override
    public List<GoodsGroupDisplayEntity> execute(List<Integer> goodsGroupSeqList) {

        // (1) パラメータチェック
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeqList", goodsGroupSeqList);

        // (2) 商品グループ表示情報取得
        // 商品グループ表示Daoの商品グループ表示情報取得処理を実行する。
        // DAO GoodsGroupDisplayDao
        // メソッド 商品グループ表示エンティティ getEntity( （パラメータ）商品グループSEQ)
        List<GoodsGroupDisplayEntity> goodsGroupDisplayEntityList =
                        goodsGroupDisplayDao.getGoodsGroupDisplayList(goodsGroupSeqList);

        // (3) 戻り値
        // 取得した商品グループ表示エンティティを返す。
        return goodsGroupDisplayEntityList;
    }
}
