/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateHelper;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品管理：商品登録更新確認ページDxo<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsRegistUpdateConfirmHelper extends AbstractGoodsRegistUpdateHelper {

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public GoodsRegistUpdateConfirmHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param shopEntity             ショップ情報エンティティ
     * @param goodsRegistUpdateModel
     */
    public void toPageForLoad(ShopEntity shopEntity, GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループ画像登録更新用DTOリスト（ページ内表示用）の作成
        initTmpGoodsGroupImageRegistUpdateDtoList(goodsRegistUpdateModel);

        // 商品共通情報部分セット
        setGoodsIndex(goodsRegistUpdateModel);

        // 商品カテゴリー部分セット
        setGoodsCategory(goodsRegistUpdateModel);

        // 商品詳細部分セット
        setGoodsDetails(goodsRegistUpdateModel);

        // 商品詳細テキスト部分セット
        setGoodsDetailstext(goodsRegistUpdateModel);

        // 商品画像部分セット
        setGoodsImage(goodsRegistUpdateModel);

        // 商品規格部分セット
        setGoodsUnit(goodsRegistUpdateModel);

        // 商品在庫部分セット
        setGoodsStock(goodsRegistUpdateModel);

        // 関連商品部分セット
        setGoodsRelation(goodsRegistUpdateModel);

        // 2023-renew No21 from here
        // よく一緒に購入される商品部分セット
        setGoodsTogetherBuyGroup(goodsRegistUpdateModel);
        // 2023-renew No21 to here
    }

    /**
     * 商品基本情報入力部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 商品グループエンティティと共通商品エンティティから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams
     */
    protected void setGoodsIndex(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();

        // 共通商品エンティティ
        GoodsEntity goods = goodsRegistUpdateModel.getCommonGoodsEntity();

        // 商品グループコード
        goodsRegistUpdateModel.setGoodsGroupCode(goodsGroup.getGoodsGroupCode());

        // 2023-renew No64 from here
        // 商品グループ名（管理用）
        goodsRegistUpdateModel.setGoodsGroupNameAdmin(goodsGroup.getGoodsGroupNameAdmin());
        // 2023-renew No64 to here

        // 商品グループ名
        goodsRegistUpdateModel.setGoodsGroupName(goodsGroup.getGoodsGroupName());

        // 登録日時
        goodsRegistUpdateModel.setRegistTime(goodsGroup.getRegistTime());

        // 更新日時
        goodsRegistUpdateModel.setUpdateTime(goodsGroup.getUpdateTime());

        // 新着日時
        if (goodsGroup.getWhatsnewDate() != null) {
            goodsRegistUpdateModel.setWhatsnewDate(conversionUtility.toYmd(goodsGroup.getWhatsnewDate()));
        }
        // 税率
        goodsRegistUpdateModel.setTaxRate(goodsGroup.getTaxRate());

        // 酒類フラグ
        goodsRegistUpdateModel.setAlcoholFlag(EnumTypeUtil.getValue(goodsGroup.getAlcoholFlag()));

        // SNS連携フラグ
        goodsRegistUpdateModel.setSnsLinkFlag(EnumTypeUtil.getValue(goodsGroup.getSnsLinkFlag()));

        // 個別配送
        goodsRegistUpdateModel.setIndividualDeliveryType(goods.getIndividualDeliveryType().getValue());

        // 無料配送
        goodsRegistUpdateModel.setFreeDeliveryFlag(goods.getFreeDeliveryFlag().getValue());

        // 公開状態PC
        goodsRegistUpdateModel.setGoodsOpenStatusPC(goodsGroup.getGoodsOpenStatusPC().getValue());

        // 公開開始日PC
        goodsRegistUpdateModel.setOpenStartDatePC(goodsGroup.getOpenStartTimePC());

        // 公開開始時間PC
        goodsRegistUpdateModel.setOpenStartTimePC(goodsGroup.getOpenStartTimePC());

        // 公開終了日PC
        goodsRegistUpdateModel.setOpenEndDatePC(goodsGroup.getOpenEndTimePC());

        // 公開終了時間PC
        goodsRegistUpdateModel.setOpenEndTimePC(goodsGroup.getOpenEndTimePC());

        // 商品区分
        goodsRegistUpdateModel.setGoodsClassType(goodsGroup.getGoodsClassType());

        // カタログ表示
        goodsRegistUpdateModel.setCatalogDisplayOrder(goodsGroup.getCatalogDisplayOrder());

        // 歯科専売可否フラグ
        goodsRegistUpdateModel.setDentalMonopolySalesFlg(goodsGroup.getDentalMonopolySalesFlg());

        // シリーズ価格記号表示フラグ
        goodsRegistUpdateModel.setGroupPriceMarkDispFlag(goodsGroup.getGroupPriceMarkDispFlag());

        // シリーズセール価格記号表示フラグ
        goodsRegistUpdateModel.setGroupSalePriceMarkDispFlag(goodsGroup.getGroupSalePriceMarkDispFlag());
    }

    /**
     * 商品カテゴリー部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 登録カテゴリーリストとカテゴリリーストとカテゴリーマップから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsCategory(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 登録カテゴリーリスト
        List<CategoryEntity> registCategoryList = goodsRegistUpdateModel.getRegistCategoryEntityList();
        // カテゴリーリスト
        List<CategoryEntity> categoryList = goodsRegistUpdateModel.getCategoryEntityList();
        // カテゴリーマップ
        Map<Integer, CategoryEntity> categoryMap = new HashMap<>();

        // カテゴリー登録商品リスト(変更前)
        List<CategoryGoodsEntity> masterCategoryGoodsEntityList =
                        goodsRegistUpdateModel.getMasterCategoryGoodsEntityList();
        List<Integer> masterCategorySeqList = new ArrayList<>();
        for (int i = 0; masterCategoryGoodsEntityList != null && i < masterCategoryGoodsEntityList.size(); i++) {
            masterCategorySeqList.add(masterCategoryGoodsEntityList.get(i).getCategorySeq());
        }

        if (ListUtils.isEmpty(registCategoryList) || ListUtils.isEmpty(categoryList)) {
            goodsRegistUpdateModel.setCategoryTrees(null);
            return;
        }
        // カテゴリーマップの生成
        for (CategoryEntity categoryEntity : categoryList) {
            categoryMap.put(categoryEntity.getCategorySeq(), categoryEntity);
        }

        List<GoodsCategoryTreeItem> goodsCategoryTrees = new ArrayList<>();

        // カテゴリー登録商品ごとにカテゴリーパスを編集
        for (CategoryEntity registCategory : registCategoryList) {
            String categprySeqPath = registCategory.getCategorySeqPath();
            int categoryLevel = 0;
            String parent = "#";
            while (categprySeqPath.length() >= 8) {
                Integer categorySeq = Integer.valueOf(categprySeqPath.substring(0, 8));
                categprySeqPath = categprySeqPath.substring(8);
                CategoryEntity caetgory = categoryMap.get(categorySeq);
                if (caetgory != null && caetgory.getCategoryName() != null && !"".equals(
                                caetgory.getCategoryName().trim())) {
                    GoodsCategoryTreeItem goodsCategoryTreeItem =
                                    ApplicationContextUtility.getBean(GoodsCategoryTreeItem.class);
                    goodsCategoryTreeItem.setId(String.valueOf(categorySeq));
                    goodsCategoryTreeItem.setParentNode(parent);
                    goodsCategoryTreeItem.setLabel(caetgory.getCategoryName());
                    if (categoryLevel > 0) {
                        parent = String.valueOf(categorySeq);
                    }
                    goodsCategoryTreeItem.setLevel(categoryLevel);
                    categoryLevel++;
                    goodsCategoryTrees.add(goodsCategoryTreeItem);
                }
            }
        }
        Set<String> nameSet = new HashSet<>();
        List<GoodsCategoryTreeItem> goodsCategoryTreeList = goodsCategoryTrees.stream()
                                                                              .filter(e -> nameSet.add(e.getId()))
                                                                              .sorted(Comparator.comparingInt(
                                                                                              GoodsCategoryTreeItem::getLevel))
                                                                              .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(goodsCategoryTrees)) {
            goodsRegistUpdateModel.setCategoryTrees(goodsCategoryTreeList);
        }
    }

    /**
     * 商品詳細設定部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 商品グループエンティティと商品グループ表示エンティティから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsDetails(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay =
                        goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 2023-renew searchKeyword-addition from here
        // 商品検索キーワード
        goodsRegistUpdateModel.setSearchKeyword(goodsGroupDisplay.getSearchKeyword());
        // 2023-renew searchKeyword-addition to here

        // metaDescription
        goodsRegistUpdateModel.setMetaDescription(goodsGroupDisplay.getMetaDescription());

        // metaKeyword
        goodsRegistUpdateModel.setMetaKeyword(goodsGroupDisplay.getMetaKeyword());

        // PDR Migrate Customization from here
        // SALEアイコンフラグ
        goodsRegistUpdateModel.setSaleIconFlag(goodsGroupDisplay.getSaleIconFlag());

        // お取りおきアイコンフラグ
        goodsRegistUpdateModel.setReserveIconFlag(goodsGroupDisplay.getReserveIconFlag());

        // NEWアイコンフラグ
        goodsRegistUpdateModel.setNewIconFlag(goodsGroupDisplay.getNewIconFlag());

        // SALEアイコン画像パス(プロパティから画像パスを指定)
        goodsRegistUpdateModel.setImageFilePathSaleIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.sale"));

        // お取りおきアイコン画像パス(プロパティから画像パスを指定)
        goodsRegistUpdateModel.setImageFilePathReserveIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.reserve"));

        // NEWアイコン画像パス(プロパティから画像パスを指定)
        goodsRegistUpdateModel.setImageFilePathNewIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.new"));
        // PDR Migrate Customization to here
    }

    /**
     * 商品詳細テキスト部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 商品グループ表示エンティティから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsDetailstext(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay =
                        goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 商品説明１
        goodsRegistUpdateModel.setGoodsNote1(goodsGroupDisplay.getGoodsNote1());
        //2023-renew No64 from here
        // 商品説明１公開開始日時
        goodsRegistUpdateModel.setGoodsNote1OpenStartDateTime(goodsGroupDisplay.getGoodsNote1OpenStartTime());
        // 商品説明１Sub
        goodsRegistUpdateModel.setGoodsNote1Sub(goodsGroupDisplay.getGoodsNote1Sub());
        // 商品説明１Sub公開開始日時
        goodsRegistUpdateModel.setGoodsNote1SubOpenStartDateTime(goodsGroupDisplay.getGoodsNote1SubOpenStartTime());
        // 商品説明２
        goodsRegistUpdateModel.setGoodsNote2(goodsGroupDisplay.getGoodsNote2());
        // 商品説明２公開開始日時
        goodsRegistUpdateModel.setGoodsNote2OpenStartDateTime(goodsGroupDisplay.getGoodsNote2OpenStartTime());
        // 商品説明２Sub
        goodsRegistUpdateModel.setGoodsNote2Sub(goodsGroupDisplay.getGoodsNote2Sub());
        // 商品説明２Sub公開開始日時
        goodsRegistUpdateModel.setGoodsNote2SubOpenStartDateTime(goodsGroupDisplay.getGoodsNote2SubOpenStartTime());
        // 商品説明３
        goodsRegistUpdateModel.setGoodsNote3(goodsGroupDisplay.getGoodsNote3());
        // 商品説明４
        goodsRegistUpdateModel.setGoodsNote4(goodsGroupDisplay.getGoodsNote4());
        // 商品説明４公開開始日時
        goodsRegistUpdateModel.setGoodsNote4OpenStartDateTime(goodsGroupDisplay.getGoodsNote4OpenStartTime());
        // 商品説明４Sub
        goodsRegistUpdateModel.setGoodsNote4Sub(goodsGroupDisplay.getGoodsNote4Sub());
        // 商品説明４Sub公開開始日時
        goodsRegistUpdateModel.setGoodsNote4SubOpenStartDateTime(goodsGroupDisplay.getGoodsNote4SubOpenStartTime());
        // 商品説明５
        goodsRegistUpdateModel.setGoodsNote5(goodsGroupDisplay.getGoodsNote5());
        // 商品説明６
        goodsRegistUpdateModel.setGoodsNote6(goodsGroupDisplay.getGoodsNote6());
        // 商品説明７
        goodsRegistUpdateModel.setGoodsNote7(goodsGroupDisplay.getGoodsNote7());
        // 商品説明８
        goodsRegistUpdateModel.setGoodsNote8(goodsGroupDisplay.getGoodsNote8());
        // 商品説明９
        goodsRegistUpdateModel.setGoodsNote9(goodsGroupDisplay.getGoodsNote9());
        // 商品説明１０
        goodsRegistUpdateModel.setGoodsNote10(goodsGroupDisplay.getGoodsNote10());
        // 商品説明１０公開開始日時
        goodsRegistUpdateModel.setGoodsNote10OpenStartDateTime(goodsGroupDisplay.getGoodsNote10OpenStartTime());
        // 商品説明１０Sub
        goodsRegistUpdateModel.setGoodsNote10Sub(goodsGroupDisplay.getGoodsNote10Sub());
        // 商品説明１０Sub公開開始日時
        goodsRegistUpdateModel.setGoodsNote10SubOpenStartDateTime(goodsGroupDisplay.getGoodsNote10SubOpenStartTime());
        //2023-renew No64 to here
        // 商品説明１１
        goodsRegistUpdateModel.setGoodsNote11(goodsGroupDisplay.getGoodsNote11());
        // 商品説明１２
        goodsRegistUpdateModel.setGoodsNote12(goodsGroupDisplay.getGoodsNote12());
        // 商品説明１３
        goodsRegistUpdateModel.setGoodsNote13(goodsGroupDisplay.getGoodsNote13());
        // 商品説明１４
        goodsRegistUpdateModel.setGoodsNote14(goodsGroupDisplay.getGoodsNote14());
        // 商品説明１５
        goodsRegistUpdateModel.setGoodsNote15(goodsGroupDisplay.getGoodsNote15());
        // 商品説明１６
        goodsRegistUpdateModel.setGoodsNote16(goodsGroupDisplay.getGoodsNote16());
        // 商品説明１７
        goodsRegistUpdateModel.setGoodsNote17(goodsGroupDisplay.getGoodsNote17());
        // 商品説明１８
        goodsRegistUpdateModel.setGoodsNote18(goodsGroupDisplay.getGoodsNote18());
        // 商品説明１９
        goodsRegistUpdateModel.setGoodsNote19(goodsGroupDisplay.getGoodsNote19());
        // 商品説明２０
        goodsRegistUpdateModel.setGoodsNote20(goodsGroupDisplay.getGoodsNote20());
        // 2023-renew No11 from here
        // 商品説明２１
        goodsRegistUpdateModel.setGoodsNote21(goodsGroupDisplay.getGoodsNote21());
        // 2023-renew No11 to here
        // 2023-renew No0 from here
        // 商品説明２２
        goodsRegistUpdateModel.setGoodsNote22(goodsGroupDisplay.getGoodsNote22());
        // 2023-renew No0 to here

        // 受注連携設定１
        goodsRegistUpdateModel.setOrderSetting1(goodsGroupDisplay.getOrderSetting1());
        // 受注連携設定２
        goodsRegistUpdateModel.setOrderSetting2(goodsGroupDisplay.getOrderSetting2());
        // 受注連携設定３
        goodsRegistUpdateModel.setOrderSetting3(goodsGroupDisplay.getOrderSetting3());
        // 受注連携設定４
        goodsRegistUpdateModel.setOrderSetting4(goodsGroupDisplay.getOrderSetting4());
        // 受注連携設定５
        goodsRegistUpdateModel.setOrderSetting5(goodsGroupDisplay.getOrderSetting5());
        // 受注連携設定６
        goodsRegistUpdateModel.setOrderSetting6(goodsGroupDisplay.getOrderSetting6());
        // 受注連携設定７
        goodsRegistUpdateModel.setOrderSetting7(goodsGroupDisplay.getOrderSetting7());
        // 受注連携設定８
        goodsRegistUpdateModel.setOrderSetting8(goodsGroupDisplay.getOrderSetting8());
        // 受注連携設定９
        goodsRegistUpdateModel.setOrderSetting9(goodsGroupDisplay.getOrderSetting9());
        // 受注連携設定１０
        goodsRegistUpdateModel.setOrderSetting10(goodsGroupDisplay.getOrderSetting10());

        // 商品納期
        goodsRegistUpdateModel.setDeliveryType(goodsGroupDisplay.getDeliveryType());
    }

    /**
     * 商品画像部分のセット<br/>
     *
     * <pre>
     * 商品グループ画像のパスを取得し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsImage(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {
        // 詳細画像に関する設定
        setDetailsImageInfo(goodsRegistUpdateModel);
    }

    /**
     * 商品規格部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 商品グループエンティティと商品グループ表示エンティティと商品DTOリストから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsUnit(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay =
                        goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList();

        // 規格管理フラグ
        if (goodsGroupDisplay.getUnitManagementFlag() != null) {
            goodsRegistUpdateModel.setUnitManagementFlag(goodsGroupDisplay.getUnitManagementFlag().getValue());
        }

        // 規格１表示名
        goodsRegistUpdateModel.setUnitTitle1(goodsGroupDisplay.getUnitTitle1());

        // 規格２表示名
        goodsRegistUpdateModel.setUnitTitle2(goodsGroupDisplay.getUnitTitle2());

        //値引きコメント
        goodsRegistUpdateModel.setGoodsPreDiscountPrice(goodsGroup.getGoodsPreDiscountPrice());

        // 商品規格リスト情報
        // 商品規格情報リストのセット
        int index = 0;
        List<GoodsRegistUpdateUnitItem> unitItemList = new ArrayList<>();
        // 条件を満たすことはないのでデッドコードになっている
        if (goodsDtoList == null || goodsDtoList.size() == 0) {
            return;
        }
        for (GoodsDto goodsDto : goodsDtoList) {
            if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()
                || goodsDto.getGoodsEntity().getGoodsCode() == null) {
                // ステータス削除の場合は飛ばす(商品コードがnullの場合も)
                continue;
            }
            GoodsRegistUpdateUnitItem unitItem = ApplicationContextUtility.getBean(GoodsRegistUpdateUnitItem.class);
            unitItem.setUnitDspNo(++index);
            unitItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
            unitItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            unitItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
            unitItem.setCatalogCode(goodsDto.getGoodsEntity().getCatalogCode());
            unitItem.setGoodsPrice(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPrice()));
            // 2023-renew addNo5 from here
            unitItem.setGoodsPriceInTaxLow(
                            conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPriceInTaxLow()));
            unitItem.setGoodsPriceInTaxHight(
                            conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPriceInTaxHight()));
            // 2023-renew addNo5 to here
            unitItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
            unitItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
            unitItem.setPreDiscountPrice(conversionUtility.toString((goodsDto.getGoodsEntity().getPreDiscountPrice())));
            // 2023-renew addNo5 from here
            unitItem.setPreDiscountPriceLow(
                            conversionUtility.toString(goodsDto.getGoodsEntity().getPreDiscountPriceLow()));
            unitItem.setPreDiscountPriceHight(
                            conversionUtility.toString(goodsDto.getGoodsEntity().getPreDiscountPriceHight()));
            // 2023-renew addNo5 to here
            if (goodsDto.getGoodsEntity().getSaleStatusPC() != null) {
                unitItem.setGoodsSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC().getValue());
            }
            if (goodsDto.getGoodsEntity().getSaleStartTimePC() != null) {
                unitItem.setSaleStartDateTimePC(goodsDto.getGoodsEntity().getSaleStartTimePC());
            }
            if (goodsDto.getGoodsEntity().getSaleEndTimePC() != null) {
                unitItem.setSaleEndDateTimePC(goodsDto.getGoodsEntity().getSaleEndTimePC());
            }
            unitItem.setPurchasedMax(String.valueOf(goodsDto.getGoodsEntity().getPurchasedMax()));
            // PDR Migrate Customization from here
            unitItem.setGoodsManagementCode(goodsDto.getGoodsEntity().getGoodsManagementCode());
            unitItem.setGoodsDivisionCode(goodsDto.getGoodsEntity().getGoodsDivisionCode());
            unitItem.setGoodsCategory1(goodsDto.getGoodsEntity().getGoodsCategory1());
            unitItem.setGoodsCategory2(goodsDto.getGoodsEntity().getGoodsCategory2());
            unitItem.setGoodsCategory3(goodsDto.getGoodsEntity().getGoodsCategory3());
            unitItem.setLandSendFlag(goodsDto.getGoodsEntity().getLandSendFlag());
            unitItem.setCoolSendFlag(goodsDto.getGoodsEntity().getCoolSendFlag());
            unitItem.setCoolSendFrom(goodsDto.getGoodsEntity().getCoolSendFrom());
            unitItem.setCoolSendTo(goodsDto.getGoodsEntity().getCoolSendTo());
            unitItem.setReserveFlag(goodsDto.getGoodsEntity().getReserveFlag());
            unitItem.setUnit(goodsDto.getGoodsEntity().getUnit());
            unitItem.setPriceMarkDispFlag(goodsDto.getGoodsEntity().getPriceMarkDispFlag());
            unitItem.setSalePriceMarkDispFlag(goodsDto.getGoodsEntity().getSalePriceMarkDispFlag());
            unitItem.setEmotionPriceType(goodsDto.getGoodsEntity().getEmotionPriceType());
            // PDR Migrate Customization to here

            unitItemList.add(unitItem);
        }
        goodsRegistUpdateModel.setConfirmUnitItems(unitItemList);
    }

    /**
     * 商品在庫部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 共通商品エンティティと商品DTOリストから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsStock(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 共通商品エンティティ
        GoodsEntity goods = goodsRegistUpdateModel.getCommonGoodsEntity();
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList();

        // 在庫管理フラグ
        if (goods.getStockManagementFlag() != null) {
            goodsRegistUpdateModel.setStockManagementFlag(goods.getStockManagementFlag().getValue());
        }
        // 商品規格情報リストのセット
        int index = 0;
        List<GoodsRegistUpdateStockItem> stockItemList = new ArrayList<>();
        // 条件を満たすことはないのでデッドコードになっている
        if (goodsDtoList == null || goodsDtoList.size() == 0) {
            return;
        }
        for (GoodsDto goodsDto : goodsDtoList) {
            if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()
                || goodsDto.getGoodsEntity().getGoodsCode() == null) {
                // ステータス削除の場合は飛ばす(商品番号がnullの場合も)
                continue;
            }
            GoodsRegistUpdateStockItem stockItem = ApplicationContextUtility.getBean(GoodsRegistUpdateStockItem.class);
            stockItem.setStockDspNo(++index);
            stockItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
            stockItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            stockItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
            stockItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
            stockItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
            stockItem.setPreDiscountPrice(goodsDto.getGoodsEntity().getPreDiscountPrice());
            if (goodsDto.getGoodsEntity().getSaleStatusPC() != null) {
                stockItem.setGoodsSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC().getValue());
            }
            if (goodsDto.getStockDto() != null) {
                if (goodsDto.getStockDto().getSupplementCount() != null) {
                    stockItem.setSupplementCount(String.valueOf(goodsDto.getStockDto().getSupplementCount()));
                }
                stockItem.setRemainderFewStock(String.valueOf(goodsDto.getStockDto().getRemainderFewStock()));
                stockItem.setOrderPointStock(String.valueOf(goodsDto.getStockDto().getOrderPointStock()));
                stockItem.setSafetyStock(String.valueOf(goodsDto.getStockDto().getSafetyStock()));
                stockItem.setRealStock(goodsDto.getStockDto().getRealStock());
            }
            stockItemList.add(stockItem);
        }
        goodsRegistUpdateModel.setStockItems(stockItemList);
    }

    /**
     * 商品関連商品部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品登録更新確認ページが持つ、
     * 関連商品エンティティリストから必要な値を取出し、
     * 商品管理・商品登録更新確認ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel
     * @param customParams           案件用引数
     */
    protected void setGoodsRelation(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // 関連商品エンティティリスト
        List<GoodsRelationEntity> goodsRelationList = goodsRegistUpdateModel.getGoodsRelationEntityList();
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

        // 関連商品情報リストのセット
        int index = 0;
        List<GoodsRegistUpdateRelationItem> relationItems = new ArrayList<>();
        for (GoodsRelationEntity goodsRelationEntity : goodsRelationList) {
            GoodsRegistUpdateRelationItem relationItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateRelationItem.class);
            relationItem.setRelationDspNo(++index);
            relationItem.setRelationZenkakuNo(zenHanConversionUtility.toZenkaku(Integer.toString(index)));
            relationItem.setRelationGoodsGroupCode(goodsRelationEntity.getGoodsGroupCode());
            // 2023-renew No64 from here
            relationItem.setRelationGoodsGroupName(goodsRelationEntity.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here
            relationItems.add(relationItem);
        }
        goodsRegistUpdateModel.setRelationItems(relationItems);

        // ダミー用関連商品（空）を作成する
        List<GoodsRegistUpdateRelationItem> dummyRelationItems = new ArrayList<>();
        for (int i = ++index; i <= goodsRegistUpdateModel.getGoodsRelationAmount(); i++) {
            GoodsRegistUpdateRelationItem relationItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateRelationItem.class);
            relationItem.setRelationDspNo(i);
            dummyRelationItems.add(relationItem);
        }
        goodsRegistUpdateModel.setRelationNoItems(dummyRelationItems);
    }

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * よく一緒に購入される商品エンティティリストから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsRegistUpdateModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsTogetherBuyGroup(GoodsRegistUpdateModel goodsRegistUpdateModel, Object... customParams) {

        // よく一緒に購入される商品エンティティリスト
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList =
                        goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList();

        // よく一緒に購入される商品情報リストのセット
        List<GoodsRegistUpdateTogetherBuyItem> goodsRegistUpdateTogetherBuyItems = new ArrayList<>();
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            GoodsRegistUpdateTogetherBuyItem goodsRegistUpdateTogetherBuyItem = new GoodsRegistUpdateTogetherBuyItem();

            goodsRegistUpdateTogetherBuyItem.setTogetherBuyGoodsGroupCode(
                            goodsTogetherBuyGroupEntity.getGoodsGroupCode());
            goodsRegistUpdateTogetherBuyItem.setTogetherBuyGoodsGroupName(
                            goodsTogetherBuyGroupEntity.getGoodsGroupName());
            goodsRegistUpdateTogetherBuyItem.setTogetherBuyDspNo(goodsTogetherBuyGroupEntity.getOrderDisplay());
            goodsRegistUpdateTogetherBuyItem.setRegistmethod(goodsTogetherBuyGroupEntity.getRegistMethod());

            goodsRegistUpdateTogetherBuyItems.add(goodsRegistUpdateTogetherBuyItem);
        }

        goodsRegistUpdateModel.setTogetherBuyItems(goodsRegistUpdateTogetherBuyItems);
    }
    // 2023-renew No21 to here

    /**
     * 画像に関する登録更新情報を整理
     *
     * @param goodsRegistUpdateModel
     */
    public void toSetImageInfo(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループ画像
        setGoodsGroupImageRegistUpdateList(goodsRegistUpdateModel);
    }

    /**
     * 登録更新情報を整理(商品グループ画像)<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void setGoodsGroupImageRegistUpdateList(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        List<GoodsGroupImageRegistUpdateDto> ruList = new ArrayList<>();

        // 登録更新内容取得
        Map<Integer, GoodsGroupImageRegistUpdateDto> registUpdateGgImageMap = new HashMap<>();
        if (goodsRegistUpdateModel.getGoodsGroupImageRegistUpdateDtoList() != null) {
            for (GoodsGroupImageRegistUpdateDto ruDto : goodsRegistUpdateModel.getGoodsGroupImageRegistUpdateDtoList()) {
                // 画像連番でMAPを作成
                registUpdateGgImageMap.put(ruDto.getGoodsGroupImageEntity().getImageTypeVersionNo(), ruDto);
            }
        }

        // 全画像連番＋全画像種別 の状態を確認
        String maxCount = PropertiesUtil.getSystemPropertiesValue("goodsimage.max.count");
        for (int versionNo = 0; versionNo <= Integer.valueOf(maxCount); versionNo++) {

            // 該当するマスタ情報取得
            GoodsGroupImageEntity masterEntity =
                            goodsRegistUpdateModel.getMasterGoodsGroupImageEntityMap().get(versionNo);

            GoodsGroupImageRegistUpdateDto ruDto = registUpdateGgImageMap.get(versionNo);

            if (ruDto != null) {
                // 登録更新リストに含まれている場合、表示状態を上書き
                ruList.add(ruDto);

                // 登録更新リストに含まれていないが、表示状態を変更した場合はマスタ情報確認
            } else if (masterEntity != null) {
                // マスタに存在するため、上書き情報作成
                GoodsGroupImageRegistUpdateDto updateDto =
                                ApplicationContextUtility.getBean(GoodsGroupImageRegistUpdateDto.class);

                // 表示状態上書き+削除対象外
                updateDto.setGoodsGroupImageEntity(masterEntity);
                updateDto.setDeleteFlg(false);

                ruList.add(updateDto);
            }
        }

        goodsRegistUpdateModel.setGoodsGroupImageRegistUpdateDtoList(ruList);
    }

    /**
     * 商品画像設定<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void setDetailsImageInfo(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // 商品グループ詳細画像アイテムリスト作成
        createDetailsImageItems(goodsRegistUpdateModel);

    }

    /**
     * 商品グループ詳細画像アイテム作成<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void createDetailsImageItems(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品画像は登録更新画面から引き渡す
        goodsRegistUpdateModel.setConfirmGoodsImageItems(goodsRegistUpdateModel.getGoodsImageItems());
    }

    /**
     * 入庫登録<br/>
     *
     * @param goodsRegistUpdateStockItem
     * @return stockResultEntity　入庫実績エンティティ
     */
    public StockResultEntity toStockResultEntityforStockSupplementIns(GoodsRegistUpdateStockItem goodsRegistUpdateStockItem) {
        StockResultEntity stockResultEntity = ApplicationContextUtility.getBean(StockResultEntity.class);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // リストより、選択された商品に対して、入庫実績エンティティへ設定する。
        stockResultEntity.setGoodsSeq(goodsRegistUpdateStockItem.getGoodsSeq());
        stockResultEntity.setSupplementCount(
                        conversionUtility.toBigDecimal(goodsRegistUpdateStockItem.getSupplementCount()));
        return stockResultEntity;

    }

    /**
     * 変更箇所の表示スタイル設定処理<br/>
     *
     * @param goodsRegistUpdateModel Model
     * @param original               　修正前GoodsGroupDto
     * @param modified               　修正後GoodsGroupDto
     */
    protected void setDiff(GoodsRegistUpdateModel goodsRegistUpdateModel,
                           GoodsGroupDto original,
                           GoodsGroupDto modified) {

        // 商品グループエンティティの差分
        goodsRegistUpdateModel.setModifiedGoodsGroupList(
                        DiffUtil.diff(original.getGoodsGroupEntity(), modified.getGoodsGroupEntity()));

        // 商品グループ表示エンティティの差分
        goodsRegistUpdateModel.setModifiedGoodsGroupDspList(
                        DiffUtil.diff(original.getGoodsGroupDisplayEntity(), modified.getGoodsGroupDisplayEntity()));

        // 規格毎の差分
        setGoodsUnitDiff(goodsRegistUpdateModel, original.getGoodsDtoList(), modified.getGoodsDtoList());

        // 関連商品エンティティの差分
        setGoodsRelationDiff(goodsRegistUpdateModel, goodsRegistUpdateModel.getMasterGoodsRelationEntityList(),
                             goodsRegistUpdateModel.getGoodsRelationEntityList()
                            );
        // 2023-renew No21 from here
        // よく一緒に購入される商品エンティティの差分
        setGoodsTogetherBuyGroupDiff(goodsRegistUpdateModel,
                                     goodsRegistUpdateModel.getMasterGoodsTogetherBuyGroupEntityList(),
                                     goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList()
                                    );
        // 2023-renew No21 to here
    }

    /**
     * 規格毎の変更箇所の表示スタイル設定処理<br/>
     *
     * @param goodsRegistUpdateModel Model
     * @param original               　修正前GoodsDtoリスト
     * @param modified               　修正後GoodsDtoリスト
     */
    protected void setGoodsUnitDiff(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                    List<GoodsDto> original,
                                    List<GoodsDto> modified) {

        // 対象Entity名
        String goodsEntity = goodsRegistUpdateModel.diffObjectNameGoods + DiffUtil.SEPARATOR;

        // 商品Dtoの差分
        List<String> tmpGoodsList = new ArrayList<>();
        goodsRegistUpdateModel.setModifiedGoodsList(new ArrayList<>());
        goodsRegistUpdateModel.setModifiedStockList(new ArrayList<>());

        // 修正後のほうが商品規格数が多い場合
        if (original.size() < modified.size()) {
            for (int i = 0; i < original.size(); i++) {
                // 削除された規格は除外
                if (HTypeGoodsSaleStatus.DELETED != modified.get(i).getGoodsEntity().getSaleStatusPC()) {
                    goodsRegistUpdateModel.getModifiedGoodsList()
                                          .add(DiffUtil.diff(original.get(i).getGoodsEntity(),
                                                             modified.get(i).getGoodsEntity()
                                                            ));
                    goodsRegistUpdateModel.getModifiedStockList()
                                          .add(DiffUtil.diff(original.get(i).getStockDto(),
                                                             modified.get(i).getStockDto()
                                                            ));
                }
            }
            // 追加分の規格
            for (int i = original.size(); i < modified.size(); i++) {
                tmpGoodsList = DiffUtil.diff(new GoodsEntity(), modified.get(i).getGoodsEntity());
                if (HTypeGoodsSaleStatus.NO_SALE == modified.get(i).getGoodsEntity().getSaleStatusPC()) {
                    // 非販売で追加した場合、差分がでないので、追加
                    tmpGoodsList.add(goodsEntity + "saleStatusPC");
                }
                goodsRegistUpdateModel.getModifiedGoodsList().add(tmpGoodsList);
                goodsRegistUpdateModel.getModifiedStockList()
                                      .add(DiffUtil.diff(new StockDto(), modified.get(i).getStockDto()));
            }
        }
        // 修正前後で同じ規格数の場合 ※修正後に削除されていても、配列数は減少しないので、修正前 > 修正後になることはない
        else {
            for (int i = 0; i < original.size(); i++) {
                // 削除された規格は除外
                if (HTypeGoodsSaleStatus.DELETED != modified.get(i).getGoodsEntity().getSaleStatusPC()) {
                    goodsRegistUpdateModel.getModifiedGoodsList()
                                          .add(DiffUtil.diff(original.get(i).getGoodsEntity(),
                                                             modified.get(i).getGoodsEntity()
                                                            ));
                    goodsRegistUpdateModel.getModifiedStockList()
                                          .add(DiffUtil.diff(original.get(i).getStockDto(),
                                                             modified.get(i).getStockDto()
                                                            ));
                }
            }
        }
    }

    /**
     * 関連商品の変更箇所の表示スタイル設定処理<br/>
     *
     * @param goodsRegistUpdateModel Model
     * @param original               　修正前 関連商品エンティティリスト
     * @param modified               　修正後 関連商品エンティティリスト
     */
    protected void setGoodsRelationDiff(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                        List<GoodsRelationEntity> original,
                                        List<GoodsRelationEntity> modified) {

        if (CollectionUtil.isEmpty(original) && CollectionUtil.isEmpty(modified)) {
            // 修正前後で設定されていないので、差分なし
            return;
        }

        goodsRegistUpdateModel.setModifiedGoodsRelationList(new ArrayList<>());

        if (CollectionUtil.isEmpty(original)) {
            // 変更前の情報がない場合は新規追加のため、追加分の差分リストを作成
            for (int i = 0; i < modified.size(); i++) {
                goodsRegistUpdateModel.getModifiedGoodsRelationList()
                                      .add(DiffUtil.diff(new GoodsRelationEntity(), modified.get(i)));
            }
        } else {

            // 修正後のほうが関連商品数が多い場合
            if (original.size() < modified.size()) {
                for (int i = 0; i < original.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsRelationList()
                                          .add(DiffUtil.diff(original.get(i), modified.get(i)));
                }
                // 追加分
                for (int i = original.size(); i < modified.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsRelationList()
                                          .add(DiffUtil.diff(new GoodsRelationEntity(), modified.get(i)));
                }
            }
            // 修正前のほうが関連商品数が多い、または同数の場合
            else {
                for (int i = 0; i < modified.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsRelationList()
                                          .add(DiffUtil.diff(original.get(i), modified.get(i)));
                }
            }
        }
    }

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品の変更箇所の表示スタイル設定処理<br/>
     *
     * @param goodsRegistUpdateModel Model
     * @param original               　修正前 よく一緒に購入される商品エンティティリスト
     * @param modified               　修正後 よく一緒に購入される商品エンティティリスト
     */
    protected void setGoodsTogetherBuyGroupDiff(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                List<GoodsTogetherBuyGroupEntity> original,
                                                List<GoodsTogetherBuyGroupEntity> modified) {

        if (CollectionUtil.isEmpty(original) && CollectionUtil.isEmpty(modified)) {
            // 修正前後で設定されていないので、差分なし
            return;
        }

        goodsRegistUpdateModel.setModifiedGoodsTogetherBuyGroupList(new ArrayList<>());

        if (CollectionUtil.isEmpty(original)) {
            // 変更前の情報がない場合は新規追加のため、追加分の差分リストを作成
            for (int i = 0; i < modified.size(); i++) {
                goodsRegistUpdateModel.getModifiedGoodsTogetherBuyGroupList()
                                      .add(DiffUtil.diff(new GoodsTogetherBuyGroupEntity(), modified.get(i)));
            }
        } else {

            // 修正後のほうが関連商品数が多い場合
            if (original.size() < modified.size()) {
                for (int i = 0; i < original.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsTogetherBuyGroupList()
                                          .add(DiffUtil.diff(original.get(i), modified.get(i)));
                }
                // 追加分
                for (int i = original.size(); i < modified.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsTogetherBuyGroupList()
                                          .add(DiffUtil.diff(new GoodsTogetherBuyGroupEntity(), modified.get(i)));
                }
            }
            // 修正前のほうが関連商品数が多い、または同数の場合
            else {
                for (int i = 0; i < modified.size(); i++) {
                    goodsRegistUpdateModel.getModifiedGoodsTogetherBuyGroupList()
                                          .add(DiffUtil.diff(original.get(i), modified.get(i)));
                }
            }
        }
    }
    // 2023-renew No21 to here

    /**
     * 商品規格画像登録更新編集<br/>
     *
     * @param model GoodsRegistUpdateModel
     */
    public void toSetUnitImagesInfo(GoodsRegistUpdateModel model) {

        for (GoodsRegistUpdateUnitImage imageItem : model.getUnitImagesItems()) {
            GoodsDto goodsDto = getGoodsDto(model.getGoodsGroupDto().getGoodsDtoList(), imageItem.getGoodsCode());

            if (goodsDto != null) {
                if (goodsDto.getUnitImage() != null && ObjectUtils.allNull(goodsDto.getUnitImage())) {
                    goodsDto.setUnitImage(null);
                } else {
                    GoodsImageEntity goodsImageEntity;
                    if (goodsDto.getUnitImage() != null) {
                        goodsImageEntity = goodsDto.getUnitImage();
                    } else {
                        goodsImageEntity = new GoodsImageEntity();
                        goodsDto.setUnitImage(goodsImageEntity);
                    }
                    goodsImageEntity.setGoodsGroupSeq(goodsDto.getGoodsEntity().getGoodsGroupSeq());
                    goodsImageEntity.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
                    goodsImageEntity.setTmpFilePath(imageItem.getRealImagePath());
                    if (goodsDto.getGoodsEntity().getSaleStatusPC().equals(HTypeGoodsSaleStatus.SALE)) {
                        if (imageItem.getUrlImagePath() != null) {
                            goodsImageEntity.setImageFileName(imageItem.getUnitImageName());
                        } else {
                            goodsImageEntity.setImageFileName(null);
                        }
                        goodsImageEntity.setDisplayFlag(HTypeDisplayStatus.DISPLAY);
                    } else if (goodsDto.getGoodsEntity().getSaleStatusPC().equals(HTypeGoodsSaleStatus.NO_SALE)) {
                        if (imageItem.getUrlImagePath() != null) {
                            goodsImageEntity.setImageFileName(imageItem.getUnitImageName());
                        } else {
                            goodsImageEntity.setImageFileName(null);
                        }
                        goodsImageEntity.setDisplayFlag(HTypeDisplayStatus.NO_DISPLAY);
                    }
                    // 画像削除
                    else {
                        goodsImageEntity.setImageFileName(null);
                    }
                }
                // 2023-renew No76 from here
                // 規格画像有無更新
                if (goodsDto.getUnitImage().getImageFileName() != null) {
                    goodsDto.getGoodsEntity().setUnitImageFlag(HTypeUnitImageFlag.ON);
                } else {
                    goodsDto.getGoodsEntity().setUnitImageFlag(HTypeUnitImageFlag.OFF);
                }
                // 2023-renew No76 to here
            }
        }
        modifyUnitImage(model);
    }

    /**
     * 商品コードで商品Dtoを取得<br/>
     *
     * @param goodsDtoList 商品Dtoリスト
     * @param goodsCode    商品コード
     */
    private GoodsDto getGoodsDto(List<GoodsDto> goodsDtoList, String goodsCode) {
        if (goodsCode != null) {
            for (GoodsDto goodsDto : goodsDtoList) {
                if (goodsCode.equals(goodsDto.getGoodsEntity().getGoodsCode())) {
                    return goodsDto;
                }
            }
        }
        return null;
    }

    /**
     * 商品規格画像登録更新編集<br/>
     *
     * @param model GoodsRegistUpdateModel
     */
    public void modifyUnitImage(GoodsRegistUpdateModel model) {
        for (GoodsDto goodsDto : model.getGoodsGroupDto().getGoodsDtoList()) {
            if (goodsDto.getUnitImage() != null) {
                if (goodsDto.getGoodsEntity().getSaleStatusPC().equals(HTypeGoodsSaleStatus.DELETED)) {
                    goodsDto.getUnitImage().setImageFileName(null);
                }
                if (ObjectUtils.allNull(goodsDto.getUnitImage().getGoodsGroupSeq(),
                                        goodsDto.getUnitImage().getGoodsSeq(),
                                        goodsDto.getUnitImage().getImageFileName(),
                                        goodsDto.getUnitImage().getTmpFilePath()
                                       )) {
                    goodsDto.setUnitImage(null);
                }
            }
        }
    }

    /**
     * 商品規格画像削除チェック<br/>
     *
     * @param model GoodsRegistUpdateModel
     */
    public List<String> checkGoodsImageDeleted(GoodsRegistUpdateModel model) {
        List<String> result = new ArrayList<>();
        for (GoodsDto goodsDto : model.getGoodsGroupDto().getGoodsDtoList()) {
            if (goodsDto.getGoodsEntity().getSaleStatusPC().equals(HTypeGoodsSaleStatus.DELETED) && (
                            goodsDto.getUnitImage() != null && goodsDto.getUnitImage().getImageFileName() != null)) {
                result.add(goodsDto.getGoodsEntity().getGoodsCode());
            }
        }
        return result;
    }
}
