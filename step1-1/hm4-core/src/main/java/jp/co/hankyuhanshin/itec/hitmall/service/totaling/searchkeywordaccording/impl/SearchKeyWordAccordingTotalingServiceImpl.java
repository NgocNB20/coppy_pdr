/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 検索キーワード集計サービス実装クラス
 *
 * @author $author$
 * @version $Revision: 1.2 $
 */
@Service
public class SearchKeyWordAccordingTotalingServiceImpl extends AbstractShopService
                implements SearchKeyWordAccordingTotalingService {
    /**
     * 検索キーワード集計Logic
     */
    private final SearchKeyWordAccordingTotalingListGetLogic searchKeyWordAccordingTotalingListGetLogic;

    @Autowired
    public SearchKeyWordAccordingTotalingServiceImpl(SearchKeyWordAccordingTotalingListGetLogic searchKeyWordAccordingTotalingListGetLogic) {
        this.searchKeyWordAccordingTotalingListGetLogic = searchKeyWordAccordingTotalingListGetLogic;
    }

    /**
     * 検索キーワード集計処理を実行します
     *
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     * @return List&lt;AccessSearchKeywordTotalDto&gt;
     */
    @Override
    public List<AccessSearchKeywordTotalDto> execute(AccessSearchKeywordTotalSearchForConditionDto conditionDto) {
        // ショップSEQを設定
        //        conditionDto.setShopSeq(shopSeq);
        conditionDto.setShopSeq(1001);

        // Logicを実行
        return searchKeyWordAccordingTotalingListGetLogic.execute(conditionDto);
    }
}
