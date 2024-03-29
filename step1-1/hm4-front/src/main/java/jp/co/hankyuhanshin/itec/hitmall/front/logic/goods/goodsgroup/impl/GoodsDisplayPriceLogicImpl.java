/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.goods.goodsgroup.GoodsDisplayPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GoodsDisplayPriceCreaterImpl
 * <pre>
 * 仕様はインタフェース参照
 * </pre>
 *
 * @author Makoto.Tezuka
 */
@Component
public class GoodsDisplayPriceLogicImpl implements GoodsDisplayPriceLogic {

    // PDR Migrate Customization from here
    /** 表示価格文字列生成用"～" */
    protected static final String WAVYLINE_FOR_DISPPRICE = "～";

    /** 表示価格文字列生成用"円" */
    protected static final String YEN_FOR_DISPPRICE = "円";
    // PDR Migrate Customization to here

    /**
     * 価格判定マップキー。
     * <pre>
     * 最高値(MAX)、最安値(MIN)、値引き前最安値(MIN_PREDISCOUNT)
     * </pre>
     */
    public enum PricesKey {
        /** 最高値(MAX)、最安値(MIN)、値引き前最安値(MIN_PREDISCOUNT) */
        MAX, MIN, MIN_PREDISCOUNT
    }

    /**
     * 表示価格判定マップキー。
     * <pre>
     * 値引き前価格の価格帯有無(RANGE_PREDISCOUNT)、値引き前価格有無(NOT_NULL_PREDISCOUNT)
     * </pre>
     */
    public enum DisplayPriceManagerKey {
        /** 値引き前価格の価格帯有無(RANGE_PREDISCOUNT)、値引き前価格有無(NOT_NULL_PREDISCOUNT) */
        RANGE_PREDISCOUNT, NOT_NULL_PREDISCOUNT
    }

    /**
     * 金額変更判定マップキー。
     * <pre>
     * 値下げ(CUT)、値上げ(RISE)、変更なし(SAME)
     * </pre>
     */
    public enum PriceManagerKey {
        /** 値下げ(CUT)、値上げ(RISE)、変更なし(SAME) */
        CUT, RISE, SAME
    }

    /** 商品関連Utility */
    private final GoodsUtility goodsUtility;

    public GoodsDisplayPriceLogicImpl(GoodsUtility goodsUtility) {
        this.goodsUtility = goodsUtility;
    }

    @Override
    public Map<Key, Object> create(GoodsGroupDto goodsGroupDto, boolean isSaleTimeCheck) {
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();

        // PDR Migrate Customization to here
        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
        if (CollectionUtil.isNotEmpty(goodsDtoList)) {
            return create(goodsDtoList, isSaleTimeCheck, goodsGroupEntity);
        }

        // 算出対象商品が存在しなかった場合、商品グループとしての情報を設定
        Map<Key, Object> result = new HashMap<>();
        // PDR Migrate Customization to here

        // 商品グループ最高値PCを設定
        result.put(Key.MAX_PRICE_PC, goodsGroupEntity.getGoodsGroupMaxPricePc());
        // 商品グループ最安値PCを設定
        result.put(Key.MIN_PRICE_PC, goodsGroupEntity.getGoodsGroupMinPricePc());
        // 商品グループ表示価格PCを設定
        result.put(Key.DISPLAY_PRICE_PC, goodsGroupEntity.getGoodsGroupMinPricePc());
        // 商品グループ表示値引き前価格PCを設定
        result.put(Key.DISPLAY_PREDISCOUNT_PRICE_PC, goodsGroupEntity.getPreDiscountMinPrice());

        return result;
    }

    /**
     * 仕様はインタフェース参照
     * <p>
     * // PDR Migrate Customization from here
     * GoodsDisplayPriceLogicImpl#create(List, boolean)の引数にGoodsGroupEntityを追加したメソッド<br/>
     * 「共通仕様書_商品価格の表示パターン」に従って表示価格の成形するメソッドの呼び出しを追加<br/>
     * // PDR Migrate Customization to here
     *
     * @param goodsList       List<GoodsDto>
     * @param isSaleTimeCheck boolean
     * @return Map<GoodsDisplayPriceCreater.Key, Object>
     */
    public Map<Key, Object> create(List<GoodsDto> goodsList,
                                   boolean isSaleTimeCheck,
                                   GoodsGroupEntity goodsGroupEntity) {

        Map<Key, Object> result = new HashMap<>();
        if (goodsList == null || goodsList.isEmpty()) {
            return result;
        }

        // 商品グループ用価格判定マップ（最高値、最安値、値引き前最安値を判定する）
        Map<PricesKey, BigDecimal> prices = createPrices();
        // 商品グループ用表示価格判定マップ（値引き前価格の価格帯有無、値引き前価格有無を判定する）
        Map<DisplayPriceManagerKey, Boolean> displayPriceManager = createDisplayPriceManager();
        // 商品グループ用金額変更判定マップ（値下げ、値上げ、変更なしを判定する）
        Map<PriceManagerKey, Integer> priceManager = createPriceManager();

        // 商品グループPC用価格判定マップ（最高値、最安値、値引き前最安値を判定する）
        Map<PricesKey, BigDecimal> pricesPc = createPrices();
        // 商品グループPC用表示価格判定マップ（値引き前価格の価格帯有無、値引き前価格有無を判定する）
        Map<DisplayPriceManagerKey, Boolean> displayPriceManagerPc = createDisplayPriceManager();
        // 商品グループPC用金額変更判定マップ（値下げ、値上げ、変更なしを判定する）
        Map<PriceManagerKey, Integer> priceManagerPc = createPriceManager();

        for (GoodsDto goods : goodsList) {
            GoodsEntity entity = goods.getGoodsEntity();

            // 削除商品の場合、算出対象外とする
            // 規格削除の場合、販売状態PCと販売状態MBともに削除となるのでPCのみで判定
            if (HTypeGoodsSaleStatus.DELETED.equals(entity.getSaleStatusPC())) {
                continue;
            }

            // 商品グループでの算出（販売状態を考慮しない）
            calcGoodsDisplayPrice(prices, displayPriceManager, priceManager, entity);

            // 商品表示価格PC関連情報（最高値、最安値、商品表示価格、商品表示値引き前価格）を算出する
            if (isSalePc(isSaleTimeCheck, entity)) {
                calcGoodsDisplayPrice(pricesPc, displayPriceManagerPc, priceManagerPc, entity);
            }

        }

        // 商品の情報を設定する
        setResult(result, prices, displayPriceManager, priceManager);

        // PC販売商品の情報を設定する
        setResultPc(result, pricesPc, displayPriceManagerPc, priceManagerPc);

        // PDR Migrate Customization from here
        // PC商品グループ表示価格・商品グループ表示値引き前価格を設定する
        setResultPdr(result, goodsGroupEntity);

        return result;
        // PDR Migrate Customization to here
    }

    /**
     * 商品表示価格関連情報（最高値、最安値、商品表示価格、商品表示値引き前価格）を算出する<br/>
     *
     * @param prices              価格判定マップ
     * @param displayPriceManager 表示価格判定マップ
     * @param priceManager        金額変更判定マップ
     * @param entity              商品エンティティ
     */
    protected void calcGoodsDisplayPrice(Map<PricesKey, BigDecimal> prices,
                                         Map<DisplayPriceManagerKey, Boolean> displayPriceManager,
                                         Map<PriceManagerKey, Integer> priceManager,
                                         GoodsEntity entity) {
        // 商品表示価格関連情報（最高値、最安値、商品表示価格、商品表示値引き前価格）を算出する
        BigDecimal price = entity.getGoodsPrice();
        BigDecimal preDiscountPrice = entity.getPreDiscountPrice();
        // 商品最高値の算出
        if (BigDecimal.ZERO.equals(prices.get(PricesKey.MAX)) || price.compareTo(prices.get(PricesKey.MAX)) > 0) {
            prices.put(PricesKey.MAX, price);
        }
        // 商品最安値の算出
        if (price.compareTo(prices.get(PricesKey.MIN)) < 0) {
            prices.put(PricesKey.MIN, price);
        }

        // 値引き前価格の算出
        // 値引き無しの場合は値引き前価格はNULL。比較を容易にするため販売価格と一致させる
        if (preDiscountPrice == null) {
            preDiscountPrice = price;
        } else {
            displayPriceManager.put(DisplayPriceManagerKey.NOT_NULL_PREDISCOUNT, true);
        }

        updatePriceManager(price, preDiscountPrice, priceManager);

        // 値引き前最低価格を取得
        if (prices.get(PricesKey.MIN_PREDISCOUNT) == null) {
            prices.put(PricesKey.MIN_PREDISCOUNT, preDiscountPrice);
        } else {
            // 価格帯有無のチェック
            // 有りの場合は～を最後尾に付与する必要有り
            if (!preDiscountPrice.equals(prices.get(PricesKey.MIN_PREDISCOUNT))) {
                displayPriceManager.put(DisplayPriceManagerKey.RANGE_PREDISCOUNT, true);
            }
            // 値引き前最低価格の取得
            if (preDiscountPrice.compareTo(prices.get(PricesKey.MIN_PREDISCOUNT)) < 0) {
                prices.put(PricesKey.MIN_PREDISCOUNT, preDiscountPrice);
            }
        }
    }

    /**
     * 価格判定マップ（最高値、最安値、値引き前最安値を判定する）<br/>
     *
     * @return 金額変更判定マップ
     */
    protected Map<PricesKey, BigDecimal> createPrices() {
        Map<PricesKey, BigDecimal> prices = new HashMap<>();
        // 商品最高値算出用
        prices.put(PricesKey.MAX, BigDecimal.ZERO);
        // 商品最安値算出用
        prices.put(PricesKey.MIN, GoodsUtility.GOODS_DISPLAY_MAX_PRICE);
        // 商品値引き前最安値算出用
        prices.put(PricesKey.MIN_PREDISCOUNT, null);
        return prices;
    }

    /**
     * 表示価格判定マップ（値引き前価格の価格帯有無、値引き前価格有無を判定する）<br/>
     *
     * @return 金額変更判定マップ
     */
    protected Map<DisplayPriceManagerKey, Boolean> createDisplayPriceManager() {
        Map<DisplayPriceManagerKey, Boolean> displayPriceManager = new HashMap<>();
        // 商品値引き前価格の価格帯有無判定用
        displayPriceManager.put(DisplayPriceManagerKey.RANGE_PREDISCOUNT, false);
        // 商品値引き前価格の設定有無判定用
        displayPriceManager.put(DisplayPriceManagerKey.NOT_NULL_PREDISCOUNT, false);
        return displayPriceManager;
    }

    /**
     * 金額変更判定マップ（値下げ、値上げ、変更なしを判定する）<br/>
     *
     * @return 金額変更判定マップ
     */
    protected Map<PriceManagerKey, Integer> createPriceManager() {
        Map<PriceManagerKey, Integer> priceManager = new HashMap<>();
        // 値下げ判定用
        priceManager.put(PriceManagerKey.CUT, 0);
        // 値上げ判定用
        priceManager.put(PriceManagerKey.RISE, 0);
        // 価格変更なし判定用
        priceManager.put(PriceManagerKey.SAME, 0);
        return priceManager;
    }

    /**
     * 商品の販売状態PCを判定する<br/>
     *
     * @param isSaleTimeCheck 販売期間判定フラグ
     * @param entity          対象商品エンティティ
     * @return true:販売中、false：非販売 or 販売期間外
     */
    protected boolean isSalePc(boolean isSaleTimeCheck, GoodsEntity entity) {
        if (isSaleTimeCheck) {
            // 販売期間を考慮し判定する
            return goodsUtility.isGoodsSalesPc(entity);
        } else {
            // 販売状態のみで判定する
            return HTypeGoodsSaleStatus.SALE.equals(entity.getSaleStatusPC());
        }
    }

    /**
     * 商品のPriceManagerを更新する
     *
     * @param price            販売価格
     * @param preDiscountPrice 値引き前価格
     * @param priceManager     PriceManager
     */
    protected void updatePriceManager(BigDecimal price,
                                      BigDecimal preDiscountPrice,
                                      Map<PriceManagerKey, Integer> priceManager) {
        int result = price.compareTo(preDiscountPrice);
        if (result == 0) {
            priceManager.put(PriceManagerKey.SAME, priceManager.get(PriceManagerKey.SAME) + 1);
        } else if (result < 0) {
            priceManager.put(PriceManagerKey.CUT, priceManager.get(PriceManagerKey.CUT) + 1);
        } else {
            priceManager.put(PriceManagerKey.RISE, priceManager.get(PriceManagerKey.RISE) + 1);
        }
    }

    /**
     * 商品の値上げ有無を検査する
     *
     * @param priceManager PriceManagerKey
     * @return trueの場合は値上げ、それ以外は値上げ無し
     */
    protected boolean isRaisingPrice(Map<PriceManagerKey, Integer> priceManager) {
        boolean notExistingCut = (priceManager.get(PriceManagerKey.CUT) == 0);
        boolean existingRise = (priceManager.get(PriceManagerKey.RISE) > 0);
        return notExistingCut && existingRise;
    }

    /**
     * 販売商品の有無を検査する
     *
     * @param priceManager PriceManagerKey
     * @return trueの場合は有、falseの場合は無し
     */
    protected boolean isPriceExist(Map<PriceManagerKey, Integer> priceManager) {
        boolean notExistCut = priceManager.get(PriceManagerKey.CUT) != 0;
        boolean notExistRise = priceManager.get(PriceManagerKey.RISE) != 0;
        boolean notExistSame = priceManager.get(PriceManagerKey.SAME) != 0;
        return notExistCut || notExistRise || notExistSame;
    }

    /**
     * 商品グループの価格関連情報を設定する<br/>
     *
     * @param result              算出結果
     * @param prices              価格判定マップ
     * @param displayPriceManager 表示価格判定マップ
     * @param priceManager        金額変更判定マップ
     */
    protected void setResult(Map<Key, Object> result,
                             Map<PricesKey, BigDecimal> prices,
                             Map<DisplayPriceManagerKey, Boolean> displayPriceManager,
                             Map<PriceManagerKey, Integer> priceManager) {
        // 商品グループ最高値を設定
        result.put(Key.MAX_PRICE, prices.get(PricesKey.MAX));
        // 商品グループ最安値を設定
        result.put(Key.MIN_PRICE, prices.get(PricesKey.MIN));
        // 商品グループ表示価格を設定
        result.put(Key.DISPLAY_PRICE, prices.get(PricesKey.MIN));
        // 商品グループ表示価格価格帯有無を設定
        result.put(Key.DISPLAY_PRICE_RANGE, prices.get(PricesKey.MAX).compareTo(prices.get(PricesKey.MIN)) != 0);
        // 商品グループ表示値引き前価格を設定
        result.put(
                        Key.DISPLAY_PREDISCOUNT_PRICE,
                        getDisplayPrediscountPriceValue(prices, displayPriceManager, priceManager)
                  );
        // 商品グループ表示値引き前価格価格帯有無を設定
        result.put(
                        Key.DISPLAY_PREDISCOUNT_PRICE_RANGE,
                        displayPriceManager.get(DisplayPriceManagerKey.RANGE_PREDISCOUNT)
                  );
    }

    /**
     * PC販売商品の情報を設定する<br/>
     *
     * @param result                算出結果
     * @param pricesPc              価格判定マップ
     * @param displayPriceManagerPc 表示価格判定マップ
     * @param priceManagerPc        金額変更判定マップ
     */
    protected void setResultPc(Map<Key, Object> result,
                               Map<PricesKey, BigDecimal> pricesPc,
                               Map<DisplayPriceManagerKey, Boolean> displayPriceManagerPc,
                               Map<PriceManagerKey, Integer> priceManagerPc) {
        if (isPriceExist(priceManagerPc)) {
            // 商品グループ最高値PCを設定
            result.put(Key.MAX_PRICE_PC, pricesPc.get(PricesKey.MAX));
            // 商品グループ最安値PCを設定
            result.put(Key.MIN_PRICE_PC, pricesPc.get(PricesKey.MIN));
            // 商品グループ表示価格PCを設定
            result.put(Key.DISPLAY_PRICE_PC, pricesPc.get(PricesKey.MIN));
            // 商品グループ表示価格PC価格帯有無を設定
            result.put(
                            Key.DISPLAY_PRICE_PC_RANGE,
                            pricesPc.get(PricesKey.MAX).compareTo(pricesPc.get(PricesKey.MIN)) != 0
                      );
            // 商品グループ表示値引き前価格PCを設定
            result.put(
                            Key.DISPLAY_PREDISCOUNT_PRICE_PC,
                            getDisplayPrediscountPriceValue(pricesPc, displayPriceManagerPc, priceManagerPc)
                      );
            // 商品グループ表示値引き前価格PC価格帯有無を設定
            result.put(
                            Key.DISPLAY_PREDISCOUNT_PRICE_PC_RANGE,
                            displayPriceManagerPc.get(DisplayPriceManagerKey.RANGE_PREDISCOUNT)
                      );
        } else {
            // 算出対象商品が存在しなかった場合、商品グループとしての情報を設定
            // 商品グループ最高値PCを設定
            result.put(Key.MAX_PRICE_PC, result.get(Key.MAX_PRICE));
            // 商品グループ最安値PCを設定
            result.put(Key.MIN_PRICE_PC, result.get(Key.MIN_PRICE));
            // 商品グループ表示価格PCを設定
            result.put(Key.DISPLAY_PRICE_PC, result.get(Key.DISPLAY_PRICE));
            // 商品グループ表示価格PC価格帯有無を設定
            result.put(Key.DISPLAY_PRICE_PC_RANGE, result.get(Key.DISPLAY_PRICE_RANGE));
            // 商品グループ表示値引き前価格PCを設定
            result.put(Key.DISPLAY_PREDISCOUNT_PRICE_PC, result.get(Key.DISPLAY_PREDISCOUNT_PRICE));
            // 商品グループ表示値引き前価格PC価格帯有無を設定
            result.put(Key.DISPLAY_PREDISCOUNT_PRICE_PC_RANGE, result.get(Key.DISPLAY_PREDISCOUNT_PRICE_RANGE));
        }
    }

    /**
     * 商品グループ表示値引き前価格を取得<br/>
     *
     * @param prices              価格判定マップ
     * @param displayPriceManager 表示価格判定マップ
     * @param priceManager        金額変更判定マップ
     * @return 商品グループ表示値引き前価格
     */
    protected BigDecimal getDisplayPrediscountPriceValue(Map<PricesKey, BigDecimal> prices,
                                                         Map<DisplayPriceManagerKey, Boolean> displayPriceManager,
                                                         Map<PriceManagerKey, Integer> priceManager) {
        if (prices.get(PricesKey.MIN).compareTo(prices.get(PricesKey.MIN_PREDISCOUNT)) < 0) {
            return prices.get(PricesKey.MIN_PREDISCOUNT);
        } else {
            return null;
        }
    }

    // PDR Migrate Customization to here

    /**
     * 「共通仕様書_商品価格の表示パターン」に従って表示価格の成形を行うメソッド<br/>
     * 商品グループエンティティ内のシリーズ価格、シリーズ価格記号表示フラグ、シリーズセール価格、シリーズセール価格記号表示フラグを利用する<br/>
     * シリーズセール価格がシリーズ価格以上の場合はシリーズセール価格整合性フラグを不整合にする<br/>
     *
     * @param result           算出結果
     * @param goodsGroupEntity 商品グループエンティティ
     */
    protected void setResultPdr(Map<GoodsDisplayPriceLogic.Key, Object> result, GoodsGroupEntity goodsGroupEntity) {

        // シリーズ価格格納用文字列
        StringBuilder goodsDisplayPriceStr = new StringBuilder();
        // シリーズセール価格格納用文字列
        StringBuilder goodsDisplayPreDiscountPriceStr = new StringBuilder();

        // シリーズ価格
        BigDecimal groupPrice = goodsGroupEntity.getGroupPrice();
        // シリーズセール価格
        BigDecimal groupSalePrice = goodsGroupEntity.getGroupSalePrice();
        // シリーズ価格記号表示フラグ
        HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag = goodsGroupEntity.getGroupPriceMarkDispFlag();
        // シリーズセール価格記号表示フラグ
        HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag = goodsGroupEntity.getGroupSalePriceMarkDispFlag();

        // フォーマッタ―
        NumberFormat formatter = NumberFormat.getNumberInstance();

        // シリーズ価格格納
        if (groupPrice != null) {
            goodsDisplayPriceStr.append(formatter.format(groupPrice.longValue()));
            goodsDisplayPriceStr.append(YEN_FOR_DISPPRICE);
        }

        // シリーズ価格記号表示フラグ有無チェック
        if (HTypeGroupPriceMarkDispFlag.ON.equals(groupPriceMarkDispFlag)) {
            goodsDisplayPriceStr.append(WAVYLINE_FOR_DISPPRICE);
            result.put(Key.DISPLAY_DISPPRICE_PRICE, true);
        }

        // シリーズセール価格のみnullチェック
        // CSVアップロード時にチェックが入っているため、シリーズセール価格とシリーズセール価格記号フラグどちらか片方のみnullであることは仕様上ありえないためシリーズセール価格のみnullチェックを行っている
        if (groupSalePrice != null) {
            // シリーズセール価格格納
            goodsDisplayPreDiscountPriceStr.append(formatter.format(groupSalePrice.longValue()));
            goodsDisplayPreDiscountPriceStr.append(YEN_FOR_DISPPRICE);
            // シリーズセール価格がシリーズ価格より大きい場合は、シリーズセール価格整合性フラグを不整合とする
            if (groupSalePrice.compareTo(groupPrice) >= 0) {
                goodsGroupEntity.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MISMATCH);
            } else {
                goodsGroupEntity.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
            }
            // シリーズセール価格記号表示フラグ有無チェック
            if (HTypeGroupSalePriceMarkDispFlag.ON.equals(groupSalePriceMarkDispFlag)) {
                goodsDisplayPreDiscountPriceStr.append(WAVYLINE_FOR_DISPPRICE);
                result.put(Key.DISPLAY_DISPPRICE_PREDISCOUNT_PRICE, true);
            }
        } else {
            // シリーズセール価格がない場合は整合性フラグを整合とする
            goodsGroupEntity.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
        }

        // 商品グループ表示価格を設定
        result.put(Key.DISPLAY_PRICE, goodsDisplayPriceStr.toString());
        // 商品グループ表示値引き後価格を設定
        result.put(Key.DISPLAY_PREDISCOUNT_PRICE, goodsDisplayPreDiscountPriceStr.toString());
        // 商品グループ表示価格PCを設定
        result.put(Key.DISPLAY_PRICE_PC, goodsGroupEntity.getGroupPrice());
        // 商品グループ表示値引き後価格PCを設定
        result.put(Key.DISPLAY_PREDISCOUNT_PRICE_PC, goodsGroupEntity.getGroupSalePrice());
    }
    // PDR Migrate Customization to here

}
