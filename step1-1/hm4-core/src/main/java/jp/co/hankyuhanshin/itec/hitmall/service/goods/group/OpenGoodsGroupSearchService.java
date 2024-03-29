/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;

import java.util.List;

/**
 * 公開商品グループ情報検索<br/>
 * 検索条件に該当する公開中の商品情報の商品グループのリストを取得する<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface OpenGoodsGroupSearchService {

    // SGP0003

    /**
     * 公開関商品グループ情報検索<br/>
     * 検索条件に該当する公開中の商品情報の商品グループのリストを取得する<br/>
     *
     * @param conditionDto 商品グループ検索条件DTO
     * @return 商品情報DTO
     */
    List<GoodsGroupDto> execute(GoodsGroupSearchForDaoConditionDto conditionDto);
}
