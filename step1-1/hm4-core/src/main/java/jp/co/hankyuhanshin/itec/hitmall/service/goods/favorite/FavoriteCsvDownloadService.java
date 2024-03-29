/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite;

import java.io.IOException;

/**
 * お気に入り商品検索CSVダウンロードサービスインターフェース<br/>
 *
 * @author takashima
 */
public interface FavoriteCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "favorite";

    void execute(Object... parameters) throws IOException;

}
