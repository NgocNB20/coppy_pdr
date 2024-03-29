package jp.co.hankyuhanshin.itec.hitmall.service.shop.questionnaire;

import java.io.IOException;

/**
 * アンケート回答取得ロジックの実装クラス。<br />
 *
 * @author matsuda
 */
public interface QuestionnaireAllCsvDownloadService {

    /**
     * 出力CSVファイル名
     */
    String FILE_NAME = "questionReply";

    void execute(Object... params) throws IOException;

}
