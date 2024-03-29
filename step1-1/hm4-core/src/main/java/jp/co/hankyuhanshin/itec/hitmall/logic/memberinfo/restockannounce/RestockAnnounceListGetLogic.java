// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;

import java.util.List;

/**
 * 入荷お知らせ情報リスト取得ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceListGetLogic {

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceConditionDto 入荷お知らせ検索条件DTO
     * @return 入荷お知らせエンティティリスト
     */
    List<RestockAnnounceEntity> execute(RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto);
}
// 2023-renew No65 to here