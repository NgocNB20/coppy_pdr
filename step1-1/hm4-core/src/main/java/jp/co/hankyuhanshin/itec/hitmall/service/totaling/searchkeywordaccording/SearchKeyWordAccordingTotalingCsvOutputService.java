package jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording;

import java.io.IOException;

/**
 * 検索キーワード集計CSV出力サービスインターフェース
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface SearchKeyWordAccordingTotalingCsvOutputService {

    String FILE_NAME = "t_SearchKeyword";

    void execute(Object... parameters) throws IOException;

}
