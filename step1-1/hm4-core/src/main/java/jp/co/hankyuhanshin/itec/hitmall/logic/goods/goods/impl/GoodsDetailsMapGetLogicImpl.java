/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品詳細情報MAP取得<br/>
 * 商品詳細Dtoを保持するマップを取得する。<br/>
 *
 * @author ozaki
 */
@Component
public class GoodsDetailsMapGetLogicImpl extends AbstractShopLogic implements GoodsDetailsMapGetLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDetailsMapGetLogicImpl.class);

    /**
     * 商品情報DAO
     */
    private GoodsDao goodsDao;

    /**
     * 商品グループ画像取得Logic
     */
    private GoodsGroupImageGetLogic goodsGroupImageGetLogic;

    /**
     * 商品規格画像Dao
     */
    private final GoodsImageDao goodsImageDao;

    /**
     * コンストラクタ
     *
     * @param goodsDao
     * @param goodsGroupImageGetLogic
     * @param goodsImageDao
     */
    @Autowired
    public GoodsDetailsMapGetLogicImpl(GoodsDao goodsDao,
                                       GoodsGroupImageGetLogic goodsGroupImageGetLogic,
                                       GoodsImageDao goodsImageDao) {
        this.goodsDao = goodsDao;
        this.goodsGroupImageGetLogic = goodsGroupImageGetLogic;
        this.goodsImageDao = goodsImageDao;
    }

    /**
     * 商品詳細Dtoを保持するマップを取得する。<br/>
     *
     * @param goodsSeqList 商品シーケンスリスト
     * @return 商品詳細情報MAP
     */
    @Override
    public Map<Integer, GoodsDetailsDto> execute(List<Integer> goodsSeqList) {
        // (1) パラメータチェック
        // 商品SEQリストがnullでないことをチェック
        ArgumentCheckUtil.assertNotNull("goodsSeqList", goodsSeqList);

        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsList(goodsSeqList);

        // 編集した商品詳細マップを返す。
        return createGoodsDetailsMap(goodsDetailsDtoList, this.createGoodsImageMapByGoodsSeqList(goodsSeqList));
    }

    @Override
    public Map<String, GoodsDetailsDto> executeByGoodsCode(List<String> goodsCodeList) {
        ArgumentCheckUtil.assertNotNull("goodsCodeList", goodsCodeList);

        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsListByGoodsCodeList(goodsCodeList);

        List<Integer> goodsSeqList =
                        goodsDetailsDtoList.stream().map(GoodsDetailsDto::getGoodsSeq).collect(Collectors.toList());
        Map<Integer, GoodsDetailsDto> tmpResult = createGoodsDetailsMap(goodsDetailsDtoList,
                                                                        this.createGoodsImageMapByGoodsSeqList(
                                                                                        goodsSeqList)
                                                                       );

        Map<String, GoodsDetailsDto> result = new LinkedHashMap<>();
        for (String goodsCode : goodsCodeList) {
            for (GoodsDetailsDto goodsDetailsDto : tmpResult.values()) {
                if (goodsDetailsDto.getGoodsCode().equals(goodsCode)) {
                    result.put(goodsCode, goodsDetailsDto);
                    tmpResult.remove(goodsDetailsDto.getGoodsSeq());
                    break;
                }
            }
        }
        return result;
    }

    // PDR Migrate Customization from here

    /**
     * 基幹連携で取得した情報で商品詳細Dtoを保持するマップを取得する<br/>
     * EC側DBに商品が存在しない場合を考慮し、nullでエラーになる前にreturnする<br/>
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細情報MAP
     */
    @Override
    public Map<String, GoodsDetailsDto> executeByExistGoodsCode(List<String> goodsCodeList) {
        ArgumentCheckUtil.assertNotNull("goodsCodeList", goodsCodeList);

        Map<String, GoodsDetailsDto> result = new LinkedHashMap<>();
        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsListByGoodsCodeList(goodsCodeList);
        List<Integer> goodsSeqList =
                        goodsDetailsDtoList.stream().map(GoodsDetailsDto::getGoodsSeq).collect(Collectors.toList());
        // goodsDetailsDtoListがnull or 空ならばreturn
        if (goodsDetailsDtoList == null || goodsDetailsDtoList.isEmpty()) {
            return result;
        }
        Map<Integer, GoodsDetailsDto> tmpResult = createGoodsDetailsMap(goodsDetailsDtoList,
                                                                        this.createGoodsImageMapByGoodsSeqList(
                                                                                        goodsSeqList)
                                                                       );
        for (String goodsCode : goodsCodeList) {
            for (GoodsDetailsDto goodsDetailsDto : tmpResult.values()) {
                if (goodsDetailsDto.getGoodsCode().equals(goodsCode)) {
                    result.put(goodsCode, goodsDetailsDto);
                    tmpResult.remove(goodsDetailsDto.getGoodsSeq());
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 基幹連携で取得した情報で商品詳細Dtoを保持するマップを取得する<br/>
     * EC側DBに商品が存在しない場合、存在しない商品番号を格納したMapを返す
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細情報MAP
     */
    @Override
    public Map<String, GoodsDetailsDto> executeByResponseGoodsCode(List<String> goodsCodeList) {
        ArgumentCheckUtil.assertNotNull("goodsCodeList", goodsCodeList);

        Map<String, GoodsDetailsDto> result = new LinkedHashMap<>();
        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDao.getGoodsDetailsListByGoodsCodeList(goodsCodeList);
        List<Integer> goodsSeqList =
                        goodsDetailsDtoList.stream().map(GoodsDetailsDto::getGoodsSeq).collect(Collectors.toList());

        List<String> existGoodsCodeList = new ArrayList<>();
        for (GoodsDetailsDto dto : goodsDetailsDtoList) {
            existGoodsCodeList.add(dto.getGoodsCode());
        }
        // ECに存在するかチェック
        for (String goodsCode : goodsCodeList) {
            if (CollectionUtils.isEmpty(existGoodsCodeList) || !existGoodsCodeList.contains(goodsCode)) {
                // 存在しない場合は、存在しない商品番号を格納したMapを返す
                result.put(goodsCode, null);
                return result;
            }
        }

        Map<Integer, GoodsDetailsDto> tmpResult = createGoodsDetailsMap(goodsDetailsDtoList,
                                                                        this.createGoodsImageMapByGoodsSeqList(
                                                                                        goodsSeqList)
                                                                       );
        for (String goodsCode : goodsCodeList) {
            for (GoodsDetailsDto goodsDetailsDto : tmpResult.values()) {
                if (goodsDetailsDto.getGoodsCode().equals(goodsCode)) {
                    result.put(goodsCode, goodsDetailsDto);
                    tmpResult.remove(goodsDetailsDto.getGoodsSeq());
                    break;
                }
            }
        }
        return result;
    }
    // PDR Migrate Customization to here

    /**
     * 商品詳細情報MAP取得<br/>
     * 商品詳細Dtoを保持するマップを取得する。<br/>
     *
     * @param goodsDetailsDtoList 商品詳細Dtoリスト
     * @param imageEntityMap      イメージマップ
     * @return 商品詳細情報MAP
     */
    protected Map<Integer, GoodsDetailsDto> createGoodsDetailsMap(List<GoodsDetailsDto> goodsDetailsDtoList,
                                                                  Map<Integer, GoodsImageEntity> imageEntityMap) {
        // (3) 商品グループ画像情報取得
        // (2) で取得した商品詳細DTOリストから商品詳細DTO．商品グループSEQのリストを作成する。
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoList) {
            goodsGroupSeqList.add(goodsDetailsDto.getGoodsGroupSeq());
        }

        // 商品グループ画像取得Logicを利用して、商品画像マップを取得する
        // Logic GoodsGroupImageGetLogic
        // パラメータ 商品グループSEQリスト
        // 戻り値 商品グループ画像マップ
        Map<Integer, List<GoodsGroupImageEntity>> goodsGroupImageMap =
                        goodsGroupImageGetLogic.execute(goodsGroupSeqList);

        // 商品規格画像取得Logicを利用して、商品画像マップを取得する
        // Map<Integer, List<GoodsImageDto>> goodsImageMap =
        // goodsImageGetLogic.getGoodsImageDtoMap(goodsGroupSeqList, true);

        // (3) 商品詳細マップの生成
        // 商品詳細マップを初期生成する。
        // ・（(2)で取得した）商品詳細DTOリストの件数分、以下の処理を繰り返す
        // ①商品詳細DTO．商品グループSEQをキー項目として(3)で取得した商品グループ画像マップから商品グループ画像エンティティリストを取得し、
        // 商品詳細DTOにセットする。
        // ※取得できない場合はエラーログを出力する
        // ②キー項目：商品詳細DTO. 商品SEQ、 設定値：商品詳細DTO で商品詳細マップに追加する。
        Map<Integer, GoodsDetailsDto> goodsDetailsMap = new HashMap<>();
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoList) {

            List<GoodsGroupImageEntity> goodsImageEntityList =
                            goodsGroupImageMap.get(goodsDetailsDto.getGoodsGroupSeq());
            goodsDetailsDto.setGoodsGroupImageEntityList(goodsImageEntityList);

            GoodsImageEntity goodsImageEntity = imageEntityMap.get(goodsDetailsDto.getGoodsSeq());
            goodsDetailsDto.setUnitImage(goodsImageEntity);

            if (goodsImageEntityList == null) {
                // log
                LOGGER.debug("「商品SEQ：" + goodsDetailsDto.getGoodsGroupSeq() + "の商品画像が存在しません。");
            }
            // List<GoodsImageDto> goodsImageDtoList =
            // goodsImageMap.get(goodsDetailsDto.getGoodsGroupSeq());
            // goodsDetailsDto.setGoodsImageDtoList(goodsImageDtoList);

            // if (goodsImageDtoList == null) {
            // // log
            // LOGGER.debug("「商品SEQ：" + goodsDetailsDto.getGoodsGroupSeq() +
            // "の商品画像が存在しません。");
            // }
            goodsDetailsMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
        }

        // (4) 戻り値
        // 編集した商品詳細マップを返す。
        return goodsDetailsMap;
    }

    /**
     * 製品イメージマップの作成
     *
     * @param goodsSeqList
     * @return 製品イメージマップ
     */
    protected Map<Integer, GoodsImageEntity> createGoodsImageMapByGoodsSeqList(List<Integer> goodsSeqList) {
        // 在庫情報取得
        Map<Integer, GoodsImageEntity> imageEntityMap = new HashMap<>();
        if (!goodsSeqList.isEmpty()) {
            List<GoodsImageEntity> goodsImageEntityList = goodsImageDao.getGoodsImageListByGoodsSeqList(goodsSeqList);
            for (GoodsImageEntity imageEntity : goodsImageEntityList) {
                imageEntityMap.put(imageEntity.getGoodsSeq(), imageEntity);
            }
        }
        return imageEntityMap;
    }

}
