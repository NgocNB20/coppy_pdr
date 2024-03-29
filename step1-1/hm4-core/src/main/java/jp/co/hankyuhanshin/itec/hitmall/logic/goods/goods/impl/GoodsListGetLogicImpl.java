/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品リスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.7 $
 */
@Component
public class GoodsListGetLogicImpl extends AbstractShopLogic implements GoodsListGetLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    /**
     * 在庫DAO
     */
    private final StockDao stockDao;

    /**
     * 商品規格画像Dao
     */
    private final GoodsImageDao goodsImageDao;

    @Autowired
    public GoodsListGetLogicImpl(GoodsDao goodsDao, StockDao stockDao, GoodsImageDao goodsImageDao) {
        this.goodsDao = goodsDao;
        this.stockDao = stockDao;
        this.goodsImageDao = goodsImageDao;
    }

    /**
     * 商品リスト取得<br/>
     * 商品情報リストを取得する。<br/>
     *
     * @param goodsSearchForDaoConditionDto 商品Dao用検索条件DTO
     * @return 商品DTOリスト
     */
    @Override
    public List<GoodsDto> execute(GoodsSearchForDaoConditionDto goodsSearchForDaoConditionDto) {

        // (1) パラメータチェック
        // 商品Dao用検索条件DTOが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsSearchForDaoConditionDto", goodsSearchForDaoConditionDto);

        // (2) 商品情報リスト取得処理
        // 商品Daoの商品リスト取得処理を実行する。
        // DAO GoodsDao
        // メソッド List<商品エンティティ> getSearchGoodsList( （パラメータ）商品Dao用検索条件DTO)
        List<GoodsEntity> goodsEntityList = goodsDao.getSearchGoodsList(goodsSearchForDaoConditionDto);

        // (2) で取得した商品エンティティリストから商品エンティティ．商品SEQのリストを作成する。
        List<Integer> goodsSeqList = new ArrayList<>();
        for (int i = 0; i < goodsEntityList.size(); i++) {
            GoodsEntity goodsEntity = goodsEntityList.get(i);
            goodsSeqList.add(goodsEntity.getGoodsSeq());
        }

        // 在庫情報取得
        Map<Integer, StockDto> stockDtoMap = new HashMap<>();
        if (goodsSeqList.size() > 0) {
            List<StockDto> stockDtoList = stockDao.getStockList(goodsSeqList);
            for (StockDto stockDto : stockDtoList) {
                stockDtoMap.put(stockDto.getGoodsSeq(), stockDto);
            }
        }

        // 在庫情報取得
        Map<Integer, GoodsImageEntity> imageEntityMap = new HashMap<>();
        if (goodsSeqList.size() > 0) {
            List<GoodsImageEntity> goodsImageEntityList = goodsImageDao.getGoodsImageListByGoodsSeqList(goodsSeqList);
            for (GoodsImageEntity imageEntity : goodsImageEntityList) {
                imageEntityMap.put(imageEntity.getGoodsSeq(), imageEntity);
            }
        }

        // (4) （戻り値用）商品DTOリストを編集する。
        // （戻り値用）商品DTOリストを初期化する。
        // ・(2)で取得した商品エンティティリストについて以下の処理を行う
        // ①商品DTOオブジェクトを初期生成する。
        // ②商品DTOに商品エンティティをセットする
        // ③商品エンティティ．商品SEQをキー項目として(3)で取得した商品画像マップから商品画像エンティティリストを取得し、商品DTOにセットする。
        // ※③で取得できない場合はエラーログを出力する
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for (int i = 0; i < goodsEntityList.size(); i++) {
            GoodsDto goodsDto = ApplicationContextUtility.getBean(GoodsDto.class);
            goodsDto.setGoodsEntity(goodsEntityList.get(i));
            goodsDto.setStockDto(stockDtoMap.get(goodsDto.getGoodsEntity().getGoodsSeq()));
            goodsDto.setUnitImage(imageEntityMap.get(goodsDto.getGoodsEntity().getGoodsSeq()));

            // ⑤商品DTOを（戻り値用）商品DTOリストに追加する。
            goodsDtoList.add(goodsDto);
        }

        // (5) 戻り値
        // （戻り値用）商品DTOリストを返す。
        return goodsDtoList;
    }

    // 2023-renew No92 from here
    /**
     * 商品リスト取得<br/>
     * 商品情報リストを取得する。<br/>
     *
     * @param goodsCodeList    商品コードリスト
     * @return 商品DTOリスト
     */
    @Override
    public List<GoodsDto> execute(List<String> goodsCodeList) {
        // (1) パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("goodsCodeList", goodsCodeList);

        // (2) 商品情報リスト取得処理
        // 商品Daoの商品リスト取得処理を実行する。
        // DAO GoodsDao
        // メソッド List<商品エンティティ> getSearchGoodsList( （パラメータ）リスト)
        List<GoodsEntity> goodsEntityList = goodsDao.getGoodsListByGoodsCodeList(goodsCodeList);

        // (2) で取得した商品エンティティリストから商品エンティティ．商品SEQのリストを作成する。
        List<Integer> goodsSeqList = new ArrayList<>();
        for (int i = 0; i < goodsEntityList.size(); i++) {
            GoodsEntity goodsEntity = goodsEntityList.get(i);
            goodsSeqList.add(goodsEntity.getGoodsSeq());
        }

        // 在庫情報取得
        Map<Integer, StockDto> stockDtoMap = new HashMap<>();
        if (goodsSeqList.size() > 0) {
            List<StockDto> stockDtoList = stockDao.getStockList(goodsSeqList);
            for (StockDto stockDto : stockDtoList) {
                stockDtoMap.put(stockDto.getGoodsSeq(), stockDto);
            }
        }

        // 商品画像情報取得
        Map<Integer, GoodsImageEntity> imageEntityMap = new HashMap<>();
        if (goodsSeqList.size() > 0) {
            List<GoodsImageEntity> goodsImageEntityList = goodsImageDao.getGoodsImageListByGoodsSeqList(goodsSeqList);
            for (GoodsImageEntity imageEntity : goodsImageEntityList) {
                imageEntityMap.put(imageEntity.getGoodsSeq(), imageEntity);
            }
        }

        // (4) （戻り値用）商品DTOリストを編集する。
        // （戻り値用）商品DTOリストを初期化する。
        // ・(2)で取得した商品エンティティリストについて以下の処理を行う
        // ①商品DTOオブジェクトを初期生成する。
        // ②商品DTOに商品エンティティをセットする
        // ③商品エンティティ．商品SEQをキー項目として(3)で取得した商品画像マップから商品画像エンティティリストを取得し、商品DTOにセットする。
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for (int i = 0; i < goodsEntityList.size(); i++) {
            GoodsDto goodsDto = ApplicationContextUtility.getBean(GoodsDto.class);
            goodsDto.setGoodsEntity(goodsEntityList.get(i));
            goodsDto.setStockDto(stockDtoMap.get(goodsDto.getGoodsEntity().getGoodsSeq()));
            goodsDto.setUnitImage(imageEntityMap.get(goodsDto.getGoodsEntity().getGoodsSeq()));

            // ⑤商品DTOを（戻り値用）商品DTOリストに追加する。
            goodsDtoList.add(goodsDto);
        }

        // (5) 戻り値
        // （戻り値用）商品DTOリストを返す。
        return goodsDtoList;
    }
    // 2023-renew No92 to here
}
