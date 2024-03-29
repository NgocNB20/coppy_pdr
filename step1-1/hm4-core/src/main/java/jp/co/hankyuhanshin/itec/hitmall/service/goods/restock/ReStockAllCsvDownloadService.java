/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock;

import java.io.IOException;

/**
 * 入荷お知らせ商品CSV一括ダウンロードサービスインターフェース<br/>
 *
 * @author st75001
 */
public interface ReStockAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "reStock";

    void execute(Object... parameters) throws IOException;

}
