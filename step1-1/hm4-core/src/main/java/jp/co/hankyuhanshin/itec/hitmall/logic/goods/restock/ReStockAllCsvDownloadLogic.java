/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;

import java.util.stream.Stream;

/**
 * 入荷お知らせ商品CSV一括ダウンロードロジックインターフェース<br/>
 *
 * @author st75001
 */
public interface ReStockAllCsvDownloadLogic {
    Stream<ReStockDownloadCsvDto> execute(ReStockSearchForBackDaoConditionDto conditionDto);
}
