/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;

import java.util.List;

/**
 * 検索キーワード集計サービスインターフェース
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface SearchKeyWordAccordingTotalingService {
    /**
     * 検索キーワード集計処理を実行します
     *
     * @param conditionDto AccessSearchKeywordTotalSearchForConditionDto
     * @return List&lt;AccessSearchKeywordTotalDto&gt;
     */
    List<AccessSearchKeywordTotalDto> execute(AccessSearchKeywordTotalSearchForConditionDto conditionDto);
}
