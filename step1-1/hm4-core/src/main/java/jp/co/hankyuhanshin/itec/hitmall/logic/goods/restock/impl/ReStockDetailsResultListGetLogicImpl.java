/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockDetailsResultListGetLogic;
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
public class ReStockDetailsResultListGetLogicImpl extends AbstractShopLogic implements ReStockDetailsResultListGetLogic {

    /**
     * 入荷お知らせ商品DAO
     */
    private final ReStockDao reStockDao;

    @Autowired
    public ReStockDetailsResultListGetLogicImpl(ReStockDao reStockDao) {
        this.reStockDao = reStockDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param key キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return 商品検索結果DTOリスト
     */
    @Override
    public List<ReStockDetailsResultDto> execute(String key) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("key", key);

        // (2)商品検索結果リスト取得処理
        return reStockDao.getDetailsReStockForBackList(key);
    }
}
