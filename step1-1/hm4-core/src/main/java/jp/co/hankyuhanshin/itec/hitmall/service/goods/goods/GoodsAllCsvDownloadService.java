/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import java.io.IOException;

/**
 * 商品検索CSV一括ダウンロードサービスインターフェース<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface GoodsAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "goods";

    void execute(Object... parameters) throws IOException;

}
