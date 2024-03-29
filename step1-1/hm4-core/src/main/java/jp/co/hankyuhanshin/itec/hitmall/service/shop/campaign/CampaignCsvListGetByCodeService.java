package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import java.io.IOException;

/**
 * 広告CSV出力<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignCsvListGetByCodeService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "ad";

    void execute(Object... parameters) throws IOException;

}
