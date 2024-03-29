/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.totaling.TotalingDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 検索キーワード集計Logic実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class SearchKeyWordAccordingTotalingListGetLogicImpl extends AbstractShopLogic
                implements SearchKeyWordAccordingTotalingListGetLogic {
    /**
     * 集計Dao
     */
    private final TotalingDao totalingDao;

    @Autowired
    public SearchKeyWordAccordingTotalingListGetLogicImpl(TotalingDao totalingDao) {
        this.totalingDao = totalingDao;
    }

    /**
     * 検索キーワード集計処理を実行します
     *
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     * @return List&lt;AccessSearchKeywordTotalDto&gt;
     */
    @Override
    public List<AccessSearchKeywordTotalDto> execute(AccessSearchKeywordTotalSearchForConditionDto conditionDto) {
        return totalingDao.getAccessSearchKeywordTotalList(conditionDto, conditionDto.getPageInfo().getSelectOptions());
    }

}
