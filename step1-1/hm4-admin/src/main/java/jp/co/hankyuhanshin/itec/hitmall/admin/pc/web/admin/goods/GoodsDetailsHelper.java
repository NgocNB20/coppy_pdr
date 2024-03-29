/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.GoodsCategoryTreeItem;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品管理：商品詳細ページHelper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsDetailsHelper extends AbstractGoodsRegistUpdateHelper {

    /**
     * 変換ユーティリティ
     */
    private ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     *
     * @param conversionUtility
     */
    @Autowired
    public GoodsDetailsHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param shopEntity        ショップ情報エンティティ
     * @param goodsDetailsModel ページ
     */
    public void toPageForLoad(ShopEntity shopEntity, GoodsDetailsModel goodsDetailsModel) {

        // 商品グループ画像登録更新用DTOリスト（ページ内表示用）の作成
        initTmpGoodsGroupImageRegistUpdateDtoList(goodsDetailsModel);

        // 商品共通情報部分セット
        setGoodsIndex(shopEntity, goodsDetailsModel);

        // 商品カテゴリー部分セット
        setGoodsCategoryTree(goodsDetailsModel);

        // 商品詳細部分セット
        setGoodsDetails(goodsDetailsModel);

        // 商品詳細テキスト部分セット
        setGoodsDetailstext(goodsDetailsModel);

        // 商品画像部分セット
        setGoodsImage(goodsDetailsModel);

        // 商品規格部分セット
        setGoodsUnit(goodsDetailsModel);

        // 商品在庫部分セット
        setGoodsStock(goodsDetailsModel);

        // 関連商品部分セット
        setGoodsRelation(goodsDetailsModel);

        // 2023-renew No21 from here
        // よく一緒に購入される商品部分セット
        setGoodsTogetherBuyGroup(goodsDetailsModel);
        // 2023-renew No21 to here
    }

    /**
     * 商品基本情報入力部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 商品グループエンティティと共通商品エンティティから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param shopEntity        ショップ情報エンティティー
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsIndex(ShopEntity shopEntity, GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 共通商品エンティティ
        GoodsEntity goods = goodsDetailsModel.getCommonGoodsEntity();

        // 商品グループコード
        goodsDetailsModel.setGoodsGroupCode(goodsGroup.getGoodsGroupCode());
        // 2023-renew No64 from here
        // 商品グループ名（管理用）
        goodsDetailsModel.setGoodsGroupNameAdmin(goodsGroup.getGoodsGroupNameAdmin());
        // 2023-renew No64 to here
        // 商品グループ名
        goodsDetailsModel.setGoodsGroupName(goodsGroup.getGoodsGroupName());
        //2023-renew No64 from here
        // 商品名1
        goodsDetailsModel.setGoodsGroupName1(goodsGroup.getGoodsGroupName1());
        // 商品名1_公開開始日時
        goodsDetailsModel.setGoodsGroupName1OpenStartDate(goodsGroup.getGoodsGroupName1OpenStartTime());
        // 商品名1_公開開始日時
        goodsDetailsModel.setGoodsGroupName1OpenStartTime(goodsGroup.getGoodsGroupName1OpenStartTime());
        // 商品名2
        goodsDetailsModel.setGoodsGroupName2(goodsGroup.getGoodsGroupName2());
        // 商品名2_公開開始日時
        goodsDetailsModel.setGoodsGroupName2OpenStartDate(goodsGroup.getGoodsGroupName2OpenStartTime());
        // 商品名2_公開開始日時
        goodsDetailsModel.setGoodsGroupName2OpenStartTime(goodsGroup.getGoodsGroupName2OpenStartTime());
        //2023-renew No64 to here
        // 登録日時
        goodsDetailsModel.setRegistTime(goodsGroup.getRegistTime());
        // 更新日時
        goodsDetailsModel.setUpdateTime(goodsGroup.getUpdateTime());
        // 新着日時
        if (goodsGroup.getWhatsnewDate() != null) {
            goodsDetailsModel.setWhatsnewDate(conversionUtility.toYmd(goodsGroup.getWhatsnewDate()));
        }
        // 税率
        goodsDetailsModel.setTaxRate(goodsGroup.getTaxRate());
        // 酒類フラグ
        goodsDetailsModel.setAlcoholFlag(EnumTypeUtil.getValue(goodsGroup.getAlcoholFlag()));
        // SNS連携フラグ
        goodsDetailsModel.setSnsLinkFlag(EnumTypeUtil.getValue(goodsGroup.getSnsLinkFlag()));
        // 個別配送
        goodsDetailsModel.setIndividualDeliveryType(goods.getIndividualDeliveryType().getValue());
        // 無料配送
        goodsDetailsModel.setFreeDeliveryFlag(goods.getFreeDeliveryFlag().getValue());
        // 公開状態PC
        goodsDetailsModel.setGoodsOpenStatusPC(goodsGroup.getGoodsOpenStatusPC().getValue());
        // 公開開始日PC
        goodsDetailsModel.setOpenStartDatePC(goodsGroup.getOpenStartTimePC());
        // 公開開始時間PC
        goodsDetailsModel.setOpenStartTimePC(goodsGroup.getOpenStartTimePC());
        // 公開終了日PC
        goodsDetailsModel.setOpenEndDatePC(goodsGroup.getOpenEndTimePC());
        // 公開終了時間PC
        goodsDetailsModel.setOpenEndTimePC(goodsGroup.getOpenEndTimePC());

        // PDR Migrate Customization from here
        // 商品区分 ⇒ 薬品区分
        goodsDetailsModel.setGoodsClassType(goodsGroup.getGoodsClassType());
        // 歯科専売可否フラグ
        goodsDetailsModel.setDentalMonopolySalesFlg(goodsGroup.getDentalMonopolySalesFlg());
        // カタログ表示
        goodsDetailsModel.setCatalogDisplayOrder(goodsGroup.getCatalogDisplayOrder());
        // シリーズ価格記号表示フラグ
        goodsDetailsModel.setGroupPriceMarkDispFlag(goodsGroup.getGroupPriceMarkDispFlag());
        // シリーズセール価格記号表示フラグ
        goodsDetailsModel.setGroupSalePriceMarkDispFlag(goodsGroup.getGroupSalePriceMarkDispFlag());
        // PDR Migrate Customization to here
        // 2023-renew No21 from here
        // 除外フラグ
        goodsDetailsModel.setExcludingFlag(goodsGroup.getExcludingFlag());
        // 2023-renew No21 to here
    }

    /**
     * 商品カテゴリー部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 商品グループエンティティと共通商品エンティティから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsCategoryTree(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 登録カテゴリリスト
        List<CategoryEntity> registCategoryList = goodsDetailsModel.getRegistCategoryEntityList();
        // カテゴリリスト
        List<CategoryEntity> categoryList = goodsDetailsModel.getCategoryEntityList();
        // カテゴリマップ
        Map<Integer, CategoryEntity> categoryEntityMap = new HashMap<>();

        if (registCategoryList == null || categoryList == null) {
            return;
        }
        // カテゴリマップの生成
        for (CategoryEntity categoryEntity : categoryList) {
            categoryEntityMap.put(categoryEntity.getCategorySeq(), categoryEntity);
        }

        List<GoodsCategoryTreeItem> goodsCategoryTrees = new ArrayList<>();
        // カテゴリ登録商品ごとにカテゴリパスを編集
        for (CategoryEntity registCategory : registCategoryList) {
            String categprySeqPath = registCategory.getCategorySeqPath();
            int categoryLevel = 0;
            String parent = "#";
            while (categprySeqPath.length() >= 8) {
                Integer categorySeq = Integer.valueOf(categprySeqPath.substring(0, 8));
                categprySeqPath = categprySeqPath.substring(8);
                CategoryEntity caetgory = categoryEntityMap.get(categorySeq);
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
            goodsDetailsModel.setCategoryTrees(goodsCategoryTreeList);
        }
    }

    /**
     * 商品詳細設定部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 商品グループエンティティと商品グループ表示エンティティから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsDetails(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 2023-renew searchKeyword-addition from here
        // 商品検索キーワード
        goodsDetailsModel.setSearchKeyword(goodsGroupDisplay.getSearchKeyword());
        // 2023-renew searchKeyword-addition to here
        // metaDescription
        goodsDetailsModel.setMetaDescription(goodsGroupDisplay.getMetaDescription());
        // metaKeyword
        goodsDetailsModel.setMetaKeyword(goodsGroupDisplay.getMetaKeyword());
    }

    /**
     * 商品詳細テキスト部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 商品グループ表示エンティティから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsDetailstext(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 商品説明１
        goodsDetailsModel.setGoodsNote1(goodsGroupDisplay.getGoodsNote1());
        //2023-renew No64 from here
        // 商品概要_公開開始日時
        goodsDetailsModel.setGoodsNote1OpenStartTimeDate(goodsGroupDisplay.getGoodsNote1OpenStartTime());
        // 商品概要_公開開始日時
        goodsDetailsModel.setGoodsNote1OpenStartTimeTime(goodsGroupDisplay.getGoodsNote1OpenStartTime());
        // 商品概要2
        goodsDetailsModel.setGoodsNote1Sub(goodsGroupDisplay.getGoodsNote1Sub());
        // 商品概要2_公開開始日時
        goodsDetailsModel.setGoodsNote1SubOpenStartTimeDate(goodsGroupDisplay.getGoodsNote1SubOpenStartTime());
        // 商品概要2_公開開始日時
        goodsDetailsModel.setGoodsNote1SubOpenStartTimeTime(goodsGroupDisplay.getGoodsNote1SubOpenStartTime());
        //2023-renew No64 to here
        // 商品説明2
        goodsDetailsModel.setGoodsNote2(goodsGroupDisplay.getGoodsNote2());
        //2023-renew No64 from here
        // 特徴_公開開始日時
        goodsDetailsModel.setGoodsNote2OpenStartTimeDate(goodsGroupDisplay.getGoodsNote2OpenStartTime());
        // 特徴_公開開始日時
        goodsDetailsModel.setGoodsNote2OpenStartTimeTime(goodsGroupDisplay.getGoodsNote2OpenStartTime());
        // 特徴2
        goodsDetailsModel.setGoodsNote2Sub(goodsGroupDisplay.getGoodsNote2Sub());
        // 特徴2_公開開始日時
        goodsDetailsModel.setGoodsNote2SubOpenStartTimeDate(goodsGroupDisplay.getGoodsNote2SubOpenStartTime());
        // 特徴2_公開開始日時
        goodsDetailsModel.setGoodsNote2SubOpenStartTimeTime(goodsGroupDisplay.getGoodsNote2SubOpenStartTime());
        //2023-renew No64 to here
        // 商品説明３
        goodsDetailsModel.setGoodsNote3(goodsGroupDisplay.getGoodsNote3());
        // 商品説明４
        goodsDetailsModel.setGoodsNote4(goodsGroupDisplay.getGoodsNote4());
        //2023-renew No64 from here
        // 注意事項_公開開始日時
        goodsDetailsModel.setGoodsNote4OpenStartTimeDate(goodsGroupDisplay.getGoodsNote4OpenStartTime());
        // 注意事項_公開開始日時
        goodsDetailsModel.setGoodsNote4OpenStartTimeTime(goodsGroupDisplay.getGoodsNote4OpenStartTime());
        // 注意事項2
        goodsDetailsModel.setGoodsNote4Sub(goodsGroupDisplay.getGoodsNote4Sub());
        // 注意事項2_公開開始日時
        goodsDetailsModel.setGoodsNote4SubOpenStartTimeDate(goodsGroupDisplay.getGoodsNote4SubOpenStartTime());
        // 注意事項2_公開開始日時
        goodsDetailsModel.setGoodsNote4SubOpenStartTimeTime(goodsGroupDisplay.getGoodsNote4SubOpenStartTime());
        //2023-renew No64 to here
        // 商品説明５
        goodsDetailsModel.setGoodsNote5(goodsGroupDisplay.getGoodsNote5());
        // 商品説明６
        goodsDetailsModel.setGoodsNote6(goodsGroupDisplay.getGoodsNote6());
        // 商品説明７
        goodsDetailsModel.setGoodsNote7(goodsGroupDisplay.getGoodsNote7());
        // 商品説明８
        goodsDetailsModel.setGoodsNote8(goodsGroupDisplay.getGoodsNote8());
        // 商品説明９
        goodsDetailsModel.setGoodsNote9(goodsGroupDisplay.getGoodsNote9());
        // 商品説明１０
        goodsDetailsModel.setGoodsNote10(goodsGroupDisplay.getGoodsNote10());
        //2023-renew No64 from here
        // シリーズPRコメントPC_公開開始日時
        goodsDetailsModel.setGoodsNote10OpenStartTimeDate(goodsGroupDisplay.getGoodsNote10OpenStartTime());
        // シリーズPRコメントPC_公開開始日時
        goodsDetailsModel.setGoodsNote10OpenStartTimeTime(goodsGroupDisplay.getGoodsNote10OpenStartTime());
        // シリーズPRコメントPC2
        goodsDetailsModel.setGoodsNote10Sub(goodsGroupDisplay.getGoodsNote10Sub());
        // シリーズPRコメントPC2_公開開始日時
        goodsDetailsModel.setGoodsNote10SubOpenStartTimeDate(goodsGroupDisplay.getGoodsNote10SubOpenStartTime());
        // シリーズPRコメントPC2_公開開始日時
        goodsDetailsModel.setGoodsNote10SubOpenStartTimeTime(goodsGroupDisplay.getGoodsNote10SubOpenStartTime());
        //2023-renew No64 to here

        // 商品説明１１
        goodsDetailsModel.setGoodsNote11(goodsGroupDisplay.getGoodsNote11());
        // 商品説明１２
        goodsDetailsModel.setGoodsNote12(goodsGroupDisplay.getGoodsNote12());
        // 商品説明１３
        goodsDetailsModel.setGoodsNote13(goodsGroupDisplay.getGoodsNote13());
        // 商品説明１４
        goodsDetailsModel.setGoodsNote14(goodsGroupDisplay.getGoodsNote14());
        // 商品説明１５
        goodsDetailsModel.setGoodsNote15(goodsGroupDisplay.getGoodsNote15());
        // 商品説明１６
        goodsDetailsModel.setGoodsNote16(goodsGroupDisplay.getGoodsNote16());
        // 商品説明１７
        goodsDetailsModel.setGoodsNote17(goodsGroupDisplay.getGoodsNote17());
        // 商品説明１８
        goodsDetailsModel.setGoodsNote18(goodsGroupDisplay.getGoodsNote18());
        // 商品説明１９
        goodsDetailsModel.setGoodsNote19(goodsGroupDisplay.getGoodsNote19());
        // 商品説明2０
        goodsDetailsModel.setGoodsNote20(goodsGroupDisplay.getGoodsNote20());
        // 2023-renew No11 from here
        // 商品説明2１
        goodsDetailsModel.setGoodsNote21(goodsGroupDisplay.getGoodsNote21());
        // 2023-renew No11 to here
        // 2023-renew No0 from here
        // 商品説明２２
        goodsDetailsModel.setGoodsNote22(goodsGroupDisplay.getGoodsNote22());
        // 2023-renew No0 to here

        // 受注連携設定１
        goodsDetailsModel.setOrderSetting1(goodsGroupDisplay.getOrderSetting1());
        // 受注連携設定２
        goodsDetailsModel.setOrderSetting2(goodsGroupDisplay.getOrderSetting2());
        // 受注連携設定３
        goodsDetailsModel.setOrderSetting3(goodsGroupDisplay.getOrderSetting3());
        // 受注連携設定４
        goodsDetailsModel.setOrderSetting4(goodsGroupDisplay.getOrderSetting4());
        // 受注連携設定５
        goodsDetailsModel.setOrderSetting5(goodsGroupDisplay.getOrderSetting5());
        // 受注連携設定６
        goodsDetailsModel.setOrderSetting6(goodsGroupDisplay.getOrderSetting6());
        // 受注連携設定７
        goodsDetailsModel.setOrderSetting7(goodsGroupDisplay.getOrderSetting7());
        // 受注連携設定８
        goodsDetailsModel.setOrderSetting8(goodsGroupDisplay.getOrderSetting8());
        // 受注連携設定９
        goodsDetailsModel.setOrderSetting9(goodsGroupDisplay.getOrderSetting9());
        // 受注連携設定１０
        goodsDetailsModel.setOrderSetting10(goodsGroupDisplay.getOrderSetting10());

        // 商品納期
        goodsDetailsModel.setDeliveryType(goodsGroupDisplay.getDeliveryType());

        // PDR Migrate Customization from here
        // SALEアイコンフラグ
        goodsDetailsModel.setSaleIconFlag(goodsGroupDisplay.getSaleIconFlag());
        // お取りおきアイコンフラグ
        goodsDetailsModel.setReserveIconFlag(goodsGroupDisplay.getReserveIconFlag());
        // NEWアイコンフラグ
        goodsDetailsModel.setNewIconFlag(goodsGroupDisplay.getNewIconFlag());
        // 2023-renew No92 from here
        // アウトレットアイコンフラグ
        goodsDetailsModel.setOutletIconFlag(goodsGroupDisplay.getOutletIconFlag());
        // 2023-renew No92 to here

        // SALEアイコン画像パス(プロパティから画像パスを指定)
        goodsDetailsModel.setImageFilePathSaleIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.sale"));

        // お取りおきアイコン画像パス(プロパティから画像パスを指定)
        goodsDetailsModel.setImageFilePathReserveIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.reserve"));

        // NEWアイコン画像パス(プロパティから画像パスを指定)
        goodsDetailsModel.setImageFilePathNewIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.new"));
        // 2023-renew No92 from here
        // アウトレットアイコン画像パス(プロパティから画像パスを指定)
        goodsDetailsModel.setImageFilePathOutletIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.outlet"));
        // 2023-renew No92 to here
        // PDR Migrate Customization to here
    }

    /**
     * 商品画像部分のセット<br/>
     *
     * <pre>
     * 商品グループ画像のパスを取得し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsImage(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 詳細画像に関する設定
        setDetailsImageInfo(goodsDetailsModel);

    }

    /**
     * 商品グループ詳細画像に関する設定<br/>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     */
    protected void setDetailsImageInfo(GoodsDetailsModel goodsDetailsModel) {

        String maxCount = PropertiesUtil.getSystemPropertiesValue("goodsimage.max.count");

        // 設定ファイルに指定数分ループ
        List<GoodsDetailsImageItem> items = new ArrayList<>();
        for (Integer i = 1; i <= Integer.valueOf(maxCount); i++) {
            GoodsDetailsImageItem item = ApplicationContextUtility.getBean(GoodsDetailsImageItem.class);

            item.setImageNo(i);

            // 商品画像をセット
            item.setImagePath(getGoodsImageFilepath(goodsDetailsModel, i));
            items.add(item);
        }
        goodsDetailsModel.setDetailsPageDetailsImageItems(items);
    }

    /**
     * 商品規格部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 商品グループエンティティと商品グループ表示エンティティと商品DTOリストから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsUnit(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplay = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsDetailsModel.getGoodsGroupDto().getGoodsDtoList();

        // 規格管理フラグ
        if (goodsGroupDisplay.getUnitManagementFlag() != null) {
            goodsDetailsModel.setUnitManagementFlag(goodsGroupDisplay.getUnitManagementFlag().getValue());
        }
        // 規格１表示名
        goodsDetailsModel.setUnitTitle1(goodsGroupDisplay.getUnitTitle1());
        // 規格２表示名
        goodsDetailsModel.setUnitTitle2(goodsGroupDisplay.getUnitTitle2());
        /*値引きコメント */
        goodsDetailsModel.setGoodsPreDiscountPrice(goodsGroup.getGoodsPreDiscountPrice());
        // 商品規格リスト情報
        // 商品規格情報リストのセット
        int index = 0;
        List<GoodsDetailsUnitItem> unitItemList = new ArrayList<>();
        List<GoodsDetailsUnitImageItem> unitImageItemList = new ArrayList<>();
        // 条件を満たすことはないのでデッドコードになっている
        if (goodsDtoList == null || goodsDtoList.size() == 0) {
            return;
        }
        for (GoodsDto goodsDto : goodsDtoList) {
            if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()) {
                // ステータス削除の場合は飛ばす
                continue;
            }
            GoodsDetailsUnitItem unitItem = ApplicationContextUtility.getBean(GoodsDetailsUnitItem.class);
            index = index + 1;
            unitItem.setUnitDspNo(index);
            unitItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
            unitItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            unitItem.setGoodsPrice(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPrice()));
            // 2023-renew addNo5 from here
            unitItem.setGoodsPriceInTaxLow(goodsDto.getGoodsEntity().getGoodsPriceInTaxLow());
            unitItem.setGoodsPriceInTaxHight(goodsDto.getGoodsEntity().getGoodsPriceInTaxHight());
            // 2023-renew addNo5 to here
            unitItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
            unitItem.setCatalogCode(goodsDto.getGoodsEntity().getCatalogCode());
            // PDR Migrate Customization from here
            unitItem.setReserveFlag(goodsDto.getGoodsEntity().getReserveFlag());
            unitItem.setGoodsManagementCode(goodsDto.getGoodsEntity().getGoodsManagementCode());
            unitItem.setGoodsDivisionCode(goodsDto.getGoodsEntity().getGoodsDivisionCode());
            unitItem.setGoodsCategory1(goodsDto.getGoodsEntity().getGoodsCategory1());
            unitItem.setGoodsCategory2(goodsDto.getGoodsEntity().getGoodsCategory2());
            unitItem.setGoodsCategory3(goodsDto.getGoodsEntity().getGoodsCategory3());
            unitItem.setLandSendFlag(goodsDto.getGoodsEntity().getLandSendFlag());
            unitItem.setCoolSendFlag(goodsDto.getGoodsEntity().getCoolSendFlag());
            unitItem.setCoolSendFrom(goodsDto.getGoodsEntity().getCoolSendFrom());
            unitItem.setCoolSendTo(goodsDto.getGoodsEntity().getCoolSendTo());
            unitItem.setUnit(goodsDto.getGoodsEntity().getUnit());
            unitItem.setPriceMarkDispFlag(goodsDto.getGoodsEntity().getPriceMarkDispFlag());
            unitItem.setSalePriceMarkDispFlag(goodsDto.getGoodsEntity().getSalePriceMarkDispFlag());
            unitItem.setEmotionPriceType(goodsDto.getGoodsEntity().getEmotionPriceType());
            // PDR Migrate Customization to here
            unitItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
            unitItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
            unitItem.setPreDiscountPrice(goodsDto.getGoodsEntity().getPreDiscountPrice());
            // 2023-renew addNo5 from here
            unitItem.setPreDiscountPriceLow(goodsDto.getGoodsEntity().getPreDiscountPriceLow());
            unitItem.setPreDiscountPriceHight(goodsDto.getGoodsEntity().getPreDiscountPriceHight());
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
            unitItem.setPurchasedMax(goodsDto.getGoodsEntity().getPurchasedMax());

            unitItemList.add(unitItem);

            GoodsDetailsUnitImageItem goodsDetailsUnitImageItem = new GoodsDetailsUnitImageItem();
            goodsDetailsUnitImageItem.setImageDspNo(index);
            goodsDetailsUnitImageItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
            goodsDetailsUnitImageItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            if (goodsDto.getUnitImage() != null) {
                goodsDetailsUnitImageItem.setUnitImagePath(
                                PropertiesUtil.getSystemPropertiesValue("images.path.goods") + "/"
                                + goodsDto.getUnitImage().getImageFileName());
            }
            // 2023-renew No76 from here
            goodsDetailsUnitImageItem.setUnitImageFlag(goodsDto.getGoodsEntity().getUnitImageFlag());
            // 2023-renew No76 to here
            unitImageItemList.add(goodsDetailsUnitImageItem);
        }
        goodsDetailsModel.setUnitItems(unitItemList);
        goodsDetailsModel.setUnitItemsImageList(unitImageItemList);
    }

    /**
     * 商品在庫部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 共通商品エンティティと商品DTOリストから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsStock(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 共通商品エンティティ
        GoodsEntity goods = goodsDetailsModel.getCommonGoodsEntity();
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsDetailsModel.getGoodsGroupDto().getGoodsDtoList();

        // 在庫管理フラグ
        if (goods.getStockManagementFlag() != null) {
            goodsDetailsModel.setStockManagementFlag(goods.getStockManagementFlag().getValue());
        }
        // 商品規格情報リストのセット
        int index = 0;
        List<GoodsDetailsStockItem> stockItemList = new ArrayList<>();
        // 条件を満たすことはないのでデッドコードになっている
        if (goodsDtoList == null || goodsDtoList.size() == 0) {
            return;
        }
        for (GoodsDto goodsDto : goodsDtoList) {
            if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()) {
                // ステータス削除の場合は飛ばす
                continue;
            }
            GoodsDetailsStockItem stockItem = ApplicationContextUtility.getBean(GoodsDetailsStockItem.class);
            index = index + 1;
            stockItem.setStockDspNo(index);
            stockItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
            stockItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
            stockItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
            stockItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
            stockItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
            stockItem.setPreDiscountPrice(goodsDto.getGoodsEntity().getPreDiscountPrice());
            if (goodsDto.getGoodsEntity().getSaleStatusPC() != null) {
                stockItem.setGoodsSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC().getValue());
            }
            // }
            if (goodsDto.getStockDto() != null) {
                stockItem.setRemainderFewStock(goodsDto.getStockDto().getRemainderFewStock());
                stockItem.setOrderPointStock(goodsDto.getStockDto().getOrderPointStock());
                stockItem.setSafetyStock(goodsDto.getStockDto().getSafetyStock());
                stockItem.setRealStock(goodsDto.getStockDto().getRealStock());
                stockItem.setSalesPossibleStock(goodsDto.getStockDto().getSalesPossibleStock());
                stockItem.setOrderReserveStock(goodsDto.getStockDto().getOrderReserveStock());
            }
            stockItemList.add(stockItem);
        }
        goodsDetailsModel.setStockItems(stockItemList);

    }

    /**
     * 商品関連商品部分のセット<br/>
     *
     * <pre>
     * 商品管理：商品詳細ページが持つ、
     * 関連商品エンティティリストから必要な値を取出し、
     * 商品管理：商品詳細ページへセットする。
     * </pre>
     *
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsRelation(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // 関連商品エンティティリスト
        List<GoodsRelationEntity> goodsRelationList = goodsDetailsModel.getGoodsRelationEntityList();
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

        // 関連商品情報リストのセット
        int index = 0;
        List<GoodsDetailsRelationItem> relationItems = new ArrayList<>();
        for (GoodsRelationEntity goodsRelationEntity : goodsRelationList) {
            GoodsDetailsRelationItem relationItem = ApplicationContextUtility.getBean(GoodsDetailsRelationItem.class);
            index = index + 1;
            relationItem.setRelationDspNo(index);
            relationItem.setRelationZenkakuNo(zenHanConversionUtility.toZenkaku(Integer.toString(index)));
            relationItem.setRelationGoodsGroupCode(goodsRelationEntity.getGoodsGroupCode());
            // 2023-renew No64 from here
            relationItem.setRelationGoodsGroupName(goodsRelationEntity.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here
            relationItems.add(relationItem);
        }
        goodsDetailsModel.setRelationItems(relationItems);

        // ダミー用関連商品（空）を作成する
        List<GoodsDetailsRelationItem> dummyRelationItems = new ArrayList<>();
        for (int i = 0; i < goodsDetailsModel.getGoodsRelationAmount() - relationItems.size(); i++) {
            GoodsDetailsRelationItem relationItem = ApplicationContextUtility.getBean(GoodsDetailsRelationItem.class);
            relationItem.setRelationDspNo(0);
            relationItem.setRelationZenkakuNo("");
            relationItem.setRelationGoodsGroupCode("");
            relationItem.setRelationGoodsGroupName("");
            dummyRelationItems.add(relationItem);
        }
        goodsDetailsModel.setRelationNoItems(dummyRelationItems);
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
     * @param goodsDetailsModel 商品管理：商品詳細ページ
     * @param customParams      案件用引数
     */
    protected void setGoodsTogetherBuyGroup(GoodsDetailsModel goodsDetailsModel, Object... customParams) {

        // よく一緒に購入される商品エンティティリスト
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList =
                        goodsDetailsModel.getGoodsTogetherBuyGroupEntityList();

        // よく一緒に購入される商品情報リストのセット
        List<GoodsTogetherBuyItem> goodsTogetherBuyItems = new ArrayList<>();
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            GoodsTogetherBuyItem goodsTogetherBuyItem = new GoodsTogetherBuyItem();

            goodsTogetherBuyItem.setTogetherBuyGoodsGroupCode(goodsTogetherBuyGroupEntity.getGoodsGroupCode());
            goodsTogetherBuyItem.setTogetherBuyGoodsGroupName(goodsTogetherBuyGroupEntity.getGoodsGroupName());
            goodsTogetherBuyItem.setTogetherBuyDspNo(goodsTogetherBuyGroupEntity.getOrderDisplay());
            goodsTogetherBuyItem.setRegistmethod(goodsTogetherBuyGroupEntity.getRegistMethod());

            goodsTogetherBuyItems.add(goodsTogetherBuyItem);
        }

        goodsDetailsModel.setTogetherBuyItems(goodsTogetherBuyItems);
    }
    // 2023-renew No21 to here

    /**
     * 削除処理時のページ情報編集<br/>
     *
     * @param goodsDetailsModel ページ
     */
    public void toPageForDelete(GoodsDetailsModel goodsDetailsModel) {
        // 商品グループエンティティ
        GoodsGroupEntity goodsGroupEntity = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupEntity();
        goodsDetailsModel.setOldGoodsOpenStatusPC(goodsGroupEntity.getGoodsOpenStatusPC());
        goodsGroupEntity.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.DELETED);
    }

}
