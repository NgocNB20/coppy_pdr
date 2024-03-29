/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;

import java.util.List;

/**
 * 商品規格リスト取得ロジック<br/>
 *
 * @author hs32101
 */
public interface GoodsUnitListGetLogic {

    /**
     * 規格値1リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値1リスト
     */
    List<GoodsUnitDto> getUnit1List(String ggcd, String gcd);

    /**
     * 規格値2リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値2リスト
     */
    List<GoodsUnitDto> getUnit2List(String ggcd, String gcd);

}
