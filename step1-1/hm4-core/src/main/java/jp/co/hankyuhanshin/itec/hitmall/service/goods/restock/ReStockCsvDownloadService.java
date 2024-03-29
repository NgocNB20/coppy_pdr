/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.restock;

import java.io.IOException;

/**
 * 入荷お知らせ商品CSVダウンロードサービスインターフェース<br/>
 *
 * @author st75001
 */
public interface ReStockCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "reStock";

    void execute(boolean memberFlg, Object... parameters) throws IOException;

}
