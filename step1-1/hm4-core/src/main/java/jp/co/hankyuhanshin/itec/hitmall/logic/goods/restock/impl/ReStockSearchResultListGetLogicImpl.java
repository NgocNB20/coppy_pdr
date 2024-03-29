/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockSearchResultListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入荷お知らせ商品検索結果リスト取得ロジック
 *
 * @author st75001
 * @version $Revision: 1.3 $
 */
@Component
public class ReStockSearchResultListGetLogicImpl extends AbstractShopLogic implements ReStockSearchResultListGetLogic {

    /**
     * 入荷お知らせ商品DAO
     */
    private final ReStockDao reStockDao;

    @Autowired
    public ReStockSearchResultListGetLogicImpl(ReStockDao reStockDao) {
        this.reStockDao = reStockDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件Dto(管理機能)
     * @return 商品検索結果DTOリスト
     */
    @Override
    public List<ReStockSearchResultDto> execute(ReStockSearchForBackDaoConditionDto searchCondition) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("reStockSearchForBackDaoConditionDto", searchCondition);

        // (2)商品検索結果リスト取得処理
        return reStockDao.getSearchReStockForBackList(searchCondition, searchCondition.getPageInfo().getSelectOptions());
    }
}
