/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;

import java.util.List;
import java.util.Map;

/**
 * 商品詳細情報MAP取得<br/>
 * 商品詳細Dtoを保持するマップを取得する。<br/>
 *
 * @author ozaki
 */
public interface GoodsDetailsMapGetLogic {

    /**
     * 共通情報から会員ランク情報を取得して商品詳細情報MAP取得<br/>
     * 商品詳細Dtoを保持するマップを取得する。<br/>
     *
     * @param goodsSeqList 商品シーケンスリスト
     * @return 商品詳細情報MAP
     */
    Map<Integer, GoodsDetailsDto> execute(List<Integer> goodsSeqList);

    /**
     * 商品詳細情報MAP取得<br/>
     * 商品詳細Dtoを保持するマップを取得する。
     * <pre>
     * <商品コード, 商品詳細Dto> のMapで返却
     * 順序は指定した商品コードのリスト順を保持
     * </pre>
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細情報MAP
     */
    Map<String, GoodsDetailsDto> executeByGoodsCode(List<String> goodsCodeList);

    // PDR Migrate Customization from here

    /**
     * 商品詳細情報MAP取得<br/>
     * 商品詳細Dtoを保持するマップを取得する。
     * <pre>
     * <商品コード, 商品詳細Dto> のMapで返却
     * 順序は指定した商品コードのリスト順を保持
     * </pre>
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細情報MAP
     */
    Map<String, GoodsDetailsDto> executeByExistGoodsCode(List<String> goodsCodeList);

    /**
     * 商品詳細情報MAP取得<br/>
     * 商品詳細Dtoを保持するマップを取得する。
     * <pre>
     * <商品コード, 商品詳細Dto> のMapで返却
     * 順序は指定した商品コードのリスト順を保持
     * 存在しない場合、エラーとする。
     * </pre>
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細情報MAP
     */
    Map<String, GoodsDetailsDto> executeByResponseGoodsCode(List<String> goodsCodeList);
    // PDR Migrate Customization to here
}
