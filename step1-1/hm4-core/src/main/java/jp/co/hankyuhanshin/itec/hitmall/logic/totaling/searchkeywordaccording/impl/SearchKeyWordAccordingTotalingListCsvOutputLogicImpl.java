package jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.totaling.TotalingDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListCsvOutputLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 検索キーワード集計CSV出力Logic実装クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class SearchKeyWordAccordingTotalingListCsvOutputLogicImpl extends AbstractShopLogic
                implements SearchKeyWordAccordingTotalingListCsvOutputLogic {
    /**
     * 集計用Dao
     */
    private final TotalingDao totalingDao;

    @Autowired
    public SearchKeyWordAccordingTotalingListCsvOutputLogicImpl(TotalingDao totalingDao) {
        this.totalingDao = totalingDao;
    }

    @Override
    public Stream<AccessSearchKeywordTotalDto> execute(AccessSearchKeywordTotalSearchForConditionDto conditionDto) {
        return this.totalingDao.getAccessSearchKeywordTotalListStream(conditionDto);
    }

}
