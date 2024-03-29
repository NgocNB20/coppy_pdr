/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;

import java.util.List;

/**
 * 商品グループリスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupListGetLogic {
    // LGP0001

    /**
     * 商品グループリスト取得<br/>
     * 検索条件をもとに商品グループ情報リストを取得する。<br/>
     *
     * @param conditionDto 商品グループDao用検索条件DTO
     * @return 商品グループリスト
     */
    List<GoodsGroupDto> execute(GoodsGroupSearchForDaoConditionDto conditionDto);

    // 2023-renew AddNo5 from here
    /**
     * 商品グループリスト取得
     *
     * @param goodsGroupCodes 商品グループコードリスト
     * @return 商品グループリスト
     */
    List<GoodsGroupDto> execute(List<String> goodsGroupCodes);
    // 2023-renew AddNo5 to here
}
