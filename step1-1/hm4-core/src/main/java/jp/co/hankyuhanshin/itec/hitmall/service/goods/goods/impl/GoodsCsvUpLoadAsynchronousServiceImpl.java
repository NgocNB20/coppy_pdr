/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsOrderDisplayCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupMapGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSettingTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsCsvCodeCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsCsvUpLoadAsynchronousService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupCorrelationDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupRegistUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品一括アップロード
 * 商品CSVファイルの一括アップロードを行う。
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Service
public class GoodsCsvUpLoadAsynchronousServiceImpl extends AbstractShopService
                implements GoodsCsvUpLoadAsynchronousService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCsvUpLoadAsynchronousServiceImpl.class);

    // 共通項目
    /**
     * 最大金額システム値(99999999円)
     */
    protected static final BigDecimal SYSTEM_PRAM_PRICE_MAX = new BigDecimal(99999999);
    /**
     * 設定値"0"用システム値
     */
    protected static final BigDecimal SYSTEM_PRAM_ZERO = new BigDecimal(0);

    // 商品グループ項目
    /**
     * SNS連携フラグシステム値(0=設定不可)
     */
    protected static final HTypeSnsLinkFlag SYSTEM_PRAM_SNSLINKFLAG = HTypeSnsLinkFlag.OFF;
    /**
     * 新着日付システム値(PKG標準が当日付が1週間以内の場合に新着アイコンを表示するため、過去日を設定)
     */
    protected static final Timestamp SYSTEM_PRAM_WHATSNEWDATE =
                    new DateUtility().toTimestampValue("1990/01/01", "yyyy/MM/dd");
    /**
     * シリーズ価格記号表示フラグシステム値(0=表示しない)
     */
    protected static final HTypeGroupPriceMarkDispFlag SYSTEM_PRAM_GROUPPRICEMARKDISPFLAG =
                    HTypeGroupPriceMarkDispFlag.OFF;
    /**
     * シリーズセール価格記号表示フラグシステム値(0=表示しない)
     */
    protected static final HTypeGroupSalePriceMarkDispFlag SYSTEM_PRAM_GROUPSALEPRICEMARKDISPFLAG =
                    HTypeGroupSalePriceMarkDispFlag.OFF;

    /**
     * 利用可能配送方法システム値(固定配送ID="1001")<br/>
     * 今回利用する固定配送IDは1001で固定とする<br/>
     */
    protected static final String SYSTEM_PRAM_POSSIBLEDELIVERYMETHOD = "1001";
    /**
     * 個別配送システム値(0=個別配送商品でない)
     */
    protected static final HTypeIndividualDeliveryType SYSTEM_PRAM_INDIVIDUALDELIVERYTYPE =
                    HTypeIndividualDeliveryType.OFF;
    /**
     * 送料区分システム値(1=送料込)
     */
    protected static final HTypeFreeDeliveryFlag SYSTEM_PRAM_FREEDELIVERYFLAG = HTypeFreeDeliveryFlag.ON;
    /**
     * 販売状態モバイルシステム値(0=非販売)
     */
    protected static final HTypeGoodsSaleStatus SYSTEM_PRAM_SALESTATUSMB = HTypeGoodsSaleStatus.NO_SALE;
    /**
     * 最大購入可能数システム値(最大値=9999)
     */
    protected static final BigDecimal SYSTEM_PRAM_PURCHASEDMAX = new BigDecimal(9999);
    /**
     * 価格記号表示フラグシステム値(0=表示しない)
     */
    protected static final HTypePriceMarkDispFlag SYSTEM_PRAM_PRICEMARKDISPFLAG = HTypePriceMarkDispFlag.OFF;
    /**
     * セール価格記号表示フラグシステム値(0=表示しない)
     */
    protected static final HTypeSalePriceMarkDispFlag SYSTEM_PRAM_SALEPRICEMARKDISPFLAG =
                    HTypeSalePriceMarkDispFlag.OFF;

    /**
     * 商品グループ詳細取得ロジック実装クラス
     */
    private final GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    /**
     * CheckMessageUtility
     */
    private final CheckMessageUtility checkMessageUtility;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品グループ取得サービス
     */
    private final GoodsGroupGetService goodsGroupGetService;

    /**
     * 関連商品リスト取得サービス（バック用）
     */
    private final GoodsRelationListGetForBackService goodsRelationListGetForBackService;

    /**
     * 商品グループマップ取得(商品グループコード)ロジック
     */
    private final GoodsGroupMapGetByCodeLogic goodsGroupMapGetByCodeLogic;

    /**
     * 商品グループDto相関チェックサービス
     */
    private final GoodsGroupCorrelationDataCheckService goodsGroupCorrelationDataCheckService;

    /**
     * 商品グループ登録更新サービス
     */
    private final GoodsGroupRegistUpdateService goodsGroupRegistUpdateService;

    /**
     * カテゴリ情報取得ロジック
     */
    private final CategoryGetLogic categoryGetLogic;

    /**
     * カテゴリ登録商品マップ取得ロジック
     */
    private final CategoryGoodsMapGetLogic categoryGoodsMapGetLogic;

    /**
     * 商品グループテーブルロックLogic
     */
    private final GoodsGroupTableLockLogic goodsGroupTableLockLogic;

    /**
     * カテゴリテーブルロックLogic
     */
    private final CategoryTableLockLogic categoryTableLockLogic;

    /**
     * 商品グループ表示テーブルロックLogic
     */
    private final GoodsGroupDisplayTableLockLogic goodsGroupDisplayTableLockLogic;

    /**
     * カテゴリ登録商品テーブルロックLogic
     */
    private final CategoryGoodsTableLockLogic categoryGoodsTableLockLogic;

    /**
     * 関連商品テーブルロックLogic
     */
    private final GoodsRelationTableLockLogic goodsRelationTableLockLogic;

    /**
     * 商品グループ画像テーブルロックLogic
     */
    private final GoodsGroupImageTableLockLogic goodsGroupImageTableLockLogic;

    /**
     * 商品テーブルロックLogic
     */
    private final GoodsTableLockLogic goodsTableLockLogic;

    /**
     * 在庫設定テーブルロックLogic
     */
    private final StockSettingTableLockLogic stockSettingTableLockLogic;

    /**
     * 商品CSVアップロードコードチェックService
     */
    private final GoodsCsvCodeCheckService goodsCsvCodeCheckService;

    /**
     * 商品規格表示順チェックLogic
     */
    private final GoodsOrderDisplayCheckLogic goodsOrderDisplayCheckLogic;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト取得
     */
    private final GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService;
    // 2023-renew No21 to here

    /**
     * CSV読み込みのユーティリティ
     */
    private final CsvReaderUtil csvReaderUtil;
    /* --- PDR Migrate Customization from here --- */
    /**
     * 心意気価格商品削除対象商品コード
     */
    protected String emotionPriceDeleteGoodsCode;

    /**
     * 心意気価格商品追加対象商品コード
     */
    protected String emotionPriceCreateGoodsCode;
    /**
     * 心意気価格商品更新フラグ
     */
    protected boolean emotionGoodUpdateFlg;

    /* --- PDR Migrate Customization to here --- */
    @Autowired
    public GoodsCsvUpLoadAsynchronousServiceImpl(GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic,
                                                 CheckMessageUtility checkMessageUtility,
                                                 ConversionUtility conversionUtility,
                                                 GoodsGroupGetService goodsGroupGetService,
                                                 GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                                 GoodsGroupMapGetByCodeLogic goodsGroupMapGetByCodeLogic,
                                                 GoodsGroupCorrelationDataCheckService goodsGroupCorrelationDataCheckService,
                                                 GoodsGroupRegistUpdateService goodsGroupRegistUpdateService,
                                                 CategoryGetLogic categoryGetLogic,
                                                 CategoryGoodsMapGetLogic categoryGoodsMapGetLogic,
                                                 GoodsGroupTableLockLogic goodsGroupTableLockLogic,
                                                 CategoryTableLockLogic categoryTableLockLogic,
                                                 GoodsGroupDisplayTableLockLogic goodsGroupDisplayTableLockLogic,
                                                 CategoryGoodsTableLockLogic categoryGoodsTableLockLogic,
                                                 GoodsRelationTableLockLogic goodsRelationTableLockLogic,
                                                 GoodsGroupImageTableLockLogic goodsGroupImageTableLockLogic,
                                                 GoodsTableLockLogic goodsTableLockLogic,
                                                 StockSettingTableLockLogic stockSettingTableLockLogic,
                                                 GoodsCsvCodeCheckService goodsCsvCodeCheckService,
                                                 GoodsOrderDisplayCheckLogic goodsOrderDisplayCheckLogic,
                                                 // 2023-renew No21 from here
                                                 GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        // 2023-renew No21 to here
        this.goodsGroupDetailsGetByCodeLogic = goodsGroupDetailsGetByCodeLogic;
        this.checkMessageUtility = checkMessageUtility;
        this.conversionUtility = conversionUtility;
        this.goodsGroupGetService = goodsGroupGetService;
        this.goodsRelationListGetForBackService = goodsRelationListGetForBackService;
        this.goodsGroupMapGetByCodeLogic = goodsGroupMapGetByCodeLogic;
        this.goodsGroupCorrelationDataCheckService = goodsGroupCorrelationDataCheckService;
        this.goodsGroupRegistUpdateService = goodsGroupRegistUpdateService;
        this.categoryGetLogic = categoryGetLogic;
        this.categoryGoodsMapGetLogic = categoryGoodsMapGetLogic;
        this.goodsGroupTableLockLogic = goodsGroupTableLockLogic;
        this.categoryTableLockLogic = categoryTableLockLogic;
        this.goodsGroupDisplayTableLockLogic = goodsGroupDisplayTableLockLogic;
        this.categoryGoodsTableLockLogic = categoryGoodsTableLockLogic;
        this.goodsRelationTableLockLogic = goodsRelationTableLockLogic;
        this.goodsGroupImageTableLockLogic = goodsGroupImageTableLockLogic;
        this.goodsTableLockLogic = goodsTableLockLogic;
        this.stockSettingTableLockLogic = stockSettingTableLockLogic;
        this.goodsCsvCodeCheckService = goodsCsvCodeCheckService;
        this.goodsOrderDisplayCheckLogic = goodsOrderDisplayCheckLogic;
        // 2023-renew No21 from here
        this.goodsTogetherBuyGroupListGetForBackService = goodsTogetherBuyGroupListGetForBackService;
        // 2023-renew No21 to here
        this.csvReaderUtil = new CsvReaderUtil();
        this.emotionGoodUpdateFlg = false;
    }

    @Override
    /**
     * 商品一括アップロード処理実行
     * @param uploadedCsvFile 商品CSVデータアップロードファイル
     * @param uploadType アップロード種別
     * @param siteType サイト種別
     * @return CsvUploadResult CSVアップロード結果Dto
     */ public CsvUploadResult execute(File uploadedCsvFile,
                                       HTypeUploadType uploadType,
                                       String administratorName,
                                       HTypeSiteType siteType) {
        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();

        /* Csvファイルを読み込み */
        List<GoodsCsvDto> goodsCsvDtoList = new ArrayList<>();

        // バリデーションチェック エラーの場合終了
        if (validate(goodsCsvDtoList, csvUploadResult, uploadedCsvFile)) {
            return csvUploadResult;
        }

        // CSVアップロード登録処理 エラーの場合 終了
        if (registProcess(goodsCsvDtoList, uploadType, csvUploadResult, uploadedCsvFile, administratorName, siteType)) {
            return csvUploadResult;
        }

        // 処理結果を返す
        return csvUploadResult;
    }

    /**
     * 登録処理
     *
     * @param goodsCsvDtoList CsvDtoリスト
     * @param uploadType      アップロード種別
     * @param csvUploadResult Csvアップロード結果
     * @param uploadedCsvFile 商品CSVデータアップロードファイル
     * @param siteType        サイト種別
     * @return エラーの有無 true=エラー、false=エラーなし
     */
    protected boolean registProcess(List<GoodsCsvDto> goodsCsvDtoList,
                                    HTypeUploadType uploadType,
                                    CsvUploadResult csvUploadResult,
                                    File uploadedCsvFile,
                                    String administratorName,
                                    HTypeSiteType siteType) {

        // CSVファイル処理行番号
        Integer recodeCount = 1;

        // アップロードされたカラムの取得
        List<String> uploadColumn = getUploadColumn(uploadedCsvFile);

        // CSVアップロード共通情報
        // Key : helper = CsvアップロードHelper
        // uploadType = アップロード種別
        // csvUploadResult = Csvアップロード結果
        // recodeCount = CSVファイル処理行番号
        // goodsGroupSeq = 処理中データの商品グループSEQ（商品グループ新規登録時はnull）
        // goodsCodeAndRecodeCountMap = 商品コードとCSV行の対応マップ（エラー行表示時に使用する）
        // goodsGroupInfoRecordCount = 商品グループ情報のCSV行（エラー行表示時に使用する）
        Map<String, Object> commonCsvInfoMap =
                        createCommonCsvInfoMap(goodsCsvDtoList, uploadType, csvUploadResult, recodeCount, uploadColumn);

        // １行前の商品グループコード
        String lastGoodsGroupCode = null;
        // 処理中の商品グループDto
        GoodsGroupDto goodsGroupDto = null;
        // 処理中の関連商品エンティティ
        List<GoodsRelationEntity> goodsRelationEntityList = null;
        // 2023-renew No21 from here
        // 処理中のよく一緒に購入される商品エンティティ
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList = null;
        // 2023-renew No21 to here
        // 規格共通情報（同一商品グループ内の全規格で同じ値が入る項目を管理するオブジェクト）
        GoodsEntity commonGoodsInfo = getComponent(GoodsEntity.class);

        goodsCsvCodeCheckService.init();

        try {
            try {
                // テーブルロック
                tableLock();
            } catch (DataAccessException sqle) {
                LOGGER.error("例外処理が発生しました", sqle);
                throwMessage("PKG-3680-020-S-");
            }

            // CSVの行数、以下の処理を繰り返す
            for (GoodsCsvDto csvline : goodsCsvDtoList) {

                if (csvline == null) {
                    addErrorMessage(CSVUPLOAD002E, new Object[] {null});
                    continue;
                }

                // (2) 商品グループ登録更新処理
                // 前回商品グループに対する処理
                // 前回の商品グループコードと異なる場合に実行される
                // 2023-renew No21 from here
                if (lastGoodsGroupCode != null && !lastGoodsGroupCode.equals(csvline.getGoodsGroupCode())) {
                    goodsGroupRegistUpdateProcess(commonCsvInfoMap, goodsGroupDto, goodsRelationEntityList,
                                                  goodsTogetherBuyGroupEntityList, commonGoodsInfo, false,
                                                  administratorName
                                                 );
                }
                // 2023-renew No21 to here

                // 処理行加算
                recodeCount++;
                commonCsvInfoMap.put("recodeCount", recodeCount);

                // 商品管理番号・商品番号のチェック
                boolean saveGgCode = goodsCsvCodeCheckService.canSaveGoodsGroupCode(csvline.getGoodsGroupCode());
                boolean saveGCode = goodsCsvCodeCheckService.canSaveGoodsCode(csvline.getGoodsCode());
                if (!saveGgCode || !saveGCode) {
                    if (!saveGgCode) {
                        addErrorMessage(SGP001086, new Object[] {csvline.getGoodsGroupCode()});
                    }
                    if (!saveGCode) {
                        addErrorMessage(SGP001087, new Object[] {csvline.getGoodsCode()});
                    }
                    if (hasErrorMessage()) {
                        // エラー発生時は、エラー発生行数（recodeCount）をメッセージに表示させるようにするため、goodsCodeAndRecodeCountをクリアする。
                        commonCsvInfoMap.remove("goodsGroupInfoRecordCount");
                        throwMessage();
                    }
                }

                // 現在商品グループの処理
                // データ１件目、または前回の商品グループコードと異なる場合に実行する
                if (lastGoodsGroupCode == null || !lastGoodsGroupCode.equals(csvline.getGoodsGroupCode())) {

                    // (3) 商品グループ情報取得処理
                    goodsGroupDetailsGetByCodeLogic.setUploadType(uploadType);
                    goodsGroupDto = goodsGroupInfoGetProcess(commonCsvInfoMap, csvline.getGoodsGroupCode(), siteType);

                    // (4) 関連商品情報リスト取得処理
                    goodsRelationEntityList = goodsRelationInfoGetProcess(commonCsvInfoMap,
                                                                          goodsGroupDto.getGoodsGroupEntity()
                                                                                       .getGoodsGroupSeq()
                                                                         );
                    // 2023-renew No21 from here
                    // (4) よく一緒に購入される商品情報リスト取得処理
                    goodsTogetherBuyGroupEntityList = goodsTogetherBuyGroupInfoGetProcess(commonCsvInfoMap,
                                                                                          goodsGroupDto.getGoodsGroupEntity()
                                                                                                       .getGoodsGroupSeq()
                                                                                         );

                    // PDR Migrate Customization from here
                    // (5) 商品グループ情報編集処理
                    goodsGroupInfoEditProcess(commonCsvInfoMap, csvline, goodsGroupDto, goodsRelationEntityList,
                                              goodsTogetherBuyGroupEntityList
                                             );
                    // 2023-renew No21 to here
                }

                // 心意気価格商品チェック
                if (csvline.getGoodsCode().endsWith("kp") || HTypeEmotionPriceType.EMOTIONPRICE.equals(
                                csvline.getEmotionPriceType())) {
                    addErrorMessage("PDR-0443-001-A-", new Object[] {});
                }
                // PDR Migrate Customization to here
                // (6) 商品情報編集処理
                goodsInfoEditProcess(commonCsvInfoMap, csvline, goodsGroupDto);

                // (7) 商品グループ情報から規格共通情報を編集する
                // 現在商品グループの処理
                // データ１件目、または前回の商品グループコードと異なる場合に実行する
                if (lastGoodsGroupCode == null || !lastGoodsGroupCode.equals(csvline.getGoodsGroupCode())) {

                    // 規格共通情報を編集する
                    commonGoodsInfo = editCommonGoodsInfo(csvline.getGoodsCode(), goodsGroupDto);
                }
                // PDR Migrate Customization from here
                // 心意気価格持ち通常商品が通常商品になった場合
                if (!StringUtils.isEmpty(emotionPriceDeleteGoodsCode)) {
                    csvline.setEmotionPriceType(HTypeEmotionPriceType.EMOTIONPRICE);
                    // 商品情報編集処理
                    goodsInfoEditProcess(commonCsvInfoMap, csvline, goodsGroupDto);
                    // 使ったら初期化
                    emotionPriceDeleteGoodsCode = null;
                }
                // PDR Migrate Customization to here
                // (8) 1行単位での処理後処理
                // １行前の商品グループコードに処理した商品グループコードを設定する
                lastGoodsGroupCode = csvline.getGoodsGroupCode();

                // 2023-renew No76 from here
                // 商品グループごとの規格登録数上限チェック
                checkGoodsLimit(commonCsvInfoMap, lastGoodsGroupCode);
                // 2023-renew No76 to here

                // PDR Migrate Customization from here
                if ((!StringUtils.isEmpty(emotionPriceCreateGoodsCode) && HTypeUploadType.MODIFY.equals(uploadType))
                    || (HTypeEmotionPriceType.NORMAL_WITH_EMOTION.equals(csvline.getEmotionPriceType())
                        && HTypeUploadType.REGIST.equals(uploadType))) {

                    setupCsvDto(csvline, goodsGroupDto);

                    // 元々の登録種別
                    HTypeUploadType uploadTypeA = uploadType;
                    if (HTypeUploadType.MODIFY.equals(uploadType) && !emotionGoodUpdateFlg) {
                        // 追加モードに変更
                        commonCsvInfoMap.put("uploadType", HTypeUploadType.REGIST);
                        uploadType = HTypeUploadType.REGIST;
                    } else if (HTypeUploadType.MODIFY.equals(uploadType) && emotionGoodUpdateFlg) {
                        // 更新モードに変更
                        commonCsvInfoMap.put("uploadType", HTypeUploadType.MODIFY);
                        uploadType = HTypeUploadType.MODIFY;
                    }

                    // 心意気価格商品チェック
                    if (csvline.getGoodsCode().endsWith("kp") || HTypeEmotionPriceType.EMOTIONPRICE.equals(
                                    csvline.getEmotionPriceType())) {
                        addErrorMessage("PDR-0443-001-A-", new Object[] {});
                    }

                    // 心意気価格保持区分に2を設定
                    csvline.setEmotionPriceType(HTypeEmotionPriceType.EMOTIONPRICE);

                    goodsGroupDetailsGetByCodeLogic.setUploadType(uploadType);

                    // 現在商品グループの処理
                    if (lastGoodsGroupCode == null || !lastGoodsGroupCode.equals(csvline.getGoodsGroupCode())) {
                        // 商品グループ情報取得処理
                        goodsGroupDto = goodsGroupInfoGetProcess(commonCsvInfoMap, csvline.getGoodsGroupCode(),
                                                                 siteType
                                                                );

                        // 関連商品情報リスト取得処理
                        goodsRelationEntityList = goodsRelationInfoGetProcess(commonCsvInfoMap,
                                                                              goodsGroupDto.getGoodsGroupEntity()
                                                                                           .getGoodsGroupSeq()
                                                                             );
                        // 2023-renew No21 from here
                        // よく一緒に購入される商品情報リスト取得処理
                        goodsTogetherBuyGroupEntityList = goodsTogetherBuyGroupInfoGetProcess(commonCsvInfoMap,
                                                                                              goodsGroupDto.getGoodsGroupEntity()
                                                                                                           .getGoodsGroupSeq()
                                                                                             );

                        // 商品グループ情報編集処理
                        goodsGroupInfoEditProcess(commonCsvInfoMap, csvline, goodsGroupDto, goodsRelationEntityList,
                                                  goodsTogetherBuyGroupEntityList
                                                 );
                    }
                    // 2023-renew No21 to here

                    // 商品情報編集処理
                    goodsInfoEditProcess(commonCsvInfoMap, csvline, goodsGroupDto);

                    // 追加する心意気価格商品の登録更新のみ実行
                    // 2023-renew No21 from here
                    goodsGroupRegistUpdateProcess(commonCsvInfoMap, goodsGroupDto, goodsRelationEntityList,
                                                  goodsTogetherBuyGroupEntityList, commonGoodsInfo, true,
                                                  administratorName
                                                 );
                    // 2023-renew No21 to here

                    // 使ったら初期化
                    if (StringUtils.isNotEmpty(emotionPriceCreateGoodsCode)) {
                        emotionPriceCreateGoodsCode = null;
                        emotionGoodUpdateFlg = false;
                    }
                    // 元のモードに変更
                    commonCsvInfoMap.put("uploadType", uploadTypeA);
                    uploadType = uploadTypeA;
                    lastGoodsGroupCode = null;
                }
                // PDR Migrate Customization to here
                // 処理件数をインクリメントする
                csvUploadResult.addMergeCount();
            }

            // CSVデータ行終了時の商品グループ登録更新処理
            if (lastGoodsGroupCode != null) {
                // PDR Migrate Customization from here
                // 2023-renew No21 from here
                goodsGroupRegistUpdateProcess(commonCsvInfoMap, goodsGroupDto, goodsRelationEntityList,
                                              goodsTogetherBuyGroupEntityList, commonGoodsInfo, false, administratorName
                                             );
                // 2023-renew No21 to here
                // PDR Migrate Customization to here
            }

        } catch (AppLevelListException appe) {
            LOGGER.error("例外処理が発生しました", appe);
            createCsvUploadErrorList(commonCsvInfoMap, appe);
            csvUploadResult.mergeRowCount = 0;
            return true;
        }

        return false;
    }

    /**
     * CSVアップロード処理共通情報生成
     * <pre>
     * Key : helper = CsvアップロードHelper
     * uploadType = アップロード種別
     * csvUploadResult = Csvアップロード結果
     * recodeCount = CSVファイル処理行番号
     * goodsGroupSeq = 処理中データの商品グループSEQ（商品グループ新規登録時はnull）
     * goodsCodeAndRecodeCountMap = 商品コードとCSV行の対応マップ（エラー行表示時に使用する）
     * goodsGroupInfoRecordCount = 商品グループ情報のCSV行（エラー行表示時に使用する）
     * </pre>
     *
     * @param goodsCsvDtoList CsvDtoリスト
     * @param uploadType      アップロード種別
     * @param csvUploadResult Csvアップロード結果
     * @param recodeCount     CSVファイルの処理行
     * @param uploadColumn    アップロードされたカラム
     * @return CSVアップロード処理中の共通情報
     */
    protected Map<String, Object> createCommonCsvInfoMap(List<GoodsCsvDto> goodsCsvDtoList,
                                                         HTypeUploadType uploadType,
                                                         CsvUploadResult csvUploadResult,
                                                         Integer recodeCount,
                                                         List<String> uploadColumn) {
        Map<String, Object> commonCsvInfoMap = new HashMap<>();
        commonCsvInfoMap.put("goodsCsvDtoList", goodsCsvDtoList);
        commonCsvInfoMap.put("uploadType", uploadType);
        commonCsvInfoMap.put("csvUploadResult", csvUploadResult);
        commonCsvInfoMap.put("recodeCount", recodeCount);
        commonCsvInfoMap.put("updateColumn", uploadColumn);
        commonCsvInfoMap.put("goodsGroupSeq", null);
        commonCsvInfoMap.put("goodsCodeAndRecodeCountMap", new HashMap<String, Integer>());
        commonCsvInfoMap.put("goodsCodeAndIndexMap", new HashMap<String, Integer>());

        return commonCsvInfoMap;
    }

    /**
     * テーブルロック処理
     */
    protected void tableLock() {
        // 商品グループ
        goodsGroupTableLockLogic.execute();
        // カテゴリ
        categoryTableLockLogic.execute();
        // 商品グループ表示
        goodsGroupDisplayTableLockLogic.execute();
        // カテゴリ登録商品
        categoryGoodsTableLockLogic.execute();
        // 関連商品
        goodsRelationTableLockLogic.execute();
        // 商品グループ画像
        goodsGroupImageTableLockLogic.execute();
        // 商品
        goodsTableLockLogic.execute();
        // 在庫設定
        stockSettingTableLockLogic.execute();
    }

    /**
     * 商品登録更新処理
     *
     * @param commonCsvInfoMap        CSVアップロード共通情報
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param commonGoodsInfo         規格共通情報
     */
    // PDR Migrate Customization from here
    protected void goodsGroupRegistUpdateProcess(Map<String, Object> commonCsvInfoMap,
                                                 GoodsGroupDto goodsGroupDto,
                                                 List<GoodsRelationEntity> goodsRelationEntityList,
                                                 // 2023-renew No21 from here
                                                 List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                                 // 2023-renew No21 to here
                                                 GoodsEntity commonGoodsInfo,
                                                 boolean emotionFlg,
                                                 String administratorName) {

        // 商品グループDtoの各規格情報に規格共通情報を設定する
        setupCommonGoodsInfo(goodsGroupDto, commonGoodsInfo);

        if (emotionFlg) {
            // 商品グループ登録更新処理（心意気商品登録用）
            goodsGroupRegistUpdateService.execute(goodsGroupDto, goodsRelationEntityList,
                                                  // 2023-renew No21 from here
                                                  goodsTogetherBuyGroupEntityList,
                                                  // 2023-renew No21 to here
                                                  null, GoodsGroupRegistUpdateService.PROCESS_TYPE_FROM_CSV, emotionFlg,
                                                  administratorName
                                                 );
        } else {
            // 商品グループDto相関チェック
            goodsGroupCorrelationDataCheckService.execute(goodsGroupDto, goodsRelationEntityList,
                                                          // 2023-renew No21 from here
                                                          goodsTogetherBuyGroupEntityList,
                                                          // 2023-renew No21 to here
                                                          GoodsGroupCorrelationDataCheckService.PROCESS_TYPE_FROM_CSV,
                                                          commonCsvInfoMap
                                                         );

            // 商品グループ登録更新処理
            goodsGroupRegistUpdateService.execute(goodsGroupDto, goodsRelationEntityList,
                                                  // 2023-renew No21 from here
                                                  goodsTogetherBuyGroupEntityList,
                                                  // 2023-renew No21 to here
                                                  null, GoodsGroupRegistUpdateService.PROCESS_TYPE_FROM_CSV,
                                                  administratorName
                                                 );

            // 商品規格表示順チェック
            goodsOrderDisplayCheckLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode()
                                               );
        }
    }

    /**
     * 商品グループ情報取得処理
     * 商品グループコード → 商品グループDto
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsGroupCode   商品グループコード
     * @param siteType         サイト種別
     * @return 商品グループDto
     */
    protected GoodsGroupDto goodsGroupInfoGetProcess(Map<String, Object> commonCsvInfoMap,
                                                     String goodsGroupCode,
                                                     HTypeSiteType siteType) {

        // 商品グループコードをもとに商品グループDtoを取得する
        GoodsGroupDto goodsGroupDto = goodsGroupGetService.execute(goodsGroupCode, siteType);
        if (goodsGroupDto == null) {
            goodsGroupDto = ApplicationContextUtility.getBean(GoodsGroupDto.class);
        }
        if (goodsGroupDto.getGoodsGroupEntity() == null) {
            goodsGroupDto.setGoodsGroupEntity(getComponent(GoodsGroupEntity.class));
        }
        if (goodsGroupDto.getGoodsGroupDisplayEntity() == null) {
            goodsGroupDto.setGoodsGroupDisplayEntity(getComponent(GoodsGroupDisplayEntity.class));
        }
        if (goodsGroupDto.getGoodsGroupImageEntityList() == null) {
            goodsGroupDto.setGoodsGroupImageEntityList(new ArrayList<>());
        }
        if (goodsGroupDto.getGoodsDtoList() == null) {
            goodsGroupDto.setGoodsDtoList(new ArrayList<>());
        }
        if (goodsGroupDto.getCategoryGoodsEntityList() == null) {
            goodsGroupDto.setCategoryGoodsEntityList(new ArrayList<>());
        }

        return goodsGroupDto;
    }

    /**
     * 関連商品情報リスト取得処理
     * 商品グループSEQ → 関連商品Dtoリスト
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsGroupSeq    商品グループSEQ
     * @return 関連商品エンティティリスト
     */
    protected List<GoodsRelationEntity> goodsRelationInfoGetProcess(Map<String, Object> commonCsvInfoMap,
                                                                    Integer goodsGroupSeq) {

        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        // 商品グループSEQをもとに関連商品エンティティリストを取得する。関連商品がなければ空リストを設定する。
        if (goodsGroupSeq != null) {
            goodsRelationEntityList = goodsRelationListGetForBackService.execute(goodsGroupSeq);
        }

        // 商品グループ単位で更新するCSVアップロード共通情報の編集
        // CSVアップロード共通情報の商品グループSEQを更新する
        commonCsvInfoMap.put("goodsGroupSeq", goodsGroupSeq);
        // CSVアップロード共通情報の商品コードとCSV行の対応マップをクリアする
        commonCsvInfoMap.put("goodsCodeAndRecodeCountMap", new HashMap<String, Integer>());
        // 商品グループ情報CSV行に現在行を設定する
        commonCsvInfoMap.put("goodsGroupInfoRecordCount", commonCsvInfoMap.get("recodeCount"));
        return goodsRelationEntityList;
    }

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品情報リスト取得処理
     * 商品グループSEQ → よく一緒に購入される商品Dtoリスト
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsGroupSeq    商品グループSEQ
     * @return よく一緒に購入される商品エンティティリスト
     */
    protected List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupInfoGetProcess(Map<String, Object> commonCsvInfoMap,
                                                                                    Integer goodsGroupSeq) {

        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList = new ArrayList<>();
        // 商品グループSEQをもとに関連商品エンティティリストを取得する。関連商品がなければ空リストを設定する。
        if (goodsGroupSeq != null) {
            goodsTogetherBuyGroupEntityList = goodsTogetherBuyGroupListGetForBackService.execute(goodsGroupSeq);
        }

        // 商品グループ単位で更新するCSVアップロード共通情報の編集
        // CSVアップロード共通情報の商品グループSEQを更新する
        commonCsvInfoMap.put("goodsGroupSeq", goodsGroupSeq);
        // CSVアップロード共通情報の商品コードとCSV行の対応マップをクリアする
        commonCsvInfoMap.put("goodsCodeAndRecodeCountMap", new HashMap<String, Integer>());
        // 商品グループ情報CSV行に現在行を設定する
        commonCsvInfoMap.put("goodsGroupInfoRecordCount", commonCsvInfoMap.get("recodeCount"));
        return goodsTogetherBuyGroupEntityList;
    }
    // 2023-renew No21 to here

    /**
     * 商品グループDtoの各規格情報に規格共通情報を設定
     * 規格共通情報オブジェクト → goodsGroupDto
     *
     * @param goodsGroupDto   商品グループDto
     * @param commonGoodsInfo 規格共通情報
     */
    protected void setupCommonGoodsInfo(GoodsGroupDto goodsGroupDto, GoodsEntity commonGoodsInfo) {

        // 規格共通情報を全規格に設定する
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            GoodsEntity goodsEntity = goodsDto.getGoodsEntity();
            goodsEntity.setFreeDeliveryFlag(commonGoodsInfo.getFreeDeliveryFlag());
            goodsEntity.setIndividualDeliveryType(commonGoodsInfo.getIndividualDeliveryType());
            goodsEntity.setUnitManagementFlag(commonGoodsInfo.getUnitManagementFlag());
            goodsEntity.setStockManagementFlag(commonGoodsInfo.getStockManagementFlag());
        }
    }

    /**
     * 商品グループ内の各規格共通オブジェクトを編集
     * goodsGroupDto → 規格共通情報オブジェクト
     *
     * @param goodsCode     商品コード
     * @param goodsGroupDto 商品グループDto
     * @return 規格共通情報
     */
    protected GoodsEntity editCommonGoodsInfo(String goodsCode, GoodsGroupDto goodsGroupDto) {

        // 規格共通情報
        GoodsEntity commonGoodsInfo = getComponent(GoodsEntity.class);
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            if (goodsCode.equals(goodsDto.getGoodsEntity().getGoodsCode())) {
                // 規格共通情報の設定
                commonGoodsInfo.setFreeDeliveryFlag(goodsDto.getGoodsEntity().getFreeDeliveryFlag());
                commonGoodsInfo.setIndividualDeliveryType(goodsDto.getGoodsEntity().getIndividualDeliveryType());
                commonGoodsInfo.setUnitManagementFlag(goodsDto.getGoodsEntity().getUnitManagementFlag());
                commonGoodsInfo.setStockManagementFlag(goodsDto.getGoodsEntity().getStockManagementFlag());
                break;
            }
        }
        return commonGoodsInfo;
    }

    /**
     * 商品グループ情報を編集
     *
     * @param commonCsvInfoMap        CSVアップロード共通情報
     * @param goodsCsvDto             商品CSVDto
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     */
    // PDR Migrate Customization from here
    protected void goodsGroupInfoEditProcess(Map<String, Object> commonCsvInfoMap,
                                             GoodsCsvDto goodsCsvDto,
                                             GoodsGroupDto goodsGroupDto,
                                             List<GoodsRelationEntity> goodsRelationEntityList,
                                             // 2023-renew No21 from here
                                             List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList) {
        // 2023-renew No21 to here
        // PDR Migrate Customization to here
        // アップロード種別を取得する
        HTypeUploadType uploadType = (HTypeUploadType) commonCsvInfoMap.get("uploadType");
        // 更新対象項目リストを取得する
        @SuppressWarnings("unchecked")
        List<String> updateColumn = (List<String>) commonCsvInfoMap.get("updateColumn");

        // CSVDtoのうち、更新対象項目の商品グループ情報を商品グループDtoに反映する
        setupGoodsGroupDto(commonCsvInfoMap, goodsCsvDto, uploadType, updateColumn, goodsGroupDto);

        // CSVDtoの関連商品設定を関連商品Dtoリストに反映する
        setupGoodsRelationDtoList(commonCsvInfoMap, goodsCsvDto, uploadType, updateColumn, goodsRelationEntityList);

        // 2023-renew No21 from here
        // CSVDtoのよく一緒に購入される商品設定をよく一緒に購入される商品Dtoリストに反映する
        setupGoodsTogetherBuyGroupDtoList(commonCsvInfoMap, goodsCsvDto, uploadType, updateColumn,
                                          goodsTogetherBuyGroupEntityList
                                         );
        // 2023-renew No21 to here
    }

    /**
     * 商品グループ情報設定
     * (編集前)商品グループDto + 商品CSVDto + 更新対象項目リスト ⇒ 商品グループDto
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsCsvDto      商品CSVDto
     * @param uploadType       アップロード種別
     * @param updateColumn     更新対象項目リスト
     * @param goodsGroupDto    商品グループDto
     */
    protected void setupGoodsGroupDto(Map<String, Object> commonCsvInfoMap,
                                      GoodsCsvDto goodsCsvDto,
                                      HTypeUploadType uploadType,
                                      List<String> updateColumn,
                                      GoodsGroupDto goodsGroupDto) {

        // CSVDtoのうち、更新対象項目データを商品グループDtoの商品グループ部に反映する

        // -------------------------------
        // 商品グループEntityの設定
        // -------------------------------
        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();

        // ショップSEQを取得し設定（無条件で設定）
        Integer shopSeq = 1001;
        goodsGroupEntity.setShopSeq(shopSeq);

        // 商品管理番号
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupCode")) {
            goodsGroupEntity.setGoodsGroupCode(goodsCsvDto.getGoodsGroupCode());
        }
        // 2023-renew No64 from here
        // 商品名（管理用）
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupNameAdmin")) {
            goodsGroupEntity.setGoodsGroupNameAdmin(goodsCsvDto.getGoodsGroupNameAdmin());
        }
        // 商品名
        if (HTypeUploadType.REGIST.equals(uploadType)) {
            // 追加モード時のみ設定
            goodsGroupEntity.setGoodsGroupName(goodsCsvDto.getGoodsGroupNameAdmin());
        }
        // 商品名１
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupName1")) {
            goodsGroupEntity.setGoodsGroupName1(goodsCsvDto.getGoodsGroupName1());
        }
        // 商品名_公開開始日時
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupName1OpenStartTime")) {
            goodsGroupEntity.setGoodsGroupName1OpenStartTime(goodsCsvDto.getGoodsGroupName1OpenStartTime());
        }
        // 商品名２
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupName2")) {
            goodsGroupEntity.setGoodsGroupName2(goodsCsvDto.getGoodsGroupName2());
        }
        // 商品名２公開開始日時
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsGroupName2OpenStartTime")) {
            goodsGroupEntity.setGoodsGroupName2OpenStartTime(goodsCsvDto.getGoodsGroupName2OpenStartTime());
        }
        //2023-renew No64 to here
        // 公開状態、公開開始日時、公開終了日時
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsOpenStatusPC")) {
            goodsGroupEntity.setGoodsOpenStatusPC(goodsCsvDto.getGoodsOpenStatusPC());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("openStartTimePC")) {
            goodsGroupEntity.setOpenStartTimePC(goodsCsvDto.getOpenStartTimePC());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("openEndTimePC")) {
            goodsGroupEntity.setOpenEndTimePC(goodsCsvDto.getOpenEndTimePC());
        }
        // 酒類フラグ
        if (HTypeUploadType.REGIST.equals(uploadType)) {
            goodsGroupEntity.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL);
        }
        // 税率
        if (HTypeUploadType.REGIST.equals(uploadType)) {
            goodsGroupEntity.setTaxRate(new BigDecimal(10));
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsTaxType")) {
            // 商品消費税種別・・・外税固定
            goodsGroupEntity.setGoodsTaxType(HTypeGoodsTaxType.OUT_TAX);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsPreDiscountPrice")) {
            goodsGroupEntity.setGoodsPreDiscountPrice(goodsCsvDto.getGoodsPreDiscountPrice());
        }
        // PDR Migrate Customization from here
        // 新規項目を商品グループエンティティに設定
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsClassType")) {
            goodsGroupEntity.setGoodsClassType(goodsCsvDto.getGoodsClassType());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("catalogDisplayOrder")) {
            goodsGroupEntity.setCatalogDisplayOrder(goodsCsvDto.getCatalogDisplayOrder());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("dentalMonopolySalesFlg")) {
            goodsGroupEntity.setDentalMonopolySalesFlg(goodsCsvDto.getDentalMonopolySalesFlg());
        }
        // 新規登録かつ商品グループ内の対象項目がnullの場合システム値登録
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsGroupEntity.getSnsLinkFlag() == null) {
            goodsGroupEntity.setSnsLinkFlag(SYSTEM_PRAM_SNSLINKFLAG);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsGroupEntity.getWhatsnewDate() == null) {
            goodsGroupEntity.setWhatsnewDate(SYSTEM_PRAM_WHATSNEWDATE);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsGroupEntity.getGroupPrice() == null) {
            goodsGroupEntity.setGroupPrice(SYSTEM_PRAM_PRICE_MAX);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsGroupEntity.getGroupPriceMarkDispFlag() == null) {
            goodsGroupEntity.setGroupPriceMarkDispFlag(SYSTEM_PRAM_GROUPPRICEMARKDISPFLAG);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsGroupEntity.getGroupSalePriceMarkDispFlag() == null) {
            goodsGroupEntity.setGroupSalePriceMarkDispFlag(SYSTEM_PRAM_GROUPSALEPRICEMARKDISPFLAG);
        }
        // PDR Migrate Customization to here
        // -------------------------------
        // 商品グループ表示Entityの設定
        // -------------------------------
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
        // 商品説明01～10
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote1")) {
            goodsGroupDisplayEntity.setGoodsNote1(goodsCsvDto.getGoodsNote1());
        }
        //2023-renew No64 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote1")) {
            goodsGroupDisplayEntity.setGoodsNote1(goodsCsvDto.getGoodsNote1());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote1Sub")) {
            goodsGroupDisplayEntity.setGoodsNote1Sub(goodsCsvDto.getGoodsNote1Sub());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote1OpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote1OpenStartTime(goodsCsvDto.getGoodsNote1OpenStartTime());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote1SubOpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote1SubOpenStartTime(goodsCsvDto.getGoodsNote1SubOpenStartTime());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote2")) {
            goodsGroupDisplayEntity.setGoodsNote2(goodsCsvDto.getGoodsNote2());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote2Sub")) {
            goodsGroupDisplayEntity.setGoodsNote2Sub(goodsCsvDto.getGoodsNote2Sub());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote2OpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote2OpenStartTime(goodsCsvDto.getGoodsNote2OpenStartTime());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote2SubOpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote2SubOpenStartTime(goodsCsvDto.getGoodsNote2SubOpenStartTime());
        }
        //2023-renew No64 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote3")) {
            goodsGroupDisplayEntity.setGoodsNote3(goodsCsvDto.getGoodsNote3());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote4")) {
            goodsGroupDisplayEntity.setGoodsNote4(goodsCsvDto.getGoodsNote4());
        }
        //2023-renew No64 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote4Sub")) {
            goodsGroupDisplayEntity.setGoodsNote4Sub(goodsCsvDto.getGoodsNote4Sub());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote4OpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote4OpenStartTime(goodsCsvDto.getGoodsNote4OpenStartTime());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote4SubOpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote4SubOpenStartTime(goodsCsvDto.getGoodsNote4SubOpenStartTime());
        }
        //2023-renew No64 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote5")) {
            goodsGroupDisplayEntity.setGoodsNote5(goodsCsvDto.getGoodsNote5());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote6")) {
            goodsGroupDisplayEntity.setGoodsNote6(goodsCsvDto.getGoodsNote6());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote7")) {
            goodsGroupDisplayEntity.setGoodsNote7(goodsCsvDto.getGoodsNote7());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote8")) {
            goodsGroupDisplayEntity.setGoodsNote8(goodsCsvDto.getGoodsNote8());
        }
        // 2023-renew No0 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote9")) {
            goodsGroupDisplayEntity.setGoodsNote9(goodsCsvDto.getGoodsNote9());
        }
        // 2023-renew No0 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote10")) {
            goodsGroupDisplayEntity.setGoodsNote10(goodsCsvDto.getGoodsNote10());
        }
        //2023-renew No64 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote10Sub")) {
            goodsGroupDisplayEntity.setGoodsNote10Sub(goodsCsvDto.getGoodsNote10Sub());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote10OpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote10OpenStartTime(goodsCsvDto.getGoodsNote10OpenStartTime());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote10SubOpenStartTime")) {
            goodsGroupDisplayEntity.setGoodsNote10SubOpenStartTime(goodsCsvDto.getGoodsNote10SubOpenStartTime());
        }
        //2023-renew No64 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote11")) {
            goodsGroupDisplayEntity.setGoodsNote11(goodsCsvDto.getGoodsNote11());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote12")) {
            goodsGroupDisplayEntity.setGoodsNote12(goodsCsvDto.getGoodsNote12());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote13")) {
            goodsGroupDisplayEntity.setGoodsNote13(goodsCsvDto.getGoodsNote13());
        }
        // 2023-renew No0 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote14")) {
            goodsGroupDisplayEntity.setGoodsNote14(goodsCsvDto.getGoodsNote14());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote15")) {
            goodsGroupDisplayEntity.setGoodsNote15(goodsCsvDto.getGoodsNote15());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote16")) {
            goodsGroupDisplayEntity.setGoodsNote16(goodsCsvDto.getGoodsNote16());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote17")) {
            goodsGroupDisplayEntity.setGoodsNote17(goodsCsvDto.getGoodsNote17());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote18")) {
            goodsGroupDisplayEntity.setGoodsNote18(goodsCsvDto.getGoodsNote18());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote19")) {
            goodsGroupDisplayEntity.setGoodsNote19(goodsCsvDto.getGoodsNote19());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote20")) {
            goodsGroupDisplayEntity.setGoodsNote20(goodsCsvDto.getGoodsNote20());
        }
        // 2023-renew No11 from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote21")) {
            goodsGroupDisplayEntity.setGoodsNote21(goodsCsvDto.getGoodsNote21());
        }
        // 2023-renew No11 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsNote22")) {
            goodsGroupDisplayEntity.setGoodsNote22(goodsCsvDto.getGoodsNote22());
        }
        // 2023-renew No0 to here
        // 規格管理フラグ
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitManagementFlag")) {
            goodsGroupDisplayEntity.setUnitManagementFlag(goodsCsvDto.getUnitManagementFlag());
        }
        // 規格1・2表示名
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitTitle1")) {
            goodsGroupDisplayEntity.setUnitTitle1(goodsCsvDto.getUnitTitle1());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitTitle2")) {
            goodsGroupDisplayEntity.setUnitTitle2(goodsCsvDto.getUnitTitle2());
        }
        // PDR Migrate Customization from here
        // 受注連携設定01～10 REMOVED
        // PDR Migrate Customization to here

        // 2023-renew searchKeyword-addition from here
        // 索引用語
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("searchKeyword")) {
            goodsGroupDisplayEntity.setSearchKeyword(goodsCsvDto.getSearchKeyword());
        }
        // 2023-renew searchKeyword-addition to here

        // -------------------------------
        // カテゴリ登録商品Entityリストの設定
        // -------------------------------
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("categoryGoodsSetting")) {
            setupCategoryGoodsEntityList(commonCsvInfoMap, goodsCsvDto, goodsGroupDto.getCategoryGoodsEntityList());
        }
    }

    /**
     * 関連商品情報設定
     * (編集前)関連商品Dtoリスト + 商品CSVDto + 更新対象項目リスト ⇒ 関連商品Dtoリスト
     *
     * @param commonCsvInfoMap        CSVアップロード共通情報
     * @param goodsCsvDto             商品CSVDto
     * @param uploadType              アップロード種別
     * @param updateColumn            更新対象項目リスト
     * @param goodsRelationEntityList 関連商品エンティティリスト
     */
    protected void setupGoodsRelationDtoList(Map<String, Object> commonCsvInfoMap,
                                             GoodsCsvDto goodsCsvDto,
                                             HTypeUploadType uploadType,
                                             List<String> updateColumn,
                                             List<GoodsRelationEntity> goodsRelationEntityList) {

        // 関連商品DtoリストのcopyObjectへディープコピーするを行う
        List<GoodsRelationEntity> copyGoodsRelationDtoList = new ArrayList<>();
        for (int i = 0; goodsRelationEntityList != null && i < goodsRelationEntityList.size(); i++) {
            copyGoodsRelationDtoList.add(CopyUtil.deepCopy(goodsRelationEntityList.get(i)));
        }

        // ショップSEQを取得
        Integer shopSeq = 1001;
        // 商品グループSEQを取得
        Integer goodsGroupSeq = (Integer) commonCsvInfoMap.get("goodsGroupSeq");

        // 以下の場合は関連商品Dtoリストを編集しない
        // 関連商品設定が更新対象項目のデータに含まれていない場合
        if (HTypeUploadType.REGIST != uploadType && !updateColumn.contains("goodsRelationGoodsGroupCode")) {
            return;
        }
        // 関連商品Dtoリストをクリアする
        goodsRelationEntityList.clear();

        // 商品グループ新規登録かつ、関連商品登録なしの場合
        if (goodsGroupSeq == null && StringUtils.isEmpty(goodsCsvDto.getGoodsRelationGoodsGroupCode())) {
            return;
        }

        // CSVから取得した関連商品グループコードをリスト化する
        String[] goodsRelationGoodsGroupCodeList = new String[] {};
        if (StringUtils.isNotEmpty(goodsCsvDto.getGoodsRelationGoodsGroupCode())) {
            goodsRelationGoodsGroupCodeList = goodsCsvDto.getGoodsRelationGoodsGroupCode().split("/");
            // 関連商品設定で0件を指定したとき
            if (goodsRelationGoodsGroupCodeList.length == 0) {
                // 空のリストを関連商品Dtoに設定して終了
                return;
            }

            // 関連商品Dtoリストを編集する
            List<String> strGoodsRelationGoodsGroupCodeList = Arrays.asList(goodsRelationGoodsGroupCodeList);
            List<String> repetitionRelationGoodsList = new ArrayList<>();
            // 自身の商品が複数関連商品に登録されているときに自商品エラーが表示されるようにする
            // 自商品が重複しているかを確認するための商品グループコードリスト
            List<String> sameGoodsList = new ArrayList<>();

            // CSVの関連商品グループコードリストをもとに、商品グループマップを取得
            Map<String, GoodsGroupEntity> goodsGroupEntityMap = new HashMap<>();
            if (goodsRelationGoodsGroupCodeList.length != 0) {
                goodsGroupEntityMap = goodsGroupMapGetByCodeLogic.execute(shopSeq,
                                                                          Arrays.asList(goodsRelationGoodsGroupCodeList)
                                                                         );
            }
            // CSVの関連商品_商品管理番号IDリストごとにDtoを編集する
            for (int i = 0;
                 goodsRelationGoodsGroupCodeList != null && i < goodsRelationGoodsGroupCodeList.length; i++) {
                String goodsRelationGoodsGroupCode = goodsRelationGoodsGroupCodeList[i];

                if (strGoodsRelationGoodsGroupCodeList.indexOf(goodsRelationGoodsGroupCode)
                    != strGoodsRelationGoodsGroupCodeList.lastIndexOf(goodsRelationGoodsGroupCode)
                    && !repetitionRelationGoodsList.contains(goodsRelationGoodsGroupCode)) {
                    // 登録関連商品重複エラー
                    addErrorMessage(RELATIONGOODS_REPETITION_FAIL,
                                    new Object[] {goodsRelationGoodsGroupCode, null, null}
                                   );
                    repetitionRelationGoodsList.add(goodsRelationGoodsGroupCode);
                    continue;
                }
                if (goodsRelationGoodsGroupCode.equals(goodsCsvDto.getGoodsGroupCode())) {
                    // 複数個登録されていた場合一つのエラーとして表示する
                    if (!sameGoodsList.contains(goodsRelationGoodsGroupCode)) {
                        // 自身を関連商品に登録エラー
                        addErrorMessage(RELATIONGOODS_MYSELF_FAIL,
                                        new Object[] {goodsRelationGoodsGroupCode, null, null}
                                       );
                        sameGoodsList.add(goodsRelationGoodsGroupCode);
                    }
                    continue;
                }

                // 関連商品Dtoの編集
                if (i < copyGoodsRelationDtoList.size() && goodsRelationGoodsGroupCode.equals(
                                copyGoodsRelationDtoList.get(i).getGoodsGroupCode())) {
                    // 商品コードと表示順が一致した場合は戻り値用リストにそのまま追加
                    goodsRelationEntityList.add(copyGoodsRelationDtoList.get(i));
                } else {
                    // 商品コードまたは表示順が異なる場合
                    GoodsRelationEntity newGoodsRelationEntity =
                                    ApplicationContextUtility.getBean(GoodsRelationEntity.class);
                    if (goodsGroupEntityMap.get(goodsRelationGoodsGroupCode) == null
                        || HTypeOpenDeleteStatus.DELETED == goodsGroupEntityMap.get(goodsRelationGoodsGroupCode)
                                                                               .getGoodsOpenStatusPC()) {
                        // 存在しない関連商品グループコードエラー(PCが削除ならMBも削除だから、片方だけみる）
                        addErrorMessage(RELATIONGOODS_NONE_FAIL,
                                        new Object[] {goodsRelationGoodsGroupCodeList[i], null, null}
                                       );
                        continue;
                    }
                    newGoodsRelationEntity.setGoodsGroupCode(
                                    goodsGroupEntityMap.get(goodsRelationGoodsGroupCode).getGoodsGroupCode());
                    // 2023-renew No64 from here
                    newGoodsRelationEntity.setGoodsGroupNameAdmin(
                                    goodsGroupEntityMap.get(goodsRelationGoodsGroupCode).getGoodsGroupNameAdmin());
                    // 2023-renew No64 to here
                    newGoodsRelationEntity.setGoodsOpenStatusPC(
                                    goodsGroupEntityMap.get(goodsRelationGoodsGroupCode).getGoodsOpenStatusPC());
                    newGoodsRelationEntity.setGoodsGroupSeq(goodsGroupSeq);
                    newGoodsRelationEntity.setGoodsRelationGroupSeq(
                                    goodsGroupEntityMap.get(goodsRelationGoodsGroupCode).getGoodsGroupSeq());
                    newGoodsRelationEntity.setOrderDisplay(i + 1);
                    if (i < copyGoodsRelationDtoList.size()) {
                        newGoodsRelationEntity.setRegistTime(copyGoodsRelationDtoList.get(i).getRegistTime());
                    }
                    goodsRelationEntityList.add(newGoodsRelationEntity);
                }
            }
            if (goodsRelationGoodsGroupCodeList.length > PropertiesUtil.getSystemPropertiesValueToInt(
                            "goods.relation.amount")) {
                // 関連商品登録数超過エラー
                addErrorMessage(RELATIONGOODS_OVER_FAIL,
                                new Object[] {PropertiesUtil.getSystemPropertiesValue("goods.relation.amount"), null,
                                                null}
                               );
            }
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品情報設定
     * (編集前)よく一緒に購入される商品Dtoリスト + 商品CSVDto + 更新対象項目リスト ⇒ よく一緒に購入される商品Dtoリスト
     *
     * @param commonCsvInfoMap        CSVアップロード共通情報
     * @param goodsCsvDto             商品CSVDto
     * @param uploadType              アップロード種別
     * @param updateColumn            更新対象項目リスト
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     */
    protected void setupGoodsTogetherBuyGroupDtoList(Map<String, Object> commonCsvInfoMap,
                                                     GoodsCsvDto goodsCsvDto,
                                                     HTypeUploadType uploadType,
                                                     List<String> updateColumn,
                                                     List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList) {

        // よく一緒に購入される商品DtoリストのcopyObjectへディープコピーするを行う
        List<GoodsTogetherBuyGroupEntity> copyGoodsTogetherBuyGroupEntityList = new ArrayList<>();
        for (int i = 0; goodsTogetherBuyGroupEntityList != null && i < goodsTogetherBuyGroupEntityList.size(); i++) {
            copyGoodsTogetherBuyGroupEntityList.add(CopyUtil.deepCopy(goodsTogetherBuyGroupEntityList.get(i)));
        }

        // ショップSEQを取得
        Integer shopSeq = 1001;
        // 商品グループSEQを取得
        Integer goodsGroupSeq = (Integer) commonCsvInfoMap.get("goodsGroupSeq");

        // 以下の場合はよく一緒に購入される商品Dtoリストを編集しない
        // よく一緒に購入される商品設定が更新対象項目のデータに含まれていない場合
        if (HTypeUploadType.REGIST != uploadType && !updateColumn.contains("goodsTogetherBuyGroupGoodsGroupCode")) {
            return;
        }
        // よく一緒に購入される商品Dtoリストをクリアする
        goodsTogetherBuyGroupEntityList.clear();

        // 商品グループ新規登録かつ、よく一緒に購入される商品登録なしの場合
        if (goodsGroupSeq == null && StringUtils.isEmpty(goodsCsvDto.getGoodsTogetherBuyGroupGoodsGroupCode())) {
            return;
        }

        // CSVから取得したよく一緒に購入される商品グループコードをリスト化する
        String[] goodsTogetherBuyGroupGoodsGroupCodeList = new String[] {};
        if (StringUtils.isNotEmpty(goodsCsvDto.getGoodsTogetherBuyGroupGoodsGroupCode())) {
            goodsTogetherBuyGroupGoodsGroupCodeList = goodsCsvDto.getGoodsTogetherBuyGroupGoodsGroupCode().split("/");
            // よく一緒に購入される商品設定で0件を指定したとき
            if (goodsTogetherBuyGroupGoodsGroupCodeList.length == 0) {
                // 空のリストをよく一緒に購入される商品Dtoに設定して終了
                return;
            }

            // よく一緒に購入される商品Dtoリストを編集する
            List<String> strGoodsTogetherBuyGroupGoodsGroupCodeList =
                            Arrays.asList(goodsTogetherBuyGroupGoodsGroupCodeList);
            List<String> repetitionGoodsTogetherBuyGroupGoodsList = new ArrayList<>();
            // 自身の商品が複数よく一緒に購入される商品に登録されているときに自商品エラーが表示されるようにする
            // 自商品が重複しているかを確認するための商品グループコードリスト
            List<String> sameGoodsList = new ArrayList<>();

            // CSVのよく一緒に購入される商品グループコードリストをもとに、商品グループマップを取得
            Map<String, GoodsGroupEntity> goodsGroupEntityMap = new HashMap<>();
            if (goodsTogetherBuyGroupGoodsGroupCodeList.length != 0) {
                goodsGroupEntityMap = goodsGroupMapGetByCodeLogic.execute(shopSeq,
                                                                          Arrays.asList(goodsTogetherBuyGroupGoodsGroupCodeList)
                                                                         );
            }
            // CSVのよく一緒に購入される商品_商品管理番号IDリストごとにDtoを編集する
            for (int i = 0; goodsTogetherBuyGroupGoodsGroupCodeList != null
                            && i < goodsTogetherBuyGroupGoodsGroupCodeList.length; i++) {
                String goodsTogetherBuyGroupGoodsGroupCode = goodsTogetherBuyGroupGoodsGroupCodeList[i];

                if (strGoodsTogetherBuyGroupGoodsGroupCodeList.indexOf(goodsTogetherBuyGroupGoodsGroupCode)
                    != strGoodsTogetherBuyGroupGoodsGroupCodeList.lastIndexOf(goodsTogetherBuyGroupGoodsGroupCode)
                    && !repetitionGoodsTogetherBuyGroupGoodsList.contains(goodsTogetherBuyGroupGoodsGroupCode)) {
                    // 登録よく一緒に購入される商品重複エラー
                    addErrorMessage(GOODSTOGETHERBUYGROUP_REPETITION_FAIL,
                                    new Object[] {goodsTogetherBuyGroupGoodsGroupCode, null, null}
                                   );
                    repetitionGoodsTogetherBuyGroupGoodsList.add(goodsTogetherBuyGroupGoodsGroupCode);
                    continue;
                }
                if (goodsTogetherBuyGroupGoodsGroupCode.equals(goodsCsvDto.getGoodsGroupCode())) {
                    // 複数個登録されていた場合一つのエラーとして表示する
                    if (!sameGoodsList.contains(goodsTogetherBuyGroupGoodsGroupCode)) {
                        // 自身をよく一緒に購入される商品に登録エラー
                        addErrorMessage(GOODSTOGETHERBUYGROUP_MYSELF_FAIL,
                                        new Object[] {goodsTogetherBuyGroupGoodsGroupCode, null, null}
                                       );
                        sameGoodsList.add(goodsTogetherBuyGroupGoodsGroupCode);
                    }
                    continue;
                }

                // よく一緒に購入される商品Dtoの編集
                if (i < copyGoodsTogetherBuyGroupEntityList.size() && goodsTogetherBuyGroupGoodsGroupCode.equals(
                                copyGoodsTogetherBuyGroupEntityList.get(i).getGoodsGroupCode())) {
                    // 商品コードと表示順が一致した場合は戻り値用リストにそのまま追加
                    goodsTogetherBuyGroupEntityList.add(copyGoodsTogetherBuyGroupEntityList.get(i));
                } else {
                    // 商品コードまたは表示順が異なる場合
                    GoodsTogetherBuyGroupEntity newGoodsTogetherBuyGroupEntity =
                                    ApplicationContextUtility.getBean(GoodsTogetherBuyGroupEntity.class);
                    if (goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode) == null
                        || HTypeOpenDeleteStatus.DELETED == goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode)
                                                                               .getGoodsOpenStatusPC()) {
                        // 存在しないよく一緒に購入される商品グループコードエラー(PCが削除ならMBも削除だから、片方だけみる）
                        addErrorMessage(GOODSTOGETHERBUYGROUP_NONE_FAIL,
                                        new Object[] {goodsTogetherBuyGroupGoodsGroupCodeList[i], null, null}
                                       );
                        continue;
                    }
                    newGoodsTogetherBuyGroupEntity.setGoodsGroupCode(
                                    goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode).getGoodsGroupCode());
                    newGoodsTogetherBuyGroupEntity.setGoodsGroupName(
                                    goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode)
                                                       .getGoodsGroupNameAdmin());
                    newGoodsTogetherBuyGroupEntity.setGoodsOpenStatusPC(
                                    goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode)
                                                       .getGoodsOpenStatusPC());
                    newGoodsTogetherBuyGroupEntity.setGoodsGroupSeq(goodsGroupSeq);
                    newGoodsTogetherBuyGroupEntity.setGoodsTogetherBuyGroupSeq(
                                    goodsGroupEntityMap.get(goodsTogetherBuyGroupGoodsGroupCode).getGoodsGroupSeq());
                    newGoodsTogetherBuyGroupEntity.setOrderDisplay(i + 1);
                    if (i < copyGoodsTogetherBuyGroupEntityList.size()) {
                        newGoodsTogetherBuyGroupEntity.setRegistTime(
                                        copyGoodsTogetherBuyGroupEntityList.get(i).getRegistTime());
                    }
                    goodsTogetherBuyGroupEntityList.add(newGoodsTogetherBuyGroupEntity);
                }
            }
            if (goodsTogetherBuyGroupGoodsGroupCodeList.length > PropertiesUtil.getSystemPropertiesValueToInt(
                            "goods.togetherbuy.amount")) {
                // よく一緒に購入される商品登録数超過エラー
                addErrorMessage(GOODSTOGETHERBUYGROUP_OVER_FAIL,
                                new Object[] {PropertiesUtil.getSystemPropertiesValue("goods.togetherbuy.amount"), null,
                                                null}
                               );
            }
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }
    // 2023-renew No21 to here

    /**
     * 商品情報を編集
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsCsvDto      商品CSVDto
     * @param goodsGroupDto    商品グループDto
     */
    @SuppressWarnings("unchecked")
    protected void goodsInfoEditProcess(Map<String, Object> commonCsvInfoMap,
                                        GoodsCsvDto goodsCsvDto,
                                        GoodsGroupDto goodsGroupDto) {

        // アップロード種別を取得する
        HTypeUploadType uploadType = (HTypeUploadType) commonCsvInfoMap.get("uploadType");
        // 更新対象項目リストを取得する
        List<String> updateColumn = (List<String>) commonCsvInfoMap.get("updateColumn");
        // 商品コードとCSV行の対応マップに追加
        Map<String, Integer> goodsCodeAndRecodeCountMap =
                        (Map<String, Integer>) commonCsvInfoMap.get("goodsCodeAndRecodeCountMap");
        goodsCodeAndRecodeCountMap.put(goodsCsvDto.getGoodsCode(), (Integer) commonCsvInfoMap.get("recodeCount"));
        Map<String, Integer> goodsCodeAndIndexMap = (Map<String, Integer>) commonCsvInfoMap.get("goodsCodeAndIndexMap");
        goodsCodeAndIndexMap.put(goodsCsvDto.getGoodsCode(), (Integer) commonCsvInfoMap.get("recodeCount"));
        // CSVDtoのうち、更新対象項目の商品情報を商品Dtoに反映する
        setupGoodsDto(commonCsvInfoMap, goodsCsvDto, uploadType, updateColumn, goodsGroupDto);
    }

    /**
     * 商品情報設定
     * (編集前)商品グループDto + 商品CSVDto + 更新対象項目リスト ⇒ 商品グループDto
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param goodsCsvDto      商品CSVDto
     * @param uploadType       アップロード種別
     * @param updateColumn     更新対象項目リスト
     * @param goodsGroupDto    商品グループDto
     */
    protected void setupGoodsDto(Map<String, Object> commonCsvInfoMap,
                                 GoodsCsvDto goodsCsvDto,
                                 HTypeUploadType uploadType,
                                 List<String> updateColumn,
                                 GoodsGroupDto goodsGroupDto) {

        // CSVDtoのうち、更新対象項目データを商品グループDtoの規格情報に反映する

        // ショップSEQを取得
        Integer shopSeq = 1001;

        // PDR Migrate Customization from here
        // 商品CsvDtoをカスタマイズクラスに変換
        // アノテーションを変更しただけの同名項目であっても別フィールド扱いとなるため、カスタマイズしたクラスから値を取得する

        if (HTypeEmotionPriceType.EMOTIONPRICE.equals(goodsCsvDto.getEmotionPriceType())) {

            if (goodsCsvDto.getGoodsCode() != null && !goodsCsvDto.getGoodsCode().endsWith("kp")) {
                // 通常商品の商品番号末尾に「kp」を追加
                goodsCsvDto.setGoodsCode(goodsCsvDto.getGoodsCode() + "kp");
            }
            // カタログ番号にnullを設定
            goodsCsvDto.setCatalogCode(null);
            // 販売状態PCにnullを設定
            goodsCsvDto.setSaleStatusPC(HTypeGoodsSaleStatus.NO_SALE);
            // 販売開始日時PCにnullを設定
            goodsCsvDto.setSaleStartTimePC(null);
            // 販売終了日時PCにnullを設定
            goodsCsvDto.setSaleEndTimePC(null);
            // 最大注文可能数に”1”を設定
            goodsCsvDto.setPurchasedMax(BigDecimal.valueOf(1));
            // 表示順に”9999”を設定
            goodsCsvDto.setOrderDisplay(9999);
        }
        // PDR Migrate Customization to here
        // （規格更新時）処理行の規格に対応する商品Entity取得
        GoodsDto goodsDto = null;
        for (GoodsDto tmpDto : goodsGroupDto.getGoodsDtoList()) {
            if (goodsCsvDto.getGoodsCode().equals(tmpDto.getGoodsEntity().getGoodsCode()) && shopSeq.equals(
                            tmpDto.getGoodsEntity().getShopSeq())) {
                goodsDto = tmpDto;
                // PDR Migrate Customization from here
                if (HTypeUploadType.MODIFY.equals(uploadType) && HTypeEmotionPriceType.NORMAL.equals(
                                goodsCsvDto.getEmotionPriceType()) && HTypeEmotionPriceType.NORMAL_WITH_EMOTION.equals(
                                tmpDto.getGoodsEntity().getEmotionPriceType())
                    || HTypeUploadType.MODIFY.equals(uploadType) && HTypeGoodsSaleStatus.DELETED.equals(
                                goodsCsvDto.getSaleStatusPC()) && HTypeEmotionPriceType.NORMAL_WITH_EMOTION.equals(
                                tmpDto.getGoodsEntity().getEmotionPriceType())) {
                    // 更新モードかつ心意気価格保持区分が1⇒0の場合、削除する心意気商品の商品番号を設定
                    // または更新モードかつ心意気元商品の販売状態が削除
                    emotionPriceDeleteGoodsCode = tmpDto.getGoodsEntity().getGoodsCode() + "kp";
                } else if (HTypeUploadType.MODIFY.equals(uploadType)
                           && HTypeEmotionPriceType.NORMAL_WITH_EMOTION.equals(goodsCsvDto.getEmotionPriceType())) {
                    // 更新モードかつ心意気価格保持区分が1の場合、心意気商品を登録、更新元の商品番号を設定
                    emotionPriceCreateGoodsCode = tmpDto.getGoodsEntity().getGoodsCode();
                    if (HTypeEmotionPriceType.NORMAL_WITH_EMOTION.equals(tmpDto.getGoodsEntity().getEmotionPriceType())
                        && HTypeUploadType.MODIFY.equals(uploadType)) {
                        // 心意気元商品の更新に伴う、心意気商品の更新が発生する場合
                        emotionGoodUpdateFlg = true;
                    }
                }
                // PDR Migrate Customization to here
            }
        }

        // 更新対象規格なしエラー
        if (goodsDto == null && HTypeUploadType.MODIFY.equals(uploadType)) {
            // PDR Migrate Customization from here
            if (!HTypeEmotionPriceType.EMOTIONPRICE.equals(goodsCsvDto.getEmotionPriceType()) && StringUtils.isEmpty(
                            emotionPriceCreateGoodsCode)) {
                addErrorMessage(UPDATEGOODS_NONE_FAIL,
                                new Object[] {goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode(),
                                                goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode()}
                               );
            }
            // PDR Migrate Customization to here
        } else if (goodsDto != null && HTypeUploadType.REGIST.equals(uploadType)) {
            addErrorMessage(REGISTGOODS_EXIST_FAIL,
                            new Object[] {goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode(),
                                            goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode()}
                           );
        } else if (HTypeUploadType.MODIFY.equals(uploadType) && HTypeGoodsSaleStatus.DELETED.equals(
                        goodsDto.getGoodsEntity().getSaleStatusPC())) {
            addErrorMessage(UPDATEGOODS_DELETED_FAIL,
                            new Object[] {goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode(),
                                            goodsCsvDto.getGoodsGroupCode(), goodsCsvDto.getGoodsCode()}
                           );
        }
        // 規格新規登録時
        if (goodsDto == null) {
            goodsDto = ApplicationContextUtility.getBean(GoodsDto.class);
            goodsDto.setGoodsEntity(getComponent(GoodsEntity.class));
            goodsDto.setStockDto(ApplicationContextUtility.getBean(StockDto.class));
            if (goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() != null) {
                goodsDto.getGoodsEntity().setGoodsGroupSeq(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
            }
            goodsGroupDto.getGoodsDtoList().add(goodsDto);
        }

        // -------------------------------
        // 商品Entityの設定
        // -------------------------------
        GoodsEntity goodsEntity = goodsDto.getGoodsEntity();
        // ショップSEQ
        goodsEntity.setShopSeq(shopSeq);

        // 商品単価
        if (HTypeUploadType.REGIST.equals(uploadType)) {
            goodsEntity.setGoodsPrice(BigDecimal.ZERO);
        }
        // 規格表示順
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("orderDisplay")) {
            goodsEntity.setOrderDisplay(goodsCsvDto.getOrderDisplay());
        }
        // 商品番号
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsCode")) {
            goodsEntity.setGoodsCode(goodsCsvDto.getGoodsCode());
        }
        // 規格管理フラグ
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitManagementFlag")) {
            goodsEntity.setUnitManagementFlag(goodsCsvDto.getUnitManagementFlag());
        }
        // 規格1、規格2
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitValue1")) {
            goodsEntity.setUnitValue1(goodsCsvDto.getUnitValue1());
        }
        // 2023-renew No76 from here
        // 規格画像有無
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitImageFlag")) {
            goodsEntity.setUnitImageFlag(goodsCsvDto.getUnitImageFlag());
        }
        // 2023-renew No76 to here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unitValue2")) {
            goodsEntity.setUnitValue2(goodsCsvDto.getUnitValue2());
        }
        // 注文上限
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("purchasedMax")) {
            goodsEntity.setPurchasedMax(goodsCsvDto.getPurchasedMax());
        }
        // 販売状態
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("saleStatusPC")) {
            goodsEntity.setSaleStatusPC(goodsCsvDto.getSaleStatusPC());
        }
        // PDR Migrate Customization from here
        // 更新モードかつ心意気価格商品削除の場合、販売状態を削除で上書き
        if (HTypeUploadType.MODIFY.equals(uploadType) && goodsCsvDto.getGoodsCode()
                                                                    .equals(emotionPriceDeleteGoodsCode)) {
            goodsEntity.setSaleStatusPC(HTypeGoodsSaleStatus.DELETED);
        }
        // PDR Migrate Customization to here
        // JANコード
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("janCode")) {
            goodsEntity.setJanCode(goodsCsvDto.getJanCode());
        }
        // カタログコード
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("catalogCode")) {
            goodsEntity.setCatalogCode(goodsCsvDto.getCatalogCode());
        }
        // 販売開始日時、販売終了日時
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("saleStartTimePC")) {
            goodsEntity.setSaleStartTimePC(goodsCsvDto.getSaleStartTimePC());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("saleEndTimePC")) {
            goodsEntity.setSaleEndTimePC(goodsCsvDto.getSaleEndTimePC());
        }
        // 在庫管理フラグ
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("stockManagementFlag")) {
            goodsEntity.setStockManagementFlag(goodsCsvDto.getStockManagementFlag());
        }
        // PDR Migrate Customization from here
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("reserveFlag")) {
            goodsEntity.setReserveFlag(goodsCsvDto.getReserveFlag());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsManagementCode")) {
            goodsEntity.setGoodsManagementCode(goodsCsvDto.getGoodsManagementCode());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsDivisionCode")) {
            goodsEntity.setGoodsDivisionCode(conversionUtility.toInteger(goodsCsvDto.getGoodsDivisionCode()));
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsCategory1")) {
            goodsEntity.setGoodsCategory1(conversionUtility.toInteger(goodsCsvDto.getGoodsCategory1()));
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsCategory2")) {
            goodsEntity.setGoodsCategory2(conversionUtility.toInteger(goodsCsvDto.getGoodsCategory2()));
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsCategory3")) {
            goodsEntity.setGoodsCategory3(conversionUtility.toInteger(goodsCsvDto.getGoodsCategory3()));
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("landSendFlag")) {
            goodsEntity.setLandSendFlag(goodsCsvDto.getLandSendFlag());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("coolSendFlag")) {
            goodsEntity.setCoolSendFlag(goodsCsvDto.getCoolSendFlag());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("coolSendFrom")) {
            goodsEntity.setCoolSendFrom(goodsCsvDto.getCoolSendFrom());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("coolSendTo")) {
            goodsEntity.setCoolSendTo(goodsCsvDto.getCoolSendTo());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("unit")) {
            goodsEntity.setUnit(goodsCsvDto.getUnit());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("priceMarkDispFlag")) {
            goodsEntity.setPriceMarkDispFlag(goodsCsvDto.getPriceMarkDispFlag());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("salePriceMarkDispFlag")) {
            goodsEntity.setSalePriceMarkDispFlag(goodsCsvDto.getSalePriceMarkDispFlag());
        }
        // 心意気価格保持区分
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("emotionPriceType")) {
            goodsEntity.setEmotionPriceType(goodsCsvDto.getEmotionPriceType());
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsEntity.getIndividualDeliveryType() == null) {
            goodsEntity.setIndividualDeliveryType(SYSTEM_PRAM_INDIVIDUALDELIVERYTYPE);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsEntity.getFreeDeliveryFlag() == null) {
            goodsEntity.setFreeDeliveryFlag(SYSTEM_PRAM_FREEDELIVERYFLAG);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsEntity.getPurchasedMax() == null) {
            goodsEntity.setPurchasedMax(SYSTEM_PRAM_PURCHASEDMAX);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsEntity.getPriceMarkDispFlag() == null) {
            goodsEntity.setPriceMarkDispFlag(SYSTEM_PRAM_PRICEMARKDISPFLAG);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && goodsEntity.getSalePriceMarkDispFlag() == null) {
            goodsEntity.setSalePriceMarkDispFlag(SYSTEM_PRAM_SALEPRICEMARKDISPFLAG);
        }
        // 2023-renew AddNo5 from here
        // 価格（最低）
        // 価格（最高）
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("goodsPriceInTax")) {
            goodsEntity.setGoodsPriceInTaxLow(goodsCsvDto.getGoodsPriceInTax());
            goodsEntity.setGoodsPriceInTaxHight(goodsCsvDto.getGoodsPriceInTax());
        }
        // セール価格（最低）
        // セール価格（最高）
        if (HTypeUploadType.REGIST.equals(uploadType) || updateColumn.contains("preDiscountPrice")) {
            goodsEntity.setPreDiscountPriceLow(goodsCsvDto.getPreDiscountPrice());
            goodsEntity.setPreDiscountPriceHight(goodsCsvDto.getPreDiscountPrice());
        }
        // 2023-renew AddNo5 to here
        // PDR Migrate Customization to here
        // -------------------------------
        // 在庫Dtoの設定
        // -------------------------------
        StockDto stockDto = goodsDto.getStockDto();
        // 残少表示在庫数
        // PDR Migrate Customization from here
        // 新規登録かつ商品内の対象項目がnullの場合システム値登録
        if (HTypeUploadType.REGIST.equals(uploadType) && stockDto.getRemainderFewStock() == null) {
            stockDto.setRemainderFewStock(SYSTEM_PRAM_ZERO);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && stockDto.getOrderPointStock() == null) {
            stockDto.setOrderPointStock(SYSTEM_PRAM_ZERO);
        }
        if (HTypeUploadType.REGIST.equals(uploadType) && stockDto.getSafetyStock() == null) {
            stockDto.setSafetyStock(SYSTEM_PRAM_ZERO);
        }
        // PDR Migrate Customization to here
        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            // PDR Migrate Customization from here
            // エラー発生時は、エラー発生行数（recodeCount）をメッセージに表示させるようにするため、goodsGroupInfoRecordCountをクリアする。
            commonCsvInfoMap.remove("goodsGroupInfoRecordCount");
            // PDR Migrate Customization to here
            throwMessage();
        }
    }

    // 2023-renew No76 from here

    /**
     * 規格登録数の上限チェック
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param lastGoodsGroupCode 処理中の商品グループコード
     */
    protected void checkGoodsLimit(Map<String, Object> commonCsvInfoMap, String lastGoodsGroupCode) {

        if (lastGoodsGroupCode == null) {
            return;
        }

        // CSVアップロード共通情報からCsvDtoリストを取得
        List<GoodsCsvDto> goodsCsvDtoList = (List<GoodsCsvDto>) commonCsvInfoMap.get("goodsCsvDtoList");

        // 商品管理番号ごとの規格登録数上限チェック
        if (goodsCsvDtoList.stream().filter(list -> list.getGoodsGroupCode().equals(lastGoodsGroupCode)).count()
            > LIMIT_GOODS_COUNT) {
            addErrorMessage(MSGCD_OVER_GOODS_COUNT, new Object[] {lastGoodsGroupCode});
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    // 2023-renew No76 to here

    // PDR Migrate Customization from here

    /**
     * CSV情報設定<br/>
     * 心意気価格元商品の情報をセットする<br/>
     *
     * @param csvline       商品CSVDto
     * @param goodsGroupDto 商品グループDto
     * @return csvline 商品CSVDto
     */
    protected GoodsCsvDto setupCsvDto(GoodsCsvDto csvline, GoodsGroupDto goodsGroupDto) {

        // 同一規格の商品情報を取得
        GoodsDto goodsDto = null;
        for (GoodsDto tempGoodsDto : goodsGroupDto.getGoodsDtoList()) {
            if (csvline.getGoodsCode().equals(tempGoodsDto.getGoodsEntity().getGoodsCode())) {
                goodsDto = tempGoodsDto;
                break;
            }
        }
        // 商品管理番号
        if (StringUtils.isEmpty(csvline.getGoodsGroupCode())) {
            csvline.setGoodsGroupCode(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode());
        }
        // 公開状態PC
        if (csvline.getGoodsOpenStatusPC() == null) {
            csvline.setGoodsOpenStatusPC(goodsGroupDto.getGoodsGroupEntity().getGoodsOpenStatusPC());
        }
        // 公開開始日時PC
        if (csvline.getOpenStartTimePC() == null) {
            csvline.setOpenStartTimePC(goodsGroupDto.getGoodsGroupEntity().getOpenStartTimePC());
        }
        // 公開終了日時PC
        if (csvline.getOpenEndTimePC() == null) {
            csvline.setOpenEndTimePC(goodsGroupDto.getGoodsGroupEntity().getOpenEndTimePC());
        }
        // 2023-renew searchKeyword-addition from here
        // 索引用語
        if (StringUtils.isEmpty(csvline.getSearchKeyword())) {
            csvline.setSearchKeyword(goodsGroupDto.getGoodsGroupDisplayEntity().getSearchKeyword());
        }
        // 2023-renew searchKeyword-addition to here
        // シリーズセールコメント
        if (StringUtils.isEmpty(csvline.getGoodsPreDiscountPrice())) {
            csvline.setGoodsPreDiscountPrice(goodsGroupDto.getGoodsGroupEntity().getGoodsPreDiscountPrice());
        }
        // 規格表示
        if (csvline.getUnitManagementFlag() == null) {
            csvline.setUnitManagementFlag(goodsDto.getGoodsEntity().getUnitManagementFlag());
        }
        // 規格1表示名
        if (StringUtils.isEmpty(csvline.getUnitTitle1())) {
            csvline.setUnitTitle1(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle1());
        }
        // 規格2表示名
        if (StringUtils.isEmpty(csvline.getUnitTitle2())) {
            csvline.setUnitTitle2(goodsGroupDto.getGoodsGroupDisplayEntity().getUnitTitle2());
        }
        // 商品番号
        if (StringUtils.isEmpty(csvline.getGoodsCode())) {
            csvline.setGoodsCode(goodsDto.getGoodsEntity().getGoodsCode());
        }
        // 商品表示順
        if (csvline.getOrderDisplay() == null) {
            csvline.setOrderDisplay(goodsDto.getGoodsEntity().getOrderDisplay());
        }
        // JANコード
        if (StringUtils.isEmpty(csvline.getJanCode())) {
            csvline.setJanCode(goodsDto.getGoodsEntity().getJanCode());
        }
        // カタログ番号
        if (StringUtils.isEmpty(csvline.getCatalogCode())) {
            csvline.setCatalogCode(goodsDto.getGoodsEntity().getCatalogCode());
        }
        // 規格1
        if (StringUtils.isEmpty(csvline.getUnitValue1())) {
            csvline.setUnitValue1(goodsDto.getGoodsEntity().getUnitValue1());
        }
        // 規格2
        if (StringUtils.isEmpty(csvline.getUnitValue2())) {
            csvline.setUnitValue2(goodsDto.getGoodsEntity().getUnitValue2());
        }
        // 2023-renew No76 from here
        // 規格画像有無
        if (csvline.getUnitImageFlag() == null) {
            csvline.setUnitImageFlag(goodsDto.getGoodsEntity().getUnitImageFlag());
        }
        // 2023-renew No76 to here
        // 販売状態PC
        if (csvline.getSaleStatusPC() == null) {
            csvline.setSaleStatusPC(goodsDto.getGoodsEntity().getSaleStatusPC());
        }
        // 販売開始日時PC
        if (csvline.getSaleStartTimePC() == null) {
            csvline.setSaleStartTimePC(goodsDto.getGoodsEntity().getSaleStartTimePC());
        }
        // 販売終了日時PC
        if (csvline.getSaleEndTimePC() == null) {
            csvline.setSaleEndTimePC(goodsDto.getGoodsEntity().getSaleEndTimePC());
        }
        // 2023-renew AddNo5 from here
        // 価格
        if (csvline.getGoodsPriceInTax() == null) {
            csvline.setGoodsPriceInTax(goodsDto.getGoodsEntity().getGoodsPriceInTaxHight());
        }
        // セール価格
        if (csvline.getPreDiscountPrice() == null) {
            csvline.setPreDiscountPrice(goodsDto.getGoodsEntity().getPreDiscountPriceHight());
        }
        // 2023-renew AddNo5 to here
        // 最大注文可能数
        if (csvline.getPurchasedMax() == null) {
            csvline.setPurchasedMax(goodsDto.getGoodsEntity().getPurchasedMax());
        }
        // 売り切りフラグ
        if (csvline.getStockManagementFlag() == null) {
            csvline.setStockManagementFlag(goodsDto.getGoodsEntity().getStockManagementFlag());
        }
        // 薬品区分
        if (csvline.getGoodsClassType() == null) {
            csvline.setGoodsClassType(goodsGroupDto.getGoodsGroupEntity().getGoodsClassType());
        }
        // カタログ表示順
        if (csvline.getCatalogDisplayOrder() == null) {
            csvline.setCatalogDisplayOrder(goodsGroupDto.getGoodsGroupEntity().getCatalogDisplayOrder());
        }
        // 保留フラグ
        if (csvline.getReserveFlag() == null) {
            csvline.setReserveFlag(goodsDto.getGoodsEntity().getReserveFlag());
        }
        // 管理商品コード
        if (StringUtils.isEmpty(csvline.getGoodsManagementCode())) {
            csvline.setGoodsManagementCode(goodsDto.getGoodsEntity().getGoodsManagementCode());
        }
        // 商品分類コード
        if (csvline.getGoodsDivisionCode() == null) {
            csvline.setGoodsDivisionCode(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsDivisionCode()));
        }
        // カテゴリー1
        if (csvline.getGoodsCategory1() == null) {
            csvline.setGoodsCategory1(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsCategory1()));
        }
        // カテゴリー2
        if (csvline.getGoodsCategory2() == null) {
            csvline.setGoodsCategory2(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsCategory2()));
        }
        // カテゴリー3
        if (csvline.getGoodsCategory3() == null) {
            csvline.setGoodsCategory3(conversionUtility.toString(goodsDto.getGoodsEntity().getGoodsCategory3()));
        }
        // 陸送商品フラグ
        if (csvline.getLandSendFlag() == null) {
            csvline.setLandSendFlag(goodsDto.getGoodsEntity().getLandSendFlag());
        }
        // クール便フラグ
        if (csvline.getCoolSendFlag() == null) {
            csvline.setCoolSendFlag(goodsDto.getGoodsEntity().getCoolSendFlag());
        }
        // クール便適用期間Ｆｒｏｍ
        if (csvline.getCoolSendFrom() == null) {
            csvline.setCoolSendFrom(goodsDto.getGoodsEntity().getCoolSendFrom());
        }
        // クール便適用期間Ｔｏ
        if (csvline.getCoolSendTo() == null) {
            csvline.setCoolSendTo(goodsDto.getGoodsEntity().getCoolSendTo());
        }
        // 単位
        if (StringUtils.isEmpty(csvline.getUnit())) {
            csvline.setUnit(goodsDto.getGoodsEntity().getUnit());
        }
        // 価格記号表示フラグ
        if (csvline.getPriceMarkDispFlag() == null) {
            csvline.setPriceMarkDispFlag(goodsDto.getGoodsEntity().getPriceMarkDispFlag());
        }
        // セール価格記号表示フラグ
        if (csvline.getSalePriceMarkDispFlag() == null) {
            csvline.setSalePriceMarkDispFlag(goodsDto.getGoodsEntity().getSalePriceMarkDispFlag());
        }
        // 心意気価格保持区分
        // 歯科専売可否フラグ
        if (csvline.getDentalMonopolySalesFlg() == null) {
            csvline.setDentalMonopolySalesFlg(goodsGroupDto.getGoodsGroupEntity().getDentalMonopolySalesFlg());
        }
        return csvline;
    }

    // PDR Migrate Customization to here

    /**
     * エラーメッセージ生成
     *
     * @param commonCsvInfoMap CSVアップロード共通情報
     * @param appe             アプリケーションExceptionオブジェクトリスト
     */
    protected void createCsvUploadErrorList(Map<String, Object> commonCsvInfoMap, AppLevelListException appe) {

        // CSV処理行
        Integer recordCount = -1;

        // エラー情報が無い場合は処理を抜ける
        if (appe.getErrorList() == null || appe.getErrorList().isEmpty()) {
            return;
        }
        // メッセージ表示に、商品管理番号の頭の行数を表示する場合はgoodsGroupInfoRecordCountの値をセット
        // 商品番号の行数を表示する場合は、recordCount（行数）をセットする
        if (commonCsvInfoMap.containsKey("goodsGroupInfoRecordCount")) {
            // CSV商品グループ行番号をデフォルト行番号に指定する
            recordCount = (Integer) commonCsvInfoMap.get("goodsGroupInfoRecordCount");
        } else {
            // CSV商品行番号をデフォルト行番号に指定する
            recordCount = (Integer) commonCsvInfoMap.get("recodeCount");
        }

        // CSVアップロード結果を取得する
        CsvUploadResult csvUploadResult = (CsvUploadResult) commonCsvInfoMap.get("csvUploadResult");

        // エラーメッセージを作成・セット
        List<CsvUploadError> csvUploadErrorList = new ArrayList<>();

        for (AppLevelException ae : appe.getErrorList()) {
            if (ignoreEmotionGoodsError(ae.getArgs())) {
                continue;
            }
            // 処理行の算出
            recordCount = this.processAppLevelException(recordCount, ae, commonCsvInfoMap);
            csvUploadErrorList.add(createCsvUploadError(recordCount, ae.getMessageCode(), ae.getArgs()));
            csvUploadResult.setCsvUploadErrorlList(csvUploadErrorList);
        }
    }

    protected boolean ignoreEmotionGoodsError(Object[] args) {
        // 例外の引数が２つ以上無い場合は処理行算出は行わない
        if (args == null || args.length < 2) {
            return false;
        }

        // 配列の最後の要素は、商品コード
        Object goodsCode = args[args.length - 1];

        // 両変数とも String のオブジェクトであることが前提。
        // String でない場合は、別の情報がセットされていると見なし、処理行の算出は行わない
        if (!(goodsCode instanceof String)) {
            return false;
        }

        if (((String) goodsCode).endsWith("kp")) {
            return true;
        }

        return false;
    }

    /**
     * AppLevelException に含まれる情報と commonCsvInfoMap より、ハンドリングしている例外のCSV処理行を算出する
     *
     * @param recordCount      算出しなかった場合に返すCSV処理行
     * @param ae               処理対象の AppLevelException
     * @param commonCsvInfoMap 処理情報
     * @return CSV処理行
     */
    protected int processAppLevelException(int recordCount,
                                           AppLevelException ae,
                                           Map<String, Object> commonCsvInfoMap) {

        Object[] args = ae.getArgs();

        // 例外の引数が２つ以上無い場合は処理行算出は行わない
        if (args == null || args.length < 2) {
            return recordCount;
        }

        // 配列の最後から２番目の要素は、商品グループコード
        // 配列の最後の要素は、商品コード
        Object group = args[args.length - 2];
        Object goods = args[args.length - 1];

        // 両変数とも String のオブジェクトであることが前提。
        // String でない場合は、別の情報がセットされていると見なし、処理行の算出は行わない
        if (!(group instanceof String) || !(goods instanceof String)) {
            return recordCount;
        }

        // 商品グループコード・商品コードをもとにCSV処理行を取得
        return getRecordCount(commonCsvInfoMap, (String) group, (String) goods);
    }

    /**
     * CSV処理行を取得する
     *
     * @param commonCsvInfoMap CSVアップロード共通情報（処理種別=画面の場合null）
     * @param goodsGroupCode   商品管理番号（処理種別=画面の場合null）
     * @param goodsCode        商品番号（処理種別=画面の場合null）
     * @return CSV処理行
     */
    protected Integer getRecordCount(Map<String, Object> commonCsvInfoMap, String goodsGroupCode, String goodsCode) {

        // CSV処理行
        Integer recordCount = -1;

        // 商品規格情報の場合
        if (goodsGroupCode != null && goodsCode != null) {
            @SuppressWarnings("unchecked")
            Map<String, Integer> goodsCodeAndIndexMap =
                            (Map<String, Integer>) commonCsvInfoMap.get("goodsCodeAndIndexMap");
            recordCount = goodsCodeAndIndexMap.get(goodsCode);

            // 今回更新対象でない規格
            if (recordCount == null) {
                recordCount = -1;
            }
        } else {
            // メッセージ表示に、商品管理番号の頭の行数を表示する場合はgoodsGroupInfoRecordCountの値をセット
            // 商品番号の行数を表示する場合は、recordCount（行数）をセットする
            if (commonCsvInfoMap.containsKey("goodsGroupInfoRecordCount")) {
                // CSV商品グループ行番号を行番号に指定する
                recordCount = (Integer) commonCsvInfoMap.get("goodsGroupInfoRecordCount");
            } else {
                // CSV商品行番号を行番号に指定する
                recordCount = (Integer) commonCsvInfoMap.get("recordCount");
            }
        }
        return recordCount;
    }

    /**
     * カテゴリエンティティリスト設定
     *
     * @param commonCsvInfoMap        CSVアップロード共通情報
     * @param goodsCsvDto             商品CSVDto
     * @param categoryGoodsEntityList カテゴリ登録商品Entityリスト
     */
    protected void setupCategoryGoodsEntityList(Map<String, Object> commonCsvInfoMap,
                                                GoodsCsvDto goodsCsvDto,
                                                List<CategoryGoodsEntity> categoryGoodsEntityList) {

        // ショップSEQを取得
        Integer shopSeq = 1001;
        // 商品グループSEQを取得
        Integer goodsGroupSeq = (Integer) commonCsvInfoMap.get("goodsGroupSeq");

        // カテゴリ登録商品リストをクリア
        categoryGoodsEntityList.clear();

        // カテゴリ設定リストがない場合は、空のリストのまま返す
        if (goodsCsvDto.getCategoryGoodsSetting() == null || "".equals(goodsCsvDto.getCategoryGoodsSetting())) {
            return;
        }

        // CSVから取得したカテゴリ設定をリスト化する
        String[] categoryIdList = goodsCsvDto.getCategoryGoodsSetting().split("/");

        // DBに登録されているカテゴリ登録商品マップ(KEY:カテゴリSEQ)を取得する
        Map<Integer, CategoryGoodsEntity> categoryGoodsEntityMap = new HashMap<>();
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        if (goodsGroupSeq != null) {
            goodsGroupSeqList.add(goodsGroupSeq);
        }
        if (goodsGroupSeqList.size() > 0) {
            Map<Integer, List<CategoryGoodsEntity>> categoryGoodsEntityListMap =
                            categoryGoodsMapGetLogic.execute(goodsGroupSeqList);
            List<CategoryGoodsEntity> masterCategoryGoodsEntityList = categoryGoodsEntityListMap.get(goodsGroupSeq);
            if (masterCategoryGoodsEntityList != null) {
                for (CategoryGoodsEntity masterCategoryGoodsEntity : masterCategoryGoodsEntityList) {
                    categoryGoodsEntityMap.put(masterCategoryGoodsEntity.getCategorySeq(), masterCategoryGoodsEntity);
                }
            }
        }

        // 重複チェック用リストの作成
        List<String> strCategoryIdList = Arrays.asList(categoryIdList);
        // 登録カテゴリ重複エラーのセット
        List<String> repetitionedCategory = new ArrayList<>();
        // 存在しないカテゴリーが指定され、同一のカテゴリーであった場合はメッセージは１行に集約する
        // 存在しないカテゴリーのセット
        List<String> noneFailCategory = new ArrayList<>();
        for (String categoryId : categoryIdList) {
            if (strCategoryIdList.indexOf(categoryId) != strCategoryIdList.lastIndexOf(categoryId)
                && !repetitionedCategory.contains(categoryId)) {
                // 登録カテゴリ重複エラー
                addErrorMessage(CATEGORY_REPETITION_FAIL, new Object[] {categoryId, null, null});
                repetitionedCategory.add(categoryId);
                continue;
            }
            CategoryEntity categoryEntity = categoryGetLogic.execute(shopSeq, categoryId);
            // カテゴリーが存在しない
            if (categoryEntity == null) {
                // １度もエラーとしてメッセージをセットしていない場合
                if (!noneFailCategory.contains(categoryId)) {
                    // カテゴリなしエラー
                    addErrorMessage(CATEGORY_NONE_FAIL, new Object[] {categoryId, null, null});
                    noneFailCategory.add(categoryId);
                }
                continue;
            }
            CategoryGoodsEntity dto = categoryGoodsEntityMap.get(categoryEntity.getCategorySeq());
            if (dto != null) {
                // カテゴリ登録商品が既に登録されている場合
                // 戻り値用カテゴリ登録商品Dtoリストにそのままセット
                categoryGoodsEntityList.add(dto);
            } else {
                // カテゴリ登録商品が既に登録されていない場合
                // 戻り値用カテゴリ登録商品リストに登録データを作成してセット
                CategoryGoodsEntity newDto = ApplicationContextUtility.getBean(CategoryGoodsEntity.class);
                newDto.setCategorySeq(categoryEntity.getCategorySeq());
                newDto.setGoodsGroupSeq(goodsGroupSeq);
                categoryGoodsEntityList.add(newDto);
            }
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * CsvアップロードエラーDto作成
     *
     * @param recodeCount レコード数
     * @param messageCode メッセージコード
     * @param args        引数
     * @return CsvアップロードエラーDto
     */
    protected CsvUploadError createCsvUploadError(Integer recodeCount, String messageCode, Object[] args) {
        CsvUploadError csvUploadError = new CsvUploadError();
        csvUploadError.setRow(recodeCount);
        csvUploadError.setMessageCode(messageCode);
        csvUploadError.setArgs(args);
        csvUploadError.setMessage(checkMessageUtility.getMessage(messageCode, args));
        return csvUploadError;
    }

    /**
     * Csvアップロード内容のバリデーションチェック
     *
     * @param goodsCsvDtoList CsvDtoリスト
     * @param csvUploadResult Csvアップロード結果
     * @param uploadedCsvFile 商品CSVデータアップロードファイル
     * @return バリデーション結果Dto
     */
    protected boolean validate(List<GoodsCsvDto> goodsCsvDtoList,
                               CsvUploadResult csvUploadResult,
                               File uploadedCsvFile) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(-1);

        /* Csvファイルを読み込み */
        try {
            List<GoodsCsvDto> goodsCsvDtoLisTmp =
                            (List<GoodsCsvDto>) csvReaderUtil.readCsv(uploadedCsvFile, GoodsCsvDto.class,
                                                                      csvUploadResult, csvReaderOptionDto
                                                                     );
            goodsCsvDtoList.addAll(goodsCsvDtoLisTmp);
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            csvReaderUtil.createUnexpectedExceptionMsg(csvUploadResult);
            return true;
        }

        // エラーあり
        if (csvUploadResult.getErrorCount() > 0) {
            return true;
        }

        // エラーなし
        return false;
    }

    /**
     * アップロードされたカラムの取得
     *
     * @param uploadedCsvFile
     * @return アップロードされたカラム
     */
    protected List<String> getUploadColumn(File uploadedCsvFile) {
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        return csvReaderUtil.getUploadedCsvHeaderEng(GoodsCsvDto.class, uploadedCsvFile, csvReaderOptionDto);
    }

}
