/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseCustomerSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponsePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetQuantityDiscountResponseSalePriceDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.goods.goodsgroup.GoodsDisplayPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.CategoryItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsGroupItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.common.GoodsIconItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.SnsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.PageInfoHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 商品検索画面 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class GoodsIndexHelper {

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * カテゴリーヘルパークラス
     */
    private final CategoryUtility categoryUtility;

    /**
     * SNS連携のUtil
     */
    private final SnsUtility snsUtility;

    /**
     * 金額計算のUtilityクラス
     */
    private final CalculatePriceUtility calculatePriceUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品表示単価を生成するイ
     */
    private final GoodsDisplayPriceLogic goodsDisplayPriceLogic;

    /**
     * コンストラクタ
     *
     * @param goodsUtility           商品系ヘルパークラス
     * @param categoryUtility        カテゴリーヘルパークラス
     * @param snsUtility             SNS連携のUtil
     * @param calculatePriceUtility  金額計算のUtilityクラス
     * @param conversionUtility      変換ユーティリティクラス
     * @param goodsDisplayPriceLogic 商品表示単価を生成するイ
     */
    @Autowired
    public GoodsIndexHelper(GoodsUtility goodsUtility,
                            CategoryUtility categoryUtility,
                            SnsUtility snsUtility,
                            CalculatePriceUtility calculatePriceUtility,
                            ConversionUtility conversionUtility,
                            GoodsDisplayPriceLogic goodsDisplayPriceLogic) {
        this.goodsUtility = goodsUtility;
        this.categoryUtility = categoryUtility;
        this.snsUtility = snsUtility;
        this.calculatePriceUtility = calculatePriceUtility;
        this.conversionUtility = conversionUtility;
        this.goodsDisplayPriceLogic = goodsDisplayPriceLogic;
    }

    /**
     * カテゴリ情報をページクラスにセット
     *
     * @param categoryDetailsDto カテゴリ詳細DTO
     * @param goodsIndexModel    商品詳細画面 Model
     */
    public void toPageForLoadCategoryDto(CategoryDetailsDto categoryDetailsDto, GoodsIndexModel goodsIndexModel) {
        if (categoryDetailsDto == null) {
            goodsIndexModel.setCategoryName(null);
            goodsIndexModel.setCategoryImagePC(null);
        } else {
            goodsIndexModel.setCategoryName(categoryDetailsDto.getCategoryNamePC());
            if (categoryDetailsDto.getCategoryImagePC() != null) {
                goodsIndexModel.setCategoryImagePC(
                                categoryUtility.getCategoryImagePath(categoryDetailsDto.getCategoryImagePC()));
            }
        }
    }

    /**
     * パンくず情報をページクラスにセット
     *
     * @param categoryDetailsDtoList 公開カテゴリパスリスト
     * @param goodsIndexModel        商品詳細画面 Model
     */
    public void toPageForLoadForTopicPath(List<CategoryDetailsDto> categoryDetailsDtoList,
                                          GoodsIndexModel goodsIndexModel) {
        List<CategoryItem> categoryItemList = new ArrayList<>();

        if (categoryDetailsDtoList != null) {
            for (CategoryDetailsDto categoryDetailsDto : categoryDetailsDtoList) {
                if (categoryDetailsDto.getCategoryLevel() > 0) {// トップは省く
                    CategoryItem categoryItem = ApplicationContextUtility.getBean(CategoryItem.class);
                    categoryItem.setCid(categoryDetailsDto.getCategoryId());
                    categoryItem.setCategoryName(categoryDetailsDto.getCategoryNamePC());

                    categoryItemList.add(categoryItem);
                }
            }
        }
        goodsIndexModel.setCategoryPassItems(categoryItemList);
    }

    /**
     * 画面表示・再表示
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    public void toPageForLoad(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {
        // 初期値の設定
        goodsIndexModel.setGoodsGroupSeq(null);
        // 存在チェック
        // 条件を満たすことはないのでデッドコードになっている
        if (goodsGroupDto == null) {
            return;
        }

        // 商品グループ情報の設定
        setGoodsGroup(goodsGroupDto, goodsIndexModel);

        // 商品グループ表示情報の設定
        setGoodsGroupDisplay(goodsGroupDto, goodsIndexModel);

        // 商品在庫情報の設定
        setGoodsStock(goodsGroupDto, goodsIndexModel);

        // 商品画像の振り分け
        // 2023-renew No11 from here
        setGoodsImage(goodsGroupDto, goodsIndexModel, customParams);
        // 2023-renew No11 to here

        // 規格選択プルダウンの作成
        setGoodsUnit(goodsGroupDto, goodsIndexModel);

        // 在庫状況表示の設定
        setGoodsGroupStock(goodsGroupDto, goodsIndexModel);

        // SNS連携情報の設定
        setSnsInfo(goodsGroupDto, goodsIndexModel);

    }

    /**
     * 商品グループ情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setGoodsGroup(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {
        // 商品グループエンティティ
        GoodsGroupEntity entity = goodsGroupDto.getGoodsGroupEntity();

        // 商品グループ情報の設定
        // 条件を満たすことはないのでデッドコードになっている
        if (entity == null) {
            return;
        }
        goodsIndexModel.setGoodsGroupSeq(entity.getGoodsGroupSeq());
        goodsIndexModel.setGgcd(entity.getGoodsGroupCode());

        // 新着画像の表示期間を取得
        Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(entity.getWhatsnewDate());
        goodsIndexModel.setWhatsnewDate(whatsnewDate);

        goodsIndexModel.setGoodsGroupName(entity.getGoodsGroupName());

        if (goodsIndexModel.getUnitSelect1() == null && goodsIndexModel.getGcd() != null) {
            // 通常商品
            goodsIndexModel.setUnitSelect1(goodsIndexModel.getGcd());
        }
        // 商品表示単価、商品表示値引き前単価を設定
        Map<GoodsDisplayPriceLogic.Key, Object> prices = goodsDisplayPriceLogic.create(goodsGroupDto, true);

        goodsIndexModel.setGoodsPreDiscountPrice(entity.getGoodsPreDiscountPrice());

        //商品表示単価 設定
        goodsIndexModel.setGoodsPrice((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PRICE_PC));

        // 商品表示価格価格帯をつけるかチェック
        goodsIndexModel.setGoodsDisplayPriceRange(
                        (boolean) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PRICE_PC_RANGE));

        // 商品表示値引き前単価 設定
        goodsIndexModel.setPreDiscountPrice(
                        (BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PREDISCOUNT_PRICE_PC));

        //値下げ前価格価格帯をつけるかチェック
        goodsIndexModel.setGoodsDisplayPreDiscountPriceRange(
                        (boolean) prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_PREDISCOUNT_PRICE_PC_RANGE));
        // PDR Migrate Customization from here
        if (prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_DISPPRICE_PRICE) != null && (boolean) prices.get(
                        GoodsDisplayPriceLogic.Key.DISPLAY_DISPPRICE_PRICE)) {
            goodsIndexModel.setPriceMarkDispFlag(true);
        }
        if (prices.get(GoodsDisplayPriceLogic.Key.DISPLAY_DISPPRICE_PREDISCOUNT_PRICE) != null && (boolean) prices.get(
                        GoodsDisplayPriceLogic.Key.DISPLAY_DISPPRICE_PREDISCOUNT_PRICE)) {
            goodsIndexModel.setSalePriceMarkDispFlag(true);
        }

        // 販売可能商品区分
        // 2023-renew No2 from here

        // 2023-renew No2 to here
        // PDR Migrate Customization to here

        // PDR Migrate Customization from here
        // シリーズセール価格整合性フラグ
        goodsIndexModel.setGroupSalePriceIntegrityFlag(
                        HTypeSalePriceIntegrityFlag.MATCH.equals(entity.getGroupSalePriceIntegrityFlag()) ?
                                        true :
                                        false);
        // SALEアイコンフラグ
        goodsIndexModel.setSaleIconFlag(
                        HTypeSaleIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getSaleIconFlag()) ?
                                        true :
                                        false);
        // お取りおきアイコンフラグ
        goodsIndexModel.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(
                        goodsGroupDto.getGoodsGroupDisplayEntity().getReserveIconFlag()) ? true : false);
        // NEWアイコンフラグ
        goodsIndexModel.setNewIconFlag(
                        HTypeNewIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getNewIconFlag()) ?
                                        true :
                                        false);
        // 2023-renew No92 from here
        // アウトレットアイコンフラグ
        goodsIndexModel.setOutletIconFlag(
                        HTypeOutletIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getOutletIconFlag()));
        // 2023-renew No92 to here
        // PDR Migrate Customization to here
    }

    /**
     * 商品グループ表示情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setGoodsGroupDisplay(GoodsGroupDto goodsGroupDto,
                                        GoodsIndexModel goodsIndexModel,
                                        Object... customParams) {
        // 商品グループエンティティ
        GoodsGroupDisplayEntity entity = goodsGroupDto.getGoodsGroupDisplayEntity();

        // 商品グループ表示情報の設定
        // 条件を満たすことはないのでデッドコードになっている
        if (entity == null) {
            return;
        }
        // 商品説明１～１０
        //2023-renew No64 from here
        goodsIndexModel.setGoodsNote1(GoodsUtility.compareGoodsNoteTimestamps(entity.getGoodsNote1OpenStartTime(),
                                                                              entity.getGoodsNote1SubOpenStartTime(),
                                                                              entity.getGoodsNote1(),
                                                                              entity.getGoodsNote1Sub()
                                                                             ));
        goodsIndexModel.setGoodsNote2(GoodsUtility.compareGoodsNoteTimestamps(entity.getGoodsNote2OpenStartTime(),
                                                                              entity.getGoodsNote2SubOpenStartTime(),
                                                                              entity.getGoodsNote2(),
                                                                              entity.getGoodsNote2Sub()
                                                                             ));
        goodsIndexModel.setGoodsNote3(entity.getGoodsNote3());
        goodsIndexModel.setGoodsNote4(GoodsUtility.compareGoodsNoteTimestamps(entity.getGoodsNote4OpenStartTime(),
                                                                              entity.getGoodsNote4SubOpenStartTime(),
                                                                              entity.getGoodsNote4(),
                                                                              entity.getGoodsNote4Sub()
                                                                             ));
        goodsIndexModel.setGoodsNote5(entity.getGoodsNote5());
        goodsIndexModel.setGoodsNote6(entity.getGoodsNote6());
        goodsIndexModel.setGoodsNote7(entity.getGoodsNote7());
        goodsIndexModel.setGoodsNote8(entity.getGoodsNote8());
        goodsIndexModel.setGoodsNote9(entity.getGoodsNote9());
        goodsIndexModel.setGoodsNote10(GoodsUtility.compareGoodsNoteTimestamps(entity.getGoodsNote10OpenStartTime(),
                                                                               entity.getGoodsNote10SubOpenStartTime(),
                                                                               entity.getGoodsNote10(),
                                                                               entity.getGoodsNote10Sub()
                                                                              ));
        //2023-renew No64 to here
        goodsIndexModel.setGoodsNote11(entity.getGoodsNote11());
        goodsIndexModel.setGoodsNote12(entity.getGoodsNote12());
        goodsIndexModel.setGoodsNote13(entity.getGoodsNote13());
        //2023-renew No0 from here
        goodsIndexModel.setGoodsNote14(entity.getGoodsNote14());
        goodsIndexModel.setGoodsNote15(entity.getGoodsNote15());
        goodsIndexModel.setGoodsNote16(entity.getGoodsNote16());
        goodsIndexModel.setGoodsNote17(entity.getGoodsNote17());
        goodsIndexModel.setGoodsNote18(entity.getGoodsNote18());
        goodsIndexModel.setGoodsNote19(entity.getGoodsNote19());
        goodsIndexModel.setGoodsNote20(entity.getGoodsNote20());
        //2023-renew No11 from here
        goodsIndexModel.setGoodsNote21(entity.getGoodsNote21());
        //2023-renew No11 to here
        goodsIndexModel.setGoodsNote22(entity.getGoodsNote22());
        //2023-renew No0 to here

        // 受注連携設定１～１０を一応セットしておく（使うかどうかは案件判断）
        goodsIndexModel.setOrderSetting1(entity.getOrderSetting1());
        goodsIndexModel.setOrderSetting2(entity.getOrderSetting2());
        goodsIndexModel.setOrderSetting3(entity.getOrderSetting3());
        goodsIndexModel.setOrderSetting4(entity.getOrderSetting4());
        goodsIndexModel.setOrderSetting5(entity.getOrderSetting5());
        goodsIndexModel.setOrderSetting6(entity.getOrderSetting6());
        goodsIndexModel.setOrderSetting7(entity.getOrderSetting7());
        goodsIndexModel.setOrderSetting8(entity.getOrderSetting8());
        goodsIndexModel.setOrderSetting9(entity.getOrderSetting9());
        goodsIndexModel.setOrderSetting10(entity.getOrderSetting10());

        if (entity.getUnitManagementFlag() != null) {
            goodsIndexModel.setUnitManagementFlag(entity.getUnitManagementFlag().getValue());
        }
        goodsIndexModel.setUnitTitle1(entity.getUnitTitle1());
        goodsIndexModel.setUnitTitle2(entity.getUnitTitle2());
        goodsIndexModel.setErrorUnitTitle1(entity.getUnitTitle1());
        goodsIndexModel.setErrorUnitTitle2(entity.getUnitTitle2());
        goodsIndexModel.setMetaDescription(entity.getMetaDescription());
        goodsIndexModel.setMetaKeyword(entity.getMetaKeyword());
        // 商品納期
        // 商品DTOリストより取得していたが、商品グループ表示エンティティに同値を保持しているため、
        // 商品グループ表示エンティティより取得する。（フロントMBと同じ挙動とする）
        goodsIndexModel.setDeliveryType(entity.getDeliveryType());

        // インフォメーションアイコン情報の設定
        List<GoodsIconItem> informationIconList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(goodsGroupDto.getGoodsInformationIconDetailsDtoList())) {
            for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsGroupDto.getGoodsInformationIconDetailsDtoList()) {
                GoodsIconItem goodsIconItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                goodsIconItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                goodsIconItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                informationIconList.add(goodsIconItem);
            }
        }
        goodsIndexModel.setInformationIconItems(informationIconList);
    }

    /**
     * 商品在庫情報の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setGoodsStock(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();
        // 在庫表示用リスト
        List<GoodsStockItem> goodsStockItems = new ArrayList<>();

        goodsIndexModel.setExistsSaleStatusGoods(false);
        for (GoodsDto goodsDto : goodsDtoList) {
            // 商品エンティティ
            GoodsEntity goodsEntity = goodsDto.getGoodsEntity();
            // 在庫DTO
            StockDto stockDto = goodsDto.getStockDto();
            // 販売状態チェック
            if (goodsUtility.isGoodsSales(goodsEntity.getSaleStatusPC(), goodsEntity.getSaleStartTimePC(),
                                          goodsDto.getGoodsEntity().getSaleEndTimePC()
                                         )) {
                goodsIndexModel.setExistsSaleStatusGoods(true);
            }

            // ページアイテムクラスに、商品情報をセット
            GoodsStockItem goodsStockItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            goodsStockItem.setGoodsSeq(goodsEntity.getGoodsSeq());
            goodsStockItem.setGcd(goodsEntity.getGoodsCode());
            goodsStockItem.setGoodsPrice(goodsEntity.getGoodsPrice());
            goodsStockItem.setPreDiscountPrice(goodsEntity.getPreDiscountPrice());
            goodsStockItem.setPreDiscountPriceInTax(
                            calculatePriceUtility.getTaxIncludedPrice(goodsStockItem.getPreDiscountPrice(),
                                                                      goodsGroupDto.getGoodsGroupEntity().getTaxRate()
                                                                     ));
            goodsStockItem.setTaxRate(goodsGroupDto.getGoodsGroupEntity().getTaxRate());
            goodsStockItem.setStockManagementFlag(goodsEntity.getStockManagementFlag());
            goodsStockItem.setIndividualDeliveryType(goodsEntity.getIndividualDeliveryType());
            goodsStockItem.setUnitTitle1(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle1());
            goodsStockItem.setUnitValue1(goodsEntity.getUnitValue1());
            goodsStockItem.setUnitTitle2(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle2());
            goodsStockItem.setUnitValue2(goodsEntity.getUnitValue2());
            goodsStockItem.setGoodsPreDiscountPrice(goodsGroupDto.getGoodsGroupEntity().getGoodsPreDiscountPrice());
            if (goodsEntity.getStockManagementFlag() == HTypeStockManagementFlag.OFF) {
                goodsStockItem.setSalesPossibleStock(null);
            } else {
                goodsStockItem.setSalesPossibleStock(stockDto.getSalesPossibleStock());
            }
            if (goodsDto.getStockStatusPc() != null) {
                goodsStockItem.setStockTextType(goodsDto.getStockStatusPc().getValue());
            }

            goodsStockItems.add(goodsStockItem);

        }
        goodsIndexModel.setGoodsStockItems(goodsStockItems);
    }

    /**
     * 商品画像の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setGoodsImage(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {

        // 商品グループ画像の取得
        List<String> groupImageItems = createGroupImageItems(goodsGroupDto);

        // 2023-renew No11 from here
        List<GoodsUnitImageItem> goodsUnitImageItems = this.createUnitImageItems(goodsGroupDto, customParams);
        // 2023-renew No11 to here

        goodsIndexModel.setGoodsUnitImageItems(goodsUnitImageItems);

        // 取得した画像をページにセット
        goodsIndexModel.setGoodsImageItems(groupImageItems);

        List<String> unitImagePathList =
                        goodsUnitImageItems.stream().map(image -> image.getImagePath()).collect(Collectors.toList());

        // PDR Migrate Customization from here
        //        List<String> unitCodeImagePathList = goodsUnitImageItems.stream()
        //                                                                .map(image -> (image.getImagePath() + ','
        //                                                                               + image.getGoodsCode() + ','
        //                                                                               + image.getGoodsUnitImageName()))
        //                                                                .collect(Collectors.toList());
        //
        //        goodsIndexModel.setGoodsGroupImageForJs(groupImageItems);
        //        goodsIndexModel.setGoodsUnitImageForJs(unitCodeImagePathList);
        // PDR Migrate Customization to here
        List<String> mergedList = new ArrayList<>();
        mergedList.addAll(groupImageItems);
        mergedList.addAll(unitImagePathList);

        goodsIndexModel.setGoodsImageAllItems(mergedList);

    }

    /**
     * 商品グループ画像の取得
     *
     * @param goodsGroupDto 商品グループDTO
     * @return 商品グループ画像リスト
     */
    protected List<String> createGroupImageItems(GoodsGroupDto goodsGroupDto) {

        List<String> groupImageList = new ArrayList<>();

        if (goodsGroupDto.getGoodsGroupImageEntityList() == null) {
            return groupImageList;
        }

        for (GoodsGroupImageEntity entity : goodsGroupDto.getGoodsGroupImageEntityList()) {
            // 商品画像パスを取得
            String imagePath = goodsUtility.getGoodsImagePath(entity.getImageFileName());
            groupImageList.add(imagePath);
        }

        return groupImageList;
    }

    /**
     * 商品規格画像の取得
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     * @return 商品規格画像アイテム
     */
    protected List<GoodsUnitImageItem> createUnitImageItems(GoodsGroupDto goodsGroupDto, Object... customParams) {
        List<GoodsUnitImageItem> unitImageList = new ArrayList<>();

        // 商品規格画像DTOが存在する場合
        if (CollectionUtils.isNotEmpty(goodsGroupDto.getGoodsDtoList())) {
            // 商品規格画像情報を設定する
            // 2023-renew No11 from here
            this.setUnitImage(unitImageList, goodsGroupDto.getGoodsDtoList(), customParams);
            // 2023-renew No11 to here
        }
        return unitImageList;
    }

    /**
     * 商品規格画像情報を設定する
     *
     * @param unitImageList 規格画像アイテムリスト
     * @param goodsDtoList  商品グループDTO
     * @param customParams  案件用引数
     */
    protected void setUnitImage(List<GoodsUnitImageItem> unitImageList,
                                List<GoodsDto> goodsDtoList,
                                Object... customParams) {

        for (GoodsDto goodsDto : goodsDtoList) {

            GoodsImageEntity entity = goodsDto.getUnitImage();

            if (ObjectUtils.isEmpty(entity) || entity.getGoodsSeq() == null) {
                continue;
            }
            GoodsUnitImageItem goodsUnitImageItem = new GoodsUnitImageItem();

            // 商品画像パスを取得
            // 2023-renew No11 from here
            String imagePath = goodsUtility.getGoodsImagePath(entity.getImageFileName());
            // 規格画像リストが空または、一致する「規格画像コード/規格画像グループNO」がない場合、アイテムを作成する
            String tmpUnitName = "";
            if (ObjectUtils.isNotEmpty(goodsDto.getGoodsEntity())
                && goodsDto.getGoodsEntity().getUnitValue1() != null) {
                tmpUnitName = goodsDto.getGoodsEntity().getUnitValue1().replace(",", "、");
            }
            goodsUnitImageItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            goodsUnitImageItem.setImagePath(imagePath);
            goodsUnitImageItem.setGoodsUnitImageName(tmpUnitName);
            if (entity.getDisplayFlag() == HTypeDisplayStatus.DISPLAY) {
                unitImageList.add(goodsUnitImageItem);
            }

            if (customParams.length > 0) {
                ((List<GoodsUnitImageItem>) customParams[0]).add(goodsUnitImageItem);
            }
            // 2023-renew No11 to here
        }
    }

    /**
     * 商品規格の設定
     * <pre>
     * 商品グループDTOから必要な値を取出し、
     * 商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setGoodsUnit(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();

        // プルダウンを初期化
        goodsIndexModel.setUnitSelect1Items(null);
        goodsIndexModel.setUnitSelect2Items(null);

        // 規格管理なしの場合、商品コードをセットして終了
        if (!goodsIndexModel.getUnitManagementFlag().equals(HTypeUnitManagementFlag.ON.getValue())) {
            if (goodsDtoList.size() > 0) {
                goodsIndexModel.setGcd(((goodsDtoList.get(0)).getGoodsEntity().getGoodsCode()));
            }
            return;
        }

        // 商品コードMAP(key=商品コード、value=規格配列[規格１、規格２])
        Map<String, String[]> gcdMap = new HashMap<>();
        // 規格１MAP(key=規格１、value=規格２配列[商品コード、規格２])
        Map<String, List<String[]>> unit1Map = new LinkedHashMap<>();

        goodsUtility.createAllUnitMap(goodsGroupDto.getGoodsDtoList(), gcdMap, unit1Map);

        // PDR Migrate Customization from here
        // プルダウン作成のために各MAP(全規格の組み合わせ)を作成
        // 全規格画像コード(商品コード、規格画像コード)JS用
        String unitImageCodeForJs = "";
        for (GoodsDto goodsDto : goodsDtoList) {

            // 規格画像コードの設定
            // 商品コード毎に規格画像コードを連結する
            if (goodsDto.getUnitImage() != null && !goodsDto.getUnitImage().getImageFileName().isEmpty()) {
                unitImageCodeForJs = unitImageCodeForJs + "," + goodsDto.getGoodsEntity().getGoodsCode();
            }
        }
        goodsIndexModel.setUnitImageCodeForJs(unitImageCodeForJs);
        // PDR Migrate Customization to here
        // プルダウン作成
        this.createUnitList(goodsIndexModel, gcdMap, unit1Map);
    }

    /**
     * 規格プルダウン作成
     *
     * @param goodsIndexModel 商品詳細画面 Model
     * @param gcdMap          商品コードMAP(key=商品コード、value=規格配列[規格１、規格２])
     * @param unit1Map        規格１MAP(key=規格１、value=規格２配列[商品コード、規格２])
     */
    protected void createUnitList(GoodsIndexModel goodsIndexModel,
                                  Map<String, String[]> gcdMap,
                                  Map<String, List<String[]>> unit1Map) {
        Map<String, String> unitValue1Map = new LinkedHashMap<>();
        Map<String, String> unitValue2Map = new LinkedHashMap<>();

        // パラメータの商品コード有無により処理を分岐
        if (StringUtils.isEmpty(goodsIndexModel.getGcd())) {
            // 商品コードなし(商品グループコードあり)の場合
            for (Map.Entry<String, List<String[]>> unit1MapEntry : unit1Map.entrySet()) {
                // 規格１表示用のリストを作成
                String unit1Label;
                if (goodsIndexModel.isUseUnit2()) {
                    unit1Label = unit1MapEntry.getKey();
                } else {
                    // 規格１のみの場合、在庫なしメッセージを付加する
                    if (unit1MapEntry.getValue().get(0)[2] == "true") {
                        unit1Label = unit1MapEntry.getKey();
                    } else {
                        unit1Label = goodsUtility.addNoStockMessage(unit1MapEntry.getKey());
                    }
                }
                unitValue1Map.put(unit1MapEntry.getValue().get(0)[0], unit1Label);
            }
        } else {
            // 商品コードありの場合
            String[] tmpUnit1Array = gcdMap.get(goodsIndexModel.getGcd());
            String tmpUnit1 = "";
            if (tmpUnit1Array != null) {
                // 該当の規格１を商品コードMAPから抽出
                tmpUnit1 = tmpUnit1Array[0];
            }
            // 規格１と該当商品に存在する規格２を出力する
            for (Map.Entry<String, List<String[]>> unit1MapEntry : unit1Map.entrySet()) {
                // 規格１表示用のリストを作成
                String unit1Label;
                if (goodsIndexModel.isUseUnit2()) {
                    unit1Label = unit1MapEntry.getKey();
                } else {
                    // 規格１のみの場合、在庫なしメッセージを付加する
                    if (unit1MapEntry.getValue().get(0)[2] == "true") {
                        unit1Label = unit1MapEntry.getKey();
                    } else {
                        unit1Label = goodsUtility.addNoStockMessage(unit1MapEntry.getKey());
                    }
                }
                unitValue1Map.put(unit1MapEntry.getValue().get(0)[0], unit1Label);
                // 該当商品の規格１かどうか確認
                if (unit1MapEntry.getKey().equals(tmpUnit1)) {
                    // 規格１選択値を規格１MAPから選択しなおす
                    // ※正しいvalue値は該当する規格１MAP規格２配列の一番上の商品コードとなるため
                    goodsIndexModel.setUnitSelect1(unit1MapEntry.getValue().get(0)[0]);
                    // 規格２管理を行う商品かどうか確認
                    if (goodsIndexModel.isUseUnit2()) {
                        for (String[] tmpArray : unit1MapEntry.getValue()) {
                            // 規格２表示用のリストを作成
                            String unit2Label;
                            if (tmpArray[2] == "true") {
                                unit2Label = tmpArray[1];
                            } else {
                                unit2Label = goodsUtility.addNoStockMessage(tmpArray[1]);
                            }
                            unitValue2Map.put(tmpArray[0], unit2Label);
                        }
                        // 規格２をセット
                        goodsIndexModel.setUnitSelect2(goodsIndexModel.getGcd());
                    }
                }
            }
        }
        // ページの規格１プルダウンをセット
        goodsIndexModel.setUnitSelect1Items(unitValue1Map);
        // ページの規格２プルダウンをセット
        goodsIndexModel.setUnitSelect2Items(unitValue2Map);
    }

    /**
     * 在庫状況を設定
     *
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     */
    protected void setGoodsGroupStock(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel) {
        // 商品詳細画面には現時点での在庫状況を表示する
        HTypeStockStatusType status = goodsGroupDto.getRealTimeStockStatus().getStockStatusPc();
        goodsIndexModel.setStockStatusDisplay(false);
        if (status != null) {
            goodsIndexModel.setStockStatusPc(status.getValue());
        }

        // 商品グループ在庫の表示判定
        if (goodsUtility.isGoodsGroupStock(status)) {
            goodsIndexModel.setStockStatusDisplay(true);
        }
    }

    /**
     * SNS連携用の各種設定を行う
     *
     * @param goodsGroupDto   商品グループDto
     * @param goodsIndexModel 商品詳細画面 Model
     * @param customParams    案件用引数
     */
    protected void setSnsInfo(GoodsGroupDto goodsGroupDto, GoodsIndexModel goodsIndexModel, Object... customParams) {
        GoodsGroupEntity goodsGroup = goodsGroupDto.getGoodsGroupEntity();
        // 商品マスタのSNS連携フラグを設定
        goodsIndexModel.setSnsLinkDisplay((HTypeSnsLinkFlag.ON == goodsGroup.getSnsLinkFlag()));

        // SNSごとの利用フラグを設定
        goodsIndexModel.setUseFacebook(snsUtility.isUseFacebook());
        goodsIndexModel.setUseTwitter(snsUtility.isUseTwitter());
        if (goodsIndexModel.isUseTwitter()) {
            // Twitter用メンションを取得
            goodsIndexModel.setTwitterVia(snsUtility.getTwitterVia());
        }
        goodsIndexModel.setUseLine(snsUtility.isUseLine());

        // SNSが全て利用しない場合、SNS連携ボタンの表示フィールドごと非表示
        if (!goodsIndexModel.isUseFacebook() && !goodsIndexModel.isUseTwitter() && !goodsIndexModel.isUseLine()) {
            goodsIndexModel.setSnsLinkDisplay(false);
        }
    }

    /**
     * 画面表示・再表示(あしあと情報)
     *
     * @param goodsGroupDtoList 商品グループ一覧情報DTO
     * @param goodsIndexModel   商品詳細画面 Model
     */
    public void toPageForLoadFootprint(List<GoodsGroupDto> goodsGroupDtoList, GoodsIndexModel goodsIndexModel) {
        goodsIndexModel.setFootPrintGoodsItems(getGoodsItemsForGoodsListDto(goodsGroupDtoList));
    }

    /**
     * 商品一覧DTOより、ページアイテムリストを作成する。
     *
     * @param goodsGroupDtoList 商品グループ一覧情報DTO
     * @return ページアイテムリスト
     */
    protected List<GoodsGroupItem> getGoodsItemsForGoodsListDto(List<GoodsGroupDto> goodsGroupDtoList) {
        List<GoodsGroupItem> goodsItems = new ArrayList<>();
        for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {
            GoodsGroupItem goodsGroupItem = ApplicationContextUtility.getBean(GoodsGroupItem.class);

            GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
            List<GoodsGroupImageEntity> goodsGroupImageEntityList = goodsGroupDto.getGoodsGroupImageEntityList();

            if (goodsGroupEntity != null) {
                // 2023-renew AddNo5 from here
                goodsGroupItem.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
                goodsGroupItem.setGgcd(goodsGroupEntity.getGoodsGroupCode());
                goodsGroupItem.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
                goodsGroupItem.setGoodsOpenStatus(goodsGroupEntity.getGoodsOpenStatusPC());
                goodsGroupItem.setOpenStartTime(goodsGroupEntity.getOpenStartTimePC());
                goodsGroupItem.setOpenEndTime(goodsGroupEntity.getOpenEndTimePC());
                goodsGroupItem.setGoodsTaxType(goodsGroupEntity.getGoodsTaxType());

                // 税率
                BigDecimal taxRate = goodsGroupEntity.getTaxRate();
                goodsGroupItem.setTaxRate(taxRate);
                goodsGroupItem.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

                // 通常価格 - 税込計算
                BigDecimal goodsPriceLow = goodsGroupEntity.getGoodsGroupMinPricePc();
                goodsGroupItem.setGoodsPriceLow(goodsPriceLow);
                BigDecimal goodsPriceHight = goodsGroupEntity.getGoodsGroupMaxPricePc();
                goodsGroupItem.setGoodsPriceHigh(goodsPriceHight);

                // 通常価格 - 価格帯チェック
                goodsGroupItem.setGoodsDisplayPriceRange(
                                goodsUtility.isGoodsDisplayPriceRange(goodsPriceLow, goodsPriceHight));

                // セール価格
                BigDecimal goodsSalePriceLow = goodsGroupEntity.getGoodsGroupMinPriceMb();
                if (goodsSalePriceLow != null) {
                    goodsGroupItem.setGoodsSalePriceLow(goodsSalePriceLow);
                }
                BigDecimal goodsSalePriceHight = goodsGroupEntity.getGoodsGroupMaxPriceMb();
                if (goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsSalePriceHigh(goodsSalePriceHight);
                }

                // セール価格 - 価格帯チェック
                if (goodsSalePriceLow != null && goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsDisplayPreDiscountPriceRange(
                                    goodsUtility.isGoodsDisplayPriceRange(goodsSalePriceLow, goodsSalePriceHight));
                }
                // 2023-renew AddNo5 to here

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsGroupEntity.getWhatsnewDate());
                goodsGroupItem.setWhatsnewDate(whatsnewDate);

            }

            List<String> goodsImageList = new ArrayList<>();
            if (goodsGroupImageEntityList != null) {
                // 商品画像リストを取り出す。
                for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                }
            }
            goodsGroupItem.setGoodsImageItems(goodsImageList);

            // 在庫状況表示
            StockStatusDisplayEntity stockStatusDisplayEntity = goodsGroupDto.getBatchUpdateStockStatus();
            if (stockStatusDisplayEntity != null) {
                if (stockStatusDisplayEntity.getStockStatusPc() != null)
                    goodsGroupItem.setStockStatusPc(stockStatusDisplayEntity.getStockStatusPc().getValue());
            }

            // アイコン情報の取得
            goodsGroupItem.setGoodsIconItems(
                            createGoodsIconList(goodsGroupDto.getGoodsInformationIconDetailsDtoList()));
            // PDR Migrate Customization from here

            // 販売可能商品区分
            // 2023-renew No2 from here

            // 2023-renew No2 to here
            // PDR Migrate Customization to here

            // PDR Migrate Customization from here
            // シリーズセール価格整合性フラグ
            goodsGroupItem.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupSalePriceIntegrityFlag()));
            // SALEアイコンフラグ
            goodsGroupItem.setSaleIconFlag(
                            HTypeSaleIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getSaleIconFlag()));
            // お取りおきアイコンフラグ
            goodsGroupItem.setReserveIconFlag(
                            HTypeReserveIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getReserveIconFlag()));
            // NEWアイコンフラグ
            goodsGroupItem.setNewIconFlag(
                            HTypeNewIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getNewIconFlag()));
            // 2023-renew No92 from here
            // アウトレットアイコンフラグ
            goodsGroupItem.setOutletIconFlag(
                            HTypeOutletIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getOutletIconFlag()));
            // 2023-renew No92 to here
            // PDR Migrate Customization to here
            // 商品表示単
            if (HTypeGroupPriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupPriceMarkDispFlag())) {
                goodsGroupItem.setPriceMarkDispFlag(true);
            }
            // 商品表示値引き後単価PC
            if (HTypeGroupSalePriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupSalePriceMarkDispFlag())) {
                goodsGroupItem.setSalePriceMarkDispFlag(true);
            }
            // PDR Migrate Customization to here
            goodsItems.add(goodsGroupItem);

        }

        return goodsItems;
    }

    /**
     * お気に入り商品検索条件Dtoの作成
     *
     * @param goodsIndexModel    商品詳細画面 Model
     * @param favoriteGoodsLimit お気に入り商品件数
     * @return お気に入り検索条件Dto
     */
    public FavoriteSearchForDaoConditionDto toFavoriteConditionDtoForSearchFavoriteList(int favoriteGoodsLimit,
                                                                                        GoodsIndexModel goodsIndexModel) {
        // お気に入り検索条件Dtoの作成 公開状態＝指定なし
        FavoriteSearchForDaoConditionDto favoriteConditionDto =
                        ApplicationContextUtility.getBean(FavoriteSearchForDaoConditionDto.class);
        favoriteConditionDto.setMemberInfoSeq(goodsIndexModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        // PageInfoHelper取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        // ページングセットアップ
        favoriteConditionDto =
                        pageInfoHelper.setupPageInfoForSkipCount(favoriteConditionDto, favoriteGoodsLimit, "updateTime",
                                                                 false
                                                                );
        return favoriteConditionDto;
    }

    /**
     * 画面表示・再表示(お気に入り情報)
     *
     * @param favoriteDtoList お気に入りDTOリスト
     * @param goodsIndexModel 商品詳細画面 Model
     */
    public void toPageForLoadFavorite(List<FavoriteDto> favoriteDtoList, GoodsIndexModel goodsIndexModel) {
        // 2023-renew AddNo5 from here
        List<GoodsStockItem> goodsItems = new ArrayList<>();
        // 2023-renew AddNo5 to here
        for (FavoriteDto favoriteDto : favoriteDtoList) {
            // お気に入り商品情報取得
            GoodsDetailsDto goodsDetailsDto = favoriteDto.getGoodsDetailsDto();

            // ページアイテムクラスにセット
            // 2023-renew AddNo5 from here
            GoodsStockItem goodsItem = ApplicationContextUtility.getBean(GoodsStockItem.class);
            // 2023-renew AddNo5 to here
            if (goodsDetailsDto != null) {
                goodsItem.setGoodsGroupSeq(goodsDetailsDto.getGoodsGroupSeq());
                goodsItem.setGcd(goodsDetailsDto.getGoodsCode());
                // 2023-renew No11 from here
                goodsItem.setGoodsCode(goodsDetailsDto.getGoodsCode());
                // 2023-renew No11 to here
                goodsItem.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
                goodsItem.setUnitTitle1(goodsDetailsDto.getUnitTitle1());
                goodsItem.setUnitValue1(goodsDetailsDto.getUnitValue1());
                goodsItem.setUnitTitle2(goodsDetailsDto.getUnitTitle2());
                goodsItem.setUnitValue2(goodsDetailsDto.getUnitValue2());
                goodsItem.setGoodsPreDiscountPrice(goodsDetailsDto.getGoodsPreDiscountPrice());
                goodsItem.setPreDiscountPrice(goodsDetailsDto.getPreDiscountPrice());
                goodsItem.setGoodsPrice(goodsDetailsDto.getGoodsPrice());

                // 税率、税種別の変換
                goodsItem.setTaxRate(goodsDetailsDto.getTaxRate());
                goodsItem.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());

                // 税込価格の計算
                goodsItem.setPreDiscountPriceInTax(
                                calculatePriceUtility.getTaxIncludedPrice(goodsItem.getPreDiscountPrice(),
                                                                          goodsItem.getTaxRate()
                                                                         ));

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsDetailsDto.getWhatsnewDate());
                goodsItem.setWhatsnewDate(whatsnewDate);

                goodsItem.setGoodsOpenStatus(goodsDetailsDto.getGoodsOpenStatusPC());
                goodsItem.setOpenStartTime(goodsDetailsDto.getOpenStartTimePC());
                goodsItem.setOpenEndTime(goodsDetailsDto.getOpenEndTimePC());

                goodsItem.setSaleStatus(goodsDetailsDto.getSaleStatusPC());
                goodsItem.setSaleStartTime(goodsDetailsDto.getSaleStartTimePC());
                goodsItem.setSaleEndTime(goodsDetailsDto.getSaleEndTimePC());

                goodsItem.setGoodsSeq(goodsDetailsDto.getGoodsSeq());

                // 商品画像リストを取り出す。
                List<String> goodsImageList = new ArrayList<>();
                String goodsItemImageFileName = null;
                if (goodsDetailsDto.getUnitImage() != null) {
                    goodsItemImageFileName = goodsDetailsDto.getUnitImage().getImageFileName();
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsItemImageFileName));
                } else {
                    if (goodsDetailsDto.getGoodsGroupImageEntityList() != null) {
                        for (GoodsGroupImageEntity goodsGroupImageEntity : goodsDetailsDto.getGoodsGroupImageEntityList()) {
                            goodsImageList.add(
                                            goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                        }
                    }
                }

                goodsItem.setGoodsImageItems(goodsImageList);

                // PDR Migrate Customization from here
                // SALEアイコンフラグ
                goodsItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsDetailsDto.getSaleIconFlag()));
                // 取りおきアイコンフラグ
                goodsItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(goodsDetailsDto.getReserveIconFlag()));
                // NEWアイコンフラグ
                goodsItem.setNewIconFlag(HTypeNewIconFlag.ON.equals(goodsDetailsDto.getNewIconFlag()));
                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                goodsItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(goodsDetailsDto.getOutletIconFlag()));
                // 2023-renew No92 to here
                // 商品表示単
                if (HTypePriceMarkDispFlag.ON.equals(goodsDetailsDto.getPriceMarkDispFlag())) {
                    goodsItem.setPriceMarkDispFlag(true);
                }
                // 商品表示値引き後単価PC
                if (HTypeSalePriceMarkDispFlag.ON.equals(goodsDetailsDto.getSalePriceMarkDispFlag())) {
                    goodsItem.setSalePriceMarkDispFlag(true);
                }

                // セール価格整合性フラグ
                goodsItem.setSalePriceIntegrityFlag(
                                HTypeSalePriceIntegrityFlag.MATCH.equals(goodsDetailsDto.getSalePriceIntegrityFlag()));

                // 2023-renew AddNo5 from here
                goodsItem.setGoodsPriceInTaxLow(goodsDetailsDto.getGoodsPriceInTaxLow());
                goodsItem.setGoodsPriceInTaxHight(goodsDetailsDto.getGoodsPriceInTaxHight());
                goodsItem.setPreDiscountPriceLow(goodsDetailsDto.getPreDiscountPriceLow());
                goodsItem.setPreDiscountPriceHight(goodsDetailsDto.getPreDiscountPriceHight());
                // 2023-renew AddNo5 to here
                // PDR Migrate Customization to here
            }

            // 在庫状況表示
            goodsItem.setStockStatusPc(favoriteDto.getStockStatus());
            // アイコン情報の取得
            goodsItem.setGoodsIconItems(createGoodsIconList(favoriteDto.getGoodsInformationIconDetailsDtoList()));

            goodsItems.add(goodsItem);

        }

        goodsIndexModel.setFavoriteGoodsItems(goodsItems);
    }

    /**
     * お気に入り表示設定
     *
     * @param favoriteList    お気に入りリスト
     * @param goodsGroupDto   商品グループDTO
     * @param goodsIndexModel 商品詳細画面 Model
     */
    public void setFavoriteView(List<FavoriteEntity> favoriteList,
                                GoodsGroupDto goodsGroupDto,
                                GoodsIndexModel goodsIndexModel) {
        if (goodsIndexModel.getFavoriteGoodsItems() != null) {
            String favoriteGoodsCodeList = "";
            for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
                for (FavoriteEntity favoriteEntity : favoriteList) {
                    if (goodsDto.getGoodsEntity().getGoodsSeq().equals(favoriteEntity.getGoodsSeq())) {
                        favoriteGoodsCodeList += goodsDto.getGoodsEntity().getGoodsCode() + ",";
                    }
                }
            }
            goodsIndexModel.setFavoriteGoodsCodeList(favoriteGoodsCodeList);
        }
    }

    /**
     * アイコン情報を設定
     *
     * @param goodsInformationIconDetailsDtoList 登録されているアイコン情報
     * @return 画面表示用に作成したアイコンリスト
     */
    protected List<GoodsIconItem> createGoodsIconList(List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList) {
        List<GoodsIconItem> goodsIconList = new ArrayList<>();
        if (goodsInformationIconDetailsDtoList != null) {
            for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsInformationIconDetailsDtoList) {
                GoodsIconItem goodsIconItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                goodsIconItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                goodsIconItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                goodsIconList.add(goodsIconItem);
            }
        }
        return goodsIconList;
    }

    /**
     * 画面表示・再表示(関連商品情報)
     *
     * @param goodsGroupDtoList 商品グループ一覧情報DTO
     * @param goodsIndexModel   商品詳細画面 Model
     */
    public void toPageForLoadRelated(List<GoodsGroupDto> goodsGroupDtoList, GoodsIndexModel goodsIndexModel) {

        List<GoodsGroupItem> goodsItems = new ArrayList<>();
        for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {

            GoodsGroupItem goodsGroupItem = ApplicationContextUtility.getBean(GoodsGroupItem.class);

            GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
            List<GoodsGroupImageEntity> goodsGroupImageEntityList = goodsGroupDto.getGoodsGroupImageEntityList();

            if (goodsGroupEntity != null) {
                goodsGroupItem.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
                goodsGroupItem.setGgcd(goodsGroupEntity.getGoodsGroupCode());
                goodsGroupItem.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
                // 税率
                BigDecimal taxRate = goodsGroupEntity.getTaxRate();
                goodsGroupItem.setTaxRate(taxRate);

                // 2023-renew AddNo5 from here
                // 通常価格 - 税込計算
                BigDecimal goodsPriceLow = goodsGroupEntity.getGoodsGroupMinPricePc();
                goodsGroupItem.setGoodsPriceLow(goodsPriceLow);
                BigDecimal goodsPriceHight = goodsGroupEntity.getGoodsGroupMaxPricePc();
                goodsGroupItem.setGoodsPriceHigh(goodsPriceHight);

                // 通常価格 - 価格帯チェック
                goodsGroupItem.setGoodsDisplayPriceRange(
                                goodsUtility.isGoodsDisplayPriceRange(goodsPriceLow, goodsPriceHight));
                goodsGroupItem.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

                // セール価格
                BigDecimal goodsSalePriceLow = goodsGroupEntity.getGoodsGroupMinPriceMb();
                if (goodsSalePriceLow != null) {
                    goodsGroupItem.setGoodsSalePriceLow(goodsSalePriceLow);
                }
                BigDecimal goodsSalePriceHight = goodsGroupEntity.getGoodsGroupMaxPriceMb();
                if (goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsSalePriceHigh(goodsSalePriceHight);
                }

                // セール価格 - 価格帯チェック
                if (goodsSalePriceLow != null && goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsDisplayPreDiscountPriceRange(
                                    goodsUtility.isGoodsDisplayPriceRange(goodsSalePriceLow, goodsSalePriceHight));
                }
                // 2023-renew AddNo5 to here

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsGroupEntity.getWhatsnewDate());
                goodsGroupItem.setWhatsnewDate(whatsnewDate);

            }

            List<String> goodsImageList = new ArrayList<>();
            if (goodsGroupImageEntityList != null) {
                // 商品画像リストを取り出す。
                for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                }
            }
            goodsGroupItem.setGoodsImageItems(goodsImageList);

            // アイコン情報の取得
            List<GoodsIconItem> goodsIconList = new ArrayList<>();
            if (goodsGroupDto.getGoodsInformationIconDetailsDtoList() != null) {
                for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsGroupDto.getGoodsInformationIconDetailsDtoList()) {
                    GoodsIconItem iconPageItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                    iconPageItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                    iconPageItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                    goodsIconList.add(iconPageItem);
                }
            }
            goodsGroupItem.setGoodsIconItems(goodsIconList);

            // 在庫状況表示
            StockStatusDisplayEntity stockStatusDisplayEntity = goodsGroupDto.getBatchUpdateStockStatus();
            if (stockStatusDisplayEntity != null) {
                if (stockStatusDisplayEntity.getStockStatusPc() != null) {
                    goodsGroupItem.setStockStatusPc(stockStatusDisplayEntity.getStockStatusPc().getValue());
                    // 2023-renew No10 from here
                    // 商品検索結果には在庫状況更新バッチ実行時点の在庫状況を表示する
                    // 商品グループ在庫の表示判定
                    if (goodsUtility.isGoodsGroupStock(stockStatusDisplayEntity.getStockStatusPc())) {
                        goodsGroupItem.setStockStatusDisplay(true);
                    }
                    // 2023-renew No10 to here
                }
            }

            // シリーズセール価格整合性フラグ
            goodsGroupItem.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH.equals(
                            goodsGroupEntity.getGroupSalePriceIntegrityFlag()) ? true : false);
            // お取りおきアイコンフラグ
            goodsGroupItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupDisplayEntity().getReserveIconFlag()) ? true : false);
            // NEWアイコンフラグ
            goodsGroupItem.setNewIconFlag(
                            HTypeNewIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getNewIconFlag()) ?
                                            true :
                                            false);
            // 2023-renew No92 from here
            // SALEアイコンフラグ
            goodsGroupItem.setSaleIconFlag(HTypeSaleIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getSaleIconFlag()) ?
                            true : false);
            // アウトレットアイコンフラグ
            goodsGroupItem.setOutletIconFlag(
                            HTypeOutletIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getOutletIconFlag()) ?
                                            true : false);
            // 2023-renew No92 to here
            // 商品表示単
            if (HTypeGroupPriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupPriceMarkDispFlag())) {
                goodsGroupItem.setPriceMarkDispFlag(true);
            }
            // 商品表示値引き後単価PC
            if (HTypeGroupSalePriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupSalePriceMarkDispFlag())) {
                goodsGroupItem.setSalePriceMarkDispFlag(true);
            }

            // PDR Migrate Customization to here
            goodsItems.add(goodsGroupItem);

        }

        goodsIndexModel.setRelatedGoodsItems(goodsItems);
    }

    // 2023-renew AddNo5 from here
    /**
     * セール価格リストによる価格を設定する
     *
     * @param goodsIndexModel                       商品詳細画面 Model
     * @param quantityDiscountResponseDetailDtos    セール価格リスト
     */
    public void setGoodsPricesByQuantityDiscountList(GoodsIndexModel goodsIndexModel, List<WebApiGetQuantityDiscountResponseDetailDto> quantityDiscountResponseDetailDtos) {
        List<BigDecimal> goodsPriceList = quantityDiscountResponseDetailDtos.stream()
                                                                            .flatMap(quantityDiscountDetails -> quantityDiscountDetails.getPriceList().stream().map(
                                                                                            WebApiGetQuantityDiscountResponsePriceDto::getPrice))
                                                                            .filter(Objects::nonNull)
                                                                            .distinct().collect(Collectors.toList());
        List<BigDecimal> goodsSalePriceList = Stream.of(
                        quantityDiscountResponseDetailDtos.stream()
                                                          .flatMap(quantityDiscountDetails -> quantityDiscountDetails.getSalePriceList().stream().map(
                                                                          WebApiGetQuantityDiscountResponseSalePriceDto::getSalePrice))
                                                          .filter(Objects::nonNull),
                        quantityDiscountResponseDetailDtos.stream()
                                                          .flatMap(quantityDiscountDetails -> quantityDiscountDetails.getCustomerSalePriceList().stream().map(
                                                                          WebApiGetQuantityDiscountResponseCustomerSalePriceDto::getCustomerSalePrice))
                                                          .filter(Objects::nonNull))
                                                    .flatMap(x -> x).distinct().collect(Collectors.toList());
        goodsIndexModel.setGoodsPriceLow(goodsPriceList.stream().min(Comparator.naturalOrder()).orElse(null));
        goodsIndexModel.setGoodsPriceHigh(goodsPriceList.stream().max(Comparator.naturalOrder()).orElse(null));
        goodsIndexModel.setGoodsSalePriceLow(goodsSalePriceList.stream().min(Comparator.naturalOrder()).orElse(null));
        goodsIndexModel.setGoodsSalePriceHigh(goodsSalePriceList.stream().max(Comparator.naturalOrder()).orElse(null));
    }
    // 2023-renew AddNo5 to here
    // 2023-renew No21 from here
    /**
     * 画面表示・再表示(よく一緒に購入される商品)
     *
     * @param goodsGroupDtoList よく一緒に購入される商品DTOリスト
     * @param goodsIndexModel   Model
     */
    public void toPageForLoadTogetherBuy(List<GoodsGroupDto> goodsGroupDtoList, GoodsIndexModel goodsIndexModel) {

        List<GoodsGroupItem> goodsItems = new ArrayList<>();
        for (GoodsGroupDto goodsGroupDto : goodsGroupDtoList) {

            GoodsGroupItem goodsGroupItem = ApplicationContextUtility.getBean(GoodsGroupItem.class);

            GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
            List<GoodsGroupImageEntity> goodsGroupImageEntityList = goodsGroupDto.getGoodsGroupImageEntityList();

            if (goodsGroupEntity != null) {
                goodsGroupItem.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
                goodsGroupItem.setGgcd(goodsGroupEntity.getGoodsGroupCode());
                goodsGroupItem.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
                // 税率
                BigDecimal taxRate = goodsGroupEntity.getTaxRate();
                goodsGroupItem.setTaxRate(taxRate);

                // 2023-renew AddNo5 from here
                // 通常価格 - 税込計算
                BigDecimal goodsPriceLow = goodsGroupEntity.getGoodsGroupMinPricePc();
                goodsGroupItem.setGoodsPriceLow(goodsPriceLow);
                BigDecimal goodsPriceHight = goodsGroupEntity.getGoodsGroupMaxPricePc();
                goodsGroupItem.setGoodsPriceHigh(goodsPriceHight);

                // 通常価格 - 価格帯チェック
                goodsGroupItem.setGoodsDisplayPriceRange(
                                goodsUtility.isGoodsDisplayPriceRange(goodsPriceLow, goodsPriceHight));
                goodsGroupItem.setGoodsPreDiscountPrice(goodsGroupEntity.getGoodsPreDiscountPrice());

                // セール価格
                BigDecimal goodsSalePriceLow = goodsGroupEntity.getGoodsGroupMinPriceMb();
                if (goodsSalePriceLow != null) {
                    goodsGroupItem.setGoodsSalePriceLow(goodsSalePriceLow);
                }
                BigDecimal goodsSalePriceHight = goodsGroupEntity.getGoodsGroupMaxPriceMb();
                if (goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsSalePriceHigh(goodsSalePriceHight);
                }

                // セール価格 - 価格帯チェック
                if (goodsSalePriceLow != null && goodsSalePriceHight != null) {
                    goodsGroupItem.setGoodsDisplayPreDiscountPriceRange(
                                    goodsUtility.isGoodsDisplayPriceRange(goodsSalePriceLow, goodsSalePriceHight));
                }
                // 2023-renew AddNo5 to here

                // 新着画像の表示期間を取得
                Timestamp whatsnewDate = goodsUtility.getRealWhatsNewDate(goodsGroupEntity.getWhatsnewDate());
                goodsGroupItem.setWhatsnewDate(whatsnewDate);

            }

            List<String> goodsImageList = new ArrayList<>();
            if (goodsGroupImageEntityList != null) {
                // 商品画像リストを取り出す。
                for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupImageEntityList) {
                    goodsImageList.add(goodsUtility.getGoodsImagePath(goodsGroupImageEntity.getImageFileName()));
                }
            }
            goodsGroupItem.setGoodsImageItems(goodsImageList);

            // アイコン情報の取得
            List<GoodsIconItem> goodsIconList = new ArrayList<>();
            if (goodsGroupDto.getGoodsInformationIconDetailsDtoList() != null) {
                for (GoodsInformationIconDetailsDto goodsInformationIconDetailsDto : goodsGroupDto.getGoodsInformationIconDetailsDtoList()) {
                    GoodsIconItem iconPageItem = ApplicationContextUtility.getBean(GoodsIconItem.class);
                    iconPageItem.setIconName(goodsInformationIconDetailsDto.getIconName());
                    iconPageItem.setIconColorCode(goodsInformationIconDetailsDto.getColorCode());

                    goodsIconList.add(iconPageItem);
                }
            }
            goodsGroupItem.setGoodsIconItems(goodsIconList);

            // 在庫状況表示
            StockStatusDisplayEntity stockStatusDisplayEntity = goodsGroupDto.getBatchUpdateStockStatus();
            if (stockStatusDisplayEntity != null) {
                if (stockStatusDisplayEntity.getStockStatusPc() != null) {
                    goodsGroupItem.setStockStatusPc(stockStatusDisplayEntity.getStockStatusPc().getValue());
                    // 2023-renew No10 from here
                    // 商品検索結果には在庫状況更新バッチ実行時点の在庫状況を表示する
                    // 商品グループ在庫の表示判定
                    if (goodsUtility.isGoodsGroupStock(stockStatusDisplayEntity.getStockStatusPc())) {
                        goodsGroupItem.setStockStatusDisplay(true);
                    }
                    // 2023-renew No10 to here
                }
            }

            // シリーズセール価格整合性フラグ
            if (goodsGroupEntity != null) {
                goodsGroupItem.setGroupSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH.equals(
                                goodsGroupEntity.getGroupSalePriceIntegrityFlag()));
            }
            // お取りおきアイコンフラグ
            goodsGroupItem.setReserveIconFlag(HTypeReserveIconFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupDisplayEntity().getReserveIconFlag()));
            // NEWアイコンフラグ
            goodsGroupItem.setNewIconFlag(
                            HTypeNewIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getNewIconFlag()));

            // 2023-renew No92 from here
            // SALEアイコンフラグ
            goodsGroupItem.setSaleIconFlag(
                            HTypeSaleIconFlag.ON.equals(goodsGroupDto.getGoodsGroupDisplayEntity().getSaleIconFlag()));
            // アウトレットアイコンフラグ
            goodsGroupItem.setOutletIconFlag(HTypeOutletIconFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupDisplayEntity().getOutletIconFlag()));
            // 2023-renew No92 to here

            // 商品表示単
            if (HTypeGroupPriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupPriceMarkDispFlag())) {
                goodsGroupItem.setPriceMarkDispFlag(true);
            }
            // 商品表示値引き後単価PC
            if (HTypeGroupSalePriceMarkDispFlag.ON.equals(
                            goodsGroupDto.getGoodsGroupEntity().getGroupSalePriceMarkDispFlag())) {
                goodsGroupItem.setSalePriceMarkDispFlag(true);
            }

            goodsItems.add(goodsGroupItem);

        }

        goodsIndexModel.setTogetherBuyGoodsItems(goodsItems);
    }
    // 2023-renew No21 to here

}
