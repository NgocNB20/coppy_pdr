/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.salesadvisability.SalesAdvisabilityDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.BeanUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 商品系ヘルパークラス<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsUtility {

    /**
     * 商品表示価格最高値<br/>
     * 商品最安値算出時に使用(DBの桁数に合わせて8ケタ)<br/>
     */
    public static final BigDecimal GOODS_DISPLAY_MAX_PRICE = new BigDecimal("99999999");

    /**
     * 規格の最大値<br/>
     */
    protected static final Integer UNIT_LENGTH = 2;

    /**
     * 画像なし画像名<br/>
     */
    public static final String NO_IMAGE_FILENAME = "noimg";

    /**
     * 区切り文字：スラッシュ<br/>
     */
    public static final String SEPARATOR_SLASH = "/";

    /**
     * 画像なし画像拡張子
     */
    protected static final String EXTENSION = ".jpg";

    /**
     * フロントの商品画像パス
     */
    protected static final String FRONT_GOODS_IMAGE_PATH = "/g_images";

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    // PDR Migrate Customization from here
    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

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
     * 販売可否判定
     */
    private final SalesAdvisabilityDao salesAdvisabilityDao;

    /**
     * エラーメッセージ：販売可否判定が取得できなかった場合エラー
     */
    protected static final String MSGCD_NOT_GET_SALESADVISABILITY = "PDR-0437-021-L-E";

    /**
     * Beanユーティリティ
     */
    private final BeanUtility beanUtility;

    /**
     * 心意気価格_商品名接頭辞
     */
    protected static final String EMOTION_PRICE_PREFIX_NAME = "【心意気価格】";

    /**
     * 心意気価格_商品コード接頭辞
     */
    protected static final String EMOTION_PRICE_PREFIX_GOODS_CODE = "kp";

    @Autowired
    public GoodsUtility(ConversionUtility conversionUtility,
                        DateUtility dateUtility,
                        SalesAdvisabilityDao salesAdvisabilityDao,
                        BeanUtility beanUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.salesAdvisabilityDao = salesAdvisabilityDao;
        this.beanUtility = beanUtility;
    }
    // PDR Migrate Customization to here

    /**
     * 商品グループ画像ファイル名作成<br/>
     *
     * @param goodsGroupCode 商品管理番号
     * @param versionNo      画像連番
     * @return ファイル名
     */
    public String createImageFileName(String goodsGroupCode, Integer versionNo, String imageExt) {

        ImageUtility imageUtility = ApplicationContextUtility.getBean(ImageUtility.class);

        String separator = PropertiesUtil.getSystemPropertiesValue("goodsimage.name.separator");

        // プロパティに該当情報が存在しない場合
        if (separator == null) {
            return null;
        }

        // 画像ファイル名を作成
        // (商品管理番号_画像種別内連番_画像種別)
        StringBuilder sb = new StringBuilder();
        sb.append(goodsGroupCode);
        sb.append(separator);
        sb.append(String.format("%02d", versionNo));

        // 拡張子を付加
        String fileName = null;
        if (imageExt != null) {
            fileName = imageUtility.createImageFileNameExtension(imageExt, sb.toString());
        } else {
            fileName = sb.toString();
        }

        return fileName;
    }

    /**
     * 商品規格画像ファイル名作成<br/>
     *
     * @param goodsGroupCode 商品グループ番号
     * @param goodsCode      商品番号
     * @return ファイル名
     */
    public String createUnitImageFileName(String goodsGroupCode, String goodsCode, String imageExt) {

        ImageUtility imageUtility = ApplicationContextUtility.getBean(ImageUtility.class);

        String separator = PropertiesUtil.getSystemPropertiesValue("goodsimage.name.separator");

        // プロパティに該当情報が存在しない場合
        if (separator == null) {
            return null;
        }

        // 画像ファイル名を作成
        // (商品管理番号_画像種別内連番_画像種別)
        StringBuilder sb = new StringBuilder();
        sb.append(goodsGroupCode);
        sb.append(separator);
        sb.append(goodsCode);

        // 拡張子を付加
        String fileName = null;
        if (imageExt != null) {
            fileName = imageUtility.createImageFileNameExtension(imageExt, sb.toString());
        } else {
            fileName = sb.toString();
        }

        return fileName;
    }

    /**
     * 商品画像パスの取得<br/>
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
     * 商品画像パスの取得(フロント)<br/>
     * ※商品画像パスがない場合は、画像なし画像を返す
     *
     * @param goodsImagePath 商品画像パス(null許可)
     * @return 実際の商品画像パス
     */
    public String getFrontGoodsImagePath(String goodsImagePath) {

        // 画像がないまたは、非表示の場合は、画像なし画像
        String imagePath = null;
        if (StringUtil.isEmpty(goodsImagePath)) {
            imagePath = NO_IMAGE_FILENAME + EXTENSION;
        } else {
            imagePath = goodsImagePath;
        }

        // システム設定パス取得
        String path = FRONT_GOODS_IMAGE_PATH;
        if (path == null) {
            return imagePath;
        }
        return path + SEPARATOR_SLASH + imagePath;
    }

    /**
     * 画像名の取得<br/>
     * 画像が[存在しない or 非表示]の場合nullを返す
     *
     * @param goodsGroupDto 商品グループ
     * @return 商品画像名
     */
    public String getImageFileName(GoodsGroupDto goodsGroupDto) {
        return getImageFileName(goodsGroupDto.getGoodsGroupImageEntityList(), null);
    }

    /**
     * 画像名の取得<br/>
     * 画像が[存在しない or 非表示]の場合nullを返す
     *
     * @param goodsDetailsDto 商品詳細
     * @return 商品画像名
     */
    public String getImageFileName(GoodsDetailsDto goodsDetailsDto) {
        return getImageFileName(goodsDetailsDto.getGoodsGroupImageEntityList(), goodsDetailsDto.getUnitImage());
    }

    /**
     * 画像名の取得<br/>
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
     * 商品公開判定<br/>
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
     * 商品公開判定<br/>
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
     * PC用商品販売判定<br/>
     * ※現在日時
     *
     * @param entity 商品
     * @return true=販売中、false=販売中でない
     */
    public boolean isGoodsSalesPc(GoodsEntity entity) {
        return isGoodsSales(entity.getSaleStatusPC(), entity.getSaleStartTimePC(), entity.getSaleEndTimePC());
    }

    /**
     * 商品販売判定<br/>
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
     * 商品販売判定<br/>
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
     * 新着商品をいつまで表示するかの、「いつまで」の日付を取得<br/>
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
     * 商品グループ在庫の表示判定<br/>
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
     * 商品の規格１～２の値を連携する<br/>
     *
     * @param goodsEntity 商品情報
     * @return /で結合した規格値　※規格なしの場合は、空文字
     */
    public String createUnitValue(GoodsEntity goodsEntity) {

        // 規格なしの場合は、空文字
        String unitValue = "";
        if (HTypeUnitManagementFlag.OFF == goodsEntity.getUnitManagementFlag()) {
            return unitValue;
        }

        // 規格ありの場合は、/区切り
        for (int i = 1; i <= UNIT_LENGTH; i++) {

            // PDR Migrate Customization from here
            String value = (String) beanUtility.getFieldValue(goodsEntity, "unitValue" + i);
            value = value == null ? "" : value;
            // PDR Migrate Customization to here

            if (StringUtils.isEmpty(unitValue)) {
                unitValue = value;
            } else {
                unitValue = unitValue + "/" + value;
            }
        }
        return unitValue;
    }

    /**
     * 指定のサイトごとにインフォメーションアイコンSEQリストを作成します。<br/>
     *
     * @param siteType サイト種別
     * @param iconPc   インフォメーションアイコンPC(※複数の場合、スラッシュ区切りでないとダメ)
     * @return インフォメーションアイコンSEQリスト
     */
    public List<Integer> createIconSeqList(HTypeSiteType siteType, String iconPc) {

        // サイトごとにアイコンSEQリストを作成
        String[] iconSeqArray = null;
        if ((siteType.isFrontPC() || siteType.isFrontSP()) && StringUtil.isNotEmpty(iconPc)) {
            iconSeqArray = iconPc.split(SEPARATOR_SLASH);
        } else if (siteType.isBack()) {
            // TreeSet
            Set<String> treeSet = new TreeSet<>();
            if (StringUtil.isNotEmpty(iconPc)) {
                treeSet.addAll(Arrays.asList(iconPc.split(SEPARATOR_SLASH)));
            }
            iconSeqArray = treeSet.toArray(new String[] {});
        }

        // Integerに変換して詰め替え
        List<Integer> iconSeqList = new ArrayList<>();
        if (iconSeqArray == null) {
            return iconSeqList;
        }
        for (String iconSeq : iconSeqArray) {
            if (StringUtil.isNotEmpty(iconSeq)) {
                iconSeqList.add(conversionUtility.toInteger(iconSeq));
            }
        }
        return iconSeqList;
    }

    /**
     * 規格単位の在庫状況を商品グループ単位に変換<br/>
     *
     * @param goodsDtoList        商品DTOリスト
     * @param goodsStockStatusMap 規格単位の在庫ステータスMAP＜商品SEQ、在庫状況＞
     * @return 在庫状況
     */
    public HTypeStockStatusType convertGoodsGroupStockStatus(List<GoodsDto> goodsDtoList,
                                                             Map<Integer, HTypeStockStatusType> goodsStockStatusMap) {
        HTypeStockStatusType status = null;
        for (GoodsDto goodsDto : goodsDtoList) {
            HTypeStockStatusType currentStatus = goodsStockStatusMap.get(goodsDto.getGoodsEntity().getGoodsSeq());
            // より大きい優先度の在庫状態を採用する
            status = getPriorityStatus(currentStatus, status);
        }
        return status;
    }

    /**
     * 優先度の高い在庫状況を返却<br/>
     *
     * @param currentStatus 現在処理中の在庫状況
     * @param status        前回までの在庫状況
     * @return 優先度の高い在庫状況
     */
    public HTypeStockStatusType getPriorityStatus(HTypeStockStatusType currentStatus, HTypeStockStatusType status) {
        if (status == null) {
            return currentStatus;
        }
        return status;
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
    // 2023-renew No2 from here

    // 2023-renew No2 to here

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
     * @param strCoolSendTo   クール便適用期間TO
     * @return true...適用期間内/false...適用期間外
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
     * 商品が心意気商品かどうかを判定する。<br/>
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
     * 商品コードが心意気商品の場合、元商品の商品コードに変換する。<br/>
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
}
