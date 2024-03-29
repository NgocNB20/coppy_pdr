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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayMapGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品グループ表示マップ取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsGroupDisplayMapGetLogicImpl extends AbstractShopLogic implements GoodsGroupDisplayMapGetLogic {

    /**
     * 商品グループ表示DAO
     */
    private final GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Autowired
    public GoodsGroupDisplayMapGetLogicImpl(GoodsGroupDisplayDao goodsGroupDisplayDao) {
        this.goodsGroupDisplayDao = goodsGroupDisplayDao;
    }

    /**
     * 商品グループ表示マップ取得<br/>
     * 商品グループSEQをもとに商品グループ表示エンティティマップを取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 商品グループ表示マップ
     */
    @Override
    public Map<Integer, GoodsGroupDisplayEntity> execute(List<Integer> goodsGroupSeqList) {

        // (1) パラメータチェック
        // 商品グループSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeqList", goodsGroupSeqList);

        // (2) 商品グループ表示リスト取得
        // 商品グループ表示Daoの商品グループ表示リスト取得処理を実行する。
        // DAO GoodsGroupDisplayDao
        List<GoodsGroupDisplayEntity> goodsGroupDisplayEntityList =
                        goodsGroupDisplayDao.getGoodsGroupDisplayList(goodsGroupSeqList);
        // 取得したリストをマップに編集
        Map<Integer, GoodsGroupDisplayEntity> map = new HashMap<>();
        for (GoodsGroupDisplayEntity tmp : goodsGroupDisplayEntityList) {
            map.put(tmp.getGoodsGroupSeq(), tmp);
        }

        // (3) 戻り値
        // 編集したマップを返す。
        return map;
    }
}
