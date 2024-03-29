package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;

import java.util.stream.Stream;

/**
 * アンケート回答情報取得ロジックのインタフェースクラス。<br />
 * <pre>
 * アンケート回答出力画面から利用する。
 * </pre>
 * @author matsuda
 */
public interface QuestionnaireAllCsvDownloadLogic {

    /**
     * アンケート回答情報を取得する。<br />
     * <pre>
     * アンケート回答検索条件Dtoよりアンケート情報を取得する。
     * </pre>
     *
     * @param conditionDto アンケート検索条件Dto
     * @return アンケート検索結果Dtoリスト
     */
    Stream<QuestionnaireReplyCsvDto> execute(QuestionnaireReplyCsvSearchDto conditionDto);
}
