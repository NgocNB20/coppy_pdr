package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateHelper;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
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
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品管理：商品登録更新（商品基本設定）ページHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsRegistUpdateHelper extends AbstractGoodsRegistUpdateHelper {

    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品グループ画像DAO
     */
    private final GoodsGroupImageDao goodsGroupImageDao;

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * コンストラクター
     *
     * @param conversionUtility
     * @param goodsGroupImageDao
     * @param goodsGroupDao
     */
    @Autowired
    public GoodsRegistUpdateHelper(ConversionUtility conversionUtility,
                                   GoodsGroupImageDao goodsGroupImageDao,
                                   GoodsGroupDao goodsGroupDao) {
        this.conversionUtility = conversionUtility;
        this.goodsGroupImageDao = goodsGroupImageDao;
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 更新時の初期表示データをページに反映<br/>
     *
     * @param shopEntity             ショップ情報エンティティ
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForLoad(ShopEntity shopEntity, GoodsRegistUpdateModel goodsRegistUpdateModel) {
        toPageForLoadCommon(shopEntity, goodsRegistUpdateModel);
        toPageForLoadDetails(goodsRegistUpdateModel);
        toPageForLoadDetailsText(goodsRegistUpdateModel);
        toPageForLoadUnitImage(goodsRegistUpdateModel);
        toPageForLoadUnit(goodsRegistUpdateModel);
        toPageForLoadImage(goodsRegistUpdateModel);
        toPageForLoadStock(goodsRegistUpdateModel);
        toPageForLoadRelation(goodsRegistUpdateModel);
        // 2023-renew No21 from here
        toPageForLoadTogetherBuy(goodsRegistUpdateModel);
        // 2023-renew No21 to here
    }

    /**
     * 入力情報をページにセット<br/>
     *
     * @param goodsRegistUpdateModel 商品管理：商品登録更新ページ
     */
    public void toPageForNext(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        toPageForNextCommon(goodsRegistUpdateModel);
        toPageForNextCategory(goodsRegistUpdateModel);
        toPageForNextDetails(goodsRegistUpdateModel);
        toPageForNextDetailsText(goodsRegistUpdateModel);
        toPageForNextUnit(goodsRegistUpdateModel);
        toPageForNextImage(goodsRegistUpdateModel);
        toPageForNextStock(goodsRegistUpdateModel);
        toPageForNextRelation(goodsRegistUpdateModel);
        // 2023-renew No21 from here
        toPageForNextTogetherBuy(goodsRegistUpdateModel);
        // 2023-renew No21 to here
    }

    /***************************************************************************************************************************
     ** 商品基本設定
     ***************************************************************************************************************************/
    /**
     * 更新時の初期表示データをページに反映<br/>
     *
     * @param shopEntity             ショップ情報エンティティ
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForLoadCommon(ShopEntity shopEntity, GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループエンティティ
        GoodsGroupEntity goodsGroupEntity = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = goodsRegistUpdateModel.getCommonGoodsEntity();
        // 商品基本設定
        setToPageBasicSettings(goodsRegistUpdateModel, goodsGroupEntity);
        // 外部連携設定
        setToPageExternalCoordinationSettings(goodsRegistUpdateModel, goodsGroupEntity);
        // 価格設定
        setToPagePriceSettings(goodsRegistUpdateModel, goodsGroupEntity);
        // 配送設定
        setToPageDeliverySettings(goodsRegistUpdateModel, commonGoodsEntity);
        // 公開状態設定
        setToPageOpenSettings(goodsRegistUpdateModel, goodsGroupEntity);
    }

    /**
     * 商品基本設定項目をページに設定する<br/>
     *
     * @param goodsRegistUpdateModel ページクラス
     * @param goodsGroupEntity       商品グループEntity
     */
    private void setToPageBasicSettings(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                        GoodsGroupEntity goodsGroupEntity) {
        // 商品グループコード
        goodsRegistUpdateModel.setGoodsGroupCode(goodsGroupEntity.getGoodsGroupCode());
        // 2023-renew No64 from here
        // 商品名（管理用）
        goodsRegistUpdateModel.setGoodsGroupNameAdmin(goodsGroupEntity.getGoodsGroupNameAdmin());
        // 2023-renew No64 to here
        // 商品名
        goodsRegistUpdateModel.setGoodsGroupName1(goodsGroupEntity.getGoodsGroupName1());
        //2023-renew No64 from here
        // 商品グループ名 公開開始日時
        if (goodsGroupEntity.getGoodsGroupName1OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsGroupName1OpenStartDate(
                            conversionUtility.toYmd(goodsGroupEntity.getGoodsGroupName1OpenStartTime()));
            goodsRegistUpdateModel.setGoodsGroupName1OpenStartTime(
                            conversionUtility.toHms(goodsGroupEntity.getGoodsGroupName1OpenStartTime()));
        }
        // 商品グループ名２
        goodsRegistUpdateModel.setGoodsGroupName2(goodsGroupEntity.getGoodsGroupName2());
        // 商品グループ名２公開開始日時
        if (goodsGroupEntity.getGoodsGroupName2OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsGroupName2OpenStartDate(
                            conversionUtility.toYmd(goodsGroupEntity.getGoodsGroupName2OpenStartTime()));
            goodsRegistUpdateModel.setGoodsGroupName2OpenStartTime(
                            conversionUtility.toHms(goodsGroupEntity.getGoodsGroupName2OpenStartTime()));
        }
        //2023-renew No64 to here
        // 登録日時
        goodsRegistUpdateModel.setRegistTime(goodsGroupEntity.getRegistTime());
        // 更新日時
        goodsRegistUpdateModel.setUpdateTime(goodsGroupEntity.getUpdateTime());
        // 新着日時
        goodsRegistUpdateModel.setWhatsnewDate(conversionUtility.toYmd(goodsGroupEntity.getWhatsnewDate()));
        // 酒類フラグ
        goodsRegistUpdateModel.setAlcoholFlag(goodsGroupEntity.getAlcoholFlag().getValue());
        // PDR Migrate Customization from here
        // 商品販売区分⇒薬品区分
        goodsRegistUpdateModel.setGoodsClassType(goodsGroupEntity.getGoodsClassType());
        // カタログ表示順
        goodsRegistUpdateModel.setCatalogDisplayOrder(goodsGroupEntity.getCatalogDisplayOrder());
        // 歯科専売可否フラグ
        goodsRegistUpdateModel.setDentalMonopolySalesFlg(goodsGroupEntity.getDentalMonopolySalesFlg());
        // PDR Migrate Customization to here
        // 2023-renew No21 from here
        // 除外フラグ
        goodsRegistUpdateModel.setExcludingFlag(goodsGroupEntity.getExcludingFlag());
        goodsRegistUpdateModel.setExcluding(HTypeExcludeFlag.ON.equals(goodsGroupEntity.getExcludingFlag()));
        // 2023-renew No21 to here
    }

    /**
     * 外部連携設定項目をページに設定する<br/>
     *
     * @param goodsRegistUpdateModel ページクラス
     * @param goodsGroupEntity       商品グループEntity
     */
    private void setToPageExternalCoordinationSettings(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                       GoodsGroupEntity goodsGroupEntity) {
        // SNS連携
        goodsRegistUpdateModel.setSnsLinkFlag(EnumTypeUtil.getValue(goodsGroupEntity.getSnsLinkFlag()));
    }

    /**
     * 価格設定項目をページに設定する<br/>
     *
     * @param goodsRegistUpdateModel ページクラス
     * @param goodsGroupEntity       商品グループEntity
     */
    private void setToPagePriceSettings(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                        GoodsGroupEntity goodsGroupEntity) {

        // 税率
        goodsRegistUpdateModel.setTaxRate(goodsGroupEntity.getTaxRate());
    }

    /**
     * 配送設定項目をページに設定する<br/>
     *
     * @param goodsRegistUpdateModel ページクラス
     * @param commonGoodsEntity      共通商品Entity
     */
    private void setToPageDeliverySettings(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                           GoodsEntity commonGoodsEntity) {

        // 個別配送
        goodsRegistUpdateModel.setIndividualDeliveryType(commonGoodsEntity.getIndividualDeliveryType().getValue());
        // 無料配送
        goodsRegistUpdateModel.setFreeDeliveryFlag(commonGoodsEntity.getFreeDeliveryFlag().getValue());
    }

    /**
     * 公開状態設定項目をページに設定する<br/>
     *
     * @param goodsRegistUpdateModel ページクラス
     * @param goodsGroupEntity       商品グループEntity
     */
    private void setToPageOpenSettings(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                       GoodsGroupEntity goodsGroupEntity) {
        // 公開状態PC
        if (goodsGroupEntity.getGoodsOpenStatusPC() != null) {
            goodsRegistUpdateModel.setGoodsOpenStatusPC(goodsGroupEntity.getGoodsOpenStatusPC().getValue());
        }
        // 公開開始日PC
        if (goodsGroupEntity.getOpenStartTimePC() != null) {
            goodsRegistUpdateModel.setGoodsOpenStartDatePC(
                            conversionUtility.toYmd(goodsGroupEntity.getOpenStartTimePC()));
            goodsRegistUpdateModel.setGoodsOpenStartTimePC(
                            conversionUtility.toHms(goodsGroupEntity.getOpenStartTimePC()));
        }
        // 公開終了日PC
        if (goodsGroupEntity.getOpenEndTimePC() != null) {
            goodsRegistUpdateModel.setGoodsOpenEndDatePC(conversionUtility.toYmd(goodsGroupEntity.getOpenEndTimePC()));
            goodsRegistUpdateModel.setGoodsOpenEndTimePC(conversionUtility.toHms(goodsGroupEntity.getOpenEndTimePC()));
        }
    }

    /**
     * 入力情報をページにセット<br/>
     *
     * @param goodsRegistUpdateModel 商品管理：商品登録更新（商品基本設定）ページ
     */
    private void toPageForNextCommon(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroupEntity = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = goodsRegistUpdateModel.getCommonGoodsEntity();

        // 商品基本設定
        setToEntityBasicSettings(goodsGroupEntity, commonGoodsEntity, goodsRegistUpdateModel);
        // 外部連携設定
        setToEntityExternalCoordinationSettings(goodsGroupEntity, goodsRegistUpdateModel);

        // 2023-renew No21 from here
        // 除外フラグ設定
        setToEntityExcludingFlagSettings(goodsGroupEntity, goodsRegistUpdateModel);
        // 2023-renew No21 to here

        // 価格設定
        setToEntityPriceSettings(goodsGroupEntity, goodsRegistUpdateModel);
        // 配送設定
        setToEntityDeliverySettings(commonGoodsEntity, goodsRegistUpdateModel);
        // 公開状態設定
        setToEntityOpenSettings(goodsGroupEntity, goodsRegistUpdateModel);
    }

    /**
     * 商品基本設定項目をEntityにセットする<br/>
     *
     * @param goodsGroupEntity       商品グループEntity
     * @param commonGoodsEntity      共通商品Entity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityBasicSettings(GoodsGroupEntity goodsGroupEntity,
                                          GoodsEntity commonGoodsEntity,
                                          GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループコード
        goodsGroupEntity.setGoodsGroupCode(goodsRegistUpdateModel.getGoodsGroupCode());
        // 2023-renew No64 from here
        // 商品名（管理用）
        goodsGroupEntity.setGoodsGroupNameAdmin(goodsRegistUpdateModel.getGoodsGroupNameAdmin());
        if (goodsRegistUpdateModel.isRegist() && StringUtil.isEmpty(goodsGroupEntity.getGoodsGroupNameAdmin())) {
            goodsGroupEntity.setGoodsGroupNameAdmin(goodsRegistUpdateModel.getGoodsGroupNameAdmin());
        }
        // 2023-renew No64 to here
        // 商品名
        goodsGroupEntity.setGoodsGroupName(goodsRegistUpdateModel.getGoodsGroupName());
        if (goodsRegistUpdateModel.isRegist() && StringUtil.isEmpty(goodsGroupEntity.getGoodsGroupName())) {
            // 新規登録かつ未設定時は商品名を設定する
            // 商品グループ名PC
            goodsGroupEntity.setGoodsGroupName(goodsRegistUpdateModel.getGoodsGroupName());
            // 新規登録かつ未設定時は商品名を半角変換して設定する
        }
        // 新着日時
        goodsGroupEntity.setWhatsnewDate(conversionUtility.toTimeStamp(goodsRegistUpdateModel.getWhatsnewDate()));
        // 酒類フラグ
        goodsGroupEntity.setAlcoholFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeAlcoholFlag.class, goodsRegistUpdateModel.getAlcoholFlag()));
        // 値引きコメン
        goodsGroupEntity.setGoodsPreDiscountPrice(goodsRegistUpdateModel.getGoodsPreDiscountPrice());

        // PDR Migrate Customization from here
        // 商品区分
        goodsGroupEntity.setGoodsClassType(goodsRegistUpdateModel.getGoodsClassType());
        // カタログ表示順
        goodsGroupEntity.setCatalogDisplayOrder(goodsRegistUpdateModel.getCatalogDisplayOrder());
        // 歯科専売可否フラグ
        goodsGroupEntity.setDentalMonopolySalesFlg(goodsRegistUpdateModel.getDentalMonopolySalesFlg());
        // PDR Migrate Customization to here
    }

    /**
     * 外部連携設定項目をEntityにセットする<br/>
     *
     * @param goodsGroupEntity       商品グループEntity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityExternalCoordinationSettings(GoodsGroupEntity goodsGroupEntity,
                                                         GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // SNS連携フラグ
        goodsGroupEntity.setSnsLinkFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSnsLinkFlag.class, goodsRegistUpdateModel.getSnsLinkFlag()));
    }

    // 2023-renew No21 from here

    /**
     * 除外フラグ設定をEntityにセットする<br/>
     *
     * @param goodsGroupEntity       商品グループEntity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityExcludingFlagSettings(GoodsGroupEntity goodsGroupEntity,
                                                  GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 除外フラグ
        goodsGroupEntity.setExcludingFlag(goodsRegistUpdateModel.getExcludingFlag());
    }
    // 2023-renew No21 to here

    /**
     * 価格設定項目をEntityにセットする<br/>
     *
     * @param goodsGroupEntity       商品グループEntity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityPriceSettings(GoodsGroupEntity goodsGroupEntity,
                                          GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 税率
        goodsGroupEntity.setTaxRate(goodsRegistUpdateModel.getTaxRate());
    }

    /**
     * 配送設定項目をEntityにセットする<br/>
     *
     * @param commonGoodsEntity      共通商品Entity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityDeliverySettings(GoodsEntity commonGoodsEntity,
                                             GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 個別配送
        commonGoodsEntity.setIndividualDeliveryType(EnumTypeUtil.getEnumFromValue(HTypeIndividualDeliveryType.class,
                                                                                  goodsRegistUpdateModel.getIndividualDeliveryType()
                                                                                 ));
        // 無料配送
        commonGoodsEntity.setFreeDeliveryFlag(EnumTypeUtil.getEnumFromValue(HTypeFreeDeliveryFlag.class,
                                                                            goodsRegistUpdateModel.getFreeDeliveryFlag()
                                                                           ));
    }

    /**
     * 公開状態設定項目をEntityにセットする<br/>
     *
     * @param goodsGroupEntity       商品グループEntity
     * @param goodsRegistUpdateModel ページクラス
     */
    private void setToEntityOpenSettings(GoodsGroupEntity goodsGroupEntity,
                                         GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 公開状態
        goodsGroupEntity.setGoodsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                            goodsRegistUpdateModel.getGoodsOpenStatusPC()
                                                                           ));
        // 公開開始日時
        goodsGroupEntity.setOpenStartTimePC(
                        conversionUtility.toTimeStampWithDefaultHms(goodsRegistUpdateModel.getGoodsOpenStartDatePC(),
                                                                    goodsRegistUpdateModel.getGoodsOpenStartTimePC(),
                                                                    ConversionUtility.DEFAULT_START_TIME
                                                                   ));
        // 公開終了日時
        goodsGroupEntity.setOpenEndTimePC(
                        conversionUtility.toTimeStampWithDefaultHms(goodsRegistUpdateModel.getGoodsOpenEndDatePC(),
                                                                    goodsRegistUpdateModel.getGoodsOpenEndTimePC(),
                                                                    ConversionUtility.DEFAULT_END_TIME
                                                                   ));
    }

    /***************************************************************************************************************************
     ** カテゴリ設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * カテゴリ情報をページクラスにセット<br />
     *
     * @param categoryEntityList カテゴリエンティティリスト
     * @param categoryPage       ページ
     */
    public void toPageForLoadCategory(List<CategoryEntity> categoryEntityList, GoodsRegistUpdateModel categoryPage) {

        // 登録されているカテゴリリストの作成
        List<Integer> categorySeqList = new ArrayList<>();
        if (categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList() != null
            && categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList().size() > 0) {
            for (int i = 0; i < categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList().size(); i++) {
                categorySeqList.add(
                                categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList().get(i).getCategorySeq());
            }
        }

        // カテゴリ一覧の作成
        List<GoodsCategoryTreeItem> goodsCategoryTrees = new ArrayList<>();
        for (CategoryEntity category : categoryEntityList) {
            String categprySeqPath = category.getCategorySeqPath();
            int categoryLevel = 0;
            String parent = "#";
            while (categprySeqPath.length() >= 8) {
                Integer categorySeq = Integer.valueOf(categprySeqPath.substring(0, 8));
                categprySeqPath = categprySeqPath.substring(8);
                if (category != null && category.getCategoryName() != null && !"".equals(
                                category.getCategoryName().trim())) {
                    GoodsCategoryTreeItem goodsCategoryTreeItem =
                                    ApplicationContextUtility.getBean(GoodsCategoryTreeItem.class);
                    goodsCategoryTreeItem.setRegistCategoryCheck(categorySeqList.contains(category.getCategorySeq()));
                    goodsCategoryTreeItem.setCategorySeq(category.getCategorySeq());
                    goodsCategoryTreeItem.setId(String.valueOf(categorySeq));
                    goodsCategoryTreeItem.setParentNode(parent);
                    goodsCategoryTreeItem.setLabel(category.getCategoryName());
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
            categoryPage.setCategoryTrees(goodsCategoryTreeList);
            categoryPage.setCategoryTreesForRecovery(goodsCategoryTreeList);
        }
    }

    /**
     * 選択されたカテゴリSEQリストをカテゴリ商品登録情報リストに反映<br/>
     *
     * @param categoryPage 商品登録更新（カテゴリ設定）ページ
     */
    private void toPageForNextCategory(GoodsRegistUpdateModel categoryPage) {
        List<CategoryGoodsEntity> categoryGoodsEntityList = new ArrayList<>();
        categoryPage.getGoodsGroupDto().setCategoryGoodsEntityList(null);
        for (int i = 0; categoryPage.getCategoryTrees() != null && i < categoryPage.getCategoryTrees().size(); i++) {
            GoodsCategoryTreeItem categoryItem = categoryPage.getCategoryTrees().get(i);
            if (categoryItem.isRegistCategoryCheck()) {
                if (getCategoryGoodsEntity(categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList(),
                                           categoryItem.getCategorySeq()
                                          ) != null) {
                    // チェックのままカテゴリ
                    categoryGoodsEntityList.add(
                                    getCategoryGoodsEntity(categoryPage.getGoodsGroupDto().getCategoryGoodsEntityList(),
                                                           categoryItem.getCategorySeq()
                                                          ));
                } else {
                    // 新しくチェックされたカテゴリ
                    CategoryGoodsEntity categoryGoodsEntity =
                                    ApplicationContextUtility.getBean(CategoryGoodsEntity.class);
                    categoryGoodsEntity.setCategorySeq(categoryItem.getCategorySeq());
                    categoryGoodsEntity.setGoodsGroupSeq(
                                    categoryPage.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq());
                    categoryGoodsEntityList.add(categoryGoodsEntity);
                }
            }
        }
        categoryPage.getGoodsGroupDto().setCategoryGoodsEntityList(categoryGoodsEntityList);
    }

    /**
     * カテゴリ登録商品取得（存在チェックでも使用）
     *
     * @param categoryGoodsEntityList カテゴリ登録商品エンティティリスト
     * @param categorySeq             カテゴリSEQ
     * @return 存在チェックするカテゴリSEQ
     */
    private CategoryGoodsEntity getCategoryGoodsEntity(List<CategoryGoodsEntity> categoryGoodsEntityList,
                                                       Integer categorySeq) {
        if (categoryGoodsEntityList == null) {
            return null;
        }
        for (CategoryGoodsEntity categoryGoodsEntity : categoryGoodsEntityList) {
            if (categorySeq.equals(categoryGoodsEntity.getCategorySeq())) {
                return categoryGoodsEntity;
            }
        }
        return null;
    }

    /***************************************************************************************************************************
     ** 商品詳細設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param detailsModel ページ
     */
    private void toPageForLoadDetails(GoodsRegistUpdateModel detailsModel) {
        // 商品グループ画像登録更新用DTOリスト（ページ内編集用）の作成
        initTmpGoodsGroupImageRegistUpdateDtoList(detailsModel);
        // PDR Migrate Customization from here
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = detailsModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 2023-renew searchKeyword-addition from here
        // 検索キーワード
        detailsModel.setSearchKeyword(goodsGroupDisplayEntity.getSearchKeyword());
        // 2023-renew searchKeyword-addition to here
        // metaDescription
        detailsModel.setMetaDescription(goodsGroupDisplayEntity.getMetaDescription());
        // metaKeyword
        detailsModel.setMetaKeyword(goodsGroupDisplayEntity.getMetaKeyword());

        // SALEアイコンフラグ
        detailsModel.setSaleIconFlag(goodsGroupDisplayEntity.getSaleIconFlag());
        // お取りおきアイコンフラグ
        detailsModel.setReserveIconFlag(goodsGroupDisplayEntity.getReserveIconFlag());
        // NEWアイコンフラグ
        detailsModel.setNewIconFlag(goodsGroupDisplayEntity.getNewIconFlag());
        // 2023-renew No92 from here
        // アウトレットアイコンフラグ
        detailsModel.setOutletIconFlag(goodsGroupDisplayEntity.getOutletIconFlag());
        // 2023-renew No92 to here

        // SALEアイコン画像パス(プロパティから画像パスを指定)
        detailsModel.setImageFilePathSaleIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.sale"));

        // お取りおきアイコン画像パス(プロパティから画像パスを指定)
        detailsModel.setImageFilePathReserveIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.reserve"));

        // NEWアイコン画像パス(プロパティから画像パスを指定)
        detailsModel.setImageFilePathNewIcon(PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.new"));
        // 2023-renew No92 from here
        // アウトレットアイコン画像パス(プロパティから画像パスを指定)
        detailsModel.setImageFilePathOutletIcon(
                        PropertiesUtil.getSystemPropertiesValue("images.path.goodsgroupicon.outlet"));
        // 2023-renew No92 to here
        // PDR Migrate Customization to here
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param detailsModel ページ
     */
    private void toPageForNextDetails(GoodsRegistUpdateModel detailsModel) {

        // (注意) 画像ファイル名が固定でない場合は、商品グループ内ファイル名の重複チェックを行う！

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = detailsModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // metaDescription
        goodsGroupDisplayEntity.setMetaDescription(detailsModel.getMetaDescription());
        // metaKeyword
        goodsGroupDisplayEntity.setMetaKeyword(detailsModel.getMetaKeyword());

        // 商品グループ画像登録更新用DTOリスト（ページ内編集用）をセッションにセット
        setTmpGoodsGroupImageRegistUpdateDtoList(detailsModel);
    }

    /***************************************************************************************************************************
     ** 商品詳細テキスト設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForLoadDetailsText(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity =
                        goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 商品説明01
        goodsRegistUpdateModel.setGoodsNote1(goodsGroupDisplayEntity.getGoodsNote1());
        //2023-renew No64 from here
        // 商品説明01公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote1OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote1OpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote1OpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote1OpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote1OpenStartTime()));
        }
        // 商品説明01Sub
        goodsRegistUpdateModel.setGoodsNote1Sub(goodsGroupDisplayEntity.getGoodsNote1Sub());
        // 商品説明01Sub公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote1SubOpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote1SubOpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime()));
        }
        // 商品説明02
        goodsRegistUpdateModel.setGoodsNote2(goodsGroupDisplayEntity.getGoodsNote2());
        // 商品説明02公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote2OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote2OpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote2OpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote2OpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote2OpenStartTime()));
        }
        // 商品説明02Sub
        goodsRegistUpdateModel.setGoodsNote2Sub(goodsGroupDisplayEntity.getGoodsNote2Sub());
        // 商品説明02Sub公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote2SubOpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote2SubOpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime()));
        }
        // 商品説明03
        goodsRegistUpdateModel.setGoodsNote3(goodsGroupDisplayEntity.getGoodsNote3());
        // 商品説明04
        goodsRegistUpdateModel.setGoodsNote4(goodsGroupDisplayEntity.getGoodsNote4());
        // 商品説明04公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote4OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote4OpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote4OpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote4OpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote4OpenStartTime()));
        }
        // 商品説明04Sub
        goodsRegistUpdateModel.setGoodsNote4Sub(goodsGroupDisplayEntity.getGoodsNote4Sub());
        // 商品説明04Sub公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote4SubOpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote4SubOpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime()));
        }
        // 商品説明05
        goodsRegistUpdateModel.setGoodsNote5(goodsGroupDisplayEntity.getGoodsNote5());
        // 商品説明06
        goodsRegistUpdateModel.setGoodsNote6(goodsGroupDisplayEntity.getGoodsNote6());
        // 商品説明07
        goodsRegistUpdateModel.setGoodsNote7(goodsGroupDisplayEntity.getGoodsNote7());
        // 商品説明08
        goodsRegistUpdateModel.setGoodsNote8(goodsGroupDisplayEntity.getGoodsNote8());
        // 商品説明09
        goodsRegistUpdateModel.setGoodsNote9(goodsGroupDisplayEntity.getGoodsNote9());
        // 商品説明10
        goodsRegistUpdateModel.setGoodsNote10(goodsGroupDisplayEntity.getGoodsNote10());
        // 商品説明10公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote10OpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote10OpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote10OpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote10OpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote10OpenStartTime()));
        }
        // 商品説明10Sub
        goodsRegistUpdateModel.setGoodsNote10Sub(goodsGroupDisplayEntity.getGoodsNote10Sub());
        // 商品説明10Sub公開開始日時
        if (goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime() != null) {
            goodsRegistUpdateModel.setGoodsNote10SubOpenStartTimeDate(
                            conversionUtility.toYmd(goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime()));
            goodsRegistUpdateModel.setGoodsNote10SubOpenStartTimeTime(
                            conversionUtility.toHms(goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime()));
        }
        //2023-renew No64 to here

        // 商品説明11
        goodsRegistUpdateModel.setGoodsNote11(goodsGroupDisplayEntity.getGoodsNote11());
        // 商品説明12
        goodsRegistUpdateModel.setGoodsNote12(goodsGroupDisplayEntity.getGoodsNote12());
        // 商品説明13
        goodsRegistUpdateModel.setGoodsNote13(goodsGroupDisplayEntity.getGoodsNote13());
        // 商品説明14
        goodsRegistUpdateModel.setGoodsNote14(goodsGroupDisplayEntity.getGoodsNote14());
        // 商品説明15
        goodsRegistUpdateModel.setGoodsNote15(goodsGroupDisplayEntity.getGoodsNote15());
        // 商品説明16
        goodsRegistUpdateModel.setGoodsNote16(goodsGroupDisplayEntity.getGoodsNote16());
        // 商品説明17
        goodsRegistUpdateModel.setGoodsNote17(goodsGroupDisplayEntity.getGoodsNote17());
        // 商品説明18
        goodsRegistUpdateModel.setGoodsNote18(goodsGroupDisplayEntity.getGoodsNote18());
        // 商品説明19
        goodsRegistUpdateModel.setGoodsNote19(goodsGroupDisplayEntity.getGoodsNote19());
        // 商品説明20
        goodsRegistUpdateModel.setGoodsNote20(goodsGroupDisplayEntity.getGoodsNote20());
        // 2023-renew No11 from here
        // 商品説明21
        goodsRegistUpdateModel.setGoodsNote21(goodsGroupDisplayEntity.getGoodsNote21());
        // 2023-renew No11 to here
        // 2023-renew No0 from here
        // 商品説明22
        goodsRegistUpdateModel.setGoodsNote22(goodsGroupDisplayEntity.getGoodsNote22());
        // 2023-renew No0 to here

        // 受注連携設定01
        goodsRegistUpdateModel.setOrderSetting1(goodsGroupDisplayEntity.getOrderSetting1());
        // 受注連携設定02
        goodsRegistUpdateModel.setOrderSetting2(goodsGroupDisplayEntity.getOrderSetting2());
        // 受注連携設定03
        goodsRegistUpdateModel.setOrderSetting3(goodsGroupDisplayEntity.getOrderSetting3());
        // 受注連携設定04
        goodsRegistUpdateModel.setOrderSetting4(goodsGroupDisplayEntity.getOrderSetting4());
        // 受注連携設定05
        goodsRegistUpdateModel.setOrderSetting5(goodsGroupDisplayEntity.getOrderSetting5());
        // 受注連携設定06
        goodsRegistUpdateModel.setOrderSetting6(goodsGroupDisplayEntity.getOrderSetting6());
        // 受注連携設定07
        goodsRegistUpdateModel.setOrderSetting7(goodsGroupDisplayEntity.getOrderSetting7());
        // 受注連携設定08
        goodsRegistUpdateModel.setOrderSetting8(goodsGroupDisplayEntity.getOrderSetting8());
        // 受注連携設定09
        goodsRegistUpdateModel.setOrderSetting9(goodsGroupDisplayEntity.getOrderSetting9());
        // 受注連携設定10
        goodsRegistUpdateModel.setOrderSetting10(goodsGroupDisplayEntity.getOrderSetting10());

        // 商品納期
        goodsRegistUpdateModel.setDeliveryType(goodsGroupDisplayEntity.getDeliveryType());

        // PDR Migrate Customization from here
        GoodsGroupEntity goodsGroupEntity = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();

        // シリーズ価格記号表示フラグ
        goodsRegistUpdateModel.setGroupPriceMarkDispFlag(goodsGroupEntity.getGroupPriceMarkDispFlag());
        // シリーズセール価格記号表示フラグ
        goodsRegistUpdateModel.setGroupSalePriceMarkDispFlag(goodsGroupEntity.getGroupSalePriceMarkDispFlag());
        // PDR Migrate Customization to here
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForNextDetailsText(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity =
                        goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 入力項目をセット
        // 商品説明01
        goodsGroupDisplayEntity.setGoodsNote1(goodsRegistUpdateModel.getGoodsNote1());
        //2023-renew No64 from here
        // 商品説明01公開開始日時
        goodsGroupDisplayEntity.setGoodsNote1OpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote1OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote1OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                      ));
        // 商品説明01Sub
        goodsGroupDisplayEntity.setGoodsNote1Sub(goodsRegistUpdateModel.getGoodsNote1Sub());
        // 商品説明01Sub公開開始日時
        goodsGroupDisplayEntity.setGoodsNote1SubOpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote1SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote1SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                         ));
        //2023-renew No64 to here
        // 商品説明02
        goodsGroupDisplayEntity.setGoodsNote2(goodsRegistUpdateModel.getGoodsNote2());
        //2023-renew No64 from here
        // 商品説明02公開開始日時
        goodsGroupDisplayEntity.setGoodsNote2OpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote2OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote2OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                      ));
        // 商品説明02Sub
        goodsGroupDisplayEntity.setGoodsNote2Sub(goodsRegistUpdateModel.getGoodsNote2Sub());
        // 商品説明02Sub公開開始日時
        goodsGroupDisplayEntity.setGoodsNote2SubOpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote2SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote2SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                         ));
        //2023-renew No64 to here
        // 商品説明03
        goodsGroupDisplayEntity.setGoodsNote3(goodsRegistUpdateModel.getGoodsNote3());
        // 商品説明04
        goodsGroupDisplayEntity.setGoodsNote4(goodsRegistUpdateModel.getGoodsNote4());
        //2023-renew No64 from here
        // 商品説明04公開開始日時
        goodsGroupDisplayEntity.setGoodsNote4OpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote4OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote4OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                      ));
        // 商品説明04Sub
        goodsGroupDisplayEntity.setGoodsNote4Sub(goodsRegistUpdateModel.getGoodsNote4Sub());
        // 商品説明04Sub公開開始日時
        goodsGroupDisplayEntity.setGoodsNote4SubOpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote4SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote4SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                         ));
        //2023-renew No64 to here
        // 商品説明05
        goodsGroupDisplayEntity.setGoodsNote5(goodsRegistUpdateModel.getGoodsNote5());
        // 商品説明06
        goodsGroupDisplayEntity.setGoodsNote6(goodsRegistUpdateModel.getGoodsNote6());
        // 商品説明07
        goodsGroupDisplayEntity.setGoodsNote7(goodsRegistUpdateModel.getGoodsNote7());
        // 商品説明08
        goodsGroupDisplayEntity.setGoodsNote8(goodsRegistUpdateModel.getGoodsNote8());
        // 商品説明09
        goodsGroupDisplayEntity.setGoodsNote9(goodsRegistUpdateModel.getGoodsNote9());
        // 商品説明10
        goodsGroupDisplayEntity.setGoodsNote10(goodsRegistUpdateModel.getGoodsNote10());
        //2023-renew No64 from here
        // 商品説明10公開開始日時
        goodsGroupDisplayEntity.setGoodsNote10OpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote10OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote10OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME
                                                                                                       ));
        // 商品説明10Sub
        goodsGroupDisplayEntity.setGoodsNote10Sub(goodsRegistUpdateModel.getGoodsNote10Sub());
        // 商品説明10Sub公開開始日時
        goodsGroupDisplayEntity.setGoodsNote10SubOpenStartTime(conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote10SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote10SubOpenStartTimeTime(),
                        ConversionUtility.DEFAULT_START_TIME
                                                                                                          ));
        //2023-renew No64 to here
        // 商品説明11
        goodsGroupDisplayEntity.setGoodsNote11(goodsRegistUpdateModel.getGoodsNote11());
        // 商品説明12
        goodsGroupDisplayEntity.setGoodsNote12(goodsRegistUpdateModel.getGoodsNote12());
        // 商品説明13
        goodsGroupDisplayEntity.setGoodsNote13(goodsRegistUpdateModel.getGoodsNote13());
        // 商品説明14
        goodsGroupDisplayEntity.setGoodsNote14(goodsRegistUpdateModel.getGoodsNote14());
        // 商品説明15
        goodsGroupDisplayEntity.setGoodsNote15(goodsRegistUpdateModel.getGoodsNote15());
        // 商品説明16
        goodsGroupDisplayEntity.setGoodsNote16(goodsRegistUpdateModel.getGoodsNote16());
        // 商品説明17
        goodsGroupDisplayEntity.setGoodsNote17(goodsRegistUpdateModel.getGoodsNote17());
        // 商品説明18
        goodsGroupDisplayEntity.setGoodsNote18(goodsRegistUpdateModel.getGoodsNote18());
        // 商品説明19
        goodsGroupDisplayEntity.setGoodsNote19(goodsRegistUpdateModel.getGoodsNote19());
        // 商品説明20
        goodsGroupDisplayEntity.setGoodsNote20(goodsRegistUpdateModel.getGoodsNote20());
        // 商品説明21
        goodsGroupDisplayEntity.setGoodsNote21(goodsRegistUpdateModel.getGoodsNote21());
        // 2023-renew No0 from here
        // 商品説明22
        goodsGroupDisplayEntity.setGoodsNote22(goodsRegistUpdateModel.getGoodsNote22());
        // 2023-renew No0 to here

        // 受注連携設定01
        goodsGroupDisplayEntity.setOrderSetting1(goodsRegistUpdateModel.getOrderSetting1());
        // 受注連携設定02
        goodsGroupDisplayEntity.setOrderSetting2(goodsRegistUpdateModel.getOrderSetting2());
        // 受注連携設定03
        goodsGroupDisplayEntity.setOrderSetting3(goodsRegistUpdateModel.getOrderSetting3());
        // 受注連携設定04
        goodsGroupDisplayEntity.setOrderSetting4(goodsRegistUpdateModel.getOrderSetting4());
        // 受注連携設定05
        goodsGroupDisplayEntity.setOrderSetting5(goodsRegistUpdateModel.getOrderSetting5());
        // 受注連携設定06
        goodsGroupDisplayEntity.setOrderSetting6(goodsRegistUpdateModel.getOrderSetting6());
        // 受注連携設定07
        goodsGroupDisplayEntity.setOrderSetting7(goodsRegistUpdateModel.getOrderSetting7());
        // 受注連携設定08
        goodsGroupDisplayEntity.setOrderSetting8(goodsRegistUpdateModel.getOrderSetting8());
        // 受注連携設定09
        goodsGroupDisplayEntity.setOrderSetting9(goodsRegistUpdateModel.getOrderSetting9());
        // 受注連携設定10
        goodsGroupDisplayEntity.setOrderSetting10(goodsRegistUpdateModel.getOrderSetting10());

        // 商品納期
        goodsGroupDisplayEntity.setDeliveryType(goodsRegistUpdateModel.getDeliveryType());
    }

    /***************************************************************************************************************************
     ** 規格設定
     ***************************************************************************************************************************/

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param unitPage ページ
     */
    public void toPageForLoadUnit(GoodsRegistUpdateModel unitPage) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = unitPage.getGoodsGroupDto().getGoodsGroupEntity();
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = unitPage.getGoodsGroupDto().getGoodsGroupDisplayEntity();

        // 規格管理フラグ
        if (goodsGroupDisplayEntity.getUnitManagementFlag() != null) {
            unitPage.setUnitManagementFlag(goodsGroupDisplayEntity.getUnitManagementFlag().getValue());
        }
        // 規格１表示名
        unitPage.setUnitTitle1(goodsGroupDisplayEntity.getUnitTitle1());
        // 規格２表示名
        unitPage.setUnitTitle2(goodsGroupDisplayEntity.getUnitTitle2());
        // 値引きコメント
        unitPage.setGoodsPreDiscountPrice(goodsGroup.getGoodsPreDiscountPrice());

        // 商品規格情報リストのセット
        toPageGoodsDataForLoad(unitPage);
        // 商品規格が0件の場合は1件追加する
        if (unitPage.getUnitCount() == 0) {
            toPageForAddGoods(unitPage);
            toPageGoodsDataForLoad(unitPage);
        }
    }

    /**
     * 商品規格情報をページにセット<br/>
     *
     * @param unitPage ページ
     */
    private void toPageGoodsDataForLoad(GoodsRegistUpdateModel unitPage) {

        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = unitPage.getGoodsGroupDto().getGoodsDtoList();

        int index = 0;
        List<GoodsRegistUpdateUnitItem> unitItemList = new ArrayList<>();
        List<GoodsRegistUpdateStockItem> stockItemList = new ArrayList<>();
        List<GoodsRegistUpdateUnitImage> unitImageList = new ArrayList<>();
        if (goodsDtoList != null && goodsDtoList.size() > 0) {
            for (GoodsDto goodsDto : goodsDtoList) {
                if (HTypeGoodsSaleStatus.DELETED.equals(goodsDto.getGoodsEntity().getSaleStatusPC())) {
                    // ステータス削除の場合は飛ばす
                    continue;
                }
                int indexUpdate = ++index;
                GoodsRegistUpdateUnitItem unitItem = createUnitItem(goodsDto);
                unitItem.setUnitDspNo(indexUpdate);
                unitItemList.add(unitItem);

                GoodsRegistUpdateStockItem stockItem = createStockItem(goodsDto);
                stockItem.setStockDspNo(indexUpdate);
                stockItemList.add(stockItem);

                if (goodsDto.getGoodsEntity().getGoodsCode() != null && !"1".equals(unitPage.getRedirectRecycle())) {
                    GoodsRegistUpdateUnitImage unitImage = getUnitImage(unitPage.getUnitImagesItems(),
                                                                        goodsDto.getGoodsEntity().getGoodsCode()
                                                                       );
                    unitImage.setImageDspNo(indexUpdate);
                    unitImageList.add(unitImage);
                } else {
                    GoodsRegistUpdateUnitImage unitImage = new GoodsRegistUpdateUnitImage();
                    unitImage.setImageDspNo(indexUpdate);
                    unitImageList.add(unitImage);
                    goodsDto.setUnitImage(new GoodsImageEntity());
                }
            }
            unitPage.setUnitItems(unitItemList);
            unitPage.setUnitCount(unitItemList.size());
            unitPage.setStockItems(stockItemList);
            if (CollectionUtil.isNotEmpty(unitImageList)) {
                unitPage.setUnitImagesItems(unitImageList);
            }
        }
    }

    /**
     * 商品規格画像リストから規格画像を取得する
     *
     * @param unitImageList 商品規格画像リスト
     * @param goodsCode     商品コード
     * @return 規格画像
     */
    private GoodsRegistUpdateUnitImage getUnitImage(List<GoodsRegistUpdateUnitImage> unitImageList, String goodsCode) {
        if (CollectionUtil.isEmpty(unitImageList)) {
            return new GoodsRegistUpdateUnitImage();
        }
        for (GoodsRegistUpdateUnitImage unitImage : unitImageList) {
            if (goodsCode.equals(unitImage.getGoodsCode())) {
                return unitImage;
            }
        }
        return new GoodsRegistUpdateUnitImage();
    }

    /**
     * 商品Dtoから規格Itemを作成する<br/>
     *
     * @param goodsDto 商品Dto
     * @return UnitPageUnitItem
     */
    private GoodsRegistUpdateUnitItem createUnitItem(GoodsDto goodsDto) {
        GoodsRegistUpdateUnitItem unitItem = ApplicationContextUtility.getBean(GoodsRegistUpdateUnitItem.class);
        unitItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
        unitItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
        unitItem.setOrderDisplay(goodsDto.getGoodsEntity().getOrderDisplay());
        unitItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
        unitItem.setCatalogCode(goodsDto.getGoodsEntity().getCatalogCode());
        unitItem.setGoodsPrice(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPrice()));
        // 2023-renew addNo5 from here
        unitItem.setGoodsPriceInTaxLow(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPriceInTaxLow()));
        unitItem.setGoodsPriceInTaxHight(
                        conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsPriceInTaxHight()));
        // 2023-renew addNo5 to here
        unitItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
        unitItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
        unitItem.setPreDiscountPrice(conversionUtility.toString(goodsDto.getGoodsEntity().getPreDiscountPrice()));
        // 2023-renew addNo5 from here
        unitItem.setPreDiscountPriceLow(conversionUtility.toString(goodsDto.getGoodsEntity().getPreDiscountPriceLow()));
        unitItem.setPreDiscountPriceHight(
                        conversionUtility.toString(goodsDto.getGoodsEntity().getPreDiscountPriceHight()));
        // 2023-renew addNo5 to here
        if (goodsDto.getGoodsEntity().getSaleStatusPC() != null) {
            unitItem.setGoodsSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC().getValue());
        }
        if (goodsDto.getGoodsEntity().getSaleStartTimePC() != null) {
            unitItem.setSaleStartDatePC(conversionUtility.toYmd(goodsDto.getGoodsEntity().getSaleStartTimePC()));
            unitItem.setSaleStartTimePC(conversionUtility.toHms(goodsDto.getGoodsEntity().getSaleStartTimePC()));
        }
        if (goodsDto.getGoodsEntity().getSaleEndTimePC() != null) {
            unitItem.setSaleEndDatePC(conversionUtility.toYmd(goodsDto.getGoodsEntity().getSaleEndTimePC()));
            unitItem.setSaleEndTimePC(conversionUtility.toHms(goodsDto.getGoodsEntity().getSaleEndTimePC()));
        }
        unitItem.setPurchasedMax(conversionUtility.toString(goodsDto.getGoodsEntity().getPurchasedMax()));

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

        return unitItem;
    }

    /**
     * 商品Dtoから商品在庫を設定する<br/>
     *
     * @param goodsDto 商品Dto
     * @return 商品在庫設定ページ情報
     */
    private GoodsRegistUpdateStockItem createStockItem(GoodsDto goodsDto) {
        GoodsRegistUpdateStockItem stockItem = ApplicationContextUtility.getBean(GoodsRegistUpdateStockItem.class);
        stockItem.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
        stockItem.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
        stockItem.setJanCode(goodsDto.getGoodsEntity().getJanCode());
        stockItem.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
        stockItem.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
        stockItem.setPreDiscountPrice(goodsDto.getGoodsEntity().getPreDiscountPrice());
        if (goodsDto.getGoodsEntity().getSaleStatusPC() != null) {
            stockItem.setGoodsSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC().getValue());
        }
        if (goodsDto.getStockDto().getSupplementCount() != null) {
            stockItem.setSupplementCount(String.valueOf(goodsDto.getStockDto().getSupplementCount()));
        }
        if (goodsDto.getStockDto().getSafetyStock() != null) {
            stockItem.setSafetyStock(String.valueOf(goodsDto.getStockDto().getSafetyStock()));
        }
        if (goodsDto.getStockDto().getRemainderFewStock() != null) {
            stockItem.setRemainderFewStock(String.valueOf(goodsDto.getStockDto().getRemainderFewStock()));
        }
        if (goodsDto.getStockDto().getOrderPointStock() != null) {
            stockItem.setOrderPointStock(String.valueOf(goodsDto.getStockDto().getOrderPointStock()));
        }
        return stockItem;
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param unitPage ページ
     */
    private void toPageForNextUnit(GoodsRegistUpdateModel unitPage) {

        // 商品グループエンティティ
        GoodsGroupEntity goodsGroup = unitPage.getGoodsGroupDto().getGoodsGroupEntity();
        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = unitPage.getGoodsGroupDto().getGoodsGroupDisplayEntity();
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = unitPage.getCommonGoodsEntity();

        // 規格管理フラグ
        goodsGroupDisplayEntity.setUnitManagementFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class, unitPage.getUnitManagementFlag()));
        // 共通商品Dtoに規格管理フラグをセット
        commonGoodsEntity.setUnitManagementFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class, unitPage.getUnitManagementFlag()));
        if (HTypeUnitManagementFlag.ON.getValue().equals(unitPage.getUnitManagementFlag())) {
            // 規格１表示名
            goodsGroupDisplayEntity.setUnitTitle1(unitPage.getUnitTitle1());
            // 規格２表示名
            goodsGroupDisplayEntity.setUnitTitle2(unitPage.getUnitTitle2());
        } else {
            // 規格１表示名
            goodsGroupDisplayEntity.setUnitTitle1(null);
            // 規格２表示名
            goodsGroupDisplayEntity.setUnitTitle2(null);
        }

        // 値引きコメント
        goodsGroup.setGoodsPreDiscountPrice(unitPage.getGoodsPreDiscountPrice());

        // 商品規格情報をページに反映
        toPageForGoodsInfoList(unitPage, false);
    }

    /**
     * 入力情報をページに反映(同一ページ内遷移)<br/>
     *
     * @param unitPage ページ
     */
    public void toPageForSame(GoodsRegistUpdateModel unitPage) {

        // 商品グループ表示エンティティ
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = unitPage.getGoodsGroupDto().getGoodsGroupDisplayEntity();
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = unitPage.getCommonGoodsEntity();

        // 規格管理フラグ
        goodsGroupDisplayEntity.setUnitManagementFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class, unitPage.getUnitManagementFlag()));
        // 共通商品Dtoに規格管理フラグをセット
        commonGoodsEntity.setUnitManagementFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class, unitPage.getUnitManagementFlag()));
        // 規格１表示名
        goodsGroupDisplayEntity.setUnitTitle1(unitPage.getUnitTitle1());
        // 規格２表示名
        goodsGroupDisplayEntity.setUnitTitle2(unitPage.getUnitTitle2());

        // 商品規格情報をページに反映
        toPageForGoodsInfoList(unitPage, true);
    }

    /**
     * 商品規格情報追加<br/>
     *
     * @param unitPage ページ
     */
    public void toPageForAddGoods(GoodsRegistUpdateModel unitPage) {
        // 商品DTO生成してリストにセット
        GoodsDto goodsDto = ApplicationContextUtility.getBean(GoodsDto.class);
        goodsDto.setGoodsEntity(ApplicationContextUtility.getBean(GoodsEntity.class));

        // 規格画像追加
        goodsDto.setUnitImage(ApplicationContextUtility.getBean(GoodsImageEntity.class));

        // 商品DTO共通情報をセット
        toGoodsDtoFromCommonGoods(unitPage, goodsDto.getGoodsEntity());
        goodsDto.getGoodsEntity()
                .setGoodsGroupSeq(unitPage.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq());
        goodsDto.getGoodsEntity().setShopSeq(unitPage.getGoodsGroupDto().getGoodsGroupEntity().getShopSeq());

        // 在庫DTOを生成
        goodsDto.setStockDto(ApplicationContextUtility.getBean(StockDto.class));
        goodsDto.getStockDto().setShopSeq(unitPage.getGoodsGroupDto().getGoodsGroupEntity().getShopSeq());
        goodsDto.getStockDto().setRealStock(new BigDecimal(0));
        goodsDto.getStockDto().setRemainderFewStock(new BigDecimal(0));
        goodsDto.getStockDto().setOrderPointStock(new BigDecimal(0));
        goodsDto.getStockDto().setSafetyStock(new BigDecimal(0));

        goodsDto.getGoodsEntity().setPurchasedMax(new BigDecimal(999));

        // 表示順を設定
        // 商品規格リスト最後尾の表示順＋１をセット
        if (unitPage.getGoodsGroupDto().getGoodsDtoList() == null) {
            unitPage.getGoodsGroupDto().setGoodsDtoList(new ArrayList<>());
        }
        if (unitPage.getUnitItems() != null && unitPage.getUnitItems().size() > 0) {
            GoodsRegistUpdateUnitItem lastUnitItem = unitPage.getUnitItems().get(unitPage.getUnitItems().size() - 1);
            goodsDto.getGoodsEntity().setOrderDisplay(lastUnitItem.getOrderDisplay().intValue() + 1);
        } else {
            goodsDto.getGoodsEntity().setOrderDisplay(1);
        }

        unitPage.getGoodsGroupDto().getGoodsDtoList().add(goodsDto);
    }

    /**
     * 商品規格情報削除<br/>
     *
     * @param unitPage ページ
     */
    public void toPageForDeleteGoods(GoodsRegistUpdateModel unitPage) {
        // 画面から選択商品コードを取得する
        Integer selectDspNo = unitPage.getSelectDspNo();

        // 該当する規格情報を取得する
        GoodsDto goodsDto = null;
        int count = 0;
        for (GoodsDto tmpGoodsDto : unitPage.getGoodsGroupDto().getGoodsDtoList()) {
            if (HTypeGoodsSaleStatus.DELETED.equals(tmpGoodsDto.getGoodsEntity().getSaleStatusPC())) {
                // ステータス削除の場合は飛ばす
                continue;
            } else {
                count++;
            }
            if (selectDspNo != null && selectDspNo.intValue() == count) {
                goodsDto = tmpGoodsDto;
            }
        }
        if (goodsDto == null) {
            return;
        }

        // 商品SEQがnullでない場合（＝既にDB登録済み）、販売状態＝"削除"をセットする
        if (goodsDto.getGoodsEntity().getGoodsSeq() != null) {
            goodsDto.getGoodsEntity().setSaleStatusPC(HTypeGoodsSaleStatus.DELETED);
        }

        // 上記以外の場合（DB未登録）、セッションの商品DTOリストから削除する
        else {
            unitPage.getGoodsGroupDto().getGoodsDtoList().remove(goodsDto);
        }
    }

    /**
     * 商品表示順変更(上)<br/>
     *
     * @param unitPage ページ
     */
    public void toPageForUpGoods(GoodsRegistUpdateModel unitPage) {
        orderDisplayChangeUnit(unitPage, true);
    }

    /**
     * 商品表示順変更(下)<br/>
     *
     * @param unitPage ページ
     */
    public void toPageForDownGoods(GoodsRegistUpdateModel unitPage) {
        orderDisplayChangeUnit(unitPage, false);
    }

    /**
     * 商品表示順変更<br/>
     *
     * @param unitPage ページ
     * @param upFlg    true: 上へ移動 false: 下へ移動
     */
    private void orderDisplayChangeUnit(GoodsRegistUpdateModel unitPage, boolean upFlg) {
        // 画面から選択商品コードを取得する
        if (unitPage.getSelectDspNo() == null) {
            return;
        }
        int selectDspNo1 = unitPage.getSelectDspNo().intValue();

        // 該当する規格情報を取得する
        GoodsDto goodsDto1 = null;
        int count = 0;
        for (GoodsDto tmpGoodsDto : unitPage.getGoodsGroupDto().getGoodsDtoList()) {
            if (HTypeGoodsSaleStatus.DELETED == tmpGoodsDto.getGoodsEntity().getSaleStatusPC()) {
                // ステータス削除の場合は飛ばす
                continue;
            } else {
                count++;
            }
            if (selectDspNo1 == count) {
                goodsDto1 = tmpGoodsDto;
            }
        }

        // 移動先表示順規格情報を取得する
        int selectDspNo2 = 0;
        if (upFlg) {
            // 上へ移動の場合
            selectDspNo2 = selectDspNo1 - 1;
        } else {
            // 下へ移動の場合
            selectDspNo2 = selectDspNo1 + 1;
        }
        GoodsDto goodsDto2 = null;
        count = 0;
        for (GoodsDto tmpGoodsDto : unitPage.getGoodsGroupDto().getGoodsDtoList()) {
            if (HTypeGoodsSaleStatus.DELETED.equals(tmpGoodsDto.getGoodsEntity().getSaleStatusPC())) {
                // ステータス削除の場合は飛ばす
                continue;
            } else {
                count++;
            }
            if (selectDspNo2 == count) {
                goodsDto2 = tmpGoodsDto;
            }
        }
        if (goodsDto1 == null || goodsDto2 == null) {
            // 1件目規格を上へなどの場合にもここに来る
            return;
        }

        // 規格表示順を入れ替える
        Integer tmpOrderDisplay = goodsDto1.getGoodsEntity().getOrderDisplay().intValue();
        goodsDto1.getGoodsEntity().setOrderDisplay(goodsDto2.getGoodsEntity().getOrderDisplay());
        goodsDto2.getGoodsEntity().setOrderDisplay(tmpOrderDisplay);
        // リスト内の順番を入れ替える
        int index2 = unitPage.getGoodsGroupDto().getGoodsDtoList().indexOf(goodsDto2);
        unitPage.getGoodsGroupDto()
                .getGoodsDtoList()
                .set(unitPage.getGoodsGroupDto().getGoodsDtoList().indexOf(goodsDto1), goodsDto2);
        unitPage.getGoodsGroupDto().getGoodsDtoList().set(index2, goodsDto1);
    }

    /**
     * 商品規格情報のページ反映<br/>
     *
     * @param unitPage      ページ
     * @param toSamePageFlg 同一ページ内遷移フラグ
     */
    private void toPageForGoodsInfoList(GoodsRegistUpdateModel unitPage, boolean toSamePageFlg) {
        if (unitPage.getUnitItems() != null) {
            for (GoodsRegistUpdateUnitItem unitPageItem : unitPage.getUnitItems()) {
                GoodsDto goodsDto = null;
                // 該当する規格情報を取得する
                int count = 0;
                for (GoodsDto tmpGoodsDto : unitPage.getGoodsGroupDto().getGoodsDtoList()) {
                    if (HTypeGoodsSaleStatus.DELETED.equals(tmpGoodsDto.getGoodsEntity().getSaleStatusPC())) {
                        // ステータス削除の場合は飛ばす
                        continue;
                    } else {
                        count++;
                    }
                    if (unitPageItem.getUnitDspNo() != null && unitPageItem.getUnitDspNo().intValue() == count) {
                        goodsDto = tmpGoodsDto;
                    }
                }
                if (goodsDto == null) {
                    return;
                }
                boolean unitValueSetFlag = toSamePageFlg || HTypeUnitManagementFlag.ON.getValue()
                                                                                      .equals(unitPage.getUnitManagementFlag());
                setToGoodsDto(goodsDto, unitPageItem, unitValueSetFlag);
            }
        }
    }

    /**
     * ページ入力値を商品Dtoにセットする<br/>
     *
     * @param goodsDto         商品Dto
     * @param unitPageItem     規格Item
     * @param unitValueSetFlag 規格値セットフラグ
     */
    private void setToGoodsDto(GoodsDto goodsDto, GoodsRegistUpdateUnitItem unitPageItem, boolean unitValueSetFlag) {
        goodsDto.getGoodsEntity().setGoodsCode(unitPageItem.getGoodsCode());
        goodsDto.getGoodsEntity().setOrderDisplay(unitPageItem.getOrderDisplay());
        goodsDto.getGoodsEntity().setGoodsPrice(conversionUtility.toBigDecimal(unitPageItem.getGoodsPrice()));
        // 2023-renew addNo5 from here
        goodsDto.getGoodsEntity()
                .setGoodsPriceInTaxLow(conversionUtility.toBigDecimal(unitPageItem.getGoodsPriceInTaxLow()));
        goodsDto.getGoodsEntity()
                .setGoodsPriceInTaxHight(conversionUtility.toBigDecimal(unitPageItem.getGoodsPriceInTaxHight()));
        // 2023-renew addNo5 to here
        goodsDto.getGoodsEntity().setJanCode(unitPageItem.getJanCode());
        goodsDto.getGoodsEntity().setCatalogCode(unitPageItem.getCatalogCode());
        if (unitValueSetFlag) {
            goodsDto.getGoodsEntity().setUnitValue1(unitPageItem.getUnitValue1());
            goodsDto.getGoodsEntity().setUnitValue2(unitPageItem.getUnitValue2());
        } else {
            goodsDto.getGoodsEntity().setUnitValue1(null);
            goodsDto.getGoodsEntity().setUnitValue2(null);
        }
        goodsDto.getGoodsEntity()
                .setPreDiscountPrice(conversionUtility.toBigDecimal(unitPageItem.getPreDiscountPrice()));
        // 2023-renew addNo5 from here
        goodsDto.getGoodsEntity()
                .setPreDiscountPriceLow(conversionUtility.toBigDecimal(unitPageItem.getPreDiscountPriceLow()));
        goodsDto.getGoodsEntity()
                .setPreDiscountPriceHight(conversionUtility.toBigDecimal(unitPageItem.getPreDiscountPriceHight()));
        // 2023-renew addNo5 to here
        goodsDto.getGoodsEntity()
                .setSaleStatusPC(EnumTypeUtil.getEnumFromValue(HTypeGoodsSaleStatus.class,
                                                               unitPageItem.getGoodsSaleStatusPC()
                                                              ));
        // 販売開始日時
        goodsDto.getGoodsEntity()
                .setSaleStartTimePC(conversionUtility.toTimeStampWithDefaultHms(unitPageItem.getSaleStartDatePC(),
                                                                                unitPageItem.getSaleStartTimePC(),
                                                                                ConversionUtility.DEFAULT_START_TIME
                                                                               ));
        // 販売終了日時
        goodsDto.getGoodsEntity()
                .setSaleEndTimePC(conversionUtility.toTimeStampWithDefaultHms(unitPageItem.getSaleEndDatePC(),
                                                                              unitPageItem.getSaleEndTimePC(),
                                                                              ConversionUtility.DEFAULT_END_TIME
                                                                             ));
        goodsDto.getGoodsEntity().setPurchasedMax(conversionUtility.toBigDecimal(unitPageItem.getPurchasedMax()));
        // PDR Migrate Customization from here
        goodsDto.getGoodsEntity().setGoodsManagementCode(unitPageItem.getGoodsManagementCode());
        goodsDto.getGoodsEntity().setGoodsDivisionCode(unitPageItem.getGoodsDivisionCode());
        goodsDto.getGoodsEntity().setGoodsCategory1(unitPageItem.getGoodsCategory1());
        goodsDto.getGoodsEntity().setGoodsCategory2(unitPageItem.getGoodsCategory2());
        goodsDto.getGoodsEntity().setGoodsCategory3(unitPageItem.getGoodsCategory3());
        goodsDto.getGoodsEntity().setLandSendFlag(unitPageItem.getLandSendFlag());
        goodsDto.getGoodsEntity().setCoolSendFlag(unitPageItem.getCoolSendFlag());
        goodsDto.getGoodsEntity().setCoolSendFrom(unitPageItem.getCoolSendFrom());
        goodsDto.getGoodsEntity().setCoolSendTo(unitPageItem.getCoolSendTo());
        goodsDto.getGoodsEntity().setReserveFlag(unitPageItem.getReserveFlag());
        goodsDto.getGoodsEntity().setUnit(unitPageItem.getUnit());
        goodsDto.getGoodsEntity().setPriceMarkDispFlag(unitPageItem.getPriceMarkDispFlag());
        goodsDto.getGoodsEntity().setSalePriceMarkDispFlag(unitPageItem.getSalePriceMarkDispFlag());
        goodsDto.getGoodsEntity().setEmotionPriceType(unitPageItem.getEmotionPriceType());
        // PDR Migrate Customization to here
    }

    /***************************************************************************************************************************
     ** 商品画像設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForLoadImage(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // 商品グループ画像登録更新用DTOリスト（ページ内編集用）の作成
        initTmpGoodsGroupImageRegistUpdateDtoList(goodsRegistUpdateModel);

        // 商品画像に関する設定
        setDetailsImageInfo(goodsRegistUpdateModel);
    }

    /**
     * 商品画像情報設定<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void setDetailsImageInfo(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // 商品グループ詳細画像アイテムリスト作成
        createGroupDetailsImageItems(goodsRegistUpdateModel);

    }

    /**
     * 商品グループ詳細画像アイテム作成<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void createGroupDetailsImageItems(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        String maxCount = PropertiesUtil.getSystemPropertiesValue("goodsimage.max.count");

        // 設定ファイルに指定数分ループ
        List<GoodsRegistUpdateImageItem> items = new ArrayList<>();
        for (Integer i = 1; i <= Integer.parseInt(maxCount); i++) {
            GoodsRegistUpdateImageItem item = ApplicationContextUtility.getBean(GoodsRegistUpdateImageItem.class);

            item.setImageNo(i);

            // 詳細画像をセット
            item.setImagePath(getGoodsImageFilepath(goodsRegistUpdateModel, i));
            items.add(item);
        }
        goodsRegistUpdateModel.setGoodsImageItems(items);
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForNextImage(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // 商品画像登録更新用DTOリスト（ページ内編集用）をセッションにセット
        setTmpGoodsGroupImageRegistUpdateDtoList(goodsRegistUpdateModel);
    }

    /***************************************************************************************************************************
     ** 在庫設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForLoadStock(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品グループエンティティ
        GoodsGroupEntity goodsGroupEntity = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity();
        // 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList();
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = goodsRegistUpdateModel.getCommonGoodsEntity();

        // 2023-renew No64 from here
        // 商品名（管理用）
        goodsRegistUpdateModel.setGoodsGroupNameAdmin(goodsGroupEntity.getGoodsGroupNameAdmin());
        // 2023-renew No64 to here
        // 商品名
        goodsRegistUpdateModel.setGoodsGroupName(goodsGroupEntity.getGoodsGroupName());
        // 公開状態PC
        if (goodsGroupEntity.getGoodsOpenStatusPC() != null) {
            goodsRegistUpdateModel.setGoodsOpenStatusPC(goodsGroupEntity.getGoodsOpenStatusPC().getValue());
        }
        // 在庫管理フラグ
        if (commonGoodsEntity.getStockManagementFlag() != null) {
            goodsRegistUpdateModel.setStockManagementFlag(commonGoodsEntity.getStockManagementFlag().getValue());
        }

        // 商品規格情報リストのセット
        int index = 0;
        List<GoodsRegistUpdateStockItem> stockItemList = new ArrayList<>();
        if (goodsDtoList != null && goodsDtoList.size() > 0) {
            for (GoodsDto goodsDto : goodsDtoList) {
                if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()) {
                    // ステータス削除の場合は飛ばす
                    continue;
                }
                GoodsRegistUpdateStockItem stockItem =
                                ApplicationContextUtility.getBean(GoodsRegistUpdateStockItem.class);
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

                    // 変換Helper取得
                    ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

                    stockItem.setRemainderFewStock(
                                    conversionUtility.toString(goodsDto.getStockDto().getRemainderFewStock()));
                    stockItem.setOrderPointStock(
                                    conversionUtility.toString(goodsDto.getStockDto().getOrderPointStock()));
                    stockItem.setSafetyStock(conversionUtility.toString(goodsDto.getStockDto().getSafetyStock()));
                    stockItem.setRealStock(goodsDto.getStockDto().getRealStock());
                }
                stockItemList.add(stockItem);
            }
            goodsRegistUpdateModel.setStockItems(stockItemList);
        }
        goodsRegistUpdateModel.setUnitCount(stockItemList.size());

    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void toPageForNextStock(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 共通商品エンティティ
        GoodsEntity commonGoodsEntity = goodsRegistUpdateModel.getCommonGoodsEntity();

        // 入力項目をセット
        // 在庫管理フラグ
        commonGoodsEntity.setStockManagementFlag(EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                               goodsRegistUpdateModel.getStockManagementFlag()
                                                                              ));

        // 商品在庫情報をページに反映
        toPageForStockInfoList(goodsRegistUpdateModel);
    }

    /**
     * 商品在庫情報のページ反映<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void toPageForStockInfoList(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (goodsRegistUpdateModel.getStockItems() != null) {
            for (GoodsRegistUpdateStockItem stockPageItem : goodsRegistUpdateModel.getStockItems()) {
                GoodsDto goodsDto = null;
                // 該当する規格情報を取得する
                int count = 0;
                for (GoodsDto tmpGoodsDto : goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList()) {
                    if (HTypeGoodsSaleStatus.DELETED == tmpGoodsDto.getGoodsEntity().getSaleStatusPC()) {
                        // ステータス削除の場合は飛ばす
                        continue;
                    } else {
                        count++;
                    }
                    if (stockPageItem.getStockDspNo().intValue() == count) {
                        goodsDto = tmpGoodsDto;
                    }
                }
                if (goodsDto == null) {
                    return;
                }

                if (HTypeStockManagementFlag.ON == EnumTypeUtil.getEnumFromValue(
                                HTypeStockManagementFlag.class, goodsRegistUpdateModel.getStockManagementFlag())) {

                    // 変換Helper取得
                    ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

                    if (!StringUtils.isEmpty(stockPageItem.getSupplementCount())) {
                        goodsDto.getStockDto()
                                .setSupplementCount(conversionUtility.toBigDecimal(stockPageItem.getSupplementCount()));
                    }
                    goodsDto.getStockDto()
                            .setRemainderFewStock(conversionUtility.toBigDecimal(stockPageItem.getRemainderFewStock()));
                    goodsDto.getStockDto()
                            .setOrderPointStock(conversionUtility.toBigDecimal(stockPageItem.getOrderPointStock()));
                    goodsDto.getStockDto()
                            .setSafetyStock(conversionUtility.toBigDecimal(stockPageItem.getSafetyStock()));
                } else {
                    // 在庫管理なしの時は、残少表示在庫数・発注点在庫数・安全在庫数に0をセット
                    goodsDto.getStockDto().setSupplementCount(new BigDecimal(0));
                    goodsDto.getStockDto().setRemainderFewStock(new BigDecimal(0));
                    goodsDto.getStockDto().setOrderPointStock(new BigDecimal(0));
                    goodsDto.getStockDto().setSafetyStock(new BigDecimal(0));
                }
            }
        }
    }

    /***************************************************************************************************************************
     ** 関連商品設定
     ***************************************************************************************************************************/
    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForLoadRelation(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (goodsRegistUpdateModel.getTmpGoodsRelationEntityList() == null
            && goodsRegistUpdateModel.getRedirectGoodsRelationEntityList() != null) {
            goodsRegistUpdateModel.setTmpGoodsRelationEntityList(
                            goodsRegistUpdateModel.getRedirectGoodsRelationEntityList());
        }

        List<GoodsRelationEntity> tmpGoodsRelationEntityList;
        if (goodsRegistUpdateModel.getTmpGoodsRelationEntityList() == null
            && goodsRegistUpdateModel.getGoodsRelationEntityList() != null) {
            // 関連商品エンティティリストをセッションからページへディープコピーする
            tmpGoodsRelationEntityList = new ArrayList<>(goodsRegistUpdateModel.getGoodsRelationEntityList());
            goodsRegistUpdateModel.setTmpGoodsRelationEntityList(tmpGoodsRelationEntityList);
        } else {
            // 同一ページ内遷移時はページ情報を取得
            tmpGoodsRelationEntityList = goodsRegistUpdateModel.getTmpGoodsRelationEntityList();
        }
        if (tmpGoodsRelationEntityList == null) {
            tmpGoodsRelationEntityList = new ArrayList<>();
            goodsRegistUpdateModel.setTmpGoodsRelationEntityList(new ArrayList<>());
        }

        // 関連商品情報リストのセット
        int index = 0;
        List<GoodsRegistUpdateRelationItem> relationItems = new ArrayList<>();
        for (GoodsRelationEntity goodsRelationEntity : tmpGoodsRelationEntityList) {
            GoodsRegistUpdateRelationItem relationItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateRelationItem.class);
            relationItem.setRelationDspNo(++index);
            relationItem.setRelationGoodsGroupCode(goodsRelationEntity.getGoodsGroupCode());
            // 2023-renew No64 from here
            relationItem.setRelationGoodsGroupName(goodsRelationEntity.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here
            relationItems.add(relationItem);
        }
        goodsRegistUpdateModel.setRelationItems(relationItems);

        // ダミー用関連商品（空）を作成する
        List<GoodsRegistUpdateRelationItem> dummyRelationItems = new ArrayList<>();
        for (int i = 0; i < goodsRegistUpdateModel.getGoodsRelationAmount() - relationItems.size(); i++) {
            GoodsRegistUpdateRelationItem relationItem =
                            ApplicationContextUtility.getBean(GoodsRegistUpdateRelationItem.class);
            relationItem.setRelationDspNo(0);
            relationItem.setRelationGoodsGroupCode("");
            relationItem.setRelationGoodsGroupName("");
            dummyRelationItems.add(relationItem);
        }
        goodsRegistUpdateModel.setRelationNoItems(dummyRelationItems);
    }

    // 2023-renew No21 from here

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel 商品管理：商品登録更新ページ
     */
    public void toPageForLoadTogetherBuy(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList() == null
            && goodsRegistUpdateModel.getRedirectGoodsTogetherBuyGroupEntityList() != null) {
            goodsRegistUpdateModel.setTmpGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateModel.getRedirectGoodsTogetherBuyGroupEntityList());
        }

        List<GoodsTogetherBuyGroupEntity> tmpGoodsTogetherBuyGroupEntityList;
        if (goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList() == null
            && goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList() != null) {
            // よく一緒に購入される商品エンティティリストをセッションからページへディープコピーする
            tmpGoodsTogetherBuyGroupEntityList =
                            new ArrayList<>(goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateModel.setTmpGoodsTogetherBuyGroupEntityList(tmpGoodsTogetherBuyGroupEntityList);
        } else {
            // 同一ページ内遷移時はページ情報を取得
            tmpGoodsTogetherBuyGroupEntityList = goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList();
        }
        if (tmpGoodsTogetherBuyGroupEntityList == null) {
            tmpGoodsTogetherBuyGroupEntityList = new ArrayList<>();
            goodsRegistUpdateModel.setTmpGoodsTogetherBuyGroupEntityList(new ArrayList<>());
        }

        // よく一緒に購入される商品情報リストのセット
        List<GoodsRegistUpdateTogetherBuyItem> goodsRegistUpdateTogetherBuyItems = new ArrayList<>();
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : tmpGoodsTogetherBuyGroupEntityList) {
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
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForLoadUnitImage(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        int dspNo = 1;
        if (goodsRegistUpdateModel.getGoodsGroupDto() == null
            || goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList() == null || "1".equals(
                        goodsRegistUpdateModel.getRedirectRecycle())) {
            toPageForLoadUnitImageInit(goodsRegistUpdateModel);
        } else {
            List<GoodsRegistUpdateUnitImage> unitImageList = new ArrayList<>();
            for (GoodsDto goodsDto : goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList()) {
                GoodsRegistUpdateUnitImage unitImage = new GoodsRegistUpdateUnitImage();
                unitImage.setImageDspNo(dspNo);
                unitImage.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
                if (goodsDto.getUnitImage() != null) {
                    unitImage.setUnitImageName(goodsDto.getUnitImage().getImageFileName());
                    if (goodsDto.getUnitImage().getImageFileName() != null) {
                        unitImage.setUrlImagePath(PropertiesUtil.getSystemPropertiesValue("images.path.goods") + "/"
                                                  + goodsDto.getUnitImage().getImageFileName());
                    }
                }
                // 2023-renew No76 from here
                unitImage.setUnitImageFlag(goodsDto.getGoodsEntity().getUnitImageFlag());
                // 2023-renew No76 to here
                unitImageList.add(unitImage);
                dspNo++;
            }
            goodsRegistUpdateModel.setUnitImagesItems(unitImageList);
        }
    }

    /**
     * 画面表示・再表示<br/>
     * 初期表示情報をページクラスにセット<br />
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForLoadUnitImageInit(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        GoodsRegistUpdateUnitImage unitImage = new GoodsRegistUpdateUnitImage();
        unitImage.setImageDspNo(1);
        List<GoodsRegistUpdateUnitImage> unitImageList = new ArrayList<>();
        unitImageList.add(unitImage);
        goodsRegistUpdateModel.setUnitImagesItems(unitImageList);
    }

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForNextRelation(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (goodsRegistUpdateModel.getTmpGoodsRelationEntityList() != null) {
            // tmp関連商品エンティティリストをセッションにセット
            goodsRegistUpdateModel.setGoodsRelationEntityList(goodsRegistUpdateModel.getTmpGoodsRelationEntityList());
        }
    }

    /**
     * 関連商品情報削除<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void doDeleteRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 画面から選択商品コードを取得する
        String goodsGroupCode = goodsRegistUpdateModel.getSelectRelationGoodsGroupCode();

        if (goodsGroupCode == null || goodsRegistUpdateModel.getTmpGoodsRelationEntityList() == null) {
            return;
        }

        // 該当する関連商品情報を取得する
        GoodsRelationEntity goodsRelationEntity = null;
        for (GoodsRelationEntity tmpGoodsRelationEntity : goodsRegistUpdateModel.getTmpGoodsRelationEntityList()) {
            if (goodsGroupCode.equals(tmpGoodsRelationEntity.getGoodsGroupCode())) {
                goodsRelationEntity = tmpGoodsRelationEntity;
                break;
            }
        }
        if (goodsRelationEntity == null) {
            return;
        }

        // tmp関連商品リストから削除する
        goodsRegistUpdateModel.getTmpGoodsRelationEntityList().remove(goodsRelationEntity);

        // 表示順を再設定する
        int orderIndex = 0;
        for (GoodsRelationEntity tmpGoodsRelationEntity : goodsRegistUpdateModel.getTmpGoodsRelationEntityList()) {
            tmpGoodsRelationEntity.setOrderDisplay(++orderIndex);
        }
    }

    /**
     * 関連商品表示順変更(上)<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForUpRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        orderDisplayChangeRelation(goodsRegistUpdateModel, true);
    }

    /**
     * 関連商品表示順変更(下)<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForDownRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        orderDisplayChangeRelation(goodsRegistUpdateModel, false);
    }

    /**
     * 商品表示順変更<br/>
     *
     * @param goodsRegistUpdateModel ページ
     * @param upFlg                  true: 上へ移動 false: 下へ移動
     */
    private void orderDisplayChangeRelation(GoodsRegistUpdateModel goodsRegistUpdateModel, boolean upFlg) {
        // 画面から選択商品コードを取得する
        if (goodsRegistUpdateModel.getSelectRelationGoodsGroupCode() == null) {
            return;
        }
        String goodsGroupCode = goodsRegistUpdateModel.getSelectRelationGoodsGroupCode();

        // 該当する関連商品情報を取得する
        GoodsRelationEntity goodsRelationEntity1 = null;
        for (GoodsRelationEntity tmpGoodsRelationEntity : goodsRegistUpdateModel.getTmpGoodsRelationEntityList()) {
            if (goodsGroupCode.equals(tmpGoodsRelationEntity.getGoodsGroupCode())) {
                goodsRelationEntity1 = tmpGoodsRelationEntity;
                break;
            }
        }

        // 移動先表示順商品情報を取得する
        GoodsRelationEntity goodsRelationEntity2 = null;
        if (upFlg && goodsRegistUpdateModel.getTmpGoodsRelationEntityList().indexOf(goodsRelationEntity1) > 0) {
            // 上へ移動の場合
            int targetIndex = goodsRegistUpdateModel.getTmpGoodsRelationEntityList().indexOf(goodsRelationEntity1) - 1;
            goodsRelationEntity2 = goodsRegistUpdateModel.getTmpGoodsRelationEntityList().get(targetIndex);
        } else if (!upFlg && goodsRegistUpdateModel.getTmpGoodsRelationEntityList().indexOf(goodsRelationEntity1)
                             < goodsRegistUpdateModel.getTmpGoodsRelationEntityList().size() - 1) {
            // 下へ移動の場合
            int targetIndex = goodsRegistUpdateModel.getTmpGoodsRelationEntityList().indexOf(goodsRelationEntity1) + 1;
            goodsRelationEntity2 = goodsRegistUpdateModel.getTmpGoodsRelationEntityList().get(targetIndex);
        }
        if (goodsRelationEntity1 == null || goodsRelationEntity2 == null) {
            // 1件目を上へなどの場合にもここへくる
            return;
        }

        // 表示順を入れ替える
        Integer tmpOrderDisplay = goodsRelationEntity1.getOrderDisplay().intValue();
        goodsRelationEntity1.setOrderDisplay(goodsRelationEntity2.getOrderDisplay());
        goodsRelationEntity2.setOrderDisplay(tmpOrderDisplay);
        // リスト内の順番を入れ替える
        int index2 = goodsRegistUpdateModel.getTmpGoodsRelationEntityList().indexOf(goodsRelationEntity2);
        goodsRegistUpdateModel.getTmpGoodsRelationEntityList()
                              .set(goodsRegistUpdateModel.getTmpGoodsRelationEntityList()
                                                         .indexOf(goodsRelationEntity1), goodsRelationEntity2);
        goodsRegistUpdateModel.getTmpGoodsRelationEntityList().set(index2, goodsRelationEntity1);
    }

    // 2023-renew No21 from here

    /**
     * 入力情報をページに反映<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    private void toPageForNextTogetherBuy(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList() != null) {
            // tmp関連商品エンティティリストをセッションにセット
            goodsRegistUpdateModel.setGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList());
        }
    }

    /**
     * よく一緒に購入される商品情報削除<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void doDeleteGoodsTogetherGroup(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 画面から選択商品コードを取得する
        String goodsGroupCode = goodsRegistUpdateModel.getSelectGoodsTogetherBuyGroupCode();

        if (goodsGroupCode == null || goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList() == null) {
            return;
        }

        // 該当するよく一緒に購入される商品情報を取得する
        GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity = null;
        for (GoodsTogetherBuyGroupEntity tmpGoodsTogetherBuyGroupEntity : goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()) {
            if (goodsGroupCode.equals(tmpGoodsTogetherBuyGroupEntity.getGoodsGroupCode())) {
                goodsTogetherBuyGroupEntity = tmpGoodsTogetherBuyGroupEntity;
                break;
            }
        }
        if (goodsTogetherBuyGroupEntity == null) {
            return;
        }

        // tmp商品リストから削除する
        goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().remove(goodsTogetherBuyGroupEntity);

        // 表示順を再設定する
        int orderIndex = 0;
        for (GoodsTogetherBuyGroupEntity tmpGoodsTogetherBuyGroupEntity : goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()) {
            if (HTypeRegisterMethodType.BACK.equals(tmpGoodsTogetherBuyGroupEntity.getRegistMethod()))
                tmpGoodsTogetherBuyGroupEntity.setOrderDisplay(++orderIndex);
        }
    }

    /**
     * よく一緒に購入される商品表示順変更(上)<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForUpGoodsTogetherBuyGroup(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        orderDisplayChangeGoodsTogetherBuyGroup(goodsRegistUpdateModel, true);
    }

    /**
     * よく一緒に購入される商品表示順変更(下)<br/>
     *
     * @param goodsRegistUpdateModel ページ
     */
    public void toPageForDownGoodsTogetherBuyGroup(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        orderDisplayChangeGoodsTogetherBuyGroup(goodsRegistUpdateModel, false);
    }

    /**
     * よく一緒に購入される商品表示順変更<br/>
     *
     * @param goodsRegistUpdateModel ページ
     * @param upFlg                  true: 上へ移動 false: 下へ移動
     */
    private void orderDisplayChangeGoodsTogetherBuyGroup(GoodsRegistUpdateModel goodsRegistUpdateModel, boolean upFlg) {
        // 画面から選択商品コードを取得する
        if (goodsRegistUpdateModel.getSelectGoodsTogetherBuyGroupCode() == null) {
            return;
        }
        String goodsGroupCode = goodsRegistUpdateModel.getSelectGoodsTogetherBuyGroupCode();

        // 該当するよく一緒に購入される商品情報を取得する
        GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity1 = null;
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()) {
            if (goodsGroupCode.equals(goodsTogetherBuyGroupEntity.getGoodsGroupCode())) {
                goodsTogetherBuyGroupEntity1 = goodsTogetherBuyGroupEntity;
                break;
            }
        }

        // 移動先表示順商品情報を取得する
        GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity2 = null;
        if (upFlg
            && goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().indexOf(goodsTogetherBuyGroupEntity1)
               > 0) {
            // 上へ移動の場合
            int targetIndex = goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                    .indexOf(goodsTogetherBuyGroupEntity1) - 1;
            goodsTogetherBuyGroupEntity2 =
                            goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().get(targetIndex);
        } else if (!upFlg &&
                   goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().indexOf(goodsTogetherBuyGroupEntity1)
                   < goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().size() - 1) {
            // 下へ移動の場合
            int targetIndex = goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                    .indexOf(goodsTogetherBuyGroupEntity1) + 1;
            if (goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                                      .get(targetIndex)
                                      .getRegistMethod()
                                      .equals(HTypeRegisterMethodType.BACK)) {
                goodsTogetherBuyGroupEntity2 =
                                goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().get(targetIndex);
            }
        }
        if (goodsTogetherBuyGroupEntity1 == null || goodsTogetherBuyGroupEntity2 == null) {
            // 1件目を上へなどの場合にもここへくる
            return;
        }

        // 表示順を入れ替える
        Integer tmpOrderDisplay = goodsTogetherBuyGroupEntity1.getOrderDisplay();
        goodsTogetherBuyGroupEntity1.setOrderDisplay(goodsTogetherBuyGroupEntity2.getOrderDisplay());
        goodsTogetherBuyGroupEntity2.setOrderDisplay(tmpOrderDisplay);
        // リスト内の順番を入れ替える
        int index2 = goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                                           .indexOf(goodsTogetherBuyGroupEntity2);
        goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                              .set(goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList()
                                                         .indexOf(goodsTogetherBuyGroupEntity1),
                                   goodsTogetherBuyGroupEntity2
                                  );
        goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList().set(index2, goodsTogetherBuyGroupEntity1);
    }
    // 2023-renew No21 to here

    /**
     * 商品グループ詳細画像アイテムリストの最後の画像のインデックスを取得
     *
     * @param imageItems
     * @return 最後の画像のインデックス
     */
    public int getLastImageIndex(List<GoodsRegistUpdateImageItem> imageItems) {
        int index = 1;
        for (GoodsRegistUpdateImageItem item : imageItems) {
            if (!item.isExistImage()) {
                return index;
            }
            index++;
        }
        return index;
    }

    public void updateImagesPosition(GoodsRegistUpdateModel model) {
        List<GoodsGroupImageEntity> goodsGroupImageListForUpdate = new ArrayList<>();
        GoodsGroupEntity entity =
                        goodsGroupDao.getGoodsGroupByCode(model.getCommonInfo().getCommonInfoBase().getShopSeq(),
                                                          model.getGoodsGroupCode(), null, null, null
                                                         );
        List<GoodsGroupImageEntity> masterGoodsGroupImageList =
                        goodsGroupImageDao.getGoodsGroupImageListByGoodsGroupSeq(entity.getGoodsGroupSeq());
        List<GoodsRegistUpdateImageItem> imageItems = model.getGoodsImageItems();
        for (GoodsGroupImageEntity goodsGroupImageEntity : masterGoodsGroupImageList) {
            for (GoodsRegistUpdateImageItem item : imageItems) {
                if (goodsGroupImageEntity.getImageTypeVersionNo().equals(item.getImageNo()) && item.isExistImage()) {
                    String imagePathDetail3ImagePcGroup = item.getImagePath();
                    String tmpImageDirPath = PropertiesUtil.getSystemPropertiesValue("tmp.path");
                    String goodsGroupImagePath = PropertiesUtil.getSystemPropertiesValue("images.path.goods");
                    String tmpImageFileName;
                    String imageFileName;
                    if (imagePathDetail3ImagePcGroup.contains(tmpImageDirPath)) {
                        tmpImageFileName = imagePathDetail3ImagePcGroup.replaceAll(tmpImageDirPath + "/", "");
                        String tmpImageFileNameDateTime =
                                        tmpImageFileName.split("_")[tmpImageFileName.split("_").length - 1].split(
                                                        "\\.")[0];
                        imageFileName = tmpImageFileName.replaceAll("_" + tmpImageFileNameDateTime, "");
                        String imageFileNameWithGoodsCd = model.getGoodsGroupCode() + "/" + imageFileName;
                        if (!imageFileNameWithGoodsCd.equals(goodsGroupImageEntity.getImageFileName())) {
                            goodsGroupImageEntity.setImageFileName(imageFileNameWithGoodsCd);
                            goodsGroupImageListForUpdate.add(goodsGroupImageEntity);
                        }
                    } else {
                        imageFileName = imagePathDetail3ImagePcGroup.replaceAll(goodsGroupImagePath + "/", "");
                        if (!imageFileName.equals(goodsGroupImageEntity.getImageFileName())) {
                            goodsGroupImageEntity.setImageFileName(imageFileName);
                            goodsGroupImageListForUpdate.add(goodsGroupImageEntity);
                        }
                    }
                    break;
                }
            }
        }
        if (goodsGroupImageListForUpdate.size() > 0) {
            // 画像の順番を更新
            for (GoodsGroupImageEntity updateEntity : goodsGroupImageListForUpdate) {
                goodsGroupImageDao.update(updateEntity);
            }
        }
    }

}
