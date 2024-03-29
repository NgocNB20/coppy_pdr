/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsSearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品検索結果リスト取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
@Component
public class GoodsSearchResultListGetLogicImpl extends AbstractShopLogic implements GoodsSearchResultListGetLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsSearchResultListGetLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件Dto(管理機能)
     * @return 商品検索結果DTOリスト
     */
    @Override
    public List<GoodsSearchResultDto> execute(GoodsSearchForBackDaoConditionDto searchCondition) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsSearchForBackDaoConditionDto", searchCondition);

        // (2)商品検索結果リスト取得処理
        List<GoodsSearchResultDto> goodsSearchResultDtoList = goodsDao.getSearchGoodsForBackList(searchCondition,
                                                                                                 searchCondition.getPageInfo()
                                                                                                                .getSelectOptions()
                                                                                                );

        return goodsSearchResultDtoList;
    }
}
