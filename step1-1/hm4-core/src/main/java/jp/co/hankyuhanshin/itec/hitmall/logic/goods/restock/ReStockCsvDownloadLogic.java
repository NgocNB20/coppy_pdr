/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;

import java.util.List;
import java.util.stream.Stream;

/**
 * 入荷お知らせ商品CSV一括ダウンロードロジックインターフェース<br/>
 *
 * @author st75001
 */
public interface ReStockCsvDownloadLogic {

    /**
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況) or （商品SEQ+入荷状態+配信ID+配信状況+会員SEQ)
     * @param memberFlg 会員SEQがキーにあるかを判定する true:あり、false:なし
     * @return 入荷お知らせ商品CSVダウンロードDto
     */
    Stream<ReStockDownloadCsvDto> execute(List<String> keyList, boolean memberFlg);
}
