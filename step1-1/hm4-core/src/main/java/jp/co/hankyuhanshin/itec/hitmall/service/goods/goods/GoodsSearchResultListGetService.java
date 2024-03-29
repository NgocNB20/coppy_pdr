/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;

import java.util.List;

/**
 * 商品検索結果リスト取得サービス<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsSearchResultListGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param searchCondition 商品Dao用検索条件Dto
     * @return 商品検索結果Dtoリスト
     */
    List<GoodsSearchResultDto> execute(GoodsSearchForBackDaoConditionDto searchCondition, HTypeSiteType siteType);
}
