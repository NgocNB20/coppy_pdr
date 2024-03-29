/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockDetailsResultListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockDetailsResultListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入荷お知らせ商品管理機能詳細リスト取得サービス実装クラス<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
@Service
public class ReStockDetailsResultListGetServiceImpl extends AbstractShopService
                implements ReStockDetailsResultListGetService {

    /**
     * 商品検索結果リスト取得ロジック
     */
    private final ReStockDetailsResultListGetLogic reStockDetailsResultListGetLogic;

    @Autowired
    public ReStockDetailsResultListGetServiceImpl(ReStockDetailsResultListGetLogic reStockDetailsResultListGetLogic) {
        this.reStockDetailsResultListGetLogic = reStockDetailsResultListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param key キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return 商品検索結果Dtoリスト
     */
    @Override
    public List<ReStockDetailsResultDto> execute(String key) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("key", key);

        // (2) 共通情報チェック

        // (3)検索条件Dtoの編集

        // (4)Logic処理を実行
        return reStockDetailsResultListGetLogic.execute(key);
    }
}
