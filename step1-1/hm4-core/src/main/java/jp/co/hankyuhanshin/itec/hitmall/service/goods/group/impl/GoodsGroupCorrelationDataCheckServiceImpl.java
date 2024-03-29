/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsCsvUpLoadAsynchronousService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupCorrelationDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * 商品グループDto相関チェックサービス<br/>
 */
@Service
public class GoodsGroupCorrelationDataCheckServiceImpl extends AbstractShopService
                implements GoodsGroupCorrelationDataCheckService {

    /**
     * 商品-最小金額<br/>
     */
    public static final BigDecimal GOODS_PRICE_MIN = BigDecimal.ZERO;

    /**
     * 商品-最大金額<br/>
     */
    public static final BigDecimal GOODS_PRICE_MAX = new BigDecimal(99999999);

    // 2023-renew No64 from here
    /**
     * エラーテキストラベル<br/>
     */
    public static final List<String> ERROR_TEXT_LABEL = Arrays.asList("商品名",
                                                                      "商品概要",
                                                                      "特徴",
                                                                      "注意事項",
                                                                      "シリーズPRコメント",
                                                                      "商品表示名PC１",
                                                                      "商品表示名PC１_公開開始日時",
                                                                      "商品表示名PC２",
                                                                      "商品表示名PC２_公開開始日時",
                                                                      "商品概要１_公開開始日時",
                                                                      "商品概要２_公開開始日時",
                                                                      "特徴１_公開開始日時",
                                                                      "特徴２_公開開始日時",
                                                                      "注意事項１_公開開始日時",
                                                                      "注意事項２_公開開始日時",
                                                                      "シリーズPRコメント１_公開開始日時",
                                                                      "シリーズPRコメント２_公開開始日時");
    // 2023-renew No64 to here

    /**
     * 商品Utility
     */
    private final GoodsUtility goodsUtility;

    /**
     * 税率情報取得
     */
    private final TaxGetLogic taxGetLogic;

    @Autowired
    public GoodsGroupCorrelationDataCheckServiceImpl(GoodsUtility goodsUtility, TaxGetLogic taxGetLogic) {

        this.goodsUtility = goodsUtility;
        this.taxGetLogic = taxGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報
     */
    @Override
    public void execute(GoodsGroupDto goodsGroupDto,
                        List<GoodsRelationEntity> goodsRelationEntityList,
                        // 2023-renew No21 from here
                        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                        // 2023-renew No21 to here
                        int processType,
                        Map<String, Object> commonCsvInfoMap) {

        /************************************
         **  入力データ有無チェック
         ************************************/
        initInputDataCheck(goodsGroupDto, processType, commonCsvInfoMap);

        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();

        /************************************
         **  キー項目チェック
         ************************************/
        String goodsGroupCode = checkKeyData(processType, commonCsvInfoMap, goodsGroupEntity);

        /************************************
         **  登録カテゴリチェック
         ************************************/
        checkCategoryGoods(goodsGroupDto, processType, commonCsvInfoMap, goodsGroupCode);

        /************************************
         **  関連商品情報チェック
         ************************************/
        checkGoodsRelation(goodsRelationEntityList, processType, commonCsvInfoMap, goodsGroupEntity, goodsGroupCode);

        // 2023-renew No21 from here
        /************************************
         **  よく一緒に購入される商品情報チェック
         ************************************/
        checkGoodsTogetherBuyGroup(goodsTogetherBuyGroupEntityList, processType, commonCsvInfoMap, goodsGroupEntity, goodsGroupCode);
        // 2023-renew No21 to here

        /************************************
         **  商品グループ情報チェック
         ************************************/
        checkGoodsGroup(processType, commonCsvInfoMap, goodsGroupEntity, goodsGroupCode);

        /************************************
         **  商品グループ表示情報チェック
         ************************************/
        checkGoodsGroupDisplay(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);

        /************************************
         **  商品規格情報チェック
         ************************************/
        checkGoodsDetail(processType, commonCsvInfoMap, goodsGroupEntity, goodsDtoList, goodsGroupDisplayEntity,
                         goodsGroupCode
                        );

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * 入力データ有無チェック<br/>
     *
     * @param goodsGroupDto    商品グループDTO
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param customParams     案件用引数
     */
    protected void initInputDataCheck(GoodsGroupDto goodsGroupDto,
                                      int processType,
                                      Map<String, Object> commonCsvInfoMap,
                                      Object... customParams) {
        // 商品グループDtoなし
        if (goodsGroupDto == null) {
            throwMessage(processType, commonCsvInfoMap, null, null, "SGP001001");
        }
        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();
        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();
        // CSVからの場合は、商品規格をCSV処理行順にソートして処理する
        if (processType == PROCESS_TYPE_FROM_CSV) {
            goodsDtoList = sortCsvRecordOrder(commonCsvInfoMap, goodsGroupDto.getGoodsDtoList());
        }
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
        // 商品グループエンティティなし、商品グループ表示エンティティなし
        if (goodsGroupEntity == null || goodsGroupDisplayEntity == null) {
            throwMessage(processType, commonCsvInfoMap, null, null, "SGP001001");
        }
        // 商品Dtoリストなし
        if (goodsDtoList == null || goodsDtoList.size() == 0) {
            throwMessage(processType, commonCsvInfoMap, null, null, "SGP001002");
        }

        // 2023-renew No64 from here
        // 商品表示名 のチェック start
        // 項目設定NULL で 公開日入力あり はNG
        if (Objects.isNull(goodsGroupEntity.getGoodsGroupName1()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(5)});
        }
        if (Objects.isNull(goodsGroupEntity.getGoodsGroupName2()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(7)});
        }

        // 項目設定あり で 公開日NULL はNG
        if (!Objects.isNull(goodsGroupEntity.getGoodsGroupName1()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(6)});
        }
        if (!Objects.isNull(goodsGroupEntity.getGoodsGroupName2()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(8)});
        }

        // 項目設定、公開日全てNULL はNG
        if (Objects.isNull(goodsGroupEntity.getGoodsGroupName1()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName2()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "SGP001005E", null);
        }

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 商品表示名PC１_公開開始日時・商品表示名PC２_公開開始日時ともに 未来日 or NULLのみ の場合はNG
        if (!Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime()) &&
            dateUtility.isAfterCurrentTime(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            dateUtility.isAfterCurrentTime(goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-003-E", null);
        }

        if (!Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName1()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName2()) &&
            dateUtility.isAfterCurrentTime(goodsGroupEntity.getGoodsGroupName1OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-004-E", new Object[] {ERROR_TEXT_LABEL.get(5)});
        }

        if (Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            Objects.isNull(goodsGroupEntity.getGoodsGroupName1()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName2()) &&
            dateUtility.isAfterCurrentTime(goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-004-E", new Object[] {ERROR_TEXT_LABEL.get(7)});
        }
        // 商品表示名 のチェック end

        // 商品概要／特徴／注意事項／シリーズPRコメント のチェック start
        // 項目設定あり で 公開日NULL はNG
        // 商品概要
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(9)});
        }
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1Sub()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(10)});
        }

        // 特徴
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(11)});
        }
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2Sub()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(12)});
        }

        // 注意事項
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(13)});
        }
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4Sub()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(14)});
        }

        // シリーズPRコメント
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(15)});
        }
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10Sub()) &&
            Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-002-E", new Object[] {ERROR_TEXT_LABEL.get(16)});
        }
        // 商品概要／特徴／注意事項／シリーズPRコメント のチェック end

        // 重複データを確認してください
        // 商品名: 商品表示名PC１_公開開始日時 = 商品表示名PC２_公開開始日時
        if (!Objects.isNull(goodsGroupEntity.getGoodsGroupName1OpenStartTime()) &&
            !Objects.isNull(goodsGroupEntity.getGoodsGroupName2OpenStartTime()) &&
            goodsGroupEntity.getGoodsGroupName1OpenStartTime().equals(
                            goodsGroupEntity.getGoodsGroupName2OpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-001-E", new Object[] {ERROR_TEXT_LABEL.get(0)});
        }

        // 商品概要: 商品概要１_公開開始日時 = 商品概要２_公開開始日時
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1OpenStartTime()) &&
            !Objects.isNull(goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime()) &&
            goodsGroupDisplayEntity.getGoodsNote1OpenStartTime().equals(
                            goodsGroupDisplayEntity.getGoodsNote1SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-001-E", new Object[] {ERROR_TEXT_LABEL.get(1)});
        }

        // 特徴: 特徴１_公開開始日時 = 特徴２_公開開始日時
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2OpenStartTime()) &&
            !Objects.isNull(goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime()) &&
            goodsGroupDisplayEntity.getGoodsNote2OpenStartTime().equals(
                            goodsGroupDisplayEntity.getGoodsNote2SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-001-E", new Object[] {ERROR_TEXT_LABEL.get(2)});
        }

        // 注意事項: 注意事項１_公開開始日時 = 注意事項２_公開開始日時
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4OpenStartTime()) &&
            !Objects.isNull(goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime()) &&
            goodsGroupDisplayEntity.getGoodsNote4OpenStartTime().equals(
                            goodsGroupDisplayEntity.getGoodsNote4SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-001-E", new Object[] {ERROR_TEXT_LABEL.get(3)});
        }

        // シリーズPRコメント: シリーズPRコメント１_公開開始日時 = シリーズPRコメント２_公開開始日時
        if (!Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10OpenStartTime()) &&
            !Objects.isNull(goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime()) &&
            goodsGroupDisplayEntity.getGoodsNote10OpenStartTime().equals(
                            goodsGroupDisplayEntity.getGoodsNote10SubOpenStartTime())) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupEntity.getGoodsGroupCode(), null,
                            "PDR-2023RENEW-64-001-E", new Object[] {ERROR_TEXT_LABEL.get(4)});
        }
        // 2023-renew No64 to here
    }

    /**
     * キー項目チェック<br/>
     *
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupEntity 商品グループエンティティ
     * @param customParams     案件用引数
     * @return 商品グループコード
     */
    protected String checkKeyData(int processType,
                                  Map<String, Object> commonCsvInfoMap,
                                  GoodsGroupEntity goodsGroupEntity,
                                  Object... customParams) {
        // 商品管理番号
        if (goodsGroupEntity.getGoodsGroupCode() == null || goodsGroupEntity.getGoodsGroupCode().length() > 20
            || !Pattern.matches(regPatternForCode, goodsGroupEntity.getGoodsGroupCode())) {
            addErrorMessage(processType, commonCsvInfoMap, null, null, "SGP001003");
        }
        String goodsGroupCode = goodsGroupEntity.getGoodsGroupCode();
        // 商品名
        // 2023-renew No64 from here
        if (goodsGroupEntity.getGoodsGroupNameAdmin() == null
            || goodsGroupEntity.getGoodsGroupNameAdmin().length() > 120) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001004");
        }
        // 2023-renew No64 to here
        return goodsGroupCode;
    }

    /**
     * 登録カテゴリチェック<br/>
     *
     * @param goodsGroupDto    商品グループDTO
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupCode   商品グループコード
     * @param customParams     案件用引数
     */
    protected void checkCategoryGoods(GoodsGroupDto goodsGroupDto,
                                      int processType,
                                      Map<String, Object> commonCsvInfoMap,
                                      String goodsGroupCode,
                                      Object... customParams) {
        // 登録カテゴリ重複チェック
        List<CategoryGoodsEntity> categoryGoodsEntityList = goodsGroupDto.getCategoryGoodsEntityList();
        // 重複チェック用カテゴリSEQリスト
        List<Integer> categorySeqList = new ArrayList<>();
        if (categoryGoodsEntityList != null) {
            for (CategoryGoodsEntity categoryGoodsEntity : categoryGoodsEntityList) {
                // 登録カテゴリSEQ重複チェック
                if (categorySeqList.contains(categoryGoodsEntity.getCategorySeq())) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001065",
                                    new Object[] {categoryGoodsEntity.getCategorySeq().toString()}
                                   );
                }
                categorySeqList.add(categoryGoodsEntity.getCategorySeq());
            }
        }
    }

    /**
     * 関連商品チェック<br/>
     *
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupEntity        商品グループエンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkGoodsRelation(List<GoodsRelationEntity> goodsRelationEntityList,
                                      int processType,
                                      Map<String, Object> commonCsvInfoMap,
                                      GoodsGroupEntity goodsGroupEntity,
                                      String goodsGroupCode,
                                      Object... customParams) {
        // 重複チェック用商品グループSEQリスト
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        int maxGoodsRelationAmount = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("goods.relation.amount"));
        // 関連商品登録数上限チェック
        if (goodsRelationEntityList.size() > maxGoodsRelationAmount) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001066",
                            new Object[] {PropertiesUtil.getSystemPropertiesValue("goods.relation.amount")}
                           );
        }
        for (GoodsRelationEntity goodsRelationEntity : goodsRelationEntityList) {
            // 更新時、自身の商品グループでないことを確認する
            if (goodsGroupEntity.getGoodsGroupSeq() != null && goodsRelationEntity.getGoodsRelationGroupSeq()
                                                                                  .equals(goodsGroupEntity.getGoodsGroupSeq())) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001067");
            }
            // 関連商品_商品グループSEQ重複チェック
            if (goodsGroupSeqList.contains(goodsRelationEntity.getGoodsRelationGroupSeq())) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001068",
                                new Object[] {goodsRelationEntity.getGoodsGroupCode()}
                               );
            }
            goodsGroupSeqList.add(goodsRelationEntity.getGoodsRelationGroupSeq());
        }
    }

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品チェック<br/>
     *
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupEntity        商品グループエンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkGoodsTogetherBuyGroup(List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                      int processType,
                                      Map<String, Object> commonCsvInfoMap,
                                      GoodsGroupEntity goodsGroupEntity,
                                      String goodsGroupCode,
                                      Object... customParams) {
        // 重複チェック用商品グループSEQリスト
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        int maxGoodsRelationAmount =
                        Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("goods.togetherbuy.amount"));
        // よく一緒に購入される商品登録数上限チェック
        if (goodsTogetherBuyGroupEntityList.size() > maxGoodsRelationAmount) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-2023RENEW-21-003-",
                            new Object[] {PropertiesUtil.getSystemPropertiesValue("goods.togetherbuy.amount")}
                           );
        }
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            // 更新時、自身の商品グループでないことを確認する
            if (goodsGroupEntity.getGoodsGroupSeq() != null && goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq()
                                                                                          .equals(goodsGroupEntity.getGoodsGroupSeq())) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-2023RENEW-21-002-");
            }
            // よく一緒に購入される商品_商品グループSEQ重複チェック
            if (HTypeRegisterMethodType.BACK.equals(goodsTogetherBuyGroupEntity.getRegistMethod()) && goodsGroupSeqList.contains(goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq())) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-2023RENEW-21-005-",
                                new Object[] {goodsTogetherBuyGroupEntity.getGoodsGroupCode()}
                               );
            }
            goodsGroupSeqList.add(goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq());
        }
    }
    // 2023-renew No21 to here

    /**
     * 商品グループチェック<br/>
     *
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupEntity 商品グループエンティティ
     * @param goodsGroupCode   商品グループコード
     * @param customParams     案件用引数
     */
    protected void checkGoodsGroup(int processType,
                                   Map<String, Object> commonCsvInfoMap,
                                   GoodsGroupEntity goodsGroupEntity,
                                   String goodsGroupCode,
                                   Object... customParams) {
        // 商品公開状態
        if (goodsGroupEntity.getGoodsOpenStatusPC() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001084");
        }
        // 商品公開開始日時≦商品公開終了日時チェック
        if (goodsGroupEntity.getOpenStartTimePC() != null && goodsGroupEntity.getOpenEndTimePC() != null
            && goodsGroupEntity.getOpenStartTimePC().getTime() > goodsGroupEntity.getOpenEndTimePC().getTime()) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001069");
        }

        // 追加モードでアップロード時、PC,モバイルの公開状態が削除の場合、エラー
        if (processType == PROCESS_TYPE_FROM_CSV) {
            if (HTypeOpenDeleteStatus.DELETED == goodsGroupEntity.getGoodsOpenStatusPC()
                && commonCsvInfoMap.get("uploadType") == HTypeUploadType.REGIST) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001105",
                                new Object[] {goodsGroupEntity.getGoodsGroupCode()}
                               );
            }
        }

        // SNS連携フラグ
        if (goodsGroupEntity.getSnsLinkFlag() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001103");
        }
        // 酒類フラグ
        if (goodsGroupEntity.getAlcoholFlag() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PKG-4113-003-L-E");
        }

        // 税率チェック
        if (goodsGroupEntity.getTaxRate() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, MSGCD_NOSET_TAX_RATE,
                            messageArgsGenerator(MSG_ARGS_TAX_RATE)
                           );
        } else {

            // 税率情報を取得
            Map<HTypeTaxRateType, TaxRateEntity> taxRateMap = taxGetLogic.getEffectiveTaxRateMap();

            boolean taxRateErrorFlg = true;
            for (TaxRateEntity val : taxRateMap.values()) {
                // 施工中の税率に該当する場合、抜ける
                if (val.getRate().compareTo(goodsGroupEntity.getTaxRate()) == 0) {
                    taxRateErrorFlg = false;
                    break;
                }

            }
            // 現在施工中の税率がヒットしない場合エラー
            if (taxRateErrorFlg) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, MSGCD_TAX_RATE_OUT);
            }
        }
        // PDR Migrate Customization from here
        //        /**
        //         * PDR#10 07_データ連携（商品情報）新規情報のチェック項目追加<br/>
        //         * 商品グループDto相関チェックサービス<br/>
        //         *
        //         */
        // 商品販売区分　⇒　薬品区分
        if (goodsGroupEntity.getGoodsClassType() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0007-001-S-");
        }
        // シリーズ価格
        if (goodsGroupEntity.getGroupPrice() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0007-002-S-");
        }
        // シリーズ価格記号表示フラグ
        if (goodsGroupEntity.getGroupPriceMarkDispFlag() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0007-003-S-");
        }
        // シリーズセール価格が設定されている(nullでない)場合、シリーズセール価格記号表示フラグをチェック
        if (goodsGroupEntity.getGroupSalePrice() != null) {
            if (goodsGroupEntity.getGroupSalePriceMarkDispFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0007-004-S-");
            }
        }
        // カタログ表示順
        if (goodsGroupEntity.getCatalogDisplayOrder() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0007-019-S-");
        }
        // 歯科専売可否フラグ
        if (goodsGroupEntity.getDentalMonopolySalesFlg() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "PDR-0437-020-S-");
        }
        // PDR Migrate Customization to here
    }

    /**
     * 商品グループ表示チェック<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkGoodsGroupDisplay(int processType,
                                          Map<String, Object> commonCsvInfoMap,
                                          GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                          String goodsGroupCode,
                                          Object... customParams) {
        // キーワードチェック（商品検索キーワード＆metaDescription＆metaKeyword）
        checkKeyword(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);
        // 商品情報チェック（商品説明）
        checkGoodsNote(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);
        // 商品情報チェック（受注連携設定）
        checkOrderSetting(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);
        // 商品納期チェック
        checkDeliveryType(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);

        // 規格情報チェック
        checkUnitInfo(processType, commonCsvInfoMap, goodsGroupDisplayEntity, goodsGroupCode);
    }

    /**
     * キーワードチェック（商品検索キーワード＆metaDescription＆metaKeyword）<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkKeyword(int processType,
                                Map<String, Object> commonCsvInfoMap,
                                GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                String goodsGroupCode,
                                Object... customParams) {
        // metaDescription
        if (goodsGroupDisplayEntity.getMetaDescription() != null
            && goodsGroupDisplayEntity.getMetaDescription().length() > 400) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001012");
        }
        // metaKeyword
        if (goodsGroupDisplayEntity.getMetaKeyword() != null
            && goodsGroupDisplayEntity.getMetaKeyword().length() > 200) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001013");
        }
    }

    /**
     * 商品説明チェック<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkGoodsNote(int processType,
                                  Map<String, Object> commonCsvInfoMap,
                                  GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                  String goodsGroupCode,
                                  Object... customParams) {
        final int MAXIMUM = ValidatorConstants.LENGTH_GOODS_NOTE_MAXIMUM;
        // 2023-renew No19 from here
        final int FREEARE_MAXIMUM = ValidatorConstants.LENGTH_GOODS_NOTE_FREEAREA_MAXIMUM;
        // 2023-renew No19 to here
        // 商品説明１
        if (goodsGroupDisplayEntity.getGoodsNote1() != null
            && goodsGroupDisplayEntity.getGoodsNote1().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明１", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明２
        if (goodsGroupDisplayEntity.getGoodsNote2() != null
            && goodsGroupDisplayEntity.getGoodsNote2().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明２", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明３
        if (goodsGroupDisplayEntity.getGoodsNote3() != null
            && goodsGroupDisplayEntity.getGoodsNote3().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明３", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明４
        if (goodsGroupDisplayEntity.getGoodsNote4() != null
            && goodsGroupDisplayEntity.getGoodsNote4().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明４", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明５
        if (goodsGroupDisplayEntity.getGoodsNote5() != null
            && goodsGroupDisplayEntity.getGoodsNote5().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明５", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明６
        // 2023-renew No19 from here
        if (goodsGroupDisplayEntity.getGoodsNote6() != null
            && goodsGroupDisplayEntity.getGoodsNote6().length() > FREEARE_MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明６", String.valueOf(FREEARE_MAXIMUM)}
                           );
            // 2023-renew No19 to here
        }
        // 商品説明７
        if (goodsGroupDisplayEntity.getGoodsNote7() != null
            && goodsGroupDisplayEntity.getGoodsNote7().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明７", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明８
        if (goodsGroupDisplayEntity.getGoodsNote8() != null
            && goodsGroupDisplayEntity.getGoodsNote8().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明８", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明９
        if (goodsGroupDisplayEntity.getGoodsNote9() != null
            && goodsGroupDisplayEntity.getGoodsNote9().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明９", String.valueOf(MAXIMUM)}
                           );
        }
        // 商品説明１０
        if (goodsGroupDisplayEntity.getGoodsNote10() != null
            && goodsGroupDisplayEntity.getGoodsNote10().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"商品説明１０", String.valueOf(MAXIMUM)}
                           );
        }
    }

    /**
     * 受注連携設定チェック<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkOrderSetting(int processType,
                                     Map<String, Object> commonCsvInfoMap,
                                     GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                     String goodsGroupCode,
                                     Object... customParams) {
        final int MAXIMUM = ValidatorConstants.LENGTH_GOODS_ORDERSETTING_MAXIMUM;
        // 受注連携設定１
        if (goodsGroupDisplayEntity.getOrderSetting1() != null
            && goodsGroupDisplayEntity.getOrderSetting1().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定１", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定２
        if (goodsGroupDisplayEntity.getOrderSetting2() != null
            && goodsGroupDisplayEntity.getOrderSetting2().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定２", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定３
        if (goodsGroupDisplayEntity.getOrderSetting3() != null
            && goodsGroupDisplayEntity.getOrderSetting3().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定３", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定４
        if (goodsGroupDisplayEntity.getOrderSetting4() != null
            && goodsGroupDisplayEntity.getOrderSetting4().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定４", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定５
        if (goodsGroupDisplayEntity.getOrderSetting5() != null
            && goodsGroupDisplayEntity.getOrderSetting5().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定５", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定６
        if (goodsGroupDisplayEntity.getOrderSetting6() != null
            && goodsGroupDisplayEntity.getOrderSetting6().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定６", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定７
        if (goodsGroupDisplayEntity.getOrderSetting7() != null
            && goodsGroupDisplayEntity.getOrderSetting7().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定７", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定８
        if (goodsGroupDisplayEntity.getOrderSetting8() != null
            && goodsGroupDisplayEntity.getOrderSetting8().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定８", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定９
        if (goodsGroupDisplayEntity.getOrderSetting9() != null
            && goodsGroupDisplayEntity.getOrderSetting9().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定９", String.valueOf(MAXIMUM)}
                           );
        }
        // 受注連携設定１０
        if (goodsGroupDisplayEntity.getOrderSetting10() != null
            && goodsGroupDisplayEntity.getOrderSetting10().length() > MAXIMUM) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001014",
                            new String[] {"受注連携設定１０", String.valueOf(MAXIMUM)}
                           );
        }
    }

    /**
     * 商品納期チェック<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkDeliveryType(int processType,
                                     Map<String, Object> commonCsvInfoMap,
                                     GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                     String goodsGroupCode,
                                     Object... customParams) {
        // 商品納期
        if (goodsGroupDisplayEntity.getDeliveryType() != null
            && goodsGroupDisplayEntity.getDeliveryType().length() > 50) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001083");
        }
    }

    /**
     * 規格情報チェック<br/>
     *
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkUnitInfo(int processType,
                                 Map<String, Object> commonCsvInfoMap,
                                 GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                 String goodsGroupCode,
                                 Object... customParams) {
        // 規格管理フラグ
        if (goodsGroupDisplayEntity.getUnitManagementFlag() == null) {
            addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001031");
        }
        // 規格管理フラグ
        if (HTypeUnitManagementFlag.ON == goodsGroupDisplayEntity.getUnitManagementFlag()) {
            // 規格１表示名
            if (goodsGroupDisplayEntity.getUnitTitle1() == null
                || goodsGroupDisplayEntity.getUnitTitle1().length() > 100) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001032");
            }
            // 規格２表示名
            if (goodsGroupDisplayEntity.getUnitTitle2() != null
                && goodsGroupDisplayEntity.getUnitTitle2().length() > 100) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001033");
            }
        } else if (HTypeUnitManagementFlag.OFF == goodsGroupDisplayEntity.getUnitManagementFlag()) {
            // 規格１表示名
            if (goodsGroupDisplayEntity.getUnitTitle1() != null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001034");
            }
            // 規格２表示名
            if (goodsGroupDisplayEntity.getUnitTitle2() != null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001035");
            }
        }
    }

    /**
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupEntity        商品グループエンティティ
     * @param goodsDtoList            商品DTOリスト
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param goodsGroupCode          商品グループコード
     * @param customParams            案件用引数
     */
    protected void checkGoodsDetail(int processType,
                                    Map<String, Object> commonCsvInfoMap,
                                    GoodsGroupEntity goodsGroupEntity,
                                    List<GoodsDto> goodsDtoList,
                                    GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                    String goodsGroupCode,
                                    Object... customParams) {
        Set<String> goodsCodeSet = new HashSet<>();
        boolean existUnit = false;
        boolean existUnitFail = false;
        // PDR Migrate Customization from here
        // PDR Migrate Customization to here
        boolean stockManagementFlagFail = false;
        boolean individualDeliveryTypeFail = false;
        boolean freeDeliveryFlagFail = false;

        // 規格 / 表示順重複チェック用
        Set<String> unitvalues = new HashSet<>();
        Set<Integer> goodsGroupCodeOrderDisplays = new HashSet<>();

        for (int i = 0; i < goodsDtoList.size(); i++) {
            GoodsDto goodsDto = goodsDtoList.get(i);
            GoodsEntity goodsEntity = goodsDto.getGoodsEntity();
            // 規格が存在しない場合は、以下のチェックをスキップ
            if (goodsEntity == null) {
                continue;
            }

            String goodsCode = goodsEntity.getGoodsCode();
            // 商品規格情報不正
            if (goodsEntity.getGoodsCode() == null) {
                addErrorMessage(processType, commonCsvInfoMap, null, null, "SGP001077",
                                new Object[] {Integer.valueOf(i + 1).toString()}
                               );
                continue;
            }

            // 追加モードでPC,モバイルの販売状態が削除の場合、エラー
            if (processType == PROCESS_TYPE_FROM_CSV) {
                if (HTypeGoodsSaleStatus.DELETED == goodsEntity.getSaleStatusPC()
                    && commonCsvInfoMap.get("uploadType") == HTypeUploadType.REGIST) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001106",
                                    new Object[] {goodsCode}
                                   );
                }
            }

            // 削除状態の場合は、以下のチェックをスキップ
            if (HTypeGoodsSaleStatus.DELETED == goodsEntity.getSaleStatusPC()) {
                continue;
            }
            if (!existUnitFail && existUnit
                && HTypeUnitManagementFlag.OFF == goodsGroupDisplayEntity.getUnitManagementFlag()) {
                // PDR Migrate Customization from here
                if (((goodsDtoList.get(i).getGoodsEntity()).getEmotionPriceType())
                    != HTypeEmotionPriceType.EMOTIONPRICE) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001036");
                    existUnitFail = true;
                }
                // PDR Migrate Customization to here
            } else {
                existUnit = true;
            }
            // 商品番号重複チェック
            if (goodsEntity != null) {
                if (goodsCodeSet.contains(goodsEntity.getGoodsCode())) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001037",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                } else {
                    goodsCodeSet.add(goodsEntity.getGoodsCode());
                }
            }
            // 個別配送
            if (!individualDeliveryTypeFail && goodsEntity.getIndividualDeliveryType() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001041",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
                individualDeliveryTypeFail = true;
            }
            // 送料
            if (!freeDeliveryFlagFail && goodsEntity.getFreeDeliveryFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001042",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
                freeDeliveryFlagFail = true;
            }
            // 商品番号
            if (goodsEntity.getGoodsCode() == null || goodsEntity.getGoodsCode().length() > 20 || !Pattern.matches(
                            regPatternForCode, goodsEntity.getGoodsCode())) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001043",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // PDR Migrate Customization from here
            // 商品表示順
            // PDR Migrate Customization to here
            if (goodsEntity.getOrderDisplay() == null || goodsEntity.getOrderDisplay().compareTo(Integer.valueOf(1)) < 0
                || goodsEntity.getOrderDisplay().compareTo(Integer.valueOf(9999)) > 0) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001099",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // JANコード
            if (goodsEntity.getJanCode() != null && (goodsEntity.getJanCode().length() > 16 || !Pattern.matches(
                            regPatternForJanCode, goodsEntity.getJanCode()))) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001044",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // PDR Migrate Customization from here
            // カタログ番号
            // PDR Migrate Customization to here
            if (goodsEntity.getCatalogCode() != null && goodsEntity.getCatalogCode().length() != 0) {
                if (goodsEntity.getCatalogCode().length() > 16 || !Pattern.matches(
                                regPatternForCode, goodsEntity.getCatalogCode())) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001045",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
            }
            // 規格情報
            if (HTypeUnitManagementFlag.ON == goodsGroupDisplayEntity.getUnitManagementFlag()) {
                if (goodsEntity.getUnitValue1() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001046",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
                if (goodsGroupDisplayEntity.getUnitTitle2() != null && goodsEntity.getUnitValue2() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001047",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
                if (goodsGroupDisplayEntity.getUnitTitle2() == null && goodsEntity.getUnitValue2() != null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001048",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
            } else if (HTypeUnitManagementFlag.OFF == goodsGroupDisplayEntity.getUnitManagementFlag()) {
                if (goodsEntity.getUnitValue1() != null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001049",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
                if (goodsEntity.getUnitValue2() != null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001050",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
            }

            // PDR Migrate Customization from here
            // 規格削除でない場合 規格名称重複 / 表示順 重複チェックを実施
            if (goodsEntity.getSaleStatusPC() != HTypeGoodsSaleStatus.DELETED
                && goodsEntity.getEmotionPriceType() != HTypeEmotionPriceType.EMOTIONPRICE) {
                // PDR Migrate Customization to here
                // 規格重複チェック
                String unitValue = goodsUtility.createUnitValue(goodsEntity);
                if (unitvalues.contains(unitValue)) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001102",
                                    new Object[] {goodsCode}
                                   );
                } else {
                    unitvalues.add(unitValue);
                }

                // 規格表示順の重複チェック
                // 商品販売状態=削除の場合以外、重複チェック対象
                if (goodsEntity.getOrderDisplay() != null) {
                    if (goodsGroupCodeOrderDisplays.contains(goodsEntity.getOrderDisplay())) {
                        addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode,
                                        GoodsCsvUpLoadAsynchronousService.OREDERDISPLAY_DUPLICATE_FAIL,
                                        new Object[] {goodsGroupEntity.getGoodsGroupCode(),
                                                        goodsEntity.getOrderDisplay()}
                                       );
                    } else {
                        goodsGroupCodeOrderDisplays.add(goodsEntity.getOrderDisplay());
                    }
                }

            }

            // 2023-renew No76 from here
            // 規格画像有無
            if (goodsEntity.getUnitImageFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, MSGCD_NOSET_UNITIMAGEFLAG,
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 2023-renew No76 to here

            // 販売状態PC
            if (goodsEntity.getSaleStatusPC() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001051",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 販売開始日時PC≦販売終了日時PCチェック
            if (goodsEntity.getSaleStartTimePC() != null && goodsEntity.getSaleEndTimePC() != null
                && goodsEntity.getSaleStartTimePC().getTime() > goodsEntity.getSaleEndTimePC().getTime()) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001070");
            }

            // 単価
            if (goodsEntity.getGoodsPrice() == null || goodsEntity.getGoodsPrice().compareTo(GOODS_PRICE_MIN) < 0
                || goodsEntity.getGoodsPrice().compareTo(GOODS_PRICE_MAX) > 0) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, MSGCD_PRICE_LIMIT_OUT,
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }

            // PDR Migrate Customization from here
            // セール価格
            // メッセージ通り0 ~ 99999999へ修正
            if (goodsEntity.getPreDiscountPrice() != null && (
                            goodsEntity.getPreDiscountPrice().compareTo(new BigDecimal(0)) < 0
                            || goodsEntity.getPreDiscountPrice().compareTo(new BigDecimal(99999999)) > 0)) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001054",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }

            // PDR Migrate Customization to here

            // PDR Migrate Customization from here
            // PDR Migrate Customization to here

            // 最大購入可能数
            if (goodsEntity.getPurchasedMax() == null || goodsEntity.getPurchasedMax().compareTo(new BigDecimal(1)) < 0
                || goodsEntity.getPurchasedMax().compareTo(new BigDecimal(9999)) > 0) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001057",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }

            // 在庫管理フラグ
            if (!stockManagementFlagFail && goodsDto.getGoodsEntity().getStockManagementFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, null, "SGP001058");
                stockManagementFlagFail = true;
            }
            // PDR Migrate Customization from here
            // カスタマイズクラスに変換
            // 保留フラグ
            if (goodsEntity.getReserveFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-005-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 管理商品コード
            if (goodsEntity.getGoodsManagementCode() != null && goodsEntity.getGoodsManagementCode().length() > 16) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-006-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 商品分類コード
            if (goodsEntity.getGoodsDivisionCode() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-007-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // カテゴリー1
            if (goodsEntity.getGoodsCategory1() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-008-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // カテゴリー2
            if (goodsEntity.getGoodsCategory2() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-009-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // カテゴリー3
            if (goodsEntity.getGoodsCategory3() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-010-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 陸送商品フラグ
            if (goodsEntity.getLandSendFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-011-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // クール便フラグ
            if (goodsEntity.getCoolSendFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-012-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // クール便フラグが1(ON)の場合
            if (HTypeCoolSendFlag.ON == goodsEntity.getCoolSendFlag()) {
                // クール便適用期間チェック
                if (goodsEntity.getCoolSendFrom() == null || goodsEntity.getCoolSendTo() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-013-S-",
                                    new Object[] {goodsEntity.getGoodsCode()}
                                   );
                }
            }
            // 単位
            if (goodsEntity.getUnit() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-014-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // 価格記号表示フラグ
            if (goodsEntity.getPriceMarkDispFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-015-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // セール価格記号表示フラグ
            if (goodsEntity.getSalePriceMarkDispFlag() == null) {
                addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "PDR-0007-016-S-",
                                new Object[] {goodsEntity.getGoodsCode()}
                               );
            }
            // PDR Migrate Customization to here
            // 商品在庫
            StockDto stockDto = goodsDto.getStockDto();
            if (HTypeStockManagementFlag.ON == goodsDto.getGoodsEntity().getStockManagementFlag()) {
                // 残少表示在庫数
                if (stockDto.getRemainderFewStock() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001059",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 発注点在庫数
                if (stockDto.getOrderPointStock() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001060",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 安全在庫数
                if (stockDto.getSafetyStock() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001061",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 入庫数
                if (stockDto.getSupplementCount() == null) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "GOODS-CSV-STOCK-001-",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
            } else if (HTypeStockManagementFlag.OFF == goodsDto.getGoodsEntity().getStockManagementFlag()) {
                // 残少表示在庫数
                if (stockDto.getRemainderFewStock() != null && !stockDto.getRemainderFewStock()
                                                                        .equals(new BigDecimal(0))) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001062",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 発注点在庫数
                if (stockDto.getOrderPointStock() != null && !stockDto.getOrderPointStock().equals(new BigDecimal(0))) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001063",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 安全在庫数
                if (stockDto.getSafetyStock() != null && !stockDto.getSafetyStock().equals(new BigDecimal(0))) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "SGP001064",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
                // 入庫数
                if (stockDto.getSupplementCount() != null && !stockDto.getSupplementCount().equals(new BigDecimal(0))) {
                    addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, "GOODS-CSV-STOCK-002-",
                                    new Object[] {goodsDto.getGoodsEntity().getGoodsCode()}
                                   );
                }
            }
        }
    }

    /**
     * 処理種別=CSV用の規格情報ソート<br/>
     * CSV処理行の順に並べる。
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsDtoList     商品Dtoリスト
     * @return CSV処理行順に並んだ商品Dto(CSVにない規格は商品 ． 表示順の昇順)
     */
    protected List<GoodsDto> sortCsvRecordOrder(Map<String, Object> commonCsvInfoMap, List<GoodsDto> goodsDtoList) {

        // 商品コード：CSV処理行の対応マップ
        @SuppressWarnings("unchecked")
        Map<String, Integer> goodsCodeAndRecodeCountMap =
                        (Map<String, Integer>) commonCsvInfoMap.get("goodsCodeAndRecodeCountMap");

        // ソート後の商品Dto
        List<GoodsDto> sortedGoodsDtoList = new ArrayList<>();
        // Key=CSV処理行, Value=商品DtoのTreeMap
        TreeMap<Integer, GoodsDto> tmap = new TreeMap<>();
        // CSV処理対象外の商品Dtoリスト
        List<GoodsDto> noUpdateGoodsDtoList = new ArrayList<>();

        for (GoodsDto goodsDto : goodsDtoList) {
            if (goodsCodeAndRecodeCountMap.get(goodsDto.getGoodsEntity().getGoodsCode()) != null) {
                tmap.put(goodsCodeAndRecodeCountMap.get(goodsDto.getGoodsEntity().getGoodsCode()), goodsDto);
            } else {
                noUpdateGoodsDtoList.add(goodsDto);
            }
        }

        for (Iterator<Map.Entry<Integer, GoodsDto>> iterator = tmap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, GoodsDto> entry = iterator.next();
            sortedGoodsDtoList.add(entry.getValue());
        }
        sortedGoodsDtoList.addAll(noUpdateGoodsDtoList);
        return sortedGoodsDtoList;
    }

    /**
     * エラーメッセージスロー処理<br/>
     * 画面からの場合は、そのままメッセージをスロー。<br/>
     * CSVからの場合は、商品管理番号・商品番号・行番号をエラーパラメータの末尾に追加してメッセージをスローする。<br/>
     *
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupCode   商品管理番号（処理種別=画面の場合null）
     * @param goodsCode        商品番号（処理種別=画面の場合null）
     * @param errorCode        エラーコード
     */
    protected void throwMessage(int processType,
                                Map<String, Object> commonCsvInfoMap,
                                String goodsGroupCode,
                                String goodsCode,
                                String errorCode) {

        // エラーパラメータ
        Object[] args = null;

        // CSVからの場合、行番号を取得する
        if (processType == PROCESS_TYPE_FROM_CSV) {

            // エラーパラメータ末尾に追加
            args = new Object[] {goodsGroupCode, goodsCode};
        }

        super.throwMessage(errorCode, args);
    }

    /**
     * エラーメッセージ追加処理<br/>
     * 画面からの場合は、そのままメッセージを設定。<br/>
     * CSVからの場合は、商品管理番号・商品番号をエラーパラメータの末尾に追加してメッセージを設定する。<br/>
     *
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupCode   商品管理番号（処理種別=画面の場合null）
     * @param goodsCode        商品番号（処理種別=画面の場合null）
     * @param errorCode        エラーコード
     * @param args             エラーパラメータ
     */
    protected void addErrorMessage(int processType,
                                   Map<String, Object> commonCsvInfoMap,
                                   String goodsGroupCode,
                                   String goodsCode,
                                   String errorCode,
                                   Object[] args) {

        // CSVからの場合、行番号を取得する
        if (processType == PROCESS_TYPE_FROM_CSV) {

            // エラーパラメータ末尾に追加
            if (args == null) {
                args = new Object[] {goodsGroupCode, goodsCode};
            } else {
                Object[] args2 = new Object[args.length + 2];
                for (int i = 0; i < args.length; i++) {
                    args2[i] = args[i];
                }
                args2[args.length] = goodsGroupCode;
                args2[args.length + 1] = goodsCode;
                args = args2;
            }
        }
        super.addErrorMessage(errorCode, args);
    }

    /**
     * エラーメッセージ追加処理<br/>
     * 画面からの場合は、そのままメッセージを設定。<br/>
     * CSVからの場合は、商品管理番号・商品番号をパラメータに追加してメッセージを設定する。<br/>
     *
     * @param processType      処理種別（画面/CSV）
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupCode   商品管理番号（処理種別=画面の場合null）
     * @param goodsCode        商品番号（処理種別=画面の場合null）
     * @param errorCode        エラーコード
     */
    protected void addErrorMessage(int processType,
                                   Map<String, Object> commonCsvInfoMap,
                                   String goodsGroupCode,
                                   String goodsCode,
                                   String errorCode) {

        this.addErrorMessage(processType, commonCsvInfoMap, goodsGroupCode, goodsCode, errorCode, null);
    }

    /**
     * メッセージの引数を生成する<br/>
     *
     * @param args メッセージ引数
     * @return 生成された引数
     */
    protected Object[] messageArgsGenerator(String... args) {
        if (args == null || args.length == 0) {
            return new Object[] {};
        }

        List<Object> objectList = new ArrayList<>();
        for (String arg : args) {
            objectList.add(arg);
        }

        return objectList.toArray();
    }

    /**
     * 文字列を結合する<br/>
     *
     * @param name   項目名
     * @param number 項目番号
     * @return 項目名 + 項目番号
     */
    protected String nameNumberAdd(String name, int number) {
        if (StringUtil.isEmpty(name)) {
            return "";
        }

        StringBuilder bi = new StringBuilder();
        bi.append(name);
        bi.append(number);

        return bi.toString();
    }
}
