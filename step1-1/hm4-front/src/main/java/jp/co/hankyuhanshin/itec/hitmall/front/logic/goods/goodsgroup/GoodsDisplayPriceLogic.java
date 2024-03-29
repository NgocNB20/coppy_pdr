/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;

import java.util.List;
import java.util.Map;

/**
 * 商品表示単価を生成するインタフェース
 * <pre>
 * </pre>
 * @author Makoto.Tezuka
 */
public interface GoodsDisplayPriceLogic {

    /**
     * executeメソッドの戻り値に指定するキー。戻り値を複数返したいので作成。
     * 商品最高値,商品最安値,商品表示単価,商品表示単価価格帯有無,商品表示値引き前単価、商品表示値引き前単価価格帯有無
     */
    public enum Key {
        /** 商品最高値,商品最安値,商品表示単価,商品表示単価価格帯有無,商品表示値引き前単価, 商品表示値引き前単価有無  */
        MAX_PRICE, MIN_PRICE, DISPLAY_PRICE, DISPLAY_PRICE_RANGE, DISPLAY_PREDISCOUNT_PRICE, DISPLAY_PREDISCOUNT_PRICE_RANGE,
        /** 商品最高値PC,商品最安値PC,商品表示単価PC,商品表示単価PC価格帯有無,商品表示値引き前単価PC, 商品表示値引き前単価PC価格帯有無 */
        MAX_PRICE_PC, MIN_PRICE_PC, DISPLAY_PRICE_PC, DISPLAY_PRICE_PC_RANGE, DISPLAY_PREDISCOUNT_PRICE_PC, DISPLAY_PREDISCOUNT_PRICE_PC_RANGE, DISPLAY_DISPPRICE_PRICE, DISPLAY_DISPPRICE_PREDISCOUNT_PRICE
    }

    /**
     * 商品群から商品最高値、商品最安値、商品表示単価、商品表示値引き前単価を生成する
     * <pre>
     * 設計書：27_HM3_共通部仕様書_商品価格・値下げ表示.xls
     *
     * GoodsGroupDto#goodsDtoList が空の場合は販売中の商品無しと判定
     * 商品グループの最高値、最安値、表示単価、表示値引き前単価を返却する
     * </pre>
     *
     * @param goodsGroupDto 商品グループDto
     * @param isSaleTimeCheck 販売期間判定を行うか（true:販売期間を考慮し、判定する、false：販売状態のみで判定する）
     * @return 商品最高値、商品最安値、商品表示単価、商品表示単価価格帯有無、商品表示値引き前単価、商品表示値引き前単価価格帯有無
     * @see GoodsDisplayPriceLogic#create(List, boolean);
     */
    Map<Key, Object> create(GoodsGroupDto goodsGroupDto, boolean isSaleTimeCheck);

}
