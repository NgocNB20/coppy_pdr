/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupLockLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品グループレコードロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
@Component
public class GoodsGroupLockLogicImpl extends AbstractShopLogic implements GoodsGroupLockLogic {

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public GoodsGroupLockLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 商品グループレコードロック<br/>
     * 商品グループテーブルのレコードを排他取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @param versionNo         更新カウンタ
     */
    @Override
    public void execute(List<Integer> goodsGroupSeqList, Integer versionNo) {

        // (1) パラメータチェック
        // 商品グループSEQリストが null または 0件 でないかをチェック
        ArgumentCheckUtil.assertNotEmpty("goodsGroupSeqList", goodsGroupSeqList);

        // (2) 商品グループのレコード排他取得
        List<GoodsGroupEntity> goodsGroupEntityList =
                        goodsGroupDao.getGoodsGroupBySeqForUpdate(goodsGroupSeqList, versionNo);
        // 商品グループSEQリストと取得件数が異なる場合
        if (goodsGroupSeqList.size() != goodsGroupEntityList.size()) {
            // 商品グループ排他取得エラーを投げる。
            throwMessage(MSGCD_GOODSGROUP_SELECT_FOR_UPDATE_FAIL);
        }
    }
}
