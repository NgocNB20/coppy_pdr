/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFavoriteSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseCustomerSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.cart.CartModelItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsIndexModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsStockItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsItem;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品系ヘルパークラス
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsUtility {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUtility.class);

    /**
     * 商品表示価格最高値
     * 商品最安値算出時に使用(DBの桁数に合わせて8ケタ)
     */
    public static final BigDecimal GOODS_DISPLAY_MAX_PRICE = new BigDecimal("99999999");

    /**
     * 画像なし画像名
     */
    public static final String NO_IMAGE_FILENAME = "noimage";

    /**
     * 区切り文字：スラッシュ
     */
    public static final String SEPARATOR_SLASH = "/";

    /**
     * 画像なし画像拡張子
     */
    protected static final String EXTENSION = ".gif";

    // PDR Migrate Customization from here
    /**
     * クール便適用期間FROM 00:00:00 判定用定数
     */
    protected static final String FROM_TIME_HH_ADD = "00:00:00";

    /**
     * クール便適用期間TO 23:59:59 判定用定数
     */
    protected static final String TO_TIME_HH_ADD = "23:59:59";

    /**
     * 比較用 年末 定数
     */
    protected static final String END_OF_THE_YEAR = "12/31";

    /**
     * エラーメッセージ：販売可否判定が取得できなかった場合エラー
     */
    protected static final String MSGCD_NOT_GET_SALESADVISABILITY = "PDR-0437-021-L-E";

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * 心意気価格_商品名接頭辞
     */
    protected static final String EMOTION_PRICE_PREFIX_NAME = "【心意気価格】";

    /**
     * 心意気価格_商品コード接頭辞
     */
    protected static final String EMOTION_PRICE_PREFIX_GOODS_CODE = "kp";

    // 2023-renew No11 from here
    /**
     * 商品詳細画面：価格情報最大文字数
     */
    public static final Integer MAX_LENGTH_PRICE_INFO_LIMIT = 10;

    /**
     * 商品レベル区切り文字
     */
    private static final String LEVEL_SEPARATOR_CHARACTER = "～";
    // 2023-renew No11 to here

    // 2023-renew No10 to here
    /** アイコン表示: 在庫切れ */
    public static final String ICON_OUT_OF_STOCK_TEXT = "在庫切れ";

    /** アイコン表示: 受付停止中 */
    public static final String ICON_RECEPTION_CLOSED_TEXT = "受付停止中";

    /** アイコン表示: 取扱中止 */
    public static final String ICON_SERVICE_STOP_TEXT = "取扱中止";

    /** アイコン表示: 販売終了しました */
    public static final String ICON_DISCONTINUED_TEXT = "販売終了しました";
    // 2023-renew No10 to here
    // PDR Migrate Customization to here

    /**
     * コンストラクタ
     *
     * @param dateUtility 日付関連Utilityクラス
     */
    @Autowired
    public GoodsUtility(DateUtility dateUtility) {
        this.dateUtility = dateUtility;
    }

    /**
     * 商品画像パスの取得
     * ※商品画像パスがない場合は、画像なし画像を返す
     *
     * @param goodsImagePath 商品画像パス(null許可)
     * @return 実際の商品画像パス
     */
    public String getGoodsImagePath(String goodsImagePath) {

        // 画像がないまたは、非表示の場合は、画像なし画像
        String imagePath = null;
        if (StringUtil.isEmpty(goodsImagePath)) {
            imagePath = NO_IMAGE_FILENAME + EXTENSION;
        } else {
            imagePath = goodsImagePath;
        }

        // システム設定パス取得
        String path = PropertiesUtil.getSystemPropertiesValue("images.path.goods");
        if (path == null) {
            return imagePath;
        }
        return path + SEPARATOR_SLASH + imagePath;
    }

    /**
     * 画像名の取得
     * 画像が[存在しない or 非表示]の場合nullを返す
     *
     * @param goodsGroupDto 商品グループ
     * @return 商品画像名
     */
    public String getImageFileName(GoodsGroupDto goodsGroupDto) {
        return getImageFileName(goodsGroupDto.getGoodsGroupImageEntityList(), null);
    }

    /**
     * 画像名の取得
     * 画像が[存在しない or 非表示]の場合nullを返す
     *
     * @param goodsDetailsDto 商品詳細
     * @return 商品画像名
     */
    public String getImageFileName(GoodsDetailsDto goodsDetailsDto) {
        return getImageFileName(goodsDetailsDto.getGoodsGroupImageEntityList(), goodsDetailsDto.getUnitImage());
    }

    /**
     * 画像名の取得
     * 画像が存在しない場合nullを返す
     *
     * @param goodsGroupImageEntityList 商品画像Entityリスト
     * @param goodsImageEntity          商品画像Entity
     * @return 商品画像名
     */
    private String getImageFileName(List<GoodsGroupImageEntity> goodsGroupImageEntityList,
                                    GoodsImageEntity goodsImageEntity) {

        if (goodsImageEntity != null) {
            // 規格画像が存在する場合は、規格画像を返却
            return goodsImageEntity.getImageFileName();
        } else {
            // 商品グループ画像を返す
            if (goodsGroupImageEntityList != null && !goodsGroupImageEntityList.isEmpty()) {
                for (GoodsGroupImageEntity entity : goodsGroupImageEntityList) {
                    // 1件目を返却
                    return entity.getImageFileName();
                }
            }
        }
        return null;
    }

    /**
     * 商品公開判定
     * ※現在日時
     *
     * @param goodsOpenStatus 商品公開状態
     * @param openStartTime   公開開始日時
     * @param openEndTime     公開終了日時
     * @return true=公開、false=公開でない
     */
    public boolean isGoodsOpen(HTypeOpenDeleteStatus goodsOpenStatus, Timestamp openStartTime, Timestamp openEndTime) {

        // 日付関連Helper取得
        DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
        // 現在日時
        Timestamp currentTime = dateHelper.getCurrentTime();
        return isGoodsOpen(goodsOpenStatus, openStartTime, openEndTime, currentTime);
    }

    /**
     * 商品公開判定
     * ※現在日時
     *
     * @param goodsOpenStatus 商品公開状態
     * @param openStartTime   公開開始日時
     * @param openEndTime     公開終了日時
     * @param targetTime      比較時間
     * @return true=公開、false=公開でない
     */
    public boolean isGoodsOpen(HTypeOpenDeleteStatus goodsOpenStatus,
                               Timestamp openStartTime,
                               Timestamp openEndTime,
                               Timestamp targetTime) {

        // 公開
        if (goodsOpenStatus.equals(HTypeOpenDeleteStatus.OPEN)) {
            // 日付関連Helper取得
            DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
            return dateHelper.isOpen(openStartTime, openEndTime, targetTime);
        }
        return false;
    }

    /**
     * PC用商品販売判定
     * ※現在日時
     *
     * @param entity 商品
     * @return true=販売中、false=販売中でない
     */
    public boolean isGoodsSalesPc(GoodsEntity entity) {
        return isGoodsSales(entity.getSaleStatusPC(), entity.getSaleStartTimePC(), entity.getSaleEndTimePC());
    }

    /**
     * 商品販売判定
     * ※現在日時
     *
     * @param goodsSaleStatus 商品販売状態
     * @param saleStartTime   販売開始日時
     * @param saleEndTime     販売終了日時
     * @return true=販売中、false=販売中でない
     */
    public boolean isGoodsSales(HTypeGoodsSaleStatus goodsSaleStatus, Timestamp saleStartTime, Timestamp saleEndTime) {

        // 現在日時
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();
        return isGoodsSales(goodsSaleStatus, saleStartTime, saleEndTime, currentTime);
    }

    /**
     * 商品販売判定
     *
     * @param goodsSaleStatus 商品販売状態
     * @param saleStartTime   販売開始日時
     * @param saleEndTime     販売終了日時
     * @param targetTime      比較時間
     * @return true=販売中、false=販売中でない
     */
    public boolean isGoodsSales(HTypeGoodsSaleStatus goodsSaleStatus,
                                Timestamp saleStartTime,
                                Timestamp saleEndTime,
                                Timestamp targetTime) {

        // 販売
        if (HTypeGoodsSaleStatus.SALE.equals(goodsSaleStatus)) {
            // 日付関連Helper取得
            DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
            return dateHelper.isOpen(saleStartTime, saleEndTime, targetTime);
        }
        return false;
    }

    // 2023-renew No11 from here

    /**
     * 商品非販売判定
     *
     * @param goodsSaleStatus 商品販売状態
     * @param saleStartTime   販売開始日時
     * @param saleEndTime     販売終了日時
     * @return true=販売中、false=販売中でない
     */
    public boolean isGoodsItemNoSales(HTypeGoodsSaleStatus goodsSaleStatus,
                                      Timestamp saleStartTime,
                                      Timestamp saleEndTime) {

        // 非販売
        DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();
        if (HTypeGoodsSaleStatus.NO_SALE.equals(goodsSaleStatus) || dateHelper.isNoOpen(saleStartTime, saleEndTime, currentTime)) {
            return true;
        }
        return false;
    }
    // 2023-renew No11 to here

    /**
     * system.properties から新着商品画像の表示日数（whatsnew.view.days）を取得する
     *
     * @return 新着商品画像の表示期間
     */
    public int getWhatsNewViewDays() {
        String whatsnewViewDays = PropertiesUtil.getSystemPropertiesValue("whatsnew.view.days");
        int addDay = 0;
        if (StringUtil.isNotEmpty(whatsnewViewDays)) {
            addDay = Integer.parseInt(whatsnewViewDays);
        }

        return addDay;
    }

    /**
     * 新着商品をいつまで表示するかの、「いつまで」の日付を取得
     * この日付まで新着商品として表示される
     *
     * @param whatsNewDate 商品データに登録されている新着日付を想定
     * @return 指定された日付に、新着商品画像の表示期間を足した日付
     */
    public Timestamp getRealWhatsNewDate(Timestamp whatsNewDate) {
        // 日付関連Helper取得
        DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
        return dateHelper.getAmountDayTimestamp(getWhatsNewViewDays(), true, whatsNewDate);
    }

    /**
     * 在庫チェック
     *
     * @param stockManagementFlag 在庫管理フラグ
     * @param salesPossibleStock  販売可能在庫数
     * @return true..在庫あり
     */
    public boolean isGoodsStock(HTypeStockManagementFlag stockManagementFlag, BigDecimal salesPossibleStock) {
        if (stockManagementFlag.equals(HTypeStockManagementFlag.OFF)) {
            // 在庫管理なし
            return true;
        } else if (salesPossibleStock.intValue() > 0) {
            // 在庫あり
            return true;
        }
        return false;
    }

    /**
     * 在庫なしメッセージ付加
     * ※在庫切れの規格値に在庫なしの文言を付加する
     *
     * @param value 文言付加対象文字列
     * @return 文字列
     */
    public String addNoStockMessage(String value) {
        // PDR Migrate Customization from here
        return value;
        // PDR Migrate Customization to here
    }

    /**
     * 商品グループ在庫の表示判定
     *
     * @param status 在庫状況
     * @return true..表示 false..非表示
     */
    public boolean isGoodsGroupStock(HTypeStockStatusType status) {
        if (status == null) {
            return false;
        }
        return status.isDisplay();
    }

    /**
     * 規格の全組み合わせを作成する(規格プルダウン表示用)
     * <pre>
     * 商品コードと規格画像コードの全組合わせを文字列で返却する
     * 【例】
     * [商品情報]
     * 商品コード/規格名1/規格名2/規格画像コード/
     * goods1/赤/S/RED
     * goods2/青/S/BLUE
     * goods3/緑/S/GREEN
     * goods4/赤/M/RED
     * [戻り値]
     * goods1,RED,goods2,BLUE,goods3,GREEN,goods4,RED
     * </pre>
     *
     * @param goodsDtoList 商品DTOリスト
     * @param gcdMap       商品コードMAP(key=商品コード、value=規格配列[規格１、規格２])
     * @param unit1Map     規格１MAP(key=規格１、value=規格２配列[商品コード、規格２、在庫フラグ])
     */
    public void createAllUnitMap(List<GoodsDto> goodsDtoList,
                                 Map<String, String[]> gcdMap,
                                 Map<String, List<String[]>> unit1Map) {

        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        // プルダウン作成のために各MAP(全規格の組み合わせ)を作成
        for (GoodsDto goodsDto : goodsDtoList) {

            GoodsEntity entity = goodsDto.getGoodsEntity();

            // 販売チェック
            // 購入可能なもののみ、プルダウンにセット
            if (!goodsUtility.isGoodsSales(
                            entity.getSaleStatusPC(), entity.getSaleStartTimePC(), entity.getSaleEndTimePC())) {
                continue;
            }

            // 規格情報を取得
            String unitGcd = entity.getGoodsCode();
            String unitValue1 = entity.getUnitValue1();
            String unitValue2 = entity.getUnitValue2();
            Boolean isStock = goodsUtility.isGoodsStock(goodsDto.getGoodsEntity().getStockManagementFlag(),
                                                        goodsDto.getStockDto().getSalesPossibleStock()
                                                       );

            // 規格２配列リスト
            List<String[]> tmpUnit1MapArrayList = new ArrayList<>();
            if (unit1Map.get(unitValue1) != null) {
                // 規格情報MAPに現在の規格１があれば、規格２配列リストへ追加
                tmpUnit1MapArrayList = unit1Map.get(unitValue1);
            }

            // MAPに追加する配列を生成
            String[] tmpGcdMapArray = {unitValue1, unitValue2};
            String[] tmpUnit1MapArray = {unitGcd, unitValue2, isStock.toString()};
            tmpUnit1MapArrayList.add(tmpUnit1MapArray);

            // MAPに格納
            gcdMap.put(unitGcd, tmpGcdMapArray);
            unit1Map.put(unitValue1, tmpUnit1MapArrayList);
        }
    }

    /**
     * 商品表示値引き前単価の表示可否
     *
     * @param preDiscountPrice 値引き前価格
     * @return 表示可はtrue、それ以外はfalse
     */
    public boolean isDisplayPreDiscountPrice(BigDecimal preDiscountPrice) {
        return preDiscountPrice != null;
    }

    /**
     * 商品表示価格帯の有無をチェック
     *
     * @param minPrice 商品最安値
     * @param maxPrice 商品最高値
     * @return true:価格帯あり false:価格帯なし
     */
    public boolean isGoodsDisplayPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return minPrice.compareTo(maxPrice) != 0;
        }
        return false;
    }

    // PDR Migrate Customization to here

    /**
     * 商品金額と消費税率から消費税額を計算します。
     *
     * @param goodsPrice 商品金額
     * @param taxRate    消費税率
     * @return 消費税額 (小数点切り捨て)
     */
    public BigDecimal calculationGoodsPriceTax(BigDecimal goodsPrice, BigDecimal taxRate) {

        return goodsPrice.multiply(taxRate).setScale(0, RoundingMode.DOWN);
    }

    /**
     * クール便適用期間中かどうかのチェックを行います。
     *
     * @param strCoolSendFrom クール便適用期間FROM
     * @return true...適用期間内/false...適用期間外
     * @pram strCoolSendTo クール便適用期間TO
     */
    public boolean checkCoolSend(String strCoolSendFrom, String strCoolSendTo) {

        Timestamp coolSendFrom = null;
        // クール便適用期間FROM
        String dateFrom = dateUtility.getCurrentYWithSlash() + strCoolSendFrom + " " + FROM_TIME_HH_ADD;
        if (dateUtility.isDate(dateFrom, DateUtility.YMD_SLASH_HMS)) {
            // クール便適用期間FROMをTimestamp型に変換(yyyy/MM/dd HH:mm:ss)
            coolSendFrom = dateUtility.toTimestampValue(dateFrom, DateUtility.YMD_SLASH + " " + DateUtility.HMS);
        }

        // クール便適用期間TO
        Timestamp coolSendTo = null;
        String dateTo = dateUtility.getCurrentYWithSlash() + strCoolSendTo + " " + TO_TIME_HH_ADD;
        if (dateUtility.isDate(dateTo, DateUtility.YMD_SLASH_HMS)) {
            // クール便適用期間TOをTimestamp型に変換(yyyy/MM/dd HH:mm:ss)
            coolSendTo = dateUtility.toTimestampValue(dateTo, DateUtility.YMD_SLASH + " " + DateUtility.HMS);
        }

        if (coolSendFrom != null && coolSendTo != null) {

            // クール便適用期間FROMよりTOのが過去の場合
            if (coolSendFrom.after(coolSendTo)) {

                // 年末 現在年 + 12/31 + 23:59:59
                Timestamp endOfTheYear = dateUtility.toTimestampValue(
                                dateUtility.getCurrentYWithSlash() + END_OF_THE_YEAR + " " + TO_TIME_HH_ADD,
                                DateUtility.YMD_SLASH + " " + DateUtility.HMS
                                                                     );
                // 現在時刻がFROM~年末の間の場合
                if (dateUtility.isOpen(coolSendFrom, endOfTheYear)) {
                    // クール便適用期間TOに1年加算
                    coolSendTo = dateUtility.getAmountYearTimestamp(1, true, coolSendTo);
                } else {
                    // クール便適用期間FROMに1年減算
                    coolSendFrom = dateUtility.getAmountYearTimestamp(1, false, coolSendFrom);
                }
            }
        }

        // クール便適用期間内かチェック
        if (dateUtility.isOpen(coolSendFrom, coolSendTo)) {
            return true;
        }

        return false;
    }

    /**
     * 値下げ有無を検査する（SKU単位）
     * <pre>
     * </pre>
     *
     * @param priceInTax       販売価格
     * @param preDiscountPrice 値引き前価格
     * @return 販売価格 < 値引き前価格の場合はtrue、それ以外はfalse
     */
    public boolean isSale(BigDecimal priceInTax, BigDecimal preDiscountPrice) {
        return preDiscountPrice != null && priceInTax.compareTo(preDiscountPrice) > 0;
    }

    /**
     * 商品が心意気商品かどうかを判定する。
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @return true:心意気商品 / false:通常商品
     */
    public boolean isEmotionPriceGoods(GoodsDetailsDto goodsDetailsDto) {

        if (HTypeEmotionPriceType.EMOTIONPRICE.equals(goodsDetailsDto.getEmotionPriceType())) {
            return true;
        }
        return false;
    }

    /**
     * 商品コードが心意気商品の場合、元商品の商品コードに変換する。
     *
     * @param goodsCode 心意気商品コード
     * @return goodsCode 元商品コード
     */
    public String convertEmotionPriceGoodsCodeToNormalGoodsCode(String goodsCode) {

        if (goodsCode.endsWith(EMOTION_PRICE_PREFIX_GOODS_CODE)) {
            int last = goodsCode.lastIndexOf(EMOTION_PRICE_PREFIX_GOODS_CODE);
            goodsCode = goodsCode.substring(0, last);
        }
        return goodsCode;
    }

    /**
     * 心意気商品の場合、商品名に接頭辞を付与する。<br/>
     *
     * @param goodsDetailsDto 商品詳細DTO
     * @return 変換後商品名
     */
    public String convertEmotionPriceGoodsNameToNormalGoodsName(GoodsDetailsDto goodsDetailsDto) {

        if (isEmotionPriceGoods(goodsDetailsDto)) {
            return EMOTION_PRICE_PREFIX_NAME + goodsDetailsDto.getGoodsGroupName();
        }
        return goodsDetailsDto.getGoodsGroupName();
    }

    /**
     * 心意気商品の場合、商品名に接頭辞を付与する。<br/>
     *
     * @param orderGoodsEntity 受注商品
     * @return 変換後商品名
     */
    public String convertEmotionPriceGoodsNameToNormalGoodsName(OrderGoodsEntity orderGoodsEntity) {

        // 心意気商品の場合は数量欄をラベルにする
        if (HTypeDiscountsType.SALEON_EMOTION_PRICE.equals(orderGoodsEntity.getDiscountsType())) {
            return EMOTION_PRICE_PREFIX_NAME + orderGoodsEntity.getGoodsGroupName();
        }
        return orderGoodsEntity.getGoodsGroupName();
    }

    /**
     * 心意気商品の場合、商品名に接頭辞を付与する。<br/>
     *
     * @param goodsName    商品名
     * @param discountFlag 適用割引
     * @return 変換後商品名
     */
    public String convertEmotionPriceGoodsNameToNormalGoodsName(String goodsName, String discountFlag) {

        if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(discountFlag)) {
            return EMOTION_PRICE_PREFIX_NAME + goodsName;
        }
        return goodsName;
    }

    // PDR Migrate Customization to here

    // 2023-renew No11 from here

    /**
     * 在庫リストを商品コードごとに並べ替える
     *
     * @param goodsStockItems   在庫商品リスト
     * @param naturalOrderSet
     * @return 在庫商品リスト
     * @param <T>
     */
    public <T> List<GoodsStockItem> sortByNaturalOrder(List<GoodsStockItem> goodsStockItems, Set<String> naturalOrderSet) {
        if (CollectionUtil.isEmpty(naturalOrderSet)) {
            return goodsStockItems;
        }
        Map<String, List<GoodsStockItem>> newGroupMap = new LinkedHashMap<>();
        naturalOrderSet.forEach(key -> {
            newGroupMap.put(key, new ArrayList<>());
        });
        goodsStockItems.forEach(item -> {
            newGroupMap.get(item.getGoodsCode()).add(item);
        });
        return newGroupMap.values().stream()
                          .flatMap(Collection::stream)
                          .collect(Collectors.toList());
    }

    /**
     * 複数数量閾値フラグ設定
     *
     * @param goodsStockItems 在庫商品Itemリスト
     */
    public void setMultiLevelFlag(List<GoodsStockItem> goodsStockItems) {
        for (int i = 0; i < goodsStockItems.size(); i++) {
            String goodsCode = goodsStockItems.get(i).getGoodsCode();
            if ((i < goodsStockItems.size() - 1) && goodsCode.equals(goodsStockItems.get(i + 1).getGoodsCode())) {
                goodsStockItems.get(i).setMultiLevelFlag(true);
            }
        }
    }

    /**
     * 価格情報をマージする
     *
     * @param inputGoodsStockItems  在庫商品リスト
     * @param orderedSet            製品コード一リスト
     */
    public List<GoodsStockItem> mergePriceInfo(List<GoodsStockItem> inputGoodsStockItems, Set<String> orderedSet, Logger log) {
        // 商品を商品コード、価格、製品レベルの順に並べます
        List<GoodsStockItem> goodsStockItems = inputGoodsStockItems.stream()
                .sorted(Comparator.comparing(GoodsStockItem::getGoodsCode)
                        .thenComparing(GoodsStockItem::getPrice)
                        .thenComparing(goodsStockItem -> {
                            if (StringUtils.isBlank(goodsStockItem.getLevel())) {
                                return null;
                            }
                            try {
                                return Integer.parseInt(
                                        StringUtils.split(goodsStockItem.getLevel(), LEVEL_SEPARATOR_CHARACTER)[0]);
                            } catch (Exception e) {
                                log.warn(e.getMessage());
                                return null;
                            }
                        }, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        // 製品コード、価格、販売価格で製品をグループ化します
        // 前のステップでソートされた入力データ
        Map<String, Map<String, Map<Optional<String>, List<GoodsStockItem>>>> groupByCodeAndPriceMap = goodsStockItems.stream().collect(
                        Collectors.groupingBy(GoodsStockItem::getGoodsCode, Collectors.groupingBy(GoodsStockItem::getPrice, Collectors.groupingBy(goodsStockItem -> {
                            // 適切なセール価格を返す(NULL の場合はオプション)
                            Optional<String> salePrice = Optional.empty();
                            if (StringUtils.isNotEmpty(goodsStockItem.getCustomerSalePrice())
                                && goodsStockItem.getCustomerSalePrice().length() <= MAX_LENGTH_PRICE_INFO_LIMIT) {
                                salePrice = Optional.of(goodsStockItem.getCustomerSalePrice());
                            } else if (StringUtils.isNotEmpty(goodsStockItem.getSalePrice())
                                && goodsStockItem.getSalePrice().length() <= MAX_LENGTH_PRICE_INFO_LIMIT) {
                                salePrice = Optional.of(goodsStockItem.getSalePrice());
                            }

                            if (MapUtils.isEmpty(getLevelRange(goodsStockItem.getLevel(), LEVEL_SEPARATOR_CHARACTER))) {
                                String salePriceSub = salePrice.orElse("");
                                return Optional.of(salePriceSub + goodsStockItem.getLevel());
                            } else {
                                return salePrice;
                            }
                            }, LinkedHashMap::new, Collectors.toList()))));

        // LinkedHashMap を使用して入力順序を維持する
        Map<String, GoodsStockItem> goodsStockUniqueItemMap = new LinkedHashMap<>();
        groupByCodeAndPriceMap.forEach((goodsCode, groupByCodeMap) -> {
            groupByCodeMap.forEach((price, groupByPriceMap) -> {
                groupByPriceMap.forEach((salePriceOpt, groupBySalePriceList) -> {
                    // リストに多くの要素がある場合は、製品レベルを結合する
                    if (groupBySalePriceList.size() == 1) {
                        goodsStockUniqueItemMap.put(goodsCode + "_" + price + "_" + salePriceOpt, groupBySalePriceList.get(0));
                    } else {
                        if (Objects.isNull(goodsStockUniqueItemMap.get(goodsCode + "_" + price + "_" + salePriceOpt))) {
                            goodsStockUniqueItemMap.put(goodsCode + "_" + price + "_" + salePriceOpt, groupBySalePriceList.get(0));
                        }
                        Map<Integer, Integer> levelRange = this.getLevelRange(groupBySalePriceList.get(0).getLevel(), LEVEL_SEPARATOR_CHARACTER);
                        if (MapUtils.isEmpty(levelRange)) {
                            goodsStockUniqueItemMap.put(goodsCode + "_" + price + "_" + salePriceOpt + groupBySalePriceList.get(0).getLevel(), groupBySalePriceList.get(0));
                        } else {
                            Integer firstLevelInRange = levelRange.keySet().iterator().next();
                            Integer lastLevelInRange = levelRange.get(firstLevelInRange);
                            Integer nextFirstLevelInRange = null;
                            Integer nextLastLevelInRange = null;
                            GoodsStockItem nextGoodsStockItem = null;
                            for (int i = 1; i < groupBySalePriceList.size(); i++) {
                                nextGoodsStockItem = groupBySalePriceList.get(i);
                                levelRange = this.getLevelRange(nextGoodsStockItem.getLevel(),
                                                                LEVEL_SEPARATOR_CHARACTER
                                                               );
                                nextFirstLevelInRange = levelRange.keySet().iterator().next();
                                nextLastLevelInRange = levelRange.get(nextFirstLevelInRange);
                                if (Objects.nonNull(nextFirstLevelInRange)) {
                                    if (Objects.isNull(firstLevelInRange) || firstLevelInRange > nextFirstLevelInRange) {
                                        firstLevelInRange = nextFirstLevelInRange;
                                    }
                                }

                                if (Objects.isNull(lastLevelInRange)) {
                                    if (Objects.nonNull(nextLastLevelInRange)) {
                                        lastLevelInRange = nextLastLevelInRange;
                                    }
                                } else if (Objects.isNull(nextLastLevelInRange)) {
                                    lastLevelInRange = null;
                                } else if (lastLevelInRange < nextLastLevelInRange) {
                                    lastLevelInRange = nextLastLevelInRange;
                                }
                            }

                            goodsStockUniqueItemMap.get(goodsCode + "_" + price + "_" + salePriceOpt)
                                                   .setLevel(this.getLevelString(firstLevelInRange, lastLevelInRange,
                                                                                 LEVEL_SEPARATOR_CHARACTER
                                                                                ));
                        }
                    }
                });
            });
        });

        // 結果を返す
        // 製品コード一リストとの元の順序を維持する
        return this.sortByNaturalOrder(new ArrayList<>(goodsStockUniqueItemMap.values().stream()
                .sorted(Comparator.comparing(goodsStockItem -> {
                    if (StringUtils.isBlank(goodsStockItem.getLevel())) {
                        return null;
                    }
                    try {
                        return Integer.parseInt(
                                        StringUtils.split(goodsStockItem.getLevel(), LEVEL_SEPARATOR_CHARACTER)[0]);
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                        try {
                            return NumberFormat.getInstance().parse(goodsStockItem.getLevel()).intValue();
                        } catch (ParseException parseException) {
                            log.warn(e.getMessage());
                            return null;
                        }
                    }
                }, Comparator.nullsFirst(Comparator.naturalOrder())))
            .collect(Collectors.toList())), orderedSet);
    }

    /**
     * 製品レベル文字列から値を抽出する
     *
     * @param level             製品レベル
     * @param splitCharacter
     * @return 製品レベルの詳細
     */
    private Map<Integer, Integer> getLevelRange(String level, String splitCharacter) {
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        if (StringUtils.isBlank(level)) {
            return new HashMap<>();
        }
        String[] levelRange = StringUtils.split(level, splitCharacter);
        if (levelRange.length == 0) {
            return new HashMap<>();
        }
        Map<Integer, Integer> levelRangeMap = new HashMap<>();
        int levelInt;
        try {
            levelInt = conversionUtility.toInteger(levelRange[0]);
            if (levelRange.length == 1) {
                levelRangeMap.put(levelInt, null);
            } else {
                levelRangeMap.put(levelInt, conversionUtility.toInteger(levelRange[1]));
            }
        } catch (NumberFormatException e) {
            LOGGER.warn(e.getMessage());
        }
        return levelRangeMap;
    }

    /**
     * 製品レベルの文字列連結
     *
     * @param firstLevelInRange 最初の値
     * @param lastLevelInRange  最終値
     * @param splitCharacter
     * @return 製品レベル
     */
    private String getLevelString(Integer firstLevelInRange, Integer lastLevelInRange, String splitCharacter) {
        return new StringBuilder().append(Objects.toString(firstLevelInRange, ""))
                                  .append(splitCharacter)
                                  .append(Objects.toString(lastLevelInRange, ""))
                                  .toString();
    }

    /**
     * 商品規格単位のアイコン表示
     *
     * @param goodsStockItem    在庫商品
     * @return 販売制御状態
     */
    public String getGoodsIconDisplay(GoodsStockItem goodsStockItem) {
        // アイコン表示: 販売終了しました
        // 日付関連Helper取得
        DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
        if ((HTypeGoodsSaleStatus.SALE.equals(goodsStockItem.getSaleStatus()) &&
            dateHelper.isEndSale(goodsStockItem.getSaleEndTime(), dateHelper.getCurrentTime())) ||
            HTypeOpenDeleteStatus.NO_OPEN.equals(goodsStockItem.getGoodsOpenStatus())) {
            return GoodsUtility.ICON_DISCONTINUED_TEXT;
        }

        //アイコン表示: 取扱中止
        if (HTypeOpenDeleteStatus.OPEN.equals(goodsStockItem.getGoodsOpenStatus()) &&
            HTypeSaleControlType.DISCONTINUED.equals(goodsStockItem.getSaleControl())) {
            return GoodsUtility.ICON_SERVICE_STOP_TEXT;
        }

        // アイコン表示: 受付停止中
        if (HTypeOpenDeleteStatus.OPEN.equals(goodsStockItem.getGoodsOpenStatus()) &&
            HTypeGoodsSaleStatus.NO_SALE.equals(goodsStockItem.getSaleStatus()) &&
            (HTypeSaleControlType.NORMAL.equals(goodsStockItem.getSaleControl()) ||
             HTypeSaleControlType.LIMITEDSTOCKNORMAL.equals(goodsStockItem.getSaleControl()) ||
             HTypeSaleControlType.NOBACKORDER.equals(goodsStockItem.getSaleControl()))) {
            return GoodsUtility.ICON_RECEPTION_CLOSED_TEXT;
        }

        // アイコン表示: 在庫切れ
        if (HTypeOpenDeleteStatus.OPEN.equals(goodsStockItem.getGoodsOpenStatus()) &&
            HTypeGoodsSaleStatus.SALE.equals(goodsStockItem.getSaleStatus()) &&
            GoodsIndexModel.DISP_OUT_OF_STOCK.equals(goodsStockItem.getStock())) {
            if (HTypeSaleControlType.LIMITEDSTOCKNORMAL.equals(goodsStockItem.getSaleControl())
                || HTypeSaleControlType.NOBACKORDER.equals(goodsStockItem.getSaleControl())) {
                return GoodsUtility.ICON_OUT_OF_STOCK_TEXT;
            }
        }
        return null;
    }

    public String getGoodsIconDisplay(CartModelItem cartModelItem) {
        // アイコン表示: 販売終了しました
        // 日付関連Helper取得
        DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
        if (dateHelper.isEndSale(cartModelItem.getSaleEndTime(), dateHelper.getCurrentTime()) ||
            !HTypeOpenDeleteStatus.OPEN.equals(cartModelItem.getGoodsOpenStatus())) {
            return GoodsUtility.ICON_DISCONTINUED_TEXT;
        }

        //アイコン表示: 取扱中止
        if (HTypeSaleControlType.DISCONTINUED.equals(cartModelItem.getSaleControl())) {
            return GoodsUtility.ICON_SERVICE_STOP_TEXT;
        }

        // アイコン表示: 受付停止中
        if (HTypeGoodsSaleStatus.NO_SALE.equals(cartModelItem.getSaleStatus())) {
            if (HTypeSaleControlType.NORMAL.equals(cartModelItem.getSaleControl())
                || HTypeSaleControlType.LIMITEDSTOCKNORMAL.equals(cartModelItem.getSaleControl())
                || HTypeSaleControlType.NOBACKORDER.equals(cartModelItem.getSaleControl())) {
                return GoodsUtility.ICON_RECEPTION_CLOSED_TEXT;
            }
        }

        // アイコン表示: 在庫切れ
        if (HTypeGoodsSaleStatus.SALE.equals(cartModelItem.getSaleStatus())
            && GoodsIndexModel.DISP_OUT_OF_STOCK.equals(cartModelItem.getStock())) {
            if (HTypeSaleControlType.LIMITEDSTOCKNORMAL.equals(cartModelItem.getSaleControl())
                || HTypeSaleControlType.NOBACKORDER.equals(cartModelItem.getSaleControl())) {
                return GoodsUtility.ICON_OUT_OF_STOCK_TEXT;
            }
        }
        return null;
    }
    // 2023-renew No11 to here

    // 2023-renew No64 from here

    /**
     * 表示する商品説明を条件に基づいて判定する<br/>
     * 【条件1】現在日時 が 公開開始日時より未来 の商品説明の値を設定<br/>
     * 【条件2】公開開始日時1・公開開始日時2を比較 し、より現在日時に近い商品説明の値 を設定
     *
     * @param startTime1 公開開始日時1
     * @param startTime2 公開開始日時2
     * @param note1 商品説明1
     * @param note2 商品説明2
     * @return 表示する商品説明
     */
    public static String compareGoodsNoteTimestamps(Timestamp startTime1,
                                                    Timestamp startTime2,
                                                    String note1,
                                                    String note2) {
        Date currentDate = new Date();
        Timestamp currentTime = new Timestamp(currentDate.getTime());
        String disp = note1;

        // 公開開始日時1の判定
        if (startTime1 != null) {
            if (currentTime.after(startTime1)) {
                disp = note1;
            } else {
                disp = note2;
            }
        }

        // 公開開始日時2の判定
        if (startTime2 != null) {
            if (currentTime.after(startTime2)) {
                disp = note2;
            } else {
                disp = note1;
            }
        }

        // 公開開始日時1・公開開始日時2の比較
        if (startTime1 != null && startTime2 != null) {
            if (currentTime.after(startTime1) && startTime1.after(startTime2)) {
                disp = note1;
            }
            if (currentTime.after(startTime2) && startTime2.after(startTime1)) {
                disp = note2;
            }
        }

        return disp;
    }
    // 2023-renew No64 to here

    // 2023-renew No65 from here
    /**
     * お気に入り商品販売判定
     * ※現在日時
     *
     * @param favoriteSaleStatus    お気に入り商品販売状態
     * @param saleStartTime         販売開始日時
     * @param saleEndTime           販売終了日時
     * @return true=販売中、false=販売中でない
     */
    public boolean isFavoriteGoodsSales(HTypeFavoriteSaleStatus favoriteSaleStatus, Timestamp saleStartTime, Timestamp saleEndTime) {
        // 現在日時
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();
        // 販売
        if (HTypeFavoriteSaleStatus.SALE.equals(favoriteSaleStatus)) {
            // 日付関連Helper取得
            DateUtility dateHelper = ApplicationContextUtility.getBean(DateUtility.class);
            return dateHelper.isOpen(saleStartTime, saleEndTime, currentTime);
        }
        return false;
    }
    // 2023-renew No65 to here
}
