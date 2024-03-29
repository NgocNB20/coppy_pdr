/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;

/**
 * 公開あしあと商品情報取得Service
 *
 * @author ozaki
 */
public interface OpenFootPrintListGetService {

    /**
     * 公開あしあと商品リスト取得<br/>
     * 端末識別番号を元にあしあと商品情報のリストを取得する<br/>
     *
     * @param conditionDto あしあと商品検索条件DTO
     * @return 商品グループDTOリスト
     */
    List<GoodsGroupDto> execute(FootprintSearchForDaoConditionDto conditionDto, String accessUid);

    /**
     * 公開あしあと商品情報取得
     * 端末識別番号を元にあしあと商品情報のリストを取得する<br/>
     *
     * @param limit               取得件数
     * @param exceptGoodsGroupSeq 取得対象外の商品
     * @return 商品グループDTO一覧
     */
    List<GoodsGroupDto> execute(int limit, Integer exceptGoodsGroupSeq, String accessUid);

}
