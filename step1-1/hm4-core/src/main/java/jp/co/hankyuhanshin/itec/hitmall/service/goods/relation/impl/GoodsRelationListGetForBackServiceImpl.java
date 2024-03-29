/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationListGetForBackLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 関連商品リスト取得（バック用）<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Service
public class GoodsRelationListGetForBackServiceImpl extends AbstractShopService
                implements GoodsRelationListGetForBackService {

    /**
     * 関連商品リスト取得（バック用）
     */
    private final GoodsRelationListGetForBackLogic goodsRelationListGetForBackLogic;

    @Autowired
    public GoodsRelationListGetForBackServiceImpl(GoodsRelationListGetForBackLogic goodsRelationListGetForBackLogic) {
        this.goodsRelationListGetForBackLogic = goodsRelationListGetForBackLogic;
    }

    /**
     * 関連商品リスト取得（バック用）<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 関連商品エンティティリスト
     */
    @Override
    public List<GoodsRelationEntity> execute(Integer goodsGroupSeq) {

        // (1) パラメータチェック
        // ・商品SEQ ： null(or 0 ) の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        // (2) 関連商品リスト取得（バック用）実行
        List<GoodsRelationEntity> goodsRelationEntityList = goodsRelationListGetForBackLogic.execute(goodsGroupSeq);

        return goodsRelationEntityList;
    }

}
