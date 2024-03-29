/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite;

import java.io.IOException;

/**
 * お気に入り商品検索CSV一括ダウンロードサービスインターフェース<br/>
 *
 * @author takashima
 */
public interface FavoriteAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "favorite";

    void execute(Object... parameters) throws IOException;

}
