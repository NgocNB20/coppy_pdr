/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsUnitListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品規格リスト取得ロジック<br/>
 *
 * @author hs32101
 */
@Component
public class GoodsUnitListGetLogicImpl extends AbstractShopLogic implements GoodsUnitListGetLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsUnitListGetLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 規格値1リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値1リスト
     */
    @Override
    public List<GoodsUnitDto> getUnit1List(String ggcd, String gcd) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("ggcd", ggcd);

        // 規格値1リスト取得
        return goodsDao.getUnit1ListByGoodsCode(ggcd, gcd);
    }

    /**
     * 規格値2リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値2リスト
     */
    @Override
    public List<GoodsUnitDto> getUnit2List(String ggcd, String gcd) {

        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("ggcd", ggcd);

        // 規格値2リスト取得
        return goodsDao.getUnit2ListByGoodsCode(ggcd, gcd);
    }

}
