/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.service.goods.category;

import java.io.IOException;

// 2023-renew categoryCSV from here
public interface CategoryCsvDownLoadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "category";

    /**
     * カテゴリCSVダウンロードサービス
     *
     * @param parameters
     * @throws IOException
     */
    void execute(Object... parameters) throws IOException;
}
// 2023-renew categoryCSV to here
