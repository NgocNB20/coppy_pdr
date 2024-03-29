package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery;

import java.io.IOException;

public interface DeliveryHolidayAllCsvDownloadService {
    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "holiday";

    void execute(Object... parameters) throws IOException;
}
