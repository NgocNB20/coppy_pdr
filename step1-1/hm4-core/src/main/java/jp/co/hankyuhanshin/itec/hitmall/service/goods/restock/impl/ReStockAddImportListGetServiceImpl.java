/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAddImportListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockAddImportListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 入荷お知らせメールアドレス帳登録サービス実装クラス<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
@Service
public class ReStockAddImportListGetServiceImpl extends AbstractShopService
                implements ReStockAddImportListGetService {

    /**
     * 商入荷お知らせメールアドレス帳登録リスト取得ロジック
     */
    private final ReStockAddImportListGetLogic reStockAddImportListGetLogic;

    @Autowired
    public ReStockAddImportListGetServiceImpl(ReStockAddImportListGetLogic reStockAddImportListGetLogic) {
        this.reStockAddImportListGetLogic = reStockAddImportListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param memberFlg 会員SEQがキーにあるかを判定する true:あり、false:なし
     * @param keyList 検索結果選択キー
     * @return 入荷お知らせメールアドレス帳登録Dtoリスト
     */
    @Override
    public List<ReStockAddImportListDto> execute(boolean memberFlg,List<String> keyList) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("keyList", keyList);

        // (2) 共通情報チェック

        // (3)検索条件Dtoの編集

        // (4)Logic処理を実行
        return reStockAddImportListGetLogic.execute(memberFlg, keyList);
    }
}
