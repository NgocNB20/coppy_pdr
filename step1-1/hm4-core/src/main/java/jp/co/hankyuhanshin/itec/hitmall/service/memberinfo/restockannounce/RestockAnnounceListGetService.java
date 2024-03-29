// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;

import java.util.List;

/**
 * 入荷お知らせ情報リスト取得サービス<br/>
 *
 * @author Thang Doan (VJP)
 */
public interface RestockAnnounceListGetService {

    /**
     * 入荷お知らせ情報リスト取得処理<br/>
     * <p>
     * ログイン会員の入荷お知らせ情報を取得する。<br/>
     *
     * @param restockAnnounceConditionDto 入荷お知らせ検索条件Dto
     * @return 入荷お知らせDTOリスト
     */
    List<RestockAnnounceDto> execute(RestockAnnounceSearchForDaoConditionDto restockAnnounceConditionDto);

}
// 2023-renew No65 to here
