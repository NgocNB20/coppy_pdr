package jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;

import java.util.stream.Stream;

/**
 * 検索キーワード集計CSV出力Logicインターフェース
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface SearchKeyWordAccordingTotalingListCsvOutputLogic {

    /**
     * 検索キーワード集計CSV出力処理を実行します
     *
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     * @return stream<AccessSearchKeywordTotalDto>
     */
    Stream<AccessSearchKeywordTotalDto> execute(AccessSearchKeywordTotalSearchForConditionDto conditionDto);
}
