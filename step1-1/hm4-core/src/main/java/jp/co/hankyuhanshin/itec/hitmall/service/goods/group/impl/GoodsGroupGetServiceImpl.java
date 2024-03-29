/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品グループ取得サービス実装クラス<br/>
 *
 * @author hirata
 * @version $Revision: 1.7 $
 */
@Service
public class GoodsGroupGetServiceImpl extends AbstractShopService implements GoodsGroupGetService {

    /**
     * 商品グループ詳細取得（商品グループコード）ロジック
     */
    private final GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    /**
     * カテゴリ登録商品マップ取得ロジック
     */
    private final CategoryGoodsMapGetLogic categoryGoodsMapGetLogic;

    /**
     * 在庫情報リスト取得ロジック
     */
    private final StockListGetLogic stockListGetLogic;

    @Autowired
    public GoodsGroupGetServiceImpl(GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic,
                                    CategoryGoodsMapGetLogic categoryGoodsMapGetLogic,
                                    StockListGetLogic stockListGetLogic) {

        this.goodsGroupDetailsGetByCodeLogic = goodsGroupDetailsGetByCodeLogic;
        this.categoryGoodsMapGetLogic = categoryGoodsMapGetLogic;
        this.stockListGetLogic = stockListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupCode 商品グループコード
     * @param siteType サイト種別
     * @return 商品グループDto
     */
    @Override
    public GoodsGroupDto execute(String goodsGroupCode, HTypeSiteType siteType) {
        return execute(goodsGroupCode, null, siteType);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupCode 商品グループコード
     * @param shopSeq        ショップSEQ
     * @param siteType       サイト種別
     * @return 商品グループDto
     */
    public GoodsGroupDto execute(String goodsGroupCode, Integer shopSeq, HTypeSiteType siteType) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupCode", goodsGroupCode);

        // 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        // サイト区分 ： null(or 空文字) の場合 エラーとして処理を終了する
        if (shopSeq == null) {
            shopSeq = 1001;
        }

        AssertionUtil.assertNotNull("siteType", siteType);

        // (2)Logic処理を実行
        GoodsGroupDto goodsGroupDto =
                        goodsGroupDetailsGetByCodeLogic.execute(shopSeq, goodsGroupCode, null, siteType, null, null);

        if (goodsGroupDto == null) {
            // 商品グループなしの場合はnullを返す
            return null;
        }

        // 商品グループDTOから商品グループSEQリスト(1件)を作成
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());

        // (3)Logic処理を実行
        Map<Integer, List<CategoryGoodsEntity>> categoryGoodsMap = categoryGoodsMapGetLogic.execute(goodsGroupSeqList);

        // 取得した商品グループDTO．商品DTOから 商品SEQリストを作成
        List<Integer> goodsSeqList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            goodsSeqList.add(goodsDto.getGoodsEntity().getGoodsSeq());
        }

        // (4)Logic処理を実行
        List<StockDto> stockEntityList = stockListGetLogic.execute(goodsSeqList);
        // 在庫マップを作成
        Map<Integer, StockDto> stockMap = new HashMap<>();
        for (StockDto stockDto : stockEntityList) {
            stockMap.put(stockDto.getGoodsSeq(), stockDto);
        }

        // サイト区分＝"管理画面" の場合のみ(5)の処理を行う
        if (siteType.isBack()) {
            // (5) 商品グループエンティティの編集
            // 商品グループエンティティ．カテゴリ登録商品リストのセット
            goodsGroupDto.setCategoryGoodsEntityList(
                            categoryGoodsMap.get(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq()));
            // 商品グループエンティティ．商品DTO．在庫DTOのセット
            for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
                goodsDto.setStockDto(stockMap.get(goodsDto.getGoodsEntity().getGoodsSeq()));
            }
        }
        return goodsGroupDto;
    }

}
