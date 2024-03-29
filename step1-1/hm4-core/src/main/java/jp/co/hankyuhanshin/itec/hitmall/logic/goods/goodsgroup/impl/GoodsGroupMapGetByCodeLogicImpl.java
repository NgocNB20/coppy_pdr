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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupMapGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品グループマップ取得(商品グループコード)<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsGroupMapGetByCodeLogicImpl extends AbstractShopLogic implements GoodsGroupMapGetByCodeLogic {

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public GoodsGroupMapGetByCodeLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 商品グループマップ取得<br/>
     * 商品グループエンティティを保持するマップを取得する。<br/>
     *
     * @param shopSeq            ショップSEQ
     * @param goodsGroupCodeList 商品グループコードリスト
     * @return 商品グループエンティティMAP
     */
    @Override
    public Map<String, GoodsGroupEntity> execute(Integer shopSeq, List<String> goodsGroupCodeList) {
        // (1) パラメータチェック
        // 商品グループコードリストがnullまたは空リストでないことをチェック
        ArgumentCheckUtil.assertNotEmpty("goodsGroupCodeList", goodsGroupCodeList);

        // (2) 商品グループエンティティリスト取得
        // 商品グループDaoの商品グループエンティティリスト取得処理を実行する。
        List<GoodsGroupEntity> goodsGroupEntityList =
                        goodsGroupDao.getGoodsGroupByCodeList(shopSeq, goodsGroupCodeList);

        // (3) 商品グループマップを作成する。
        // 商品グループマップを初期化する。
        // ・(2)で取得した商品グループエンティティリストについて以下の処理を行う
        // ①商品グループエンティティを商品グループエンティティマップにセットする。（キー項目：商品グループエンティティ．商品グループコード）
        Map<String, GoodsGroupEntity> goodsGroupMap = new HashMap<>();
        for (GoodsGroupEntity goodsGroupEntity : goodsGroupEntityList) {
            goodsGroupMap.put(goodsGroupEntity.getGoodsGroupCode(), goodsGroupEntity);
        }

        // (4) 戻り値
        // 編集した商品グループマップを返す。
        return goodsGroupMap;
    }
}
