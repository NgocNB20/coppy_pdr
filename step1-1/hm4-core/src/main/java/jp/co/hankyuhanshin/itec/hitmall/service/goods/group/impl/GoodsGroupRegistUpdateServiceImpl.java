/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.FileRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupRegistUpdateResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleCheckResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsImageRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.NewGoodsSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsDisplayPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageRegistDataOnlyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupPopularityRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.NewGoodsGroupSeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl.GoodsDisplayPriceLogicImpl;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSettingRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy.GoodsTogetherBuyGroupRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupRegistUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockSupplementService;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品グループ登録更新サービス実装クラス<br/>
 *
 * @author hirata
 * @author Kaneko (itec) 2012/08/16 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class GoodsGroupRegistUpdateServiceImpl extends AbstractShopService implements GoodsGroupRegistUpdateService {

    /**
     * 全角スペース<br/>
     * <code>EM_SPACE</code>
     */
    protected static final String EM_SPACE = "　";

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGroupRegistUpdateServiceImpl.class);

    /**
     * FileOperationUtility
     */
    private final FileOperationUtility fileOperationUtility;

    /**
     * 商品グループデータチェックLogic
     */
    private final GoodsGroupDataCheckLogic goodsGroupDataCheckLogic;

    /**
     * 商品グループテーブルロックLogic
     */
    private final GoodsGroupTableLockLogic goodsGroupTableLockLogic;

    /**
     * カテゴリテーブルロックLogic
     */
    private final CategoryTableLockLogic categoryTableLockLogic;

    /**
     * 商品グループレコードロックLogic
     */
    private final GoodsGroupLockLogic goodsGroupLockLogic;

    /**
     * カテゴリレコードロックLogic
     */
    private final CategoryLockLogic categoryLockLogic;

    /**
     * 商品グループSEQ採番Logic
     */
    private final NewGoodsGroupSeqGetLogic newGoodsGroupSeqGetLogic;

    /**
     * 商品グループ登録Logic
     */
    private final GoodsGroupRegistLogic goodsGroupRegistLogic;

    /**
     * 商品グループ表示登録Logic
     */
    private final GoodsGroupDisplayRegistLogic goodsGroupDisplayRegistLogic;

    /**
     * 商品グループ人気登録Logic
     */
    private final GoodsGroupPopularityRegistLogic goodsGroupPopularityRegistLogic;

    /**
     * 商品グループ更新Logic
     */
    private final GoodsGroupUpdateLogic goodsGroupUpdateLogic;

    /**
     * 商品グループ表示更新Logic
     */
    private final GoodsGroupDisplayUpdateLogic goodsGroupDisplayUpdateLogic;

    /**
     * カテゴリ登録商品登録Logic
     */
    private final CategoryGoodsRegistLogic categoryGoodsRegistLogic;

    /**
     * 関連商品登録Logic
     */
    private final GoodsRelationRegistLogic goodsRelationRegistLogic;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品登録
     */
    private final GoodsTogetherBuyGroupRegistLogic goodsTogetherBuyGroupRegistLogic;
    // 2023-renew No21 to here

    /**
     * 商品グループ画像登録Logic
     */
    private final GoodsGroupImageRegistLogic goodsGroupImageRegistLogic;

    /**
     * 商品グループ画像登録(データのみ、画像ファイル操作は行わない CSV用)Logic
     */
    private final GoodsGroupImageRegistDataOnlyLogic goodsGroupImageRegistDataOnlyLogic;

    /**
     * 商品SEQ採番Logic
     */
    private final NewGoodsSeqGetLogic newGoodsSeqGetLogic;

    /**
     * 商品登録Logic
     */
    private final GoodsRegistLogic goodsRegistLogic;

    /**
     * 在庫設定登録Logic
     */
    private final StockSettingRegistLogic stockSettingRegistLogic;

    /**
     * 在庫登録Logic
     */
    private final StockRegistLogic stockRegistLogic;

    /**
     * 商品規格画像登録Logic
     */
    private final GoodsImageRegistLogic goodsImageRegistLogic;

    /**
     * 入庫登録サービス
     */
    private final StockSupplementService stockSupplementService;

    /**
     * 商品表示単価を生成するイ
     */
    private final GoodsDisplayPriceLogic goodsDisplayPriceLogic;

    @Autowired
    public GoodsGroupRegistUpdateServiceImpl(FileOperationUtility fileOperationUtility,
                                             GoodsGroupDataCheckLogic goodsGroupDataCheckLogic,
                                             GoodsGroupTableLockLogic goodsGroupTableLockLogic,
                                             CategoryTableLockLogic categoryTableLockLogic,
                                             GoodsGroupLockLogic goodsGroupLockLogic,
                                             CategoryLockLogic categoryLockLogic,
                                             NewGoodsGroupSeqGetLogic newGoodsGroupSeqGetLogic,
                                             GoodsGroupRegistLogic goodsGroupRegistLogic,
                                             GoodsGroupDisplayRegistLogic goodsGroupDisplayRegistLogic,
                                             GoodsGroupPopularityRegistLogic goodsGroupPopularityRegistLogic,
                                             GoodsGroupUpdateLogic goodsGroupUpdateLogic,
                                             GoodsGroupDisplayUpdateLogic goodsGroupDisplayUpdateLogic,
                                             CategoryGoodsRegistLogic categoryGoodsRegistLogic,
                                             GoodsRelationRegistLogic goodsRelationRegistLogic,
                                             // 2023-renew No21 from here
                                             GoodsTogetherBuyGroupRegistLogic goodsTogetherBuyGroupRegistLogic,
                                             // 2023-renew No21 to here
                                             GoodsGroupImageRegistLogic goodsGroupImageRegistLogic,
                                             GoodsGroupImageRegistDataOnlyLogic goodsGroupImageRegistDataOnlyLogic,
                                             NewGoodsSeqGetLogic newGoodsSeqGetLogic,
                                             GoodsRegistLogic goodsRegistLogic,
                                             StockSettingRegistLogic stockSettingRegistLogic,
                                             StockRegistLogic stockRegistLogic,
                                             GoodsDisplayPriceLogic goodsDisplayPriceLogic,
                                             GoodsImageRegistLogic goodsImageRegistLogic,
                                             StockSupplementService stockSupplementService) {

        this.fileOperationUtility = fileOperationUtility;
        this.goodsGroupDataCheckLogic = goodsGroupDataCheckLogic;
        this.goodsGroupTableLockLogic = goodsGroupTableLockLogic;
        this.categoryTableLockLogic = categoryTableLockLogic;
        this.goodsGroupLockLogic = goodsGroupLockLogic;
        this.categoryLockLogic = categoryLockLogic;
        this.newGoodsGroupSeqGetLogic = newGoodsGroupSeqGetLogic;
        this.goodsGroupRegistLogic = goodsGroupRegistLogic;
        this.goodsGroupDisplayRegistLogic = goodsGroupDisplayRegistLogic;
        this.goodsGroupPopularityRegistLogic = goodsGroupPopularityRegistLogic;
        this.goodsGroupUpdateLogic = goodsGroupUpdateLogic;
        this.goodsGroupDisplayUpdateLogic = goodsGroupDisplayUpdateLogic;
        this.categoryGoodsRegistLogic = categoryGoodsRegistLogic;
        this.goodsRelationRegistLogic = goodsRelationRegistLogic;
        // 2023-renew No21 from here
        this.goodsTogetherBuyGroupRegistLogic = goodsTogetherBuyGroupRegistLogic;
        // 2023-renew No21 to here
        this.goodsGroupImageRegistLogic = goodsGroupImageRegistLogic;
        this.goodsGroupImageRegistDataOnlyLogic = goodsGroupImageRegistDataOnlyLogic;
        this.newGoodsSeqGetLogic = newGoodsSeqGetLogic;
        this.goodsRegistLogic = goodsRegistLogic;
        this.stockSettingRegistLogic = stockSettingRegistLogic;
        this.stockRegistLogic = stockRegistLogic;
        this.goodsImageRegistLogic = goodsImageRegistLogic;
        this.stockSupplementService = stockSupplementService;
        this.goodsDisplayPriceLogic = goodsDisplayPriceLogic;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> execute(GoodsGroupDto inputGoodsGroupDto,
                                       List<? extends GoodsRelationEntity> inputGoodsRelationEntityList,
                                       // 2023-renew No21 from here
                                       List<? extends GoodsTogetherBuyGroupEntity> inputGoodsTogetherBuyGroupEntityList,
                                       // 2023-renew No21 to here
                                       List<GoodsGroupImageRegistUpdateDto> inputGoodsGroupImageRegistUpdateDtoList,
                                       int processType,
                                       String administratorName) {

        // 使用するパラメータをディープコピーする
        // （※エラーで戻ったときのために入力状態を保持する）
        GoodsGroupDto goodsGroupDto = CopyUtil.deepCopy(inputGoodsGroupDto);
        // リストのディープコピーを行う
        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        for (int i = 0; inputGoodsRelationEntityList != null && inputGoodsRelationEntityList.size() > i; i++) {
            goodsRelationEntityList.add(CopyUtil.deepCopy(inputGoodsRelationEntityList.get(i)));
        }

        // 2023-renew No21 from here
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList = new ArrayList<>();
        for (int i = 0;
             inputGoodsTogetherBuyGroupEntityList != null && inputGoodsTogetherBuyGroupEntityList.size() > i; i++) {
            goodsTogetherBuyGroupEntityList.add(CopyUtil.deepCopy(inputGoodsTogetherBuyGroupEntityList.get(i)));
        }
        // 2023-renew No21 to here

        List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList = new ArrayList<>();
        for (int i = 0; inputGoodsGroupImageRegistUpdateDtoList != null
                        && inputGoodsGroupImageRegistUpdateDtoList.size() > i; i++) {
            goodsGroupImageRegistUpdateDtoList.add(CopyUtil.deepCopy(inputGoodsGroupImageRegistUpdateDtoList.get(i)));
        }

        /** 共通処理結果マップ領域 */
        Map<String, Integer> resultMap = null;
        // 処理件数保管Dto
        GoodsGroupRegistUpdateResultDto resultDto =
                        ApplicationContextUtility.getBean(GoodsGroupRegistUpdateResultDto.class);

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupDto", goodsGroupDto);
        // 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;

        // テーブル＆レコードロック処理（画面からの登録更新処理時のみ）
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            executeTableLock(goodsGroupDto);
        }

        // (3) 商品グループデータチェックLogic処理
        // 2023-renew No21 from here
        goodsGroupDataCheckLogic.execute(
                        goodsGroupDto, goodsRelationEntityList, goodsTogetherBuyGroupEntityList, shopSeq);
        // 2023-renew No21 to here

        // (4) 商品データチェックLogic処理
        // 商品グループデータチェックLogic内で実施

        // (5)-1 商品グループ(最安値・最高値・商品表示単価)の算出
        calculateGoodsGroupPrice(goodsGroupDto);

        // PDR Migrate Customization from here
        // 検索キーワード全角の編集は削除
        // PDR Migrate Customization to here

        boolean isRegist = goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() == null;

        if (isRegist) {
            /********************************************************
             **  商品グループ登録用処理
             ********************************************************/
            // (6) 商品グループSEQ採番Logic処理
            // (7) ショップSEQをセット
            GoodsGroupPopularityEntity goodsGroupPopularityEntity =
                            createRegistGoodsGroupData(processType, goodsGroupDto, goodsRelationEntityList,
                                                       // 2023-renew No21 from here
                                                       goodsTogetherBuyGroupEntityList,
                                                       // 2023-renew No21 to here
                                                       goodsGroupImageRegistUpdateDtoList, shopSeq
                                                      );
            // PDR Migrate Customization from here
            if ((inputGoodsGroupDto.getGoodsDtoList().get(0).getGoodsEntity().getEmotionPriceType())
                != HTypeEmotionPriceType.EMOTIONPRICE) {
                // (8) 商品グループ登録Logic処理
                resultDto.setResultGoodsGroupRegist(goodsGroupRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity()));

                // (9) 商品グループ表示登録Logic処理
                resultDto.setResultGoodsGroupDisplayRegist(
                                goodsGroupDisplayRegistLogic.execute(goodsGroupDto.getGoodsGroupDisplayEntity()));

                // (10) 商品グループ人気登録Logic処理
                resultDto.setResultGoodsGroupPopularityRegist(
                                goodsGroupPopularityRegistLogic.execute(goodsGroupPopularityEntity));
            }
            // PDR Migrate Customization to here

        } else {
            /********************************************************
             **  商品グループ更新用処理
             ********************************************************/
            createUpdateGoodsGroupData(processType, goodsGroupDto, goodsGroupImageRegistUpdateDtoList);

            // (11) 商品グループ更新Logic処理
            resultDto.setResultGoodsGroupUpdate(goodsGroupUpdateLogic.execute(goodsGroupDto.getGoodsGroupEntity()));

            // (12) 商品グループ表示更新Logic処理
            resultDto.setResultGoodsGroupDisplayUpdate(
                            goodsGroupDisplayUpdateLogic.execute(goodsGroupDto.getGoodsGroupDisplayEntity()));
        }

        // (13) カテゴリ登録商品登録Logic処理
        if (goodsGroupDto.getCategoryGoodsEntityList() == null) {
            goodsGroupDto.setCategoryGoodsEntityList(new ArrayList<>());
        }
        resultMap = categoryGoodsRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                     goodsGroupDto.getCategoryGoodsEntityList()
                                                    );
        resultDto.setResultCategoryGoodsRegist(resultMap.get(CategoryGoodsRegistLogic.CATEGORY_GOODS_REGIST));
        resultDto.setResultCategoryGoodsDelete(resultMap.get(CategoryGoodsRegistLogic.CATEGORY_GOODS_DELETE));

        // (14) 関連商品登録Logic処理
        resultMap = goodsRelationRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                     goodsRelationEntityList
                                                    );
        resultDto.setResultGoodsRelationRegist(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_REGIST));
        resultDto.setResultGoodsRelationUpdate(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_UPDATE));
        resultDto.setResultGoodsRelationDelete(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_DELETE));

        // 2023-renew No21 from here
        resultMap = goodsTogetherBuyGroupRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                             goodsTogetherBuyGroupEntityList
                                                            );
        resultDto.setResultGoodsTogetherBuyGroupRegist(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_REGIST));
        resultDto.setResultGoodsTogetherBuyGroupUpdate(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_UPDATE));
        resultDto.setResultGoodsTogetherBuyGroupDelete(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_DELETE));
        // 2023-renew No21 to here

        // (15) 商品グループ画像登録Logic処理
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            // 画面からの場合（画像ファイルも含めて登録更新用Logic）
            Map<String, Object> resultMap2 =
                            goodsGroupImageRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                               goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(),
                                                               goodsGroupImageRegistUpdateDtoList
                                                              );
            resultDto.setResultGoodsGroupImageRegist(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST));
            resultDto.setResultGoodsGroupImageUpdate(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE));
            resultDto.setResultGoodsGroupImageDelete(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE));
            resultDto.setDeleteImageFilePathList(
                            (List<String>) resultMap2.get(GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST));
            resultDto.setRegistImageFilePathList((List<FileRegistDto>) resultMap2.get(
                            GoodsGroupImageRegistLogic.REGIST_IMAGE_FILE_PATH_LIST));
        } else if (processType == PROCESS_TYPE_FROM_CSV) {
            // 画面からの場合（画像ファイルを含めない登録更新用Logic）
            Map<String, Object> resultMap2 = goodsGroupImageRegistDataOnlyLogic.execute(
                            goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                            goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(),
                            goodsGroupDto.getGoodsGroupImageEntityList()
                                                                                       );
            resultDto.setResultGoodsGroupImageRegist(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST));
            resultDto.setResultGoodsGroupImageUpdate(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE));
            resultDto.setResultGoodsGroupImageDelete(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE));
            resultDto.setDeleteImageFilePathList(
                            (List<String>) resultMap2.get(GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST));
        }

        // (16) 商品・在庫設定・在庫登録更新用リスト編集
        // 商品Dtoリストから(登録更新用)商品エンティティリストを作成
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録更新用)在庫設定エンティティリストを作成
        List<StockSettingEntity> stockSettingEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録用)在庫エンティティリストを作成
        List<StockEntity> stockEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録更新用)規格画像エンティティリストを作成
        List<GoodsImageEntity> goodsImageEntityList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            // (17) 商品SEQ採番Logic処理
            createRegistGoodsData(shopSeq, goodsEntityList, stockSettingEntityList, stockEntityList,
                                  goodsImageEntityList, goodsDto
                                 );
        }

        // (18) 商品登録Logic処理
        if (goodsEntityList.size() > 0) {
            resultMap = goodsRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                 goodsEntityList, administratorName
                                                );
            resultDto.setResultGoodsRegist(resultMap.get(GoodsRegistLogic.GOODS_REGIST));
            resultDto.setResultGoodsUpdate(resultMap.get(GoodsRegistLogic.GOODS_UPDATE));
        }

        // (19) 在庫設定登録Logic処理
        if (stockSettingEntityList.size() > 0) {
            resultMap = stockSettingRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                        stockSettingEntityList
                                                       );
            resultDto.setResultStockSettingRegist(resultMap.get(StockSettingRegistLogic.STOCK_SETTING_REGIST));
            resultDto.setResultStockSettingUpdate(resultMap.get(StockSettingRegistLogic.STOCK_SETTING_UPDATE));
        }

        // (20) 在庫登録Logic処理
        // 商品グループが新規登録の場合
        if (isRegist) {
            resultDto.setResultStockRegist(stockRegistLogic.execute(stockEntityList, administratorName));
        }
        // 商品規格の追加がある場合
        else if (!isRegist && stockEntityList.size() > 0) {
            // 追加した商品規格の在庫登録
            resultDto.setResultStockRegist(stockRegistLogic.execute(stockEntityList, administratorName));

            // 更新対象外の商品を抽出する
            List<Integer> excludeGoodsSeqList = new ArrayList<>();
            for (GoodsEntity goodsEntity : goodsEntityList) {
                for (StockEntity stockEntity : stockEntityList) {
                    if (goodsEntity.getGoodsSeq() == stockEntity.getGoodsSeq()) {
                        excludeGoodsSeqList.add(stockEntity.getGoodsSeq());
                    }
                }
            }

            // 在庫更新
            updateStock(goodsGroupDto.getGoodsDtoList(), excludeGoodsSeqList, administratorName);
        }
        // 更新のみの場合
        else {
            // 在庫更新
            updateStock(goodsGroupDto.getGoodsDtoList(), null, administratorName);
        }

        // (20-1) 商品規格画像登録Logic処理
        if (goodsImageEntityList.size() > 0) {
            Map<String, Object> resultMap3 =
                            goodsImageRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                          goodsImageEntityList
                                                         );
            resultDto.setResultGoodsUnitImageRegist((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_REGIST));
            resultDto.setResultGoodsUnitImageUpdate((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_UPDATE));
            resultDto.setResultGoodsUnitImageDelete((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_DELETE));
            resultDto.setDeleteUnitImageFilePathList(
                            (List<String>) resultMap3.get(GoodsImageRegistLogic.DELETE_UNIT_IMAGE_FILE_PATH_LIST));
            resultDto.setRegistUnitImageFilePathList((List<FileRegistDto>) resultMap3.get(
                            GoodsImageRegistLogic.REGIST_UNIT_IMAGE_FILE_PATH_LIST));
        }

        String errorDeleteFilePath = "";

        // (21) 画像ファイル削除
        for (String deleteFilePath : resultDto.getDeleteImageFilePathList()) {
            errorDeleteFilePath = deleteFilePath;
            boolean ret = false;
            try {
                ret = fileOperationUtility.remove(deleteFilePath);
            } catch (IOException e) {
                LOGGER.warn("商品画像の削除に失敗しました。" + errorDeleteFilePath, e);
            }
            if (ret) {
                resultDto.setResultGoodsGroupImageFileDelete(resultDto.getResultGoodsGroupImageFileDelete() + 1);
            }
        }

        // (21-1) 規格画像ファイル削除
        String errorDeleteUnitImagePath = "";
        if (resultDto.getDeleteUnitImageFilePathList() != null) {
            for (String deleteFilePath : resultDto.getDeleteUnitImageFilePathList()) {
                errorDeleteUnitImagePath = deleteFilePath;
                boolean ret = false;
                try {
                    ret = fileOperationUtility.remove(deleteFilePath);
                } catch (IOException e) {
                    LOGGER.warn("商品規格画像の削除に失敗しました。" + errorDeleteUnitImagePath, e);
                }
                if (ret) {
                    resultDto.setResultGoodsUnitImageFileDelete(resultDto.getResultGoodsGroupImageFileDelete() + 1);
                }
            }
        }

        // (22) 画像ファイル登録（一時領域からフロント公開領域へ）
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            resultDto.setResultGoodsGroupImageFileRegist(
                            registGoodsGroupImageFile(resultDto.getRegistImageFilePathList(),
                                                      resultDto.getResultGoodsGroupImageFileRegist()
                                                     ));
            if (resultDto.getRegistUnitImageFilePathList() != null) {
                resultDto.setResultGoodsUnitImageFileRegist(
                                registGoodsGroupImageFile(resultDto.getRegistUnitImageFilePathList(),
                                                          resultDto.getResultGoodsUnitImageFileRegist()
                                                         ));
            }
        }

        // (24) 処理件数マップを返す
        return createReturnMap(resultDto, goodsGroupDto);
    }

    // PDR Migrate Customization from here

    /**
     * 実行メソッド<br/>
     *
     * @param inputGoodsGroupDto                      商品グループDto
     * @param inputGoodsRelationEntityList            関連商品エンティティリスト
     * @param inputGoodsRelationEntityList            よく一緒に購入される商品エンティティリスト
     * @param inputGoodsGroupImageRegistUpdateDtoList 商品グループ画像登録更新用DTOリスト（処理種別=CSV時はnull）
     * @param processType                             処理種別（画面/CSV）
     * @param emotionFlg                              心意気商品登録更新用フラグ
     * @return 処理件数マップ
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> execute(GoodsGroupDto inputGoodsGroupDto,
                                       List<? extends GoodsRelationEntity> inputGoodsRelationEntityList,
                                       // 2023-renew No21 from here
                                       List<? extends GoodsTogetherBuyGroupEntity> inputGoodsTogetherBuyGroupEntityList,
                                       // 2023-renew No21 to here
                                       List<GoodsGroupImageRegistUpdateDto> inputGoodsGroupImageRegistUpdateDtoList,
                                       int processType,
                                       boolean emotionFlg,
                                       String administratorName) {
        // 使用するパラメータをディープコピーする
        // （※エラーで戻ったときのために入力状態を保持する）
        GoodsGroupDto goodsGroupDto = CopyUtil.deepCopy(inputGoodsGroupDto);
        // リストのディープコピーを行う
        List<GoodsRelationEntity> goodsRelationEntityList = new ArrayList<>();
        for (int i = 0; inputGoodsRelationEntityList != null && inputGoodsRelationEntityList.size() > i; i++) {
            goodsRelationEntityList.add(CopyUtil.deepCopy(inputGoodsRelationEntityList.get(i)));
        }

        // 2023-renew No21 from here
        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList = new ArrayList<>();
        for (int i = 0;
             inputGoodsTogetherBuyGroupEntityList != null && inputGoodsTogetherBuyGroupEntityList.size() > i; i++) {
            goodsTogetherBuyGroupEntityList.add(CopyUtil.deepCopy(inputGoodsTogetherBuyGroupEntityList.get(i)));
        }
        // 2023-renew No21 to here

        List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList = new ArrayList<>();
        for (int i = 0; inputGoodsGroupImageRegistUpdateDtoList != null
                        && inputGoodsGroupImageRegistUpdateDtoList.size() > i; i++) {
            goodsGroupImageRegistUpdateDtoList.add(CopyUtil.deepCopy(inputGoodsGroupImageRegistUpdateDtoList.get(i)));
        }

        /** 共通処理結果マップ領域 */
        Map<String, Integer> resultMap = null;
        // 処理件数保管Dto
        GoodsGroupRegistUpdateResultDto resultDto =
                        ApplicationContextUtility.getBean(GoodsGroupRegistUpdateResultDto.class);

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupDto", goodsGroupDto);
        // 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;

        // テーブル＆レコードロック処理（画面からの登録更新処理時のみ）
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            executeTableLock(goodsGroupDto);
        }

        if (!emotionFlg) {
            // (3) 商品グループデータチェックLogic処理
            // 2023-renew No21 from here
            goodsGroupDataCheckLogic.execute(
                            goodsGroupDto, goodsRelationEntityList, goodsTogetherBuyGroupEntityList, shopSeq);
            // 2023-renew No21 to here
        }

        // (4) 商品データチェックLogic処理
        // 商品グループデータチェックLogic内で実施

        // (5)-1 商品グループ(最安値・最高値・商品表示単価)の算出
        calculateGoodsGroupPrice(goodsGroupDto);

        // PDR Migrate Customization from here
        // 検索キーワード全角の編集は削除
        // PDR Migrate Customization to here

        boolean isRegist = goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() == null;

        if (isRegist) {
            /********************************************************
             **  商品グループ登録用処理
             ********************************************************/
            // (6) 商品グループSEQ採番Logic処理
            // (7) ショップSEQをセット
            GoodsGroupPopularityEntity goodsGroupPopularityEntity =
                            createRegistGoodsGroupData(processType, goodsGroupDto, goodsRelationEntityList,
                                                       // 2023-renew No21 from here
                                                       goodsTogetherBuyGroupEntityList,
                                                       // 2023-renew No21 to here
                                                       goodsGroupImageRegistUpdateDtoList, shopSeq
                                                      );
            if ((inputGoodsGroupDto.getGoodsDtoList().get(0).getGoodsEntity().getEmotionPriceType())
                != HTypeEmotionPriceType.EMOTIONPRICE) {
                // (8) 商品グループ登録Logic処理
                resultDto.setResultGoodsGroupRegist(goodsGroupRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity()));

                // (9) 商品グループ表示登録Logic処理
                resultDto.setResultGoodsGroupDisplayRegist(
                                goodsGroupDisplayRegistLogic.execute(goodsGroupDto.getGoodsGroupDisplayEntity()));

                // (10) 商品グループ人気登録Logic処理
                resultDto.setResultGoodsGroupPopularityRegist(
                                goodsGroupPopularityRegistLogic.execute(goodsGroupPopularityEntity));
            }

        } else {
            /********************************************************
             **  商品グループ更新用処理
             ********************************************************/
            createUpdateGoodsGroupData(processType, goodsGroupDto, goodsGroupImageRegistUpdateDtoList);

            // (11) 商品グループ更新Logic処理
            resultDto.setResultGoodsGroupUpdate(goodsGroupUpdateLogic.execute(goodsGroupDto.getGoodsGroupEntity()));

            // (12) 商品グループ表示更新Logic処理
            resultDto.setResultGoodsGroupDisplayUpdate(
                            goodsGroupDisplayUpdateLogic.execute(goodsGroupDto.getGoodsGroupDisplayEntity()));
        }

        // (13) カテゴリ登録商品登録Logic処理
        if (goodsGroupDto.getCategoryGoodsEntityList() == null) {
            goodsGroupDto.setCategoryGoodsEntityList(new ArrayList<>());
        }
        resultMap = categoryGoodsRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                     goodsGroupDto.getCategoryGoodsEntityList()
                                                    );
        resultDto.setResultCategoryGoodsRegist(resultMap.get(CategoryGoodsRegistLogic.CATEGORY_GOODS_REGIST));
        resultDto.setResultCategoryGoodsDelete(resultMap.get(CategoryGoodsRegistLogic.CATEGORY_GOODS_DELETE));

        // (14) 関連商品登録Logic処理
        resultMap = goodsRelationRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                     goodsRelationEntityList
                                                    );
        resultDto.setResultGoodsRelationRegist(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_REGIST));
        resultDto.setResultGoodsRelationUpdate(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_UPDATE));
        resultDto.setResultGoodsRelationDelete(resultMap.get(GoodsRelationRegistLogic.GOODS_RELATION_DELETE));

        // 2023-renew No21 from here
        resultMap = goodsTogetherBuyGroupRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                             goodsTogetherBuyGroupEntityList);
        resultDto.setResultGoodsTogetherBuyGroupRegist(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_REGIST));
        resultDto.setResultGoodsTogetherBuyGroupUpdate(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_UPDATE));
        resultDto.setResultGoodsTogetherBuyGroupDelete(
                        resultMap.get(GoodsTogetherBuyGroupRegistLogic.GOODS_TOGETHER_BUY_GROUP_DELETE));
        // 2023-renew No21 to here

        // (15) 商品グループ画像登録Logic処理
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            // 画面からの場合（画像ファイルも含めて登録更新用Logic）
            Map<String, Object> resultMap2 =
                            goodsGroupImageRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                               goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(),
                                                               goodsGroupImageRegistUpdateDtoList
                                                              );
            resultDto.setResultGoodsGroupImageRegist(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST));
            resultDto.setResultGoodsGroupImageUpdate(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE));
            resultDto.setResultGoodsGroupImageDelete(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE));
            resultDto.setDeleteImageFilePathList(
                            (List<String>) resultMap2.get(GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST));
            resultDto.setRegistImageFilePathList((List<FileRegistDto>) resultMap2.get(
                            GoodsGroupImageRegistLogic.REGIST_IMAGE_FILE_PATH_LIST));
        } else if (processType == PROCESS_TYPE_FROM_CSV) {
            // 画面からの場合（画像ファイルを含めない登録更新用Logic）
            Map<String, Object> resultMap2 = goodsGroupImageRegistDataOnlyLogic.execute(
                            goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                            goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(),
                            goodsGroupDto.getGoodsGroupImageEntityList()
                                                                                       );
            resultDto.setResultGoodsGroupImageRegist(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST));
            resultDto.setResultGoodsGroupImageUpdate(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE));
            resultDto.setResultGoodsGroupImageDelete(
                            (Integer) resultMap2.get(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE));
            resultDto.setDeleteImageFilePathList(
                            (List<String>) resultMap2.get(GoodsGroupImageRegistLogic.DELETE_IMAGE_FILE_PATH_LIST));
        }

        // (16) 商品・在庫設定・在庫登録更新用リスト編集
        // 商品Dtoリストから(登録更新用)商品エンティティリストを作成
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録更新用)在庫設定エンティティリストを作成
        List<StockSettingEntity> stockSettingEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録用)在庫エンティティリストを作成
        List<StockEntity> stockEntityList = new ArrayList<>();
        // 商品Dtoリストから(登録更新用)規格画像エンティティリストを作成
        List<GoodsImageEntity> goodsImageEntityList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            // (17) 商品SEQ採番Logic処理
            createRegistGoodsData(shopSeq, goodsEntityList, stockSettingEntityList, stockEntityList,
                                  goodsImageEntityList, goodsDto
                                 );
        }

        // (18) 商品登録Logic処理
        if (goodsEntityList.size() > 0) {
            resultMap = goodsRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                 goodsEntityList, administratorName
                                                );
            resultDto.setResultGoodsRegist(resultMap.get(GoodsRegistLogic.GOODS_REGIST));
            resultDto.setResultGoodsUpdate(resultMap.get(GoodsRegistLogic.GOODS_UPDATE));
        }

        // (19) 在庫設定登録Logic処理
        if (stockSettingEntityList.size() > 0) {
            resultMap = stockSettingRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                        stockSettingEntityList
                                                       );
            resultDto.setResultStockSettingRegist(resultMap.get(StockSettingRegistLogic.STOCK_SETTING_REGIST));
            resultDto.setResultStockSettingUpdate(resultMap.get(StockSettingRegistLogic.STOCK_SETTING_UPDATE));
        }

        // (20) 在庫登録Logic処理
        // 商品グループが新規登録の場合
        if (isRegist) {
            resultDto.setResultStockRegist(stockRegistLogic.execute(stockEntityList, administratorName));
        }
        // 商品規格の追加がある場合
        else if (!isRegist && stockEntityList.size() > 0) {
            // 追加した商品規格の在庫登録
            resultDto.setResultStockRegist(stockRegistLogic.execute(stockEntityList, administratorName));

            // 更新対象外の商品を抽出する
            List<Integer> excludeGoodsSeqList = new ArrayList<>();
            for (GoodsEntity goodsEntity : goodsEntityList) {
                for (StockEntity stockEntity : stockEntityList) {
                    if (goodsEntity.getGoodsSeq() == stockEntity.getGoodsSeq()) {
                        excludeGoodsSeqList.add(stockEntity.getGoodsSeq());
                    }
                }
            }

            // 在庫更新
            updateStock(goodsGroupDto.getGoodsDtoList(), excludeGoodsSeqList, administratorName);
        }
        // 更新のみの場合
        else {
            // 在庫更新
            updateStock(goodsGroupDto.getGoodsDtoList(), null, administratorName);
        }

        // (20-1) 商品規格画像登録Logic処理
        if (goodsImageEntityList.size() > 0) {
            Map<String, Object> resultMap3 =
                            goodsImageRegistLogic.execute(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq(),
                                                          goodsImageEntityList
                                                         );
            resultDto.setResultGoodsUnitImageRegist((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_REGIST));
            resultDto.setResultGoodsUnitImageUpdate((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_UPDATE));
            resultDto.setResultGoodsUnitImageDelete((Integer) resultMap3.get(GoodsImageRegistLogic.GOODS_IMAGE_DELETE));
            resultDto.setDeleteUnitImageFilePathList(
                            (List<String>) resultMap3.get(GoodsImageRegistLogic.DELETE_UNIT_IMAGE_FILE_PATH_LIST));
            resultDto.setRegistUnitImageFilePathList((List<FileRegistDto>) resultMap3.get(
                            GoodsImageRegistLogic.REGIST_UNIT_IMAGE_FILE_PATH_LIST));
        }

        String errorDeleteFilePath = "";

        // (21) 画像ファイル削除
        for (String deleteFilePath : resultDto.getDeleteImageFilePathList()) {
            errorDeleteFilePath = deleteFilePath;
            boolean ret = false;
            try {
                ret = fileOperationUtility.remove(deleteFilePath);
            } catch (IOException e) {
                LOGGER.warn("商品画像の削除に失敗しました。" + errorDeleteFilePath, e);
            }
            if (ret) {
                resultDto.setResultGoodsGroupImageFileDelete(resultDto.getResultGoodsGroupImageFileDelete() + 1);
            }
        }

        // (21-1) 規格画像ファイル削除
        String errorDeleteUnitImagePath = "";
        if (resultDto.getDeleteUnitImageFilePathList() != null) {
            for (String deleteFilePath : resultDto.getDeleteUnitImageFilePathList()) {
                errorDeleteUnitImagePath = deleteFilePath;
                boolean ret = false;
                try {
                    ret = fileOperationUtility.remove(deleteFilePath);
                } catch (IOException e) {
                    LOGGER.warn("商品規格画像の削除に失敗しました。" + errorDeleteUnitImagePath, e);
                }
                if (ret) {
                    resultDto.setResultGoodsUnitImageFileDelete(resultDto.getResultGoodsGroupImageFileDelete() + 1);
                }
            }
        }

        // (22) 画像ファイル登録（一時領域からフロント公開領域へ）
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            resultDto.setResultGoodsGroupImageFileRegist(
                            registGoodsGroupImageFile(resultDto.getRegistImageFilePathList(),
                                                      resultDto.getResultGoodsGroupImageFileRegist()
                                                     ));
            if (resultDto.getRegistUnitImageFilePathList() != null) {
                resultDto.setResultGoodsUnitImageFileRegist(
                                registGoodsGroupImageFile(resultDto.getRegistUnitImageFilePathList(),
                                                          resultDto.getResultGoodsUnitImageFileRegist()
                                                         ));
            }
        }

        // (24) 処理件数マップを返す
        return createReturnMap(resultDto, goodsGroupDto);
    }

    // PDR Migrate Customization to here

    /**
     * (23) 処理件数マップの作成
     * 各Logicの処理件数のマップを作成する<br/>
     *
     * @param resultDto     結果Dto
     * @param goodsGroupDto 商品グループDto
     * @return Map&lt;String, Object&gt;
     */
    protected Map<String, Object> createReturnMap(GoodsGroupRegistUpdateResultDto resultDto,
                                                  GoodsGroupDto goodsGroupDto) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put(GOODS_GROUP_SEQ, goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
        returnMap.put(GOODS_GROUP_REGIST, resultDto.getResultGoodsGroupRegist());
        returnMap.put(GOODS_GROUP_DISPLAY_REGIST, resultDto.getResultGoodsGroupDisplayRegist());
        returnMap.put(GOODS_GROUP_POPULARITY_REGIST, resultDto.getResultGoodsGroupPopularityRegist());
        returnMap.put(GOODS_GROUP_UPDATE, resultDto.getResultGoodsGroupUpdate());
        returnMap.put(GOODS_GROUP_DISPLAY_UPDATE, resultDto.getResultGoodsGroupDisplayUpdate());
        returnMap.put(CategoryGoodsRegistLogic.CATEGORY_GOODS_REGIST, resultDto.getResultCategoryGoodsRegist());
        returnMap.put(CategoryGoodsRegistLogic.CATEGORY_GOODS_DELETE, resultDto.getResultCategoryGoodsDelete());
        returnMap.put(GoodsRelationRegistLogic.GOODS_RELATION_REGIST, resultDto.getResultGoodsRelationRegist());
        returnMap.put(GoodsRelationRegistLogic.GOODS_RELATION_UPDATE, resultDto.getResultGoodsRelationUpdate());
        returnMap.put(GoodsRelationRegistLogic.GOODS_RELATION_DELETE, resultDto.getResultGoodsRelationDelete());
        returnMap.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_REGIST, resultDto.getResultGoodsGroupImageRegist());
        returnMap.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_UPDATE, resultDto.getResultGoodsGroupImageUpdate());
        returnMap.put(GoodsGroupImageRegistLogic.GOODS_GROUP_IMAGE_DELETE, resultDto.getResultGoodsGroupImageDelete());
        returnMap.put(GoodsRegistLogic.GOODS_REGIST, resultDto.getResultGoodsRegist());
        returnMap.put(GoodsRegistLogic.GOODS_UPDATE, resultDto.getResultGoodsUpdate());
        returnMap.put(StockSettingRegistLogic.STOCK_SETTING_REGIST, resultDto.getResultStockSettingRegist());
        returnMap.put(StockSettingRegistLogic.STOCK_SETTING_UPDATE, resultDto.getResultStockSettingUpdate());
        returnMap.put(STOCK_REGIST, resultDto.getResultStockRegist());
        returnMap.put(GOODS_GROUP_IMAGE_FILE_REGIST, resultDto.getResultGoodsGroupImageFileRegist());
        returnMap.put(GOODS_GROUP_IMAGE_FILE_DELETE, resultDto.getResultGoodsGroupImageFileDelete());
        returnMap.put(WARNING_MESSAGE, resultDto.getWarningMessage());

        return returnMap;
    }

    /**
     * テーブル＆レコードロック処理<br/>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    protected void executeTableLock(GoodsGroupDto goodsGroupDto, Object... customParams) {
        // 商品グループテーブルロックLogic処理
        goodsGroupTableLockLogic.execute();

        // カテゴリテーブルロックLogic処理
        categoryTableLockLogic.execute();

        // 商品グループレコードロックLogic処理
        if (goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() != null) {
            List<Integer> goodsGroupSeqList = new ArrayList<>();
            goodsGroupSeqList.add(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
            goodsGroupLockLogic.execute(goodsGroupSeqList, goodsGroupDto.getGoodsGroupEntity().getVersionNo());
        }

        // カテゴリレコードロックLogic処理
        if (goodsGroupDto.getCategoryGoodsEntityList() != null
            && goodsGroupDto.getCategoryGoodsEntityList().size() > 0) {
            List<Integer> categorySeqList = new ArrayList<>();
            for (CategoryGoodsEntity categoryGoodsEntity : goodsGroupDto.getCategoryGoodsEntityList()) {
                categorySeqList.add(categoryGoodsEntity.getCategorySeq());
            }
            categoryLockLogic.execute(categorySeqList);
        }
    }

    /**
     * 商品グループ登録データ作成<br/>
     *
     * @param processType                        処理種別（画面/CSV）
     * @param goodsGroupDto                      商品グループDTO
     * @param goodsRelationEntityList            関連商品エンティティリスト
     * @param goodsRelationEntityList            よく一緒に購入される商品エンティティリスト
     * @param goodsGroupImageRegistUpdateDtoList 商品グループ画像登録更新用DTOリスト
     * @param shopSeq                            ショップSEQ
     * @param customParams                       案件用引数
     * @return 商品グループ人気エンティティ
     */
    protected GoodsGroupPopularityEntity createRegistGoodsGroupData(int processType,
                                                                    GoodsGroupDto goodsGroupDto,
                                                                    List<GoodsRelationEntity> goodsRelationEntityList,
                                                                    // 2023-renew No21 from here
                                                                    List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                                                    // 2023-renew No21 to here
                                                                    List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList,
                                                                    Integer shopSeq,
                                                                    Object... customParams) {
        // (6) 商品グループSEQ採番Logic処理
        Integer goodsGroupSeq = newGoodsGroupSeqGetLogic.execute();
        // 採番した商品グループSEQを各データにセット
        // 商品グループ
        goodsGroupDto.getGoodsGroupEntity().setGoodsGroupSeq(goodsGroupSeq);
        // 商品グループ表示
        goodsGroupDto.getGoodsGroupDisplayEntity().setGoodsGroupSeq(goodsGroupSeq);
        // カテゴリ登録商品
        if (goodsGroupDto.getCategoryGoodsEntityList() != null) {
            for (CategoryGoodsEntity categoryGoodsEntity : goodsGroupDto.getCategoryGoodsEntityList()) {
                categoryGoodsEntity.setGoodsGroupSeq(goodsGroupSeq);
            }
        } else {
            goodsGroupDto.setCategoryGoodsEntityList(new ArrayList<>());
        }
        // 商品グループ画像
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            for (GoodsGroupImageRegistUpdateDto goodsGroupImageRegistUpdateDto : goodsGroupImageRegistUpdateDtoList) {
                goodsGroupImageRegistUpdateDto.getGoodsGroupImageEntity().setGoodsGroupSeq(goodsGroupSeq);
                // ファイル名に商品管理番号のディレクトリ名が付いていない場合に付ける(登録時にはつけることになる)
                if (goodsGroupImageRegistUpdateDto.getImageFileName() != null
                    && goodsGroupImageRegistUpdateDto.getImageFileName().indexOf("/") < 0) {
                    goodsGroupImageRegistUpdateDto.setImageFileName(
                                    goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode() + "/"
                                    + goodsGroupImageRegistUpdateDto.getImageFileName());
                }
            }
        } else if (processType == PROCESS_TYPE_FROM_CSV) {
            for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupDto.getGoodsGroupImageEntityList()) {
                goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupSeq);
                // ファイル名に商品管理番号のディレクトリ名が付いていない場合に付ける(登録時にはつけることになる)
                if (goodsGroupImageEntity.getImageFileName() != null
                    && goodsGroupImageEntity.getImageFileName().indexOf("/") < 0) {
                    goodsGroupImageEntity.setImageFileName(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode() + "/"
                                                           + goodsGroupImageEntity.getImageFileName());
                }
            }
        }

        // 関連商品
        for (GoodsRelationEntity goodsRelationEntity : goodsRelationEntityList) {
            goodsRelationEntity.setGoodsGroupSeq(goodsGroupSeq);
        }

        // 2023-renew No21 from here
        // よく一緒に購入される商品
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            goodsTogetherBuyGroupEntity.setGoodsGroupSeq(goodsGroupSeq);
        }
        // 2023-renew No21 to here

        // 商品
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            goodsDto.getGoodsEntity().setGoodsGroupSeq(goodsGroupSeq);
            if (goodsDto.getUnitImage() != null) {
                goodsDto.getUnitImage().setGoodsGroupSeq(goodsGroupSeq);
            }
        }
        // 商品グループ人気（初期データ生成）
        GoodsGroupPopularityEntity goodsGroupPopularityEntity =
                        ApplicationContextUtility.getBean(GoodsGroupPopularityEntity.class);
        goodsGroupPopularityEntity.setGoodsGroupSeq(goodsGroupSeq);
        goodsGroupPopularityEntity.setPopularityCount(0);

        // (7) ショップSEQをセット
        goodsGroupDto.getGoodsGroupEntity().setShopSeq(shopSeq);
        return goodsGroupPopularityEntity;
    }

    /**
     * 商品グループ更新データ作成<br/>
     *
     * @param processType                        処理種別（画面/CSV）
     * @param goodsGroupDto                      商品グループDTO
     * @param goodsGroupImageRegistUpdateDtoList 商品グループ画像登録更新用DTOリスト
     * @param customParams                       案件用引数
     */
    protected void createUpdateGoodsGroupData(int processType,
                                              GoodsGroupDto goodsGroupDto,
                                              List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList,
                                              Object... customParams) {
        // 商品グループ画像
        if (processType == PROCESS_TYPE_FROM_SCREEN) {
            for (GoodsGroupImageRegistUpdateDto goodsGroupImageRegistUpdateDto : goodsGroupImageRegistUpdateDtoList) {
                // ファイル名に商品管理番号のディレクトリ名が付いていない場合に付ける(登録時にはつけることになる)
                if (goodsGroupImageRegistUpdateDto.getImageFileName() != null
                    && goodsGroupImageRegistUpdateDto.getImageFileName().indexOf("/") < 0) {
                    goodsGroupImageRegistUpdateDto.setImageFileName(
                                    goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode() + "/"
                                    + goodsGroupImageRegistUpdateDto.getImageFileName());
                }
            }
        } else if (processType == PROCESS_TYPE_FROM_CSV) {
            for (GoodsGroupImageEntity goodsGroupImageEntity : goodsGroupDto.getGoodsGroupImageEntityList()) {
                goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
                // ファイル名に商品管理番号のディレクトリ名が付いていない場合に付ける(登録時にはつけることになる)
                if (goodsGroupImageEntity.getImageFileName() != null
                    && goodsGroupImageEntity.getImageFileName().indexOf("/") < 0) {
                    goodsGroupImageEntity.setImageFileName(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode() + "/"
                                                           + goodsGroupImageEntity.getImageFileName());
                }
            }
        }
    }

    /**
     * 商品登録データ作成<br/>
     *
     * @param shopSeq                ショップSEQ
     * @param goodsEntityList        商品エンティティリスト
     * @param stockSettingEntityList 在庫設定エンティティリスト
     * @param stockEntityList        在庫エンティティリスト
     * @param goodsImageEntityList   規格画像エンティティリスト
     * @param goodsDto               商品DTO
     * @param customParams           案件用引数
     */
    protected void createRegistGoodsData(Integer shopSeq,
                                         List<GoodsEntity> goodsEntityList,
                                         List<StockSettingEntity> stockSettingEntityList,
                                         List<StockEntity> stockEntityList,
                                         List<GoodsImageEntity> goodsImageEntityList,
                                         GoodsDto goodsDto,
                                         Object... customParams) {
        if (goodsDto.getGoodsEntity().getGoodsSeq() == null) {
            // 新規登録商品の場合は商品エンティティと在庫Dtoに商品SEQの採番とショップSEQの設定を行う
            setGoodsSeqForGoodsDto(shopSeq, stockEntityList, goodsDto);
        }
        // 商品登録更新用リストに追加
        goodsEntityList.add(goodsDto.getGoodsEntity());

        // 在庫設定エンティティを初期化して登録更新用リストに追加
        StockSettingEntity stockSettingEntity = ApplicationContextUtility.getBean(StockSettingEntity.class);
        stockSettingEntity.setShopSeq(shopSeq);
        stockSettingEntity.setGoodsSeq(goodsDto.getStockDto().getGoodsSeq());
        stockSettingEntity.setRemainderFewStock(goodsDto.getStockDto().getRemainderFewStock());
        stockSettingEntity.setOrderPointStock(goodsDto.getStockDto().getOrderPointStock());
        stockSettingEntity.setSafetyStock(goodsDto.getStockDto().getSafetyStock());
        stockSettingEntity.setRegistTime(goodsDto.getStockDto().getRegistTime());
        stockSettingEntity.setUpdateTime(goodsDto.getStockDto().getUpdateTime());
        stockSettingEntityList.add(stockSettingEntity);
        if (goodsDto.getUnitImage() != null) {
            goodsDto.getUnitImage().setGoodsGroupSeq(goodsDto.getGoodsEntity().getGoodsGroupSeq());
            goodsDto.getUnitImage().setGoodsSeq(goodsDto.getStockDto().getGoodsSeq());
            goodsImageEntityList.add(goodsDto.getUnitImage());
        }
    }

    /**
     * 商品Dto配下のEntityに商品SEQをセットする
     *
     * @param shopSeq         ショップSEQ
     * @param stockEntityList 在庫エンティティリスト
     * @param goodsDto        商品DTO
     * @param customParams    案件用引数
     */
    protected void setGoodsSeqForGoodsDto(Integer shopSeq,
                                          List<StockEntity> stockEntityList,
                                          GoodsDto goodsDto,
                                          Object... customParams) {
        // (17) 商品SEQ採番Logic処理
        Integer goodsSeq = newGoodsSeqGetLogic.execute();
        goodsDto.getGoodsEntity().setShopSeq(shopSeq);
        goodsDto.getGoodsEntity().setGoodsSeq(goodsSeq);
        goodsDto.getStockDto().setShopSeq(shopSeq);
        goodsDto.getStockDto().setGoodsSeq(goodsSeq);
        if (goodsDto.getUnitImage() != null) {
            goodsDto.getUnitImage().setGoodsSeq(goodsSeq);
        }

        // 在庫エンティティを初期化して登録用リストに追加
        StockEntity stockEntity = ApplicationContextUtility.getBean(StockEntity.class);
        stockEntity.setShopSeq(shopSeq);
        stockEntity.setGoodsSeq(goodsSeq);
        // 商品新規登録時の入荷数を実在庫にセット
        if (goodsDto.getStockDto().getSupplementCount() != null) {
            stockEntity.setRealStock(goodsDto.getStockDto().getSupplementCount());
        } else {
            stockEntity.setRealStock(BigDecimal.ZERO);
        }
        stockEntity.setOrderReserveStock(BigDecimal.ZERO);
        stockEntityList.add(stockEntity);
    }

    /**
     * 画像ファイル登録（一時領域からフロント公開領域へ）<br/>
     *
     * @param registImageFilePathList         登録用画像ファイルパスリスト
     * @param resultGoodsGroupImageFileRegist 画像ファイル登録件数
     * @param customParams                    案件用引数
     * @return 画像ファイル登録件数
     */
    protected int registGoodsGroupImageFile(List<FileRegistDto> registImageFilePathList,
                                            int resultGoodsGroupImageFileRegist,
                                            Object... customParams) {
        for (FileRegistDto registFilePath : registImageFilePathList) {
            String strImgDir = registFilePath.getToFilePath()
                                             .substring(0, registFilePath.getToFilePath().lastIndexOf("/"));
            if (!new File(strImgDir).exists()) {
                new File(strImgDir).mkdir();
            }
            try {
                fileOperationUtility.put(registFilePath.getFromFilePath(), registFilePath.getToFilePath(), true);
            } catch (IOException e) {
                LOGGER.warn("移動元画像の削除に失敗しました。", e);
            }
            resultGoodsGroupImageFileRegist++;
        }
        return resultGoodsGroupImageFileRegist;
    }

    /**
     * 在庫更新<br/>
     * ※新規登録は実施しない。登録済みの商品の在庫更新が対象
     *
     * @param goodsDtoList        商品Dtoリスト
     * @param excludeGoodsSeqList 更新除外対象の商品SEQ
     */
    protected void updateStock(List<GoodsDto> goodsDtoList,
                               List<Integer> excludeGoodsSeqList,
                               String administratorName) {

        for (GoodsDto goodsDto : goodsDtoList) {
            // 入庫数が0以外の場合のみ更新する
            if (goodsDto.getStockDto().getSupplementCount() != null
                && goodsDto.getStockDto().getSupplementCount().compareTo(BigDecimal.ZERO) != 0) {

                // 更新除外対の商品SEQが含まれている場合、スキップする
                if (excludeGoodsSeqList != null && excludeGoodsSeqList.contains(
                                goodsDto.getGoodsEntity().getGoodsSeq())) {
                    continue;
                }

                StockResultEntity stockResultEntity = ApplicationContextUtility.getBean(StockResultEntity.class);

                // 入庫実績エンティティへ設定する。
                stockResultEntity.setGoodsSeq(goodsDto.getGoodsEntity().getGoodsSeq());
                stockResultEntity.setSupplementCount(goodsDto.getStockDto().getSupplementCount());

                // 入庫更新＋入庫実績登録
                stockSupplementService.execute(
                                stockResultEntity, goodsDto.getGoodsEntity().getGoodsCode(), administratorName);
            }
        }
    }

    /**
     * 商品グループ(最安値・最高値)の算出<br/>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    @Override
    public void calculateGoodsGroupPrice(GoodsGroupDto goodsGroupDto, Object... customParams) {
        // PDR Migrate Customization from here
        // 商品グループ(最安値・最高値・商品表示単価)の算出
        Map<GoodsDisplayPriceLogic.Key, Object> prices = goodsDisplayPriceLogic.create(goodsGroupDto, false);
        // PDR Migrate Customization to here

        // 商品最高値を設定する
        goodsGroupDto.getGoodsGroupEntity()
                     .setGoodsGroupMaxPricePc((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MAX_PRICE_PC));

        // 商品最安値を設定する
        goodsGroupDto.getGoodsGroupEntity()
                     .setGoodsGroupMinPricePc((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MIN_PRICE_PC));

        // 2023-renew AddNo5 from here
        if (Objects.nonNull(prices.get(GoodsDisplayPriceLogic.Key.HAS_SALE_PRICE))
            && (boolean) prices.get(GoodsDisplayPriceLogic.Key.HAS_SALE_PRICE)) {
            if (Objects.nonNull(prices.get(GoodsDisplayPriceLogic.Key.MAX_PRICE_MB))
                && BigDecimal.ZERO.compareTo((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MAX_PRICE_MB)) != 0) {
                goodsGroupDto.getGoodsGroupEntity()
                             .setGoodsGroupMaxPriceMb((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MAX_PRICE_MB));
            }
            if (Objects.nonNull(prices.get(GoodsDisplayPriceLogic.Key.MIN_PRICE_MB))
                && GoodsUtility.GOODS_DISPLAY_MAX_PRICE.compareTo((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MIN_PRICE_MB)) != 0) {
                goodsGroupDto.getGoodsGroupEntity()
                             .setGoodsGroupMinPriceMb((BigDecimal) prices.get(GoodsDisplayPriceLogic.Key.MIN_PRICE_MB));
            }
        } else {
            goodsGroupDto.getGoodsGroupEntity().setGoodsGroupMaxPriceMb(BigDecimal.ZERO);
            goodsGroupDto.getGoodsGroupEntity().setGoodsGroupMinPriceMb(BigDecimal.ZERO);
        }
        // 2023-renew AddNo5 to here
    }

}
