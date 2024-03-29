/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;

import java.util.List;

/**
 * 入荷お知らせメールアドレス帳登録リスト取得<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
public interface ReStockAddImportListGetLogic {

    /**
     * 実行メソッド<br/>
     * @param memberFlg 会員SEQがキーにあるかを判定する true:あり、false:なし
     * @param keyList 検索結果選択キー
     * @return 入荷お知らせメールアドレス帳登録Dtoリスト
     */
    List<ReStockAddImportListDto> execute(boolean memberFlg, List<String> keyList);
}
