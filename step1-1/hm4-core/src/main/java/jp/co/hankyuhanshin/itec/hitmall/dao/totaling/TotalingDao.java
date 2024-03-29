/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.totaling;

import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;
import java.util.stream.Stream;

/**
 * 集計表Daoクラス
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface TotalingDao {
    /**
     * 検索キーワード集計<br/>
     *
     * @param conditionDto  検索条件DTO
     * @param selectOptions 検索オプション
     * @return AccessSearchKeywordTotalDtoリスト
     */
    @Select
    List<AccessSearchKeywordTotalDto> getAccessSearchKeywordTotalList(AccessSearchKeywordTotalSearchForConditionDto conditionDto,
                                                                      SelectOptions selectOptions);

    /**
     * 検索キーワード集計<br/>
     *
     * @param conditionDto 検索条件DTO
     * @return AccessSearchKeywordTotalDtoリスト
     */
    @Select
    Stream<AccessSearchKeywordTotalDto> getAccessSearchKeywordTotalListStream(
                    AccessSearchKeywordTotalSearchForConditionDto conditionDto);
}
