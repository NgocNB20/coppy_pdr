// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsTogetherBuyGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * よく一緒に購入される商品リスト取得（バック用）<br/>
 * 対象商品のよく一緒に購入される商品リストを取得する。<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsTogetherBuyGroupListGetForBackLogicImpl extends AbstractShopLogic
                implements GoodsTogetherBuyGroupListGetForBackLogic {

    /**
     * よく一緒に購入される商品Daoクラス
     */
    private final GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao;

    @Autowired
    public GoodsTogetherBuyGroupListGetForBackLogicImpl(GoodsTogetherBuyGroupDao goodsTogetherBuyGroupDao) {
        this.goodsTogetherBuyGroupDao = goodsTogetherBuyGroupDao;
    }

    /**
     * よく一緒に購入される商品リスト取得（バック用）<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品エンティティリスト
     */
    @Override
    public List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq) {

        // (1) パラメータチェック
        // 商品グループSEQが null でないことをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupSeq", goodsGroupSeq);

        // (2) よく一緒に購入されている商品リスト取得
        // よく一緒に購入されている商品Daoのよく一緒に購入されている商品マップ検索処理を実行する。
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList =
                        goodsTogetherBuyGroupDao.getGoodsTogetherBuyGroupListByGoodsGroupSeq(goodsGroupSeq);

        // (3) 戻り値
        // （戻り値用）よく一緒に購入されている商品DTOリストを返す。
        return goodsTogetherBuyGroupEntityList;
    }
}
// 2023-renew No21 to here