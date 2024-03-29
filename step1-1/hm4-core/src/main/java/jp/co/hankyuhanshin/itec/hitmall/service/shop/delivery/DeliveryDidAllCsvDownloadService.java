package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import java.io.IOException;

/**
 * お届け不可日CSVダウンロードサービス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface DeliveryDidAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "deliveryImpossibleDay";

    void execute(Object... params) throws IOException;

}
