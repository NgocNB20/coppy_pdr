package jp.co.hankyuhanshin.itec.hitmall.service.order;

import java.io.IOException;

/**
 * 受注CSV出力Serviceインターフェース
 * <p>
 * Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface OrderCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "order";

    void execute(Object... parameters) throws IOException;

}
